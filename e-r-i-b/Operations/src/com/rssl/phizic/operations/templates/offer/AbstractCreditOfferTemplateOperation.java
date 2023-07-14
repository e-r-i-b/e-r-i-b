package com.rssl.phizic.operations.templates.offer;

import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockModeDictionaryHelper;
import com.rssl.phizic.business.operations.restrictions.Restriction;
import com.rssl.phizic.business.template.offer.CreditOfferTemplate;
import com.rssl.phizic.business.template.offer.CreditOfferTemplateHandler;
import com.rssl.phizic.business.template.offer.CreditOfferTemplateService;
import com.rssl.phizic.business.template.offer.CreditOfferTemplateSortComparator;
import com.rssl.phizic.operations.CachedOperationBase;
import org.apache.commons.collections.Closure;

import java.util.Comparator;

/**
 * Базовый класс для операций с кредитными офертами
 *
 * @author Balovtsev
 * @since  04.06.2015.
 */
public abstract class AbstractCreditOfferTemplateOperation<R extends Restriction> extends CachedOperationBase<CreditOfferTemplate, R>
{
	protected static final String                     CACHE_NAME        = "credit-offer-template-cache-name";
	protected static final CreditOfferTemplateService TEMPLATE_SERVICE  = new CreditOfferTemplateService(CACHE_NAME);

	@Override
	protected Comparator<CreditOfferTemplate> getSortComparator()
	{
		return new CreditOfferTemplateSortComparator();
	}

	@Override
	protected Closure getResultHandler()
	{
		return new CreditOfferTemplateHandler();
	}

	protected String getCacheName()
	{
		return CACHE_NAME;
	}

	@Override
	protected String getInstanceName()
	{
		return MultiBlockModeDictionaryHelper.getDBInstanceName();
	}
}
