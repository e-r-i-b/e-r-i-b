package com.rssl.phizicgate.esberibgate.documents.senders;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.documents.AbstractTransfer;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.AccountClosingPaymentToAccount;
import com.rssl.phizic.gate.payments.AccountClosingPaymentToCard;
import com.rssl.phizic.gate.payments.ClientAccountsTransfer;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.esberibgate.ws.generated.OperName_Type;
import com.rssl.phizicgate.esberibgate.ws.generated.XferInfo_Type;

/**
 * @author Balovtsev
 * @created 05.03.2011
 * @ $Author$
 * @ $Revision$
 */

public class AccountClosingPaymentToCardSender extends AccountClosingPaymentSender
{
	public AccountClosingPaymentToCardSender(GateFactory factory) throws GateException
	{
		super(factory);
	}

	protected XferInfo_Type createBody(AbstractTransfer document) throws GateException, GateLogicException
	{
		XferInfo_Type xferInfo_type = new XferInfo_Type();
		AccountClosingPaymentToCard transfer = (AccountClosingPaymentToCard) document;
		Client owner = getBusinessOwner(transfer);

		if( !StringHelper.isEmpty( transfer.getReceiverAccount()) )
		{
			Card card = getCard(owner, transfer.getReceiverAccount(), document.getOffice());
			xferInfo_type = createConversationBody(transfer);
			xferInfo_type.setCardAcctIdTo(paymentsRequestHelper.createCardAcctId(card, owner, transfer.getReceiverCardExpireDate(), true, false));
		}

		Account account = getAccount(((AccountClosingPaymentToAccount)document).getChargeOffAccount(), document.getOffice());
		xferInfo_type.setDepAcctIdFrom(createDepAcctIdFrom(account, owner));
		appendCommonBody(document, xferInfo_type, true);
		return xferInfo_type;
	}

	public Currency getDestinationCurrency(AbstractTransfer document) throws GateException, GateLogicException
	{
		if (!(document instanceof AccountClosingPaymentToCard))
			throw new GateException("Неверный тип документа, должен быть - AccountClosingPaymentToCard.");

		ClientAccountsTransfer doc = (ClientAccountsTransfer) document;
		if ( StringHelper.isEmpty(doc.getReceiverAccount()) )
		{
			return null;
		}
		
		return doc.getDestinationCurrency();
	}

	protected OperName_Type getOperationName(AbstractTransfer document)
	{
		return OperName_Type.TDCC;
	}
}
