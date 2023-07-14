package com.rssl.phizic.dataaccess.hibernate;

import org.dom4j.Node;
import org.hibernate.EntityMode;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.engine.Mapping;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.type.NullableType;
import org.hibernate.type.Type;
import org.hibernate.type.ForeignKeyDirection;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * @author Evgrafov
 * @ created 10.02.2006
 * @ $Author: Evgrafov $
 * @ $Revision: 1564 $
 * @noinspection ClassWithTooManyMethods
 */
public class NotNullStringType implements Type
{
	private static final NullableType stringType = Hibernate.STRING;

	public boolean isAssociationType()
	{
		return stringType.isAssociationType();
	}

	public boolean isCollectionType()
	{
		return stringType.isCollectionType();
	}

	public boolean isComponentType()
	{
		return stringType.isComponentType();
	}

	public boolean isEntityType()
	{
		return stringType.isEntityType();
	}

	public boolean isXMLElement()
	{
		return stringType.isXMLElement();
	}

	public int compare(Object x, Object y, EntityMode entityMode)
	{
		return stringType.compare(x, y, entityMode);
	}

	public Serializable disassemble(Object value, SessionImplementor session, Object owner)
			throws HibernateException
	{
		return stringType.disassemble(value, session, owner);
	}

	public Object assemble(Serializable cached, SessionImplementor session, Object owner)
			throws HibernateException
	{
		return stringType.assemble(cached, session, owner);
	}

	public void beforeAssemble(Serializable cached, SessionImplementor session)
	{
		stringType.beforeAssemble(cached, session);
	}

	public boolean isDirty(Object old, Object current, SessionImplementor session)
			throws HibernateException
	{
		return stringType.isDirty(old, current, session);
	}

	public boolean isDirty(Object old, Object current, boolean[] checkable, SessionImplementor session) throws HibernateException
	{
		return stringType.isDirty(old, current, checkable, session);
	}

	public boolean isModified(Object oldHydratedState, Object currentState, boolean[] checkable, SessionImplementor session) throws HibernateException
	{
		return stringType.isModified(oldHydratedState, currentState, checkable, session);
	}

	public Object hydrate(ResultSet rs, String[] names, SessionImplementor session, Object owner)
			throws HibernateException, SQLException
	{
		Object str = stringType.nullSafeGet(rs, names, session, owner);
		return str == null ? "" : str;
	}

	public Object resolve(Object value, SessionImplementor session, Object owner)
			throws HibernateException
	{
		return stringType.resolve(value, session, owner);
	}

	public Object semiResolve(Object value, SessionImplementor session, Object owner)
			throws HibernateException
	{
		return stringType.semiResolve(value, session, owner);
	}

	public boolean isAnyType()
	{
		return stringType.isAnyType();
	}

	public boolean isSame(Object x, Object y, EntityMode entityMode)
			throws HibernateException
	{
		return stringType.isSame(x, y, entityMode);
	}

	public int getHashCode(Object x, EntityMode entityMode)
	{
		return stringType.getHashCode(x, entityMode);
	}

	public boolean isEqual(Object x, Object y, EntityMode entityMode, SessionFactoryImplementor factory)
	{
		return stringType.isEqual(x, y, entityMode, factory);
	}

	public int getHashCode(Object x, EntityMode entityMode, SessionFactoryImplementor factory)
	{
		return stringType.getHashCode(x, entityMode, factory);
	}

	public Type getSemiResolvedType(SessionFactoryImplementor factory)
	{
		return stringType.getSemiResolvedType(factory);
	}

	public void nullSafeSet(PreparedStatement st, Object value, int index, boolean[] settable, SessionImplementor session)
			throws HibernateException, SQLException
	{
		stringType.nullSafeSet(st, value, index, settable, session);
	}

	public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session)
			throws HibernateException, SQLException
	{
		stringType.nullSafeSet(st, value, index, session);
	}

	public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner)
			throws HibernateException, SQLException
	{
		Object str = stringType.nullSafeGet(rs, names, session, owner);
		return str == null ? "" : str;
	}

	public Object nullSafeGet(ResultSet rs, String name, SessionImplementor session, Object owner)
			throws HibernateException, SQLException
	{
		Object str = stringType.nullSafeGet(rs, name, session, owner);
		return str == null ? "" : str;
	}

	public int getColumnSpan(Mapping session)
	{
		return stringType.getColumnSpan(session);
	}

	public int[] sqlTypes(Mapping session)
	{
		return stringType.sqlTypes(session);
	}

	public boolean isEqual(Object x, Object y, EntityMode entityMode)
	{
		return stringType.isEqual(x, y, entityMode);
	}

	public String toLoggableString(Object value, SessionFactoryImplementor factory)
	{
		return stringType.toLoggableString(value, factory);
	}

	public Object fromXMLNode(Node xml, Mapping factory)
			throws HibernateException
	{
		return stringType.fromXMLNode(xml, factory);
	}

	public void setToXMLNode(Node xml, Object value, SessionFactoryImplementor factory)
			throws HibernateException
	{
		stringType.setToXMLNode(xml, value, factory);
	}

	public Object deepCopy(Object value, EntityMode entityMode, SessionFactoryImplementor factory)
	{
		return stringType.deepCopy(value, entityMode, factory);
	}

	public boolean isMutable()
	{
		return stringType.isMutable();
	}

	public Object replace(Object original, Object target, SessionImplementor session, Object owner, Map copyCache)
			throws HibernateException
	{
		return stringType.replace(original, target, session, owner, copyCache);
	}

	public Object replace(Object original, Object target, SessionImplementor session, Object owner, Map copyCache, ForeignKeyDirection foreignKeyDirection) throws HibernateException
	{
		return stringType.replace(original, target, session, owner, copyCache, foreignKeyDirection);
	}

	public boolean[] toColumnNullness(Object value, Mapping mapping)
	{
		return stringType.toColumnNullness(value, mapping);
	}

	public Class getReturnedClass()
	{
		return stringType.getReturnedClass();
	}

	public String getName()
	{
		return stringType.getName();
	}
}