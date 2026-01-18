package model.growth;

public class GrowthDTO {

    private int idx;
    private String category;
    private String subject;
    private String contents;
    private String img;
    private String hashtags;

    private String n_name;
    private String pass;
    private String regdate;

    private int readcnt;
    private int like_cnt;
    private int sym_cnt;
    private int sad_cnt;
 // ⭐ 관리자 기능을 위한 필드
    private int status;   // 1: 보임 / 0: 숨김


    // ====== Getter / Setter ======

    
	public int getIdx() {
        return idx;
    }
    public void setIdx(int idx) {
        this.idx = idx;
    }

    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContents() {
        return contents;
    }
    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getImg() {
        return img;
    }
    public void setImg(String img) {
        this.img = img;
    }

    public String getHashtags() {
        return hashtags;
    }
    public void setHashtags(String hashtags) {
        this.hashtags = hashtags;
    }

    public String getN_name() {
        return n_name;
    }
    public void setN_name(String n_name) {
        this.n_name = n_name;
    }

    public String getPass() {
        return pass;
    }
    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getRegdate() {
        return regdate;
    }
    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }

    public int getReadcnt() {
        return readcnt;
    }
    public void setReadcnt(int readcnt) {
        this.readcnt = readcnt;
    }

    public int getLike_cnt() {
        return like_cnt;
    }
    public void setLike_cnt(int like_cnt) {
        this.like_cnt = like_cnt;
    }

    public int getSym_cnt() {
        return sym_cnt;
    }
    public void setSym_cnt(int sym_cnt) {
        this.sym_cnt = sym_cnt;
    }

    public int getSad_cnt() {
        return sad_cnt;
    }
    public void setSad_cnt(int sad_cnt) {
        this.sad_cnt = sad_cnt;
    }
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
    
}
