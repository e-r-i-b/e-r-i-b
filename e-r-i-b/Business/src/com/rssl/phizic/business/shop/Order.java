package com.rssl.phizic.business.shop;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.person.Person;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Mescheryakova
 * @ created 03.12.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * ����� ��� ��������� ����� �������� � ������
 */
public class Order extends NotificatedBean
{
	private Long id;
	private Calendar date;             // ���� ������
	private String systemName;         // ��� �������
	private String extendedId;         // ������� ������������� ������
	private Money amount;               // ����� ������
	private String description;        // �������� �������
	private String receiverAccount;    // ����� ����� ����������
	private String BIC;                // ��� ����� ����������
	private String correspondentAccount;  // ���. ���� ����� ����������
	private String INN;                // ��� ����������
	private String KPP;                // ��� ����������
	private String receiverName;       // ������������ ����������
	private Person person;               // ������������
	private String uuid;                 // ������������� �������
	private Date   notificationTime;     //����� �������� �����������(����������)
	private String additionalFields;    //�������������� ���� � ���� json-������
	private String printDesc;           //���������� � ������ ��� �������� �����.

	public String getCorrespondentAccount()
	{
		return correspondentAccount;
	}

	public void setCorrespondentAccount(String correspondentAccount)
	{
		this.correspondentAccount = correspondentAccount;
	}

	public Money getAmount()
	{
		return amount;
	}

	public void setAmount(Money amount)
	{
		this.amount = amount;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getReceiverAccount()
	{
		return receiverAccount;
	}

	public void setReceiverAccount(String receiverAccount)
	{
		this.receiverAccount = receiverAccount;
	}

	public String getBIC()
	{
		return BIC;
	}

	public void setBIC(String BIC)
	{
		this.BIC = BIC;
	}

	public String getKorAccount()
	{
		return correspondentAccount;
	}

	public void setKorAccount(String korAccount)
	{
		this.correspondentAccount = korAccount;
	}

	public String getINN()
	{
		return INN;
	}

	public void setINN(String INN)
	{
		this.INN = INN;
	}

	public String getKPP()
	{
		return KPP;
	}

	public void setKPP(String KPP)
	{
		this.KPP = KPP;
	}

	public String getReceiverName()
	{
		return receiverName;
	}

	public void setReceiverName(String receiverName)
	{
		this.receiverName = receiverName;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getExtendedId()
	{
		return extendedId;
	}

	public void setExtendedId(String extendedId)
	{
		this.extendedId = extendedId;
	}

	public Calendar getDate()
	{
		return date;
	}

	public void setDate(Calendar date)
	{
		this.date = date;
	}

	public String getSystemName()
	{
		return systemName;
	}

	public void setSystemName(String systemName)
	{
		this.systemName = systemName;
	}

	public Person getPerson()
	{
		return person;
	}

	public void setPerson(Person person)
	{
		this.person = person;
	}

	public String getUuid()
	{
		return uuid;
	}

	public void setUuid(String uuid)
	{
		this.uuid = uuid;
	}

	public Date getNotificationTime()
	{
		return notificationTime;
	}

	public void setNotificationTime(Date notificationTime)
	{
		this.notificationTime = notificationTime;
	}

	public String getAdditionalFields()
	{
		return additionalFields;
	}

	public void setAdditionalFields(String additionalFields)
	{
		this.additionalFields = additionalFields;
	}

	public String getPrintDesc()
	{
		return printDesc;
	}

	public void setPrintDesc(String printDesc)
	{
		this.printDesc = printDesc;
	}
}
