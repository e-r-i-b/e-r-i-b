package com.rssl.phizicgate.asbc.documents;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizgate.common.payments.BillingPaymentHelper;
import com.rssl.phizgate.common.payments.systems.recipients.CommonField;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.BackRefBankrollService;
import com.rssl.phizic.gate.dictionaries.BackRefBankInfoService;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.documents.DocumentSender;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.WithdrawDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.gate.payments.systems.AccountPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.recipients.BackRefReceiverInfoService;
import com.rssl.phizic.gate.payments.systems.recipients.BusinessRecipientInfo;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizicgate.asbc.messaging.Constants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.math.BigDecimal;
import javax.xml.transform.TransformerException;

/**
 * @author Krenev
 * @ created 01.02.2010
 * @ $Author$
 * @ $Revision$
 */
public class ASBCDocumentSender extends AbstractService implements DocumentSender
{
	private Map<String, ?> parameters = new HashMap<String, Object>();
	private static final int MAX_LENGTH_ADDITIONAL_REQUISITES = 256; //иаксимально возможная длина поля additionalRequisites
	private static final String DELIMITER = "@";                     //Разделитель значений дополнительных реквизитов

	public ASBCDocumentSender(GateFactory factory)
	{
		super(factory);
	}

	public void setParameters(Map<String, ?> params)
	{
		parameters = params;
	}

	protected Object getParameter(String name)
	{
		return parameters.get(name);
	}

	public void send(GateDocument document) throws GateException, GateLogicException
	{
		//nothing to do
	}

	public void repeatSend(GateDocument document) throws GateException, GateLogicException
	{
		//nothing to do
	}

