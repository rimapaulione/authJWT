FROM ubuntu:latest
LABEL authors="ignaspaulionis"

ENTRYPOINT ["top", "-b"]