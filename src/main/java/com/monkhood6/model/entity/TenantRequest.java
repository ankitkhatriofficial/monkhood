package com.monkhood6.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "tenantRequests")
public class TenantRequest implements Serializable {

	private static final long serialVersionUID = -7270129029468296940L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotNull(message = "Name cannot be empty..!")
	@NotBlank(message = "Name cannot be empty..!")
	private String fullName;

	@Pattern(regexp = "[6-9][0-9]{9}", message = "Invalid Phone Number..!")
	@Column(length = 10)
	private String mobile;

	private String alternateMobile;

	private char gender;

	private boolean tenantReqStatus;

	private Date tenantReqDate;

	private Date tenantConfirmDate;

	private String noOfMembers;

	/* Alone-> AL, With Friends-> FR, With GF/BF-> OPP, With Family-> FAM */
	private String wantToLive;

	/*
	 * SameGender-> SAME, Along with opposite gender(Less frequent)-> OPPLF, Along
	 * with opposite gender(More frequent)-> OPPMF,
	 */
	private String entryAllowed;

	private String college;

	private String desiredLocation;

	@ElementCollection
	private List<String> propertyType;

	private String budget;

	private String furnishingReq;

	private String specialReq;

	private String refernce;

	private String bookingFees;

	private boolean bookingFeesStatus;

	private String restFees;

	private boolean restFeesStatus;

	private boolean policeVerification;

	@OneToMany(mappedBy = "tenantRequest", cascade = CascadeType.ALL)
	private List<Document> documents;

	private String rentAgreeMentLink;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE })
	private User requestedBy;

	public TenantRequest() {
		super();
		propertyType = new ArrayList<>();
		documents = new ArrayList<>();
	}

	public String getBookingFees() {
		return bookingFees;
	}

	public boolean isPoliceVerification() {
		return policeVerification;
	}

	public void setPoliceVerification(boolean policeVerification) {
		this.policeVerification = policeVerification;
	}

	public void setBookingFees(String bookingFees) {
		this.bookingFees = bookingFees;
	}

	public Date getTenantConfirmDate() {
		return tenantConfirmDate;
	}

	public void setTenantConfirmDate(Date tenantConfirmDate) {
		this.tenantConfirmDate = tenantConfirmDate;
	}

	public boolean isBookingFeesStatus() {
		return bookingFeesStatus;
	}

	public void setBookingFeesStatus(boolean bookingFeesStatus) {
		this.bookingFeesStatus = bookingFeesStatus;
	}

	public String getRestFees() {
		return restFees;
	}

	public void setRestFees(String restFees) {
		this.restFees = restFees;
	}

	public boolean isRestFeesStatus() {
		return restFeesStatus;
	}

	public void setRestFeesStatus(boolean restFeesStatus) {
		this.restFeesStatus = restFeesStatus;
	}

	public List<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}

	public String getRentAgreeMentLink() {
		return rentAgreeMentLink;
	}

	public void setRentAgreeMentLink(String rentAgreeMentLink) {
		this.rentAgreeMentLink = rentAgreeMentLink;
	}

	public long getId() {
		return id;
	}

	public boolean isTenantReqStatus() {
		return tenantReqStatus;
	}

	public void setTenantReqStatus(boolean tenantReqStatus) {
		this.tenantReqStatus = tenantReqStatus;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAlternateMobile() {
		return alternateMobile;
	}

	public void setAlternateMobile(String alternateMobile) {
		this.alternateMobile = alternateMobile;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public String getNoOfMembers() {
		return noOfMembers;
	}

	public void setNoOfMembers(String noOfMembers) {
		this.noOfMembers = noOfMembers;
	}

	public String getWantToLive() {
		return wantToLive;
	}

	public void setWantToLive(String wantToLive) {
		this.wantToLive = wantToLive;
	}

	public String getEntryAllowed() {
		return entryAllowed;
	}

	public void setEntryAllowed(String entryAllowed) {
		this.entryAllowed = entryAllowed;
	}

	public String getCollege() {
		return college;
	}

	public void setCollege(String college) {
		this.college = college;
	}

	public String getDesiredLocation() {
		return desiredLocation;
	}

	public void setDesiredLocation(String desiredLocation) {
		this.desiredLocation = desiredLocation;
	}

	public List<String> getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(List<String> propertyType) {
		this.propertyType = propertyType;
	}

	public String getBudget() {
		return budget;
	}

	public void setBudget(String budget) {
		this.budget = budget;
	}

	public String getFurnishingReq() {
		return furnishingReq;
	}

	public void setFurnishingReq(String furnishingReq) {
		this.furnishingReq = furnishingReq;
	}

	public String getSpecialReq() {
		return specialReq;
	}

	public void setSpecialReq(String specialReq) {
		this.specialReq = specialReq;
	}

	public String getRefernce() {
		return refernce;
	}

	public void setRefernce(String refernce) {
		this.refernce = refernce;
	}

	public User getRequestedBy() {
		return requestedBy;
	}

	public void setRequestedBy(User requestedBy) {
		this.requestedBy = requestedBy;
	}

	public Date getTenantReqDate() {
		return tenantReqDate;
	}

	public void setTenantReqDate(Date tenantReqDate) {
		this.tenantReqDate = tenantReqDate;
	}

//	@Override
//	public String toString() {
//		return "TenantRequest [id=" + id + ", fullName=" + fullName + ", mobile=" + mobile + ", alternateMobile="
//				+ alternateMobile + ", gender=" + gender + ", tenantReqStatus=" + tenantReqStatus + ", tenantReqDate="
//				+ tenantReqDate + ", tenantConfirmDate=" + tenantConfirmDate + ", noOfMembers=" + noOfMembers
//				+ ", wantToLive=" + wantToLive + ", entryAllowed=" + entryAllowed + ", college=" + college
//				+ ", desiredLocation=" + desiredLocation + ", propertyType=" + propertyType + ", budget=" + budget
//				+ ", furnishingReq=" + furnishingReq + ", specialReq=" + specialReq + ", refernce=" + refernce
//				+ ", bookingFees=" + bookingFees + ", bookingFeesStatus=" + bookingFeesStatus + ", restFees=" + restFees
//				+ ", restFeesStatus=" + restFeesStatus + ", policeVerification=" + policeVerification + ", documents="
//				+ documents + ", rentAgreeMentLink=" + rentAgreeMentLink + ", requestedBy=" + requestedBy + "]";
//	}

	
}
