package model.success;

/**
 * SuccessDTO
 * -----------------------------------------------------
 * tbl_success 테이블의 데이터 구조를 저장하는 클래스
 * (한 개의 게시글 객체를 의미)
 * -----------------------------------------------------
 * 테이블 구조:
 *  idx       INT AUTO_INCREMENT PRIMARY KEY
 *  subject   VARCHAR(30)
 *  contents  LONGTEXT
 *  regdate   TIMESTAMP
 *  pass      VARCHAR(20)
 *  readcnt   INT
 *  likes     INT
 *  n_name    VARCHAR(10)
 */
public class SuccessDTO {

    // -------------------------------
    // DB 컬럼과 동일한 멤버 변수
    // -------------------------------
    private int idx;          // 글 번호 (PK)
    private String subject;   // 제목
    private String contents;  // 내용 (LongText)
    private String regdate;   // 작성일자
    private String pass;      // 비밀번호 (수정/삭제용)
    private int readcnt;      // 조회수
    private int likes;        // 좋아요 수
    private String n_name;    // 작성자 닉네임
    private boolean userLiked; // 로그인한 사용자가 좋아요 눌렀는지 여부
    private String hashtag;
	// -------------------------------
    // Getter / Setter
    // -------------------------------
    public int getIdx() {
        return idx;
    }
    public void setIdx(int idx) {
        this.idx = idx;
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

    public String getRegdate() {
        return regdate;
    }
    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }

    public String getPass() {
        return pass;
    }
    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getReadcnt() {
        return readcnt;
    }
    public void setReadcnt(int readcnt) {
        this.readcnt = readcnt;
    }

    public int getLikes() {
        return likes;
    }
    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getN_name() {
        return n_name;
    }
    public void setN_name(String n_name) {
        this.n_name = n_name;
    }
    public boolean isUserLiked() {
		return userLiked;
	}
	public void setUserLiked(boolean userLiked) {
		this.userLiked = userLiked;
	}
	public String getHashtag() {
		return hashtag;
	}
	public void setHashtag(String hashtag) {
		this.hashtag = hashtag;
	}
}
