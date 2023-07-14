package com.rssl.phizic.business.dictionaries.providers;

import com.rssl.phizic.business.dictionaries.kbk.PaymentType;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.gate.payments.systems.recipients.Service;
import com.rssl.phizic.utils.BeanHelper;

/**
 * Налоговый получатель платежа
 *
 * @author hudyakov
 * @ created 20.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class TaxationServiceProvider extends ServiceProviderBase
{
	private boolean fullPayment;        //полноформатный платеж
	private PaymentType payType;        //тип оплаты

	/**
	 * Признак того, что плятеж является полноформатным
	 * @return - true (является)
	 */
	public boolean isFullPayment()
	{
		return fullPayment;
	}

	public void setFullPayment(boolean fullPayment)
	{
		this.fullPayment = fullPayment;
	}

	/**
	 * Тип оплаты
	 * @return - PaymentType
	 */
	public PaymentType getPayType()
	{
		return payType;
	}

	public void setPayType(PaymentType payType)
	{
		this.payType = payType;
	}

	public Service getService()
	{
		return null;
	}

	public void updateFrom(DictionaryRecord that)
	{
		((TaxationServiceProvider) that).setId(this.id);
		BeanHelper.copyProperties(this, that);
	}

	public ServiceProviderType getType()
	{
		return ServiceProviderType.TAXATION;
	}
}
