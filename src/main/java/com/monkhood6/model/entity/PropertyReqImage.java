package com.monkhood6.model.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "propertiesReqImages")
public class PropertyReqImage implements Serializable {

	private static final long serialVersionUID = -1003593348351570679L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String imageURL;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH })
	private PropertyReq propertyReq;

	public PropertyReqImage() {
		super();
	}

	public PropertyReqImage(String imageURL, PropertyReq propertyReq) {
		super();
		this.imageURL = imageURL;
		this.propertyReq = propertyReq;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public PropertyReq getPropertyReq() {
		return propertyReq;
	}

	public void setPropertyReq(PropertyReq propertyReq) {
		this.propertyReq = propertyReq;
	}

}
