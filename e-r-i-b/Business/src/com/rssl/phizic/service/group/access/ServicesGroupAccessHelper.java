package com.rssl.phizic.service.group.access;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.services.groups.ServiceInformation;
import com.rssl.phizic.business.services.groups.ServiceMode;
import com.rssl.phizic.business.services.groups.ServicesGroupHelper;
import com.rssl.phizic.business.services.groups.ServicesGroupInformation;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.security.PermissionUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author komarov
 * @ created 08.05.2015
 * @ $Author$
 * @ $Revision$
 */
public class ServicesGroupAccessHelper
{
	private static volatile ServicesGroupAccessHelper INSTANCE = null;
	private static final Object LOCKER = new Object();

	private List<ServicesGroupInformation> groups;

	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private ServicesGroupAccessHelper()
	{
		try
		{
			groups = ServicesGroupHelper.getServicesGroupsList();
		}
		catch (BusinessException be)
		{
			log.error("Ошибка получения групп из operations-tree.xml", be);
		}
	}

	/**
	 * @return INSTANCE
	 */
	public static ServicesGroupAccessHelper getInstance()
	{
		if(INSTANCE != null)
			return INSTANCE;

		synchronized (LOCKER)
		{
			if(INSTANCE == null)
				INSTANCE = new ServicesGroupAccessHelper();
			return INSTANCE;
		}

	}


	/**
	 * Проверяет возможность доступа к группе
	 * @return доступные
	 */
	public Set<String> getImpliesGroup()
	{
		HashSet<String> result = new HashSet<String>();
		try
		{
			if(groups == null || groups.isEmpty())
				return result;

			for(ServicesGroupInformation information : groups)
				if(imples(information, result))
					result.add(information.getKey());
		}
		catch (Exception ex)
		{
			log.error("Ошибка при получении списка доступных групп", ex);
			return new HashSet<String>();
		}
		return result;
	}

	private boolean imples(ServicesGroupInformation group, Set<String> result)
	{
		boolean hasAccess = false;
		for(ServicesGroupInformation subGroup : group.getSubgroups())
		{
			if(imples(subGroup, result))
			{
				result.add(subGroup.getKey());
				hasAccess = true;
			}
		}
		if (!group.getSubgroups().isEmpty())
			return hasAccess;
		for(ServicesGroupInformation action : group.getActions())
		{
			if(imples(action, result))
			{
				result.add(action.getKey());
				return true;
			}
		}

		boolean view = true;
		boolean edit = true;
		boolean hasView = false;
		boolean hasEdit = false;
		for(ServiceInformation service : group.getServices())
		{
			boolean iResult = PermissionUtil.impliesServiceRigid(service.getKey());
			hasView |= service.getMode() == ServiceMode.view;
			hasEdit |= service.getMode() == ServiceMode.edit;
			view &= service.getMode() != ServiceMode.view || iResult;
			edit &= service.getMode() != ServiceMode.edit || iResult;
		}

		return view && hasView || edit && hasEdit;
	}
}
