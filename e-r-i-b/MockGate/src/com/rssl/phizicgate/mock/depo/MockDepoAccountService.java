package com.rssl.phizicgate.mock.depo;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.NotRoundedMoney;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.depo.*;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.business.depo.MockDepoAccount;
import com.rssl.phizicgate.mock.clients.ClientImpl;
import com.rssl.phizicgate.mock.offices.OfficeImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mihaylov
 * @ created 18.08.2010
 * @ $Author$
 * @ $Revision$
 */

public class MockDepoAccountService extends AbstractService implements DepoAccountService
{
	public MockDepoAccountService(GateFactory factory)
	{
		super(factory);
	}

	public void register(Client client) throws GateException, GateLogicException
	{

	}

	public List<DepoAccount> getDepoAccounts(Client client) throws GateException, GateLogicException
	{
		List<DepoAccount> depoAccounts = new ArrayList<DepoAccount>();
		depoAccounts.add(createDepoAccount("1314242"));
		depoAccounts.add(createDepoAccount("22222233456"));
		return depoAccounts;
	}

	public GroupResult<String, DepoAccount> getDepoAccount(String... depoAccountId)
	{
		GroupResult<String,DepoAccount> res = new GroupResult<String,DepoAccount>();
		for(String accountId : depoAccountId)
		{
			res.putResult(accountId,createDepoAccount(accountId));
		}
		return res;
	}

