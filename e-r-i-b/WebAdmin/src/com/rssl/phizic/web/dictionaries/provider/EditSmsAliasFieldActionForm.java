package com.rssl.phizic.web.dictionaries.provider;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.types.StringType;
import com.rssl.phizic.business.ermb.provider.ServiceProviderSmsAlias;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.Map;
import java.util.Set;

/**
 * User: Moshenko
 * Date: 23.05.2013
 * Time: 16:34:20
 * ����� ������ ��������� ���������� ����� ������� � ������� ��� �������
 */
public class EditSmsAliasFieldActionForm extends EditFormBase
{
	private ServiceProviderSmsAlias alias;

	private String[] aliasFieldEditable;

	public ServiceProviderSmsAlias getAlias()
	{
		return alias;
	}

	public void setAlias(ServiceProviderSmsAlias alias)
	{
		this.alias = alias;
	}

	public String[] getAliasFieldEditable()
	{
		return aliasFieldEditable;
	}

	public void setAliasFieldEditable(String[] aliasFieldEditable)
	{
		this.aliasFieldEditable = aliasFieldEditable;
	}

	public Form createFilterForm(EditFormBase frm)
	{
		Map<String,Object> fields = frm.getFields();
		if (fields.isEmpty())
			return FormBuilder.EMPTY_FORM;

		Set<String> fieldsName = fields.keySet();
		FormBuilder formBuilder = new FormBuilder();
		for (String fieldName:fieldsName)
		{
			FieldBuilder fieldBuilder = new FieldBuilder();
			fieldBuilder.setDescription("�������� �� ���������");
			fieldBuilder.setName(fieldName);
			fieldBuilder.setType(StringType.INSTANCE.getName());
			fieldBuilder.addValidators(
					new RegexpFieldValidator(".{0,50}", "�������� �� ��������� �� ������ ��������� 50 ��������"),
					new RegexpFieldValidator("[^@&��]*", "�� ������� ������������ �������, ��������, @, &, �, � . ����������, ������� ������ �������� � ���� �������� �� ���������.")
			);
			formBuilder.addField(fieldBuilder.build());
		}

		return formBuilder.build();
	}
}
