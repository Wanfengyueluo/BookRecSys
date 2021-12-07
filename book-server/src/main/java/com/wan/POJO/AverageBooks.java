package com.wan.POJO;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author wanfeng
 * @date 2021/3/22 20:18
 */
@Document(collection = "AverageBooks")
public class AverageBooks {

	/** bookId : 1060391626 avgScore : 10 */
	private int bookId;

	private int avgScore;

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public int getAvgScore() {
		return avgScore;
	}

	public void setAvgScore(int avgScore) {
		this.avgScore = avgScore;
	}
}
