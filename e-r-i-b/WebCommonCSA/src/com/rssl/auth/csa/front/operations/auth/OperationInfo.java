package com.rssl.auth.csa.front.operations.auth;

import com.rssl.phizic.web.auth.Stage;

import java.util.Map;

/**
 * @author niculichev
 * @ created 13.09.2012
 * @ $Author$
 * @ $Revision$
 */
public interface OperationInfo
{
	/**
	 * @return false - ������������ ��������
	 */
	boolean isValid();

	/**
	 * @return ������������� ��������
	 */
	String getOUID();

	/**
	 * @return ������� ���������
	 */
	Stage getCurrentStage();

	/**
	 * @return ��� �������� ���������
	 */
	String getCurrentName();

	/**
	 * ����������� �� ��������� ���������
	 */
	void nextStage();

	/**
	 * @return ��������� �������������(��� �������������, ���������� �������, ���������� �����, ����� ������ �� ����, ����� ����, ���������� ������� �� ����)
	 */
	Map<String, Object> getConfirmParams();

	/**
	 * @return ����� ��������������
	 */
	public String getAuthToken();
}
