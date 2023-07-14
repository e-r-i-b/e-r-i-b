package com.rssl.phizic.business.documents.templates.service.filters.API;

import com.rssl.phizic.business.documents.templates.APITemplateType;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.TemplateHelper;
import com.rssl.phizic.business.documents.templates.service.filters.TemplateDocumentFilter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Фильтр шаблонов документов по типу шаблона в АПИ
 *
 * @author khudyakov
 * @ created 10.07.14
 * @ $Author$
 * @ $Revision$
 */
public class APITemplateTypeTemplateFilter implements TemplateDocumentFilter
{
	private List<APITemplateType> APITemplateTypes = new ArrayList<APITemplateType>();


	public APITemplateTypeTemplateFilter(String[] types)
	{
		if (ArrayUtils.isNotEmpty(types))
		{
			for (String type : types)
			{
				APITemplateTypes.add(APITemplateType.valueOf(type));
			}
		}
	}

	public boolean accept(TemplateDocument template)
	{
		if (CollectionUtils.isEmpty(APITemplateTypes))
		{
			return true;
		}

		return APITemplateTypes.contains(TemplateHelper.getAPITemplateType(template));
	}
}
