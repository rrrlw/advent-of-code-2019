# read input
modules <- as.integer(readLines("day1.in"))

# solve part 1
answer <- sum(as.integer(modules / 3) - 2)
cat(paste("Part 1:", answer))

# solve part 2
answer <- sum(vapply(X = modules,
					 FUN.VALUE = integer(1),
					 FUN = function(f) {
					   counter <- 0
						 f <- as.integer(f / 3) - 2
					   while (f > 0) {
							 counter <- counter + f
							 f <- as.integer(f / 3) - 2
					   }
						 as.integer(counter)
					 }))
cat(paste("Part 2:", answer))
