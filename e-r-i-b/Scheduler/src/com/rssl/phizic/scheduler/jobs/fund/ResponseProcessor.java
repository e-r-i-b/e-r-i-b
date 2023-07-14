package com.rssl.phizic.scheduler.jobs.fund;

import com.rssl.auth.csa.utils.UserInfoHelper;
import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.CSABackResponseSerializer;
import com.rssl.auth.csa.wsclient.CSAResponseConstants;
import com.rssl.auth.csa.wsclient.RequestConstants;
import com.rssl.auth.csa.wsclient.exceptions.BackException;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.auth.csa.wsclient.responses.ConnectorInfo;
import com.rssl.phizic.gate.csa.UserInfo;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.DepartmentsUtils;
import com.rssl.phizic.business.fund.FundInfoImpl;
import com.rssl.phizic.business.fund.FundPushInfo;
import com.rssl.phizic.business.fund.initiator.FundInitiatorResponse;
import com.rssl.phizic.business.fund.initiator.FundInitiatorResponseService;
import com.rssl.phizic.business.fund.initiator.FundRequest;
import com.rssl.phizic.business.persons.*;
import com.rssl.phizic.business.profile.images.AvatarInfo;
import com.rssl.phizic.business.profile.images.AvatarService;
import com.rssl.phizic.business.profile.images.AvatarType;
import com.rssl.phizic.common.types.fund.Constants;
import com.rssl.phizic.common.types.transmiters.Triplet;
import com.rssl.phizic.gate.clients.GUID;
import com.rssl.phizic.gate.fund.FundInfo;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.messaging.push.FundPushHelper;
import com.rssl.phizic.person.ClientData;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;

import java.util.*;
import javax.xml.transform.TransformerException;

/**
 * @author osminin
 * @ created 05.12.14
 * @ $Author$
 * @ $Revision$
 *
 * ���������� ������� � ����� ����������
 */
