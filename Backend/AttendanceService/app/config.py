from pydantic import BaseSettings
from en

class Settings(BaseSettings):
    database_hostname: str
    database_port: int
    database_username: str
    database_password: str
    database_name: str

    class Config:
        env_file = ".env"

settings = Settings()