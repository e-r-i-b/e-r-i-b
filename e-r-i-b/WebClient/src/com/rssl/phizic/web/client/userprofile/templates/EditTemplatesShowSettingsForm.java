package com.rssl.phizic.web.client.userprofile.templates;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.web.common.FilterActionForm;

import java.util.List;

/**
 * @author osminin
 * @ created 05.04.2013
 * @ $Author$
 * @ $Revision$
 *
 * Форма редактирования настроек видимости шаблонов
 */
public class EditTemplatesShowSettingsForm extends FilterActionForm
{
	public static final String CHANNEL_TYPE_NAME_FIELD = "channelType";
	public static final String CHANGED_IDS_NAME_FIELD = "changedIds";

	public static final Form FORM = createForm();

	private List<TemplateDocument> templates;

	public List<TemplateDocument> getTemplates()
	{
		return templates;
	}

	public void setTemplates(List<TemplateDocument> templates)
	{
		this.templates = templates;
	}

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(CHANNEL_TYPE_NAME_FIELD);
		fieldBuilder.setDescription("Тип канала");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator("mobile|atm|sms|internet"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(CHANGED_IDS_NAME_FIELD);
		fieldBuilder.setDescription("Список идентификаторов изменненых шаблонов");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
