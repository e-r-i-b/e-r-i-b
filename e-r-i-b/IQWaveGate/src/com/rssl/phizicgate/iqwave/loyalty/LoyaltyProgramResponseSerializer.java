package com.rssl.phizicgate.iqwave.loyalty;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.loyalty.LoyaltyOffer;
import com.rssl.phizic.gate.loyalty.LoyaltyOperationKind;
import com.rssl.phizic.gate.loyalty.LoyaltyProgram;
import com.rssl.phizic.gate.loyalty.LoyaltyProgramOperation;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.ForeachElementAction;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizicgate.iqwave.loyalty.mock.LoyaltyOfferImpl;
import com.rssl.phizicgate.iqwave.loyalty.mock.LoyaltyProgramImpl;
import com.rssl.phizicgate.iqwave.loyalty.mock.LoyaltyProgramOperationImpl;
import com.rssl.phizicgate.iqwave.messaging.Constants;
import org.w3c.dom.Element;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author gladishev
 * @ created 06.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class LoyaltyProgramResponseSerializer
{
	private static final int CENTS_SCALE = 2;
	private static Log log = PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE);

	/**
	 * Заполнение информации по программе лояльности
	 * @param element - ответ
	 * @param cardNumber - номер карты, посылаемый в запросе
	 * @return - информация по программе лояльности
	 */
	public LoyaltyProgram fillLoyaltyProgram(Element element, String cardNumber) throws GateException, GateLogicException
	{
		if (StringHelper.isEmpty(LoyaltyProgramRequestHelper.validateMessage(element, cardNumber)))
			return null;
		// если код возврата равен 0 и   LoyStatus == 0 то клиент не зарегистрирован(возвращаем null)
		if (XmlHelper.getSimpleElementValue(element, Constants.LOY_STATUS_TAG_NAME).equals("0"))
			 return  null;

		String loyBNS = XmlHelper.getSimpleElementValue(element, Constants.LOY_BNS_TAG_NAME);
		String loyTel = XmlHelper.getSimpleElementValue(element, Constants.LOY_TEL_TAG_NAME);
		String loyEmail = XmlHelper.getSimpleElementValue(element, Constants.LOY_EMAIL_TAG_NAME);

		LoyaltyProgramImpl program = new LoyaltyProgramImpl();
		program.setExternalId(cardNumber);
		if (!StringHelper.isEmpty(loyBNS))
		{
			program.setBalance(getBalance(loyBNS));
		}
		program.setPhone(loyTel);
		program.setEmail(loyEmail);

		return program;
	}

	/**
	 * Заполнение списка операций по программе лояльности
	 * @param element - ответ
	 * @param cardNumber - номер карты, посылаемый в запросе
	 * @return - список операций
	 */
	public List<LoyaltyProgramOperation> fillLoyaltyProgramOperationList(Element element, String cardNumber) throws GateException, GateLogicException
	{
		LoyaltyProgramRequestHelper.validateMessage(element, cardNumber);

		final List<LoyaltyProgramOperation> operations = new ArrayList<LoyaltyProgramOperation>();

		try
		{
			XmlHelper.foreach(element, "//LoyTransactions/Transaction", new ForeachElementAction()
			{
				public void execute(Element element) throws GateException, GateLogicException
				{
					LoyaltyProgramOperation loyaltyProgramOperation = fillOperation(element);
					if (loyaltyProgramOperation != null)
						operations.add(loyaltyProgramOperation);
				}
			});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}

		return operations;
	}

	/**
	 * Заполнение информации по операции
	 * @param element - ответ
	 * @return операция
	 */
	private LoyaltyProgramOperation fillOperation(Element element)
	{
		LoyaltyProgramOperationImpl operation = new LoyaltyProgramOperationImpl();
		String dateTime = XmlHelper.getSimpleElementValue(element, Constants.DATETIME_TAG_NAME);
		operation.setOperationDate(parseDate(dateTime));
		String loyaltyKind = XmlHelper.getSimpleElementValue(element, Constants.OPERATION_KIND_TAG_NAME);
		LoyaltyOperationKind kind;
		if (loyaltyKind.equals(Constants.LOY_KIND_1) || loyaltyKind.equals(Constants.LOY_KIND_4))
			kind = LoyaltyOperationKind.credit;
		else if (loyaltyKind.equals(Constants.LOY_KIND_2) || loyaltyKind.equals(Constants.LOY_KIND_3))
			kind = LoyaltyOperationKind.debit;
		else
			return null;

		operation.setOperationKind(kind);
		String cash = XmlHelper.getSimpleElementValue(element, Constants.CASH_TAG_NAME);
		if (!StringHelper.isEmpty(cash))
		{
			try
			{
				CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
				Currency currency = currencyService.findByAlphabeticCode("RUB");
				operation.setMoneyOperationalBalance(new Money(getBalance(cash), currency));
			}
			catch (GateException e)
			{
				log.error("Ошибка при заполнении суммы в операцию по программе лояльности.", e);
			}
		}
		String bns = XmlHelper.getSimpleElementValue(element, Constants.BNS_TAG_NAME);
		if (!StringHelper.isEmpty(bns))
		{
			operation.setOperationalBalance(getBalance(bns));
		}

		String partner = XmlHelper.getSimpleElementValue(element, Constants.PARTNER_TAG_NAME);
		operation.setOperationInfo(partner);

		return operation;
	}

	private Calendar parseDate(String dateTime)
	{
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		try
		{
			Calendar result = new GregorianCalendar();
			result.setTime(df.parse(dateTime));
			return result;
		}
		catch (ParseException e)
		{
			log.error("Ошибка при парсинге даты: " + dateTime);
			return null;
		}
	}

	/**
	 * Заполнение списка предложений по программе лояльности
	 * @param element - ответ
	 * @param cardNumber - номер карты, посылаемый в запросе
	 * @return - список предложений
	 */
	public List<LoyaltyOffer> fillLoyaltyOfferList(Element element, String cardNumber) throws GateException, GateLogicException
	{
		LoyaltyProgramRequestHelper.validateMessage(element, cardNumber);
		String offers = XmlHelper.getSimpleElementValue(element, Constants.LOY_OFFERS_TAG_NAME);
		if (StringHelper.isEmpty(offers))
			return Collections.emptyList();

		String[] strings = offers.split("\\\\r\\\\n");
		List<LoyaltyOffer> result = new ArrayList<LoyaltyOffer>();
		for (String str : strings)
			result.add(new LoyaltyOfferImpl(str));

		return result;
	}

	private BigDecimal getBalance(String loyBNS)
	{
		//баллы приходит в баллокопейках, поэтому делим их на 100
		return BigDecimal.valueOf(Long.parseLong(loyBNS), CENTS_SCALE);
	}
}
