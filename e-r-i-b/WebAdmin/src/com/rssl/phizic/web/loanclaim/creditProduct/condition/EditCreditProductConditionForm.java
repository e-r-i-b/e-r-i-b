package com.rssl.phizic.web.loanclaim.creditProduct.condition;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.types.*;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.business.loanclaim.creditProduct.condition.CreditProductCondition;
import com.rssl.phizic.business.loanclaim.creditProduct.condition.CurrencyCreditProductCondition;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.loanclaim.Constants;

import java.util.List;

/**
 * @author Moshenko
 * @ created 16.01.2014
 * @ $Author$
 * @ $Revision$
 * ����� �������������� ������� �� ��������� ���������.
 */
public class EditCreditProductConditionForm extends EditFormBase
{
	private static final String DATE_FORMAT = "dd.MM.yyyy";

	private List<String> tbCodes;
	//������� ������� �� �������.
	private CurrencyCreditProductCondition rubCondition;

	private CurrencyCreditProductCondition usdCondition;

	private CurrencyCreditProductCondition eurCondition;

	private CreditProductCondition condition;

	public CreditProductCondition getCondition()
	{
		return condition;
	}

	public void setCondition(CreditProductCondition condition)
	{
		this.condition = condition;
	}

	public CurrencyCreditProductCondition getRubCondition()
	{
		return rubCondition;
	}

	public void setRubCondition(CurrencyCreditProductCondition rubCondition)
	{
		this.rubCondition = rubCondition;
	}

	public CurrencyCreditProductCondition getUsdCondition()
	{
		return usdCondition;
	}

	public void setUsdCondition(CurrencyCreditProductCondition usdCondition)
	{
		this.usdCondition = usdCondition;
	}

	public CurrencyCreditProductCondition getEurCondition()
	{
		return eurCondition;
	}

	public void setEurCondition(CurrencyCreditProductCondition eurCondition)
	{
		this.eurCondition = eurCondition;
	}

	public List<String> getTbCodes()
	{
		return tbCodes;
	}

	public void setTbCodes(List<String> tbCodes)
	{
		this.tbCodes = tbCodes;
	}

