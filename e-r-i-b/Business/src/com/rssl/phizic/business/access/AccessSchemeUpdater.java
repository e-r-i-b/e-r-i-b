package com.rssl.phizic.business.access;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.schemes.*;
import com.rssl.phizic.business.services.Service;
import com.rssl.phizic.business.services.ServiceService;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Апдейтер схем прав по данным из jms
 * @author koptyaev
 * @ created 27.05.14
 * @ $Author$
 * @ $Revision$
 */
public class AccessSchemeUpdater
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	public static final String REPLICATE_ACCESS_SCHEME_JMS_TYPE = "ReplicateAccessSchemes";
	private static final AccessSchemeService accessSchemeService = new AccessSchemeService();
	private static final ServiceService serviceService = new ServiceService();

	/**
	 * Обновить схему прав
	 * @param jmsMessage параметры для обновления
	 */
	public void update(List jmsMessage)
	{
		//noinspection unchecked
		List<AccessScheme> schemeList = (ArrayList<AccessScheme>)jmsMessage;
		for (AccessScheme scheme : schemeList)
		{
			try
			{
				AccessScheme internalScheme = accessSchemeService.findByExternalId(scheme.getExternalId());
				if (internalScheme == null)
				{
					if (scheme instanceof SharedAccessScheme)
						internalScheme = new SharedAccessScheme();
					else
						internalScheme = new PersonalAccessScheme();
				}
				copyParameters((AccessSchemeBase)scheme, (AccessSchemeBase)internalScheme);
				accessSchemeService.save(internalScheme);
			}
			catch (BusinessException e)
			{
				log.error("Ошибка обновления схемы прав" + scheme.toString(), e);
			}
		}
	}

	/**
	 * Перенести параметря схемы прав доступа
	 * @param from откуда
	 * @param to куда
	 * @throws BusinessException
	 */
	private void copyParameters(AccessSchemeBase from, AccessSchemeBase to) throws BusinessException
	{
		to.setName(from.getName());
		to.setCategory(from.getCategory());
		to.setMailManagement(from.isMailManagement());
		to.setCAAdminScheme(from.isCAAdminScheme());
		to.setVSPEmployeeScheme(from.isVSPEmployeeScheme());
		to.setExternalId(from.getExternalId());
		List<Service> toServices = new ArrayList<Service>();
		for (Service fromService : from.getServices())
		{
			Service toService = serviceService.findByKey(fromService.getKey());
			toServices.add(toService);
		}
		to.setServices(toServices);
	}
}
