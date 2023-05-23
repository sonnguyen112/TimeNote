import geopy.distance

coords_1 = (12.695607, 108.043388)
coords_2 = (12.679995, 108.043880)

print(geopy.distance.geodesic(coords_1, coords_2).meters)