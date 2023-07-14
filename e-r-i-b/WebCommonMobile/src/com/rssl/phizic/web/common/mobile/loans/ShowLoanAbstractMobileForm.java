package com.rssl.phizic.web.common.mobile.loans;

import com.rssl.phizic.gate.loans.ScheduleAbstract;
import com.rssl.phizic.web.common.mobile.common.FilterFormBase;

/**
 * ������ �������� �� �������
 * @author mihaylov
 * @ created 24.07.2010
 * @ $Author$
 * @ $Revision$
 */

public class ShowLoanAbstractMobileForm extends FilterFormBase
{
	private ScheduleAbstract scheduleAbstract;
	private boolean isError = false;

	public boolean isError()
	{
		return isError;
	}

	public void setError(boolean error)
	{
		isError = error;
	}

	public ScheduleAbstract getScheduleAbstract()
	{
		return scheduleAbstract;
	}

	public void setScheduleAbstract(ScheduleAbstract scheduleAbstract)
	{
		this.scheduleAbstract = scheduleAbstract;
	}

}