	/**
	 * Для оплаты получателя АСБЦ важны 4 доп поля:
	 * 1) лицевой счет. Всегда забит в БД.
	 * 2) transactionId - скрытое поле. значение приходит в findCredits
	 * 3) задолженность
	 * 4) сумма
	 * процесс подготовки документа выглядит сл образом.
	 * 1) Клиент вводит лицевой счет.
	 * 2) посылается запрос findCredits, содержащий идентфикатор получателя и лицевой счет клиента.
	 * 3) в ответе может придти 1 или несколько элементов result_element с информацией о получаетле и задолженности
	 * 4) определяется по справочнику поставщиков ИКФЛ признаки "поддерживает задолженность" и "запрос реквизитов онлайн".
	 * 5) если пришел один элемент result_element (1 задолженность), то
	 *     a) если у получателя выставлен признак "запрос реквизитов онлайн", то в документе обновляются платежные реквизиты получателя (Банк, Счет, ИНН, КПП) значениями из ответа
	 *     b) генерируются следующие поля: скрытое поле с кодом задолженности (creditId), нередактируемое поле задолженность со значением задолженности (если у получателя выставлен признак "поддерживает задолженность", для шаблона платежа поле задолженности не формируем),
	 *        редактируемое поле сумма с инициализирующим значением равным размеру задолженности(при положительной задолженности) и скрытое поле со значением transactionId
	 *     с) поля предъявляются клиенту и процесс подготовки заканчивается
	 * 6) если пришло несколько элементов result_element (несколько задолженностей), то
	 *     a) если у получателя НЕ выставлен признак "поддерживает задолженность", то кидается исключение, тк неизвестно которую задолженность оплачивать
	 *     b) генерируются поле выпадающий список с выбором задолженности для предъявления клиенту. (для шаблона платежа берем первую задолженность из списка, поле самой задолженности не формируем)
	 *     с) после выбора задолженности клиентом происходит второй шаг подготовки документа.
	 *          1. посылается повторный запрос findCredits, содержащий идентфикатор получателя и лицевой счет клиента.
	 *          2. в ответе находится секция result_element, соответствующая выбранной задолженности(соотвествие по полю creditId)
	 *          3. если у получателя выставлен признак "запрос реквизитов онлайн", то в документе обновляются платежные реквизиты получателя (Банк, Счет, ИНН, КПП) значениями из найденной секции
	 *          4. генерируются  редактируемое поле сумма с инициализирующим значением равным размеру задолженности(при положительной задолженности) и скрытое поле со значением transactionId
	 *          5. поля предъявляются клиенту и процесс подготовки заканчивается
	 * @param document документ
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public void prepare(GateDocument document) throws GateException, GateLogicException
	{
		//обновляем поля в документе значениями из ответа на запрос задолженности
		if (!(document instanceof AccountPaymentSystemPayment))
			throw new GateException("Ожидается AccountPaymentSystemPayment");

		AccountPaymentSystemPayment payment = (AccountPaymentSystemPayment) document;

		try
		{
			List<Field> extendedFields = payment.getExtendedFields();

			ASBCRequestHelper requestHepler = new ASBCRequestHelper(getFactory());
			//получаем ключевое поле.
			Field keyField = ASBCRequestHelper.findKeyFields(payment);
			if (keyField == null)
			{
				extendedFields.add(requestHepler.createKeyField());// добавляем ключевое поле в платеж
				return;//без ключевого поля делать нечего, ждем когда клиент его введет.
			}
			else
			{
				//ключевое поле делаем нередактируемым
				((CommonField) keyField).setEditable(false);
			}
			//теперь важно узнать есть ли поле transactionId. его наличие говорит о том, что клиент выбрал задолженность или задолженность 1.
			Field transactionId = BillingPaymentHelper.getFieldById(extendedFields, Constants.TRANSACTIONAL_ID_FIELD_NAME);
			if (transactionId != null)
			{
				//transactionId есть значит все заполненно..
				BillingPaymentHelper.prepareFields(payment);
				//в шаблоне не показываем клиенту поле задолженность
				if (payment.isTemplate())
				{
					//поле задолженность может быть как простым, так и списковым
					Field debtField = BillingPaymentHelper.getFieldById(extendedFields, Constants.DISPLAY_DEBT_FIELD_NAME);
					if (debtField == null)
					{
						debtField = BillingPaymentHelper.getFieldById(extendedFields, Constants.DEBT_FIELD_NAME);
					}
					extendedFields.remove(debtField);
				}

				payment.setIdFromPaymentSystem(BillingPaymentHelper.generateIdFromPaymentSystem(payment));
				return;//заканчиваем процесс подготовки.
			}

			//поля с транзакцией нет. значит либо не получали задолженность, либо получали, но пришло несколько.
			//запрос findCredits в любом случае нужен.
			Document result = requestHepler.makeFindCreditsRequest(payment.getReceiverPointCode(), ASBCRequestHelper.findKeyFields(payment));
			//смотрим есть ли поле задолженности
			Field debtField = BillingPaymentHelper.getFieldById(extendedFields, Constants.DEBT_FIELD_NAME);
			if (debtField != null)
			{
				//задолженность в документе уже есть. значит она должна быть списочным полем и клиент выбрал значение.. получаем это значение и ищем нужный элемент в ответе
				Element resultElement = getResultElement(result, (String) debtField.getValue());//нашли секцию result_element, соотвествующую выбранной задолженности...
				// генерим поле TransactionId
				extendedFields.add(requestHepler.createTransactionIdField(XmlHelper.getSimpleElementValue(resultElement, Constants.TRANSACTIONAL_ID_FIELD_NAME)));
				// генерим поле главной суммы
				extendedFields.add(requestHepler.createAmountField(requestHepler.getDebt(resultElement)));
				//обновляем, если нужно реквизиты получателя
				refreshRecipientInfo(payment, resultElement);
				//делаем задолженность нередактируемой
				((CommonField) debtField).setEditable(false);
				//обновляем значения полей
				requestHepler.updateFieldsValue(payment, result);
				return; //больше делать нечего. пусть клиент соглашается с суммой или вводит свою
			}
			//задолженности вообще нет в документе... будем добавлять в документ...
			//но для начала получим инфу из бизнеса о получатее
			BusinessRecipientInfo businessRecipientInfo = getBusinessResipietnInfo(payment);
			NodeList nodeList = XmlHelper.selectNodeList(result.getDocumentElement(), "//result/result_element");//Сколько там задолженностей вернулось?
			if (nodeList.getLength() == 1) //1 задолженность... нужны поля задолженность, сумма и скрытое поле код транзакции
			{
				Element element = (Element) nodeList.item(0);
				// генерим поле TransactionId
				extendedFields.add(requestHepler.createTransactionIdField(XmlHelper.getSimpleElementValue(element, Constants.TRANSACTIONAL_ID_FIELD_NAME)));
				//добавляем задолженность
				BigDecimal debt = requestHepler.getDebt(element);
				//поле с кодом задолженности создаем всегда
				extendedFields.add(requestHepler.createHiddenDeptField(XmlHelper.getSimpleElementValue(element, "creditId")));
				//но с суммой задолженности интерснее, ее надо показывать в зависимости от признака
				//для шаблона платежа поле задолженность не формируем
				if (businessRecipientInfo.isDeptAvailable() && !payment.isTemplate())
				{
					extendedFields.add(requestHepler.createSingleDebtField(debt));
				}
				//генерим сумму , в случае если задолженость неотрицательная
				extendedFields.add(requestHepler.createAmountField(businessRecipientInfo.isDeptAvailable() && (BigDecimal.ZERO.compareTo(debt) == -1) ? debt : null));
				//обновляем, если нужно реквизиты получателя
				refreshRecipientInfo(payment, element);
				//обновляем значения полей
				requestHepler.updateFieldsValue(payment, result);
				return; //больше делать нечего. пусть клиент соглашается с суммой или вводит свою
			}
			//пришло несколько задолженностей
			if (!businessRecipientInfo.isDeptAvailable())
			{
				//если не поддерживается получение задолженности, а пришло несколько видов оплаты, сделать мы ничего не можем..
				throw new GateLogicException("В данный момент Вы не можете оплатить услуги выбранной организации. Повторите операцию позже.");
			}

			//для платежа: предоставляем клиенту самому выбрать оплачиваемую задолженность
			if (!payment.isTemplate())
			{
				//генерим теперь поле (выпадающий список) задолженность
				extendedFields.add(requestHepler.createDebtListField(nodeList));
				//все.
			}
			else
			//для шаблона платежа: берем первое значение задолженности
			{
				Element element = (Element) nodeList.item(0);
				// генерим поле TransactionId
				extendedFields.add(requestHepler.createTransactionIdField(XmlHelper.getSimpleElementValue(element, Constants.TRANSACTIONAL_ID_FIELD_NAME)));
				//добавляем задолженность
				BigDecimal debt = requestHepler.getDebt(element);
				//поле с кодом задолженности создаем всегда
				extendedFields.add(requestHepler.createHiddenDeptField(XmlHelper.getSimpleElementValue(element, "creditId")));
				//генерим сумму
				extendedFields.add(requestHepler.createAmountField(businessRecipientInfo.isDeptAvailable() && (BigDecimal.ZERO.compareTo(debt) == -1) ? debt : null));
				//обновляем, если нужно реквизиты получателя
				refreshRecipientInfo(payment, element);
				//обновляем значения полей
				requestHepler.updateFieldsValue(payment, result);
			}
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

	public void rollback(WithdrawDocument document) throws GateException, GateLogicException
	{
		WebBankServiceFacade serviceFacade = GateSingleton.getFactory().service(WebBankServiceFacade.class);
//		GateMessage message = serviceFacade.createRequest("reversePayment");
		//....TODO
	}

	public void confirm(GateDocument document) throws GateException, GateLogicException
	{
		if (!(document instanceof AccountPaymentSystemPayment))
		{
			throw new GateException("Ожидается AccountPaymentSystemPayment");
		}
		AccountPaymentSystemPayment payment = (AccountPaymentSystemPayment) document;

		try
		{
			Document result = makeMakePaymentRequest(payment);
			String externalId = XmlHelper.getElementText((Element)result.getElementsByTagName("makePayment").item(0));
			payment.setIdFromPaymentSystem(externalId);
		}
		catch (DocumentException e)
		{
			throw new GateException(e);
		}
	}

	public void validate(GateDocument document) throws GateException, GateLogicException
	{
		//nothing to do
	}

	private Document makeMakePaymentRequest(AccountPaymentSystemPayment payment) throws GateLogicException, GateException, DocumentException
	{
		WebBankServiceFacade serviceFacade = GateSingleton.getFactory().service(WebBankServiceFacade.class);

		String receiverId = payment.getReceiverPointCode();
		ResidentBank receiverBank = payment.getReceiverBank();
		List<Field> extendedFields = payment.getExtendedFields();

		//Получаем значение даты исполнения платежа
		Date executionDate = DateHelper.toDate(payment.getExecutionDate());

		//Получаем значения additionalsRequisites
		StringBuilder additionalsRequisites = new StringBuilder();
		for (Field field : extendedFields)
		{
			Object value = field.getValue();
			String additionalParameter = StringHelper.getEmptyIfNull(value);
			//если длина поля additionalsRequisites больше 256, то АСБЦ вернет ошибку, поэтому обрезаем лишнее до ближайшего разделителя
			if (additionalsRequisites.length() + additionalParameter.length() >= MAX_LENGTH_ADDITIONAL_REQUISITES)
			{
				break;
			}
			additionalsRequisites.append(additionalParameter);
			additionalsRequisites.append(DELIMITER);
		}

		GateMessage message = serviceFacade.createRequest("makePayment");
		Field creditId = BillingPaymentHelper.getFieldById(extendedFields, Constants.DEBT_FIELD_NAME);
		message.addParameter("creditId", creditId.getValue());  // Идентификатор начисления	 ИКФЛ берет значение из ответа АС «БЦ» на запрос FindCreditsExt (получить задолженность)
		message.addParameter("paymentDate", DateHelper.toXMLDateFormat(executionDate));//Дата исполнения платежа в АБС банка в формате yyyy-mm-dd
		message.addParameter("paymentTime", paymentTimeFormat(executionDate));//Время исполнения платежа в АБС банка в формате hh:mm:ss

		BackRefBankrollService backRefBankrollService = GateSingleton.getFactory().service(BackRefBankrollService.class);
		Office office = backRefBankrollService.getAccountOffice(payment.getInternalOwnerId(), payment.getChargeOffAccount());

		message.addParameter("osb", office.getCode().getFields().get("branch"));//Отделение, принявшее платеж	Номер ОСБ, где открыт счет плательщика
		message.addParameter("filial", "88888");//Филиал, принявший платеж	ИКФЛ передает значение «88888». Передаваемое значение в этом поле должно быть таким же, как при выгрузке ЖБТ.
		message.addParameter("paymentType", "8");//Тип платежа	Тип платежа. Всегда 8 - платеж принят по системе ИКФЛ/Сбербанк ОнЛ@йн
		message.addParameter("docNumber", payment.getDocumentNumber());  //Номер платежного документа	Номер документа в ИКФЛ
		message.addParameter("sum", payment.getDestinationAmount().getAsCents());//ИКФЛ передает результат сложения сумма платежа и суммы комиссии с клиента за исполнение платежа
		message.addParameter("commission", "0"); //Комиссия с получателя	ИКФЛ передает значение = 0.
		message.addParameter("payerCommission", "0"); //Rомиссия с клиента за исполнение платежа	Комиссия с клиента за исполнение платежа. ИКФЛ передает нуль, так как из АС «БЦ» не возвращается комиссия по платежу клиента.
		message.addParameter("sumPu", payment.getDestinationAmount().getAsCents()); //Сумма поставщику за услугу	ИКФЛ проставляет значение суммы платежа за вычетом суммы комиссии за исполнение платежа клиента	/**
		//message.addParameter("sumSt", "??"); //Сумма страховки, поле не используется.	ИКФЛ всегда значение не передает
		message.addParameter("additionalsRequisites", additionalsRequisites); //Дополнительные реквизиты платежа     Порядок следования атрибутов и разделитель совпадает с порядком следования и разделителем, используемые при выгрузке дополнительных реквизитов для ЖБТ. Разделителем значений дополнительных реквизитов и последним символом строки является символ «@». В отличие от ЖБТ в данном поле передаются только значения атрибутов и разделитель.
		message.addParameter("cashierId", "88888"); //Номер кассира	В АС «БЦ» это поле не используется, но ФП «Коммунальные Платежи» определяет реквизит «Тип платежа» по номеру кассира. ИКФЛ всегда передает значение «88888».
		message.addParameter("serviceId", "0"); //Идентификатор услуги в АС «БЦ»	ИКФЛ передает значение «0».
		message.addParameter("bic", receiverBank.getBIC()); //БИК банка получателя
		message.addParameter("correspondentAccount", StringHelper.isEmpty(receiverBank.getAccount()) ? "0" : receiverBank.getAccount()); //Корсчет банка получателя
		message.addParameter("settlementAccount", payment.getReceiverAccount()); //Расчетный счет получателя
		message.addParameter("inn", payment.getReceiverINN()); //ИКФЛ всегда передает ИНН получателя
		if (!StringHelper.isEmpty(payment.getReceiverKPP()))
		{
			message.addParameter("kpp", payment.getReceiverKPP()); //Если есть значение, то КПП получателя, иначе ничего.
		}
		//message.addParameter("kbk", "??"); //Код бюджетной классификации	Если есть значение код бюджетной классификации, иначе ничего
		//message.addParameter("okato", "??"); //Код по ОКАТО	Если есть значение, то ОКАТО, иначе ничего.
		message.addParameter("providerId", receiverId); //Идентификатор поставщика услуг	Номер поставщика услуг в биллинговой системе

		String transactionId = (String) BillingPaymentHelper.getFieldById(extendedFields, Constants.TRANSACTIONAL_ID_FIELD_NAME).getValue();
		if (!StringHelper.isEmpty(transactionId))
		{
			message.addParameter("transactionId", transactionId); //Идентификатор транзакции	ИКФЛ передает значение из ответа АС «БЦ» запрос FindCreditsExt. АС «БЦ» присылает идентификатор транзакции при взаимодействии с поставщиками услуг в режиме on-line.
		}
		//message.addParameter("providerInfo", "??"); //Наименование получателя платежа	Наименование получателя платежа. ИКФЛ значение не передает

		return serviceFacade.sendOnlineMessage(message, null);
	}

	private String paymentTimeFormat(Date date)
	{
		DateFormat df = new SimpleDateFormat("HH:mm:ss");
		return df.format(date);
	}

	/**
	 * Обновляем реквизиты получателя платежа, если у него установлено свойство "запрос реквизитов онлайн"
	 * @param payment платеж
	 * @param result секция result_element, с инфой о получателе.
	 * @throws GateException
	 * @throws GateLogicException
	 */
	private void refreshRecipientInfo(AccountPaymentSystemPayment payment, Element result) throws GateException, GateLogicException
	{
		BusinessRecipientInfo businessRecipientInfo = getBusinessResipietnInfo(payment);

		if (!businessRecipientInfo.isPropsOnline())
		{
			return; //реквизиты обновлять не требуется.
		}

		BackRefBankInfoService refBankInfoService = getFactory().service(BackRefBankInfoService.class);
		ResidentBank residentBank = refBankInfoService.findByBIC(XmlHelper.getSimpleElementValue(result, "bic"));
		//вернулись данные, которых у нас нет
		if (residentBank == null)
			throw new GateLogicException("В данный момент Вы не можете оплатить услуги выбранной организации. Повторите операцию позже.");

		payment.setReceiverBank(residentBank);
		payment.setReceiverAccount(XmlHelper.getSimpleElementValue(result, "settlementAccount"));
		payment.setReceiverINN(XmlHelper.getSimpleElementValue(result, "inn"));
		payment.setReceiverKPP(XmlHelper.getSimpleElementValue(result, "kpp"));
	}

