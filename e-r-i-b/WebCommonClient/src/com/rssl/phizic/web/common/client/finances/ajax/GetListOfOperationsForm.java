package com.rssl.phizic.web.common.client.finances.ajax;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.types.DateType;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.ChooseValueValidator;
import com.rssl.common.forms.validators.DateFieldValidator;
import com.rssl.common.forms.validators.MonthPeriodFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.operations.finances.CardOperationDescription;
import com.rssl.phizic.utils.ListUtil;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.web.component.MonthPeriodFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lepihina
 * @ created 12.10.2012
 * @ $Author$
 * @ $Revision$
 */
public class GetListOfOperationsForm extends ListFormBase
{
	public static final Form FILTER_FORM = getFilterForm();
	private static final String DATE_FORMAT = "dd/MM/yyyy";
	private static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";

	private List<CardOperationDescription> cardOperations;

	private int searchPage; //����� �������� ������
	private int resOnPage; //���������� ����� �� ��������

	public int getSearchPage()
	{
		return searchPage;
	}

	public void setSearchPage(int searchPage)
	{
		this.searchPage = searchPage;
	}

	public int getResOnPage()
	{
		return resOnPage;
	}

	public void setResOnPage(int resOnPage)
	{
		this.resOnPage = resOnPage;
	}

	public List<CardOperationDescription> getCardOperations()
	{
		return cardOperations;
	}

	public void setCardOperations(List<CardOperationDescription> cardOperations)
	{
		this.cardOperations = cardOperations;
	}

	private static Form getFilterForm()
	{
		List<Field> fields = new ArrayList<Field>();
		FieldBuilder fieldBuilder;

		DateParser dataParser = new DateParser(DATE_FORMAT);

		DateFieldValidator datePeriodValidator = new DateFieldValidator(DATE_FORMAT);
		datePeriodValidator.setMessage("������� ���� � ���� ������ � ������� ��/��/����.");

		MonthPeriodFieldValidator monthPeriodFieldValidator = new MonthPeriodFieldValidator(DATE_FORMAT);
		monthPeriodFieldValidator.setParameter(MonthPeriodFieldValidator.BEFORE_MONTH, -13);
		monthPeriodFieldValidator.setMessage("�� ������ ����������� �������� ������� ������ �� ��������� ���.");

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��� �������");
		fieldBuilder.setName(MonthPeriodFilter.TYPE_PERIOD);
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new ChooseValueValidator(ListUtil.fromArray(new String[]{MonthPeriodFilter.TYPE_PERIOD_MONTH,
						MonthPeriodFilter.TYPE_PERIOD_PERIOD, MonthPeriodFilter.TYPE_PERIOD_MONTH_PERIOD}))
		);
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("onDate");
		fieldBuilder.setDescription("������");
		fieldBuilder.setType("date");
		fieldBuilder.setParser(dataParser);
		fieldBuilder.clearValidators();
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.typeDate != 'period'"));
		fieldBuilder.addValidators(new RequiredFieldValidator(), datePeriodValidator, monthPeriodFieldValidator);
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("fromDate");
		fieldBuilder.setDescription("������ c");
		fieldBuilder.setType("date");
		fieldBuilder.setParser(dataParser);
		fieldBuilder.clearValidators();
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.typeDate == 'period'"));
		fieldBuilder.addValidators(new RequiredFieldValidator(), datePeriodValidator, monthPeriodFieldValidator);
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("toDate");
		fieldBuilder.setDescription("������ ��");
		fieldBuilder.setType("date");
		fieldBuilder.setParser(dataParser);
		fieldBuilder.clearValidators();
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.typeDate == 'period' "));
		fieldBuilder.addValidators(new RequiredFieldValidator(), datePeriodValidator, monthPeriodFieldValidator);
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������� ��������");
		fieldBuilder.setName("income");
		fieldBuilder.setType("integer");
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("���������� ������ ���������, ��������� � ��������������");
		fieldBuilder.setName("onlyAvailableCategories");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������� � ���������");
		fieldBuilder.setName("cash");
		fieldBuilder.setType("integer");
		fields.add(fieldBuilder.build());

		// �������� �������� ����������� �� ��� ������ � ����� ������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("showCreditCards");
		fieldBuilder.setDescription("�������� �������� ����������� �� ��������� ������");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fields.add(fieldBuilder.build());

		// �������� �������� ����������� �� ��� ������ � ����� ������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("showOtherAccounts");
		fieldBuilder.setDescription("�������� �������� ����������� �� �������������� ������ � ����� ������");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fields.add(fieldBuilder.build());

		// �������� �������� � ���������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("showCash");
		fieldBuilder.setDescription("�������� ��������, ����������� �� ��������");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fields.add(fieldBuilder.build());

		// �������� ��������, ���������� ����������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("showTransfers");
		fieldBuilder.setDescription("�������� ��������, ���������� ����������");
		fieldBuilder.setType(StringType.INSTANCE.getName());
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
		fieldBuilder.setDescription("�������������� ����");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("openPageDate");
		fieldBuilder.setDescription("����, ����� ������ ����� �� ��������");
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.setParser(new DateParser(DATE_TIME_FORMAT));
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new DateFieldValidator(DATE_TIME_FORMAT));
		fields.add(fieldBuilder.build());

		FormBuilder formBuilder = new FormBuilder();
		formBuilder.setFields(fields);

		return formBuilder.build();
	}
}
