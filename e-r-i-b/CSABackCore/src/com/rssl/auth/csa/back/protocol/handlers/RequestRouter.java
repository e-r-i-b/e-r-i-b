package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.RequestProcessor;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.protocol.handlers.clients.GetClientNodeStateRequestProcessor;
import com.rssl.auth.csa.back.protocol.handlers.clients.GetClientProfileIdProcessor;
import com.rssl.auth.csa.back.protocol.handlers.clients.GetTemporaryNodeClientsCountRequestProcessor;
import com.rssl.auth.csa.back.protocol.handlers.clients.UpdateClientMDMInfoProcessor;
import com.rssl.auth.csa.back.protocol.handlers.guest.*;
import com.rssl.auth.csa.back.protocol.handlers.nodes.ChangeNodesAvailabilityInfoRequestProcessor;
import com.rssl.auth.csa.back.protocol.handlers.profile.CreateProfileRequestProcessor;
import com.rssl.auth.csa.back.protocol.handlers.profile.FindProfileCardNumberListRequestProcessor;
import com.rssl.auth.csa.back.protocol.handlers.profile.FindProfileInformationRequestProcessor;
import com.rssl.auth.csa.back.protocol.handlers.profile.FindProfileInformationWithNodeInfoRequestProcessor;
import com.rssl.auth.csa.back.protocol.handlers.profile.lock.LockProfileCHG071536RequestProcessor;
import com.rssl.auth.csa.back.protocol.handlers.profile.lock.LockProfileForExecuteDocumentRequestProcessor;
import com.rssl.auth.csa.back.protocol.handlers.profile.lock.UnlockProfileForExecuteDocumentRequestProcessor;
import com.rssl.auth.csa.back.protocol.handlers.verification.InitializeVerifyBusinessEnvironmentRequestProcessor;
import com.rssl.auth.csa.back.protocol.handlers.verification.VerifyBusinessEnvironmentRequestProcessor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author krenev
 * @ created 22.08.2012
 * @ $Author$
 * @ $Revision$
 * "�������������" ��������. ����� ������������ ���� ������� � �����������. 
 */

public class RequestRouter implements RequestProcessor
{
	private static final RequestRouter INSTANCE = new RequestRouter();
	private final Map<String, RequestProcessor> processors = new HashMap<String, RequestProcessor>();

