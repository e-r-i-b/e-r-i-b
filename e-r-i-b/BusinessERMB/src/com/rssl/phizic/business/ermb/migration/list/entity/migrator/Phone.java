package com.rssl.phizic.business.ermb.migration.list.entity.migrator;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

/**
 * ������ ������-������� � ���. ����������� � ��������� ��������� ��������
 * @author Puzikov
 * @ created 31.01.14
 * @ $Author$
 * @ $Revision$
 */

public class Phone
{
	private Long id;
	private String phoneNumber;                             //����� ��������
	private PhoneSource source;                             //��� ��� ���

	private boolean manually = false;                       //����� �� ��������� �������� �� ����� �������� �������
	private boolean unresolvable = false;                   //�������� �� �������� �� ������� �������� ������������ (3_2_2)
	private Set<ConflictedClient> conflictedClients = new HashSet<ConflictedClient>();  //������������� �������

	//����� ��� �����������
	private boolean unique;                                 //�������� �� ������� ���������� (���������� ����������)
	private boolean vipOrMbc;                               //�������� �� ���� �� ���������� ��� ��� ���, ������� ��� ����������
	private SmsActivity smsActivity;                        //@see SmsActivity

	//����� ��� �����������, ���������� � �� (������������ ��� ��������)
	private int smsCount;                                   //����� ��� �� ������� �������
	private Calendar registrationDate;                      //���� ����������� ��������

	//����� � ��������� ���. ���� (��������� ��� ���)
	private boolean hasAdditional;                          //���� �� � ������ � ��� �� ����� �������� ���. �����
	private boolean additionalCardOwner;                    //� ������ ���� �������������� �������������� �����, ���������� ������� �������� ������, �������� �� ��������
	private boolean onlyAdditional;                         //���. ����� �������� ������������ �������������� ������
	private boolean hasMain;                                // � ������������� ����� ������ ���, ���� �������� ����� �������, ��� ��� �����, ����������� �� ����
	private boolean sultan;
	private boolean belongClientRegistration;                        // ����������� ����������� - �����������, ���������� �������� ����� ������� �������� ������.
	private boolean cardActivity;
	private boolean lastSmsActivity;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public PhoneSource getSource()
	{
		return source;
	}

	public void setSource(PhoneSource source)
	{
		this.source = source;
	}

	public SmsActivity getSmsActivity()
	{
		return smsActivity;
	}

	public void setSmsActivity(SmsActivity smsActivity)
	{
		this.smsActivity = smsActivity;
	}

	public int getSmsCount()
	{
		return smsCount;
	}

	public void setSmsCount(int smsCount)
	{
		this.smsCount = smsCount;
	}

	public Calendar getRegistrationDate()
	{
		return registrationDate;
	}

	public void setRegistrationDate(Calendar registrationDate)
	{
		this.registrationDate = registrationDate;
	}

	public boolean getUnique()
	{
		return unique;
	}

	public void setUnique(boolean unique)
	{
		this.unique = unique;
	}

	public boolean getVipOrMbc()
	{
		return vipOrMbc;
	}

	public void setVipOrMbc(boolean vipOrMbc)
	{
		this.vipOrMbc = vipOrMbc;
	}

	public boolean isManually()
	{
		return manually;
	}

	public void setManually(boolean manually)
	{
		this.manually = manually;
	}

	public boolean isUnresolvable()
	{
		return unresolvable;
	}

	public void setUnresolvable(boolean unresolvable)
	{
		this.unresolvable = unresolvable;
	}

	public Set<ConflictedClient> getConflictedClients()
	{
		return conflictedClients;
	}

	public void setConflictedClients(Set<ConflictedClient> conflictedClients)
	{
		this.conflictedClients = conflictedClients;
	}

	public boolean isHasAdditional()
	{
		return hasAdditional;
	}

	public void setHasAdditional(boolean hasAdditional)
	{
		this.hasAdditional = hasAdditional;
	}

	public boolean isAdditionalCardOwner()
	{
		return additionalCardOwner;
	}

	public void setAdditionalCardOwner(boolean additionalCardOwner)
	{
		this.additionalCardOwner = additionalCardOwner;
	}

	public boolean isOnlyAdditional()
	{
		return onlyAdditional;
	}

	public void setOnlyAdditional(boolean onlyAdditional)
	{
		this.onlyAdditional = onlyAdditional;
	}

	public boolean isHasMain()
	{
		return hasMain;
	}

	public void setHasMain(boolean hasMain)
	{
		this.hasMain = hasMain;
	}

	public void setSultan(boolean sultan)
	{
		this.sultan = sultan;
	}

	public boolean isSultan()
	{
		return sultan;
	}

	public void setBelongClientRegistration(boolean ownRegistration)
	{
		this.belongClientRegistration = ownRegistration;
	}

	public boolean isBelongClientRegistration()
	{
		return belongClientRegistration;
	}

	public void setCardActivity(boolean cardActivity)
	{
		this.cardActivity = cardActivity;
	}

	public boolean isCardActivity()
	{
		return cardActivity;
	}

	public void setLastSmsActivity(boolean lastSmsActivity)
	{
		this.lastSmsActivity = lastSmsActivity;
	}

	public boolean isLastSmsActivity()
	{
		return lastSmsActivity;
	}
}
