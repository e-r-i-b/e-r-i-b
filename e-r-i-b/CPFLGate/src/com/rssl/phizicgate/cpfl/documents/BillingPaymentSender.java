package com.rssl.phizicgate.cpfl.documents;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizgate.common.payments.BillingPaymentHelper;
import com.rssl.phizgate.common.payments.systems.recipients.CommonField;
import com.rssl.phizgate.ext.sbrf.payments.billing.CPFLBillingPaymentHelper;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.WithdrawDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizgate.common.payments.documents.AbstractDocumentSenderBase;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.gate.payments.longoffer.CardPaymentSystemPaymentLongOffer;
import com.rssl.phizic.gate.payments.systems.AbstractPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.AccountPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizicgate.cpfl.messaging.RequestHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.transform.TransformerException;

/**
 * @author krenev
 * @ created 09.02.2011
 * @ $Author$
 * @ $Revision$
 * Сендер биллингового платежа AccountPaymentSystemPayment в ЦПФЛ
 */
public class BillingPaymentSender extends AbstractDocumentSenderBase
{
	private RequestHelper requestHelper;

	public BillingPaymentSender(GateFactory factory)
	{
		super(factory);
		requestHelper = new RequestHelper(factory);
	}

	/**
	 * подготовить документ
	 * @param document документ
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 * @throws com.rssl.phizic.gate.exceptions.GateLogicException
	 */
	public void prepare(GateDocument document) throws GateException, GateLogicException
	{
		if (document == null)
		{
			throw new GateException("Не передан документ для подготовки");
		}
		if (!AbstractPaymentSystemPayment.class.isAssignableFrom(document.getType()))
			throw new GateException("Неверный тип платежа - ожидается наследник AbstractPaymentSystemPayment");

		AbstractPaymentSystemPayment payment = (AbstractPaymentSystemPayment) document;

		//Доп ввод данных о тарифах клиент делает уже после последнего запроса в ЦПФЛ.
		//поэтому сеттим внешний идентфикатор из скрытого поля
		boolean isLongOffer = payment.getType() == CardPaymentSystemPaymentLongOffer.class;
		String idFromPaymentSystem = RequestHelper.getHiddenIdFromPaymentSystem(payment);
		if (isLongOffer && idFromPaymentSystem != null)
		{
			payment.setIdFromPaymentSystem(idFromPaymentSystem);
			return;
		}

		//Формируем и отправляем запрос на подготовку платежа
		Document responce = sendPrepareMessage(payment);
		// обрабатываем ответ.
		processPrepeaResponce(responce, payment);
	}

	/**
	 * Заполнить и послать сообщение на подготовку платежа
	 * @param payment платеж, который нужно подготовить
	 * @return ответ на запрос подготовки платежа
	 */
	private Document sendPrepareMessage(AbstractPaymentSystemPayment payment) throws GateException, GateLogicException
	{
		boolean isLongOffer = payment.getType() == CardPaymentSystemPaymentLongOffer.class;
		String receiverUID = payment.getReceiverPointCode();
		if (StringHelper.isEmpty(receiverUID))
		{
			throw new GateException("Некорректный документ " + payment.getId() + ": не задан получатель для оплаты");
		}
		WebBankServiceFacade service = getFactory().service(WebBankServiceFacade.class);
		//Формируем сообщение
		GateMessage gateMessage = service.createRequest("preparePaymentDemand_q");
		gateMessage.addParameter("clientId", "0");//идентифкатор плательщика - всегда 0
		if (isLongOffer)
		{
			gateMessage.addParameter("isForm190", "true");
		}
		if (!CPFLBillingPaymentHelper.isContractual(receiverUID))
		{
			String account = payment.getReceiverAccount();
			gateMessage.addParameter("account", account);//Счет недоговорника
			gateMessage.addParameter("inn", payment.getReceiverINN());//ИНН недоговорника
			ResidentBank bank = payment.getReceiverBank();
			gateMessage.addParameter("bankBIC", bank.getBIC());//БИК банка недоговорника
			if (!StringHelper.isEmpty(bank.getAccount()))
			{
				gateMessage.addParameter("bankAccount", bank.getAccount());
			}
			else if (account.startsWith("40101"))
			{
				gateMessage.addParameter("bankAccount", "0");//Для налоговых получателей вставляем 0 для отсуствующийх корсчетов
			}
		}
		else
		{
			gateMessage.addParameter("uniqueNumber", receiverUID);//идентифкатор получателя
		}
		//Для шаблонов и в случае отсутсвия суммы отправляем "0.00" в поле сумма
		gateMessage.addParameter("sum", payment.isTemplate() || payment.getDestinationAmount() == null ? "0.00" : payment.getDestinationAmount().getDecimal());
		gateMessage.addParameter("isShablon", payment.isTemplate());
		//добавляем инфу о доп полях.
		Document request = gateMessage.getDocument();
		Element requestRoot = request.getDocumentElement();

		//Добавляем данные о спец клиенте(полях) из платежа(если есть).
		List<Document> specialClient = requestHelper.getSpecialClient(payment);
		if (specialClient != null)
		{
			for (Document node : specialClient)
			{
				requestRoot.appendChild(request.importNode(node.getDocumentElement(), true));
			}
		}
		//добавляем реквизиты для наличных платежей из скрытого поля документа(если есть).
		Document nplatRequisites = CPFLBillingPaymentHelper.getNplatRequisites(payment);
		if (nplatRequisites != null)
		{
			requestRoot.appendChild(request.importNode(nplatRequisites.getDocumentElement(), true));
		}
		//отсылаем
		return service.sendOnlineMessage(gateMessage, null);
	}

