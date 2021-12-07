package com.wan.POJO;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author wanfeng
 * @date 2021/3/22 20:19
 */
@Document(collection = "inc")
public class IncInfo {
	/** collName : User incId : 1 */
	@Field
	private String collName;

	@Field private int incId;

	public String getCollName() {
		return collName;
	}

	public void setCollName(String collName) {
		this.collName = collName;
	}

	public int getIncId() {
		return incId;
	}

	public void setIncId(int incId) {
		this.incId = incId;
	}
}
