/**
 * 
 * @author norirman
 * @desc -
 * © 2024 snoriman@gmail.com
 * Zurich POC for insurance open api
 */
package com.gs.api.db.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Nationalized;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;


@Entity
@Table(name = "CLIENT_TOKEN_POC")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthUser {

	@Id
	@Column(name = "TOKEN_ID")
	private long id;

	@Column(name = "USERNAME", nullable = false)
	private String username;

	@Column(name = "PASSWORD", nullable = false)
	private String password;

	@Column(name = "TOKEN", nullable = false)
	private String token;

	@Column(name = "CREATE_DATE", nullable = false)
	private Date createDate;

	@Column(name = "UPDATE_DATE", nullable = false)
	private Date updateDate;

	@Column(name = "TOKEN_ISSUE_DATE", nullable = false)
	private Date issueDate;

	@Column(name = "TOKEN_EXPIRED_DATE", nullable = false)
	private Date expiredDate;

}
