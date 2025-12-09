package model.success.user;

public class UserDTO {

    private String userid;
    private String n_name;
    private String regdate;
    private String email;

    // Getter / Setter
    public String getUserid() { return userid; }
    public void setUserid(String userid) { this.userid = userid; }

    public String getN_name() { return n_name; }
    public void setN_name(String n_name) { this.n_name = n_name; }

    public String getRegdate() { return regdate; }
    public void setRegdate(String regdate) { this.regdate = regdate; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
