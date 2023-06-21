package com.hellobody.model.dao;

import java.util.List;

import com.hellobody.model.dto.Review;

public interface ReviewDao {
	public int insertReview(Review review);

	public int updateReview(Review review);

	public int deleteReview(int rId);
	
	public List<Review> getReviewInRecord(int cId);

}
