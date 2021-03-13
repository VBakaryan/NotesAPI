CREATE TABLE IF NOT EXISTS oauth_client_token (token_id VARCHAR(255),
                                               token LONGBLOB,
                                               authentication_id VARCHAR(255),
                                               user_name VARCHAR(255),
                                               client_id VARCHAR(255),
                                               primary key (authentication_id)
                                              );

CREATE TABLE IF NOT EXISTS oauth_client_details (client_id varchar(255) NOT NULL,
                                                 resource_ids varchar(255) DEFAULT NULL,
                                                 client_secret varchar(255) DEFAULT NULL,
                                                 scope varchar(255) DEFAULT NULL,
                                                 authorized_grant_types varchar(255) DEFAULT NULL,
                                                 web_server_redirect_uri varchar(255) DEFAULT NULL,
                                                 authorities varchar(255) DEFAULT NULL,
                                                 access_token_validity integer(11) DEFAULT NULL,
                                                 refresh_token_validity integer(11) DEFAULT NULL,
                                                 additional_information varchar(255) DEFAULT NULL,
                                                 autoapprove varchar(255) DEFAULT NULL,
                                                 primary key (client_id)
                                                );

CREATE TABLE IF NOT EXISTS oauth_access_token (token_id VARCHAR(255),
                                               token LONGBLOB,
                                               authentication_id VARCHAR(255),
                                               user_name VARCHAR(255),
                                               client_id VARCHAR(255),
                                               authentication LONGBLOB,
                                               refresh_token VARCHAR(255),
                                               primary key (authentication_id)
                                              );

CREATE TABLE IF NOT EXISTS oauth_refresh_token (token_id VARCHAR(255),
                                                token LONGBLOB,
                                                authentication LONGBLOB
                                               );

CREATE TABLE IF NOT EXISTS oauth_code (code VARCHAR(255),
                                       authentication VARBINARY(255)
                                      );

CREATE TABLE IF NOT EXISTS oauth_approvals (userId VARCHAR(255),
                                            clientId VARCHAR(255),
                                            scope VARCHAR(255),
                                            status VARCHAR(10),
                                            expiresAt DATETIME,
                                            lastModifiedAt DATETIME
                                           );

