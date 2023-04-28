from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from . import router  
import asyncio
from .routers import face_recoginition


app = FastAPI()

app.include_router(face_recoginition.router)

origins = ["*"]

app.add_middleware(
    CORSMiddleware,
    allow_origins=origins,
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

@app.get("/")
async def root():
    return {"message": "Hello World"}

app.include_router(router.router)
asyncio.create_task(router.consumer())