package com.rssl.phizic.business.documents.templates.service.filters;

import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.common.types.documents.StateCode;
import com.rssl.phizic.utils.ArraysHelper;
import com.rssl.phizic.utils.ListUtil;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Фильтр шаблонов документов по статусу шаблона
 * Важно: для переводов между своими счетами и погашения кредитов производится замена статуса Сверхлимитный на Активный
 * @author khudyakov
 * @ created 01.07.14
 * @ $Author$
 * @ $Revision$
 */
public class StateTemplateFilter implements TemplateDocumentFilter
{
	public static final String STATE_FILTER_ATTRIBUTE_NAME = "status";

	private static final Map<String, String> SYNONYMS = new HashMap<String, String>(4);
	static
	{
		SYNONYMS.put(StateCode.WAIT_CONFIRM_TEMPLATE.name(),   StateCode.WAIT_CONFIRM_TEMPLATE.name());
		SYNONYMS.put(StateCode.TEMPLATE.name(),                StateCode.WAIT_CONFIRM_TEMPLATE.name());
		SYNONYMS.put(StateCode.DRAFTTEMPLATE.name(),           StateCode.DRAFTTEMPLATE.name());
		SYNONYMS.put(StateCode.SAVED_TEMPLATE.name(),          StateCode.SAVED_TEMPLATE.name());
	}

	private List<String> states = new ArrayList<String>();

	public StateTemplateFilter(StateCode ... stateCodes)
	{
		if (ArrayUtils.isNotEmpty(stateCodes))
		{
			for (StateCode stateCode : stateCodes)
			{
				states.add(stateCode.name());
			}
		}
	}

	public StateTemplateFilter(Map<String, Object> params)
	{
		String s = (String) params.get(STATE_FILTER_ATTRIBUTE_NAME);
		if (StringHelper.isNotEmpty(s))
		{
			states.addAll(ListUtil.fromArray(s.split(",")));
		}
	}

	public boolean accept(TemplateDocument template)
	{
		if (CollectionUtils.isEmpty(states))
		{
			return true;
		}

		if (!FormType.isInternalDocument(template.getFormType()))
		{
			return states.contains(template.getState().getCode());
		}
		return states.contains(SYNONYMS.get(template.getState().getCode()));
	}
}
