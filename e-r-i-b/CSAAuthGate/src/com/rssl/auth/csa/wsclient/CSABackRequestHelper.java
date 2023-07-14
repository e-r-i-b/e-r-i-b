package com.rssl.auth.csa.wsclient;

import com.rssl.auth.csa.back.servises.nodes.CreateProfileNodeMode;
import com.rssl.auth.csa.utils.UpdateProfileInfo;
import com.rssl.auth.csa.wsclient.exceptions.BackException;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.auth.csa.wsclient.requests.*;
import com.rssl.auth.csa.wsclient.requests.clients.GetClientNodeStateRequestData;
import com.rssl.auth.csa.wsclient.requests.clients.GetClientProfileIdRequestData;
import com.rssl.auth.csa.wsclient.requests.clients.GetTemporaryNodeClientsCountRequestData;
import com.rssl.auth.csa.wsclient.requests.clients.UpdateClientMDMInfoRequestData;
import com.rssl.auth.csa.wsclient.requests.guest.FinishCreateGuestSessionRequestData;
import com.rssl.auth.csa.wsclient.requests.guest.GuestEntryConfirmationRequestData;
import com.rssl.auth.csa.wsclient.requests.guest.GuestEntryInitialRequestData;
import com.rssl.auth.csa.wsclient.requests.guest.GuestRegistrationRequestData;
import com.rssl.auth.csa.wsclient.requests.info.ValidateLoginInfo;
import com.rssl.auth.csa.wsclient.requests.nodes.ChangeNodesAvailabilityInfoRequestData;
import com.rssl.auth.csa.wsclient.requests.profile.CreateProfileRequestData;
import com.rssl.auth.csa.wsclient.requests.profile.FindProfileCardNumberListRequestData;
import com.rssl.auth.csa.wsclient.requests.profile.FindProfileInformationRequestData;
import com.rssl.auth.csa.wsclient.requests.profile.FindProfileInformationWithNodeInfoRequestData;
import com.rssl.auth.csa.wsclient.requests.profile.incognito.ChangeIncognitoSettingData;
import com.rssl.auth.csa.wsclient.requests.profile.incognito.GetClientConnectorsData;
import com.rssl.auth.csa.wsclient.requests.profile.incognito.GetIncognitoSettingData;
import com.rssl.auth.csa.wsclient.requests.profile.lock.LockProfileCHG071536ByProfileIdRequestData;
import com.rssl.auth.csa.wsclient.requests.profile.lock.LockProfileCHG071536RequestData;
import com.rssl.auth.csa.wsclient.requests.profile.lock.LockProfileForExecuteDocumentRequestData;
import com.rssl.auth.csa.wsclient.requests.profile.lock.UnlockProfileForExecuteDocumentRequestData;
import com.rssl.auth.csa.wsclient.requests.verification.InitializeVerifyBusinessEnvironmentRequestData;
import com.rssl.auth.csa.wsclient.requests.verification.VerifyBusinessEnvironmentRequestData;
import com.rssl.auth.csa.wsclient.responses.ConnectorInfo;
import com.rssl.auth.csa.wsclient.responses.ResponseData;
import com.rssl.phizic.gate.csa.*;
import com.rssl.phizgate.ext.sbrf.technobreaks.TechnoBreak;
import com.rssl.phizic.cache.CacheProvider;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.common.types.client.CreationType;
import com.rssl.phizic.common.types.csa.AuthorizedZoneType;
import com.rssl.phizic.gate.clients.IncognitoState;
import com.rssl.phizic.gate.payments.owner.EmployeeInfo;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;

import java.util.*;

