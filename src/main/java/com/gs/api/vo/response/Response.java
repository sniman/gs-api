
/**
 * 
 * @author norirman
 * @desc -
 * © 2024 snoriman@gmail.com
 * Zurich POC for insurance open api
 */
package com.gs.api.vo.response;

import java.io.Serializable;

import com.gs.api.db.entity.AuthUser;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Response implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private AuthUser user;

}