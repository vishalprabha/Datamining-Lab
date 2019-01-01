#install.packages("factoextra")

#load libraries
library(cluster)
library(factoextra)


#Input dataset
data <- read.csv("/Users/vishalprabhachandar/Documents/Programming/DataminingLab/R-Programs/1.6/input.csv")

#Making species column NULL
data$Species<-NULL


d<- scale(dist(data,method = "euclidian"))
#KMeans
kfit <- kmeans(d,3)
#HClustering
hfit <- hkmeans(d, 3)
fviz_cluster(kfit, data)
fviz_cluster(hfit, data)
