var express = require('express');
var app = express();
app.set('port', (process.env.PORT || 8081));
app.set('address', (process.env.MONGODB_URI || '127.0.0.1'));

app.listen(app.get('port'));

require('./routes')(app);

console.log('Check the app at Port: ' + app.get('port'));
console.log('Address: ' + app.get('address'));