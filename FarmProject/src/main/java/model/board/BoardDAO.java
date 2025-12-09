package model.board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import util.DBManager;

public class BoardDAO {

	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	//싱글톤 생성
	//생성자-- 객체 생성 밒 객체생성 시 초기화
	private BoardDAO() {}
	
	private static BoardDAO instance = new BoardDAO();//자신의 객체 생성
	
	public static BoardDAO getInstance() { //메소드 정의
		return instance;
	}
	
	//공지사항 최근글 n개
	public List<BoardDTO> boardList(int num) {
		//반환타입
		List<BoardDTO> list = new ArrayList();
		//쿼리
		String sql = "select * from tbl_board order by idx desc limit 0,?";
		try {
			conn = DBManager.getConn();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				BoardDTO dto = new BoardDTO();
				dto.setIdx(rs.getInt("idx"));
				dto.setName(rs.getString("name"));
				dto.setSubject(rs.getString("subject"));
				dto.setContents(rs.getString("contents"));
				dto.setRegdate(rs.getString("regdate"));
				dto.setReadcnt(rs.getInt("readcnt"));
				
				list.add(dto);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			DBManager.close(conn, pstmt,rs);
		}
		return list;
	}
	
	//1. 전체 게시글 수 카운트
	public int boardCount() {
		//반환타입
		int row = 0;
		//쿼리
		String sql = "select count(*) counter from tbl_board";
		try {
			conn = DBManager.getConn();
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				row = rs.getInt("counter");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			DBManager.close(conn, pstmt,rs);
		}
		return row;
	}
	//1-1. 조건에 맞는 게시글 카운트
			public int boardCount(String search,String key) {
				//반환타입
				int row = 0;
				//쿼리
				String sql = "select count(*) counter from tbl_board where "+ search + " like ?";
				try {
					conn = DBManager.getConn();
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, "%"+key+"%");
					
					rs = pstmt.executeQuery();
					if(rs.next()) {
						row = rs.getInt("counter");
					}
				}catch(Exception e) {
					e.printStackTrace();
				}finally{
					DBManager.close(conn, pstmt,rs);
				}
				return row;
			}
	//2.전체 게시글 목록
	public List<BoardDTO> boardList() {
		//반환타입
		List<BoardDTO> list = new ArrayList();
		//쿼리
		String sql = "select * from tbl_board order by regdate desc";
		try {
			conn = DBManager.getConn();
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				BoardDTO dto = new BoardDTO();
				dto.setIdx(rs.getInt("idx"));
				dto.setName(rs.getString("name"));
				dto.setSubject(rs.getString("subject"));
				dto.setRegdate(rs.getString("regdate"));
				dto.setReadcnt(rs.getInt("readcnt"));
				
				list.add(dto);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			DBManager.close(conn, pstmt,rs);
		}
		return list;
	}
	//2-2.조건에 맞는 게시글 목록
			public List<BoardDTO> boardList(String search,String key) {
				//반환타입
				List<BoardDTO> list = new ArrayList();
				//쿼리
				String sql = "select * from tbl_board where "+ search + " like ? order by regdate desc";
				try {
					conn = DBManager.getConn();
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, "%"+key+"%");
					rs = pstmt.executeQuery();
					while(rs.next()) {
						BoardDTO dto = new BoardDTO();
						dto.setIdx(rs.getInt("idx"));
						dto.setName(rs.getString("name"));
						dto.setSubject(rs.getString("subject"));
						dto.setRegdate(rs.getString("regdate"));
						dto.setReadcnt(rs.getInt("readcnt"));
						
						list.add(dto);
					}
				}catch(Exception e) {
					e.printStackTrace();
				}finally{
					DBManager.close(conn, pstmt,rs);
				}
				return list;
			}
	//(예외) 페이지 처리를 위한 메소드
			public List<BoardDTO> boardList(int pagestart,int maxlist) {
				//반환타입
				List<BoardDTO> list = new ArrayList();
				//쿼리
				String sql = "select * from tbl_board order by idx desc limit ?,?";
				try {
					conn = DBManager.getConn();
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, pagestart);
					pstmt.setInt(2, maxlist);
					
					rs = pstmt.executeQuery();
					
					while(rs.next()) {
						BoardDTO dto = new BoardDTO();
						dto.setIdx(rs.getInt("idx"));
						dto.setName(rs.getString("name"));
						dto.setSubject(rs.getString("subject"));
						dto.setRegdate(rs.getString("regdate"));
						dto.setReadcnt(rs.getInt("readcnt"));
						
						list.add(dto);
					}
				}catch(Exception e) {
					e.printStackTrace();
				}finally{
					DBManager.close(conn, pstmt,rs);
				}
				return list;
			}	
			
		//(예외) 페이지 처리를 위한 메소드 (검색 조건 포함)
			public List<BoardDTO> boardList(int pagestart,int maxlist,String search,String key) {
				//반환타입
				List<BoardDTO> list = new ArrayList();
				//쿼리
				String sql = "select * from tbl_board where " + search + " like ? order by idx desc limit ?,?";
				try {
					conn = DBManager.getConn();
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1,"%"+key+"%");
					pstmt.setInt(2, pagestart);
					pstmt.setInt(3, maxlist);
					
					rs = pstmt.executeQuery();
					
					while(rs.next()) {
						BoardDTO dto = new BoardDTO();
						dto.setIdx(rs.getInt("idx"));
						dto.setName(rs.getString("name"));
						dto.setSubject(rs.getString("subject"));
						dto.setRegdate(rs.getString("regdate"));
						dto.setReadcnt(rs.getInt("readcnt"));
						
						list.add(dto);
					}
				}catch(Exception e) {
					e.printStackTrace();
				}finally{
					DBManager.close(conn, pstmt,rs);
				}
				return list;
			}	
			
	//3.게시글 저장(등록) 메소드
		public int boardWrite(BoardDTO dto) {
			int row=0;
			String sql="insert into tbl_board(name,subject,contents,pass) \n"
					+ "	value(?,?,?,?)";
			try {
				conn = DBManager.getConn();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, dto.getName());
				pstmt.setString(2, dto.getSubject());
				pstmt.setString(3, dto.getContents());
				pstmt.setString(4, dto.getPass());
				
				row = pstmt.executeUpdate();
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				DBManager.close(conn, pstmt);
			}
			return row;
		}
	//4.idx에 해당하는 글 검색
		public BoardDTO boardSelect(int idx) {
			BoardDTO dto = new BoardDTO();
			String sql="select * from tbl_board where idx=?";
			try {
				conn = DBManager.getConn();
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setInt(1, idx);
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					dto.setIdx(rs.getInt("idx"));
					dto.setName(rs.getString("name"));
					dto.setSubject(rs.getString("subject"));
					dto.setContents(rs.getString("contents"));
					dto.setRegdate(rs.getString("regdate"));
					dto.setReadcnt(rs.getInt("readcnt"));
					dto.setPass(rs.getString("pass"));
				}
			}catch(Exception e) {
				e.printStackTrace();
			
			}finally {
				DBManager.close(conn, pstmt, rs);
			}
			return dto;
		}
		//5.조회수 증가 메소드
		public void boardHits(int idx) {
			
			String sql="update tbl_board set readcnt = readcnt + 1\n"
					+ "	where idx=?";
			try {
				conn = DBManager.getConn();
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setInt(1, idx);
				
				pstmt.executeUpdate();
				
			}catch(Exception e) {
				e.printStackTrace();
				
			}finally {
				DBManager.close(conn, pstmt);
			}
		}
		//6.게시글 수정 메소드
		public int boardModify(BoardDTO dto) {
			int row=0;
			String sql="update tbl_board set subject=?,contents=? where idx=? and pass=?";
			try {
				conn = DBManager.getConn();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, dto.getSubject());
				pstmt.setString(2, dto.getContents());
				pstmt.setInt(3, dto.getIdx());
				pstmt.setString(4, dto.getPass());

				
				row = pstmt.executeUpdate();
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				DBManager.close(conn, pstmt);
			}
			return row;
		}
		//7.게시글 삭제 메소드
		public int boardDelete(int idx,String pass) {
			int row=0;
			String sql="delete from tbl_board where idx=? and pass=?";
			try {
				conn = DBManager.getConn();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, idx );
				pstmt.setString(2, pass);
				

				
				row = pstmt.executeUpdate();
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				DBManager.close(conn, pstmt);
			}
			return row;
		}
		
		
}
