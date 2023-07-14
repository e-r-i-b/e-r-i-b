package com.rssl.phizic.business.payments.forms.meta.filters;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.HandlerFilterBase;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.utils.StringHelper;

/**
 * Фильтр выполнения хендлера, проверка приложения
 *
 * @author khudyakov
 * @ created 25.10.14
 * @ $Author$
 * @ $Revision$
 */
public class ApplicationDocumentHandlerFilter extends HandlerFilterBase<BusinessDocument>
{
	private static final String APPLICATION_PARAMETER_NAME = "application";

	public boolean isEnabled(BusinessDocument stateObject) throws DocumentException, DocumentLogicException
	{
		String parameter = getParameter(APPLICATION_PARAMETER_NAME);
		if (StringHelper.isEmpty(parameter))
		{
			return true;
		}

		Application application = ApplicationConfig.getIt().getApplicationInfo().getApplication();
		for (String s : parameter.trim().split("( )*( |,|;)( )*"))
		{
			if (s.equals(application.name()))
			{
				return true;
			}
		}
		return false;
	}
}
