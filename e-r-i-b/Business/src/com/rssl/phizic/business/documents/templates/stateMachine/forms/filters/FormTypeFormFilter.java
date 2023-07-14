package com.rssl.phizic.business.documents.templates.stateMachine.forms.filters;

import com.rssl.common.forms.doc.FormFilterBase;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.utils.StringHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Фильтр форм по типу формы шаблона документа
 *
 * @author khudyakov
 * @ created 30.07.14
 * @ $Author$
 * @ $Revision$
 */
public class FormTypeFormFilter extends ListValueFormFilter<FormType, TemplateDocument>
{
	private static final String FORM_TYPES_PARAMETER_NAME           = "formTypes";


	@Override
	protected FormType getValue(TemplateDocument template)
	{
		return template.getFormType();
	}

	@Override
	protected List<FormType> getValues()
	{
		String values = getParameter(FORM_TYPES_PARAMETER_NAME);
		if (StringHelper.isEmpty(values))
		{
			return null;
		}

		List<FormType> result = new ArrayList<FormType>();
		for (String value : values.trim().split(DELIMITER))
		{
			result.add(FormType.valueOf(value));
		}
		return result;
	}
}
