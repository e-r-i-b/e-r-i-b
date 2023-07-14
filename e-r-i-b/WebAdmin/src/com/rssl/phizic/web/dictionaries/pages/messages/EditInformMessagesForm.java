package com.rssl.phizic.web.dictionaries.pages.messages;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.parsers.BooleanParser;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.business.dictionaries.pages.Page;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.web.actions.StrutsUtils;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.Set;

/**
 * ����� �������������� ��������������� ���������.
 * @author komarov
 * @ created 20.09.2011
 * @ $Author$
 * @ $Revision$
 */

public class EditInformMessagesForm extends EditFormBase
{
	public static final String INDEFINITELY_FIELD = "indefinitely";
	public static final String SAVE_AS_TEMPLATE_FIELD = "saveAsTemplate";

	private static final String TIMESTAMP_FORMAT = "HH:mm:ss";
	private static final String DATESTAMP_FORMAT = "dd.MM.yyyy";
	private static final String TIME_MESSAGE = "����� ������ ���� � ������� ��:��:CC.";
	private static final String START_DATE_MESSAGE = "������� ���������� ���� ������ ���������� � ������� ��.��.����.";
	private static final String CANCEL_DATE_MESSAGE = "������� ���������� ���� ������ � ���������� � ������� ��.��.����.";

	public static final Form INFORM_MESSAGES_FORM  = EditInformMessagesForm.createForm();

	private Long[] selectedIds = new Long[]{};
	private String[] selectedDepartmentsIds = new String[]{};

	private Set<Page> pages;  //��������
	private Set<String>  departments; //������ �� ������� ����� �������� �� �����

	public Set<Page> getPages()
	{
		return pages;
	}

	public void setPages(Set<Page> pages)
	{
		this.pages = pages;
	}

	public Set<String> getDepartments()
	{
		return departments;
	}

	public void setDepartments(Set<String> departments)
	{
		this.departments = departments;
	}

	public Long[] getSelectedIds()
	{
		return selectedIds;
	}

	public void setSelectedIds(Long[] selectedIds)
	{
		this.selectedIds = selectedIds;
	}

	public String[] getSelectedDepartmentsIds()
	{
		return selectedDepartmentsIds;
	}

	public void setSelectedDepartmentsIds(String[] selectedDepartmentsIds)
	{
		this.selectedDepartmentsIds = selectedDepartmentsIds;
	}

