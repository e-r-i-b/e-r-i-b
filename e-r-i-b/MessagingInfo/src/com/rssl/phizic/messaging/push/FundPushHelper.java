package com.rssl.phizic.messaging.push;

import com.rssl.auth.csa.wsclient.responses.ConnectorInfo;
import com.rssl.phizic.gate.csa.UserInfo;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.csa.ConnectorsService;
import com.rssl.phizic.business.departments.DepartmentsUtils;
import com.rssl.phizic.business.fund.FundPushInfo;
import com.rssl.phizic.business.fund.initiator.FundInitiatorResponseService;
import com.rssl.phizic.business.fund.initiator.FundRequest;
import com.rssl.phizic.business.fund.sender.FundSenderResponse;
import com.rssl.phizic.business.persons.ClientDataImpl;
import com.rssl.phizic.business.persons.LightPerson;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.profile.images.AvatarInfo;
import com.rssl.phizic.business.profile.images.AvatarJurnalService;
import com.rssl.phizic.business.profile.images.AvatarType;
import com.rssl.phizic.common.types.exceptions.IKFLMessagingException;
import com.rssl.phizic.common.types.fund.Constants;
import com.rssl.phizic.common.types.transmiters.Triplet;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.fund.FundMultiNodeService;
import com.rssl.phizic.gate.fund.RequestInfo;
import com.rssl.phizic.gate.fund.ResponseExternalIdImpl;
import com.rssl.phizic.messaging.MessageComposer;
import com.rssl.phizic.messaging.MessagingSingleton;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author osminin
 * @ created 25.12.14
 * @ $Author$
 * @ $Revision$
 */
public class FundPushHelper
{
	private static PersonService personService = new PersonService();
	private static FundInitiatorResponseService initiatorResponseService = new FundInitiatorResponseService();
	private static AvatarJurnalService avatarJurnalService = new AvatarJurnalService();

	/**
	 * ��������� ������ �������� �� id �������� �������, ��������������� �� guid � �������� ��������� ��������� �������
	 * @param listClientConnected ���������� � ����������� ������������
	 * @return  null - ���� ������ ������ ������ ��� ��� �����������, �������������� ����
	 *          empty - ���� ���� ���� ���������� � ���������� �����, �� �� � ������ �� �������� deviceId
	 *          list - ���� ���� ���������� � ���������� ����� � ������������ deviceId, �� ����������� ������ ���� �����������
	 */
	public static List<Triplet<String, String, String>> fillTripletList(List<ConnectorInfo> listClientConnected)
	{
		if (CollectionUtils.isEmpty(listClientConnected))
		{
			return null;
		}

		List<Triplet<String, String, String>> triplets = new ArrayList<Triplet<String, String, String>>(listClientConnected.size());
		boolean pushSupportedNotEmpty = false;

		for (ConnectorInfo clientConnected : listClientConnected)
		{
			if (clientConnected.isPushSupported())
			{
				pushSupportedNotEmpty = true;
				//��������� ������, ������ ��� ����������� � ����������� deviceId
				if (StringHelper.isNotEmpty(clientConnected.getDeviceID()))
				{
					Triplet<String, String, String> triplet = new Triplet<String, String, String>();
					triplet.setFirst(clientConnected.getDeviceID());
					triplet.setSecond(clientConnected.getGuid());
					triplet.setThird(clientConnected.getDeviceInfo());
					triplets.add(triplet);
				}
			}
		}

		return pushSupportedNotEmpty ? triplets : null;
	}

