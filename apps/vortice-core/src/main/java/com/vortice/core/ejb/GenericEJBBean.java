package com.vortice.core.ejb;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

import com.vortice.core.abstracao.Entidade;
import com.vortice.core.persistencia.GenericJPADAO;

@SuppressWarnings({"unused"})
public class GenericEJBBean<E extends Entidade, ID extends Serializable> {

	private EntityManager	entityManager;
	
	private GenericJPADAO<E, ID> genericJPADAO;
	
	public E insert(E e){
		return genericJPADAO.insert(e);
	}
	
	public void update(E e){
		genericJPADAO.update(e);
	}
	
	public void delete(E e){
		genericJPADAO.delete(e);
	}
	
	public E findByPrimaryKey(E e){
		return genericJPADAO.findByPrimaryKey(e);
	}
	
	public List<E> findAll(){
		return genericJPADAO.findAll();
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public void setDAO(GenericJPADAO<E, ID> dao) {
		this.genericJPADAO = dao;
	}
	
	public GenericJPADAO<E, ID> getDAO() {
		return genericJPADAO;
	}
}