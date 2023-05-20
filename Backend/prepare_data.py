import requests

NUM_LECTURER = 10
NUM_COURSE_INFO=10
STUDENT_LIST = [
    {
        "Name": "Hà Thiên Lộc",
        "Code": "20125002"
    },
    {
        "Name": "Hồ Trọng Bảo",
        "Code": "20125000"
    },
    {
        "Name": "Nguyễn Hồ Trường Sơn",
        "Code": "20125112"
    },
]

# Add data lecturer
for i in range(NUM_LECTURER):
    try:
        res = requests.post("http://localhost:8201/course_api/lecturer",json={
            "lecturerName" : f"Nguyễn Văn {chr(65 + i)}",
            "lecturerCode" : f"0000{i}"
        })
        print(f"{res.json()}")
    except:
        pass


# Add data course info
for i in range(NUM_LECTURER):
    try:
        res = requests.post("http://localhost:8201/course_api/course",json={
            "courseName" : f"Name of Course {i}",
            "courseCode" : f"CS00{i}"
        })
        print(f"{res.json()}")
    except:
        pass

# Add data student info
for stu in STUDENT_LIST:
    try:
        res = requests.post("http://localhost:8201/course_api/student", files={
            "image": open(f"""sample_img_student//{stu["Code"]}.jpg""", "rb")
        }, data={
            "body": f"""{{"studentName": "{stu["Name"]}", "studentCode":"{stu["Code"]}"}}"""
        })
        print(f"{res.json()}")
    except:
        pass

# Add Student to course
res = requests.post("http://localhost:8201/course_api/course_detail",json={
    "courseCode" : "CS000",
    "classCode" : "20CTT",
    "timeStarts" : [
        "2 7:30",
        "3 7:30"
    ],
    "studentCodes":[
        "20125112",
        "20125000",
        "20125002"
    ],
    "lectureCodes":[
        "00000",
        "00003"
    ]
})
print(f"{res.json()}")