/**
 * @author niculichev
 * @ created 28.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class CSABackRequestHelper
{
	private static final CSABackRequestSender sender = new CSABackRequestSender();

	/**
	 * ������� ������ ��������������
	 * @param login �����
	 * @param password ������
	 * @return �����
	 */
	public static Document sendAuthenticationRq(String login, String password) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new AuthenticationRequestData(login, password));
		return responseData.getBody();
	}

	/**
	 * ������� ������ �� ������ �����������
	 * @param cardNumber ����� �����
	 * @param confirmStrategyType ������ �������������
	 * @return �����
	 */
	public static Document sendStartUserRegistrationRq(String cardNumber, ConfirmStrategyType confirmStrategyType) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new StartUserRegistrationRequestData(cardNumber, confirmStrategyType));
		return responseData.getBody();
	}

	/**
	 * ������� ������ �� ������ ��������������� �����������
	 * @param cardNumber ����� �����
	 * @param confirmStrategyType ������ �������������
	 * @return �����
	 */
	public static Document sendStartUserSelfRegistrationRq(String cardNumber, ConfirmStrategyType confirmStrategyType) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new StartUserSelfRegistrationRequestData(cardNumber, confirmStrategyType));
		return responseData.getBody();
	}

	/**
	 * ������� ������ �� ���������� �����������
	 * @param ouid ������������� ��������
	 * @param login �����
	 * @param password ������
	 * @param notification ���������� �� ����������� �� ���.
	 * @return �����
	 */
	public static Document sendFinishUserRegistrationRq(String ouid, String login, String password, boolean notification) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new FinishUserRegistrationRequestData(ouid, login, password, notification));
		return responseData.getBody();
	}

	/**
	 * ������� ������ �� ���������� ��������������� �����������
	 * @param ouid ������������� ��������
	 * @param login �����
	 * @param password ������
	 * @param notification ���������� �� ����������� �� ���.
	 * @return �����
	 */
	public static Document sendFinishUserSelfRegistrationRq(String ouid, String login, String password, boolean notification) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new FinishUserSelfRegistrationRequestData(ouid, login, password, notification));
		return responseData.getBody();
	}

	/**
	 * ������� ������ �� ������ �������������� ������
	 * @param login �����
	 * @param confirmStrategyType ������ �������������
	 * @return �����
	 */
	public static Document sendStartRecoverPasswordRq(String login, ConfirmStrategyType confirmStrategyType) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new StartRestorePasswordRequestData(login, confirmStrategyType));
		return responseData.getBody();
	}

	/**
	 * ������� ������ �� ���������� �������������� ������
	 * @param ouid ������������� ��������
	 * @param password ����� ������
	 * @return �����
	 */
	public static Document sendFinishRecoverPasswordRq(String ouid, String password) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new FinishRestorePasswordRequestData(ouid, password));
		return responseData.getBody();
	}

	/**
	 * ������� ������ �� ���������� �������������� ��������� ������
	 * @param ouid ������������� ��������
	 * @param password ����� ������
	 * @return �����
	 */
	public static Document sendFinishGuestRecoverPasswordRq(String ouid, String password) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new FinishGuestRestorePasswordRequestData(ouid, password));
		return responseData.getBody();
	}

	/**
	 * ������� ������ �� ������������� �����������
	 * @param ouid ������������� ��������
	 * @param confirmationCode ��� �������������
	 * @return �����
	 */
	public static Document sendConfirmOperationRq(String ouid, String confirmationCode) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new ConfirmOperationRequestData(ouid, confirmationCode));
		return responseData.getBody();
	}

	/**
	 * ������� ������ �� ������������� ��������� ��������
	 * @param ouid ������������� ��������
	 * @param confirmationCode ��� �������������
	 * @return �����
	 */
	public static Document sendGuestConfirmOperationRq(String ouid, String confirmationCode) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new ConfirmGuestOperationRequestData(ouid, confirmationCode));
		return responseData.getBody();
	}

	/**
	 * ������� ������ �� ��������� ���������� � ������ ��� �������������� �������.
	 * @param ouid ������������� ��������
	 * @return �����
	 */
	public static Document sendFinishCreateSessionRq(String ouid) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new FinishCreateSessionRequestData(ouid));
		return responseData.getBody();
	}

	/**
	 * ������� ������ �� �������� ������
	 * @param login �����
	 * @param password ������
	 * @return �����
	 */
	public static Document sendStartCreateSessionRq(String login, String password) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new StartCreateSessionRequestData(login, password));
		return responseData.getBody();
	}

	/**
	 * ������� ������ �� �������� ������ ��� ���������� ����������
	 * @param data - ������ ��� ������������ �������
	 * @return �����
	 * @throws BackException
	 * @throws BackLogicException
	 */
	public static Document sendStartCreateMobileSessionRq(StartCreateMobileSessionRequestData data) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(data);
		return responseData.getBody();
	}

	/**
     * ������� ������ �� �������� ������ ��� ����������� ����������
     * @param guid ������������� ����������
     * @param deviceState ������
     * @param deviceId ������������� ����������
     * @param authorizedZoneType ��� ���� ����� ������������
     * @param pin ������ ��� ������� � ������� ����������� (����� ���� null)
     * @return �����
     * @throws BackException
     * @throws BackLogicException
     */
	public static Document sendStartCreateSocialSessionRq(String guid, String deviceState, String deviceId, AuthorizedZoneType authorizedZoneType, String pin) throws BackException, BackLogicException
    {
        ResponseData responseData = sender.sendRequest(new StartCreateSocialSessionRequestData(guid, deviceState, deviceId, authorizedZoneType, pin));
        return responseData.getBody();
    }

	/**
	 * ������� ������ �� ��������� ���������� � ������ ��� �������������� ����� ���� �������
	 * @param ouid ����� ��������������
	 * @return �����
	 * @throws BackException
	 * @throws BackLogicException
	 */
	public static Document sendFinishCreateMobileSessionRq(String ouid) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new FinishCreateMobileSessionRequestData(ouid));
		return responseData.getBody();
	}

	/**
	 * ������� ������ �� ��������� ���������� � ������ ��� �������������� ����� ���� �������
	 * @param ouid ����� ��������������
	 * @return �����
	 * @throws BackException
	 * @throws BackLogicException
	 */
	public static Document sendFinishCreateSocialSessionRq(String ouid) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new FinishCreateSocialSessionRequestData(ouid));
		return responseData.getBody();
	}

	/**
	 * ������� ������ �� �������� ������.
	 *
	 * @param sid ������������� ������.
	 * @return �����.
	 */
	public static Document sendCheckSessionRq(String sid) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new CheckSessionRequestData(sid));
		return responseData.getBody();
	}

	/**
	 * ������� ������ �� �������� ������.
	 *
	 * @param sid ������������� ������.
	 * @return �����.
	 */
	public static Document sendCloseSessionRq(String sid) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new CloseSessionRequestData(sid));
		return responseData.getBody();
	}

	/**
	 * ������� ������ �� ����� ������.
	 * @param sid ������������� ������.
	 * @param password ������.
	 * @return �����.
	 */
	public static Document sendChangePasswordRq(String sid, String password) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new ChangePasswordRequestData(sid, password));
		return responseData.getBody();
	}

	/**
	 * ������� ������ �� ����� ������.
	 * @param sid ������������� ������.
	 * @param login �����.
	 * @return �����.
	 */
	public static Document sendChangeLoginRq(String sid, String login) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new ChangeLoginRequestData(sid, login));
		return responseData.getBody();
	}

	/**
	 * ������� ������ �� �������� �������� ������.
	 * @param sid ������������� ������.
	 * @param password ������.
	 * @return �����.
	 */
	public static Document sendCheckPasswordRq(String sid, String password) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new CheckPasswordRequestData(sid, password));
		return responseData.getBody();
	}

	/**
	 * ������� ������ �� �������� ������������ ������� ������ � ��������� ��������
	 * @param ouid - ������������� ���������� ��������
	 * @param login - �����
	 * @return - �����
	 * @throws BackException
	 * @throws BackLogicException
	 */
	public static Document sendPrepareCheckLoginByOperationRq(String ouid, String login) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new PrepareCheckLoginByOperationRequestData(ouid, login));
		return responseData.getBody();
	}

	/**
	 * ������� ������ �� �������� ������������ ������� ������ � ��������� ��������
	 * @param ouid - ������������� ���������� ��������
	 * @param password - �����
	 * @return - �����
	 * @throws BackException
	 * @throws BackLogicException
	 */
	public static Document sendPrepareCheckPasswordByOperationRq(String ouid, String password) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new PrepareCheckPasswordByOperationRequestData(ouid, password));
		return responseData.getBody();
	}

	/**
	 * ������� ������ �� ��������� ������ ������.
	 * @param login �����.
	 * @param employeeInfo - ���������� � ����������, ����������� ������. ���� �������������� �� ���������� �����, ���� �������� null.
	 * @return �����.
	 */
	public static Document sendGeneratePasswordRq(String login, EmployeeInfo employeeInfo, boolean ignoreImsiCheck) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new GeneratePasswordRequestData(login, employeeInfo, ignoreImsiCheck));
		return responseData.getBody();
	}

	/**
	 * ������� ������ �� ��������� ������ ������ �� ������ �� ������� �������.
	 * @param userInfo ���������� � ������.
	 * @param employeeInfo - ���������� � ����������, ����������� ������
	 * @return �����.
	 */
	public static Document sendGeneratePassword2Rq(Map<String, String> userInfo, EmployeeInfo employeeInfo, boolean ignoreIMSICheck) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new GeneratePassword2RequestData(userInfo, employeeInfo, ignoreIMSICheck));
		return responseData.getBody();
	}

	/**
	 * ������� ������ �� ������ ����������� ���������� ����������
	 * @param login ��������� �������
	 * @param deviceInfo ���������� �� ����������
	 * @param confirmStrategyType ������ �������������
	 * @param registrationIPasAvailable ����������� ����������� �� ������ iPas
	 * @param deviceId ���������� ������������� ����������
	 * @return ����� ���
	 */
	public static Document sendStartMobileRegistrationRq(String login, String deviceInfo, ConfirmStrategyType confirmStrategyType, boolean registrationIPasAvailable, String deviceId, String appName) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new StartMobileRegistrationRequestData(login, deviceInfo, confirmStrategyType, registrationIPasAvailable, deviceId, appName));
		return responseData.getBody();
	}

	/**
     * ������� ������ �� ������ ����������� ���������� ����������
     * @param login ��������� �������
     * @param deviceInfo ���������� �� ����������
     * @param confirmStrategyType ������ �������������
     * @param registrationIPasAvailable ����������� ����������� �� ������ iPas
     * @param deviceId ���������� ������������� ����������
     * @return ����� ���
	 */
	public static Document sendStartMobileRegistrationRq(String login, String deviceInfo, ConfirmStrategyType confirmStrategyType, boolean registrationIPasAvailable, String deviceId, String card, String appName) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new StartMobileRegistrationRequestData(login, deviceInfo, confirmStrategyType, registrationIPasAvailable, deviceId, card, appName));
		return responseData.getBody();
	}

	/**
     * ������� ������ �� ������ ����������� ����������� ����������
     * @param login ��������� �������
     * @param deviceInfo ���������� �� ����������
     * @param confirmStrategyType ������ �������������
     * @param registrationIPasAvailable ����������� ����������� �� ������ iPas
     * @param deviceId ���������� ������������� �������
     * @return ����� ���
	 */
	public static Document sendStartSocialRegistrationRq(String login, String deviceInfo, ConfirmStrategyType confirmStrategyType, boolean registrationIPasAvailable, String deviceId, String card, String appName) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new StartSocialRegistrationRequestData(login, deviceInfo, confirmStrategyType, registrationIPasAvailable, deviceId, card, appName));
		return responseData.getBody();
	}

	/**
	 * ������� ������ �� ��������� ����������� ���������� ����������
	 * @param ouid ������������� ��������
	 * @param password ������
	 * @param deviceState ��������� ����������
	 * @param deviceId ���������� ������������� ����������
	 * @return ����� ���
	 */
	public static Document sendFinishMobileRegistrationRq(String ouid, String password, String deviceState, String deviceId, String appName) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new FinishMobileRegistrationRequestData(ouid, password, deviceState, deviceId, appName));
		return responseData.getBody();
	}

	/**
     * ������� ������ �� ��������� ����������� ����������� ����������
     * @param ouid ������������� ��������
     * @param password ������
     * @param deviceState ��������� ����������
     * @param deviceId ���������� ������������� �������
     * @return ����� ���
     */
	public static Document sendFinishSocialRegistrationRq(String ouid, String password, String deviceState, String deviceId, String appName) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new FinishSocialRegistrationRequestData(ouid, password, deviceState, deviceId, appName));
		return responseData.getBody();
	}

	/**
	 * ������� ������ �� ������ ����������� ���������� ����������
	 * @param guid ����
	 * @return ����� ���
	 */
	public static Document sendCancelMobileRegistrationRq(String guid) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new CancelMobileRegistrationRequestData(guid));
		return responseData.getBody();
	}

	/**
	 * ������� ������ �� ������ ����������� ����������� ����������
	 * @param guid ����
	 * @return ����� ���
	 */
	public static Document sendCancelSocialRegistrationRq(String guid) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new CancelSocialRegistrationRequestData(guid));
		return responseData.getBody();
	}

	/**
	 * ������� ������ �� ��������� ������ ������
	 * @param sid ������������� ������
	 * @param password ������
	 * @return ����� ���
	 */
	public static Document sendValidatePasswordRq(String sid, String password) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new ValidatePasswordRequestData(sid, password));
		return responseData.getBody();
	}

	/**
	 * ������� ������ �� ��������� ������ ������ � ���������, ��� � ������������ ��� ����� ���� ����� �����.
	 * @param validateLoginInfo ���������� ��� ��������� ������
	 * @return ����� ���
	 */
	public static Document sendValidateLoginRq(ValidateLoginInfo validateLoginInfo) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new ValidateLoginRequestData(validateLoginInfo));
		return responseData.getBody();
	}

	/**
	 * ������� ������ �� ������������ ���������� � �����
	 * @param ouid ������������� ��������
	 * @param guid ������������� ����������
	 * @return ����� ���
	 * @throws BackException
	 * @throws BackLogicException
	 */
	public static Document sendActualizeLogonInfoRq(String ouid, String guid) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new ActualizeLogonInfoRequestData(ouid, guid));
		return responseData.getBody();
	}

	/**
	 * ������� ������ �� ��������� ������������� ��������
	 * @param authToken ����� ��������������
	 * @param clientExternalId ������������� ������� �� ������� �������
	 * @param confirmType ��� �������������
	 * @param cardNumber ����� ����� �������������
	 * @return ����� ���
	 * @throws BackLogicException
	 * @throws BackException
	 */
	public static Document sendInitializeVerifyBusinessEnvironmentRq(String authToken, String clientExternalId, ConfirmStrategyType confirmType, String cardNumber) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new InitializeVerifyBusinessEnvironmentRequestData(authToken, clientExternalId, confirmType, cardNumber));
		return responseData.getBody();
	}

	/**
	 * ������� ������ �� ����������� ������ � ������� �����
	 * @param ouid ������������� ��������
	 * @return ����� ���
	 * @throws BackLogicException
	 * @throws BackException
	 */
	public static Document sendVerifyBusinessEnvironmentRq(String ouid) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new VerifyBusinessEnvironmentRequestData(ouid));
		return responseData.getBody();
	}

	/**
	 * ������� ������ �� ��������� ������ � �������� ������������� �������� ��������
	 * @param authToken ����� ��������������
	 * @return ����� ���
	 * @throws BackLogicException
	 * @throws BackException
	 */
	public static Document sendGetConfirmationInfoRq(String authToken) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new GetConfirmationInfoRequestData(authToken));
		return responseData.getBody();
	}

	/**
	 * ������� ������ �� ��������� �������� ����������� push-�����������
	 * @param guid - ������������� ����������
	 * @param deviceId - ������������� ����������
	 * @param pushSupported - ������� ����������� push-�����������
	 * @param securityToken - ������ ������ ������������, �������������� ��������� �����������
	 * @return ����� ���
	 * @throws BackLogicException
	 * @throws BackException
	 */
	public static Document sendChangePushSupportedRq(String guid, String deviceId, boolean pushSupported, String securityToken) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new SetPushSupportedRequestData(guid, deviceId, pushSupported, securityToken));
		return responseData.getBody();
	}

	/**
	 * ������� ������ �� �������� ������ ��� ���
	 * @param cardNumber ����� �����
	 * @return ����� ���
	 * @throws BackLogicException
	 * @throws BackException
	 */
	public static Document sendStartCreateATMSessionRq(String cardNumber) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new StartCreateATMSessionRequestData(cardNumber));
		return responseData.getBody();
	}

	/**
	 * ������� ������ �� ��������� ���������� � ������ ��� �������������� ������������
	 * @param ouid ����� ��������������
	 * @return ����� ���
	 * @throws BackException
	 * @throws BackLogicException
	 */
	public static Document sendFinishCreateATMSessionRq(String ouid) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new FinishCreateATMSessionRequestData(ouid));
		return  responseData.getBody();
	}

	/**
	 * ������� ������ ���������� ����������� ��������� (����������, ��������, ����� ��������� ��������)
	 * @param phoneNumber ����� ��������� ��������
	 * @param userInfo ���������� � ������������
	 * @param addPhones ����������� ��������
     * @param removePhones ��������� ��������
	 * @return ����� ���
	 * @throws BackLogicException
	 * @throws BackException
	 */
	public static Document sendUpdatePhoneRegistrationsRq(String phoneNumber, UserInfo userInfo, List<String> addPhones, List<String> removePhones) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new UpdatePhoneRegistrationsRequestData(phoneNumber, userInfo, addPhones, removePhones));
		return responseData.getBody();
	}

	/**
	 * !!! ������������ ����� ������ � ������, ��� ������ ����������, ��� ����������� ��������,
	 * ��� ������� ������� ��������� � ������ �������������, ����������� ��� !!!
	 *
	 * ������� ������ ���������� ����������� ��������� (����������, ��������, ����� ��������� ��������), ��������� ���������
	 * @param phoneNumber ����� ��������� ��������
	 * @param userInfo ���������� � ������������
	 * @param addPhones ����������� ��������
     * @param removePhones ��������� ��������
	 * @return ����� ���
	 * @throws BackLogicException
	 * @throws BackException
	 */
	public static Document sendUpdatePhoneRegRemoveDuplicateRq(String phoneNumber, UserInfo userInfo, List<String> addPhones, List<String> removePhones) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new UpdatePhoneRegRemoveDuplicateRequestData(phoneNumber, userInfo, addPhones, removePhones));
		return responseData.getBody();
	}

	/**
	 * ������� ������ �� ��������� ���������� � ����� ������������ �� ��������
	 * @param phoneNumber ����� ��������
	 * @return ����� ���
	 * @throws BackLogicException
	 * @throws BackException
	 */
	public static Document sendFindProfileNodeByPhoneRq(String phoneNumber) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new FindProfileNodeByPhoneRequestData(phoneNumber));
		return responseData.getBody();
	}

	/**
	 * ������� ������ �� ��������� ���������� � ����� ������������ �� ��� ��� �� ��
	 * @param userInfo ���������� � ������������
	 * @param needCreateProfile ���������� �� ��������� ������� �������.
	 * @param createProfileNodeMode ������� �������� ������� � �����.
	 * @return ����� ���
	 * @throws BackException
	 * @throws BackLogicException
	 */
	public static Document sendFindProfileNodeByUserInfoRq(UserInfo userInfo, boolean needCreateProfile, CreateProfileNodeMode createProfileNodeMode) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new FindProfileNodeByUserInfoRequestData(userInfo, needCreateProfile, createProfileNodeMode));
		return responseData.getBody();
	}

	/**
	 * ������� ������ �� ���������� ������� ���
	 * @param newUserInfo ����� ���������� � ������������
	 * @param oldUserInfo ������ ���������� � ������������
	 * @return ����� ���
	 * @throws BackException
	 * @throws BackLogicException
	 */
	public static Document sendUpdateProfileRq(UserInfo newUserInfo, UserInfo oldUserInfo) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new UpdateProfileRequestData(newUserInfo, oldUserInfo));
		return responseData.getBody();
	}

	/**
	 * �������� ���������� � ������
	 * @return - �������� � ����������� � ������ �������
	 */
	public static Document sendGetNodesInfoRq() throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new GetNodesInfoRequestData());
		return responseData.getBody();
	}

	/**
	 * �������� ���������� � ������������ �� ������ ��������
	 * � ������, ���� ���� ���������� �� �������� �� ����� ������� � ��������� ������� "������������ �����",
	 * ����� ������� � �� ����� �� �������� � ���������� �� ������� ���������� �� ������� ���������� �� ������ ����� ����������
	 * �����! ���� ��� �� ����� ������������, ����� ������ "ProfileNotFoundException: �������� �������� ����������, ��������� ������� �����."
	 * @param phoneNumber ����� ��������
	 * @param usingCardByPhone ������������ �� �����, ��������� �� �������� � �� ��� ��������� ����������
	 * @return ����� ���
	 * @throws BackException
	 * @throws BackLogicException
	 */
	public static Document sendGetUserInfoByPhoneRq(String phoneNumber, boolean usingCardByPhone) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new GetUserInfoByPhoneRequestData(phoneNumber, usingCardByPhone));
		return responseData.getBody();
	}

	/**
	 * �������� ���������� � ������������ �� deviceId � deviceInfo.
	 * @param deviceId - ������������� ������� �� ������� ����������
	 * @param deviceInfo - ������������� ���������, � ������ ������� �������� ������ �� �������
	 * @return ����� ���
	 * @throws BackException
	 * @throws BackLogicException
	 */
	public static Document sendGetUserInfoByDeviceIdAndInfoRq(String deviceId, String deviceInfo) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new GetUserInfoByDeviceIdAndInfoRequestData(deviceId, deviceInfo));
		return responseData.getBody();
	}

	/**
	 * �������� ���������� � ������������ �� ������ �������� � ���������� � ���
	 * @param phoneNumber ����� ��������
	 * @return ����� ���
	 * @throws BackException
	 * @throws BackLogicException
	 */
	public static Document sendGetUserInfoByPhoneWithMBRq(String phoneNumber) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new GetUserInfoByPhoneWithMBRequestData(phoneNumber));
		return responseData.getBody();
	}

	/**
     * ������� ������ �� ��������� ��������� ����������� �������
     * @param sid - ������������� ������
     * @param incognito - �������� ��������� �����������
     * @return �����
     * @throws BackLogicException
     * @throws BackException
     */
    public static Document sendChangeIncognitoSettingRq(String sid, boolean incognito) throws BackLogicException, BackException
    {
        ResponseData responseData = sender.sendRequest(new ChangeIncognitoSettingData(sid, incognito));
        return responseData.getBody();
	}

	/**
     * ������� ������ �� ��������� ��������� ����������� �������
     * @param userInfo - ������
     * @param incognito - �������� ��������� �����������
     * @return �����
     * @throws BackLogicException
     * @throws BackException
     */
    public static Document sendChangeIncognitoSettingRq(UserInfo userInfo, IncognitoState incognito) throws BackLogicException, BackException
    {
        ResponseData responseData = sender.sendRequest(new ChangeIncognitoSettingData(userInfo, incognito));
        return responseData.getBody();
	}

	/**
	 * ����� ���� �� ��������
	 * @param fio ��� �������
	 * @param document ��� �������
	 * @param birthday �� �������
	 * @param login ����� �������
	 * @param creationType ��� ��������
	 * @param agreementNumber ����� ��������
	 * @param phoneNumber ����� ��������
	 * @param tbList ������ �� � ������� ����� ������
	 * @param maxResults ������������ ���������� ��������
	 * @param firstResult �������� �������
	 * @return �����
	 */
	@SuppressWarnings("MethodWithTooManyParameters")
	public static Document getClientsInformationRq(String fio, String document, Calendar birthday, String login,
	                                               CreationType creationType, String agreementNumber, String phoneNumber,
	                                               List<String> tbList, int maxResults, int firstResult) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new GetClientsInformationRequestData(fio, document, birthday, login, creationType, agreementNumber, phoneNumber, tbList, maxResults, firstResult));
		return responseData.getBody();
	}

	/**
	 * ����� ���� �� ��������, ��������� ��������
	 * @param fio ��� �������
	 * @param document ��� �������
	 * @param birthday �� �������
	 * @param creationType ��� ��������
	 * @param agreementNumber ����� ��������
	 * @param tbList ������ �� � ������� ����� ������
	 * @param nodeId ������������� ��������� �����
	 * @param maxResults ������������ ���������� ��������
	 * @param firstResult �������� �������
	 * @return �����
	 */
	@SuppressWarnings("MethodWithTooManyParameters")
	public static Document getTemporaryNodeClientsInformationRq(String fio, String document, Calendar birthday,
	                                               CreationType creationType, String agreementNumber,
	                                               List<String> tbList, Long nodeId, int maxResults, int firstResult) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new GetTemporaryNodeClientsInformationRequestData(fio, document, birthday, creationType, agreementNumber, tbList, nodeId, maxResults, firstResult));
		return responseData.getBody();
	}

	/**
	 * ���������� �������� � ��������� �����
	 * @param nodeId ������������� ��������� �����
	 * @return �����
	 */
	public static Document getTemporaryNodeClientsCountRq(Long nodeId) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new GetTemporaryNodeClientsCountRequestData(nodeId));
		return responseData.getBody();
	}

	/**
	 * ��������� �������
	 * @param userInfo ���������� � �������
	 * @return �����
	 */
	public static Document getClientNodeStateRq(UserInfo userInfo) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new GetClientNodeStateRequestData(userInfo));
		return responseData.getBody();
	}

	/**
	 * ���������� ������� ������� �� ���
	 * @param profileId ������������� �������
	 * @param mdmId ������������� � ���
	 * @return �����
	 */
	public static Document updateClientMDMInfoRq(Long profileId, String mdmId) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new UpdateClientMDMInfoRequestData(profileId, mdmId));
		return responseData.getBody();
	}

	/**
	 * ��������e �������������� ������� �� ���+���+��+��
	 * @param info ���������� � �������
	 * @return �����
	 */
	public static Document getClientProfileIdRequestDataRq(UserInfo info) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new GetClientProfileIdRequestData(info));
		return responseData.getBody();
	}

	/**
	 * ������� ������ �� ��������� ������� ��������� ������ ������������.
	 * @param userInfo ������ ������������.
	 * @return ������� ���������.
	 */
	public static Document sendGetProfileHistoryInfo(UserInfo userInfo) throws BackLogicException, BackException
	{
		ResponseData responceData = sender.sendRequest(new GetProfileHistoryInfo(userInfo));
		return responceData.getBody();
	}
	/**
	 * ���������� ���. ���������� �������
	 * @param userInfo ����������������� ������ �������
	 * @param creationType ��� ��������
	 * @param agreementNumber ����� ��������
	 * @param phone �������
	 * @return �����
	 */
	public static Document sendUpdateProfileAdditionalDataRq(UserInfo userInfo, CreationType creationType, String agreementNumber, String phone) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new UpdateProfileAdditionalDataRequestData(userInfo, creationType, agreementNumber, phone));
		return responseData.getBody();
	}

	/**
	 * ������� ������ �� ��������� ������ ������ �� ������ ����������� ��������
	 * @param updateProfileInfoList ������ ����������� ��������
	 * @return ����� ���
	 * @throws BackLogicException
	 * @throws BackException
	 */
	public static Document sendFindNodesByUpdatedProfilesRq(List<UpdateProfileInfo> updateProfileInfoList) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new FindNodesByUpdatedProfilesRequestData(updateProfileInfoList));
		return responseData.getBody();
	}

	/**
	 * ����� ���������� � ������������
	 * @param firstName - ���
	 * @param surName - �������
	 * @param patrName - ��������
	 * @param passport - ������ ��������
	 * @param birthdate - ���� ��������
	 * @param tb - �������
	 * @return �����
	 * @throws BackLogicException
	 * @throws BackException
	 */
	@SuppressWarnings("MethodWithTooManyParameters")
	public static Document findProfileInformationRq(String surName, String firstName, String patrName, String passport,
	                                                Calendar birthdate, String tb) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new FindProfileInformationRequestData(surName, firstName, patrName, passport, birthdate, tb));
		return responseData.getBody();
	}

	/**
	 * ����� ���������� � ������������ � ������ ����������� �� ����� � ������� ������� ������
	 * @param firstName - ���
	 * @param surName - �������
	 * @param patrName - ��������
	 * @param passport - ������ ��������
	 * @param birthdate - ���� ��������
	 * @param tb - �������
	 * @return ������� � �������� � ����������� �� ����� � ������� ������� ������
	 * @throws BackLogicException
	 * @throws BackException
	 */
	@SuppressWarnings("MethodWithTooManyParameters")
	public static Document findProfileInformationWithNodeInfoRq(String surName, String firstName, String patrName, String passport,
	                                                            Calendar birthdate, String tb) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new FindProfileInformationWithNodeInfoRequestData(surName, firstName, patrName, passport, birthdate, tb));
		return responseData.getBody();
	}

	/**
	 * ������� ������� ������� � ���.
	 * @param firstName - ���
	 * @param surName - �������
	 * @param patrName - ��������
	 * @param passport - ������ ��������
	 * @param birthdate - ���� ��������
	 * @param tb - �������
	 * @return ��������� ������� ������� � ����������� ������.
	 *         ���� ������� � ������ ������� ��� � ���, �� ����� ������� �� ������� - ���������� ������������.
	 * @throws BackLogicException
	 * @throws BackException
	 */
	@SuppressWarnings("MethodWithTooManyParameters")
	public static Document createProfileRq(String surName, String firstName, String patrName, String passport,
	                                                Calendar birthdate, String tb) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new CreateProfileRequestData(surName, firstName, patrName, passport, birthdate, tb));
		return responseData.getBody();
	}

	/**
	 * ������� ������ �� ���������� �������
	 * @param userInfo ���������� � ����������� �������
	 * @param lockFrom ������ ����������
	 * @param lockTo ��������� ����������
	 * @param reason ������� ����������
	 * @param blockerFIO ��� ������������ ����������
	 * @return �����
	 */
	public static Document sendLockProfileRq(UserInfo userInfo, Calendar lockFrom, Calendar lockTo, String reason, String blockerFIO) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new LockProfileRequestData(userInfo, lockFrom, lockTo, reason, blockerFIO));
		return responseData.getBody();
	}

	/**
	 * ������� ������ �� ������ ���������� �������
	 * @param userInfo ���������� � �������������� �������
	 * @return �����
	 */
	public static Document sendUnlockProfileRq(UserInfo userInfo) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new UnlockProfileRequestData(userInfo));
		return responseData.getBody();
	}

	/**
	 * ������� ������ �� "����������" ������� ������������� � ������ ������� CHG071536.
	 * �� ����� ����� ���� ������� ���������� � �������.
	 * @param userInfo ���������� � ����������� �������
	 * @param lockFrom ���� ������ ����������
	 * @param lockTo ���� ��������� ����������
	 * @param reason ������� ����������
	 * @param blockerFIO ��� ������������ ����������
	 * @return �����
	 */
	public static Document sendLockProfileCHG071536Rq(UserInfo userInfo, Calendar lockFrom, Calendar lockTo, String reason, String blockerFIO) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new LockProfileCHG071536RequestData(userInfo, lockFrom, lockTo, reason, blockerFIO));
		return responseData.getBody();
	}

	/**
	 * ������� ������ �� "����������" ������� ������������� � ������ ������� CHG071536.
	 * �� ����� ����� ���� ������� ���������� � �������.
	 * @param profileId ������������� ������� �������
	 * @param lockFrom ���� ������ ����������
	 * @param lockTo ���� ��������� ����������
	 * @param reason ������� ����������
	 * @param blockerFIO ��� ������������ ����������
	 * @return �����
	 */
	public static Document sendLockProfileCHG071536Rq(Long profileId, Calendar lockFrom, Calendar lockTo, String reason, String blockerFIO) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new LockProfileCHG071536ByProfileIdRequestData(profileId, lockFrom, lockTo, reason, blockerFIO));
		return responseData.getBody();
	}

	/**
	 * ������� ������
	 * @param cardNum ����� �����
	 * @param sendSMS ������� �������� ���
	 * @return �����
	 */
	public static Document sendUserRegistrationDisposableRq(String cardNum, String sendSMS) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new UserRegistrationDisposableRequestData(cardNum, sendSMS));
		return responseData.getBody();
	}

	/**
	 * ������� ������ �� ���������� ������� ������� ������ ������������
	 * @param history ������� ��������� ������ �������
	 * @return ����� ���
	 * @throws BackLogicException
	 * @throws BackException
	 */
	public static Document sendLowerProfileSecurityTypeRq(List<UserInfo> history) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new LowerProfileSecurityTypeRequestData(history));
		return responseData.getBody();
	}

	/**
	 * �������� �� ��� ������� ���������.
	 * @param sid ������������ ������
	 * @return ����� ���.
	 * @throws BackLogicException
	 * @throws BackException
	 */
	public static Document sendGetIncognitoSettingRq(String sid) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new GetIncognitoSettingData(sid));
		return responseData.getBody();
	}

	/**
	 * �������� �� ��� ������� ���������.
	 * @param userInfo ���������� � �������
	 * @return ����� ���.
	 * @throws BackLogicException
	 * @throws BackException
	 */
	public static Document sendGetIncognitoSettingRq(UserInfo userInfo) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new GetIncognitoSettingData(userInfo));
		return responseData.getBody();
	}

	/**
	 * �������� ���������� � ����������� ����
	 * @param userInfo ���������� � �������
	 * @return ����� ���.
	 * @throws BackLogicException
	 * @throws BackException
	 */
	public static Document getErmbConnectorInfoRq(UserInfo userInfo) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new GetErmbConnectorInfoRequestData(userInfo));
		return responseData.getBody();
	}

	/**
	 * ����� ����-���������
	 *
	 * @param surName - �������
	 * @param firstName - ���
	 * @param patrName - ��������
	 * @param passports ������ ���
	 * @param birthdate - ���� ��������
	 * @return ������ ���������� ������� ��������� ��� ������ ������
	 */
	@SuppressWarnings("MethodWithTooManyParameters")
	public static Collection<String> findErmbPhones(String surName, String firstName, String patrName, Collection<String> passports, Calendar birthdate) throws BackLogicException, BackException
	{
		Cache cache = CacheProvider.getCache("csa-ermb-phones-cache");
		String[] strings = new String[]{surName, firstName, patrName, passports.toString(), birthdate.toString()};
		String cacheKey = StringUtils.join(strings);
		Element cacheValue = null;
		if (cache != null)
			cacheValue = cache.get(cacheKey);
		if (cacheValue != null)
			//noinspection unchecked
			return new ArrayList<String>((Collection<String>) cacheValue.getObjectValue());

		ResponseData responseData = sender.sendRequest(new GetErmbPhoneListRequestData(surName, firstName, patrName, birthdate, passports));
		Document response = responseData.getBody();
		String[] phoneNumbers = StringUtils.split(XmlHelper.getSimpleElementValue(response.getDocumentElement(), CSAResponseConstants.PHONES_TAG), ',');
		if (phoneNumbers == null)
			return Collections.emptyList();
		else
		{
			Collection<String> result = Arrays.asList(phoneNumbers);
			cache.put(new Element(cacheKey, result));
			return result;
		}
	}

	/**
	 * �������� ���������� � ����������� �������
	 * @param sid ������������ ������
	 * @return ����� ���.
	 * @throws BackLogicException
	 * @throws BackException
	 */
	public static Document sendGetClientConnectorsRq(String sid, ConnectorInfo.Type type) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new GetClientConnectorsData(sid, type));
		return responseData.getBody();
	}

	/**
	 * �������� ���������� � ����������� �������
	 *
	 * @param userInfo ���������� � �������
	 * @param type
	 * @return ����� ���.
	 * @throws BackLogicException
	 * @throws BackException
	 */
	public static Document sendGetClientConnectorsRq(UserInfo userInfo, ConnectorInfo.Type type) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new GetClientConnectorsData(userInfo, type));
		return responseData.getBody();
	}

	/**
	 * �������� ������ ���� �� ������ �������
	 * @param surName - ������� �������
	 * @param firstName - ��� �������
	 * @param patrName - �������� �������
	 * @param passport - ���
	 * @param birthdate - ��
	 * @param tb - ��
	 * @return ����� ���
	 * @throws BackLogicException
	 * @throws BackException
	 */
	@SuppressWarnings("MethodWithTooManyParameters")
	public static Document findProfileCardNumberListRq(String surName, String firstName, String patrName, String passport,
		                                                Calendar birthdate, String tb) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new FindProfileCardNumberListRequestData(surName, firstName, patrName, passport, birthdate, tb));
		return responseData.getBody();
	}

	/**
	 * ��������� ���������� � ��������� ������
	 * @param changedNodesAvailabilityInfo ����� ����������
	 * @return ���������� � ��������� ������
	 * @throws BackLogicException
	 * @throws BackException
	 */
	public static Document changeNodesAvailabilityInfo(Collection<com.rssl.phizic.gate.csa.NodeInfo> changedNodesAvailabilityInfo) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new ChangeNodesAvailabilityInfoRequestData(changedNodesAvailabilityInfo));
		return responseData.getBody();
	}
