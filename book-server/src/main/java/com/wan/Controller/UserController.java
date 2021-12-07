package com.wan.Controller;

import com.wan.POJO.Receive;
import com.wan.POJO.User;
import com.wan.Result.Result;
import com.wan.Service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wanfeng
 * @date 2021/3/22 20:26
 */
@RestController
@Api(tags = "用户管理")
public class UserController {
	@Autowired
	UserService userService;

	@ApiOperation(value = "用户登录")
	@ApiImplicitParam(name = "user", value = "用户实体 user", required = true, dataType = "User")
	@PostMapping("/api/login")
	/**
	 * @description: 接收用户的登录请求
	 * @param user 包含userName和password
	 * @return com.wan.Result.Result
	 */
	public Result getUser(@RequestBody User user) {
		return userService.findUser(user);
	}

	@ApiOperation(value = "用户注册")
	@ApiImplicitParam(name = "user", value = "实体 user", required = true, dataType = "User")
	@PostMapping("/api/register")
	/**
	 * @description:接受用户的注册请求
	 * @param user 包含userName和password
	 * @return com.wan.Result.Result
	 */
	public Result registerUser(@RequestBody User user) {
		return userService.registerUser(user);
	}

	@ApiOperation(
			value = "用户对书籍评分",
			notes = "根据userId,bookId,score封装的receive进行评分，当该书籍存在推荐列表时，进行埋点产生实时推荐结果")
	@ApiImplicitParam(name = "receive", value = "评分 receive", required = true, dataType = "Receive")
	@PostMapping("/api/book/rating")
	/**
	 * @description: 用户评分接口
	 * @param receive 包含userId bookId score
	 * @return com.wan.Result.Result
	 */
	public Result bookRating(@RequestBody Receive receive) {
		return userService.bookRating(receive);
	}

	@ApiOperation(value = "书籍收藏")
	@ApiImplicitParam(name = "receive", value = "实体 receive", required = true, dataType = "Receive")
	@PostMapping("/api/book/favorite")
	/**
	 * @description: 书籍收藏接口
	 * @param receive userId和bookId
	 * @return com.wan.Result.Result
	 */
	public Result bookFavorite(@RequestBody Receive receive) {
		return userService.bookFavorite(receive);
	}

	/*
	 * 浏览记录删除
	 * 收藏记录删除
	 * */

}
