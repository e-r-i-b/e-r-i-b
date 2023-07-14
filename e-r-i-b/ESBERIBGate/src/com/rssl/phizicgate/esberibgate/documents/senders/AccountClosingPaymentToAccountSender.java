package com.rssl.phizicgate.esberibgate.documents.senders;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.documents.AbstractTransfer;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.AccountClosingPaymentToAccount;
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

public class AccountClosingPaymentToAccountSender extends AccountClosingPaymentSender
{
	public AccountClosingPaymentToAccountSender(GateFactory factory) throws GateException
	{
		super(factory);
	}

	protected XferInfo_Type createBody(AbstractTransfer document) throws GateException, GateLogicException
	{
		XferInfo_Type info_type = new XferInfo_Type();

		if( !StringHelper.isEmpty( ((ClientAccountsTransfer) document).getReceiverAccount()) )
		{
			info_type = super.createBody(document);
		}
		else
	    {
			Account account = getAccount(((AccountClosingPaymentToAccount)document).getChargeOffAccount(), document.getOffice());
			info_type.setDepAcctIdFrom(createDepAcctIdFrom(account, getBusinessOwner(document)));
		}

		appendCommonBody(document, info_type, true);
		return info_type;
	}

	public Currency getDestinationCurrency(AbstractTransfer document) throws GateException, GateLogicException
	{
		if (!(document instanceof AccountClosingPaymentToAccount))
		{
			throw new GateException("Неверный тип документа, должен быть - AccountClosingPaymentToAccount.");
		}

		ClientAccountsTransfer doc = (ClientAccountsTransfer) document;
		if (StringHelper.isEmpty( doc.getReceiverAccount() ))
		{
			return null;
		}
		
		return doc.getDestinationCurrency();
	}

	protected OperName_Type getOperationName(AbstractTransfer document)
	{
		return OperName_Type.TDDC;
	}
}
