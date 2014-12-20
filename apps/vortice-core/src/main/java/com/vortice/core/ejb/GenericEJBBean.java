package com.vortice.core.ejb;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

import com.vortice.core.abstracao.Entidade;
import com.vortice.core.exception.AplicacaoException;
import com.vortice.core.persistencia.GenericJPADAO;

@SuppressWarnings({"unused"})
public class GenericEJBBean<E extends Entidade> {

	private EntityManager	entityManager;
	
	private GenericJPADAO<E> genericJPADAO;
	
	public E insert(E e) throws AplicacaoException{
		return genericJPADAO.insert(e);
	}
	
	public void update(E e) throws AplicacaoException{
		genericJPADAO.update(e);
	}
	
	public void delete(E e) throws AplicacaoException{
		genericJPADAO.delete(e);
	}
	
	public E findByPrimaryKey(E e) throws AplicacaoException{
		return genericJPADAO.findByPrimaryKey(e);
	}
	
	public List<E> findAll() throws AplicacaoException{
		return genericJPADAO.findAll();
	}
	
	public List<E> findByFilter(E e) throws AplicacaoException{
		return genericJPADAO.findByFilter(e);
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public void setDAO(GenericJPADAO<E> dao) {
		this.genericJPADAO = dao;
	}
	
	public GenericJPADAO<E> getDAO() {
		return genericJPADAO;
	}
}