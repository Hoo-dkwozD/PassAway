DROP DATABASE IF EXISTS `record`;
CREATE DATABASE `record`;

USE `record`;

#Attraction
INSERT INTO `record`.`attraction`(`attraction_id`, `address`,	`background_image`, `barcode_image`, `benefits`,	`cannot_book`,	`description`,	`expiry_date`,	`max_loans_per_month`,	`max_passes_per_loan`,	`membership_id`,	`name`,	`num_accompanying_guests`,	`pass_type`,	`replacement_fee`,	`terms_conditions`) VALUES 
(1, "80 Mandai Lake Rd, 729826", "staticAttractions/Singapore zoo14-11-2022_18-12-57.jpeg","staticAttractionBarcodes/Singapore zoo15-11-2022_12-44-53.jpeg", "Present this letter to enjoy up to 20% discount at selected Retail and F&B outlets.", false, "premium corporate friends of the singapore zoo (cfoz) membership", "2022-12-31", 2, 2, "22401046113600009088", "Singapore Zoo", 4, 0, 60.15, "• All corporate members must secure a time slot via https://managebooking.mandai.com/ and comply to MWR Safe Management Measures for your safety and well-being, otherwise entry is not allowed. Time slot bookings are subject to availability, on a first come, first served basis. \n• Each letter allows complimentary admission to Singapore Zoo & River Wonders for up to four (4) persons, one of whom must be an employee of the corporate member named above. The employee must be present and produce valid staff pass or NRIC along with the signed letter for benefit to apply. \n• Each letter can only be used ONCE a day.\n• In the event of unauthorized use or copy of the letter, Singapore Zoo & River Wonders will deny entry and verification will be done with the company which will take appropriate action for any wilful violation. \n • In the event that the letter is detected to be presented more than once on the same day; the employee will be liable for the additional entry at Singapore Zoo & River Wonders prevailing walk-in rate accordingly.\n• Your existing Corporate Membership will expire once the validity period is over. In addition, you shall adhere to the Membership terms and conditions stated https://www.mandai.com/en/memberships/corporate-membership.html during your visit.")
;

INSERT INTO `record`.`attraction`(`attraction_id`, `address`,	`background_image`, `barcode_image`, `benefits`,	`cannot_book`,	`description`,	`expiry_date`,	`max_loans_per_month`,	`max_passes_per_loan`,	`membership_id`,	`name`,	`num_accompanying_guests`,	`pass_type`,	`replacement_fee`,	`terms_conditions`) VALUES 
(2, "18 Marina Gardens Dr, Singapore 018953", "staticAttractions/Gardens by the bay14-11-2022_18-12-50.jpeg","staticAttractionBarcodes/Gardens by the bay15-11-2022_12-44-59.jpeg","Present this letter to enjoy up to 20% discount at selected Retail and F&B outlets.", false,"premium corporate friends of the gardens by the bay membership", "2022-12-31", 2, 2, "12345678", "Gardens by the Bay", 4, 0, 52.15, "• All corporate members must secure a time slot via https://managebooking.mandai.com/ and comply to MWR Safe Management Measures for your safety and well-being, otherwise entry is not allowed. Time slot bookings are subject to availability, on a first come, first served basis. \n• Each letter allows complimentary admission to Singapore Zoo & River Wonders for up to four (4) persons, one of whom must be an employee of the corporate member named above. The employee must be present and produce valid staff pass or NRIC along with the signed letter for benefit to apply. \n• Each letter can only be used ONCE a day.\n• In the event of unauthorized use or copy of the letter, Singapore Zoo & River Wonders will deny entry and verification will be done with the company which will take appropriate action for any wilful violation. \n • In the event that the letter is detected to be presented more than once on the same day; the employee will be liable for the additional entry at Singapore Zoo & River Wonders prevailing walk-in rate accordingly.\n• Your existing Corporate Membership will expire once the validity period is over. In addition, you shall adhere to the Membership terms and conditions stated https://www.mandai.com/en/memberships/corporate-membership.html during your visit.")
;

