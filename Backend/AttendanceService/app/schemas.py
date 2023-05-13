from pydantic import BaseModel

class ActiveToggleRequest(BaseModel):
    course_id: str

class ActiveToggleResponse(BaseModel):
    is_active: bool

class AddStudentToCourseRequest(BaseModel):
    course_id: str
    student_id: str

class AddStudentToCourseResponse(BaseModel):
    message: str

class GetCoursesActiveRequest(BaseModel):
    student_id: str

class GetCoursesActiveResponse(BaseModel):
    course_id: str
    course_name: str
    course_code: str