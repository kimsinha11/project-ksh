package com.KoreaIT.ksh.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KoreaIT.ksh.demo.repository.ArticleRepository;
import com.KoreaIT.ksh.demo.repository.ReviewRepository;
import com.KoreaIT.ksh.demo.vo.Review;

@Service
public class ReviewService {
	@Autowired
	private ReviewRepository reviewRepository;

	public ReviewService(ReviewRepository reviewRepository) {
		this.reviewRepository = reviewRepository;
	}

	public Review getReview(int id) {
		return reviewRepository.getReview(id);
	}

	public void deleteReview(int id) {
		reviewRepository.deleteReview(id);
	}

	public int getReviewsCount(Integer boardId, Integer searchId, String searchKeyword) {
		return reviewRepository.getReviewsCount(boardId, searchId, searchKeyword);
	}

	public List<Review> getReviews(Integer boardId, int i,int itemsPerPage, String searchKeyword,
			Integer searchId) {
		return reviewRepository.getReviews(boardId, i, itemsPerPage, searchKeyword, searchId);
	}

}
