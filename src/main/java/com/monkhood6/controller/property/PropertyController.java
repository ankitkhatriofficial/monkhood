package com.monkhood6.controller.property;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.monkhood6.model.dto.PropertyReqDto;
import com.monkhood6.model.entity.PropertyReq;
import com.monkhood6.model.entity.PropertyReqImage;
import com.monkhood6.model.entity.User;
import com.monkhood6.service.ApiService;
import com.monkhood6.utils.Utils;

@Controller
@RequestMapping("/property")
public class PropertyController {

	@Autowired
	private ApiService<PropertyReq> propertyReqApiService;
	@Autowired
	private ApiService<User> userApiService;
	@Autowired
	private Utils utils;

	@GetMapping("/req")
	public String showPropertyRequestForm(Model model) {
		model.addAttribute("property", new PropertyReqDto());
		return "rooms/requestProperty.jsp";
	}

	@PostMapping("/req")
	public String requestProperty(@Valid @ModelAttribute("property") PropertyReqDto propertyReqDto,
			BindingResult result, @RequestParam(required = false, value = "images") List<MultipartFile> images,
			Model model) {

		if (result.hasErrors())
			return "rooms/requestProperty.jsp";

		/*
		 * Fetching the last id of the Property Request from the database & adding 1 to
		 * make it new PropertyRequest Id
		 */
		long propertyId = propertyReqApiService.getLastEntryId("PropertyReq") + 1;

		/* Converting the Dto -> Entity */
		PropertyReq propertyReq = new PropertyReq();
		BeanUtils.copyProperties(propertyReqDto, propertyReq);

		/* Checking the status of image uploading..! */
		boolean isImagesUploaded = uploadImagestoUnverified(images, propertyId, propertyReq);
		if (isImagesUploaded == false) {
			deleteUploadedFolder(propertyId);
			model.addAttribute("propertyReqMsg", "Error in uploading the images..!");
			return "rooms/requestProperty.jsp";
		}

		/*
		 * All the images are successfully uploaded hence adding some default values and
		 * saving the Property Request Details to DataBase.
		 */
		/* Setting Room Request Time */
		propertyReq.setPropertyReqDateTime(new Date());
		propertyReq.setAvailability(false);
		/* Setting Owner */
		if (propertyReqDto.getOwnerId() == null || propertyReqDto.getOwnerId().trim().isEmpty()) {
			User owner = userApiService.getSingleResult(User.class, "email", utils.getAuthentication().getName());
			propertyReq.setOwner(owner);
		} else {
			User owner = userApiService.getSingleResult(User.class, "userId", propertyReqDto.getOwnerId().trim());
			if (owner == null) {
				model.addAttribute("propertyReqMsg", "Invalid Owner Id..!");
				return "rooms/requestProperty.jsp";
			} else
				propertyReq.setOwner(owner);
		}

		/* Saving the Property Request Form Data */
		try {
			propertyReqApiService.save(propertyReq);
		} catch (Exception e) {
			deleteUploadedFolder(propertyId);
			throw new RuntimeException("Exception from PropertyController while saving Property Request Object..!");
		}

		/* Succesfully executed everything */
		model.addAttribute("propertyReqMsg", "Property request Submitted..!");
		model.addAttribute("property", new PropertyReqDto());
		return "rooms/requestProperty.jsp";
	}

	/* deleting the folder of the uploaded images if uploading fails */
	private void deleteUploadedFolder(long propertyId) {
		Path path = Paths.get(utils.getSystemPath() + "/properties/unverified/property_" + propertyId + "/");
		if (path != null)
			path.toFile().getParentFile().delete();
	}

	/* Uploading the images to the server */
	private boolean uploadImagestoUnverified(List<MultipartFile> images, long propertyId, PropertyReq propertyReq) {
		Path path = null;
		if (images.get(0).getSize() > 0) {
			for (MultipartFile image : images) {
				try {
					byte[] bytes = image.getBytes();
					path = Paths.get(utils.getSystemPath() + "/properties/unverified/property_" + propertyId + "/"
							+ image.getOriginalFilename());

					/* Creating each file inside the root directory */
					if (!path.toFile().exists())
						path.toFile().getParentFile().mkdirs();

					/* Creating PropertyImage Object and setting values */
					PropertyReqImage propertyReqImgObj = new PropertyReqImage();
					propertyReqImgObj.setImageURL(path.toFile().getAbsolutePath());
					propertyReqImgObj.setPropertyReq(propertyReq);

					/* Adding Property Image object to roomRequest */
					propertyReq.getPropertyReqImages().add(propertyReqImgObj);

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

}
