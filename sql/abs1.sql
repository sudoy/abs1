CREATE DATABASE abs1;

CREATE TABLE abs1(
	id INT PRIMARY KEY AUTO_INCREMENT,
	date DATE NOT NULL,
	category INT NOT NULL,
	note VARCHAR(100),
	price int NOT NULL
);


INSERT INTO abs1(date, category, note, price)
VALUES('2018-05-29', 1, 'ティッシュペーパー、歯磨き粉など', -740);
INSERT INTO abs1(date, category, note, price)
VALUES('2018-05-29', 2, '', -800);
INSERT INTO abs1(date, category, note, price)
VALUES('2018-05-30', 4, '', 120000);
INSERT INTO abs1(date, category, note, price)
VALUES('2018-05-30', 5, 'ラッキー', 3000);

INSERT INTO abs1(date, category, note ,price)
VALUES('2018-06-1', 1, '', 3000);
INSERT INTO abs1(date, category, note ,price)
VALUES('2018-06-1', 2, '', 5000);
INSERT INTO abs1(date, category, note ,price)
VALUES('2018-06-2', 3, 'テスト', 3000);


CREATE TABLE list(
	category_id INT PRIMARY KEY AUTO_INCREMENT,
	category_data VARCHAR(100)
);

INSERT INTO list(category_data)
VALUES('食費');
INSERT INTO list(category_data)
VALUES('日用品');
INSERT INTO list(category_data)
VALUES('交際費');
INSERT INTO list(category_data)
VALUES('アルバイト代');
INSERT INTO list(category_data)
VALUES('宝くじ');

