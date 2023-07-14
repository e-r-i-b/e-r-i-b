package com.rssl.phizic.operations.userprofile;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.business.pfr.PFRLink;
import com.rssl.phizic.business.pfr.PFRLinkService;
import com.rssl.phizic.business.profile.Profile;
import com.rssl.phizic.business.profile.ProfileService;
import com.rssl.phizic.business.profileSynchronization.products.ResourceInfoSynchronizationHelper;
import com.rssl.phizic.business.resources.external.*;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.context.PersonDataProvider;
import com.rssl.phizic.operations.OperationBase;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;

/**
 * @author kligina
 * @ created 09.02.2011
 * @ $Author$
 * @ $Revision$
 */

public class SetupProductsMainViewOperation extends OperationBase
{
	private static final ExternalResourceService externalService = new ExternalResourceService();
	private Map<Class<? extends EditableExternalResourceLink>, Set<? extends EditableExternalResourceLink>> changedResources =
						new HashMap<Class<? extends EditableExternalResourceLink>, Set<? extends EditableExternalResourceLink>>();
	
	private static final ProfileService profileService = new ProfileService();
	private static final PFRLinkService pfrLinkService = new PFRLinkService();

	private Profile profile;
	private List<AccountLink> accounts;
	private List<CardLink> cards;
	private List<LoanLink> loans;
	private List<DepoAccountLink> depoAccounts;
	private List<IMAccountLink> ima;
	private PFRLink pfrLink;

	public void initialize() throws BusinessException, BusinessLogicException
	{
		PersonDataProvider dataProvider = PersonContext.getPersonDataProvider();
		PersonData personData = dataProvider.getPersonData();
		Login login = personData.getPerson().getLogin();
		profile = personData.getProfile();

		accounts = externalService.getInSystemLinks(login, AccountLink.class);
		cards = externalService.getInSystemLinks(login, CardLink.class);
		loans = externalService.getInSystemLinks(login, LoanLink.class);
		depoAccounts = externalService.getInSystemLinks(login, DepoAccountLink.class);
		ima = externalService.getInSystemLinks(login, IMAccountLink.class);
		pfrLink = pfrLinkService.findByLoginId(login.getId());
	}

	/**
	 * �������� ��������� ������ �� ������� �������� � �� ������� � ������, ���� ��� ��������� ���� ��������
	 * @param sortedIds - ������ ������, ��������������� � ������� �� ����������� �� ������� ��������
	 * @param clazz - ����� ������
	 * @return - true, ���� ���� �������� ��������� ��� ������� ������
	 * @throws BusinessException
	 */
	public boolean saveLinksIfChanged(Integer[] sortedIds, Class clazz) throws BusinessException
	{
		boolean isChanged = false;

		List<EditableExternalResourceLink> links = new ArrayList<EditableExternalResourceLink>();

		if (clazz.equals(AccountLink.class))
			links.addAll(accounts);
		if (clazz.equals(CardLink.class))
			links.addAll(cards);
		if (clazz.equals(LoanLink.class))
			links.addAll(loans);
		if (clazz.equals(DepoAccountLink.class))
			links.addAll(depoAccounts);
		if (clazz.equals(IMAccountLink.class))
			links.addAll(ima);

		if (checkLinkPositionChanges(sortedIds, links))
			isChanged = true;

		if (isChanged)
			updateSettings(links, sortedIds);

		return isChanged;
	}

	/**
	 * ��������� �� ������� ����������� ������ �� ������� ��������
	 * @param sortedIds - ������ ��������������� ������ � ������� �� �����������
	 * @param links - ������ ������
	 * @return true, ���� ������� ����������� ���������
	 */
	public boolean checkLinkPositionChanges(Integer[] sortedIds, List<? extends EditableExternalResourceLink> links)
	{
		List<Integer> positionIds = new ArrayList<Integer>(links.size());
		for (EditableExternalResourceLink link : links)
		{
			positionIds.add(link.getId().intValue());
		}
		List<Integer> newPositionIds = Arrays.asList(sortedIds);

		return !positionIds.equals(newPositionIds);
	}

	/**
	 * �������� ���������� �����
	 * @return
	 * @throws BusinessException
	 */
	public List<AccountLink> getClientAccounts() throws BusinessException
	{
		return accounts;
	}

	/**
	 * �������� ���������� �����
	 * @return
	 * @throws BusinessException
	 */
	public List<CardLink> getClientCards() throws BusinessException
	{
		return cards;
	}

	/**
	 * �������� ���������� �������
	 * @return
	 * @throws BusinessException
	 */
	public List<LoanLink> getClientLoans() throws BusinessException
	{
		return loans;
	}

	/**
	 * �������� ���������� ����� ����
	 * @return
	 * @throws BusinessException
	 */
	public List<DepoAccountLink> getClientDepoAccounts() throws BusinessException
	{
		return depoAccounts;
	}

	/**
	 * �������� ���������� ������������� �����
	 * @return
	 * @throws BusinessException
	 */
	public List<IMAccountLink> getClientIMAccounts() throws BusinessException
	{
		return ima;
	}

	/**
	 * �������� �������� ���
	 * @return �������� ��� �������
	 */
	public PFRLink getClientPfrLink()
	{
		return pfrLink;
	}


