package com.rssl.phizic.business.documents.templates.service.filters;

import com.rssl.phizic.business.documents.templates.TemplateDocument;

import java.util.Arrays;
import java.util.Collection;

/**
 * Фильтр шаблонов документов по идентификатору шаблона
 *
 * @author khudyakov
 * @ created 21.05.14
 * @ $Author$
 * @ $Revision$
 */
public class IdsTemplatesFilter implements TemplateDocumentFilter
{
	private Collection<Long> ids;

	public IdsTemplatesFilter(Long... ids)
	{
		this(Arrays.asList(ids));
	}

	public IdsTemplatesFilter(Collection<Long> ids)
	{
		this.ids = ids;
	}

	public boolean accept(TemplateDocument template)
	{
		return ids.contains(template.getId());
	}
}
