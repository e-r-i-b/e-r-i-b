package com.rssl.phizic.gate.mobilebank;

import java.io.Serializable;

/**
 * @author Erkin
 * @ created 22.04.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������ ������� � ��
 */
public interface MobileBankTemplate extends Serializable
{
	/**
	 * @return �����, � ������� ����������� ������
	 */
	MobileBankCardInfo getCardInfo();

	/**
	 * @return ������������ ���������� � ��
	 */
	String getRecipient();

	/**
	 * @return ������ ��������������� ����������� � ��. ������� �����!!
	 */
	String[] getPayerCodes();
}