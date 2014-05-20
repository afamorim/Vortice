package com.vortice.core.ejb;

import java.util.List;

import javax.persistence.EntityManager;

import com.vortice.core.abstracao.Entidade;
import com.vortice.core.persistencia.GenericJPADAO;

public class GenericEJBBean<E extends Entidade> {

	private EntityManager	entityManager;
	
	private GenericJPADAO<E> genericJPADAO;
	
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

	public void setGenericJPADAO(GenericJPADAO<E> genericJPADAO) {
		this.genericJPADAO = genericJPADAO;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}
