package com.rssl.phizic.business.documents.templates.service;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.impl.activity.TemplateTransferInformerBase;
import com.rssl.phizic.cache.CacheAction;
import com.rssl.phizic.cache.CacheHelper;
import com.rssl.phizic.cache.CacheProvider;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.documents.GateTemplate;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.payments.template.TemplateGateService;
import net.sf.ehcache.Cache;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import java.util.Collections;
import java.util.List;

/**
 * Сервис для работы с шаблонами
 *
 * @author khudyakov
 * @ created 27.03.2013
 * @ $Author$
 * @ $Revision$
 */
public class TemplateGateServiceImpl extends AbstractService implements TemplateGateService
{
	private static final String BUSINESS_TEMPLATE_CACHE_NAME            = "business-template-cache-name";

	private static final Cache cache = CacheProvider.getCache(BUSINESS_TEMPLATE_CACHE_NAME);
	private static final SimpleService simpleService = new SimpleService();


	public TemplateGateServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public GateTemplate findById(Long id) throws GateException
	{
		try
		{
			TemplateDocument template = simpleService.findById(TemplateDocument.class, id);
			if (template == null)
			{
				return null;
			}

			template.setActivityInfo(TemplateTransferInformerBase.getTemplateActivityInfo(template));
			return template;
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
	}

	public List<? extends GateTemplate> getAll(final Client client) throws GateException
	{
		//в приложении админа получаем шаблоны в обход кеша
		if (ApplicationConfig.getIt().getApplicationInfo().isAdminApplication())
		{
			return doGetAll(client);
		}

		try
		{
			return CacheHelper.getCachedEntity(cache, client.getInternalId().toString(), new CacheAction<List<? extends GateTemplate>>()
			{
				public List<? extends GateTemplate> getEntity() throws Exception
				{
					return doGetAll(client);
				}
			});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	private List<? extends GateTemplate> doGetAll(Client client) throws GateException
	{
		Long internalId = client.getInternalId();
		if (internalId == null)
		{
			throw new GateException("Не задан внутренний идентификатор клиента");
		}

		List<TemplateDocument> templates = getAll(internalId);
		if (CollectionUtils.isEmpty(templates))
		{
			return Collections.emptyList();
		}

		for (TemplateDocument template : templates)
		{
			template.setActivityInfo(TemplateTransferInformerBase.getTemplateActivityInfo(template));
		}
		return templates;
	}

	private List<TemplateDocument> getAll(Long internalId) throws GateException
	{
		try
		{
			DetachedCriteria criteria = DetachedCriteria.forClass(TemplateDocument.class)
					.add(Expression.eq("clientExternalId", internalId.toString()))
					.addOrder(Order.desc("templateInfo.orderInd"));

			return simpleService.find(criteria);
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
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
			cache.remove(templates.get(0).getClientOwner().getInternalId().toString());
			//обновляем/добавляем
			doAddOrUpdate(templates);
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
	}

	private void doAddOrUpdate(List<? extends GateTemplate> templates) throws BusinessException
	{
		simpleService.addOrUpdateListWithConstraintViolationException(templates);
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
			cache.remove(templates.get(0).getClientOwner().getInternalId().toString());
			//удаляем
			simpleService.removeList(templates);
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
	}
}
