
U=<your username>
P=<your password>
S=<your server address>

all: src/example/*.go
	@GOPATH=$(CURDIR) go build example

deps:
	@GOPATH=$(CURDIR) go get github.com/memcachier/gomemcache/memcache

run:
	MEMCACHIER_USERNAME=$(U) MEMCACHIER_PASSWORD=$(P) MEMCACHIER_SERVERS=$(S) ./example
