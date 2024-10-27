CREATE DOMAIN created_at AS TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP;
CREATE DOMAIN updated_at AS TIMESTAMPTZ NULL;

-- Employee
CREATE TYPE employee_status AS ENUM ('active', 'inactive');
CREATE TYPE gender AS ENUM ('male', 'female', 'others');

CREATE TABLE employees (
    id             UUID PRIMARY KEY,
    user_name      VARCHAR(10),
    email          VARCHAR(255) NOT NULL UNIQUE,
    password       VARCHAR(255) NOT NULL,
    first_name     VARCHAR(30),
    middle_name    VARCHAR(30),
    last_name      VARCHAR(30),
    gender         gender,
    birth_date     DATE,
    mobile_no      VARCHAR(20),
    address        TEXT,
    aadhar_no      VARCHAR(12),
    pan            VARCHAR(10),
    status         employee_status NOT NULL,
    "level"        INTEGER CHECK ("level" IN (1, 2, 3)) NOT NULL DEFAULT 3,
    joining_date   DATE,
    end_date       DATE DEFAULT '9999-12-31',
    CONSTRAINT user_name_length CHECK (LENGTH(user_name) <= 10),
    CONSTRAINT pan_length CHECK (LENGTH(pan) <= 10)
);

-- Bank
CREATE TABLE banks (
    id       UUID PRIMARY KEY,
    name     VARCHAR(100),
    address  TEXT
);

-- Customer
CREATE TABLE customers (
    id          UUID PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    company     VARCHAR(255),
    address     TEXT,
    city        VARCHAR(100),
    pin_code    VARCHAR(20),
    state       VARCHAR(100),
    state_code  VARCHAR(10),
    contact1    VARCHAR(50),
    contact2    VARCHAR(50),
    email_id    VARCHAR(255),
    entered_by  UUID NOT NULL REFERENCES employees (id) ,
    updated_by  UUID  REFERENCES employees (id),
    created_at  created_at,
    updated_at  updated_at
);

-- Transaction
CREATE TYPE transaction_status AS ENUM ('ready', 'delivered', 'invoiced', 'completed');

CREATE TABLE transactions (
    id               UUID PRIMARY KEY,
    status           transaction_status NOT NULL,
    vehicle_number   VARCHAR(255) NOT NULL,
    from_rto         VARCHAR(255) NOT NULL,
    to_rto           VARCHAR(255) NOT NULL,
    amount           DOUBLE PRECISION,
    chassis_number   VARCHAR(255),
    letter_number    VARCHAR(255),
    letter_date      DATE,
    note             TEXT,
    seller           JSONB,
    purchaser        JSONB,
    challan_payment  DOUBLE PRECISION,
    challan_number   INTEGER,
    officer_payment  DOUBLE PRECISION,
    cancel_date      DATE,
    customer_id      UUID NOT NULL REFERENCES customers (id),
    bank_id          UUID REFERENCES banks (id),
    created_by       UUID NOT NULL REFERENCES employees (id),
    created_at       created_at,
    updated_at       updated_at
);

-- Attachment
CREATE TYPE entity_type AS ENUM ('employee', 'transaction');
CREATE TYPE attachment_type AS ENUM ('aadhaar', 'pan', 'insurance', 'chassis', 'profile', 'form29', 'form30_1', 'form30_2');

CREATE TABLE attachments (
    id            UUID PRIMARY KEY,
    entity_type   entity_type NOT NULL,
    entity_id     UUID NOT NULL,
    content_type  VARCHAR(30) NOT NULL,
    ext           VARCHAR(5) NOT NULL,
    type          attachment_type NOT NULL
);

-- Service
CREATE TABLE services (
    id UUID PRIMARY KEY,
    name VARCHAR(50),
    short_name VARCHAR(10),
    amount DOUBLE PRECISION
);

-- ServiceTransaction

CREATE TABLE services_transactions (
    id              UUID PRIMARY KEY,
    service_id      UUID NOT NULL REFERENCES services (id),
    transaction_id  UUID NOT NULL REFERENCES transactions (id)
);


