package com.rssl.phizic.web.common.mobile.finances.targets;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.types.DateType;
import com.rssl.common.forms.types.MoneyType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.business.finances.targets.AccountTarget;
import com.rssl.phizic.web.common.EditFormBase;
import org.apache.struts.upload.FormFile;

/**
 * @author Balovtsev
 * @version 01.10.13 10:22
 */
public class TargetsEditMobileForm extends EditFormBase
{
	private static final String DATE_FORMAT = "dd.MM.yyyy";
	private static final long   MAX_PERIOD  = 100 * 365 + 24;
	public  static final Form   EDIT_FORM   = createForm();

	private String name;
	private String date;
	private String comment;
	private String amount;
	private String type;
	private FormFile file;
	private String clearImage;
	private AccountTarget target;

	public AccountTarget getTarget()
	{
		return target;
	}

	public void setTarget(AccountTarget target)
	{
		this.target = target;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDate()
	{
		return date;
	}

	public void setDate(String date)
	{
		this.date = date;
	}

	public String getComment()
	{
		return comment;
	}

	public void setComment(String comment)
	{
		this.comment = comment;
	}

	public String getAmount()
	{
		return amount;
	}

	public void setAmount(String amount)
	{
		this.amount = amount;
	}

	/**
	 * @return �������� ��� ����
	 */
	public FormFile getFile()
	{
		return file;
	}

	/**
	 * ���������� �������� ��� ����
	 * @param file �������� ��� ����
	 */
	public void setFile(FormFile file)
	{
		this.file = file;
	}

	public String getClearImage()
	{
		return clearImage;
	}

	public void setClearImage(String clearImage)
	{
		this.clearImage = clearImage;
	}

	private static Form createForm()
	{
		DateParser dateParser = new DateParser(DATE_FORMAT);

		MoneyFieldValidator moneyFieldValidator = new MoneyFieldValidator();
		moneyFieldValidator.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, "9999999999999999.99");
		moneyFieldValidator.setMessage("�������� � ���� ��������� ���� ������ ���� � ��������� 0,00 - 9 999 999 999 999 999,99 ���");

		DatePeriodFieldValidator datePeriodFieldValidator = new DatePeriodFieldValidator(DATE_FORMAT);
		datePeriodFieldValidator.setParameter(DatePeriodFieldValidator.BEFORE_DAY, "1");
		datePeriodFieldValidator.setParameter(DatePeriodFieldValidator.AFTER_DAY, Long.valueOf(MAX_PERIOD).toString());
		datePeriodFieldValidator.setMessage("����������, ������� ���� � ��������� �� 1 ��� �� 100 ��� ������������ ������� ����.");

		RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();

		FormBuilder fb = new FormBuilder();
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��� ����");
		fieldBuilder.setName("type");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��������");
		fieldBuilder.setName("name");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.type=='OTHER'"));
		fieldBuilder.addValidators(
				requiredFieldValidator,
				new RegexpFieldValidator(".{1,35}", "�������� ���� ������ ���� �� ����� 35 ��������.")
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����������� � ��������");
		fieldBuilder.setName("comment");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{1,100}", "����������� � �������� ���� ������ ���� �� ����� 100 ��������.")
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����������� ���� ������������ ����");
		fieldBuilder.setName("date");
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.setParser(dateParser);
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(
				requiredFieldValidator,
				datePeriodFieldValidator);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��������� ����");
		fieldBuilder.setName("amount");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(
				moneyFieldValidator,
				requiredFieldValidator
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������� �����������");
		fieldBuilder.setName("clearImage");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		return fb.build();
	}
}
