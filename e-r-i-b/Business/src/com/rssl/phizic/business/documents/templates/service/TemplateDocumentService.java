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
 * ������ ��� ������ � ��������� ����������
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
	 * @return ������� �������
	 */
	public static TemplateDocumentService getInstance()
	{
		return INSTANCE;
	}

	/**
	 * �������� ������ �������� ���������� � ������������ � ��������������� ������� ��������
	 * @param client ������
	 * @param filters �������
	 * @return ������ �������� ����������
	 * @throws BusinessException
	 */
	public List<TemplateDocument> getFiltered(Client client, List<TemplateDocumentFilter> filters) throws BusinessException, BusinessLogicException
	{
		return getFiltered(client, filters.toArray(new TemplateDocumentFilter[filters.size()]));
	}
	/**
	 * �������� ������ �������� ���������� � ������������ � ��������������� ������� ��������
	 * @param client ������
	 * @param filters �������
	 * @return ������ �������� ����������
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
	 * �������� ������ �������� ���������� � ������������ � ��������������� ������� �������� + ���������
	 * @param client ������
	 * @param currentPage ����� ������� ��������
	 * @param itemsPerPage ���������� ��������� �� ��������
	 * @param filters �������
	 * @return ������ �������� ����������
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
	 * ����� ���� ������
	 * @param client ������
	 * @param filters ������ ��������
	 * @return ������
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
			throw new BusinessException("�� �������� ����������� ���������� ������� " + templates.size() + " �������(��)");
		}

		return templates.get(0);
	}

	/**
	 * ����� ������� �� id
	 * @param id �������������
	 * @return ������
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
	 * ����� ���������� �������� �������
	 *
	 * @param client ������
	 * @param ids �������������� ��������
	 * @return ������ ��������
	 */
	public List<TemplateDocument> findByIds(Client client, Collection<Long> ids) throws BusinessException, BusinessLogicException
	{
		return getFiltered(client, new IdsTemplatesFilter(ids));
	}

	/**
	 * ����� ��� ������� �������
	 *
	 * @param client ������
	 * @return ������ ��������
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
	 * ����� ������� ������� �� �������� �������
	 *
	 * @param client ������
	 * @param info ���������� �� �������
	 * @return ������
	 */
	public TemplateDocument findByTemplateName(Client client, TemplateInfo info) throws BusinessException, BusinessLogicException
	{
		return findByTemplateName(client, info.getName());
	}

	/**
	 * ����� ������� ������� �� �������� �������
	 *
	 * @param client ������
	 * @param templateName �������� �������
	 * @return ������
	 */
	public TemplateDocument findByTemplateName(Client client, String templateName) throws BusinessException, BusinessLogicException
	{
		return findSingle(client, new NameTemplateFilter(templateName));
	}

	/**
	 * ����� ������� ������� �� �������� � ������
	 *
	 * @param client �������
	 * @param templateName �������� �������
	 * @param channelType ����� �������������
	 * @return ������
	 */
	public TemplateDocument findByTemplateNameInChannel(Client client, String templateName, CreationType channelType) throws BusinessException, BusinessLogicException
	{
		return findSingle(client, new NameTemplateFilter(templateName), new ChannelActivityTemplateFilter(channelType));
	}

	/**
	 * ����� �������� ������� ������ ������
	 *
	 * @param client �������
	 * @param channelType ��� ������
	 * @return ������ ��������
	 * @throws BusinessException
	 */
	public List<TemplateDocument> findByChannel(Client client, CreationType channelType) throws BusinessException, BusinessLogicException
	{
		return getFiltered(client, new ChannelActivityTemplateFilter(channelType));
	}

	/**
	 * ����� �������� ������� ������ ������ �� ����������
	 *
	 * @param client ������
	 * @param channelType ��� ������
	 * @param receiverId - id ����������
	 * @return ������ ��������
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
	 * ����� �������� ������� ������ ������ �� ������� ��������
	 *
	 * @param client ������
	 * @param channelType ��� ������
	 * @param chargeOffResource ������ ��������
	 * @return ������ ��������
	 * @throws BusinessException
	 */
	public List<TemplateDocument> findByChargeOffResourceInChannel(Client client, CreationType channelType, String chargeOffResource) throws BusinessException, BusinessLogicException
	{
		return getFiltered(client, new ChannelActivityTemplateFilter(channelType), new ChargeOffResourceTemplateFilter(chargeOffResource));
	}

	/**
	 * ����� �������� ������� ��������� ��� ������ ������ ������ �� ���� �����
	 *
	 * @param client �������
	 * @param channelType ��� ������
	 * @param formType ��� �����
	 * @return ������ ��������
	 */
	public List<TemplateDocument> findByFormTypeInChannel(Client client, CreationType channelType, FormType formType) throws BusinessException, BusinessLogicException
	{
		return getFiltered(client, new ChannelActivityTemplateFilter(channelType), new FormNameTemplateFilter(formType), new ReadyToPaymentTemplateFilter());
	}

	/**
	 * ����� �������� �������, ��������� ��� ������ ������ ���-������
	 * @param client ������
	 * @param smsCommands ������ ���-������
	 * @return ������ ��������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public List<TemplateDocument> findReadyToPaymentInSmsChannel(Client client, Set<String> smsCommands) throws BusinessException, BusinessLogicException
	{
		return getFiltered(client, new ChannelActivityTemplateFilter(CreationType.sms), new ReadyToPaymentTemplateFilter(), new SmsChannelTemplateNameFilter(smsCommands));
	}

	/**
	 * ���������� �������, ������� ������ ��������, ����� ��, ��� � templateName (like templateName+"%")
	 *
	 * @param client ��������� �������
	 * @param templateName �������� �������
	 * @return ������ ��������
	 * @throws BusinessException
	 */
	public List<TemplateDocument> getResemblingNames(Client client, String templateName) throws BusinessException, BusinessLogicException
	{
		return getFiltered(client, new ResemblingNameTemplateFilter(templateName));
	}

	/**
	 * ����� ����������� �������� � ������ ���������
	 *
	 * @param client ������
	 * @param currentPage ����� ������� ��������
	 * @param itemsPerPage ���������� ��������� �� ��������
	 * @return ������ ��������
	 */
	public List<TemplateDocument> findAllExceptDeletedWithPagination(Client client, int currentPage, int itemsPerPage) throws BusinessException, BusinessLogicException
	{
		return getWithPagination(client, currentPage, itemsPerPage, Collections.<TemplateDocumentFilter>singletonList(new ActiveTemplateFilter()));
	}

	/**
	 * ����� ���� ����������� ��������
	 *
	 * @param client ������
	 * @return ������ ��������
	 * @throws BusinessException
	 */
	public List<TemplateDocument> findAllExceptDeleted(Client client) throws BusinessException, BusinessLogicException
	{
		return getFiltered(client, new ActiveTemplateFilter());
	}

	/**
	 * ����� ���� �������� ��� ������� ����
	 *
	 * @param client ������
	 * @return ������ ��������
	 * @throws BusinessException
	 */
	public List<TemplateDocument> findForPersonalMenu(Client client) throws BusinessException, BusinessLogicException
	{
		return getFiltered(client, new ChannelActivityTemplateFilter(getCurrentChannelType()), new ActiveTemplateFilter());
	}

	/**
	 * @return ������� ����� �������� �������
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
		throw new IllegalArgumentException("������������ ����� ��������� ��������");
	}


	/**
	 * �������� ������ ��������
	 * @param templates ������ ��������
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
	 * �������� ������ ��������
	 * @param templates ������ ��������
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
	 * ������� ������ ��������
	 * @param templates ������ ��������
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
	 * ������� ������ ��������
	 * @param templates ������ ��������
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
