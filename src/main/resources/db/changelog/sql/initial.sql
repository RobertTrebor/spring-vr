INSERT INTO `cemetery` (`id`, `name`, `city`, `country`, `zipcode`, `street`)
VALUES
(1,'Dorotheenstädtischer-Friedrichswerderscher Friedh','Berlin','DE','10115','Chausseestr. 126'),
(2,'Friedhof Schöneberg III','Berlin','DE','12161','Stubenrauchstraße 43-45');

INSERT INTO `grave` (`id`, `firstname`, `lastname`, `sex`, `datebirth`, `datedeath`, `cemetery_id`, `grave_loc`, `latitude`, `longitude`, `vita_path`, `tombstone_path`)
VALUES
(1,'Anna','Seghers','f','1900-11-19','1983-06-01',1,'NULL','52.52805','13.38416','http://www.lengsfeld.de/cimitery/vitae/Anna_Seghers.html','c_id1_g_id1_1386244170.jpg'),
(2,'Arnold','Zweig','m','1887-11-10','1968-11-26',1,'NULL','52.5275','13.38388','','c_id1_g_id2_1386244904.jpg'),
(3,'Marlene','Dietrich','f','1901-12-27','1992-05-06',2,'NULL','52.47666','13.32194','NULL',''),
(33,'Carl Friedrich','Fichte','m','1781-03-13','1841-10-09',1,'NULL','9.99','9.99','http://www.lengsfeld.de/cimitery/vitae/Fichte.html','c_id1_g_id33_1386243013.jpg'),
(32,'Bertolt','Brecht','f','1898-02-10','1956-08-14',1,'NULL','9.99','9.99','http://www.lengsfeld.de/cimitery/vitae/Brecht.html','c_id1_g_id32_1386242302.jpg'),
(31,'Johann Friedrich August','Borsig','m','1804-06-23','1854-07-06',1,'NULL','9.99','9.99','http://www.lengsfeld.de/cimitery/vitae/Borsig.html','c_id1_g_id31_1386241940.jpg'),
(30,'Dietrich','Bonhoeffer','m','1906-02-04','1945-04-09',1,'NULL','9.99','9.99','http://www.lengsfeld.de/cimitery/vitae/Bonhoeffer.html','c_id1_g_id30_1386241387.jpg'),
(29,'Peter Christoph Wilhelm','Beuth','f','1781-12-28','1853-08-27',1,'NULL','9.99','9.99','http://www.lengsfeld.de/cimitery/vitae/Beuth.html','c_id1_g_id29_1386240705.jpg'),
(34,'Christa','Wolf','f','1929-03-18','2011-12-01',1,'NULL','52.52778','13.384167','http://www.lengsfeld.de/cimitery/vitae/','c_id1_g_id34_1386244881.jpg'),
(35,'Fritz','Teufel','m','1943-06-17','2010-07-06',1,'NULL','52.52778','13.384444','http://www.lengsfeld.de/cimitery/vitae/','c_id1_g_id35_1386244843.jpg'),
(36,'Helmut','Newton','m',NULL,NULL,2,'NULL','52.476665','13.321944','http://www.lengsfeld.de/cimitery/vitae/',''),
(37,'Christian Daniel','Rauch','m','1777-01-02','1857-12-03',1,'NULL','52.526943','13.383611','http://www.lengsfeld.de/cimitery/vitae/','c_id1_g_id37_1386243203.jpg'),
(80,'Heinrich','Mann','m','1871-03-27','1950-03-11',1,'NULL','52.52833','13.383611','http://www.lengsfeld.de/cimitery/vitae/','c_id1_g_id80_1386321929.jpg'),
(94,'Johannes','Rau','m','1931-01-16','2006-01-27',1,'null','52.528057','13.383333','http://www.lengsfeld.de/cimitery/vitae/','');
