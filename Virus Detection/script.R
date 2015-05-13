# install.packages('deepnet')
library('deepnet')

virus.list <- list.files(path='viruses_sterilized', full.names=TRUE)
virus.data <- lapply(virus.list, readBin, what=raw())

benign.list <- list.files(path='win_xp_benign_sterilized', full.names=TRUE)
benign.data <- lapply(benign.list, readBin, what=raw())

v <- cbind(virus.data, 1)
b <- cbind(benign.data, 0)
data <- rbind(v, b)

dnn <- dbn.dnn.train(data[1,], data[2,])