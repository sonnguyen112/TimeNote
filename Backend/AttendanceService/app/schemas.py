from pydantic import BaseModel

class AddFaceInput(BaseModel):
    id_course: str
    face_image: str

class AddFaceOutput(BaseModel):
    message: str

class VerifiedFaceInput(BaseModel):
    id_course: str
    face_image: str

class VerifiedFaceOutput(BaseModel):
    is_verified: bool