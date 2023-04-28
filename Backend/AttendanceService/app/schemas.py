from pydantic import BaseModel

class VerifiedFaceInput(BaseModel):
    id_course: str
    face_image: str

class VerifiedFaceOutput(BaseModel):
    is_verified: bool