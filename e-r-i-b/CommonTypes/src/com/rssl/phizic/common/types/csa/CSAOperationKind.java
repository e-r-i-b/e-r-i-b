package com.rssl.phizic.common.types.csa;

/**
 * @author vagin
 * @ created 18.10.13
 * @ $Author$
 * @ $Revision$
 * ��� �������� � ���, � ����������� �� �������.
 */
public enum CSAOperationKind
{
	//������������� ��������
	ConfirmOperation("confirmOperationRq"),
	//�������������� ������������
	Authentication("authenticationRq"),
	//����������� ������������
	StartUserRegistration("startUserRegistrationRq"),
	FinishUserRegistration("finishUserRegistrationRq"),
	//��������������� ����������� ������������
	StartUserSelfRegistration("startUserSelfRegistrationRq"),
	FinishUserSelfRegistration("finishUserSelfRegistrationRq"),
	//�������������� ������
	StartRestorePassword("startRestorePasswordRq"),
	FinishRestorePassword("finishRestorePasswordRq"),
	//�������� ������ ����
	StartCreateSession("startCreateSessionRq"),
	FinishCreateSession("finishCreateSessionRq"),
	//��������� ������
	GeneratePassword("generatePasswordRq"),
	GeneratePassword2("generatePassword2Rq"),

	//����� ������
	ChangePassword("changePasswordRq"),
	//����� ������
	ChangeLogin("changeLoginRq"),
	//����������� �������� ��������� ������
	StartCreateMobileSession("startCreateMobileSessionRq"),
	FinishCreateMobileSession("finishCreateMobileSessionRq"),
	//����������� �������� ��������� ������
	StartCreateSocialSession("startCreateSocialSessionRq"),
	FinishCreateSocialSession("finishCreateSocialSessionRq"),
	//���������� � ������� ������
	//�������� ������� ������������� �����������
	InitializeVerifyBusinessEnvironment("initializeVerifyBusinessEnvironmentRq"),
	//������ �������� �����������
	VerifyBusinessEnvironment("verifyBusinessEnvironmentRq"),
	//���������� ������� ������������
	LockProfile("lockProfileRq"),
	//������������ ������� ������������
	UnlockProfile("unlockProfileRq"),
	//����������� �������� ������ ���
	StartCreateATMSession("startCreateATMSessionRq"),
	FinishCreateATMSession("finishCreateATMSessionRq"),
	//�������������� ����
	FindProfileNodeByPhone("findProfileNodeByPhoneRq"),
	//������ ����������� ��
	CancelMobileRegistration("cancelMobileRegistrationRq"),
	//����������� ��
	StartMobileRegistration("startMobileRegistrationRq"),
	FinishMobileRegistration("finishMobileRegistrationRq"),
	//������ ����������� ��
	CancelSocialRegistration("cancelSocialRegistrationRq"),
	//����������� ��
	StartSocialRegistration("startSocialRegistrationRq"),
	FinishSocialRegistration("finishSocialRegistrationRq"),
	//���������� ���������� � ���������� ������������ � ����-�
	LockProfileInfo("lockProfileCHG071536Rq"),
	//���������� ���������� � ���������� ������������ � ����-�
	LockProfileInfoByProfileId("lockProfileCHG071536ByProfileIdRq"),
	//�������� IMSI
	checkIMSI("checkIMSIRq"),
	//������������� ��������� �����
	confirmGuestEntry("confirmGuestOperationRq"),
	//���������� ��������� �����;
	finishGuestEntry("finishCreateGuestSessionRq");

	private String requestType;

	CSAOperationKind(String requestType)
	{
		this.requestType = requestType;
	}

	public String getRequestType()
	{
		return requestType;
	}

	public static CSAOperationKind fromValue(String requestType)
	{
		for(CSAOperationKind kind : CSAOperationKind.values())
			if(kind.requestType.equals(requestType))
				return kind;
		throw new IllegalArgumentException("����������� ��� �������� [" + requestType + "]");
	}
}
