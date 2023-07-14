package com.rssl.phizgate.basket;

import com.rssl.phizgate.basket.generated.*;
import com.rssl.phizgate.common.basket.RequisitesHelper;
import com.rssl.phizgate.common.payments.offline.OfflineDocumentInfo;
import com.rssl.phizgate.common.payments.offline.OfflineDocumentInfoService;
import com.rssl.phizgate.common.payments.systems.recipients.CommonField;
import com.rssl.phizgate.common.payments.systems.recipients.FieldValidationRuleImpl;
import com.rssl.phizic.ApplicationAutoRefreshConfig;
import com.rssl.phizic.common.types.basket.InvoiceState;
import com.rssl.phizic.common.types.basket.InvoiceStatus;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.basket.InvoiceService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.systems.recipients.FieldDataType;
import com.rssl.phizic.gate.payments.systems.recipients.FieldValidationRule;
import com.rssl.phizic.gate.payments.systems.recipients.ListValue;
import com.rssl.phizic.gate.utils.ExternalSystemHelper;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.xml.jaxb.JAXBUtils;
import com.rssl.phizic.gate.payments.systems.recipients.Field;

import java.util.*;
import javax.jms.TextMessage;
import javax.xml.bind.JAXBException;

/**
 * @author vagin
 * @ created 26.04.14
 * @ $Author$
 * @ $Revision$
 * Хелпер работы с корзиной платежей
 */
