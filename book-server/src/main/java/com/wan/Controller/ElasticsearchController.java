package com.wan.Controller;

import com.wan.POJO.Book;
import com.wan.Result.Result;
import com.wan.Result.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wanfeng
 * @date 2021/3/22 20:27
 */
@Api(tags = "ES全局搜索")
@RestController
public class ElasticsearchController {
	@Autowired
	ElasticsearchTemplate elasticsearchTemplate;
	@Autowired
	MongoTemplate mongoTemplate;

	@ApiOperation(value = "向ES中存储书籍数据")
	@GetMapping("/api/es/save")
	public void save() {
		if (!elasticsearchTemplate.indexExists("es-book")) {
			elasticsearchTemplate.createIndex("es-book");
			System.out.println("---");
		}
		System.out.println("---+++");
		List<IndexQuery> queries = new ArrayList<>();
		List<Book> books = mongoTemplate.findAll(Book.class);
		if (books != null && books.size() > 0) {
			for (Book book : books) {
				IndexQuery indexQuery = new IndexQuery();
				indexQuery.setId(book.getBookId().toString());
				indexQuery.setObject(book);
				indexQuery.setIndexName(("es-book"));
				indexQuery.setType("book");
				queries.add(indexQuery);
			}
		}
		elasticsearchTemplate.bulkIndex(queries);
	}

	@ApiOperation(value = "获取书籍列表", notes = "根据搜索词在书名和作者搜索所有书籍")
	@ApiImplicitParam(name = "keyword", value = "keyword", required = true, dataType = "String")
	@GetMapping("/api/es/queryBook")
	public Result queryBook(
			@RequestParam(value = "keyword") String keyword,
			@RequestParam(value = "state") int state,
			@RequestParam(value = "page") int page,
			@RequestParam(value = "size") int size) {
		SearchQuery searchQuery =
				new NativeSearchQueryBuilder()
						.withQuery(QueryBuilders.multiMatchQuery(keyword, "bookTitle", "bookAuthor"))
						.build();
		List<Book> lists = elasticsearchTemplate.queryForList(searchQuery, Book.class);
		if (lists.size() < size) {
			size = lists.size();
		}
		if (state == 0) {
			state = 1;
			return new Result(
					ResultCode.SUCCESS.getCode(),
					"OK",
					lists.subList(size * (page - 1), size * page),
					lists.size(),
					state);
		} else {
			return new Result(
					ResultCode.SUCCESS.getCode(), "OK", lists.subList(size * (page - 1), size * page));
		}
	}
}

