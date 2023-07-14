package com.rssl.phizic.business.dictionaries.synchronization.processors.providers;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.payment.services.PaymentService;
import com.rssl.phizic.business.dictionaries.providers.BillingProviderService;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import com.rssl.phizic.business.dictionaries.providers.aggr.AggregationService;
import com.rssl.phizic.business.dictionaries.replication.providers.ServiceProviderForReplicationWrapper;
import com.rssl.phizic.business.dictionaries.synchronization.processors.ProcessorBase;
import com.rssl.phizic.business.dictionaries.synchronization.processors.serviceProvider.PaymentServiceProcessor;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.query.ExecutorQuery;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.HashSet;

/**
 * @author lukina
 * @ created 04.03.2014
 * @ $Author$
 * @ $Revision$
 */
public class ServiceProviderForReplicationWrapperProcessor extends ProcessorBase<ServiceProviderForReplicationWrapper>
{
	private static final String MULTI_BLOCK_RECORD_ID_FIELD_NAME = "uuid";
	private static final String QUERY_PREFIX = ServiceProviderBase.class.getName() + ".";
	private static final BillingServiceProviderProcessor BILLING_SERVICE_PROVIDER_PROCESSOR = new BillingServiceProviderProcessor();

	@Override
	protected Class<ServiceProviderForReplicationWrapper> getEntityClass()
	{
		return ServiceProviderForReplicationWrapper.class;
	}

	@Override
	protected ServiceProviderForReplicationWrapper getNewEntity()
	{
		return new ServiceProviderForReplicationWrapper(BILLING_SERVICE_PROVIDER_PROCESSOR.getNewEntity(), new HashSet<PaymentService>());
	}

	@Override
	protected ServiceProviderForReplicationWrapper getEntity(String uuid) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(getEntityClass()).add(Expression.eq(MULTI_BLOCK_RECORD_ID_FIELD_NAME, uuid));
		return simpleService.findSingle(criteria);
	}

	@Override
	protected void update(ServiceProviderForReplicationWrapper source, ServiceProviderForReplicationWrapper destination) throws BusinessException
	{
		BILLING_SERVICE_PROVIDER_PROCESSOR.update(source.getProvider(), destination.getProvider());
		//TODO Костыль. Необходим для ухода от левых обновлений через ServiceProviderForReplicationWrapper при флаше сесии.
		//TODO Убрать вместе с данным процессором в рамках запроса CHG068539.
		BillingServiceProvider provider = destination.getProvider();
		simpleService.addOrUpdate(provider);

		removeProviderServices(provider);
		for (PaymentService service : getLocalVersionByGlobal(source.getPaymentServices(), PaymentServiceProcessor.MULTI_BLOCK_RECORD_ID_FIELD_NAME))
			simpleService.addOrUpdate(new BillingProviderService(provider, service));
	}

	@Override
	protected void doSave(ServiceProviderForReplicationWrapper localEntity) throws BusinessException, BusinessLogicException
	{
		//Ничего не делаем. Всё уже сохранили в методе update();
		AggregationService.markNeedAggregation();
	}

	@Override
	protected void doRemove(ServiceProviderForReplicationWrapper localEntity) throws BusinessException, BusinessLogicException
	{
		BillingServiceProvider provider = localEntity.getProvider();
		//разобраться с удалением привязанных услуг
		removeProviderServices(provider);
		simpleService.remove(provider);
		AggregationService.markNeedAggregation();
	}

	private void removeProviderServices(BillingServiceProvider provider) throws BusinessException
	{
		try
		{
			new ExecutorQuery(HibernateExecutor.getInstance(), QUERY_PREFIX + "removeProviderServicesSQL")
					.setParameter("providerId", provider.getId())
					.executeUpdate();
		}
		catch (DataAccessException e)
		{
			throw new BusinessException(e);
		}
	}
}