	public DepoDebtInfo getDepoDebtInfo(DepoAccount depoAccount) throws GateException, GateLogicException
	{
		final CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
		Currency currencyRUB = currencyService.findByAlphabeticCode("RUB");

		List<DepoDebtItem> debts = new ArrayList<DepoDebtItem>();
		try
		{

			debts.add(new DepoDebtItemImpl("132","132",
							new Money(new BigDecimal("1230.50"),currencyRUB),
							new Money(new BigDecimal("147.00"),currencyRUB),
							DateHelper.parseCalendar("18.09.2010"),
							DateHelper.parseCalendar("01.09.2010"),
							DateHelper.parseCalendar("29.09.2010")));

			debts.add(new DepoDebtItemImpl("132","132",
							new Money(new BigDecimal("1230.50"),currencyRUB),
							new Money(new BigDecimal("147.00"),currencyRUB),
							DateHelper.parseCalendar("18.08.2010"),
							DateHelper.parseCalendar("01.08.2010"),
							DateHelper.parseCalendar("29.08.2010")));

			debts.add(new DepoDebtItemImpl("132","132",
							new Money(new BigDecimal("1230.50"),currencyRUB),
							new Money(new BigDecimal("147.00"),currencyRUB),
							DateHelper.parseCalendar("18.07.2010"),
							DateHelper.parseCalendar("01.07.2010"),
							DateHelper.parseCalendar("29.07.2010")));

			debts.add(new DepoDebtItemImpl("132","132",
							new Money(new BigDecimal("1230.50"),currencyRUB),
							new Money(new BigDecimal("147.00"),currencyRUB),
							DateHelper.parseCalendar("18.06.2010"),
							DateHelper.parseCalendar("01.06.2010"),
							DateHelper.parseCalendar("29.06.2010")));

			debts.add(new DepoDebtItemImpl("132","132",
							new Money(new BigDecimal("1230.50"),currencyRUB),
							new Money(new BigDecimal("147.00"),currencyRUB),
							DateHelper.parseCalendar("18.05.2010"),
							DateHelper.parseCalendar("01.05.2010"),
							DateHelper.parseCalendar("29.05.2010")));

			debts.add(new DepoDebtItemImpl("132","132",
							new Money(new BigDecimal("1230.50"),currencyRUB),
							new Money(new BigDecimal("147.00"),currencyRUB),
							DateHelper.parseCalendar("18.04.2010"),
							DateHelper.parseCalendar("01.04.2010"),
							DateHelper.parseCalendar("29.04.2010")));

			debts.add(new DepoDebtItemImpl("132","132",
							new Money(new BigDecimal("1230.50"),currencyRUB),
							new Money(new BigDecimal("147.00"),currencyRUB),
							DateHelper.parseCalendar("18.03.2010"),
							DateHelper.parseCalendar("01.03.2010"),
							DateHelper.parseCalendar("29.03.2010")));

			debts.add(new DepoDebtItemImpl("132","132",
							new Money(new BigDecimal("1230.50"),currencyRUB),
							new Money(new BigDecimal("147.00"),currencyRUB),
							DateHelper.parseCalendar("18.02.2010"),
							DateHelper.parseCalendar("01.02.2010"),
							DateHelper.parseCalendar("15.02.2010")));

			debts.add(new DepoDebtItemImpl("132","132",
							new Money(new BigDecimal("1230.50"),currencyRUB),
							new Money(new BigDecimal("147.00"),currencyRUB),
							DateHelper.parseCalendar("18.01.2010"),
							DateHelper.parseCalendar("01.01.2010"),
							DateHelper.parseCalendar("29.01.2010")));

			debts.add(new DepoDebtItemImpl("132","132",
							new Money(new BigDecimal("1230.50"),currencyRUB),
							new Money(new BigDecimal("147.00"),currencyRUB),
							DateHelper.parseCalendar("18.12.2009"),
							DateHelper.parseCalendar("01.12.2009"),
							DateHelper.parseCalendar("31.12.2009")));

			debts.add(new DepoDebtItemImpl("132","132",
							new Money(new BigDecimal("1230.50"),currencyRUB),
							new Money(new BigDecimal("147.00"),currencyRUB),
							DateHelper.parseCalendar("18.11.2009"),
							DateHelper.parseCalendar("01.11.2009"),
							DateHelper.parseCalendar("29.11.2009")));

			debts.add(new DepoDebtItemImpl("132","132",
							new Money(new BigDecimal("1230.50"),currencyRUB),
							new Money(new BigDecimal("147.00"),currencyRUB),
							DateHelper.parseCalendar("18.10.2009"),
							DateHelper.parseCalendar("01.10.2009"),
							DateHelper.parseCalendar("29.10.2009")));

			debts.add(new DepoDebtItemImpl("132","132",
							new Money(new BigDecimal("1230.50"),currencyRUB),
							new Money(new BigDecimal("147.00"),currencyRUB),
							DateHelper.parseCalendar("18.09.2009"),
							DateHelper.parseCalendar("01.09.2009"),
							DateHelper.parseCalendar("29.09.2009")));

			debts.add(new DepoDebtItemImpl("132","132",
							new Money(new BigDecimal("1230.50"),currencyRUB),
							new Money(new BigDecimal("147.00"),currencyRUB),
							DateHelper.parseCalendar("18.08.2009"),
							DateHelper.parseCalendar("01.08.2009"),
							DateHelper.parseCalendar("29.08.2009")));

			return new DepoDebtInfoImpl(new Money(new BigDecimal("51230.50"),currencyRUB),debts);
		}
		catch (Exception e)
		{
			return null;
		}
	}

	public GroupResult<DepoDebtItem, DepoDebtItemInfo> getDepoDebtItemInfo(DepoAccount depoAccount,DepoDebtItem... debtItem)
	{
		GroupResult<DepoDebtItem, DepoDebtItemInfo> res = new GroupResult<DepoDebtItem, DepoDebtItemInfo>();
		DepoDebtItemInfo debtInfo = new DepoDebtItemInfoImpl("049514608","АБАКАНСКОЕ ОСБ №8602","30101810500000000608","Депозитарий","111111111111","121212","42303810277123456789");
		for(DepoDebtItem debt: debtItem)
			res.putResult(debt,debtInfo);
		return res;
	}

