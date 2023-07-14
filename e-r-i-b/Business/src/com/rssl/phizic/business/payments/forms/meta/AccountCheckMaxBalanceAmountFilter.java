package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.doc.HandlerFilterBase;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.GateExecutableDocument;
import com.rssl.phizic.business.documents.InternalTransfer;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.payments.AccountClosingPayment;

/**
 * @author: sergunin
 * @ created: 10.01.2014
 * @ $Author$
 * @ $Revision$
 */
public class AccountCheckMaxBalanceAmountFilter extends HandlerFilterBase
{
	public boolean isEnabled(StateObject stateObject) throws DocumentException
	{
		if (stateObject instanceof AccountClosingPayment)
			return true;

		if(stateObject instanceof InternalTransfer) {
			GateExecutableDocument document = (GateExecutableDocument) stateObject;
			Class<? extends GateDocument> documentType = document.asGateDocument().getType();
			if (documentType.getName().endsWith("ClientAccountsTransfer"))
				return true;
			if (documentType.getName().endsWith("CardToAccountTransfer"))
				return true;
			if (documentType.getName().endsWith("IMAToAccountTransfer"))
				return true;
		}

		return false;
	}
}
