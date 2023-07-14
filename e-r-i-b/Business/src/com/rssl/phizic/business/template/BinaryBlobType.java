package com.rssl.phizic.business.template;

import org.hibernate.usertype.UserType;
import org.hibernate.HibernateException;
import org.hibernate.Hibernate;

import java.sql.*;
import java.io.Serializable;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created by IntelliJ IDEA.
 * User: Novikov_A
 * Date: 07.03.2007
 * Time: 13:21:14
 * To change this template use File | Settings | File Templates.
 */
public class BinaryBlobType implements UserType
{
  public int[] sqlTypes()
  {
    return new int[] { Types.BLOB };
  }

  public Class returnedClass()
  {
    return byte[].class;
  }

  public int hashCode(Object x) throws HibernateException
  {
	 return 0;
  }

  public Serializable disassemble(Object value) throws HibernateException
  {
	return null;
  }

 public Object assemble(Serializable cached, Object owner) throws HibernateException
 {
	 return null;
 }

public Object replace(Object original, Object target, Object owner) throws HibernateException
{
	return null;
}
	
  public boolean equals(Object x, Object y)
  {
    return (x == y)
      || (x != null
        && y != null
        && java.util.Arrays.equals((byte[]) x, (byte[]) y));
  }

  public Object nullSafeGet(ResultSet rs, String[] names, Object owner)
  throws HibernateException, SQLException
  {
    Blob blob = rs.getBlob(names[0]);
	if(blob == null)
		return null;
    return blob.getBytes(1, (int) blob.length());
  }

  public void nullSafeSet(PreparedStatement st, Object value, int index)
  throws HibernateException, SQLException
  {
    if (value == null)
	   st.setNull(index, Types.BINARY);
	else
    {
//	    byte[] bytes = (byte[]) value;
	    byte[] bytes = toByteArray(Hibernate.createBlob((byte[]) value));
        st.setBytes(index, bytes);
    }
  }

  public Object deepCopy(Object value)
  {
    if (value == null) return null;

    byte[] bytes = (byte[]) value;
    byte[] result = new byte[bytes.length];
    System.arraycopy(bytes, 0, result, 0, bytes.length);

    return result;
  }

  public boolean isMutable()
  {
    return true;
  }

  private byte[] toByteArray(Blob fromBlob)
  {
     ByteArrayOutputStream baos = new ByteArrayOutputStream();
     try
     {
        return toByteArrayImpl(fromBlob, baos);
     }
     catch (SQLException e)
     {
        throw new RuntimeException(e);
     }
     catch (IOException e)
     {
        throw new RuntimeException(e);
     }
     finally
     {
        if (baos != null)
        {
           try
           {
              baos.close();
           }
           catch (IOException ex)
           {   }
        }
     }
 }

  private byte[] toByteArrayImpl(Blob fromBlob, ByteArrayOutputStream baos)throws SQLException, IOException
  {
     byte[] buf = new byte[4000];
     InputStream is = fromBlob.getBinaryStream();

     try
     {
        for (;;)
        {
           int dataSize = is.read(buf);

           if (dataSize == -1)
              break;
           baos.write(buf, 0, dataSize);
        }
     }
     finally
     {
        if (is != null)
        {
           try
           {
              is.close();
           }
           catch (IOException ex)
           {    }
        }
     }

     return baos.toByteArray();
 }
}
