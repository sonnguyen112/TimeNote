from fastapi import Request, APIRouter, status, Depends
from .. import schemas, models
from sqlalchemy.orm import Session
from ..database import get_db

router = APIRouter(
    prefix="/attendance_api/student_management",
    tags=["student_management"]
)

@router.post("/", status_code=status.HTTP_200_OK, response_model=schemas.AddStudentToCourseResponse)
async def add_student_to_course(request: schemas.AddStudentToCourseRequest, db: Session = Depends(get_db)): # Add student to suitable course
    records = db.query(models.StudentManagement).filter(models.StudentManagement.course_id==request.course_id).all() 
    students = list(map(lambda x: x.student_code, records))
    for code in request.student_codes:
        if code in students:
            continue
        record = models.StudentManagement(course_id = request.course_id, student_code = code)
        db.add(record)
        db.commit()
    response = {
        "message": "Success"
    }
    return response