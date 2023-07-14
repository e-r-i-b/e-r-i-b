package com.rssl.phizic.web.common.mobile.profile;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.actions.ActionFormBase;
import com.rssl.phizic.web.actions.StrutsUtils;

/**
 * @author sergunin
 * @ created 28.10.14
 * $Author$
 * $Revision$
 * Форма добавление/изменение/удаление номера карты для контакта в адресной книге ЕРИБ
 */
public class EditAddressBookForm extends ActionFormBase
{
	public static final Form EDIT_FORM = createForm();
	public static final Form DELETE_FORM = deleteForm();

    private Long contactId;
    private String cardNumber;

    public String getCardNumber()
    {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber)
    {
        this.cardNumber = cardNumber;
    }

    public Long getContactId()
    {
        return contactId;
    }

    public void setContactId(Long contactId)
    {
        this.contactId = contactId;
    }

    private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder;

        fieldBuilder = new FieldBuilder();
        fieldBuilder.setName("cardNumber");
		fieldBuilder.setDescription("Номер карты");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
                    new RequiredFieldValidator(),
                    new RegexpFieldValidator("\\d{15,19}", StrutsUtils.getMessage("message.mobile.contact.too.short.card.number", "commonBundle")),
                    new RegexpFieldValidator("^[4|5|6]\\d*", StrutsUtils.getMessage("message.mobile.contact.wrong.card.number.start.digit", "commonBundle"))
                );
		formBuilder.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("contactId");
		fieldBuilder.setDescription("ID контакта");
		fieldBuilder.setType(LongType.INSTANCE.getName());
        fieldBuilder.addValidators(
                    new RequiredFieldValidator()
                );
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}

    private static Form deleteForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder;

        fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("contactId");
		fieldBuilder.setDescription("ID контакта");
		fieldBuilder.setType(LongType.INSTANCE.getName());
        fieldBuilder.addValidators(
                    new RequiredFieldValidator()
                );
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
