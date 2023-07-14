package com.rssl.phizicgate.sbrf.ws.listener.handler;

import com.rssl.common.forms.doc.DocumentCommand;
import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.cache.MessagesCacheManager;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.documents.AbstractAccountTransfer;
import com.rssl.phizic.gate.documents.UpdateDocumentService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.gate.payments.ClientAccountsTransfer;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizicgate.sbrf.ws.listener.ConfirmOfflineRequestHandler;
import com.rssl.phizicgate.sbrf.ws.listener.InternalMessageInfoContainer;
import com.rssl.phizicgate.sbrf.ws.util.ServiceReturnHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.math.BigDecimal;
import java.util.HashMap;

/**
 * User: Omeliyanchuk
 * Date: 09.01.2007
 * Time: 12:44:43
 */

/**
 * обработчик сообщения о выполнении платежа
 */
public abstract class PaymentConfirmationOfflineHandlerBase implements ConfirmOfflineRequestHandler
{
	protected static Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);
	
	public boolean handleMessage(InternalMessageInfoContainer messageInfoContainer, Object object) throws GateException, GateLogicException
	{
		UpdateDocumentService updateDocumentService = GateSingleton.getFactory().service(UpdateDocumentService.class);

		AbstractAccountTransfer document = (AbstractAccountTransfer) object;

		try
		{
			fillPaymentData(document, messageInfoContainer.getMessage().getBody());
			updateDocumentService.update(document);
			updateDocumentService.update(document,  getUpdateCommand(messageInfoContainer));

			clearCache(document);
		}
		catch (Exception ex)
		{
			//в данном случае это исключение показать некому.
			throw new GateException(ex);
		}

		return true;
	}

	public void fillPaymentData(AbstractAccountTransfer document, String messageText) throws GateException
	{
		try
		{
			Document doc = XmlHelper.parse(messageText);
			Element chargeOffDateElement = XmlHelper.selectSingleNode(doc.getDocumentElement(), "/message/confirmation_offline_a/debitRow/date");
			//установим дату исполнения платежа
			document.setExecutionDate(DateHelper.toCalendar(DateHelper.fromXMlDateToDate(chargeOffDateElement.getTextContent())));

			if (isConvertionPayment(document))
			{
				Money creditSum = readSum(doc.getDocumentElement(), "/message/confirmation_offline_a/creditRow/sum", getDestinationAccount(document), document);
				Money debitSum = readSum(doc.getDocumentElement(), "/message/confirmation_offline_a/debitRow/sum", document.getChargeOffAccount(), document);
				if (debitSum != null)
				{
					document.setChargeOffAmount(debitSum);
				}
				if (creditSum != null)
				{
					document.setDestinationAmount(creditSum);
				}
			}
		}
		catch (Exception e)
		{
			throw new GateException("Ошибка при разборе сообщения", e);
		}
	}

	/**
	 * @return Возвращает состояние в которое требуется перевести документ
	 */
	public DocumentCommand getUpdateCommand(InternalMessageInfoContainer messageInfoContainer)
	{
		return new DocumentCommand(DocumentEvent.EXECUTE, new HashMap<String, Object>());
	}

	/**
	 * является ли докумнт конверсионным
	 * @param document документ
	 * @return да/нет
	 */
	protected abstract boolean isConvertionPayment(AbstractAccountTransfer document) throws GateException, GateLogicException;

	/**
	 * получить счет зачисления из платежа
	 * @param document платеж
	 * @return счет
	 */
	protected abstract String getDestinationAccount(AbstractAccountTransfer document) throws GateLogicException, GateException;

	/**
	 * Очистка кеша сообщений, для обновления остатков на счетах
	 * @param document платеж
	 */
	private void clearCache(AbstractAccountTransfer document) throws GateException
	{
		try
		{
			BankrollService bankrollService = ServiceReturnHelper.getInstance().service(document, BankrollService.class);
			WebBankServiceFacade serviceFacade = ServiceReturnHelper.getInstance().service(document, WebBankServiceFacade.class);
			MessagesCacheManager messagesManager = serviceFacade.getMessagesCacheManager();

			Account chargeOffAccount = GroupResultHelper.getOneResult(bankrollService.getAccountByNumber(new Pair<String, Office>(document.getChargeOffAccount(), document.getOffice())));
			if (chargeOffAccount != null)
				messagesManager.clear(chargeOffAccount);

			//todo криво преребирать все платежи, но пока другого решения не видно
			if (ClientAccountsTransfer.class.isAssignableFrom(document.getType()))
			{
				ClientAccountsTransfer transfer = (ClientAccountsTransfer) document;
				Account account = GroupResultHelper.getOneResult(bankrollService.getAccountByNumber(new Pair<String, Office>(transfer.getReceiverAccount(), transfer.getOffice())));
				if (account != null)
					messagesManager.clear(account);
			}
		}
		catch (Exception ex)
		{
			log.error("Ошибка при очистке кэша сообщений", ex);
		}
	}

	private Money readSum(Element root, String sumPath, String accountNumber, AbstractAccountTransfer document) throws GateException
	{
		BankrollService bankrollService = ServiceReturnHelper.getInstance().service(document, BankrollService.class);

		try
		{
			Element sumEl = XmlHelper.selectSingleNode(root, sumPath);
			String sumStr = XmlHelper.getElementText(sumEl);

			if (!StringHelper.isEmpty(sumStr))
			{
				BigDecimal sumtDec = new BigDecimal(sumStr);
				Account account = GroupResultHelper.getOneResult(bankrollService.getAccount(accountNumber));
				return new Money(sumtDec, account.getCurrency());
			}
			return null;
		}
		catch (Exception ex)
		{
			throw new GateException("Ошибка при разборе offline сообщения", ex);
		}
	}
}