public class BasketInvoiceHelper
{
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);

	/**
	 * Обработка сообщеня AddBillBasketInfoRq
	 * @param addBillBasketInfoMessage - входящее сообщение
	 * @param messageId - идентификатор сообщения
	 */
	public static void processAddBillBasketInfo(String addBillBasketInfoMessage, String messageId)
	{
		AddBillBasketInfoRq request = null;
		try
		{
			request = JAXBUtils.unmarshalBean(AddBillBasketInfoRq.class, addBillBasketInfoMessage);
		}
		catch (JAXBException e)
		{
			//пишем в лог, сделать ничего не можем
			log.error(e.getMessage(), e);
			return;
		}
		try
		{
			InvoiceService service = GateSingleton.getFactory().service(InvoiceService.class);
			if (request.getActionMode() == ActionModeType.DELETE)
			{
				service.deleteInvoice(request.getOperUID(), request.getAutoSubscriptionId().getAutoSubscriptionId() );
				sendTicketResponse(request.getRqUID(), request.getOperUID(), TicketStatusCode.OK, null, messageId);
				return;
			}

			PaymentStatusASAPType paymentStatus = request.getPayment().getPaymentInfo().getPaymentStatus();
			//если новый инвойс(задолженость)
			if (paymentStatus == PaymentStatusASAPType.NEW)
			{
				//и добавляем его в базу
				if (request.getActionMode() == ActionModeType.ADD)
					service.addInvoice(fillInvoice(request), request.getOperUID());
				else if (request.getActionMode() == ActionModeType.UPDATE)
					service.updateInvoice(fillInvoice(request), request.getOperUID());
				else
				{
					sendTicketResponse(request.getRqUID(), request.getOperUID(), TicketStatusCode.ERROR, "Ошибка разбора запроса AddBillBasketInfoRq : неверный ActionMode", messageId);
					return;
				}
				sendTicketResponse(request.getRqUID(), request.getOperUID(), TicketStatusCode.OK, null, messageId);
			}
			else
				updateDocumentStatus(request, messageId);
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			//отправляем квитанцию с кодом -100
			sendTicketResponse(request.getRqUID(), request.getOperUID(), TicketStatusCode.ERROR, e.getMessage(), messageId);
		}
	}

	/**
	 * Обработка ответа от АС "AutoPay" acceptBillBasketExecuteRs о принятии акцепта оплаты задолжености.
	 * @param acceptBillBasketExecuteRs - входящее сообщение.
	 */
	public static void processAcceptBillBasketExecute(String acceptBillBasketExecuteRs)
	{
		try
		{
			AcceptBillBasketExecuteRs response = JAXBUtils.unmarshalBean(AcceptBillBasketExecuteRs.class, acceptBillBasketExecuteRs);
			InvoiceService service = GateSingleton.getFactory().service(InvoiceService.class);
			String externalId = response.getRqUID(); //RqUID является externalID для платежа.
			String nonExecReasonDesc = response.getStatus().getStatusDesc();
			service.updatePaymentStatus(externalId, response.getStatus().getStatusCode(), nonExecReasonDesc);
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * Отправка сообщения об акцепте оплаты задолжености по выставленному счету.
	 * @param request - AcceptBillBasketExecuteRq
	 */
	public static void sendAcceptBillBasketExecuteRq(AcceptBillBasketExecuteRq request) throws Exception
	{
		//добавлеяем запись в БД офлайн документов
		Long nodeNumber = ConfigFactory.getConfig(ApplicationAutoRefreshConfig.class).getNodeNumber();
		OfflineDocumentInfo offlineDocumentInfo = new OfflineDocumentInfo(null, AcceptBillBasketExecuteRq.class.getCanonicalName(), nodeNumber, ExternalSystemHelper.getBasketAutoPaySystemCode());
		offlineDocumentInfo.setExternalId(request.getRqUID());
		OfflineDocumentInfoService.addOfflineDocumentInfo(offlineDocumentInfo);
		//отправляем сообщение.
		String requestString = JAXBUtils.marshalBean(request);
		AcceptBillBasketExecuteRqSender.send(requestString, request.getRqUID());
	}

	/**
	 * Отправка квитанции о принятии инвойсов AddBilBasketInfoRs
	 */
	public static void sendTicketResponse(String rqUID, String operUID, TicketStatusCode statusCode, String errorDescription, String messageId)
	{

		AddBillBasketInfoRs response = new AddBillBasketInfoRs();
		response.setOperUID(operUID);
		response.setRqTm(Calendar.getInstance());
		response.setRqUID(rqUID);
		StatusType statusType = new StatusType();
		statusType.setStatusCode(statusCode.getCode());
		if (statusCode != TicketStatusCode.OK)
		{
			statusType.setStatusDesc(errorDescription);
		}
		response.setStatus(statusType);

		try
		{
			String responseString = JAXBUtils.marshalBean(response);
			AddBillBasketInfoRsSender.send(responseString, rqUID, messageId);
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * Обновление статуса платежа по задолжености.
	 * Если платеж с указанным externalID не найден в платежах клиента - посылем квитанцию с кодом ошибки -122
	 */
	private static void updateDocumentStatus(AddBillBasketInfoRq request, String messageId)
	{
		try
		{
			InvoiceService service = GateSingleton.getFactory().service(InvoiceService.class);
			InvoiceState state = InvoiceState.valueOf(request.getPayment().getPaymentInfo().getPaymentStatus().value().toUpperCase());
			String externalId = request.getPayment().getPaymentId().getPaymentId();
			String nonExecReasonDesc = request.getPayment().getPaymentInfo().getExecStatus().getNonExecReasonDesc();
			String nonExecReasonCode = request.getPayment().getPaymentInfo().getExecStatus().getNonExecReasonCode();
			Calendar execPaymentDate = request.getPayment().getPaymentInfo().getExecStatus().getExecPaymentDate();
			service.updatePaymentState(externalId, state, nonExecReasonCode ,nonExecReasonDesc, execPaymentDate, request.getOperUID());
			sendTicketResponse(request.getRqUID(), request.getOperUID(), TicketStatusCode.OK, null, messageId);
		}
		catch (GateException e)
		{
			log.error(e.getMessage(), e);
			//общая ошибка обработки отправляем квитанцию с кодом -100
			sendTicketResponse(request.getRqUID(), request.getOperUID(), TicketStatusCode.ERROR, e.getMessage(), messageId);
		}
		catch (GateLogicException e)
		{
			log.error(e.getMessage(), e);
			//не найден платеж для обновления -122
			sendTicketResponse(request.getRqUID(), request.getOperUID(), TicketStatusCode.PAYMENT_NOT_FOUND, "В ЕРИБ не найден акцептованный клиентом платеж.", messageId);
		}
	}

	/**
	 * Заполнение новго инвойса данными из request
	 * @param request - AddBillBasketInfoRq
	 */
	private static GateInvoiceImpl fillInvoice(AddBillBasketInfoRq request) throws Exception
	{
		GateInvoiceImpl invoice = new GateInvoiceImpl();
		invoice.setAutoPayId(request.getPayment().getPaymentId().getPaymentId());
		invoice.setAutoPaySubscriptionId(request.getAutoSubscriptionId().getAutoSubscriptionId());   //внешний идентификатор подписки.
		invoice.setState(InvoiceStatus.valueOf(request.getPayment().getPaymentInfo().getPaymentStatus().value().toUpperCase()));    //NEW,CANCELED,DONE
		invoice.setStateDesc(request.getPayment().getPaymentInfo().getPaymentStatusDesc());
		invoice.setCommission(request.getPayment().getPaymentInfo().getCommission());
		invoice.setExecPaymentDate(request.getPayment().getPaymentInfo().getExecStatus().getExecPaymentDate());
		invoice.setNonExecReasonCode(request.getPayment().getPaymentInfo().getExecStatus().getNonExecReasonCode());
		invoice.setNonExecReasonDesc(request.getPayment().getPaymentInfo().getExecStatus().getNonExecReasonDesc());
		RecipientRecType recipientRec = request.getPayment().getRecipientRec();
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
		List<Field> requisites = requisitesAsFields(recipientRec.getRequisites().getRequisites());
		invoice.setRequisites(RequisitesHelper.serialize(requisites));
		invoice.setCardNumber(request.getPayment().getBankAcctRec().getCardAcctId().getCardNum());
		return invoice;
	}

	/**
	 * Адаптция jaxB реквизита к общему Field
	 * @param gateRequisites - реквизиты ПУ.
	 * @return List<Field>
	 */
	private static List<Field> requisitesAsFields(List<RequisiteType> gateRequisites) throws Exception
	{
		List<Field> listFields = new ArrayList<Field>();
		for (RequisiteType requisite : gateRequisites)
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
			result.setRequired(getNullBooleanValue(requisite.getIsRequired(), false));
			result.setMainSum(getNullBooleanValue(requisite.getIsSum(), false));
			result.setKey(getNullBooleanValue(requisite.getIsKey(), false));
			result.setEditable(getNullBooleanValue(requisite.getIsEditable(), false));
			result.setVisible(getNullBooleanValue(requisite.getIsVisible(), false));
			result.setRequiredForBill(getNullBooleanValue(requisite.getIsForBill(), false));
			result.setRequiredForConformation(getNullBooleanValue(requisite.getIncludeInSMS(), false));
			result.setSaveInTemplate(getNullBooleanValue(requisite.getSaveInTemplate(), false));
			result.setHideInConfirmation(getNullBooleanValue(requisite.getHideInConfirmation(), false));

			RequisiteTypesType requisiteTypes = requisite.getRequisiteTypes();
			if (requisiteTypes != null && requisite.getRequisiteTypes().getRequisiteTypes() != null)
			{
				List<String> requisiteTypeArray = requisite.getRequisiteTypes().getRequisiteTypes();
				List<com.rssl.phizic.common.types.RequisiteType> requisiteTypeList = new ArrayList<com.rssl.phizic.common.types.RequisiteType>();
				for (String requisiteType : requisiteTypeArray)
				{
					requisiteTypeList.add(com.rssl.phizic.common.types.RequisiteType.fromValue(requisiteType));
				}
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

	private static List<ListValue> convertToListValues(MenuType menu)
	{
		if (menu == null)
		{
			return null;
		}
		List<ListValue> result = new ArrayList<ListValue>();
		for (String item : menu.getMenuItems())
		{
			result.add(new ListValue(item, item));
		}
		return result;
	}

	private static Object processEnteredData(RequisiteType requisite) throws GateException
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

	private static boolean getNullBooleanValue(Boolean value, boolean defaultValue)
	{
		if (value == null)
		{
			return defaultValue;
		}
		return value;
	}
}
