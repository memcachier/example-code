<?php

require 'vendor/autoload.php';

// create a new persistent client
$m = new Memcached("memcached_pool");
$m->setOption(Memcached::OPT_BINARY_PROTOCOL, TRUE);

// some nicer default options
// - nicer TCP options
$m->setOption(Memcached::OPT_TCP_NODELAY, TRUE);
$m->setOption(Memcached::OPT_NO_BLOCK, FALSE);
// - timeouts
$m->setOption(Memcached::OPT_CONNECT_TIMEOUT, 2000);    // ms
$m->setOption(Memcached::OPT_POLL_TIMEOUT, 2000);       // ms
$m->setOption(Memcached::OPT_RECV_TIMEOUT, 750 * 1000); // us
$m->setOption(Memcached::OPT_SEND_TIMEOUT, 750 * 1000); // us
// - better failover
$m->setOption(Memcached::OPT_DISTRIBUTION, Memcached::DISTRIBUTION_CONSISTENT);
$m->setOption(Memcached::OPT_LIBKETAMA_COMPATIBLE, TRUE);
$m->setOption(Memcached::OPT_RETRY_TIMEOUT, 2);
$m->setOption(Memcached::OPT_SERVER_FAILURE_LIMIT, 1);
$m->setOption(Memcached::OPT_AUTO_EJECT_HOSTS, TRUE);

// setup authentication
$m->setSaslAuthData( getenv("MEMCACHIER_USERNAME")
                   , getenv("MEMCACHIER_PASSWORD") );

// We use a consistent connection to memcached, so only add in the
// servers first time through otherwise we end up duplicating our
// connections to the server.
if (!$m->getServerList()) {
    // parse server config
    $servers = explode(",", getenv("MEMCACHIER_SERVERS"));
    foreach ($servers as $s) {
        $parts = explode(":", $s);
        $m->addServer($parts[0], $parts[1]);
    }
}

// Test client
$m->set("foo", "bar");
$foo = $m->get("foo");

var_dump($foo);
?>
