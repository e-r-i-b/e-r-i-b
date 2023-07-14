package com.rssl.phizic.web.common.mobile.finances.lt7;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.Expression;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.utils.ListUtil;
import com.rssl.phizic.web.common.mobile.finances.ShowFinanceStructureForm;
import com.rssl.phizic.web.component.DatePeriodFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mescheryakova
 * @ created 30.03.2012
 * @ $Author$
 * @ $Revision$
 */

public class FinanceMobileFormBase extends ShowFinanceStructureForm
{
	public static final Form MOBILE_FILTER_FORM  = createMobileFilterForm();

	private String from;
	private String to;
	private String incomeType;
	private boolean showCash;
	private boolean showOtherAccounts;
	private int paginationSize; //��������� �������
	private int paginationOffset; //��������� �������

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

	public String getIncomeType()
	{
		return incomeType;
	}

	public void setIncomeType(String incomeType)
	{
		this.incomeType = incomeType;
	}

	public boolean isShowCash()
	{
		return showCash;
	}

	public void setShowCash(boolean showCash)
	{
		this.showCash = showCash;
	}

	public boolean isShowOtherAccounts()
	{
		return showOtherAccounts;
	}

	public void setShowOtherAccounts(boolean showOtherAccounts)
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

	private static Form createMobileFilterForm()
	{
		List<Field> fields = new ArrayList<Field>();
		FieldBuilder fieldBuilder;

		Expression periodDatesExpression = new RhinoExpression("form."+ DatePeriodFilter.TYPE_PERIOD+" == '"+DatePeriodFilter.TYPE_PERIOD_PERIOD+"' ");
		String format = "dd.MM.yyyy";
		DateParser dataParser = new DateParser(format);

		DateFieldValidator dataValidator = new DateFieldValidator(format);
		dataValidator.setEnabledExpression(periodDatesExpression);
		dataValidator.setMessage("������� ���� � ������� ��.��.����.");

		RequiredFieldValidator requiredValidator = new  RequiredFieldValidator();
		requiredValidator.setEnabledExpression(periodDatesExpression);
		// ������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��� �������");
		fieldBuilder.setName(DatePeriodFilter.TYPE_PERIOD);
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new ChooseValueValidator(ListUtil.fromArray(new String[]{DatePeriodFilter.TYPE_PERIOD_MONTH, DatePeriodFilter.TYPE_PERIOD_PERIOD}))
		);
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(DatePeriodFilter.FROM_DATE);
		fieldBuilder.setDescription("���� �");
		fieldBuilder.setType("date");
		fieldBuilder.setParser(dataParser);
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(requiredValidator, dataValidator);
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(DatePeriodFilter.TO_DATE);
		fieldBuilder.setDescription("���� ��");
		fieldBuilder.setType("date");
		fieldBuilder.setParser(dataParser);
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(requiredValidator, dataValidator);
		fields.add(fieldBuilder.build());

		// �������� �������� ���������� �� ��� ������ � ����� ������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("showOtherAccounts");
		fieldBuilder.setDescription("�������� �������� ���������� �� �������������� ������ � ����� ������");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(requiredValidator);
		fields.add(fieldBuilder.build());

		// ��� ��������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��� ��������");
		fieldBuilder.setName("incomeType");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new ChooseValueValidator(ListUtil.fromArray(new String[]{INCOME_TYPE, OUTCOME_TYPE}))
		);
		fields.add(fieldBuilder.build());

		// �������� �������� � ���������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("showCash");
		fieldBuilder.setDescription("�������� �������� ����������� �� ��������");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(requiredValidator);
		fields.add(fieldBuilder.build());


		FormBuilder formBuilder = new FormBuilder();
		formBuilder.setFields(fields);


		CompareValidator compareDateValidator = new CompareValidator();
		compareDateValidator.setParameter(CompareValidator.OPERATOR, CompareValidator.LESS);
		compareDateValidator.setBinding(CompareValidator.FIELD_O1, DatePeriodFilter.FROM_DATE);
		compareDateValidator.setBinding(CompareValidator.FIELD_O2, DatePeriodFilter.TO_DATE);
		compareDateValidator.setMessage("�������� ���� ������ ���� ������ ���������!");

		DatePeriodMultiFieldValidator dateYearMultiFieldValidator = new DatePeriodMultiFieldValidator();
		dateYearMultiFieldValidator.setMaxTimeSpan(MAX_PERIOD);
		dateYearMultiFieldValidator.setBinding(DatePeriodMultiFieldValidator.FIELD_DATE_FROM, DatePeriodFilter.FROM_DATE);
		dateYearMultiFieldValidator.setBinding(DatePeriodMultiFieldValidator.FIELD_DATE_TO, DatePeriodFilter.TO_DATE);
		dateYearMultiFieldValidator.setMessage("����������, ������� ������ �� ����� 1 ����");

		formBuilder.setFormValidators(compareDateValidator, dateYearMultiFieldValidator);
		return formBuilder.build();
	}
}
