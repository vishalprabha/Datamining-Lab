#Using the orange dataset which has age and circumference of trees
View(Orange)

#IF asked to read from file and make own dataset
#write.csv(Orange, "./data.csv", row.names = FALSE)
#data1 <- read.csv("./data.csv")

data <- Orange

age <- data[1:7,2:2]
circum <- data[1:7,3:3]

plot(age, circum, xlab = "age", ylab = "circumference")

data <- data.frame(age, circum)
model = lm(circum ~ age, data)
summary(model)

abline(model, cex=1)

new_data <- data.frame(age = 700)
result <- predict(model, new_data)
print(result)
