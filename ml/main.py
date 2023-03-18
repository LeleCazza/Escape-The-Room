from tflite_model_maker import image_classifier
from tflite_model_maker.image_classifier import DataLoader

if __name__ == '__main__':
    # Load input data specific to an on-device ML app.
    data = DataLoader.from_folder('C:/Users/leyel/Documents/Universita/TESI/escape_room/ml/dataset/flower_photos/')
    train_data, test_data = data.split(0.9)

    # Customize the TensorFlow model.
    model = image_classifier.create(train_data)

    # Evaluate the model.
    loss, accuracy = model.evaluate(test_data)

    # Export to Tensorflow Lite model and label file in `export_dir`.
    model.export(export_dir='/C:/Users/leyel/Desktop')


'''
import tensorflow as tf
import tensorflow_datasets as tfds
import os
import numpy as np
from PIL import Image
import pathlib
#from tflite_model_maker import image_classifier
#from tflite_model_maker.image_classifier import DataLoader
import pandas as pd

if __name__ == '__main__':
    data_dir = "C:/Users/leyel/Documents/Universita/TESI/escape_room/ml/dataset/flower_photos"
    batch_size = 32
    img_height = 180
    img_width = 180
    train_ds = tf.keras.utils.image_dataset_from_directory(
        data_dir,
        validation_split=0.2,
        subset="training",
        seed=123,
        image_size=(img_height, img_width),
        batch_size=batch_size)
    val_ds = tf.keras.utils.image_dataset_from_directory(
        data_dir,
        validation_split=0.2,
        subset="validation",
        seed=123,
        image_size=(img_height, img_width),
        batch_size=batch_size)
    class_names = train_ds.class_names
    print(class_names)

    # faccio cose
    normalization_layer = tf.keras.layers.Rescaling(1. / 255)
    normalized_ds = train_ds.map(lambda x, y: (normalization_layer(x), y))
    image_batch, labels_batch = next(iter(normalized_ds))
    first_image = image_batch[0]
    print(np.min(first_image), np.max(first_image))
    AUTOTUNE = tf.data.AUTOTUNE
    train_ds = train_ds.cache().prefetch(buffer_size=AUTOTUNE)
    val_ds = val_ds.cache().prefetch(buffer_size=AUTOTUNE)
    num_classes = 6
    model = tf.keras.Sequential([
        tf.keras.layers.Rescaling(1. / 255),
        tf.keras.layers.Conv2D(32, 3, activation='relu'),
        tf.keras.layers.MaxPooling2D(),
        tf.keras.layers.Conv2D(32, 3, activation='relu'),
        tf.keras.layers.MaxPooling2D(),
        tf.keras.layers.Conv2D(32, 3, activation='relu'),
        tf.keras.layers.MaxPooling2D(),
        tf.keras.layers.Flatten(),
        tf.keras.layers.Dense(128, activation='relu'),
        tf.keras.layers.Dense(num_classes)
    ])
    model.compile(
        optimizer='adam',
        loss=tf.losses.SparseCategoricalCrossentropy(from_logits=True),
        metrics=['accuracy'])
    model.fit(
        train_ds,
        validation_data=val_ds,
        epochs=1)
'''