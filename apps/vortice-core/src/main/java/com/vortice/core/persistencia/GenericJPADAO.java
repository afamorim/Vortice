package com.vortice.core.persistencia;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.vortice.core.abstracao.Entidade;

@SuppressWarnings("unchecked")
public class GenericJPADAO<E extends Entidade> {

	private EntityManager	entityManager;
	
	private Class<E>		eClass;
	
	public GenericJPADAO(EntityManager aEntityManager){
		entityManager = aEntityManager;
		if (aEntityManager == null){
			throw new RuntimeException("O Objeto DAO não recebeu uma conexão apropriada, contate o administrador.");
		}
		eClass = (Class<E>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	public E insert(E e){
		entityManager.clear();
		entityManager.persist(e);
		
		return e;
	}
	
	public void update(E e){
		entityManager.merge(e);
	}
	
	public void delete(E e){
		e = entityManager.merge(e);
		entityManager.remove(e);
	}
	
	public E findByPrimaryKey(E e){
		return (E)entityManager.find(e.getClass(), e.getCodigo());
	}
	
	public List<E> findAll(){
		String sql = (new StringBuilder().append("SELECT obj FROM ").append(eClass.getSimpleName()).append(" obj")).toString();
		Query query = entityManager.createQuery(sql);
		return query.getResultList();
	}
	
	//TODO DESENVOLVER MÉTODO FIND BY FILTER
	public List<E> findByFilter(E e){
		return null;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager aEntityManager) {
		if (aEntityManager == null){
			throw new RuntimeException("O Objeto DAO não recebeu uma conexão apropriada, contate o administrador.");
		}
		this.entityManager = aEntityManager;
	}
	
}
