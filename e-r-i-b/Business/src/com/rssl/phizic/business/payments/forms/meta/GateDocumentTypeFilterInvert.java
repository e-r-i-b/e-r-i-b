package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.state.StateObject;

/**
 * @author vagin
 * @ created 24.09.2012
 * @ $Author$
 * @ $Revision$
 * ����� �� ���� ���������, ������������� �������� GateDocumentTypeFilter.
 * ���������� �������� ��� ���������� � className ���� ���������.
 */
public class GateDocumentTypeFilterInvert extends GateDocumentTypeFilter
{
	public boolean isEnabled(StateObject stateObject)
	{
		return !super.isEnabled(stateObject);
	}
}
