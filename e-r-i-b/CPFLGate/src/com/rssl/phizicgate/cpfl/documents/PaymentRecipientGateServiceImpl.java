package com.rssl.phizicgate.cpfl.documents;

import com.rssl.phizgate.common.routable.RecipientImpl;
import com.rssl.phizgate.ext.sbrf.payments.billing.CPFLBillingPaymentHelper;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.dictionaries.BackRefBankInfoService;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.dictionaries.billing.Billing;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.GateTimeOutException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.gate.payments.systems.recipients.PaymentRecipientGateService;
import com.rssl.phizic.gate.payments.systems.recipients.Recipient;
import com.rssl.phizic.gate.payments.systems.recipients.RecipientInfo;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.ForeachElementAction;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * @author krenev
 * @ created 06.03.2011
 * @ $Author$
 * @ $Revision$
 */
public class PaymentRecipientGateServiceImpl extends AbstractService implements PaymentRecipientGateService
{
	public PaymentRecipientGateServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	/**
	 * Найти получателей в биллинге по счету, БИКу и ИНН
	 * @param account счет получателя
	 * @param bic бик банка получателя
	 * @param inn инн получателя
	 * @param billing биллинг, в котором нуджно найти получателя
	 * @return список получателей удовлетворяющих результату. если получтели не найдены - пустой список
	 */
	public List<Recipient> getRecipientList(String account, String bic, String inn, Billing billing) throws GateException, GateLogicException
	{
		WebBankServiceFacade service = getFactory().service(WebBankServiceFacade.class);
		BackRefBankInfoService bankInfoService = getFactory().service(BackRefBankInfoService.class);

		GateMessage gateMessage = service.createRequest("recipientListDemand_q");
		gateMessage.addParameter("account", account);
		gateMessage.addParameter("bankBIC", bic);

		ResidentBank bank = bankInfoService.findByBIC(bic);
		if(bank == null)
			throw new GateException("Банк с БИК равным " + bic + " не найден");
		
		if(!StringHelper.isEmpty(bank.getAccount()))
		{
			gateMessage.addParameter("bankAccount", bank.getAccount());
		}
		else if (account.startsWith("40101"))
		{
			gateMessage.addParameter("bankAccount", "0");//Для налоговых получателей вставляем 0 для отсуствующийх корсчетов
		}
		if (!StringHelper.isEmpty(inn))
		{
			gateMessage.addParameter("inn", inn);
		}
		Document responce = service.sendOnlineMessage(gateMessage, null);
		final List<Recipient> result = new ArrayList<Recipient>();
		try
		{
			XmlHelper.foreach(responce.getDocumentElement(), "//recipient", new ForeachElementAction()
			{
				public void execute(Element element) throws Exception
				{
					String code = XmlHelper.getSimpleElementValue(element, "uniqueNumber");
					String name = XmlHelper.getSimpleElementValue(element, "name");
					RecipientImpl recipient = new RecipientImpl();
					recipient.setSynchKey(code);
					recipient.setName(name);
					result.add(recipient);
				}
			}
			);
		}
		catch (GateTimeOutException e)
		{
			throw new GateLogicException(e.getMessage());
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
		if (result.isEmpty())
		{
			//если в ЦПФЛ получателя нет - создаем псевдополучателя, чтобы оплата всегда осущестлялась с участием ЦПФЛ.
			RecipientImpl recipient = new RecipientImpl();
			recipient.setSynchKey(CPFLBillingPaymentHelper.NOT_CONTRACTUAL_RECEIVER_CODE);
			result.add(recipient);
		}
		return result;
	}

	/**
	 * Получить список персональных получателй(шаблонов) клиента
	 *
	 * @param billingClientId Идентификатор клиента в биллнге.
	 * @param billing биллнг
	 * @return список получателей или пустой спискок, если их нет
	 */
	public List<Recipient> getPersonalRecipientList(String billingClientId, Billing billing) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * Получить список значенийе заполененных полей персонального получателя(шаблона)
	 * @param recipient получатель
	 * @param billingClientId идентификатор клиента в биллинге
	 * @return список значений полей или пустой спискок, если их нет
	 */
	public List<Field> getPersonalRecipientFieldsValues(Recipient recipient, String billingClientId) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * Получить дополнительную информацию по получателю.
	 *
	 * @param recipient получатель
	 * @param fields список дополнительных полей
	 * @param debtCode - код задолженности, может быть null
	 * @return дополнительная информция по получателю
	 * @exception com.rssl.phizic.gate.exceptions.GateException
	 * @exception com.rssl.phizic.gate.exceptions.GateLogicException
	 */
	public RecipientInfo getRecipientInfo(Recipient recipient, List<Field> fields, String debtCode) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * Получение получателя платежа по его идентификатору.
	 * GateException - если получатель не найден.
	 *
	 * @param recipientId Идентификатор получателя.
	 * @return получатель платежа
	 * @exception com.rssl.phizic.gate.exceptions.GateException
	 * @exception com.rssl.phizic.gate.exceptions.GateLogicException
	 */
	public Recipient getRecipient(String recipientId) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * Получить список получателей заведенных в биллинге
	 * @param billing биллинг
	 * @return список получателей или пустой спискок, если их нет
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 * @throws com.rssl.phizic.gate.exceptions.GateLogicException
	 */
	public List<Recipient> getRecipientList(Billing billing) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * Получить список доп. полей для получателя
	 * @param recipient получатель
	 * @param keyFields поля
	 * @return список доп полей
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 * @throws com.rssl.phizic.gate.exceptions.GateLogicException
	 */
	public List<Field> getRecipientFields(Recipient recipient, List<Field> keyFields) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * Получить список ключевых полей для получателя
	 * @param recipient получатель
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 * @throws com.rssl.phizic.gate.exceptions.GateLogicException
	 * @return список ключевых полей
	 */
	public List<Field> getRecipientKeyFields(Recipient recipient) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * Получить владельца карты персональных платежей
	 * @param cardId - номер карты персональных платежей
	 * @param billing - биллинг
	 * @return владелец карты персональных платежей
	 * @exception com.rssl.phizic.gate.exceptions.GateException
	 * @exception com.rssl.phizic.gate.exceptions.GateLogicException
	 */
	public Client getCardOwner(String cardId, Billing billing) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException();
	}
}
