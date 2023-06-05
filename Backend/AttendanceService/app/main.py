from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
import asyncio
from .routers import course_activation, student_management, kafka_router, face_recognize
import uvicorn
from .config import settings
from . import models
from .database import engine
from dotenv import load_dotenv
import os
import py_eureka_client.eureka_client as eureka_client
import multiprocessing
import random

load_dotenv()

# # Drop all tables
# models.Base.metadata.drop_all(engine)


# Create all tables
models.Base.metadata.create_all(engine)

app = FastAPI()

app.include_router(course_activation.router)
app.include_router(student_management.router)
app.include_router(kafka_router.router)
app.include_router(face_recognize.router)

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

# register_process = multiprocessing.Process(target=register_eureka)
# register_process.start()
# register_process.join()
asyncio.create_task(kafka_router.consumer())
