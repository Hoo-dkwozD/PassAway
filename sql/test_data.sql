USE `record`;

INSERT INTO `staff` (
    `email`, 
    `first_name`, 
    `last_name`, 
    `contact_number`, 
    `role`, 
    `hashed_password`, 
    `cannot_book`, 
    `is_registered`, 
    `is_disabled`
) VALUES (
    "test@test",
    "test",
    "testing",
    NULL,
    "1",
    NULL,
    TRUE,
    FALSE,
    FALSE
);
INSERT INTO `staff` (
    `email`, 
    `first_name`, 
    `last_name`, 
    `contact_number`, 
    `role`, 
    `hashed_password`, 
    `cannot_book`, 
    `is_registered`, 
    `is_disabled`
) VALUES (
    "test1@test",
    "test1",
    "testing1",
    NULL,
    "0",
    NULL,
    TRUE,
    FALSE,
    FALSE
);
