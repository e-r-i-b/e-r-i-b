package com.rssl.phizic.security.certification;

import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.security.crypto.Certificate;
import com.rssl.phizic.security.crypto.CryptoProvider;
import com.rssl.phizic.security.crypto.CryptoProviderHelper;
import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * @author Evgrafov
 * @ created 01.06.2006
 * @ $Author: mescheryakova $
 * @ $Revision: 22429 $
 */

public class CertificateType implements UserType
{
    private static final int[] SQL_TYPES = {Types.VARCHAR};
	private CryptoProvider provider;

	public int[] sqlTypes()
    {
        return SQL_TYPES.clone();
    }

    public Class returnedClass()
    {
        return Certificate.class;
    }

    public boolean equals(Object x, Object y)
    {
        if( x == null && y == null)
            return true;

        if( x == null)
            return false;

        return x.equals(y);
    }

    public int hashCode(Object x) throws HibernateException
    {
        return x.hashCode();
    }

    public Object deepCopy(Object value) throws HibernateException
    {
	    return value;
    }

    public boolean isMutable()
    {
        return false;
    }

    public Object nullSafeGet(ResultSet resultSet, String[] names, Object owner) throws HibernateException, SQLException
    {
        String certId = resultSet.getString(names[0]);

	    try
	    {
		    return getProvider().findCertificate(certId);
	    }
	    catch (SecurityException ex)
	    {
		    throw new HibernateException(ex);
	    }
	    catch (SecurityLogicException ex)
	    {
		    throw new HibernateException(ex);
	    }
    }

	private synchronized CryptoProvider getProvider() throws SecurityException
	{
		if (provider == null)
			provider = CryptoProviderHelper.getDefaultFactory().getProvider();
		return provider;
	}

	public void nullSafeSet(PreparedStatement statement, Object value, int index) throws HibernateException, SQLException
    {
        if (value == null)
        {
            statement.setNull(index, Types.BIGINT);
        }
        else
        {
	        Certificate certificate = (Certificate) value;

	        statement.setString(index, certificate.getId());
        }
    }

	public Object assemble(Serializable cached, Object owner) throws HibernateException
    {
        return deepCopy(cached);
    }

    public Serializable disassemble(Object value) throws HibernateException
    {
        return (Serializable) value;
    }

    public Object replace(Object original, Object target, Object owner) throws HibernateException
    {
        return original;
    }
}