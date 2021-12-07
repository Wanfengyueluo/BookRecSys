package com.wan.DAO;

import com.wan.POJO.*;
import com.wan.Result.Result;
import com.wan.Result.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wanfeng
 * @date 2021/3/22 20:30
 */
@Component
@CacheConfig(cacheNames = "c1")
public class BookDaoTemp {

	@Autowired
	MongoTemplate mongoTemplate;

	/*TODO:
	 * 1.所有书籍信息，用cache缓存起来
	 * 2.根据id获取书籍信息
	 * 3.用户的相关列表进行缓存
	 *
	 * */

	/**
	 * @description: 获取书籍列表并分页
	 * @param state 当前状态
	 * @param page 页数
	 * @param size 每页大小
	 * @return
	 */
	// TODO :使用Pageable
	public Result getBookList(int state, int page, int size) {
		ArrayList<Book> lists = getBookList();
		//    List<Book> books = mongoTemplate.findAll(Book.class).subList(size * (page - 1), size *
		// page);
		//    for (Book book : books) {
		//      lists.add(book);
		//    }
		if (state == 0) {
			state = 1;
			int total = lists.size();
			return new Result(
					ResultCode.SUCCESS.getCode(),
					"获取书籍成功",
					lists.subList(size * (page - 1), size * page),
					total,
					state);
		} else {
			return new Result(
					ResultCode.SUCCESS.getCode(), "获取书籍成功", lists.subList(size * (page - 1), size * page));
		}
	}

	/**
	 * @description: 获取书籍列表，并初始化书籍表（过滤一些不完整的数据）
	 * @param
	 * @return
	 */
	@Cacheable(key = "#root.methodName")
	public ArrayList<Book> getBookList() {
		ArrayList<Book> bookList = new ArrayList<>();
		List<Book> books = mongoTemplate.findAll(Book.class);
		bookList.addAll(books);
		System.out.println("getBookList...");
		return bookList;
	}

	/**
	 * @description:从RateMoreBooks表中获取评分次数最多的书籍列表
	 * @param
	 * @return
	 */
	public Result getHotBookList() {
		List<RateMoreBooks> rateMoreBooks = mongoTemplate.findAll(RateMoreBooks.class);

		ArrayList<Book> results = new ArrayList<>();
		for (int i = 0; i < rateMoreBooks.size(); i++) {
			findBook(results, rateMoreBooks.get(i).getBookId());
		}
		return new Result(
				ResultCode.SUCCESS.getCode(),
				"获取最热书籍成功",
				results.subList(0, results.size() > 20 ? 20 : results.size()));
	}

	/**
	 * @description:从AverageBooks表中获取评分高并且评分次数大于1的书籍列表
	 * @param
	 * @return
	 */
	public Result getHighBookList() {
		List<AverageBooks> averageBooks = mongoTemplate.findAll(AverageBooks.class);
		ArrayList<Book> results = new ArrayList<>();
		for (int i = 0; i < averageBooks.size(); i++) {
			findBook(results, averageBooks.get(i).getBookId());
		}
		return new Result(
				ResultCode.SUCCESS.getCode(),
				"获取高分书籍成功",
				results.subList(0, results.size() > 20 ? 20 : results.size()));
	}

	/**
	 * @description:从DataLoader将数据导入后需要过滤一些错误数据，每次取数据前调用删除错误数据
	 * @param
	 * @return
	 */
	public void initMongoDB() {
		List<Book> books = mongoTemplate.findAll(Book.class);
		for (Book book : books) {
			if (!book.getBookImageUrl().contains("http")) {
				mongoTemplate.remove(book, "Book");
			}
		}
	}

	/**
	 * @description: 根据推荐列表的bookId获取对应的书籍
	 * @param results
	 * @param bookId
	 * @return
	 */
	public void findBook(ArrayList<Book> results, int bookId) {
		ArrayList<Book> lists = getBookList();
		for (Book book : lists) {
			if (bookId == book.getBookId()) {
				results.add(book);
			}
		}
	}

	/**
	 * @description:获取用户的推荐列表
	 * @param userId
	 * @return
	 */
	public Result getUserBookList(int userId) {
		ArrayList<Book> results = getAllUserBookList(userId);
		return new Result(ResultCode.SUCCESS.getCode(), "获取推荐书籍成功！", results);
	}

