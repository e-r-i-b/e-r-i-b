package com.rssl.phizic.business.documents.forJob;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author mihaylov
 * @ created 04.09.2011
 * @ $Author$
 * @ $Revision$
 */

public class ProcessedDocumentIdService
{
	private static SimpleService service = new SimpleService();
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final String QUERY_PREFIX = "com.rssl.phizic.business.documents.forJob.";

	public void add(Long documentId, String jobName) throws BusinessException
	{
		try
		{
			service.add(new ProcessedDocumentId(documentId, jobName));
		}
		catch (BusinessException e)
		{
			log.error("�������� � ����������� �������������� ��������������� ���������",e);
			throw e;
		}
	}

	public void deleteAllForJob(final String jobName)
	{
		try
	    {
		   HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
		   {
			   public Void run(Session session) throws Exception
			   {
				   Query query = session.getNamedQuery(QUERY_PREFIX + "deleteAll");
				   query.setParameter("jobName",jobName);
				   query.executeUpdate();
				   return null;
			   }
		   });
	    }
	    catch(Exception e)
	    {
		    log.error("�������� � �������� ��������������� �������������� ���������� ��� ����� " + jobName,e);
	    }
	}

	/**
	 * �������� ���������, ������� ��� ������������ �� ������� ������ �����
	 * @param documentId id ���������
	 * @param jobName ��� �����
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
		    log.error("�������� � �������� ��������������� ��������� � documentId = " + documentId + "jobName = " + jobName, e);
	    }
	}

}
