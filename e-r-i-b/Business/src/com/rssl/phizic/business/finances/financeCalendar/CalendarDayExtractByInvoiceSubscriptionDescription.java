package com.rssl.phizic.business.finances.financeCalendar;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizgate.common.basket.RequisitesHelper;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.utils.StringHelper;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

/**
 * @author lepihina
 * @ created 08.05.14
 * $Author$
 * $Revision$
 *
 * ���������� �� ��������/����������� ����� ��� ����������� ���������
 */
public class CalendarDayExtractByInvoiceSubscriptionDescription
{
	private String id;
	private String type;
	private Calendar payDate;
	private String serviceName;
	private String accountingEntityName;
	private String receiverName;
	private String requisites;
	private BigDecimal amount;

	/**
	 * @return ������������� �����
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * @param id - ������������� �����
	 */
	public void setId(String id)
	{
		this.id = id;
	}

	/**
	 * @return ��� ������� (invoice ��� shopOrder)
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * @param type ��� ������� (invoice ��� shopOrder)
	 */
	public void setType(String type)
	{
		this.type = type;
	}

	/**
	 * @return ���� ���������� ����������� �����
	 */
	public Calendar getPayDate()
	{
		return payDate;
	}

	/**
	 * @param payDate - ���� ���������� ����������� �����
	 */
	public void setPayDate(Calendar payDate)
	{
		this.payDate = payDate;
	}

	/**
	 * @return ������������ ������
	 */
	public String getServiceName()
	{
		return serviceName;
	}

	/**
	 * @param serviceName - ������������ ������
	 */
	public void setServiceName(String serviceName)
	{
		this.serviceName = serviceName;
	}

	/**
	 * @return ������������ ������� �����
	 */
	public String getAccountingEntityName()
	{
		return accountingEntityName;
	}

	/**
	 * @param accountingEntityName - ������������ ������� �����
	 */
	public void setAccountingEntityName(String accountingEntityName)
	{
		this.accountingEntityName = accountingEntityName;
	}

	/**
	 * @return �������� ���������� �����
	 */
	public String getReceiverName()
	{
		return receiverName;
	}

	/**
	 * @param receiverName - �������� ���������� �����
	 */
	public void setReceiverName(String receiverName)
	{
		this.receiverName = receiverName;
	}

	/**
	 * @return ��������� �� ����������� �����
	 */
	public String getRequisites()
	{
		return requisites;
	}

	/**
	 * @param requisites - ��������� �� ����������� �����
	 */
	public void setRequisites(String requisites)
	{
		this.requisites = requisites;
	}

	/**
	 * @param amount �����
	 */
	public void setAmount(BigDecimal amount)
	{
		this.amount = amount;
	}

	/**
	 * @return �����
	 */
	public BigDecimal getAmount()
	{
		if (amount != null)
			return amount;

		if (StringHelper.isNotEmpty(requisites))
		{
			try
			{
				List<Field> requisitesList = RequisitesHelper.deserialize(requisites);
				for(Field field : requisitesList)
				{
					if(field.isMainSum())
					{
						return new BigDecimal(field.getValue().toString());
					}
				}
			}
			catch (DocumentException ignore)
			{}
		}

		return null;
	}
}
