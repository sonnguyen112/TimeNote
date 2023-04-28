from fastapi import APIRouter, status
from .. import schemas

router = APIRouter(
    prefix="/face_recoginition",
    tags=["face_recoginition"]
)

@router.post("/", status_code=status.HTTP_201_CREATED, response_model=schemas.AddFaceOutput)
def add_face(face_encode:schemas.AddFaceInput):
    return