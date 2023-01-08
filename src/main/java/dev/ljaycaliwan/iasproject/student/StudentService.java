package dev.ljaycaliwan.iasproject.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }

    public void addNewStudent(Student student) {
        studentRepository.save(student);

    }

    @Transactional
    public void updateStudent(Long studentId, String fullName, String email, String address) {
        //if student does not exist this will throw an exception
        Student student = studentRepository.findById(studentId).orElseThrow(() ->
                new IllegalStateException("student with id " + studentId + "does not exists"));

        //if full name is not null and the name provided is not the same as the current one, update student full name
        if(fullName != null && fullName.length() > 0 && !Objects.equals(student.getFullName(), fullName)){
            student.setFullName(fullName);
        }
        //if email is not null and the email provided is not the same as the current one, update email
        if(email != null && email.length() > 0 && !Objects.equals(student.getFullName(), email)){
            Optional<Student> studentOptional = studentRepository.findStudentByEmail(email);
            if (studentOptional.isPresent()){
                throw new IllegalStateException("Email is already taken");
            }
            student.setEmail(email);
        }
        //if address is not null and the address provided is not the same as the current one, update address
        if(address != null && address.length() > 0 && !Objects.equals(student.getFullName(), address)){
            student.setAddress(address);
        }
    }

    public void deleteStudent(Long studentId) {
        boolean exists = studentRepository.existsById(studentId);
        if(!exists){
            throw new IllegalStateException("student with id " + studentId + "does not exists");
        }
        studentRepository.deleteById(studentId);
    }

    public Student findStudentById(Long studentId) {
        return studentRepository.findById(studentId).get();
    }
}
