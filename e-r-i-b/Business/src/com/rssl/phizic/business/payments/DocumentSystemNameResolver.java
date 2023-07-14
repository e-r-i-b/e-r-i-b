package com.rssl.phizic.business.payments;

import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * ������������ ����� � ���������� ������� ���������� ������ 
 * ���������� ������� � ������ ��������� ��������
 * @author gladishev
 * @ created 26.04.2011
 * @ $Author$
 * @ $Revision$
 */
public interface DocumentSystemNameResolver
{
	/**
	 * @param document - ��������
	 * @return ��� ������� � ������� ��������� ��������  
	 */
	String getSystemName(StateObject document) throws GateException;
}
