package com.rssl.phizicgate.iqwave.documents;

import com.rssl.phizgate.common.payments.BillingPaymentHelper;
import com.rssl.phizgate.common.payments.systems.recipients.CommonField;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.gate.payments.systems.recipients.ListField;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.iqwave.messaging.Constants;
import org.w3c.dom.Document;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * @author niculichev
 * @ created 16.04.2013
 * @ $Author$
 * @ $Revision$
 */
public class AeroexpressPaymentSender extends IQWaveAbstractDocumentSender
{
	private static final String INVALID_DATE_MESSAGE = "Дата поездки не может быть меньше текущей даты.";
	private static final String INVALID_CHOICEPLACE_MESSAGE = "Выберите места для поездки.";
	private static final String PRE_SEND_INVALID_DATE_MESSAGE = "Дата поездки не может быть меньше текущей даты. Для покупки билета нажмите на кнопку «Отмена» и оформите новый заказ.»";
	private static final String INVALID_TYPE_DOCUMENT_MESSAGE = "Неверный тип платежа - ожидается CardPaymentSystemPayment";
	private static final String AGREEMENT_ERROR_MESSAGE = "Для того чтобы совершить оплату, ознакомьтесь с правилами оказания услуг, щелкнув по ссылке «правила». " +
			"Если Вы с правилами согласны, то установите флажок в поле «Я согласен с правилами покупки билетов» и нажмите на кнопку «Продолжить»";


	/**
	 * ctor
	 * @param factory - гейтовая фабрика
	 */
	public AeroexpressPaymentSender(GateFactory factory)
	{
		super(factory);
	}

	public void prepare(GateDocument document) throws GateException, GateLogicException
	{
		if (document.getType() != CardPaymentSystemPayment.class)
			throw new GateException(INVALID_TYPE_DOCUMENT_MESSAGE);

		CardPaymentSystemPayment payment = (CardPaymentSystemPayment) document;

		List<Field> extendedFields = BillingPaymentHelper.getExtendedFields(payment);
		Map<String, Field> extendedFieldsMap = BillingPaymentHelper.convertExtendedFieldsToMap(extendedFields);

		PrepareState state = formCurrentState(extendedFieldsMap);
		// состояние не удалось определить, возможно возникли ошибки, остаемся на текущей страницы
		if(state == null)
			return;

		// добавляем название группы если еще не добавляли
		if(!extendedFieldsMap.containsKey(AeroexpressHelper.INFO_ABOUT_TICKETS_GROUP_NAME))
		{
			extendedFields.add(0, RequestHelper.createDisableTextField(
					AeroexpressHelper.INFO_ABOUT_TICKETS_GROUP_NAME, "", "Информация о билетах", AeroexpressHelper.INFO_ABOUT_TICKETS_GROUP_NAME));
		}

		// в соответствии с состоянием добавляем поля
		switch (state)
		{
			case CHOICE_DATE:
			{
				extendedFields.add(AeroexpressHelper.createDateField());
				break;
			}

			case CHOICE_CITY:
			{
				extendedFields.add(AeroexpressHelper.createCityField());
				break;
			}

			case CHOICE_BRANCH:
			{
				Field date = extendedFieldsMap.get(AeroexpressHelper.DATE_FIELD_NAME);
				try
				{
					Document responce = AeroexpressHelper.sendAeroexpressScheduleRequest(
							DateHelper.parseCalendar((String) date.getValue()), null);
					AeroexpressHelper.fillPaymentByAeroexpressSchedule(payment, responce);
				}
				catch (ParseException e)
				{
					throw new GateException(e);
				}
				break;
			}

			case CHOICE_TARIF:
			{
				Document responce = AeroexpressHelper.sendAeroexpressPricelistRequest();
				AeroexpressHelper.fillPaymentByAeroexpressPricelist(payment, responce);
				break;
			}

			case CHOICE_TRIP_TIME:
			{
				Field date = extendedFieldsMap.get(AeroexpressHelper.DATE_FIELD_NAME);
				Field tarif = extendedFieldsMap.get(AeroexpressHelper.TARIF_FIELD_NAME);
				try
				{
					Document responce = AeroexpressHelper.sendAeroexpressFreeSeatsGlobalRequest(
							DateHelper.parseCalendar((String)date.getValue()), Integer.parseInt((String) tarif.getValue()));
					AeroexpressHelper.fillPaymentByAeroexpressFreeSeatsGlobal(payment, responce);
				}
				catch (ParseException e)
				{
					throw new GateException(e);
				}
				break;
			}

			case CHOICE_COUNT_PLACE:
			{
				ListField maxTicketsByTarifField = (ListField) extendedFieldsMap.get(AeroexpressHelper.TARIF_MAX_TICKETS_FIELD_NAME);
				Field priceId = extendedFieldsMap.get(AeroexpressHelper.TARIF_FIELD_NAME);

				extendedFields.add(AeroexpressHelper.createChoiceCountFreeSeatsField(
						Integer.parseInt(AeroexpressHelper.getValueByIdFromListField(maxTicketsByTarifField, (String) priceId.getValue()))));
				extendedFields.addAll(AeroexpressHelper.getContanctInfoFields());
				extendedFields.add(AeroexpressHelper.createAgreementField());
				break;
			}

			case CHOICE_PLACE:
			{
				Field date = extendedFieldsMap.get(AeroexpressHelper.DATE_FIELD_NAME);
				Field triptime = extendedFieldsMap.get(AeroexpressHelper.TRIP_TIME_FIELD_NAME);

				try
				{
					Document responce = AeroexpressHelper.sendAeroexpressFreeSeatsDetailRequest(
							DateHelper.parseCalendar((String) date.getValue()), Integer.parseInt((String) triptime.getValue()));
					AeroexpressHelper.fillPaymentByAeroexpressFreeSeatsDetail(payment, responce, extendedFieldsMap);
				}
				catch (ParseException e)
				{
					throw new GateException(e);
				}

				extendedFields.addAll(AeroexpressHelper.getContanctInfoFields());
				extendedFields.add(AeroexpressHelper.createAgreementField());
				break;
			}

			case COMPLETE:
			{
				Field date = extendedFieldsMap.get(AeroexpressHelper.DATE_FIELD_NAME);
				Field tarif = extendedFieldsMap.get(AeroexpressHelper.TARIF_FIELD_NAME);
				Field scheduleId = extendedFieldsMap.get(AeroexpressHelper.TRIP_TIME_FIELD_NAME);
				ListField orderTypeByPrice = (ListField) extendedFieldsMap.get(AeroexpressHelper.ORDER_TYPE_PRICE_FIELD_NAME);

				try
				{
					Document responce = AeroexpressHelper.sendAeroexpressGetTicketRequest(
							DateHelper.parseCalendar((String) date.getValue()),
							Integer.valueOf((String) tarif.getValue()),
							Integer.parseInt(AeroexpressHelper.getValueByIdFromListField(orderTypeByPrice, (String) tarif.getValue())),
							scheduleId != null ? Integer.valueOf((String) scheduleId.getValue()) : null,
							AeroexpressHelper.getSeats(extendedFieldsMap));
					AeroexpressHelper.fillPaymentByAeroexpressGetTicket(payment, responce);
				}
				catch (ParseException e)
				{
					throw new GateException(e);
				}
				// дополнительная обработка(по сути костыли для формы подтверждения)
				AeroexpressHelper.completeAdditionProcess(payment, extendedFieldsMap);

				//конец. устанавливаем идентификатор поля во внешней системе
				payment.setIdFromPaymentSystem(BillingPaymentHelper.generateIdFromPaymentSystem(payment));

				break;
			}
			default:
				throw new GateException("Неизвестное состояние подготовки платежа " + state);
		}
	}

