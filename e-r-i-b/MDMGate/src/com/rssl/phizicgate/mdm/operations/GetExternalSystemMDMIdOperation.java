package com.rssl.phizicgate.mdm.operations;

import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.mdm.business.MDMDatabaseServiceBase;
import com.rssl.phizicgate.mdm.business.profiles.Document;
import com.rssl.phizicgate.mdm.business.profiles.Profile;
import com.rssl.phizicgate.mdm.common.*;
import com.rssl.phizicgate.mdm.integration.mdm.MDMClientService;
import org.hibernate.Session;

/**
 * @author akrenev
 * @ created 13.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * Операция поиска mdm_id во вншней системе
 */

public class GetExternalSystemMDMIdOperation extends UpdateClientInfoOperationBase<SearchClientInfo, String>
{
	private static final MDMClientService mdmService = new MDMClientService();

	public String execute() throws GateLogicException, GateException
	{
		try
		{
			SearchClientInfo searchClientInfo = getSource();
			Long csaProfileId = searchClientInfo.getInnerId();

			String storedMdmId = getProfileService().getMdmId(csaProfileId);
			ClientWithProductsInfo clientWithProductsInfo = getMdmService().searchProfile(searchClientInfo, storedMdmId);

			ClientInfo clientInfo = clientWithProductsInfo.getClientInfo();
			if (StringHelper.isNotEmpty(storedMdmId) && !storedMdmId.equals(clientInfo.getMdmId()))
				throw new GateException("Не совпали идентификаторы мдм [старый " + storedMdmId + ", новый " + clientInfo.getMdmId() + "].");

			Profile updatedProfile = synchronize(csaProfileId, clientWithProductsInfo);
			createMdmSubscription(csaProfileId, storedMdmId, updatedProfile);
			notifyCSA(csaProfileId, updatedProfile.getMdmId());
			return updatedProfile.getMdmId();
		}
		catch (GateLogicException e)
		{
			throw e;
		}
		catch (GateException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new GateException("Ошибка обновления профиля из мдм.", e);
		}
	}

	private Profile synchronize(final Long csaProfileId, ClientWithProductsInfo clientWithProductsInfo) throws Exception
	{
		final ClientInfo clientInfo = clientWithProductsInfo.getClientInfo();
		final ClientProductsInfo productsInfo = clientWithProductsInfo.getProductsInfo();

		return MDMDatabaseServiceBase.executeAtomic(new HibernateAction<Profile>()
		{
			public Profile run(Session session) throws Exception
			{
				Profile profile = updateProfile(csaProfileId, clientInfo);
				updateProducts(productsInfo, profile.getId());
				return profile;
			}
		});
	}

	private void createMdmSubscription(Long csaProfileId, String storedMdmId, Profile profile)
	{
		try
		{
			if (StringHelper.isNotEmpty(storedMdmId))
				return;

			CreateNotificationClientInfo createNotificationClientInfo = new CreateNotificationClientInfo();
			createNotificationClientInfo.setMdmId(profile.getMdmId());
			createNotificationClientInfo.setInnerId(csaProfileId);
			createNotificationClientInfo.setLastName(profile.getLastName());
			createNotificationClientInfo.setFirstName(profile.getFirstName());
			createNotificationClientInfo.setMiddleName(profile.getMiddleName());
			createNotificationClientInfo.setBirthday(profile.getBirthday());

			Document document = profile.getDocuments().iterator().next();

			createNotificationClientInfo.setDocumentSeries(document.getSeries());
			createNotificationClientInfo.setDocumentNumber(document.getNumber());
			createNotificationClientInfo.setDocumentType(document.getType());

			getMdmService().createProfileNotification(createNotificationClientInfo);
		}
		catch (Exception e)
		{
			log.error("Ошибка отправки сообщения на подписку к рассылке оповещений мдм.", e);
		}
	}

	private static MDMClientService getMdmService()
	{
		return mdmService;
	}
}
