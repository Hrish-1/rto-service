CREATE TYPE attachment_type AS ENUM ('aadhaar', 'pan', 'insurance', 'chassis', 'profile');
ALTER TABLE attachments ADD COLUMN attachment_type attachment_type NOT NULL;
