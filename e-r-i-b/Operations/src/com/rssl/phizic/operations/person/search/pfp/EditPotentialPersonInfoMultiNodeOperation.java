package com.rssl.phizic.operations.person.search.pfp;

import com.rssl.auth.csa.wsclient.NodeInfoConfig;
import com.rssl.phizic.ApplicationAutoRefreshConfig;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.messaging.info.PersonalSubscriptionData;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.csa.ProfileService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.csa.Profile;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.operations.person.search.multinode.ChangeNodeLogicException;
import com.rssl.phizicgate.csaadmin.service.authentication.CSAAdminAuthService;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mihaylov
 * @ created 11.11.13
 * @ $Author$
 * @ $Revision$
 * ќпераци€ сохранени€ потенциального клиента в многоблочной структуре
 */
public class EditPotentialPersonInfoMultiNodeOperation extends EditPotentialPersonInfoOperation
{
	private static final ProfileService profileService = new ProfileService();
	private static final CSAAdminAuthService csaAdminAuthService = new CSAAdminAuthService();

	public void save() throws BusinessException, BusinessLogicException
	{
		Profile profile = profileService.createProfileByClient(getEntity().asClient());
		checkNode(profile);
		super.save();
	}

	public void continueSave() throws BusinessException, BusinessLogicException
	{
		setOperationContext();
		super.save();
	}

	private void checkNode(Profile profile) throws BusinessException, BusinessLogicException
	{
		if (!ConfigFactory.getConfig(NodeInfoConfig.class).getNode(profile.getNodeId()).isAdminAvailable())
			throw new BusinessLogicException("Ѕлок не доступен");

		if(!ConfigFactory.getConfig(ApplicationAutoRefreshConfig.class).getNodeNumber().equals(profile.getNodeId()))
		{
			saveOperationContext();
			throw new ChangeNodeLogicException("ѕользователь найден в другом блоке системы",profile.getNodeId());
		}
	}

	private void saveOperationContext() throws BusinessException, BusinessLogicException
	{
		try
		{
			Map<String,Object> context = new HashMap<String,Object>();
			context.put("person",getEntity());
			context.put("subscription",getSubscriptionData());
			csaAdminAuthService.saveOperationContext(context);
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
		catch (GateLogicException e)
		{
			throw new BusinessLogicException(e);
		}
	}

	private void setOperationContext() throws BusinessException, BusinessLogicException
	{
		try
		{
			Map<String,Object> context = csaAdminAuthService.getOperationContext();
			setEditingPerson((ActivePerson)context.get("person"));
			setSubscriptionData((PersonalSubscriptionData)context.get("subscription"));
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
		catch (GateLogicException e)
		{
			throw new BusinessLogicException(e);
		}
	}

}
