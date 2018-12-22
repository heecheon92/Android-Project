package model;

public class User {

    private String token;

    public User(){}
    public User(String token)
    {
        this.setToken(token);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
