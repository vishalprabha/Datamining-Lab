# Get and print current working directory.
print(getwd())

# Get and print current working directory.
print(getwd())

# Set current working directory.
setwd("/Users/vishalprabhachandar/Documents/Programming/DataminingLab/R-Programs/1.1")

# Read the csv file.
data <- read.csv(file = "input.csv")

# Display contents.
data

# Get the person detail having max salary.
retval <- subset(data, salary == max(salary))
print(retval)

# Write data to file.
write.csv(retval, "output.csv")
