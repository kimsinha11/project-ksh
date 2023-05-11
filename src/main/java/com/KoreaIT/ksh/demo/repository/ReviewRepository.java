package com.KoreaIT.ksh.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.KoreaIT.ksh.demo.vo.Review;
@Mapper
public interface ReviewRepository {

	public Review getReview(int id);

	public void deleteReview(int id);
	
	@Select("""
			<script>
			SELECT COUNT(*) AS cnt
			FROM review AS A
			WHERE 1
			<if test="boardId != 0">
				AND A.boardId = #{boardId}
			</if>
			<if test="searchKeyword != null and searchKeyword != ''">
			<choose>
				<when test="searchId != null and searchId.intValue() == 1">
					AND title LIKE CONCAT('%', #{searchKeyword}, '%')
				</when>
				<when test="searchId != null and searchId.intValue() == 2">
					AND body LIKE CONCAT('%', #{searchKeyword}, '%')
				</when>
				<otherwise>
					AND (title LIKE CONCAT('%', #{searchKeyword}, '%') OR body LIKE
					CONCAT('%', #{searchKeyword}, '%'))
				</otherwise>
			</choose>
		</if>

			</script>
				""")
	public int getReviewsCount(Integer boardId, Integer searchId, String searchKeyword);
	
	public List<Review> getReviews(Integer boardId, int i, int itemsPerPage, String searchKeyword, Integer searchId);
}
