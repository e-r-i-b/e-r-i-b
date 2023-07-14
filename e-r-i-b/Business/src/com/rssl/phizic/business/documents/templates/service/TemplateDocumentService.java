package com.rssl.phizic.business.documents.templates.service;

import com.rssl.common.forms.doc.CreationType;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.service.filters.*;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.documents.GateTemplate;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.template.TemplateGateService;
import com.rssl.phizic.gate.payments.template.TemplateInfo;
import com.rssl.phizic.utils.ListUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;

import java.util.*;

/**
 * Сервис для работы с шаблонами документов
 *
 * @author khudyakov
 * @ created 05.02.14
 * @ $Author$
 * @ $Revision$
 */
public class TemplateDocumentService
{
	private static final TemplateDocumentService INSTANCE = new TemplateDocumentService();

	/**
	 * @return инстанс сервиса
	 */
	public static TemplateDocumentService getInstance()
	{
		return INSTANCE;
	}

	/**
	 * Получить список шаблонов документов в соответствии с соответствующим набором фильтров
	 * @param client клиент
	 * @param filters фильтры
	 * @return список шаблонов документов
	 * @throws BusinessException
	 */
	public List<TemplateDocument> getFiltered(Client client, List<TemplateDocumentFilter> filters) throws BusinessException, BusinessLogicException
	{
		return getFiltered(client, filters.toArray(new TemplateDocumentFilter[filters.size()]));
	}
	/**
	 * Получить список шаблонов документов в соответствии с соответствующим набором фильтров
	 * @param client клиент
	 * @param filters фильтры
	 * @return список шаблонов документов
	 * @throws BusinessException
	 */
	public List<TemplateDocument> getFiltered(Client client, TemplateDocumentFilter ... filters) throws BusinessException, BusinessLogicException
	{
		List<TemplateDocument> templates = getAll(client);
		if (CollectionUtils.isEmpty(templates))
		{
			return Collections.emptyList();
		}

		if (ArrayUtils.isEmpty(filters))
		{
			return templates;
		}

		CollectionUtils.filter(templates, new TemplateDocumentFilterConjunction(filters));
		return templates;
	}

	/**
	 * Получить список шаблонов документов в соответствии с соответствующим набором фильтров + пагинация
	 * @param client клиент
	 * @param currentPage номер текущей страницы
	 * @param itemsPerPage количество элементов на странице
	 * @param filters фильтры
	 * @return список шаблонов документов
	 * @throws BusinessException
	 */
	public List<TemplateDocument> getWithPagination(Client client, int currentPage, int itemsPerPage, List<TemplateDocumentFilter> filters) throws BusinessException, BusinessLogicException
	{
		List<TemplateDocument> templates = getFiltered(client, filters);
		if (CollectionUtils.isEmpty(templates))
		{
			return Collections.emptyList();
		}

		List<TemplateDocument> result = new ArrayList<TemplateDocument>();
		int index = 1;

		for (TemplateDocument template : templates)
		{
			if (index > currentPage * itemsPerPage && result.size() <= itemsPerPage + 1)
			{
				result.add(template);
			}
			index++;
			if(result.size() > itemsPerPage)
				break;
		}
		return result;
	}

	/**
	 * Найти один шаблон
	 * @param client клиент
	 * @param filters список фильтров
	 * @return шаблон
	 * @throws BusinessException
	 */
	public TemplateDocument findSingle(Client client, TemplateDocumentFilter ... filters) throws BusinessException, BusinessLogicException
	{
		List<TemplateDocument> templates = getFiltered(client, filters);
		if (CollectionUtils.isEmpty(templates))
		{
			return null;
		}

		if (templates.size() > 1)
		{
			throw new BusinessException("По заданным парамметрам фильтрации найдено " + templates.size() + " шаблона(ов)");
		}

		return templates.get(0);
	}

