package com.rssl.phizic.business.ermb.migration.mbk.registrator.sender;

import com.rssl.phizic.gate.mobilebank.MBKRegistrationResultCode;
import com.rssl.phizic.utils.StringHelper;

/**
 * ��������� ��������� ���������, ������� ���������� ������� � ���
 * @author Puzikov
 * @ created 09.07.14
 * @ $Author$
 * @ $Revision$
 */

public class ProcessResult
{
	//�������� ID �����������
	private final long registrationId;

	//��� ���������� ���������
	private final MBKRegistrationResultCode resultCode;

	//�������� �������� ������, ���� ��������� ��������� � ������
	private final String errorDescription;

	/**
	 * ctor
	 * @param registrationId id �����������
	 * @param resultCode ��� ����������
	 * @param errorDescription �������� ������
	 */
	public ProcessResult(long registrationId, MBKRegistrationResultCode resultCode, String errorDescription)
	{
		if (resultCode == null)
		    throw new IllegalArgumentException("�� ������ resultCode");

		if (resultCode != MBKRegistrationResultCode.SUCCESS && StringHelper.isEmpty(errorDescription))
		    throw new IllegalArgumentException("�� ������ errorDescription ��� ���� " + resultCode);

		this.registrationId = registrationId;
		this.resultCode = resultCode;
		this.errorDescription = errorDescription;
	}

	public long getRegistrationId()
	{
		return registrationId;
	}

	public MBKRegistrationResultCode getResultCode()
	{
		return resultCode;
	}

	public String getErrorDescription()
	{
		return errorDescription;
	}
}
