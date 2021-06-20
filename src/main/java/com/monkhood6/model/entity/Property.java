package com.monkhood6.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "properties")
public class Property implements Serializable {

	private static final long serialVersionUID = 4227487964040409748L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotBlank(message = "Please fill out this field..!")
	private String locality;

	@NotBlank(message = "Please fill out this field..!")
	private String address;

	@NotBlank(message = "Please fill out this field..!")
	private String state;

	@NotBlank(message = "Please fill out this field..!")
	@Pattern(regexp = "[\\d]{6}", message = "Invalid Pincode..!")
	private String pincode;

	@Pattern(regexp = "^[0-9]$", message = "Floor Number must be between 0 to 9..!")
	private String floorNo;

	@NotBlank(message = "Please fill out this field..!")
	private String propertyType;

	@ElementCollection
	private List<String> tenantType;

	private char tenantGender;

	/* Y-> YES, N-> NO, M-> Moderate */
	private char freeEntry;

	private boolean coupledAllowed;

	@NotBlank(message = "Please fill out this field..!")
	private String rent;

	@NotBlank(message = "Please fill out this field..!")
	@Pattern(regexp = "^([0-1]?[0-9]|24)$", message = "Security Fee must be between 0 to 24..!")
	private String securityMonth;

	@ElementCollection
	private List<String> amenitiesProvided;

	@NotBlank(message = "Please fill out this field..!")
	@Pattern(regexp = "^[1-5]", message = "Invalid Rating..!")
	private String rating;

	private String description;

	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE })
	private User owner;

	private boolean availability;

	private Date propertyConfirmationDateTime;

	@OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
	private List<PropertyImage> propertyImages;

	public Property() {
		super();
		tenantType = new ArrayList<>();
		amenitiesProvided = new ArrayList<>();
		propertyImages = new ArrayList<>();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getFloorNo() {
		return floorNo;
	}

	public void setFloorNo(String floorNo) {
		this.floorNo = floorNo;
	}

	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	public char getTenantGender() {
		return tenantGender;
	}

	public void setTenantGender(char tenantGender) {
		this.tenantGender = tenantGender;
	}

	public List<String> getTenantType() {
		return tenantType;
	}

	public void setTenantType(List<String> tenantType) {
		this.tenantType = tenantType;
	}

	public char getFreeEntry() {
		return freeEntry;
	}

	public void setFreeEntry(char freeEntry) {
		this.freeEntry = freeEntry;
	}

	public boolean isCoupledAllowed() {
		return coupledAllowed;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public List<PropertyImage> getPropertyImages() {
		return propertyImages;
	}

	public void setPropertyImages(List<PropertyImage> propertyImages) {
		this.propertyImages = propertyImages;
	}

	public void setCoupledAllowed(boolean coupledAllowed) {
		this.coupledAllowed = coupledAllowed;
	}

	public String getRent() {
		return rent;
	}

	public void setRent(String rent) {
		this.rent = rent;
	}

	public String getSecurityMonth() {
		return securityMonth;
	}

	public void setSecurityMonth(String securityMonth) {
		this.securityMonth = securityMonth;
	}

	public List<String> getAmenitiesProvided() {
		return amenitiesProvided;
	}

	public void setAmenitiesProvided(List<String> amenitiesProvided) {
		this.amenitiesProvided = amenitiesProvided;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isAvailability() {
		return availability;
	}

	public void setAvailability(boolean availability) {
		this.availability = availability;
	}

	public Date getPropertyConfirmationDateTime() {
		return propertyConfirmationDateTime;
	}

	public void setPropertyConfirmationDateTime(Date propertyConfirmationDateTime) {
		this.propertyConfirmationDateTime = propertyConfirmationDateTime;
	}

}