	/**
	 * обработать ответ на запрос подготовки документа.
	 * @param responce ответ
	 * @param payment платеж, для обновления состояния объекта(новые поля, внешний идентифкатор....).
	 */
	private void processPrepeaResponce(Document responce, AbstractPaymentSystemPayment payment) throws GateException, GateLogicException
	{
		boolean isLongOffer = payment.getType() == CardPaymentSystemPaymentLongOffer.class;
		Element node = responce.getDocumentElement();
		try
		{
			//получаем specialClient сохранем содержимое specialClient в скрытом поле для последующих запросов на подготовку платежа.
			NodeList specialClientNodes = XmlHelper.selectNodeList(node, "specialClient");
			RequestHelper.updateDocumentSpecalClientInfo(payment, specialClientNodes);

			//получаем реквизиты для наличных платежей(nplatRequisites). Данное поле должно передаваться
			// без изменений и обновляться при каждом запросе на подготовку платежа.
			// впоследствии данный тег будет передан в запросе на исполнение платежа в ЦОД.
			CPFLBillingPaymentHelper.updateDocumentNplatRequisitesInfo(payment, XmlHelper.selectSingleNode(node, "nplatRequisites"));

			List<Field> documentExtendedFields = payment.getExtendedFields();
			List<Field> newFields = new ArrayList<Field>(documentExtendedFields);

			//для недоговорных получателей
			Field receiverNameField = BillingPaymentHelper.getFieldById(payment, CPFLBillingPaymentHelper.RECEIVER_NAME_FIELD_NAME);
			if (!CPFLBillingPaymentHelper.isContractual(payment.getReceiverPointCode()))
			{
				if (receiverNameField == null)
				{
					//наименованме получателя платежа вводит клиент, формируем его как доп. поле
					receiverNameField = CPFLBillingPaymentHelper.createReceiverNameField();
					newFields.add(receiverNameField);
				}
				if (!StringHelper.isEmpty(payment.getReceiverName()))
				{
					//при редактировании платежа необходимо имя получателя вынести в доп поле, чтобы пользователь мог его редактировать.
					((CommonField)receiverNameField).setDefaultValue(payment.getReceiverName());
					payment.setReceiverName(null);
				}
			}

			//ищем поле paymentType и его значение
			Document nplatRequisite = CPFLBillingPaymentHelper.getNplatRequisites(payment);
			String paymentTypeValue = XmlHelper.getSimpleElementValue(nplatRequisite.getDocumentElement(),"paymentType");
			//создаем доп поля по описаниям specialClient
			List<Field> responceFields = requestHelper.parseSpecialClients(specialClientNodes, paymentTypeValue);

			//формируем список новых полей.
			for (Field responceField : responceFields)
			{
				if (!CPFLBillingPaymentHelper.hasField(payment, responceField))
				{
					//если поля в платеже не было - добавляем в список новых полей
					newFields.add(responceField);
				}
			}

			//смотрим. есть ли в платеже поле для главной суммы
			Field amountField = BillingPaymentHelper.getMainSumField(payment);
			if (amountField == null)
			{
				//поля нет - генерим и добавляем в список новых полей.
				newFields.add(BillingPaymentHelper.createAmountField());
			}

			//Смотрим. есть ли комиссия
			String commission = XmlHelper.getSimpleElementValue(node, "commision");
			if (commission != null && StringHelper.isEmpty(XmlHelper.getElementValueByPath(node, "nplatRequisites/specialClientCodeOrg")))
			{
				throw new GateLogicException("В адрес данного получателя платежи временно не принимаются. Повторите попытку позже."); 
			}
			if (commission != null && (payment.isTemplate() || payment.getDestinationAmount() != null)) //сумма заполнена или шаблон и комиссия есть. подготовка документа закончена.
			{
				if (!payment.isTemplate())// в шаблонах комиисия не проставляется
				{
					//сеттим ее значение
					payment.setCommission(new Money(new BigDecimal(commission), payment.getDestinationAmount().getCurrency()));
				}
				String billingDocumentId = XmlHelper.getSimpleElementValue(node, "messageId");
				XmlHelper.getSimpleElementValue(node, "messageId");

				boolean needClientData = false; // признак того, что требуется еще данные от клиента
				if (isLongOffer)
				{
					//Для длительных поручений должен(если ЦПФЛ запросил) ввести доп инфу о тарифах.
					needClientData = processAckClientBankCanChangeSumm(newFields, responce) | processTariff(newFields, responce);
					newFields.add(CPFLBillingPaymentHelper.createHiddenCommissionLabel(XmlHelper.getSimpleElementValue(node, "commisionLabel")));
					newFields.add(CPFLBillingPaymentHelper.createHiddenServiceCode(XmlHelper.getSimpleElementValue(node, "serviceCode")));
				}

				if (needClientData)
				{
					//Клиент должен еще что-то выбрать. пусть выбирает - внешний идентфикатор сообщения сохраним в доп поле.
					//при последующем запросе на адаптер мы не будем стучаться в ЦПФЛ, а просто просетим IdFromPaymentSystem.
					newFields.add(RequestHelper.createHiddenIdFromPaymentSystem(billingDocumentId));
				}
				else
				{
					//на втором этапе проставляем введенное значение в основные параметры платежа, доп. поле удаляем
					if (receiverNameField != null && !CPFLBillingPaymentHelper.isContractual(payment.getReceiverPointCode()))
					{
						payment.setReceiverName((String) (StringHelper.isEmpty((String)receiverNameField.getValue()) ? receiverNameField.getDefaultValue() : receiverNameField.getValue()));
						newFields.remove(receiverNameField);
					}
					//устанавливаем внешний идентификатор документа
					payment.setIdFromPaymentSystem(billingDocumentId);
				}
			}
			//обновляем поля в платеже
			payment.setExtendedFields(newFields);
		}
		catch (TransformerException e)
		{
			throw new GateException(e);
		}
		catch (DocumentException e)
		{
			throw new GateException(e);
		}
	}

