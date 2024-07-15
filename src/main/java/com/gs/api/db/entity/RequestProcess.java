package com.gs.api.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "SAF_REQUEST")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestProcess {
	
		@Column(name = "ID")
	    @Id
	    @GeneratedValue(strategy = GenerationType.SEQUENCE)
	    private Long id;

		@Column(name = "STATUS")
	    private String status;
		
		@Column(name = "MSG")
	    private String message;
}
