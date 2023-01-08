db.createUser(
    {
        user: "usr-primemusic-mongo",
        pwd: "pwd-primemusic-mongo",
        roles: [
            {
                role: "readWrite",
                db: "primemusic-dev-mongo"
            }
        ]
    }
);