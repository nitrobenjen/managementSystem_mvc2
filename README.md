# managementSystem_mvc2
MVC 패턴을 적용한 학사관리 (서블릿)

쿼리문 정리중

------------------수강생--------------------------------------

--특정 수강생이 듣고있는 > 개설과정ID, 과정명, 과정기간, 강의실명, 과목조회(수)
SELECT student_name, o1.open_course_id, o1.course_id, course_name, o1.class_id, class_name, TO_CHAR(course_start_day,'YYYY-MM-DD') AS course_start_day, TO_CHAR(course_end_day, 'YYYY-MM-DD') AS course_end_day
,(SELECT COUNT(*) 
FROM open_sub
WHERE open_course_id = o1.open_course_id) AS count_
FROM open_course o1, course o2, class_ o3, course_list o4, student o5
WHERE o1.course_id=o2.course_id
AND o1.class_id = o3.class_id
AND o1.open_course_id = o4.open_course_id
AND o4.student_id = o5.student_id
AND o4.student_id = 'STU010';




--특정 수강생의 특정 과정 > 개설과목ID, 과목명, 과목기간, 교재명, 강사명, 출결/배점, 필기/배점, 실기/배점, 총점, 시험날짜, 시험문제명()


SELECT o3.open_sub_id, subject_name,TO_CHAR(sub_start_day, 'YYYY-MM-DD') AS sub_start_day, TO_CHAR(sub_end_day, 'YYYY-MM-DD') AS sub_end_day ,o1.open_course_id, student_name
, o8.BOOK_NAME, book_imgname, o9.TEACHER_NAME, o6.CHULSUK_GRADE, o7.CHULSUK_TOTAL_GRADE, o6.FILKI_GRADE, o7.FILKI_TOTAL_GRADE, o6.SILKI_GRADE, o7.SILKI_TOTAL_GRADE
,(o6.CHULSUK_GRADE + o6.FILKI_GRADE + o6.SILKI_GRADE ) AS total , (o7.CHULSUK_TOTAL_GRADE + o7.FILKI_TOTAL_GRADE +  o7.SILKI_TOTAL_GRADE)AS total_grade
,TO_CHAR(o7.TEST_DATE, 'YYYY-MM-DD') AS test_date, o7.TEST_MUNJE
FROM open_course o1
LEFT OUTER JOIN open_sub o3 ON o3.OPEN_COURSE_ID = o1.OPEN_COURSE_ID
LEFT OUTER JOIN subject o2 ON o2.SUBJECT_ID = o3.SUBJECT_ID
LEFT OUTER JOIN course_list o4 ON o1.OPEN_COURSE_ID = o4.OPEN_COURSE_ID
LEFT OUTER JOIN student o5 ON o4.STUDENT_ID = o5.STUDENT_ID
LEFT OUTER JOIN grade o6 ON o5.STUDENT_ID = o6.STUDENT_ID AND o6.OPEN_SUB_ID = o3.OPEN_SUB_ID
LEFT OUTER JOIN baejum o7 ON o3.OPEN_SUB_ID = o7.OPEN_SUB_ID
LEFT OUTER JOIN book o8 ON o3.BOOK_ID = o8.BOOK_ID
LEFT OUTER JOIN teacher o9 ON o3.TEACHER_ID = o9.TEACHER_ID
WHERE o4.STUDENT_ID = 'STU068'
AND o1.open_course_id = 'OCU002'
ORDER BY o3.OPEN_course_ID;


--수강생 개인정보 아이디, 비밀번호, 이름, 주민번호 뒷자리, 전화번호, 과정 수강횟수
SELECT id_, pw,student_name, student_ssn, student_phone, student_regdate
, (SELECT COUNT(*) FROM course_list WHERE o1.STUDENT_ID = student_id) AS count_
, DECODE(grade, 0, '관리자', 1, '강사', 2, '수강생') AS grade
FROM student o1
INNER JOIN login o2 ON o1.STUDENT_ID = o2.ID_;

--수강생 비밀번호 변경 확인 전 준비. 해당 ID와 pw가 있는지 확인한다.
SELECT id_, pw FROM login WHERE id_='STU068' AND pw = 1234;
--그 후 업데이트
UPDATE login SET pw = 1234 WHERE id_='STU068';




