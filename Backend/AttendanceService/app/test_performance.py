import face_recognition
import time
import pickle
import base64
known_image_0 = face_recognition.load_image_file(r"image_source\naruto.png")
known_image_1 = face_recognition.load_image_file(r"image_source\musk.jpg")
known_image_2 = face_recognition.load_image_file(r"image_source\obama.jpg")
known_image_3 = face_recognition.load_image_file(r"image_source\taylor.jpg")
known_image_4 = face_recognition.load_image_file(r"image_source\warrent.jpg")
unknown_image = face_recognition.load_image_file(r"image_source\trump.jpg")
image_encoding_0 = face_recognition.face_encodings(known_image_0)

image_encoding_1 = face_recognition.face_encodings(known_image_1)[0]
print(image_encoding_0)
# image_encoding_2 = face_recognition.face_encodings(known_image_2)[0]
# image_encoding_3 = face_recognition.face_encodings(known_image_3)[0]
# image_encoding_4 = face_recognition.face_encodings(known_image_4)[0]
# unknown_encoding = face_recognition.face_encodings(unknown_image)[0]
# start =time.perf_counter()
# results = face_recognition.compare_faces([image_encoding_0, image_encoding_1, image_encoding_2, image_encoding_3, image_encoding_4], unknown_encoding)
# end = time.perf_counter()
# print(end - start)
# print(results)
# a = pickle.dumps(image_encoding_0)
# print(len(str(a)))
# print(str(a))