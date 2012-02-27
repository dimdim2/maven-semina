package com.uangel.movisk.content.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uangel.movisk.content.dao.ContentDao;
import com.uangel.movisk.content.model.Content;
import com.uangel.platform.dao.GenericDao;
import com.uangel.platform.service.AbstractGenericService;

@Service
public class ContentServiceImpl extends AbstractGenericService<Content, Long> implements ContentService {

	@Autowired
	private ContentDao contentDao;

	protected GenericDao<Content, Long> getGenericDao() {
		return contentDao;
	}

}
