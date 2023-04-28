from fastapi import APIRouter, status
from .. import schemas

router = APIRouter(
    prefix="/face_recoginition",
    tags=["face_recoginition"]
)

@router.post("/verified_face", status_code=status.HTTP_202_ACCEPTED, response_model=schemas.VerifiedFaceOutput)
def verified_face(request:schemas.VerifiedFaceInput):
    return {"Mes": "Verified Face"}