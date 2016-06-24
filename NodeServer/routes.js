var database = require('./database');

module.exports = function(app){
	app.get('/', function(req, res){
		database.getConnection(app.address, 'iit-project', function(err, db){
			if(err){
				throw err;
			}else{
				db.collection('measures').find().toArray(function(error, result){
					if(error){
						throw error;
					}else{
						res.json(result);
					}
				});
			}
		});
	});
};