package com.rssl.phizic.gate.payments.template;

import com.rssl.phizic.common.types.documents.State;

import java.io.Serializable;

/**
 * ���������� �� �������
 *
 * @author khudyakov
 * @ created 17.05.2013
 * @ $Author$
 * @ $Revision$
 */
public interface TemplateInfo extends Serializable
{
	/**
	 * @return �������� �������
	 */
	String getName();

	/**
	 * ���������� �������� �������
	 * @param name �������� �������
	 */
	void setName(String name);

	/**
	 * @return ������������/�������� �� � ����
	 */
	boolean isUseInMAPI();

	/**
	 * ���������� ������� ������������� � ����
	 * @param useInMAPI true - ������������
	 */
	void setUseInMAPI(boolean useInMAPI);

	/**
	 * @return ������������/�������� �� � ����������� ����������������
	 */
	boolean isUseInATM();

	/**
	 * ���������� ������� ������������� � ����������� ����������������
	 * @param useInATM true - ������������
	 */
	void setUseInATM(boolean useInATM);

	/**
	 * @return ������������/�������� �� � ����
	 */
	boolean isUseInERMB();

	/**
	 * ���������� ������� ������������� � ����
	 * @param useInERMB true - ������������
	 */
	void setUseInERMB(boolean useInERMB);

	/**
	 * @return ������������/�������� �� � ����
	 */
	boolean isUseInERIB();

	/**
	 * ���������� ������� ������������� � ����
	 * @param useInERIB true - ������������
	 */
	void setUseInERIB(boolean useInERIB);

	/**
	 * @return ������ ����������� ��� ������
	 */
	int getOrderInd();

	/**
	 * ���������� ������ ����������� ������
	 * @param orderInd ������ ����������� ��� ������
	 */
	void setOrderInd(int orderInd);

	/**
	 * @return ��������� � ���������� ���� ����, �������
	 */
	boolean isVisible();

	/**
	 * ���������� ��������� � ���������� ���� ����, �������
	 * @param visible ��������� � ���������� ���� ����, �������
	 */
	void setVisible(boolean visible);

	/**
	 * @return ������ ���������� ������� ���������. ����������� ��������: ������ ��������, �������
	 */
	State getState();

	/**
	 * ���������� ������ ���������
	 * @param state ������
	 */
	void setState(State state);
}
