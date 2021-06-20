package com.monkhood6.controller.api;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.monkhood6.model.entity.Document;
import com.monkhood6.model.entity.Property;
import com.monkhood6.model.entity.PropertyImage;
import com.monkhood6.model.entity.PropertyReq;
import com.monkhood6.model.entity.PropertyReqImage;
import com.monkhood6.model.entity.TenantRequest;
import com.monkhood6.service.ApiService;
import com.monkhood6.utils.Utils;

@RestController
@RequestMapping("/api")
public class AdminApiController {

	@Autowired
	private ApiService<TenantRequest> tenantReqApiService;
	@Autowired
	private ApiService<Property> propertyApiService;
	@Autowired
	private ApiService<PropertyReq> propertyReqApiService;
	@Autowired
	private Utils utils;

	@GetMapping("/localities")
	public List<String> getLocalities() {
		List<String> localities = new ArrayList<>();
		List<Property> propInDB = propertyApiService
				.getAllByQuery("SELECT prop FROM Property prop ORDER BY prop.locality");
		for (Property p : propInDB) {
			if (p.getLocality() != null || !p.getLocality().trim().isEmpty())
				localities.add(p.getLocality());
		}
		return localities;
	}

	/*
	 * Api to upload Rent Agreement, Tenant Documents & to view and delete images of
	 * property
	 */

	@GetMapping("/a/rent-agreement/{id}")
	public String getRentAgreeMent(@PathVariable String id) {
		if (id == null || id.trim().isEmpty())
			return "Invalid url..!";
		long tid = 0;
		try {
			tid = Long.parseLong(id);
		} catch (Exception e) {
			return "Invalid url..!";
		}
		TenantRequest tenantReqInDB = tenantReqApiService.get(TenantRequest.class, tid);
		if (tenantReqInDB == null)
			return "Invalid url..!";
		else
			return tenantReqInDB.getRentAgreeMentLink();
	}

	/* Api to add rent agreement */
	@PostMapping("/a/rent-agreement/{id}")
	public String uploadRentAgreeMent(@PathVariable String id,
			@RequestParam(value = "rent-agreement", required = false) MultipartFile rentAgreement) {
		if (id == null || id.trim().isEmpty() || rentAgreement == null || rentAgreement.getSize() <= 0)
			return "Request Body is Missing..!";
		long tid = 0;
		try {
			tid = Long.parseLong(id);
		} catch (Exception e) {
			return "Invalid url..!";
		}
		TenantRequest tenantReqInDB = tenantReqApiService.get(TenantRequest.class, tid);
		if (tenantReqInDB == null)
			return "Invalid url..!";
		String destPath = utils.getSystemPath() + "/tenant_documents/tenantReq_" + tid + "/rent-agreement/"
				+ rentAgreement.getOriginalFilename();
		try {
			byte[] bytes = rentAgreement.getBytes();
			Path dest = Paths.get(destPath);
			if (!dest.toFile().exists())
				dest.toFile().getParentFile().mkdirs();
			Files.write(dest, bytes);
			tenantReqInDB.setRentAgreeMentLink(destPath);
			tenantReqApiService.update(tenantReqInDB);
		} catch (Exception e) {
			Path path = Paths.get(destPath);
			if (path != null)
				path.toFile().getParentFile().delete();
			throw new RuntimeException("Error in file uploading..!");
		}
		return "Succesfully uploaded..!";
	}

	@GetMapping("/u/docs/{id}")
	public Object getUserDocuments(@PathVariable String id) {
		long tid = 0;
		try {
			tid = Long.parseLong(id);
		} catch (Exception e) {
			return "Invalid url..!";
		}
		TenantRequest tenantReqInDB = tenantReqApiService.get(TenantRequest.class, tid);
		if (tenantReqInDB == null)
			return "Invalid url..!";

		List<Map<String, String>> res = new ArrayList<>();
		for (Document d : tenantReqInDB.getDocuments()) {
			Map<String, String> map = new HashMap<>();
			map.put("_id", d.getId() + "");
			map.put("docType", d.getType());
			map.put("docURL", d.getDocURL());
			res.add(map);
		}
		return res;
	}