	private BusinessRecipientInfo getBusinessResipietnInfo(AccountPaymentSystemPayment payment) throws GateException, GateLogicException
	{
		BackRefReceiverInfoService receiverInfoService = getFactory().service(BackRefReceiverInfoService.class);
		BusinessRecipientInfo businessRecipientInfo = receiverInfoService.getRecipientInfo(payment.getReceiverPointCode(), payment.getService().getCode());
		if (businessRecipientInfo == null)
			throw new GateException("Не найдена дополнительная информация по поставщику услуг, код поставщика = " + payment.getReceiverPointCode() + ", код услуги = " + payment.getService().getCode());
		return businessRecipientInfo;
	}

	private Element getResultElement(Document document, String creditId) throws GateException
	{
		try
		{
			NodeList nodeList = XmlHelper.selectNodeList(document.getDocumentElement(), "//result/result_element");
			for (int i = 0; i < nodeList.getLength(); i++)
			{
				Element node = (Element) nodeList.item(i);
				if (creditId.equals(XmlHelper.getSimpleElementValue(node, "creditId")))
					return node;
			}

			//этот момент возможен только, если creditId изменен в ручную
			throw new GateException("Используется некорректное значения для поля creditId = " + creditId);
		}
		catch (TransformerException e)
		{
			throw new GateException(e);
		}
	}
}
