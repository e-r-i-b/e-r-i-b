package com.rssl.phizic.business.documents.templates.service.filters;

import com.rssl.phizic.business.documents.templates.TemplateDocument;

/**
 * @author khudyakov
 * @ created 01.07.14
 * @ $Author$
 * @ $Revision$
 */
public abstract class TemplateIntervalFilterBase<V extends Comparable> implements TemplateDocumentFilter
{
	protected abstract V getFromValue();
	protected abstract V getToValue();
	protected abstract V getValue(TemplateDocument template);


	public boolean accept(TemplateDocument template)
	{
		V value = getValue(template);
		if (isEmpty(value))
		{
			return true;
		}

		V fromValue = getFromValue();
		V toValue = getToValue();

		//если  фильтре не задан диапазон значений
		if (isEmpty(fromValue) && isEmpty(toValue))
		{
			return true;
		}

		if (isEmpty(fromValue))
		{
			//noinspection unchecked
			return value.compareTo(toValue) < 0;
		}

		if (isEmpty(toValue))
		{
			//noinspection unchecked
			return value.compareTo(fromValue) > 0;
		}

		//noinspection unchecked
		return value.compareTo(fromValue) > 0 && value.compareTo(toValue) < 0;
	}

	protected boolean isEmpty(V value)
	{
		return value == null;
	}
}
