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
 * Информация по текущему/отложенному счету для финансового календаря
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
	 * @return идентификатор счета
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * @param id - идентификатор счета
	 */
	public void setId(String id)
	{
		this.id = id;
	}

	/**
	 * @return тип платежа (invoice или shopOrder)
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * @param type тип платежа (invoice или shopOrder)
	 */
	public void setType(String type)
	{
		this.type = type;
	}

	/**
	 * @return дата исполнения отложенного счета
	 */
	public Calendar getPayDate()
	{
		return payDate;
	}

	/**
	 * @param payDate - дата исполнения отложенного счета
	 */
	public void setPayDate(Calendar payDate)
	{
		this.payDate = payDate;
	}

	/**
	 * @return наименование услуги
	 */
	public String getServiceName()
	{
		return serviceName;
	}

	/**
	 * @param serviceName - наименование услуги
	 */
	public void setServiceName(String serviceName)
	{
		this.serviceName = serviceName;
	}

	/**
	 * @return наименование объекта учета
	 */
	public String getAccountingEntityName()
	{
		return accountingEntityName;
	}

	/**
	 * @param accountingEntityName - наименование объекта учета
	 */
	public void setAccountingEntityName(String accountingEntityName)
	{
		this.accountingEntityName = accountingEntityName;
	}

	/**
	 * @return название поставщика услуг
	 */
	public String getReceiverName()
	{
		return receiverName;
	}

	/**
	 * @param receiverName - название поставщика услуг
	 */
	public void setReceiverName(String receiverName)
	{
		this.receiverName = receiverName;
	}

	/**
	 * @return реквизиты по отложенному счету
	 */
	public String getRequisites()
	{
		return requisites;
	}

	/**
	 * @param requisites - реквизиты по отложенному счету
	 */
	public void setRequisites(String requisites)
	{
		this.requisites = requisites;
	}

	/**
	 * @param amount сумма
	 */
	public void setAmount(BigDecimal amount)
	{
		this.amount = amount;
	}

	/**
	 * @return сумма
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
