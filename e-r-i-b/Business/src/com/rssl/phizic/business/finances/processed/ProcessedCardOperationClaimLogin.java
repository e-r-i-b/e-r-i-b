package com.rssl.phizic.business.finances.processed;

import java.util.Calendar;

/**
 * @author mihaylov
 * @ created 10.07.14
 * @ $Author$
 * @ $Revision$
 *
 * ������ ���������� ������ �� ��������� ��������� �������� �� ���, �������������� � ������� ������.
 * �������� ���������� ��� ����������� ������������� ��������� ������ ������ ������� � ������ �������(��� �� ������ ��).
 */
public class ProcessedCardOperationClaimLogin
{
	private Long loginId;
	private Calendar processingDate;

	/**
	 * ������ �����������, ��� ���� Hibernate
	 */
	public ProcessedCardOperationClaimLogin()
	{
	}

	/**
	 * �����������
	 * @param loginId - ������������� ������
	 */
	public ProcessedCardOperationClaimLogin(Long loginId)
	{
		this.loginId = loginId;
		this.processingDate = Calendar.getInstance();
	}

	/**
	 * @return ������������� ������
	 */
	public Long getLoginId()
	{
		return loginId;
	}

	/**
	 * ���������� ������������� ������
	 * @param loginId ������������� ������
	 */
	public void setLoginId(Long loginId)
	{
		this.loginId = loginId;
	}

	/**
	 * @return ���� ������ ��������� ������ �������
	 */
	public Calendar getProcessingDate()
	{
		return processingDate;
	}

	/**
	 * ���������� ���� ������ ��������� ������ �������
	 * @param processingDate ���� ������ ��������� ������ �������
	 */
	public void setProcessingDate(Calendar processingDate)
	{
		this.processingDate = processingDate;
	}
}
