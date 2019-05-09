package json;

public class User {
    private String id; //Why not have username? That makes more sense right? Why yes it does. However, the form would not work with username and I don't know why. So it is ID now.
    private String password;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}

