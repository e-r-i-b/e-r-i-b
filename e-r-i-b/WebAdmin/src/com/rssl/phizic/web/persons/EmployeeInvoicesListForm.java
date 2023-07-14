package com.rssl.phizic.web.persons;

import com.rssl.phizic.business.EmployeeInvoiceData;

/**
 * @author tisov
 * @ created 28.05.15
 * @ $Author$
 * @ $Revision$
 * форма просмотра инвойсов сотрудником
 */
public class EmployeeInvoicesListForm extends EditPersonForm
{
	EmployeeInvoiceData invoiceData;                //данные об инвойсах клиента
	private Boolean showAllCommonInvoices;          //нужно ли отображать на странице все неотложенные инвойсы
	private Boolean showAllDelayedInvoices;         //нужно ли отображать на странице все отложенные инвойсы

	/**
	 * @return объект invoiceData
	 */
	public EmployeeInvoiceData getInvoiceData()
	{
		return invoiceData;
	}

	/**
	 * Просеттить значение объекта invoiceData
	 * @param invoiceData
	 */
	public void setInvoiceData(EmployeeInvoiceData invoiceData)
	{
		this.invoiceData = invoiceData;
	}

	/**
	 * @return нужно ли показывать основной список инвойсов целиком
	 */
	public Boolean isShowAllCommonInvoices()
	{
		return showAllCommonInvoices;
	}

	/**
	 * проставить значение триггера о необходимости показывать основной список инвойсов целиком
	 * @param showAllCommonInvoices
	 */
	public void setShowAllCommonInvoices(Boolean showAllCommonInvoices)
	{
		this.showAllCommonInvoices = showAllCommonInvoices;
	}

	/**
	 * @return нужно ли показывать список отложенных инвойсов целиком
	 */
	public Boolean isShowAllDelayedInvoices()
	{
		return showAllDelayedInvoices;
	}

	/**
	 * проставить значение триггера о необходимости показывать список отложенных инвойсов целиком
	 * @param showAllDelayedInvoices
	 */
	public void setShowAllDelayedInvoices(Boolean showAllDelayedInvoices)
	{
		this.showAllDelayedInvoices = showAllDelayedInvoices;
	}
}
