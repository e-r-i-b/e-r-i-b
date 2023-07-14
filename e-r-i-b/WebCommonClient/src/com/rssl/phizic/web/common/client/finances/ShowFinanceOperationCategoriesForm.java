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
import com.rssl.phizic.business.dictionaries.finances.CardOperationCategory;
import com.rssl.phizic.business.dictionaries.finances.CardOperationCategoryGraphAbstract;
import com.rssl.phizic.business.finances.MonthBalanceByOperations;
import com.rssl.phizic.business.tree.TreeNode;
import com.rssl.phizic.utils.ListUtil;
import com.rssl.phizic.web.component.MonthPeriodFilter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author mihaylov
 * @ created 15.10.2012
 * @ $Author$
 * @ $Revision$
 */
public class ShowFinanceOperationCategoriesForm extends FinanceFormBase
{
	private static final String DATE_FORMAT = "dd/MM/yyyy";

	private BigDecimal income; // доход по всем операциям
	private BigDecimal outcome; // расход по всем операциям
	private BigDecimal maxVal; // максимальное значение среди дохода и расхода за период (необходимо для построения графика)
	private List<MonthBalanceByOperations> monthList; // список дохода и расхода по месяцам

	private List<CardOperationCategoryGraphAbstract> outcomeOperations; //списания по категориям

	private List<CardOperationCategory> outcomeCategories; //расходные категории

	private TreeNode node; // дерево карт
	private String selectedId; // id карты/группа карт, по которой отображать расходы

	private boolean readAllHints = false; // Признак прочитаны ли подсказки в бюджетировании

	private Calendar openPageDate; // Дата открытия страницы с категориями расходов. Относительно этой даты отображаются операции (запрос CHG058580)

	public BigDecimal getIncome()
	{
		return income;
	}

	public void setIncome(BigDecimal income)
	{
		this.income = income;
	}

	public BigDecimal getOutcome()
	{
		return outcome;
	}

	public void setOutcome(BigDecimal outcome)
	{
		this.outcome = outcome;
	}

	public BigDecimal getMaxVal()
	{
		return maxVal;
	}

	public void setMaxVal(BigDecimal maxVal)
	{
		this.maxVal = maxVal;
	}

	public List<MonthBalanceByOperations> getMonthList()
	{
		return monthList;
	}

	public void setMonthList(List<MonthBalanceByOperations> monthList)
	{
		this.monthList = monthList;
	}

	public List<CardOperationCategoryGraphAbstract> getOutcomeOperations()
	{
		return outcomeOperations;
	}

	public void setOutcomeOperations(List<CardOperationCategoryGraphAbstract> outcomeOperations)
	{
		this.outcomeOperations = outcomeOperations;		
	}

	public List<CardOperationCategory> getOutcomeCategories()
	{
		return outcomeCategories;
	}

	public void setOutcomeCategories(List<CardOperationCategory> outcomeCategories)
	{
		this.outcomeCategories = outcomeCategories;
	}

	public TreeNode getNode()
	{
		return node;
	}

	public void setNode(TreeNode node)
	{
		this.node = node;
	}

	public String getSelectedId()
	{
		return selectedId;
	}

	public void setSelectedId(String selectedId)
	{
		this.selectedId = selectedId;
	}

	public boolean isReadAllHints()
	{
		return readAllHints;
	}

	public void setReadAllHints(boolean readAllHints)
	{
		this.readAllHints = readAllHints;
	}

	public Calendar getOpenPageDate()
	{
		return openPageDate;
	}

	public void setOpenPageDate(Calendar openPageDate)
	{
		this.openPageDate = openPageDate;
	}