	public Form createForm()
	{
		FormBuilder fb = new FormBuilder();
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����������� ������������ ����� Transact SM");
		fieldBuilder.setName(Constants.TRANSACT_SM_USE);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());


		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("���������� � ���� ������");
		fieldBuilder.setName(Constants.SELECTION_AVALIABLE);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��� ��������");
		fieldBuilder.setName(Constants.PRODUCT_TYPE_ID);
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RequiredFieldValidator()
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��� ���� ��������");
		fieldBuilder.setName(Constants.PRODUCT_TYPE_CODE);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������������ ��������");
		fieldBuilder.setName(Constants.PRODUCT_ID);
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());
		fieldBuilder.addValidators(
				new RequiredFieldValidator()
		);

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��� ��������");
		fieldBuilder.setName(Constants.PRODUCT_CODE);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("���� �������� �� (���)");
		fieldBuilder.setName(Constants.PRODUCT_DATE_FROM_YEAR);
		fieldBuilder.addValidators(new RegexpFieldValidator("\\d{0,2}", "���� ������� � ����� ������ ��������� �� ����� 2 ����"));
		fieldBuilder.setType(IntType.INSTANCE.getName());

		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("���� �������� �� (���.)");
		fieldBuilder.setName(Constants.PRODUCT_DATE_FROM_MONTH);
		fieldBuilder.setType(IntType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator("\\d{0,3}", "���� ������� � ������� ������ ��������� �� ����� 3 ����"));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("���� �������� �� (���)");
		fieldBuilder.setName(Constants.PRODUCT_DATE_TO_YEAR);
		fieldBuilder.setType(IntType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator("\\d{0,2}", "���� ������� � ����� ������ ��������� �� ����� 2 ����"));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("���� �������� �� (���.)");
		fieldBuilder.setName(Constants.PRODUCT_DATE_TO_MONTH);
		fieldBuilder.setType(IntType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator("\\d{0,3}", "���� ������� � ������� ������ ��������� �� ����� 3 ����"));
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("���� �������� �� (������������)");
		fieldBuilder.setName(Constants.PRODUCT_MAX_DATE_INCLUDE);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������������� �����");
		fieldBuilder.setName(Constants.USE_INITIAL_FEE);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		NumericRangeValidator notZeroValidator = new NumericRangeValidator();
		notZeroValidator.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "0.01");
		notZeroValidator.setMessage("����� �� ����� ���� 0");

		NumericRangeValidator notZeroPercentValidator = new NumericRangeValidator();
		notZeroPercentValidator.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "0.01");
		notZeroPercentValidator.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, "100");
		notZeroPercentValidator.setMessage("�������� ���� ���������");

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������������� ����� ��");
		fieldBuilder.setName(Constants.MIN_INITIAL_FEE);
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.useInitialFee == true"));
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(Constants.PERCENT_REGEX, "����������� '�������������� ����� ��'. ������: NNNNNNN.NN (���������� �������), c ������������ �������� � ������"),
				notZeroPercentValidator
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������������� ����� ��");
		fieldBuilder.setName(Constants.MAX_INITIAL_FEE);
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.useInitialFee == true"));
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(Constants.PERCENT_REGEX, "����������� '�������������� ����� ��'. ������: NNNNNNN.NN (���������� �������), c ������������ �������� � ������"),
				notZeroPercentValidator
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������������� ����� �� (������������)");
		fieldBuilder.setName(Constants.MAX_INITIAL_FEE_INCLUDE);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�����������");
		fieldBuilder.setName(Constants.ENSURING);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������������� �������");
		fieldBuilder.setName(Constants.ADDITIONAL_TERMS);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RegexpFieldValidator("(?s).{1,500}", "���� '�������������� �������' ������ ��������� �� ����� 500 ��������.")
		);
		fb.addField(fieldBuilder.build());

		for (String tbCode: tbCodes)
		{
			fieldBuilder = new FieldBuilder();
			fieldBuilder.setName(Constants.TB + tbCode);
			fieldBuilder.setType(BooleanType.INSTANCE.getName());
			fb.addField(fieldBuilder.build());
		}

		String[] currArr = {Constants.RUB_CODE_NUMBER,Constants.USD_CODE_NUMBER,Constants.EUR_CODE_NUMBER};
		for (String currCode: currArr)
		{
			String requireStr = "false";
			if (!StringHelper.isEmpty((String)getField(Constants.CURR_COND_FROM_DATE + currCode))||
				!StringHelper.isEmpty((String)getField(Constants.CURR_COND_MIN_LIMIT + currCode))||
				!StringHelper.isEmpty((String)getField(Constants.CURR_COND_MAX_LIMIT + currCode))||
				!StringHelper.isEmpty((String)getField(Constants.CURR_COND_MIN_PERCENT_RATE + currCode))||
				!StringHelper.isEmpty((String)getField(Constants.CURR_COND_MAX_PERCENT_RATE + currCode)))
				requireStr = "true";

			RhinoExpression enableExpr = new RhinoExpression(requireStr);
			fieldBuilder = new FieldBuilder();
			fieldBuilder.setDescription("(" + currCode + ") �������� �������");
			fieldBuilder.setName(Constants.CURR_COND_AVALIABLE + currCode);
			fieldBuilder.setType(BooleanType.INSTANCE.getName());
			fb.addField(fieldBuilder.build());

			fieldBuilder = new FieldBuilder();
			fieldBuilder.setName(Constants.CURR_COND_ID + currCode);
			fieldBuilder.setType(LongType.INSTANCE.getName());
			fb.addField(fieldBuilder.build());

			fieldBuilder = new FieldBuilder();
			fieldBuilder.setDescription("(" + currCode + ") ��������� �");
			fieldBuilder.setName(Constants.CURR_COND_FROM_DATE + currCode);
			fieldBuilder.setType(DateType.INSTANCE.getName());
			fieldBuilder.setEnabledExpression(enableExpr);
			fieldBuilder.addValidators(
					new RequiredFieldValidator(),
					new DateNotInPastValidator(DATE_FORMAT,"���� ������ �������� ������� ������ ���� ������ ���� ����� ������� ��������� ����"));

			fb.addField(fieldBuilder.build());

			fieldBuilder = new FieldBuilder();
			fieldBuilder.setDescription("(" + currCode + ") ����� �������� ��");
			fieldBuilder.setName(Constants.CURR_COND_MIN_LIMIT + currCode);
			fieldBuilder.setType(MoneyType.INSTANCE.getName());
			fieldBuilder.setEnabledExpression(enableExpr);
			fieldBuilder.addValidators(
					new RequiredFieldValidator(),
					new RegexpMoneyFieldValidator(Constants.MONEY_REGEX, "����������� '����� �������� ��'.  ������: NNNNNNN.NN (���������� �������), c ������������ �������� � ������"),
					notZeroValidator
			);
			fb.addField(fieldBuilder.build());

			fieldBuilder = new FieldBuilder();
			fieldBuilder.setName(Constants.CURR_COND_MAX_LIMIT_PERCENT_USE + currCode);
			fieldBuilder.setType(BooleanType.INSTANCE.getName());
			fieldBuilder.setEnabledExpression(enableExpr);
			fb.addField(fieldBuilder.build());

			fieldBuilder = new FieldBuilder();
			fieldBuilder.setDescription("(" + currCode + ") ����� �������� �� (� ������)");
			fieldBuilder.setName(Constants.CURR_COND_MAX_LIMIT + currCode);
			fieldBuilder.setEnabledExpression(
					new RhinoExpression("form." + Constants.CURR_COND_MAX_LIMIT_PERCENT_USE + currCode + "==false && " + requireStr));
			fieldBuilder.setType(MoneyType.INSTANCE.getName());
			fieldBuilder.addValidators(
					new RequiredFieldValidator(),
					new RegexpMoneyFieldValidator(Constants.MONEY_REGEX, "����������� '����� �������� ��'.  ������: NNNNNNN.NN (���������� �������), c ������������ �������� � ������"),
					notZeroValidator
			);
			fb.addField(fieldBuilder.build());

			fieldBuilder = new FieldBuilder();
			fieldBuilder.setDescription("(" + currCode + ") ����� �������� �� (% �� ���������)");
			fieldBuilder.setName(Constants.CURR_COND_MAX_LIMIT + currCode);
			fieldBuilder.setType(MoneyType.INSTANCE.getName());
			fieldBuilder.setEnabledExpression(
					new RhinoExpression("form." + Constants.CURR_COND_MAX_LIMIT_PERCENT_USE + currCode + "==true && " + requireStr));
			fieldBuilder.addValidators(
					new RequiredFieldValidator(),
					new RegexpFieldValidator(Constants.PERCENT_REGEX, "����������� '����� �������� ��'.  ������: NNNNNNN.NN (���������� �������), c ������������ �������� � ������"),
					notZeroPercentValidator
			);
			fb.addField(fieldBuilder.build());

			fieldBuilder = new FieldBuilder();
			fieldBuilder.setName(Constants.CURR_COND_MAX_LIMIT_INCLUDE + currCode);
			fieldBuilder.setType(BooleanType.INSTANCE.getName());
			fieldBuilder.setEnabledExpression(enableExpr);
			fb.addField(fieldBuilder.build());

			fieldBuilder = new FieldBuilder();
			fieldBuilder.setDescription("(" + currCode + ") ���������� ������ ��");
			fieldBuilder.setName(Constants.CURR_COND_MIN_PERCENT_RATE + currCode);
			fieldBuilder.setType(MoneyType.INSTANCE.getName());
			fieldBuilder.setEnabledExpression(enableExpr);
			fieldBuilder.addValidators(
					new RequiredFieldValidator(),
					new RegexpFieldValidator(Constants.PERCENT_REGEX, "����������� '���������� ������ ��'.  ������: NNNNNNN.NN (���������� �������), c ������������ �������� � ������"),
					notZeroPercentValidator
			);
			fb.addField(fieldBuilder.build());

			fieldBuilder = new FieldBuilder();
			fieldBuilder.setDescription("(" + currCode + ")���������� ������ ��");
			fieldBuilder.setName(Constants.CURR_COND_MAX_PERCENT_RATE + currCode);
			fieldBuilder.setType(MoneyType.INSTANCE.getName());
			fieldBuilder.setEnabledExpression(enableExpr);
			fieldBuilder.addValidators(
					new RequiredFieldValidator(),
					new RegexpFieldValidator(Constants.PERCENT_REGEX, "����������� '���������� ������ ��'.  ������: NNNNNNN.NN (���������� �������), c ������������ �������� � ������"),
					notZeroPercentValidator
			);
			fb.addField(fieldBuilder.build());

			fieldBuilder = new FieldBuilder();
			fieldBuilder.setName(Constants.CURR_COND_MAX_PERCENT_RATE_INCLUDE + currCode);
			fieldBuilder.setType(BooleanType.INSTANCE.getName());
			fieldBuilder.setEnabledExpression(enableExpr);
			fb.addField(fieldBuilder.build());
		}


		return fb.build();
	}

}
