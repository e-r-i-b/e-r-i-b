package com.rssl.phizic.web.common.socialApi.finances.operations;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.types.DateType;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.DateFieldValidator;
import com.rssl.common.forms.validators.DateInYearBeforeCurrentDateValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.common.client.finances.ajax.GetListOfOperationsForm;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dorzhinov
 * @ created 03.10.2013
 * @ $Author$
 * @ $Revision$
 */
public class ListFinanceOperationsMobileForm extends GetListOfOperationsForm
{
	public static final Form FORM = getFilterForm();

	private String from; //Дата начала построения списка операций
	private String to; //Дата окончания построения списка операций
	private String categoryId; //Идентификатор категории
	private String[] selectedCardId; //Идентификатор карты
	private String income; //Признак приходной операции
	private String hidden; //Режим отображения скрытых операций
	private String country; //Страна совершения операции
	private String showCash; //Показать внесение/выдачу наличных
	private String showOtherAccounts; //Показывать ли операции по дополнительным картам к чужим счетам
	private int paginationSize; //Размер результирующей выборки
	private int paginationOffset; //Смещение относительно начала выборки
	private String[] excludeCategories; //исключаемые категории операций
	private String showCashPayments; //Отображать траты наличными

	public String getFrom()
	{
		return from;
	}

	public void setFrom(String from)
	{
		this.from = from;
	}

	public String getTo()
	{
		return to;
	}

	public void setTo(String to)
	{
		this.to = to;
	}

	public String getCategoryId()
	{
		return categoryId;
	}

	public void setCategoryId(String categoryId)
	{
		this.categoryId = categoryId;
	}

	public String[] getSelectedCardId()
	{
		return selectedCardId;
	}

	public void setSelectedCardId(String[] selectedCardId)
	{
		this.selectedCardId = selectedCardId;
	}

	public String getIncome()
	{
		return income;
	}

	public void setIncome(String income)
	{
		this.income = income;
	}

	public String getShowCash()
	{
		return showCash;
	}

	public void setShowCash(String showCash)
	{
		this.showCash = showCash;
	}

	public String getShowOtherAccounts()
	{
		return showOtherAccounts;
	}

	public void setShowOtherAccounts(String showOtherAccounts)
	{
		this.showOtherAccounts = showOtherAccounts;
	}

	public int getPaginationSize()
	{
		return paginationSize;
	}

	public void setPaginationSize(int paginationSize)
	{
		this.paginationSize = paginationSize;
	}

	public int getPaginationOffset()
	{
		return paginationOffset;
	}

	public void setPaginationOffset(int paginationOffset)
	{
		this.paginationOffset = paginationOffset;
	}

	public String[] getExcludeCategories()
	{
		return excludeCategories;
	}

	public void setExcludeCategories(String[] excludeCategories)
	{
		this.excludeCategories = excludeCategories;
	}

	/**
	 * @return true - отобразить траты наличными
	 */
	public String getShowCashPayments()
	{
		return showCashPayments;
	}

	/**
	 * @param showCashPayments - отображать ли траты наличными
	 */
	public void setShowCashPayments(String showCashPayments)
	{
		this.showCashPayments = showCashPayments;
	}

	/**
	 * @return режим отображения скрытых операций
	 */
	public String getHidden()
	{
		return hidden;
	}

	/**
	 * Установить режим отображения скрытых операций
	 * @param hidden null- все операции, true - только скрытые, false - только видимые
	 */
	public void setHidden(String hidden)
	{
		this.hidden = hidden;
	}

	/**
	 * @return страна совершения операции, iso3166-1 3х символьный код
	 */
	public String getCountry()
	{
		return country;
	}

	/**
	 * Фильтрация по стране совершения операции. null - не фильтровать
	 * @param country страна совершения операции, iso3166-1 3х символьный код
	 */
	public void setCountry(String country)
	{
		this.country = country;
	}

	private static Form getFilterForm()
	{
		List<Field> fields = new ArrayList<Field>();
		FieldBuilder fieldBuilder;

		DateFieldValidator datePeriodValidator = new DateFieldValidator();
		datePeriodValidator.setMessage("Введите дату в поле Период в формате ДД.ММ.ГГГГ");

		DateParser dataParser = new DateParser();

		DateInYearBeforeCurrentDateValidator dateFieldValidator = new DateInYearBeforeCurrentDateValidator();
		dateFieldValidator.setMessage("Вы можете просмотреть движение средств только за последний год.");

		RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("from");
		fieldBuilder.setDescription("Период c");
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.setParser(dataParser);
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(requiredFieldValidator, datePeriodValidator, dateFieldValidator);
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("to");
		fieldBuilder.setDescription("Период по");
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.setParser(dataParser);
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(requiredFieldValidator, datePeriodValidator, dateFieldValidator);
		fields.add(fieldBuilder.build());

		// идентификатор категории операций
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("categoryId");
		fieldBuilder.setDescription("Идентификатор категории операций");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fields.add(fieldBuilder.build());

		// идентификаторы карт
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("selectedCardIds");
		fieldBuilder.setDescription("Идентификатор карты");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fields.add(fieldBuilder.build());

		// доход / расход
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Приходная операция");
		fieldBuilder.setName("income");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.addValidators(requiredFieldValidator);
		fields.add(fieldBuilder.build());

		// включать операции с наличными
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("showCash");
		fieldBuilder.setDescription("Показать внесение/выдачу наличных");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.addValidators(requiredFieldValidator);
		fields.add(fieldBuilder.build());

		// показать операции совершенные по доп картам к чужим счетам
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("showOtherAccounts");
		fieldBuilder.setDescription("Показать операции совершенные по дополнительным картам к чужим счетам");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.addValidators(requiredFieldValidator);
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("excludeCategories");
		fieldBuilder.setDescription("Исключаемые категории операций");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("showCashPayments");
		fieldBuilder.setDescription("Отображать траты наличными");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.addValidators(requiredFieldValidator);
		fields.add(fieldBuilder.build());

		FormBuilder formBuilder = new FormBuilder();
		formBuilder.setFields(fields);

		return formBuilder.build();
	}
}
