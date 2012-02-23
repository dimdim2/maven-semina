package com.uangel.movisk.content.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.displaytag.pagination.PaginatedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.uangel.movisk.content.model.Content;
import com.uangel.movisk.content.service.ContentService;
import com.uangel.platform.model.Pageable;
import com.uangel.platform.support.web.PaginatedListImpl;

@Controller
@SessionAttributes("content")
public class ContentController {

	@Autowired
	private ContentService contentService;

	@RequestMapping(value = "/content/create.htm", method = RequestMethod.GET)
	public String createForm(Model model, HttpSession session) {
		model.addAttribute("content", new Content());
		return "content/create";
	}

	@RequestMapping(value = "/content/create.htm", method = RequestMethod.POST)
	public String create(@ModelAttribute Content content,
			@RequestParam(value = "functionNames", required = false) String[] functionNames,
			@RequestParam(value = "functionUrls", required = false) String[] functionUrls,
			@RequestParam(value = "functionTypes", required = false) String[] functionTypes,
			SessionStatus sessionStatus) {

		content.setCreateTime(new Date());
		contentService.create(content);
		sessionStatus.setComplete();

		return "redirect:/content/detail.htm?id=" + content.getId();
	}

	@RequestMapping("/content/delete.json")
	@ResponseBody
	public Map<String, Object> delete(@RequestParam("id") Long id) {

		Map<String, Object> result = new HashMap<String, Object>();

		Content content = contentService.findById(id);
		if (content != null) {
			contentService.delete(content);
			result.put("isSuccess", true);
			result.put("resultMsg", String.format("Delete Content [ID:%s, Name:%s]", id, content.getName()));

		} else {
			result.put("isSuccess", false);
			result.put("resultMsg", String.format("Not Exist Content [ID:%s]", id));
		}

		return result;
	}

	@RequestMapping("/content/detail.htm")
	public String detail(Model model, @RequestParam("id") Long id) throws Exception {
		model.addAttribute("content", contentService.findById(id));
		return "content/detail";
	}

	@RequestMapping("/content/search.htm")
	public String search(Model model,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rowPerPage", defaultValue = "10") int rowPerPage,
			@RequestParam(value = "name", required = false) String name) {

		PaginatedList contents = null;

		Content content = new Content();
		if (StringUtils.isNotEmpty(name)) {
			content.setName(name);
		}

		List<Content> list = contentService.find(content, new Pageable(page, rowPerPage));
		contents = new PaginatedListImpl(list, page, contentService.count(content), rowPerPage);

		model.addAttribute("contents", contents);
		return "content/search";
	}

	@RequestMapping(value = "/content/update.htm", method = RequestMethod.GET)
	public String updateForm(Model model, @RequestParam("id") Long id) throws Exception {
		model.addAttribute("content", contentService.findById(id));
		return "content/update";
	}

	@RequestMapping(value = "/content/update.htm", method = RequestMethod.POST)
	public String update(@ModelAttribute Content content,
			@RequestParam(value = "functionNames", required = false) String[] functionNames,
			@RequestParam(value = "functionUrls", required = false) String[] functionUrls,
			@RequestParam(value = "functionTypes", required = false) String[] functionTypes,
			SessionStatus sessionStatus) {

		content.setUpdateTime(new Date());
		contentService.update(content);
		sessionStatus.setComplete();

		return "redirect:/content/detail.htm?id=" + content.getId();
	}
}
