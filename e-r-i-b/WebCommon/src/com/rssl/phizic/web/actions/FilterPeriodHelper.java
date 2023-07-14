package com.rssl.phizic.web.actions;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.Expression;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.types.DateType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.common.types.Period;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.ListUtil;

import java.util.Calendar;
import java.util.Date;

/**
 * @author krenev
 * @ created 28.03.2013
 * @ $Author$
 * @ $Revision$
 * Хелпер для облегчения работы с периодами в фильтре.
 * см талес filterDataPeriod.
 */
public class FilterPeriodHelper
{
	public static final String PERIOD_TYPE_WEEK = "week"; // тип периода неделя назад от текущей даты
	public static final String PERIOD_TYPE_MONTH = "month"; // тип периода месяц назад от текущей даты
	public static final String PERIOD_TYPE_CUSTOM = "period"; // произвольно заданыый период(диапазон дат)

	public static final String PERIOD_TYPE_FIELD_NAME = "typeDate"; // имя поля, содержащего тип периода
	public static final String FROM_DATE_FIELD_NAME = "fromDate"; // имя поля, содержащего начало проивольного периода
	public static final String TO_DATE_FIELD_NAME = "toDate";   // имя поля, содержащего конец проивольного периода

	/**
	 * Создать формбилдер для валидации полей завязанных на период с дефолтным форматом даты "dd/MM/yyyy".
	 * @return билдер
	 */
	public static FormBuilder createFilterPeriodFormBuilder()
	{
		return createFilterPeriodFormBuilder("dd/MM/yyyy", "Введите дату в поле Период в формате ДД/ММ/ГГГГ.");
	}
	/**
	 * Создать формбилдер для валидации полей завязанных на период.
	 * @param format формат датф времени
	 * @param formatDateErrorMessage сообщение пользователю в случае, если введенная дата не удовлетворяет формату
	 * @return билдер
	 */
	public static FormBuilder createFilterPeriodFormBuilder(String format, String formatDateErrorMessage)
	{
		Expression periodDatesExpression = new RhinoExpression("form.typeDate == 'period'");
		DateParser dataParser = new DateParser(format);

		DateFieldValidator dataValidator = new DateFieldValidator(format, formatDateErrorMessage);
		dataValidator.setEnabledExpression(periodDatesExpression);

		RequiredFieldValidator requiredValidator = new RequiredFieldValidator();
		requiredValidator.setEnabledExpression(periodDatesExpression);

		DateNotInFutureValidator dateNotInFutureValidator = new DateNotInFutureValidator(format);
		dateNotInFutureValidator.setEnabledExpression(periodDatesExpression);

		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();

		fieldBuilder.setDescription("Тип периода");
		fieldBuilder.setName(PERIOD_TYPE_FIELD_NAME);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new ChooseValueValidator(ListUtil.fromArray(new String[]{PERIOD_TYPE_WEEK, PERIOD_TYPE_CUSTOM, PERIOD_TYPE_MONTH}))
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Период с");
		fieldBuilder.setName(FROM_DATE_FIELD_NAME);
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.setParser(dataParser);
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(requiredValidator, dataValidator, dateNotInFutureValidator);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Период по");
		fieldBuilder.setName(TO_DATE_FIELD_NAME);
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.setParser(dataParser);
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(requiredValidator, dataValidator);
		formBuilder.addField(fieldBuilder.build());

		CompareValidator compareValidator = new CompareValidator();
		compareValidator.setParameter(CompareValidator.OPERATOR, DateTimeCompareValidator.LESS_EQUAL);
		compareValidator.setBinding(CompareValidator.FIELD_O1, FROM_DATE_FIELD_NAME);
		compareValidator.setBinding(CompareValidator.FIELD_O2, TO_DATE_FIELD_NAME);
		compareValidator.setMessage("Конечная дата должна быть больше либо равна начальной!");
		compareValidator.setEnabledExpression(periodDatesExpression);

		formBuilder.addFormValidators(compareValidator);

		return formBuilder;
	}

