

DROP DATABASE IF EXISTS studentmanagementsystem;
CREATE DATABASE studentmanagementsystem;
USE studentmanagementsystem;


CREATE TABLE students (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    class VARCHAR(50),
    roll_no VARCHAR(50),
    email VARCHAR(100),
    attendance_status VARCHAR(20) DEFAULT 'Absent'
);


SELECT * FROM students;
CREATE TABLE student_attendance (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT,
    attendance_date DATE,
    status VARCHAR(20),
    FOREIGN KEY (student_id) REFERENCES students(id)
);
Select * FROM student_attendance;

-- =========================================
-- FACULTY TABLE
-- =========================================
CREATE TABLE faculty (
    id INT PRIMARY KEY,
    name VARCHAR(100),
    department VARCHAR(100),
    email VARCHAR(100),
    phone VARCHAR(20),
    attendance_status VARCHAR(20) DEFAULT 'Absent'
);
SELECT * FROM faculty;
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL
);
SELECT * FROM users;



-- =========================================
-- COURSE TABLE
-- =========================================
CREATE TABLE course (
    RollNumber VARCHAR(15),
    Name TEXT NOT NULL,
    Programme TEXT NOT NULL,
    Mail TEXT NOT NULL,
    Course TEXT NOT NULL,
    Section INT NOT NULL,
    Attendance TINYINT(1) DEFAULT 0
);

INSERT INTO course VALUES
('130', 'Harsh Mange', 'B.Tech', 'harshm44', 'OOP', 1, 0);

-- =========================================
-- ATTENDANCE TABLE
-- =========================================
CREATE TABLE attendance (
    RollNumber VARCHAR(15),
    Name TEXT NOT NULL,
    Course TEXT NOT NULL,
    Section INT NOT NULL,
    Date DATE NOT NULL,
    Present TINYINT(1)
);

INSERT INTO attendance VALUES
('130', 'Harsh Mange', 'OOP', 1, '2019-04-06', 1),
('130', 'Harsh Mange', 'OOP', 1, '2019-04-07', 0),
('130', 'Harsh Mange', 'MAT100', 1, '2019-04-08', 1);

-- =========================================
-- QUIZ TABLE
-- =========================================
CREATE TABLE quiz (
    QuizNo VARCHAR(10),
    Course TEXT NOT NULL,
    Section VARCHAR(10),
    Question TEXT NOT NULL,
    OptA TEXT NOT NULL,
    OptB TEXT NOT NULL,
    OptC TEXT NOT NULL,
    OptD TEXT NOT NULL,
    Answer TEXT NOT NULL
);

INSERT INTO quiz VALUES
('1', 'MAT100', '1', 'What is Java?', 'Language', 'OOP Language', 'Programming', 'All', 'All'),
('1', 'MAT100', '1', 'What is C?', 'Language', 'OOP Language', 'Programming', 'All', 'Programming'),
('2', 'MAT100', '1', 'What is sin 30?', '0', '1', '1/2', 'All', '1/2');

-- =========================================
-- QUIZ RESULT TABLE
-- =========================================
CREATE TABLE quizresult (
    QuizNo VARCHAR(10),
    Course TEXT,
    Section TEXT,
    Marks INT,
    RollNumber VARCHAR(15)
);

INSERT INTO quizresult VALUES
('1', 'MAT100', '1', 4, '130'),
('1', 'MAT100', '1', 3, '130'),
('2', 'MAT100', '1', 1, '130');

-- =========================================
-- END OF DATABASE
-- =========================================
