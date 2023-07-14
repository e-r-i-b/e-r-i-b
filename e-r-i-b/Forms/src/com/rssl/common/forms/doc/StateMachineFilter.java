package com.rssl.common.forms.doc;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.state.StateObject;

import java.io.Serializable;
import java.util.Map;

/**
 * ��������� �������� ������ ���������
 *
 * @author khudyakov
 * @ created 30.07.14
 * @ $Author$
 * @ $Revision$
 */
public interface StateMachineFilter<SO extends StateObject> extends Serializable
{
	/**
	 * �������� �� ���������� �������
	 * @param stateObject ������ ������ ���������
	 * @return true - ������ ������ �������
	 */
	boolean isEnabled(SO stateObject) throws DocumentException, DocumentLogicException;

	/**
	 * ������� ��������� �������
	 * @return ��������� �������
	 */
	Map<String, String> getParameters();

	/**
	 * ���������� ��������� �������
	 * @param parameters ���������
	 */
	void setParameters(Map<String, String> parameters);
}
