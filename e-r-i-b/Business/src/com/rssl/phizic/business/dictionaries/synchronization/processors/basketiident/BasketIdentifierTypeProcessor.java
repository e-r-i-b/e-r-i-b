package com.rssl.phizic.business.dictionaries.synchronization.processors.basketiident;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.basketident.AttributeForBasketIdentType;
import com.rssl.phizic.business.dictionaries.basketident.BasketIndetifierType;
import com.rssl.phizic.business.dictionaries.synchronization.processors.ProcessorBase;

import java.util.HashMap;

/**
 * @author bogdanov
 * @ created 13.11.14
 * @ $Author$
 * @ $Revision$
 */

public class BasketIdentifierTypeProcessor extends ProcessorBase<BasketIndetifierType>
{
	@Override
	protected Class<BasketIndetifierType> getEntityClass()
	{
		return BasketIndetifierType.class;
	}

	@Override
	protected BasketIndetifierType getNewEntity()
	{
		return new BasketIndetifierType();
	}

	@Override
	protected void update(BasketIndetifierType source, BasketIndetifierType destination) throws BusinessException
	{
		destination.setUuid(source.getUuid());
		destination.setName(source.getName());
		destination.setSystemId(source.getSystemId());
		destination.setAttributes(new HashMap<String, AttributeForBasketIdentType>());

		for (AttributeForBasketIdentType attr : source.getAttributes().values())
		{
			AttributeForBasketIdentType t = new AttributeForBasketIdentType();
			t.setDataType(attr.getDataType());
			t.setSystemId(attr.getSystemId());
			t.setName(attr.getName());
			t.setRegexp(attr.getRegexp());
			t.setMandatory(attr.isMandatory());
		}
	}
}
