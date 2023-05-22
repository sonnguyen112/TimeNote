from .database import Base
from sqlalchemy import TIMESTAMP, Column, Integer, String, ForeignKey,Boolean, BigInteger, BINARY

class FaceEncoding(Base):
    __tablename__ = "faceencoding"
    id = Column(BigInteger, primary_key=True, nullable=False)
    student_id = Column(String(20), nullable=False, unique=True)
    face_encoding = Column(String(10000), nullable=False)

class CourseActivation(Base):
    __tablename__ = "courseactivation"
    id = Column(BigInteger, primary_key=True, nullable=False)
    course_id = Column(String(20), nullable=False, unique=True)
    is_active = Column(Boolean, nullable=False)
    coord = Column(String(20), nullable=False)

class StudentManagement(Base):
    __tablename__ = "studentmanagement"
    id = Column(BigInteger, primary_key=True, nullable=False)
    student_code = Column(String(20), nullable=False)
    course_id = Column(String(20), nullable=False)