package com.inti.formation.shop.api.repository.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "stock")
public class Stock implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 6986667548877223402L;

	@Id
	private Long id;
	private Long quantite;
	private String magasin;
	private boolean active;
	private Long idproduct;
	private Date date;
	private Date datesupp;
	
	
}
