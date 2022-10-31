CREATE TABLE TASK (ID BIGINT NOT NULL AUTO_INCREMENT,
                   CREATED_AT DATETIME,
                   DESCRIPTION VARCHAR(255),
                   TASK_STATUS VARCHAR(255),
                   SUMMARY VARCHAR(255),
                   UPDATED_AT DATETIME,
                   ID_USER BIGINT,
                   CONSTRAINT TASK_PK PRIMARY KEY (ID),
                   CONSTRAINT FK_USER FOREIGN KEY (ID_USER)
                   REFERENCES USER (ID)
) ENGINE=InnoDB