import logging
from contextlib import asynccontextmanager

from fastapi import FastAPI

from app.adapters.persistence.database import Base, engine
from app.core.logger import setup_logging

setup_logging()

logger = logging.getLogger(__name__)


@asynccontextmanager
async def lifespan(app: FastAPI):
    logger.info("Starting GameHub API...")
    async with engine.begin() as conn:
        await conn.run_sync(Base.metadata.create_all)
    logger.info("Connection established to the database.")
    yield
    logger.info("Closing connection to the database...")
    await engine.dispose()


app = FastAPI(lifespan=lifespan)
