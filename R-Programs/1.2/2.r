data(iris)

# Exploration
head(iris)
names(iris)
attributes(iris)
summary(iris)
mean(iris$Sepal.Length)
median(iris$Sepal.Width)
range(iris$Petal.Length)
quantile(iris$Sepal.Length)
var(iris$Sepal.Length)
table(iris$Species)

# Covarience and Correlation
cov(iris$Sepal.Length, iris$Petal.Length)
cor(iris$Sepal.Length, iris$Petal.Length)

# Visualization
hist(iris$Sepal.Length)
plot(density(iris$Sepal.Length))
pie(table(iris$Species))
barplot(table(iris$Species))
plot(iris$Petal.Length)
