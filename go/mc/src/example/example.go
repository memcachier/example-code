package main

import (
	"fmt"
	"os"
	"time"
	"github.com/memcachier/mc"
)

func main() {

	username := os.Getenv("MEMCACHIER_USERNAME")
	password := os.Getenv("MEMCACHIER_PASSWORD")
	server := os.Getenv("MEMCACHIER_SERVERS")

	// Launch a client with default configuration
	// c := mc.NewMC(server, username, password)

	// Launch a client with custom configuration
	config := mc.DefaultConfig()
	config.Hasher = mc.NewModuloHasher()         // default
	config.Retries = 2                           // default
	config.RetryDelay = 200 * time.Millisecond   // default
	config.Failover = true                       // default
	config.ConnectionTimeout = 2 * time.Second   // default
	config.DownRetryDelay = 60 * time.Second     // default
	config.PoolSize = 1                          // default
	config.TcpKeepAlive = true                   // default
	config.TcpKeepAlivePeriod = 60 * time.Second // default
	config.TcpNoDelay = true                     // default
	c := mc.NewMCwithConfig(server, username, password, config)

	defer c.Quit()

	_, err := c.Set("foo", "bar", 0, 0, 0)
	if err != nil {
		fmt.Printf("Failed to set value: %s\n", err)
		return
	}

	val, _, _, err := c.Get("foo")
	if err != nil {
		fmt.Printf("Failed to fetch value: %s\n", err)
		return
	}
	fmt.Printf("Got value: %s\n", val)
}
