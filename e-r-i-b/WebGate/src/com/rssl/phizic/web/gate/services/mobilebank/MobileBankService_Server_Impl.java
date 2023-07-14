package com.rssl.phizic.web.gate.services.mobilebank;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizgate.mobilebank.MobileBankCardInfoImpl;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.TemporalGateException;
import com.rssl.phizic.gate.mobilebank.*;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.BeanHelper;
import com.rssl.phizic.utils.EncodeHelper;
import com.rssl.phizic.web.gate.services.mobilebank.generated.GroupResult;
import com.rssl.phizic.web.gate.services.mobilebank.generated.MobileBankService;
import com.rssl.phizic.web.gate.services.types.ClientImpl;
import com.rssl.phizic.web.security.Constants;
import org.apache.commons.codec.binary.Base64;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.*;

/**
 * @author Jatsky
 * @ created 19.05.15
 * @ $Author$
 * @ $Revision$
 */

public class MobileBankService_Server_Impl implements MobileBankService
{

	public void addRegistration(com.rssl.phizic.web.gate.services.mobilebank.generated.Client client_1, String string_2, String string_3, String string_4, String string_5) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.mobilebank.MobileBankService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.mobilebank.MobileBankService.class);
			if (client_1 != null && client_1.getId() != null)
			{
				String decoded = decodeData(client_1.getId());
				client_1.setId(decoded);
			}

			com.rssl.phizic.gate.clients.Client client = new ClientImpl();
			BeanHelper.copyPropertiesWithDifferentTypes(client, client_1, MobileBankServiceTypesCorrelation.types);

			service.addRegistration(client, string_2, string_3, string_4, Enum.valueOf(MobileBankTariff.class, string_5));
		}
		catch (GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
				throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
			}
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
		catch (TemporalGateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
		}
		catch (GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage() + Constants.MESSAGE_SUFFIX, e);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public void addTemplates(com.rssl.phizic.web.gate.services.mobilebank.generated.MobileBankCardInfo mobileBankCardInfo_1, List list_2) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.mobilebank.MobileBankService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.mobilebank.MobileBankService.class);
			com.rssl.phizgate.mobilebank.MobileBankCardInfoImpl mobileBankCardInfo = new MobileBankCardInfoImpl();
			BeanHelper.copyPropertiesWithDifferentTypes(mobileBankCardInfo, mobileBankCardInfo_1, MobileBankServiceTypesCorrelation.types);

			List<com.rssl.phizic.gate.mobilebank.MobileBankTemplate> mobileBankTemplates = new ArrayList<com.rssl.phizic.gate.mobilebank.MobileBankTemplate>(list_2.size());
			BeanHelper.copyPropertiesWithDifferentTypes(mobileBankTemplates, list_2, MobileBankServiceTypesCorrelation.types);
			service.addTemplates(mobileBankCardInfo, mobileBankTemplates);
		}
		catch (GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
				throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
			}
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
		catch (TemporalGateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
		}
		catch (GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage() + Constants.MESSAGE_SUFFIX, e);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public com.rssl.phizic.web.gate.services.mobilebank.generated.BeginMigrationResult beginMigrationErmb(Set set_1, Set set_2, Set set_3, String string_4) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.mobilebank.MobileBankService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.mobilebank.MobileBankService.class);
			Set<com.rssl.phizic.gate.mobilebank.MbkCard> mbkCardSet = new HashSet<com.rssl.phizic.gate.mobilebank.MbkCard>(set_1.size());
			BeanHelper.copyPropertiesWithDifferentTypes(mbkCardSet, set_1, MobileBankServiceTypesCorrelation.types);
			com.rssl.phizic.gate.mobilebank.BeginMigrationResult beginMigrationResult = service.beginMigrationErmb(mbkCardSet, set_2, set_3, Enum.valueOf(MigrationType.class, string_4));
			com.rssl.phizic.web.gate.services.mobilebank.generated.BeginMigrationResult generatedBeginMigrationResult = new com.rssl.phizic.web.gate.services.mobilebank.generated.BeginMigrationResult();

			List<MbkConnectionInfo> infoList = beginMigrationResult.getMbkConnectionInfo();
			List<com.rssl.phizic.web.gate.services.mobilebank.generated.MbkConnectionInfo> gateInfoList = new ArrayList<com.rssl.phizic.web.gate.services.mobilebank.generated.MbkConnectionInfo>();

			for (MbkConnectionInfo info:infoList)
			{
				com.rssl.phizic.web.gate.services.mobilebank.generated.MbkConnectionInfo gateInfo = new com.rssl.phizic.web.gate.services.mobilebank.generated.MbkConnectionInfo();

				if (info.getInfoCards() != null)
				{
					List<com.rssl.phizic.web.gate.services.mobilebank.generated.InfoCardImpl> gateInfoCardList = new ArrayList<com.rssl.phizic.web.gate.services.mobilebank.generated.InfoCardImpl>();
					for(InfoCardImpl infoCard:info.getInfoCards())
					{
						com.rssl.phizic.web.gate.services.mobilebank.generated.InfoCardImpl gateInfoCard = new com.rssl.phizic.web.gate.services.mobilebank.generated.InfoCardImpl();
						BeanHelper.copyPropertiesWithDifferentTypes(gateInfoCard, infoCard, MobileBankServiceTypesCorrelation.types);
						gateInfoCardList.add(gateInfoCard);
					}
					gateInfo.setInfoCards(gateInfoCardList.toArray(new com.rssl.phizic.web.gate.services.mobilebank.generated.InfoCardImpl[gateInfoCardList.size()]));
				}
				gateInfo.setLinkID(info.getLinkID());
				gateInfo.setLinkPaymentBlockID(info.getLinkPaymentBlockID());
				gateInfo.setLinkTariff(info.getLinkTariff());
				gateInfo.setPhoneBlockCode(info.getPhoneBlockCode());
				gateInfo.setPhoneOffers(info.getPhoneOffers());
				gateInfo.setPhoneBlockCode(info.getPhoneBlockCode());
				gateInfo.setPhoneQuickServices(info.getPhoneQuickServices());
				gateInfo.setPymentCard(info.getPymentCard());
				gateInfo.setLinkID(info.getLinkID());

				if (info.getTemplates() != null)
				{
					List<com.rssl.phizic.web.gate.services.mobilebank.generated.MobileBankTemplate>  gateTemplateList = new ArrayList<com.rssl.phizic.web.gate.services.mobilebank.generated.MobileBankTemplate>();
					BeanHelper.copyPropertiesWithDifferentTypes(gateTemplateList, info.getTemplates(), MobileBankServiceTypesCorrelation.types);
					gateInfo.setTemplates(gateTemplateList.toArray(new com.rssl.phizic.web.gate.services.mobilebank.generated.MobileBankTemplate[info.getTemplates().size()]));
				}

				com.rssl.phizic.web.gate.services.mobilebank.generated.PhoneNumber gatePhone = new com.rssl.phizic.web.gate.services.mobilebank.generated.PhoneNumber();
				gatePhone.setAbonent(info.getPhoneNumber().abonent());
				gatePhone.setCountry(info.getPhoneNumber().country());
				gatePhone.setOperator(info.getPhoneNumber().operator());
				gateInfo.setPhoneNumber(gatePhone);
				gateInfoList.add(gateInfo);
			}
			com.rssl.phizic.web.gate.services.mobilebank.generated.MbkConnectionInfo[] infoAr = (com.rssl.phizic.web.gate.services.mobilebank.generated.MbkConnectionInfo[]) gateInfoList.toArray(new com.rssl.phizic.web.gate.services.mobilebank.generated.MbkConnectionInfo[infoList.size()]);
			generatedBeginMigrationResult.setMbkConnectionInfo(infoAr);
			generatedBeginMigrationResult.setMigrationId(beginMigrationResult.getMigrationId());
			return generatedBeginMigrationResult;
		}
		catch (GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}

		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
				throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
			}
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
		catch (TemporalGateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
		}
		catch (GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage() + Constants.MESSAGE_SUFFIX, e);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public com.rssl.phizic.web.gate.services.mobilebank.generated.CommitMigrationResult[]  commitMigrationErmb(Long long_1) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.mobilebank.MobileBankService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.mobilebank.MobileBankService.class);
			List<com.rssl.phizic.gate.mobilebank.CommitMigrationResult> commitMigrationErmb = service.commitMigrationErmb(long_1);
			List<com.rssl.phizic.web.gate.services.mobilebank.generated.CommitMigrationResult> generatedCommitMigrationErmb = new ArrayList<com.rssl.phizic.web.gate.services.mobilebank.generated.CommitMigrationResult>();
			for (com.rssl.phizic.gate.mobilebank.CommitMigrationResult result:commitMigrationErmb)
			{
				com.rssl.phizic.web.gate.services.mobilebank.generated.CommitMigrationResult gateResult = new com.rssl.phizic.web.gate.services.mobilebank.generated.CommitMigrationResult();
				gateResult.setLinkId(result.getLinkId());
				gateResult.setPaymentCard(result.getPaymentCard());
				gateResult.setPhoneNumber(result.getPhoneNumber());

				ClientTariffInfo tariff = result.getTariffInfo();
				if (tariff != null)
				{
					com.rssl.phizic.web.gate.services.mobilebank.generated.ClientTariffInfo gateTariff = new com.rssl.phizic.web.gate.services.mobilebank.generated.ClientTariffInfo();
					gateTariff.setFirstRegistrationDate(tariff.getFirstRegistrationDate());
					gateTariff.setLinkPaymentBlockID(tariff.getLinkPaymentBlockID());
					gateTariff.setLinkTariff(tariff.getLinkTariff());
					gateTariff.setNextPaidPeriod(tariff.getNextPaidPeriod());
					gateTariff.setPayDate(tariff.getPayDate());

					gateResult.setTariffInfo(gateTariff);
				}
				generatedCommitMigrationErmb.add(gateResult);
			}
			com.rssl.phizic.web.gate.services.mobilebank.generated.CommitMigrationResult[] arr = (com.rssl.phizic.web.gate.services.mobilebank.generated.CommitMigrationResult[]) generatedCommitMigrationErmb.toArray(new com.rssl.phizic.web.gate.services.mobilebank.generated.CommitMigrationResult[generatedCommitMigrationErmb.size()]);
			return arr;
		}
		catch (GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch (TemporalGateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
		}
		catch (GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage() + Constants.MESSAGE_SUFFIX, e);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public void confirmMbRegistrationsLoading(List list_1) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.mobilebank.MobileBankService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.mobilebank.MobileBankService.class);

			service.confirmMbRegistrationsLoading(list_1);
		}
		catch (TemporalGateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
		}
		catch (GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage() + Constants.MESSAGE_SUFFIX, e);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public void confirmUESIMessages(List list_1) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.mobilebank.MobileBankService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.mobilebank.MobileBankService.class);
			service.confirmUESIMessages(list_1);
		}
		catch (TemporalGateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
		}
		catch (GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage() + Constants.MESSAGE_SUFFIX, e);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public Boolean generatePassword(String string_1) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.mobilebank.MobileBankService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.mobilebank.MobileBankService.class);
			return service.generatePassword(string_1);
		}
		catch (TemporalGateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
		}
		catch (GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage() + Constants.MESSAGE_SUFFIX, e);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public Boolean generatePasswordForErmbClient(String string_1, String string_2) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.mobilebank.MobileBankService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.mobilebank.MobileBankService.class);
			return service.generatePasswordForErmbClient(string_1, string_2);
		}
		catch (TemporalGateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
		}
		catch (GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage() + Constants.MESSAGE_SUFFIX, e);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public com.rssl.phizic.web.gate.services.mobilebank.generated.Card getCardByPhone(com.rssl.phizic.web.gate.services.mobilebank.generated.Client client_1, String string_2) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.mobilebank.MobileBankService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.mobilebank.MobileBankService.class);
			if (client_1 != null && client_1.getId() != null)
			{
				String decoded = decodeData(client_1.getId());
				client_1.setId(decoded);
			}

			com.rssl.phizic.gate.clients.Client client = new ClientImpl();
			BeanHelper.copyPropertiesWithDifferentTypes(client, client_1, MobileBankServiceTypesCorrelation.types);

			com.rssl.phizic.gate.bankroll.Card card = service.getCardByPhone(client, string_2);
			com.rssl.phizic.web.gate.services.mobilebank.generated.Card generatedCard = new com.rssl.phizic.web.gate.services.mobilebank.generated.Card();

			BeanHelper.copyPropertiesWithDifferentTypes(generatedCard, card, MobileBankServiceTypesCorrelation.types);
			return generatedCard;
		}
		catch (GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
				throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
			}
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
		catch (TemporalGateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
		}
		catch (GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage() + Constants.MESSAGE_SUFFIX, e);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public String getCardNumberByPhone(com.rssl.phizic.web.gate.services.mobilebank.generated.Client client_1, String string_2) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.mobilebank.MobileBankService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.mobilebank.MobileBankService.class);
			if (client_1 != null && client_1.getId() != null)
			{
				String decoded = decodeData(client_1.getId());
				client_1.setId(decoded);
			}

			com.rssl.phizic.gate.clients.Client client = new ClientImpl();
			BeanHelper.copyPropertiesWithDifferentTypes(client, client_1, MobileBankServiceTypesCorrelation.types);

			return service.getCardNumberByPhone(client, string_2);
		}
		catch (GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
				throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
			}
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
		catch (TemporalGateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
		}
		catch (GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage() + Constants.MESSAGE_SUFFIX, e);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public Set getCardsByPhone(String string_1) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.mobilebank.MobileBankService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.mobilebank.MobileBankService.class);
			return service.getCardsByPhone(string_1);
		}
		catch (TemporalGateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
		}
		catch (GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage() + Constants.MESSAGE_SUFFIX, e);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public Set getCardsByPhoneViaReportDB(String string_1) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.mobilebank.MobileBankService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.mobilebank.MobileBankService.class);
			return service.getCardsByPhoneViaReportDB(string_1);
		}
		catch (TemporalGateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
		}
		catch (GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage() + Constants.MESSAGE_SUFFIX, e);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public com.rssl.phizic.web.gate.services.mobilebank.generated.UserInfo getClientByCardNumber(String string_1) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.mobilebank.MobileBankService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.mobilebank.MobileBankService.class);

			com.rssl.phizic.gate.mobilebank.UserInfo userInfo = service.getClientByCardNumber(string_1);
			com.rssl.phizic.web.gate.services.mobilebank.generated.UserInfo generatedUserInfo = new com.rssl.phizic.web.gate.services.mobilebank.generated.UserInfo();

			BeanHelper.copyPropertiesWithDifferentTypes(generatedUserInfo, userInfo, MobileBankServiceTypesCorrelation.types);
			return generatedUserInfo;
		}
		catch (GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
				throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
			}
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
		catch (TemporalGateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
		}
		catch (GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage() + Constants.MESSAGE_SUFFIX, e);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public com.rssl.phizic.web.gate.services.mobilebank.generated.UserInfo getClientByLogin(String string_1) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.mobilebank.MobileBankService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.mobilebank.MobileBankService.class);

			com.rssl.phizic.gate.mobilebank.UserInfo userInfo = service.getClientByLogin(string_1);
			com.rssl.phizic.web.gate.services.mobilebank.generated.UserInfo generatedUserInfo = new com.rssl.phizic.web.gate.services.mobilebank.generated.UserInfo();

			BeanHelper.copyPropertiesWithDifferentTypes(generatedUserInfo, userInfo, MobileBankServiceTypesCorrelation.types);
			return generatedUserInfo;
		}
		catch (GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
				throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
			}
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
		catch (TemporalGateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
		}
		catch (GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage() + Constants.MESSAGE_SUFFIX, e);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public com.rssl.phizic.web.gate.services.mobilebank.generated.UserInfo getClientByPhone(String string_1) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.mobilebank.MobileBankService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.mobilebank.MobileBankService.class);

			com.rssl.phizic.gate.mobilebank.UserInfo userInfo = service.getClientByPhone(string_1);
			com.rssl.phizic.web.gate.services.mobilebank.generated.UserInfo generatedUserInfo = new com.rssl.phizic.web.gate.services.mobilebank.generated.UserInfo();

			BeanHelper.copyPropertiesWithDifferentTypes(generatedUserInfo, userInfo, MobileBankServiceTypesCorrelation.types);
			return generatedUserInfo;
		}
		catch (GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
				throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
			}
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
		catch (TemporalGateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
		}
		catch (GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage() + Constants.MESSAGE_SUFFIX, e);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public List getDisconnectedPhones(int int_1) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.mobilebank.MobileBankService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.mobilebank.MobileBankService.class);

			List<com.rssl.phizic.gate.mobilebank.DisconnectedPhoneResult> disconnectedPhoneResult = service.getDisconnectedPhones(int_1);

			List<com.rssl.phizic.web.gate.services.mobilebank.generated.DisconnectedPhoneResult> generatedDisconnectedPhoneResult = new ArrayList<com.rssl.phizic.web.gate.services.mobilebank.generated.DisconnectedPhoneResult>();

			BeanHelper.copyPropertiesWithDifferentTypes(generatedDisconnectedPhoneResult, disconnectedPhoneResult, MobileBankServiceTypesCorrelation.types);
			return generatedDisconnectedPhoneResult;
		}
		catch (GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
				throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
			}
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
		catch (TemporalGateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
		}
		catch (GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage() + Constants.MESSAGE_SUFFIX, e);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public List getMbkRegistrationsForErmb() throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.mobilebank.MobileBankService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.mobilebank.MobileBankService.class);

			List<com.rssl.phizic.gate.ermb.MBKRegistration> resultSet = service.getMbkRegistrationsForErmb();

			List<com.rssl.phizic.web.gate.services.mobilebank.generated.MBKRegistration> generatedMBKRegistration = new ArrayList<com.rssl.phizic.web.gate.services.mobilebank.generated.MBKRegistration>();

			BeanHelper.copyPropertiesWithDifferentTypes(generatedMBKRegistration, resultSet, MobileBankServiceTypesCorrelation.types);
			return generatedMBKRegistration;
		}
		catch (GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
				throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
			}
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
		catch (TemporalGateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
		}
		catch (GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage() + Constants.MESSAGE_SUFFIX, e);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public String getMobileContact(String string_1) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.mobilebank.MobileBankService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.mobilebank.MobileBankService.class);

			return service.getMobileContact(string_1);
		}
		catch (TemporalGateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
		}
		catch (GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage() + Constants.MESSAGE_SUFFIX, e);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public List getMobilePaymentCardRequests() throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.mobilebank.MobileBankService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.mobilebank.MobileBankService.class);

			List<com.rssl.phizic.gate.mobilebank.P2PRequest> p2PRequest = service.getMobilePaymentCardRequests();

			List<com.rssl.phizic.web.gate.services.mobilebank.generated.P2PRequest> generatedP2PRequest = new ArrayList<com.rssl.phizic.web.gate.services.mobilebank.generated.P2PRequest>();

			for (com.rssl.phizic.gate.mobilebank.P2PRequest req: p2PRequest)
			{
				com.rssl.phizic.web.gate.services.mobilebank.generated.P2PRequest request =  new com.rssl.phizic.web.gate.services.mobilebank.generated.P2PRequest();
				request.setBranch(req.getBranch());
				request.setCreation(req.getCreation());
				request.setId(req.getId());
				request.setTb(req.getTb());
				com.rssl.phizic.utils.PhoneNumber number = req.getPhone();
				com.rssl.phizic.web.gate.services.mobilebank.generated.PhoneNumber phoneNumber = new com.rssl.phizic.web.gate.services.mobilebank.generated.PhoneNumber();
				phoneNumber.setAbonent(number.abonent());
				phoneNumber.setOperator(number.operator());
				phoneNumber.setCountry(number.country());
				request.setPhone(phoneNumber);
				generatedP2PRequest.add(request);
			}
			return generatedP2PRequest;
		}
		catch (GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch (TemporalGateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
		}
		catch (GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage() + Constants.MESSAGE_SUFFIX, e);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public List getOfferConfirm() throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.mobilebank.MobileBankService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.mobilebank.MobileBankService.class);

			List<com.rssl.phizic.gate.mobilebank.AcceptInfo> acceptInfo = service.getOfferConfirm();

			List<com.rssl.phizic.web.gate.services.mobilebank.generated.AcceptInfo> generatedAcceptInfo = new ArrayList<com.rssl.phizic.web.gate.services.mobilebank.generated.AcceptInfo>();

			BeanHelper.copyPropertiesWithDifferentTypes(generatedAcceptInfo, acceptInfo, MobileBankServiceTypesCorrelation.types);
			return generatedAcceptInfo;
		}
		catch (GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
				throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
			}
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
		catch (TemporalGateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
		}
		catch (GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage() + Constants.MESSAGE_SUFFIX, e);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public com.rssl.phizic.web.gate.services.mobilebank.generated.GatePaymentTemplate getPaymentTemplate(String string_1) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.mobilebank.MobileBankService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.mobilebank.MobileBankService.class);

			com.rssl.phizic.gate.mobilebank.GatePaymentTemplate gatePaymentTemplate = service.getPaymentTemplate(string_1);
			com.rssl.phizic.web.gate.services.mobilebank.generated.GatePaymentTemplate generatedPaymentTemplate = new com.rssl.phizic.web.gate.services.mobilebank.generated.GatePaymentTemplate();

			BeanHelper.copyPropertiesWithDifferentTypes(generatedPaymentTemplate, gatePaymentTemplate, MobileBankServiceTypesCorrelation.types);
			return generatedPaymentTemplate;
		}
		catch (GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
				throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
			}
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
		catch (TemporalGateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
		}
		catch (GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage() + Constants.MESSAGE_SUFFIX, e);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public GroupResult getPaymentTemplates(String[] arrayOfString_1) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.mobilebank.MobileBankService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.mobilebank.MobileBankService.class);
			String[] decoded = decodeData(arrayOfString_1);
			com.rssl.phizic.common.types.transmiters.GroupResult<String, List<com.rssl.phizic.gate.mobilebank.GatePaymentTemplate>> gateGroupResult = service.getPaymentTemplates(decoded);
			com.rssl.phizic.web.gate.services.mobilebank.generated.GroupResult generatedGroupResult = new com.rssl.phizic.web.gate.services.mobilebank.generated.GroupResult();

			BeanHelper.copyPropertiesWithDifferentTypes(generatedGroupResult, gateGroupResult, MobileBankServiceTypesCorrelation.types);
			return generatedGroupResult;
		}
		catch (GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
				throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
			}
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
		catch (TemporalGateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
		}
		catch (GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage() + Constants.MESSAGE_SUFFIX, e);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public List getPhonesForPeriod(Calendar calendar_1) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.mobilebank.MobileBankService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.mobilebank.MobileBankService.class);

			List<com.rssl.phizic.gate.profile.MBKPhone> mBKPhone = service.getPhonesForPeriod(calendar_1);

			List<com.rssl.phizic.web.gate.services.mobilebank.generated.MBKPhone> generatedMBKPhone = new ArrayList<com.rssl.phizic.web.gate.services.mobilebank.generated.MBKPhone>();

			BeanHelper.copyPropertiesWithDifferentTypes(generatedMBKPhone, mBKPhone, MobileBankServiceTypesCorrelation.types);
			return generatedMBKPhone;
		}
		catch (GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
				throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
			}
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
		catch (TemporalGateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
		}
		catch (GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage() + Constants.MESSAGE_SUFFIX, e);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public String getQuickServiceStatus(String string_1) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.mobilebank.MobileBankService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.mobilebank.MobileBankService.class);

			return service.getQuickServiceStatus(string_1).name();
		}
		catch (TemporalGateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
		}
		catch (GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage() + Constants.MESSAGE_SUFFIX, e);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public Set getRegPhonesByCardNumbers(List list_1, String string_2) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.mobilebank.MobileBankService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.mobilebank.MobileBankService.class);

			return service.getRegPhonesByCardNumbers(list_1, Enum.valueOf(GetRegistrationMode.class, string_2));
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public GroupResult getRegistrations(Boolean boolean_1, String[] arrayOfString_2) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.mobilebank.MobileBankService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.mobilebank.MobileBankService.class);
			com.rssl.phizic.common.types.transmiters.GroupResult<String, List<com.rssl.phizic.gate.mobilebank.MobileBankRegistration>> gateGroupResult = service.getRegistrations(boolean_1, arrayOfString_2);
			com.rssl.phizic.web.gate.services.mobilebank.generated.GroupResult generatedGroupResult = new com.rssl.phizic.web.gate.services.mobilebank.generated.GroupResult();

			BeanHelper.copyPropertiesWithDifferentTypes(generatedGroupResult, gateGroupResult, MobileBankServiceTypesCorrelation.types);
			return generatedGroupResult;
		}
		catch (GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
				throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
			}
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
		catch (TemporalGateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
		}
		catch (GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage() + Constants.MESSAGE_SUFFIX, e);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public List getRegistrations3(String string_1) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.mobilebank.MobileBankService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.mobilebank.MobileBankService.class);
			List<com.rssl.phizic.gate.mobilebank.MobileBankRegistration3> gateMobileBankRegistration = service.getRegistrations3(string_1);
			List<com.rssl.phizic.web.gate.services.mobilebank.generated.MobileBankRegistration3> generatedMobileBankRegistration = new ArrayList<com.rssl.phizic.web.gate.services.mobilebank.generated.MobileBankRegistration3>();

			BeanHelper.copyPropertiesWithDifferentTypes(generatedMobileBankRegistration, gateMobileBankRegistration, MobileBankServiceTypesCorrelation.types);
			return generatedMobileBankRegistration;
		}
		catch (GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
				throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
			}
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
		catch (TemporalGateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
		}
		catch (GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage() + Constants.MESSAGE_SUFFIX, e);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public GroupResult getTemplates(Long long_1, com.rssl.phizic.web.gate.services.mobilebank.generated.MobileBankCardInfo[] arrayOfMobileBankCardInfo_2) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.mobilebank.MobileBankService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.mobilebank.MobileBankService.class);

			com.rssl.phizgate.mobilebank.MobileBankCardInfoImpl[] gateMobileBankCardInfo = new MobileBankCardInfoImpl[arrayOfMobileBankCardInfo_2.length];
			BeanHelper.copyPropertiesWithDifferentTypes(gateMobileBankCardInfo, arrayOfMobileBankCardInfo_2, MobileBankServiceTypesCorrelation.types);

			com.rssl.phizic.common.types.transmiters.GroupResult<com.rssl.phizic.gate.mobilebank.MobileBankCardInfo, List<com.rssl.phizic.gate.mobilebank.MobileBankTemplate>> gateGroupResult = service.getTemplates(long_1, gateMobileBankCardInfo);
			com.rssl.phizic.web.gate.services.mobilebank.generated.GroupResult generatedGroupResult = new com.rssl.phizic.web.gate.services.mobilebank.generated.GroupResult();

			BeanHelper.copyPropertiesWithDifferentTypes(generatedGroupResult, gateGroupResult, MobileBankServiceTypesCorrelation.types);
			return generatedGroupResult;
		}
		catch (GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
				throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
			}
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
		catch (TemporalGateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
		}
		catch (GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage() + Constants.MESSAGE_SUFFIX, e);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public List getUESIMessagesFromMBK() throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.mobilebank.MobileBankService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.mobilebank.MobileBankService.class);
			List<com.rssl.phizic.gate.mobilebank.UESIMessage> gateUESIMessage = service.getUESIMessagesFromMBK();
			List<com.rssl.phizic.web.gate.services.mobilebank.generated.UESIMessage> generatedUESIMessage = new ArrayList<com.rssl.phizic.web.gate.services.mobilebank.generated.UESIMessage>();

			BeanHelper.copyPropertiesWithDifferentTypes(generatedUESIMessage, gateUESIMessage, MobileBankServiceTypesCorrelation.types);
			return generatedUESIMessage;
		}
		catch (GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
				throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
			}
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
		catch (TemporalGateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
		}
		catch (GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage() + Constants.MESSAGE_SUFFIX, e);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public void removeTemplates(com.rssl.phizic.web.gate.services.mobilebank.generated.MobileBankCardInfo mobileBankCardInfo_1, List list_2) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.mobilebank.MobileBankService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.mobilebank.MobileBankService.class);
			com.rssl.phizgate.mobilebank.MobileBankCardInfoImpl mobileBankCardInfo = new MobileBankCardInfoImpl();
			BeanHelper.copyPropertiesWithDifferentTypes(mobileBankCardInfo, mobileBankCardInfo_1, MobileBankServiceTypesCorrelation.types);

			List<com.rssl.phizic.gate.mobilebank.MobileBankTemplate> mobileBankTemplates = new ArrayList<com.rssl.phizic.gate.mobilebank.MobileBankTemplate>(list_2.size());
			BeanHelper.copyPropertiesWithDifferentTypes(mobileBankTemplates, list_2, MobileBankServiceTypesCorrelation.types);
			service.removeTemplates(mobileBankCardInfo, mobileBankTemplates);
		}
		catch (GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
				throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
			}
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
		catch (TemporalGateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
		}
		catch (GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage() + Constants.MESSAGE_SUFFIX, e);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public void reverseMigration(Long long_1, com.rssl.phizic.web.gate.services.mobilebank.generated.ClientTariffInfo clientTariffInfo_2) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.mobilebank.MobileBankService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.mobilebank.MobileBankService.class);
			com.rssl.phizic.gate.mobilebank.ClientTariffInfo gateMobileBankCardInfo = new com.rssl.phizic.gate.mobilebank.ClientTariffInfo();
			BeanHelper.copyPropertiesWithDifferentTypes(gateMobileBankCardInfo, clientTariffInfo_2, MobileBankServiceTypesCorrelation.types);

			service.reverseMigration(long_1, gateMobileBankCardInfo);
		}
		catch (GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
				throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
			}
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
		catch (TemporalGateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
		}
		catch (GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage() + Constants.MESSAGE_SUFFIX, e);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public void rollbackMigration(Long long_1) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.mobilebank.MobileBankService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.mobilebank.MobileBankService.class);

			service.rollbackMigration(long_1);
		}
		/*catch(GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
				throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
			}
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}*/
		catch (TemporalGateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
		}
		catch (GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage() + Constants.MESSAGE_SUFFIX, e);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public Map sendIMSICheck(String[] arrayOfString_1) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.mobilebank.MobileBankService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.mobilebank.MobileBankService.class);
			return service.sendIMSICheck(arrayOfString_1);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public void sendMbRegistrationProcessingResult(long long_1, String string_2, String string_3) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.mobilebank.MobileBankService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.mobilebank.MobileBankService.class);
			service.sendMbRegistrationProcessingResult(long_1, Enum.valueOf(MBKRegistrationResultCode.class, string_2), string_3);
		}
		catch (TemporalGateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
		}
		catch (GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage() + Constants.MESSAGE_SUFFIX, e);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public void sendMobilePaymentCardResult(com.rssl.phizic.web.gate.services.mobilebank.generated.MobilePaymentCardResult mobilePaymentCardResult_1) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.mobilebank.MobileBankService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.mobilebank.MobileBankService.class);
			com.rssl.phizic.gate.mobilebank.MobilePaymentCardResult gateMobilePaymentCardResult = new com.rssl.phizic.gate.mobilebank.MobilePaymentCardResult();
			BeanHelper.copyPropertiesWithDifferentTypes(gateMobilePaymentCardResult, mobilePaymentCardResult_1, MobileBankServiceTypesCorrelation.types);

			service.sendMobilePaymentCardResult(gateMobilePaymentCardResult);
		}
		catch (GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
				throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
			}
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
		catch (TemporalGateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
		}
		catch (GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage() + Constants.MESSAGE_SUFFIX, e);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public void sendOfferMessageSMS(String string_1, String string_2, Long long_3, String string_4) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.mobilebank.MobileBankService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.mobilebank.MobileBankService.class);

			service.sendOfferMessageSMS(string_1, string_2, long_3, string_4);
		}
		catch (TemporalGateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
		}
		catch (GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage() + Constants.MESSAGE_SUFFIX, e);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public void sendOfferQuit(Long long_1) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.mobilebank.MobileBankService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.mobilebank.MobileBankService.class);

			service.sendOfferQuit(long_1);
		}
		catch (TemporalGateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
		}
		catch (GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage() + Constants.MESSAGE_SUFFIX, e);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public void sendSMS(String string_1, String string_2, int int_3, String string_4) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.mobilebank.MobileBankService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.mobilebank.MobileBankService.class);

			service.sendSMS(string_1, string_2, int_3, string_4);
		}
		catch (TemporalGateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
		}
		catch (GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage() + Constants.MESSAGE_SUFFIX, e);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public Map sendSMSWithIMSICheck(com.rssl.phizic.web.gate.services.mobilebank.generated.MessageInfo messageInfo_1, String[] arrayOfString_2) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.mobilebank.MobileBankService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.mobilebank.MobileBankService.class);
			com.rssl.phizic.web.gate.services.types.MessageInfoImpl gateMessageInfo = new com.rssl.phizic.web.gate.services.types.MessageInfoImpl();
			BeanHelper.copyPropertiesWithDifferentTypes(gateMessageInfo, messageInfo_1, MobileBankServiceTypesCorrelation.types);
			return service.sendSMSWithIMSICheck(gateMessageInfo, arrayOfString_2);
		}
		catch (GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
				throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
			}
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
		catch (TemporalGateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
		}
		catch (GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage() + Constants.MESSAGE_SUFFIX, e);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public String setQuickServiceStatus(String string_1, String string_2) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.mobilebank.MobileBankService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.mobilebank.MobileBankService.class);
			return service.setQuickServiceStatus(string_1, Enum.valueOf(QuickServiceStatusCode.class, string_2)).name();
		}
		catch (TemporalGateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
		}
		catch (GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage() + Constants.MESSAGE_SUFFIX, e);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public void updateDisconnectedPhonesState(List list_1) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.mobilebank.MobileBankService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.mobilebank.MobileBankService.class);
			service.updateDisconnectedPhonesState(list_1);
		}
		catch (TemporalGateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
		}
		catch (GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage() + Constants.MESSAGE_SUFFIX, e);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public void updateErmbPhonesInMb(List list_1) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.mobilebank.MobileBankService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.mobilebank.MobileBankService.class);
			List<com.rssl.phizic.gate.mobilebank.ERMBPhone> gateERMBPhone = new ArrayList<com.rssl.phizic.gate.mobilebank.ERMBPhone>();
			BeanHelper.copyPropertiesWithDifferentTypes(gateERMBPhone, list_1, MobileBankServiceTypesCorrelation.types);
			service.updateErmbPhonesInMb(gateERMBPhone);
		}
		catch (TemporalGateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
		}
		catch (GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage() + Constants.MESSAGE_SUFFIX, e);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public List getRegistrations4(java.lang.String string_1, java.lang.String string_2) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.mobilebank.MobileBankService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.mobilebank.MobileBankService.class);
			List<com.rssl.phizic.gate.mobilebank.MobileBankRegistration> gateMobileBankRegistration = service.getRegistrations4(string_1, Enum.valueOf(GetRegistrationMode.class, string_2));
			List<com.rssl.phizic.web.gate.services.mobilebank.generated.MobileBankRegistration> generatedMobileBankRegistration = new ArrayList<com.rssl.phizic.web.gate.services.mobilebank.generated.MobileBankRegistration>();

			BeanHelper.copyPropertiesWithDifferentTypes(generatedMobileBankRegistration, gateMobileBankRegistration, MobileBankServiceTypesCorrelation.types);
			return generatedMobileBankRegistration;
		}
		catch (GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
				throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
			}
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
		catch (TemporalGateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
		}
		catch (GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage() + Constants.MESSAGE_SUFFIX, e);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public List getRegistrationsPack(Boolean boolean_1, String[] arrayOfString_2) throws RemoteException
	{
		try
		{
			com.rssl.phizic.gate.mobilebank.MobileBankService service = GateSingleton.getFactory().service(com.rssl.phizic.gate.mobilebank.MobileBankService.class);
			List<com.rssl.phizic.gate.mobilebank.MobileBankRegistration> gateMobileBankRegistration = service.getRegistrationsPack(boolean_1, arrayOfString_2);
			List<com.rssl.phizic.web.gate.services.mobilebank.generated.MobileBankRegistration> generatedMobileBankRegistration = new ArrayList<com.rssl.phizic.web.gate.services.mobilebank.generated.MobileBankRegistration>();

			BeanHelper.copyPropertiesWithDifferentTypes(generatedMobileBankRegistration, gateMobileBankRegistration, MobileBankServiceTypesCorrelation.types);
			return generatedMobileBankRegistration;
		}
		catch (GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
				throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
			}
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
		catch (TemporalGateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.TEMPORAL_MESSAGE_PREFIX + e.getMessage() + Constants.TEMPORAL_MESSAGE_SUFFIX, e);
		}
		catch (GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage() + Constants.MESSAGE_SUFFIX, e);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public void methodForAddClassesToWSDL(com.rssl.phizic.web.gate.services.mobilebank.generated.CommitMigrationResult commitMigrationResult_1, com.rssl.phizic.web.gate.services.mobilebank.generated.MobileBankTemplate mobileBankTemplate_2, com.rssl.phizic.web.gate.services.mobilebank.generated.MbkCard mbkCard_3, com.rssl.phizic.web.gate.services.mobilebank.generated.DisconnectedPhoneResult disconnectedPhoneResult_4, com.rssl.phizic.web.gate.services.mobilebank.generated.P2PRequest p2PRequest_5, com.rssl.phizic.web.gate.services.mobilebank.generated.AcceptInfo acceptInfo_6, com.rssl.phizic.web.gate.services.mobilebank.generated.UESIMessage UESIMessage_7, com.rssl.phizic.web.gate.services.mobilebank.generated.ERMBPhone ERMBPhone_8, com.rssl.phizic.web.gate.services.mobilebank.generated.MobileBankRegistration mobileBankRegistration_9, com.rssl.phizic.web.gate.services.mobilebank.generated.MBKPhone MBKPhone_10, com.rssl.phizic.web.gate.services.mobilebank.generated.MQInfo MQInfo_11, com.rssl.phizic.web.gate.services.mobilebank.generated.NodeInfo nodeInfo_12, com.rssl.phizic.web.gate.services.mobilebank.generated.UserInfoCSA userInfoCSA_13, com.rssl.phizic.web.gate.services.mobilebank.generated.MBKRegistration MBKRegistration_14, com.rssl.phizic.web.gate.services.mobilebank.generated.CodeOffice codeOffice_15, com.rssl.phizic.web.gate.services.mobilebank.generated.IKFLException IKFLException_16, com.rssl.phizic.web.gate.services.mobilebank.generated.MobileBankRegistration3 mobileBankRegistration3_17) throws RemoteException
	{
	}

	private static String[] decodeData(String[] data)
	{
		String[] newData = data;
		for (int i = 0; i < data.length; i++)
		{
			newData[i] = decodeData(data[i]);
		}
		return newData;
	}

	private static String decodeData(String data)
	{
		//вырезаем конечные "=="
		String end = "";
		int i = data.indexOf('=');
		if (i >= 0)
		{
			end = data.substring(i);
			data = data.substring(0, i);
		}
		//декодируем
		byte[] bytes = EncodeHelper.decode(data.getBytes());

		//вырезаем контрольную информацию
		byte contralInfo = bytes[0];
		//получаем строку дл€ раскодировани€ из base64
		byte[] base64bytes = new byte[bytes.length - 1 + end.length()];
		System.arraycopy(bytes, 1, base64bytes, 0, bytes.length - 1);
		System.arraycopy(end.getBytes(), 0, base64bytes, bytes.length - 1, end.length());

		//провер€ем контрольную информацию
		checkContralInfo(base64bytes, contralInfo);
		byte[] decodedBase64 = Base64.decodeBase64(base64bytes);

		return new String(decodedBase64);
	}

	private static final byte[] info = new byte[64];

	static
	{
		info[0] = 'A';
		info[1] = 'B';
		info[2] = 'C';
		info[3] = 'D';
		info[4] = 'E';
		info[5] = 'F';
		info[6] = 'G';
		info[7] = 'H';
		info[8] = 'I';
		info[9] = 'J';
		info[10] = 'K';
		info[11] = 'L';
		info[12] = 'M';
		info[13] = 'N';
		info[14] = 'O';
		info[15] = 'P';
		info[16] = 'Q';
		info[17] = 'R';
		info[18] = 'S';
		info[19] = 'T';
		info[20] = 'U';
		info[21] = 'V';
		info[22] = 'W';
		info[23] = 'X';
		info[24] = 'Y';
		info[25] = 'Z';
		info[26] = 'a';
		info[27] = 'b';
		info[28] = 'c';
		info[29] = 'd';
		info[30] = 'e';
		info[31] = 'f';
		info[32] = 'g';
		info[33] = 'h';
		info[34] = 'i';
		info[35] = 'j';
		info[36] = 'k';
		info[37] = 'l';
		info[38] = 'm';
		info[39] = 'n';
		info[40] = 'o';
		info[41] = 'p';
		info[42] = 'q';
		info[43] = 'r';
		info[44] = 's';
		info[45] = 't';
		info[46] = 'u';
		info[47] = 'v';
		info[48] = 'w';
		info[49] = 'x';
		info[50] = 'y';
		info[51] = 'z';
		info[52] = '0';
		info[53] = '1';
		info[54] = '2';
		info[55] = '3';
		info[56] = '4';
		info[57] = '5';
		info[58] = '6';
		info[59] = '7';
		info[60] = '8';
		info[61] = '9';
		info[62] = '+';
		info[63] = '/';
	}

	private static void checkContralInfo(byte[] data, byte contralInfo)
	{
		int sum = 0;
		for (byte b : data)
		{
			sum += b;
		}
		if (info[sum % info.length] != contralInfo)
		{
			throw new IllegalArgumentException("Ќарушены лицензионные ограничени€");
		}
	}
}
