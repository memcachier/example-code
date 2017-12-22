#! /bin/python3

import bmemcached
import os

servers = os.environ.get('MEMCACHIER_SERVERS', '').split(',')
user = os.environ.get('MEMCACHIER_USERNAME', '')
passw = os.environ.get('MEMCACHIER_PASSWORD', '')

mc = bmemcached.Client(servers,
                       username=user, password=passw,
                       socket_timeout=3)  # 3s is the default socket timeout

mc.enable_retry_delay(True)  # Enabled by default. Sets retry delay to 5s.

mc.set("foo", "bar")
print(mc.get("foo"))
