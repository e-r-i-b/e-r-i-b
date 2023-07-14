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

	private String from; //���� ������ ���������� ������ ��������
	private String to; //���� ��������� ���������� ������ ��������
	private String categoryId; //������������� ���������
	private String[] selectedCardId; //������������� �����
	private String income; //������� ��������� ��������
	private String hidden; //����� ����������� ������� ��������
	private String country; //������ ���������� ��������
	private String showCash; //�������� ��������/������ ��������
	private String showOtherAccounts; //���������� �� �������� �� �������������� ������ � ����� ������
	private int paginationSize; //������ �������������� �������
	private int paginationOffset; //�������� ������������ ������ �������
	private String[] excludeCategories; //����������� ��������� ��������
	private String showCashPayments; //���������� ����� ���������

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
	 * @return true - ���������� ����� ���������
	 */
	public String getShowCashPayments()
	{
		return showCashPayments;
	}

	/**
	 * @param showCashPayments - ���������� �� ����� ���������
	 */
	public void setShowCashPayments(String showCashPayments)
	{
		this.showCashPayments = showCashPayments;
	}

	/**
	 * @return ����� ����������� ������� ��������
	 */
	public String getHidden()
	{
		return hidden;
	}

	/**
	 * ���������� ����� ����������� ������� ��������
	 * @param hidden null- ��� ��������, true - ������ �������, false - ������ �������
	 */
	public void setHidden(String hidden)
	{
		this.hidden = hidden;
	}

	/**
	 * @return ������ ���������� ��������, iso3166-1 3� ���������� ���
	 */
	public String getCountry()
	{
		return country;
	}

	/**
	 * ���������� �� ������ ���������� ��������. null - �� �����������
	 * @param country ������ ���������� ��������, iso3166-1 3� ���������� ���
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
		datePeriodValidator.setMessage("������� ���� � ���� ������ � ������� ��.��.����");

		DateParser dataParser = new DateParser();

		DateInYearBeforeCurrentDateValidator dateFieldValidator = new DateInYearBeforeCurrentDateValidator();
		dateFieldValidator.setMessage("�� ������ ����������� �������� ������� ������ �� ��������� ���.");

		RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("from");
		fieldBuilder.setDescription("������ c");
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.setParser(dataParser);
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(requiredFieldValidator, datePeriodValidator, dateFieldValidator);
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("to");
		fieldBuilder.setDescription("������ ��");
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.setParser(dataParser);
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(requiredFieldValidator, datePeriodValidator, dateFieldValidator);
		fields.add(fieldBuilder.build());

		// ������������� ��������� ��������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("categoryId");
		fieldBuilder.setDescription("������������� ��������� ��������");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fields.add(fieldBuilder.build());

		// �������������� ����
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("selectedCardIds");
		fieldBuilder.setDescription("������������� �����");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fields.add(fieldBuilder.build());

		// ����� / ������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��������� ��������");
		fieldBuilder.setName("income");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.addValidators(requiredFieldValidator);
		fields.add(fieldBuilder.build());

		// �������� �������� � ���������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("showCash");
		fieldBuilder.setDescription("�������� ��������/������ ��������");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.addValidators(requiredFieldValidator);
		fields.add(fieldBuilder.build());

		// �������� �������� ����������� �� ��� ������ � ����� ������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("showOtherAccounts");
		fieldBuilder.setDescription("�������� �������� ����������� �� �������������� ������ � ����� ������");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.addValidators(requiredFieldValidator);
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("excludeCategories");
		fieldBuilder.setDescription("����������� ��������� ��������");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("showCashPayments");
		fieldBuilder.setDescription("���������� ����� ���������");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.addValidators(requiredFieldValidator);
		fields.add(fieldBuilder.build());

		FormBuilder formBuilder = new FormBuilder();
		formBuilder.setFields(fields);

		return formBuilder.build();
	}
}
