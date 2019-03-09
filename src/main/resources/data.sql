
--DROP TABLE IF EXISTS stand_alone ;

CREATE TABLE IF NOT EXISTS stand_alone (
  id_stand_alone INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 100, INCREMENT BY 1) PRIMARY KEY,
  name VARCHAR(255) NOT NULL UNIQUE,
  pattern VARCHAR(255) NULL,
  test_text VARCHAR(255) NULL,
  description VARCHAR(255) NULL
);


--DROP TABLE IF EXISTS message_pattern ;

CREATE TABLE IF NOT EXISTS message_pattern (
  id_message_pattern INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 100, INCREMENT BY 1) PRIMARY KEY,
  name VARCHAR(255) NOT NULL UNIQUE,
  pattern VARCHAR(1000000) NULL,
  test_text VARCHAR(1000000) NULL,
  description VARCHAR(255) NULL
);

ALTER TABLE message_pattern ALTER COLUMN pattern SET DATA TYPE VARCHAR(1000000);
ALTER TABLE message_pattern ALTER COLUMN test_text SET DATA TYPE VARCHAR(1000000);

INSERT INTO STAND_ALONE(id_stand_alone,name,pattern,test_text,description) SELECT * FROM (VALUES(100,'AIRLINE','[A-Z]{3}|[A-Z0-9]{2}','MAS','Airline IATA or ICAO'))  WHERE NOT EXISTS (SELECT * FROM STAND_ALONE  WHERE name = 'AIRLINE');
INSERT INTO STAND_ALONE(id_stand_alone,name,pattern,test_text,description) SELECT * FROM (VALUES(101,'SEATNUM','\d+[A-Z]{1}','12A','Seat number'))  WHERE NOT EXISTS (SELECT * FROM STAND_ALONE  WHERE name = 'SEATNUM');
INSERT INTO STAND_ALONE(id_stand_alone,name,pattern,test_text,description) SELECT * FROM (VALUES(102,'PAXNAME','[A-Z]{1,50}(/[A-Z]{1,50})?','RAMESH/KRISHNAN','Passenger Surname/givenname'))  WHERE NOT EXISTS (SELECT * FROM STAND_ALONE  WHERE name = 'PAXNAME');
INSERT INTO STAND_ALONE(id_stand_alone,name,pattern,test_text,description) SELECT * FROM (VALUES(103,'AIRPORT','[A-Z]{3}','DEL','Airport code'))  WHERE NOT EXISTS (SELECT * FROM STAND_ALONE  WHERE name = 'AIRPORT');
INSERT INTO STAND_ALONE(id_stand_alone,name,pattern,test_text,description) SELECT * FROM (VALUES(105,'MSG','[A-Z]*','PSM','Message Type'))  WHERE NOT EXISTS (SELECT * FROM STAND_ALONE  WHERE name = 'MSG');
INSERT INTO STAND_ALONE(id_stand_alone,name,pattern,test_text,description) SELECT * FROM (VALUES(106,'FLIGHTNUMBER','[0-9]{2,4}','1234','Flight Number'))  WHERE NOT EXISTS (SELECT * FROM STAND_ALONE  WHERE name = 'FLIGHTNUMBER');
INSERT INTO STAND_ALONE(id_stand_alone,name,pattern,test_text,description) SELECT * FROM (VALUES(107,'SUFFIX','[A-Z]{0,1}','A','Flight Suffix'))  WHERE NOT EXISTS (SELECT * FROM STAND_ALONE  WHERE name = 'SUFFIX');
INSERT INTO STAND_ALONE(id_stand_alone,name,pattern,test_text,description) SELECT * FROM (VALUES(108,'FLIGHTIDENTIFIER','%{AIRLINE:FLIGHTAIRLINE}%{FLIGHTNUMBER}(?:%{SUFFIX})','MH124A','FlightIdentifier'))  WHERE NOT EXISTS (SELECT * FROM STAND_ALONE  WHERE name = 'FLIGHTIDENTIFIER');
INSERT INTO STAND_ALONE(id_stand_alone,name,pattern,test_text,description) SELECT * FROM (VALUES(109,'ENDMSG','END%{MSG}','ENDPSM','End of Message'))  WHERE NOT EXISTS (SELECT * FROM STAND_ALONE  WHERE name = 'ENDMSG');
INSERT INTO STAND_ALONE(id_stand_alone,name,pattern,test_text,description) SELECT * FROM (VALUES(110,'MONTHCAPS','JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC','JAN','Months'))  WHERE NOT EXISTS (SELECT * FROM STAND_ALONE  WHERE name = 'MONTHCAPS');
INSERT INTO STAND_ALONE(id_stand_alone,name,pattern,test_text,description) SELECT * FROM (VALUES(111,'DATEMON','%{MONTHDAY}%{MONTHCAPS}','03JAN','Date and Month'))  WHERE NOT EXISTS (SELECT * FROM STAND_ALONE  WHERE name = 'DATEMON')
INSERT INTO STAND_ALONE(id_stand_alone,name,pattern,test_text,description) SELECT * FROM (VALUES(112,'COUNT2','\d{2}','23','Count of 2'))  WHERE NOT EXISTS (SELECT * FROM STAND_ALONE  WHERE name = 'COUNT2');
INSERT INTO STAND_ALONE(id_stand_alone,name,pattern,test_text,description) SELECT * FROM (VALUES(113,'COUNT3','\d{3}','234','Count of 3'))  WHERE NOT EXISTS (SELECT * FROM STAND_ALONE  WHERE name = 'COUNT3');
INSERT INTO STAND_ALONE(id_stand_alone,name,pattern,test_text,description) SELECT * FROM (VALUES(114,'CABIN','[A-Z]{1}','Y','Cabin code'))  WHERE NOT EXISTS (SELECT * FROM STAND_ALONE  WHERE name = 'CABIN');
INSERT INTO STAND_ALONE(id_stand_alone,name,pattern,test_text,description) SELECT * FROM (VALUES(116,'NIL','NIL','NIL','NIL identifier'))  WHERE NOT EXISTS (SELECT * FROM STAND_ALONE  WHERE name = 'NIL');
INSERT INTO STAND_ALONE(id_stand_alone,name,pattern,test_text,description) SELECT * FROM (VALUES(117,'CLS','%{CABIN:cabin}CLASS','FCLASS','Class in PIL Message'))  WHERE NOT EXISTS (SELECT * FROM STAND_ALONE  WHERE name = 'CLS');
INSERT INTO STAND_ALONE(id_stand_alone,name,pattern,test_text,description) SELECT * FROM (VALUES(118,'ID','\d+','24234243242','Numberic Number'))  WHERE NOT EXISTS (SELECT * FROM STAND_ALONE  WHERE name = 'ID');
INSERT INTO STAND_ALONE(id_stand_alone,name,pattern,test_text,description) SELECT * FROM (VALUES(119,'FQTVSSR','.R\/(FQTV|FQTU|FQTS|FQTR) %{AIRLINE} %{NUMBER:FQTVNUMBER}','.R/FQTV IB 123766565644 .R/FQTV IB 123766565644','Format for FQTV SSR'))  WHERE NOT EXISTS (SELECT * FROM STAND_ALONE  WHERE name = 'FQTVSSR');
INSERT INTO STAND_ALONE(id_stand_alone,name,pattern,test_text,description) SELECT * FROM (VALUES(120,'FREETEXT','[\S* ]+','VGML SPEAKS GERMAN ONLY','Match All Free Text'))  WHERE NOT EXISTS (SELECT * FROM STAND_ALONE  WHERE name = 'FREETEXT');
INSERT INTO STAND_ALONE(id_stand_alone,name,pattern,test_text,description) SELECT * FROM (VALUES(121,'STDTIME','STD/\d{4}','STD/1622','Standard time'))  WHERE NOT EXISTS (SELECT * FROM STAND_ALONE  WHERE name = 'STDTIME');
INSERT INTO STAND_ALONE(id_stand_alone,name,pattern,test_text,description) SELECT * FROM (VALUES(122,'RL','.L/[A-Z0-9]+','.L/4567AS','RecordLocator'))  WHERE NOT EXISTS (SELECT * FROM STAND_ALONE  WHERE name = 'RL');
INSERT INTO STAND_ALONE(id_stand_alone,name,pattern,test_text,description) SELECT * FROM (VALUES(124,'HYPHEN','-','-','HYPHEN'))  WHERE NOT EXISTS (SELECT * FROM STAND_ALONE  WHERE name = 'HYPHEN');
INSERT INTO STAND_ALONE(id_stand_alone,name,pattern,test_text,description) SELECT * FROM (VALUES(125,'PAXCOUNT','\d+','10','Passenger count in a number'))  WHERE NOT EXISTS (SELECT * FROM STAND_ALONE  WHERE name = 'PAXCOUNT');
INSERT INTO MESSAGE_PATTERN(id_message_pattern,name,pattern,test_text,description) SELECT * FROM (VALUES(100,'PIL',U&'%{MSG}\000a%{FLIGHTIDENTIFIER}/%{DATEMON} %{AIRPORT}\000a[REPEAT::]\000a%{CLS}\000a[REPEAT::]\000a%{NIL}|%{SEATNUM} ?(%{AIRPORT} %{PAXNAME})? ?%{FREETEXT}?\000a[::REPEAT]\000a[::REPEAT]\000a%{ENDMSG}',U&'PIL\000aNX890/27MAY DFW\000aFCLASS\000a01A DEN GREEN/LEROYMR\000a01B JMS JOHNSON/TERRYMRS VGML\000a01C JMS JOHNSON/JACKSONMR\000a01D\000a02A\000a02B\000a02C DEN OHARA/KENDRAMS VGML SPEAKS GERMAN ONLY\000a02D DEN FULTON/ELEANORMS SFML\000aCCLASS\000aNIL\000aYCLASS\000a16C DEN BACCOUCHE/LISAMS WCHR AND NECK BRACE\000a18J DEN SNYDER/THOMASMR MEDA USING CRUTCHES\000a20A DEN ULMER/KARLMR EXST COURIER\000aENDPIL','Message Pattern for PIL') )  WHERE NOT EXISTS (SELECT MESSAGE_PATTERN.name FROM MESSAGE_PATTERN  WHERE name = 'PIL');
INSERT INTO MESSAGE_PATTERN (id_message_pattern,name,pattern,test_text,description)SELECT * FROM (VALUES(101,'FTL',U&'%{MSG}\000a%{FLIGHTIDENTIFIER}/%{DATEMON} %{AIRPORT} PART1\000a%{STDTIME}\000a[REPEAT::]\000a%{HYPHEN}%{AIRPORT}%{COUNT3}%{CABIN}\000a[REPEAT::]\000a%{PAXCOUNT}%{PAXNAME} %{RL}/%{AIRLINE:ORG} %{FQTVSSR} ?%{FQTVSSR}?-?%{FREETEXT}?\000a[::REPEAT]\000a[::REPEAT]\000a%{ENDMSG}',U&'FTL\000aIB777/15JUL MAD PART1\000aSTD/1622\000a-JFK004F\000a1ANDERS/EMILYMRS .L/4567AS/IB .R/FQTR IB 54265465644-UPG COUPON\000a1BENNETT/TOMMR .L/8762W2/IB .R/FQTV IB 12377579337\000a1BENNETT/SALLYMRS .L/8762W2/IB .R/FQTV IB 456745677456-R\000a1SMITH/AMY .L/H1W234/IB .R/FQTR IB 1234567812348-50 PC COUPON\000a-JFK007Y\000a1CARTIER/LEEMR .L/H88885/IB .R/FQTV IB 1236534856825\000a1JOHNSON/BRYANMR .L/H76767/IB .R/FQTV IB 1236546789234\000a1KELSO/JANICEMS .L/123456/IB .R/FQTV IB 123655656\000a1KELSO/TOMMR .L/123456/IB .R/FQTV IB 767676645\000a1MAJORS/LANCEMR .L/H77777/IB .R/FQTV IB 1235745675874\000a1MARTINEZ/JUANMR .L/876543/IB .R/FQTV IB 1238383743921\000a1LIM/SUE .L/666XYZ/IB .R/FQTV IB 123766565644 .R/FQTV AA 88877766\000a-MIA001Y\000a1TAYLOR/LAWRENCEMR .L/UV8692/IB .R/FQTV IB 1234675874-F\000aENDFTL','FTL Message Pattern')) WHERE NOT EXISTS (SELECT MESSAGE_PATTERN.name FROM MESSAGE_PATTERN  WHERE name = 'FTL');
