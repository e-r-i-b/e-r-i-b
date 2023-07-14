package com.rssl.phizicgate.rsV55.clients;

import org.hibernate.usertype.UserType;
import org.hibernate.HibernateException;
import com.rssl.phizic.common.types.client.ClientDocumentType;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.Constants;

import java.util.*;
import java.sql.Types;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.io.Serializable;

/**
 * обертка вокруг ClientDocumentType для трансляции типов документов retail в типа документов ИКФЛ, при работе с hiberante
 */
public class ClientDocumentTypeWrapper implements UserType
{
	private static final String REGULAR_PASSPORT_RF     = "Паспорт гражданина РФ";
	private static final String MILITARY_IDCARD         = "Военный билет солдата (матроса, сержанта, старшины)";
	private static final String SEAMEN_PASSPORT         = "Паспорт моряка";
	private static final String RESIDENTIAL_PERMIT_RF   = "Вид на жительство";
	private static final String FOREIGN_PASSPORT_RF     = "Загранпаспорт гражданина РФ";


	private String stateString;
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);

	private final static Map<String,ClientDocumentType> mapping= new HashMap<String,ClientDocumentType>();
	static
	{
		ClientDocumentTypeWrapper.mapping.put(ClientDocumentTypeWrapper.REGULAR_PASSPORT_RF, ClientDocumentType.REGULAR_PASSPORT_RF);
		ClientDocumentTypeWrapper.mapping.put(ClientDocumentTypeWrapper.MILITARY_IDCARD, ClientDocumentType.MILITARY_IDCARD);
		ClientDocumentTypeWrapper.mapping.put(ClientDocumentTypeWrapper.SEAMEN_PASSPORT, ClientDocumentType.SEAMEN_PASSPORT);
		ClientDocumentTypeWrapper.mapping.put(ClientDocumentTypeWrapper.RESIDENTIAL_PERMIT_RF, ClientDocumentType.RESIDENTIAL_PERMIT_RF);
		ClientDocumentTypeWrapper.mapping.put(ClientDocumentTypeWrapper.FOREIGN_PASSPORT_RF, ClientDocumentType.FOREIGN_PASSPORT_RF);
	}

	public String getStateString()
	{
		return stateString;
	}

	public void setStateString(String stateString)
	{
		this.stateString = stateString;
	}

	public ClientDocumentType getStateValue()
	{
		return parseState(stateString);
	}

	private ClientDocumentType parseState(String state)
	{
		if(ClientDocumentTypeWrapper.mapping.containsKey(state))
			return ClientDocumentTypeWrapper.mapping.get(state);
		else
			return ClientDocumentType.OTHER;
	}

	/**
	 * пробразование документа ИКФЛ в документ Retail
	 * при нахождении более одного, возвращаеться первый
	 * @param state
	 * @return
	 * @throws org.hibernate.HibernateException
	 */
	private String parseState(ClientDocumentType state) throws HibernateException
	{
		List<String> result = new ArrayList<String>();
		Set<Map.Entry<String, ClientDocumentType> > set = ClientDocumentTypeWrapper.mapping.entrySet();
		for (Map.Entry<String, ClientDocumentType> entry : set)
		{
			if(entry.getValue() ==state )
			{
				result.add(entry.getKey());
			}
		}

		if(result.size()==0)
			throw new HibernateException("ClientDocumentTypeWrapper:не найдено документа соответствующего " + state.name());
		if(result.size()>1)
		{
			ClientDocumentTypeWrapper.log.info("ClientDocumentTypeWrapper:найдено более одного документа соответствующего " + state.name());
		}
		return result.get(0);
	}

	private static final int[] SQL_TYPES = {Types.VARCHAR};

	public int[] sqlTypes()
	{
		return ClientDocumentTypeWrapper.SQL_TYPES;
	}

	public Class returnedClass()
	{
		return ClientDocumentType.class;
	}

	public boolean equals(Object x, Object y) throws HibernateException
	{
		//так как сравниваем enum'ы
		return x == y;
	}

	public int hashCode(Object x) throws HibernateException
	{
		return x.hashCode();
	}

	public Object nullSafeGet(ResultSet rs, String[] names, Object owner) throws HibernateException, SQLException
	{
        String value = rs.getString(names[0]);

        if (!rs.wasNull())
        {
            return parseState(value);
        }
		else
        {
	        return parseState((String)null);
        }
	}

	public void nullSafeSet(PreparedStatement st, Object value, int index) throws HibernateException, SQLException
	{
       if (null == value)
       {
            st.setNull(index, Types.VARCHAR);
        } else
       {
            st.setString(index, parseState( (ClientDocumentType)value ) );
        }
	}

	public Object deepCopy(Object value) throws HibernateException
	{
		return value;
	}

	public boolean isMutable()
	{
		return false;
	}

	public Serializable disassemble(Object value) throws HibernateException
	{
		return (Serializable)value;
	}

	public Object assemble(Serializable cached, Object owner) throws HibernateException
	{
		return cached;
	}

	public Object replace(Object original, Object target, Object owner) throws HibernateException
	{
		return original;
	}
}
