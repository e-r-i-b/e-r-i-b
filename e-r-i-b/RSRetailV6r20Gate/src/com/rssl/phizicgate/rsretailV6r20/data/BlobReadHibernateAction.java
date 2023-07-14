package com.rssl.phizicgate.rsretailV6r20.data;

import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.gate.exceptions.GateException;
import org.hibernate.Session;

import java.sql.Connection;
import java.sql.CallableStatement;

/**
 * @author Omeliyanchuk
 * @ created 21.09.2007
 * @ $Author$
 * @ $Revision$
 */

public class BlobReadHibernateAction<R> implements HibernateAction<R>
{
	private String structName = null;
	private HibernateAction<R> action;

	public BlobReadHibernateAction(String structName, HibernateAction<R> action) throws GateException
	{
		if( (structName != null) && (structName.length()!=0) )
			this.structName = structName;
		else throw new GateException("Не установлена струкутра для чтения");

		if(action != null)
			this.action = action;
		else
			throw new GateException("Не установлен HibernateAction");
	}

	public R run(Session session) throws Exception
	{
		Connection connect = session.connection();
		CallableStatement statement = connect.prepareCall("call rsb_struct.readStruct(?)");
		statement.setString(1, structName);
		statement.execute();
		return action.run(session);
	}
}
