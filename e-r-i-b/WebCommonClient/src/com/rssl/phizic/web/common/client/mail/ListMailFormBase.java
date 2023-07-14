package com.rssl.phizic.web.common.client.mail;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.Expression;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.utils.ListUtil;
import com.rssl.phizic.web.common.ListLimitActionForm;

/**
 * ������� ����� ��� ���� ������� �����. �������� ��� ���������� ���� �������
 * @author egorova
 * @ created 25.03.2011
 * @ $Author$
 * @ $Revision$
 */

public abstract class ListMailFormBase  extends ListLimitActionForm
{
	public static final String FIELD_FROM_DATE_NAME = "fromDate";
	public static final String FIELD_TO_DATE_NAME = "toDate";
	public static final String FIELD_NUM_NAME = "num";
	public static final String FIELD_IS_ATTACH_NAME = "isAttach";
	public static final String FIELD_SUBJECT_NAME = "subject";

	protected static FormBuilder createFilter()
	{
		FormBuilder formBuilder = createDateFields();
		createAdditionalFields(formBuilder);
		return formBuilder;
	}

	private static FormBuilder createDateFields()
	{
		FormBuilder formBuilder = new FormBuilder();

		String DATESTAMP = "dd/MM/yyyy";
		DateParser dateParser = new DateParser(DATESTAMP);

		Expression periodDatesExpression = new RhinoExpression("form.typeDate == 'period'");

		DateFieldValidator dataValidator = new DateFieldValidator(DATESTAMP);
		dataValidator.setEnabledExpression(periodDatesExpression);
		dataValidator.setMessage("�� ����������� ��������� ���� \"������\". ����������, ������� ���� ������ � ��������� ������� � ������� ��/��/����.");

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��� �������");
		fieldBuilder.setName("typeDate");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new ChooseValueValidator(ListUtil.fromArray(new String[]{"month", "period", "week"}))
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(FIELD_FROM_DATE_NAME);
		fieldBuilder.setDescription("���� ������ �������");
		fieldBuilder.setType("date");
		fieldBuilder.setParser(dateParser);
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(dataValidator);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(FIELD_TO_DATE_NAME);
		fieldBuilder.setType("date");
		fieldBuilder.setDescription("���� ��������� �������");
		fieldBuilder.setParser(dateParser);
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(dataValidator);
		formBuilder.addField(fieldBuilder.build());

		CompareValidator compareValidator = new CompareValidator();
		compareValidator.setParameter(CompareValidator.OPERATOR, CompareValidator.LESS_EQUAL);
		compareValidator.setBinding(CompareValidator.FIELD_O1, FIELD_FROM_DATE_NAME);
		compareValidator.setBinding(CompareValidator.FIELD_O2, FIELD_TO_DATE_NAME);
		compareValidator.setMessage("�������� ���� ������ ���� ������ ���� ����� ���������!");
		formBuilder.setFormValidators(compareValidator);

		return formBuilder;
	}

	protected static void createAdditionalFields(FormBuilder formBuilder)
	{
		//����� ������
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(FIELD_NUM_NAME);
		fieldBuilder.setDescription("����� ������");
		fieldBuilder.addValidators(
				new RegexpFieldValidator("\\d*", "�� ����������� ������� ����� ������. ����������, ������� ������ �����."),
				new RegexpFieldValidator("\\d{1,7}", "����������, ������� �� ����� 7 ����.")
		);
		formBuilder.addField(fieldBuilder.build());

		//���� �� ������������� ����
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(FIELD_IS_ATTACH_NAME);
		fieldBuilder.setDescription("������� ����������� ������");
		formBuilder.addField(fieldBuilder.build());

		//���� ������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(FIELD_SUBJECT_NAME);
		fieldBuilder.setDescription("���� ������");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{1,40}", "���� ������ ���� �� ����� 40 ��������")
		);
		formBuilder.addField(fieldBuilder.build());
	}
}
