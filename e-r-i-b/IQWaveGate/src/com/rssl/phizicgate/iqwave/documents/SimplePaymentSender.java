package com.rssl.phizicgate.iqwave.documents;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizgate.common.payments.BillingPaymentHelper;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.WithdrawDocument;
import com.rssl.phizic.gate.documents.WithdrawMode;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.iqwave.messaging.Constants;
import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author krenev
 * @ created 12.05.2010
 * @ $Author$
 * @ $Revision$
 * Типовая оплата
Элемент	        Тип	            Комментарий	                            Кратность
<Route>	        RouteCode       Код сервиса (маршрута)	                [1]
<DebitCard>	    CardInf         Информация по карте списания	        [1]
<CurrCode>	    IsoCode         Валюта операции	                        [1]
<RecIdentifier>	Requisite       Реквизит, идентифицирующий плательщика	[1]
<Summa>	        Money           Cумма платежа	                        [1]
<MBOperCode>    MBOperCode      Код операции в МБ                       [0-1]
 */
public class SimplePaymentSender extends PaymentSystemPaymentSenderBase
{
	/**
	 * ctor
	 * @param factory - гейтовая фабрика
	 */
	public SimplePaymentSender(GateFactory factory)
	{
		super(factory);
	}

	protected Pair<String, String> getExecutionMessageName(GateDocument document)
	{
		CardPaymentSystemPayment payment = (CardPaymentSystemPayment) document;
		if (payment.isEinvoicing())
			return new Pair<String, String>(Constants.SIMPLE_PAYMENT_ECOMMERCE_REQUEST, Constants.SIMPLE_PAYMENT_ECOMMERCE_RESPONSE);
		else
			return new Pair<String, String>(Constants.SIMPLE_PAYMENT_REQUEST, Constants.SIMPLE_PAYMENT_RESPONSE);
	}

	protected String getDebtMessageName()
	{
		throw new UnsupportedOperationException();
	}

	protected void addExtendedFieldsToDebtRequest(GateMessage message, List<Field> extendedFields) throws GateException
	{

	}

