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
@Table(name = "documents")
public class Document implements Serializable {

	private static final long serialVersionUID = 6453535707744159130L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String type;

	private String docURL;

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE })
	private TenantRequest tenantRequest;

	public Document() {

	}

	public Document(String type, String docURL, TenantRequest tenantRequest) {
		this.type = type;
		this.docURL = docURL;
		this.tenantRequest = tenantRequest;
	}

	public TenantRequest getTenantRequest() {
		return tenantRequest;
	}

	public void setTenantRequest(TenantRequest tenantRequest) {
		this.tenantRequest = tenantRequest;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDocURL() {
		return docURL;
	}

	public void setDocURL(String docURL) {
		this.docURL = docURL;
	}
//
//	@Override
//	public String toString() {
//		return "Document [id=" + id + ", type=" + type + ", docURL=" + docURL + ", tenantRequest=" + tenantRequest
//				+ "]";
//	}

}
