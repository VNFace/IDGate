import numpy
import cv2

 # Load an color image in grayscale
img = cv2.imread('C:/Users/Dell/Desktop/IDGate/Docker/test3.jpg',0)

 # show image

cv2.imshow('image',img)
cv2.waitKey(0)
cv2.destroyAllWindows()