	protected static Form createFilterForm()
	{
	    List<Field> fields = new ArrayList<Field>();
		FieldBuilder fieldBuilder;

		Expression periodDatesExpression = new RhinoExpression("form."+ MonthPeriodFilter.TYPE_PERIOD+" == '"+MonthPeriodFilter.TYPE_PERIOD_PERIOD+"' ");
		Expression monthDatesExpression = new RhinoExpression("form."+ MonthPeriodFilter.TYPE_PERIOD+" == '"+MonthPeriodFilter.TYPE_PERIOD_MONTH+"' ");

		DateParser dataParser = new DateParser(DATE_FORMAT);

		DateFieldValidator datePeriodValidator = new DateFieldValidator(DATE_FORMAT);
		datePeriodValidator.setMessage("Введите дату в поле Период в формате ДД/ММ/ГГГГ.");

		DateFieldValidator dateMonthValidator = new DateFieldValidator(DATE_FORMAT);
		dateMonthValidator.setMessage("Введите дату в поле Месяц в формате ДД/ММ/ГГГГ.");

		DateInYearBeforeCurrentDateValidator dateFieldValidator = new DateInYearBeforeCurrentDateValidator(DATE_FORMAT);
		dateFieldValidator.setMessage("Вы можете просмотреть движение средств только за последний год.");

		RequiredFieldValidator requiredValidator = new  RequiredFieldValidator();

		// Период
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Тип периода");
		fieldBuilder.setName(MonthPeriodFilter.TYPE_PERIOD);
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				requiredValidator,
				new ChooseValueValidator(ListUtil.fromArray(new String[]{MonthPeriodFilter.TYPE_PERIOD_MONTH,
						MonthPeriodFilter.TYPE_PERIOD_PERIOD}))
		);
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(MonthPeriodFilter.FROM_DATE);
		fieldBuilder.setDescription("Период");
		fieldBuilder.setType("date");
		fieldBuilder.setParser(dataParser);
		fieldBuilder.clearValidators();
		fieldBuilder.setEnabledExpression(periodDatesExpression);
		fieldBuilder.addValidators(requiredValidator, datePeriodValidator, dateFieldValidator);
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(MonthPeriodFilter.TO_DATE);
		fieldBuilder.setDescription("Период");
		fieldBuilder.setType("date");
		fieldBuilder.setParser(dataParser);
		fieldBuilder.clearValidators();
		fieldBuilder.setEnabledExpression(periodDatesExpression);
		fieldBuilder.addValidators(requiredValidator, datePeriodValidator, dateFieldValidator);
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("onDate");
		fieldBuilder.setDescription("Месяц");
		fieldBuilder.setType("date");
		fieldBuilder.setParser(dataParser);
		fieldBuilder.clearValidators();
		fieldBuilder.setEnabledExpression(monthDatesExpression);
		fieldBuilder.addValidators(requiredValidator, dateMonthValidator, dateFieldValidator);
		fields.add(fieldBuilder.build());

		// показать операции совершенные по доп картам к чужим счетам
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("showCreditCards");
		fieldBuilder.setDescription("Показать операции совершенные по кредитным картам");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fields.add(fieldBuilder.build());

		// показать операции совершенные по доп картам к чужим счетам
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("showOtherAccounts");
		fieldBuilder.setDescription("Показать операции совершенные по дополнительным картам к чужим счетам");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fields.add(fieldBuilder.build());

		// включать операции с наличными
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("showCash");
		fieldBuilder.setDescription("Показать операции, совершенные за наличные");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fields.add(fieldBuilder.build());

		//Показывать копейки
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("showCents");
		fieldBuilder.setDescription("Показать копейки");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fields.add(fieldBuilder.build());

		// Показать переводы
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("showTransfer");
		fieldBuilder.setDescription("Показать операции, являющиеся переводами");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("customSelectId");
		fieldBuilder.setDescription("Карта");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fields.add(fieldBuilder.build());

		FormBuilder formBuilder = new FormBuilder();
		formBuilder.setFields(fields);

		CompareValidator compareDateValidator = new CompareValidator();
		compareDateValidator.setEnabledExpression(periodDatesExpression);
		compareDateValidator.setParameter(CompareValidator.OPERATOR, CompareValidator.LESS_EQUAL);
		compareDateValidator.setBinding(CompareValidator.FIELD_O1, MonthPeriodFilter.FROM_DATE);
		compareDateValidator.setBinding(CompareValidator.FIELD_O2, MonthPeriodFilter.TO_DATE);
		compareDateValidator.setMessage("Конечная дата должна быть больше начальной!");

		formBuilder.setFormValidators(compareDateValidator);

		return formBuilder.build();
	}
}
