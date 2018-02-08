var memjs = require('memjs')

var mc = memjs.Client.create()

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
