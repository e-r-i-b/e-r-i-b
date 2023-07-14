package com.rssl.phizic.web.common.socialApi.finances.categories;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.business.dictionaries.finances.CardOperationCategoryGraphAbstract;
import com.rssl.phizic.business.tree.TreeElement;
import com.rssl.phizic.web.common.client.finances.FinanceFormBase;
import com.rssl.phizic.web.component.DatePeriodFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * ��������� �������� �� ����������
 * @author Dorzhinov
 * @ created 01.10.2013
 * @ $Author$
 * @ $Revision$
 */
public class ShowOperationCategoriesMobileForm extends FinanceFormBase
{
	//in
	private String from; //���� �
	private String to; //���� ��
	private String showCash; //�������� ��������/������ ��������
	private String selectedId; // id �����/������ ����, �� ������� ���������� �������
	private String[] excludeCategories; // ����������� ��������� ��������
	private String showCashPayments; //���������� ����� ���������
	private String country; //������, ��� ������� ���������� ��������
	//out
	private TreeElement element; // ������ ����
	private List<CardOperationCategoryGraphAbstract> outcomeOperations; //�������� �� ����������

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

	public String getShowCash()
	{
		return showCash;
	}

	public void setShowCash(String showCash)
	{
		this.showCash = showCash;
	}

	public String getSelectedId()
	{
		return selectedId;
	}

	public void setSelectedId(String selectedId)
	{
		this.selectedId = selectedId;
	}

	public TreeElement getElement()
	{
		return element;
	}

	public void setElement(TreeElement element)
	{
		this.element = element;
	}

	public List<CardOperationCategoryGraphAbstract> getOutcomeOperations()
	{
		return outcomeOperations;
	}

	public void setOutcomeOperations(List<CardOperationCategoryGraphAbstract> outcomeOperations)
	{
		this.outcomeOperations = outcomeOperations;
	}

	public String[] getExcludeCategories()
	{
		return excludeCategories;
	}

	public void setExcludeCategories(String[] setxcludeCategories)
	{
		this.excludeCategories = setxcludeCategories;
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
	 * @return ������ ���������� ��������
	 */
	public String getCountry()
	{
		return country;
	}

	/**
	 * @param country ������ ���������� ��������
	 */
	public void setCountry(String country)
	{
		this.country = country;
	}

	public static Form createMobileFilterForm()
	{
		List<Field> fields = new ArrayList<Field>();
		FieldBuilder fieldBuilder;

		DateParser dataParser = new DateParser();

		DateFieldValidator datePeriodValidator = new DateFieldValidator();
		datePeriodValidator.setMessage("������� ���� � ���� ������ � ������� ��.��.����");

		DateInYearBeforeCurrentDateValidator dateFieldValidator = new DateInYearBeforeCurrentDateValidator();
		dateFieldValidator.setMessage("�� ������ ����������� �������� ������� ������ �� ��������� ���.");

		RequiredFieldValidator requiredValidator = new RequiredFieldValidator();

		// ������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("from");
		fieldBuilder.setDescription("������");
		fieldBuilder.setType("date");
		fieldBuilder.setParser(dataParser);
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(requiredValidator, datePeriodValidator, dateFieldValidator);
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("to");
		fieldBuilder.setDescription("������");
		fieldBuilder.setType("date");
		fieldBuilder.setParser(dataParser);
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(requiredValidator, datePeriodValidator, dateFieldValidator);
		fields.add(fieldBuilder.build());

		// �������� �������� � ���������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("showCash");
		fieldBuilder.setDescription("�������� ��������/������ ��������");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.addValidators(requiredValidator);
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("selectedId");
		fieldBuilder.setDescription("�����");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator("^(\\d*|allCards|mainCards|debitCards|overdraftCards|creditCards|ownAdditionalCards|otherAdditionalCards)$"));
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
		fieldBuilder.addValidators(requiredValidator);
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("country");
		fieldBuilder.setDescription("������ ���������� ��������");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator("[A-Z]{3}"));
		fields.add(fieldBuilder.build());

		FormBuilder formBuilder = new FormBuilder();
		formBuilder.setFields(fields);

		CompareValidator compareDateValidator = new CompareValidator();
		compareDateValidator.setParameter(CompareValidator.OPERATOR, CompareValidator.LESS_EQUAL);
		compareDateValidator.setBinding(CompareValidator.FIELD_O1, DatePeriodFilter.FROM_DATE);
		compareDateValidator.setBinding(CompareValidator.FIELD_O2, DatePeriodFilter.TO_DATE);
		compareDateValidator.setMessage("�������� ���� ������ ���� ������ ���������!");

		formBuilder.setFormValidators(compareDateValidator);

		return formBuilder.build();
	}
}
