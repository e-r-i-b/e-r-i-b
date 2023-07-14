package com.rssl.phizicgate.rsretailV6r4.dictionaries.officies;

import com.rssl.phizicgate.rsretailV6r4.dictionaries.NamedQueryReplicaSource;
import com.rssl.phizicgate.rsretailV6r4.data.RSRetailV6r4Executor;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;

import java.util.Iterator;
import java.util.List;
import java.sql.Statement;
import java.sql.SQLException;
import javax.naming.NameNotFoundException;

import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author Egorova
 * @ created 05.11.2008
 * @ $Author$
 * @ $Revision$
 */
public class ExtendedOfficiesReplicaSource extends NamedQueryReplicaSource
{
	public ExtendedOfficiesReplicaSource()
	{
		super("com.rssl.phizgate.common.services.bankroll.ExtendedOfficeGateImpl.getAll");
	}

	public void initialize(GateFactory factory) throws GateException
	{
	}

	public Iterator iterator() throws GateException
    {
	    try
	    {
		    return RSRetailV6r4Executor.getInstance().execute(new HibernateAction<Iterator>()
		    {
			    public Iterator run(Session session)
			    {
				    Query query = session.getNamedQuery(namedQuery);
				    return query.list().iterator();
			    }
		    });
	    }
	    catch(Exception e)
	    {
		    throw new GateException(e);
	    }
    }
}
