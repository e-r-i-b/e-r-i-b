package com.rssl.phizic.web.news;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.parsers.EnumParser;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.business.departments.froms.validators.TBValidator;
import com.rssl.phizic.business.news.NewsType;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * User: Zhuravleva
 * Date: 21.07.2006
 * Time: 13:42:21
 */
public class EditNewsForm extends EditFormBase
{
	public static String DATESTAMP = "dd.MM.yyyy";
	public static String TIMESTAMP = "HH:mm";
	public static final Form EDIT_FORM = createForm();
	private Long template;
	private String type;
	private boolean mainNews = false;

	public boolean isMainNews()
	{
		return mainNews;
	}

	public void setMainNews(boolean mainNews)
	{
		this.mainNews = mainNews;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public Long getTemplate()
	{
		return template;
	}

	public void setTemplate(Long template)
	{
		this.template = template;
	}

	@SuppressWarnings({"OverlyLongMethod"})
	private static Form createForm()
	{
		DateParser dateParser = new DateParser(DATESTAMP);
		DateParser timeParser = new DateParser(TIMESTAMP);
		FormBuilder fb = new FormBuilder();

		FieldBuilder fieldBuilder;

		// ���������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("���������");
		fieldBuilder.setName("title");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{0,100}", "��������� ������ ���� �� ����� 100 ��������")
		);
		fb.addField(fieldBuilder.build());

		// �������� ����� �������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������� �����");
		fieldBuilder.setName("shortText");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{0,150}", "������� ����� ������� ������ ���� �� ����� 150 ��������")
		);
		fb.addField(fieldBuilder.build());

		// �����
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�����");
		fieldBuilder.setName("text");
		fieldBuilder.setType("string");
	    fieldBuilder.addValidators(
				new RequiredFieldValidator(),
			    new RegexpFieldValidator("(?s).{0,5000}", "����� ������� ������ ���� �� ����� 5000 ��������")
		);
		fb.addField(fieldBuilder.build());

		// ����
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����");
		fieldBuilder.setName("newsDate");
		fieldBuilder.setType("date");
		fieldBuilder.setParser(dateParser);
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new DateFieldValidator(DATESTAMP));
		fb.addField(fieldBuilder.build());

		// �����
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�����");
		fieldBuilder.setName("newsDateTime");
		fieldBuilder.setType("date");
		fieldBuilder.setParser(timeParser);
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new DateFieldValidator(TIMESTAMP, "����� ������ ���� � ������� ��:��"));
		fb.addField(fieldBuilder.build());

		// ��������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������� ��������");
		fieldBuilder.setName("important");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fb.addField(fieldBuilder.build());

		// ������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������");
		fieldBuilder.setName("state");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fb.addField(fieldBuilder.build());

		// ��� �����������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��� �����������");
		fieldBuilder.setName("type");
		fieldBuilder.setType("string");
		fieldBuilder.setParser(new EnumParser<NewsType>(NewsType.class));
		fieldBuilder.addValidators(new RequiredFieldValidator(), new EnumFieldValidator<NewsType>(NewsType.class, "�������� �������� ��� �����������."));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();		
		fieldBuilder.setDescription("�������");
		fieldBuilder.setName("TB");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(new RequiredFieldValidator(), new RegexpFieldValidator("^\\d+(,\\d+)*$"), new TBValidator());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������� ���������");
		fieldBuilder.setName("departmentName");
		fieldBuilder.setType("string");
		fb.addField(fieldBuilder.build());

		// ������������� ������������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������������� ������������");
		fieldBuilder.setName("automaticPublish");
		fieldBuilder.setType("boolean");
		fb.addField(fieldBuilder.build());


		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������������� ������������(����)");
		fieldBuilder.setName("automaticPublishDate");
		fieldBuilder.setType("date");
		fieldBuilder.setParser(dateParser);
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new DateFieldValidator(DATESTAMP));
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.automaticPublish == true"));
		fb.addField(fieldBuilder.build());


		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������������� ������������(�����)");
		fieldBuilder.setName("automaticPublishTime");
		fieldBuilder.setType("date");
		fieldBuilder.setParser(timeParser);
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new DateFieldValidator(TIMESTAMP, "����� ������ ���� � ������� ��:��"));
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.automaticPublish == true"));
		fb.addField(fieldBuilder.build());

		//������������� ����� � ����������
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������������� ����� � ����������");
		fieldBuilder.setName("cancelPublish");
		fieldBuilder.setType("boolean");
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������������� ����� � ����������(����)");
		fieldBuilder.setName("cancelPublishDate");
		fieldBuilder.setType("date");
		fieldBuilder.setParser(dateParser);
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new DateFieldValidator(DATESTAMP));
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.cancelPublish == true"));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������������� ����� � ����������(�����)");
		fieldBuilder.setName("cancelPublishTime");
		fieldBuilder.setType("date");
		fieldBuilder.setParser(timeParser);
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new DateFieldValidator(TIMESTAMP, "����� ������ ���� � ������� ��:��"));
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.cancelPublish == true"));
		fb.addField(fieldBuilder.build());

		DateTimeCompareValidator dateTimeCompareValidator = new DateTimeCompareValidator();
		dateTimeCompareValidator.setParameter(DateTimeCompareValidator.OPERATOR, DateTimeCompareValidator.LESS);
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_DATE, "automaticPublishDate");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_TIME, "automaticPublishTime");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_TO_DATE, "cancelPublishDate");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_TO_TIME, "cancelPublishTime");
		dateTimeCompareValidator.setMessage("���� ������ � ���������� ������ ���� ������ ���� ����������!");

		fb.setFormValidators(dateTimeCompareValidator);
		return fb.build();
	}
}