	/* Api to add all the documents to a tenant Request */
	@PostMapping("/u/docs/{id}")
	public String uploadDocuments(@PathVariable String id,
			@RequestParam(value = "doc1", required = false) MultipartFile doc1,
			@RequestParam(value = "doc1Type", required = false) String doc1Type,
			@RequestParam(value = "doc2", required = false) MultipartFile doc2,
			@RequestParam(value = "doc2Type", required = false) String doc2Type,
			@RequestParam(value = "tenantPhoto", required = false) MultipartFile tenantPhoto) {

		if (doc1 == null || doc1.getSize() <= 0 || doc2 == null || doc2.getSize() <= 0 || tenantPhoto == null
				|| tenantPhoto.getSize() <= 0 || doc1Type == null || doc1Type.trim().isEmpty() || doc2Type == null
				|| doc2Type.trim().isEmpty())
			return "Request Body is Missing..!";
		long tid = 0;
		try {
			tid = Long.parseLong(id);
		} catch (Exception e) {
			return "Invalid url..!";
		}
		TenantRequest tenantReqInDB = tenantReqApiService.get(TenantRequest.class, tid);
		if (tenantReqInDB == null)
			return "Invalid url..!";
		Path destPath1 = Paths.get(utils.getSystemPath() + "/tenant_documents/tenantReq_" + tid + "/documents/"
				+ doc1.getOriginalFilename());
		Path destPath2 = Paths.get(utils.getSystemPath() + "/tenant_documents/tenantReq_" + tid + "/documents/"
				+ doc2.getOriginalFilename());
		Path destPath3 = Paths.get(utils.getSystemPath() + "/tenant_documents/tenantReq_" + tid + "/documents/"
				+ tenantPhoto.getOriginalFilename());
		try {
			byte[] doc1Bytes = doc1.getBytes();
			byte[] doc2Bytes = doc2.getBytes();
			byte[] doc3Bytes = tenantPhoto.getBytes();

			if (!destPath1.toFile().exists())
				destPath1.toFile().getParentFile().mkdirs();
			if (!destPath2.toFile().exists())
				destPath2.toFile().getParentFile().mkdirs();
			if (!destPath3.toFile().exists())
				destPath3.toFile().getParentFile().mkdirs();

			Files.write(destPath1, doc1Bytes);
			Files.write(destPath2, doc2Bytes);
			Files.write(destPath3, doc3Bytes);

			Document document1 = new Document(doc1Type, destPath1.toAbsolutePath().toString(), tenantReqInDB);
			Document document2 = new Document(doc2Type, destPath2.toAbsolutePath().toString(), tenantReqInDB);
			Document document3 = new Document("tenantPhoto", destPath3.toAbsolutePath().toString(), tenantReqInDB);

			tenantReqApiService
					.executeQuery("DELETE FROM Document doc where doc.tenantRequest.id = " + tenantReqInDB.getId());

			List<Document> documents = new ArrayList<>(Arrays.asList(document1, document2, document3));
			tenantReqInDB.setDocuments(documents);
			tenantReqApiService.update(tenantReqInDB);
		} catch (Exception e) {
			if (destPath1 != null)
				destPath1.toFile().delete();
			if (destPath2 != null)
				destPath2.toFile().delete();
			if (destPath3 != null)
				destPath3.toFile().delete();
			throw new RuntimeException("Error in file uploading..!");
		}
		return "Succesfully uploaded..!";
	}

	/* Api to add a new document to the tenant Request */
	@PostMapping("/u/docs/{id}/add-doc")
	public String addDocument(@PathVariable String id, @RequestParam(value = "doc", required = false) MultipartFile doc,
			@RequestParam(value = "docType", required = false) String docType) {
		if (doc == null || doc.getSize() <= 0 || docType == null || docType.trim().isEmpty())
			return "Request Body is Missing..!";
		long tid = 0;
		try {
			tid = Long.parseLong(id);
		} catch (Exception e) {
			return "Invalid url..!";
		}
		TenantRequest tenantReqInDB = tenantReqApiService.get(TenantRequest.class, tid);
		String docURL = uploadFile(doc, tid);
		tenantReqInDB.getDocuments().add(new Document(docType, docURL, tenantReqInDB));
		tenantReqApiService.update(tenantReqInDB);
		return "Successfully Added..!";
	}

	/* Api to Delete user document */
	@DeleteMapping("/u/docs/{id}/docId/{docId}")
	public String deleteDocument(@PathVariable String id, @PathVariable String docId) {
		long tid = 0, documentId = 0;
		try {
			tid = Long.parseLong(id);
			documentId = Long.parseLong(docId);
		} catch (Exception e) {
			return "Invalid url..!";
		}
		TenantRequest tenantRequestInDB = tenantReqApiService.get(TenantRequest.class, tid);
		if (tenantRequestInDB == null)
			return "Invalid url..!";
		tenantReqApiService.executeQuery("DELETE FROM Document doc where doc.id = " + documentId);
		return "Successfully Deleted..!";
	}

