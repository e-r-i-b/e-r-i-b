package com.rssl.phizicgate.esberibgate.payment.recipients;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizicgate.esberibgate.ws.generated.BillingPayExecRq_Type;

/**
 * расширенные хелпер для создания запросов.
 *
 * @author bogdanov
 * @ created 16.10.2012
 * @ $Author$
 * @ $Revision$
 */

public class BillingExtendRequestHelper extends BillingRequestHelper
{
	public BillingExtendRequestHelper(GateFactory factory)
	{
		super(factory);
	}

	/**
	 * Создание запроса на испольнение с параметрами AuthorizeCode и AuthorizeDate (повторный запрос на исполнение).
	 * @param payment платеж
	 * @return запрос.
	 * @throws GateException
	 * @throws GateLogicException
	 */
	@Override
	public BillingPayExecRq_Type createBillingPayExecRq(CardPaymentSystemPayment payment) throws GateException, GateLogicException
	{
		BillingPayExecRq_Type billingPayExecRq = super.createBillingPayExecRq(payment);
		if (!StringHelper.isEmpty(payment.getAuthorizeCode()))
			billingPayExecRq.setAuthorizationCode(Long.parseLong(payment.getAuthorizeCode()));

		if (payment.getAuthorizeDate() != null)
			billingPayExecRq.setAuthorizationDtTm(XMLDatatypeHelper.formatDateTime(payment.getAuthorizeDate()));

		return billingPayExecRq;
	}
}
