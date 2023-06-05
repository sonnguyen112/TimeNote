import pickle
import numpy as np
a = np.array([[1,2],[3, 4]])
a_hex = pickle.dumps(a).hex()
# print(a_hex)
a_decode = pickle.loads(bytes.fromhex(a_hex))
print(a_decode)