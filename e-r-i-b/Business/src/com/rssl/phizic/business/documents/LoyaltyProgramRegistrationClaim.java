package com.rssl.phizic.business.documents;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.TypeOfPayment;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.cards.CardsUtil;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.common.types.MessageCollector;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.util.ApplicationUtil;
import com.rssl.phizic.utils.StringHelper;
import org.w3c.dom.Document;

import java.util.List;

/**
 * Заявка на подключение бонусной программы
 * @author gladishev
 * @ created 09.08.2012
 * @ $Author$
 * @ $Revision$
 */
@SuppressWarnings({"ClassNameSameAsAncestorName"})
public class LoyaltyProgramRegistrationClaim extends GateExecutableDocument implements com.rssl.phizic.gate.claims.LoyaltyProgramRegistrationClaim
{
	private static final String CARD_NUMBER_ATTRIBUTE_NAME = "card-number";
	private static final String PHONE_ATTRIBUTE_NAME = "phone";
	private static final String EMAIL_ATTRIBUTE_NAME = "email";

	public void initialize(Document document, MessageCollector messageCollector) throws DocumentException, DocumentLogicException
	{
		super.initialize(document, messageCollector);
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		ActivePerson activePerson = personData.getPerson();
		if (!StringHelper.isEmpty(activePerson.getEmail()))
			setEmail(activePerson.getEmail());
		if (!StringHelper.isEmpty(activePerson.getMobilePhone()))
			setPhoneNumber(activePerson.getMobilePhone());

		Login login = activePerson.getLogin();
		String lastLogonCardNumber = login.getLastLogonCardNumber();
		try
		{
			if (ApplicationUtil.isErmbSms())
			{
				List<CardLink> cards = personData.getCards();
				CardLink cardLink = CardsUtil.getLongTermCard(cards);
				setCardNumber(cardLink.getNumber());
			}
			else
			{
				setCardNumber(lastLogonCardNumber);
			}
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
		catch (BusinessLogicException e)
		{
			throw new DocumentLogicException(e);
		}
	}

	public TypeOfPayment getTypeOfPayment()
	{
		return TypeOfPayment.NOT_PAYMENT_OPEATION;
	}

	public Class<? extends GateDocument> getType()
	{
		return com.rssl.phizic.gate.claims.LoyaltyProgramRegistrationClaim.class;
	}

	public String getCardNumber()
	{
		return getNullSaveAttributeStringValue(CARD_NUMBER_ATTRIBUTE_NAME);
	}

	public void setCardNumber(String cardNumber)
	{
		setNullSaveAttributeStringValue(CARD_NUMBER_ATTRIBUTE_NAME, cardNumber);
	}

	public String getPhoneNumber()
	{
		return getNullSaveAttributeStringValue(PHONE_ATTRIBUTE_NAME);
	}

	public String getEmail()
	{
		return getNullSaveAttributeStringValue(EMAIL_ATTRIBUTE_NAME);
	}

	public void setPhoneNumber(String phoneNumber)
	{
		setNullSaveAttributeStringValue(PHONE_ATTRIBUTE_NAME, phoneNumber);
	}

	public void setEmail(String email)
	{
		setNullSaveAttributeStringValue(EMAIL_ATTRIBUTE_NAME, email);
	}
}
