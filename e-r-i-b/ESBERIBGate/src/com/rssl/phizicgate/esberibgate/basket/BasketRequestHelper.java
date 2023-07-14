package com.rssl.phizicgate.esberibgate.basket;

import com.rssl.phizgate.basket.BasketProxyListenerActiveRestriction;
import com.rssl.phizgate.basket.BasketProxyLoggerHelper;
import com.rssl.phizgate.basket.GateInvoiceImpl;
import com.rssl.phizgate.basket.TicketStatusCode;
import com.rssl.phizgate.basket.generated.PaymentIdType;
import com.rssl.phizgate.common.basket.RequisitesHelper;
import com.rssl.phizgate.common.payments.systems.recipients.CommonField;
import com.rssl.phizgate.common.payments.systems.recipients.FieldValidationRuleImpl;
import com.rssl.phizic.BasketPaymentsListenerConfig;
import com.rssl.phizic.common.types.basket.InvoiceStatus;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.basket.GateInvoice;
import com.rssl.phizic.gate.clients.Address;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.gate.clients.DocumentTypeComparator;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.basket.InvoiceAcceptPayment;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.gate.payments.systems.recipients.FieldDataType;
import com.rssl.phizic.gate.payments.systems.recipients.FieldValidationRule;
import com.rssl.phizic.gate.payments.systems.recipients.ListValue;
import com.rssl.phizic.gate.utils.EntityCompositeId;
import com.rssl.phizic.utils.BooleanHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.jms.JmsService;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizicgate.esberibgate.messaging.RequestHelperBase;
import com.rssl.phizicgate.esberibgate.types.CardTypeWrapper;
import com.rssl.phizicgate.esberibgate.ws.generated.SPName_Type;
import com.rssl.phizicgate.esberibgate.ws.jms.ESBSegment;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.RequestHelper;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.*;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;
import javax.jms.JMSException;

/**
 * @author osminin
 * @ created 09.06.14
 * @ $Author$
 * @ $Revision$
 *
 * Хэлпер для работы с запросами по функционалу "корзина платежей"
 */
public class BasketRequestHelper extends RequestHelperBase
{
	private static final String SYSTEM_ID = SPName_Type.AUTOPAY.getValue();
	private static final JmsService jmsService = new JmsService();

	/**
	 * ctor
	 * @param factory фабрика
	 */
	public BasketRequestHelper(GateFactory factory)
	{
		super(factory);
	}

	/**
	 * Создать запрос на оплату задолженности для взаимодейтсвия напрямую с автопэй
	 * @param document документ
	 * @return запрос
	 */
	public com.rssl.phizgate.basket.generated.AcceptBillBasketExecuteRq createAutoPayAcceptRequest(InvoiceAcceptPayment document) throws GateException, GateLogicException
	{
		com.rssl.phizgate.basket.generated.AcceptBillBasketExecuteRq request =
				new com.rssl.phizgate.basket.generated.AcceptBillBasketExecuteRq();

		request.setRqUID(generateUUID());
		request.setRqTm(Calendar.getInstance());
		request.setOperUID(generateOUUID());
		request.setSPName(SPName_Type.BP_ERIB.getValue());
		request.setBankInfo(createAutoPayBankInfo(document));
		request.setAutoSubscriptionId(createSubscriptionId(document));
		request.setPaymentId(createPaymentId(document));

		return request;
	}

	/**
	 * Создать запрос на оплату задолженности для взаимодействия через шину
	 * @param document документ
	 * @return запрос
	 */
	public AcceptBillBasketExecuteRq createESBAcceptRequest(InvoiceAcceptPayment document) throws GateException, GateLogicException
	{
		AcceptBillBasketExecuteRq request = new AcceptBillBasketExecuteRq();
		request.setRqUID(generateUUID());
		request.setRqTm(generateRqTm());
		request.setOperUID(generateOUUID());
		request.setSPName(SPNameType.BP_ERIB);
		request.setBankInfo(createESBBankInfo(document));
		request.setAutoSubscriptionID(createESBSubscriptionId(document));
		request.setAutoPaymentId(createESBPaymentId(document));
		request.setCardAcctId(createESBCardAcctId(document));
		return request;
	}

	private PaymentIdType createPaymentId(InvoiceAcceptPayment document)
	{
		PaymentIdType paymentIdType = new PaymentIdType();
		paymentIdType.setSystemId(SYSTEM_ID);
		paymentIdType.setPaymentId(document.getAutoInvoiceId());

		return paymentIdType;
	}

	private com.rssl.phizgate.basket.generated.AutoSubscriptionIdType createSubscriptionId(InvoiceAcceptPayment document)
	{
		com.rssl.phizgate.basket.generated.AutoSubscriptionIdType subscriptionIdType = new com.rssl.phizgate.basket.generated.AutoSubscriptionIdType();
		subscriptionIdType.setSystemId(SYSTEM_ID);
		subscriptionIdType.setAutoSubscriptionId(document.getAutoSubscriptionId());
		return subscriptionIdType;
	}

