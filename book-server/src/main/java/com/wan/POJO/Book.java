package com.wan.POJO;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @author wanfeng
 * @date 2021/3/22 20:17
 */
@Document(collection = "Book")
@org.springframework.data.elasticsearch.annotations.Document(
		indexName = "es-book",
		type = "book",
		refreshInterval = "-1")
public class Book implements Serializable {
	private Object id;
	private Integer bookId;
	private String bookTitle;
	private String bookAuthor;
	private String publishDate;
	private String press;
	private String bookImageUrl;
	/** publishDate : 2002 press : Oxford University Press */
	public String getBookTitle() {
		return bookTitle;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public String getBookitle() {
		return bookTitle;
	}

	public void setBookitle(String bookitle) {
		this.bookTitle = bookitle;
	}

	public String getBookAuthor() {
		return bookAuthor;
	}

	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor;
	}

	public String getBookImageUrl() {
		return bookImageUrl;
	}

	public void setBookImageUrl(String bookImageUrl) {
		this.bookImageUrl = bookImageUrl;
	}

	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}

	public String getPress() {
		return press;
	}

	public void setPress(String press) {
		this.press = press;
	}
}

