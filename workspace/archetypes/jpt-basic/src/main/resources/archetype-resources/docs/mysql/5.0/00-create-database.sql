CREATE DATABASE ${databaseSchema};

GRANT ALL PRIVILEGES ON ${databaseSchema}.* TO '${databaseUsername}'@'localhost' IDENTIFIED BY '${databasePassword}' WITH GRANT OPTION;
