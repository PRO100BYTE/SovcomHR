from kafka import KafkaConsumer
from telegram import Bot

consumer = KafkaConsumer(
    bootstrap_servers='your_kafka_server:9092',
    group_id='your_consumer_group',
    value_deserializer=lambda x: x.decode('utf-8')
)
consumer.subscribe(['your_topic'])

telegram_token = 'your_telegram_bot_token'
bot = Bot(token=telegram_token)

for message in consumer:
    notification = message.value
    chat_id = 'user_chat_id'
    bot.send_message(chat_id=chat_id, text=notification)
