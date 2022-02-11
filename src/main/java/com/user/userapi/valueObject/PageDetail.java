package com.user.userapi.valueObject;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageDetail {

	public int page;
	public int per_page;
	public int total;
	public int total_pages;
	public List<UserdetailVo> data;
}
