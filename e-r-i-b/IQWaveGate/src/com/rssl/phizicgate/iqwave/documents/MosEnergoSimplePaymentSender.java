package com.rssl.phizicgate.iqwave.documents;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizgate.common.payments.BillingPaymentHelper;
import com.rssl.phizgate.common.payments.systems.recipients.CommonField;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizicgate.iqwave.messaging.Constants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.rssl.phizicgate.iqwave.messaging.Constants.*;

/**
 * Оплата Мосэнерго  – отличается от типовой оплаты  типом сообщения и наличием обязательных полей Period,
 * LastIndication, CurrentIndication и необязательного поля TariffNumber
 *
 * Элемент	            Тип	            Комментарий	                    Кратность
 * <Route>	            RouteCode       Код сервиса (маршрута)	        [1]
 * <DebitCard>	        CardInf         Информация по карте списания	[1]
 * <RecIdentifier>	    Requisite       Номер налогового извещения	    [1]
 * <CurrCode>	        IsoCode         Валюта операции	                [1]
 * <Summa>	            Money           Cумма платежа (задолженности)	[1]
 * <Period>	            PayPeriod       Период мес/год	                [1]
 * <LastIndication>	    NC-10           Поле должно содержать           [1]
 *                                      фиксированное значение «001»	
 * <CurrentIndication>	Long            Текущее показание счетчика	    [1]
 * <TariffNumber>	    TariffNumb      Номер тарифа	                [0-1]
*/

public class MosEnergoSimplePaymentSender extends SimplePaymentSender
{
	private static final String TARIFF_VAR_OR_ZONA_EMPTY = "Нет";
	private static final Map<String, String> valuesCorrelation = new HashMap<String, String>();//Маппинг соответствий того, что ввел выбрал пользователь и что должно вставиться в сообщение

	static
	{
		valuesCorrelation.put(TARIFF_ZONA_DAY,      "1");
		valuesCorrelation.put(TARIFF_ZONA_NIGHT,    "2");
		valuesCorrelation.put(TARIFF_ZONA_PEAK,     "13");
		valuesCorrelation.put(TARIFF_ZONA_HALFPEAK, "15");
		valuesCorrelation.put(TARIFF_VAR_ONETARIFF, "1");
		valuesCorrelation.put(TARIFF_VAR_TWOTARIFF, "2");
		valuesCorrelation.put(TARIFF_VAR_MANYTARIFF,"3");
	}

	/**
	 * ctor
	 * @param factory - гейтовая фабрика
	 */
	public MosEnergoSimplePaymentSender(GateFactory factory)
	{
		super(factory);
	}