---------------------------------관리자--------------------------------------
------------------------------기초정보 관리-------------------------------------------
------------------------------과정 관리-------------------------------------------
--과정 출력
SELECT course_id, course_name FROM course ORDER BY course_id;
--과정 입력
INSERT INTO course (course_id, course_name) VALUES ((SELECT CONCAT('CU',LPAD((NVL(MAX(SUBSTR(course_id,3)), 0)+1), 3, '0') ) FROM course), '과정명 입력');
--과정 수정
UPDATE course SET course_name = '과정명수정' WHERE course_id='CU010';

--과정 삭제
--참조하고 있는 테이블이 있을 경우 삭제가 불가능하다. 참조하고 있는 테이블이 있을 경우 비활성화 처리를 해야하기 때문에 아래의 쿼리를 실행한다.
SELECT o1.course_id FROM course o1 INNER JOIN open_course o2 ON o1.course_id = o2.course_id GROUP BY o1.course_id;
--결과값이 없다면 아래의 쿼리를 실행해 삭제
DELETE FROM course WHERE course_id ='CU008';


------------------------------과목 관리-------------------------------------------
--과목 출력
SELECT subject_id, subject_name FROM subject ORDER BY subject_id;
--과목 입력
INSERT INTO subject (subject_id, subject_name) VALUES ((SELECT CONCAT('SUB',LPAD((NVL(MAX(SUBSTR(subject_id,4)), 0)+1), 3, '0') ) FROM subject), '과목명 입력');
--과목 수정
UPDATE subject SET subject_name = '과정명수정' WHERE subject_id='SUB015';
--과목 삭제
--참조하고 있는 테이블이 있을 경우 삭제가 불가능하다. 참조하고 있는 테이블이 있을 경우 비활성화 처리를 해야하기 때문에 아래의 쿼리를 실행한다.
--주의. 기본과목같은 경우 강의가능과목과 개설과목 두 테이블과 연결되있기 때문에 전부 확인해야 한다.
--개설과목과 연결 확인
SELECT o1.subject_id FROM subject o1, open_sub o2 WHERE o1.subject_id = o2.subject_id GROUP BY o1.subject_id; 
--강의가능과목과 연결 확인
SELECT o1.subject_id FROM subject o1, teach_sub o2 WHERE o1.SUBJECT_ID = o2.SUBJECT_ID GROUP BY o1.subject_id; 
--결과값이 없다면 아래의 쿼리를 실행해 삭제
DELETE FROM subject WHERE subject_id ='SUB015';



------------------------------교재 관리-------------------------------------------
--교재 출력
SELECT book_id, book_name, publisher, book_imgname FROM book ORDER BY book_id;
--교재 입력
INSERT INTO book (book_id, book_name, publisher, book_imgname) VALUES ((SELECT CONCAT('B',LPAD((NVL(MAX(SUBSTR(book_id,2)), 0)+1), 3, '0') ) FROM book), '교재명 입력', '출판사입력', '업로드된이미지파일명');
--교재 수정
--업로드한 이미지 삭제를 위해 select 쿼리 추가
SELECT book_imgname FROM book WHERE book_id='B013';
UPDATE book SET book_name='수정교재명', publisher='수정출판사명', book_imgname='수정파일명' WHERE book_id='B012';
--교재 삭제
--참조하고 있는 테이블이 있을 경우 삭제가 불가능하다. 참조하고 있는 테이블이 있을 경우 비활성화 처리를 해야하기 때문에 아래의 쿼리를 실행한다.
SELECT o1.book_id FROM book o1, open_sub o2 WHERE o1.BOOK_ID = o2.BOOK_ID;
--결과값이 없다면 아래의 쿼리를 실행해 삭제
DELETE FROM book WHERE book_id ='B012';



------------------------------강의실 관리-------------------------------------------
--강의실 출력
SELECT class_id, class_name, jungwon FROM class_ ORDER BY class_id;
--강의실 입력
INSERT INTO class_ (class_id, class_name, jungwon) VALUES ((SELECT CONCAT('CA',LPAD((NVL(MAX(SUBSTR(class_id,3)), 0)+1), 3, '0') ) FROM class_), '강의실명 입력', 30);
--강의실 수정
--진행중인 과정의 강의실은 수정이 불가능하기 때문에 아래의 쿼리를 실행한다.
SELECT o1.class_id AS count_ FROM class_ o1, open_course o2 WHERE o1.class_id = o2.class_id AND TO_CHAR(o2.COURSE_END_DAY, 'YYYY-MM-DD') > SYSDATE AND TO_CHAR(o2.COURSE_START_DAY, 'YYYY-MM-DD') < SYSDATE;

