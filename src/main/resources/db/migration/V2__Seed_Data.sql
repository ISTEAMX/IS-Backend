-- V2__Seed_Data.sql
-- Seed data for University of Oradea - Faculty of Electrical Engineering and Information Technology

-- =============================================
-- Users (password is BCrypt hash of "password123")
-- =============================================
INSERT INTO users (id, first_name, last_name, email, password, role) VALUES
    -- Admins
    (1,  'Ramona',   'Iuga',       'riuga@uoradea.ro',           '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'ADMIN'),
    (2,  'Anca',     'Bondar',     'ipoinar@uoradea.ro',         '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'ADMIN'),
    (3,  'Daniela',  'Pușcaș',     'dpuscas@uoradea.ro',         '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'ADMIN'),

    -- Departamentul CTI
    (4,  'Daniela Elena',       'Popescu',     'depopescu@uoradea.ro',        '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    (5,  'Doina',               'Zmaranda',    'dzmaranda@uoradea.ro',        '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    (6,  'Robert Ștefan',       'Győrödi',     'rgyorodi@uoradea.ro',         '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    (7,  'Cornelia Aurora',     'Győrödi',     'cgyorodi@uoradea.ro',         '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    (8,  'Alexandrina Mirela',  'Pater',       'mpater@uoradea.ro',           '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    (9,  'Gianina Adela',       'Gabor',       'gianina@uoradea.ro',          '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    (10, 'Ovidiu Constantin',   'Novac',       'ovnovac@uoradea.ro',          '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    (11, 'Elisa Valentina',     'Moisi',       'emoisi@uoradea.ro',           '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    (12, 'Florin',              'Vancea',      'fvancea@uoradea.ro',          '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    (13, 'Otto',                'Poszet',      'poszet@uoradea.ro',           '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    (14, 'Rodica',              'Țirtea',      'rtirtea@uoradea.ro',          '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    (15, 'George Dominic',      'Pecherle',    'gpecherle@uoradea.ro',        '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    (16, 'Octavia Maria',       'Bolojan',     'obolojan@uoradea.ro',         '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    (17, 'Simina Maria',        'Coman',       'scoman@uoradea.ro',           '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    (18, 'Mircea Petru',        'Ursu',        'mpursu@uoradea.ro',           '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    (19, 'Mircea Ioan',         'Mihăilă',     'mircea.mihaila@uoradea.ro',   '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    (20, 'Felicia Mirabela',    'Costea',      'mira_costea@uoradea.ro',      '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    (21, 'Adriana-Maria',       'Cuc',         'acuc@uoradea.ro',             '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    (22, 'Hannelore',           'Sebestyen',   'hsebestyen@uoradea.ro',       '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    (23, 'Cătălin-Daniel',      'Morar',       'cdmorar@uoradea.ro',          '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),

    -- Departamentul ETc
    (24, 'Cornelia Emilia',     'Gordan',      'cgordan@uoradea.ro',          '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    (25, 'Nistor Daniel',       'Trip',        'dtrip@uoradea.ro',            '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    (26, 'Cristian',            'Grava',       'cgrava@uoradea.ro',           '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    (27, 'Sorin',               'Curilă',      'scurila@uoradea.ro',          '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    (28, 'Ioan Marius',         'Buciu',       'ibuciu@uoradea.ro',           '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    (29, 'Ovidiu Marius',       'Neamțu',      'oneamtu@uoradea.ro',          '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    (30, 'Simona Cristina',     'Castrase',    'scastrase@uoradea.ro',        '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    (31, 'Liviu',               'Moldovan',    'liviu@uoradea.ro',            '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    (32, 'Marin Titus',         'Tomșe',       'mtomse@uoradea.ro',           '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    (33, 'Adrian Lazăr',        'Șchiop',      'aschiop@uoradea.ro',          '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    (34, 'Ioan',                'Gavriluț',    'gavrilut@uoradea.ro',         '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    (35, 'Sorin Vasile',        'Popa',        'sopopa@uoradea.ro',           '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    (36, 'Romulus',             'Reiz',        'rreiz@uoradea.ro',            '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    (37, 'Florin Lucian',       'Morgoș',      'lmorgos@uoradea.ro',          '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    (38, 'Laviniu Flavius',     'Țepelea',     'ltepelea@uoradea.ro',         '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    (39, 'Adrian Traian',       'Burca',       'aburca@uoradea.ro',           '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    (40, 'Răzvan Daniel',       'Albu',        'ralbu@uoradea.ro',            '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),

    -- Departamentul ISAM
    (41, 'Helga Maria',         'Silaghi',     'hsilaghi@uoradea.ro',         '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    (42, 'Gabriela',            'Tonț',        'gtont@uoradea.ro',            '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    (43, 'Tiberiu',             'Barabaș',     'tbarabas@uoradea.ro',         '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    (44, 'Eugen Ioan',          'Gergely',     'egergely@uoradea.ro',         '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    (45, 'Dragoș Cristian',     'Spoială',     'dspoiala@uoradea.ro',         '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    (46, 'Viorica',             'Spoială',     'vspoiala@uoradea.ro',         '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    (47, 'Sanda',               'Dale',        'sdale@uoradea.ro',            '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    (48, 'Laura',               'Coroiu',      'lcoroiu@uoradea.ro',          '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    (49, 'Anca',                'Păcală',      'apacala@uoradea.ro',          '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    (50, 'Marius',              'Romocea',     'mromocea@uoradea.ro',         '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    (51, 'Claudiu Raul',        'Costea',      'ccostea@uoradea.ro',          '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    (52, 'Zoltan',              'Kovendi',     'zkovendi@uoradea.ro',         '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    (53, 'Diana Monica',        'Mesaros',     'dsas@uoradea.ro',             '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),

    -- Departamentul IE
    (54, 'Livia',               'Bandici',     'lbandici@uoradea.ro',         '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    (55, 'Adriana',             'Grava',       'agrava@uoradea.ro',           '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    (56, 'Carmen Otilia',       'Molnar',      'cmolnar@uoradea.ro',          '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    (57, 'Sorin Dorel',         'Pașca',       'spasca@uoradea.ro',           '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    (58, 'Monica',              'Popa',        'mpopa@uoradea.ro',            '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    (59, 'Vasile Darie',        'Șoproni',     'vsoproni@uoradea.ro',         '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    (60, 'Mircea Nicolae',      'Arion',       'marion@uoradea.ro',           '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    (61, 'Teofil Ovidiu',       'Gal',         'tgal@uoradea.ro',             '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    (62, 'Cornelia Mihaela',    'Novac',       'mnovac@uoradea.ro',           '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    (63, 'Claudia Olimpia',     'Stașac',      'cstasac@uoradea.ro',          '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    (64, 'Radu Dimitrie',       'Sebeșan',     'rsebesan@uoradea.ro',         '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    (65, 'Mircea Danuț',        'Pantea',      'mpantea@uoradea.ro',          '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    (66, 'Marius',              'Codrean',     'mcodrean@uoradea.ro',         '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR');

-- Reset the sequence to continue after our explicitly inserted IDs
SELECT setval('users_id_seq', (SELECT COALESCE(MAX(id), 0) FROM users));

-- =============================================
-- Professors (linked to user accounts)
-- =============================================
INSERT INTO professors (first_name, last_name, department, user_id) VALUES
    -- Departamentul CTI
    ('Daniela Elena',       'Popescu',     'Calculatoare și Tehnologia Informației',  (SELECT id FROM users WHERE email = 'depopescu@uoradea.ro')),
    ('Doina',               'Zmaranda',    'Calculatoare și Tehnologia Informației',  (SELECT id FROM users WHERE email = 'dzmaranda@uoradea.ro')),
    ('Robert Ștefan',       'Győrödi',     'Calculatoare și Tehnologia Informației',  (SELECT id FROM users WHERE email = 'rgyorodi@uoradea.ro')),
    ('Cornelia Aurora',     'Győrödi',     'Calculatoare și Tehnologia Informației',  (SELECT id FROM users WHERE email = 'cgyorodi@uoradea.ro')),
    ('Alexandrina Mirela',  'Pater',       'Calculatoare și Tehnologia Informației',  (SELECT id FROM users WHERE email = 'mpater@uoradea.ro')),
    ('Gianina Adela',       'Gabor',       'Calculatoare și Tehnologia Informației',  (SELECT id FROM users WHERE email = 'gianina@uoradea.ro')),
    ('Ovidiu Constantin',   'Novac',       'Calculatoare și Tehnologia Informației',  (SELECT id FROM users WHERE email = 'ovnovac@uoradea.ro')),
    ('Elisa Valentina',     'Moisi',       'Calculatoare și Tehnologia Informației',  (SELECT id FROM users WHERE email = 'emoisi@uoradea.ro')),
    ('Florin',              'Vancea',      'Calculatoare și Tehnologia Informației',  (SELECT id FROM users WHERE email = 'fvancea@uoradea.ro')),
    ('Otto',                'Poszet',      'Calculatoare și Tehnologia Informației',  (SELECT id FROM users WHERE email = 'poszet@uoradea.ro')),
    ('Rodica',              'Țirtea',      'Calculatoare și Tehnologia Informației',  (SELECT id FROM users WHERE email = 'rtirtea@uoradea.ro')),
    ('George Dominic',      'Pecherle',    'Calculatoare și Tehnologia Informației',  (SELECT id FROM users WHERE email = 'gpecherle@uoradea.ro')),
    ('Octavia Maria',       'Bolojan',     'Calculatoare și Tehnologia Informației',  (SELECT id FROM users WHERE email = 'obolojan@uoradea.ro')),
    ('Simina Maria',        'Coman',       'Calculatoare și Tehnologia Informației',  (SELECT id FROM users WHERE email = 'scoman@uoradea.ro')),
    ('Mircea Petru',        'Ursu',        'Calculatoare și Tehnologia Informației',  (SELECT id FROM users WHERE email = 'mpursu@uoradea.ro')),
    ('Mircea Ioan',         'Mihăilă',     'Calculatoare și Tehnologia Informației',  (SELECT id FROM users WHERE email = 'mircea.mihaila@uoradea.ro')),
    ('Felicia Mirabela',    'Costea',      'Calculatoare și Tehnologia Informației',  (SELECT id FROM users WHERE email = 'mira_costea@uoradea.ro')),
    ('Adriana-Maria',       'Cuc',         'Calculatoare și Tehnologia Informației',  (SELECT id FROM users WHERE email = 'acuc@uoradea.ro')),
    ('Hannelore',           'Sebestyen',   'Calculatoare și Tehnologia Informației',  (SELECT id FROM users WHERE email = 'hsebestyen@uoradea.ro')),
    ('Cătălin-Daniel',      'Morar',       'Calculatoare și Tehnologia Informației',  (SELECT id FROM users WHERE email = 'cdmorar@uoradea.ro')),

    -- Departamentul ETc
    ('Cornelia Emilia',     'Gordan',      'Electronică și Telecomunicații',           (SELECT id FROM users WHERE email = 'cgordan@uoradea.ro')),
    ('Nistor Daniel',       'Trip',        'Electronică și Telecomunicații',           (SELECT id FROM users WHERE email = 'dtrip@uoradea.ro')),
    ('Cristian',            'Grava',       'Electronică și Telecomunicații',           (SELECT id FROM users WHERE email = 'cgrava@uoradea.ro')),
    ('Sorin',               'Curilă',      'Electronică și Telecomunicații',           (SELECT id FROM users WHERE email = 'scurila@uoradea.ro')),
    ('Ioan Marius',         'Buciu',       'Electronică și Telecomunicații',           (SELECT id FROM users WHERE email = 'ibuciu@uoradea.ro')),
    ('Ovidiu Marius',       'Neamțu',      'Electronică și Telecomunicații',           (SELECT id FROM users WHERE email = 'oneamtu@uoradea.ro')),
    ('Simona Cristina',     'Castrase',    'Electronică și Telecomunicații',           (SELECT id FROM users WHERE email = 'scastrase@uoradea.ro')),
    ('Liviu',               'Moldovan',    'Electronică și Telecomunicații',           (SELECT id FROM users WHERE email = 'liviu@uoradea.ro')),
    ('Marin Titus',         'Tomșe',       'Electronică și Telecomunicații',           (SELECT id FROM users WHERE email = 'mtomse@uoradea.ro')),
    ('Adrian Lazăr',        'Șchiop',      'Electronică și Telecomunicații',           (SELECT id FROM users WHERE email = 'aschiop@uoradea.ro')),
    ('Ioan',                'Gavriluț',    'Electronică și Telecomunicații',           (SELECT id FROM users WHERE email = 'gavrilut@uoradea.ro')),
    ('Sorin Vasile',        'Popa',        'Electronică și Telecomunicații',           (SELECT id FROM users WHERE email = 'sopopa@uoradea.ro')),
    ('Romulus',             'Reiz',        'Electronică și Telecomunicații',           (SELECT id FROM users WHERE email = 'rreiz@uoradea.ro')),
    ('Florin Lucian',       'Morgoș',      'Electronică și Telecomunicații',           (SELECT id FROM users WHERE email = 'lmorgos@uoradea.ro')),
    ('Laviniu Flavius',     'Țepelea',     'Electronică și Telecomunicații',           (SELECT id FROM users WHERE email = 'ltepelea@uoradea.ro')),
    ('Adrian Traian',       'Burca',       'Electronică și Telecomunicații',           (SELECT id FROM users WHERE email = 'aburca@uoradea.ro')),
    ('Răzvan Daniel',       'Albu',        'Electronică și Telecomunicații',           (SELECT id FROM users WHERE email = 'ralbu@uoradea.ro')),

    -- Departamentul ISAM
    ('Helga Maria',         'Silaghi',     'Ingineria Sistemelor Automate și Management', (SELECT id FROM users WHERE email = 'hsilaghi@uoradea.ro')),
    ('Gabriela',            'Tonț',        'Ingineria Sistemelor Automate și Management', (SELECT id FROM users WHERE email = 'gtont@uoradea.ro')),
    ('Tiberiu',             'Barabaș',     'Ingineria Sistemelor Automate și Management', (SELECT id FROM users WHERE email = 'tbarabas@uoradea.ro')),
    ('Eugen Ioan',          'Gergely',     'Ingineria Sistemelor Automate și Management', (SELECT id FROM users WHERE email = 'egergely@uoradea.ro')),
    ('Dragoș Cristian',     'Spoială',     'Ingineria Sistemelor Automate și Management', (SELECT id FROM users WHERE email = 'dspoiala@uoradea.ro')),
    ('Viorica',             'Spoială',     'Ingineria Sistemelor Automate și Management', (SELECT id FROM users WHERE email = 'vspoiala@uoradea.ro')),
    ('Sanda',               'Dale',        'Ingineria Sistemelor Automate și Management', (SELECT id FROM users WHERE email = 'sdale@uoradea.ro')),
    ('Laura',               'Coroiu',      'Ingineria Sistemelor Automate și Management', (SELECT id FROM users WHERE email = 'lcoroiu@uoradea.ro')),
    ('Anca',                'Păcală',      'Ingineria Sistemelor Automate și Management', (SELECT id FROM users WHERE email = 'apacala@uoradea.ro')),
    ('Marius',              'Romocea',     'Ingineria Sistemelor Automate și Management', (SELECT id FROM users WHERE email = 'mromocea@uoradea.ro')),
    ('Claudiu Raul',        'Costea',      'Ingineria Sistemelor Automate și Management', (SELECT id FROM users WHERE email = 'ccostea@uoradea.ro')),
    ('Zoltan',              'Kovendi',     'Ingineria Sistemelor Automate și Management', (SELECT id FROM users WHERE email = 'zkovendi@uoradea.ro')),
    ('Diana Monica',        'Mesaros',     'Ingineria Sistemelor Automate și Management', (SELECT id FROM users WHERE email = 'dsas@uoradea.ro')),

    -- Departamentul IE
    ('Livia',               'Bandici',     'Inginerie Electrică',                     (SELECT id FROM users WHERE email = 'lbandici@uoradea.ro')),
    ('Adriana',             'Grava',       'Inginerie Electrică',                     (SELECT id FROM users WHERE email = 'agrava@uoradea.ro')),
    ('Carmen Otilia',       'Molnar',      'Inginerie Electrică',                     (SELECT id FROM users WHERE email = 'cmolnar@uoradea.ro')),
    ('Sorin Dorel',         'Pașca',       'Inginerie Electrică',                     (SELECT id FROM users WHERE email = 'spasca@uoradea.ro')),
    ('Monica',              'Popa',        'Inginerie Electrică',                     (SELECT id FROM users WHERE email = 'mpopa@uoradea.ro')),
    ('Vasile Darie',        'Șoproni',     'Inginerie Electrică',                     (SELECT id FROM users WHERE email = 'vsoproni@uoradea.ro')),
    ('Mircea Nicolae',      'Arion',       'Inginerie Electrică',                     (SELECT id FROM users WHERE email = 'marion@uoradea.ro')),
    ('Teofil Ovidiu',       'Gal',         'Inginerie Electrică',                     (SELECT id FROM users WHERE email = 'tgal@uoradea.ro')),
    ('Cornelia Mihaela',    'Novac',       'Inginerie Electrică',                     (SELECT id FROM users WHERE email = 'mnovac@uoradea.ro')),
    ('Claudia Olimpia',     'Stașac',      'Inginerie Electrică',                     (SELECT id FROM users WHERE email = 'cstasac@uoradea.ro')),
    ('Radu Dimitrie',       'Sebeșan',     'Inginerie Electrică',                     (SELECT id FROM users WHERE email = 'rsebesan@uoradea.ro')),
    ('Mircea Danuț',        'Pantea',      'Inginerie Electrică',                     (SELECT id FROM users WHERE email = 'mpantea@uoradea.ro')),
    ('Marius',              'Codrean',     'Inginerie Electrică',                     (SELECT id FROM users WHERE email = 'mcodrean@uoradea.ro'));