	private boolean processTariff(List<Field> newFields, Document responce) throws GateException
	{
		List<Field> fields = CPFLBillingPaymentHelper.createTariffFields(responce);
		if (fields == null)
		{
			return false;
		}
		newFields.addAll(fields);
		return true;
	}

	/**
	 * обработать признак AckClientBankCanChangeSumm.
	 * @param fields - список полей в который требуется добавить новые поля.
	 * @param responce - ответ ЦПФЛ
	 * @return признак требуется ли выбор клиента для этого поля.
	 */
	private boolean processAckClientBankCanChangeSumm(List<Field> fields, Document responce) throws GateException
	{
		boolean ackClientBankCanChangeSumm = Boolean.parseBoolean(XmlHelper.getSimpleElementValue(responce.getDocumentElement(), "ackClientBankCanChangeSumm"));
		if (ackClientBankCanChangeSumm)
		{
			fields.add(CPFLBillingPaymentHelper.createAckClientBankCanChangeSumm());
		}
		return ackClientBankCanChangeSumm;
	}

	/**
	 * послать документ
	 * @param document документ
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 * @throws com.rssl.phizic.gate.exceptions.GateLogicException
	 */
	public void send(GateDocument document) throws GateException, GateLogicException
	{
		//в ЦПФЛ происходит только подготовка документа(prepea). отсылка осуществляется в ЦОД
	}

	/**
	 * отозвать документ
	 * @param document документ
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 * @throws com.rssl.phizic.gate.exceptions.GateLogicException
	 */
	public void rollback(WithdrawDocument document) throws GateException, GateLogicException
	{
		//Отзыва в ЦПФЛ нет.
	}

	/**
	 * подтвердить платеж
	 * @param document платеж для подтверждения
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 * @throws com.rssl.phizic.gate.exceptions.GateLogicException
	 */
	public void confirm(GateDocument document) throws GateException, GateLogicException
	{
		//подтверждения в ЦПФЛ нет
		//BUG028971.Затираем значение unp для ЦПФЛ, не нужно отображать в чеке.
		AccountPaymentSystemPayment payment = (AccountPaymentSystemPayment) document;
		payment.setIdFromPaymentSystem(null);
	}

	/**
	 * валидация платежа
	 * @param document  платеж
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 * @throws com.rssl.phizic.gate.exceptions.GateLogicException
	 */
	public void validate(GateDocument document) throws GateException, GateLogicException
	{
		//валидация происходит на этапе подготовки платежа(prepea)
	}

	public void repeatSend(GateDocument document) throws GateException, GateLogicException
	{
		//nothing to do
	}
}
