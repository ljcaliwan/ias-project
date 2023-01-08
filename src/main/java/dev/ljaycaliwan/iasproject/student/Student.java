package dev.ljaycaliwan.iasproject.student;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table
public class Student {
    @Id
    @SequenceGenerator(name = "student_sequence", sequenceName = "student_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_sequence")
    private Long id;
    private String fullName;
    private String email;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;
    @Transient
    private Integer age;
    private String address;

    public Student(Long id, String fullName, String email, LocalDate dob, String address) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.dob = dob;
        this.address = address;
    }

    public Student(String fullName, String email, LocalDate dob, String address) {
        this.fullName = fullName;
        this.email = email;
        this.dob = dob;
        this.address = address;
    }

    public Integer getAge() {
        return Period.between(this.dob, LocalDate.now()).getYears();
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", dob=" + dob +
                ", age=" + age +
                ", address='" + address + '\'' +
                '}';
    }
}
