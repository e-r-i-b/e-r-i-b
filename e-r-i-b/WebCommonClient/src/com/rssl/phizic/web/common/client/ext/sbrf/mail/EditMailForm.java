package com.rssl.phizic.web.common.client.ext.sbrf.mail;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.BusinessCategory;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.validators.CompareValidator;
import com.rssl.common.forms.validators.EmailFieldValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.mail.MailConfig;
import com.rssl.phizic.business.mail.MailSubject;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.utils.PhoneNumberFormat;

import java.util.List;

/**
 * @author kligina
 * @ created 05.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class EditMailForm extends com.rssl.phizic.web.common.client.mail.EditMailForm
{
	private static final FormBuilder formBuilder = createFormBuilder();
	public static final String RESPONSE_METHOD = "response_method";
	private static final String CATEGORY = "iccs.properties";

	private String type;

	private List<MailSubject> themes;

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public List<MailSubject> getThemes()
	{
		return themes;
	}

	public void setThemes(List<MailSubject> themes)
	{
		this.themes = themes;
	}

	public Long getClientTextLength()
	{
		return ConfigFactory.getConfig(MailConfig.class).getClientTextLength();
	}

	private static FormBuilder createFormBuilder()
	{
		FormBuilder fb = fieldsBuid();
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("mail_theme");
		fieldBuilder.setDescription("�������� ���������");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������ ��������� ������");
		fieldBuilder.setName(RESPONSE_METHOD);
		fieldBuilder.setType("string");
		fieldBuilder.addValidators (new RequiredFieldValidator());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("���������� �������");
		fieldBuilder.setName("phone");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(PhoneNumberFormat.SIMPLE_NUMBER.pattern(), "������� ���������� �������� � ���� ����� ��������. � ������ ������ ������ ���� ������ �����, ����� ����� ���� ������ ���� ����� ������.")
		);
		fieldBuilder.setEnabledExpression(new RhinoExpression("form." + RESPONSE_METHOD + " == 'BY_PHONE'"));
		fieldBuilder.setBusinessCategory(BusinessCategory.PHONE);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("C����� ������");
		fieldBuilder.setName("mailState");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators (new RequiredFieldValidator());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� ����������� �����");
		fieldBuilder.setName("email");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators (
				new RequiredFieldValidator(),
				new EmailFieldValidator()
		);
		fieldBuilder.setEnabledExpression(new RhinoExpression("form." + RESPONSE_METHOD + " == 'IN_WRITING'"));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� ����������� �����");
		fieldBuilder.setName("secondEMail");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators (
				new RequiredFieldValidator(),
				new EmailFieldValidator()
		);
		fieldBuilder.setEnabledExpression(new RhinoExpression("form." + RESPONSE_METHOD + " == 'IN_WRITING'"));
		fb.addField(fieldBuilder.build());

		CompareValidator compareValidator = new CompareValidator();
		compareValidator.setParameter(CompareValidator.OPERATOR, CompareValidator.EQUAL);
		compareValidator.setBinding(CompareValidator.FIELD_O1, "email");
		compareValidator.setBinding(CompareValidator.FIELD_O2, "secondEMail");
		compareValidator.setMessage("�� ����������� ������� E-mail. ����������, ��������� ���������� ������� ����. ������ ����������� ����� ������ ���������.");
		fb.setFormValidators(compareValidator);

		fieldBuilder = new FieldBuilder();
	   	fieldBuilder.setDescription("����� ������������ �� ����� ������� ������ ������ ������");
		fieldBuilder.setName("questionFormText");
		fieldBuilder.setType("string");
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("C����� ������");
		fieldBuilder.setName("newMailState");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators (new RequiredFieldValidator());
		fb.addField(fieldBuilder.build());

		fb.build();
		return fb;
	}

	public static Form getForm()
	{
		FormBuilder fb = new FormBuilder();
		fb.addFields(formBuilder.getFields());
		String clientTextLength =  Long.toString(ConfigFactory.getConfig(MailConfig.class).getClientTextLength());

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("C��������");
		fieldBuilder.setName("newText");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators (
				new RequiredFieldValidator(),
				new RegexpFieldValidator("(?s).{0,"+clientTextLength+"}", "����� ��������� ������ ���� �� ����� "+clientTextLength+" ��������")
		);
		fb.addField(fieldBuilder.build());

		// ����� ������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("���������");
		fieldBuilder.setName("body");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator("(?s).{0,"+clientTextLength+"}", "����� ��������� ������ ���� �� ����� "+clientTextLength+" ��������")
		);
		fb.addField(fieldBuilder.build());

		return fb.build();
	}
}
