package com.rssl.phizic.business.documents.templates.stateMachine.forms.filters;

import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.utils.StringHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Фильтр активности приложения клиета
 *
 * @author khudyakov
 * @ created 30.07.14
 * @ $Author$
 * @ $Revision$
 */
public class ActiveApplicationFormFilter extends ListValueFormFilter<Application, TemplateDocument>
{
	private static final String APPLICATIONS_PARAMETER_NAME             = "applications";


	@Override
	protected Application getValue(TemplateDocument template)
	{
		return ApplicationConfig.getIt().getApplicationInfo().getApplication();
	}

	@Override
	protected List<Application> getValues()
	{
		String values = getParameter(APPLICATIONS_PARAMETER_NAME);
		if (StringHelper.isEmpty(values))
		{
			return null;
		}

		List<Application> result = new ArrayList<Application>();
		for (String value : values.trim().split(DELIMITER))
		{
			result.add(Application.valueOf(value));
		}
		return result;
	}
}
