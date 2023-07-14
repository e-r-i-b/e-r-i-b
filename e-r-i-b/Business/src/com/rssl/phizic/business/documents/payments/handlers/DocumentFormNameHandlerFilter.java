package com.rssl.phizic.business.documents.payments.handlers;

import com.rssl.common.forms.doc.ParameterListHandlerFilterBase;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.payments.BusinessDocument;

/**
 * @author khudyakov
 * @ created 13.01.2009
 * @ $Author$
 * @ $Revision$
 */
public class DocumentFormNameHandlerFilter extends ParameterListHandlerFilterBase
{

	/**
	 * ���������: blackList = ������ ������� �� ������� ��� ��� ��������, ��� ������ � ���� ������
	 *            whiteList = ������ ������� ������� ��� ��������, ��� � ����� ������.
	 * @param stateObject
	 * @return
	 */
	public boolean isEnabled(StateObject stateObject)
	{
		if (!(stateObject instanceof BusinessDocument))
			return false;

		String formName = ((BusinessDocument) stateObject).getFormName();
		if (getParameter("blackList") != null)
			return !compareList(getParameter("blackList"), formName);

		if (getParameter("whiteList") != null)
			return compareList(getParameter("whiteList"), formName);

		return false;
	}
}
