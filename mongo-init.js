db.createUser({
    user: "stock_app_user",
    pwd: "stock",
    roles: [{ role: "readWrite", db: "stock_db" }],
});