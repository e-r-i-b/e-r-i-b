package com.rssl.phizic.dataaccess.hibernate;

import org.hibernate.type.Type;
import org.hibernate.type.ForeignKeyDirection;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.engine.Mapping;
import org.hibernate.HibernateException;
import org.hibernate.EntityMode;
import org.hibernate.MappingException;
import org.dom4j.Node;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.Map;

/**
 * Предназначен для передачи нетипизированных null параметров в Hibernate
 * @author Evgrafov
 * @ created 09.02.2006
 * @ $Author: Evgrafov $
 * @ $Revision: 1564 $
 * @noinspection ClassWithTooManyMethods
 */
public final class NullParameterType implements Type
{

	public static final Type   INSTANCE = new NullParameterType();
	public static final Object VALUE    = new Object();

	private NullParameterType()
	{
	}

	/**
	 * Reconstruct the object from its cached "disassembled" state.
	 *
	 * @param cached  the disassembled state from the cache
	 * @param session the session
	 * @param owner   the parent entity object
	 * @return the the object
	 */
	public Object assemble(Serializable cached, SessionImplementor session, Object owner) throws HibernateException
	{
		throw new UnsupportedOperationException();
	}

	public void beforeAssemble(Serializable cached, SessionImplementor session)
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * compare two instances of the type
	 *
	 * @param entityMode
	 */
	public int compare(Object x, Object y, EntityMode entityMode)
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * Return a deep copy of the persistent state, stopping at entities and at collections.
	 *
	 * @param value      generally a collection element or entity field
	 * @param entityMode
	 * @param factory    
	 * @return Object a copy
	 */
	public Object deepCopy(Object value, EntityMode entityMode, SessionFactoryImplementor factory) throws HibernateException
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * Return a cacheable "disassembled" representation of the object.
	 *
	 * @param value   the value to cache
	 * @param session the session
	 * @param owner   optional parent entity object (needed for collections)
	 * @return the disassembled, deep cloned state
	 */
	public Serializable disassemble(Object value, SessionImplementor session, Object owner) throws HibernateException
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * Parse the XML representation of an instance.
	 *
	 * @param xml
	 * @param factory
	 * @return an instance of the type
	 * @throws org.hibernate.HibernateException
	 *
	 */
	public Object fromXMLNode(Node xml, Mapping factory) throws HibernateException
	{
		throw new UnsupportedOperationException();
	}

	/** How many columns are used to persist this type. */
	public int getColumnSpan(Mapping mapping) throws MappingException
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * Get a hashcode, consistent with persistence "equality"
	 *
	 * @param x
	 * @param entityMode
	 */
	public int getHashCode(Object x, EntityMode entityMode) throws HibernateException
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * Get a hashcode, consistent with persistence "equality"
	 *
	 * @param x
	 * @param entityMode
	 * @param factory
	 */
	public int getHashCode(Object x, EntityMode entityMode, SessionFactoryImplementor factory) throws HibernateException
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * Returns the abbreviated name of the type.
	 *
	 * @return String the Hibernate type name
	 */
	public String getName()
	{
		return "null_parameter_type";
	}

	/**
	 * The class returned by <tt>nullSafeGet()</tt> methods. This is used to establish the class of an array of
	 * this type.
	 *
	 * @return Class
	 */
	public Class getReturnedClass()
	{
		throw new UnsupportedOperationException();
	}

	/** Get the type of a semi-resolved value. */
	public Type getSemiResolvedType(SessionFactoryImplementor factory)
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * Retrieve an instance of the mapped class, or the identifier of an entity or collection, from a JDBC
	 * resultset. This is useful for 2-phase property initialization - the second phase is a call to
	 * <tt>resolveIdentifier()</tt>.
	 *
	 * @param rs
	 * @param names   the column names
	 * @param session the session
	 * @param owner   the parent entity
	 * @return Object an identifier or actual value
	 * @throws org.hibernate.HibernateException
	 * @see org.hibernate.type.Type#resolve(Object, org.hibernate.engine.SessionImplementor, Object)
	 */
	public Object hydrate(ResultSet rs, String[] names, SessionImplementor session, Object owner) throws HibernateException
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * Is this an "any" type.
	 * <p/>
	 * i.e. a reference to a persistent entity that is not modelled as a (foreign key) association.
	 */
	public boolean isAnyType()
	{
		return false;
	}