	private com.rssl.phizgate.basket.generated.AcceptBillBasketExecuteRq.BankInfo createAutoPayBankInfo(InvoiceAcceptPayment document) throws GateException, GateLogicException
	{
		com.rssl.phizgate.basket.generated.AcceptBillBasketExecuteRq.BankInfo bankInfo =
				new com.rssl.phizgate.basket.generated.AcceptBillBasketExecuteRq.BankInfo();
		bankInfo.setRbTbBrchId(getRbTbBrch(document.getInternalOwnerId()));
		return bankInfo;
	}

	private AutoSubscriptionIdType createESBSubscriptionId(InvoiceAcceptPayment document)
	{
		AutoSubscriptionIdType subscriptionIdType = new AutoSubscriptionIdType();
		subscriptionIdType.setSystemId(SYSTEM_ID);
		subscriptionIdType.setAutopayId(Long.parseLong(document.getAutoSubscriptionId()));

		return subscriptionIdType;
	}

	private AutoPaymentIdType createESBPaymentId(InvoiceAcceptPayment document)
	{
		AutoPaymentIdType paymentIdType = new AutoPaymentIdType();
		paymentIdType.setSystemId(SYSTEM_ID);
		paymentIdType.setPaymentId(document.getAutoInvoiceId());

		return paymentIdType;
	}

	private AcceptBillBasketExecuteRq.BankInfo createESBBankInfo(InvoiceAcceptPayment document) throws GateException, GateLogicException
	{
		AcceptBillBasketExecuteRq.BankInfo bankInfo = new AcceptBillBasketExecuteRq.BankInfo();
		String tb = getRbTbBrch(document.getInternalOwnerId()).substring(0,2);
		bankInfo.setRbTbBrchId(StringHelper.appendLeadingZeros(tb, 8));
		return bankInfo;
	}

	private AcceptBillBasketExecuteRq.CardAcctId createESBCardAcctId(InvoiceAcceptPayment document) throws GateException, GateLogicException
	{
		AcceptBillBasketExecuteRq.CardAcctId cardAcctId = new AcceptBillBasketExecuteRq.CardAcctId();
		Client owner = getBusinessOwner(document);
		Card card = getCard(owner, document.getChargeOffCard(), document.getOffice());

		EntityCompositeId compositeId = RequestHelper.getCardCompositeId(card, owner, getBackRefInfoRequestHelper());
		cardAcctId.setSystemId(compositeId.getSystemId());
		cardAcctId.setAcctId(card.getPrimaryAccountNumber());
		cardAcctId.setCardNum(card.getNumber());
		cardAcctId.setCardType(card.getCardType() != null ? CardTypeWrapper.getCardTypeForRequest(card.getCardType()) : "");
		cardAcctId.setEndDt(getStringDate(card.getExpireDate()));
		cardAcctId.setCardLevel(card.getCardLevel().name());
		cardAcctId.setAcctCur(card.getCurrency().getCode());

		List<? extends ClientDocument> documents = owner.getDocuments();
		if (CollectionUtils.isEmpty(documents))
			throw new GateException("Не найден документ клиента id = " + owner.getId());
		Collections.sort(documents, new DocumentTypeComparator());

		FullAddressType fullAddress = new FullAddressType();
		Address address = owner.getLegalAddress();
		fullAddress.setAddr3(StringHelper.getEmptyIfNull(address.getUnparseableAddress()));
		fullAddress.setAddrType("1");

		ContactInfoType contactInfo = new ContactInfoType();
		contactInfo.getPostAddrs().add(fullAddress);

		PersonInfoType personInfo = RequestHelper.getPersonInfo(owner, documents.get(0));
		personInfo.getIdentityCard().setExpDt(null); // этого параметра быть не должно
		personInfo.setContactInfo(contactInfo);

		CustInfoType custInfo = new CustInfoType();
		custInfo.setPersonInfo(personInfo);
		cardAcctId.setCustInfo(custInfo);

		BankInfoType bankInfo = new BankInfoType();
		bankInfo.setRbBrchId(compositeId.getRbBrchId());
		cardAcctId.setBankInfo(bankInfo);

		return cardAcctId;
	}

	public static PaymentStatusInfoListType createPaymentStatusInfo(PaymentListType payment, TicketStatusCode statusCode, String statusDesc)
	{
		AutoPaymentIdType paymentIdRq = payment.getAutoPaymentId();

		AutoPaymentIdType paymentId = new AutoPaymentIdType();
		paymentId.setPaymentId(paymentIdRq.getPaymentId());
		paymentId.setSystemId(paymentIdRq.getSystemId());

		PaymentStatusInfoListType rs = new PaymentStatusInfoListType();
		rs.setAutoPaymentId(paymentId);
		rs.setStatus(createStatusType(statusCode, statusDesc));

		return rs;
	}

