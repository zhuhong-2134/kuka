package com.camelot.kuka.model.user;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户账号类型
 * 
 * @author 崔春松
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCredential implements Serializable {

	private static final long serialVersionUID = -958701617717204974L;

	private String username;
	/**
	 * @see com.camelot.kuka.model.user.constants.CredentialType
	 */
	private String type;
	private Long userId;

}
