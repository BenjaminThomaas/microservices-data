package com.inti.formation.data.kafka.message;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.Data;

@Document(indexName="shop",type="stock")
@Data
public class Event {
	
	@Id
	private Long id;
	@Field(type = FieldType.Long)
	private Long quantite;
	@Field(type = FieldType.Keyword)
	private String magasin;
	@Field(type = FieldType.Boolean)
	private boolean active;
	@Field(type = FieldType.Long)
	private Long idproduct;
	@Field(type = FieldType.Date)
	private Date date;
	@Field(type = FieldType.Date)
	private Date datesupp;
}
