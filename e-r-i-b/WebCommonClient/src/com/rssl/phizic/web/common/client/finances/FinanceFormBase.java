package com.rssl.phizic.web.common.client.finances;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.Expression;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.utils.ListUtil;
import com.rssl.phizic.web.common.FilterActionForm;
import com.rssl.phizic.web.component.DatePeriodFilter;
import com.rssl.phizic.operations.finances.FinancesStatus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author rydvanskiy
 * @ created 02.08.2011
 * @ $Author$
 * @ $Revision$
 */

@SuppressWarnings({"AbstractClassExtendsConcreteClass"})
public abstract class FinanceFormBase extends FilterActionForm
{

	public static final String INCOME_TYPE = "income";
	public static final String OUTCOME_TYPE = "outcome";

	protected static final long MAX_PERIOD = 366 * 24 * 60 * 60 * 1000L; // 366 дней или чуть больше, чем года
	public static final Form FILTER_FORM  = createFilterForm();

	
	// дата последнего обновления операций
	private Calendar lastModified;
	private List data;

	private FinancesStatus financesStatus;
	private String activePagesCategory;

	private boolean hasErrors;

	public boolean isHasErrors()
	{
		return hasErrors;
	}

	public void setHasErrors(boolean hasErrors)
	{
		this.hasErrors = hasErrors;
	}

	/**
	 * @return статус АЛФ
	 */
	public FinancesStatus getFinancesStatus()
	{
		return financesStatus;
	}

	/**
	 * задать статус АЛФ
	 * @param financesStatus статус АЛФ
	 */
	public void setFinancesStatus(FinancesStatus financesStatus)
	{
		this.financesStatus = financesStatus;
	}

	/**
	 * @return дата последнего обновления операций
	 */
	public Calendar getLastModified()
	{
		return lastModified;
	}

	/**
	 * задать дата последнего обновления операций
	 * @param lastModified дата последнего обновления операций
	 */
	public void setLastModified(Calendar lastModified)
	{
		this.lastModified = lastModified;
	}

	/**
	 * Установить данные списка
	 * @param data данные списка
	 */
	public void setData(List data)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.data = data;
	}

	/**
	 * @return данные списка
	 */
	public List getData()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return data;
	}

	/**
	 * @return к какой категории страниц (операции или категории операций) относится данная страница
	 */
	public String getActivePagesCategory()
	{
		return activePagesCategory;
	}

	/**
	 * задать категорию
	 * @param activePagesCategory к какой категории страниц (операции или категории операций) относится данная страница
	 */
	public void setActivePagesCategory(String activePagesCategory)
	{
		this.activePagesCategory = activePagesCategory;
	}

	/**
	 * метод для создания формы фильтра
	 * @return логическая форма
	 */
	@SuppressWarnings({"OverlyLongMethod"})
	protected static Form createFilterForm()
	{
	    List<Field> fields = new ArrayList<Field>();
		@SuppressWarnings({"TooBroadScope"})
		FieldBuilder fieldBuilder;

		Expression periodDatesExpression = new RhinoExpression("form."+DatePeriodFilter.TYPE_PERIOD+" == '"+DatePeriodFilter.TYPE_PERIOD_PERIOD+"' ");
		String format = "dd/MM/yyyy";
		DateParser dataParser = new DateParser(format);

		DateFieldValidator dataValidator = new DateFieldValidator(format);
        dataValidator.setEnabledExpression(periodDatesExpression);
		dataValidator.setMessage("Введите дату в поле Период в формате ДД/ММ/ГГГГ.");

		RequiredFieldValidator requiredValidator = new  RequiredFieldValidator();
		requiredValidator.setEnabledExpression(periodDatesExpression);
		// Период
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Тип периода");
		fieldBuilder.setName(DatePeriodFilter.TYPE_PERIOD);
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new ChooseValueValidator(ListUtil.fromArray(new String[]{DatePeriodFilter.TYPE_PERIOD_MONTH,
						DatePeriodFilter.TYPE_PERIOD_PERIOD, DatePeriodFilter.TYPE_PERIOD_WEEK}))
		);
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(DatePeriodFilter.FROM_DATE);
		fieldBuilder.setDescription("Период");
		fieldBuilder.setType("date");
		fieldBuilder.setParser(dataParser);
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(requiredValidator, dataValidator);
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(DatePeriodFilter.TO_DATE);
		fieldBuilder.setDescription("Период");
		fieldBuilder.setType("date");
		fieldBuilder.setParser(dataParser);
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(requiredValidator, dataValidator);
		fields.add(fieldBuilder.build());

		// показать операции совещённые по доп картам к чужим счетам
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("showOtherAccounts");
		fieldBuilder.setDescription("показать операции совещённые по дополнительным картам к чужим счетам");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fields.add(fieldBuilder.build());

		// Тип операции
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Тип операции");
		fieldBuilder.setName("incomeType");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new ChooseValueValidator(ListUtil.fromArray(new String[]{INCOME_TYPE, OUTCOME_TYPE}))
		);
		fields.add(fieldBuilder.build());

		// включать операции с наличными
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("showCash");
		fieldBuilder.setDescription("показать операции совещённые за наличные");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fields.add(fieldBuilder.build());

		FormBuilder formBuilder = new FormBuilder();
		formBuilder.setFields(fields);


		CompareValidator compareDateValidator = new CompareValidator();
		compareDateValidator.setParameter(CompareValidator.OPERATOR, CompareValidator.LESS);
		compareDateValidator.setBinding(CompareValidator.FIELD_O1, DatePeriodFilter.FROM_DATE);
		compareDateValidator.setBinding(CompareValidator.FIELD_O2, DatePeriodFilter.TO_DATE);
		compareDateValidator.setMessage("Конечная дата должна быть больше начальной!");

		DatePeriodMultiFieldValidator datePeriodValidator = new DatePeriodMultiFieldValidator();
		datePeriodValidator.setMaxTimeSpan(MAX_PERIOD);
		datePeriodValidator.setBinding(DatePeriodMultiFieldValidator.FIELD_DATE_FROM, DatePeriodFilter.FROM_DATE);
		datePeriodValidator.setBinding(DatePeriodMultiFieldValidator.FIELD_DATE_TO, DatePeriodFilter.TO_DATE);
		datePeriodValidator.setMessage("Период для формирования графической выписки должен быть не более года."
				+ " Пожалуйста, выберите другой период." );

		formBuilder.setFormValidators(compareDateValidator, datePeriodValidator);

		return formBuilder.build();
	}

}
