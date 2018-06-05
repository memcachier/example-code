package main

import (
	"fmt"
	"os"
	"github.com/memcachier/mc"
)

func main() {


	username := os.Getenv("MEMCACHIER_USERNAME")
	password := os.Getenv("MEMCACHIER_PASSWORD")
	server := os.Getenv("MEMCACHIER_SERVERS")

	// Launch a client with custom configuration
	// config := mc.DefaultConfig()
	// c := mc.NewMCwithConfig(server, username, password, config{/*modify values*/})

	c := mc.NewMC(server, username, password)
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
