package com.rssl.phizic.business.ext.sbrf.csa.blockingrules;

import java.util.Calendar;

/**
 * @author vagin
 * @ created 21.08.2012
 * @ $Author$
 * @ $Revision$
 * ����� ��������. ������� ����������.
 */
public class BlockingRules
{
	private Long id;
	private String name;        // �������� �������
	private String departments; //������������� ����� ������� ���� ������������� ��� �����
	private BlockingState state;      //��������� �������������(true)/�� �������������(false)
	private Calendar resumingTime; // ����� �������������� �������

	//��������� ���������� �����������
	private Calendar fromPublishDate;
	private Calendar toPublishDate;
	private Calendar fromRestrictionDate;
	private Calendar toRestrictionDate;

	private boolean applyToERIB;    //���������������� �� ����� ����
	private boolean applyToMAPI;    //���������������� �� ����� ����
	private boolean applyToATM;     //���������������� �� ����� ���
	private boolean applyToERMB;    //���������������� �� ����� ����

	private String ERIBMessage;     //��������� ��� ������ ����
	private String mapiMessage;     //��������� ��� ������ ����
	private String ATMMessage;      //��������� ��� ������ ���
	private String ERMBMessage;     //��������� ��� ������ ����

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDepartments()
	{
		return departments;
	}

	public void setDepartments(String departments)
	{
		this.departments = departments;
	}

	public BlockingState getState()
	{
		return state;
	}

	public void setState(BlockingState state)
	{
		this.state = state;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Calendar getResumingTime()
	{
		return resumingTime;
	}

	public void setResumingTime(Calendar resumingTime)
	{
		this.resumingTime = resumingTime;
	}

	/**
	 *
	 * @return ������ ���������� ����������� � ���������� c
	 */
	public Calendar getFromPublishDate()
	{
		return fromPublishDate;
	}

	/**
	 *
	 * @param fromPublishDate ������ ���������� ����������� � ���������� c
	 */
	public void setFromPublishDate(Calendar fromPublishDate)
	{
		this.fromPublishDate = fromPublishDate;
	}

	/**
	 *
	 * @return ������ ���������� ����������� � ���������� ��
	 */
	public Calendar getToPublishDate()
	{
		return toPublishDate;
	}

	/**
	 *
	 * @param toPublishDate ������ ���������� ����������� � ���������� ��
	 */
	public void setToPublishDate(Calendar toPublishDate)
	{
		this.toPublishDate = toPublishDate;
	}

	/**
	 *
	 * @return ������, ������������ � ����������� � ���������� �
	 */
	public Calendar getFromRestrictionDate()
	{
		return fromRestrictionDate;
	}

	/**
	 *
	 * @param fromRestrictionDate ������, ������������ � ����������� � ���������� �
	 */
	public void setFromRestrictionDate(Calendar fromRestrictionDate)
	{
		this.fromRestrictionDate = fromRestrictionDate;
	}

	/**
	 *
	 * @return ������, ������������ � ����������� � ���������� ��
	 */
	public Calendar getToRestrictionDate()
	{
		return toRestrictionDate;
	}

	/**
	 * @param toRestrictionDate ������, ������������ � ����������� � ���������� ��
	 */
	public void setToRestrictionDate(Calendar toRestrictionDate)
	{
		this.toRestrictionDate = toRestrictionDate;
	}

	public boolean isApplyToERIB()
	{
		return applyToERIB;
	}

	public void setApplyToERIB(boolean applyToERIB)
	{
		this.applyToERIB = applyToERIB;
	}

	public boolean isApplyToERMB()
	{
		return applyToERMB;
	}

	public void setApplyToERMB(boolean applyToERMB)
	{
		this.applyToERMB = applyToERMB;
	}

	public boolean isApplyToMAPI()
	{
		return applyToMAPI;
	}

	public void setApplyToMAPI(boolean applyToMAPI)
	{
		this.applyToMAPI = applyToMAPI;
	}

	public boolean isApplyToATM()
	{
		return applyToATM;
	}

	public void setApplyToATM(boolean applyToATM)
	{
		this.applyToATM = applyToATM;
	}

	public String getERIBMessage()
	{
		return ERIBMessage;
	}

	public void setERIBMessage(String ERIBMessage)
	{
		this.ERIBMessage = ERIBMessage;
	}

	public String getERMBMessage()
	{
		return ERMBMessage;
	}

	public void setERMBMessage(String ERMBMessage)
	{
		this.ERMBMessage = ERMBMessage;
	}

	public String getMapiMessage()
	{
		return mapiMessage;
	}

	public void setMapiMessage(String mapiMessage)
	{
		this.mapiMessage = mapiMessage;
	}

	public String getATMMessage()
	{
		return ATMMessage;
	}

	public void setATMMessage(String ATMMessage)
	{
		this.ATMMessage = ATMMessage;
	}
}