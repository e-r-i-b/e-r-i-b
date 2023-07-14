package com.rssl.common.forms.doc;

import com.rssl.common.forms.state.StateObject;

/**
 * Базовый класс для хендлера, где параметром может быть список
 * @author egorova
 * @ created 07.04.2011
 * @ $Author$
 * @ $Revision$
 */

public abstract class ParameterListHandlerBase<SO extends StateObject> implements BusinessDocumentHandler<SO>
{
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
