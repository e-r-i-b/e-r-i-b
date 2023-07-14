package com.rssl.phizic.common.type;

import com.rssl.phizic.common.types.client.ClientDocumentType;
import com.rssl.phizic.person.PersonDocumentType;
import com.rssl.phizic.utils.PassportTypeWrapper;
import com.rssl.phizic.utils.StringHelper;

import java.util.Calendar;

/**
 @author: EgorovaA
 @ created: 25.12.2012
 @ $Author$
 @ $Revision$
 */

/**
 * Идентификационные данные клиента (ФИО, дата рождения, документ)
 */
public class ErmbProfileIdentity
{
	private String firstName;
	private String surName;
	private String patrName;
	private Calendar birthDay;
	//Время изменения документа. Заполнено только для устаревших записей. Для актуальных - Null
	private Calendar docTimeUpDate;
	private ErmbProfileIdentityCard identityCard;
	private String tb;

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getSurName()
	{
		return surName;
	}

	public void setSurName(String surName)
	{
		this.surName = surName;
	}

	public String getPatrName()
	{
		return patrName;
	}

	public void setPatrName(String patrName)
	{
		this.patrName = patrName;
	}

	public Calendar getBirthDay()
	{
		return birthDay;
	}

	public void setBirthDay(Calendar birthDay)
	{
		this.birthDay = birthDay;
	}

	public ErmbProfileIdentityCard getIdentityCard()
	{
		return identityCard;
	}

	public void setIdentityCard(ErmbProfileIdentityCard identityCard)
	{
		this.identityCard = identityCard;
	}

	public Calendar getDocTimeUpDate()
	{
		return docTimeUpDate;
	}

	public void setDocTimeUpDate(Calendar docTimeUpDate)
	{
		this.docTimeUpDate = docTimeUpDate;
	}

	public String getTb()
	{
		return tb;
	}

	public void setTb(String tb)
	{
		this.tb = tb;
	}

	/**
	 * Возвращает текущий объект в виде PersonIdentity
	 */
	public PersonIdentity getPersonIdentity()
	{
		if (StringHelper.isEmpty(firstName)||StringHelper.isEmpty(surName)||StringHelper.isEmpty(tb))
			throw new IllegalArgumentException("Невозможно построить PersonIdentity, не хватает обязательных полей.");

		PersonIdentity personIdentity = new PersonOldIdentity();
		personIdentity.setFirstName(firstName);
		personIdentity.setSurName(surName);
		personIdentity.setPatrName(patrName);
		personIdentity.setBirthDay(birthDay);
		personIdentity.setRegion(tb);


		if (identityCard != null)
		{
			ClientDocumentType personDocumentType = PassportTypeWrapper.getClientDocumentType(identityCard.getIdType());
			personIdentity.setDocType(PersonDocumentType.valueOf(personDocumentType));
			if ("300".equals(identityCard.getIdType()))
			{
				personIdentity.setDocSeries(StringHelper.getEmptyIfNull(identityCard.getIdSeries()) + StringHelper.getEmptyIfNull(identityCard.getIdNum()));
				personIdentity.setDocNumber("");
			}
			else
			{
				personIdentity.setDocSeries(identityCard.getIdSeries());
				personIdentity.setDocNumber(identityCard.getIdNum());
			}
			personIdentity.setDocIssueBy(identityCard.getIssuedBy());
			personIdentity.setDocIssueDate(identityCard.getIssueDt());
		}
		return personIdentity;
	}
}
