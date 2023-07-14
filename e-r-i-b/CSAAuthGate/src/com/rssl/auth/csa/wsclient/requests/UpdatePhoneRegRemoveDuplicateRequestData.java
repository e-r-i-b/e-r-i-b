package com.rssl.auth.csa.wsclient.requests;

import com.rssl.phizic.gate.csa.UserInfo;

import java.util.List;

/**
 * @author osminin
 * @ created 30.06.14
 * @ $Author$
 * @ $Revision$
 *
 * ������ �� ���������� ����������� ���������, ��������� ��������� ��������� � ��������, � ������� ���������� ���������.
 */
public class UpdatePhoneRegRemoveDuplicateRequestData extends UpdatePhoneRegistrationsRequestData
{
	private static final String REQUEST_DATA_NAME = "updatePhoneRegRemoveDuplicateRq";

	/**
	 * ctor
	 * @param phoneNumber ����� ��������
	 * @param userInfo ���������� � ������������
	 * @param addPhones ����������� ��������
	 * @param removePhones ��������� ��������
	 */
	public UpdatePhoneRegRemoveDuplicateRequestData(String phoneNumber, UserInfo userInfo, List<String> addPhones, List<String> removePhones)
	{
		super(phoneNumber, userInfo, addPhones, removePhones);
	}

	@Override
	public String getName()
	{
		return REQUEST_DATA_NAME;
	}
}