INSERT INTO `record`.`attraction`(`attraction_id`, `address`,	`background_image`, `barcode_image`, `benefits`,	`cannot_book`,	`description`,	`expiry_date`,	`max_loans_per_month`,	`max_passes_per_loan`,	`membership_id`,	`name`,	`num_accompanying_guests`,	`pass_type`,	`replacement_fee`,	`terms_conditions`) VALUES 
(3, "8 Sentosa Gateway, 098269", "staticAttractions/Universal Studios14-11-2022_18-12-30.jpeg","staticAttractionBarcodes/Universal Studios15-11-2022_12-45-02.jpeg","Present this letter to enjoy up to 20% discount at selected Retail and F&B outlets.", false, "premium corporate friends of Universal Studios membership", "2022-12-31", 2, 2, "677892", "Universal Studios Singapore", 4, 1, 80.15, "• All corporate members must secure a time slot via https://managebooking.mandai.com/ and comply to MWR Safe Management Measures for your safety and well-being, otherwise entry is not allowed. Time slot bookings are subject to availability, on a first come, first served basis. \n• Each letter allows complimentary admission to Singapore Zoo & River Wonders for up to four (4) persons, one of whom must be an employee of the corporate member named above. The employee must be present and produce valid staff pass or NRIC along with the signed letter for benefit to apply. \n• Each letter can only be used ONCE a day.\n• In the event of unauthorized use or copy of the letter, Singapore Zoo & River Wonders will deny entry and verification will be done with the company which will take appropriate action for any wilful violation. \n • In the event that the letter is detected to be presented more than once on the same day; the employee will be liable for the additional entry at Singapore Zoo & River Wonders prevailing walk-in rate accordingly.\n• Your existing Corporate Membership will expire once the validity period is over. In addition, you shall adhere to the Membership terms and conditions stated https://www.mandai.com/en/memberships/corporate-membership.html during your visit.")
;

# Staff
INSERT INTO `record`.`staff`(`staff_id`, `cannot_book`, `contact_number`, `email`, `first_name`, `hashed_password`, `is_disabled`, `is_registered`, `last_name`, `role`) VALUES 
(1, false, "91234567", "zwthean.2021@scis.smu.edu.sg", "Borrower1", "5E884898DA28047151D0E56F8DC6292773603D0D6AABBDD62A11EF721D1542D8", false, true, "Thean", 0)
;

INSERT INTO `record`.`staff`(`staff_id`, `cannot_book`, `contact_number`, `email`, `first_name`, `hashed_password`, `is_disabled`, `is_registered`, `last_name`, `role`) VALUES 
(2, false, "91234567", "borrower2@sportsschool.edu.sg", "borrower2", "5E884898DA28047151D0E56F8DC6292773603D0D6AABBDD62A11EF721D1542D8", false, true, "Tan", 0)
;

INSERT INTO `record`.`staff`(`staff_id`, `cannot_book`, `contact_number`, `email`, `first_name`, `hashed_password`, `is_disabled`, `is_registered`, `last_name`, `role`) VALUES 
(3, false, "81234567", "theanzhiwei@gmail.com", "ZWGmail", "5E884898DA28047151D0E56F8DC6292773603D0D6AABBDD62A11EF721D1542D8", false, true, "Thean", 1)
;

INSERT INTO `record`.`staff`(`staff_id`, `cannot_book`, `contact_number`, `email`, `first_name`, `hashed_password`, `is_disabled`, `is_registered`, `last_name`, `role`) VALUES 
(4, false, "82234567", "test1@sportsschool.edu.sg", "testuser", "5E884898DA28047151D0E56F8DC6292773603D0D6AABBDD62A11EF721D1542D8", false, true, "test", 2)
;


# Pass
INSERT INTO `record`.`pass`(`pass_id`, `is_lost`, `attraction_id`) VALUES 
("1", false, 1),
("2", false, 1),
("3", false, 1)
;

INSERT INTO `record`.`pass`(`pass_id`, `is_lost`, `attraction_id`) VALUES 
("A", false, 2),
("B", false, 2),
("C", false, 2)
;

INSERT INTO `record`.`pass`(`pass_id`, `is_lost`, `attraction_id`) VALUES 
("123A", false, 3),
("123B", false, 3),
("123C", false, 3)
;

# Loans
INSERT INTO `record`.`loan`(`has_collected`, `has_returned`, `start_date`, `pass_id`, `staff_id`) VALUES 
(false, false, "2022-12-5", "1", 1),
(false, false, "2022-12-5", "2", 1),
(false, false, "2022-12-7", "1", 2),
(false, false, "2022-12-7", "2", 2),
(false, false, "2022-12-9", "1", 4),
(false, false, "2022-12-9", "2", 4),
(false, false, "2022-12-9", "3", 4),
(false, false, "2022-12-11", "123A", 4),
(false, false, "2022-12-11", "123B", 4)
;

