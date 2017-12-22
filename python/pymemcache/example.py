#! /bin/python3

import pymemcache.client.base as pymemcache
import os

servers = os.environ.get('MEMCACHIER_SERVERS', '').split(',')
user = os.environ.get('MEMCACHIER_USERNAME', '')
passw = os.environ.get('MEMCACHIER_PASSWORD', '')

# pymemcache only works with one server because of MemCachier's ASCII auth.
# TODO: To enable HashClient we would need to add the auth to add_server method
# of HashClient and to _create_client of PooledClient.
hostname, port = servers[0].split(':')

mc = pymemcache.Client((hostname, int(port)),
                       # Faster IO
                       no_delay=True,
                       # defaults
                       connect_timeout=None,
                       timeout=None,
                       ignore_exc=False,
                       key_prefix='',
                       default_noreply=True,
                       allow_unicode_keys=False)
# MemCachier ASCII auth
mc.set(user, passw)

mc.set("foo", "bar")
print(mc.get("foo"))
