DROP TABLE IF EXISTS comment;
CREATE TABLE comment
(
    id           INT      NOT NULL AUTO_INCREMENT,
    content      TEXT     NOT NULL,
    user_id      INT      NOT NULL,
    entity_id    INT      NOT NULL,
    entity_type  INT      NOT NULL,
    created_date DATETIME NOT NULL,
    status       INT      NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    INDEX entity_index (entity_id ASC, entity_type ASC)
);
