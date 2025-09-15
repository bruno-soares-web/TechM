package com.techmanage.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome completo é obrigatório")
    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Email(message = "Email deve ter formato válido")
    @NotBlank(message = "Email é obrigatório")
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank(message = "Telefone é obrigatório")
    @Pattern(regexp = "^\\+[1-9]\\d{1,14}$", message = "Telefone deve estar no formato internacional (ex: +55 11 99999-9999)")
    @Column(nullable = false)
    private String phone;

    @Past(message = "Data de nascimento deve ser no passado")
    @NotNull(message = "Data de nascimento é obrigatória")
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Tipo de usuário é obrigatório")
    @Column(name = "user_type", nullable = false)
    private UserType userType;

    public User() {
    }

    public User(String fullName, String email, String phone, LocalDate birthDate, UserType userType) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.birthDate = birthDate;
        this.userType = userType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", birthDate=" + birthDate +
                ", userType=" + userType +
                '}';
    }
}