package com.rssl.phizic.gate.mobilebank;

import com.rssl.phizic.common.types.client.LoginType;

import java.util.Calendar;
import java.util.List;

/**
 * User: Balovtsev
 * Date: 11.09.2012
 * Time: 17:19:48
 *
 * �������� ����� �� ������� ����������� � MobileBankGate
 */
public interface UserInfo
{
	/**
	 * @return iPas �������������
	 */
	String getUserId();

	/**
	 * @return RbTbBrunch
	 */
	String getCbCode();

	/**
	 * @return ����� �����
	 */
	String getCardNumber();

	/**
	 * @return ���� ��������
	 */
	Calendar getBirthdate();

	/**
	 * @return ���
	 */
	String getFirstname();

	/**
	 * @return ��������
	 */
	String getPatrname();

	/**
	 * @return �������
	 */
	String getSurname();

	/**
	 * @return ��������
	 */
	String getPassport();

	/**
	 * @return ��� ���������� � ���� ���
	 */
	LoginType getLoginType();

	/**
	 * @return �������, �������� �� ����� ��������
	 */
	boolean isMainCard();

	/**
	 * @return ������� �������� �� �����
	 */
	boolean isActiveCard();

    /**
     * @return ������ ���� �������
     */
    List<String> getCards();
}