	protected String getConfirmRequestName(GateDocument document)
	{
		return Constants.AEROEXPRESS_PAY_TICKETS_RESPONSE;
	}

	public void send(GateDocument document) throws GateException, GateLogicException
	{
		if (document.getType() != CardPaymentSystemPayment.class)
			throw new GateException(INVALID_TYPE_DOCUMENT_MESSAGE);

		CardPaymentSystemPayment payment = (CardPaymentSystemPayment) document;
		List<Field> extendedFields = BillingPaymentHelper.getExtendedFields(payment);
		// проверяем дату на корректность
		AeroexpressHelper.checkTravelDate(
				BillingPaymentHelper.getFieldById(extendedFields, AeroexpressHelper.DATE_FIELD_NAME), PRE_SEND_INVALID_DATE_MESSAGE);

		Document responce = AeroexpressHelper.sendExecutionRequest(payment);
		AeroexpressHelper.fillPaymentByExecutionResponce(responce, payment);

		payment.setExternalId(getExternalId(responce));
		payment.setIdFromPaymentSystem(null);
	}

	public void validate(GateDocument document) throws GateException, GateLogicException
	{
	}

	/**
	 * Определение текущего состояния
	 * @param extendedFields мап расширенных полей
	 * @return текущее состояние
	 * @throws GateException
	 */
	private PrepareState formCurrentState(Map<String, Field> extendedFields) throws GateException, GateLogicException
	{
		PrepareState state = PrepareState.CHOICE_DATE;

		CommonField date        = (CommonField) extendedFields.get(AeroexpressHelper.DATE_FIELD_NAME);
		CommonField city        = (CommonField) extendedFields.get(AeroexpressHelper.CITY_FIELD_NAME);
		CommonField route       = (CommonField) extendedFields.get(AeroexpressHelper.BRANCH_ROUTE_FIELD_NAME);
		CommonField tarif       = (CommonField) extendedFields.get(AeroexpressHelper.TARIF_FIELD_NAME);
		CommonField tripTime    = (CommonField) extendedFields.get(AeroexpressHelper.TRIP_TIME_FIELD_NAME);
		CommonField choicePlace = (CommonField) extendedFields.get(AeroexpressHelper.CHOICE_PLACE_FIELD_NAME);
		CommonField countPlace  = (CommonField) extendedFields.get(AeroexpressHelper.COUNT_PLACE_FIELD_NAME);
		CommonField phoneNumber = (CommonField) extendedFields.get(AeroexpressHelper.PHONE_NUMBER_FIELD_NAME);
		CommonField email       = (CommonField) extendedFields.get(AeroexpressHelper.EMAIL_FIELD_NAME);
		CommonField agreement   = (CommonField) extendedFields.get(AeroexpressHelper.AGREEMENT_FIELD_NAME);
		ListField tarifSeatsSelect = (ListField)extendedFields.get(AeroexpressHelper.TARIF_SEATS_SELECT_FIELD_NAME);

		if(date != null)
		{
			// проверяем дату на корректность
			AeroexpressHelper.checkTravelDate(date, INVALID_DATE_MESSAGE);
			date.setEditable(false);
			// если поле создали в поставщике, устаналваием ему группу
			date.setGroupName(AeroexpressHelper.INFO_ABOUT_TICKETS_GROUP_NAME);
			state = PrepareState.CHOICE_CITY;
		}
		if(city != null)
		{
			city.setEditable(false);
			// если поле создали в поставщике, устаналваием ему группу
			city.setGroupName(AeroexpressHelper.INFO_ABOUT_TICKETS_GROUP_NAME);
			state = PrepareState.CHOICE_BRANCH;
		}
		if(route != null)
		{
			state = PrepareState.CHOICE_TARIF;
			route.setEditable(false);
		}
		if(tarif != null)
		{
			tarif.setEditable(false);
			state = Boolean.parseBoolean(AeroexpressHelper.getValueByIdFromListField(tarifSeatsSelect, (String)tarif.getValue())) ?
					PrepareState.CHOICE_TRIP_TIME : PrepareState.CHOICE_COUNT_PLACE ;
		}
		if(tripTime != null)
		{
			tripTime.setEditable(false);
			state = PrepareState.CHOICE_PLACE;
		}
		if(choicePlace != null)
		{
			if(!Boolean.TRUE.toString().equals(agreement.getValue()))
			{
				agreement.setError(AGREEMENT_ERROR_MESSAGE);
				return null;
			}
			// убираем ошибка, если была установлена ранее
			agreement.setError(null);

			if(StringHelper.isEmpty((String) choicePlace.getValue()))
			{
				choicePlace.setError(INVALID_CHOICEPLACE_MESSAGE);
				return null;
			}
			// убираем ошибка, если была установлена ранее
			choicePlace.setError(null);

			email.setEditable(false);
			email.setVisible(StringHelper.isNotEmpty((String) email.getValue()));

			phoneNumber.setEditable(false);
			phoneNumber.setVisible(StringHelper.isNotEmpty((String) phoneNumber.getValue()));

			choicePlace.setEditable(false);

			state = PrepareState.COMPLETE;
		}
		if(countPlace != null)
		{
			if(!Boolean.TRUE.toString().equals(agreement.getValue()))
			{
				agreement.setError(AGREEMENT_ERROR_MESSAGE);
				return null;
			}
			// убираем ошибка, если была установлена ранее
			agreement.setError(null);

			email.setEditable(false);
			email.setVisible(StringHelper.isNotEmpty((String) email.getValue()));

			phoneNumber.setEditable(false);
			phoneNumber.setVisible(StringHelper.isNotEmpty((String) phoneNumber.getValue()));

			countPlace.setEditable(false);

			state = PrepareState.COMPLETE;
		}

		return state;
	}

	private enum PrepareState
	{
		/**
		 * Выбор даты
		 */
		CHOICE_DATE,

		/**
		 * Выбор города
		 */
		CHOICE_CITY,

		/**
		 * Выбор направления
		 */
		CHOICE_BRANCH,

		/**
		 * Выбор тарифа
		 */
		CHOICE_TARIF,

		/**
		 * Выбор рейса(время)
		 */
		CHOICE_TRIP_TIME,

		/**
		 * Выбор количества мест
		 */
		CHOICE_COUNT_PLACE,

		/**
		 * Выбор самих мест
		 */
		CHOICE_PLACE,

		/**
		 * Завершение подготовки
		 */
		COMPLETE
	}
}
