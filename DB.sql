#DB 생성
DROP DATABASE IF EXISTS PJ_KSH;
CREATE DATABASE PJ_KSH;
USE PJ_KSH;
# 게시물 테이블 생성
CREATE TABLE article(
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    title CHAR(100) NOT NULL,
    `body` TEXT NOT NULL
);

# 게시물 테스트데이터 생성
INSERT INTO article 
SET regDate = NOW(),
updateDate = NOW(),
title = '제목 1',
`body` = '내용 1';

INSERT INTO article 
SET regDate = NOW(),
updateDate = NOW(),
title = '제목 2',
`body` = '내용 2';

INSERT INTO article 
SET regDate = NOW(),
updateDate = NOW(),
title = '제목 3',
`body` = '내용 3';

# 회원 테이블 생성
CREATE TABLE `member`(
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    loginId CHAR(20) NOT NULL,
    loginPw CHAR(255) NOT NULL,
    `authLevel` SMALLINT(2) UNSIGNED DEFAULT 3 COMMENT '권한 레벨 (3=일반,7=관리자)',
    `name` CHAR(20) NOT NULL,
    nickname CHAR(20) NOT NULL,
    cellphoneNum CHAR(20) NOT NULL,
    email CHAR(50) NOT NULL,
    delStatus TINYINT(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '탈퇴 여부 (0=탈퇴 전, 1=탈퇴 후)',
    delDate DATETIME COMMENT '탈퇴 날짜'
);

# 회원 테스트데이터 생성 (관리자)
INSERT INTO `member` 
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'admin',
loginPw = 'admin',
`authLevel` = 7,
`name` = '관리자',
`nickname` = '관리자',
cellphoneNum = '01012341234',
email = 'abcdef@gmail.com';

# 회원 테스트데이터 생성 (일반)
INSERT INTO `member` 
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'sinha',
loginPw = 'sinha',
`name` = '신하',
`nickname` = '신하',
cellphoneNum = '01043214321',
email = 'kimsinah5@gmail.com';

INSERT INTO `member` 
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'test2',
loginPw = 'test2',
`name` = '회원2',
`nickname` = '회원2',
cellphoneNum = '01067896789',
email = 'zxcv@gmail.com';

# 게시물 테이블 구조 변경 - memberId 추가
ALTER TABLE article ADD COLUMN memberId INT(10) UNSIGNED NOT NULL AFTER `updateDate`;

UPDATE article 
SET memberId = 2
WHERE id IN(1,2);

UPDATE article 
SET memberId = 3
WHERE id = 3;

# 게시판 테이블 생성
CREATE TABLE board (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    `code` CHAR(50) NOT NULL UNIQUE COMMENT 'notice(공지사항), free(자유), qna(질의응답), ....',
    `name` CHAR(20) NOT NULL UNIQUE COMMENT '게시판 이름',
    delStatus TINYINT(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '삭제 여부 (0=삭제 전, 1=삭제 후)',
    delDate DATETIME COMMENT '삭제 날짜'
);

INSERT INTO board
SET regDate = NOW(),
updateDate = NOW(),
`code` = 'NOTICE',
`name` = '공지사항';

INSERT INTO board
SET regDate = NOW(),
updateDate = NOW(),
`code` = 'FREE',
`name` = '자유';

INSERT INTO board
SET regDate = NOW(),
updateDate = NOW(),
`code` = 'QNA',
`name` = '질의응답';
# 게시판 테이블 추가 생성
INSERT INTO board
SET regDate = NOW(),
updateDate = NOW(),
`code` = 'eReview',
`name` = '도구리뷰';

INSERT INTO board
SET regDate = NOW(),
updateDate = NOW(),
`code` = 'cReview',
`name` = '요리리뷰';


INSERT INTO board
SET regDate = NOW(),
updateDate = NOW(),
`code` = '캠핑 후기',
`name` = '캠핑 후기';


INSERT INTO board
SET regDate = NOW(),
updateDate = NOW(),
`code` = '차박 후기',
`name` = '차박 후기';


INSERT INTO board
SET regDate = NOW(),
updateDate = NOW(),
`code` = '야영 후기',
`name` = '야영 후기';


ALTER TABLE article ADD COLUMN boardId INT(10) UNSIGNED NOT NULL AFTER `memberId`;

UPDATE article
SET boardId = 1
WHERE id IN (1,2);

UPDATE article
SET boardId = 2
WHERE id = 3;

ALTER TABLE article ADD COLUMN hitCount INT(10) UNSIGNED NOT NULL;

# reactionPoint 테이블 생성
CREATE TABLE reactionPoint (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    memberId INT(10) UNSIGNED NOT NULL,
    relTypeCode CHAR(50) NOT NULL COMMENT '관련 데이터 타입 코드',
    relId INT(10) NOT NULL COMMENT '관련 데이터 번호',
    `point` INT(10) NOT NULL

);

# reactionPoint 테스트 데이터
# 1번 회원이 1번 글에 싫어요
INSERT INTO reactionPoint
SET regDate = NOW(),
updateDate = NOW(),
memberId = 1,
relTypeCode = 'article',
relId = 1,
`point` = -1;

# 1번 회원이 2번 글에 좋아요
INSERT INTO reactionPoint
SET regDate = NOW(),
updateDate = NOW(),
memberId = 1,
relTypeCode = 'article',
relId = 2,
`point` = 1;

# 2번 회원이 1번 글에 싫어요
INSERT INTO reactionPoint
SET regDate = NOW(),
updateDate = NOW(),
memberId = 2,
relTypeCode = 'article',
relId = 1,
`point` = -1;

# 2번 회원이 2번 글에 싫어요
INSERT INTO reactionPoint
SET regDate = NOW(),
updateDate = NOW(),
memberId = 2,
relTypeCode = 'article',
relId = 2,
`point` = -1;

# 3번 회원이 1번 글에 좋아요
INSERT INTO reactionPoint
SET regDate = NOW(),
updateDate = NOW(),
memberId = 3,
relTypeCode = 'article',
relId = 1,
`point` = 1;

# 게시물 테이블에 추천 관련 컬럼 추가
ALTER TABLE article ADD COLUMN goodReactionPoint INT(10) UNSIGNED NOT NULL;
ALTER TABLE article ADD COLUMN badReactionPoint INT(10) UNSIGNED NOT NULL;

# 기존 게시물의 good,bad ReactionPoint 필드의 값을 채운다
UPDATE article AS A
INNER JOIN (
    SELECT RP.relTypeCode, RP.relId,
    SUM(IF(RP.point > 0, RP.point,0)) AS goodReactionPoint,
    SUM(IF(RP.point < 0, RP.point * -1,0)) AS badReactionPoint
    FROM reactionPoint AS RP
    GROUP BY RP.relTypeCode, RP.relId
) AS RP_SUM
ON A.id = RP_SUM.relId
SET A.goodReactionPoint = RP_SUM.goodReactionPoint,
A.badReactionPoint = RP_SUM.badReactionPoint;


# 댓글 테이블 생성
CREATE TABLE `COMMENT`(
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    `body` TEXT NOT NULL,
    memberId INT(10) UNSIGNED NOT NULL,
    boardId INT(10) UNSIGNED NOT NULL,
    relTypeCode CHAR(50) NOT NULL COMMENT '관련 데이터 타입 코드',
    relId INT(10) UNSIGNED NOT NULL,
    goodReactionPoint INT(10) UNSIGNED NOT NULL,
    badReactionPoint INT(10) UNSIGNED NOT NULL
);

# 날씨 관련 테이블 생성
CREATE TABLE `tb_weather_area` (
	`areacode` VARCHAR(50) NOT NULL COMMENT '행정구역코드' COLLATE 'utf8_general_ci',
	`step1` VARCHAR(50) NOT NULL COMMENT '시도' COLLATE 'utf8_general_ci',
	`step2` VARCHAR(50) NULL DEFAULT NULL COMMENT '시군구' COLLATE 'utf8_general_ci',
	`step3` VARCHAR(50) NULL DEFAULT NULL COMMENT '읍면동' COLLATE 'utf8_general_ci',
	`gridX` VARCHAR(50) NOT NULL COMMENT '격자X' COLLATE 'utf8_general_ci',
	`gridY` VARCHAR(50) NOT NULL COMMENT '격자Y' COLLATE 'utf8_general_ci',
	`longitudeHour` VARCHAR(50) NOT NULL COMMENT '경도(시)' COLLATE 'utf8_general_ci',
	`longitudeMin` VARCHAR(50) NOT NULL COMMENT '경도(분)' COLLATE 'utf8_general_ci',
	`longitudeSec` VARCHAR(50) NOT NULL COMMENT '경도(초)' COLLATE 'utf8_general_ci',
	`latitudeHour` VARCHAR(50) NOT NULL COMMENT '위도(시)' COLLATE 'utf8_general_ci',
	`latitudeMin` VARCHAR(50) NOT NULL COMMENT '위도(분)' COLLATE 'utf8_general_ci',
	`latitudeSec` VARCHAR(50) NOT NULL COMMENT '위도(초)' COLLATE 'utf8_general_ci',
	`longitudeMs` VARCHAR(50) NOT NULL COMMENT '경도(초/100)' COLLATE 'utf8_general_ci',
	`latitudeMs` VARCHAR(50) NOT NULL COMMENT '위도(초/100)' COLLATE 'utf8_general_ci'
)
COMMENT='Excel 파일의 값들을 DB화 한 테이블'
COLLATE='utf8_general_ci'
ENGINE=INNODB
;


CREATE TABLE `tw_weather_response` (
	`baseDate` VARCHAR(50) NOT NULL COMMENT '발표일자' COLLATE 'utf8_general_ci',
	`baseTime` VARCHAR(50) NOT NULL COMMENT '발표시각' COLLATE 'utf8_general_ci',
	`category` VARCHAR(50) NOT NULL COMMENT '자료구분코드' COLLATE 'utf8_general_ci',
	`nx` VARCHAR(50) NOT NULL COMMENT '예보지점X좌표' COLLATE 'utf8_general_ci',
	`ny` VARCHAR(50) NOT NULL COMMENT '예보지점Y좌표' COLLATE 'utf8_general_ci',
	`obsrValue` VARCHAR(50) NOT NULL COMMENT '실황 값' COLLATE 'utf8_general_ci'
)
COMMENT='날씨 API 호출 응답값 저장'
COLLATE='utf8_general_ci'
ENGINE=INNODB
;

# 달력 테이블 생성
CREATE TABLE `schedule` (
schedule_idx INT(11) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
schedule_num INT(11),
schedule_subject VARCHAR(45),
schedule_desc VARCHAR(45),
schedule_startdate DATE,
schedule_enddate DATE,
memberId INT(10) NOT NULL,
color VARCHAR(45)
);

# 파일 테이블 추가
CREATE TABLE genFile (
  id INT(10) UNSIGNED NOT NULL AUTO_INCREMENT, # 번호
  regDate DATETIME DEFAULT NULL, # 작성날짜
  updateDate DATETIME DEFAULT NULL, # 갱신날짜
  delDate DATETIME DEFAULT NULL, # 삭제날짜
  delStatus TINYINT(1) UNSIGNED NOT NULL DEFAULT 0, # 삭제상태(0:미삭제,1:삭제)
  relTypeCode CHAR(50) NOT NULL, # 관련 데이터 타입(article, member)
  relId INT(10) UNSIGNED NOT NULL, # 관련 데이터 번호
  originFileName VARCHAR(100) NOT NULL, # 업로드 당시의 파일이름
  fileExt CHAR(10) NOT NULL, # 확장자
  typeCode CHAR(20) NOT NULL, # 종류코드 (common)
  type2Code CHAR(20) NOT NULL, # 종류2코드 (attatchment)
  fileSize INT(10) UNSIGNED NOT NULL, # 파일의 사이즈
  fileExtTypeCode CHAR(10) NOT NULL, # 파일규격코드(img, video)
  fileExtType2Code CHAR(10) NOT NULL, # 파일규격2코드(jpg, mp4)
  fileNo SMALLINT(2) UNSIGNED NOT NULL, # 파일번호 (1)
  fileDir CHAR(20) NOT NULL, # 파일이 저장되는 폴더명
  PRIMARY KEY (id),
  KEY relId (relTypeCode,relId,typeCode,type2Code,fileNo)
);

# 찜 테이블 추가
CREATE TABLE likebutton (
  regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    memberId INT(10) UNSIGNED NOT NULL,
    relTypeCode CHAR(50) NOT NULL COMMENT '관련 데이터 타입 코드',
    relId INT(10) NOT NULL COMMENT '관련 데이터 번호',
    `point` INT(10) NOT NULL
);

# chat 테이블 추가
CREATE TABLE chat (
    id INT(11) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `type` VARCHAR(255) NOT NULL,
    roomId INT(10) UNSIGNED NOT NULL,
    sender VARCHAR(255) NOT NULL,
    memberId INT(11) UNSIGNED NOT NULL,
    message VARCHAR(255) NOT NULL,
    `time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE chat CONVERT TO CHARSET UTF8;


# chatRoom 테이블 생성
CREATE TABLE chatRoom (
  id INT(11) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  roomName VARCHAR(255) NOT NULL,
  memberId INT(11) UNSIGNED NOT NULL

);
ALTER TABLE chatRoom CONVERT TO CHARSET UTF8;

# chat_user 테이블 생성
CREATE TABLE chat_user (
  id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  roomId INT(10) UNSIGNED NOT NULL,
  memberId INT(11) UNSIGNED NOT NULL
);

ALTER TABLE chat_user CONVERT TO CHARSET UTF8;

###################################################################
SELECT * FROM article;
SELECT * FROM `member`;
SELECT * FROM `schedule`;
SELECT * FROM tb_weather_area;
SELECT * FROM board;
SELECT * FROM reactionPoint;
SELECT * FROM chat;
SELECT * FROM chatRoom;
SELECT * FROM chat_user;

SELECT *
FROM reactionPoint AS RP
GROUP BY RP.relTypeCode, RP.relId;

SELECT IF(RP.point > 0, '큼','작음')
FROM reactionPoint AS RP
GROUP BY RP.relTypeCode, RP.relId;

# 각 게시물 별 좋아요, 싫어요의 갯수
SELECT RP.relTypeCode, RP.relId,
SUM(IF(RP.point > 0, RP.point,0)) AS goodReactionPoint,
SUM(IF(RP.point < 0, RP.point * -1,0)) AS badReactionPoint
FROM reactionPoint AS RP
GROUP BY RP.relTypeCode, RP.relId;


SELECT IFNULL(SUM(RP.point),0)
FROM reactionPoint AS RP
WHERE RP.relTypeCode = 'article'
AND RP.relId = 3
AND RP.memberId = 2



UPDATE article
SET `body` = '내용4'
WHERE id= 1;

UPDATE article
SET `body` = '내용5'
WHERE id= 2;

UPDATE article
SET `body` = '내용6'
WHERE id= 3;


# 게시물 테스트데이터 생성
INSERT INTO article 
SET regDate = NOW(),
updateDate = NOW(),
title = '제목 1',
`body` = '내용 1';

INSERT INTO article 
SET regDate = NOW(),
updateDate = NOW(),
title = '제목 2',
`body` = '내용 2';

INSERT INTO article 
SET regDate = NOW(),
updateDate = NOW(),
title = '제목 3',
`body` = '내용 3';


# 게시물 갯수 늘리기
INSERT INTO article 
( 
    regDate, updateDate, memberId, boardId, title, `body`
)
SELECT NOW(), NOW(), FLOOR(RAND() * 2) + 2, FLOOR(RAND() * 2) + 1, CONCAT('제목_',RAND()), CONCAT('내용_',RAND())
FROM article;

SELECT COUNT(*) FROM article;

DESC article;



SELECT COUNT(*) AS cnt
FROM article AS A
WHERE 1
AND A.boardId = 1

SELECT *
		  FROM article
		  WHERE boardId = 1
		  ORDER BY id DESC
		  LIMIT 0, 10

DESC `member`;

SELECT LAST_INSERT_ID();

SELECT  CONCAT('%' 'abc' '%');

# left join
SELECT A.*, M.nickname, RP.point
FROM article AS A
INNER JOIN `member` AS M 
ON A.memberId = M.id
LEFT JOIN reactionPoint AS RP 
ON A.id = RP.relId AND RP.relTypeCode = 'article'
GROUP BY A.id
ORDER BY A.id DESC;

# 서브쿼리 버전
SELECT A.*, 
IFNULL(SUM(RP.point),0) AS extra__sumReactionPoint,
IFNULL(SUM(IF(RP.point > 0, RP.point, 0)),0) AS extra__goodReactionPoint,
IFNULL(SUM(IF(RP.point < 0, RP.point, 0)),0) AS extra__badReactionPoint
FROM (
	SELECT A.*, M.nickname AS extra__writerName
	FROM article AS A
	LEFT JOIN `member` AS M
	ON A.memberId= M.id 
			) AS A
LEFT JOIN reactionPoint AS RP
ON RP.relTypeCode = 'article'
AND A.id = RP.relId
GROUP BY A.id
ORDER BY id DESC;

# join 버전
SELECT A.*,
IFNULL(SUM(RP.point),0) AS extra__sumReactionPoint,
IFNULL(SUM(IF(RP.point > 0,RP.point,0)),0) AS extra__goodReactionPoint,
IFNULL(SUM(IF(RP.point < 0,RP.point,0)),0) AS extra__badReactionPoint,
M.nickname
FROM article AS A
INNER JOIN `member` AS M 
ON A.memberId = M.id
LEFT JOIN reactionPoint AS RP 
ON A.id = RP.relId AND RP.relTypeCode = 'article'
GROUP BY A.id
ORDER BY A.id DESC;

SELECT A.*,
SUM(RP.point) AS extra__sumReactionPoint,
SUM(IF(RP.point > 0,RP.point,0)) AS extra__goodReactionPoint,
SUM(IF(RP.point < 0,RP.point,0)) AS extra__badReactionPoint,
M.nickname
FROM article AS A
INNER JOIN `member` AS M 
ON A.memberId = M.id
LEFT JOIN reactionPoint AS RP 
ON A.id = RP.relId AND RP.relTypeCode = 'article'
GROUP BY A.id
ORDER BY A.id DESC;

SELECT comment.*,
		IFNULL(SUM(reactionPoint.point),0) AS extra__sumReactionPoint,
		IFNULL(SUM(IF(reactionPoint.point > 0,reactionPoint.point,0)),0) AS extra__goodReactionPoint,
		IFNULL(SUM(IF(reactionPoint.point < 0,reactionPoint.point,0)),0) AS extra__badReactionPoint, member.name AS 'name'
		FROM COMMENT
		INNER JOIN `member`
		ON comment.memberId = member.id
		LEFT JOIN reactionPoint
		ON comment.id = reactionPoint.relId AND reactionPoint.relTypeCode = 'comment'
		WHERE comment.id
		=
		2
		
SELECT comment.*, member.name
FROM `comment`
INNER JOIN `member`
ON comment.memberId = member.id