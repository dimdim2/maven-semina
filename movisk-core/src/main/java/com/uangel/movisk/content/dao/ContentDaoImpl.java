package com.uangel.movisk.content.dao;

import org.springframework.stereotype.Repository;

import com.uangel.movisk.content.model.Content;
import com.uangel.platform.dao.HibernateGenericDao;

@Repository
public class ContentDaoImpl extends HibernateGenericDao<Content, Long> implements ContentDao {

}
