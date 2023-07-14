package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.doc.ParameterListHandlerFilterBase;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.GateExecutableDocument;

/**
 * @author Gulov
 * @ created 24.04.14
 * @ $Author$
 * @ $Revision$
 */
public class DocumentCreationChannelFilter extends ParameterListHandlerFilterBase
{
	public boolean isEnabled(StateObject stateObject) throws DocumentException
	{
		/**
		 * ���������: blackList = ������ �� ������� ��� ��� ������� �������� ���������, ��� ������ � ���� ������
		 *            whiteList = ������ ������� ��� ������� �������� ���������, ��� � ����� ������.
		 * @param stateObject
		 * @return
		 */

		if (!(stateObject instanceof GateExecutableDocument))
			return false;

		GateExecutableDocument paymentDocument = (GateExecutableDocument) stateObject;
		CreationType creationChannel = paymentDocument.getClientCreationChannel();

		if (getParameter("blackList") != null)
			return !compareList(getParameter("blackList"), creationChannel.name());

		if (getParameter("whiteList") != null)
			return compareList(getParameter("whiteList"), creationChannel.name());

		return false;
	}
}
