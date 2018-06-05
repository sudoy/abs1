CREATE DATABASE abs1;

CREATE TABLE abs1(
	id INT PRIMARY KEY AUTO_INCREMENT,
	date DATE NOT NULL, 
	category VARCHAR(100) NOT NULL,
	note VARCHAR(100),
	price int NOT NULL
);



INSERT INTO abs1(date, category, note, price)
VALUES('2018-05-29', '日用品', 'ティッシュペーパー、歯磨き粉など', -740);
INSERT INTO abs1(date, category, note, price)
VALUES('2018-05-29', '交際費', '', -800);
INSERT INTO abs1(date, category, note, price)
VALUES('2018-05-30', 'アルバイト代', '', 120000);
INSERT INTO abs1(date, category, note, price)
VALUES('2018-05-30', '宝くじ', '', 3000);

