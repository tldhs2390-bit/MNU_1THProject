package model.reply;

public class ReplyDTO {

    private int r_idx;       // 댓글 PK
    private int post_idx;    // 게시글 번호
    private int parent;      // 부모 댓글 (0 = 부모)
    private String n_name;   // 작성자
    private String contents; // 내용
    private String img;      // 이미지 (사용 안 하면 null)
    private String emoji;    // 이모지 (사용 안 하면 null)
    private String regdate;
    private int status; // 1=보임, 0=숨김

    
	
	private int like_cnt;
    private int sym_cnt;
    private int sad_cnt;

    public int getR_idx() {
        return r_idx;
    }
    public void setR_idx(int r_idx) {
        this.r_idx = r_idx;
    }
    public int getPost_idx() {
        return post_idx;
    }
    public void setPost_idx(int post_idx) {
        this.post_idx = post_idx;
    }
    public int getParent() {
        return parent;
    }
    public void setParent(int parent) {
        this.parent = parent;
    }
    public String getN_name() {
        return n_name;
    }
    public void setN_name(String n_name) {
        this.n_name = n_name;
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
    public String getEmoji() {
        return emoji;
    }
    public void setEmoji(String emoji) {
        this.emoji = emoji;
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
    public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}