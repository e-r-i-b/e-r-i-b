package com.rssl.phizicgate.rsretailV6r4.test;

import com.rssl.phizicgate.rsretailV6r4.junit.RSRetailV6r4GateTestCaseBase;
import com.rssl.phizicgate.rsretailV6r4.data.RSRetailV6r4Executor;
import com.rssl.phizicgate.rsretailV6r4.data.BlobReadHibernateAction;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import org.hibernate.Session;
import org.hibernate.Query;

import java.sql.Connection;
import java.util.List;

/**
 * @author Omeliyanchuk
 * @ created 20.09.2007
 * @ $Author$
 * @ $Revision$
 */

public class BlobTest extends RSRetailV6r4GateTestCaseBase
{
	public void testGetBlobValue() throws Exception
	{
		TestV6 resultTest= null;
		try
		{
			resultTest = RSRetailV6r4Executor.getInstance().execute(new BlobReadHibernateAction<TestV6>("drt_paym_1",
					new HibernateAction<TestV6>()
					{
						public TestV6 run(Session session) throws Exception
						{
							Query namedQuery = session.getNamedQuery("com.rssl.phizicgate.rsretailV6r4.test.getBlobTest");
							namedQuery.setParameter("appKind", "0100261006110738000010008200");

							TestV6 test = (TestV6) namedQuery.uniqueResult();

							return test;
						}
					}
			));
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}

		assertNotNull("В базе должен быть объект с таким appKey",resultTest);
		assertNotNull("У облъекта должна быть не пустой информация из blob",resultTest.getBlobInfo());

		List<TestV6> resultList = null;
		try
		{
			resultList = RSRetailV6r4Executor.getInstance().execute(new BlobReadHibernateAction<List<TestV6>>("drt_paym_1",
					new HibernateAction<List<TestV6>>()
					{
						public List<TestV6> run(Session session) throws Exception
						{
							Query namedQuery = session.getNamedQuery("com.rssl.phizicgate.rsretailV6r4.test.getBlobTest.list");

							List<TestV6> test = (List<TestV6>) namedQuery.list();

							return test;
						}
					}
			));
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}

		if( resultList != null )
			resultList.size();
	}
}