UPDATE class_ SET class_name='수정강의실명', jungwon=55 WHERE class_id='CA006';
--강의실 삭제
--참조하고 있는 테이블이 있을 경우 삭제가 불가능하다. 참조하고 있는 테이블이 있을 경우 비활성화 처리를 해야하기 때문에 아래의 쿼리를 실행한다.
SELECT o1.class_id FROM class_ o1, open_course o2 WHERE o1.class_id = o2.class_id;
--결과값이 없다면 아래의 쿼리를 실행해 삭제
DELETE FROM class_ WHERE class_id ='CA006';


------------------------------------------------------------------------------------------------------------------------

------------------------------강사 관리-------------------------------------------

------------------------------------강사번호, 강사명, 주민번호뒷자리, 전화번호, 강의가능과목 수----------------------------------
SELECT teacher_id, teacher_name, teacher_ssn, teacher_phone, TO_CHAR(teacher_hiredate, 'YYYY-MM-DD') AS teacher_hiredate
, (SELECT COUNT(*) FROM teach_sub WHERE teacher_id = o1.teacher_id) AS count_
FROM teacher o1
ORDER BY teacher_id;


------------------------------------특정 강사의 강의가능 과목 목록----------------------------------
SELECT o1.teacher_id, teacher_name, teacher_phone, o2.subject_id, subject_name
FROM teacher o1
LEFT OUTER JOIN teach_sub o2 ON o1.TEACHER_ID = o2.TEACHER_ID
LEFT OUTER JOIN subject o3 ON o2.SUBJECT_ID = o3.SUBJECT_ID
WHERE o1.teacher_id = 'TCH001'
ORDER BY o1.teacher_id;


-----------------------------강사 등록----------------------------------
--강사 기본정보 등록, 여러 테이블에 동시 입력을 해야하기 때문에 입력할 PK를 받는 쿼리 별도 실행
SELECT CONCAT('TCH',LPAD((NVL(MAX(SUBSTR(teacher_id,4)), 0)+1), 3, '0') ) FROM teacher;
--전달받은 강사 PK로 입력을 시도
INSERT INTO teacher (teacher_id, teacher_name, teacher_ssn, teacher_phone, teacher_hiredate) VALUES ('전달받은PK', '강사명', 123457, '폰번호', SYSDATE);
--강사 기본정보가 등록된 것을 확인한 후 강의 가능 과목 입력 쿼리를 실행. 실패할 경우 롤백한다.
--주의. 과목을 여러개를 받을 경우 처리 방법. 입력받은 만큼 아래의 쿼리문을 실행할 예정
INSERT INTO teach_sub (teacher_id, subject_id) VALUES ('전달받은pk', '선택한과목pk');


------------------------------------강사 수정----------------------------------
--특정 강사의 강의가능 과목 목록 쿼리문을 정보를 받은 뒤 아래의 쿼리문 실행
--주의. 강의가능과목 같은 경우 개설과목과 연결된 강의가능과목이라면 수정이 불가능하기 때문에 체크 해제를 못하게 비활성화 처리를 해야 한다.
--등록과 마찬가지로 기본정보와 강의가능과목은 따로 처리한다. 중간에 오류가 있을 경우 롤백
UPDATE teacher SET teacher_name='수정강사명', teacher_ssn='수정뒷자리', teacher_phone='수정휴대폰' WHERE teacher_id='선택한강사pk';
--강의가능과목 수정

-------------------보관용-----------------------------------
--강의가능과목이면서 개설과목과 연결되지 않은 목록을 받아온다.
--SELECT o1.teacher_id, teacher_name, teacher_phone, o2.subject_id, subject_name
--FROM teacher o1
--LEFT OUTER JOIN teach_sub o2 ON o1.TEACHER_ID = o2.TEACHER_ID
--LEFT OUTER JOIN subject o3 ON o2.SUBJECT_ID = o3.SUBJECT_ID
--LEFT OUTER JOIN open_sub o4 ON o1.TEACHER_ID = o4.TEACHER_ID AND o4.SUBJECT_ID = o3.SUBJECT_ID
--WHERE o1.TEACHER_ID = 'TCH001'
--AND o4.subject_id NOT IN
--(SELECT subject_id FROM open_sub WHERE subject_id = o4.subject_id);
----------------------------------------------------

