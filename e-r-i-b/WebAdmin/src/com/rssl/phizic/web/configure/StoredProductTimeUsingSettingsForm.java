package com.rssl.phizic.web.configure;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.config.ResourceTypeKey;
import com.rssl.phizic.web.settings.EditPropertiesFormBase;

/**
 * User: Balovtsev
 * Date: 08.11.2012
 * Time: 11:45:16
 */
public class StoredProductTimeUsingSettingsForm extends EditPropertiesFormBase
{
	private final static Form TIME_USING_CONFIG_FORM = createForm();

	@Override
	public Form getForm()
	{
		return TIME_USING_CONFIG_FORM;
	}

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(ResourceTypeKey.ACCOUNT_TYPE_KEY.toValue());
		fieldBuilder.setType("long");
		fieldBuilder.setDescription("Информация по вкладам");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(ResourceTypeKey.ACCOUNT_INFO_TYPE_KEY.toValue());
		fieldBuilder.setType("long");
		fieldBuilder.setDescription("Дополнительная информация по вкладам");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(ResourceTypeKey.CARD_TYPE_KEY.toValue());
		fieldBuilder.setType("long");
		fieldBuilder.setDescription("Информация по картам");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(ResourceTypeKey.CARD_INFO_TYPE_KEY.toValue());
		fieldBuilder.setType("long");
		fieldBuilder.setDescription("Дополнительная информация по картам");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(ResourceTypeKey.DEPOACCOUNT_TYPE_KEY.toValue());
		fieldBuilder.setType("long");
		fieldBuilder.setDescription("Информация по счетам ДЕПО");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(ResourceTypeKey.IMACCOUNT_TYPE_KEY.toValue());
		fieldBuilder.setType("long");
		fieldBuilder.setDescription("Информация по металлическим счетам");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(ResourceTypeKey.LOAN_TYPE_KEY.toValue());
		fieldBuilder.setType("long");
		fieldBuilder.setDescription("Информация по кредитам");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(ResourceTypeKey.SECURITY_ACC_TYPE_KEY.toValue());
		fieldBuilder.setType("long");
		fieldBuilder.setDescription("Информация по сберегательным сертификатам");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(ResourceTypeKey.LOAN_INFO_TYPE_KEY.toValue());
		fieldBuilder.setType("long");
		fieldBuilder.setDescription("Дополнительная информация по кредитам");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(ResourceTypeKey.LONG_OFFER_TYPE_KEY.toValue());
		fieldBuilder.setType("long");
		fieldBuilder.setDescription("Дополнительная информация по длительным поручениям");
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
