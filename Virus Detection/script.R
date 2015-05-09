# install.packages('deepnet')
# install.packages('data.table')
library('deepnet')
library('data.table')

virus.list <- list.files(path='viruses_sterilized', full.names=TRUE)

virus.data <- lapply(virus.list, readBin, what=raw())