	/**
	 * Создать формбилдер для валидации полей, завязанных на период без указания периода.
	 * @param defaultPeriodType дефолтный тип периода(используется в случае, если хотя бы одна из дат не задана)
	 * @return билдер
	 */
	public static FormBuilder createFilterPeriodFormBuilder(String defaultPeriodType)
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Период с");
		fieldBuilder.setName(FROM_DATE_FIELD_NAME);
		fieldBuilder.setType(DateType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Период по");
		fieldBuilder.setName(TO_DATE_FIELD_NAME);
		fieldBuilder.setType(DateType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Тип периода");
		fieldBuilder.setName(PERIOD_TYPE_FIELD_NAME);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setValueExpression(new RhinoExpression("(form.fromDate == null || form.toDate == null)?'"+defaultPeriodType+"':'period'"));
		formBuilder.addField(fieldBuilder.build());

		CompareValidator compareValidator = new CompareValidator();
		compareValidator.setParameter(CompareValidator.OPERATOR, DateTimeCompareValidator.LESS_EQUAL);
		compareValidator.setBinding(CompareValidator.FIELD_O1, FROM_DATE_FIELD_NAME);
		compareValidator.setBinding(CompareValidator.FIELD_O2, TO_DATE_FIELD_NAME);
		compareValidator.setMessage("Конечная дата должна быть больше либо равна начальной!");
		compareValidator.setEnabledExpression(new RhinoExpression("form.typeDate == 'period'"));

		formBuilder.addFormValidators(compareValidator);

		return formBuilder;
	}
	/**
	 * Получить период.
	 * @param periodType тип периода. Обязательный параметр. Для типа периода PERIOD_TYPE_CUSTOM обязательны второй и третий параметры
	 * @param fromDate начальная дата
	 * @param toDate конечная дата
	 * @return период.
	 */
	public static Period getPeriod(String periodType, Calendar fromDate, Calendar toDate)
	{
		if (periodType == null)
		{
			throw new IllegalArgumentException("Тип периода должен быть задан");
		}

		Calendar startDate = null;
		Calendar endDate = null;
		if (PERIOD_TYPE_CUSTOM.equals(periodType))
		{
			if (fromDate == null)
			{
				throw new IllegalArgumentException("Начальная дата должна быть задана");
			}
			if (toDate == null)
			{
				throw new IllegalArgumentException("Конечная дата должна быть задана");
			}
			if (fromDate.after(toDate))
			{
				throw new IllegalArgumentException("Конечная дата должна быть больше начальной");
			}
			startDate = (Calendar) fromDate.clone();
			endDate = (Calendar) toDate.clone();
		}
		else if (PERIOD_TYPE_MONTH.equals(periodType))
		{
			endDate = Calendar.getInstance();
			startDate = (Calendar) endDate.clone();
			startDate.add(Calendar.MONTH, -1);
		}
		else if (PERIOD_TYPE_WEEK.equals(periodType))
		{
			return getWeekPeriod();
		}
		else
		{
			throw new IllegalArgumentException("Некорректный тип периода " + periodType);
		}

		startDate = DateHelper.startOfDay(startDate);
		endDate = DateHelper.endOfDay(endDate);

		return new Period(startDate, endDate);
	}

	/**
	 * Получить период.
	 * @param periodType тип периода. Обязательный параметр. Для типа периода PERIOD_TYPE_CUSTOM обязательны второй и третий параметры
	 * @param fromDate начальная дата
	 * @param toDate конечная дата
	 * @return период.
	 */
	public static Period getPeriod(String periodType, Date fromDate, Date toDate)
	{
		return getPeriod(periodType, DateHelper.toCalendar(fromDate), DateHelper.toCalendar(toDate));
	}

	/**
	 * Получить недельный период
	 * @return период
	 */
	public static Period getWeekPeriod()
	{
		Calendar endDate = Calendar.getInstance();
		Calendar startDate = (Calendar) endDate.clone();
		startDate.add(Calendar.WEEK_OF_MONTH, -1);
		return new Period(DateHelper.startOfDay(startDate), DateHelper.endOfDay(endDate));
	}

}
