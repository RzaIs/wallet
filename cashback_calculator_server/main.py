from fastapi import FastAPI
from dataclasses import dataclass

@dataclass
class Cashback:
    cashbackAmount: float

app = FastAPI()

@app.get('/cashback')
async def calculate_cashback(transactionAmount: float) -> Cashback:
    return Cashback(transactionAmount * 0.1)
