package com.rssl.phizic.business.dictionaries.synchronization.processors.serviceProvider;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.payment.services.PaymentService;
import com.rssl.phizic.business.dictionaries.synchronization.processors.ProcessorBase;
import com.rssl.phizic.business.dictionaries.synchronization.processors.aggr.PostAggregationProcessorBase;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.ArrayList;

/**
 * @author lepihina
 * @ created 27.01.14
 * $Author$
 * $Revision$
 *
 * Процессор записей услуг для поставщиков
 */
public class PaymentServiceProcessor extends PostAggregationProcessorBase<PaymentService>
{
	public static final String MULTI_BLOCK_RECORD_ID_FIELD_NAME = "synchKey";

	@Override
	protected Class<PaymentService> getEntityClass()
	{
		return PaymentService.class;
	}

	@Override
	protected PaymentService getNewEntity()
	{
		PaymentService paymentService = new PaymentService();
		paymentService.setParentServices(new ArrayList<PaymentService>());
		return new PaymentService();
	}

	@Override
	protected PaymentService getEntity(String uuid) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(getEntityClass()).add(Expression.eq(MULTI_BLOCK_RECORD_ID_FIELD_NAME, uuid));
		return simpleService.findSingle(criteria);
	}

	@Override
	protected void update(PaymentService source, PaymentService destination) throws BusinessException
	{
		destination.setSynchKey(source.getSynchKey());
		destination.setName(source.getName());
		destination.setPopular(source.isPopular());
		destination.setImageId(mergeImage(source.getImageId(), destination.getImageId()));
		destination.setDescription(source.getDescription());
		destination.setSystem(source.isSystem());
		destination.setPriority(source.getPriority());
		destination.setVisibleInSystem(source.isVisibleInSystem());
		destination.setDefaultImage(source.getDefaultImage());
		destination.setCategory(source.getCategory());
		destination.setShowInSystem(source.getShowInSystem());
		destination.setShowInMApi(source.getShowInMApi());
		destination.setShowInAtmApi(source.getShowInAtmApi());
		destination.getParentServices().clear();
		destination.getParentServices().addAll(getLocalVersionByGlobal(source.getParentServices(), MULTI_BLOCK_RECORD_ID_FIELD_NAME));
	}

	@Override
	protected void doRemove(PaymentService localEntity) throws BusinessException, BusinessLogicException
	{
		removeImage(localEntity.getImageId());
		super.doRemove(localEntity);
	}
}
