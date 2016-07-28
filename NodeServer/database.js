var MongoClient = require('mongodb').MongoClient;

module.exports = {
	getConnection: function(address, callback){
		return getConnection(address, callback);
	}
};

function getConnection(address, callback){
	var connection = MongoClient.connect(address, function(err, db){
			if(err){
				callback(err);
			}else{
				callback(null, db);
			}
		}
	);
}