	/**
	 * Поиск шаблона по id
	 * @param id идентификатор
	 * @return шаблон
	 */
	public TemplateDocument findById(Long id) throws BusinessException
	{
		try
		{
			TemplateGateService service = GateSingleton.getFactory().service(TemplateGateService.class);
			return (TemplateDocument) service.findById(id);
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Поиск конкретных шаблонов клиента
	 *
	 * @param client клиент
	 * @param ids идентификаторы шаблонов
	 * @return список шаблонов
	 */
	public List<TemplateDocument> findByIds(Client client, Collection<Long> ids) throws BusinessException, BusinessLogicException
	{
		return getFiltered(client, new IdsTemplatesFilter(ids));
	}

	/**
	 * Найти все шаблоны клиента
	 *
	 * @param client клиент
	 * @return список шаблонов
	 */
	public List<TemplateDocument> getAll(Client client) throws BusinessException, BusinessLogicException
	{
		TemplateGateService service = GateSingleton.getFactory().service(TemplateGateService.class);

		try
		{
			//noinspection unchecked
			return new ArrayList<TemplateDocument>((List<TemplateDocument>) service.getAll(client));
		}
		catch (GateLogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Поиск шаблона клиента по названию шаблона
	 *
	 * @param client клиент
	 * @param info информация по шаблону
	 * @return шаблон
	 */
	public TemplateDocument findByTemplateName(Client client, TemplateInfo info) throws BusinessException, BusinessLogicException
	{
		return findByTemplateName(client, info.getName());
	}

	/**
	 * Поиск шаблона клиента по названию шаблона
	 *
	 * @param client клиент
	 * @param templateName название шаблона
	 * @return шаблон
	 */
	public TemplateDocument findByTemplateName(Client client, String templateName) throws BusinessException, BusinessLogicException
	{
		return findSingle(client, new NameTemplateFilter(templateName));
	}

	/**
	 * Поиск шаблона клиента по названию в канале
	 *
	 * @param client клиента
	 * @param templateName название шаблона
	 * @param channelType канал использования
	 * @return шаблон
	 */
	public TemplateDocument findByTemplateNameInChannel(Client client, String templateName, CreationType channelType) throws BusinessException, BusinessLogicException
	{
		return findSingle(client, new NameTemplateFilter(templateName), new ChannelActivityTemplateFilter(channelType));
	}

	/**
	 * Поиск шаблонов клиента внутри канала
	 *
	 * @param client клиента
	 * @param channelType тип канала
	 * @return список шаблонов
	 * @throws BusinessException
	 */
	public List<TemplateDocument> findByChannel(Client client, CreationType channelType) throws BusinessException, BusinessLogicException
	{
		return getFiltered(client, new ChannelActivityTemplateFilter(channelType));
	}

	/**
	 * Поиск шаблонов клиента внутри канала по получателю
	 *
	 * @param client клиент
	 * @param channelType тип канала
	 * @param receiverId - id получателя
	 * @return список шаблонов
	 * @throws BusinessException
	 */
	public List<TemplateDocument> findByReceiverIdInChannel(Client client, CreationType channelType, Long receiverId) throws BusinessException, BusinessLogicException
	{
		List<TemplateDocument> templates = getFiltered(client, new ChannelActivityTemplateFilter(channelType), new ReadyToPaymentTemplateFilter());
		if (CollectionUtils.isEmpty(templates))
		{
			return Collections.emptyList();
		}

		List<TemplateDocument> result = new ArrayList<TemplateDocument>();
		for (TemplateDocument template : templates)
		{
			if (receiverId.equals(template.getReceiverInternalId()))
			{
				result.add(template);
			}
		}
		return result;
	}

	/**
	 * Поиск шаблонов клиента внутри канала по ресурсу списания
	 *
	 * @param client клиент
	 * @param channelType тип канала
	 * @param chargeOffResource рескрс списания
	 * @return список шаблонов
	 * @throws BusinessException
	 */
	public List<TemplateDocument> findByChargeOffResourceInChannel(Client client, CreationType channelType, String chargeOffResource) throws BusinessException, BusinessLogicException
	{
		return getFiltered(client, new ChannelActivityTemplateFilter(channelType), new ChargeOffResourceTemplateFilter(chargeOffResource));
	}

	/**
	 * Поиск шаблонов клиента доступных для оплаты внутри канала по типу формы
	 *
	 * @param client клиента
	 * @param channelType тип канала
	 * @param formType тип формы
	 * @return список шаблонов
	 */
	public List<TemplateDocument> findByFormTypeInChannel(Client client, CreationType channelType, FormType formType) throws BusinessException, BusinessLogicException
	{
		return getFiltered(client, new ChannelActivityTemplateFilter(channelType), new FormNameTemplateFilter(formType), new ReadyToPaymentTemplateFilter());
	}

	/**
	 * Поиск шаблонов клиента, доступных для оплаты внутри смс-канала
	 * @param client клиент
	 * @param smsCommands список смс-команд
	 * @return список шаблонов
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public List<TemplateDocument> findReadyToPaymentInSmsChannel(Client client, Set<String> smsCommands) throws BusinessException, BusinessLogicException
	{
		return getFiltered(client, new ChannelActivityTemplateFilter(CreationType.sms), new ReadyToPaymentTemplateFilter(), new SmsChannelTemplateNameFilter(smsCommands));
	}

	/**
	 * Возвращает шаблоны, имеющие корень названия, такой же, как и templateName (like templateName+"%")
	 *
	 * @param client владельца шаблона
	 * @param templateName название шаблона
	 * @return список шаблонов
	 * @throws BusinessException
	 */
	public List<TemplateDocument> getResemblingNames(Client client, String templateName) throws BusinessException, BusinessLogicException
	{
		return getFiltered(client, new ResemblingNameTemplateFilter(templateName));
	}

	/**
	 * Поиск неудаленных шаблонов с учетом пагинации
	 *
	 * @param client клиент
	 * @param currentPage номер текущей страницы
	 * @param itemsPerPage количество элементов на странице
	 * @return список шаблонов
	 */
	public List<TemplateDocument> findAllExceptDeletedWithPagination(Client client, int currentPage, int itemsPerPage) throws BusinessException, BusinessLogicException
	{
		return getWithPagination(client, currentPage, itemsPerPage, Collections.<TemplateDocumentFilter>singletonList(new ActiveTemplateFilter()));
	}

	/**
	 * Поиск всех неудаленных шаблонов
	 *
	 * @param client клиент
	 * @return список шаблонов
	 * @throws BusinessException
	 */
	public List<TemplateDocument> findAllExceptDeleted(Client client) throws BusinessException, BusinessLogicException
	{
		return getFiltered(client, new ActiveTemplateFilter());
	}

	/**
	 * Поиск всех шаблонов для правого меню
	 *
	 * @param client клиент
	 * @return список шаблонов
	 * @throws BusinessException
	 */
	public List<TemplateDocument> findForPersonalMenu(Client client) throws BusinessException, BusinessLogicException
	{
		return getFiltered(client, new ChannelActivityTemplateFilter(getCurrentChannelType()), new ActiveTemplateFilter());
	}

	/**
	 * @return текущий канал создания шаблона
	 */
	public CreationType getCurrentChannelType()
	{
		ApplicationInfo applicationInfo = ApplicationConfig.getIt().getApplicationInfo();
		if (applicationInfo.isWeb())
		{
			return CreationType.internet;
		}
		if (applicationInfo.isSMS())
		{
			return CreationType.sms;
		}
		if (applicationInfo.isATM())
		{
			return CreationType.atm;
		}
		if (applicationInfo.isMobileApi())
		{
			return CreationType.mobile;
		}
		throw new IllegalArgumentException("Некорректный канал получения шаблонов");
	}


	/**
	 * Обновить список шаблонов
	 * @param templates список шаблонов
	 */
	public void addOrUpdate(TemplateDocument ... templates) throws BusinessException
	{
		if (ArrayUtils.isEmpty(templates))
		{
			return;
		}

		addOrUpdate(ListUtil.fromArray(templates));
	}

	/**
	 * Обновить список шаблонов
	 * @param templates список шаблонов
	 */
	public void addOrUpdate(List<? extends GateTemplate> templates) throws BusinessException
	{
		try
		{
			TemplateGateService service = GateSingleton.getFactory().service(TemplateGateService.class);
			service.addOrUpdate(templates);
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Удалить список шаблонов
	 * @param templates список шаблонов
	 */
	public void remove(TemplateDocument ... templates) throws BusinessException
	{
		if (ArrayUtils.isEmpty(templates))
		{
			return;
		}

		remove(ListUtil.fromArray(templates));
	}

	/**
	 * Удалить список шаблонов
	 * @param templates список шаблонов
	 */
	public void remove(List<? extends GateTemplate> templates) throws BusinessException
	{
		try
		{
			TemplateGateService service = GateSingleton.getFactory().service(TemplateGateService.class);
			service.remove(templates);
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
	}
}
