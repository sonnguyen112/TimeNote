from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
import router   
import asyncio

app = FastAPI()

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