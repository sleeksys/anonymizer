-- Tokens: used for authentication
CREATE TABLE tokens (
    id BIGINT NOT NULL,
    value VARCHAR (255),
    date_start DATETIME NOT NULL,
    date_end DATETIME NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (value)
);

-- Sheet cells
CREATE TABLE cells (
    id BIGINT NOT NULL,
    token_id BIGINT NOT NULL,
    row_index INT NOT NULL,
    column_index INT NOT NULL,
    text VARCHAR(255),
    PRIMARY KEY (id)
);

-- Labels
CREATE TABLE labels (
    id BIGINT NOT NULL,
    cell_id BIGINT NOT NULL,
    text VARCHAR(255),
    privacy_level VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
);
