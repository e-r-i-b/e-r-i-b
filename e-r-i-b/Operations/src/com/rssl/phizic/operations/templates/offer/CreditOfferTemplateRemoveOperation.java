package com.rssl.phizic.operations.templates.offer;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockModeDictionaryHelper;
import com.rssl.phizic.business.operations.restrictions.Restriction;
import com.rssl.phizic.business.template.offer.CreditOfferTemplate;
import com.rssl.phizic.operations.RemoveEntityOperation;

/**
 * Операция удаления кредитной оферты
 *
 * @author Balovtsev
 * @since 04.06.2015.
 */
public class CreditOfferTemplateRemoveOperation<R extends Restriction> extends AbstractCreditOfferTemplateOperation<R> implements RemoveEntityOperation<CreditOfferTemplate, R>
{
	private CreditOfferTemplate template;

	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		template = TEMPLATE_SERVICE.find(id);

		if (template == null)
		{
			throw new ResourceNotFoundBusinessException(String.format("Шаблон оферты с %d не найден", id), CreditOfferTemplate.class);
		}
	}

	public void remove() throws BusinessException, BusinessLogicException
	{
		TEMPLATE_SERVICE.remove(template, getInstanceName());
		MultiBlockModeDictionaryHelper.updateDictionary(getEntity().getClass());
	}

	public CreditOfferTemplate getEntity()
	{
		return template;
	}
}
