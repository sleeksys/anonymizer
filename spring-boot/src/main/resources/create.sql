CREATE TABLE labels (
    id BIGINT NOT NULL,
    text VARCHAR(255),
    sheetColumn INT(11),
    privacyLevel VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE tokens (
    id BIGINT NOT NULL,
    value VARCHAR (255),
    start DATETIME NOT NULL,
    end DATETIME NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (value)
);
