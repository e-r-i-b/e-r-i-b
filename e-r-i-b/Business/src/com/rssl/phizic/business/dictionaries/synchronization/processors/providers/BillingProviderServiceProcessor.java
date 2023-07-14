package com.rssl.phizic.business.dictionaries.synchronization.processors.providers;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.payment.services.PaymentService;
import com.rssl.phizic.business.dictionaries.providers.BillingProviderService;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.business.dictionaries.synchronization.processors.aggr.PostAggregationProcessorBase;
import com.rssl.phizic.business.dictionaries.synchronization.processors.serviceProvider.PaymentServiceProcessor;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;

import java.util.regex.Pattern;

/**
 *  Процессор связки биллинговых поставщиков с услугами
 * @author lukina
 * @ created 27.02.2014
 * @ $Author$
 * @ $Revision$
 */

public class BillingProviderServiceProcessor extends PostAggregationProcessorBase<BillingProviderService>
{
	private static final Pattern SEPARATOR_PATTERN = Pattern.compile("\\^");
	private static final String SYNCH_KEY_FIELD_NAME = "synchKey";
	private static final String UUID_KEY_FIELD_NAME = "uuid";
	private static final String ID_FIELD_NAME = "id";

	@Override
	protected Class<BillingProviderService> getEntityClass()
	{
		return BillingProviderService.class;
	}

	@Override
	protected BillingProviderService getNewEntity()
	{
		return new BillingProviderService();
	}

	private Long getId(Class entityClass, String uuidFieldName,  String uuid) throws BusinessException
	{
		DetachedCriteria providerCriteria = DetachedCriteria.forClass(entityClass)
				.add(Expression.eq(uuidFieldName, uuid))
				.setProjection(Projections.property(ID_FIELD_NAME));
		return simpleService.findSingle(providerCriteria);
	}

	@Override
	protected BillingProviderService getEntity(String uuid) throws BusinessException
	{
		String[] keys = SEPARATOR_PATTERN.split(uuid);
		Long providerId = getId(BillingServiceProvider.class, UUID_KEY_FIELD_NAME, keys[0]);
		Long serviceId = getId(PaymentService.class, SYNCH_KEY_FIELD_NAME, keys[1]);
		DetachedCriteria referenceCriteria = DetachedCriteria.forClass(getEntityClass())
				.add(Expression.eq("serviceProvider.id", providerId))
				.add(Expression.eq("paymentService.id", serviceId));
		return simpleService.findSingle(referenceCriteria);
	}

	@Override
	protected void update(BillingProviderService source, BillingProviderService destination) throws BusinessException
	{
		destination.setPaymentService(getLocalVersionByGlobal(source.getPaymentService(), PaymentServiceProcessor.MULTI_BLOCK_RECORD_ID_FIELD_NAME));
		destination.setServiceProvider(getLocalVersionByGlobal(source.getServiceProvider(), ProviderProcessorBase.MULTI_BLOCK_RECORD_ID_FIELD_NAME));
	}
}