	public static StatusType createStatusType(TicketStatusCode statusCode, String statusDesc)
	{
		StatusType status = new StatusType();
		status.setStatusCode(statusCode.getCode());
		status.setStatusDesc(statusDesc);

		return status;
	}

	public static AddBillBasketInfoRs createAddBillBasketInfoRs(AddBillBasketInfoRq rq)
	{
		AddBillBasketInfoRs rs = new AddBillBasketInfoRs();
		rs.setOperUID(rq.getOperUID());
		rs.setSystemId(rq.getSystemId());
		rs.setRqUID(rq.getRqUID());
		rs.setSPName(rq.getSPName());
		rs.setRqTm(XMLDatatypeHelper.formatDateTimeWithoutTimeZone(Calendar.getInstance()));

		return rs;
	}

	/**
	 * Отправить ответ в очередь
	 * @param rs ответ
	 * @param rqUID уид запроса
	 * @param messageId jmsCorrelationID
	 * @throws GateException
	 */
	public static void sendResponce(AddBillBasketInfoRs rs, String rqUID, String messageId) throws GateException
	{
		//если отключено настройкой - кидаем исключение.
		if(!BasketProxyListenerActiveRestriction.check())
			throw new GateException("Операция временно недоступна. Повторите попытку позже.");

		BasketPaymentsListenerConfig config = ConfigFactory.getConfig(BasketPaymentsListenerConfig.class);
		String message = ESBSegment.federal.getMessageBuilder().buildMessage(rs);

		try
		{
			BasketProxyLoggerHelper.writeToLog(message, rqUID, rs.getClass().getSimpleName());
			jmsService.sendMessageToQueue(message, config.getJMSESBResponceQuery(), config.getJMSESBResponceFactory(), null, messageId);
		}
		catch (JMSException e)
		{
			throw new GateException(e);
		}
	}

	public static GateInvoice convertInvoice(PaymentListType paymentType, String externalAutosubId) throws GateException
	{
		GateInvoiceImpl invoice = new GateInvoiceImpl();

		AutoPaymentIdType autoPaymentIdType = paymentType.getAutoPaymentId();
		invoice.setAutoPayId(autoPaymentIdType.getPaymentId());
		invoice.setAutoPaySubscriptionId(externalAutosubId);   //внешний идентификатор подписки.
		invoice.setState(InvoiceStatus.NEW);    //NEW,CANCELED,DONE

		PaymentInfoType paymentInfo = paymentType.getPaymentInfo();
		invoice.setStateDesc(paymentInfo.getPaymentStatusDesc());
		invoice.setCommission(paymentInfo.getCommission());
		invoice.setExecPaymentDate(XMLDatatypeHelper.parseDateTime(paymentInfo.getExecStatus().getExecPaymentDate()));
		invoice.setNonExecReasonCode(paymentInfo.getExecStatus().getNonExecReasonCode());
		invoice.setNonExecReasonDesc(paymentInfo.getExecStatus().getNonExecReasonDesc());

		RecipientRec recipientRec = paymentType.getRecipientRec();
		invoice.setCodeRecipientBs(recipientRec.getCodeRecipientBS());
		invoice.setRecName(recipientRec.getName());
		invoice.setCodeService(recipientRec.getCodeService());
		invoice.setNameService(recipientRec.getNameService());
		invoice.setRecInn(recipientRec.getTaxId());  //ИНН
		invoice.setRecCorAccount(recipientRec.getCorrAccount());
		invoice.setRecKpp(recipientRec.getKPP());
		invoice.setRecBic(recipientRec.getBIC());
		invoice.setRecAccount(recipientRec.getAcctId());
		invoice.setRecNameOnBill(recipientRec.getNameOnBill());
		invoice.setRecPhoneNumber(recipientRec.getPhoneToClient());
		invoice.setRecTb(recipientRec.getBankInfo().getRegionId());
		invoice.setCardNumber(paymentType.getBankAcctRec().getCardAcctId().getCardNum());

		try
		{
			List<com.rssl.phizic.gate.payments.systems.recipients.Field> requisites =
					requisitesAsFields(recipientRec.getRequisites().getRequisites());
			invoice.setRequisites(RequisitesHelper.serialize(requisites));
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}

		return invoice;
	}

