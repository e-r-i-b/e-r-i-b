package com.rssl.phizic.web.dictionaries.provider;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.parsers.BigDecimalParser;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;

/**
 * User: hudyakov
 * Date: 26.02.2010
 * Time: 14:25:49
 */
public class EditCommissionServiceProviderForm extends EditServiceProviderFormBase
{
	public static final Form COMISSION_FORM = createComissionForm();

	private static Form createComissionForm()
	{
		FormBuilder fb = new FormBuilder();

		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Минимальная комиссия");
		fieldBuilder.setName("minCommission");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setParser(new BigDecimalParser());
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator("^\\d{1,8}(\\.\\d{0,2}){0,1}$", "Укажите минимальную комиссию в правильном формате: ########.##")
			);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Максимальная комиссия");
		fieldBuilder.setName("maxCommission");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setParser(new BigDecimalParser());
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator("^\\d{1,8}(\\.\\d{0,2}){0,1}$", "Укажите максимальную комиссию в правильном формате: ########.##")
			);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Процентная ставка");
		fieldBuilder.setName("rate");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setParser(new BigDecimalParser());
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator("^\\d{1,5}(\\.\\d{0,2}){0,1}$", "Укажите процентную ставку в правильном формате: #####.##")
			);
		fb.addField(fieldBuilder.build());

		return fb.build();
	}
}
