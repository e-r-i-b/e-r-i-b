package com.rssl.phizicgate.iqwave.loyalty.mock;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.loyalty.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author gladishev
 * @ created 02.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class MockLoyaltyProgramServiceImpl extends AbstractService implements LoyaltyProgramService
{
	public MockLoyaltyProgramServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public LoyaltyProgram getClientLoyaltyProgram(Card card) throws GateException, GateLogicException
	{
		LoyaltyProgramImpl pr = new LoyaltyProgramImpl();
		pr.setExternalId("12344567890");
		pr.setBalance(new BigDecimal("11.12"));
		pr.setPhone("9212345678");
		pr.setEmail("example1@mail.ru");

		return pr;
	}

	public LoyaltyProgram getClientLoyaltyProgram(String externalId) throws GateException, GateLogicException
	{
		LoyaltyProgramImpl pr = new LoyaltyProgramImpl();
		pr.setExternalId("12344567890");
		pr.setBalance(new BigDecimal("11.12"));
		pr.setPhone("9212345678");
		pr.setEmail("example2@mail.ru");

		return pr;
	}

	public List<LoyaltyProgramOperation> getLoyaltyOperationInfo(LoyaltyProgram loyaltyProgram) throws GateException, GateLogicException
	{
		List<LoyaltyProgramOperation> result = new ArrayList<LoyaltyProgramOperation>();
		result.add(createLoyaltyProgramOperation("33.11", "22.22", "Hello, World!!!", LoyaltyOperationKind.credit));
		result.add(createLoyaltyProgramOperation("55", "44", "Tratata!!!", LoyaltyOperationKind.debit));
		return result;
	}

	public List<LoyaltyOffer> getLoyaltyOffers(LoyaltyProgram loyaltyProgram) throws GateException, GateLogicException
	{
		List<LoyaltyOffer> result = new ArrayList<LoyaltyOffer>();
		result.add(new LoyaltyOfferImpl("Раз"));
		result.add(new LoyaltyOfferImpl("Два"));
		result.add(new LoyaltyOfferImpl("Три"));

		return result;
	}

	private LoyaltyProgramOperation createLoyaltyProgramOperation(String moneyBalance, String operationalBalance, String operationInfo, LoyaltyOperationKind kind)
	{
		LoyaltyProgramOperationImpl operation = new LoyaltyProgramOperationImpl();
		CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
		try
		{
			operation.setMoneyOperationalBalance(new Money(new BigDecimal(moneyBalance), currencyService.findByAlphabeticCode("RUB")));
		}
		catch (GateException ignore)
		{
			//
		}
		operation.setOperationalBalance(new BigDecimal(operationalBalance));
		operation.setOperationDate(Calendar.getInstance());
		operation.setOperationInfo(operationInfo);
		operation.setOperationKind(kind);

		return operation;
	}
}
