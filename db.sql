CREATE TABLE account (
                         account_id SERIAL PRIMARY KEY,
                         balance double precision,
                         document_number VARCHAR(255) NOT NULL
);



CREATE TABLE operation_type (
                                operation_type_id INT  PRIMARY KEY,
                                description VARCHAR(255),
                                amount_multiplier INT NOT NULL CHECK (amount_multiplier IN (1, -1))
);


CREATE TABLE transaction (
                             transaction_id SERIAL PRIMARY KEY,
                             account_id INTEGER NOT NULL REFERENCES account(account_id),
                             operation_type_id INTEGER NOT NULL REFERENCES operation_type(operation_type_id),
                             amount DOUBLE PRECISION,
                             event_date TIMESTAMP
);

insert into  operation_type values (1, 'Normal Purchase', -1);
insert into  operation_type values (2, 'Purchase with installments', -1);
insert into  operation_type values (3, 'Withdrawal', -1);
insert into  operation_type values (4, 'Credit Voucher', 1);

-- Optional: indexes for common lookups
CREATE INDEX IF NOT EXISTS idx_transactions_account_id ON transaction(account_id);
CREATE INDEX IF NOT EXISTS idx_transactions_operation_type_id ON transaction(operation_type_id);
CREATE INDEX IF NOT EXISTS idx_transactions_event_date ON transaction(event_date);
