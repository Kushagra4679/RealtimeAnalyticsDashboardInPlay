# import json
# import random
# import time
#
# from faker import Faker
# from confluent_kafka import SerializingProducer
# from datetime import datetime
#
# fake = Faker(locale='en_IN')
#
#
# def generate_sales_transactions():
#
#     return {
#         'User_Name': fake.name(),
#         # 'Timestamp': fake.date_time_between(start_date='-30d', end_date='now').strftime('%Y-%m-%d %H:%M:%S'),
#         'User_ID': fake.random_number(digits=6, fix_len=5),
#         'Event_Type': random.choice(['click', 'purchase', 'login']),
#         'Location': fake.country(),
#         'Device_Type': random.choice(['desktop', 'mobile', 'tablet']),
#         'Product_ID': fake.random_number(digits=6),
#         'Revenue': random.uniform(10, 1000)
#     }
#
# def delivery_report(err, msg):
#     if err is not None:
#         print(f'Message delivery failed: {err}')
#     else:
#         print(f"Message delivered to {msg.topic} [{msg.partition()}]")
#
#
# def main():
#     topic = 'data-generation'
#     producer = SerializingProducer({
#         'bootstrap.servers': 'localhost:9092'
#     })
#
#     curr_time = datetime.now()
#
#     while (datetime.now() - curr_time).seconds < 180:
#         try:
#             transaction = generate_sales_transactions()
#             # Convert Product_ID to string before using it as the key
#             key = str(transaction['Product_ID'])
#             value = json.dumps(transaction).encode('utf-8')  # Encode the value as bytes
#             producer.produce(topic, key=key.encode('utf-8'), value=value, on_delivery=delivery_report)
#             producer.poll(0)
#
#             # wait for 5 seconds before sending the next transaction
#             time.sleep(5)
#         except BufferError:
#             print("Buffer full! Waiting...")
#             time.sleep(1)
#         except Exception as e:
#             print(e)
#
#
# if __name__ == "__main__":
#     main()

from elasticsearch import Elasticsearch
import json
import random
import time
from faker import Faker
from datetime import datetime

fake = Faker(locale='en_IN')
es = Elasticsearch(
    ['https://localhost:9200'],
    basic_auth=('elastic', '123456'),
    verify_certs=False  # Warning: Disabling certificate verification is insecure
)  # Initialize Elasticsearch client

def generate_sales_transactions():
    return {
        'User_Name': fake.name(),
        'User_ID': fake.random_number(digits=6, fix_len=5),
        'Event_Type': random.choice(['click', 'purchase', 'login']),
        'Location': fake.country(),
        'Device_Type': random.choice(['desktop', 'mobile', 'tablet']),
        'Product_ID': fake.random_number(digits=6),
        'Revenue': random.uniform(100, 10000)
    }

def main():
    topic = 'data-generation'

    curr_time = datetime.now()

    while (datetime.now() - curr_time).seconds < 20:
        try:
            transaction = generate_sales_transactions()
            es.index(index='user_events', body=transaction)  # Index the transaction data
            time.sleep(5)  # Wait for 5 seconds before sending the next transaction
        except Exception as e:
            print(e)

if __name__ == "__main__":
    main()
