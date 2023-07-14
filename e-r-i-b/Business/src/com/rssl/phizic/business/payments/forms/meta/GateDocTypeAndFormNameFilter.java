package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.handlers.DocumentFormNameHandlerFilter;
import com.rssl.common.forms.doc.HandlerFilter;
import com.rssl.common.forms.doc.ParameterListHandlerFilterBase;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.GateExecutableDocument;
import com.rssl.phizic.gate.documents.GateDocument;

/**
 * @author: vagin
 * @ created: 06.12.2011
 * @ $Author$
 * @ $Revision$
 */
public class GateDocTypeAndFormNameFilter extends ParameterListHandlerFilterBase
{
	private final String DENIED_TYPES = "deniedTypes";
	private final String ALLOWED_TYPES = "allowedTypes";

	/**
	 * ����������� ������ �� DocumentFormNameHandlerFilter � ������������� GateDocumentTypeFilter
	 * ���������: blackList = ������ ������� �� ������� ��� ��� ��������, ��� ������ � ���� ������
	 *            whiteList = ������ ������� ������� ��� ��������, ��� � ����� ������, ���� ��� ��������� �� ����������� � deniedTypes
	 *            deniedTypes = ������ ������� �������� ��� �������� � ���� ��������� ����� ��������
	 *            allowedTypes = ������ ������� ������� ������ ��� ��������� � ���� ��������� ����� ��������
	 * @param stateObject ������ ������ ���������
	 * @return
	 * @throws DocumentException
	 */
	public boolean isEnabled(StateObject stateObject) throws DocumentException, DocumentLogicException
	{
		//������� ��������� ������� ��������� �������� �� getFormName()
		if (!(stateObject instanceof BusinessDocument))
			return false;

		HandlerFilter formNameHandlerFilter = new DocumentFormNameHandlerFilter();
		formNameHandlerFilter.setParameters(getParameters());

		//����� ��������� �� gateDocumentType
		if (!(stateObject instanceof GateExecutableDocument))
			 return false;

		GateExecutableDocument document = (GateExecutableDocument) stateObject;
		Class<? extends GateDocument> documentType = document.asGateDocument().getType();
		if (documentType == null)
			return false;

		String deniedTypes = getParameter(DENIED_TYPES);
		if (deniedTypes != null && compareList(deniedTypes, documentType.getName()))
			return false;

		String allowedTypes = getParameter(ALLOWED_TYPES);
		if (allowedTypes != null && !compareList(allowedTypes, documentType.getName()))
			return false;

		return formNameHandlerFilter.isEnabled(stateObject);
	}
}
