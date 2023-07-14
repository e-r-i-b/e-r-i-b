package com.rssl.phizic.web.pfp;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.types.MoneyType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.business.dictionaries.pfp.targets.TargetCountService;
import com.rssl.phizic.business.pfp.PersonTarget;
import com.rssl.phizic.utils.ListUtil;

import java.util.List;

/**
 * @author mihaylov
 * @ created 05.03.2012
 * @ $Author$
 * @ $Revision$
 */

public class EditPfpPersonalDataForm extends EditPersonalFinanceProfileForm
{
	private List<PersonTarget> personTargetList;//���� �������
	public static final Form EDIT_FORM = getEditForm();

	public List<PersonTarget> getPersonTargetList()
	{
		return personTargetList;
	}

	public void setPersonTargetList(List<PersonTarget> personTargetList)
	{
		this.personTargetList = personTargetList;
	}

	public Long getMaxTargetCount()
	{
		return TargetCountService.getTargetCountSafe();
	}

	private static Form getEditForm()
	{
		RequiredFieldValidator requiredForEditValidator = new RequiredFieldValidator();
		requiredForEditValidator.setEnabledExpression(new RhinoExpression("!form.isViewMode"));
		MoneyFieldValidator moneyFieldValidator = new MoneyFieldValidator();
		moneyFieldValidator.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, "9999999999999.99");

		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������� ��� ��������");
		fieldBuilder.setName("personFIO");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("isViewMode");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������");
		fieldBuilder.setName("personAge");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������� ���������");
		fieldBuilder.setName("maritalStatus");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(requiredForEditValidator,
				new ChooseValueValidator(ListUtil.fromArray(new String[]{"MARRIED","NOT_MARRIED"})));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����");
		fieldBuilder.setName("childCount");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(requiredForEditValidator,
				new ChooseValueValidator(ListUtil.fromArray(new String[]{"NONE","ONE","TWO","THREE","FOUR","FIVE","moreThanFIVE"})));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������� � ����������� �������� �� ������ � �������: � ���������");
		fieldBuilder.setName("shortTermAssetsSBRF");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(moneyFieldValidator);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������� � ����������� �������� �� ������ � �������: � ������ ������");
		fieldBuilder.setName("shortTermAssetsOtherBanks");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(moneyFieldValidator);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������� � ����������� �������� �� ������ � �������: ��������");
		fieldBuilder.setName("shortTermAssetsCash");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(moneyFieldValidator);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������������� ��������: ����");
		fieldBuilder.setName("mediumTermAssetsFunds");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(moneyFieldValidator);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������������� ��������: ���");
		fieldBuilder.setName("mediumTermAssetsIMA");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(moneyFieldValidator);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������������� ��������: ������ ����������");
		fieldBuilder.setName("mediumTermAssetsOther");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(moneyFieldValidator);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("���������� �������� ������������ � �������������");
		fieldBuilder.setName("apartmentCount");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(requiredForEditValidator,
				new ChooseValueValidator(ListUtil.fromArray(new String[]{"NONE","ONE","TWO","THREE","FOUR","FIVE","moreThanFIVE"})));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������");
		fieldBuilder.setName("mortgageCount");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new ChooseValueValidator(ListUtil.fromArray(new String[]{"NONE","ONE","TWO","THREE","moreThanTHREE"})));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��������������� ������");
		fieldBuilder.setName("consumerLoanCount");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new ChooseValueValidator(ListUtil.fromArray(new String[]{"NONE","ONE","TWO","THREE","moreThanTHREE"})));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����������");
		fieldBuilder.setName("autoLoanCount");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new ChooseValueValidator(ListUtil.fromArray(new String[]{"NONE","ONE","TWO","THREE","moreThanTHREE"})));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��������� �����");
		fieldBuilder.setName("creditCardCount");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new ChooseValueValidator(ListUtil.fromArray(new String[]{"NONE","ONE","TWO","THREE","moreThanTHREE"})));
		formBuilder.addField(fieldBuilder.build());


		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("���� ����������� ������");
		fieldBuilder.setName("incomeMoney");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(requiredForEditValidator, moneyFieldValidator);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("���� ����������� �������");
		fieldBuilder.setName("outcomeMoney");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(requiredForEditValidator, moneyFieldValidator);
		formBuilder.addField(fieldBuilder.build());

		CompareValidator compareValidator = new CompareValidator();
	    compareValidator.setParameter(CompareValidator.OPERATOR, CompareValidator.GREATE_EQUAL);
	    compareValidator.setBinding(CompareValidator.FIELD_O1, "incomeMoney");
	    compareValidator.setBinding(CompareValidator.FIELD_O2, "outcomeMoney");
	    compareValidator.setMessage("���� ����������� ������� ��������� ������.");
	    formBuilder.setFormValidators(compareValidator);

		return formBuilder.build();
	}
}
