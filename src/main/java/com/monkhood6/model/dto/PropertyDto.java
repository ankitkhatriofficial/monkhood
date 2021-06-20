package com.monkhood6.model.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class PropertyDto {

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

	private LinkedHashMap<Integer, Integer> floorNoOptions;

	@NotBlank(message = "Please fill out this field..!")
	private String propertyType;

	public LinkedHashMap<String, String> propertyTypeOptions;

	@ElementCollection
	private List<String> tenantType;

	public List<String> tenantOptions;

	private char tenantGender;

	private LinkedHashMap<Character, String> tenantGenderOptions;

	/* Y-> YES, N-> NO, M-> Moderate */
	private char freeEntry;

	private LinkedHashMap<Character, String> freeEntryOptions;

	private boolean coupledAllowed;

	private LinkedHashMap<Boolean, String> coupledAllowedOptions;

	@NotBlank(message = "Please fill out this field..!")
	private String rent;

	@NotBlank(message = "Please fill out this field..!")
	@Pattern(regexp = "^([0-1]?[0-9]|24)$", message = "Security Fee must be between 0 to 24..!")
	private String securityMonth;

	@ElementCollection
	private List<String> amenitiesProvided;

	public List<String> amenitiesOptions;

	@NotBlank(message = "Please fill out this field..!")
	@Pattern(regexp = "^[1-5]", message = "Invalid Rating..!")
	private String rating;

	private LinkedHashMap<Integer, Integer> ratingOptions;

	private String description;

	private String ownerId;

	private boolean availability;

	private LinkedHashMap<Boolean, String> availabilityOptions;

	public PropertyDto() {
		super();
		propertyTypeOptions = new LinkedHashMap<>();
		propertyTypeOptions.put("1ROOM", "1ROOM");
		propertyTypeOptions.put("1BHK", "1BHK");
		propertyTypeOptions.put("2BHK", "2BHK");
		propertyTypeOptions.put("3BHK", "3BHK");
		propertyTypeOptions.put("4BHK", "4BHK");
		propertyTypeOptions.put("PG", "PG");

		tenantType = new ArrayList<>();
		tenantOptions = new ArrayList<>(Arrays.asList("Student", "Working Bachelors", "Family"));

		tenantGenderOptions = new LinkedHashMap<>();
		tenantGenderOptions.put('M', "Male");
		tenantGenderOptions.put('F', "Female");
		tenantGenderOptions.put('O', "Other");

		floorNoOptions = new LinkedHashMap<>();
		for (int i = 0; i < 10; i++)
			floorNoOptions.put(i, i);

		ratingOptions = new LinkedHashMap<>();
		for (int i = 1; i <= 5; i++)
			ratingOptions.put(i, i);

		freeEntryOptions = new LinkedHashMap<>();
		freeEntryOptions.put('N', "No");
		freeEntryOptions.put('Y', "Yes");
		freeEntryOptions.put('M', "Moderate");

		coupledAllowedOptions = new LinkedHashMap<>();
		coupledAllowedOptions.put(false, "No");
		coupledAllowedOptions.put(true, "Yes");

		availabilityOptions = new LinkedHashMap<>();
		availabilityOptions.put(true, "Available");
		availabilityOptions.put(false, "Not-Available");

		amenitiesProvided = new ArrayList<>();
		amenitiesOptions = new ArrayList<>(Arrays.asList("NDPL Meter", "SUB Meter", "Fridge", "Ro", "Gas Connection",
				"WiFi", "Beds", "Almirah", "AC", "Cooler", "under ground water", "supply water"));

	}

	public LinkedHashMap<Boolean, String> getAvailabilityOptions() {
		return availabilityOptions;
	}

	public void setAvailabilityOptions(LinkedHashMap<Boolean, String> availabilityOptions) {
		this.availabilityOptions = availabilityOptions;
	}

	public LinkedHashMap<Boolean, String> getCoupledAllowedOptions() {
		return coupledAllowedOptions;
	}

	public void setCoupledAllowedOptions(LinkedHashMap<Boolean, String> coupledAllowedOptions) {
		this.coupledAllowedOptions = coupledAllowedOptions;
	}

	public LinkedHashMap<Character, String> getTenantGenderOptions() {
		return tenantGenderOptions;
	}

	public void setTenantGenderOptions(LinkedHashMap<Character, String> tenantGenderOptions) {
		this.tenantGenderOptions = tenantGenderOptions;
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

	public LinkedHashMap<Integer, Integer> getFloorNoOptions() {
		return floorNoOptions;
	}

	public void setFloorNoOptions(LinkedHashMap<Integer, Integer> floorNoOptions) {
		this.floorNoOptions = floorNoOptions;
	}

	public LinkedHashMap<Character, String> getFreeEntryOptions() {
		return freeEntryOptions;
	}

	public void setFreeEntryOptions(LinkedHashMap<Character, String> freeEntryOptions) {
		this.freeEntryOptions = freeEntryOptions;
	}

	public LinkedHashMap<Integer, Integer> getRatingOptions() {
		return ratingOptions;
	}

	public void setRatingOptions(LinkedHashMap<Integer, Integer> ratingOptions) {
		this.ratingOptions = ratingOptions;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	public LinkedHashMap<String, String> getPropertyTypeOptions() {
		return propertyTypeOptions;
	}

	public void setPropertyTypeOptions(LinkedHashMap<String, String> propertyTypeOptions) {
		this.propertyTypeOptions = propertyTypeOptions;
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

	public List<String> getTenantOptions() {
		return tenantOptions;
	}

	public void setTenantOptions(List<String> tenantOptions) {
		this.tenantOptions = tenantOptions;
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

	public List<String> getAmenitiesOptions() {
		return amenitiesOptions;
	}

	public void setAmenitiesOptions(List<String> amenitiesOptions) {
		this.amenitiesOptions = amenitiesOptions;
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

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public boolean isAvailability() {
		return availability;
	}

	public void setAvailability(boolean availability) {
		this.availability = availability;
	}

	@Override
	public String toString() {
		return "PropertyDto [id=" + id + ", locality=" + locality + ", address=" + address + ", state=" + state
				+ ", pincode=" + pincode + ", floorNo=" + floorNo + ", floorNoOptions=" + floorNoOptions
				+ ", propertyType=" + propertyType + ", propertyTypeOptions=" + propertyTypeOptions + ", tenantType="
				+ tenantType + ", tenantOptions=" + tenantOptions + ", tenantGender=" + tenantGender
				+ ", tenantGenderOptions=" + tenantGenderOptions + ", freeEntry=" + freeEntry + ", freeEntryOptions="
				+ freeEntryOptions + ", coupledAllowed=" + coupledAllowed + ", coupledAllowedOptions="
				+ coupledAllowedOptions + ", rent=" + rent + ", securityMonth=" + securityMonth + ", amenitiesProvided="
				+ amenitiesProvided + ", amenitiesOptions=" + amenitiesOptions + ", rating=" + rating
				+ ", ratingOptions=" + ratingOptions + ", description=" + description + ", ownerId=" + ownerId
				+ ", availability=" + availability + "]";
	}

}
