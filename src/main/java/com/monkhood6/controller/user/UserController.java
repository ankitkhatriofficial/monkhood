package com.monkhood6.controller.user;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.monkhood6.model.dto.TenantReqDto;
import com.monkhood6.model.entity.TenantRequest;
import com.monkhood6.model.entity.User;
import com.monkhood6.service.ApiService;
import com.monkhood6.utils.Utils;

@Controller
@RequestMapping("/u")
public class UserController {

	@Autowired
	private ApiService<TenantRequest> tenantReqAPiService;
	@Autowired
	private ApiService<User> userApiService;
	@Autowired
	private ApiService<TenantRequest> tenantReqApiService;
	@Autowired
	private Utils utils;

	@GetMapping("/tenant-form")
	public String showTenantForm(Model model) {
		model.addAttribute("tenantInfo", new TenantReqDto());
		return "tenantForm.jsp";
	}

	@PostMapping("/tenant-form")
	public String submitTenantForm(@Valid @ModelAttribute("tenantInfo") TenantReqDto tenantReqDto, BindingResult result,
			Model model) {

		if (result.hasErrors())
			return "tenantForm.jsp";

		try {
			User requestedBy = userApiService.getSingleResult(User.class, "email", utils.getAuthentication().getName());
			TenantRequest tenantReq = new TenantRequest();
			BeanUtils.copyProperties(tenantReqDto, tenantReq);
			tenantReq.setRequestedBy(requestedBy);
			if (tenantReq.getNoOfMembers() == null || tenantReq.getNoOfMembers().trim().isEmpty())
				tenantReq.setNoOfMembers("1");
			tenantReq.setTenantReqDate(new Date());
			tenantReqAPiService.save(tenantReq);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Exception from UserController while saving TenantInfo..!");
		}

		model.addAttribute("tenantMsg", "Successfully sent..!");
		model.addAttribute("tenantInfo", new TenantReqDto());
		return "tenantForm.jsp";
	}

	@GetMapping("/booking-history")
	public String showBookingHistory(Model model) {
		User user = userApiService.getSingleResult(User.class, "email", utils.getAuthentication().getName());

		List<TenantRequest> list = tenantReqApiService
				.getAllByQuery("from TenantRequest t where t.requestedBy=" + user.getId());
		if (list == null)
			model.addAttribute("bookings", new ArrayList<>());
		else
			model.addAttribute("bookings", list);
		return "bookingHistory.jsp";
	}

	@GetMapping("/withdraw/tenant-req/{id}")
	public String withdrawTenantRequest(@PathVariable String id, Model model) {
		long tid = 0;
		try {
			tid = Long.parseLong(id);
		} catch (Exception e) {
			return "redirect:/error-page";
		}
		try {
			tenantReqApiService.delete(TenantRequest.class, tid);
		} catch (Exception e) {
			throw new RuntimeException("Exception from UserController while withdrawing tenantRequest..!");
		}
		return "redirect:/u/booking-history";
	}

}
