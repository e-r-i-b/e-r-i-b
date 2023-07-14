package com.rssl.phizic.gate.documents;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.exceptions.GateException;

import java.util.Calendar;

/**
 * @author krenev
 * @ created 19.04.2010
 * @ $Author$
 * @ $Revision$

 * ����������� ������� � ����� ������� ����-����
 *
 * �� ��������� ���� � �������� ����������� ���������� �� ������� ��������
 * ������ ������ �������� �������� �� ����� �������� (getChargeOffAccount)
 */
public interface AbstractCardTransfer extends AbstractTransfer
{
	/**
	 * ����� ��������. �����, � ������� ����������� �������� ��� ���������� �������(��� ������ ��������)
	 *
	 * @return ����� ����� ��������
	 */
	String getChargeOffCard();

	/**
	 * @return ������� ����� ��������
	 */
    Currency getChargeOffCurrency() throws GateException;

	/**
	 * @return ��������� ����
	 */
	String getChargeOffCardAccount();

	/**
	 * ���� ��������� ����� �������� ����� ��������
	 *
	 * @return ���� ��������� ����� �������� ����� ��������
	 */
	Calendar getChargeOffCardExpireDate();

	/**
	 * @return  �������� ����� (Visa Classic, MasterCard, Maestro Cirrus etc)
	 */
	String getChargeOffCardDescription();

	/**
	 * ���������� ��� �����������
	 * @param authorizeCode ���  �����������
	 */
	void setAuthorizeCode(String authorizeCode);

	/**
	 * �������� ��� �����������
 	 * @return ��� �����������
	 */
	String getAuthorizeCode();

	/**
 	 * @return ���� ����������� ������� � ��
	 */
	Calendar getAuthorizeDate();

	/**
	 * ���������� ���� ����������� ������� � ��
	 * @param authorizeDate ���� ����������� ������� � ��
	 */
	void setAuthorizeDate(Calendar authorizeDate);
}
