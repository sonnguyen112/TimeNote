import base64
import PIL.Image as Image
import io
import json
from config import loop, KAFKA_BOOTSTRAP_SERVERS,KAFKA_CONSUMER_GROUP,KAFKA_TOPIC
from aiokafka import AIOKafkaConsumer
from fastapi import APIRouter
router = APIRouter()

async def consumer(): 
    consumer = AIOKafkaConsumer(KAFKA_TOPIC, loop= loop, bootstrap_servers= KAFKA_BOOTSTRAP_SERVERS, group_id= KAFKA_CONSUMER_GROUP )
    await consumer.start()
    try:
        async for msg in consumer:
          
            print(f"Consumed msg: {msg}")
         
    finally:
        await consumer.stop()
# loop.run_until_complete(consumer())