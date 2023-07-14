package com.rssl.phizic.web.finances;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.parsers.ListLongParser;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * @author Koptyaev
 * @ created 17.10.13
 * @ $Author$
 * @ $Revision$
 */
public class EditCardOperationCategoryForm extends EditFormBase
{
	public static final Form EDIT_FORM = createForm();

	private static Form createForm()
	{
		ListLongParser validateParser = new ListLongParser("����������, ������� ���������� ������ MCC-�����.");
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder;

		RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();

		RequiredFieldValidator requiredColorFieldValidator = new RequiredFieldValidator("������� ���� ���������");
		requiredColorFieldValidator.setEnabledExpression(new RhinoExpression("form.visibility == true"));

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��������");
		fieldBuilder.setName("name");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
				requiredFieldValidator,
				new RegexpFieldValidator(".{1,128}", "�������� ������ ���� �� ����� 128 ��������.")
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��-���������");
		fieldBuilder.setName("isDefault");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������� ����� ������ �������.");
		fieldBuilder.setName("forInternalOperations");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������");
		fieldBuilder.setName("transfer");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("���");
		fieldBuilder.setName("type");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.addValidators(requiredFieldValidator);
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.isDefault != true"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("���������");
		fieldBuilder.setName("visibility");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.addValidators(requiredFieldValidator);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("ID � mAPI");
		fieldBuilder.setName("idInmAPI");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator(".{0,30}", "ID � mAPI ������ ���� �� ����� 30 ��������."));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("MCC-����");
		fieldBuilder.setName("mcc");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
				validateParser
		);
		fieldBuilder.setParser(validateParser);
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.isDefault !=true && form.forInternalOperations !=true"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("���� ���������");
		fieldBuilder.setName("color");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(requiredColorFieldValidator, new RegexpFieldValidator("^[a-fA-F0-9]{6}$", "������� ���� ��������� � ������� #FFFFFF."));
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
