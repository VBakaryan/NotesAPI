INSERT IGNORE INTO oauth_client_details(client_id, client_secret, scope, authorized_grant_types,
  web_server_redirect_uri, authorities, access_token_validity,
  refresh_token_validity, additional_information, autoapprove)
 VALUES
 ('notes', '$2a$10$SCL34RlN8G.2mGyOnkuWMeAPQMqdjq.KV.uWOOHQr6KBQmHUSAgda', 'notes,read,write',
  'password,authorization_code,refresh_token,client_credentials', null, null, 2592000, 5184000, null, true);
