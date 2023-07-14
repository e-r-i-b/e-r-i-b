package com.rssl.phizic.business.receptiontimes;

/**
 * @author Erkin
 * @ created 21.04.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * ���������� � ������� �������
 */
public class WorkTimeInfo
{
	private final ReceptionTime receptionTime;

	private final TimeMatching workTimeNow;

	///////////////////////////////////////////////////////////////////////////

	WorkTimeInfo(ReceptionTime receptionTime, TimeMatching workTimeNow)
	{
		this.receptionTime = receptionTime;
		this.workTimeNow = workTimeNow;
	}

	/**
	 * @return ����� ����� (����������)
	 */
	public ReceptionTime getReceptionTime()
	{
		return receptionTime;
	}

	/**
	 * �������� �� ������: ��������� ������� ����� ��� ���
	 * ����������� ��� ����������� ��� "������� ������"
	 * @return
	 */
	public TimeMatching isWorkTimeNow()
	{
		return workTimeNow;
	}
}
