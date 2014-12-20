package com.vortice.core.abstracao;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;

public class EnumType implements UserType, ParameterizedType {

	private static final int[] SQL_TYPES = { Types.BIGINT };

	private Class<Entidade<Long>> enumClass;

	@Override
	public int[] sqlTypes() {
		return SQL_TYPES;
	}

	@Override
	public Class<Entidade<Long>> returnedClass() {
		return enumClass;
	}

	@Override
	public boolean equals(Object x, Object y) throws HibernateException {
		if (x == y) {
			return true;
		}
		if (null == x || null == y) {
			return false;
		}
		return x.equals(y);
	}

	@Override
	public int hashCode(Object x) throws HibernateException {
		return x.hashCode();
	}

	@Override
	public Object deepCopy(Object value) throws HibernateException {
		return value;
	}

	@Override
	public boolean isMutable() {
		return false;
	}

	@Override
	public Serializable disassemble(Object value) throws HibernateException {
		return (Serializable) value;
	}

	@Override
	public Object assemble(Serializable cached, Object owner) throws HibernateException {
		return cached;
	}

	@Override
	public Object replace(Object original, Object target, Object owner) throws HibernateException {
		return original;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setParameterValues(Properties parameters) {
		final String enumClassName = parameters.getProperty("enumClass");
		try {
//			enumClass = (Class<BaseDomain<Long>>) Class.forName(enumClassName).asSubclass(Enum.class);
			enumClass = (Class<Entidade<Long>>) Class.forName(enumClassName);
		} catch (ClassNotFoundException e) {
			throw new HibernateException("Classe Enum não encontrada.", e);
		}
	}

	@Override
	public Object nullSafeGet(ResultSet rs, String[] names,
			SessionImplementor sessionImplementor, Object owner) throws HibernateException,
			SQLException {
		Long codigo = rs.getLong(names[0]);

		if (rs.wasNull()) {
			return null;
		}

		for (Entidade<Long> value : returnedClass().getEnumConstants()) {
			if (codigo.equals(value.getCodigo())) {
				return value;
			}
		}

		throw new IllegalStateException("Erro obtendo valor do código para: " + returnedClass().getSimpleName() + ".");
	}

	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index,
			SessionImplementor sessionImplementor) throws HibernateException, SQLException {
		if (value == null) {
			st.setNull(index, Types.BIGINT);
		} else {
			st.setLong(index, ((Entidade<Long>) value).getCodigo());
		}
	}

}
