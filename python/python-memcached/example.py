#! /bin/python3

import memcache
import os

servers = os.environ.get('MEMCACHIER_SERVERS', '').split(',')
user = os.environ.get('MEMCACHIER_USERNAME', '')
passw = os.environ.get('MEMCACHIER_PASSWORD', '')

# python-memcached only works with one server because of MemCachier's ASCII auth.
server = [servers[0]]

# TODO: currently fails with:
#   MemCached: MemCache: inet:mc1.c1.ec2.staging.memcachier.com:11211: timed out.
#   Marking dead.
mc = memcache.Client(server,
                     # defaults
                     debug=1,
                     dead_retry=30,  # seconds to wait before a retry
                     socket_timeout=3)
# MemCachier ASCII auth
mc.set(user, passw)

mc.set("foo", "bar")
print(mc.get("foo"))
