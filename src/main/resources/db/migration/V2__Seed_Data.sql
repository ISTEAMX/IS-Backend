-- V2__Seed_Data.sql
-- Seed data for University of Oradea - Faculty of Electrical Engineering and Information Technology

-- =============================================
-- Users (password is BCrypt hash of "password123")
-- =============================================
INSERT INTO users (first_name, last_name, email, password, role) VALUES
    -- Admins
    ('Ramona',   'Iuga',       'riuga@uoradea.ro',           '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'ADMIN'),
    ('Anca',     'Bondar',     'ipoinar@uoradea.ro',         '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'ADMIN'),
    ('Daniela',  'Pușcaș',     'dpuscas@uoradea.ro',         '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'ADMIN'),

    -- Departamentul CTI
    ('Daniela Elena',       'Popescu',     'depopescu@uoradea.ro',        '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    ('Doina',               'Zmaranda',    'dzmaranda@uoradea.ro',        '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    ('Robert Ștefan',       'Győrödi',     'rgyorodi@uoradea.ro',         '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    ('Cornelia Aurora',     'Győrödi',     'cgyorodi@uoradea.ro',         '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    ('Alexandrina Mirela',  'Pater',       'mpater@uoradea.ro',           '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    ('Gianina Adela',       'Gabor',       'gianina@uoradea.ro',          '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    ('Ovidiu Constantin',   'Novac',       'ovnovac@uoradea.ro',          '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    ('Elisa Valentina',     'Moisi',       'emoisi@uoradea.ro',           '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    ('Florin',              'Vancea',      'fvancea@uoradea.ro',          '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    ('Otto',                'Poszet',      'poszet@uoradea.ro',           '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    ('Rodica',              'Țirtea',      'rtirtea@uoradea.ro',          '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    ('George Dominic',      'Pecherle',    'gpecherle@uoradea.ro',        '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    ('Octavia Maria',       'Bolojan',     'obolojan@uoradea.ro',         '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    ('Simina Maria',        'Coman',       'scoman@uoradea.ro',           '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    ('Mircea Petru',        'Ursu',        'mpursu@uoradea.ro',           '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    ('Mircea Ioan',         'Mihăilă',     'mircea.mihaila@uoradea.ro',   '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    ('Felicia Mirabela',    'Costea',      'mira_costea@uoradea.ro',      '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    ('Adriana-Maria',       'Cuc',         'acuc@uoradea.ro',             '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    ('Hannelore',           'Sebestyen',   'hsebestyen@uoradea.ro',       '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    ('Cătălin-Daniel',      'Morar',       'cdmorar@uoradea.ro',          '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),

    -- Departamentul ETc
    ('Cornelia Emilia',     'Gordan',      'cgordan@uoradea.ro',          '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    ('Nistor Daniel',       'Trip',        'dtrip@uoradea.ro',            '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    ('Cristian',            'Grava',       'cgrava@uoradea.ro',           '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    ('Sorin',               'Curilă',      'scurila@uoradea.ro',          '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    ('Ioan Marius',         'Buciu',       'ibuciu@uoradea.ro',           '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    ('Ovidiu Marius',       'Neamțu',      'oneamtu@uoradea.ro',          '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    ('Simona Cristina',     'Castrase',    'scastrase@uoradea.ro',        '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    ('Liviu',               'Moldovan',    'liviu@uoradea.ro',            '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    ('Marin Titus',         'Tomșe',       'mtomse@uoradea.ro',           '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    ('Adrian Lazăr',        'Șchiop',      'aschiop@uoradea.ro',          '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    ('Ioan',                'Gavriluț',    'gavrilut@uoradea.ro',         '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    ('Sorin Vasile',        'Popa',        'sopopa@uoradea.ro',           '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    ('Romulus',             'Reiz',        'rreiz@uoradea.ro',            '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    ('Florin Lucian',       'Morgoș',      'lmorgos@uoradea.ro',          '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    ('Laviniu Flavius',     'Țepelea',     'ltepelea@uoradea.ro',         '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    ('Adrian Traian',       'Burca',       'aburca@uoradea.ro',           '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    ('Răzvan Daniel',       'Albu',        'ralbu@uoradea.ro',            '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),

    -- Departamentul ISAM
    ('Helga Maria',         'Silaghi',     'hsilaghi@uoradea.ro',         '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    ('Gabriela',            'Tonț',        'gtont@uoradea.ro',            '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    ('Tiberiu',             'Barabaș',     'tbarabas@uoradea.ro',         '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    ('Eugen Ioan',          'Gergely',     'egergely@uoradea.ro',         '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    ('Dragoș Cristian',     'Spoială',     'dspoiala@uoradea.ro',         '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    ('Viorica',             'Spoială',     'vspoiala@uoradea.ro',         '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    ('Sanda',               'Dale',        'sdale@uoradea.ro',            '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    ('Laura',               'Coroiu',      'lcoroiu@uoradea.ro',          '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    ('Anca',                'Păcală',      'apacala@uoradea.ro',          '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    ('Marius',              'Romocea',     'mromocea@uoradea.ro',         '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    ('Claudiu Raul',        'Costea',      'ccostea@uoradea.ro',          '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    ('Zoltan',              'Kovendi',     'zkovendi@uoradea.ro',         '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    ('Diana Monica',        'Mesaros',     'dsas@uoradea.ro',             '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),

    -- Departamentul IE
    ('Livia',               'Bandici',     'lbandici@uoradea.ro',         '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    ('Adriana',             'Grava',       'agrava@uoradea.ro',           '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    ('Carmen Otilia',       'Molnar',      'cmolnar@uoradea.ro',          '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    ('Sorin Dorel',         'Pașca',       'spasca@uoradea.ro',           '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    ('Monica',              'Popa',        'mpopa@uoradea.ro',            '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    ('Vasile Darie',        'Șoproni',     'vsoproni@uoradea.ro',         '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    ('Mircea Nicolae',      'Arion',       'marion@uoradea.ro',           '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    ('Teofil Ovidiu',       'Gal',         'tgal@uoradea.ro',             '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    ('Cornelia Mihaela',    'Novac',       'mnovac@uoradea.ro',           '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    ('Claudia Olimpia',     'Stașac',      'cstasac@uoradea.ro',          '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    ('Radu Dimitrie',       'Sebeșan',     'rsebesan@uoradea.ro',         '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    ('Mircea Danuț',        'Pantea',      'mpantea@uoradea.ro',          '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR'),
    ('Marius',              'Codrean',     'mcodrean@uoradea.ro',         '$2a$10$Psw8eS1Ssv75VE020Mchvu7SNeVPCAw2kbTl5geYrZHM9ixRJ6kc6', 'PROFESSOR');

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




