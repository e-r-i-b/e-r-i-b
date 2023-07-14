package com.rssl.phizic.auth.modes;

import com.rssl.phizic.common.types.ConfirmStrategyType;

import java.io.Serializable;
import java.util.List;

/**
 * @author Evgrafov
 * @ created 02.01.2007
 * @ $Author: moshenko $
 * @ $Revision: 43580 $
 */

public interface ConfirmRequest extends Serializable
{
	/**
	 * @return ��� ���������
	 */
	ConfirmStrategyType getStrategyType();

	/**
	 * @return ������ � �������
	 */
	boolean isError();

	/**
	 * @return ������ ��� ����� ������ 	
	 */
	boolean isErrorFieldPassword();

	/**
	 * @param error - � ���� ������ ��������� ������.
	 */
	void setErrorFieldPassword(boolean error);
	
	/**
	 * @return ��������� �� �������� ����������� ����� ��������������
	 */
	boolean isPreConfirm();

	/**
	 * @param isPreConfirm - ��������� �� �������� ����������� ����� ��������������
	 */
	void setPreConfirm(boolean isPreConfirm);

	/**
	 * @return ��������� �� ������
	 */
	String getErrorMessage();

	/**
	 * @param errorMessage - ��������� �� ������
	 */
	void setErrorMessage(String errorMessage);

	/**
	 * @return ������ �������������� ���������
	 */
	List<String> getMessages();

	/**
	 * @param message - �������������� ���������
	 */
	void addMessage(String message);

	/**
	 * @return �������������� ����������
	 */
	List<String> getAdditionInfo();

	/**
	 * �������� ������ ���������
	 */
	public void resetMessages();
}