	private static Form createForm()
	{
		DateParser timeParser = new DateParser(TIMESTAMP_FORMAT);
		DateParser dateParser = new DateParser(DATESTAMP_FORMAT);

		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("C��������");
		fieldBuilder.setName("text");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators (
				new RequiredFieldValidator("������� ����� ���������."),
				new RegexpFieldValidator("(?s).{0,500}", "����� ��������������� ��������� ������ ���� �� ����� 500 ��������.")
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType("string");
		fieldBuilder.setName(SAVE_AS_TEMPLATE_FIELD);
		fieldBuilder.setDescription("��������� ������");
		fieldBuilder.setParser(new BooleanParser());
		formBuilder.addField( fieldBuilder.build() );

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType("string");
		fieldBuilder.setName(INDEFINITELY_FIELD);
		fieldBuilder.setDescription("���������");
		fieldBuilder.setParser(new BooleanParser());
		formBuilder.addField( fieldBuilder.build() );

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType("string");
		fieldBuilder.setName("viewType");
		fieldBuilder.setDescription("�����������");
		formBuilder.addField( fieldBuilder.build() );

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType("string");
		fieldBuilder.setName("fromDate");
		fieldBuilder.setDescription("��������� ����");
		fieldBuilder.addValidators(
				new RequiredFieldValidator(START_DATE_MESSAGE),
				new DateFieldValidator(DATESTAMP_FORMAT, START_DATE_MESSAGE),
				new RegexpFieldValidator(".{0,10}", START_DATE_MESSAGE)
		);
		fieldBuilder.setParser(dateParser);
		fieldBuilder.setEnabledExpression(new RhinoExpression("form." + SAVE_AS_TEMPLATE_FIELD + " != true"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType("string");
		fieldBuilder.setName("toDate");
		fieldBuilder.setDescription("�������� ����");
		fieldBuilder.addValidators(
				new RequiredFieldValidator(CANCEL_DATE_MESSAGE),
				new DateFieldValidator(DATESTAMP_FORMAT, CANCEL_DATE_MESSAGE),
				new RegexpFieldValidator(".{0,10}", CANCEL_DATE_MESSAGE)
		);
		fieldBuilder.setParser(dateParser);
		fieldBuilder.setEnabledExpression(new RhinoExpression("form." + SAVE_AS_TEMPLATE_FIELD + " != true && form." +
	                                      INDEFINITELY_FIELD + "!= true"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType("string");
		fieldBuilder.setName("fromTime");
		fieldBuilder.setDescription("��������� �����");
		fieldBuilder.addValidators(
				new RequiredFieldValidator(TIME_MESSAGE),
				new DateFieldValidator(TIMESTAMP_FORMAT, TIME_MESSAGE),
				new RegexpFieldValidator(".{0,8}", TIME_MESSAGE)
		);
		fieldBuilder.setParser(timeParser);
		fieldBuilder.setEnabledExpression(new RhinoExpression("form." + SAVE_AS_TEMPLATE_FIELD + " != true"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType("string");
		fieldBuilder.setName("toTime");
		fieldBuilder.setDescription("�������� �����");
		fieldBuilder.addValidators(
				new RequiredFieldValidator(TIME_MESSAGE),
				new DateFieldValidator(TIMESTAMP_FORMAT, TIME_MESSAGE),
				new RegexpFieldValidator(".{0,8}", TIME_MESSAGE)
		);
		fieldBuilder.setParser(timeParser);
		fieldBuilder.setEnabledExpression(new RhinoExpression("form." + SAVE_AS_TEMPLATE_FIELD + " != true && form." +
	                                      INDEFINITELY_FIELD + "!= true"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType("string");
		fieldBuilder.setName("name");
		fieldBuilder.setDescription("�������� �������");
		fieldBuilder.addValidators(
				new RequiredFieldValidator("������� �������� �������."),
				new RegexpFieldValidator(".{0,40}", "�������� ������� ������ ���� �� ����� 40 ��������.")
		);
		fieldBuilder.setEnabledExpression(new RhinoExpression("form." + SAVE_AS_TEMPLATE_FIELD + " == true"));
		formBuilder.addField( fieldBuilder.build() );

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType("string");
		fieldBuilder.setName("importance");
		fieldBuilder.setDescription("�������� ��������������� ���������");
		formBuilder.addField(fieldBuilder.build());

		CurrentDateTimeCompareValidator dateTimeValidator = new CurrentDateTimeCompareValidator();
		dateTimeValidator.setParameter(DateTimeCompareValidator.OPERATOR, DateTimeCompareValidator.GREATE_EQUAL);
		dateTimeValidator.setBinding("date", "fromDate");
		dateTimeValidator.setBinding("time", "fromTime");
		dateTimeValidator.setMessage(StrutsUtils.getMessage("message.wrongBeginDate", "informMessagesBundle"));
		dateTimeValidator.setEnabledExpression(new RhinoExpression("form." + SAVE_AS_TEMPLATE_FIELD + " != true"));

		DateTimeCompareValidator dateTimeCompareValidator = new DateTimeCompareValidator();
		dateTimeCompareValidator.setParameter(DateTimeCompareValidator.OPERATOR, DateTimeCompareValidator.LESS);
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_DATE, "fromDate");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_TIME, "fromTime");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_TO_DATE, "toDate");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_TO_TIME, "toTime");
		dateTimeCompareValidator.setMessage("�������� ���� ������ ���� ������ ���������!");
		formBuilder.addFormValidators(dateTimeValidator, dateTimeCompareValidator);

		return formBuilder.build();
	}
}
