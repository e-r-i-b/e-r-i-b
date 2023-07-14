package com.rssl.phizic.business.receptiontimes;

import com.rssl.phizic.business.calendar.WorkCalendar;
import com.rssl.phizic.dictionaries.synchronization.MultiBlockDictionaryRecord;

import java.sql.Time;

/**
 * @author: Pakhomova
 * @created: 18.08.2009
 * @ $Author$
 * @ $Revision$
 * ����� ��������� ����� ������ ���������� ��� ������� �������������
 */
public class ReceptionTime implements MultiBlockDictionaryRecord
{
	private Long id;
	private Long departmentId;
	private WorkCalendar calendar;      //������� ���������
	private Time receptionTimeStart;    //����� ������ ������ ����������
	private Time receptionTimeEnd;      //����� ��������� ������ ����������
	private boolean useParentSettings; //������������ �� ��������� ������������� �������������
	private String paymentType;           //��� �������
	private String paymentTypeDescription;           //�������� ���� �������

	public Long getId()
	{
		return id;
	}

	public Long getDepartmentId()
	{
		return departmentId;
	}

	public WorkCalendar getCalendar()
	{
		return calendar;
	}

	public Time getReceptionTimeStart()
	{
		return receptionTimeStart;
	}

	public Time getReceptionTimeEnd()
	{
		return receptionTimeEnd;
	}

	public boolean isUseParentSettings()
	{
		return useParentSettings;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public void setDepartmentId(Long departmentId)
	{
		this.departmentId = departmentId;
	}

	public void setCalendar(WorkCalendar calendar)
	{
		this.calendar = calendar;
	}

	public void setReceptionTimeStart(Time receptionTimeStart)
	{
		this.receptionTimeStart = receptionTimeStart;
	}

	public void setReceptionTimeEnd(Time receptionTimeEnd)
	{
		this.receptionTimeEnd = receptionTimeEnd;
	}

	public void setUseParentSettings(boolean useParentSettings)
	{
		this.useParentSettings = useParentSettings;
	}

	public String getPaymentType()
	{
		return paymentType;
	}

	public void setPaymentType(String paymentType)
	{
		this.paymentType = paymentType;
	}

	public String getPaymentTypeDescription()
	{
		return paymentTypeDescription;
	}

	public void setPaymentTypeDescription(String paymentTypeDescription)
	{
		this.paymentTypeDescription = paymentTypeDescription;
	}

	public String getMultiBlockRecordId()
	{
		return departmentId + "^" + paymentType;
	}
}
