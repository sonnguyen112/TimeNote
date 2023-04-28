from fastapi import APIRouter, status
from .. import schemas

router = APIRouter(
    prefix="/face_recoginition",
    tags=["face_recoginition"]
)

@router.post("/", status_code=status.HTTP_201_CREATED, response_model=schemas.AddFaceOutput)
def add_face(request:schemas.AddFaceInput):
    return {"Mes": "Add Face"}

@router.post("/verified_face", status_code=status.HTTP_202_ACCEPTED, response_model=schemas.VerifiedFaceOutput)
def verified_face(request:schemas.VerifiedFaceInput):
    return {"Mes": "Verified Face"}