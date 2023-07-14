package com.rssl.phizic.web.ermb.tariff;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.IntegerType;
import com.rssl.common.forms.types.MoneyType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.MoneyFieldValidator;
import com.rssl.common.forms.validators.NumericRangeValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * @author Moshenko
 * @ created 09.12.13
 * @ $Author$
 * @ $Revision$
 * ����� �������������� ������� ����
 */
public class ErmbTariffEditForm extends EditFormBase
{
	public static final Form EDIT_FORM = createForm();

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		RequiredFieldValidator requiredFieldValidator  = new RequiredFieldValidator();
		MoneyFieldValidator moneyFieldValidator  = new MoneyFieldValidator();
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("name");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("��������");
		fieldBuilder.addValidators(
				requiredFieldValidator,
				new RegexpFieldValidator(".{0,50}", "�������� ������ �� ������ ��������� 50 ��������.")
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("connectionCost");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.setDescription("�����������");
		fieldBuilder.addValidators(
				requiredFieldValidator,
				moneyFieldValidator
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("graceClassFee");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.setDescription("����������� �����. ����� �������� '��������'");
		fieldBuilder.addValidators(
				requiredFieldValidator,
				moneyFieldValidator
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("premiumClassFee");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.setDescription("����������� �����. ����� �������� '�������'");
		fieldBuilder.addValidators(
				requiredFieldValidator,
				moneyFieldValidator
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("socialClassFee");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.setDescription("����������� �����. ����� �������� '����������'");
		fieldBuilder.addValidators(
				requiredFieldValidator,
				new MoneyFieldValidator()
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("standardClassFee");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.setDescription("����������� �����. ����� �������� '��������'");
		fieldBuilder.addValidators(
				requiredFieldValidator,
				moneyFieldValidator
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("chargePeriod");
		fieldBuilder.setType(IntegerType.INSTANCE.getName());
		fieldBuilder.setDescription("������ �������� ����������� �����");
		NumericRangeValidator numericRangeValidator = new  NumericRangeValidator();
		numericRangeValidator.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "1");
		numericRangeValidator.setMessage("������ �������� ����������� ����� ������ ���� ������ ����");

		fieldBuilder.addValidators(
				requiredFieldValidator,
				new RegexpFieldValidator("\\d*", "���� '������ �������� ����������� �����' ������ �������� ������ �� ����� �����."),
				numericRangeValidator
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("gracePeriod");
		fieldBuilder.setType(IntegerType.INSTANCE.getName());
		fieldBuilder.setDescription("�������� ������");
		fieldBuilder.addValidators(
				requiredFieldValidator,
				new RegexpFieldValidator("\\d*", "���� '�������� ������' ������ �������� ������ �� ����� �����.")
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("gracePeriodCost");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.setDescription("������ ����� � �������� ������");
		fieldBuilder.addValidators(
				requiredFieldValidator,
				moneyFieldValidator
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("noticeConsIncomCardOp");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("������: ����������� �  ���������/��������� ���������, ������������ �� ����� �����");
		fieldBuilder.addValidators(
				requiredFieldValidator
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("noticeConsIncomAccountOp");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("������: ����������� � ���������/��������� ��������� �� ����� ������");
		fieldBuilder.addValidators(
				requiredFieldValidator
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("cardInfoOp");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("������: �������������� �� ������� ������� ���������� �� �����");
		fieldBuilder.addValidators(
				requiredFieldValidator
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("accountInfoOp");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("������: �������������� �� ������� ������� ���������� �� �����");
		fieldBuilder.addValidators(
				requiredFieldValidator
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("cardMiniInfoOp");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("������: �������������� �� ������� ������� ������� ������� �� ����� (����-�������)");
		fieldBuilder.addValidators(
				requiredFieldValidator
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("accountMiniInfoOp");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("������: �������������� �� ������� ������� ������� ������� �� ����� (����-�������)");
		fieldBuilder.addValidators(
				requiredFieldValidator
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("reIssueCardOp");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("������: ����������/������ �� ���������� ����� �� ������� �������");
		fieldBuilder.addValidators(
				requiredFieldValidator
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("jurPaymentOp");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("������: ������� � ������ ����������� (������ �����)");
		fieldBuilder.addValidators(
				requiredFieldValidator
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("transfersToThirdPartiesOp");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("������: �������� � ������ ������� ��� (�� �������)");
		fieldBuilder.addValidators(
				requiredFieldValidator
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("description");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("��������");
		fieldBuilder.addValidators(
				requiredFieldValidator,
				new RegexpFieldValidator(".{0,300}", "�������� ������ �� ������ ��������� 300 ��������.")
		);
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
