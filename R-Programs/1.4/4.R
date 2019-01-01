# Install packages
install.packages("party")
install.packages("e1071")
install.packages("caret")

#load libraries

library(party)  #Contains the decision tree functions
library(caret)  #Contains confusion matrix functions
library(e1071)  #Contains the naive bayes functions

#data <- read.csv(file = "/Users/vishalprabhachandar/Documents/Programming/DataminingLab/R-Programs/1.4/input.csv")
#View(data)

#We need a large dataset for modeling the decision tree so inform the teacher and use a built in dataset
data <- readingSkills
#Split dataset into test and train
index <- sample(2, nrow(data), replace=TRUE, prob=c(0.7,0.3))
train <- data[index==1,]
test <- data[index==2,]

#Select the dependent and independent features
features <- nativeSpeaker ~ age + shoeSize + score


#DECISION TREE

#Obtain a decision tree model
model <- ctree(features, data=train)

#Plot the model
plot(model)

#Evaluate model on test data
test_predictions <- predict(model, newdata=test)
confusionMatrix(test_predictions, test$nativeSpeaker, positive="yes")

#NAIVE BAYES CLASSIFIER

#Obtain a decision tree model
model2 <- naiveBayes(features, data=train)

#Model Summary
print(model2)

#Evaluate model on test data
test_predictions2 <- predict(model2, newdata = test)
confusionMatrix(test_predictions2, test$nativeSpeaker, positive="yes")

