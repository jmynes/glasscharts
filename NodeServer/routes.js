var database = require('./database');

module.exports = function(app){
	var address = app.get('address');
	address = address == "127.0.0.1" ? 'mongodb://127.0.0.1/iit-project' : address; 

	app.get('/', function(req, res){
		database.getConnection(address, function(err, db){
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
	app.get('/getHourData', function(req, res){
		database.getConnection(address, function(err, db){
			if(err){
				throw err;
			}else{
				var today = new Date();
				var tomorrow = new Date(today.getTime() + (24 * 60 * 60 * 1000));
				db.collection('measures').aggregate(
					[
						{
							$match: 
							{
								datetime: 
								{
									$gte: today, 
									$lt: tomorrow
								}
							}
						},
						{
							$group:
							{
								_id : { room: "$room", time: { $hour: "$datetime" }, type: "Hour" },
								avgTemperature : { $avg: "$temperature" },
								avgPressure: {$avg: "$pressure"},
								avgHumidity: {$avg: "$humidity"}
							}
						}
					]
				).toArray(function(error, result){
					if(error){
						throw error;
					}else{
						res.json(result);
					}
				});
			}
		});
	});
	app.get('/getMonthData', function(req, res){
		database.getConnection(address, function(err, db){
			if(err){
				throw err;
			}else{
				var date = new Date();
				db.collection('measures').aggregate(
					[
						{
							$match: 
							{
								datetime: 
								{
									$gte: new Date(date.getFullYear(), date.getMonth(), 1), 
									$lt: new Date(date.getFullYear(), date.getMonth() + 1, 0)
								}
							}
						},
						{
							$group:
							{
								_id : { room: "$room", time: { $week: "$datetime" }, type: "Week" },
								avgTemperature : { $avg: "$temperature" },
								avgPressure: { $avg: "$pressure" },
								avgHumidity: { $avg: "$humidity" },
							}
						}
					]
				).toArray(function(error, result){
					if(error){
						throw error;
					}else{
						res.json(result);
					}
				});
			}
		});
	});
	app.get('/getYearData', function(req, res){
		database.getConnection(address, function(err, db){
			if(err){
				throw err;
			}else{
				var date = new Date();
				db.collection('measures').aggregate(
					[
						{
							$match: 
							{
								datetime: 
								{
									$gte: new Date(new Date().getFullYear(), 0, 1), 
									$lt: new Date(new Date().getFullYear(), 13, 1)
								}
							}
						},
						{
							$group:
							{
								_id : { room: "$room", time: { $month: "$datetime" }, type: "Month" },
								avgTemperature : { $avg: "$temperature" },
								avgPressure: { $avg: "$pressure" },
								avgHumidity: { $avg: "$humidity" },
							}
						}
					]
				).toArray(function(error, result){
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