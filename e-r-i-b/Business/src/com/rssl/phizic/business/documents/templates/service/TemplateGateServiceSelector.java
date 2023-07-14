package com.rssl.phizic.business.documents.templates.service;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.templates.ConfigImpl;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.documents.GateTemplate;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.impl.SimpleServiceCreator;
import com.rssl.phizic.gate.payments.template.TemplateGateService;

import java.util.List;

/**
 * —ервис-селектор дл€ работы с шаблонами клиентов
 *
 * @author khudyakov
 * @ created 20.01.14
 * @ $Author$
 * @ $Revision$
 */
public class TemplateGateServiceSelector extends AbstractService implements TemplateGateService
{
	private static final String GATE_DELEGATE           = ".gate";
	private static final String BUSINESS_DELEGATE       = ".business";

	private static final Object MONITOR = new Object();

	private static volatile TemplateGateService gateService;
	private static volatile TemplateGateService businessService;


	public TemplateGateServiceSelector(GateFactory factory) throws BusinessException
	{
		super(factory);
	}

	public GateTemplate findById(Long id) throws GateException
	{
		return getDelegate().findById(id);
	}

	public List<? extends GateTemplate> getAll(Client client) throws GateException, GateLogicException
	{
		return getDelegate().getAll(client);
	}

	public void addOrUpdate(List<? extends GateTemplate> templates) throws GateException
	{
		getDelegate().addOrUpdate(templates);
	}

	public void remove(List<? extends GateTemplate> templates) throws GateException
	{
		getDelegate().remove(templates);
	}

	private TemplateGateService getDelegate() throws GateException
	{
		ConfigImpl config = ConfigFactory.getConfig(ConfigImpl.class);
		if (config.isUSCTEnabled())
		{
			return getGateService();
		}
		return getBusinessService();
	}

	private TemplateGateService getGateService() throws GateException
	{
		if (gateService == null)
		{
			synchronized (MONITOR)
			{
				if (gateService == null)
				{
					try
					{
						PropertyReader reader = ConfigFactory.getReaderByFileName("gate.properties");
						gateService = (TemplateGateService) new SimpleServiceCreator().createService(getServiceClassName(reader, GATE_DELEGATE), getFactory());
					}
					catch (GateException e)
					{
						throw new GateException(e);
					}
				}
			}
		}
		return gateService;
	}

	private TemplateGateService getBusinessService() throws GateException
	{
		if (businessService == null)
		{
			synchronized (MONITOR)
			{
				if (businessService == null)
				{
					try
					{
						PropertyReader reader = ConfigFactory.getReaderByFileName("gate.properties");
						businessService = (TemplateGateService) new SimpleServiceCreator().createService(getServiceClassName(reader, BUSINESS_DELEGATE), getFactory());
					}
					catch (GateException e)
					{
						throw new GateException(e);
					}
				}
			}
		}
		return businessService;
	}

	private String getServiceClassName(PropertyReader reader, String postfix)
	{
		return reader.getProperty(TemplateGateService.class.getName() + postfix);
	}
}
