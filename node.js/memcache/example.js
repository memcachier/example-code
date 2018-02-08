// TODO: require fails with error. Try to fix.
var memcache = require('memcache')

var mc = new memcache.Client(11211, 'mc1.dev.ec2.memcachier.com');

mc.on('connect', function() {
  client.set(process.env.MEMCACHIER_USERNAME, process.env.MEMCACHIER_PASSWORD, function(err, res){
    if(err != null) {
      console.log('Error doing auth: ' + err)
    }
    console.log(res)
  }, 0);
})

client.set('hello', 'world', function(err, res){
  if(err != null) {
    console.log('Error setting value: ' + err)
  }
}, 0);

client.get('hello', function(err, res){
  if(err != null) {
    console.log('Error getting value: ' + err)
  }
  else {
    console.log(res.toString('utf8'))
  }
});
