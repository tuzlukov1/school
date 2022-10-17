CREATE table users (
    id INTEGER PRIMARY KEY,
    name CHAR,
    age INTEGER,
    have_driver_license BOOLEAN
);

CREATE table vehicles (
                       id INTEGER PRIMARY KEY,
                       brand CHAR,
                       model CHAR,
                       cost INTEGER
);

CREATE table OWNERSHIP (
                          id INTEGER PRIMARY KEY,
                          owner_id INTEGER REFERENCES users(id),
                          vehicle_id INTEGER REFERENCES vehicles(id)
);