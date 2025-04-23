from typing import Annotated
from sqlmodel import Session, SQLModel, create_engine
from fastapi import Depends



postgres_url = "postgresql://postgres:postgres@localhost:5432/poo_postegres" 

engine = create_engine(postgres_url)

def create_db_and_tables():
    SQLModel.metadata.create_all(engine)

def get_session():
    with Session(engine) as session:
        yield session

session_depends = Annotated[Session, Depends(get_session)]
