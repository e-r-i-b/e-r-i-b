package com.rssl.phizic.gate.depo;

import java.io.Serializable;

/**
 * @author mihaylov
 * @ created 16.08.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * ����������� ���������� � ������ ������������� �� ����� ����
 * �������� � ���� ��������� ��� ������ �������������
 */
public interface DepoDebtItemInfo extends Serializable
{
	/**
	 * ��� ����� ����������
	 * @return bankBIC
	 */
	String getBankBIC();

	/**
	 * ������������ ����� ����������
	 * @return bankName
	 */
	String getBankName();

	/**
	 * ������� ����� ����������
	 * @return bankCorAccount
	 */
	String getBankCorAccount();

	/**
	 * ������������ ���������� �������
	 * @return recipientName
	 */
	String getRecipientName();

	/**
	 * ��� ����������
	 * @return recipientINN
	 */
	String getRecipientINN();

	/**
	 * ��� ����������
	 * @return recipientKPP
	 */
	String getRecipientKPP();

	/**
	 * ��������� ���� ����������
	 * @return recipientAccount
	 */
	String getRecipientAccount();
}
