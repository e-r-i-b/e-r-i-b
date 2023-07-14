package com.rssl.phizicgate.wsgate.services.template;

import com.rssl.phizgate.common.services.StubUrlWrapper;
import com.rssl.phizgate.common.ws.WSRequestHandler;
import com.rssl.phizgate.common.ws.WSRequestHandlerUtil;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.templates.ConfigImpl;
import com.rssl.phizic.business.documents.templates.Constants;
import com.rssl.phizic.business.documents.templates.service.ClientCacheKeyGenerator;
import com.rssl.phizic.business.persons.csa.ProfileHelper;
import com.rssl.phizic.business.persons.csa.ProfileService;
import com.rssl.phizic.cache.CacheAction;
import com.rssl.phizic.cache.CacheHelper;
import com.rssl.phizic.cache.CacheProvider;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.GUID;
import com.rssl.phizic.gate.csa.Profile;
import com.rssl.phizic.gate.documents.GateTemplate;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.template.TemplateGateService;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizicgate.wsgate.services.JAXRPCClientSideServiceBase;
import com.rssl.phizicgate.wsgate.services.template.generated.TemplateGateServiceImpl_Impl;
import com.rssl.phizicgate.wsgate.services.template.generated.holders.ListHolder;
import com.rssl.phizicgate.wsgate.services.template.logging.USCTJAXRPCLogHandler;
import com.rssl.phizicgate.wsgate.services.template.updaters.TemplateUpdater;
import com.rssl.phizicgate.wsgate.services.template.updaters.TemplateUpdaterImpl;
import net.sf.ehcache.Cache;
import org.apache.commons.collections.CollectionUtils;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Враппер к сервису работы с шаблонами
 *
 * @author khudyakov
 * @ created 05.02.14
 * @ $Author$
 * @ $Revision$
 */
public class TemplateGateServiceWrapper extends JAXRPCClientSideServiceBase implements TemplateGateService
{
	private static final Log log = PhizICLogFactory.getLog(LogModule.Core);
	private static final Cache cache = CacheProvider.getCache(Constants.GATE_TEMPLATE_CACHE_NAME);
	private static final ProfileService profileService = new ProfileService();

	private StubUrlWrapper<com.rssl.phizicgate.wsgate.services.template.generated.TemplateGateService> wrapper;

	public TemplateGateServiceWrapper(GateFactory factory)
	{
		super(factory);

		wrapper = new StubUrlWrapper<com.rssl.phizicgate.wsgate.services.template.generated.TemplateGateService>()
		{
			protected void createStub()
			{
				TemplateGateServiceImpl_Impl service = new TemplateGateServiceImpl_Impl();
				WSRequestHandlerUtil.addWSRequestHandlerToService(service, WSRequestHandler.class);
				WSRequestHandlerUtil.addWSRequestHandlerToService(service, USCTJAXRPCLogHandler.class);
				setStub(service.getTemplateGateServicePort());
			}

			protected String getUrl()
			{
				return ConfigFactory.getConfig(ConfigImpl.class).getWebServiceUrl();
			}
		};
	}

	public GateTemplate findById(Long id) throws GateException
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.template.generated.GateTemplate generated = wrapper.getStub().findById(id);
			if (generated == null)
			{
				return null;
			}

			return GateTemplateFactory.getInstance().create(generated);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public List<? extends GateTemplate> getAll(final Client client) throws GateException, GateLogicException
	{
		try
		{
			//в приложении сотрудника банка шаблоны не кешируем
			if (ApplicationConfig.getIt().getApplicationInfo().isAdminApplication())
			{
				final List<? extends GUID> history = ProfileHelper.getProfileHistory(client);
				if (history == null || CollectionUtils.isEmpty(history))
				{
					return Collections.emptyList();
				}

				return doGetAll(history);
			}
		}
		catch (BusinessLogicException e)
		{
			throw new GateLogicException(e);
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}

		try
		{
			final Pair<Profile, List<? extends GUID>> history = ProfileHelper.getStoredProfileHistory(client);
			if (history == null || CollectionUtils.isEmpty(history.getSecond()))
			{
				return Collections.emptyList();
			}

			return CacheHelper.getCachedEntity(cache, ClientCacheKeyGenerator.getKey(client), new CacheAction<List<? extends GateTemplate>>()
			{
				public List<? extends GateTemplate> getEntity() throws Exception
				{
					return doGetAll(history.getSecond());
				}
			});
		}
		catch (BusinessLogicException e)
		{
			throw new GateLogicException(e);
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
		catch (GateLogicException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	private List<? extends GateTemplate> doGetAll(List<? extends GUID> guids) throws GateException
	{
		List objects = null;

		try
		{
			objects = wrapper.getStub().getAll(CorrelationHelper.toGeneratedProfiles(guids));
			if (CollectionUtils.isEmpty(objects))
			{
				return Collections.emptyList();
			}
		}
		catch (RemoteException e)
		{
			throw new GateException(e);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}

		List<GateTemplate> result = new ArrayList<GateTemplate>(objects.size());
		for (Object object : objects)
		{
			try
			{
				result.add(GateTemplateFactory.getInstance().create(object));
			}
			catch (Exception e)
			{
				log.error("Ошибка обработки шаблона документа, шаблон не добавлен в список шаблонов клиента. ", e);
			}
		}
		return result;
	}

	public void addOrUpdate(List<? extends GateTemplate> templates) throws GateException
	{
		if (CollectionUtils.isEmpty(templates))
		{
			return;
		}

		try
		{
			//чистим кеш
			cache.remove(ClientCacheKeyGenerator.getKey(templates.get(0).getClientOwner()));
			//обновляем/добавляем
			doAddOrUpdate(templates);
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	private void doAddOrUpdate(List<? extends GateTemplate> templates) throws Exception
	{
		ListHolder holder = new ListHolder(CorrelationHelper.toGeneratedTemplates(templates));
		wrapper.getStub().addOrUpdate(holder);

		TemplateUpdater updater = new TemplateUpdaterImpl(holder.value);
		for (GateTemplate template : templates)
		{
			updater.update(template);
		}
	}

	public void remove(List<? extends GateTemplate> templates) throws GateException
	{
		if (CollectionUtils.isEmpty(templates))
		{
			return;
		}

		try
		{
			//чистим кеш
			cache.remove(ClientCacheKeyGenerator.getKey(templates.get(0).getClientOwner()));
			//удаляем шаблоны
			wrapper.getStub().remove(CorrelationHelper.toGeneratedTemplates(templates));
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}
}
