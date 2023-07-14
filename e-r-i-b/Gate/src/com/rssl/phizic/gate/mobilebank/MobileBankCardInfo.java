package com.rssl.phizic.gate.mobilebank;

import java.io.Serializable;

/**
 * @author Erkin
 * @ created 22.04.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * ���������� �� ����� � �� (�����������)
 */
public interface MobileBankCardInfo extends Serializable
{
	/**
	 * @return ����� �����
	 */
	String getCardNumber();

	/**
	 * @return ������ ����� � ��.
	 */
	MobileBankCardStatus getStatus();

	/**
	 * @return ����� �������� ��� null � ������ ��������� �����
	 */
	String getMobilePhoneNumber();
}