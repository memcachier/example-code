# Author: MemCachier Inc.
# License: BSD-3
# Date: November 26th, 216
#
import pylibmc
import os

# Specific to server - but can't sum
stats_remove = [
    'pid',
    'uptime',
    'rusage_user',
    'rusage_system',
    'threads',
    'connection_structures',
    'pointer_size',
    'version',
    'time',
]

# Shared across all servers
stats_sum_no = [
    'limit_maxbytes',
    'curr_items',
    'bytes',
    'evictions',
]

# Specific to server = sum
stats_sum_yes = [
    'bytes_read',
    'bytes_written',
    'total_connections',
    'curr_connections',
    'total_items',
    'cmd_get',
    'cmd_set',
    'get_misses',
    'get_hits',
]

# Get stats summarized correctly across all MemCachier proxies
def get_stats_all(self):
    sum_stats = {}
    all_stats = self.get_stats()
    for s in all_stats:
        s = s[1]
        for k in s:
            if k in stats_sum_no:
                sum_stats[k] = s[k]
            elif k in stats_sum_yes:
                s0 = sum_stats.setdefault(k, 0)
                sum_stats[k] += int(s[k])
    return sum_stats

# Monkey patch pylibmc.Client
pylibmc.Client.get_stats_all = get_stats_all

#
# Test out new method
# --------------------------------------------------------

mc_srvs = os.environ.get('MEMCACHIER_SERVERS', '').split(',')
mc_user = os.environ.get('MEMCACHIER_USERNAME', '')
mc_pass = os.environ.get('MEMCACHIER_PASSWORD', '')

mc = pylibmc.Client(
        mc_srvs,
        username=mc_user,
        password=mc_pass,
        binary=True,
        behaviors={
            # Enable faster IO
            'tcp_nodelay': True,
            'tcp_keepalive': True,

            # Timeout settings
            'connect_timeout': 2000, # ms
            'send_timeout': 750 * 1000, # us
            'receive_timeout': 750 * 1000, # us
            '_poll_timeout': 2000, # ms

            # Better failover
            'ketama': True,
            'remove_failed': 1,
            'retry_timeout': 2,
            'dead_timeout': 30,
        }
    )

print mc.get_stats_all()