--강의가능과목이면서 개설과목과 연결되지 않은 목록을 삭제한다.

DELETE FROM teach_sub 
WHERE teacher_id='TCH001' 
AND subject_id IN(SELECT o2.subject_id
FROM teacher o1
LEFT OUTER JOIN teach_sub o2 ON o1.TEACHER_ID = o2.TEACHER_ID
LEFT OUTER JOIN subject o3 ON o2.SUBJECT_ID = o3.SUBJECT_ID
LEFT OUTER JOIN open_sub o4 ON o1.TEACHER_ID = o4.TEACHER_ID AND o4.SUBJECT_ID = o3.SUBJECT_ID
WHERE o1.TEACHER_ID = 'TCH001'
AND o4.subject_id NOT IN
(SELECT subject_id FROM open_sub WHERE subject_id = o4.subject_id));



--그 후 선택한 강의가능과목을 횟수만큼 아래의 쿼리 실행
INSERT INTO teach_sub (teacher_id, subject_id) VALUES ('선택한강사pk', '선택한과목pk');




----------------------------------강사 삭제------------------------------------------------
--수정과 마찬가지로 참조하고 있는 테이블이 있을 경우 삭제가 불가능하다.
--먼저 연결되있는지 확인하기 위해 아래의 쿼리를 실행해 연결되있을 경우 삭제버튼을 비활성화 처리 해야한다.
--개설과목과 연결되있는지 확인
SELECT o1.teacher_id AS teacher_id FROM teacher o1  INNER JOIN open_sub o2 ON o1.TEACHER_ID = o2.TEACHER_ID GROUP BY o1.teacher_id ORDER BY o1.teacher_id;
--강의가능과목과 연결되있는지 확인
SELECT o1.teacher_id AS teacher_id FROM teacher o1 INNER JOIN teach_sub o2 ON o1.TEACHER_ID = o2.TEACHER_ID GROUP BY o1.teacher_id ORDER BY o1.teacher_id;





--검색결과가 없다면 아래의 쿼리를 통해 삭제 실행. 
DELETE FROM teacher WHERE teacher_id ='TCH999';





------------------------------------------------------------------------------------------------------------------------

------------------------------개설 과정/과목 관리-------------------------------------------

--개설 과정 목록 출력 
--개설과정번호, 과정명, 강의실명, 강의실 정원, 수강 인원, 과정 시작일, 과정 종료일, 개설 과목 수
--과정별 수강인원. 한번에 출력을 하기에는 번거롭기 때문에 분리함.
SELECT o1.open_course_id, (SELECT COUNT(*) FROM course_list WHERE open_course_id = o1.OPEN_COURSE_ID) AS studentcount FROM open_course o1;
--개설과정번호, 과정명, 강의실명, 강의실 정원, 과정 시작일, 과정 종료일, 개설 과목 수
SELECT o1.open_course_id, course_name, class_name, jungwon, TO_CHAR(course_start_day,'YYYY-MM-DD') AS course_start_day, TO_CHAR(course_end_day,'YYYY-MM-DD') AS course_end_day
,(SELECT COUNT(*) FROM open_sub WHERE open_course_id = o1.OPEN_COURSE_ID) AS subcount
FROM open_course o1, course o2, class_ o3 WHERE o1.COURSE_ID = o2.COURSE_ID AND o1.CLASS_ID = o3.CLASS_ID ORDER BY o1.open_course_id;



----------------------------특정 과정의 수강생 명단------------------------------------------
--개설과정ID, 과정명, 과정 시작 종료일
--수강생 번호, 수강생명, 주민번호, 전화번호, 등록일, 수료 여부

