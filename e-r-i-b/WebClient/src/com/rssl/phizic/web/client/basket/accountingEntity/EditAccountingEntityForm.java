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
 * Форма редактирования объекта учета
 */
public class EditAccountingEntityForm extends EditFormBase
{
	public static final String NAME_FIELD = "name";

	public static final Form EDIT_FORM = createEditForm();

	private boolean needUngroupSubscriptions;

	/**
	 * @return Необходимо ли разруппировать подписки (есть ли подписки, привязанные к данному объекту учета)?
	 */
	public boolean isNeedUngroupSubscriptions()
	{
		return needUngroupSubscriptions;
	}

	/**
	 * @param needUngroupSubscriptions Необходимо ли разруппировать подписки (есть ли подписки, привязанные к данному объекту учета)?
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
		fieldBuilder.setDescription("Наименование объекта учета");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator(".{0,100}", "Наименование объекта учета не должно превышать 100 символов."));
		formBuilder.addField(fieldBuilder.build());
		return formBuilder.build();
	}
}
