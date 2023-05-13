from fastapi import APIRouter, status
from .. import schemas

router = APIRouter(
    prefix="/student_management",
    tags=["student_management"]
)

@router.post("/", status_code=status.HTTP_200_OK, response_model=schemas.AddStudentToCourseResponse)
async def add_student_to_course(request:schemas.AddStudentToCourseResponse): # Add student to suitable course
    # Code is here
    response = {
        "message": "Some message"
    }
    return response