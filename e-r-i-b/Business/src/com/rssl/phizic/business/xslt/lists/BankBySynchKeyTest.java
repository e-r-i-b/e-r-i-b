package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.test.BusinessTestCaseBase;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.business.BusinessException;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Query;

/**
 * @author Omeliyanchuk
 * @ created 02.10.2007
 * @ $Author$
 * @ $Revision$
 */

public class BankBySynchKeyTest extends BusinessTestCaseBase
{
	private Session session;
	public void testGetBankBySynchKey() throws Exception
	{
		List<DictionaryRecord> banks = null;
		List<DictionaryRecord> banks1 = null;
		try
	    {
	        banks = HibernateExecutor.getInstance().execute(new HibernateAction<List<DictionaryRecord>>()
	        {
	            public List<DictionaryRecord> run(Session session) throws Exception
	            {
		            //Query query = session.getNamedQuery("com.rssl.phizic.business.getBankBySynchKey");
		            Query query = session.createQuery("select bank from ForeignBank bank order by bank.synchKey asc");
		            //noinspection unchecked
		            return query.list();
	            }
	        });
	    }
	    catch (Exception e)
	    {
	       throw new BusinessException(e);
	    }
		banks.size();

		session = HibernateExecutor.getSessionFactory().openSession();
		Query query = getQuery();
		banks1 = query.list();
		banks1.size();
	}

	private Query getQuery()
	{
		return session.createQuery("select bank from ForeignBank bank order by bank.synchKey asc");
	}
}
