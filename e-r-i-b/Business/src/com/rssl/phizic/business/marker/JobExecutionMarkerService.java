package com.rssl.phizic.business.marker;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Calendar;

/**
 * @author mihaylov
 * @ created 25.02.2013
 * @ $Author$
 * @ $Revision$
 */
public class JobExecutionMarkerService
{
	private static final int DELAY = 30;
	private static final String QUERY_PREFIX = JobExecutionMarkerService.class.getName();
	private static final SimpleService simpleService = new SimpleService();

	/**
	 * Создать маркер для имени джоба
	 * @param jobName - название джоба для которого необходимо создать маркер
	 * @throws BusinessException
	 */
	public void createForJob(String jobName) throws BusinessException
	{
		Calendar actualDate = Calendar.getInstance();
		actualDate.add(Calendar.SECOND,DELAY);
		JobExecutionMarker marker = new JobExecutionMarker(jobName,actualDate.getTime());
		addOrUpdate(marker);
	}

	/**
	 * Добавить новый или обновить существующий маркер
	 * @param marker - маркер
	 * @throws BusinessException
	 */
	public void addOrUpdate(JobExecutionMarker marker) throws BusinessException
	{
		simpleService.addOrUpdate(marker);
	}

	/**
	 * Найти метку о необходимости выполнения джоба
	 * @param jobName - имя джоба
	 * @return метка
	 * @throws BusinessException
	 */
	public JobExecutionMarker findForJob(final String jobName) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<JobExecutionMarker>()
			{
				public JobExecutionMarker run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(QUERY_PREFIX + ".findForJob");
					query.setParameter("jobName", jobName);
					query.setParameter("actualDate",Calendar.getInstance().getTime());
					query.setMaxResults(1);
					return (JobExecutionMarker)query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Удалить маркер
	 * @param marker маркер для удаления
	 * @throws BusinessException
	 */
	public void remove(JobExecutionMarker marker) throws BusinessException
	{
		simpleService.remove(marker);
	}


}
