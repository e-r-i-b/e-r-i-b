package com.rssl.phizicgate.manager.services.objects;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPayment;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPaymentStatus;
import com.rssl.phizic.gate.longoffer.ExecutionEventType;
import com.rssl.phizic.gate.longoffer.SumType;
import com.rssl.phizic.gate.longoffer.TotalAmountPeriod;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.common.types.Money;

import java.util.Calendar;
import java.math.BigDecimal;

/**
 * @author hudyakov
 * @ created 28.02.2011
 * @ $Author$
 * @ $Revision$
 */
public abstract class AutoPaymentBase implements AutoPayment
{
	AutoPayment delegate;

	public AutoPaymentBase(AutoPayment payment)
	{
		delegate = payment;
	}

	/**
	 * @return ����� �����
	 */
	public String getCardNumber()
	{
		return delegate.getCardNumber();
	}

	/**
	 * @return ��� ������� (��������)
	 */
	public String getCodeService()
	{
		return delegate.getCodeService();
	}

	public void setCodeService(String codeService)
	{
		delegate.setCodeService(codeService);
	}

	/**
	 * @return ��������� �����
	 * (���� ����������� ��� ���������� �����������)
	 */
	public Money getFloorLimit()
	{
		return delegate.getFloorLimit();
	}

	/**
	 * @return ������������� ��� �����������
	 */
	public String getFriendlyName()
	{
		return delegate.getFriendlyName();
	}

	/**
	 * @return ��������� ����������
	 */
	public AutoPaymentStatus getReportStatus()
	{
		return delegate.getReportStatus();
	}

	/**
	 * @return ���� ���������� ������ �� ����������
	 */
	public Calendar getDateAccepted()
	{
		return delegate.getDateAccepted();
	}

	/**
	 * @return ����� �������� �����/��������
	 */
	public String getRequisite()
	{
		return delegate.getRequisite();
	}

	/**
	 * @return ��� ����������
	 */
	public String getReceiverName()
	{
		return delegate.getReceiverName();
	}

	/**
	 * @return ����� ����������� ���������
	 */
	public String getNumber()
	{
		return delegate.getNumber();
	}

	/**
	 * @return ����, � ������� ��������� ���������.
	 */
	public Office getOffice() throws GateException
	{
		return delegate.getOffice();
	}

	/**
	 * @return ���� ������ �������� ���������
	 */
	public Calendar getStartDate()
	{
		return delegate.getStartDate();
	}

	/**
	 * @return ���� ��������� �������� ���������
	 */
	public Calendar getEndDate()
	{
		return delegate.getEndDate();
	}

	/**
	 * @return ����������� ��� ��������, ������������� � ������ ���������.
	 */
	public Class<? extends GateDocument> getType()
	{
		return delegate.getType();
	}

	/**
	 * @return ��� ������� ���������� ����������� �������: �� �������������, ����������....
	 */
	public ExecutionEventType getExecutionEventType()
	{
		return delegate.getExecutionEventType();
	}

	/**
	 * @return ��� ����� ��������� ����������� �������
	 */
	public SumType getSumType()
	{
		return delegate.getSumType();
	}

	/**
	 * @return ����� ����������. �� ����� ���� ��������� ������������ � getPercent
	 */
	public Money getAmount()
	{
		return delegate.getAmount();
	}

	/**
	 * @return ������� �� ����� ����������. �� ����� ���� ��������� ������������ � getAmount
	 */
	public BigDecimal getPercent()
	{
		return delegate.getPercent();
	}

	/**
	 * @return ���� ������, � ������� ������������ �������� �� ���������
	 */
	public Long getPayDay()
	{
		return delegate.getPayDay();
	}

	/**
	 * 0 � ��������� ���������
	 * @return ��������� ���������� ���������
	 */
	public Long getPriority()
	{
		return delegate.getPriority();
	}

	/**
	 * @return ����� �����
	 */
	public String getAccountNumber()
	{
		return delegate.getAccountNumber();
	}

	/**
	 * @return ����� �� ����� ����� �������� �� ����������� ����(��������� � getTotalAmountPeriod())
	 */
	public Money getTotalAmountLimit()
	{
		return delegate.getTotalAmountLimit();
	}

	/**
	 * @return ������, �� ������� ��������� ����� ����� ��������, ��� ����������� �� ������
	 */
	public TotalAmountPeriod getTotalAmountPeriod()
	{
		return delegate.getTotalAmountPeriod();
	}
}
