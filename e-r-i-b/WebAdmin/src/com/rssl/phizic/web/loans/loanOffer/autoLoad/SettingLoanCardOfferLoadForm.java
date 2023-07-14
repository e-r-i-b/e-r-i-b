package com.rssl.phizic.web.loans.loanOffer.autoLoad;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.validators.DateFieldValidator;
import com.rssl.common.forms.validators.FieldValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.loans.loanOffer.ClaimsHoursValidator;

/**
 * @author Mescheryakova
 * @ created 07.07.2011
 * @ $Author$
 * @ $Revision$
 */

public class SettingLoanCardOfferLoadForm extends EditFormBase
{
	public static String TIMESTAMP = "HH:mm";

	private String radio = "MonthDay";

	public String getRadio()
	{
		return radio;
	}

	public void setRadio(String radio)
	{
		this.radio = radio;
	}

	public static final Form EDIT_FORM = createForm();

	protected static Form createForm()
	{
		FormBuilder fb = new FormBuilder();

		FieldBuilder fieldBuilder;
		DateParser timeParser = new DateParser(TIMESTAMP);
		RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������� ��������");
		fieldBuilder.setName("radio");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(requiredFieldValidator);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������� ������ ��������");
		fieldBuilder.setName("manualLoadPath");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				requiredFieldValidator,
				new RegexpFieldValidator(".{0,255}", "����� �������� �������� ������ ��������  ������ ���� �� ����� 255 ��������")

		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������� ����� ������ ��������");
		fieldBuilder.setName("manualLoadFile");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				requiredFieldValidator,
				new RegexpFieldValidator(".{0,255}", "����� �������� ����� ������ �������� ������ ���� �� ����� 255 ��������")

		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������� �������������� ��������");
		fieldBuilder.setName("automaticLoadPath");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				requiredFieldValidator,
				new RegexpFieldValidator(".{0,255}", "����� �������� �������� �������������� ��������  ������ ���� �� ����� 255 ��������")

		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������� ����� �������������� ��������");
		fieldBuilder.setName("automaticLoadFile");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{0,255}", "����� �������� ����� �������������� �������� ������ ���� �� ����� 255 ��������")

		);
		fb.addField(fieldBuilder.build());

		DateFieldValidator timeFieldValidator = new DateFieldValidator(TIMESTAMP, "���� ������ ���� � ������� ��:��");

		RegexpFieldValidator dayFieldValidator   = new RegexpFieldValidator("^{1}[1-7]", "����� ������� ���������� ��  ����� 7 ����");
		RegexpFieldValidator hourFieldValidator = new RegexpFieldValidator("^{1}[1-9]*", "� �������� ���� ������ ��������� ���������� ���� ");
		FieldValidator hoursValidator = new ClaimsHoursValidator();

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("Hour");
		fieldBuilder.setDescription("�������������: ����");
		fieldBuilder.setType("integer");
		fieldBuilder.addValidators(requiredFieldValidator, hourFieldValidator, hoursValidator);
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.radio=='Hours'"));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("Day");
		fieldBuilder.setDescription("�������������: ����");
		fieldBuilder.setType("integer");
		fieldBuilder.addValidators(requiredFieldValidator, dayFieldValidator);
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.radio=='Days'"));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("DayTime");
		fieldBuilder.setDescription("�������������: ���� �(�����)");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(requiredFieldValidator, timeFieldValidator);
		fieldBuilder.setParser(timeParser);
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.radio=='Days'"));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("DayInMonth");
		fieldBuilder.setDescription("�������������: ����� ������");
		fieldBuilder.setType("integer");
		fieldBuilder.addValidators(requiredFieldValidator);
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.radio=='MonthDay'"));
		fieldBuilder.addValidators( new RegexpFieldValidator("[1-9]|1[0-9]|2[0-9]|3[0-1]", "����� ������� ���������� ��  ����� 31 ���"));
		fb.addField(fieldBuilder.build());
		return fb.build();
	}
}