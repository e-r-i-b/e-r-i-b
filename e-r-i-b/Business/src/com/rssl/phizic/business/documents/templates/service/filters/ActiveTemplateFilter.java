package com.rssl.phizic.business.documents.templates.service.filters;

import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.common.types.documents.templates.ActivityCode;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.BooleanUtils;

import java.util.Map;

/**
 * Фильтр отображаемых в АРМ клиента шаблонов
 *
 * @author khudyakov
 * @ created 20.05.14
 * @ $Author$
 * @ $Revision$
 */
public class ActiveTemplateFilter implements TemplateDocumentFilter
{
	public static final String ACTIVITY_STATE_FILTER_ATTRIBUTE_NAME = "showDeleted";


	private ActivityCode code;

	public ActiveTemplateFilter()
	{
		code = ActivityCode.ACTIVE;
	}

	public ActiveTemplateFilter(Map<String, Object> params)
	{
		Boolean activity = (Boolean) params.get(ACTIVITY_STATE_FILTER_ATTRIBUTE_NAME);
		if (BooleanUtils.isNotTrue(activity))
		{
			code = ActivityCode.ACTIVE;
		}
	}

	public boolean accept(TemplateDocument template)
	{
		if (code == null)
		{
			return true;
		}

		State activityState = template.getTemplateInfo().getState();
		return code.name().equals(activityState.getCode());
	}
}
