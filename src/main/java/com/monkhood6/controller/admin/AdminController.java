package com.monkhood6.controller.admin;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.validation.Valid;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.monkhood6.model.dto.PropertyDto;
import com.monkhood6.model.dto.PropertyReqDto;
import com.monkhood6.model.dto.TenantReqDto;
import com.monkhood6.model.dto.UserDto;
import com.monkhood6.model.dto.UserRegDto;
import com.monkhood6.model.entity.Property;
import com.monkhood6.model.entity.PropertyImage;
import com.monkhood6.model.entity.PropertyReq;
import com.monkhood6.model.entity.PropertyReqImage;
import com.monkhood6.model.entity.Role;
import com.monkhood6.model.entity.TenantRequest;
import com.monkhood6.model.entity.User;
import com.monkhood6.service.ApiService;
import com.monkhood6.utils.Utils;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private ApiService<User> userApiService;
	@Autowired
	private ApiService<Role> roleApiService;
	@Autowired
	private ApiService<PropertyReq> propertyReqApiService;
	@Autowired
	private ApiService<Property> propertyApiService;
	@Autowired
	private ApiService<TenantRequest> tenantReqApiService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private Utils utils;

	/*---------------------------------- User Related Api Service ----------------------------------*/
	/* Function to show All unverified users */
	@GetMapping("/unverified-users")
	public String showAllunVerifiedUsers(Model model) {
		model.addAttribute("users",
				userApiService.getAllByQuery("from User u where u.status=false ORDER BY u.signupTime DESC"));
		return "admins/unverifiedUsers.jsp";
	}

	/* Function to show Add user/Admin/emp form */
	@GetMapping("/add-user")
	public String showAddUserPage(Model model) {
		model.addAttribute("user", new UserRegDto());
		return "admins/addUser.jsp";
	}

	/* Function to Add a new user/admin/emp by Admin */
	@PostMapping("/add-user")
	public String addNewUser(@Valid @ModelAttribute("user") UserRegDto user, BindingResult result,
			@RequestParam(required = false, value = "role") String roleStr, Model model) {

		if (result.hasErrors())
			return "admins/addUser.jsp";
		else if (!user.getPassword().equals(user.getConfirmPass())) {
			model.addAttribute("addUserMsg", "Password does not Match..!");
			return "admins/addUser.jsp";
		}

		/* role -> 1 (user) , 2 (employee), 3 (Admin) */
		int role = 1;
		try {
			role = Integer.parseInt(roleStr);
		} catch (NumberFormatException e) {
			role = 1;
		}

		/* Setting some default values for users */
		Role userRole = roleApiService.get(Role.class, role);
		user.setRoles(new HashSet<>(Arrays.asList(userRole)));
		String hashPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(hashPassword);
		user.setUserId(utils.generateToken());
		user.setSignupTime(new Date());
		user.setStatus(true);

		/* Converting the Dto to Entity */
		User userToBeSaved = new User();
		BeanUtils.copyProperties(user, userToBeSaved);
		/* Adding user to the Database */
		try {
			userApiService.save(userToBeSaved);
		} catch (ConstraintViolationException e) {
			/* Constraint violet exception like duplicate entry for email/mobile */
			user.setPassword("");
			user.setConfirmPass("");
			String fieldName = e.getConstraintName().substring(e.getConstraintName().indexOf(".") + 1);
			model.addAttribute("addUserMsg", "Already have an account with this " + fieldName + "..!");
			return "admins/addUser.jsp";
		} catch (Exception e) {
			throw new RuntimeException("Exception from AdminController during saving the user Object..!");
		}
		model.addAttribute("addUserMsg", "User Added Successfully..!");
		model.addAttribute("user", new UserRegDto());
		return "admins/addUser.jsp";
	}

	/* Function to show modify user form */
	@GetMapping("/modify-user/{id}")
	public String showEditUserDetailsPage(@PathVariable String id, Model model) {
		if (id == null || id.trim().isEmpty())
			return "redirect:/error-page";

		User userInDB = userApiService.getSingleResult(User.class, "userId", id);

		if (userInDB == null)
			return "redirect:/error-page";

		/* Converting the entity into dto */
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userInDB, userDto);
		/* Sending the Dto to Front end */
		model.addAttribute("user", userDto);
		return "admins/editUser.jsp";
	}

	/* Function to modify user */
	@PostMapping("/modify-user")
	public String editUserDetails(@Valid @ModelAttribute("user") UserDto user, BindingResult result,
			@RequestParam(required = false, value = "role") String roleStr, Model model) {

		if (result.hasErrors())
			return "admins/editUser.jsp";
		if (user.getEmail().trim().equals("test@admin.com")) {
			model.addAttribute("editUserMsg", "Access Denied..!");
			return "admins/editUser.jsp";
		}

		/* role -> 1 (user) , 2 (employee), 3 (Admin) */
		int role = 1;
		try {
			role = Integer.parseInt(roleStr);
		} catch (NumberFormatException e) {
			role = 1;
		}

		/* Setting roles of the user */
		Role userRole = roleApiService.get(Role.class, role);
		user.setRoles(new HashSet<>(Arrays.asList(userRole)));

		/* Finding the user Object from the Database */
		User userInDB = userApiService.getSingleResult(User.class, "userId", user.getUserId());
		if (userInDB == null)
			return "redirect:/error-page";

		/* Copying the Dto to entity */
		BeanUtils.copyProperties(user, userInDB);

		/* Updating the changes & returning the response */
		try {
			userApiService.update(userInDB);
		} catch (ConstraintViolationException e) {
			String fieldName = e.getConstraintName().substring(e.getConstraintName().indexOf(".") + 1);
			model.addAttribute("editUserMsg", "Already have an account with this " + fieldName + "..!");
			return "admins/editUser.jsp";
		} catch (Exception e) {
			throw new RuntimeException("Exception from AdminController during updating the User Object..!");
		}

		model.addAttribute("editUserMsg", "User Details Modified..!");
		return "admins/editUser.jsp";
	}

	/* Function to delete a user completely from the database */
