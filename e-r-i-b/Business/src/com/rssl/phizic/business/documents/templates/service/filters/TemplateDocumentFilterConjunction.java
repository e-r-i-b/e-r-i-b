package com.rssl.phizic.business.documents.templates.service.filters;

import com.rssl.phizic.business.documents.templates.TemplateDocument;
import org.apache.commons.collections.Predicate;

import java.util.Arrays;
import java.util.List;

/**
 * @author khudyakov
 * @ created 20.05.14
 * @ $Author$
 * @ $Revision$
 */
public class TemplateDocumentFilterConjunction implements Predicate
{
	private List<TemplateDocumentFilter> filters;


	public TemplateDocumentFilterConjunction(TemplateDocumentFilter ... filters)
	{
		this.filters = Arrays.asList(filters);
	}

	public boolean evaluate(Object object)
	{
		for (TemplateDocumentFilter filter : filters)
		{
			if (!filter.accept((TemplateDocument) object))
			{
				return false;
			}
		}

		return true;
	}
}
