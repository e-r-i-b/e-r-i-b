package com.rssl.phizicgate.esberibgate.documents.senders;

import com.rssl.phizgate.basket.BasketInvoiceHelper;
import com.rssl.phizgate.basket.generated.AcceptBillBasketExecuteRq;
import com.rssl.phizgate.common.payments.documents.AbstractDocumentSenderBase;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.WithdrawDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.basket.InvoiceAcceptPayment;
import com.rssl.phizicgate.esberibgate.basket.BasketRequestHelper;

/**
 * @author osminin
 * @ created 09.06.14
 * @ $Author$
 * @ $Revision$
 *
 * Сендер оплаты инвойса. Сообщение об акцепте задолженности отпраляется в очередь, которую слушает шина
 */
public class InvoiceAcceptSender extends AbstractDocumentSenderBase
{
	private BasketRequestHelper basketRequestHelper;

	/**
	 * ctor
	 * @param factory фабрика
	 */
	public InvoiceAcceptSender(GateFactory factory)
	{
		super(factory);
		basketRequestHelper = new BasketRequestHelper(factory);
	}

	public void send(GateDocument document) throws GateException, GateLogicException
	{
		if (!InvoiceAcceptPayment.class.isAssignableFrom(document.getType()))
		{
			throw new GateException("Ожидается InvoiceAcceptPayment");
		}
		try
		{
			InvoiceAcceptPayment payment = (InvoiceAcceptPayment) document;
			AcceptBillBasketExecuteRq request = basketRequestHelper.createAutoPayAcceptRequest(payment);
			BasketInvoiceHelper.sendAcceptBillBasketExecuteRq(request);
			payment.setExternalId(request.getRqUID());
		}
		catch (GateException e)
		{
			throw e;
		}
		catch (GateLogicException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public void repeatSend(GateDocument document) throws GateException, GateLogicException
	{
		//Не поддерживается
	}

	public void prepare(GateDocument document) throws GateException, GateLogicException
	{
		//Не поддерживается
	}

	public void rollback(WithdrawDocument document) throws GateException, GateLogicException
	{
		//Не поддерживается
	}

	public void confirm(GateDocument document) throws GateException, GateLogicException
	{
		//Не поддерживается
	}

	public void validate(GateDocument document) throws GateException, GateLogicException
	{
		//Не поддерживается
	}
}
