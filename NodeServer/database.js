var MongoClient = require('mongodb').MongoClient;

module.exports = {
	getConnection: function(address, database, callback){
		return getConnection(address, database, callback);
	}
};

function getConnection(address, database, callback){
	var connection = MongoClient.connect(
		'mongodb://' + address + '/' + database,
		function(err, db){
			if(err){
				callback(err);
			}else{
				callback(null, db);
			}
		}
	);
}