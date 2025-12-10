package model.user;

public class UserDTO {

    private String user_name;
    private String n_name;
    private String tel;
    private String email;
    private String address;
    private int idx;
    private String user_rank;   // (기존 유지)
    private int point;
    private String user_id;
    private String user_pass;

    // ⭐ 추가된 필드: 등급 뱃지 (🌰 🐥 🌿 🐄 🌳)
    private String badge; 

    // ============================================
    // Getter / Setter
    // ============================================

    public int getIdx() {
        return idx;
    }
    public void setIdx(int idx) {
        this.idx = idx;
    }

    public String getUser_rank() {
        return user_rank;
    }
    public void setUser_rank(String user_rank) {
        this.user_rank = user_rank;
    }

    public int getPoint() {
        return point;
    }
    public void setPoint(int point) {
        this.point = point;
    }

    public String getUser_id() {
        return user_id;
    }
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_pass() {
        return user_pass;
    }
    public void setUser_pass(String user_pass) {
        this.user_pass = user_pass;
    }

    public String getUser_name() {
        return user_name;
    }
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getN_name() {
        return n_name;
    }
    public void setN_name(String n_name) {
        this.n_name = n_name;
    }

    public String getTel() {
        return tel;
    }
    public void setTel(String tel) {
        this.tel = tel;
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

    // ⭐ 추가된 getter/setter (배지)
    public String getBadge() {
        return badge;
    }
    public void setBadge(String badge) {
        this.badge = badge;
    }
}