package com.rssl.phizic.web.loans.loanOffer.unload;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.loans.loanOffer.ClaimsHoursValidator;

import java.math.BigInteger;

/**
 * User: Moshenko
 * Date: 22.06.2011
 * Time: 16:55:22
 * ����� ��������� ����-�������� ��������� ������
 */
public class AutoUnloadOfferForm extends EditFormBase
{
	private static final String RADIO_HOURS = "Hours";
	public static String TIMESTAMP = "HH:mm";

	private String loanProductRadio = RADIO_HOURS;        //������ �� ������� (Hours/Days)
	private String loanOfferRadio = RADIO_HOURS;          //������ �� �������������� ������� (Hours/Days)
	private String loanCardProductRadio = RADIO_HOURS;    //������ �� ��������� ����� (Hours/Days)
	private String loanCardOfferRadio = RADIO_HOURS;      //������ �� �������������� ��������� ����� (Hours/Days)
	private String virtualCardRadio = RADIO_HOURS;        //������ �� ����������� �����

	public String getLoanProductRadio()
	{
		return loanProductRadio;
	}

	public void setLoanProductRadio(String loanProductRadio)
	{
		this.loanProductRadio = loanProductRadio;
	}

	public String getLoanOfferRadio()
	{
		return loanOfferRadio;
	}

	public void setLoanOfferRadio(String loanOfferRadio)
	{
		this.loanOfferRadio = loanOfferRadio;
	}

	public String getLoanCardProductRadio()
	{
		return loanCardProductRadio;
	}

	public void setLoanCardProductRadio(String loanCardProductRadio)
	{
		this.loanCardProductRadio = loanCardProductRadio;
	}

	public String getLoanCardOfferRadio()
	{
		return loanCardOfferRadio;
	}

	public void setLoanCardOfferRadio(String loanCardOfferRadio)
	{
		this.loanCardOfferRadio = loanCardOfferRadio;
	}

	public String getVirtualCardRadio()
	{
		return virtualCardRadio;
	}

	public void setVirtualCardRadio(String virtualCardRadio)
	{
		this.virtualCardRadio = virtualCardRadio;
	}

