package com.rssl.phizicgate.wsgate.services.bankroll;

import java.util.Map;
import java.util.HashMap;

/**
 * @author: Pakhomova
 * @created: 09.06.2009
 * @ $Author$
 * @ $Revision$
 */
public class BankrollTypesCorrelation
{
	public static final Map<Class, Class> types  = new HashMap<Class,Class>();

	static
	{
		types.put(com.rssl.phizic.gate.clients.Client.class, com.rssl.phizicgate.wsgate.services.bankroll.generated.Client.class);
		types.put(com.rssl.phizic.gate.clients.Address.class, com.rssl.phizicgate.wsgate.services.bankroll.generated.Address.class);
		types.put(com.rssl.phizic.gate.clients.ClientDocument.class, com.rssl.phizicgate.wsgate.services.bankroll.generated.ClientDocument.class);
		types.put(com.rssl.phizic.common.types.client.ClientDocumentType.class, null);

		types.put(com.rssl.phizic.gate.dictionaries.officies.Office.class, com.rssl.phizicgate.wsgate.services.bankroll.generated.Office.class);
		types.put(com.rssl.phizic.gate.dictionaries.officies.Code.class, com.rssl.phizicgate.wsgate.services.bankroll.generated.Code.class);

		types.put(com.rssl.phizic.gate.bankroll.Card.class, com.rssl.phizicgate.wsgate.services.bankroll.generated.Card.class);
		types.put(com.rssl.phizic.gate.bankroll.Account.class, com.rssl.phizicgate.wsgate.services.bankroll.generated.Account.class);
		types.put(com.rssl.phizic.gate.bankroll.CardOperation.class, com.rssl.phizicgate.wsgate.services.bankroll.generated.CardOperation.class);
		types.put(com.rssl.phizic.gate.bankroll.Trustee.class, com.rssl.phizicgate.wsgate.services.bankroll.generated.Trustee.class);
		types.put(com.rssl.phizic.gate.clients.ClientState.class, com.rssl.phizicgate.wsgate.services.bankroll.generated.ClientState.class);
		types.put(com.rssl.phizic.gate.bankroll.CardType.class, null);
		types.put(com.rssl.phizic.gate.bankroll.CardLevel.class, null);
		types.put(com.rssl.phizic.gate.bankroll.CardBonusSign.class, null);
		types.put(com.rssl.phizic.gate.bankroll.CardState.class, null);
		types.put(com.rssl.phizic.gate.bankroll.ReportDeliveryType.class, null);
		types.put(com.rssl.phizic.gate.bankroll.ReportDeliveryLanguage.class, null);
		types.put(com.rssl.phizic.gate.bankroll.AccountState.class, null);
		types.put(com.rssl.phizic.gate.bankroll.AdditionalCardType.class, null);
		types.put(com.rssl.phizic.gate.clients.ClientStateCategory.class, null); //прописан в BeanFormatterMap
		types.put(com.rssl.phizic.common.types.SegmentCodeType.class, null);
		types.put(com.rssl.phizic.common.types.Currency.class, com.rssl.phizicgate.wsgate.services.bankroll.generated.Currency.class);
		types.put(com.rssl.phizic.common.types.Money.class, com.rssl.phizicgate.wsgate.services.bankroll.generated.Money.class);

		types.put(com.rssl.phizic.common.types.transmiters.Pair.class, com.rssl.phizicgate.wsgate.services.bankroll.generated.Pair.class);
		types.put(com.rssl.phizicgate.wsgate.services.bankroll.generated.Pair.class, com.rssl.phizic.common.types.transmiters.Pair.class);
		           
        types.put(com.rssl.phizicgate.wsgate.services.bankroll.generated.Client.class, com.rssl.phizicgate.wsgate.services.types.ClientImpl.class);
        types.put(com.rssl.phizicgate.wsgate.services.bankroll.generated.Office.class, com.rssl.phizicgate.wsgate.services.types.OfficeImpl.class);
		types.put(com.rssl.phizicgate.wsgate.services.bankroll.generated.Code.class, com.rssl.phizicgate.wsgate.services.types.CodeImpl.class);
		types.put(com.rssl.phizicgate.wsgate.services.bankroll.generated.Address.class, com.rssl.phizicgate.wsgate.services.types.AddressImpl.class);
		types.put(com.rssl.phizicgate.wsgate.services.bankroll.generated.ClientDocument.class, com.rssl.phizicgate.wsgate.services.types.ClientDocumentImpl.class);
		types.put(com.rssl.phizicgate.wsgate.services.bankroll.generated.Trustee.class, com.rssl.phizicgate.wsgate.services.types.TrusteeImpl.class);
		types.put(com.rssl.phizicgate.wsgate.services.bankroll.generated.Account.class, com.rssl.phizicgate.wsgate.services.types.AccountImpl.class);
		types.put(com.rssl.phizicgate.wsgate.services.bankroll.generated.CardOperation.class, com.rssl.phizicgate.wsgate.services.types.CardOperationImpl.class);
		types.put(com.rssl.phizicgate.wsgate.services.bankroll.generated.Currency.class, com.rssl.phizicgate.wsgate.services.types.CurrencyImpl.class);
		types.put(com.rssl.phizicgate.wsgate.services.bankroll.generated.Money.class, com.rssl.phizic.common.types.Money.class);

		types.put(com.rssl.phizicgate.wsgate.services.bankroll.generated.Card.class, com.rssl.phizicgate.wsgate.services.types.CardImpl.class);

		types.put(com.rssl.phizicgate.wsgate.services.bankroll.generated.AccountAbstract.class, com.rssl.phizicgate.wsgate.services.types.AccountAbstractImpl.class);
		types.put(com.rssl.phizicgate.wsgate.services.bankroll.generated.AccountTransaction.class, com.rssl.phizicgate.wsgate.services.types.TransactionImpl.class);
		types.put(com.rssl.phizicgate.wsgate.services.bankroll.generated.CardUseData.class, com.rssl.phizicgate.wsgate.services.types.CardUseDataImpl.class);
		types.put(com.rssl.phizicgate.wsgate.services.bankroll.generated.CardAbstract.class, com.rssl.phizicgate.wsgate.services.types.CardAbstractImpl.class);
		types.put(com.rssl.phizicgate.wsgate.services.bankroll.generated.CardOperation.class, com.rssl.phizicgate.wsgate.services.types.TransactionImpl.class);
		types.put(com.rssl.phizicgate.wsgate.services.bankroll.generated.IKFLException.class, com.rssl.phizicgate.wsgate.services.types.IKFLExceptionImpl.class);
		types.put(com.rssl.phizicgate.wsgate.services.bankroll.generated.ClientState.class, com.rssl.phizic.gate.clients.ClientState.class);
	}
}
