package com.rssl.phizic.dataaccess.hibernate;

import org.hibernate.type.StringType;
import org.hibernate.type.Type;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Evgrafov
 * @ created 10.02.2006
 * @ $Author: Evgrafov $
 * @ $Revision: 3841 $
 * @noinspection ClassWithTooManyMethods
 */
public class LikeStringType extends StringType
{
	public static final Type INSTANCE = new LikeStringType();

	public void set(PreparedStatement st, Object value, int index) throws SQLException
	{
		if(value == null)
			st.setString(index, null);
		else
			st.setString(index, "%" + value + "%");
	}

	public String getName()
	{
		return "like-string";
	}
}