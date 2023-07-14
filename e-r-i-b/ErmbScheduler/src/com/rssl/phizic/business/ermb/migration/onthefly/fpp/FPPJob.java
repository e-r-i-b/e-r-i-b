package com.rssl.phizic.business.ermb.migration.onthefly.fpp;

import org.quartz.*;

import java.io.InvalidClassException;

/**
 * @author Erkin
 * @ created 27.08.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * ���� ��� �������� ��-���� ����� ������� ������ (���) ���������� ����� �� ������ (���).
 * ������������� ��� ��� � ������������� �������� � ������ "��������� ����" � ���
 * � ���������� �������� � ����.
 * ������� ������������ �� ���, � ������ �� �������� ���� (��).
 */
public class FPPJob implements Job, InterruptableJob
{
	/**
	 * ������ "���������� ����� ���� ��������"
	 */
	private transient volatile boolean interrupted = false;

	///////////////////////////////////////////////////////////////////////////

	public void execute(JobExecutionContext context) throws JobExecutionException
	{

	}

	public void interrupt() throws UnableToInterruptJobException
	{
		interrupted = true;
	}
}
