package com.rssl.phizic.operations.templates.offer.pdp;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockModeDictionaryHelper;
import com.rssl.phizic.business.operations.restrictions.Restriction;
import com.rssl.phizic.business.template.offer.CreditOfferTemplate;
import com.rssl.phizic.business.template.offer.CreditOfferTemplateType;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.templates.offer.AbstractCreditOfferTemplateOperation;
import com.rssl.phizic.utils.DateHelper;

import java.util.Calendar;

/**
 * @author Balovtsev
 * @since 25.07.2015.
 */
public class CreditOfferPdpTemplateEditOperation<R extends Restriction> extends AbstractCreditOfferTemplateOperation<R> implements EditEntityOperation<CreditOfferTemplate, R>
{
	private CreditOfferTemplate template;

	public void init() throws BusinessException
	{
		template = TEMPLATE_SERVICE.findPdp();

		if (template == null)
		{
			template = new CreditOfferTemplate(null, DateHelper.clearTime(Calendar.getInstance()), null, null, null, CreditOfferTemplateType.PDP);
		}
	}

	public void save() throws BusinessException, BusinessLogicException
	{
		TEMPLATE_SERVICE.save(getEntity(), getInstanceName());
		MultiBlockModeDictionaryHelper.updateDictionary(getEntity().getClass());
	}

	public CreditOfferTemplate getEntity() throws BusinessException, BusinessLogicException
	{
		return template;
	}
}