//	@GetMapping("/remove-user/{id}")
//	public String removeUserFromDataBase(@PathVariable String id) {
//		long userId = 0;
//		try {
//			userId = Long.parseLong(id);
//		} catch (Exception e) {
//			return "./brokenLink.jsp";
//		}
//
//		List<TenantRequest> tenantReqs = tenantReqApiService
//				.getAllByQuery("SELECT tr FROM TenantRequest WHERE tr.requestedBy.id = " + userId);
//
//		StringBuilder ids = new StringBuilder();
//		for (int i = 0; i < tenantReqs.size(); i++) {
//			ids.append(tenantReqs.get(i).getId());
//			if (i != tenantReqs.size() - 1)
//				ids.append(", ");
//		}
//		
//		tenantReqApiService.executeQuery("DELETE FROM Tena");
//
//		tenantReqApiService.executeQuery("DELETE FROM TenantRequest.");
//		tenantReqApiService.executeQuery("DELETE FROM TenantRequest tr where tr.requestedBy.id = " + userId);
//		userApiService.delete(User.class, userId);
//		return "admins/verifiedUsers.jsp";
//	}

	/*---------------------------------- Property Related Api Service ----------------------------------*/
	/* Function to show All verified Properties */
	@GetMapping("/verified-properties")
	public String showAllVerifiedProperties(Model model) {
		/* Fetching all the data of verified Properties from Database */
		model.addAttribute("properties",
				propertyApiService.getAllByQuery("from Property p ORDER BY p.propertyConfirmationDateTime DESC"));
		return "admins/verifiedProperties.jsp";
	}

	/* Function to show All unverified rooms */
	@GetMapping("/unverified-properties")
	public String showAllunVerifiedProperties(Model model) {
		/* Fetching all the data of unverified Properties from Database */
		model.addAttribute("properties",
				propertyReqApiService.getAllByQuery("from PropertyReq pr ORDER BY pr.propertyReqDateTime DESC"));
		return "admins/unverifiedProperties.jsp";
	}

	/* Function to show add-Property Page */
	@GetMapping("/add-property")
	public String showAddPropertyForm(Model model) {
		model.addAttribute("property", new PropertyDto());
		return "admins/addProperty.jsp";
	}

	/* Function to add Property to the DataBase */
	@PostMapping("/add-property")
	public String addProperty(@Valid @ModelAttribute("property") PropertyDto propertyDto, BindingResult result,
			@RequestParam(value = "images", required = false) List<MultipartFile> images, Model model) {

		if (result.hasErrors())
			return "admins/addProperty.jsp";

		/*
		 * Fetching the last id of the Property from the database & adding 1 to make it
		 * new Property Id
		 */
		long propertyId = propertyApiService.getLastEntryId("Property") + 1;
		/* Converting the Dto -> Entity */
		Property property = new Property();
		BeanUtils.copyProperties(propertyDto, property);

		/* Checking the status of image uploading..! */
		boolean isImagesUploaded = uploadImagestoVerified(images, propertyId, property);
		if (isImagesUploaded == false) {
			deleteUploadedFolder(propertyId);
			model.addAttribute("propertyMsg", "Error in uploading the images..!");
			return "rooms/addProperty.jsp";
		}

		/*
		 * All the images are successfully uploaded hence adding some default values and
		 * saving the Property Details to DataBase.
		 */
		/* Setting Property Confirmation/Added Time */
		property.setPropertyConfirmationDateTime(new Date());
		/* Setting Owner */
		if (propertyDto.getOwnerId() == null || propertyDto.getOwnerId().trim().isEmpty()) {
			User owner = userApiService.getSingleResult(User.class, "email", utils.getAuthentication().getName());
			property.setOwner(owner);
		} else {
			User owner = userApiService.getSingleResult(User.class, "userId", propertyDto.getOwnerId().trim());
			if (owner == null) {
				model.addAttribute("propertyMsg", "Invalid Owner Id..!");
				return "rooms/addProperty.jsp";
			} else
				property.setOwner(owner);
		}

		/* Saving the Property Form Data */
		try {
			propertyApiService.save(property);
		} catch (Exception e) {
			deleteUploadedFolder(propertyId);
			throw new RuntimeException("Exception from AdminController while saving Property Object..!");
		}

		/* Succesfully executed everything */
		model.addAttribute("propertyMsg", "Property Details Added..!");
		model.addAttribute("property", new PropertyDto());

		return "admins/addProperty.jsp";
	}

	/* Function to Delete all the properties images if any Error Occurs */
	private void deleteUploadedFolder(long propertyId) {
		Path path = Paths.get(utils.getSystemPath() + "/properties/verified/property_" + propertyId + "/");
		if (path != null)
			path.toFile().getParentFile().delete();
	}

	/* Function to upload all the properties images to the verified folder */
	private boolean uploadImagestoVerified(List<MultipartFile> images, long propertyId, Property property) {
		Path path = null;
		if (images.get(0).getSize() > 0) {
			for (MultipartFile image : images) {
				try {
					byte[] bytes = image.getBytes();
					path = Paths.get(utils.getSystemPath() + "/properties/verified/property_" + propertyId + "/"
							+ image.getOriginalFilename());

					/* Creating each file inside the root directory */
					if (!path.toFile().exists())
						path.toFile().getParentFile().mkdirs();

					/* Creating PropertyImage Object and setting values */
					PropertyImage propertyImgObj = new PropertyImage();
					propertyImgObj.setImageURL(path.toFile().getAbsolutePath());
					propertyImgObj.setProperty(property);

					/* Adding Property Image object to Property */
					property.getPropertyImages().add(propertyImgObj);

					/* writing the image into the root directory */
					Files.write(path, bytes);
				} catch (Exception e) {
					path.toFile().getParentFile().delete();
					return false;
				}
			}
		}
		return true;
	}

	/* Function to show Edit Property Request Details Page */
	@GetMapping("/modify-unverified-property/{id}")
	public String showEditunVerifiedPropertyPage(@PathVariable String id, Model model) {
		Long propertyId = null;
		try {
			propertyId = Long.parseLong(id);
		} catch (Exception e) {
			return "redirect:/error-page";
		}

		PropertyReq PropertyReqInDB = propertyReqApiService.get(PropertyReq.class, propertyId);
		if (PropertyReqInDB == null)
			return "redirect:/error-page";

		PropertyReqDto propertyReqDto = new PropertyReqDto();
		propertyReqDto.setOwnerId(PropertyReqInDB.getOwner().getUserId());
		BeanUtils.copyProperties(PropertyReqInDB, propertyReqDto);
		model.addAttribute("property", propertyReqDto);
		return "admins/editunverifiedProperty.jsp";
	}

	/* function to modify unverified property */
	@PostMapping("/modify-unverified-property")
	public String modifyunVerifiedRoom(@Valid @ModelAttribute("property") PropertyReqDto propertyReqDto,
			BindingResult result, Model model) {

		if (result.hasErrors())
			return "admins/unverifiedProperty.jsp";

		User owner = userApiService.getSingleResult(User.class, "userId", propertyReqDto.getOwnerId().trim());
		if (owner == null) {
			model.addAttribute("editPropertyMsg", "Invalid Owner Id..!");
			return "admins/editunverifiedProperty.jsp";
		}

		PropertyReq propertyReqInDB = propertyReqApiService.get(PropertyReq.class, propertyReqDto.getId());
		if (propertyReqInDB == null)
			return "redirect:/error-page";

		/* Converting Dto -> Entity */
		BeanUtils.copyProperties(propertyReqDto, propertyReqInDB);
		propertyReqInDB.setOwner(owner);
		try {
			propertyReqApiService.update(propertyReqInDB);
		} catch (Exception e) {
			throw new RuntimeException("Exception from AdminController while modifying unverified Property Details..!");
		}

		model.addAttribute("editPropertyMsg", "Property Details Modified..!");
		return "admins/editunverifiedProperty.jsp";
	}

	/* Function to show Edit Property Details Page */
	@GetMapping("/modify-verified-property/{id}")
	public String showEditVerifiedPropertyPage(@PathVariable String id, Model model) {
		Long propertyId = null;
		try {
			propertyId = Long.parseLong(id);
		} catch (Exception e) {
			return "redirect:/error-page";
		}

		Property PropertyInDB = propertyApiService.get(Property.class, propertyId);
		if (PropertyInDB == null)
			return "redirect:/error-page";

		PropertyDto propertyDto = new PropertyDto();
		propertyDto.setOwnerId(PropertyInDB.getOwner().getUserId());
		BeanUtils.copyProperties(PropertyInDB, propertyDto);
		model.addAttribute("property", propertyDto);
		return "admins/editverifiedProperty.jsp";
	}

	/* function to modify verified property */
	@PostMapping("/modify-verified-property")
	public String modifyVerifiedRoom(@Valid @ModelAttribute("property") PropertyDto propertyDto, BindingResult result,
			Model model) {

		if (result.hasErrors())
			return "admins/verifiedProperty.jsp";

		User owner = userApiService.getSingleResult(User.class, "userId", propertyDto.getOwnerId().trim());
		if (owner == null) {
			model.addAttribute("editPropertyMsg", "Invalid Owner Id..!");
			return "admins/editverifiedProperty.jsp";
		}

		Property propertyInDB = propertyApiService.get(Property.class, propertyDto.getId());
		if (propertyInDB == null)
			return "redirect:/error-page";

		/* Converting Dto -> Entity */
		BeanUtils.copyProperties(propertyDto, propertyInDB);
		propertyInDB.setOwner(owner);
		try {
			propertyApiService.update(propertyInDB);
		} catch (Exception e) {
			throw new RuntimeException("Exception from AdminController while modifying verified Property Details..!");
		}

		model.addAttribute("editPropertyMsg", "Property Details Modified..!");
		return "admins/editverifiedProperty.jsp";
	}

	/* Function to delete unverified Properties */
	@GetMapping("/remove-unverified-properties/{id}")
	public String removeUnVerifiedProperties(@PathVariable String id) {
		Long propertyId = null;
		try {
			propertyId = Long.parseLong(id);
		} catch (Exception e) {
			return "redirect:/error-page";
		}
		propertyReqApiService.delete(PropertyReq.class, propertyId);
		return "admins/unverifiedProperties.jsp";
	}

	/* Function to delete verified Properties */
	@GetMapping("/remove-verified-properties/{id}")
	public String removeVerifiedProperties(@PathVariable String id) {
		Long propertyId = null;
		try {
			propertyId = Long.parseLong(id);
		} catch (Exception e) {
			return "redirect:/error-page";
		}
		propertyApiService.delete(Property.class, propertyId);
		return "admins/verifiedProperties.jsp";
	}

	/* Function to verify a room */
	@GetMapping("/verify/property/{id}")
	public String verifyProperty(@PathVariable String id, Model model) {
		if (id == null || id.trim().isEmpty())
			return "redirect:/error-page";
		/* Validating the property Id */
		Long propertyId = null;
		try {
			propertyId = Long.parseLong(id);
		} catch (Exception e) {
			return "redirect:/error-page";
		}
		/* Fetching the property Request Object from the Database */
		PropertyReq propertyReq = propertyReqApiService.get(PropertyReq.class, propertyId);
		if (propertyReq == null)
			return "redirect:/error-page";
		/* converting Property Request object to Property Object */
		Property property = new Property();
		BeanUtils.copyProperties(propertyReq, property);
		property.setPropertyConfirmationDateTime(new Date());
		property.setAvailability(true);
		/*
		 * Fetching the last id of the Property from the database & adding 1 to make it
		 * new Property Id
		 */
		long newPropertyId = propertyApiService.getLastEntryId("Property") + 1;
		/* Move images from unverified to verified */
		if (moveImagesUnverifiedToVerified(propertyReq.getPropertyReqImages(), property, newPropertyId,
				propertyReq.getId()) == false) {
			model.addAttribute("verifyPropertyMsg", "Error in Image Shifting..!");
			return "admins/propertyVerificationMsg.jsp";
		}
		/* Deleting the Property Request */
		try {
			propertyReqApiService.delete(PropertyReq.class, propertyReq.getId());
		} catch (Exception e) {
			shiftBackImages(property, propertyReq.getId());
			throw new RuntimeException(
					"Exception from Admin Controller while deleting(in Verifying) Property Request Object..!");
		}
		try {
			propertyApiService.save(property);
		} catch (Exception e) {
			deleteImages(newPropertyId);
			throw new RuntimeException("Exception from AdminController while saving(in Verifying) Property Object..!");
		}
		model.addAttribute("verifyPropertyMsg", "Property is successfully verified..!");
		return "admins/propertyVerificationMsg.jsp";
	}

	/*
	 * Function to shift All the unverifie Property images to the verified Property
	 */
	private boolean moveImagesUnverifiedToVerified(List<PropertyReqImage> images, Property property, long newPropertyId,
			long propertyReqId) {
		String destPath = utils.getSystemPath() + "/properties/verified/property_" + newPropertyId + "/";
		Path dest = null;
		Path source = null;

		/* Moving all the images of Property Request to verified Property */
		for (PropertyReqImage image : images) {
			/* Extracting the source image */
			source = Paths.get(image.getImageURL());
			if (source.toFile().exists()) {
				try {
					/* Creating the destination folder */
					dest = Paths.get(destPath + source.getFileName());
					if (!dest.toFile().exists())
						dest.toFile().getParentFile().mkdirs();

					PropertyImage propertyImageObj = new PropertyImage();
					propertyImageObj.setImageURL(dest.toString());
					propertyImageObj.setProperty(property);
					/* Adding Property Images */
					property.getPropertyImages().add(propertyImageObj);
					/* Moving the files(images) */
					Files.move(source, dest);
				} catch (Exception e) {
					shiftBackImages(property, propertyReqId);
					return false;
				}
			}
		}
		/* Deleting the Source folder of that particular Property Request */
		if (source != null)
			source.getParent().toFile().delete();
		return true;
	}

	/* Function to shift back all the Property images from verified to unverified */
	private void shiftBackImages(Property property, long propertyReqId) {
		String destPath = utils.getSystemPath() + "/properties/unverified/property_" + propertyReqId + "/";

		Path dest = null;
		Path source = null;
		List<PropertyImage> images = property.getPropertyImages();
		for (PropertyImage image : images) {
			/* Extracting the source image */
			source = Paths.get(image.getImageURL());
			try {
				/* Creating the destination folder */
				dest = Paths.get(destPath + source.getFileName());
				if (!dest.toFile().exists())
					dest.toFile().getParentFile().mkdirs();

				/* Moving the files(images) */
				Files.move(source, dest);
			} catch (Exception e) {
				continue;
			}
		}
		/* Deleting the Source folder of that particular Property */
		if (source != null)
			source.getParent().toFile().delete();
	}

	/* function to delete all the images of a particluar folder */
	private void deleteImages(long newPropertyId) {
		Path destPath = Paths.get(utils.getSystemPath() + "/properties/verified/property_" + newPropertyId + "/");
		if (destPath != null)
			destPath.getParent().toFile().delete();
	}

	/*---------------------------------- Tenant Related Api Service ----------------------------------*/
	/* Function to show all the tenants Request */
	@GetMapping("/tenant-requests")
	public String showTenantReq(Model model) {
		model.addAttribute("tenantReq",
				tenantReqApiService.getAllByQuery("from TenantRequest tr where tr.tenantReqStatus=false"));
		return "admins/tenantRequest.jsp";
	}

	@GetMapping("/modify-tenant-request/{id}")
	public String showModifyTenantReq(@PathVariable String id, Model model) {
		if (id == null || id.trim().isEmpty())
			return "redirect:/error-page";
		long tenantReqId = 0;
		try {
			tenantReqId = Long.parseLong(id);
		} catch (Exception e) {
			return "redirect:/error-page";
		}

		TenantRequest tenantReq = tenantReqApiService.get(TenantRequest.class, tenantReqId);
		if (tenantReq == null)
			return "redirect:/error-page";

		TenantReqDto tenantReqDto = new TenantReqDto();
		BeanUtils.copyProperties(tenantReq, tenantReqDto);

		tenantReqDto.setUserId(tenantReq.getRequestedBy().getUserId());
		model.addAttribute("tenantReq", tenantReqDto);
		return "admins/editTenantRequest.jsp";
	}

	@PostMapping("/modify-tenant-request")
	public String modifyTenantReq(@ModelAttribute("tenantReq") TenantReqDto tenantReqDto, BindingResult result,
			Model model) {
		if (result.hasErrors())
			return "admins/editTenantRequest.jsp";

		TenantRequest tenantRequest = tenantReqApiService.get(TenantRequest.class, tenantReqDto.getId());
		User requestedBy = userApiService.getSingleResult(User.class, "userId", tenantReqDto.getUserId());

		if (tenantRequest == null || requestedBy == null)
			return "redirect:/error-page";

		BeanUtils.copyProperties(tenantReqDto, tenantRequest);
		tenantRequest.setRequestedBy(requestedBy);
		try {
			tenantReqApiService.update(tenantRequest);
		} catch (Exception e) {
			throw new RuntimeException("Exception from AdminController while Modifying TenantRequest Object..!");
		}
		model.addAttribute("tenantReqMsg", "Details Modified..!");
		return "admins/editTenantRequest.jsp";
	}

	@GetMapping("/remove-tenant-request/{id}")
	public String deleteTenantReq(@PathVariable String id) {
		if (id == null || id.trim().isEmpty())
			return "redirect:/error-page";

		Long tid = null;
		try {
			tid = Long.parseLong(id);
		} catch (Exception e) {
			return "redirect:/error-page";
		}

		tenantReqApiService.delete(TenantRequest.class, tid);

		return "redirect:/admin/tenant-requests";
	}

	@GetMapping("/tenant-history")
	public String showTenantServices(Model model) {
		model.addAttribute("tenantBookings",
				tenantReqApiService.getAllByQuery("from TenantRequest tr where tr.tenantReqStatus=true"));
		return "admins/tenantHistory.jsp";
	}

	@GetMapping("/tenant-service-approved/{id}")
	public String approveService(@PathVariable String id, Model model) {
		long trId = 0;
		try {
			trId = Long.parseLong(id);
		} catch (Exception e) {
			return "redirect:/error-page";
		}

		TenantRequest tr = tenantReqApiService.get(TenantRequest.class, trId);
		if (tr == null)
			return "redirect:/error-page";

		tr.setTenantReqStatus(true);
		tr.setTenantConfirmDate(new Date());
		try {
			tenantReqApiService.update(tr);
		} catch (Exception e) {
			throw new RuntimeException("Exception from AdminController while Approving TenantRequest Object..!");
		}
		model.addAttribute("approveMsg", "Service is succesfully Approved..!");
		return "admins/serviceApproved.jsp";
	}
}
