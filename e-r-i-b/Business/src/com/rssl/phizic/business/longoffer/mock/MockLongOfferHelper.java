package com.rssl.phizic.business.longoffer.mock;

import com.rssl.phizic.business.documents.payments.mock.*;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.payments.*;
import com.rssl.phizic.gate.payments.systems.AccountPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;

import java.util.HashMap;
import java.util.Map;

/**
 * @author osminin
 * @ created 24.10.2010
 * @ $Author$
 * @ $Revision$
 */
public class MockLongOfferHelper
{
	private static final Map<Class<? extends GateDocument>, MockAbstractTransfer> mockTypes = new HashMap<Class<? extends GateDocument>, MockAbstractTransfer>();

	static
	{
		mockTypes.put(AccountPaymentSystemPayment.class,    new MockPaymentSystemPayment());
		mockTypes.put(CardPaymentSystemPayment.class,       new MockPaymentSystemPayment());
		mockTypes.put(ClientAccountsTransfer.class,         new MockClientAccountsTransfer());
		mockTypes.put(AccountToCardTransfer.class,          new MockAccountToCardTransfer());
		mockTypes.put(CardToAccountTransfer.class,          new MockCardToAccountTransfer());
		mockTypes.put(InternalCardsTransfer.class,          new MockCardsTransfer());
		mockTypes.put(CardJurIntraBankTransfer.class,       new MockCardJurIntraBankTransfer());
		mockTypes.put(CardJurTransfer.class,                new MockCardJurTransfer());
		mockTypes.put(AccountJurIntraBankTransfer.class,    new MockAccountJurIntraBankTransfer());
		mockTypes.put(AccountJurTransfer.class,             new MockAccountJurTransfer());
		mockTypes.put(CardIntraBankPayment.class,           new MockCardIntraBankPayment());
		mockTypes.put(CardRUSPayment.class,                 new MockCardRUSPayment());
		mockTypes.put(AccountIntraBankPayment.class,        new MockAccountIntraBankPayment());
		mockTypes.put(AccountRUSPayment.class,              new MockAccountRUSPayment());
		mockTypes.put(LoanTransfer.class,                   new MockLoanTransfer());
	}

	public static MockAbstractTransfer getMockInfo(Class<? extends GateDocument> type)
	{
		return mockTypes.get(type);
	}
}
