INSERT INTO clients (client_type,id,email,name,phone,company_identifier,birth_date)
VALUES
    ('PERSON','11111111-1111-1111-1111-111111111111','alice.person@example.com','Alice Person','0760000001',NULL,'1990-05-01')
ON CONFLICT (id) DO NOTHING;

INSERT INTO clients (client_type,id,email,name,phone,company_identifier,birth_date)
VALUES
    ('COMPANY','22222222-2222-2222-2222-222222222222','contact@acme.example','ACME SA','0210000002','aaa-123',NULL)
ON CONFLICT (id) DO NOTHING;

INSERT INTO contracts (id,client_id,start_date,end_date,cost_amount,updated_at)
VALUES ('aaaaaaa1-0000-0000-0000-000000000001','11111111-1111-1111-1111-111111111111',
        DATE '2019-01-10', DATE '2031-12-31', 600000, NOW())
ON CONFLICT (id) DO NOTHING;

INSERT INTO contracts (id,client_id,start_date,end_date,cost_amount,updated_at)
VALUES ('aaaaaaa2-0000-0000-0000-000000000002','11111111-1111-1111-1111-111111111111',
        CURRENT_DATE, NULL, 700000, NOW())
ON CONFLICT (id) DO NOTHING;

INSERT INTO contracts (id,client_id,start_date,end_date,cost_amount,updated_at)
VALUES ('aaaaaaa3-0000-0000-0000-000000000003','11111111-1111-1111-1111-111111111111',
        DATE '2024-10-19', DATE '2025-09-30', 100, NOW())
ON CONFLICT (id) DO NOTHING;

INSERT INTO contracts (id,client_id,start_date,end_date,cost_amount,updated_at)
VALUES ('bbbbbbb1-0000-0000-0000-000000000001','22222222-2222-2222-2222-222222222222',
        DATE '2018-01-10', DATE '2030-12-31', 300000, NOW())
ON CONFLICT (id) DO NOTHING;

INSERT INTO contracts (id,client_id,start_date,end_date,cost_amount,updated_at)
VALUES ('bbbbbbb2-0000-0000-0000-000000000002','22222222-2222-2222-2222-222222222222',
        CURRENT_DATE, CURRENT_DATE, 2000, NOW())
ON CONFLICT (id) DO NOTHING;