	/* Function to upload a new File */
	private String uploadFile(MultipartFile doc, long tid) {
		Path destPath = Paths.get(utils.getSystemPath() + "/tenant_documents/tenantReq_" + tid + "/documents/"
				+ doc.getOriginalFilename());
		try {
			byte[] bytes = doc.getBytes();
			if (!destPath.toFile().exists())
				destPath.toFile().getParentFile().mkdirs();
			Files.write(destPath, bytes);
		} catch (Exception e) {
			if (destPath != null)
				destPath.toFile().delete();
			throw new RuntimeException("Error from AdminApiController while uploading File..!");
		}
		return destPath.toAbsolutePath().toString();
	}

	/* Function to delete the existing File */
//	private void deleteFile(String url) {
//		if (url == null || url.trim().isEmpty())
//			return;
//		Path destPath = Paths.get(url);
//		if (destPath != null)
//			destPath.toFile().delete();
//	}

	/* Function to return all the images of the verified property */
	@GetMapping("/e/property/verified/{id}")
	public Object getAllVerifiedImages(@PathVariable String id) {
		long pid = 0;
		try {
			pid = Long.parseLong(id);
		} catch (Exception e) {
			return "Invalid url..!";
		}
		Property property = propertyApiService.get(Property.class, pid);
		if (property == null)
			return "Invalid url..!";
		List<Object> res = new ArrayList<>();
		List<PropertyImage> propImgs = property.getPropertyImages();
		for (PropertyImage propImg : propImgs) {
			Map<String, String> map = new HashMap<>();
			map.put("_id", propImg.getId() + "");
			map.put("URL", propImg.getImageURL());
			res.add(map);
		}
		return res;
	}

	/* Api to Delete the image of the verified property */
	@DeleteMapping("/e/property/verified/{id}/img/{imgId}")
	public String deleteVerifiedImage(@PathVariable String id, @PathVariable String imgId) {
		long pid = 0, imageId = 0;
		try {
			pid = Long.parseLong(id);
			imageId = Long.parseLong(imgId);
		} catch (Exception e) {
			return "Invalid url..!";
		}
		Property propertyInDB = propertyApiService.get(Property.class, pid);
		if (propertyInDB == null)
			return "Invalid url..!";
		propertyApiService.executeQuery("DELETE FROM PropertyImage prop where prop.id = " + imageId);
		return "Successfully Deleted..!";
	}

	/* Function to add image into the verified property */
	@PostMapping("/e/property/verified/{id}")
	public String uploadImgToVerifiedProperty(@PathVariable String id,
			@RequestParam(value = "images", required = false) List<MultipartFile> images) {
		long pid = 0;
		try {
			pid = Long.parseLong(id);
		} catch (Exception e) {
			return "Invalid url..!";
		}
		if (images.get(0).getSize() <= 0)
			return "Request Body is Missing..!";
		Property property = propertyApiService.get(Property.class, pid);
		if (property == null)
			return "Invalid url..!";

		List<String> newImgURLs = uploadImgsToVerified(images, pid);
		if (newImgURLs == null || newImgURLs.isEmpty()) {
			deleteImgsFromVerified(pid);
			throw new RuntimeException("Exception from AdminApi Controller while Uploading Property Images");
		}
		try {
			for (String newImageURL : newImgURLs)
				property.getPropertyImages().add(new PropertyImage(newImageURL, property));
			propertyApiService.update(property);
		} catch (Exception e) {
			deleteImgsFromVerified(pid);
			throw new RuntimeException("Exception from AdminApi Controller while Saving Property Images");
		}
		return "successfully uploaded..!";
	}

	/* Function to upload new Images to verified */
	private List<String> uploadImgsToVerified(List<MultipartFile> images, long pid) {
		List<String> urls = new ArrayList<>();
		for (MultipartFile image : images) {
			Path path = Paths.get(
					utils.getSystemPath() + "/properties/verified/property_" + pid + "/" + image.getOriginalFilename());
			/* Creating each file inside the root directory */
			if (!path.toFile().exists())
				path.toFile().getParentFile().mkdirs();
			try {
				byte[] bytes = image.getBytes();
				/* writing the image into the root directory */
				Files.write(path, bytes);
				urls.add(path.toAbsolutePath().toString());
			} catch (IOException e) {
				deleteImgsFromVerified(pid);
				return null;
			}
		}
		return urls;
	}

