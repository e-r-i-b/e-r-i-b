package com.rssl.phizic.payment;


import com.rssl.phizic.business.ermb.provider.ServiceProviderSmsAlias;
import java.util.List;

/**
 * Платежная задача "Оплата услуг"
 * @author Rtischeva
 * @created 03.09.13
 * @ $Author$
 * @ $Revision$
 */
public interface ServicePaymentTask extends PaymentTask
{

	public void setRequisites(List<String> requisites);

	/**
	 * задать смс-алиас поставщика услуг
	 * @param smsAlias
	 */
	void setServiceProviderSmsAlias(ServiceProviderSmsAlias smsAlias);
}
