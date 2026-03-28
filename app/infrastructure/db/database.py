from sqlalchemy.ext.asyncio import create_async_engine
from sqlalchemy.orm import declarative_base

from app.core.config import settings

engine = create_async_engine(settings.gamehub_datasource_url)

Base = declarative_base()

