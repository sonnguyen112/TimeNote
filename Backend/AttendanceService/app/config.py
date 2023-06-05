from pydantic import BaseSettings
import asyncio 
import os 
from dotenv import load_dotenv
load_dotenv()

KAFKA_TOPIC = os.getenv('KAFKA_TOPIC_NAME', "default")
KAFKA_CONSUMER_GROUP = os.getenv('KAFKA_CONSUME_GROUP', 'group')
KAFKA_BOOTSTRAP_SERVERS = os.getenv('KAFKA_BOOTSTRAP_SERVERS', 'localhost:9092')
# print(KAFKA_TOPIC, KAFKA_CONSUMER_GROUP, KAFKA_BOOTSTRAP_SERVERS)
loop = asyncio.get_event_loop()
class Settings(BaseSettings):
    database_hostname: str
    database_port: int
    database_username: str
    database_password: str
    database_name: str
    discovery_host: str

    class Config:
        env_file = ".env"

settings = Settings()
