from fastapi import responses, APIRouter, status, Depends
from .. import schemas
from typing import List
from .. import models
from sqlalchemy.orm import Session
from ..database import get_db
from datetime import datetime


router = APIRouter(
    prefix="/attendance_api/course_activation",
    tags=["course_activation"]
)

@router.get("/", status_code=status.HTTP_200_OK, response_model=schemas.ActiveToggleResponse)
async def active_toggle(course_id:str, longitude: float,latitude: float,db: Session = Depends(get_db)): # Change status of host(Attendance/Not Attendance)
    # Code is here
    status = False
    records = db.query(models.CourseActivation).filter(models.CourseActivation.course_id==course_id).first()
    if records is not None:
        db.delete(records)
        status = False
    else:
        records = models.CourseActivation(course_id=course_id,coord="{}-{}".format(longitude,latitude))
        db.add(records)
        status = True
    if status:
        student_records = db.query(models.StudentManagement).filter(models.StudentManagement.course_id == course_id).all()
        for student_record in student_records:
            student_status_record = models.StudentStatus(student_code=student_record.student_code,course_id=student_record.course_id)
            db.add(student_status_record)
    else:
        student_status_records = db.query(models.StudentStatus).filter(models.StudentStatus.course_id==course_id).all()
        for student_status_record in student_status_records:
            history_status_record = models.HistoryTable(course_id = student_status_record.course_id,student_code =student_status_record.student_code,
                                                         date_time=datetime.now().strftime("%H:%M:%S-%d/%m/%Y"))
            db.add(history_status_record)
            db.delete(student_status_record)
    db.commit()
    response = {
        "is_active": status
    }
    return response

@router.get("/course_avaiable", status_code=status.HTTP_200_OK, response_model=List[schemas.GetCoursesActiveResponse])
async def get_courses_active(db: Session = Depends(get_db)): # Get all course avaiable
    # Code is here
    response = db.query(models.CourseActivation).all()
    response = list(map(lambda x: {
        "course_id": x.course_id,
        "coord": x.coord
    }, response))
    return response

@router.get("/checking_student", status_code=status.HTTP_200_OK)
async def get_checking_student(student_code: str, db:  Session = Depends(get_db)):
    try:
        student_record = db.query(models.StudentStatus).filter(models.StudentStatus.student_code==student_code).first()
        if student_code is not None:
            db.delete(student_record)
        db.commit()
        response = {
            "message":"OK"
        }
        return response
    except Exception as e:
        print(e)
        return{
            "message": "something wrong"
        }
    
@router.get("/get_student_absent", status_code=status.HTTP_200_OK)
async def get_student_absent(course_id:str, db: Session = Depends(get_db)):
    # Code is here
    response = db.query(models.StudentStatus).filter(models.StudentStatus.course_id==course_id).all()
    response = list(map(lambda x: {
        "student_id": x.student_code,
    }, response))
    return response

@router.get("/getting_course_history",status_code=status.HTTP_200_OK)
async def get_getting_course(course_id:str, db:  Session = Depends(get_db)):
    history_records = db.query(models.HistoryTable).filter(models.HistoryTable.course_id == course_id).distinct().all()
    dictionary=[]
    for history_record in history_records:
        dictionary.append(history_record.date_time)
    return list(set(dictionary))

@router.get("/getting_absent_student_history",status_code=status.HTTP_200_OK)
async def get_getting_absent_student(course_id:str, datetimestr,  db:  Session = Depends(get_db)):
    history_records = db.query(models.HistoryTable).filter(models.HistoryTable.course_id==course_id, models.HistoryTable.date_time==datetimestr).all()
    list_students=list()
    if history_records is not None:
        for history_record in history_records:
            list_students.append(history_record.student_code)
    return list_students
