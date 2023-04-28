import asyncio
import os 
from dotenv import load_dotenv
load_dotenv()

KAFKA_TOPIC = "chat"
KAFKA_CONSUMER_GROUP = os.getenv('KAFKA_CONSUMER_GROUP', 'group')
KAFKA_BOOTSTRAP_SERVERS = os.getenv('KAFKA_BOOTSTRAP_SERVERS', 'localhost:9092')
loop = asyncio.get_event_loop()
from pydantic import BaseSettings
class Settings(BaseSettings):
    database_hostname: str
    database_port: int
    database_username: str
    database_password: str
    database_name: str

    class Config:
        env_file = ".env"

settings = Settings()
