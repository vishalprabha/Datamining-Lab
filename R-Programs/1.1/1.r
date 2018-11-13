# Get and print current working directory. (optional)
#pwd <- (getwd())

# Get and print current working directory. (optional)
#print(pwd)

# Set current working directory. (optional)
#setwd("/Users/vishalprabhachandar/Documents/Programming/DataminingLab/R-Programs/1.1")

# Read the csv file.
data <- read.csv(file = "input.csv")

# Display contents.
data

# Get the person detail having max salary. (optional)
#retval <- subset(data, salary == max(salary))
#print(retval)
#write.csv(retval, "output.csv")


# Write data to file.
write.csv(data, "output.csv")
