

#load libraries
library("MASS") #Contains the breast cancer dataset
library(party)  #Contains the decision tree functions
library(caret)  #Contains confusion matrix functions
library(e1071)  #Contains the naive bayes functions


#Load the dataset
data <- biopsy
View(data)


#Split dataset into test and train
index <- sample(2, nrow(data), replace=TRUE, prob=c(0.7,0.3))
train <- data[index==1,]
test <- data[index==2,]

#Select the dependent and independent features
features <- class ~ V1+V2+V3+V4+V5+V6+V7+V8+V9


#NAIVE BAYES CLASSIFIER

#Obtain a decision tree model
model2 <- naiveBayes(features, data=train)

#Model Summary
print(model2)

#Evaluate model on test data
test_predictions2 <- predict(model2, newdata = test)
confusionMatrix(test_predictions2, test$class, positive="malignant")

