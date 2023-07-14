package com.rssl.phizic.auth;

import com.rssl.phizic.auth.modes.UserVisitingMode;

/**
 * Created by IntelliJ IDEA.
 * User: Evgrafov
 * Date: 01.09.2005
 * Time: 20:49:23
 */
public interface Login extends CommonLogin
{
	Long getPinEnvelopeId();

	/**
	 * @return ��� ���������� ����� �������
	 */
	UserVisitingMode getLastUserVisitingMode();

	/**
	 * @param lastUserVisitingMode ��� ���������� ����� �������
	 */
	void setLastUserVisitingMode(UserVisitingMode lastUserVisitingMode);

	/**
	 * ���������� ����� ���������� �����
	 * @param cardNumber ����� �����
	 */
	void setLastLogonCardNumber(String cardNumber);
}
