require 'memcached'

cache = Memcached.new(ENV["MEMCACHIER_SERVERS"].split(","),
          binary_protocol: true,
          credentials: [ENV['MEMCACHIER_USERNAME'], ENV['MEMCACHIER_PASSWORD']]
          )

# This fails with Memcached:WriteFailure TODO: investigate
cache.set("foo", "bar")
puts cache.get("foo")
