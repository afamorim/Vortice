package com.vortice.core.persistencia;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Example;

import com.vortice.core.abstracao.Entidade;

@SuppressWarnings("unchecked")
public class GenericJPADAO<E extends Entidade> {

	private EntityManager	entityManager;
	
	private Class<E>		entityClass;
	
//	public GenericJPADAO(EntityManager aEntityManager){
//		entityManager = aEntityManager;
//		if (aEntityManager == null){
//			throw new RuntimeException("O Objeto DAO não recebeu uma conexão apropriada, contate o administrador.");
//		}
//		entityClass = (Class<E>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
//	}
	
	public GenericJPADAO(){
		entityClass = (Class<E>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
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
//		e = entityManager.merge(e);
//		entityManager.remove(e);
		getEntityManager().remove(getEntityManager().contains(e) ? e : getEntityManager().merge(e));
        getEntityManager().flush();
	}
	
	public E findByPrimaryKey(E e){
		return (E)entityManager.find(e.getClass(), e.getCodigo());
	}
	
	public List<E> findAll(){
		String sql = (new StringBuilder().append("SELECT obj FROM ").append(entityClass.getSimpleName()).append(" obj")).toString();
		Query query = entityManager.createQuery(sql);
		return query.getResultList();
	}
	
	public List<E> findByFilter(E e){
		Criteria c = ((Session)getEntityManager().getDelegate()).createCriteria(getEntityClass());
		c.add(Example.create(e).enableLike());
		return c.list();
//		return null;
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
	
	public Class<E> getEntityClass() {
		if (entityClass == null) {
			ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
			Object o = type.getActualTypeArguments()[0];
			this.entityClass = (Class<E>) o;
		}
		return entityClass;
	}
    
    public void setEntityClass(Class<E> entityClass) {
		this.entityClass = entityClass;
	}

}