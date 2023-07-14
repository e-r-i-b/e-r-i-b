package com.rssl.phizic.business.dictionaries.synchronization.processors.ima;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.synchronization.processors.ProcessorBase;
import com.rssl.phizic.business.ima.IMAProduct;
import com.rssl.phizic.business.ima.IMAProductService;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

/**
 * @author mihaylov
 * @ created 02.03.14
 * @ $Author$
 * @ $Revision$
 *
 * Процессор для синхронизации ОМС продуктов
 */
public class IMAProductProcessor extends ProcessorBase<IMAProduct>
{
	private static final IMAProductService imaProductservice = new IMAProductService();

	@Override
	protected Class<IMAProduct> getEntityClass()
	{
		return IMAProduct.class;
	}

	@Override
	protected IMAProduct getNewEntity()
	{
		return new IMAProduct();
	}

	@Override
	protected IMAProduct getEntity(String uuid) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(getEntityClass()).add(Expression.eq("uuid", uuid));
		return simpleService.findSingle(criteria);
	}

	@Override
	protected void update(IMAProduct source, IMAProduct destination) throws BusinessException
	{
		destination.setUuid(source.getUuid());
		destination.setType(source.getType());
		destination.setSubType(source.getSubType());
		destination.setName(source.getName());
		destination.setCurrency(source.getCurrency());
		destination.setContractTemplate(source.getContractTemplate());
	}

	@Override
	protected void doSave(IMAProduct localEntity) throws BusinessException, BusinessLogicException
	{
		super.doSave(localEntity);
		imaProductservice.deleteIMAProductResources(localEntity.getId());
	}
}
