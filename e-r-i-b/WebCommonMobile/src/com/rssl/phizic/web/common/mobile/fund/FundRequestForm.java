package com.rssl.phizic.web.common.mobile.fund;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.types.MoneyType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.business.fund.initiator.FundInitiatorResponse;
import com.rssl.phizic.business.fund.initiator.FundRequest;
import com.rssl.phizic.business.profile.addressbook.Contact;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.common.EditFormBase;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author usachev
 * @ created 09.12.14
 * @ $Author$
 * @ $Revision$
 *
 * Форма для работы с исходящими запросами на сбор средств
 */
public class FundRequestForm extends EditFormBase
{
	public static final Form CREATE_FORM = createForm();

	private static final String DATE_FORMAT = "dd.MM.yyyy";

	private FundRequest fundRequest;
	private List<FundInitiatorResponse> listFundInitiatorResponse;
	private Integer availableLimit;
	private Map<Long, Contact> contactsMap;
	private Map<String, String> profileAvatarMap;
	private BigDecimal accumulatedSum;

	/**
	 * Получить сущность "исходящий запрос на сбор средств"
	 * @return Запрос на сбор средств
	 */
	public FundRequest getFundRequest()
	{
		return fundRequest;
	}

	/**
	 * Установить сущность "исходящий запрос на сбор средств"
	 * @param fundRequest  Исходящий запрос на сбор средств
	 */
	public void setFundRequest(FundRequest fundRequest)
	{
		this.fundRequest = fundRequest;
	}

	/**
	 * Получение списка отправителей денег
	 * @return  Список отправителей денег
	 */
	public List<FundInitiatorResponse> getListFundInitiatorResponse()
	{
		return listFundInitiatorResponse;
	}

	/**
	 * Установить список отправителей денег
	 * @param listFundInitiatorResponse Список отправителей денег
	 */
	public void setListFundInitiatorResponse(List<FundInitiatorResponse> listFundInitiatorResponse)
	{
		this.listFundInitiatorResponse = listFundInitiatorResponse;
	}

	private static Form createForm()
	{
		DateParser dateParser = new DateParser(DATE_FORMAT);
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fb = new FieldBuilder();
		fb.setName("operation");
		fb.setType(StringType.INSTANCE.getName());
		fb.setDescription("Операция");
		fb.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("message");
		fb.setType(StringType.INSTANCE.getName());
		fb.setDescription("Сообщение отправителям");
		fb.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("closeDate");
		fb.setDescription("Плановая дата закрытия запроса");
		fb.setType("date");
		fb.clearValidators();

		DateFieldValidator minDateFieldValidator = new DateFieldValidator(DATE_FORMAT, "Дата планового закрытия запроса должна быть больше текущей даты.");
		minDateFieldValidator.setMinDate(DateHelper.addDays(DateHelper.getCurrentDate(), 1).getTime());

		fb.addValidators(new DateFieldValidator(DATE_FORMAT, "Время должно быть в формате ДД.ММ.ГГГГ"), minDateFieldValidator);
		fb.setParser(dateParser);
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("requiredSum");
		fb.setType(MoneyType.INSTANCE.getName());
		fb.setDescription("Общая необходимая сумма");
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("reccomendSum");
		fb.setType(MoneyType.INSTANCE.getName());
		fb.setDescription("Рекомендованная сумма");
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("resource");
		fb.setType(LongType.INSTANCE.getName());
		fb.setDescription("Карта зачисления");
		fb.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("phones");
		fb.setType(StringType.INSTANCE.getName());
		fb.setDescription("Список участников");
		RegexpFieldValidator regexpPhoneValidator = new MAPIPhoneNumberListValidator(fb.getName(),',');
		fb.addValidators(new RequiredFieldValidator(), regexpPhoneValidator);
		formBuilder.addField(fb.build());

		CompareValidator compareValidator = new CompareValidator();
		compareValidator.setParameter(CompareValidator.OPERATOR, CompareValidator.LESS_EQUAL);
		compareValidator.setBinding(CompareValidator.FIELD_O1, "reccomendSum");
		compareValidator.setBinding(CompareValidator.FIELD_O2, "requiredSum");
		compareValidator.setMessage("Рекомендуемая сумма не может превышать необходимую сумму сбора средств. Пожалуйста, измените параметры запроса.");
		formBuilder.setFormValidators(compareValidator);

		return formBuilder.build();
	}

	public Integer getAvailableLimit()
	{
		return availableLimit;
	}

	public void setAvailableLimit(Integer availableLimit)
	{
		this.availableLimit = availableLimit;
	}

	/**
	 * Получение Map'ы, содержащей аватары отправителей денег.
	 * @return Map'а аватаров.
	 */
	public Map<Long, Contact> getContactsMap()
	{
		return contactsMap;
	}

	/**
	 * Установка  Map'ы, содержащей аватары отправителей денег.
	 * @param avatarsMap Map'а аватаров.
	 */
	public void setContactsMap(Map<Long, Contact> avatarsMap)
	{
		this.contactsMap = avatarsMap;
	}

	/**
	 * Получение Map'ы аватаров клиентов, которых нету в адресной книги инициатора запроса, из профиля.
	 * @return   Map'а аватаров клиентов
	 */
	public Map<String, String> getProfileAvatarMap()
	{
		return profileAvatarMap;
	}

	/**
	 * Установка Map'ы аватаров клиентов, которых нету в адресной книги инициатора запроса, из профиля.
	 * @param profileAvatarMap Map'а аватаров клиентов
	 */
	public void setProfileAvatarMap(Map<String, String> profileAvatarMap)
	{
		this.profileAvatarMap = profileAvatarMap;
	}

	/**
	 * @return общая собранная сумма
	 */
	public BigDecimal getAccumulatedSum()
	{
		return accumulatedSum;
	}

	/**
	 * @param accumulatedSum общая собранная сумма
	 */
	public void setAccumulatedSum(BigDecimal accumulatedSum)
	{
		this.accumulatedSum = accumulatedSum;
	}
}
