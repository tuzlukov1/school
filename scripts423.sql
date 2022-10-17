SELECT student.name, student.age, f.name
FROM student
LEFT JOIN faculty f
ON student.faculty_id = f.id;

SELECT student.name, student.age
FROM student
INNER JOIN avatar
ON student.id = avatar.student_id;