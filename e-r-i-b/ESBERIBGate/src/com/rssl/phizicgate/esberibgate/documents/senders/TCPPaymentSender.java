package com.rssl.phizicgate.esberibgate.documents.senders;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.documents.AbstractCardTransfer;
import com.rssl.phizic.gate.documents.AbstractTransfer;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.AbstractRUSPayment;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.esberibgate.ws.generated.DepAcctId_Type;
import com.rssl.phizicgate.esberibgate.ws.generated.OperName_Type;
import com.rssl.phizicgate.esberibgate.ws.generated.XferInfo_Type;

/**
 * Ѕазовый сендер дл€ интерфейса TCP
 *
 * (интерфейс TCP - перевод денежных средств
 * с карты юридическому, физическому лицу)
 *
 * @author hudyakov
 * @ created 21.09.2010
 * @ $Author$
 * @ $Revision$
 */
public abstract class TCPPaymentSender extends AbstractRUSPaymentSender
{
	/**
	 * ctor
	 * @param factory фабрика
	 * @throws GateException
	 */
	public TCPPaymentSender(GateFactory factory) throws GateException
	{
		super(factory);
	}

	protected boolean needRates(AbstractTransfer transfer)
	{
		return false;
	}

	protected XferInfo_Type createBody(AbstractTransfer document) throws GateException, GateLogicException
	{
		if (!(document instanceof AbstractCardTransfer))
		{
			throw new GateException("Ќеверный тип документа, должен быть - AbstractCardTransfer.");
		}
		AbstractCardTransfer payment = (AbstractCardTransfer) document;

		XferInfo_Type xferInfo = super.createBody(document);
		//запол€нем источник списани€
		Client owner = getBusinessOwner(document);
		xferInfo.setCardAcctIdFrom(createCardAcctId(getCard(owner, payment.getChargeOffCard(), payment.getOffice()), owner, payment.getChargeOffCardExpireDate()));
		return xferInfo;
	}

	protected DepAcctId_Type createDepAcctId(AbstractRUSPayment document) throws GateLogicException, GateException
	{
		DepAcctId_Type result = new DepAcctId_Type();
		result.setAcctId(document.getReceiverAccount());
		ResidentBank residentBank = document.getReceiverBank();
		result.setBIC(residentBank.getBIC());

		String account = residentBank.getAccount();
		if (StringHelper.isNotEmpty(account))
		{
			result.setCorrAcctId(account);
		}
		return result;
	}

	public Currency getChargeOffCurrency(AbstractTransfer document) throws GateException, GateLogicException
	{
		if (!(document instanceof AbstractCardTransfer))
		{
			throw new GateException("Ќеверный тип документа, должен быть - AbstractCardTransfer.");
		}
		AbstractCardTransfer transfer = (AbstractCardTransfer) document;
		return transfer.getChargeOffCurrency();
	}

	protected OperName_Type getOperationName(AbstractTransfer document)
	{
		return OperName_Type.TCP;
	}
}
