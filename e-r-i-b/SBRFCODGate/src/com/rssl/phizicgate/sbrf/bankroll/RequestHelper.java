package com.rssl.phizicgate.sbrf.bankroll;

import com.rssl.phizgate.common.payments.PaymentHelper;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.clients.BackRefClientService;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.config.GateConnectionConfig;
import com.rssl.phizic.gate.documents.AbstractAccountTransfer;
import com.rssl.phizic.gate.documents.AbstractTransfer;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.gate.messaging.impl.MessageHeadImpl;
import com.rssl.phizic.gate.payments.*;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author krenev
 * @ created 12.07.2010
 * @ $Author$
 * @ $Revision$
 */
public class RequestHelper extends AbstractService
{
	private static final String PROPERTY_KEY = "com.rssl.phizic.gate.OurTBcodes";
	private static Pattern operationCodePattern = Pattern.compile("\\{VO\\d{5}\\}");

	public RequestHelper(GateFactory factory)
	{
		super(factory);
	}

	/**
	 * Получить номер картоного счета по номеру карты
	 * @param cardNumber номер карты
	 * @return номер картоного счета
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public String getCardAccountNumber(String cardNumber) throws GateException, GateLogicException
	{
		WebBankServiceFacade service = getFactory().service(WebBankServiceFacade.class);
		GateMessage msg = service.createRequest("cardInfoDemand");
		msg.addParameter("cardNumber", cardNumber);
		Document responce = service.sendOnlineMessage(msg, new MessageHeadImpl(null, null, null, "", null, null));
		return XmlHelper.getSimpleElementValue(responce.getDocumentElement(), "account");
	}

	/**
	 * Возвращает номер счета получателя
	 * @param payment - платеж
	 * @return номерсчета получателя
	 */
	public String getReceiverAccount(AbstractTransfer payment) throws GateException, GateLogicException
	{
		Class<? extends GateDocument> type = payment.getType();
		if (type == AccountToCardTransfer.class)
			return getCardAccountNumber(((AccountToCardTransfer) payment).getReceiverCard());
		else if (type == ClientAccountsTransfer.class)
			return ((ClientAccountsTransfer) payment).getReceiverAccount();
		else if (type == AccountRUSPayment.class || type == AccountIntraBankPayment.class)
		{
			AbstractRUSPayment rusPayment = (AbstractRUSPayment) payment;
			return rusPayment.getReceiverAccount();
		}
		else
			throw new GateException("Тип документа не поддерживается: "  + type);
	}

	/**
	 * Является ли перевод переводом в рамках 1 подразделения
	 * @param payment перевод
	 * @return true - перевод в рамках 1 подразделения
	 */
	public boolean isOurBank(AbstractTransfer payment) throws GateLogicException, GateException
	{
		Class<? extends GateDocument> type = payment.getType();
		if (type == AccountRUSPayment.class)
			return false; //всегда перевод в другой банк
		else if (type == ClientAccountsTransfer.class || type == AccountToCardTransfer.class)
			return true;  //перевод между счетами всегда в рамках 1 банка.
		else if (type == AccountIntraBankPayment.class)
		{
			//Проверка находится ли счет получателя в "нашем" банке (по коду ТБ в счете получателя)
			//Вырезать из счета получателя 10 и 11 символы (код ТБ)
			String receiverTBCode = getReceiverAccount(payment).substring(9, 11);
			String tbCodesList = ConfigFactory.getConfig(GateConnectionConfig.class).getProperty(PROPERTY_KEY);
			return StringHelper.isNotEmpty(tbCodesList) && tbCodesList.contains(receiverTBCode);
		}
		else
			throw new GateException("Тип документа не поддерживается: "  + type);
	}

	/**
	 * Возвращает сумму платежа
	 * @param payment - платеж
	 * @return сумма платежа
	 */
	public Money getAmount(AbstractAccountTransfer payment) throws GateException
	{
		if (payment.getChargeOffAmount() != null)
		{
			return payment.getChargeOffAmount();
		}
		else if (payment.getDestinationAmount() != null)
		{
			return payment.getDestinationAmount();
		}
		else
		{
			throw new GateException("Не задана сумма перевода");
		}
	}

	/**
	 * получить наименование получателя платежа
	 * @param payment платеж
	 * @return наименование получателя
	 */
	public String getReceiverName(AbstractTransfer payment) throws GateLogicException, GateException
	{
		Class<? extends GateDocument> type = payment.getType();
		if (type == ClientAccountsTransfer.class || type == AccountToCardTransfer.class)
		{
			return payment.getPayerName();
		}
	    else if (type == AccountIntraBankPayment.class || type == AccountRUSPayment.class)
		{
			return PaymentHelper.getReceiverName((AbstractRUSPayment) payment);
		}
		else
			throw new GateException("Тип документа не поддерживается: "  + type);
	}

	/**
	 * получить имя получателя платежа
	 * @param payment платеж
	 * @return наименование получателя
	 */
	public String getReceiverFirstName(AbstractTransfer payment) throws GateException
	{
		Class<? extends GateDocument> type = payment.getType();
		if (type == AccountIntraBankPayment.class)
		{
			return PaymentHelper.getReceiverFirstName(payment);
		}
        throw new GateException("Тип документа не поддерживается: "  + type);
	}

	/**
	 * получить фамилию получателя платежа
	 * @param payment платеж
	 * @return наименование получателя
	 */
	public String getReceiverSecondName(AbstractTransfer payment) throws GateException
	{
        Class<? extends GateDocument> type = payment.getType();
        if (type == AccountIntraBankPayment.class)
        {
            return PaymentHelper.getReceiverSecondName(payment);
        }
        throw new GateException("Тип документа не поддерживается: "  + type);
	}

	/**
	 * получить отчество получателя платежа
	 * @param payment платеж
	 * @return наименование получателя
	 */
	public String getReceiverSurName(AbstractTransfer payment) throws GateException
	{
        Class<? extends GateDocument> type = payment.getType();
        if (type == AccountIntraBankPayment.class)
        {
            return PaymentHelper.getReceiverSurName(payment);
        }
        throw new GateException("Тип документа не поддерживается: "  + type);
	}

	/**
	 * Возвращает код операции из платежа
	 * @param payment - платеж
	 * @return код операции
	 */
	public String getOperationCode(AbstractAccountTransfer payment)
	{
		String ground = payment.getGround();
		if (!StringHelper.isEmpty(ground))
		{
			Matcher matcher = operationCodePattern.matcher(ground);
			if (matcher.find())
			{
				String operationCode = matcher.group();
				return operationCode.substring(3, 8);
			}
		}

		return null;
	}
}
