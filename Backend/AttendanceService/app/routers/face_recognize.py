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
import pickle
from PIL import Image
import io

router = APIRouter(
    prefix="/attendance_api/face_recognize",
    tags=["face_recognize"]
)

@router.post("/", status_code=status.HTTP_200_OK, response_model=schemas.FaceRecognizeResponse)
async def face_recognize(request: schemas.FaceRecognizeRequest, db: Session = Depends(get_db)):
    record = db.query(models.FaceEncoding).filter(models.FaceEncoding.student_id==request.student_id).first()
    face_encoding = pickle.loads(bytes.fromhex(record.face_encoding))
    # print("Hehe", type(face_encodings[0]))
    unknown_image = np.array(Image.open(io.BytesIO(base64.b64decode(request.img_base64))))
    unknown_image = cv2.resize(unknown_image, dsize=(int(unknown_image.shape[1]/4), int(unknown_image.shape[0]/4)), interpolation=cv2.INTER_CUBIC)
    unknown_encoding = face_recognition.face_encodings(unknown_image)[0]
    results = face_recognition.compare_faces([face_encoding], unknown_encoding)
    print(results)
    if True in results:
        return {
            "is_real": True,
            "is_find": True,
        }
    else:
        return {
            "is_real": True,
            "is_find": False,
        }



