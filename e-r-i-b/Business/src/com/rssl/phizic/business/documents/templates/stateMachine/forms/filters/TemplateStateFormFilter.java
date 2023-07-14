package com.rssl.phizic.business.documents.templates.stateMachine.forms.filters;

import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.common.types.documents.StateCode;
import com.rssl.phizic.utils.StringHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Фильтр по статусам шаблона документа
 *
 *  @author khudyakov
 * @ created 30.07.14
 * @ $Author$
 * @ $Revision$
 */
public class TemplateStateFormFilter extends ListValueFormFilter<StateCode, TemplateDocument>
{
	private static final String STATE_CODE_PARAMETER_NAME =             "states";


	@Override
	protected StateCode getValue(TemplateDocument template)
	{
		return StateCode.valueOf(template.getState().getCode());
	}

	@Override
	protected List<StateCode> getValues()
	{
		String values = getParameter(STATE_CODE_PARAMETER_NAME);
		if (StringHelper.isEmpty(values))
		{
			return null;
		}

		List<StateCode> result = new ArrayList<StateCode>();
		for (String value : values.trim().split(DELIMITER))
		{
			result.add(StateCode.valueOf(value));
		}
		return result;
	}
}
