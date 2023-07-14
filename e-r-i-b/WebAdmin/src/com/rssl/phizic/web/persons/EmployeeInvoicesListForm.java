package com.rssl.phizic.web.persons;

import com.rssl.phizic.business.EmployeeInvoiceData;

/**
 * @author tisov
 * @ created 28.05.15
 * @ $Author$
 * @ $Revision$
 * ����� ��������� �������� �����������
 */
public class EmployeeInvoicesListForm extends EditPersonForm
{
	EmployeeInvoiceData invoiceData;                //������ �� �������� �������
	private Boolean showAllCommonInvoices;          //����� �� ���������� �� �������� ��� ������������ �������
	private Boolean showAllDelayedInvoices;         //����� �� ���������� �� �������� ��� ���������� �������

	/**
	 * @return ������ invoiceData
	 */
	public EmployeeInvoiceData getInvoiceData()
	{
		return invoiceData;
	}

	/**
	 * ���������� �������� ������� invoiceData
	 * @param invoiceData
	 */
	public void setInvoiceData(EmployeeInvoiceData invoiceData)
	{
		this.invoiceData = invoiceData;
	}

	/**
	 * @return ����� �� ���������� �������� ������ �������� �������
	 */
	public Boolean isShowAllCommonInvoices()
	{
		return showAllCommonInvoices;
	}

	/**
	 * ���������� �������� �������� � ������������� ���������� �������� ������ �������� �������
	 * @param showAllCommonInvoices
	 */
	public void setShowAllCommonInvoices(Boolean showAllCommonInvoices)
	{
		this.showAllCommonInvoices = showAllCommonInvoices;
	}

	/**
	 * @return ����� �� ���������� ������ ���������� �������� �������
	 */
	public Boolean isShowAllDelayedInvoices()
	{
		return showAllDelayedInvoices;
	}

	/**
	 * ���������� �������� �������� � ������������� ���������� ������ ���������� �������� �������
	 * @param showAllDelayedInvoices
	 */
	public void setShowAllDelayedInvoices(Boolean showAllDelayedInvoices)
	{
		this.showAllDelayedInvoices = showAllDelayedInvoices;
	}
}
