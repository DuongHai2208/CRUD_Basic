package com.example.test.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // Thiết lập cho chuỗi id ngẫu nhiên không trùng lặp
    String id;
    String username;
    String password;
    String name;
    LocalDate dob;
    String sex;
    String local;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }
    // Hàm builder thủ công
    public static ClientBuilder builder() {
        return new ClientBuilder();
    }

    // Lớp ClientBuilder thủ công
    public static class ClientBuilder {
        private String username;
        private String password;
        private String name;
        private LocalDate dob;
        private String sex;
        private String local;

        ClientBuilder() {
        }

        public ClientBuilder username(String username) {
            this.username = username;
            return this;
        }

        public ClientBuilder password(String password) {
            this.password = password;
            return this;
        }

        public ClientBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ClientBuilder dob(LocalDate dob) {
            this.dob = dob;
            return this;
        }

        public ClientBuilder sex(String sex) {
            this.sex = sex;
            return this;
        }

        public ClientBuilder local(String local) {
            this.local = local;
            return this;
        }

        public Client build() {
            Client client = new Client();
            client.setUsername(this.username);
            client.setPassword(this.password);
            client.setName(this.name);
            client.setDob(this.dob);
            client.setSex(this.sex);
            client.setLocal(this.local);
            return client;
        }
    }
}
