from fastapi import responses, APIRouter, status, Depends
from .. import schemas
from typing import List
from .. import models
from sqlalchemy.orm import Session
from ..database import get_db
import numpy as np
import base64
import cv2
import face_recognition


router = APIRouter(
    prefix="/attendance_api/face_recognize",
    tags=["face_recognize"]
)

@router.post("/", status_code=status.HTTP_200_OK, response_model=schemas.FaceRecognizeResponse)
async def face_recognize(request: schemas.FaceRecognizeRequest, db: Session = Depends(get_db)):
    records = db.query(models.FaceEncoding).join(models.StudentManagement.student_code==
                                                      models.FaceEncoding.student_id).filter(models.StudentManagement.course_id==request.course_id).all()
    face_encodings = list(map(lambda x: x.face_encoding, records))
    unknown_image = np.frombuffer(base64.b64decode(request.img_base64))
    unknown_image = cv2.resize(unknown_image, dsize=(int(unknown_image.shape[1]/4), int(unknown_image.shape[0]/4)), interpolation=cv2.INTER_CUBIC)
    unknown_encoding = face_recognition.face_encodings(unknown_image)[0]
    results = face_recognition.compare_faces(face_encodings, unknown_encoding)
    if True in results:
        record_found = records[results.index(True)]
        return {
            "is_real": True,
            "is_find": True,
            "code": record_found.student_id
        }
    else:
        return {
            "is_real": True,
            "is_find": False,
            "code": ""
        }