	/**
	 * ���������� ����� �������� �������� ��������� �� ������� �������� ��� ���������
	 * @param sortedIds ������ ��������������� ������ � ������� �� ����������� � ������
	 */
	private void updateSettings(List<EditableExternalResourceLink> links, Integer[] sortedIds)
	{
		for (EditableExternalResourceLink link : links)
		{
			updateLinkPosition(link, sortedIds);
		}
	}

	/**
	 * �������� ��������� ������ �� ������� �������� � �� ������� � ������, ���� ��� ��������� ���� ��������
	 * @param selectedIds - ������ ��������������� ������� ���������
	 * @param sortedIds - ������ ������, ��������������� � ������� �� ����������� �� ������� ��������
	 * @param clazz - ����� ������
	 * @return - true, ���� ���� �������� ��������� ��� ������� ������
	 * @throws BusinessException
	 */
	public boolean saveLinksIfChanged(String[] selectedIds, Integer[] sortedIds, Class clazz) throws BusinessException
	{
		boolean isChanged = false;

		List<EditableExternalResourceLink> links = new ArrayList<EditableExternalResourceLink>();

		if (clazz.equals(AccountLink.class))
			links.addAll(accounts);
		if (clazz.equals(CardLink.class))
			links.addAll(cards);
		if (clazz.equals(LoanLink.class))
			links.addAll(loans);
		if (clazz.equals(DepoAccountLink.class))
			links.addAll(depoAccounts);
		if (clazz.equals(IMAccountLink.class))
			links.addAll(ima);

		if (checkLinkPositionChanges(sortedIds, links))
			isChanged = true;

		if (checkLinkChanged(links, selectedIds))
			isChanged = true;

		if (isChanged)
			updateSettings(links,selectedIds, sortedIds);

		return isChanged;
	}

	/**
	 * ���������� ����� �������� �������� ��������� �� ������� �������� ��� ���������
	 * @param selectedIds ������ ��������������� (id) ������� ���������
	 */
	private void updateSettings(List<EditableExternalResourceLink> links, String[] selectedIds, Integer[] sortedIds)
	{
		for (EditableExternalResourceLink link : links)
		{
			Long currentId = link.getId();
			Boolean state = link.getShowInMain();
			Boolean newState = null;

			for (String linkId : selectedIds)
			{
				if (currentId.equals(Long.valueOf(linkId)))
				{
					newState = true;
					break;
				}
			}
			if (newState == null)
				newState = false;

			if (!state.equals(newState))
			{
				link.setShowInMain(newState);
				addChangedResource(link);
			}
			updateLinkPosition(link, sortedIds);
		}
	}

	private void updateLinkPosition(EditableExternalResourceLink link, Integer[] sortedIds)
	{
		Integer i =0;
		for(int id : sortedIds)
		{
			i++;
			if (link.getId() == id)
			{
				if (link.getPositionNumber() == null || !link.getPositionNumber().equals(i))
					addChangedResource(link);
				link.setPositionNumber(i);
			}
		}
	}
	/**
	 * ���������� �� ��������� ��������� ������ �� ������� ��������
	 * @param links - ������ ������
	 * @param selectedIds - ������ ��������������� ������, ������� ������ ������������ �� ������� ��������
	 * @return true, ���� ��������� ��������� ���� ��������
	 */
	private boolean checkLinkChanged(List<? extends EditableExternalResourceLink> links, String[] selectedIds)
	{
		List<String> selectedLinksIds = Arrays.asList(selectedIds);
		List<String> savedLinks = new ArrayList<String>(links.size());
		for (EditableExternalResourceLink link : links)
		{
			if (link.getShowInMain())
				savedLinks.add(link.getId().toString());
		}
		return !(selectedLinksIds.containsAll(savedLinks) && selectedLinksIds.size() == savedLinks.size());
	}

	public void save() throws BusinessException
	{
		doSave();
		updateResourceSettings();
	}

	@Transactional
	private void doSave() throws BusinessException
	{
		for (Class key : changedResources.keySet())
		{
			Set<? extends EditableExternalResourceLink> list = changedResources.get(key);
			if (CollectionUtils.isEmpty(list))
				continue;
			for (EditableExternalResourceLink link : list)
				externalService.updateLink(link);
		}

		profileService.update(profile);
		if (pfrLink != null)
			pfrLinkService.addOrUpdate(pfrLink);
	}

	private void updateResourceSettings()
	{
		for (Class key : changedResources.keySet())
		{
			ResourceInfoSynchronizationHelper.updateResourceInfo(key);
		}
		if (pfrLink != null)
			ResourceInfoSynchronizationHelper.updateResourceInfo(PFRLink.class);
	}

	private <T extends EditableExternalResourceLink> void addChangedResource(T resource)
	{
		Class clazz = resource.getClass();
		Set list = changedResources.get(clazz);
		if (list == null)
		{
			list = new HashSet();
			changedResources.put(clazz, list);
		}
		list.add(resource);
	}

	/**
	 * ���������� ����� �������� �������� ��������� ��� �������� ���
	 * @param showInMain-����� �������� ���������  isShowInMain
	 */
	public void updatePFRLink(boolean showInMain)
	{
		pfrLink.setShowInMain(showInMain);
	}
}
