package com.rssl.phizic.business.finances.processed;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.utils.DateHelper;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Calendar;

/**
 * @author mihaylov
 * @ created 10.07.14
 * @ $Author$
 * @ $Revision$
 *
 * —ервис дл€ работы с логинами владельцев за€вок, выполн€емых в текущий момент.
 */
public class ProcessedCardOperationClaimLoginService
{
	private static final String DELETE_OLD_QUERY_NAME = ProcessedCardOperationClaimLogin.class.getName() + ".deleteOld";
	private static final SimpleService simpleService = new SimpleService();


	/**
	 * ƒобавить запись о блокировке логина владельца за€вки.
	 * @param loginId - идентификатор логина владельца за€вки
	 * @return запись о блокировке логина
	 * @throws BusinessException
	 */
	public ProcessedCardOperationClaimLogin add(Long loginId) throws BusinessException
	{
		return simpleService.add(new ProcessedCardOperationClaimLogin(loginId));
	}

	/**
	 * ”далить запись о блокировке владельца за€вки
	 * @param loginId идентификатор логина владельца за€вки
	 * @throws BusinessException
	 */
	public void remove(Long loginId) throws BusinessException
	{
		simpleService.remove(new ProcessedCardOperationClaimLogin(loginId));
	}

	/**
	 * ”далить записи о блокировке логинов старше, чем claimExecutionMaxTime
	 * @param claimExecutionMaxTime - максимальное врем€ выполнени€ за€вки
	 * @throws BusinessException
	 */
	public void removeOld(final int claimExecutionMaxTime) throws BusinessException
	{
		final Calendar processingDate = DateHelper.addSeconds(Calendar.getInstance(), -claimExecutionMaxTime);
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
				{
					public Void run(Session session) throws Exception
					{
						Query query = session.getNamedQuery(DELETE_OLD_QUERY_NAME);
						query.setParameter("processingDate", processingDate);
						query.executeUpdate();
						return null;
					}
				});
		}
		catch (Exception e)
		{
			throw new BusinessException("Ќе удалось удалить старые записи об обработке за€вок",e);
		}
	}

}
