package com.rssl.phizic.web.passwordcards;

import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.Field;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.validators.DateFieldValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.NumericRangeValidator;
import com.rssl.common.forms.validators.CompareValidator;

/**
 * @author Roshka
 * @ created 05.12.2005
 * @ $Author$
 * @ $Revision$
 */
public class ListUnassignedPasswordCardsForm extends ListFormBase
{
	public static Form FILTER_FORM = createForm();

	private static Form createForm()
	{
	    FormBuilder formBuilder = new FormBuilder();

	    Field[]      fields = new Field[6];
	    FieldBuilder fieldBuilder;

	    fieldBuilder = new FieldBuilder();
	    fieldBuilder.setName("number");
	    fieldBuilder.setDescription("�����");
	    fields[0] = fieldBuilder.build();

	    fieldBuilder = new FieldBuilder();
	    fieldBuilder.setName("fromDate");
	    fieldBuilder.setDescription("��������� ����");
	    fieldBuilder.setValidators(new DateFieldValidator());
	    fieldBuilder.setParser(new DateParser());
	    fields[1] = fieldBuilder.build();

	    fieldBuilder = new FieldBuilder();
	    fieldBuilder.setName("toDate");
	    fieldBuilder.setDescription("�������� ����");
	    fieldBuilder.setValidators(new DateFieldValidator());
	    fieldBuilder.setParser(new DateParser());
	    fields[2] = fieldBuilder.build();

	    fieldBuilder = new FieldBuilder();
	    fieldBuilder.setType("integer");
	    fieldBuilder.setName("passwordsCount");
	    RegexpFieldValidator regexpFieldValidator = new RegexpFieldValidator("\\d{1,5}");
	    regexpFieldValidator.setMessage("�������� ������ ������, ������� �������� ��������.");
	    fieldBuilder.setValidators(regexpFieldValidator);
	    fieldBuilder.setDescription("���������� �������");
	    fields[3] = fieldBuilder.build();

	    //���� ��� ����� ���-�� ���� � ���-�� �������
	    fieldBuilder = new FieldBuilder();
	    fieldBuilder.setName("cardsCount");
	    RegexpFieldValidator regexpFieldValidatorCardsCount = new RegexpFieldValidator("\\d{1,3}");
	    regexpFieldValidatorCardsCount.setMessage("�������� ������ ������ � ����: ���������� ����, ������� �������� �������� � ��������� 1...100.");

	    NumericRangeValidator numericRangeValidatorCardsCount = new NumericRangeValidator();
	    numericRangeValidatorCardsCount.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "1");
	    numericRangeValidatorCardsCount.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, "100");
	    numericRangeValidatorCardsCount.setMessage("�������� ������ ������ � ����: ���������� ����, ������� �������� �������� � ��������� 1...100.");

	    fieldBuilder.setValidators(regexpFieldValidatorCardsCount, numericRangeValidatorCardsCount);
	    fieldBuilder.setDescription("���������� ����");
	    fields[4] = fieldBuilder.build();

	    fieldBuilder = new FieldBuilder();
	    fieldBuilder.setName("keysCount");
	    RegexpFieldValidator regexpFieldValidatorKeysCount = new RegexpFieldValidator("\\d{1,3}");
	    regexpFieldValidatorKeysCount.setMessage("�������� ������ ������ � ����: ���������� ������, ������� �������� �������� � ��������� 1...500.");

	    NumericRangeValidator numericRangeValidatorKeysCount = new NumericRangeValidator();
	    numericRangeValidatorKeysCount.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "1");
	    numericRangeValidatorKeysCount.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, "500");
	    numericRangeValidatorKeysCount.setMessage("�������� ������ ������ � ����: ���������� ������, ������� �������� �������� � ��������� 1...500.");

	    fieldBuilder.setValidators(regexpFieldValidatorKeysCount, numericRangeValidatorKeysCount);
	    fieldBuilder.setDescription("���������� ������");
	    fields[5] = fieldBuilder.build();

	    formBuilder.setFields(fields);

	    CompareValidator compareValidator = new CompareValidator();
	    compareValidator.setParameter(CompareValidator.OPERATOR, CompareValidator.LESS_EQUAL);
	    compareValidator.setBinding(CompareValidator.FIELD_O1, "fromDate");
	    compareValidator.setBinding(CompareValidator.FIELD_O2, "toDate");
	    compareValidator.setMessage("�������� ���� ������ ���� ������ ���� ����� ���������!");
	    formBuilder.setFormValidators(compareValidator);

	    return formBuilder.build();
	}

}
