package com.techmanage.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Entity
@Table(name = "users")
@JsonPropertyOrder({"id", "fullName", "email", "phone", "birthDate", "userType", "address"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome completo é obrigatório")
    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Email(message = "Email deve ter um formato válido")
    @NotBlank(message = "Email é obrigatório")
    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "address")
    private String address;

    @NotBlank(message = "Telefone é obrigatório")
    @Pattern(regexp = "^\\+\\d{1,3}\\s\\d{2}\\s\\d{4,5}-\\d{4}$", message = "Telefone deve estar no formato internacional (ex: +55 11 99999-9999)")
    @Column(nullable = false)
    private String phone;

    @Past(message = "Data de nascimento deve estar no passado")
    @NotNull(message = "Data de nascimento é obrigatória")
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Tipo de usuário é obrigatório")
    @Column(name = "user_type", nullable = false)
    private UserType userType;

    public User() {
    }

public User(String fullName, String email, String phone, LocalDate birthDate, UserType userType, String address) {
    this.fullName = fullName;
    this.email = email;
    this.phone = phone;
    this.birthDate = birthDate;
    this.userType = userType;
    this.address = address;
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

    public String getAddress() {
    return address;
    }

    public void setAddress(String address) {
    this.address = address;
    }

    @JsonGetter("phone")
    public String getPhone() {
        if (phone != null && phone.matches("^\\+\\d{1,3}\\d{2}\\d{4,5}\\d{4}$")) {
            // Se o telefone está sem formatação, aplicar formato +XX XX XXXXX-XXXX
            String digits = phone.substring(1); // Remove o +
            if (digits.length() >= 10) {
                String countryCode = digits.substring(0, 2);
                String areaCode = digits.substring(2, 4);
                String firstPart = digits.substring(4, digits.length() - 4);
                String lastPart = digits.substring(digits.length() - 4);
                return "+" + countryCode + " " + areaCode + " " + firstPart + "-" + lastPart;
            }
        }
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
            ", address='" + address + '\'' +
            '}';
}
}