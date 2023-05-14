import base64
import pickle
import PIL.Image as Image
import io
import json
import face_recognition
from sqlalchemy.orm import Session
import AttendanceService.app.models
from AttendanceService.app.database import get_db

from ..config import loop, KAFKA_BOOTSTRAP_SERVERS,KAFKA_CONSUMER_GROUP,KAFKA_TOPIC
from aiokafka import AIOKafkaConsumer
from fastapi import APIRouter,Depends
import numpy as np
router = APIRouter()

def add_to_table(student_id,face_encoding):
    query = ("INSERT INTO faceencode (student_id,face_encoding)\
             values ({},{});".format(student_id,face_encoding))

def add_face(f,studentID, db: Session = next(get_db())):
    known_image = face_recognition.load_image_file(f)
    image_encoding = face_recognition.face_encodings(known_image)
    if len(image_encoding) != 0 :
        layer_image_encoding = image_encoding[0]
        str_encoding = str(pickle.dumps(layer_image_encoding))
        new_student_face =  AttendanceService.app.models.FaceEncoding(student_id=studentID, face_encoding=str_encoding)
        db.add(new_student_face)
        db.commit()
    else: 
        new_student_face =  AttendanceService.app.models.FaceEncoding(student_id=studentID, face_encoding="")
        db.add(new_student_face)
        db.commit()



async def consumer(): 
    consumer = AIOKafkaConsumer(KAFKA_TOPIC, loop= loop, bootstrap_servers= KAFKA_BOOTSTRAP_SERVERS, group_id= KAFKA_CONSUMER_GROUP )
    await consumer.start()
    try:
        async for msg in consumer:
            data_to_dictionary = json.loads(msg.value.decode()) #load binary to dictionary
            f = io.BytesIO(base64.b64decode(data_to_dictionary['byteArray'])) #convert stringbyte to base 64
            studentID = data_to_dictionary['studentCode']
            add_face(f,studentID)
         
    finally:
        await consumer.stop()