/**
	 * ������ ������ ���� �� ������� ���������.
	 *
	 * @param srcPhones �������� ������.
	 * @return ������ ������� ����.
	 * @throws BackLogicException
	 * @throws BackException
	 */
	public static List<String> getRegisteredPhones(Collection<String> srcPhones) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new HasUserByPhoneRequestData(srcPhones));
		Document document = responseData.getBody();
		org.w3c.dom.Element element = document.getDocumentElement();
        String phoneNumbers[] = XmlHelper.getSimpleElementValue(element, RequestConstants.PHONES_TAG).split("\\|");
		if (phoneNumbers.length == 0 || StringHelper.isEmpty(phoneNumbers[0]))
			return Collections.EMPTY_LIST;
		return Arrays.asList(phoneNumbers);
	}

	/**
	 * ��������� ��� �� ������� ��� ���������� ��������� �����������
	 * @param userInfo ���������� � �������
	 * @return ���������� �� ���
	 * @throws BackLogicException
	 * @throws BackException
	 */
	public static Document sendLockProfileForExecuteDocument(UserInfo userInfo) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new LockProfileForExecuteDocumentRequestData(userInfo));
		return responseData.getBody();
	}

	/**
	 * ����� ��� � ������� ����� ���������� ��������� �����������
	 * @param userInfo ���������� � �������
	 * @return ������ �� ���
	 * @throws BackLogicException
	 * @throws BackException
	 */
	public static Document sendUnlockProfileForExecuteDocument(UserInfo userInfo) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new UnlockProfileForExecuteDocumentRequestData(userInfo));
		return responseData.getBody();
	}

	/**
	 * ��������� ��������������� ������� ��� ��� � ��� (����� ��� ��������)
	 * ����� ������ ��� ���!
	 * @param technoBreak ��������������� �������
	 * @return ����� ���
	 */
	public static Document saveOrUpdateMbkTechnoBreak(TechnoBreak technoBreak) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new MbkTechnoBreakSaveOrUpdateRequestData(technoBreak));
		return responseData.getBody();
	}

	/**
	 * �������� ���������� � ���������� ������� �� ���� �������
	 * @param userInfo ���������� � ����������
	 * @return ����� ���
	 * @throws BackLogicException
	 * @throws BackException
	 */
	public static Document sendFindFundSenderInfo(UserInfo userInfo) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new FindFundSenderInfoRequestData(userInfo));
		return responseData.getBody();
	}

	/**
	 * ������� ������ �� ������������� ��������� ����� �� ����������� ������
	 * @param phoneNumber - ����� ��������
	 * @return - ����� ���
	 * @throws BackLogicException
	 * @throws BackException
	 */
	public static Document sendGuestEntryInitialRq(String phoneNumber) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new GuestEntryInitialRequestData(phoneNumber));
		return responseData.getBody();
	}

	/**
	 * ������� ������ �� ������������� ���-������ ��� �������������� � �������� �����
	 * @param password - ������
	 * @return - ����� ���
	 * @throws BackLogicException
	 * @throws BackException
	 */
	public static Document sendGuestEntryConfirmationRq(String ouid, String password) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new GuestEntryConfirmationRequestData(ouid, password));
		return responseData.getBody();
	}

	/**
	 * ������� ������ �� ���������� ��������������
	 * @param ouid ������������� �������� �����
	 * @return ������ ��������������
	 * @throws BackLogicException
	 * @throws BackException
	 */
	public static Document sendFinishCreateGuestSessionRq(String ouid) throws BackLogicException, BackException
	{
		ResponseData responseData = sender.sendRequest(new FinishCreateGuestSessionRequestData(ouid));
		return responseData.getBody();
	}

	/**
	 * ������� ������ �� �������� IMSI.
	 * @param login �����.
	 * @param employeeInfo - ���������� � ����������, ����������� ������. ���� �������������� �� ���������� �����, ���� �������� null.
	 * @return �����.
	 */
	public static Document sendCheckIMSIRq(String login, EmployeeInfo employeeInfo) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new CheckIMSIRequestData(login, employeeInfo));
		return responseData.getBody();
	}

	/**
	 * ������� ������ �� ����������� �����
	 * @param phone ����� �������� �����
	 * @param code ��� �����
	 * @param login �����
	 * @param password ������
	 * @param nodeNumber ����� �����
	 * @param userInfo ���������� � �����
	 * @return ���� ������
	 * @throws BackException
	 * @throws BackLogicException
	 */
	public static Document sendGuestRegistrationRq(String phone, Long code, String login, String password, Long nodeNumber, UserInfo userInfo) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new GuestRegistrationRequestData(phone, code, login, password, nodeNumber, userInfo));
		return responseData.getBody();
	}

	/**
	 * ��������� ���������� � ������� ���� ���-������ � �������
	 * @param userInfo ���������� � �������
	 * @return ����� ���
	 * @throws BackException
	 * @throws BackLogicException
	 */
	public static Document sendGetContainsProMAPIInfoRq(UserInfo userInfo) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new GetContainsProMAPIInfoRequestData(userInfo));
		return responseData.getBody();
	}

	/**
	 * ��������� �������������� ���������� �� �����:
	 *  <ol>
	 *     <li>��������� �� ������� � ��</li>
	 *     <li>���� �� � ��� �������� ������� ������</li>
	 * </ol>
	 * @param phone ����� �������� �����
	 * @return ����� �� ���
	 */
	public static Document getAdditionInformationForGuest(String phone) throws BackException, BackLogicException
	{
		ResponseData responseData = sender.sendRequest(new GetAdditionInformationForGuestRequestData(phone));
		return responseData.getBody();
	}
}