SELECT o3.open_course_id, course_name,  TO_CHAR(course_start_day,'YYYY-MM-DD') AS course_start_day, TO_CHAR(course_end_day,'YYYY-MM-DD') AS course_end_day, o1.student_id, student_name, student_ssn, student_phone, To_CHAR(student_regdate,'YYYY-MM-DD') AS student_regdate,  TO_CHAR(finish_day, 'YYYY-MM-DD')AS finish_day
FROM student o1
INNER JOIN course_list o2 ON o1.STUDENT_ID = o2.STUDENT_ID
INNER JOIN open_course o3 ON o2.OPEN_COURSE_ID = o3.OPEN_COURSE_ID
INNER JOIN course o5 ON o3.COURSE_ID = o5.COURSE_ID
LEFT OUTER JOIN fail_check o4 ON o2.OPEN_COURSE_ID = o4.OPEN_COURSE_ID AND o2.STUDENT_ID = o4.STUDENT_ID
WHERE o3.OPEN_COURSE_ID = 'OCU001'
ORDER BY o1.STUDENT_ID;




----------------------------개설과정 등록------------------------------------------
----------개설과정/과목 시작날짜는 sysdate 이전일은 선택못하게 jquery로 처리한다.
--개설과정을 등록하는 경우 자신이 선택한 날짜에 있는 모든 개설과정을 불러와 강의실 비교를 한 뒤, 일치하는 강의실이 없을 경우에만 진행해야 한다.
--과정 시작일< 입력한 시작일이나 종료일 하나라도 안쪽에 들어 가있으면 강의실 비교 < 과정 종료일     >>>>>  선택한 강의실과 일치하는 강의실이 없을 경우 진행
SELECT open_course_id, class_id, TO_CHAR(course_start_day,'YYYY-MM-DD') AS course_start_day, TO_CHAR(course_end_day,'YYYY-MM-DD') AS course_end_day 
FROM open_course
WHERE TO_CHAR(course_start_day,'YYYY-MM-DD') <= '과정종료일2019-02-01' AND TO_CHAR(course_end_day,'YYYY-MM-DD') >= '과정시작일2017-10-31'
AND class_id='선택한강의실CA002';

--위의 쿼리 검색결과가 0건이라면 아래의 쿼리를 실행한다.
INSERT INTO open_course (open_course_id, course_id, class_id, course_start_day, course_end_day) VALUES ((SELECT CONCAT('OCU',LPAD((NVL(MAX(SUBSTR(open_course_id,4)), 0)+1), 3, '0') ) FROM open_course), '선택한강의id', '선택한강의실id', '시작날짜', '종료날짜');



----------------------------개설과정 수정------------------------------------------
--우선 선택한 개설과정이 수정가능한 상태인지 확인해야 한다.
--수정 가능한 경우는 2가지다. 과정이 아직 시작하지 않은 경우는 시작, 종료일자 모두 수정이 가능하며,
--계획에 없던 휴강이 있을 경우 수료일이 늦춰지는 경우도 있기 때문에 진행중인 과정도 종료날짜는 수정가능하게 한다.
--먼저 시작된 과정인지 확인하는 쿼리 실행.
SELECT open_course_id, class_id, TO_CHAR(course_start_day,'YYYY-MM-DD') AS course_start_day, TO_CHAR(course_end_day,'YYYY-MM-DD') AS course_end_day 
FROM open_course
WHERE TO_CHAR(course_start_day,'YYYY-MM-DD') > TO_CHAR(SYSDATE, 'YYYY-MM-DD')
AND open_course_id = 'OCU007';

--위의 쿼리결과가 0이 아닐 경우, 시작되지 않은 과정이다. 아래의 쿼리를 건너띄고 바로 update문을 실행한다.
--다만 update문을 실행할 때는, 강의실 확인 쿼리가 필요.
--위의 쿼리결과가 0일 경우 아래의 쿼리를 실행해, 진행중인 과정인지 확인한다.
SELECT open_course_id, class_id, TO_CHAR(course_start_day,'YYYY-MM-DD') AS course_start_day, TO_CHAR(course_end_day,'YYYY-MM-DD') AS course_end_day 
FROM open_course
WHERE TO_CHAR(course_end_day,'YYYY-MM-DD') > TO_CHAR(SYSDATE, 'YYYY-MM-DD')
AND open_course_id = 'OCU007';

--위의 쿼리 실행결과가 0이라면 update문을 실행. 아니라면 비활성화
UPDATE open_course SET course_id='선택한과정명', class_id='선택한강의실명', course_start_day='시작날짜',  course_end_day='종료날짜' WHERE open_course_id = '선택한개설과정id';


