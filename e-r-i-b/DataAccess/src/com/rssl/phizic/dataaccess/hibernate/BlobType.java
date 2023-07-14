package com.rssl.phizic.dataaccess.hibernate;

import org.hibernate.usertype.UserType;
import org.hibernate.HibernateException;
import org.apache.commons.io.IOUtils;

import java.sql.Types;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.io.*;
import java.util.Arrays;

public class BlobType implements UserType
{

	public BlobType()
	{
		super();
	}

	public int[] sqlTypes()
	{
		return new int[]{Types.BLOB};
	}

	public Class returnedClass()
	{
		return String.class;
	}

	public boolean equals(Object x, Object y) throws HibernateException
	{
		if(x == null && y == null)
			return true;

		if(x == null)
			return false;

		if(y == null)
			return false;

		byte[] xa = (byte[]) x;
		byte[] ya = (byte[]) y;

		return Arrays.equals(xa, ya);
	}

	public int hashCode(Object x) throws HibernateException
	{
		return x.hashCode();
	}

	public Object nullSafeGet(ResultSet rs, String[] names, Object owner) throws HibernateException, SQLException
	{

		InputStream           is  = null;
		ByteArrayOutputStream os  = null;
		byte[]                res = null;

		try
		{
			is = rs.getBinaryStream(names[0]);
			if(is != null)
			{
				os = new ByteArrayOutputStream();
				IOUtils.copy(is, os);
				res = os.toByteArray();
			}
			return res;
		}
		catch (IOException ioe)
		{
			throw new HibernateException("Unable to read from resultset ", ioe);
		}
		finally
		{
			IOUtils.closeQuietly(is);
			IOUtils.closeQuietly(os);
		}
	}

	public void nullSafeSet(PreparedStatement st, Object value, int index) throws HibernateException, SQLException
	{
		byte[] buf = (byte[]) value;

		if(buf == null)
		{
			st.setNull(index, Types.BLOB);
		}
		else
		{
			ByteArrayInputStream bais = new ByteArrayInputStream(buf);
			st.setBinaryStream(index, bais, buf.length);
		}

	}

	public Object deepCopy(Object value) throws HibernateException
	{
		if(value == null)
			return null;

		byte[] buf = (byte[]) value;
		byte[] res = new byte[buf.length];
		System.arraycopy(buf, 0, res, 0, buf.length);
		return res;
	}

	public boolean isMutable()
	{
		return true;
	}

	public Serializable disassemble(Object value) throws HibernateException
	{
		return (byte[])value;
	}

	public Object assemble(Serializable cached, Object owner) throws HibernateException
	{
		return deepCopy(cached);
	}

	public Object replace(Object original, Object target, Object owner) throws HibernateException
	{
		return deepCopy(original);
	}
}