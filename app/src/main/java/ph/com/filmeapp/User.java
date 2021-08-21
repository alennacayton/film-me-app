package ph.com.filmeapp;

public class User {
    private String email;
    private String password;
    private String name;
    private String username;


    public User(String email, String password, String name, String username)
    {
        this.email = email;
        this.password = password;
        this.name = name;
        this.username = username;
    }

    public String getEmail() { return this.email; }

    public String getPassword() { return this.password; }

    public String getName() { return this.name; }

    public String getUsername() { return this.username; }


}
