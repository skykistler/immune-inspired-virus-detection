# install.packages('deepnet')
# install.packages('ngram')
library('deepnet')
library('ngram')
library('R.oo')
set.seed(1)

virus.list <- list.files(path='viruses_sterilized', full.names=TRUE)
virus.data <- list()
for (i in 1:length(virus.list)) {
  virus.data[[i]] <- iconv(enc2utf8(paste(rawToChar(readBin(virus.list[i], what=raw(), n=file.info(virus.list[i])$size), multiple=TRUE), collapse=' ')), sub="byte")
}
rm(i)

virus.ngrams <- list()
for (i in 1:length(virus.list)) {
  virus.ngrams[[i]] <- get.ngrams(ngram(virus.data[[i]], n=2))[1:10]
}
rm(i)

benign.list <- list.files(path='win_xp_benign_sterilized', full.names=TRUE)
benign.data <- list()
for (i in 1:length(benign.list)) {
  benign.data[[i]] <- iconv(enc2utf8(paste(rawToChar(readBin(benign.list[i], what=raw(), n=file.info(benign.list[i])$size), multiple=TRUE), collapse=' ')), sub="byte")
}
rm(i)

benign.ngrams <- list()
for (i in 1:length(benign.list)) {
  benign.ngrams[[i]] <- get.ngrams(ngram(benign.data[[i]], n=2))[1:10]
}
rm(i)

rm(virus.data)
rm(benign.data)
save.image("~/git/immune-inspired-virus-detection/Virus Detection/calculated_ngrams.RData")

data <- as.matrix(do.call(rbind, do.call(c, list(virus.ngrams, benign.ngrams))))
data <- apply(data, c(1,2), hashCode)
type <- as.matrix(do.call(c, list(rep(1, length(virus.ngrams)), rep(0, length(benign.ngrams)))))

data <- cbind(data, type)
data <- data[sample(nrow(data)),]

numTrain <- 2547
numTest <- 100

dnn <- dbn.dnn.train(data[1:numTrain,1:10], data[1:numTrain,11], hidden=c(5))
nn.test(dnn, data[(numTrain + 1):(numTrain+numTest),1:10], data[(numTrain + 1):(numTrain+numTest),11])