	@Cacheable(key = "#root.methodName")
	public ArrayList<Book> getAllUserBookList(int userId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("userId").is(userId));
		List<UserRec> userRecs = mongoTemplate.find(query, UserRec.class);
		ArrayList<Book> results = new ArrayList<>();
		if (userRecs.size() > 0) {
			for (int i = 0; i < userRecs.get(0).getRecs().size(); i++) {
				findBook(results, userRecs.get(0).getRecs().get(i).getBookId());
			}
		}
		System.out.println("getAllUserBookList...");
		return results;
	}

	/**
	 * @description: 获取用户的收藏列表
	 * @param userId
	 * @return
	 */
	public Result getFavoriteBooks(int userId) {
		ArrayList<Book> results = getAllFavoriteBooks(userId);
		return new Result(ResultCode.SUCCESS.getCode(), "获取成功!", results);
	}

	@Cacheable(key = "#root.methodName")
	public ArrayList<Book> getAllFavoriteBooks(int userId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("userId").is(userId));
		List<User> users = mongoTemplate.find(query, User.class);
		ArrayList<Book> results = new ArrayList<>();
		if (users.size() > 0) {
			for (int i = 0; i < users.get(0).getFavorite().size(); i++) {
				findBook(results, users.get(0).getFavorite().get(i).getBookId());
			}
		}
		System.out.println("getAllFavoriteBooks...");
		return results;
	}

	@Cacheable(key = "#root.methodName")
	public Book getOneBookByBookId(int bookId) {
		ArrayList<Book> books = getBookList();
		Book book = new Book();
		for (Book b : books) {
			if (b.getBookId().equals(bookId)) {
				book = b;
				break;
			}
		}

		return book;
	}

	@Cacheable(key = "#root.methodName")
	public ArrayList<AverageBooks> getAverageBooksList() {
		ArrayList<AverageBooks> averageBooksList = new ArrayList<>();
		List<AverageBooks> books = mongoTemplate.findAll(AverageBooks.class);
		averageBooksList.addAll(books);
		return averageBooksList;
	}

	@Cacheable(key = "#root.methodName")
	public ArrayList<User> getAllUsersList() {
		ArrayList<User> usersList = new ArrayList<>();
		List<User> users = mongoTemplate.findAll(User.class);
		usersList.addAll(users);
		return usersList;
	}
	/**
	 * @description:根据bookId获取书籍详情
	 * @param bookId
	 * @param userId
	 * @return
	 */
	public Result getBookByBookId(int bookId, int userId) {
		Book book = getOneBookByBookId(bookId);
		ArrayList<AverageBooks> averageBooks = getAverageBooksList();
		AverageBooks averageBook = new AverageBooks();
		for (AverageBooks b : averageBooks) {
			if (userId == b.getBookId()) {
				averageBook = b;
			}
		}
		ArrayList<User> users = getAllUsersList();
		User user = new User();
		for (User u : users) {
			if (u.getUserId() == userId) {
				user = u;
				break;
			}
		}
		boolean isRating = false;
		int myScore = 0;
		for (int i = 0; i < user.getScoreRecord().size(); i++) {
			if (bookId == user.getScoreRecord().get(i).getBookId()
					&& user.getScoreRecord().get(i).getScore() > 0) {
				System.out.println(user.getScoreRecord().get(i).getBookId());
				isRating = true;
				myScore = user.getScoreRecord().get(i).getScore();
				break;
			}
		}
		if (!isRating) {
			return new Result(ResultCode.SUCCESS.getCode(), "获取成功!未评分", book, averageBook.getAvgScore());
		} else {
			return new Result(
					ResultCode.SUCCESS.getCode(), "获取成功!已评分", book, averageBook.getAvgScore(), myScore, 0, 0);
		}
	}

	/**
	 * @description: 获取该书籍的相似推荐列表
	 * @param bookId
	 * @return
	 */
	public Result getBookRecsByBookId(int bookId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("bookId").is(bookId));
		List<BookRec> lists = mongoTemplate.find(query, BookRec.class);
		ArrayList<Book> books = new ArrayList<>();
		if (lists.size() > 0) {
			for (int i = 0; i < lists.get(0).getRecs().size(); i++) {
				findBook(books, lists.get(0).getRecs().get(i).getBookId());
			}
		}
		return new Result(ResultCode.SUCCESS.getCode(), "获取成功!", books);
	}

	/**
	 * @description:获取该用户的实时推荐列表
	 * @param userId
	 * @return
	 */
	public Result getStreamRecsByBookId(int userId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("userId").is(userId));
		List<StreamRec> lists = mongoTemplate.find(query, StreamRec.class);
		ArrayList<Book> books = new ArrayList<>();
		if (lists.size() > 0) {
			for (int i = 0; i < lists.get(0).getRecs().size(); i++) {
				findBook(books, lists.get(0).getRecs().get(i).getBookId());
			}
		}
		return new Result(ResultCode.SUCCESS.getCode(), "获取成功!", books);
	}
}
