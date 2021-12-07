package com.wan.Controller;

import com.wan.POJO.PageHelper;
import com.wan.Result.Result;
import com.wan.Service.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wanfeng
 * @date 2021/3/22 20:27
 */
@Api(tags = "书籍管理")
@RestController
public class BookController {

	@Autowired
	BookService bookService;

	@ApiOperation(value = "获取书籍分页列表", notes = "需要state，page，size参数")
	@ApiImplicitParam(
			value = "pageHelper",
			name = "pageHelper",
			required = true,
			dataType = "PageHelper")
	@PostMapping("/api/books")
	/**
	 * @description: 获取所有书籍列表并分页
	 * @param pageHelper page size state
	 * @return com.wan.Result.Result
	 */
	public Result getBookList(@RequestBody PageHelper pageHelper) {
		int state = pageHelper.getState();
		int page = pageHelper.getPage();
		int size = pageHelper.getSize();
		return bookService.getBookList(state, page, size);
	}

	@ApiOperation(value = "获取热门书籍列表")
	@GetMapping("/api/hotBooks")
	/**
	 * @description: 获取热门书籍列表
	 * @param
	 * @return com.wan.Result.Result
	 */
	public Result getHotBookList() {
		return bookService.getHotBookList();
	}

	@ApiOperation(value = "获取高分书籍列表")
	@GetMapping("/api/highBooks")
	/**
	 * @description: 获取高分书籍列表
	 * @param
	 * @return com.wan.Result.Result
	 */
	public Result getHighBookList() {
		return bookService.getHighBookList();
	}

	@ApiOperation(value = "获取用户推荐书籍列表")
	@ApiImplicitParam(value = "userId", name = "userId", required = true, dataType = "int")
	@GetMapping("/api/likeBooks")
	/**
	 * @description: 获取用户的书籍推荐列表
	 * @param userId
	 * @return com.wan.Result.Result
	 */
	public Result getUserBookList(@RequestParam(value = "userId") int userId) {
		return bookService.getUserBookList(userId);
	}

	@ApiOperation(value = "获取用户收藏书籍列表")
	@ApiImplicitParam(value = "userId", name = "userId", required = true, dataType = "int")
	@GetMapping("/api/book/favorite")
	/**
	 * @description: 获取用户收藏书籍列表
	 * @param userId
	 * @return com.wan.Result.Result
	 */
	public Result getFavoriteBooks(@RequestParam(value = "userId") int userId) {
		return bookService.getFavoriteBooks(userId);
	}

	@ApiOperation(value = "获取书籍详情")
	@ApiImplicitParam(
			value = "userId,bookId",
			name = "userId,bookId",
			required = true,
			dataType = "int")
	@GetMapping("/api/books/item")
	/**
	 * @description: 获取书籍详情
	 * @param bookId
	 * @param userId
	 * @return com.wan.Result.Result
	 */
	public Result getBookByBookId(
			@RequestParam(value = "bookId") int bookId, @RequestParam(value = "userId") int userId) {
		return bookService.getBookByBookId(bookId, userId);
	}

	@ApiOperation(value = "获取书籍相似列表")
	@ApiImplicitParam(value = "bookId", name = "bookId", required = true, dataType = "int")
	@GetMapping("/api/books/bookRecs")
	/**
	 * @description: 获取书籍的相似推荐列表
	 * @param bookId
	 * @return com.wan.Result.Result
	 */
	public Result getBookRecsByBookId(@RequestParam(value = "bookId") int bookId) {
		return bookService.getBookRecsByBookId(bookId);
	}

	@ApiOperation(value = "获取用户评价书籍后的实时推荐列表")
	@ApiImplicitParam(value = "userId", name = "userId", required = true, dataType = "int")
	@GetMapping("/api/books/streamRecs")
	/**
	 * @description: 获取用户评价书籍后的实时推荐列表
	 * @param userId
	 * @return com.wan.Result.Result
	 */
	public Result getStreamRecsByBookId(@RequestParam(value = "userId") int userId) {
		return bookService.getStreamRecsByBookId(userId);
	}
}

