package com.rssl.phizic.operations.templates.offer;

import com.rssl.phizic.business.operations.restrictions.Restriction;
import com.rssl.phizic.business.template.offer.CreditOfferTemplate;
import com.rssl.phizic.business.template.offer.CreditOfferTemplateType;
import com.rssl.phizic.business.template.offer.Status;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.Predicate;

import java.util.HashMap;
import java.util.Map;

/**
 * Операция получения списка крединых оферт
 *
 * @author Balovtsev
 * @since  03.06.2015.
 */
public class CreditOfferTemplateListOperation <R extends Restriction> extends AbstractCreditOfferTemplateOperation<R> implements ListEntitiesOperation<R>
{
	private final Map<String, Object> filterParams = new HashMap<String, Object>();

	/**
	 * @param parameters параметры фильтрации
	 */
	public void applyFilterParameters(Map<String, Object> parameters)
	{
		filterParams.clear();
		filterParams.putAll(parameters);
	}

	@Override
	protected Predicate getResultFilter()
	{
		return new Predicate()
		{
			private final Long   id     = (Long)   filterParams.get("id");
			private final String status = (String) filterParams.get("status");

			public boolean evaluate(Object object)
			{
				CreditOfferTemplate template = (CreditOfferTemplate) object;

				if (template.getType() != CreditOfferTemplateType.ERIB)
				{
					return false;
				}

				if (id != null && !template.getId().equals(id))
				{
					return false;
				}

				if (StringHelper.isNotEmpty(status) && Status.valueOf(status) != template.getStatus())
				{
					return false;
				}

				return true;
			}
		};
	}

	@Override
	protected String getInstanceName()
	{
		return null;
	}
}