	/**
	 * Return true if the implementation is castable to <tt>AssociationType</tt>. This does not necessarily imply
	 * that the type actually represents an association.
	 *
	 * @return boolean
	 * @see org.hibernate.type.AssociationType
	 */
	public boolean isAssociationType()
	{
		return false;
	}

	/** Is this type a collection type. */
	public boolean isCollectionType()
	{
		return false;
	}

	/**
	 * Is this type a component type. If so, the implementation must be castable to
	 * <tt>AbstractComponentType</tt>. A component type may own collections or associations and hence must
	 * provide certain extra functionality.
	 *
	 * @return boolean
	 * @see org.hibernate.type.AbstractComponentType
	 */
	public boolean isComponentType()
	{
		return false;
	}

	/**
	 * Should the parent be considered dirty, given both the old and current field or element value?
	 *
	 * @param old     the old value
	 * @param current the current value
	 * @param session
	 * @return true if the field is dirty
	 */
	public boolean isDirty(Object old, Object current, SessionImplementor session) throws HibernateException
	{
		throw new UnsupportedOperationException();
	}

	public boolean isDirty(Object old, Object current, boolean[] checkable, SessionImplementor session) throws HibernateException
	{
		throw new UnsupportedOperationException();
	}

	public boolean isModified(Object oldHydratedState, Object currentState, boolean[] checkable, SessionImplementor session) throws HibernateException
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * Is this type an entity type?
	 *
	 * @return boolean
	 */
	public boolean isEntityType()
	{
		return false;
	}

	/**
	 * Compare two instances of the class mapped by this type for persistence "equality" - equality of persistent
	 * state.
	 *
	 * @param x
	 * @param y
	 * @param entityMode
	 * @return boolean
	 * @throws org.hibernate.HibernateException
	 *
	 */
	public boolean isEqual(Object x, Object y, EntityMode entityMode) throws HibernateException
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * Compare two instances of the class mapped by this type for persistence "equality" - equality of persistent
	 * state.
	 *
	 * @param x
	 * @param y
	 * @param entityMode
	 * @return boolean
	 * @throws org.hibernate.HibernateException
	 *
	 */
	public boolean isEqual(Object x, Object y, EntityMode entityMode, SessionFactoryImplementor factory) throws HibernateException
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * Are objects of this type mutable. (With respect to the referencing object ... entities and collections are
	 * considered immutable because they manage their own internal state.)
	 *
	 * @return boolean
	 */
	public boolean isMutable()
	{
		return false;
	}

	/**
	 * Compare two instances of the class mapped by this type for persistence "equality" - equality of persistent
	 * state - taking a shortcut for entity references.
	 *
	 * @param x
	 * @param y
	 * @param entityMode
	 * @return boolean
	 * @throws org.hibernate.HibernateException
	 *
	 */
	public boolean isSame(Object x, Object y, EntityMode entityMode) throws HibernateException
	{
		return true;
	}

	public boolean isXMLElement()
	{
		return false;
	}

