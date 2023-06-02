FROM python:3.9

# 
WORKDIR /AttendanceService

# 
COPY ./requirements.txt /AttendanceService/requirements.txt

# 
RUN pip install -U pip wheel cmake
RUN pip install --no-cache-dir --upgrade -r /AttendanceService/requirements.txt

# 
COPY ./app /AttendanceService/app

# 
CMD ["uvicorn", "app.main:app"]