package com.rssl.phizic.operations.longoffers.links;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.cache.BusinessWaitCreateCacheException;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedDepartment;
import com.rssl.phizic.business.longoffer.LongOfferServiceHelper;
import com.rssl.phizic.business.longoffer.links.LongOfferLinksService;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.resources.external.*;
import com.rssl.phizic.common.types.client.CreationType;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.GateWaitCreateCacheException;
import com.rssl.phizic.gate.longoffer.LongOffer;
import com.rssl.phizic.gate.longoffer.LongOfferService;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ListLongOfferLinksOperation extends OperationBase implements ListEntitiesOperation
{
	private static final DepartmentService departmentService = new DepartmentService();
	private static final ExternalResourceService resourceService = new ExternalResourceService();
	private static final LongOfferLinksService longOfferLinksService = new LongOfferLinksService();

	private List<LongOfferLink> links = new ArrayList<LongOfferLink>();

	private boolean isUseStoredResource;

	public void initialize() throws BusinessException, BusinessLogicException
	{
		ActivePerson person = PersonContext.getPersonDataProvider().getPersonData().getPerson();
		Client client = person.asClient();
		ExtendedDepartment department = (ExtendedDepartment) client.getOffice();

		if (department.isEsbSupported() && CreationType.UDBO == person.getCreationType())
		{
			try
			{
				links = longOfferLinksService.findByUserId(person.getLogin());

				//запрашиваем информацию по длительным поручениям
				LongOfferService longOfferService = GateSingleton.getFactory().service(LongOfferService.class);
				for (LongOffer longOffer: longOfferService.getClientsLongOffers(client))
				{
					//если линк не существует, то добавляем информацию по нему
					LongOfferLink longOfferLink = LongOfferServiceHelper.findLongOfferLinkByEternalId(links, longOffer.getExternalId());
					if (longOfferLink == null)
					{
						longOfferLink = new LongOfferLink();
						longOfferLink.setExternalId(longOffer.getExternalId());
						longOfferLink.setChargeOffAccount(longOffer.getAccountNumber());
						longOfferLink.setChargeOffCard(longOffer.getCardNumber());
						resourceService.addLongOfferLink(person.getLogin(),longOffer);
					}
					else
					{
						links.remove(longOfferLink);
						longOfferLink.setExternalId(longOffer.getExternalId());
						longOfferLink.setChargeOffAccount(longOffer.getAccountNumber());
						longOfferLink.setChargeOffCard(longOffer.getCardNumber());
						resourceService.addOrUpdateLink(longOfferLink);
					}

					StoredResourceHelper.updateStoredResource(longOfferLink, longOffer);
				}

				//удаляем оставшиеся линки из БД, т.к. по ним не пришло информации
				for (LongOfferLink link: links)
				{
					resourceService.removeLink(link);
				}

				//актуализируем список линков
				links = longOfferLinksService.findByUserId(person.getLogin());
			}
			catch (GateException e)
			{
				throw new BusinessException(e);
			}
			catch (GateWaitCreateCacheException e)
			{
				throw new BusinessWaitCreateCacheException(e.getMessage(), e);
			}
			catch (GateLogicException e)
			{
				throw new BusinessLogicException(e);
			}
		}
	}

	private void removeUnuseableLinks(String account)
	{
		if (CollectionUtils.isEmpty(links))
			return;

		Iterator<LongOfferLink> iter = links.iterator();
		while (iter.hasNext())
		{
			LongOfferLink link = iter.next();
			if (!StringHelper.equals(link.getChargeOffAccount(), account) &&
					!StringHelper.equals(link.getChargeOffCard(), account))
			{
				iter.remove();
			}
		}
	}

	public void initialize(CardLink cardLink) throws BusinessException, BusinessLogicException
	{
		initialize();
		removeUnuseableLinks(cardLink.getCard().getNumber());
	}

	public void initialize(AccountLink accountLink) throws BusinessException, BusinessLogicException
	{
		initialize();
		removeUnuseableLinks(accountLink.getAccount().getNumber());
	}

	public void initialize(Long productId, boolean isCard) throws BusinessException, BusinessLogicException
	{
		if (productId == null)
		{
			initialize();
		}
		else if (isCard)
		{
			CardLink cardLink = PersonContext.getPersonDataProvider().getPersonData().getCard(productId);
			initialize(cardLink);
		}
		else
		{
			AccountLink accountLink = PersonContext.getPersonDataProvider().getPersonData().getAccount(productId);
			initialize(accountLink);
		}
	}

	/**
	 * @return список линков длительных поручений
	 */
	public List<LongOfferLink> getEntity()
	{
		for (LongOfferLink link : links)
		{
			isUseStoredResource = (link.getValue() instanceof StoredLongOffer);
			if (isUseStoredResource)
			{
				break;
			}
		}

		return Collections.unmodifiableList(links);
	}

	public boolean isUseStoredResource()
	{
		return isUseStoredResource;
	}
}