	private static List<com.rssl.phizic.gate.payments.systems.recipients.Field> requisitesAsFields(List<Requisite> gateRequisites) throws Exception
	{
		List<com.rssl.phizic.gate.payments.systems.recipients.Field> listFields = new ArrayList<Field>();
		for (Requisite requisite : gateRequisites)
		{
			FieldDataType type = FieldDataType.fromValue(requisite.getType());
			CommonField result = new CommonField();
			result.setName(requisite.getNameVisible());
			result.setExternalId(requisite.getNameBS());
			result.setHint(requisite.getDescription());
			result.setDescription(requisite.getComment());
			result.setType(type);
			if (type == FieldDataType.number)
			{
				result.setNumberPrecision(requisite.getNumberPrecision() != null ? requisite.getNumberPrecision().intValue() : null);
			}
			result.setRequired(BooleanHelper.getNullBooleanValue(requisite.getIsRequired(), false));
			result.setMainSum(BooleanHelper.getNullBooleanValue(requisite.getIsSum(), false));
			result.setKey(BooleanHelper.getNullBooleanValue(requisite.getIsKey(), false));
			result.setEditable(BooleanHelper.getNullBooleanValue(requisite.getIsEditable(), false));
			result.setVisible(BooleanHelper.getNullBooleanValue(requisite.getIsVisible(), false));
			result.setRequiredForBill(BooleanHelper.getNullBooleanValue(requisite.getIsForBill(), false));
			result.setRequiredForConformation(BooleanHelper.getNullBooleanValue(requisite.getIncludeInSMS(), false));
			result.setSaveInTemplate(BooleanHelper.getNullBooleanValue(requisite.getSaveInTemplate(), false));
			result.setHideInConfirmation(BooleanHelper.getNullBooleanValue(requisite.getHideInConfirmation(), false));

			RequisiteTypesType requisiteTypes = requisite.getRequisiteTypes();
			if (requisiteTypes != null && requisite.getRequisiteTypes().getRequisiteTypes() != null)
			{
				List<String> requisiteTypeArray = requisite.getRequisiteTypes().getRequisiteTypes();
				List<com.rssl.phizic.common.types.RequisiteType> requisiteTypeList = new ArrayList<com.rssl.phizic.common.types.RequisiteType>();
				for (String requisiteType : requisiteTypeArray)
					requisiteTypeList.add(com.rssl.phizic.common.types.RequisiteType.fromValue(requisiteType));

				result.setRequisiteTypes(requisiteTypeList);
			}

			AttributeLengthType length = requisite.getAttributeLength();
			if (length != null)
			{
				result.setMaxLength(length.getMaxLength() != null ? length.getMaxLength().longValue() : null);
				result.setMinLength(length.getMinLength() != null ? length.getMinLength().longValue() : null);
			}

			result.setFieldValidationRules(processValidators(requisite.getValidators()));

			if (type == FieldDataType.list || type == FieldDataType.set)
			{
				result.setValues(convertToListValues(requisite.getMenu()));
			}
			result.setValue(processEnteredData(requisite));
			result.setDefaultValue(requisite.getDefaultValue());
			result.setError(requisite.getError());
			listFields.add(result);
		}

		return listFields;
	}

	private static List<FieldValidationRule> processValidators(ValidatorsType validators)
	{
		if (validators == null)
		{
			return null;
		}
		List<FieldValidationRule> result = new ArrayList<FieldValidationRule>();
		for (ValidatorType validator : validators.getValidators())
		{
			result.add(processValidator(validator));
		}
		return result;
	}

	private static FieldValidationRule processValidator(ValidatorType validator)
	{
		FieldValidationRuleImpl result = new FieldValidationRuleImpl();
		result.setErrorMessage(validator.getMessage());
		result.setFieldValidationRuleType(validator.getType());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(validator.getType(), validator.getParameter());
		result.setParameters(map);
		return result;
	}

	private static List<ListValue> convertToListValues(Menu menu)
	{
		if (menu == null)
			return null;

		List<ListValue> result = new ArrayList<ListValue>();
		for (String item : menu.getMenuItems())
			result.add(new ListValue(item, item));

		return result;
	}

	private static Object processEnteredData(Requisite requisite) throws GateException
	{
		FieldDataType type = FieldDataType.fromValue(requisite.getType());
		EnteredDataType data = requisite.getEnteredData();
		if (data == null || data.getDataItems() == null)
		{
			return null;
		}
		if (data.getDataItems().size() == 1)
		{
			return data.getDataItems().get(0);
		}
		if (type == FieldDataType.set)
		{
			//значения для типа set передаются в виде листа данных
			StringBuilder values = new StringBuilder();
			values.append(data.getDataItems().get(0));

			for (int i = 1; i < data.getDataItems().size(); i++)
			{
				values.append("@").append(data.getDataItems().get(i));
			}

			return values.toString();
		}
		if (data.getDataItems().size() > 1)
		{
			throw new GateException("Для поля с типом " + type + " пришло " + data.getDataItems() + " значений");
		}
		return null;
	}
}
