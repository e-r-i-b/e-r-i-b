package com.rssl.phizic.web.client.basket.accountingEntity;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * @author osminin
 * @ created 09.04.14
 * @ $Author$
 * @ $Revision$
 *
 * ����� �������������� ������� �����
 */
public class EditAccountingEntityForm extends EditFormBase
{
	public static final String NAME_FIELD = "name";

	public static final Form EDIT_FORM = createEditForm();

	private boolean needUngroupSubscriptions;

	/**
	 * @return ���������� �� �������������� �������� (���� �� ��������, ����������� � ������� ������� �����)?
	 */
	public boolean isNeedUngroupSubscriptions()
	{
		return needUngroupSubscriptions;
	}

	/**
	 * @param needUngroupSubscriptions ���������� �� �������������� �������� (���� �� ��������, ����������� � ������� ������� �����)?
	 */
	public void setNeedUngroupSubscriptions(boolean needUngroupSubscriptions)
	{
		this.needUngroupSubscriptions = needUngroupSubscriptions;
	}

	private static Form createEditForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(NAME_FIELD);
		fieldBuilder.setDescription("������������ ������� �����");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator(".{0,100}", "������������ ������� ����� �� ������ ��������� 100 ��������."));
		formBuilder.addField(fieldBuilder.build());
		return formBuilder.build();
	}
}
