var express = require('express');
var app = express();
var port = 8081;
var address = '127.0.0.1'

app.address = address;
app.listen(port, address);

require('./routes')(app);

console.log('Check the app at Port :' + port);