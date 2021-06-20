package com.monkhood6.model.dto;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class TenantReqDto {

	private long id;

	@NotNull(message = "Name cannot be empty..!")
	@NotBlank(message = "Name cannot be empty..!")
	private String fullName;

	@Pattern(regexp = "[6-9][0-9]{9}", message = "Invalid Phone Number..!")
	@Column(length = 10)
	private String mobile;

	private String alternateMobile;

	private char gender;

	private LinkedHashMap<Character, String> genderOptions;

	private boolean tenantReqStatus;

	private String noOfMembers;

	/* Alone-> AL, With Friends-> FR, With GF/BF-> OPP, With Family-> FAM */
	private String wantToLive;

	private LinkedHashMap<String, String> wantToLiveOptions;

	/*
	 * SameGender-> SAME, Along with opposite gender(Less frequent)-> OPPLF, Along
	 * with opposite gender(More frequent)-> OPPMF,
	 */
	private String entryAllowed;

	private LinkedHashMap<String, String> entryAllowedOptions;

	private String college;

	private String desiredLocation;

	private List<String> propertyType;

	private LinkedHashMap<String, String> propertyTypeOptions;

	private String budget;

	private String furnishingReq;

	private String specialReq;

	private String refernce;

	private String bookingFees;

	private boolean bookingFeesStatus;

	private LinkedHashMap<Boolean, String> bookingFeesStatusOptions;

	private String restFees;

	private boolean restFeesStatus;

	private boolean policeVerification;

	private LinkedHashMap<Boolean, String> policeVerificationOptions;

	private String rentAgreeMentLink;

	private String userId;

	public TenantReqDto() {
		super();
		propertyType = new ArrayList<>();
		propertyTypeOptions = new LinkedHashMap<>();
		propertyTypeOptions.put("1ROOM", "1ROOM");
		propertyTypeOptions.put("1BHK", "1BHK");
		propertyTypeOptions.put("2BHK", "2BHK");
		propertyTypeOptions.put("3BHK", "3BHK");
		propertyTypeOptions.put("4BHK", "4BHK");
		propertyTypeOptions.put("PG", "PG");

		wantToLiveOptions = new LinkedHashMap<>();
		wantToLiveOptions.put("AL", "Alone");
		wantToLiveOptions.put("FR", "With Friends");
		wantToLiveOptions.put("OPP", "With girlfriend/boyfriend");
		wantToLiveOptions.put("FAM", "With Family");

		entryAllowedOptions = new LinkedHashMap<>();
		entryAllowedOptions.put("SAME", "Only of same gender");
		entryAllowedOptions.put("OPPLF", "Along with opposite gender- Less frequently");
		entryAllowedOptions.put("OPPMF", "Along with opposite gender- Frequently");

		genderOptions = new LinkedHashMap<>();
		genderOptions.put('M', "Male");
		genderOptions.put('F', "Female");
		genderOptions.put('O', "Other");

		bookingFeesStatusOptions = new LinkedHashMap<>();
		bookingFeesStatusOptions.put(false, "Unpaid");
		bookingFeesStatusOptions.put(true, "Paid");

		policeVerificationOptions = new LinkedHashMap<>();
		policeVerificationOptions.put(false, "Not yet");
		policeVerificationOptions.put(true, "Done");
	}

	public LinkedHashMap<Boolean, String> getPoliceVerificationOptions() {
		return policeVerificationOptions;
	}

	public void setPoliceVerificationOptions(LinkedHashMap<Boolean, String> policeVerificationOptions) {
		this.policeVerificationOptions = policeVerificationOptions;
	}

	public LinkedHashMap<Boolean, String> getBookingFeesStatusOptions() {
		return bookingFeesStatusOptions;
	}

	public void setBookingFeesStatusOptions(LinkedHashMap<Boolean, String> bookingFeesStatusOptions) {
		this.bookingFeesStatusOptions = bookingFeesStatusOptions;
	}

	public long getId() {
		return id;
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

	public LinkedHashMap<Character, String> getGenderOptions() {
		return genderOptions;
	}

	public void setGenderOptions(LinkedHashMap<Character, String> genderOptions) {
		this.genderOptions = genderOptions;
	}

	public boolean isTenantReqStatus() {
		return tenantReqStatus;
	}

	public void setTenantReqStatus(boolean tenantReqStatus) {
		this.tenantReqStatus = tenantReqStatus;
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

	public LinkedHashMap<String, String> getWantToLiveOptions() {
		return wantToLiveOptions;
	}

	public void setWantToLiveOptions(LinkedHashMap<String, String> wantToLiveOptions) {
		this.wantToLiveOptions = wantToLiveOptions;
	}

	public String getEntryAllowed() {
		return entryAllowed;
	}

	public void setEntryAllowed(String entryAllowed) {
		this.entryAllowed = entryAllowed;
	}

	public LinkedHashMap<String, String> getEntryAllowedOptions() {
		return entryAllowedOptions;
	}

	public void setEntryAllowedOptions(LinkedHashMap<String, String> entryAllowedOptions) {
		this.entryAllowedOptions = entryAllowedOptions;
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

	public LinkedHashMap<String, String> getPropertyTypeOptions() {
		return propertyTypeOptions;
	}

	public void setPropertyTypeOptions(LinkedHashMap<String, String> propertyTypeOptions) {
		this.propertyTypeOptions = propertyTypeOptions;
	}

	public String getBudget() {
		return budget;
	}

	public void setBudget(String budget) {
		this.budget = budget;
	}

	public boolean isPoliceVerification() {
		return policeVerification;
	}

	public void setPoliceVerification(boolean policeVerification) {
		this.policeVerification = policeVerification;
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

	public String getBookingFees() {
		return bookingFees;
	}

	public void setBookingFees(String bookingFees) {
		this.bookingFees = bookingFees;
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

	public String getRentAgreeMentLink() {
		return rentAgreeMentLink;
	}

	public void setRentAgreeMentLink(String rentAgreeMentLink) {
		this.rentAgreeMentLink = rentAgreeMentLink;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "TenantReqDto [id=" + id + ", fullName=" + fullName + ", mobile=" + mobile + ", alternateMobile="
				+ alternateMobile + ", gender=" + gender + ", genderOptions=" + genderOptions + ", tenantReqStatus="
				+ tenantReqStatus + ", noOfMembers=" + noOfMembers + ", wantToLive=" + wantToLive
				+ ", wantToLiveOptions=" + wantToLiveOptions + ", entryAllowed=" + entryAllowed
				+ ", entryAllowedOptions=" + entryAllowedOptions + ", college=" + college + ", desiredLocation="
				+ desiredLocation + ", propertyType=" + propertyType + ", propertyTypeOptions=" + propertyTypeOptions
				+ ", budget=" + budget + ", furnishingReq=" + furnishingReq + ", specialReq=" + specialReq
				+ ", refernce=" + refernce + ", bookingFees=" + bookingFees + ", bookingFeesStatus=" + bookingFeesStatus
				+ ", bookingFeesStatusOptions=" + bookingFeesStatusOptions + ", restFees=" + restFees
				+ ", restFeesStatus=" + restFeesStatus + ", policeVerification=" + policeVerification
				+ ", policeVerificationOptions=" + policeVerificationOptions + ", rentAgreeMentLink="
				+ rentAgreeMentLink + ", userId=" + userId + "]";
	}

}
