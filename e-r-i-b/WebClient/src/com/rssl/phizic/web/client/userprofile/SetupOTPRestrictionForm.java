package com.rssl.phizic.web.client.userprofile;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.auth.modes.ConfirmStrategy;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lepihina
 * @ created 13.12.2011
 * @ $Author$
 * @ $Revision$
 */
public class SetupOTPRestrictionForm extends EditFormBase
{
	protected static final String OTP_GET_FIELD = "OTPGet";
	protected static final String OTP_USE_FIELD = "OTPUse";


	private List<CardLink> cards = new ArrayList<CardLink>();
	private List<CardLink> changedCards = new ArrayList<CardLink>();
	private ConfirmableObject confirmableObject;
    private ConfirmStrategy confirmStrategy;


	public ConfirmStrategy getConfirmStrategy()
	{
		return confirmStrategy;
	}

	public void setConfirmStrategy(ConfirmStrategy confirmStrategy)
	{
		this.confirmStrategy = confirmStrategy;
	}
	public List<CardLink> getCards()
	{
		return cards;
	}

	public void setCards(List<CardLink> cards)
	{
		this.cards = cards;
	}

	public List<CardLink> getChangedCards()
	{
		return changedCards;
	}

	public void setChangedCards(List<CardLink> changedCards)
	{
		this.changedCards = changedCards;
	}

	public ConfirmableObject getConfirmableObject()
	{
		return confirmableObject;
	}

	public void setConfirmableObject(ConfirmableObject confirmableObject)
	{
		this.confirmableObject = confirmableObject;
	}

	public Form createForm(List<CardLink> cardLinks)
	{
		FormBuilder formBuilder  = new FormBuilder();
		FieldBuilder fieldBuilder = null;

		for (CardLink cardLink : cardLinks)
		{
			// разрешена ли печать одноразовых паролей
			fieldBuilder = new FieldBuilder();
			fieldBuilder.setName(OTP_GET_FIELD + cardLink.getId());
			fieldBuilder.setType("boolean");
			fieldBuilder.setDescription("Разрешена печать одноразовых паролей");
			formBuilder.addField(fieldBuilder.build());

			// использовать ли распечатанные ранее чековые пароли
			fieldBuilder = new FieldBuilder();
			fieldBuilder.setName(OTP_USE_FIELD + cardLink.getId());
			fieldBuilder.setType("boolean");
			fieldBuilder.setDescription("Использовать распечатанные ранее чековые пароли");
			formBuilder.addField(fieldBuilder.build());
		}

		return formBuilder.build();
	}
}
