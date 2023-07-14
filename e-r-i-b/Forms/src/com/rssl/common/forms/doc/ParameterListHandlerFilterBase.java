package com.rssl.common.forms.doc;

import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;

/**
 *
 * Базовый класс для хендлеров, у которых может быть список в параметре
 *
 * @author egorova
 * @ created 01.04.2011
 * @ $Author$
 * @ $Revision$
 */

public abstract class ParameterListHandlerFilterBase<SO extends StateObject> extends HandlerFilterBase<SO>
{
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	protected boolean compareList(String str, String formName)
	{
		String[] data = str.split(";");
		for (int i = 0; i < data.length; i++)
		{
			if (data[i].trim().equals(formName))
				return true;
		}
		return false;
	}
}
