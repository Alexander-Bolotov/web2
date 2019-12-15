package model;

import service.UserService;

import java.util.Objects;

public class User {

    private Long id;
    private String email;
    private String password;

//    private final UserService userService = UserService.getInstance();

    public User(String email, String password) {
        this.email = email;
        this.password = password;

    }

    public User(Long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            User user = (User)o;
            return Objects.equals(this.getEmail(), user.getEmail()) && Objects.equals(this.getPassword(), user.getPassword());
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(this.getEmail(), this.getPassword());
    }

    public int compareTo(User o) {
        return this.getEmail().compareTo(o.getEmail());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("id=").append(id);
        sb.append(", email='").append(email).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
