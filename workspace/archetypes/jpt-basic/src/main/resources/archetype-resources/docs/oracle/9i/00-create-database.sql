CREATE TABLESPACE "${databaseSchema}" 
    LOGGING 
    DATAFILE 'D:\ORACLE\ORADATA\ORADB\${databaseSchema}.dbf' SIZE 100M REUSE 
    AUTOEXTEND 
    ON NEXT  100M MAXSIZE UNLIMITED EXTENT MANAGEMENT LOCAL 
    SEGMENT SPACE MANAGEMENT  AUTO 
/

CREATE USER "${databaseUsername}"  PROFILE "DEFAULT" 
    IDENTIFIED BY "${databasePassword}" DEFAULT TABLESPACE "${databaseSchema}" 
    ACCOUNT UNLOCK;
GRANT "CONNECT" TO "${databaseUsername}";
GRANT "RESOURCE" TO "${databaseUsername}";
GRANT UNLIMITED TABLESPACE TO ${databaseUsername};
GRANT CREATE ANY DIRECTORY TO ${databaseUsername};
/