	/* Function to delete new Images to verified */
	private void deleteImgsFromVerified(long pid) {
		Path path = Paths.get(utils.getSystemPath() + "/properties/verified/property_" + pid + "/");
		if (path != null)
			path.toFile().getParentFile().delete();
	}

	/* Function to return all the images of the unverified property */
	@GetMapping("/e/property/unverified/{id}")
	public Object getAllUnVerifiedImages(@PathVariable String id) {
		long pid = 0;
		try {
			pid = Long.parseLong(id);
		} catch (Exception e) {
			return "Invalid url..!";
		}
		PropertyReq propertyReq = propertyReqApiService.get(PropertyReq.class, pid);
		if (propertyReq == null)
			return "Invalid url..!";
		List<Object> res = new ArrayList<>();
		List<PropertyReqImage> propImgs = propertyReq.getPropertyReqImages();
		for (PropertyReqImage propImg : propImgs) {
			Map<String, String> map = new HashMap<>();
			map.put("_id", propImg.getId() + "");
			map.put("URL", propImg.getImageURL());
			res.add(map);
		}
		return res;
	}

	/* Api to Delete the image of the unverified property */
	@DeleteMapping("/e/property/unverified/{id}/img/{imgId}")
	public String deleteunVerifiedImage(@PathVariable String id, @PathVariable String imgId) {
		long pid = 0, imageId = 0;
		try {
			pid = Long.parseLong(id);
			imageId = Long.parseLong(imgId);
		} catch (Exception e) {
			return "Invalid url..!";
		}
		PropertyReq propertyReqInDB = propertyReqApiService.get(PropertyReq.class, pid);
		if (propertyReqInDB == null)
			return "Invalid url..!";
		propertyReqApiService.executeQuery("DELETE FROM PropertyReqImage prop where prop.id = " + imageId);
		return "Successfully Deleted..!";
	}

	/* Function to add image into the unverified property */
	@PostMapping("/e/property/unverified/{id}")
	public String uploadImgToUnVerifiedProperty(@PathVariable String id,
			@RequestParam(value = "images", required = false) List<MultipartFile> images) {
		long pid = 0;
		try {
			pid = Long.parseLong(id);
		} catch (Exception e) {
			return "Invalid url..!";
		}
		if (images.get(0).getSize() <= 0)
			return "Request Body is Missing..!";
		PropertyReq propertyReq = propertyReqApiService.get(PropertyReq.class, pid);
		if (propertyReq == null)
			return "Invalid url..!";

		List<String> newImgURLs = uploadImgsToUnVerified(images, pid);
		if (newImgURLs == null) {
			deleteImgsFromUnVerified(pid);
			throw new RuntimeException("Exception from AdminApi Controller while Uploading PropertyReq Images");
		}
		try {
			for (String newImageURL : newImgURLs)
				propertyReq.getPropertyReqImages().add(new PropertyReqImage(newImageURL, propertyReq));
			propertyReqApiService.update(propertyReq);
		} catch (Exception e) {
			deleteImgsFromUnVerified(pid);
			throw new RuntimeException("Exception from AdminApi Controller while Saving PropertyReq Images");
		}
		return "successfully uploaded..!";
	}

	/* Function to upload new Images to unverified */
	private List<String> uploadImgsToUnVerified(List<MultipartFile> images, long pid) {
		List<String> urls = new ArrayList<>();
		for (MultipartFile image : images) {
			Path path = Paths.get(utils.getSystemPath() + "/properties/unverified/property_" + pid + "/"
					+ image.getOriginalFilename());
			/* Creating each file inside the root directory */
			if (!path.toFile().exists())
				path.toFile().getParentFile().mkdirs();
			try {
				byte[] bytes = image.getBytes();
				/* writing the image into the root directory */
				Files.write(path, bytes);
				urls.add(path.toAbsolutePath().toString());
			} catch (IOException e) {
				deleteImgsFromUnVerified(pid);
				return null;
			}
		}
		return urls;
	}

	/* Function to delete new Images to unverified */
	private void deleteImgsFromUnVerified(long pid) {
		Path path = Paths.get(utils.getSystemPath() + "/properties/unverified/property_" + pid + "/");
		if (path != null)
			path.toFile().getParentFile().delete();
	}
}