	public DepoAccountPosition getDepoAccountPosition(DepoAccount depoAccount) throws GateException, GateLogicException
	{

		List<DepoAccountDivision> depoDivision = new ArrayList<DepoAccountDivision>();

		List<DepoAccountSecurity> baseSecurities = new ArrayList<DepoAccountSecurity>();
		/*Для корректного отображения позиции по счету депо необходимо указывать корректный код выпуска ценной бумаги*/
		/*Впоследствии название ценной бумаги будет браться по данному коду из справочника ценных бумаг*/
		final CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
		Currency currencyRUB = currencyService.findByAlphabeticCode("RUB");
		NotRoundedMoney amount = new NotRoundedMoney(new BigDecimal("100") ,currencyRUB);
		baseSecurities.add(new DepoAccountSecurityImpl(null,"1-04-01837",Long.valueOf(125),DepoAccountSecurityStorageMethod.open,"name", amount ,  "fsdfsd1e12"));
		baseSecurities.add(new DepoAccountSecurityImpl(null,"13003RMFS",Long.valueOf(12),DepoAccountSecurityStorageMethod.open,"name",amount, "213123d"));
		baseSecurities.add(new DepoAccountSecurityImpl(null,"1-07-00029-A-005",Long.valueOf(15),DepoAccountSecurityStorageMethod.closed,"name",amount, "asda23234"));
		baseSecurities.add(new DepoAccountSecurityImpl(null,"1-04-01837-A-365",Long.valueOf(37),DepoAccountSecurityStorageMethod.open,"name",new NotRoundedMoney(new BigDecimal("0.2") ,currencyRUB), "23sd233"));
		List<SecurityMarker> securityMarkerList = new ArrayList<SecurityMarker>();
		securityMarkerList.add(new SecurityMarkerImpl("c=34, H=26 000",Long.valueOf(6)));
		securityMarkerList.add(new SecurityMarkerImpl("c=39, H=234 000",Long.valueOf(24)));
		baseSecurities.add(new DepoAccountSecurityImpl(securityMarkerList,"1-04-01812",Long.valueOf(30),DepoAccountSecurityStorageMethod.markByNominal,"name",new NotRoundedMoney(new BigDecimal("1") ,currencyRUB),"1231231df"));

		DepoAccountDivision baseDepoDivision = new DepoAccountDivisionImpl("Базовый","223456",baseSecurities);

		List<DepoAccountSecurity> additionalSecurities = new ArrayList<DepoAccountSecurity>();
		additionalSecurities.add(new DepoAccountSecurityImpl(null,"1-04-01837",Long.valueOf(12),DepoAccountSecurityStorageMethod.open,"name",new NotRoundedMoney(new BigDecimal("34") ,currencyRUB), "1232dd12ed"));
		additionalSecurities.add(new DepoAccountSecurityImpl(null,"1-04-01812",Long.valueOf(43),DepoAccountSecurityStorageMethod.markByNominalAndCoupon,"name",new NotRoundedMoney(new BigDecimal("45") ,currencyRUB), "d4f3tgj6"));

		DepoAccountDivision additionalDepoDivision = new DepoAccountDivisionImpl("Дополнительный раздел","458798",additionalSecurities);

		depoDivision.add(baseDepoDivision);
		depoDivision.add(additionalDepoDivision);
		return new DepoAccountPositionImpl(depoDivision);
	}

	public GroupResult<DepoAccount, Client> getDepoAccountOwner(DepoAccount... depoAccount)
	{
		GroupResult<DepoAccount, Client> res = new GroupResult<DepoAccount, Client>();

		for(DepoAccount depo: depoAccount)
		{
			ClientImpl client = new ClientImpl();
			client.setFullName("Тестовый Клиент, владелец счета депо "+ depo.getAccountNumber());
			res.putResult(depo,client);
		}
		return res;
	}

	private DepoAccount createDepoAccount(String id)
	{
		try
		{
			final CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);

			Currency currencyRUB = currencyService.findByAlphabeticCode("RUB");
			DepoAccount depoAccount = new MockDepoAccount(id, DepoAccountState.open, id,"7788-2c",
					DateHelper.parseCalendar("17.12.2009"), new Money(new BigDecimal("1230.50"),currencyRUB),true,getMockOffice());
			return depoAccount;
		}
		catch (Exception e)
		{
			return null;
		}
	}

	private Office getMockOffice() throws GateException, GateLogicException
	{
		Office office = new OfficeImpl();

		Map<String,Object> codeFields = new HashMap();
		codeFields.put("region", "040");
		codeFields.put("branch", "1555");
		codeFields.put("office", "64");

		office.buildCode(codeFields);
		office.setAddress("г. Вологда ул. Ленинградская 70");
		office.setName("ЦЕНТРАЛЬНОЕ ОСБ №8641/0111");

		return office;
	}
}
