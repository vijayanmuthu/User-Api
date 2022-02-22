package com.user.userapi.valueObject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserdetailVo {

	private int id;
	private String email;
	private String first_name;
	private String last_name;
	private String avatar;
	private String password;
}
