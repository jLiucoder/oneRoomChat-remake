CREATE DATABASE one_room_chat_app;
\c one_room_chat_app
CREATE TABLE USERS (

  user_id SERIAL PRIMARY KEY,
  username TEXT NOT NULL unique,
  user_fullname TEXT NOT NULL,
  user_DOB DATE DEFAULT now(),
  user_email TEXT,
  user_pic TEXT,
  date_created date NOT NULL DEFAULT now(),
  user_password TEXT NOT NULL
);

CREATE TABLE CHATS(

  message_id SERIAL PRIMARY KEY,
  message_text TEXT NOT NULL,
  user_id INTEGER NOT NULL,
  message_date DATE NOT NULL DEFAULT now() ,
  CONSTRAINT fk_users
      FOREIGN KEY(user_id) 
	  REFERENCES USERS(user_id)
);

insert into users(
  username,
  user_fullname,
  user_password,
                  user_email,
                  user_DOB
) VALUES (
  'palakkeni5',
  'Palak Pramod Keni',
  '827ccb0eea8a706c4c34a16891f84e7b',
          'palak@gmail.com',
            '2000-02-23'
);

insert into users(
  username,
  user_fullname,
  user_password,
    user_email,
    user_DOB
) VALUES (
  'jerryliu',
  'jerryliu',
  '827ccb0eea8a706c4c34a16891f84e7b',
    'jiayuliu@gmail.com',
    '2000-07-27'
);

# insert into chats(
#   message_text,
#   user_id
# ) values (
#   'Hello. This is my first message',
#   1
# ),(
#   'Hi palakkeni5 . This is also my first message',
#   1
# );
commit;