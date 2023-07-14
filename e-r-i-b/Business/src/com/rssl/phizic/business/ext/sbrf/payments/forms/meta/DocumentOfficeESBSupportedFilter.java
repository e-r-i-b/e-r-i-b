package com.rssl.phizic.business.ext.sbrf.payments.forms.meta;

import com.rssl.common.forms.doc.HandlerFilterBase;
import com.rssl.common.forms.state.StateObject;
import com.rssl.common.forms.DocumentException;
import com.rssl.phizic.business.documents.GateExecutableDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizicgate.manager.ext.sbrf.ESBHelper;

/**
 * @author krenev
 * @ created 12.07.2010
 * @ $Author$
 * @ $Revision$
 * Фильтрует документы, отправленные в подразделение, обслуживаемое шиной
 */
public class DocumentOfficeESBSupportedFilter extends HandlerFilterBase
{
	public boolean isEnabled(StateObject stateObject) throws DocumentException
	{
		if (!(stateObject instanceof GateExecutableDocument))
			return false;
		GateExecutableDocument document = (GateExecutableDocument) stateObject;
		try
		{
			return ESBHelper.isESBSupported(document);
		}
		catch (GateException e)
		{
			throw new DocumentException(e);
		}
	}
}
