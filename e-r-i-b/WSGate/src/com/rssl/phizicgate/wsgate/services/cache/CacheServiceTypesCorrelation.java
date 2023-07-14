package com.rssl.phizicgate.wsgate.services.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * @author osminin
 * @ created 04.09.2009
 * @ $Author$
 * @ $Revision$
 */

public class CacheServiceTypesCorrelation
{
	public static final Map<Class, Class> toGeneratedTypes = new HashMap<Class, Class>();
	public static final Map<Class, Class> toGateTypes = new HashMap<Class, Class>();

	static
	{
		toGeneratedTypes.put(com.rssl.phizic.gate.clients.Client.class, com.rssl.phizicgate.wsgate.services.cache.generated.Client.class);
		toGeneratedTypes.put(com.rssl.phizic.gate.clients.Address.class, com.rssl.phizicgate.wsgate.services.cache.generated.Address.class);
		toGeneratedTypes.put(com.rssl.phizic.gate.bankroll.Account.class, com.rssl.phizicgate.wsgate.services.cache.generated.Account.class);
		toGeneratedTypes.put(com.rssl.phizic.common.types.Currency.class, com.rssl.phizicgate.wsgate.services.cache.generated.Currency.class);
		toGeneratedTypes.put(com.rssl.phizic.gate.bankroll.Card.class, com.rssl.phizicgate.wsgate.services.cache.generated.Card.class);
		toGeneratedTypes.put(com.rssl.phizic.gate.dictionaries.officies.Code.class, com.rssl.phizicgate.wsgate.services.cache.generated.Code.class);
		toGeneratedTypes.put(com.rssl.phizic.gate.deposit.Deposit.class, com.rssl.phizicgate.wsgate.services.cache.generated.Deposit.class);
		toGeneratedTypes.put(com.rssl.phizic.common.types.Money.class, com.rssl.phizicgate.wsgate.services.cache.generated.Money.class);
		toGeneratedTypes.put(com.rssl.phizic.gate.dictionaries.officies.Office.class, com.rssl.phizicgate.wsgate.services.cache.generated.Office.class);
		toGeneratedTypes.put(com.rssl.phizic.common.types.CurrencyRate.class, com.rssl.phizicgate.wsgate.services.cache.generated.CurrencyRate.class);
		toGeneratedTypes.put(com.rssl.phizic.gate.ima.IMAccount.class, com.rssl.phizicgate.wsgate.services.cache.generated.IMAccount.class);
		toGeneratedTypes.put(com.rssl.phizic.gate.loans.Loan.class, com.rssl.phizicgate.wsgate.services.cache.generated.Loan.class);
		toGeneratedTypes.put(com.rssl.phizic.gate.depo.DepoAccount.class, com.rssl.phizicgate.wsgate.services.cache.generated.DepoAccount.class);
		toGeneratedTypes.put(com.rssl.phizic.gate.longoffer.LongOffer.class, com.rssl.phizicgate.wsgate.services.cache.generated.LongOffer.class);
		toGeneratedTypes.put(com.rssl.phizic.gate.insurance.InsuranceApp.class, com.rssl.phizicgate.wsgate.services.cache.generated.InsuranceApp.class);
		toGeneratedTypes.put(com.rssl.phizic.gate.insurance.PolicyDetails.class, com.rssl.phizicgate.wsgate.services.cache.generated.PolicyDetails.class);
		toGeneratedTypes.put(com.rssl.phizic.gate.security.SecurityAccount.class, com.rssl.phizicgate.wsgate.services.cache.generated.SecurityAccount.class);
		toGeneratedTypes.put(com.rssl.phizic.gate.clients.ClientState.class, com.rssl.phizicgate.wsgate.services.cache.generated.ClientState.class);
		toGeneratedTypes.put(com.rssl.phizic.gate.bankroll.AdditionalCardType.class, null);
		toGeneratedTypes.put(com.rssl.phizic.gate.bankroll.CardType.class, null);
		toGeneratedTypes.put(com.rssl.phizic.gate.bankroll.CardLevel.class, null);
		toGeneratedTypes.put(com.rssl.phizic.gate.bankroll.CardBonusSign.class, null);
		toGeneratedTypes.put(com.rssl.phizic.gate.bankroll.CardState.class, null);
		toGeneratedTypes.put(com.rssl.phizic.gate.bankroll.ReportDeliveryType.class, null);
		toGeneratedTypes.put(com.rssl.phizic.gate.bankroll.ReportDeliveryLanguage.class, null);
		toGeneratedTypes.put(com.rssl.phizic.gate.bankroll.AccountState.class, null);
		toGeneratedTypes.put(com.rssl.phizic.gate.deposit.DepositState.class, null);
		toGeneratedTypes.put(com.rssl.phizic.gate.depo.DepoAccountState.class, null);
		toGeneratedTypes.put(com.rssl.phizic.gate.ima.IMAccountState.class, null);
		toGeneratedTypes.put(com.rssl.phizic.gate.loans.LoanState.class, null);
		toGeneratedTypes.put(com.rssl.phizic.gate.loans.PersonLoanRole.class, null);
		toGeneratedTypes.put(com.rssl.phizic.gate.longoffer.ExecutionEventType.class, null);
		toGeneratedTypes.put(com.rssl.phizic.gate.longoffer.SumType.class, null);
		toGeneratedTypes.put(java.lang.Class.class, null);
		toGeneratedTypes.put(com.rssl.phizic.common.types.CurrencyRateType.class, null);
		toGeneratedTypes.put(com.rssl.phizic.common.types.DynamicExchangeRate.class, null);
		toGeneratedTypes.put(com.rssl.phizic.gate.longoffer.autopayment.AutoPaymentStatus.class, null);
		toGeneratedTypes.put(com.rssl.phizic.gate.longoffer.autopayment.ScheduleItemState.class, null);
		toGeneratedTypes.put(com.rssl.phizic.gate.clients.ClientStateCategory.class, null); //прописан в BeanFormatterMap
		toGeneratedTypes.put(com.rssl.phizic.common.types.SegmentCodeType.class, null);
		toGeneratedTypes.put(com.rssl.phizic.gate.longoffer.TotalAmountPeriod.class, null);

		toGateTypes.put(com.rssl.phizicgate.wsgate.services.cache.generated.Client.class, com.rssl.phizicgate.wsgate.services.types.ClientImpl.class);
		toGateTypes.put(com.rssl.phizicgate.wsgate.services.cache.generated.Address.class, com.rssl.phizicgate.wsgate.services.types.AddressImpl.class);
		toGateTypes.put(com.rssl.phizicgate.wsgate.services.cache.generated.Account.class, com.rssl.phizicgate.wsgate.services.types.AccountImpl.class);
		toGateTypes.put(com.rssl.phizicgate.wsgate.services.cache.generated.Currency.class, com.rssl.phizicgate.wsgate.services.types.CurrencyImpl.class);
		toGateTypes.put(com.rssl.phizicgate.wsgate.services.cache.generated.Card.class, com.rssl.phizicgate.wsgate.services.types.CardImpl.class);
		toGateTypes.put(com.rssl.phizicgate.wsgate.services.cache.generated.Code.class, com.rssl.phizicgate.wsgate.services.types.CodeImpl.class);
		toGateTypes.put(com.rssl.phizicgate.wsgate.services.cache.generated.Deposit.class, com.rssl.phizicgate.wsgate.services.types.DepositImpl.class);
		toGateTypes.put(com.rssl.phizicgate.wsgate.services.cache.generated.Money.class, com.rssl.phizic.common.types.Money.class);
		toGateTypes.put(com.rssl.phizicgate.wsgate.services.cache.generated.Office.class, com.rssl.phizicgate.wsgate.services.types.OfficeImpl.class);
		toGateTypes.put(com.rssl.phizicgate.wsgate.services.cache.generated.CurrencyRate.class, com.rssl.phizic.common.types.CurrencyRate.class);
		toGateTypes.put(com.rssl.phizicgate.wsgate.services.cache.generated.DepoAccount.class, com.rssl.phizicgate.wsgate.services.types.DepoAccountImpl.class);
		toGateTypes.put(com.rssl.phizicgate.wsgate.services.cache.generated.IMAccount.class, com.rssl.phizicgate.wsgate.services.types.IMAccountImpl.class);
		toGateTypes.put(com.rssl.phizicgate.wsgate.services.cache.generated.Loan.class, com.rssl.phizicgate.wsgate.services.types.LoanImpl.class);
		toGateTypes.put(com.rssl.phizicgate.wsgate.services.cache.generated.LongOffer.class, com.rssl.phizicgate.wsgate.services.types.LongOfferImpl.class);
		toGateTypes.put(com.rssl.phizicgate.wsgate.services.cache.generated.ClientState.class, com.rssl.phizic.gate.clients.ClientState.class);
		toGateTypes.put(com.rssl.phizicgate.wsgate.services.cache.generated.InsuranceApp.class, com.rssl.phizicgate.wsgate.services.types.InsuranceAppImpl.class);
		toGateTypes.put(com.rssl.phizicgate.wsgate.services.cache.generated.PolicyDetails.class, com.rssl.phizicgate.wsgate.services.types.PolicyDetailsImpl.class);
		toGateTypes.put(com.rssl.phizicgate.wsgate.services.cache.generated.SecurityAccount.class, com.rssl.phizicgate.wsgate.services.types.SecurityAccountImpl.class);
	}
}