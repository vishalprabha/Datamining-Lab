#load libraries
library(datasets)
library(cluster)
library(factoextra) #install.packages("factoextra")

#Using Iris dataset
data <- iris
#Making species column NULL
data$Species<-NULL

#KMeans
d<- scale(dist(data,method = "euclidian"))
kfit <- kmeans(d,3)
fviz_cluster(kfit, data, geom = "point")
