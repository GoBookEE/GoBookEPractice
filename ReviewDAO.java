import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.jdbc.common.JDBCTemplate;
import com.jdbc.model.dto.MemberDTO;

public class ReviewDAO {
	private PreparedStatement pstmt;
	private ResultSet rs;
	private Properties sqlProp = new Properties();

	private static ReviewDao dao;

	private ReviewDao() {
		String path=ReviewDao.class.getResource("/sql/review_sql.properties").getPath();
		try(FileReader fr=new FileReader(path)){
			sqlProp.load(fr);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	public static ReviewDao reviewDao() {
		if (dao == null)
			dao = new ReviewDao();
		return dao;
	}

	public List<ReviewDTO> getAllReviews(Connection conn) {
		List<ReviewDTO> reviews = new ArrayList();
		try {
			pstmt = conn.prepareStatement(sqlProp.getProperty("getAllReviews"));
			rs = pstmt.executeQuery();
			while (rs.next())
				reviews.add(getReviewDTO(rs));

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
		}
		return reviews;
	}

	public List<ReviewDTO> getBestReviews(Connection conn) {
		List<ReviewDTO> reviews = new ArrayList();
		try {
			pstmt = conn.prepareStatement(sqlProp.getProperty("getBestReviews"));
			rs = pstmt.executeQuery();
			while (rs.next())
				reviews.add(getReviewDTO(rs));

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
		}
		return reviews;
	}

	public int insertReview(Connection conn, ReviewDTO dto) {
		int result = 0;
		try {
			pstmt = conn.prepareStatement(sqlProp.getProperty("insertReview"));
			pstmt.setString(1, dto.getReviewTitle());
			pstmt.setString(2, dto.getReviewContents());
			pstmt.setInt(3, dto.getReviewRate());
			pstmt.setInt(4, dto.getUserSeq());
			pstmt.setInt(5, dto.getBookSeq());
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
		}
		return result;
	}

	public int updateReview(Connection conn, ReviewDTO dto) {
		int result = 0;
		try {
			pstmt = conn.prepareStatement(sqlProp.getProperty("updateReview"));
			pstmt.setString(1, dto.getReviewTitle());
			pstmt.setString(2, dto.getReviewContents());
			pstmt.setInt(3, dto.getReviewRate());
			pstmt.setInt(4, dto.getReviewSeq());
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}

	public ReviewDTO getReviewBySeq(Connection conn, Integer reviewSeq) {
		ReviewDTO dto = new ReviewDTO();
		try {
			pstmt = conn.prepareStatement(sqlProp.getProperty("getReviewBySeq"));
			pstmt.setString(1, reviewSeq);
			rs = pstmt.executeQuery();
			if (rs.next())
				dto = getReviewDTO(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
		}
		return dto;
	}

	private ReviewDTO getReviewDTO(ResultSet rs) throws SQLException {
		ReviewDTO dto = ReviewDTO.builder().reviewSeq(rs.getInt("REVIEW_SEQ")).reviewTitle(rs.getString("REVIEW_TITLE"))
				.reviewContents(rs.getString("REVIEW_CONTENTS")).reviewCreateTime(rs.getTimestamp("REVIEW_CREATE_TIME"))
				.reviewRate(rs.getInt("REVIEW_RATE")).reviewEditTime(rs.getTimestamp("REVIEW_EDIT_TIME"))
				.userSeq(rs.getInt("USER_SEQ")).bookSeq(rs.getInt("BOOK_SEQ")).build();
		return dto;
	}
}
