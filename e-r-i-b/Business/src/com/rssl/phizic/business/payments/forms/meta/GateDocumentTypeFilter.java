package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.doc.ParameterListHandlerFilterBase;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.GateExecutableDocument;
import com.rssl.phizic.gate.documents.GateDocument;

import java.util.Map;

/**
 * @author Krenev
 * @ created 14.01.2010
 * @ $Author$
 * @ $Revision$
 * фильтр по типу документа в гейтовом представлении.
 * Значение(класс типа) передается в параметре className
 */
public class GateDocumentTypeFilter<SO extends StateObject> extends ParameterListHandlerFilterBase<SO>
{
	private static final String CLASS_NAME_PARAM_NAME = "className";

	public boolean isEnabled(StateObject stateObject)
	{
		if (!(stateObject instanceof GateExecutableDocument))
			return false;

		GateExecutableDocument document = (GateExecutableDocument) stateObject;
		Class<? extends GateDocument> type = document.asGateDocument().getType();
		if (type == null)
			return false;
		
		String documentType = type.getName();

		for (Map.Entry<String, String> entry : getParameters().entrySet())
		{
			//если значение параметра совпало с типом документа, то хендлер активен
			if (entry.getKey().contains(CLASS_NAME_PARAM_NAME) && compareList(entry.getValue(), documentType))
				return true;
		}
		return false;
	}
}
