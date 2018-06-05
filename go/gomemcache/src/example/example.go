package main

import (
	"fmt"
	"os"
	"strings"
	"github.com/memcachier/gomemcache/memcache"
)

func main() {

	username := os.Getenv("MEMCACHIER_USERNAME")
	password := os.Getenv("MEMCACHIER_PASSWORD")
	servers := os.Getenv("MEMCACHIER_SERVERS")

	mc := memcache.New(strings.Split(servers, ",")...)
	mc.SetAuth(username, []byte(password))

	err := mc.Set(&memcache.Item{Key: "foo", Value: []byte("my value")})
	if err != nil {
		fmt.Printf("Failed to set value: %s\n", err)
		return
	}

	val, err := mc.Get("foo")
	if err != nil {
		fmt.Printf("Failed to fetch value: %s\n", err)
		return
	}
	fmt.Printf("Got value: %s\n", val)
}
