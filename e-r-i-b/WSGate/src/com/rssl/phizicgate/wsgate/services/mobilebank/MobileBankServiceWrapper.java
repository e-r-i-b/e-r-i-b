package com.rssl.phizicgate.wsgate.services.mobilebank;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizgate.common.ws.WSRequestHandlerUtil;
import com.rssl.phizgate.mobilebank.GatePaymentTemplateImpl;
import com.rssl.phizgate.mobilebank.MobileBankConfig;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.ermb.MBKRegistration;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.GateWrapperTimeOutException;
import com.rssl.phizic.gate.exceptions.TemporalGateException;
import com.rssl.phizic.gate.impl.TimeoutHttpTransport;
import com.rssl.phizic.gate.mobilebank.*;
import com.rssl.phizic.gate.profile.MBKPhone;
import com.rssl.phizic.utils.BeanHelper;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.utils.PhoneNumber;
import com.rssl.phizicgate.wsgate.WSGateException;
import com.rssl.phizicgate.wsgate.WSGateLogicException;
import com.rssl.phizicgate.wsgate.WSTemporalGateException;
import com.rssl.phizicgate.wsgate.services.JAXRPCClientSideServiceBase;
import com.rssl.phizicgate.wsgate.services.mobilebank.generated.MobileBankServiceImpl_Impl;
import com.rssl.phizicgate.wsgate.services.mobilebank.generated.MobileBankService_Stub;
import com.rssl.phizicgate.wsgate.services.types.CardImpl;
import com.rssl.phizicgate.wsgate.services.types.UserInfoImpl;
import com.sun.xml.rpc.client.ClientTransportException;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.*;

/**
 * @author Jatsky
 * @ created 19.05.15
 * @ $Author$
 * @ $Revision$
 */

public class MobileBankServiceWrapper extends JAXRPCClientSideServiceBase<MobileBankService_Stub> implements com.rssl.phizic.gate.mobilebank.MobileBankService
{
	public MobileBankServiceWrapper(GateFactory factory)
	{
		super(factory);
		String url = ConfigFactory.getConfig(MobileBankConfig.class).getMobilebankWebServiceUrl();
		MobileBankServiceImpl_Impl service = new MobileBankServiceImpl_Impl();
		WSRequestHandlerUtil.addWSRequestHandlerToService(service);
		initStub((MobileBankService_Stub) service.getMobileBankServicePort(), url);
	}

