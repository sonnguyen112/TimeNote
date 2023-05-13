from fastapi import responses, APIRouter, status
from .. import schemas
from typing import List

router = APIRouter(
    prefix="/course_activation",
    tags=["course_activation"]
)

@router.get("/", status_code=status.HTTP_200_OK, response_model=schemas.ActiveToggleResponse)
async def active_toggle(request:schemas.ActiveToggleRequest): # Change status of host(Attendance/Not Attendance)
    # Code is here
    response = {
        "is_active": False
    }
    return response

@router.get("/{student_id}", status_code=status.HTTP_200_OK, response_model=List[schemas.GetCoursesActiveResponse])
async def get_courses_active(request: schemas.GetCoursesActiveRequest): # Get all course which have student and in area 100 metres
    # Code is here
    response = {}
    return response