	private RequestRouter()
	{
		registerProcessor(new ConfirmOperationRequestProcessor());
		registerProcessor(new ConfirmGuestOperationRequestProcessor());
		// �������������� ������������
		registerProcessor(new AuthenticationRequestProcessor());

		//����������� ������������
		registerProcessor(new StartUserRegistrationRequestProcessor());
		registerProcessor(new FinishUserRegistrationRequestProcessor());

		//��������������� ����������� ������������
		registerProcessor(new StartUserSelfRegistrationRequestProcessor());
		registerProcessor(new FinishUserSelfRegistrationRequestProcessor());

		//�������������� ������
		registerProcessor(new RouteRestorePasswordRequestProcessor());
		registerProcessor(new FinishClientRestorePasswordRequestProcessor());
		registerProcessor(new FinishGuestRestorePasswordRequestProcessor());

		//�������� ������ ����
		registerProcessor(new RouterCreateSessionRequestProcessor());
		registerProcessor(new FinishCreateSessionRequestProcessor());
		registerProcessor(new ActualizationLogonInfoRequestProcessor());

		//���� ������
		registerProcessor(new CheckSessionRequestProcessor());

		//�������� ������ ������� ������
		registerProcessor(new CheckPasswordRequestProcessor());

		//�������� ������
		registerProcessor(new CloseSessionRequestProcessor());

		//��������� ������
		registerProcessor(new GeneratePasswordRequestProcessor());
		registerProcessor(new GeneratePassword2RequestProcessor());

		//��������� ������
		registerProcessor(new ValidatePasswordRequestProcessor());
		//����� ������
		registerProcessor(new ChangePasswordRequestProcessor());

		//��������� ������
		registerProcessor(new ValidateLoginRequestProcessor());
		//����� ������
		registerProcessor(new ChangeLoginRequestProcessor());

		//����������� ���������� ����������
		registerProcessor(new StartMobileRegistrationRequestProcessor());
		registerProcessor(new FinishMobileRegistrationRequestProcessor());

		//����������� �������� ��������� ������
		registerProcessor(new StartCreateMobileSessionRequestProcessor());
		registerProcessor(new FinishCreateMobileSessionRequestProcessor());

		//������ ����������� ���������� ����������
		registerProcessor(new CancelMobileRegistrationRequestProcessor());

		//����������� ���������� ����������
		registerProcessor(new StartSocialRegistrationRequestProcessor());
		registerProcessor(new FinishSocialRegistrationRequestProcessor());

		//����������� �������� ��������� ������
		registerProcessor(new StartCreateSocialSessionRequestProcessor());
		registerProcessor(new FinishCreateSocialSessionRequestProcessor());

		//������ ����������� ���������� ����������
		registerProcessor(new CancelSocialRegistrationRequestProcessor());

		//���������� � ������� ������
		//�������� ������� ������������� �����������
		registerProcessor(new InitializeVerifyBusinessEnvironmentRequestProcessor());
		//������ �������� �����������
		registerProcessor(new VerifyBusinessEnvironmentRequestProcessor());
		//��������� ���������� � �������� ������������� ��������
		registerProcessor(new GetConfirmationInfoRequestProcessor());

		//���������� �������� ����������� push-�����������
		registerProcessor(new ChangePushSupportedRequestProcessor());

		//���������� ������� ������������
		registerProcessor(new LockProfileRequestProcessor());
		//������������ ������� ������������
		registerProcessor(new UnlockProfileRequestProcessor());

		//����������� �������� ������ ���
		registerProcessor(new StartCreateATMSessionRequestProcessor());
		registerProcessor(new FinishCreateATMSessionRequestProcessor());

		//����������� ��������
		registerProcessor(new UpdatePhoneRegistrationsRequestProcessor());
		registerProcessor(new UpdatePhoneRegRemoveDuplicateRequestProcessor());
		//����� ����� ������������ �� ��������
		registerProcessor(new FindProfileNodeByPhoneRequestProcessor());
		//����� ����� ������������ �� ��� ��� �� ��
		registerProcessor(new FindProfileNodeByUserInfoRequestProcessor());
		//���������� �������
		registerProcessor(new UpdateProfileRequestProcessor());
		//��������� ���������� � ������
		registerProcessor(new GetNodesInfoRequestProcessor());

		//�������� ���������� � ������������ �� �������� � �������� ���, ���� � ��� ������ �� �������
		registerProcessor(new GetUserInfoByPhoneWithMBRequestProcessor());
		//�������� ���������� � ������������ �� ��������
		registerProcessor(new GetUserInfoByPhoneRequestProcessor());
		//�������� ���������� � ������������ �� �� � �������
		registerProcessor(new GetUserInfoByDeviceIdAndInfoRequestProcessor());

		//��������� ��������� ����������� �������
		registerProcessor(new GetIncognitoRequestProcessor());
		//���������� ��������� ����������� �������
		registerProcessor(new ChangeIncognitoRequestProcessor());
		//��������� ������ ��������
		registerProcessor(new GetClientsInformationRequestProcessor());
		//��������� ������ ��������, ��������� ��������
		registerProcessor(new GetTemporaryNodeClientsInformationRequestProcessor());
		//��������� ���������� ��������, ��������� ��������
		registerProcessor(new GetTemporaryNodeClientsCountRequestProcessor());
		//��������� ��������� �������
		registerProcessor(new GetClientNodeStateRequestProcessor());
		//��������� ������� ��������� �������
		registerProcessor(new GetProfileHistoryInfoRequestProcessor());
		//���������� ��������������� ������� ������� �������
		registerProcessor(new UpdateProfileAdditionalDataRequestProcessor());
		//��������� ���� �� ������� ��� ���������� ��������� �����������
		registerProcessor(new LockProfileForExecuteDocumentRequestProcessor());
		//������ ���� � ������� ����� ���������� ��������� �����������
		registerProcessor(new UnlockProfileForExecuteDocumentRequestProcessor());

		//��������� ���������� � ������ �������� ��������
		registerProcessor(new FindNodesByUpdatedProfilesRequestProcessor());
		//����� ���������� � ������������
		registerProcessor(new FindProfileInformationRequestProcessor());
		//����� ������� ������� ��� � ������ ����������� �� ����� � ������� ������� ������
		registerProcessor(new FindProfileInformationWithNodeInfoRequestProcessor());
		//�������� �������
		registerProcessor(new CreateProfileRequestProcessor());
		//��������� ������������ ������ � ������ ����� ��
		registerProcessor(new UserRegistrationDisposableProcessor());

		//���������� ������� ������� ������ ������������
		registerProcessor(new LowerProfileSecurityTypeRequestProcessor());

		//��������� ���������� � ������� ����������� ����
		registerProcessor(new GetErmbConnectorInfoProcessor());

		//��������� ���������� � ���������� ����������� �������
		registerProcessor(new GetClientConnectorsProcessor());
		//���������� ������������ � ������ ������� CHG071536
		registerProcessor(new LockProfileCHG071536RequestProcessor());
		//���������� ������������ � ������ ������� CHG071536
		registerProcessor(new LockProfileCHG071536ByProfileIdRequestProcessor());
		//��������� ������ ���� ��� �������
		registerProcessor(new FindProfileCardNumberListRequestProcessor());
		//��������� ������ ��������� �������
		registerProcessor(new GetErmbPhonesProcessor());

		//���������� � ��������� ������
		registerProcessor(new ChangeNodesAvailabilityInfoRequestProcessor());

		//������ ������� ����
		registerProcessor(new HasUserByPhoneRequestProcessor());

		//�����������/������ ���������������� �������� ���
		registerProcessor(new MbkTechnoBreakSaveOrUpdateProcessor());

		//��������� ���������� � ���������� ������� �� ���� �������
		registerProcessor(new FindFundSenderInfoRequestProcessor());

		//��������� ���������� � ������� ���� ���-������
		registerProcessor(new GetContainsProMAPIInfoRequestProcessor());

		//�������������� ��������� �����
		registerProcessor(new StartCreateGuestSessionRequestProcessor());
		registerProcessor(new AuthGuestSessionRequestProcessor());
		registerProcessor(new FinishCreateGuestSessionRequestProcessor());

		//��������� ��� ���������� �� �����
		registerProcessor(new AdditionInformationForGuestRequestProcessor());

		// ����������� �����
		registerProcessor(new GuestRegistrationRequestProcessor());

		//�������� IMSI
		registerProcessor(new CheckIMSIRequestProcessor());
		//���������� ������� ������� �� ���
		registerProcessor(new UpdateClientMDMInfoProcessor());
		registerProcessor(new GetClientProfileIdProcessor());
	}

	private void registerProcessor(RequestProcessorBase processor)
	{
		processors.put(processor.getRequestType(), processor);
	}

	/**
	 * @return �������.
	 */
	public static RequestRouter getInstance()
	{
		return INSTANCE;
	}

	/**
	 * ����� �������������� ������� ���������� � ������������ �����
	 * @param requestInfo ���������� � �������
	 * @return ���������� �� ������
	 */
	public ResponseInfo process(RequestInfo requestInfo) throws Exception
	{
		RequestProcessor requestHandler = processors.get(requestInfo.getType());
		if (requestHandler == null)
		{
			requestHandler = UnsupportedMessageTypeProcessor.INSTANCE;
		}
		return requestHandler.process(requestInfo);
	}

	public boolean isAccessStandIn()
	{
		return true;
	}
}
