import uvicorn
import os
import py_eureka_client.eureka_client as eureka_client
import random

if __name__ == "__main__":
    rest_port = int(os.getenv("SERVER_PORT", 8000))

    eureka_client.init(zone="http://localhost:8761/eureka",
                       instance_host="localhost",
                       app_name="attendance-service",
                       instance_port=8000,
                       instance_id="attendance-service:{}".format(
                           random.randint(0, 1000)),
                       )
    print("Connected Eureka")
    uvicorn.run(app="app.main:app")