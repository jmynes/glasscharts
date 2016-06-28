# HOW TO

# assuming you have ruby > 2.1 installed
# open terminal and run 'gem install mongo'
# remove comment tags according to what you want to do
# navigate to this file's folder and run 'ruby seed.rb' to execute the script

require 'mongo'
require 'date'
include Mongo


mongo_client = Mongo::Client.new(['127.0.0.1'], database: 'iit-project')

mongo_client.database.drop
measures = mongo_client[:measures]

rooms = ['TS-2030', 'TS-2033']

# insert into measures 30 records for each room (30 different days)
rooms.each do |room|
  30.times do |index|
    record = {room: room,
     temperature: Random.new.rand(0.1..50.1),
     pressure: Random.new.rand(0.1..50.1),
     humidity: Random.new.rand(0.1..50.1),
     datetime: (DateTime.now + (index)).to_time
    }
    measures.insert_one(record)
  end
end


# insert into measures 10 records for each room (with different times on the same day <current day>)
rooms.each do |room|
    10.times do |index|
        record = {room: room,
         temperature: Random.new.rand(0.1..50.1),
         pressure: Random.new.rand(0.1..50.1),
         humidity: Random.new.rand(0.1..50.1),
         datetime: Date.today.to_time + (index * 60 * 60)
        }
        measures.insert_one(record)
    end
end


# insert into measures 12 records for each room (one per month)
rooms.each do |room|
    12.times do |index|
        record = {room: room,
         temperature: Random.new.rand(0.1..50.1),
         pressure: Random.new.rand(0.1..50.1),
         humidity: Random.new.rand(0.1..50.1),
         datetime: Date.new(2016, index + 1, 1).to_time
        }
        measures.insert_one(record)
    end
end

# print number of records in measures collection
puts 'Number of records: ' + measures.find().count.to_s

# get all measures where room = TS-2033
# r1_measures = measures.find(room: 'TS-2033')

# print each one of those measures
# r1_measures.each do |m|
#   puts m
# end

# print the number of records from room TS-2033
# puts r1_measures.count

# delete measures from room TS-2033
# deleted_records = measures.delete_many(room: 'TS-2033')
# puts deleted_records.n

# print all collections in the database
# puts mongo_client.database.collection_names


# after running this code you can use this command (in your terminal) to export your data to a json file
# mongoexport --db iit-project --collection measures --out measures.json

# in case you want to import data, use this command (in your terminal)
# mongoimport --db iit-project --collection measures --file measures.json

