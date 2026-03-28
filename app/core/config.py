from pydantic_settings import BaseSettings, SettingsConfigDict


class Settings(BaseSettings):

    model_config = SettingsConfigDict(
        env_file=".env",
        env_file_encoding="utf-8"
    )

    gamehub_datasource_username: str = None
    gamehub_datasource_password: str = None
    gamehub_datasource_database: str = None
    gamehub_datasource_host: str = "localhost"
    gamehub_datasource_port: int = 5432

    @property
    def gamehub_datasource_url(self) -> str:
        return (f"postgresql+asyncpg://{self.gamehub_datasource_username}:"
                f"{self.gamehub_datasource_password}@{self.gamehub_datasource_host}:"
                f"{self.gamehub_datasource_port}/{self.gamehub_datasource_database}")

settings = Settings()