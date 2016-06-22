# HOW TO

# assuming you have ruby > 2.1 installed
# open terminal and run 'gem install mongo'
# run 'mongod'
# remove comment tags according to what you want to do
# in a new terminal tab, nivigate to db folder and run 'ruby seed.rb' to execute this code

require 'mongo'
require 'date'
include Mongo


mongo_client = Mongo::Client.new([ '127.0.0.1:27017' ], :database => 'iit-project')
mongo_client.database.drop
measures = mongo_client[:measures]

devices = ['device1', 'device2', 'device3']

# populate measures collection with 20 records for each device and the current datetime
devices.each do |device|
  20.times do |index|
    record = {device: device, temperature: Random.new.rand(0.1..50.1),
     pressure: Random.new.rand(0.1..50.1),
     humidity: Random.new.rand(0.1..50.1),
     datetime: DateTime.now - (index * 2)}

    measures.insert_one(record)
  end
end

# print number of records in measures collection
puts 'Number of records: ' + measures.find().count.to_s

# get all measures where device = device1
# d1_measures = measures.find(device: 'device1')

# print each one of those measures
# d1_measures.each do |m|
#   puts m
# end

# print the number of records from device1
# puts d1_measures.count

# delete measures from device1
# deleted_records = measures.delete_many(device: 'device1')
# puts deleted_records.n

# print all collections in the database
# puts mongo_client.database.collection_names


# after running this code you can use this command (in your terminal) to export your data to a json file
# mongoexport --db iit-project --collection measures --out measures.json

# in case you want to import data, use this command (in your terminal)
# mongoimport --db iit-project --collection measures --file measures.json

