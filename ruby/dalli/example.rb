require 'dalli'


cache = Dalli::Client.new(ENV["MEMCACHIER_SERVERS"].split(","),
                    {:username => ENV["MEMCACHIER_USERNAME"],
                     :password => ENV["MEMCACHIER_PASSWORD"],
                     :failover => true,
                     :socket_timeout => 1.5,
                     :socket_failure_delay => 0.2,
                     :down_retry_delay => 60
                    })

cache.set("foo", "bar")
puts cache.get("foo")
