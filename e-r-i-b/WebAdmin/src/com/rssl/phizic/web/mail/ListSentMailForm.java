package com.rssl.phizic.web.mail;

import com.rssl.common.forms.validators.*;
import com.rssl.phizic.business.mail.MailConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.web.common.ListLimitActionForm;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.parsers.DateParser;

/**
 * @author gladishev
 * @ created 27.08.2007
 * @ $Author$
 * @ $Revision$
 */

public class ListSentMailForm extends ListLimitActionForm
{
	private String paginationSize;

	public static final Form FILTER_FORM = createForm();

	public String getPaginationSize()
	{
		return paginationSize;
	}

	public void set$$pagination_size0(String paginationSize)
	{
		this.paginationSize = paginationSize;
	}

	protected static final FormBuilder getPartlyFormBuilder()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder;

		// ����
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����");
		fieldBuilder.setName("subject");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		// �� �������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��");
		fieldBuilder.setName("user_TB");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		//�������� ����������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������� ��");
		fieldBuilder.setName("area_uuid");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		//��� ����������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��� ����������");
		fieldBuilder.setName("recipientType");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		//��� ����������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��� ����������");
		fieldBuilder.setName("fioEmpl");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		//����� ����������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�����");
		fieldBuilder.setName("login");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		//��� ������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��� ������");
		fieldBuilder.setName("groupName");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("fromDate");
		fieldBuilder.setDescription("����");
		fieldBuilder.setType("date");
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("toDate");
		fieldBuilder.setType("date");
		fieldBuilder.setDescription("����");
		fieldBuilder.addValidators(new DateFieldValidator());
		fieldBuilder.setParser(new DateParser());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("type");
		fieldBuilder.setDescription("��� ������");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("isAttach");
		fieldBuilder.setDescription("������� ����������� ������");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("num");
		fieldBuilder.setDescription("����� ������");
		fieldBuilder.addValidators(new RegexpFieldValidator("\\d*", "���� ����� ������ ������ ��������� ������ �����."));
		formBuilder.addField(fieldBuilder.build());

	    fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������ ��������� ������");
		fieldBuilder.setName("response_method");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������� ���������");
		fieldBuilder.setName("theme");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		DateTimeCompareValidator dateTimeCompareValidator = new DateTimeCompareValidator();
		dateTimeCompareValidator.setParameter(DateTimeCompareValidator.OPERATOR, DateTimeCompareValidator.LESS_EQUAL);
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_DATE, "fromDate");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_TIME, "fromTime");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_TO_DATE, "toDate");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_TO_TIME, "toTime");
		dateTimeCompareValidator.setMessage("�������� ���� ������ ���� ������ ���� ����� ���������!");
		formBuilder.addFormValidators(dateTimeCompareValidator);

		DateInPeriodValidator dateInPeriodValidator= new DateInPeriodValidator();
		dateInPeriodValidator.setBinding(DateInPeriodValidator.FIELD_FROM_DATE, "fromDate");
		dateInPeriodValidator.setBinding(DateInPeriodValidator.FIELD_TO_DATE, "toDate");
		formBuilder.addFormValidators(dateInPeriodValidator);

		return formBuilder;
	}

	private static Form createForm()
    {
        FormBuilder formBuilder = getPartlyFormBuilder();

        FieldBuilder fieldBuilder;

	    //���
	    fieldBuilder = new FieldBuilder();
	    fieldBuilder.setDescription("���");
	    fieldBuilder.setName("fio");
	    fieldBuilder.setType("string");
	    formBuilder.addField(fieldBuilder.build());

        return formBuilder.build();
    }
}