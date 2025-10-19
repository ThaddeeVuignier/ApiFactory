-- unicité company_identifier seulement pour les COMPANY non nuls
CREATE UNIQUE INDEX IF NOT EXISTS ux_company_identifier_company
    ON clients(company_identifier)
    WHERE client_type = 'COMPANY' AND company_identifier IS NOT NULL;

-- règle "COMPANY => company_identifier obligatoire"
ALTER TABLE clients
    DROP CONSTRAINT IF EXISTS chk_company_identifier_required,
    ADD  CONSTRAINT chk_company_identifier_required
        CHECK (client_type <> 'COMPANY' OR company_identifier IS NOT NULL);
