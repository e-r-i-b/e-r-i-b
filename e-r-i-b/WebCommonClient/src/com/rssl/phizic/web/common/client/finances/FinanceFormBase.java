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

	protected static final long MAX_PERIOD = 366 * 24 * 60 * 60 * 1000L; // 366 ���� ��� ���� ������, ��� ����
	public static final Form FILTER_FORM  = createFilterForm();

	
	// ���� ���������� ���������� ��������
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
	 * @return ������ ���
	 */
	public FinancesStatus getFinancesStatus()
	{
		return financesStatus;
	}

	/**
	 * ������ ������ ���
	 * @param financesStatus ������ ���
	 */
	public void setFinancesStatus(FinancesStatus financesStatus)
	{
		this.financesStatus = financesStatus;
	}

	/**
	 * @return ���� ���������� ���������� ��������
	 */
	public Calendar getLastModified()
	{
		return lastModified;
	}

	/**
	 * ������ ���� ���������� ���������� ��������
	 * @param lastModified ���� ���������� ���������� ��������
	 */
	public void setLastModified(Calendar lastModified)
	{
		this.lastModified = lastModified;
	}

	/**
	 * ���������� ������ ������
	 * @param data ������ ������
	 */
	public void setData(List data)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.data = data;
	}

	/**
	 * @return ������ ������
	 */
	public List getData()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return data;
	}

	/**
	 * @return � ����� ��������� ������� (�������� ��� ��������� ��������) ��������� ������ ��������
	 */
	public String getActivePagesCategory()
	{
		return activePagesCategory;
	}

	/**
	 * ������ ���������
	 * @param activePagesCategory � ����� ��������� ������� (�������� ��� ��������� ��������) ��������� ������ ��������
	 */
	public void setActivePagesCategory(String activePagesCategory)
	{
		this.activePagesCategory = activePagesCategory;
	}

	/**
	 * ����� ��� �������� ����� �������
	 * @return ���������� �����
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
		dataValidator.setMessage("������� ���� � ���� ������ � ������� ��/��/����.");

		RequiredFieldValidator requiredValidator = new  RequiredFieldValidator();
		requiredValidator.setEnabledExpression(periodDatesExpression);
		// ������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��� �������");
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
		fieldBuilder.setDescription("������");
		fieldBuilder.setType("date");
		fieldBuilder.setParser(dataParser);
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(requiredValidator, dataValidator);
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(DatePeriodFilter.TO_DATE);
		fieldBuilder.setDescription("������");
		fieldBuilder.setType("date");
		fieldBuilder.setParser(dataParser);
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(requiredValidator, dataValidator);
		fields.add(fieldBuilder.build());

		// �������� �������� ���������� �� ��� ������ � ����� ������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("showOtherAccounts");
		fieldBuilder.setDescription("�������� �������� ���������� �� �������������� ������ � ����� ������");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fields.add(fieldBuilder.build());

		// ��� ��������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��� ��������");
		fieldBuilder.setName("incomeType");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new ChooseValueValidator(ListUtil.fromArray(new String[]{INCOME_TYPE, OUTCOME_TYPE}))
		);
		fields.add(fieldBuilder.build());

		// �������� �������� � ���������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("showCash");
		fieldBuilder.setDescription("�������� �������� ���������� �� ��������");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fields.add(fieldBuilder.build());

		FormBuilder formBuilder = new FormBuilder();
		formBuilder.setFields(fields);


		CompareValidator compareDateValidator = new CompareValidator();
		compareDateValidator.setParameter(CompareValidator.OPERATOR, CompareValidator.LESS);
		compareDateValidator.setBinding(CompareValidator.FIELD_O1, DatePeriodFilter.FROM_DATE);
		compareDateValidator.setBinding(CompareValidator.FIELD_O2, DatePeriodFilter.TO_DATE);
		compareDateValidator.setMessage("�������� ���� ������ ���� ������ ���������!");

		DatePeriodMultiFieldValidator datePeriodValidator = new DatePeriodMultiFieldValidator();
		datePeriodValidator.setMaxTimeSpan(MAX_PERIOD);
		datePeriodValidator.setBinding(DatePeriodMultiFieldValidator.FIELD_DATE_FROM, DatePeriodFilter.FROM_DATE);
		datePeriodValidator.setBinding(DatePeriodMultiFieldValidator.FIELD_DATE_TO, DatePeriodFilter.TO_DATE);
		datePeriodValidator.setMessage("������ ��� ������������ ����������� ������� ������ ���� �� ����� ����."
				+ " ����������, �������� ������ ������." );

		formBuilder.setFormValidators(compareDateValidator, datePeriodValidator);

		return formBuilder.build();
	}

}
