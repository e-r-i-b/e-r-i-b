package com.rssl.phizic.asfilial.listener;

import com.rssl.auth.csa.back.servises.nodes.CreateProfileNodeMode;
import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.CSAResponseConstants;
import com.rssl.auth.csa.wsclient.exceptions.UserNotFoundException;
import com.rssl.phizic.gate.csa.UserInfo;
import com.rssl.phizic.ApplicationAutoRefreshConfig;
import com.rssl.phizic.asfilial.listener.generated.*;
import com.rssl.phizic.business.ermb.migration.list.task.migration.asfilial.ASFilialReturnCode;
import com.rssl.phizic.common.types.csa.ProfileType;
import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.apache.axis.client.Stub;
import org.w3c.dom.Element;

import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Сервис прокси-листенера для обработки запросов от АС Филиал
 * @author gladishev
 * @ created 04.12.13
 * @ $Author$
 * @ $Revision$
 */
public class ASFilialProxyService extends AsFilialServiceBase
{
	private final ASFilialServiceBaseHelper asHelper = new ASFilialServiceBaseHelper();

	public QueryProfileRsType queryProfile(QueryProfileRqType parameters) throws RemoteException
	{
		QueryProfileRsType result = new QueryProfileRsType();
		result.setRqTm(parameters.getRqTm());
		result.setRqUID(parameters.getRqUID());
		result.setOperUID(parameters.getOperUID());

		IdentityType clientInd = parameters.getClientIdentity();
		asHelper.removeTbLeadingZeros(clientInd);
		Long nodeId = null;
		try
		{
			nodeId = getProfileNode(clientInd, parameters.isCreateIfNone());
		}
		catch (UserNotFoundException ignore)
		{
			StatusType status = new StatusType();
			asHelper.setClientNotFoundMsg(status, clientInd);
			result.setStatus(status);
			return result;
		}
		catch (GateLogicException gle)
		{
			result.setStatus(buildStatusType(gle, ASFilialReturnCode.BUSINES_ERROR));
			return result;
		}
		catch (GateException ge)
		{
			result.setStatus(buildStatusType(ge, ASFilialReturnCode.TECHNICAL_ERROR));
			return result;
		}

		try
		{
			//редиректим запрос в блок
			return getStub(nodeId).queryProfile(parameters);
		}
		catch (Exception e)
		{
			result.setStatus(buildStatusType(e, ASFilialReturnCode.TECHNICAL_ERROR));
			return result;
		}
	}

	public UpdateProfileRsType updateProfile(UpdateProfileRqType parameters) throws RemoteException
	{
		UpdateProfileRsType result = new UpdateProfileRsType();
		result.setRqTm(parameters.getRqTm());
		result.setRqUID(parameters.getRqUID());
		result.setOperUID(parameters.getOperUID());

		IdentityType clientInd = parameters.getClientIdentity();
		asHelper.removeTbLeadingZeros(clientInd);
		try
		{
			return getStub(getProfileNode(clientInd, false)).updateProfile(parameters);
		}
		catch (UserNotFoundException ignore)
		{
			StatusType status = new StatusType();
			asHelper.setClientNotFoundMsg(status, clientInd);
			result.setStatus(status);
			return result;
		}
		catch (GateLogicException gle)
		{
			result.setStatus(buildStatusType(gle, ASFilialReturnCode.BUSINES_ERROR));
			return result;
		}
		catch (Exception e)
		{
			result.setStatus(buildStatusType(e, ASFilialReturnCode.TECHNICAL_ERROR));
			return result;
		}
	}

