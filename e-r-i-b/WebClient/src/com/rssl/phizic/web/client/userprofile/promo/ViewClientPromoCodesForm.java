package com.rssl.phizic.web.client.userprofile.promo;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.LengthFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.clientPromoCodes.ClientPromoCode;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.promoCodesDeposit.PromoCodesDepositConfig;
import com.rssl.phizic.config.promoCodesDeposit.PromoCodesMessage;
import com.rssl.phizic.web.actions.ActionFormBase;

import java.util.List;

/**
 * Форма для отображения промо кодов.
 *
 * @author sergunin
 * @ created 11.12.14
 * @ $Author$
 * @ $Revision$
 */

public class ViewClientPromoCodesForm extends ActionFormBase
{

    public static final Form EDIT_FORM = createForm();

    private List<ClientPromoCode> activePromoCodes;
	private List<ClientPromoCode> archivePromoCodes;
    private String clientPromo;
    private Long id;

    private PromoCodesMessage messageNine;
    private PromoCodesMessage messageTen;
    private PromoCodesMessage messageSeven;

    private PromoCodesMessage errorMessage;
    private PromoCodesMessage error12Message;

    public List<ClientPromoCode> getActivePromoCodes()
    {
        return activePromoCodes;
    }

    public void setActivePromoCodes(List<ClientPromoCode> activePromoCodes)
    {
        this.activePromoCodes = activePromoCodes;
    }

    public List<ClientPromoCode> getArchivePromoCodes()
    {
        return archivePromoCodes;
    }

    public void setArchivePromoCodes(List<ClientPromoCode> archivePromoCodes)
    {
        this.archivePromoCodes = archivePromoCodes;
    }

    public String getClientPromo()
    {
        return clientPromo;
    }

    public void setClientPromo(String clientPromo)
    {
        this.clientPromo = clientPromo;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public PromoCodesMessage getErrorMessage()
    {
        return errorMessage;
    }

    public void setErrorMessage(PromoCodesMessage errorMessage)
    {
        this.errorMessage = errorMessage;
    }

    public PromoCodesMessage getError12Message()
    {
        return error12Message;
    }

    public void setError12Message(PromoCodesMessage error12Message)
    {
        this.error12Message = error12Message;
    }

    public PromoCodesMessage getMessageNine()
    {
        return messageNine;
    }

    public void setMessageNine(PromoCodesMessage messageNine)
    {
        this.messageNine = messageNine;
    }

    public PromoCodesMessage getMessageTen()
    {
        return messageTen;
    }

    public void setMessageTen(PromoCodesMessage messageTen)
    {
        this.messageTen = messageTen;
    }

    public PromoCodesMessage getMessageSeven()
    {
        return messageSeven;
    }

    public void setMessageSeven(PromoCodesMessage messageSeven)
    {
        this.messageSeven = messageSeven;
    }

    private static Form createForm()
    {
        FormBuilder formBuilder = new FormBuilder();

        FieldBuilder fb = new FieldBuilder();
        fb.setName("clientPromo");
        fb.setDescription("Новый промо-код");
        fb.setType(StringType.INSTANCE.getName());
        fb.addValidators(new RequiredFieldValidator());
        formBuilder.addField(fb.build());

        return formBuilder.build();
    }
}
