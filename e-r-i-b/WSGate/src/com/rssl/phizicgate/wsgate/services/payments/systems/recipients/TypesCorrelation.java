package com.rssl.phizicgate.wsgate.services.payments.systems.recipients;

import java.util.Map;
import java.util.HashMap;

/**
 * @author akrenev
 * @ created 29.12.2009
 * @ $Author$
 * @ $Revision$
 */
public class TypesCorrelation
{
	public static final Map<Class, Class> types= new HashMap<Class,Class>();

	static
	{
		types.put(com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.ResidentBank.class, com.rssl.phizic.gate.dictionaries.ResidentBank.class);
		types.put(com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Billing.class, com.rssl.phizgate.common.routable.BillingImpl.class);
		types.put(com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Field.class, com.rssl.phizgate.common.payments.systems.recipients.CommonField.class);
		types.put(com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Code.class, com.rssl.phizicgate.wsgate.services.types.CodeImpl.class);
		types.put(com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Currency.class, com.rssl.phizic.business.dictionaries.currencies.CurrencyImpl.class);
		types.put(com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.DebtImpl.class, com.rssl.phizgate.common.payments.systems.recipients.DebtImpl.class);
		types.put(com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.DebtRowImpl.class, com.rssl.phizgate.common.payments.systems.recipients.DebtRowImpl.class);
		types.put(com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Money.class, com.rssl.phizic.common.types.Money.class);
		types.put(com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Service.class, com.rssl.phizgate.common.payments.systems.recipients.ServiceImpl.class);
		types.put(com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.FieldValidationRule.class, com.rssl.phizgate.common.payments.systems.recipients.FieldValidationRuleImpl.class);
		types.put(com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Pair.class, com.rssl.phizic.common.types.transmiters.Pair.class);
		types.put(com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Recipient.class, com.rssl.phizgate.common.routable.RecipientImpl.class);
		types.put(com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Client.class, com.rssl.phizicgate.wsgate.services.types.ClientImpl.class);
		types.put(com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Address.class, com.rssl.phizicgate.wsgate.services.types.AddressImpl.class);
		types.put(com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.ClientDocument.class, com.rssl.phizicgate.wsgate.services.types.ClientDocumentImpl.class);
		types.put(com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.ListValue.class, com.rssl.phizic.gate.payments.systems.recipients.ListValue.class);

		types.put(com.rssl.phizic.common.types.transmiters.Pair.class, com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Pair.class);
		types.put(com.rssl.phizic.common.types.Money.class, com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Money.class);
		types.put(com.rssl.phizic.gate.dictionaries.officies.Code.class, com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Code.class);
		types.put(com.rssl.phizic.common.types.Currency.class, com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Currency.class);
		types.put(com.rssl.phizic.gate.payments.systems.recipients.Debt.class, com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.DebtImpl.class);
		types.put(com.rssl.phizic.gate.payments.systems.recipients.DebtRow.class, com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.DebtRowImpl.class);
		types.put(com.rssl.phizic.gate.dictionaries.ResidentBank.class, com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.ResidentBank.class);
		types.put(com.rssl.phizic.gate.dictionaries.billing.Billing.class, com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Billing.class);
		types.put(com.rssl.phizic.gate.payments.systems.recipients.Field.class, com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Field.class);
		types.put(com.rssl.phizic.gate.payments.systems.recipients.ListValue.class, com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.ListValue.class);
		types.put(com.rssl.phizic.gate.payments.systems.recipients.Service.class, com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Service.class);
		types.put(com.rssl.phizic.gate.payments.systems.recipients.Recipient.class, com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Recipient.class);
		types.put(com.rssl.phizic.gate.payments.systems.recipients.FieldDataType.class, null);
		types.put(com.rssl.phizic.common.types.BusinessFieldSubType.class, null);
		types.put(com.rssl.phizic.gate.payments.systems.recipients.CalendarFieldPeriod.class, null);
		types.put(com.rssl.phizic.gate.clients.Client.class, com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Client.class);
		types.put(com.rssl.phizic.gate.clients.Address.class, com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Address.class);
		types.put(com.rssl.phizic.gate.clients.ClientDocument.class, com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.ClientDocument.class);
		types.put(com.rssl.phizic.common.types.client.ClientDocumentType.class, null);
		types.put(com.rssl.phizic.gate.clients.ClientState.class, null);
		types.put(com.rssl.phizic.gate.clients.ClientStateCategory.class, null);
		types.put(com.rssl.phizic.common.types.SegmentCodeType.class, null);
	}
}