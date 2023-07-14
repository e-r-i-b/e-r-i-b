package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.FieldValidatorBase;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.MultiInstanceExternalResourceService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.build.BuildContextConfig;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * ѕроверка на то, что счет не прив€зан уже к другому клиенту
 * @ author: filimonova
 * @ created: 15.03.2010
 * @ $Author$
 * @ $Revision$
 */
public class AccountIsUsedValidator extends FieldValidatorBase
{
	private static final MultiInstanceExternalResourceService externalResourceService = new MultiInstanceExternalResourceService();
	private static final String INSTANCE_NAME = "Shadow";
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private ArrayList<String> getInstanceName()
	{
		ArrayList<String> instances = new ArrayList();
		instances.add(null);
		BuildContextConfig buildConfig = ConfigFactory.getConfig(BuildContextConfig.class);
		if (buildConfig.isShadowDatabaseOn())
		{
			instances.add(INSTANCE_NAME);
		}
		return instances;
	}

	private String setWarnMessage(List<AccountLink> links, String value)
	{
		String warnMessage="";
		for (AccountLink link : links)
		{
			warnMessage+=link.getLoginId()+" ";
		}
		return warnMessage;
	}

	public boolean validate(String value) throws TemporalDocumentException
	{
		try
		{
			ArrayList<AccountLink> links = new ArrayList<AccountLink>();
			String warnMessage = "—чет с номером " + value + " уже существует в системе и прив€зан к пользователю с логином ";
			for (String instance : getInstanceName())
			{
				links.addAll(links.size() , externalResourceService.getLinksByAccount(value, instance));
			}
	        warnMessage += setWarnMessage(links, value);
			setMessage(warnMessage);
			return (links.isEmpty());
		}
		catch (BusinessException e)
		{
			log.warn("Ќе удалось найти данных о введенном счете");
			return false;
		}
	}
}
