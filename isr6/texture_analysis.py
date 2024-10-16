import cv2
import numpy as np
import matplotlib.pyplot as plt
from skimage.feature import graycomatrix, graycoprops

# Specify your image path
image_path = 'a.jpg'
image = cv2.imread(image_path)

# Convert to RGB for plotting
image_rgb = cv2.cvtColor(image, cv2.COLOR_BGR2RGB)

def plot_color_histogram(image_rgb):
    colors = ('r', 'g', 'b')
    for i, color in enumerate(colors):
        hist = cv2.calcHist([image_rgb], [i], None, [256], [0, 256])
        plt.plot(hist, color=color)
    plt.xlim([0, 256])
    plt.title('Color Histogram (RGB)')
    plt.xlabel('Pixel Intensity')
    plt.ylabel('Frequency')
    plt.show()

def extract_texture_features(gray_image):
    glcm = graycomatrix(gray_image, distances=[1], angles=[0], levels=256, symmetric=True, normed=True)
    contrast = graycoprops(glcm, 'contrast')[0, 0]
    dissimilarity = graycoprops(glcm, 'dissimilarity')[0, 0]
    homogeneity = graycoprops(glcm, 'homogeneity')[0, 0]
    energy = graycoprops(glcm, 'energy')[0, 0]
    correlation = graycoprops(glcm, 'correlation')[0, 0]
    
    texture_features = {
        'Contrast': contrast,
        'Dissimilarity': dissimilarity,
        'Homogeneity': homogeneity,
        'Energy': energy,
        'Correlation': correlation
    }
    return texture_features

# Convert to grayscale
gray_image = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)

# Plot color histogram
plot_color_histogram(image_rgb)

# Extract texture features
texture_features = extract_texture_features(gray_image)

# Print texture features
print("Texture Features (GLCM):")
for feature, value in texture_features.items():
    print(f'{feature}: {value}')

# Show grayscale image for texture analysis
plt.imshow(gray_image, cmap='gray')
plt.title('Grayscale Image for Texture Analysis')
plt.axis('off')  # Hide axis
plt.show()