	public ConfirmPhoneHolderRsType confirmPhoneHolder(ConfirmPhoneHolderRqType parameters) throws RemoteException
	{
		ConfirmPhoneHolderRsType result = new ConfirmPhoneHolderRsType();
		result.setRqTm(parameters.getRqTm());
		result.setRqUID(parameters.getRqUID());
		result.setOperUID(parameters.getOperUID());
		StatusType status = new StatusType();
		status.setStatusCode(ASFilialReturnCode.OK.toValue());

		Map<Long, List<PhoneNumberType>> phoneNodesMap = null;

		try
		{
			phoneNodesMap = getProfileNode(parameters.getPhones());
		}
		catch (GateLogicException gle)
		{
			result.setStatus(buildStatusType(gle, ASFilialReturnCode.BUSINES_ERROR));
			return result;
		}
		catch (GateException e)
		{
			result.setStatus(buildStatusType(e, ASFilialReturnCode.TECHNICAL_ERROR));
			return result;
		}


		for (Long nodeId : phoneNodesMap.keySet())
		{
			try
			{
				List<PhoneNumberType> phoneNumberTypes = phoneNodesMap.get(nodeId);
				getStub(nodeId).confirmPhoneHolder(new ConfirmPhoneHolderRqType(parameters.getRqUID(), parameters.getRqTm(), parameters.getOperUID(),
						                            parameters.getSName(), phoneNumberTypes.toArray(new PhoneNumberType[phoneNumberTypes.size()])));
			}
			catch (GateLogicException gle)
			{
				asHelper.setStatus(status, gle, ASFilialReturnCode.BUSINES_ERROR);
			}
			catch (Exception e)
			{
				asHelper.setStatus(status, e, ASFilialReturnCode.TECHNICAL_ERROR);
			}
		}
		result.setStatus(status);
		return result;
	}

	private ASFilialInfoService getStub(Long nodeId) throws Exception
	{
		ASFilialInfoServiceImplLocator locator = new ASFilialInfoServiceImplLocator();
		ASFilialInfoServiceSoapBindingStub stub = (ASFilialInfoServiceSoapBindingStub) locator.getASFilialInfoServicePort();
		ASFilialConfig config = ConfigFactory.getConfig(ASFilialConfig.class);
		stub._setProperty(Stub.ENDPOINT_ADDRESS_PROPERTY, config.getRedirectServiceUrl(nodeId));
		return stub;
	}

	private Map<Long, List<PhoneNumberType>> getProfileNode(PhoneNumberType[] phones) throws GateLogicException, GateException
	{
//		Map<Long, List<PhoneNumberType>> result = new HashMap<Long, List<PhoneNumberType>>();
//		try
//		{
//			for (PhoneNumberType phone : phones)
//			{
//				Document document = CSABackRequestHelper.sendFindProfileNodeByPhoneRq(phone.getPhoneNumberN());
//				long nodeId = Long.decode(XmlHelper.getSimpleElementValue(document.getDocumentElement(), CSAResponseConstants.NODE_ID_TAG));
//				List<PhoneNumberType> list = result.get(nodeId);
//				if (list == null)
//				{
//					list = new ArrayList<PhoneNumberType>();
//					result.put(nodeId, list);
//				}
//				list.add(phone);
//			}
//
//			return result;
//		}
//		catch (BackLogicException le)
//		{
//			throw new GateLogicException(le);
//		}
//		catch (BackException be)
//		{
//			throw new GateException(be);
//		}

		return Collections.singletonMap(
				ConfigFactory.getConfig(ApplicationAutoRefreshConfig.class).getNodeNumber(),
				Arrays.asList(phones)
		);
	}

	private Long getProfileNode(IdentityType clientInd, boolean needCreateProfile) throws UserNotFoundException, GateLogicException, GateException
	{
		UserInfo userInfo = asHelper.createCsaUserInfo(clientInd);
		try
		{
			Element docElement = CSABackRequestHelper.sendFindProfileNodeByUserInfoRq(userInfo, needCreateProfile, CreateProfileNodeMode.CREATION_ALLOWED_FOR_MAIN_NODE).getDocumentElement();
			ProfileType profileType = ProfileType.valueOf(XmlHelper.getSimpleElementValue(docElement, CSAResponseConstants.PROFILE_TYPE_TAG));
			if (profileType == ProfileType.TEMPORARY)
				throw new GateException("Создался временный пользователь. Для ASFilialListener создавать такого пользователя нельзя");
			return Long.decode(XmlHelper.getSimpleElementValue(docElement, CSAResponseConstants.NODE_ID_TAG));
		}
		catch (UserNotFoundException unfe)
		{
			throw unfe;
		}
		catch (LogicException le)
		{
			throw new GateLogicException(le);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	private StatusType buildStatusType(Throwable e, ASFilialReturnCode code)
	{
		StatusType status = new StatusType();
		asHelper.setStatus(status, e, code);
		return status;
	}
}
