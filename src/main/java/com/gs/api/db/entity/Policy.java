package com.gs.api.db.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CLIENT_POLICY_POC")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Policy {
	
	@Column(name = "ID")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
	
	@Column(name = "POLICY_ID", nullable = false)
	private String policyId;
	
	@Column(name = "POLICY_HOLDER_NAME", nullable = false)
	private String policyHolderName;
	
	@Column(name = "AGE", nullable = false)
	private String age;
	
	@Column(name = "HEALTH_FACTOR", nullable = false)
	private String healthFactor;
	
	@Column(name = "ACTIVATE_DATE", nullable = false)
	private Date activationDate;
	
	@Column(name = "PRODUCT_TYPE", nullable = false)
	private String productType;
	
	@Column(name = "STATUS", nullable = false)
	private String status;
	
	

}
