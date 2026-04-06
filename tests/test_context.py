import pytest
from sqlalchemy import text

from tests.conftest import db_engine


@pytest.mark.asyncio
async def test_app_context_loads(db_engine):
    engine, async_session = db_engine

    async with async_session() as session:
        result = await session.execute(text("SELECT 1"))
        value = result.scalar()
        assert value == 1