	public GroupResult<String, List<MobileBankRegistration>> getRegistrations(Boolean alternative, String... cardNumbers)
	{
		try
		{

			com.rssl.phizicgate.wsgate.services.mobilebank.generated.GroupResult generatedGroupResult = getStub().getRegistrations(alternative, cardNumbers);
			GroupResult<String, List<MobileBankRegistration>> gateGroupResult = new GroupResult<String, List<MobileBankRegistration>>();
			BeanHelper.copyPropertiesWithDifferentTypes(gateGroupResult, generatedGroupResult, MobileBankServiceTypesCorrelation.types);
			return gateGroupResult;
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				return GroupResultHelper.getOneErrorResult(cardNumbers, new TemporalGateException(e));
			}
			return GroupResultHelper.getOneErrorResult(cardNumbers, new GateException(e));
		}
		catch (RemoteException ex)
		{
			if (ex.getMessage().contains(TimeoutHttpTransport.SOCKET_TIMEOUT_EXCEPTION))
				return GroupResultHelper.getOneErrorResult(cardNumbers, new GateWrapperTimeOutException(GateWrapperTimeOutException.GATE_WRAPPER_TIMEOUT_DEFAULT_MESSAGE, ex));

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				return GroupResultHelper.getOneErrorResult(cardNumbers, new WSTemporalGateException(ex));
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				return GroupResultHelper.getOneErrorResult(cardNumbers, new WSGateLogicException(ex));
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				return GroupResultHelper.getOneErrorResult(cardNumbers, new WSGateException(ex));
			}
			return GroupResultHelper.getOneErrorResult(cardNumbers, new GateException(ex));
		}
		catch (Exception ex)
		{
			throw new RuntimeException(ex);
		}
	}

	public List<MobileBankRegistration3> getRegistrations3(String cardNumber) throws GateException, GateLogicException
	{
		try
		{
			List<com.rssl.phizicgate.wsgate.services.mobilebank.generated.MobileBankRegistration3> generatedMobileBankRegistrations = getStub().getRegistrations3(cardNumber);

			List<MobileBankRegistration3> mobileBankRegistrations = new ArrayList<MobileBankRegistration3>(generatedMobileBankRegistrations.size());

			BeanHelper.copyPropertiesWithDifferentTypes(mobileBankRegistrations, generatedMobileBankRegistrations, MobileBankServiceTypesCorrelation.types);

			return mobileBankRegistrations;
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				throw new TemporalGateException(e);
			}
			throw new GateException(e);
		}
		catch (RemoteException ex)
		{
			checkTimeoutException(ex);

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateException(ex);
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicException(ex);
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				throw new WSGateException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public List<MobileBankRegistration> getRegistrationsPack(Boolean alternative, String... cardNumbers) throws GateException, GateLogicException
	{
		try
		{

			List<com.rssl.phizicgate.wsgate.services.mobilebank.generated.MobileBankRegistration> generatedMobileBankRegistrations = getStub().getRegistrationsPack(alternative, cardNumbers);
			List<MobileBankRegistration> mobileBankRegistrations = new ArrayList<MobileBankRegistration>(generatedMobileBankRegistrations.size());
			BeanHelper.copyPropertiesWithDifferentTypes(mobileBankRegistrations, generatedMobileBankRegistrations, MobileBankServiceTypesCorrelation.types);
			return mobileBankRegistrations;
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				throw new TemporalGateException(e);
			}
			throw new GateException(e);
		}
		catch (RemoteException ex)
		{
			checkTimeoutException(ex);

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateException(ex);
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicException(ex);
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				throw new WSGateException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public GroupResult<MobileBankCardInfo, List<MobileBankTemplate>> getTemplates(Long count, MobileBankCardInfo... cardInfo)
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.mobilebank.generated.MobileBankCardInfo[] generatedMobileBankCardInfo = new com.rssl.phizicgate.wsgate.services.mobilebank.generated.MobileBankCardInfo[cardInfo.length];
			BeanHelper.copyPropertiesWithDifferentTypes(generatedMobileBankCardInfo, cardInfo, MobileBankServiceTypesCorrelation.types);

			com.rssl.phizicgate.wsgate.services.mobilebank.generated.GroupResult generatedGroupResult = getStub().getTemplates(count, generatedMobileBankCardInfo);
			GroupResult<MobileBankCardInfo, List<MobileBankTemplate>> gateGroupResult = new GroupResult<MobileBankCardInfo, List<MobileBankTemplate>>();
			BeanHelper.copyPropertiesWithDifferentTypes(gateGroupResult, generatedGroupResult, MobileBankServiceTypesCorrelation.types);
			return gateGroupResult;
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				return GroupResultHelper.getOneErrorResult(cardInfo, new TemporalGateException(e));
			}
			return GroupResultHelper.getOneErrorResult(cardInfo, new GateException(e));
		}
		catch (RemoteException ex)
		{
			if (ex.getMessage().contains(TimeoutHttpTransport.SOCKET_TIMEOUT_EXCEPTION))
				return GroupResultHelper.getOneErrorResult(cardInfo, new GateWrapperTimeOutException(GateWrapperTimeOutException.GATE_WRAPPER_TIMEOUT_DEFAULT_MESSAGE, ex));

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				return GroupResultHelper.getOneErrorResult(cardInfo, new WSTemporalGateException(ex));
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				return GroupResultHelper.getOneErrorResult(cardInfo, new WSGateLogicException(ex));
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				return GroupResultHelper.getOneErrorResult(cardInfo, new WSGateException(ex));
			}
			return GroupResultHelper.getOneErrorResult(cardInfo, new GateException(ex));
		}
		catch (Exception ex)
		{
			throw new RuntimeException(ex);
		}
	}

	public void addTemplates(MobileBankCardInfo cardInfo, List<MobileBankTemplate> templates) throws GateException, GateLogicException
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.mobilebank.generated.MobileBankCardInfo generatedMobileBankCardInfo = new com.rssl.phizicgate.wsgate.services.mobilebank.generated.MobileBankCardInfo();
			BeanHelper.copyPropertiesWithDifferentTypes(generatedMobileBankCardInfo, cardInfo, MobileBankServiceTypesCorrelation.types);

			List<com.rssl.phizicgate.wsgate.services.mobilebank.generated.MobileBankTemplate> generatedMobileBankTemplate = new ArrayList<com.rssl.phizicgate.wsgate.services.mobilebank.generated.MobileBankTemplate>();
			BeanHelper.copyPropertiesWithDifferentTypes(generatedMobileBankTemplate, templates, MobileBankServiceTypesCorrelation.types);

			getStub().addTemplates(generatedMobileBankCardInfo, generatedMobileBankTemplate);
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				throw new TemporalGateException(e);
			}
			throw new GateException(e);
		}
		catch (RemoteException ex)
		{
			checkTimeoutException(ex);

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateException(ex);
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicException(ex);
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				throw new WSGateException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public void removeTemplates(MobileBankCardInfo cardInfo, List<MobileBankTemplate> templates) throws GateException, GateLogicException
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.mobilebank.generated.MobileBankCardInfo generatedMobileBankCardInfo = new com.rssl.phizicgate.wsgate.services.mobilebank.generated.MobileBankCardInfo();
			BeanHelper.copyPropertiesWithDifferentTypes(generatedMobileBankCardInfo, cardInfo, MobileBankServiceTypesCorrelation.types);

			List<com.rssl.phizicgate.wsgate.services.mobilebank.generated.MobileBankTemplate> generatedMobileBankTemplate = new ArrayList<com.rssl.phizicgate.wsgate.services.mobilebank.generated.MobileBankTemplate>();
			BeanHelper.copyPropertiesWithDifferentTypes(generatedMobileBankTemplate, templates, MobileBankServiceTypesCorrelation.types);

			getStub().removeTemplates(generatedMobileBankCardInfo, generatedMobileBankTemplate);
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				throw new TemporalGateException(e);
			}
			throw new GateException(e);
		}
		catch (RemoteException ex)
		{
			checkTimeoutException(ex);

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateException(ex);
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicException(ex);
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				throw new WSGateException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public void sendSMS(String text, String textToLog, int id, String phone) throws GateException, GateLogicException
	{
		try
		{
			getStub().sendSMS(text, textToLog, id, phone);
		}
		catch (RemoteException ex)
		{
			checkTimeoutException(ex);

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateException(ex);
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicException(ex);
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				throw new WSGateException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public Map<String, SendMessageError> sendSMSWithIMSICheck(MessageInfo messageInfo, String... toPhones) throws GateException, GateLogicException
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.mobilebank.generated.MessageInfo generatedMessageInfo = new com.rssl.phizicgate.wsgate.services.mobilebank.generated.MessageInfo();
			BeanHelper.copyPropertiesWithDifferentTypes(generatedMessageInfo, messageInfo, MobileBankServiceTypesCorrelation.types);

			Map generatedErrors = getStub().sendSMSWithIMSICheck(generatedMessageInfo, toPhones);
			Map<String, SendMessageError> gateGroupResult = new HashMap<String, SendMessageError>();
			for (String s : gateGroupResult.keySet())
			{
				gateGroupResult.put(s, Enum.valueOf(SendMessageError.class, String.valueOf(generatedErrors.get(s))));
			}
			BeanHelper.copyPropertiesWithDifferentTypes(gateGroupResult, generatedErrors, MobileBankServiceTypesCorrelation.types);
			return gateGroupResult;
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				throw new TemporalGateException(e);
			}
			throw new GateException(e);
		}
		catch (RemoteException ex)
		{
			checkTimeoutException(ex);

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateException(ex);
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicException(ex);
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				throw new WSGateException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public Card getCardByPhone(Client client, String phone) throws GateException, GateLogicException
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.mobilebank.generated.Client generatedClient = new com.rssl.phizicgate.wsgate.services.mobilebank.generated.Client();

			BeanHelper.copyPropertiesWithDifferentTypes(generatedClient, client, MobileBankServiceTypesCorrelation.types);

			if (generatedClient.getId() != null)
			{
				String encoded = encodeData(generatedClient.getId());
				generatedClient.setId(encoded);
			}

			com.rssl.phizicgate.wsgate.services.mobilebank.generated.Card generatedCard = getStub().getCardByPhone(generatedClient, phone);
			Card gateCard = new CardImpl();

			BeanHelper.copyPropertiesWithDifferentTypes(gateCard, generatedCard, MobileBankServiceTypesCorrelation.types);
			return gateCard;
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				throw new TemporalGateException(e);
			}
			throw new GateException(e);
		}
		catch (RemoteException ex)
		{
			checkTimeoutException(ex);

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateException(ex);
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicException(ex);
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				throw new WSGateException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public UserInfo getClientByPhone(String phone) throws GateException, GateLogicException
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.mobilebank.generated.UserInfo generatedUserInfo = getStub().getClientByPhone(phone);
			UserInfo gateUserInfo = new UserInfoImpl();

			BeanHelper.copyPropertiesWithDifferentTypes(gateUserInfo, generatedUserInfo, MobileBankServiceTypesCorrelation.types);
			return gateUserInfo;
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				throw new TemporalGateException(e);
			}
			throw new GateException(e);
		}
		catch (RemoteException ex)
		{
			checkTimeoutException(ex);

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateException(ex);
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicException(ex);
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				throw new WSGateException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public QuickServiceStatusCode setQuickServiceStatus(String phoneNumber, QuickServiceStatusCode status) throws GateException, GateLogicException
	{
		try
		{
			String generatedQuickServiceStatusCode = getStub().setQuickServiceStatus(phoneNumber, status.name());

			return Enum.valueOf(QuickServiceStatusCode.class, generatedQuickServiceStatusCode);
		}
		catch (RemoteException ex)
		{
			checkTimeoutException(ex);

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateException(ex);
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicException(ex);
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				throw new WSGateException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public QuickServiceStatusCode getQuickServiceStatus(String phoneNumber) throws GateException, GateLogicException
	{
		try
		{
			String generatedQuickServiceStatusCode = getStub().getQuickServiceStatus(phoneNumber);

			return Enum.valueOf(QuickServiceStatusCode.class, generatedQuickServiceStatusCode);
		}
		catch (RemoteException ex)
		{
			checkTimeoutException(ex);

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateException(ex);
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicException(ex);
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				throw new WSGateException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public void addRegistration(Client client, String cardNumber, String phoneNumber, String netType, MobileBankTariff packetType) throws GateException, GateLogicException
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.mobilebank.generated.Client generatedClient = new com.rssl.phizicgate.wsgate.services.mobilebank.generated.Client();

			BeanHelper.copyPropertiesWithDifferentTypes(generatedClient, client, MobileBankServiceTypesCorrelation.types);

			if (generatedClient.getId() != null)
			{
				String encoded = encodeData(generatedClient.getId());
				generatedClient.setId(encoded);
			}

			getStub().addRegistration(generatedClient, cardNumber, phoneNumber, netType, packetType.name());
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				throw new TemporalGateException(e);
			}
			throw new GateException(e);
		}
		catch (RemoteException ex)
		{
			checkTimeoutException(ex);

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateException(ex);
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicException(ex);
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				throw new WSGateException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public UserInfo getClientByCardNumber(String cardNumber) throws GateException, GateLogicException
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.mobilebank.generated.UserInfo generatedUserInfo = getStub().getClientByCardNumber(cardNumber);
			UserInfo gateUserInfo = new UserInfoImpl();

			BeanHelper.copyPropertiesWithDifferentTypes(gateUserInfo, generatedUserInfo, MobileBankServiceTypesCorrelation.types);
			return gateUserInfo;
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				throw new TemporalGateException(e);
			}
			throw new GateException(e);
		}
		catch (RemoteException ex)
		{
			checkTimeoutException(ex);

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateException(ex);
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicException(ex);
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				throw new WSGateException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public UserInfo getClientByLogin(String userId) throws GateException, GateLogicException
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.mobilebank.generated.UserInfo generatedUserInfo = getStub().getClientByLogin(userId);
			UserInfo gateUserInfo = new UserInfoImpl();

			BeanHelper.copyPropertiesWithDifferentTypes(gateUserInfo, generatedUserInfo, MobileBankServiceTypesCorrelation.types);
			return gateUserInfo;
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				throw new TemporalGateException(e);
			}
			throw new GateException(e);
		}
		catch (RemoteException ex)
		{
			checkTimeoutException(ex);

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateException(ex);
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicException(ex);
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				throw new WSGateException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public String getMobileContact(String phoneNumbersToFind) throws GateException, GateLogicException
	{
		try
		{
			return getStub().getMobileContact(phoneNumbersToFind);
		}
		catch (RemoteException ex)
		{
			checkTimeoutException(ex);

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateException(ex);
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicException(ex);
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				throw new WSGateException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public void sendOfferMessageSMS(String text, String textLog, Long id, String phone) throws GateException, GateLogicException
	{
		try
		{
			getStub().sendOfferMessageSMS(text, textLog, id, phone);
		}
		catch (RemoteException ex)
		{
			checkTimeoutException(ex);

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateException(ex);
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicException(ex);
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				throw new WSGateException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public List<AcceptInfo> getOfferConfirm() throws GateException, GateLogicException
	{
		try
		{
			List<com.rssl.phizicgate.wsgate.services.mobilebank.generated.AcceptInfo> generatedAcceptInfo = getStub().getOfferConfirm();
			List<AcceptInfo> gateAcceptInfo = new ArrayList<AcceptInfo>();

			BeanHelper.copyPropertiesWithDifferentTypes(gateAcceptInfo, generatedAcceptInfo, MobileBankServiceTypesCorrelation.types);
			return gateAcceptInfo;
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				throw new TemporalGateException(e);
			}
			throw new GateException(e);
		}
		catch (RemoteException ex)
		{
			checkTimeoutException(ex);

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateException(ex);
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicException(ex);
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				throw new WSGateException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public void sendOfferQuit(Long id) throws GateException, GateLogicException
	{
		try
		{
			getStub().sendOfferQuit(id);
		}
		catch (RemoteException ex)
		{
			checkTimeoutException(ex);

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateException(ex);
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicException(ex);
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				throw new WSGateException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public Set<String> getCardsByPhone(String phone) throws GateException, GateLogicException
	{
		try
		{
			return getStub().getCardsByPhone(phone);
		}
		catch (RemoteException ex)
		{
			checkTimeoutException(ex);

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateException(ex);
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicException(ex);
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				throw new WSGateException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public Set<String> getCardsByPhoneViaReportDB(String phone) throws GateException, GateLogicException
	{
		try
		{
			return getStub().getCardsByPhoneViaReportDB(phone);
		}
		catch (RemoteException ex)
		{
			checkTimeoutException(ex);

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateException(ex);
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicException(ex);
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				throw new WSGateException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public BeginMigrationResult beginMigrationErmb(Set<MbkCard> cards, Set<String> phoneMigrateNumbers, Set<String> phoneDeleteNumbers, MigrationType migrationType) throws GateException, GateLogicException
	{
		Set<com.rssl.phizicgate.wsgate.services.mobilebank.generated.MbkCard> mbkCards = new HashSet<com.rssl.phizicgate.wsgate.services.mobilebank.generated.MbkCard>();
		for (MbkCard card:cards)
		{
			com.rssl.phizicgate.wsgate.services.mobilebank.generated.MbkCard mbkCard = new com.rssl.phizicgate.wsgate.services.mobilebank.generated.MbkCard();
			mbkCard.setCardType(card.getCardType().getCode());
			mbkCard.setErmbConnected(card.isErmbConnected());
			mbkCard.setNumber(card.getNumber());
			mbkCards.add(mbkCard);
		}

		try
		{
			com.rssl.phizicgate.wsgate.services.mobilebank.generated.BeginMigrationResult generatedBeginMigrationResult = getStub().beginMigrationErmb(mbkCards, phoneMigrateNumbers, phoneDeleteNumbers, migrationType.name());
			BeginMigrationResult gateBeginMigrationResult = new BeginMigrationResult();
			List<MbkConnectionInfo> mbkConnectionInfo = new ArrayList<MbkConnectionInfo>();
			com.rssl.phizicgate.wsgate.services.mobilebank.generated.MbkConnectionInfo[]  gateInfoArr = generatedBeginMigrationResult.getMbkConnectionInfo();
			for (com.rssl.phizicgate.wsgate.services.mobilebank.generated.MbkConnectionInfo gateInfo: gateInfoArr)
			{
				MbkConnectionInfo info = new MbkConnectionInfo();
				info.setLinkID(gateInfo.getLinkID());
				info.setLinkPaymentBlockID(gateInfo.getLinkPaymentBlockID());
				info.setLinkTariff(gateInfo.getLinkTariff());
				info.setPhoneBlockCode(gateInfo.getPhoneBlockCode());

				com.rssl.phizicgate.wsgate.services.mobilebank.generated.PhoneNumber number = gateInfo.getPhoneNumber();
				String numberStr =  number.getCountry() + number.getOperator() + number.getAbonent();
				PhoneNumber phoneNumber = PhoneNumber.fromString(numberStr);
				info.setPhoneNumber(phoneNumber);

				info.setPymentCard(gateInfo.getPymentCard());
				info.setPhoneOffers(gateInfo.getPhoneOffers());

				List<MobileBankTemplate> templateList = new ArrayList<MobileBankTemplate>();
				if (gateInfo.getTemplates() != null)
					BeanHelper.copyPropertiesWithDifferentTypes(templateList, new ArrayList<com.rssl.phizicgate.wsgate.services.mobilebank.generated.MobileBankTemplate>(Arrays.asList(gateInfo.getTemplates())), MobileBankServiceTypesCorrelation.types);
				info.setTemplates(templateList);

				List<InfoCardImpl> infoCards = new ArrayList<InfoCardImpl>();
				if (gateInfo.getInfoCards() != null)
					BeanHelper.copyPropertiesWithDifferentTypes(infoCards, new ArrayList<com.rssl.phizicgate.wsgate.services.mobilebank.generated.InfoCardImpl>(Arrays.asList(gateInfo.getInfoCards())), MobileBankServiceTypesCorrelation.types);
				info.setInfoCards(infoCards);

				mbkConnectionInfo.add(info)	;
			}

			gateBeginMigrationResult.setMbkConnectionInfo(mbkConnectionInfo);
			gateBeginMigrationResult.setMigrationId(generatedBeginMigrationResult.getMigrationId());
			return gateBeginMigrationResult;
		}
		catch (RemoteException ex)
		{
			checkTimeoutException(ex);

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateException(ex);
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicException(ex);
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				throw new WSGateException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public List<CommitMigrationResult> commitMigrationErmb(Long migrationId) throws GateException, GateLogicException
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.mobilebank.generated.CommitMigrationResult[] generatedCommitMigrationResult = getStub().commitMigrationErmb(migrationId);
			List<CommitMigrationResult> gateCommitMigrationResult = new ArrayList<CommitMigrationResult>();

			for (com.rssl.phizicgate.wsgate.services.mobilebank.generated.CommitMigrationResult gateRes:generatedCommitMigrationResult)
			{
				CommitMigrationResult res = new CommitMigrationResult();
				com.rssl.phizicgate.wsgate.services.mobilebank.generated.ClientTariffInfo gateTariffInfo = gateRes.getTariffInfo();
				ClientTariffInfo tariffInfo =  new ClientTariffInfo();
				tariffInfo.setPayDate(gateTariffInfo.getPayDate());
				tariffInfo.setNextPaidPeriod(gateTariffInfo.getNextPaidPeriod());
				tariffInfo.setLinkTariff(gateTariffInfo.getLinkTariff());
				tariffInfo.setFirstRegistrationDate(gateTariffInfo.getFirstRegistrationDate());
				tariffInfo.setLinkPaymentBlockID(gateTariffInfo.getLinkPaymentBlockID());
				res.setTariffInfo(tariffInfo);
				res.setLinkId(gateRes.getLinkId());
				res.setPaymentCard(gateRes.getPaymentCard());
				res.setPhoneNumber(gateRes.getPhoneNumber());
				gateCommitMigrationResult.add(res);
			}
			return gateCommitMigrationResult;
		}
		catch (RemoteException ex)
		{
			checkTimeoutException(ex);

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateException(ex);
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicException(ex);
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				throw new WSGateException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public void rollbackMigration(Long migrationId) throws GateException, GateLogicException
	{
		try
		{
			getStub().rollbackMigration(migrationId);
		}
		catch (RemoteException ex)
		{
			checkTimeoutException(ex);

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateException(ex);
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicException(ex);
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				throw new WSGateException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public void reverseMigration(Long migrationId, ClientTariffInfo clientTariffInfo) throws GateException, GateLogicException
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.mobilebank.generated.ClientTariffInfo generatedClientTariffInfo = new com.rssl.phizicgate.wsgate.services.mobilebank.generated.ClientTariffInfo();
			BeanHelper.copyPropertiesWithDifferentTypes(generatedClientTariffInfo, clientTariffInfo, MobileBankServiceTypesCorrelation.types);
			getStub().reverseMigration(migrationId, generatedClientTariffInfo);
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				throw new TemporalGateException(e);
			}
			throw new GateException(e);
		}
		catch (RemoteException ex)
		{
			checkTimeoutException(ex);

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateException(ex);
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicException(ex);
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				throw new WSGateException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public List<DisconnectedPhoneResult> getDisconnectedPhones(int maxResults) throws GateException, GateLogicException
	{
		try
		{
			List<com.rssl.phizicgate.wsgate.services.mobilebank.generated.DisconnectedPhoneResult> generatedDisconnectedPhoneResult = getStub().getDisconnectedPhones(maxResults);
			List<DisconnectedPhoneResult> gateDisconnectedPhoneResult = new ArrayList<DisconnectedPhoneResult>();
			BeanHelper.copyPropertiesWithDifferentTypes(gateDisconnectedPhoneResult, generatedDisconnectedPhoneResult, MobileBankServiceTypesCorrelation.types);
			return gateDisconnectedPhoneResult;
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				throw new TemporalGateException(e);
			}
			throw new GateException(e);
		}
		catch (RemoteException ex)
		{
			checkTimeoutException(ex);

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateException(ex);
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicException(ex);
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				throw new WSGateException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public void updateDisconnectedPhonesState(List<Integer> processedPhones) throws GateException, GateLogicException
	{
		try
		{
			getStub().updateDisconnectedPhonesState(processedPhones);
		}
		catch (RemoteException ex)
		{
			checkTimeoutException(ex);

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateException(ex);
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicException(ex);
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				throw new WSGateException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public List<P2PRequest> getMobilePaymentCardRequests() throws GateException, GateLogicException
	{
		try
		{
			List<com.rssl.phizicgate.wsgate.services.mobilebank.generated.P2PRequest> generatedP2PRequest = getStub().getMobilePaymentCardRequests();
			List<P2PRequest> gateP2PRequest = new ArrayList<P2PRequest>();
			for (com.rssl.phizicgate.wsgate.services.mobilebank.generated.P2PRequest req: generatedP2PRequest)
			{
				P2PRequest request =  new P2PRequest();
				request.setBranch(req.getBranch());
				request.setCreation(req.getCreation());
				request.setId(req.getId());
				request.setTb(req.getTb());
				com.rssl.phizicgate.wsgate.services.mobilebank.generated.PhoneNumber number = req.getPhone();
				String numberStr =  number.getCountry() + number.getOperator() + number.getAbonent();
				PhoneNumber phoneNumber = PhoneNumber.fromString(numberStr);
				request.setPhone(phoneNumber);
				gateP2PRequest.add(request);
			}
			return gateP2PRequest;
		}
		catch (RemoteException ex)
		{
			checkTimeoutException(ex);

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateException(ex);
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicException(ex);
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				throw new WSGateException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public void sendMobilePaymentCardResult(MobilePaymentCardResult cardResult) throws GateException, GateLogicException
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.mobilebank.generated.MobilePaymentCardResult generatedMobilePaymentCardResult = new com.rssl.phizicgate.wsgate.services.mobilebank.generated.MobilePaymentCardResult();
			BeanHelper.copyPropertiesWithDifferentTypes(generatedMobilePaymentCardResult, cardResult, MobileBankServiceTypesCorrelation.types);
			getStub().sendMobilePaymentCardResult(generatedMobilePaymentCardResult);
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				throw new TemporalGateException(e);
			}
			throw new GateException(e);
		}
		catch (RemoteException ex)
		{
			checkTimeoutException(ex);

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateException(ex);
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicException(ex);
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				throw new WSGateException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public List<MBKRegistration> getMbkRegistrationsForErmb() throws GateException, GateLogicException
	{
		try
		{
			List<com.rssl.phizicgate.wsgate.services.mobilebank.generated.MBKRegistration> generatedResultSet = getStub().getMbkRegistrationsForErmb();
			List<MBKRegistration> gateResultSet = new ArrayList<MBKRegistration>();
			BeanHelper.copyPropertiesWithDifferentTypes(gateResultSet, generatedResultSet, MobileBankServiceTypesCorrelation.types);
			return gateResultSet;
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				throw new TemporalGateException(e);
			}
			throw new GateException(e);
		}
		catch (RemoteException ex)
		{
			checkTimeoutException(ex);

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateException(ex);
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicException(ex);
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				throw new WSGateException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public void confirmMbRegistrationsLoading(List<Long> regIds) throws GateException, GateLogicException
	{
		try
		{
			getStub().confirmMbRegistrationsLoading(regIds);
		}
		catch (RemoteException ex)
		{
			checkTimeoutException(ex);

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateException(ex);
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicException(ex);
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				throw new WSGateException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public void sendMbRegistrationProcessingResult(long id, MBKRegistrationResultCode resultCode, String errorDescr) throws GateException, GateLogicException
	{
		try
		{
			getStub().sendMbRegistrationProcessingResult(id, resultCode.name(), errorDescr);
		}
		catch (RemoteException ex)
		{
			checkTimeoutException(ex);

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateException(ex);
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicException(ex);
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				throw new WSGateException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public void updateErmbPhonesInMb(List<ERMBPhone> ermbPhones) throws GateException, GateLogicException
	{
		try
		{
			List<com.rssl.phizicgate.wsgate.services.mobilebank.generated.ERMBPhone> generatedMobilePaymentERMBPhone = new ArrayList<com.rssl.phizicgate.wsgate.services.mobilebank.generated.ERMBPhone>();
			BeanHelper.copyPropertiesWithDifferentTypes(generatedMobilePaymentERMBPhone, ermbPhones, MobileBankServiceTypesCorrelation.types);
			getStub().updateErmbPhonesInMb(generatedMobilePaymentERMBPhone);
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				throw new TemporalGateException(e);
			}
			throw new GateException(e);
		}
		catch (RemoteException ex)
		{
			checkTimeoutException(ex);

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateException(ex);
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicException(ex);
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				throw new WSGateException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public List<MBKPhone> getPhonesForPeriod(Calendar start) throws GateException, GateLogicException
	{
		try
		{
			List<com.rssl.phizicgate.wsgate.services.mobilebank.generated.MBKPhone> generatedMBKPhone = getStub().getPhonesForPeriod(start);
			List<MBKPhone> gateMBKPhone = new ArrayList<MBKPhone>();
			BeanHelper.copyPropertiesWithDifferentTypes(gateMBKPhone, generatedMBKPhone, MobileBankServiceTypesCorrelation.types);
			return gateMBKPhone;
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				throw new TemporalGateException(e);
			}
			throw new GateException(e);
		}
		catch (RemoteException ex)
		{
			checkTimeoutException(ex);

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateException(ex);
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicException(ex);
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				throw new WSGateException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public String getCardNumberByPhone(Client client, String phone) throws GateException, GateLogicException
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.mobilebank.generated.Client generatedClient = new com.rssl.phizicgate.wsgate.services.mobilebank.generated.Client();

			BeanHelper.copyPropertiesWithDifferentTypes(generatedClient, client, MobileBankServiceTypesCorrelation.types);

			if (generatedClient.getId() != null)
			{
				String encoded = encodeData(generatedClient.getId());
				generatedClient.setId(encoded);
			}

			return getStub().getCardNumberByPhone(generatedClient, phone);
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				throw new TemporalGateException(e);
			}
			throw new GateException(e);
		}
		catch (RemoteException ex)
		{
			checkTimeoutException(ex);

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateException(ex);
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicException(ex);
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				throw new WSGateException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public List<UESIMessage> getUESIMessagesFromMBK() throws GateException, GateLogicException
	{
		try
		{
			List<com.rssl.phizicgate.wsgate.services.mobilebank.generated.UESIMessage> generatedUESIMessage = getStub().getUESIMessagesFromMBK();
			List<UESIMessage> gateUESIMessage = new ArrayList<UESIMessage>();
			BeanHelper.copyPropertiesWithDifferentTypes(gateUESIMessage, generatedUESIMessage, MobileBankServiceTypesCorrelation.types);
			return gateUESIMessage;
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				throw new TemporalGateException(e);
			}
			throw new GateException(e);
		}
		catch (RemoteException ex)
		{
			checkTimeoutException(ex);

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateException(ex);
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicException(ex);
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				throw new WSGateException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public void confirmUESIMessages(List<Long> ids) throws GateException, GateLogicException
	{
		try
		{
			getStub().confirmUESIMessages(ids);
		}
		catch (RemoteException ex)
		{
			checkTimeoutException(ex);

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateException(ex);
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicException(ex);
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				throw new WSGateException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public Map<String, SendMessageError> sendIMSICheck(String... phones) throws GateException, GateLogicException
	{
		try
		{
			Map<String, String> generatedMap = getStub().sendIMSICheck(phones);
			Map<String, SendMessageError> gateMap = new HashMap<String, SendMessageError>();
			for (String s : generatedMap.keySet())
			{
				gateMap.put(s, Enum.valueOf(SendMessageError.class, generatedMap.get(s)));
			}
			return gateMap;
		}
		catch (RemoteException ex)
		{
			checkTimeoutException(ex);

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateException(ex);
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicException(ex);
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				throw new WSGateException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public Set<String> getRegPhonesByCardNumbers(List<String> cardNumbers, GetRegistrationMode mode) throws GateException, GateLogicException
	{
		try
		{
			return getStub().getRegPhonesByCardNumbers(cardNumbers, mode.name());
		}
		catch (RemoteException ex)
		{
			checkTimeoutException(ex);

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateException(ex);
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicException(ex);
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				throw new WSGateException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public List<MobileBankRegistration> getRegistrations4(String cardNumber, GetRegistrationMode mode) throws GateException, GateLogicException, SystemException
	{
		try
		{
			List<com.rssl.phizicgate.wsgate.services.mobilebank.generated.MobileBankRegistration> generatedMobileBankRegistration = getStub().getRegistrations4(cardNumber, mode.name());
			List<MobileBankRegistration> gateMobileBankRegistration = new ArrayList<MobileBankRegistration>();
			BeanHelper.copyPropertiesWithDifferentTypes(gateMobileBankRegistration, generatedMobileBankRegistration, MobileBankServiceTypesCorrelation.types);
			return gateMobileBankRegistration;
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				throw new TemporalGateException(e);
			}
			throw new GateException(e);
		}
		catch (RemoteException ex)
		{
			checkTimeoutException(ex);

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateException(ex);
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicException(ex);
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				throw new WSGateException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public Boolean generatePassword(String cardNumber) throws GateException, GateLogicException
	{
		try
		{
			return getStub().generatePassword(cardNumber);
		}
		catch (RemoteException ex)
		{
			checkTimeoutException(ex);

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateException(ex);
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicException(ex);
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				throw new WSGateException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public Boolean generatePasswordForErmbClient(String cardNumber, String phoneNumber) throws GateException, GateLogicException
	{
		try
		{
			return getStub().generatePasswordForErmbClient(cardNumber, phoneNumber);
		}
		catch (RemoteException ex)
		{
			checkTimeoutException(ex);

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateException(ex);
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicException(ex);
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				throw new WSGateException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public GroupResult<String, List<GatePaymentTemplate>> getPaymentTemplates(String... cardNumbers)
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.mobilebank.generated.GroupResult generatedGroupResult = getStub().getPaymentTemplates(cardNumbers);
			GroupResult<String, List<GatePaymentTemplate>> gateGroupResult = new GroupResult<String, List<GatePaymentTemplate>>();
			BeanHelper.copyPropertiesWithDifferentTypes(gateGroupResult, generatedGroupResult, MobileBankServiceTypesCorrelation.types);
			return gateGroupResult;
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				return GroupResultHelper.getOneErrorResult(cardNumbers, new TemporalGateException(e));
			}
			return GroupResultHelper.getOneErrorResult(cardNumbers, new GateException(e));
		}
		catch (RemoteException ex)
		{
			if (ex.getMessage().contains(TimeoutHttpTransport.SOCKET_TIMEOUT_EXCEPTION))
				return GroupResultHelper.getOneErrorResult(cardNumbers, new GateWrapperTimeOutException(GateWrapperTimeOutException.GATE_WRAPPER_TIMEOUT_DEFAULT_MESSAGE, ex));

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				return GroupResultHelper.getOneErrorResult(cardNumbers, new WSTemporalGateException(ex));
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				return GroupResultHelper.getOneErrorResult(cardNumbers, new WSGateLogicException(ex));
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				return GroupResultHelper.getOneErrorResult(cardNumbers, new WSGateException(ex));
			}
			return GroupResultHelper.getOneErrorResult(cardNumbers, new GateException(ex));
		}
		catch (Exception ex)
		{
			throw new RuntimeException(ex);
		}
	}

	public GatePaymentTemplate getPaymentTemplate(String externalId) throws GateException, GateLogicException
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.mobilebank.generated.GatePaymentTemplate generatedPaymentTemplate = getStub().getPaymentTemplate(externalId);
			GatePaymentTemplate gatePaymentTemplate = new GatePaymentTemplateImpl();
			BeanHelper.copyPropertiesWithDifferentTypes(gatePaymentTemplate, generatedPaymentTemplate, MobileBankServiceTypesCorrelation.types);
			return gatePaymentTemplate;
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				throw new TemporalGateException(e);
			}
			throw new GateException(e);
		}
		catch (RemoteException ex)
		{
			checkTimeoutException(ex);

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateException(ex);
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicException(ex);
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				throw new WSGateException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}
}