	protected Pair<String, String> getExecutionMessageName(GateDocument document)
	{
		return new Pair<String, String>(Constants.PAYMENT_MOSENERGO_REQUEST, Constants.PAYMENT_MOSENERGO_RESPONSE);
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
			List<Field> newExtendedFields = new ArrayList<Field>();

			//если дополнительное поле лицевой счет не найдено, то добавляем его
			CommonField identifierField = (CommonField) BillingPaymentHelper.getFieldById(extendedFields, Constants.REC_IDENTIFIER_FIELD_NAME);
			if (identifierField == null)
			{
				newExtendedFields.add(RequestHelper.createIdentifierField());
			}

			//если дополнительное поле период не найдено, то добавляем его
			if (BillingPaymentHelper.getFieldById(extendedFields, Constants.PERIOD_FIELD_NAME) == null)
			{
				newExtendedFields.add(RequestHelper.createPeriodField());
			}

			//если дополнительное поле предыдущее показание счетчика не найдено, то добавляем его
			if (BillingPaymentHelper.getFieldById(extendedFields, Constants.LAST_INDICATION_FIELD_NAME) == null)
			{
				newExtendedFields.add(RequestHelper.createIndicationFieldField());
			}

			//если дополнительное поле текущее состояние счетчика не найдено, то добавляем его
			if (BillingPaymentHelper.getFieldById(extendedFields, Constants.CURRENT_INDICATION_FIELD_NAME) == null)
			{
				newExtendedFields.add(RequestHelper.createCurrentIndicationField());
			}

			//если дополнительное поле вариант тарифа не найдено, то добавляем его
			CommonField tarifVersionField = (CommonField) BillingPaymentHelper.getFieldById(extendedFields, Constants.TARIFF_VAR_FIELD_NAME);
			if (tarifVersionField == null)
			{
				newExtendedFields.add(RequestHelper.createTarifVersionField());
			}
			else
			{
				tarifVersionField.setEditable(false);
			}

			//показываем форму клиенту
			if (!newExtendedFields.isEmpty())
			{
				extendedFields.addAll(newExtendedFields);
				return;
			}

			//добавляем поле зона суток
			CommonField tarifZoneField = (CommonField) BillingPaymentHelper.getFieldById(extendedFields, Constants.TARIFF_ZONE_FIELD_NAME);
			if (tarifZoneField == null)
			{
				newExtendedFields.add(RequestHelper.createTarifZone((String) tarifVersionField.getValue()));
			}

			//если дополнительное поле сумма не найдено, то добавляем его
			if (BillingPaymentHelper.getMainSumField(extendedFields) == null)
			{
				newExtendedFields.add(BillingPaymentHelper.createAmountField());
			}

			//показываем форму клиенту
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

	protected void fillExecutionMessage(GateMessage message, CardPaymentSystemPayment payment) throws GateException, GateLogicException
	{
		super.fillExecutionMessage(message, payment);

		try
		{
			//Период задолженности
			Field period = BillingPaymentHelper.getFieldById(payment.getExtendedFields(), Constants.PERIOD_FIELD_NAME);
			RequestHelper.appendPeriod(message, "Period", (String) period.getValue());
			//Предыдущее показание счетчика
			message.addParameter(Constants.LAST_INDICATION_FIELD_NAME, getAttribute(payment, Constants.LAST_INDICATION_FIELD_NAME));
			//Текущее состояние счетчика
			message.addParameter(Constants.CURRENT_INDICATION_FIELD_NAME, getAttribute(payment, Constants.CURRENT_INDICATION_FIELD_NAME));
			//Номер тарифа
			Field tariffVar = BillingPaymentHelper.getFieldById(payment.getExtendedFields(), Constants.TARIFF_VAR_FIELD_NAME);
			if (tariffVar != null && !tariffVar.getValue().equals(TARIFF_VAR_OR_ZONA_EMPTY))
			{
				Field tariffZone = BillingPaymentHelper.getFieldById(payment.getExtendedFields(), Constants.TARIFF_ZONE_FIELD_NAME);
				appendTariffNumber(message, tariffVar.getValue(), tariffZone.getValue());
			}
		}
		catch(DocumentException e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * Добавить номер тарифа
	 *  TariffNumb Номер тарифа при оплате Мосэнерго
	 *      TariffVar	NC-2 Вариант тарифа	[1]
	 *      TariffZone	NC-2 Зона суток	    [1]
	 *
	 * @param message сообщение.
	 * @param tariffVar значение поля Вариант тарифа
	 * @param tariffZone занчение поля Зона суток
	 */
	private void appendTariffNumber(GateMessage message, Object tariffVar, Object tariffZone) throws GateException
	{
		Document requestDoc = message.getDocument();
		Element element = requestDoc.getDocumentElement();
		Element tariffNumber = XmlHelper.appendSimpleElement(element, Constants.TARIFF_NUMBER_FIELD_NAME);
		XmlHelper.appendSimpleElement(tariffNumber, Constants.TARIFF_VAR_FIELD_NAME, valuesCorrelation.get((String) tariffVar));
		XmlHelper.appendSimpleElement(tariffNumber, Constants.TARIFF_ZONE_FIELD_NAME, valuesCorrelation.get((String) tariffZone));
	}

	public void validate(GateDocument document) throws GateException, GateLogicException
	{
		try
		{
			CardPaymentSystemPayment payment = (CardPaymentSystemPayment) document;
			Field tariffVar = BillingPaymentHelper.getFieldById(payment.getExtendedFields(), Constants.TARIFF_VAR_FIELD_NAME);
			//Поле необязательное, если его нет у поставщика или оно не заполнено, то не валидируем
			if (tariffVar != null && !tariffVar.getValue().equals(TARIFF_VAR_OR_ZONA_EMPTY))
			{
				String tarifVarValue = (String) tariffVar.getValue();
				//Проверяем, что доступные значения поля "Вариант тарифа" удовлетворяют принятым за константы
				if (!tarifVarValue.equals(TARIFF_VAR_ONETARIFF) && !tarifVarValue.equals(TARIFF_VAR_TWOTARIFF) &&
							!tarifVarValue.equals(TARIFF_VAR_MANYTARIFF) && !tarifVarValue.equals(TARIFF_VAR_OR_ZONA_EMPTY))
						throw new GateException("Значения поля 'Вариант тарифа' не соответствуют допустимым. Должны быть: '" + TARIFF_VAR_ONETARIFF + "','" +
								TARIFF_VAR_TWOTARIFF + "','" + TARIFF_VAR_MANYTARIFF + "' и '" + TARIFF_VAR_OR_ZONA_EMPTY + "'");

				Field tariffZona = BillingPaymentHelper.getFieldById(payment.getExtendedFields(), Constants.TARIFF_ZONE_FIELD_NAME);
				//Если вариант тарифа выбран, то поле "Зона суток" должно быть заполнено
				if (tariffZona == null)
					throw new GateException("Не найден атрибут " + Constants.TARIFF_VAR_FIELD_NAME);
				else if ((tariffZona.getValue()).equals(TARIFF_VAR_OR_ZONA_EMPTY))
					throw new GateLogicException("Введите значение в поле 'Зона суток'");
				else
				{
					String tariffZonaValue = (String) tariffZona.getValue();
					//Проверяем, что доступные значения поля "Зоня суток" удовлетворяют принятым за константы
					if (!tariffZonaValue.equals(TARIFF_ZONA_DAY) && !tariffZonaValue.equals(TARIFF_ZONA_NIGHT) &&
							!tariffZonaValue.equals(TARIFF_ZONA_PEAK) && !tariffZonaValue.equals(TARIFF_ZONA_HALFPEAK) && !tariffZonaValue.equals(TARIFF_VAR_OR_ZONA_EMPTY))
						throw new GateException("Значения поля 'Зона суток' не соответствуют допустимым. Должны быть: '" + TARIFF_ZONA_DAY + "','" +
								TARIFF_ZONA_NIGHT + "','" + TARIFF_ZONA_PEAK + "','" + TARIFF_ZONA_HALFPEAK + "' и '" + TARIFF_VAR_OR_ZONA_EMPTY + "'");

					//Если однотарифный – то платеж идет по существующей ветке (с указанием на каждой фазе в
					//дополнительных реквизитах  в соответствующем поле признак «1(день)») с заполнением периода платежа и показания счетчиков
					if (tarifVarValue.equals(TARIFF_VAR_ONETARIFF) && !tariffZonaValue.equals(TARIFF_ZONA_DAY))
						throw new GateLogicException("Вы неправильно указали зону суток. Для данного тарифа можно выбрать только значение «" + TARIFF_ZONA_DAY + "»");

					//Если двухтарифный – то далее клиенту предлагается выбрать за какую зону суток он платит (день/ночь), после выбора платеж
					//идет по существующей ветке (с указанием на каждой фазе в дополнительных реквизитах  в соответствующем поле признак «1(день)/2(ночь)»)
					//с заполнением периода платежа и показания счетчиков
					if (tarifVarValue.equals(TARIFF_VAR_TWOTARIFF) && !(tariffZonaValue.equals(TARIFF_ZONA_DAY) || tariffZonaValue.equals(TARIFF_ZONA_NIGHT)))
						throw new GateLogicException("Вы неправильно указали зону суток. Для данного тарифа нужно выбрать значения «" + TARIFF_ZONA_DAY + "» или «" + TARIFF_ZONA_NIGHT + "»");
				}
			}
		}
		catch(DocumentException e)
		{
			throw new GateException(e);
		}
	}

	//Для обязательных полей
	private Object getAttribute(CardPaymentSystemPayment payment, String attributeName) throws GateException
	{
		Field pair;
		try
		{
			pair = BillingPaymentHelper.getFieldById(payment.getExtendedFields(), attributeName);
		}
		catch(DocumentException e)
		{
			throw new GateException(e);
		}
		if (pair == null)
		{
			throw new GateException("Не найден атрибут " + attributeName);
		}
		return pair.getValue();
	}
}
