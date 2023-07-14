package com.rssl.phizic.business.documents.templates.service.filters.API;

import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.service.filters.TemplateDocumentFilter;
import com.rssl.phizic.common.types.documents.FormType;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Ôטכüענ ןמ פמנלאל ÀÏÈ
 *
 * @author khudyakov
 * @ created 10.07.14
 * @ $Author$
 * @ $Revision$
 */
public class APIFormTemplateFilter implements TemplateDocumentFilter
{
	private List<String> APIFormNames = new ArrayList<String>();


	public APIFormTemplateFilter(String[] formNames)
	{
		APIFormNames = getAPIForms(formNames);
	}

	public boolean accept(TemplateDocument template)
	{
		if (CollectionUtils.isEmpty(APIFormNames))
		{
			return true;
		}

		return APIFormNames.contains(template.getFormType().name());
	}

	private List<String> getAPIForms(String[] formNames)
	{
		if (ArrayUtils.isEmpty(formNames))
		{
			return Collections.emptyList();
		}

		List<String> result = new ArrayList<String>();
		Map<String, String> formTypes = FormType.getFormTypes();

		for (String formName : formNames)
		{
			for (Map.Entry<String, String> type : formTypes.entrySet())
			{
				if (formName.equals(type.getValue()))
				{
					result.add(type.getKey());
				}
			}
		}
		return result;
	}
}