public class ResponseProcessor
{
	private static final Log log = PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_SCHEDULER);

	private static PersonService personService = new PersonService();
	private static FundInitiatorResponseService responseService = new FundInitiatorResponseService();

	private GUIDImpl initiatorGuid;
	private String initiatorPhones;
	private String avatarPath = StringUtils.EMPTY;
	private FundRequest request;
	private List<FundInitiatorResponse> responses;

	/**
	 * ctor
	 * @param request ������ �� ���� �������
	 * @param responses ������ � ����� ����������
	 */
	public ResponseProcessor(FundRequest request, List<FundInitiatorResponse> responses) throws BusinessException
	{
		if (request == null)
		{
			throw new BusinessException("����� �� ���� ������� �� ����� ���� null.");
		}
		if (responses == null)
		{
			throw new BusinessException("������ ������� �� ����� ���� null.");
		}

		this.request = request;
		this.responses = new ArrayList<FundInitiatorResponse>(responses);

		initializeInitiatorParameters(request);
	}

	/**
	 * ���������� ������ � ����� ����������
	 * @param pushInfoMap ���� ���������� � ���-������������
	 * @param synchronizeMap ���� ���������� � ������ ������� � ������� ������
	 */
	public void process(Map<Long, List<FundInfo>> synchronizeMap, Map<String, FundPushInfo> pushInfoMap)
	{
		for (FundInitiatorResponse response : responses)
		{
			processResponse(response, synchronizeMap, pushInfoMap);
		}
	}

	private void initializeInitiatorParameters(FundRequest request) throws BusinessException
	{
		//�������� ���������� �� ������ �� �������
		LightPerson initiator = personService.getLightPersonByLogin(request.getLoginId());
		if (initiator == null)
		{
			throw new BusinessException("�� ������ ������������ � loginId " + request.getLoginId());
		}

		//������ ����
		initiatorGuid = new GUIDImpl();
		initiatorGuid.setFirstName(initiator.getFirstName());
		initiatorGuid.setPatrName(initiator.getPatrName());
		initiatorGuid.setSurName(initiator.getSurName());
		initiatorGuid.setBirthDay(initiator.getBirthDay());
		initiatorGuid.setPassport(PersonHelper.getPersonPassportWay(initiator.getId()).getDocumentSeries());
		initiatorGuid.setTb(DepartmentsUtils.getTbByDepartmentId(initiator.getDepartmentId()));

		//�������� ��� �������� ����������
		initiatorPhones = request.getInitiatorPhones();

		initializeInitiatorAvatar(request.getLoginId());
	}

	private void initializeInitiatorAvatar(Long loginId)
	{
		try
		{
			UserInfo userInfo = new UserInfo(initiatorGuid.getTb(), initiatorGuid.getFirstName(), initiatorGuid.getSurName(), initiatorGuid.getPatrName(), initiatorGuid.getPassport(), initiatorGuid.getBirthDay());
			userInfo.setTb(initiatorGuid.getTb());

			Document response = CSABackRequestHelper.sendGetIncognitoSettingRq(userInfo);
			boolean isIncognito =  Boolean.parseBoolean(XmlHelper.getSimpleElementValue(response.getDocumentElement(), RequestConstants.INCOGNITO_SETTING_PARAM_NAME));

			if (!isIncognito)
			{
				AvatarInfo avatarInfo = AvatarService.get().getAvatarInfoByLoginId(AvatarType.SMALL, loginId);
				if (avatarInfo != null && avatarInfo.getImageInfo() != null)
				{
					avatarPath = avatarInfo.getImageInfo().getPath();
				}
			}
		}
		catch (BackException e)
		{
			log.error("������ ��� ��������� �������� ��������� ��� ������������ � loginId " + loginId, e);
		}
		catch (BackLogicException e)
		{
			log.error(e.getMessage(), e);
		}
		catch (BusinessException e)
		{
			log.error("������ ��� ��������� �������� ��������� ��� ������������ � loginId " + loginId, e);
		}
		catch (Exception e)
		{
			log.error("������ ��� ������� ���������� ������ ��� ������������ � loginId " + loginId, e);
		}
	}

	/**
	 * ���������� ����� � ����� ����������
	 * @param response �����
	 */
	private void processResponse(FundInitiatorResponse response, Map<Long, List<FundInfo>> synchronizeMap, Map<String, FundPushInfo> pushInfoMap)
	{
		UserInfo userInfo = UserInfoHelper.getByPhoneWithoutTb(response.getPhone());
		if (userInfo == null)
		{
			log.error("���������� � ������������ �� ������ �������� " + response.getPhone() + " �� �������.");
			return;
		}

		//��������� ����, ���� ���������� ����� ��������� ���������� ��� ������
		fillFundMaps(response, userInfo, synchronizeMap, pushInfoMap);
	}

	private void fillFundMaps(FundInitiatorResponse response, UserInfo userInfo, Map<Long, List<FundInfo>> synchronizeMap, Map<String, FundPushInfo> pushInfoMap)
	{
		try
		{
			//���������� ������ � ��� � ���/��/��� ���������� ������� � �� ��������� �������.
			userInfo.setTb(initiatorGuid.getTb());

			//����������� �� ��� ���������� � ���������� ������� �� ���� �������
			Document document = CSABackRequestHelper.sendFindFundSenderInfo(userInfo);
			String nodeIdStr = XmlHelper.getSimpleElementValue(document.getDocumentElement(), CSAResponseConstants.NODE_ID_TAG);

			//����� ����� �� �������� - ������ �� ������
			if (StringHelper.isEmpty(nodeIdStr))
			{
				return;
			}

			//��������� ���������� � ������������ ��������� ����������� �����
			String userTb = XmlHelper.getSimpleElementValue(document.getDocumentElement(), CSAResponseConstants.TB_TAG);
			userInfo.setTb(DepartmentsUtils.getTbBySynonymAndIdentical(userTb));

			//��������� ��� �����������
			updateSenderFIO(response.getExternalId(), userInfo);

			//��������� ���� ��� ������������� �������� �� ������
			fillSynchronizeMap(response, userInfo, Long.parseLong(nodeIdStr), synchronizeMap);

			//��������� ���� ��� ��� �����������
			fillPushInfoMap(response, userInfo, document, pushInfoMap);
		}
		catch (BackException e)
		{
			log.error("������ ��� ��������� ���������� �� ���� ������������.", e);
		}
		catch (BackLogicException e)
		{
			log.error(e.getMessage(), e);
		}
		catch (Exception e)
		{
			log.error("������ ��� ��������� ������ � ����� ���������� � id " + response.getId(), e);
		}
	}

	private void fillPushInfoMap(FundInitiatorResponse response, UserInfo userInfo, Document document, Map<String, FundPushInfo> pushInfoMap)
	{
		try
		{
			//�������� ����������� ���������� ��� �������� ���-����������� ���������� �������
			List<ConnectorInfo> connectorInfoList = CSABackResponseSerializer.getConnectorInfos(document);
			//��������� ������
			List<Triplet<String, String, String>> triplets = FundPushHelper.fillTripletList(connectorInfoList);
			//���� ������ ����� null, ������ ���-����������� ���������
			if (triplets != null)
			{
				//������ ������� ��� �������� ������ � �������
				ClientData clientData = new ClientDataImpl(userInfo);
				//���� ������ ����� ������, ������ ��������� ������ qpe_addresses_tab, ����� ������ qpe_devices_tab (��. CHG089693)
				List<String> phones = CollectionUtils.isEmpty(triplets) ? Collections.singletonList(response.getPhone()) : Collections.<String>emptyList();
				//������ ���� � ����
				FundPushInfo fundPushInfo = new FundPushInfo(phones, clientData, triplets, fillProperties(response), Constants.PUSH_CREATED_MESSAGE_KEY);
	            //��������� � ����
				pushInfoMap.put(response.getExternalId(), fundPushInfo);
			}
		}
		catch (TransformerException e)
		{
			log.error("������ ��� ������������ ���������� � ����������� ����������.", e);
		}
		catch (Exception e)
		{
			log.error("������ ��� ���������� ���������� ��� ��� ����������� �� ������ � id " + response.getId(), e);
		}
	}

	private Map<String, Object> fillProperties(FundInitiatorResponse response)
	{
		Map<String, Object> properties = new HashMap<String, Object>();

		properties.put(Constants.PUSH_PARAM_REQUEST_ID, response.getExternalId());
		properties.put(Constants.PUSH_PARAM_FULL_NAME, getInitiatorFullName());
		properties.put(Constants.PUSH_PARAM_PHONES, initiatorPhones);
		properties.put(Constants.PUSH_PARAM_CREATED_DATE, request.getCreatedDate());
		properties.put(Constants.PUSH_PARAM_MESSAGE, request.getMessage());
		properties.put(Constants.PUSH_PARAM_STATE, request.getViewState().getDescription());
		properties.put(Constants.PUSH_PARAM_REQUIRED_SUM, request.getRequiredSum());
		properties.put(Constants.PUSH_PARAM_RECCOMEND_SUM, request.getReccomendSum());
		properties.put(Constants.PUSH_PARAM_CLOSED_DATE, request.getClosedDate());
		properties.put(Constants.PUSH_PARAM_AVATAR_AVAILABLE, Boolean.toString(StringHelper.isNotEmpty(avatarPath)));
		properties.put(Constants.PUSH_PARAM_AVATAR_URL, StringHelper.getEmptyIfNull(avatarPath));

		return properties;
	}

	private String getInitiatorFullName()
	{
		StringBuilder builder = new StringBuilder();

		builder.append(initiatorGuid.getFirstName()).append(" ");
		if (StringHelper.isNotEmpty(initiatorGuid.getPatrName()))
		{
			builder.append(initiatorGuid.getPatrName()).append(" ");
		}
		builder.append(initiatorGuid.getSurName().substring(0, 1)).append(".");

		return builder.toString();
	}

	private void fillSynchronizeMap(FundInitiatorResponse response, UserInfo userInfo, Long nodeId, Map<Long, List<FundInfo>> synchronizeMap)
	{
		//�������� ������ ���������� � ������ �������, ������� ���������� ��������� � ����
		List<FundInfo> fundInfoList = synchronizeMap.get(nodeId);
		if (fundInfoList == null)
		{
			//������� ������, ���� ��� � ������ ���� �� ����������
			fundInfoList = new ArrayList<FundInfo>(responses.size());
			synchronizeMap.put(nodeId, fundInfoList);
		}

		//��������� ���������� � ����� �������
		GUID senderGuid = new GUIDImpl(userInfo.getSurname(), userInfo.getFirstname(), userInfo.getPatrname(), userInfo.getPassport(), userInfo.getBirthdate(), userInfo.getTb());
		FundInfo fundInfo = new FundInfoImpl(request, initiatorGuid, senderGuid, response.getExternalId(), initiatorPhones);

		fundInfoList.add(fundInfo);
	}

	private void updateSenderFIO(String externalId, UserInfo userInfo)
	{
		try
		{
			responseService.updateFIO(userInfo.getFirstname(), userInfo.getSurname(), userInfo.getPatrname(), externalId);
		}
		catch (Exception e)
		{
			log.error("������ ��� ���������� ��� ����������� ��� ������ � externalId " + externalId, e);
		}
	}
}
