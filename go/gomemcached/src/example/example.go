package main

import (
	"fmt"
	"os"
	"github.com/dustin/gomemcached/client"
)

func main() {


	username := os.Getenv("MEMCACHIER_USERNAME")
	password := os.Getenv("MEMCACHIER_PASSWORD")
	server := os.Getenv("MEMCACHIER_SERVERS") // can only be one

	c, err := memcached.Connect("tcp", server)
	if err != nil {
		fmt.Printf("Error connecting: %v\n", err)
		return
	}
	defer c.Close()

	// TODO Fails with PLAIN not supported even if PLAIN is supported
	resp, err := c.Auth(username, password)
	if err != nil {
		fmt.Printf("Auth error: %v\n", err)
		return
	}
	fmt.Printf("Auth response: %v\n", resp)

	// Set(vb uint16, key string, flags int, exp int, body []byte) (*gomemcached.MCResponse, error)
	_, err = c.Set(0, "foo", 0, 0, []byte("bar"))
	if err != nil {
		fmt.Printf("Failed to set value: %s\n", err)
		return
	}

	// Get(vb uint16, key string) (*gomemcached.MCResponse, error)
	val, err := c.Get(0, "foo")
	if err != nil {
		fmt.Printf("Failed to fetch value: %s\n", err)
		return
	}
	fmt.Printf("Got value: %v\n", val.Body)
}
