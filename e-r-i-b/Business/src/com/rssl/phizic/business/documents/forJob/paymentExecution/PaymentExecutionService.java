package com.rssl.phizic.business.documents.forJob.paymentExecution;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;

import java.util.Calendar;

/**
 * Сервис для работы с записями обработанных джобами документов
 * @author gladishev
 * @ created 20.02.2013
 * @ $Author$
 * @ $Revision$
 */
public class PaymentExecutionService
{
	private static SimpleService service = new SimpleService();
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final String QUERY_PREFIX = "com.rssl.phizic.business.documents.forJob.paymentExecution.";

	/**
	 * Добавить запись
	 * @param documentId - id документа
	 * @param jobName - имя джоба
	 */
	public void add(Long documentId, String jobName) throws BusinessException
	{
		add(documentId, jobName, null);
	}

	/**
	 * Добавить запись
	 * @param documentId - id документа
	 * @param jobName - имя джоба
	 * @param nextProcessDate - время следующей проверки документа
	 */
	public void add(final Long documentId, final String jobName, final Calendar nextProcessDate) throws BusinessException

	{
		try
		{
			final PaymentExecutionRecord paymentExecutionRecord = getPaymentExecutionRecord(documentId, jobName);
			if (paymentExecutionRecord == null)
				service.add(new PaymentExecutionRecord(documentId, jobName, nextProcessDate));
			else
			{
				final Calendar date;
				if (nextProcessDate != null && !nextProcessDate.before(paymentExecutionRecord.getNextProcessDate()))
					date = nextProcessDate;
				else
					date = paymentExecutionRecord.getNextProcessDate();

				HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			    {
					public Void run(Session session) throws Exception
				    {
				        Query query = session.getNamedQuery(QUERY_PREFIX + (date != null ? "incCounterAndNextProcessDate" : "incCounter"));
					    query.setParameter("documentId", documentId);
					    query.setParameter("jobName", jobName);
					    if (date != null)
					        query.setParameter("nextProcessDate", date);
					    query.executeUpdate();
					    return null;
			        }
		        });
			}
		}
		catch (Exception e)
		{
			log.error("Проблема с добавлением идентификатора обрабатываемого документа",e);
			throw new BusinessException(e);
		}
	}

	/**
	 * Удаление документа, который уже обрабатывали за текущий запуск джоба
	 * @param documentId id документа
	 * @param jobName имя джоба
	 */
	public void delete(final Long documentId, final String jobName)
	{
		try
	    {
		   HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
		   {
			   public Void run(Session session) throws Exception
			   {
				   Query query = session.getNamedQuery(QUERY_PREFIX + "deleteByDocumentIdAndJobName");
				   query.setParameter("documentId", documentId);
				   query.setParameter("jobName", jobName);
				   query.executeUpdate();
				   return null;
			   }
		   });
	    }
	    catch(Exception e)
	    {
		    log.error("Проблема с очисткой обрабатываемого документа с documentId = " + documentId + "jobName = " + jobName, e);
	    }
	}

	/**
	 * Возвращает счетчик элемента с таким идентификатором документа и названием джоба
	 * @param documentId - идентификатор документа
	 * @param jobName - название джоба
	 * @return счетчик элемента
	 */
	public long getCount(final Long documentId, final String jobName) throws BusinessException
	{
		PaymentExecutionRecord paymentExecutionRecord = getPaymentExecutionRecord(documentId, jobName);
		if (paymentExecutionRecord != null)
			return paymentExecutionRecord.getCounter();

		return 0L;
	}


	private PaymentExecutionRecord getPaymentExecutionRecord(final Long documentId, final String jobName) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<PaymentExecutionRecord>()
			{
				public PaymentExecutionRecord run(Session session) throws Exception
				{
					Criteria criteria = session.createCriteria(PaymentExecutionRecord.class);
					criteria.add(Expression.eq("jobName", jobName))
							.add(Expression.eq("documentId", documentId));

					return (PaymentExecutionRecord)criteria.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
