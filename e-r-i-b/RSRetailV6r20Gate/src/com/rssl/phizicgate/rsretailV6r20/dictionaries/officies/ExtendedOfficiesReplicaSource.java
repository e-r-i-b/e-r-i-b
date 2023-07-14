package com.rssl.phizicgate.rsretailV6r20.dictionaries.officies;

import com.rssl.phizicgate.rsretailV6r20.dictionaries.NamedQueryReplicaSource;
import com.rssl.phizicgate.rsretailV6r20.data.RSRetailV6r20Executor;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.GateFactory;

import java.util.Iterator;
import java.util.List;
import javax.naming.NameNotFoundException;

import org.hibernate.Query;

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
		super("com.rssl.phizic.gate.impl.dictionaries.officies.ExtendedOfficeGateImpl.rsRetailV6r20.GetOfficies");
	}

	public void initialize(GateFactory factory) throws GateException
	{
	}

	public Iterator iterator()
    {
        if(session != null)
            throw new RuntimeException("Обнаружена незакрытая сессия, вызовите метод close()!");

	    try
	    {
			//noinspection HibernateResourceOpenedButNotSafelyClosed
		    session = RSRetailV6r20Executor.getSessionFactory().openSession();
	    }
	    catch (NameNotFoundException e)
	    {
		    throw new RuntimeException(e);
	    }

	    Query query = session.getNamedQuery(namedQuery);
        List list = query.list();

        return list.iterator();
    }
}