	public static Form createForm()
	{
		FormBuilder fb = new FormBuilder();

		DateParser timeParser = new DateParser(TIMESTAMP);

		RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();
		DateFieldValidator timeFieldValidator = new DateFieldValidator(TIMESTAMP, "���� ������ ���� � ������� ��:��");

		RegexpFieldValidator dayFieldValidator = new RegexpFieldValidator("^{1}[1-7]", "����� ������� ���������� ��  ����� 7 ����");
		RegexpFieldValidator hourFieldValidator = new RegexpFieldValidator("^{1}[1-9]*", "� �������� ���� ������ ��������� ���������� ���� ");
		FieldValidator unloadClaimsHoursValidator = new ClaimsHoursValidator();

		LengthFieldValidator lengthValidator = new LengthFieldValidator();
		lengthValidator.setParameter("maxlength", new BigInteger("150"));
		lengthValidator.setMessage("������� �������� ����� ���� �� ����� 150 ��������");

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("loanProductEnabled");
		fieldBuilder.setDescription("������ �� �������");
		fieldBuilder.setType("boolean");
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("loanOfferEnabled");
		fieldBuilder.setDescription("������ �� �������������� �������");
		fieldBuilder.setType("boolean");
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("loanCardProductEnabled");
		fieldBuilder.setDescription("������ �� ��������� �����");
		fieldBuilder.setType("boolean");
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("loanCardOfferEnabled");
		fieldBuilder.setDescription("������ �� �������������� ��������� �����");
		fieldBuilder.setType("boolean");
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("virtualCardEnabled");
		fieldBuilder.setDescription("������ �� ����������� �����");
		fieldBuilder.setType("boolean");
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("loanProductRadio");
		fieldBuilder.setDescription("������ �� �������");
		fieldBuilder.setType("string");
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("loanOfferRadio");
		fieldBuilder.setDescription("������ �� �������������� �������");
		fieldBuilder.setType("string");
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("loanCardProductRadio");
		fieldBuilder.setDescription("������ �� ��������� �����");
		fieldBuilder.setType("string");
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("loanCardOfferRadio");
		fieldBuilder.setDescription("������ �� �������������� ��������� �����");
		fieldBuilder.setType("string");
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("virtualCardRadio");
		fieldBuilder.setDescription("������ �� ����������� �����");
		fieldBuilder.setType("string");
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("loanProductFolder");
		fieldBuilder.setDescription("������ �� �������: ������� ��������");
		fieldBuilder.setType("string");
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.loanProductEnabled != false"));
		fieldBuilder.addValidators(lengthValidator, requiredFieldValidator);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("loanOfferFolder");
		fieldBuilder.setDescription("������ �� �������������� �������: ������� ��������");
		fieldBuilder.setType("string");
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.loanOfferEnabled != false"));
		fieldBuilder.addValidators(lengthValidator, requiredFieldValidator);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("loanCardProductFolder");
		fieldBuilder.setDescription("������ �� ��������� �����: ������� ��������");
		fieldBuilder.setType("string");
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.loanCardProductEnabled != false"));
		fieldBuilder.addValidators(lengthValidator, requiredFieldValidator);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("loanCardOfferFolder");
		fieldBuilder.setDescription("������ �� �������������� ��������� �����: ������� ��������");
		fieldBuilder.setType("string");
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.loanCardOfferEnabled != false"));
		fieldBuilder.addValidators(lengthValidator, requiredFieldValidator);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("virtualCardFolder");
		fieldBuilder.setDescription("������ �� ����������� �����: ������� ��������");
		fieldBuilder.setType("string");
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.virtualCardEnabled != false"));
		fieldBuilder.addValidators(lengthValidator, requiredFieldValidator);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("loanProductHour");
		fieldBuilder.setDescription("������ �� �������: �������������: ����");
		fieldBuilder.setType("integer");
		fieldBuilder.addValidators(requiredFieldValidator, hourFieldValidator, unloadClaimsHoursValidator);
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.loanProductRadio=='Hours'&&form.loanProductEnabled != false"));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("loanProductDay");
		fieldBuilder.setDescription("������ �� �������: �������������: ����");
		fieldBuilder.setType("integer");
		fieldBuilder.addValidators(requiredFieldValidator, dayFieldValidator);
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.loanProductRadio=='Days'&&form.loanProductEnabled != false"));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("loanProductDayTime");
		fieldBuilder.setDescription("������ �� �������: �������������: ���� �(�����)");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(requiredFieldValidator, timeFieldValidator);
		fieldBuilder.setParser(timeParser);
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.loanProductRadio=='Days'&&form.loanProductEnabled != false"));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("loanOfferHour");
		fieldBuilder.setDescription("������ �� �������������� �������: �������������: ����");
		fieldBuilder.setType("integer");
		fieldBuilder.addValidators(requiredFieldValidator, hourFieldValidator, unloadClaimsHoursValidator);
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.loanOfferRadio=='Hours'&&form.loanOfferEnabled != false"));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("loanOfferDay");
		fieldBuilder.setDescription("������ �� �������������� �������: �������������: ����");
		fieldBuilder.setType("integer");
		fieldBuilder.addValidators(requiredFieldValidator, dayFieldValidator);
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.loanOfferRadio=='Days'&&form.loanOfferEnabled != false"));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("loanOfferDayTime");
		fieldBuilder.setDescription("������ �� �������������� �������: �������������: ���� �(�����)");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(requiredFieldValidator, timeFieldValidator);
		fieldBuilder.setParser(timeParser);
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.loanOfferRadio=='Days'&&form.loanOfferEnabled != false"));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("loanCardProductHour");
		fieldBuilder.setDescription("������ �� ��������� �����: �������������: ����");
		fieldBuilder.setType("integer");
		fieldBuilder.addValidators(requiredFieldValidator, hourFieldValidator, unloadClaimsHoursValidator);
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.loanCardProductRadio=='Hours'&&form.loanCardProductEnabled != false"));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("loanCardProductDay");
		fieldBuilder.setDescription("������ �� ��������� �����: �������������: ����");
		fieldBuilder.setType("integer");
		fieldBuilder.addValidators(requiredFieldValidator, dayFieldValidator);
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.loanCardProductRadio=='Days'&&form.loanCardProductEnabled != false"));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("loanCardProductDayTime");
		fieldBuilder.setDescription("������ �� ��������� �����: �������������: ���� �(�����)");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(requiredFieldValidator, timeFieldValidator);
		fieldBuilder.setParser(timeParser);
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.loanCardProductRadio=='Days'&&form.loanCardProductEnabled != false"));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("loanCardOfferHour");
		fieldBuilder.setDescription("������ �� �������������� ��������� �����: �������������: ����");
		fieldBuilder.setType("integer");
		fieldBuilder.addValidators(requiredFieldValidator, hourFieldValidator, unloadClaimsHoursValidator);
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.loanCardOfferRadio=='Hours'&&form.loanCardOfferEnabled != false"));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("loanCardOfferDay");
		fieldBuilder.setDescription("������ �� �������������� ��������� �����:  �������������: ����");
		fieldBuilder.setType("integer");
		fieldBuilder.addValidators(requiredFieldValidator, dayFieldValidator);
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.loanCardOfferRadio=='Days'&&form.loanCardOfferEnabled != false"));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("loanCardOfferDayTime");
		fieldBuilder.setDescription("������ �� �������������� ��������� �����:  �������������: ���� �(�����)");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(requiredFieldValidator, timeFieldValidator);
		fieldBuilder.setParser(timeParser);
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.loanCardOfferRadio=='Days'&&form.loanCardOfferEnabled != false"));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("virtualCardHour");
		fieldBuilder.setDescription("������ �� ����������� �����: �������������: ����");
		fieldBuilder.setType("integer");
		fieldBuilder.addValidators(requiredFieldValidator, hourFieldValidator, unloadClaimsHoursValidator);
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.virtualCardRadio=='Hours'&&form.virtualCardEnabled != false"));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("virtualCardDay");
		fieldBuilder.setDescription("������ �� ����������� �����: �������������: ����");
		fieldBuilder.setType("integer");
		fieldBuilder.addValidators(requiredFieldValidator, dayFieldValidator);
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.virtualCardRadio=='Days'&&form.virtualCardEnabled != false"));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("virtualCardDayTime");
		fieldBuilder.setDescription("������ �� ����������� �����: �������������: ���� �(�����)");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(requiredFieldValidator, timeFieldValidator);
		fieldBuilder.setParser(timeParser);
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.virtualCardRadio=='Days'&&form.virtualCardEnabled != false"));
		fb.addField(fieldBuilder.build());

		return fb.build();
	}
}
