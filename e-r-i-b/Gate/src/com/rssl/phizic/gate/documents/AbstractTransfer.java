package com.rssl.phizic.gate.documents;

import com.rssl.phizic.common.types.CurrencyRate;
import com.rssl.phizic.common.types.Money;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author khudyakov
 * @ created 18.08.2010
 * @ $Author$
 * @ $Revision$

 * ����������� ������� �� ����� ��� ����� ������� ���� ����
 * � �������� ������ ���� ������ ���� ChargeOffAmount, ���� DestinationAmount,
 * �� �� ��� ������, � ����� ��� �������.
 *
 * �� ��������� ���� � �������� ����������� ���������� �� ������� ��������
 * ������ ������ �������� �������� �� ����� �������� (getChargeOffAccount)
 */
public interface AbstractTransfer extends SynchronizableDocument, Serializable
{
	/**
	 * ����� �������� (����� �������) ��� ����� ��������.
	 * ���������� ������� ����� �������� ����������� �� ������ � ������.
	 *
	 * @return ����� �������.
	 */
	Money getChargeOffAmount();

	/**
	 * ���������� ����� ��������(� ��� ������, ���� ��� ���������� � �������� ���������� �������)
	 * @param amount ����� ��������
	 */
	void setChargeOffAmount(Money amount);

	/**
	 * ����� ����������. (��� ��������)
	 *
	 * @return ����� ����������.
	 */
	Money getDestinationAmount();

	/**
	 * ���������� ����� ����������(� ��� ������, ���� ��� ���������� � �������� ���������� �������)
	 * @param amount ����� ����������
	 */
	void setDestinationAmount(Money amount);

	/**
	 * ��������� ���  ��������� �����: ����� �������� ��� ����� ����������
	 * @return
	 */
	InputSumType getInputSumType();

	/**
	 * @return ���� ������� ������ ������� ��� ������ ��������
	 */
	CurrencyRate getDebetSaleRate();

	/**
	 * @return ���� ������� ������ ������� ��� ������ ��������
	 */
	CurrencyRate getDebetBuyRate();

	/**
	 * @return ���� ������� ������ ������� ��� ������ ����������
	 */
	CurrencyRate getCreditSaleRate();

	/**
	 * @return ���� ������� ������ � ������� ��� ������ ����������
	 */
	CurrencyRate getCreditBuyRate();

	/**
	 * @return ���� ��������� �� ������ �������� � ������ ����������
	 */
	BigDecimal getConvertionRate();

	/**
	 * @return ��� ��������
	 */
	String getOperationCode();

	/**
	 * @return ���������/���������� ������� (��� ���������� ���������� null)
	 */
	String getGround();

	/**
	 * ���������� �������� ��������� �������
	 * @param ground ���������
	 */
	void setGround(String ground);

	/**
	 * ��� ����������� ������ ���� ��������
	 * @return - ���
	 */
	String getPayerName();

	/**
	 * ��� ��������� �����, �� �������� ������ ��������
	 * @param tariffPlanESB - ��� ��������� �����
	 */
	void setTariffPlanESB(String tariffPlanESB);

}
