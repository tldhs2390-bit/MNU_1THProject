package model.success.reply;

import java.util.List;

public class ReplyDTO {

    private int c_idx;      // 댓글 PK
    private int idx;        // 원글 번호
    private String nickname; // DB에서 가져오는 실제 닉네임
    private String n_name;   // JSP에서 사용하는 닉네임(=nickname)
    private String contents;
    private String regdate;
    private int parent;     // 0 = 일반댓글, >0 = 대댓글
    private int likes;      // 좋아요 수 (댓글용)

    // ⭐ 대댓글 리스트 저장용
    private List<ReplyDTO> replyList;


    // ============================
    // Getter / Setter
    // ============================

    public int getC_idx() { return c_idx; }
    public void setC_idx(int c_idx) { this.c_idx = c_idx; }

    public int getIdx() { return idx; }
    public void setIdx(int idx) { this.idx = idx; }

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { 
        this.nickname = nickname; 
        this.n_name = nickname;  // JSP 호환용
    }

    public String getN_name() { return n_name; }
    public void setN_name(String n_name) { 
        this.n_name = n_name;
        this.nickname = n_name; // 양쪽 일치
    }

    public String getContents() { return contents; }
    public void setContents(String contents) { this.contents = contents; }

    public String getRegdate() { return regdate; }
    public void setRegdate(String regdate) { this.regdate = regdate; }

    public int getParent() { return parent; }
    public void setParent(int parent) { this.parent = parent; }

    public int getLikes() { return likes; }
    public void setLikes(int likes) { this.likes = likes; }

    public List<ReplyDTO> getReplyList() { return replyList; }
    public void setReplyList(List<ReplyDTO> replyList) { this.replyList = replyList; }
}