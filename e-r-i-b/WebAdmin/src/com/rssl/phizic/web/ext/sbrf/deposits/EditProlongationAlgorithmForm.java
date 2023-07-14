package com.rssl.phizic.web.ext.sbrf.deposits;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.utils.DepositConfig;
import com.rssl.phizic.web.settings.EditPropertiesFormBase;

/**
 * Форма редактирования алгоритма определения даты пролонгации вкладов
 * @author Jatsky
 * @ created 09.12.14
 * @ $Author$
 * @ $Revision$
 */

public class EditProlongationAlgorithmForm extends EditPropertiesFormBase
{
	private static final Form FORM = createForm();

	public Form getForm()
	{
		return FORM;
	}

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();

		//showServices
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(DepositConfig.NEW_ALGORITHM_FOR_PROLONGATION_DATE);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setDescription("Определение даты пролонгации вкладов");
		fieldBuilder.addValidators(requiredFieldValidator);
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
