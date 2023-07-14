package com.rssl.phizic.gate.longoffer;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;

import java.math.BigDecimal;
import java.util.Calendar;
import java.io.Serializable;

/**
 * @author krenev
 * @ created 20.08.2010
 * @ $Author$
 * @ $Revision$
 * ���������� � ���������� ���������
 */
public interface LongOffer extends Serializable
{
	/**
	 * @return ������� ������������� ���������
	 */
	String getExternalId();

	/**
	 * @return ����� ����������� ���������
	 */
	String getNumber();

	/**
	 * @return ����, � ������� ��������� ���������.
	 */
	Office getOffice() throws GateException;

	/**
	 * @return ���� ������ �������� ���������
	 */
	Calendar getStartDate();

	/**
	 * @return ���� ��������� �������� ���������
	 */
	Calendar getEndDate();

	/**
	 * @return ����������� ��� ��������, ������������� � ������ ���������.
	 */
	Class<? extends GateDocument> getType();

	/**
	 * @return ��� ������� ���������� ����������� �������: �� �������������, ����������....
	 */
	ExecutionEventType getExecutionEventType();

	/**
	 * @return ��� ����� ��������� ����������� �������
	 */
	SumType getSumType();

	/**
	 * @return ����� ����������. �� ����� ���� ��������� ������������ � getPercent
	 */
	Money getAmount();

	/**
	 * @return ������� �� ����� ����������. �� ����� ���� ��������� ������������ � getAmount
	 */
	BigDecimal getPercent();

	/**
	 * @return ���� ������, � ������� ������������ �������� �� ���������
	 */
	Long getPayDay();

	/**
	 * 0 � ��������� ���������
	 * @return ��������� ���������� ���������
	 */
	Long getPriority();

	/**
	 * @return ��� ����������
	 */
	String getReceiverName();

	/**
	 * @return ������������� ��� �����������
	 */
	String getFriendlyName();
	
	/**
	 * @return ��������� �����
	 * (���� ����������� ��� ���������� �����������)
	 */
	Money getFloorLimit();

	/**
	 * @return ����� �� ����� ����� �������� �� ����������� ����(��������� � getTotalAmountPeriod())
	 */
	Money getTotalAmountLimit();

	/**
	 * @return ������, �� ������� ��������� ����� ����� ��������, ��� ����������� �� ������
	 */
	TotalAmountPeriod getTotalAmountPeriod();

	/**
	 * @return ����� �����
	 */
	public String getCardNumber();

	/**
	 * @return ����� �����
	 */
	public String getAccountNumber();
}
