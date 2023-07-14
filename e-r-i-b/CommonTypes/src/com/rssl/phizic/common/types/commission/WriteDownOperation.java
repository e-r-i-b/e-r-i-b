package com.rssl.phizic.common.types.commission;

import com.rssl.phizic.common.types.Money;

import java.math.BigDecimal;

/**
 * @author vagin
 * @ created 01.10.13
 * @ $Author$
 * @ $Revision$
 * ������������� ��� �������(��������/�����������).
 */
public class WriteDownOperation
{
	private String operationName;  //������� ������������
	private Money curAmount;       //�����
	private String turnOver;       //������� �����������/��������(RECEIPT/CHARGE).
	private static final String ATTR_DELIMITER = "|";  //����������� ���������� � ��������� ������������� �������.

	public String getOperationName()
	{
		return operationName;
	}

	public void setOperationName(String operationName)
	{
		this.operationName = operationName;
	}

	public Money getCurAmount()
	{
		return curAmount;
	}

	public void setCurAmount(Money curAmount)
	{
		this.curAmount = curAmount;
	}

	public String getTurnOver()
	{
		return turnOver;
	}

	public void setTurnOver(String turnOver)
	{
		this.turnOver = turnOver;
	}

	/**
	 * �������� ���������� ������������� ��������
	 * @return ������, ��� ��������� �������� ����� '|' � �������: �������� ��������, �����, ��� ������ �����, ������� ��������/������������(RECEIPT/CHARGE)
	 */
	public String asString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(operationName).append(ATTR_DELIMITER).append(curAmount.getDecimal())
				.append(ATTR_DELIMITER).append(curAmount.getCurrency().getCode())
				.append(ATTR_DELIMITER).append(turnOver);
		return sb.toString();
	}
}
