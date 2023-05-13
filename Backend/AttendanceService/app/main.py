from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
import asyncio
from .routers import course_activation, student_management, kafka_router
import uvicorn


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

asyncio.create_task(kafka_router.consumer())