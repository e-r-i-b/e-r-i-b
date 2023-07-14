package com.rssl.phizic.business.dictionaries.synchronization.processors.template.offer;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.synchronization.processors.ProcessorBase;
import com.rssl.phizic.business.template.offer.CreditOfferTemplate;

/**
 * @author Balovtsev
 * @since 17.06.2015.
 */
public class CreditOfferTemplateProcessor extends ProcessorBase<CreditOfferTemplate>
{
	@Override
	protected Class<CreditOfferTemplate> getEntityClass()
	{
		return CreditOfferTemplate.class;
	}

	@Override
	protected CreditOfferTemplate getNewEntity()
	{
		return new CreditOfferTemplate();
	}

	@Override
	protected void update(CreditOfferTemplate source, CreditOfferTemplate destination) throws BusinessException
	{
		destination.setTo(source.getTo());
		destination.setUuid(source.getUuid());
		destination.setFrom(source.getFrom());
		destination.setText(source.getText());
		destination.setType(source.getType());
	}
}
