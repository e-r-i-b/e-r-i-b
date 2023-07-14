package com.rssl.phizic.web.ext.sbrf.mail;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.web.mail.DateInPeriodValidator;
import com.rssl.common.forms.validators.DateTimeCompareValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.mail.MailConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.web.common.ListFormBase;

/**
 * @author kligina
 * @ created 20.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class ArchiveMailForm extends ListFormBase
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

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();


		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("subject");
		fieldBuilder.setType("string");
		fieldBuilder.setDescription("����");
		formBuilder.addField(fieldBuilder.build());

		//����� ������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�����");
		fieldBuilder.setName("num");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(new RegexpFieldValidator("\\d*", "���� ����� ������ ��������� ������ �����."));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("fromDate");
		fieldBuilder.setType("date");
		fieldBuilder.setDescription("����");
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("toDate");
		fieldBuilder.setType("date");
		fieldBuilder.setDescription("����");
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("fio");
		fieldBuilder.setType("string");
		fieldBuilder.setDescription("��� �������");
		formBuilder.addField(fieldBuilder.build());

		//�������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������");
		fieldBuilder.setName("surNameEmpl");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		//���
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("���");
		fieldBuilder.setName("firstNameEmpl");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		//��������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��������");
		fieldBuilder.setName("patrNameEmpl");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		//�����
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�����");
		fieldBuilder.setName("employeeLogin");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("���");
		fieldBuilder.setName("type");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("���");
		fieldBuilder.setName("mailType");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��� ����������");
		fieldBuilder.setName("clientType");
		fieldBuilder.setType("string");
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

		return formBuilder.build();
	}

}
