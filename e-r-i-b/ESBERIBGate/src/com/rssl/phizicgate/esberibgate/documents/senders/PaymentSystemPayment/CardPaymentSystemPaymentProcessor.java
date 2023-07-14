package com.rssl.phizicgate.esberibgate.documents.senders.PaymentSystemPayment;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizgate.common.payments.BillingPaymentHelper;
import com.rssl.phizgate.common.payments.systems.recipients.CommonField;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.gate.payments.systems.recipients.FieldDataType;
import com.rssl.phizicgate.esberibgate.ws.jms.ESBSegment;
import com.rssl.phizicgate.esberibgate.ws.jms.common.message.OnlineMessageProcessor;
import com.rssl.phizicgate.esberibgate.ws.jms.common.message.OnlineMessageProcessorBase;
import com.rssl.phizicgate.esberibgate.ws.jms.common.message.Request;
import com.rssl.phizicgate.esberibgate.ws.jms.common.message.Response;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.light.ExternalIdGenerator;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.light.RequestHelper;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.light.generated.SimplePaymentRequest;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.light.generated.SimplePaymentResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author akrenev
 * @ created 16.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * Билдер запроса "Оплата товаров и услуг с карты."
 */

class CardPaymentSystemPaymentProcessor extends OnlineMessageProcessorBase<SimplePaymentResponse>
{
	private static final String OK_ERROR_CODE = "0";

	private static final String INTERNAL_MESSAGE_TYPE    = "SimplePaymentRequest";
	private static final String EXTERNAL_MESSAGE_TYPE    = "SimplePaymentRequest";
	private static final String MONITORING_DOCUMENT_TYPE = Request.SKIP_MONITORING;
	private static final String SYSTEM_ID                = "light-esb";
	private static final String REC_IDENTIFIER           = "RecIdentifier";
	private static final String DEFAULT_AMOUNT_FIELD_ID  = "amount";
	private static final Pattern DIG_CODE_PATTERN        = Pattern.compile("([^@]*)@.*");
	private static final int DIG_CODE_GROUP_INDEX        = 1;

	private final CardPaymentSystemPayment document;
	private SimplePaymentRequest request;

	/**
	 * конструктор
	 * @param document документ
	 * @param serviceName имя сервиса
	 */
	CardPaymentSystemPaymentProcessor(CardPaymentSystemPayment document, String serviceName)
	{
		super(ESBSegment.light, serviceName);
		this.document = document;
	}

	@Override
	protected String getRequestId()
	{
		return request.getHead().getMessUID().getMessageId();
	}

	@Override
	protected String getRequestSystemId()
	{
		return SYSTEM_ID;
	}

	@Override
	protected String getRequestMessageType()
	{
		return INTERNAL_MESSAGE_TYPE;
	}

	@Override
	protected String getMonitoringDocumentType()
	{
		return MONITORING_DOCUMENT_TYPE;
	}

	@Override
	protected Object initialize() throws GateException, GateLogicException
	{
		request = buildRequestObject(document);

		document.setExternalId(ExternalIdGenerator.generateExternalId(request));

		return request;
	}

	@Override
	protected String getResponseId(SimplePaymentResponse response)
	{
		return response.getHead().getMessUID().getMessageId();
	}

	@Override
	protected String getResponseErrorCode(SimplePaymentResponse response)
	{
		return response.getHead().getError().getErrCode();
	}

	@Override
	protected String getResponseErrorMessage(SimplePaymentResponse response)
	{
		return response.getHead().getError().getErrMes();
	}

	@Override
	protected void processResponse(Request<OnlineMessageProcessor<SimplePaymentResponse>> request, Response<SimplePaymentResponse> response) throws GateException, GateLogicException
	{
		if (!OK_ERROR_CODE.equals(response.getErrorCode()))
			processError(request, response);

		//затираем это значение, в IqWave не нужно отображать в чеке номер операции во внешней системе.
		document.setIdFromPaymentSystem(null);
	}

