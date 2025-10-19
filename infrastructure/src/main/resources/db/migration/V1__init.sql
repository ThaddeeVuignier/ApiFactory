-- tables
CREATE TABLE IF NOT EXISTS clients (
                                       client_type        varchar(31)  NOT NULL,
    id                 uuid         NOT NULL PRIMARY KEY,
    email              varchar(255) NOT NULL,
    name               varchar(255) NOT NULL,
    phone              varchar(255) NOT NULL,
    company_identifier varchar(255),
    birth_date         date
    );

CREATE TABLE IF NOT EXISTS contracts (
                                         id           uuid          NOT NULL PRIMARY KEY,
                                         client_id    uuid          NOT NULL REFERENCES clients(id) ON DELETE CASCADE,
    start_date   date          NOT NULL,
    end_date     date,
    cost_amount  numeric(19,2) NOT NULL,
    updated_at   timestamptz   NOT NULL
    );

CREATE INDEX IF NOT EXISTS idx_contract_client_id  ON contracts(client_id);
CREATE INDEX IF NOT EXISTS idx_contract_end_date   ON contracts(end_date);
CREATE INDEX IF NOT EXISTS idx_contract_updated_at ON contracts(updated_at);
