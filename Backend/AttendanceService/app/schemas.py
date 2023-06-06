from pydantic import BaseModel
from typing import List

class ActiveToggleResponse(BaseModel):
    is_active: bool

class AddStudentToCourseRequest(BaseModel):
    course_id: str
    student_codes: List[str]

class AddStudentToCourseResponse(BaseModel):
    message: str

class GetCoursesActiveResponse(BaseModel):
    course_id: str
    coord: str

class FaceRecognizeRequest(BaseModel):
    img_base64: str
    student_id: str

class FaceRecognizeResponse(BaseModel):
    is_real: bool
    is_find: bool