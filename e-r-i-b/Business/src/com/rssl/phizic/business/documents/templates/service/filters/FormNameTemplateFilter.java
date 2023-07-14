package com.rssl.phizic.business.documents.templates.service.filters;

import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Фильтр шаблонов документов по названию формы
 *
 * @author khudyakov
 * @ created 20.05.14
 * @ $Author$
 * @ $Revision$
 */
public class FormNameTemplateFilter implements TemplateDocumentFilter
{
	public static final String FORM_TYPE_FILTER_ATTRIBUTE_NAME = "formType";


	private List<FormType> formTypes = new ArrayList<FormType>();

	public FormNameTemplateFilter(FormType formType)
	{
		if (formType != null)
		{
			formTypes.add(formType);
		}
	}

	public FormNameTemplateFilter(Map<String, Object> params)
	{
		String s = (String) params.get(FORM_TYPE_FILTER_ATTRIBUTE_NAME);
		if (StringHelper.isNotEmpty(s))
		{
			for(String formType : s.split(","))
			{
				formTypes.add(FormType.valueOf(formType));
			}
		}
	}

	public boolean accept(TemplateDocument template)
	{
		if (CollectionUtils.isEmpty(formTypes))
		{
			return true;
		}

		return formTypes.contains(template.getFormType());
	}
}
