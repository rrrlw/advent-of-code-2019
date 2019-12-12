# intcode program for day 2
intcode <- function(ops_list) {
	ops <- ops_list
	i <- 1
	while (i < length(ops)) {
		# exit
		if (ops[i] == 99) {
			break
		# add next 2, store in 3rd
		} else if (ops[i] == 1) {
			ops[ops[i + 3] + 1] <- ops[ops[i + 1] + 1] + ops[ops[i + 2] + 1]
		# multiply next 2, store in 3rd
		} else if (ops[i] == 2) {
			ops[ops[i + 3] + 1] <- ops[ops[i + 1] + 1] * ops[ops[i + 2] + 1]
		}
		
		i <- i + 4
	}
	return(ops)
}

#####PART 1#####
# input
ops_input <- as.integer(strsplit(readLines("day2.in"), ",")[[1]])

# calculate
ops_input[2] <- 12
ops_input[3] <- 2
answer <- intcode(ops_input)

# output
cat("Part 1:", answer[1], "\n")

#####PART 2#####
cat("Part 2: ")

# try all combos
for (i in 0:99) {
	for (j in 0:99) {
		ops_input[2] <- i
		ops_input[3] <- j
		
		curr_ans <- intcode(ops_input)
		
		# found answer, but keep going in case not unique pair
		if (curr_ans[1] == 19690720) {
			cat(i * 100 + j, " ")
		}
	}
}
cat("\n")

