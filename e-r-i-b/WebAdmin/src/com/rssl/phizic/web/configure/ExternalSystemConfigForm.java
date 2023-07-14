package com.rssl.phizic.web.configure;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.web.common.FilterActionForm;

import java.util.LinkedList;
import java.util.List;
/**
 * @author krenev
 * @ created 28.04.2010
 * @ $Author$
 * @ $Revision$
 */

public class ExternalSystemConfigForm extends FilterActionForm
{
	/**
	 * Список типов внешних систем.
	 */
	private static final List<Pair<String, String>> externalSystems = new LinkedList<Pair<String, String>>();

	public static final Form FORM = createForm();

	static
	{
		for (ExternalSystemType type : ExternalSystemType.values())
		{
			externalSystems.add(new Pair<String, String>(type.name(), type.getDescription()));
		}
	}

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("adapterName");
		fieldBuilder.setDescription("Наименование адаптера");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("adapterId");
		fieldBuilder.setDescription("Идентификатор адаптера IQWAVE");
		fieldBuilder.setType("integer");
		fieldBuilder.addValidators(new RequiredFieldValidator("Выберите адаптер"));
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}

	/**
	 * @return список внешних систем.
	 */
	public List<Pair<String, String>> getExternalSystemsList()
	{
	   return externalSystems;
	}
}