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
# from check_real_face.src.anti_spoof_predict import AntiSpoofPredict
# from check_real_face.src.generate_patches import CropImage
# from check_real_face.src.utility import parse_model_name
# import os

# model_test = AntiSpoofPredict(0)
# image_cropper = CropImage()

router = APIRouter(
    prefix="/attendance_api/face_recognize",
    tags=["face_recognize"]
)

@router.post("/", status_code=status.HTTP_200_OK)
async def face_recognize(request: schemas.FaceRecognizeRequest, db: Session = Depends(get_db)):
    try:
        test_f = open("test_f.txt", 'w')
        test_f.write(request.img_base64)
        test_f.close
        record = db.query(models.FaceEncoding).filter(models.FaceEncoding.student_id==request.student_id).first()
        face_encoding = pickle.loads(bytes.fromhex(record.face_encoding))
        # print("Hehe", type(face_encodings[0]))
        unknown_image = np.array(Image.open(io.BytesIO(base64.b64decode(request.img_base64))))
        unknown_image = cv2.cvtColor(unknown_image, cv2.COLOR_RGB2BGR)
        unknown_image = cv2.resize(unknown_image, dsize=(int(unknown_image.shape[1]/4), int(unknown_image.shape[0]/4)), interpolation=cv2.INTER_CUBIC)
        unknown_image = cv2.rotate(unknown_image, cv2.ROTATE_90_CLOCKWISE)
        # if check_real_face(unknown_image) == False:
        #     return {
        #         "is_real": False,
        #         "is_find": False,
        #     }
        cv2.imwrite("test.jpg", unknown_image)
        unknown_encoding_list = face_recognition.face_encodings(unknown_image)
        print(unknown_encoding_list)
        if len(unknown_encoding_list) == 0:
            return {
                "message": "Face not found"
            } 
        unknown_encoding = unknown_encoding_list[0]
        results = face_recognition.compare_faces([face_encoding], unknown_encoding)
        print("Result:",results)
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
    except Exception as e:
        print(e)
        return {
            "message": "somthing wrong"
        }


# def check_real_face(image, model_dir="./check_real_face/resources/anti_spoof_models"):
#     image_bbox = model_test.get_bbox(image)
#     prediction = np.zeros((1, 3))
#     # sum the prediction from single model's result
#     for model_name in os.listdir(model_dir):
#         h_input, w_input, model_type, scale = parse_model_name(model_name)
#         param = {
#             "org_img": image,
#             "bbox": image_bbox,
#             "scale": scale,
#             "out_w": w_input,
#             "out_h": h_input,
#             "crop": True,
#         }
#         if scale is None:
#             param["crop"] = False
#         img = image_cropper.crop(**param)
#         prediction += model_test.predict(img, os.path.join(model_dir, model_name))

#     # draw result of prediction
#     label = np.argmax(prediction)
#     if label == 1:
#         return True
#     else: 
#         return False
