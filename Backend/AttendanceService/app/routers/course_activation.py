from fastapi import responses, APIRouter, status, Depends
from .. import schemas
from typing import List
from .. import models
from sqlalchemy.orm import Session
from ..database import get_db


router = APIRouter(
    prefix="/attendance_api/course_activation",
    tags=["course_activation"]
)

@router.get("/", status_code=status.HTTP_200_OK, response_model=schemas.ActiveToggleResponse)
async def active_toggle(course_id:str, longtitude: float,latitude: float,db: Session = Depends(get_db)): # Change status of host(Attendance/Not Attendance)
    # Code is here
    status = False
    records = db.query(models.CourseActivation).filter(models.CourseActivation.course_id==course_id).first()
    if records is not None:
        db.delete(records)
        status = False
    else:
        records = models.CourseActivation(course_id=course_id,coord="{}-{}".format(longtitude,latitude))
        db.add(records)
        status = True
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