	/**
	 * ��������� ���-����������� � ���, ��� ����������� ����� �������
	 * @param request ��������� ������ �� ���� �������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public static void sendAccumulatedPush(FundRequest request) throws BusinessException, BusinessLogicException
	{
		sendPush(fillAccumulatedPushInfo(request));
	}

	/**
	 * ��������� ���-����������� � ���, ��� ������ ��� ��������
	 * @param response �������� ������ �� ���� �������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public static void sendRejectPush(FundSenderResponse response) throws BusinessException, BusinessLogicException
	{
		sendPush(fillRejectPushInfo(response));
	}

	/**
	 * ��������� ���-����������� � ���, ��� ������ ��� ������������
	 * @param response �������� ������ �� ���� �������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public static void sendExecutedPush(FundSenderResponse response) throws BusinessException, BusinessLogicException
	{
		sendPush(fillExecutedPushInfo(response));
	}

	/**
	 * ��������� ��� �����������
	 * @param pushInfo ���������� � ����
	 * @throws BusinessException
	 */
	public static void sendPush(FundPushInfo pushInfo) throws BusinessException
	{
		try
		{
			if (pushInfo != null)
			{
				PushMessage message = MessageComposer.buildInformingPushMessage(pushInfo.getPushMessageKey(), pushInfo.getProperties(), false);
                PushTransportService pushTransportService = MessagingSingleton.getInstance().getPushTransportService();
				pushTransportService.sendPush(pushInfo.getClientData(), message, pushInfo.getPhones(), pushInfo.getTriplets());
			}
		}
		catch (IKFLMessagingException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ��������� ���������� � ���-����������� ��� ���������� � �������� ������� � ����� � ��������� ����������� ������
	 * @param request ������ �� ���� �������
	 * @return ���������� � ���-�����������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	private static FundPushInfo fillAccumulatedPushInfo(FundRequest request) throws BusinessException, BusinessLogicException
	{
		UserInfo userInfo = fillUserInfoByRequest(request);
		return fillPushInfo(userInfo, Constants.PUSH_ACCUMULATED_MESSAGE_KEY, fillAccumulatedPushProperties(request), request.getInitiatorPhonesAsList());
	}

	/**
	 * ��������� ���������� � ������������ �� �������
	 * @param request ������ �� ���� �������
	 * @return ���������� � ������������
	 */
	private static UserInfo fillUserInfoByRequest(FundRequest request) throws BusinessException
	{
		if (request == null)
		{
			throw new IllegalArgumentException("������ �� ���� ������� �� ����� ���� null.");
		}

		//�������� ���������� �� ������ �� �������
		LightPerson initiator = personService.getLightPersonByLogin(request.getLoginId());
		if (initiator == null)
		{
			throw new BusinessException("�� ������ ������������ � loginId " + request.getLoginId());
		}

		UserInfo userInfo = new UserInfo();

		userInfo.setFirstname(initiator.getFirstName());
		userInfo.setSurname(initiator.getSurName());
		userInfo.setPatrname(initiator.getPatrName());
		userInfo.setBirthdate(initiator.getBirthDay());
		userInfo.setTb(DepartmentsUtils.getTbByDepartmentId(initiator.getDepartmentId()));
		userInfo.setCbCode(userInfo.getTb());
		userInfo.setPassport(PersonHelper.getPersonPassportWay(initiator.getId()).getDocumentSeries());

		return userInfo;
	}

	/**
	 * ��������� ���������� � ������������ �� ��������� ������� �� ���� �������
	 * @param response �������� ������ �� ���� �������
	 * @return ���������� � ������������
	 */
	private static UserInfo fillUserInfoByResponse(FundSenderResponse response)
	{
		if (response == null)
		{
			throw new IllegalArgumentException("�������� ������ �� ���� ������� �� ����� ���� null.");
		}

		UserInfo userInfo = new UserInfo();

		userInfo.setFirstname(response.getInitiatorFirstName());
		userInfo.setSurname(response.getInitiatorSurName());
		userInfo.setPatrname(response.getInitiatorPatrName());
		userInfo.setBirthdate(response.getInitiatorBirthDate());
		userInfo.setPassport(response.getInitiatorPassport());
		userInfo.setTb(response.getInitiatorTb());
		userInfo.setCbCode(response.getInitiatorTb());

		return userInfo;
	}

	/**
	 * �������� ������ ��� ����� �� ���������� � ������������
	 * @param userInfo ���������� � ������������
	 * @return ������ �����
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	private static List<Triplet<String, String, String>> getTriplets(UserInfo userInfo) throws BusinessException, BusinessLogicException
	{
		if (userInfo == null)
		{
			throw new IllegalArgumentException("���������� � ������������ �� ����� ���� null.");
		}

		List<ConnectorInfo> connectorInfoList = ConnectorsService.getClientMAPIConnectors(userInfo);
		return fillTripletList(connectorInfoList);
	}

	private static FundPushInfo fillRejectPushInfo(FundSenderResponse response) throws BusinessException, BusinessLogicException
	{
		UserInfo userInfo = fillUserInfoByResponse(response);
		List<String> phones = Arrays.asList(response.getListInitiatorPhones());

		return fillPushInfo(userInfo, Constants.PUSH_REJECT_MESSAGE_KEY, fillRejectPushProperties(response), phones);
	}

	private static FundPushInfo fillExecutedPushInfo(FundSenderResponse response) throws BusinessException, BusinessLogicException
	{
		UserInfo userInfo = fillUserInfoByResponse(response);
		List<String> phones = Arrays.asList(response.getListInitiatorPhones());

		return fillPushInfo(userInfo, Constants.PUSH_PAY_MESSAGE_KEY, fillExecutedPushProperties(response), phones);
	}

	private static FundPushInfo fillPushInfo(UserInfo userInfo, String pushMessageKey, Map<String, Object> properties, List<String> phones) throws BusinessLogicException, BusinessException
	{
		List<Triplet<String, String, String>> triplets = getTriplets(userInfo);
		//���� ������ ����� null, ������ ���-����������� ���������
		if (triplets != null)
		{
			FundPushInfo fundPushInfo = new FundPushInfo();
			fundPushInfo.setClientData(new ClientDataImpl(userInfo));
			//���� ������ ����� ������, ������ ��������� ������ qpe_addresses_tab, ����� ������ qpe_devices_tab (��. CHG089693)
			fundPushInfo.setPhones(CollectionUtils.isEmpty(triplets) ? phones : Collections.<String>emptyList());
			fundPushInfo.setTriplets(triplets);
			fundPushInfo.setPushMessageKey(pushMessageKey);
			fundPushInfo.setProperties(properties);

			return fundPushInfo;
		}

		return null;
	}

	private static Map<String, Object> fillAccumulatedPushProperties(FundRequest request) throws BusinessException
	{
		Map<String, Object> properties = new HashMap<String, Object>();

		properties.put(Constants.PUSH_PARAM_ACCUMULATED_SUM, initiatorResponseService.getAccumulatedSum(request.getId()));
		properties.put(Constants.PUSH_PARAM_MESSAGE, request.getMessage());
		properties.put(Constants.PUSH_PARAM_REQUEST_ID, request.getId());
		properties.put(Constants.PUSH_PARAM_REQUIRED_SUM, request.getRequiredSum());
		properties.put(Constants.PUSH_PARAM_STATE, request.getViewState().getDescription());

		return properties;
	}

	private static Map<String, Object> fillRejectPushProperties(FundSenderResponse response) throws BusinessException, BusinessLogicException
	{
		RequestInfo requestInfo = getRequestInfo(response.getExternalId());
		if (requestInfo == null || requestInfo.getInternalId() == null)
		{
			throw new BusinessException("������ �� ���� ������� �� �������������� ������ " + response.getExternalId() + " �� ������.");
		}

		try
		{
			Map<String, Object> properties = new HashMap<String, Object>();

			properties.put(Constants.PUSH_PARAM_NAME, response.getFirstName());
			properties.put(Constants.PUSH_PARAM_MESSAGE, response.getMessage());
			properties.put(Constants.PUSH_PARAM_REQUEST_ID, requestInfo.getInternalId());
			properties.put(Constants.PUSH_PARAM_STATE, response.getViewRequestState().getDescription());
			properties.put(Constants.PUSH_PARAM_REQUIRED_SUM, response.getRequiredSum());
			properties.put(Constants.PUSH_PARAM_RECCOMEND_SUM, response.getReccomendSum());
			properties.put(Constants.PUSH_PARAM_FULL_NAME, response.getFIO());
			properties.put(Constants.PUSH_PARAM_ACCUMULATED_SUM, getAccumulatedSum(requestInfo.getAccumulatedSum(), response.getSum()));

			ResponseExternalIdImpl externalId = new ResponseExternalIdImpl(response.getExternalId());
			properties.put(Constants.PUSH_PARAM_SENDER_PHONE, externalId.getSenderPhone());

			String avatarPath = getAvatar(externalId.getSenderPhone());
			properties.put(Constants.PUSH_PARAM_AVATAR_AVAILABLE, Boolean.toString(StringHelper.isNotEmpty(avatarPath)));
			properties.put(Constants.PUSH_PARAM_AVATAR_URL, avatarPath);

			return properties;
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
	}

	private static String getAvatar(String phone) throws BusinessException
	{
		//���� ��� ������������ �� ��� ����������
		if (!PersonContext.isAvailable())
		{
			return getAdminAvatar(phone);
		}

		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		//���� ������ �� ���������, ���������� ������
		if (!personData.isIncognito())
		{
			return getClientAvatar(personData.getAvatarInfoByType(AvatarType.SMALL));
		}

		return StringUtils.EMPTY;
	}

	private static String getClientAvatar(AvatarInfo avatarInfo)
	{
		if (avatarInfo == null || avatarInfo.getImageInfo() == null)
		{
			return StringUtils.EMPTY;
		}
		return avatarInfo.getImageInfo().getPath();
	}

	private static String getAdminAvatar(String phone) throws BusinessException
	{
		return avatarJurnalService.getAvatar(phone);
	}

	private static BigDecimal getAccumulatedSum(BigDecimal accumulatedSum, BigDecimal sum)
	{
		if (accumulatedSum == null)
		{
			return BigDecimal.ZERO;
		}
		if (sum == null)
		{
			return accumulatedSum;
		}
		return accumulatedSum.subtract(sum);
	}

	private static Map<String, Object> fillExecutedPushProperties(FundSenderResponse response) throws BusinessException, BusinessLogicException
	{
		Map<String, Object> properties = fillRejectPushProperties(response);

		properties.put(Constants.PUSH_PARAM_SUM, response.getSum());

		return properties;
	}

	private static RequestInfo getRequestInfo(String externalId) throws BusinessLogicException, BusinessException
	{
		try
		{
			FundMultiNodeService fundMultiNodeService = GateSingleton.getFactory().service(FundMultiNodeService.class);
			return fundMultiNodeService.getRequestInfo(externalId);
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
		catch (GateLogicException e)
		{
			throw new BusinessLogicException(e.getMessage(), e);
		}
	}
}
