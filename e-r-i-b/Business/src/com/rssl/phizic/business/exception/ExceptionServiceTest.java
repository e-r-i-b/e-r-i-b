package com.rssl.phizic.business.exception;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.test.BusinessTestCaseBase;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import com.rssl.phizic.logging.exceptions.InternalExceptionEntry;
import com.rssl.phizic.logging.exceptions.ExceptionEntry;
import com.rssl.phizic.logging.exceptions.ExternalExceptionEntry;

/**
 * @author mihaylov
 * @ created 15.04.2013
 * @ $Author$
 * @ $Revision$
 */
public class ExceptionServiceTest extends BusinessTestCaseBase
{
	private static final SimpleService simpleService = new SimpleService();	

	/**
	 * Тест
	 * @throws Exception
	 */
	public void testGetMessageAndUpdate() throws Exception
	{
		testGetMessageAndUpdate(new ExceptionEntryUpdateService(), getExternalExceptionEntry());
	}

	private void testGetMessageAndUpdate(final ExceptionEntryUpdateService service, final ExternalExceptionEntry exceptionEntryTemplate) throws Exception
	{
		final ExceptionEntry exceptionEntry = geExceptionEntry();
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					session.save(exceptionEntry);
					service.getMessageAndUpdate(exceptionEntryTemplate, ExceptionEntryApplication.PhizIC, "77");
					throw new BusinessException("Откатываем транзакцию");
				}
			});
		}
		catch (BusinessException ignore)
		{}

		assertNull(findByHash(exceptionEntry.getHash(), exceptionEntry.getClass()));
		ExceptionEntry entry = findByHash(exceptionEntryTemplate.getHash(), exceptionEntryTemplate.getClass());
		assertNotNull(entry);
		simpleService.remove(entry);
	}

	private ExceptionEntry findByHash(String hash, Class<? extends ExceptionEntry> entryClass) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(entryClass);
		criteria.add(Expression.eq("hash", hash));
		return simpleService.findSingle(criteria);
	}

	private ExternalExceptionEntry getExternalExceptionEntry()
	{
		return new ExternalExceptionEntry("testCode","testDetail", "testOperation", "phiz-gate-cod");
	}

	private ExceptionEntry geExceptionEntry()
	{
		InternalExceptionEntry exceptionEntryTemplate = new InternalExceptionEntry();
		exceptionEntryTemplate.setHash("testExternalExceptionHash");
		exceptionEntryTemplate.setDetail("testExternalExceptionDetail");
		exceptionEntryTemplate.setOperation("testExternalExceptionOperation");
		exceptionEntryTemplate.setApplication(Application.JUnitTest);
		return exceptionEntryTemplate;
	}
}
