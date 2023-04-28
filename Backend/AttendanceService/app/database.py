import mysql.connector
import time
from .config import settings

while 1:
    try:
        mydb = mysql.connector.connect(
            host="localhost",
            user=settings.database_username,
            password=settings.database_password,
            database=settings.database_name
        )
        cursor = mydb.cursor()
        print("Connected to MySQL")
        break
    except Exception as e:
        print("Error when connect database: ", e)
        time.sleep(2)
