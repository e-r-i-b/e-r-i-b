package com.rssl.phizic.business.dictionaries.contact;

import com.rssl.phizic.business.dictionaries.PaymentReceiverBase;
import com.rssl.phizic.business.dictionaries.receivers.personal.PaymentReceiverXmlSerializer;
import com.rssl.phizic.gate.dictionaries.ContactMember;

/**
 * @author Kosyakova
 * @ created 09.01.2007
 * @ $Author$
 * @ $Revision$
 */
public class ContactReceiver extends PaymentReceiverBase
{
	private ContactMember bank;
	private String surName;
	private String firstName;
	private String patrName;
	private String addInfo;
	private String birthDay;
	private Long   receiverId;

	public String getBirthDay()
	{
		return birthDay;
	}

	public void setBirthDay(String birthDay)
	{
		this.birthDay = birthDay;
	}

	public ContactMember getBank()
	{
		return bank;
	}

	public void setBank(ContactMember bank)
	{
		this.bank = bank;
	}

	public String getSurName()
	{
		return surName;
	}

	public void setSurName(String surName)
	{
		this.surName = surName;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getAddInfo()
	{
		return addInfo;
	}

	public void setAddInfo(String addInfo)
	{
		this.addInfo = addInfo;
	}

	public String getPatrName()
	{
		return patrName;
	}

	public void setPatrName(String patrName)
	{
		this.patrName = patrName;
	}

	public Long getReceiverId()
	{
		return receiverId;
	}

	public void setReceiverId(Long receiverId)
	{
		this.receiverId = receiverId;
	}

	public PaymentReceiverXmlSerializer getXmlSerializer()
	{
		return new PaymentReceiverXmlSerializer(this);
	}
}
