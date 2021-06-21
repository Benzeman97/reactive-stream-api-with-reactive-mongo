var db = connect("mongodb://root:14292@127.0.0.1:27017/admin");

db = db.getSiblingDB('shop');

db.createUser(
     {
        user: "benz",
        pwd: "14292",
        roles: [ { role: "readWrite", db: "shop" } ]
    }
);

db.createCollection('product');

db.product.insert([{"_id":1009,"prodName":"Kelly Benz","price":145000.0},{"_id":1010,"prodName":"Chopa Malli","price":95000.0}]);
