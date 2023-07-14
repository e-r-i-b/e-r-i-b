package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.test.BusinessTestCaseBase;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Query;
import org.hibernate.Session;

import javax.xml.transform.Source;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Evgrafov
 * @ created 30.01.2006
 * @ $Author: Evgrafov $
 * @ $Revision: 2789 $
 */
public class HqlEntityListSourceTest extends BusinessTestCaseBase
{
	protected void setUp() throws Exception
	{
		super.setUp();
		initializeRsV51Gate();
	}

	public void testHqlEntityListSource() throws Exception
	{
		ListFormImpl testListForm = PaymentListFormParserTest.getTestListForm();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("loginId", getTestClientFromRetail().getLogin().getId());
		params.put("state", null);
		params.put("minAmount", null);
		params.put("maxAmount", null);
		params.put("fromDate", null);
		params.put("toDate", null);
		params.put("currencyCode", null);
		params.put("payerAccount", null);
		params.put("receiverAccount", null);
		params.put("anotherOneFooParameter", null);

		Source list = testListForm.getListSource().getList(params, new String[]{});
		assertNotNull(list);

		Source filter = testListForm.getListSource().getFilter(params);
		assertNotNull(filter);

	}

	public void testHqlEntityListSource2() throws Exception
	{
		final String stringQuery = "select payment from Payment as payment where payment.id >=  :amount";

		try
		{
		    HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
		    {
		        public Void run(Session session) throws Exception
		        {

			        Query query = session.createQuery(stringQuery);
			        query.setParameter("amount", 0L).list();
			        return null;
		        }
		    });
		}
		catch (Exception e)
		{
		   throw new BusinessException(e);
		}
	}
}