	private SimplePaymentRequest buildRequestObject(CardPaymentSystemPayment document) throws GateException, GateLogicException
	{
		SimplePaymentRequest cardToCardRequest = new SimplePaymentRequest();

		cardToCardRequest.setHead(RequestHelper.getHeader(EXTERNAL_MESSAGE_TYPE));
		cardToCardRequest.setBody(getBody(document));
		cardToCardRequest.setSign(RequestHelper.getSign());

		return cardToCardRequest;
	}

	private SimplePaymentRequest.Body getBody(CardPaymentSystemPayment document) throws GateException
	{
		Money amount = getAmount(document);

		SimplePaymentRequest.Body body = new SimplePaymentRequest.Body();

		body.setRoute(getRoute(document));
		body.setDebitCard(getDebitCard(document));
		body.setCurrCode(RequestHelper.getCurrency(amount));
		body.setRecIdentifier(getRecIdentifier(document));
		body.setSumma(RequestHelper.getMoney(amount));

		return body;
	}

	private Money getAmount(CardPaymentSystemPayment document)
	{
		return document.getChargeOffAmount() == null ? document.getDestinationAmount() : document.getChargeOffAmount();
	}

	private SimplePaymentRequest.Body.Route getRoute(CardPaymentSystemPayment document) throws GateException
	{
		SimplePaymentRequest.Body.Route route = new SimplePaymentRequest.Body.Route();

		String receiverPointCode = document.getReceiverPointCode();
		Matcher matcher = DIG_CODE_PATTERN.matcher(receiverPointCode);
		if (!matcher.find())
			throw new GateException("Ошибка получения DigCode для " + receiverPointCode);

		route.setDigCode(matcher.group(DIG_CODE_GROUP_INDEX));

		return route;
	}

	private SimplePaymentRequest.Body.DebitCard getDebitCard(CardPaymentSystemPayment document)
	{
		SimplePaymentRequest.Body.DebitCard debitCard = new SimplePaymentRequest.Body.DebitCard();
		debitCard.setCardNumber(document.getChargeOffCard());
		debitCard.setEndDate(RequestHelper.getDate(document.getChargeOffCardExpireDate()));
		return debitCard;
	}

	private String getRecIdentifier(CardPaymentSystemPayment document) throws GateException
	{
		Field field = null;
		try
		{
			field = BillingPaymentHelper.getFieldById(document.getExtendedFields(), REC_IDENTIFIER);
		}
		catch (DocumentException e)
		{
			throw new GateException(e);
		}
		if (field == null)
		{
			throw new GateException("Не найден атрибут " + REC_IDENTIFIER + ", идентифицирующи плательщика");
		}
		return String.valueOf(field.getValue());
	}

	/**
	 * Возвращает недостающие поля платежа
	 * @param extendedFields - список полей, которые уже есть в платеже
	 * @return список полей, которые нужно добавить в платёж
	 */
	static List<Field> getNewExtendedFields(List<Field> extendedFields) throws GateException
	{
		List<Field> newExtendedFields = new ArrayList<Field>();

		//если дополнительное поле лицевой счет не найдено и не шаблон, то добавляем его
		Field identifierField = BillingPaymentHelper.getFieldById(extendedFields, REC_IDENTIFIER);
		if (identifierField == null)
		{
			newExtendedFields.add(createIdentifierField());
		}

		//если дополнительное поле сумма не найдено и не шаблон, то добавляем его
		if (BillingPaymentHelper.getMainSumField(extendedFields) == null)
		{
			newExtendedFields.add(createAmountField());
		}

		return newExtendedFields;
	}

	private static Field createIdentifierField()
	{
		CommonField field = new CommonField();
		field.setType(FieldDataType.string);
		field.setExternalId(REC_IDENTIFIER);
		field.setName("Лицевой счет");
		field.setRequired(true);
		field.setEditable(true);
		field.setVisible(true);
		field.setRequiredForBill(true);
		field.setSaveInTemplate(true);
		return field;
	}

	private static CommonField createAmountField()
	{
		CommonField field = new CommonField();
		field.setType(FieldDataType.money);
		field.setExternalId(DEFAULT_AMOUNT_FIELD_ID);
		field.setName("Сумма");
		field.setRequired(true);
		field.setEditable(true);
		field.setVisible(true);
		field.setMainSum(true);
		field.setDefaultValue(null);
		return field;
	}
}