	/**
	 * Retrieve an instance of the mapped class from a JDBC resultset. Implementations should handle possibility
	 * of null values. This method might be called if the type is known to be a single-column type.
	 *
	 * @param rs
	 * @param name    the column name
	 * @param session
	 * @param owner   the parent entity
	 * @return Object
	 * @throws org.hibernate.HibernateException
	 */
	public Object nullSafeGet(ResultSet rs, String name, SessionImplementor session, Object owner) throws HibernateException
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * Retrieve an instance of the mapped class from a JDBC resultset. Implementors should handle possibility of
	 * null values.
	 *
	 * @param rs
	 * @param names   the column names
	 * @param session
	 * @param owner   the parent entity
	 * @return Object
	 * @throws org.hibernate.HibernateException
	 * @see org.hibernate.type.Type#hydrate(java.sql.ResultSet, String[], org.hibernate.engine.SessionImplementor,
	 *      Object) alternative, 2-phase property initialization
	 */
	public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner) throws HibernateException
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * Write an instance of the mapped class to a prepared statement. Implementors should handle possibility of
	 * null values. A multi-column type should be written to parameters starting from <tt>index</tt>.
	 *
	 * @param st
	 * @param value   the object to write
	 * @param index   statement parameter index
	 * @param session
	 * @throws org.hibernate.HibernateException
	 *
	 * @throws java.sql.SQLException
	 */
	public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session) throws HibernateException, SQLException
	{
		st.setNull(index, Types.NULL);
	}

	/**
	 * Write an instance of the mapped class to a prepared statement, ignoring some columns. Implementors should
	 * handle possibility of null values. A multi-column type should be written to parameters starting from
	 * <tt>index</tt>.
	 *
	 * @param st
	 * @param value    the object to write
	 * @param index    statement parameter index
	 * @param settable an array indicating which columns to ignore
	 * @param session
	 * @throws org.hibernate.HibernateException
	 *
	 * @throws java.sql.SQLException
	 */
	public void nullSafeSet(PreparedStatement st, Object value, int index, boolean[] settable, SessionImplementor session) throws HibernateException, SQLException
	{
		if(settable[0])
			nullSafeSet(st, value, index, session);
	}

	/**
	 * During merge, replace the existing (target) value in the entity we are merging to with a new (original)
	 * value from the detached entity we are merging. For immutable objects, or null values, it is safe to simply
	 * return the first parameter. For mutable objects, it is safe to return a copy of the first parameter. For
	 * objects with component values, it might make sense to recursively replace component values.
	 *
	 * @param original the value from the detached entity being merged
	 * @param target   the value in the managed entity
	 * @return the value to be merged
	 */
	public Object replace(Object original, Object target, SessionImplementor session, Object owner, Map copyCache) throws HibernateException
	{
		throw new UnsupportedOperationException();
	}

	public Object replace(Object original, Object target, SessionImplementor session, Object owner, Map copyCache, ForeignKeyDirection foreignKeyDirection) throws HibernateException
	{
		throw new UnsupportedOperationException();
	}

	public boolean[] toColumnNullness(Object value, Mapping mapping)
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * Map identifiers to entities or collections. This is the second phase of 2-phase property initialization.
	 *
	 * @param value   an identifier or value returned by <tt>hydrate()</tt>
	 * @param owner   the parent entity
	 * @param session the session
	 * @return the given value, or the value associated with the identifier
	 * @throws org.hibernate.HibernateException
	 *
	 * @see org.hibernate.type.Type#hydrate(java.sql.ResultSet, String[], org.hibernate.engine.SessionImplementor,
	 *      Object)
	 */
	public Object resolve(Object value, SessionImplementor session, Object owner) throws HibernateException
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * Given a hydrated, but unresolved value, return a value that may be used to reconstruct property-ref
	 * associations.
	 */
	public Object semiResolve(Object value, SessionImplementor session, Object owner) throws HibernateException
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * A representation of the value to be embedded in an XML element.
	 *
	 * @param value
	 * @param factory
	 * @throws org.hibernate.HibernateException
	 *
	 */
	public void setToXMLNode(Node node, Object value, SessionFactoryImplementor factory) throws HibernateException
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * Return the SQL type codes for the columns mapped by this type. The codes are defined on
	 * <tt>java.sql.Types</tt>.
	 *
	 * @return the typecodes
	 * @throws org.hibernate.MappingException
	 * @see java.sql.Types
	 */
	public int[] sqlTypes(Mapping mapping) throws MappingException
	{
		return new int[0];
	}

	/**
	 * A representation of the value to be embedded in a log file.
	 *
	 * @param value
	 * @param factory
	 * @return String
	 * @throws org.hibernate.HibernateException
	 *
	 */
	public String toLoggableString(Object value, SessionFactoryImplementor factory) throws HibernateException
	{
		return "null parameter";
	}
}