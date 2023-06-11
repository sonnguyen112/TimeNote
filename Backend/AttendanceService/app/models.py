from .database import Base
from sqlalchemy import TIMESTAMP, Column, Integer, String, ForeignKey,Boolean, BigInteger, BINARY, DATETIME
from datetime import datetime

class FaceEncoding(Base):
    __tablename__ = "faceencoding"
    id = Column(BigInteger, primary_key=True, nullable=False)
    student_id = Column(String(20), nullable=False, unique=True)
    face_encoding = Column(String(10000), nullable=False)

class CourseActivation(Base):
    __tablename__ = "courseactivation"
    id = Column(BigInteger, primary_key=True, nullable=False)
    course_id = Column(String(20), nullable=False, unique=True)
    coord = Column(String(200), nullable=False)

class StudentManagement(Base):
    __tablename__ = "studentmanagement"
    id = Column(BigInteger, primary_key=True, nullable=False)
    student_code = Column(String(20), nullable=False)
    course_id = Column(String(20), nullable=False)

class StudentStatus(Base):
    __tablename__ = "studentStatus"
    id = Column(BigInteger, primary_key=True, nullable=False)
    student_code = Column(String(20), nullable=False)
    course_id = Column(String(20), nullable=False)

class HistoryTable(Base):
     __tablename__ = "HistoryTable"
     id = Column(BigInteger, primary_key=True, nullable=False)
     course_id = Column(String(20), nullable=False)
     date_time = Column(String(100), nullable=False)
     student_code = Column(String(20), nullable=False)