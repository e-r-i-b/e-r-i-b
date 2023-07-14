package com.rssl.phizic.web.gate.services.bankroll;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: Pakhomova
 * @created: 09.06.2009
 * @ $Author$
 * @ $Revision$
 */
public class BankrollTypesCorrelation
{
	public static final Map<Class, Class> types = new HashMap<Class,Class>();


	static
	{
		types.put(com.rssl.phizic.web.gate.services.bankroll.generated.Office.class, com.rssl.phizgate.common.services.types.OfficeImpl.class);
		types.put(com.rssl.phizic.web.gate.services.bankroll.generated.Code.class, com.rssl.phizgate.common.services.types.CodeImpl.class);
    	types.put(com.rssl.phizic.web.gate.services.bankroll.generated.Client.class, com.rssl.phizic.web.gate.services.types.ClientImpl.class);
		types.put(com.rssl.phizic.web.gate.services.bankroll.generated.Address.class, com.rssl.phizic.web.gate.services.types.AddressImpl.class);
		types.put(com.rssl.phizic.web.gate.services.bankroll.generated.ClientDocument.class, com.rssl.phizic.web.gate.services.types.ClientDocumentImpl.class);
		types.put(com.rssl.phizic.web.gate.services.bankroll.generated.Trustee.class, com.rssl.phizic.web.gate.services.types.TrusteeImpl.class);
		types.put(com.rssl.phizic.web.gate.services.bankroll.generated.Account.class, com.rssl.phizic.web.gate.services.types.AccountImpl.class);
		types.put(com.rssl.phizic.web.gate.services.bankroll.generated.Card.class, com.rssl.phizic.web.gate.services.types.CardImpl.class);
		types.put(com.rssl.phizic.web.gate.services.bankroll.generated.CardOperation.class, com.rssl.phizic.web.gate.services.types.CardOperationImpl.class);
		types.put(com.rssl.phizic.web.gate.services.bankroll.generated.Currency.class, com.rssl.phizic.web.gate.services.types.CurrencyImpl.class);
		types.put(com.rssl.phizic.web.gate.services.bankroll.generated.Money.class, com.rssl.phizic.common.types.Money.class);
		types.put(com.rssl.phizic.web.gate.services.bankroll.generated.IKFLException.class, com.rssl.phizic.common.types.exceptions.IKFLException.class);
		types.put(com.rssl.phizic.web.gate.services.bankroll.generated.Throwable.class, java.lang.Throwable.class);

		types.put(com.rssl.phizic.common.types.transmiters.Pair.class, com.rssl.phizic.web.gate.services.bankroll.generated.Pair.class);
		types.put(com.rssl.phizic.web.gate.services.bankroll.generated.Pair.class, com.rssl.phizic.common.types.transmiters.Pair.class);
		types.put(com.rssl.phizic.web.gate.services.bankroll.generated.ClientState.class, com.rssl.phizic.gate.clients.ClientState.class);

		types.put(com.rssl.phizic.gate.dictionaries.officies.Office.class, com.rssl.phizic.web.gate.services.bankroll.generated.Office.class);
		types.put(com.rssl.phizic.gate.dictionaries.officies.Code.class, com.rssl.phizic.web.gate.services.bankroll.generated.Code.class);

		types.put(com.rssl.phizic.gate.clients.Client.class, com.rssl.phizic.web.gate.services.bankroll.generated.Client.class);
		types.put(com.rssl.phizic.gate.clients.Address.class, com.rssl.phizic.web.gate.services.bankroll.generated.Address.class);
		types.put(com.rssl.phizic.gate.clients.ClientDocument.class, com.rssl.phizic.web.gate.services.bankroll.generated.ClientDocument.class);
		types.put(com.rssl.phizic.common.types.client.ClientDocumentType.class, null);
		types.put(com.rssl.phizic.gate.clients.ClientStateCategory.class, null); //прописан в BeanFormatterMap
		types.put(com.rssl.phizic.gate.bankroll.CardState.class, null);
		types.put(com.rssl.phizic.gate.bankroll.CardType.class, null);
		types.put(com.rssl.phizic.gate.bankroll.CardLevel.class, null);
		types.put(com.rssl.phizic.gate.bankroll.ReportDeliveryType.class, null);
		types.put(com.rssl.phizic.gate.bankroll.ReportDeliveryLanguage.class, null);
		types.put(com.rssl.phizic.gate.bankroll.CardBonusSign.class, null);
		types.put(com.rssl.phizic.gate.bankroll.AdditionalCardType.class, null);

		types.put(com.rssl.phizic.gate.bankroll.Card.class, com.rssl.phizic.web.gate.services.bankroll.generated.Card.class);
		types.put(com.rssl.phizic.gate.bankroll.Account.class, com.rssl.phizic.web.gate.services.bankroll.generated.Account.class);
		types.put(com.rssl.phizic.gate.bankroll.AccountState.class, null);
		types.put(com.rssl.phizic.gate.bankroll.Trustee.class, com.rssl.phizic.web.gate.services.bankroll.generated.Trustee.class);
		types.put(com.rssl.phizic.gate.bankroll.CardType.class, null);
		types.put(com.rssl.phizic.common.types.Currency.class, com.rssl.phizic.web.gate.services.bankroll.generated.Currency.class);
		types.put(com.rssl.phizic.common.types.Money.class, com.rssl.phizic.web.gate.services.bankroll.generated.Money.class);

		types.put(com.rssl.phizic.gate.bankroll.AccountAbstract.class, com.rssl.phizic.web.gate.services.bankroll.generated.AccountAbstract.class);
		types.put(com.rssl.phizic.gate.bankroll.AccountTransaction.class, com.rssl.phizic.web.gate.services.bankroll.generated.AccountTransaction.class);
		types.put(com.rssl.phizic.gate.bankroll.CardUseData.class, com.rssl.phizic.web.gate.services.bankroll.generated.CardUseData.class);

		types.put(com.rssl.phizic.gate.bankroll.CardAbstract.class, com.rssl.phizic.web.gate.services.bankroll.generated.CardAbstract.class);
		types.put(com.rssl.phizic.gate.bankroll.CardOperation.class, com.rssl.phizic.web.gate.services.bankroll.generated.CardOperation.class);
		types.put(com.rssl.phizic.common.types.transmiters.GroupResult.class, com.rssl.phizic.web.gate.services.bankroll.generated.GroupResult.class);
		types.put(com.rssl.phizic.common.types.exceptions.IKFLException.class, com.rssl.phizic.web.gate.services.bankroll.generated.IKFLException.class);
		types.put(java.rmi.RemoteException.class, com.rssl.phizic.web.gate.services.bankroll.generated.Throwable.class);
		types.put(com.rssl.phizic.gate.clients.ClientState.class, com.rssl.phizic.web.gate.services.bankroll.generated.ClientState.class);
		types.put(java.lang.Class.class, null); //прописан в BeanFormatterMap
		types.put(com.rssl.phizic.errors.ErrorType.class, null); //прописан в BeanFormatterMap
		types.put(com.rssl.phizic.errors.ErrorSystem.class, null); //прописан в BeanFormatterMap
		types.put(com.rssl.phizic.common.types.SegmentCodeType.class, null);
	}

}
