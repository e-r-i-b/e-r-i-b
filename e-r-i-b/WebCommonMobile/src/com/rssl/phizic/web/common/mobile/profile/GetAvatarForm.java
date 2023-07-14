package com.rssl.phizic.web.common.mobile.profile;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * @author sergunin
 * @ created 29.10.14
 * $Author$
 * $Revision$
 * Получение аватара по номеру телефона из профиля клиента ЕРИБ
 */
public class GetAvatarForm extends ActionFormBase
{
	public static final Form EDIT_FORM = createForm();

    private String phoneNumber;
    private String avatarPath;

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public String getAvatarPath()
    {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath)
    {
        this.avatarPath = avatarPath;
    }

    private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder;

        fieldBuilder = new FieldBuilder();
        fieldBuilder.setName("phoneNumber");
		fieldBuilder.setDescription("Номер телефона");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
                    new RequiredFieldValidator()
                );
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}

}
