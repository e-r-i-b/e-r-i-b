package com.rssl.phizic.business.dictionaries.providers;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.dictionaries.synchronization.log.ChangeType;
import com.rssl.phizic.business.dictionaries.synchronization.log.DictionaryRecordChangeInfoService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.List;
import java.util.Set;

/**
 * @author lukina
 * @ created 27.02.2014
 * @ $Author$
 * @ $Revision$
 */
public class BillingProviderServiceService
{
	private static final SimpleService simpleService = new SimpleService();
	private static final DictionaryRecordChangeInfoService dictionaryRecordChangeInfoService = new DictionaryRecordChangeInfoService();

	public List<BillingProviderService> findByProviderId(Long id, String instanceName) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(BillingProviderService.class).add(Expression.eq("serviceProvider.id", id));
		return simpleService.find(criteria, instanceName);
	}


	public List<BillingProviderService> findByServiceIds(List<Long> ids, Long providerId, String instanceName) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(BillingProviderService.class).add(Expression.eq("serviceProvider.id", providerId)).add(Expression.in("paymentService.id", ids));
		return simpleService.find(criteria, instanceName);
	}


	/**
	 * Сохранение связи между поставщиком и услугой
	 * @param billingProviderService
	 * @return
	 * @exception com.rssl.phizic.business.BusinessException
	 * */
	public void addOrUpdate(final BillingProviderService billingProviderService, final String instanceName) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					simpleService.addOrUpdate(billingProviderService, instanceName);
					addToLog(billingProviderService, ChangeType.update);
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	private <T> void addToLog(BillingProviderService record, ChangeType changeType) throws BusinessException
	{
		dictionaryRecordChangeInfoService.addChangesToLog(record, changeType);
	}
	/**
	 * Удаление связи между поставщиком и услугой
	 * @exception BusinessException
	 * */
	public void remove(final Set<BillingProviderService> billingProviderServices,final String instanceName) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					for (BillingProviderService billingProviderService : billingProviderServices)
					{
						simpleService.remove(billingProviderService, instanceName);
						addToLog(billingProviderService, ChangeType.delete);
					}
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