	public void prepare(GateDocument document) throws GateException, GateLogicException
	{
		if (document.getType() != CardPaymentSystemPayment.class)
			throw new GateException("Неверный тип платежа - ожидается CardPaymentSystemPayment");

		CardPaymentSystemPayment payment = (CardPaymentSystemPayment) document;

		if (StringHelper.isEmpty(payment.getChargeOffCard())
				|| payment.getChargeOffCardExpireDate() == null)
			throw new GateException("Поставщик поддерживает только карточные переводы");

		try
		{
			List<Field> extendedFields    = payment.getExtendedFields();
			List<Field> newExtendedFields = getNewExtendedFields(extendedFields);

			//показываем форму клиеннту
			if (!newExtendedFields.isEmpty())
			{
				extendedFields.addAll(newExtendedFields);
				return;
			}

			//конец. устанавливаем идентификатор поля во внешней системе
			payment.setIdFromPaymentSystem(BillingPaymentHelper.generateIdFromPaymentSystem(payment));
		}
		catch (DocumentException e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * Возвращает недостающие поля платежа
	 * @param extendedFields - список полей, которые уже есть в платеже
	 * @return список полей, которые нужно добавить в платёж
	 */
	protected List<Field> getNewExtendedFields(List<Field> extendedFields) throws GateException
	{
		List<Field> newExtendedFields = new ArrayList<Field>();

		//если дополнительное поле лицевой счет не найдено и не шаблон, то добавляем его
		Field identifierField = BillingPaymentHelper.getFieldById(extendedFields, Constants.REC_IDENTIFIER_FIELD_NAME);
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

	/**
	 * Создаёт поле с идентификатором плательщика (номер лицевого счёта и т.п.)
	 * @return
	 */
	protected Field createIdentifierField()
	{
		return RequestHelper.createIdentifierField();
	}

	/**
	 * @return поле главной суммы
	 */
	protected Field createAmountField()
	{
		return BillingPaymentHelper.createAmountField();
	}

	/**
	 * Заполнить сообщение на исполение платежа
	 * @param message сообщение
	 * @param payment платеж
	 * @throws GateException
	 */
	protected void fillExecutionMessage(GateMessage message, CardPaymentSystemPayment payment) throws GateException, GateLogicException
	{
		Calendar offCardExpireDate = payment.getChargeOffCardExpireDate();
		String chargeOffCard = payment.getChargeOffCard();
		String pointCode = payment.getReceiverPointCode();
		Money amount = payment.getDestinationAmount();

		//Код сервиса (маршрута)
		RequestHelper.appendRouteCode(message, "Route", Long.valueOf(pointCode));
		//Информация по карте списания
		RequestHelper.appendCardInf(message, chargeOffCard, offCardExpireDate);
		//Валюта операции
		message.addParameter("CurrCode", amount.getCurrency().getCode());
		//Реквизит, идентифицирующий плательщика
		message.addParameter(Constants.REC_IDENTIFIER_FIELD_NAME, getIdentifier(payment));
		//Cумма платежа
		RequestHelper.appendSumma(message, amount, "Summa");
	    //Код мобильного банка
	    fillMBOperCodeField(message, payment);
}

	public void validate(GateDocument document) throws GateException, GateLogicException
	{
		//валидировать ничего не надо
	}

	/**
	 * Отзыв платежа/возврат товара
	 *
	 * <Route>	RouteCode Код сервиса (маршрута)	[1]
	 * <CurrCode>	IsoCode Валюта операции	[1]
	 * <RecIdentifier>	Requisite Реквизит, идентифицирующий плательщика	[1]
	 * <OperationIdentifier>	Requisite Идентификатор исходной операции IQWAVE	[1]
	 * <Summa>	Money Cумма возврата	[1]
	 * <MBOperCode>    MBOperCode  Код операции в МБ  [0-1]
	 * @param withdrawDocument - документ отзыва
	 */
	public void rollback(WithdrawDocument withdrawDocument) throws GateException, GateLogicException
	{
		if (withdrawDocument.getWithdrawType() != CardPaymentSystemPayment.class)
		{
			throw new GateException("Неверный тип отзываемого платежа - ожидается CardPaymentSystemPayment");
		}

		CardPaymentSystemPayment cardPaymentSystemPayment = (CardPaymentSystemPayment) (withdrawDocument).getTransferPayment();
		if (StringHelper.isEmpty(cardPaymentSystemPayment.getIdFromPaymentSystem()))
			throw new GateException("Не установлен идентификатор отзываемого платежа");

		WebBankServiceFacade serviceFacade   = GateSingleton.getFactory().service(WebBankServiceFacade.class);

		String requestName = withdrawDocument.getWithdrawMode() == WithdrawMode.Partial ? Constants.REFUND_SIMPLE_PAYMENT_REQUEST
																						: Constants.REVERSAL_SIMPLE_PAYMENT_REQUEST;
		GateMessage message = serviceFacade.createRequest(requestName);

		Money withdrawAmount = withdrawDocument.getChargeOffAmount();
		String pointCode = cardPaymentSystemPayment.getReceiverPointCode();

		//Код сервиса (маршрута)
		RequestHelper.appendRouteCode(message, Constants.ROUTE_TAG_NAME, Long.valueOf(pointCode));
		//Валюта операции
		message.addParameter(Constants.CURR_CODE, withdrawAmount.getCurrency().getCode());
		//Реквизит, идентифицирующий плательщика
		message.addParameter(Constants.REC_IDENTIFIER_FIELD_NAME, getIdentifier(cardPaymentSystemPayment));
		//Идентификатор исходной операции IQWAVE
		message.addParameter(Constants.OPERATIOIN_IDENTIFIER, cardPaymentSystemPayment.getIdFromPaymentSystem());
		//Cумма платежа
		RequestHelper.appendSumma(message, withdrawAmount, Constants.SUMMA_TEG);
	    //Код мобильного банка
	    fillMBOperCodeField(message, withdrawDocument);
		Document response = serviceFacade.sendOnlineMessage(message, null);

		String externalId = getExternalId(response);
		withdrawDocument.setExternalId(externalId);

		addOfflineDocumentInfo(withdrawDocument);
	}
}
