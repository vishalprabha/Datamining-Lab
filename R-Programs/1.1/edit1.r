data <- read.csv(file = "/Users/vishalprabhachandar/Documents/Programming/DataminingLab/R-Programs/1.1/inp.csv")
data
data$ID <- ifelse(is.na(data$ID),mean(data$ID,na.rm=TRUE),data$ID)
data
write.csv(data,file="output.csv")
