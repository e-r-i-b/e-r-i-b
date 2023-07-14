package com.rssl.phizicgate.wsgate.services.template.updaters;

import com.rssl.phizic.business.documents.templates.Constants;
import com.rssl.phizic.gate.documents.GateTemplate;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Апдейтер шаблонов документов
 *
 * @author khudyakov
 * @ created 05.08.14
 * @ $Author$
 * @ $Revision$
 */
public class TemplateUpdaterImpl implements TemplateUpdater
{
	private Map<String, Long> ids = new HashMap<String, Long>();

	public TemplateUpdaterImpl(List objects)
	{
		if (CollectionUtils.isEmpty(objects))
		{
			return;
		}

		for (Object object : objects)
		{
			com.rssl.phizicgate.wsgate.services.template.generated.GateTemplate generated = (com.rssl.phizicgate.wsgate.services.template.generated.GateTemplate) object;
			ids.put(generated.getOperationUID(), generated.getId());
		}
	}

	public void update(GateTemplate template) throws GateException
	{
		if (template.getId() == null)
		{
			if (StringHelper.isEmpty(template.getOperationUID()))
			{
				throw new GateException(String.format(Constants.NOT_FOUND_OPERATION_UID_ERROR_MESSAGE, template.getOperationUID()));
			}

			Long id = ids.get(template.getOperationUID());
			if (id == null)
			{
				throw new GateException(String.format(Constants.NOT_FOUND_TEMPLATE_ID_ERROR_MESSAGE, template.getOperationUID()));
			}

			template.setId(id);
		}
	}
}
