from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
import asyncio
from .routers import course_activation, student_management, kafka_router
import uvicorn
from .config import settings
from . import models
from .database import engine
from dotenv import load_dotenv
import os
import py_eureka_client.eureka_client as eureka_client
import threading

load_dotenv()

def register_eureka():
    rest_port = int(os.getenv("SERVER_PORT", 8000))


    eureka_client.init(eureka_server=f"http://{settings.discovery_host}:8761/eureka",
                   app_name="attendance-service",
                   instance_port=rest_port)

# Drop all tables
models.Base.metadata.drop_all(engine)

# Create all tables
models.Base.metadata.create_all(engine)

app = FastAPI()

app.include_router(course_activation.router)
app.include_router(student_management.router)
app.include_router(kafka_router.router)

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

register_thread = threading.Thread(target=register_eureka)
register_thread.start()
register_thread.join()
asyncio.create_task(kafka_router.consumer())

