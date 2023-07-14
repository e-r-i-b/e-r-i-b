package com.rssl.phizic.web.ext.sbrf.dictionaries;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.utils.DepositConfig;
import com.rssl.phizic.web.settings.EditPropertiesFormBase;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Pankin
 * @ created 20.05.2011
 * @ $Author$
 * @ $Revision$
 */

public class EditDepositSynchronizeSettingsForm extends EditPropertiesFormBase
{
	static final Form FILTER_FORM = createFilterForm();
	static final Form EDIT_FORM = createEditForm();

	private Long minCode; //мин. значение диапазона кодов карточных видов вкладов
	private Long maxCode; //макс. значение диапазона кодов карточных видов вкладов
	private Map<Long, List<Long>> numbers; // арта <вид, список подвидов> дл€ загрузки в форму

	private String[] kinds; //¬иды дл€ загрузки из формы. »ндексаци€ должна совпадать с subkinds.
	private String[] subkinds; //ѕодвиды дл€ загрузки из формы. »ндексаци€ должна совпадать с kinds.

	public Long getMinCode()
	{
		return minCode;
	}

	public void setMinCode(Long minCode)
	{
		this.minCode = minCode;
	}

	public Long getMaxCode()
	{
		return maxCode;
	}

	public void setMaxCode(Long maxCode)
	{
		this.maxCode = maxCode;
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

	private static Form createFilterForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		@SuppressWarnings({"TooBroadScope"})
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(DepositConfig.DEPOSITPRODUCT_KINDS_UPLOADING_MODE);
		fieldBuilder.setDescription("—пособ загрузки");
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}

	private static Form createEditForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("kind");
		fieldBuilder.setDescription(" од вида вклада");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("subkinds");
		fieldBuilder.setDescription(" оды подвидов вклада");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator("^((\\s*\\d{1,2}\\s*)(,\\s*\\d{1,19}\\s*)*)?$",
				"¬ведите корректное значение кодов подвидов вклада - числа, разделенные зап€тыми."));
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}

	@Override
	public Form getForm()
	{
		return FILTER_FORM;
	}

	@Override
	public Set<String> getFieldKeys()
	{
		Set<String> result = super.getFieldKeys();
		result.add(DepositConfig.DEPOSITPRODUCT_KINDS_ALLOWED_UPLOADING);
		return result;
	}
}
