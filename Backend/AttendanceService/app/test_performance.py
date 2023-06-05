import face_recognition
import time
import pickle
import base64
known_image_0 = face_recognition.load_image_file(r"image_source/20125000.jpg")
known_image_1 = face_recognition.load_image_file(r"image_source/20125112.jpg")
known_image_2 = face_recognition.load_image_file(r"image_source/20125002.jpg")
known_image_3 = face_recognition.load_image_file(r"image_source/taylor.jpg")
known_image_4 = face_recognition.load_image_file(r"image_source/warrent.jpg")
unknown_image = face_recognition.load_image_file(r"image_source/20125112.jpg")
location_0 = face_recognition.face_locations(known_image_0)
location_1 = face_recognition.face_locations(known_image_1)
location_2 = face_recognition.face_locations(known_image_2)
location_3 = face_recognition.face_locations(known_image_3)
location_4 = face_recognition.face_locations(known_image_4)
location_unk = face_recognition.face_locations(unknown_image)
image_encoding_0 = face_recognition.face_encodings(known_image_0, location_0)[0]
image_encoding_1 = face_recognition.face_encodings(known_image_1, location_1)[0]
image_encoding_2 = face_recognition.face_encodings(known_image_2, location_2)[0]
image_encoding_3 = face_recognition.face_encodings(known_image_3, location_3)[0]
image_encoding_4 = face_recognition.face_encodings(known_image_4, location_4)[0]
unknown_encoding = face_recognition.face_encodings(unknown_image, location_unk)[0]
start =time.perf_counter()
results = face_recognition.compare_faces([image_encoding_0, image_encoding_1, image_encoding_2, image_encoding_3, image_encoding_4], unknown_encoding)
end = time.perf_counter()
print(end - start)
print(results)
# a = pickle.dumps(image_encoding_0)
# print(len(str(a)))
# print(str(a))