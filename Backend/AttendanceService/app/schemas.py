from pydantic import BaseModel
from typing import List

class ActiveToggleResponse(BaseModel):
    is_active: bool

class AddStudentToCourseRequest(BaseModel):
    course_id: str
    student_codes: List[str]

class AddStudentToCourseResponse(BaseModel):
    message: str

class GetCoursesActiveRequest(BaseModel):
    student_code: str

class GetCoursesActiveResponse(BaseModel):
    course_id: str
    course_name: str
    course_code: str