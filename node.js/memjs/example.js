var memjs = require('memjs')

var mc = memjs.Client.create(process.env.MEMCACHIER_SERVERS, {
  failover: true,     // default: false
  failoverTime: 30,   // default: 60 (seconds)
  retries: 2,         // default: 2
  retry_delay: 0.2,   // default: 0.2 (seconds)
  expires: 0,         // default: 0 (never)
  logger: console,    // default: console
  timeout: 1.0,       // default: 0.5 (seconds)
  conntimeout: 2.0,   // default: 2 * timeout
  keepAlive: true,    // default: false
  keepAliveDelay: 30, // default: 30 (seconds)
  username: process.env.MEMCACHIER_USERNAME,
  password: process.env.MEMCACHIER_PASSWORD
})

mc.set('hello', 'world', {expires:600}, function(err, val) {
  if(err != null) {
    console.log('Error setting value: ' + err)
  }
})

mc.get('hello', function(err, val) {
  if(err != null) {
    console.log('Error getting value: ' + err)
  }
  else {
    console.log(val.toString('utf8'))
  }
})
