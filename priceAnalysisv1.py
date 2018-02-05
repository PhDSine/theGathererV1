# Multiple Linear Regression

# Importing the libraries
import numpy as np
import matplotlib.pyplot as plt
import pandas as pd

# Importing the dataset
csv = 'Master12718.csv'
dataset = pd.read_csv(csv, encoding='latin-1')

# Contain everything but the name and prices
X = dataset.iloc[:, 1:26].values
df = pd.DataFrame(X)
y1 = dataset.iloc[:, 27].values
y5 = dataset.iloc[:, 28].values
y10 = dataset.iloc[:, 29].values

# Fitting random forest regression to dataset at different price points
from sklearn.ensemble import RandomForestRegressor

# Create a regressor for each price point
regressor1 = RandomForestRegressor(n_estimators = 100, random_state = 0)
regressor5 = RandomForestRegressor(n_estimators = 100, random_state = 0)
regressor10 = RandomForestRegressor(n_estimators = 100, random_state = 0)

# For each price point, fit a regressor
regressor1.fit(X, y1)
regressor5.fit(X, y5)
regressor10.fit(X, y10)

# Predicting a new result
dataset_RIX = pd.read_csv('RIX.csv', encoding='latin-1')
X_RIX = dataset_RIX.iloc[:, 1:26].values
RIX_names = dataset_RIX.iloc[:, 0:1].values

# Give us a viewable variable
RIX_names_view = pd.DataFrame(RIX_names)

# Create a prediction for each price point
y_pred_1dollar = regressor1.predict(X_RIX)
y_pred_5dollar = regressor5.predict(X_RIX)
y_pred_10dollar = regressor10.predict(X_RIX)

# Printing out the results
print("Potential to be over $10")
index = -1
for num in y_pred_10dollar:
    index+=1
    #This number can be changed to make results more or less strict
    if(num > .25): 
        print(RIX_names[index])

print()
print("Likely to be over $5")
index = -1
for num in y_pred_5dollar:
    index+=1
    #This number can be changed to make results more or less strict
    if(num >= .5):
        print(RIX_names[index])

print()
print("Likely to be over $1")
index = -1
for num in y_pred_1dollar:
    index+=1
    #This number can be changed to make results more or less strict
    if(num >= .5):
        print(RIX_names[index])