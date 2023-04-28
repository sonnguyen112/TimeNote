from pydantic import BaseModel

class AddFaceInput(BaseModel):
    id_course: str
    face_encode: str

class AddFaceOutput(BaseModel):
    id_course: str
    face_encode: str