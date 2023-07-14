package com.rssl.phizic.web.ext.sbrf.dictionaries;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.utils.IMAConfig;
import com.rssl.phizic.web.settings.EditPropertiesFormBase;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Форма редактирования настроек загрузки справочника ОМС
 * @author Pankin
 * @ created 24.07.2012
 * @ $Author$
 * @ $Revision$
 */

public class EditIMASynchronizeSettingsForm extends EditPropertiesFormBase
{
	static final Form EDIT_FORM = createForm();
	static final Form EDIT_FIELD_FORM = createEditForm();

	private List<Long> imaTypes; // номера видов ОМС
	private Map<Long, List<Long>> numbers; //Карта <вид, список подвидов> для загрузки в форму

	private String[] kinds; //Виды для загрузки из формы. Индексация должна совпадать с subkinds.
	private String[] subkinds; //Подвиды для загрузки из формы. Индексация должна совпадать с kinds.

	public List<Long> getImaTypes()
	{
		return imaTypes;
	}

	public void setImaTypes(List<Long> imaTypes)
	{
		this.imaTypes = imaTypes;
	}

	public Map<Long, List<Long>> getNumbers()
	{
		return numbers;
	}

	public void setNumbers(Map<Long, List<Long>> numbers)
	{
		this.numbers = numbers;
	}

	public String[] getKinds()
	{
		return kinds;
	}

	public void setKinds(String[] kinds)
	{
		this.kinds = kinds;
	}

	public String[] getSubkinds()
	{
		return subkinds;
	}

	public void setSubkinds(String[] subkinds)
	{
		this.subkinds = subkinds;
	}

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		@SuppressWarnings({"TooBroadScope"})
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(IMAConfig.IMA_PRODUCT_MODE);
		fieldBuilder.setDescription("Способ загрузки");
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}

	private static Form createEditForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("kind");
		fieldBuilder.setDescription("Код вида вклада");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("subkinds");
		fieldBuilder.setDescription("Коды подвидов вклада");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator("^((\\s*\\d{1,19}\\s*)(,\\s*\\d{1,19}\\s*)*)?$",
				"Введите корректное значение кодов подвидов ОМС - числа, разделенные запятыми."));
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}


	@Override
	public Form getForm()
	{
		return EDIT_FORM;
	}

	@Override
	public Set<String> getFieldKeys()
	{
		Set<String> result = super.getFieldKeys();
		result.add(IMAConfig.IMA_PRODUCT_USED_KINDS);
		return result;
	}
}