----------------------------개설과정 삭제------------------------------------------
--삭제는 개설과목 혹은, 수강 목록과 연결되있는 경우 비활성화 처리 한다.
--개설과정 리스트를 출력할 때 사용한 쿼리를 재사용
--카운터 0인것은 비활성화 처리
--과정별 수강인원. 한번에 출력을 하기에는 번거롭기 때문에 분리함.
SELECT o1.open_course_id, (SELECT COUNT(*) FROM course_list WHERE open_course_id = o1.OPEN_COURSE_ID) AS studentcount FROM open_course o1;
--개설과정번호, 과정명, 강의실명, 강의실 정원, 과정 시작일, 과정 종료일, 개설 과목 수
SELECT o1.open_course_id, course_name, class_name, jungwon, TO_CHAR(course_start_day,'YYYY-MM-DD') AS course_start_day, TO_CHAR(course_end_day,'YYYY-MM-DD') AS course_end_day
,(SELECT COUNT(*) FROM open_sub WHERE open_course_id = o1.OPEN_COURSE_ID) AS subcount
FROM open_course o1, course o2, class_ o3 WHERE o1.COURSE_ID = o2.COURSE_ID AND o1.CLASS_ID = o3.CLASS_ID ORDER BY o1.open_course_id;

--수강인원과 개설과목이 0인 경우 아래의 쿼리 실행
DELETE FROM open_course WHERE open_course_id ='선택한개설과정pk';



------------------------------------------------------------------------------------------------------------------------

------------------------------개설 과정/과목 관리-------------------------------------------
--선택한 개설과정에 속한 개설과목 리스트
--개설과정명, 과정기간, 개설과목번호, 개설과목명, 기간, 교재명, 강사명
--과정명, 기간을 따로 처리할지 생각중.
SELECT o6.open_course_id, course_name, o1.open_sub_id, subject_name, sub_start_day, sub_end_day, book_name, teacher_name 
FROM open_sub o1
LEFT OUTER JOIN subject o2 ON o1.SUBJECT_ID = o2.SUBJECT_ID
LEFT OUTER JOIN teacher o3 ON o1.TEACHER_ID = o3.TEACHER_ID
LEFT OUTER JOIN book o4 ON o1.BOOK_ID = o4.BOOK_ID
LEFT OUTER JOIN teach_sub o5 ON o3.TEACHER_ID = o5.TEACHER_ID AND o2.SUBJECT_ID = o5.SUBJECT_ID
LEFT OUTER JOIN open_course o6 ON o1.OPEN_COURSE_ID = o6.OPEN_COURSE_ID
LEFT OUTER JOIN course o7 ON o6.COURSE_ID = o7.COURSE_ID
WHERE o1.OPEN_COURSE_ID = 'OCU001'
ORDER BY o6.open_course_id;


-------------------------개설과목 입력-------------------------------------
--기초정보 쿼리로 과목, 교재를 불러 오고, 과목선택 change 이벤트를 걸어 해당 과목이 가능한 강사목록을 실시간으로 바꾼다.
--선택한 과목 강의가 가능한 강사 정보 출력
SELECT o1.teacher_id, teacher_name, teacher_ssn, teacher_phone, o2.subject_id, subject_name FROM teacher o1, teach_sub o2, subject o3 WHERE o1.TEACHER_ID = o2.TEACHER_ID AND o2.SUBJECT_ID = o3.SUBJECT_ID AND o2.subject_id = 'SUB001' ORDER BY teacher_id;
--선택한 개설과정 정보
SELECT open_course_id, course_id, course_start_day, course_end_day FROM open_course WHERE open_course_id = 'OCU001';

--개설과목 입력
--과목 시작일, 종료일은 jquery 처리를 해서 과정기간 내부에서만 선택가능하게 해야한다. 구상중.
--또한 기존에 등록되있던 개설과목 기간들과도 비교를 해서 겹쳐지지 않는지도 확인 해야하지만..
INSERT INTO open_sub (open_sub_id, teacher_id, subject_id, book_id, open_course_id, sub_start_day, sub_end_day) VALUES ((SELECT CONCAT('OUB',LPAD((NVL(MAX(SUBSTR(open_sub_id,4)), 0)+1), 3, '0') ) FROM open_sub), '강사id', '과목id', '교재id', '개설과정id', '과목시작일', '과목종료일');






