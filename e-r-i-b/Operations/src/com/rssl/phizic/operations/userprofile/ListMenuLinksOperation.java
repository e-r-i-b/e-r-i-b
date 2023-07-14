package com.rssl.phizic.operations.userprofile;

import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.UserPrincipal;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.menulinks.MenuLink;
import com.rssl.phizic.business.menulinks.MenuLinkInfo;
import com.rssl.phizic.business.menulinks.MenuLinkService;
import com.rssl.phizic.business.profileSynchronization.PersonSettingsManager;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.security.permissions.AccessSchemePermission;
import com.rssl.phizic.security.permissions.OperationPermission;
import com.rssl.phizic.security.permissions.ServicePermission;
import com.rssl.phizic.utils.ClassHelper;
import com.rssl.phizic.utils.MenuLinkConfig;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author lukina
 * @ created 19.05.2010
 * @ $Author$
 * @ $Revision$
 */

public class ListMenuLinksOperation extends OperationBase implements ListEntitiesOperation
{
	private static MenuLinkService linkService = new MenuLinkService();
	private static ExternalResourceService externalResourceService = new ExternalResourceService();
	private List<MenuLinkInfo> linksInfo;
	private AuthModule authModule;
	private boolean forEdit; // �������� ������ ��� ����������� ��� ��� ��������������
	private int linksCount; // ���������� ������ � ������� ����

	public void initialize(boolean isForEdit) throws BusinessException
	{
		forEdit = isForEdit;
		authModule = AuthModule.getAuthModule();
		linksCount = ConfigFactory.getConfig(MenuLinkConfig.class).getLinkCount();

		List<MenuLink> links = getMenuLinks();
		linksInfo = loadLinksInfo(links);
	}

	/**
	 * �������� ������ ������� �������� ����	 
	 * @throws BusinessException
	 */
	public List<MenuLinkInfo> getMenuLinksInfo() throws  BusinessException
	{
		return linksInfo;
	}

	/**
	 * ��������� ��� ��������� � �������� �������� ����
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void save() throws  BusinessException, BusinessLogicException
	{
		List<MenuLink> menuLinks = new ArrayList<MenuLink>();
		for(MenuLinkInfo linkInfo : linksInfo)
		{
			linkService.addOrUpdate(linkInfo.getLink());
			menuLinks.add(linkInfo.getLink());
		}
		PersonSettingsManager.savePersonData(PersonSettingsManager.MENU_LINKS_DATA_KEY, menuLinks);
	}

	/**
	 * ��������� ������
	 * @param sortIds - ������� �����������
	 * @param selectedIds - ����������/�� ���������� � �������
	 * @throws BusinessException
	 */
	public void save(String[] selectedIds, int[] sortIds) throws  BusinessException
	{
		List<MenuLinkInfo> list = new ArrayList<MenuLinkInfo>();
		List<MenuLink> menuLinks = new ArrayList<MenuLink>();
		List ids = Arrays.asList(selectedIds);
		int i =0;
		for(int id :sortIds)
		{
			i++;
			boolean isUse = ids.contains(String.valueOf(id));
			MenuLink link = findLink(id);
			if (link.getOrderInd() != i || link.isUse() != isUse)
			{
				link.setOrderInd(i);
				link.setUse(isUse);
				linkService.addOrUpdate(link);
			}
			if (isUse)
				list.add(makeMenuLinkInfo(link));
			menuLinks.add(link);
		}
		PersonContext.getPersonDataProvider().getPersonData().setMenuLinkInfoList(list);
		PersonSettingsManager.savePersonData(PersonSettingsManager.MENU_LINKS_DATA_KEY, menuLinks);
	}

	private List<MenuLink> getMenuLinks()
	{
		CommonLogin login = null;
		List<MenuLink> menuLinks = null;
		try
		{
			if (authModule != null) {
				UserPrincipal principal = authModule.getPrincipal();
				if (principal != null) {
					login = principal.getLogin();
					if (login != null)
						menuLinks = linkService.findByUserId(login);
				}
			}
		}
		catch (BusinessException e)
		{
			// ������ �� ������. ���� �� ������ �������� ������ ������� ���� �� ����, �������� �� ������
		}

		if(login == null)
			return Collections.emptyList();

		try
		{
			if(menuLinks == null || menuLinks.isEmpty())
			{
				menuLinks = createMenuLinks(login);
			}
			else if(menuLinks.size() < linksCount)
				menuLinks = updateMenuLinks(menuLinks,login);
		}
		catch (BusinessException e)
		{
			menuLinks = Collections.emptyList();
		}
		return menuLinks;
	}

	private List<MenuLink> createMenuLinks(CommonLogin login) throws BusinessException
	{
		List<MenuLink> links = new ArrayList<MenuLink>();
		for (int i=1; i<=linksCount; i++)
		{
			MenuLink link = new MenuLink();
			link.setLinkId(i);
			link.setLoginId(login.getId());
			link.setOrderInd(i);
			link.setUse(true);
			// ���� �������� ������ ������ ��� ��������������, ��
			// �������������� ��������� �� � ����
			if(forEdit)
				link = linkService.addOrUpdate(link);
			links.add(link);
		}
		return links;
	}

	/* ��������� ������ �� ������ �������� ���� � ����, � ������ ���� � ������� � ���� ������ �� �� ��� ������ */
	private List<MenuLink> updateMenuLinks(List<MenuLink> links, CommonLogin login) throws BusinessException
	{
		List<MenuLink> updatedLinks = new ArrayList<MenuLink>(links);
		//����� ������ ���� ����� ��������� � ����� �������� ����
		int orderInd = getMaxOrederLink(links);
		for (int i=1; i<=linksCount; i++)
		{
			if(!isLinkExist(links,i))
			{
				MenuLink link = new MenuLink();
				link.setLinkId(i);
				link.setLoginId(login.getId());
				link.setOrderInd(++orderInd);
				link.setUse(true);
				link = linkService.addOrUpdate(link);
				updatedLinks.add(link);
			}
		}
		return updatedLinks;
	}

	/*���������, ���� �� � ��� ������ �� ������ ����� ����*/
	private boolean isLinkExist(List<MenuLink> links,int linkId)
	{
		for(MenuLink link: links)
			if(linkId == link.getLinkId())
				return true;
		return false;
	}

	/*�������� ����� ���������� ������ ����*/
	private int getMaxOrederLink(List<MenuLink> links)
	{
		if(links.isEmpty())
			return 0;
		int maxOrder = links.get(0).getOrderInd();
		for(MenuLink link: links)
			if(link.getOrderInd()>maxOrder)
				maxOrder = link.getOrderInd();
		return maxOrder;
	}

	private List<MenuLinkInfo> loadLinksInfo(List<MenuLink> links) throws BusinessException
	{
		List<MenuLinkInfo> linksInfoList =  new ArrayList<MenuLinkInfo>();
		for(MenuLink link: links)
		{
			if(forEdit || link.isUse())
			{
				MenuLinkInfo linkInfo = makeMenuLinkInfo(link);
				if(linkInfo != null)
					linksInfoList.add(linkInfo);
			}
		}
		return linksInfoList;
	}

	private MenuLink findLink(long linkId) throws BusinessException
	{
		for(MenuLinkInfo linkInfo : linksInfo)
		{
			MenuLink link = linkInfo.getLink();
			if(link.getId().equals(linkId))
				return link;
		}
		throw new BusinessException("������ � id=" + linkId + " �� �������.");
	}

	/**
	 * ��������� ��������� ������ �������� ���� �� iccs.properties �� id
	 * ���������� ���������� �� ���������� ������ ����� ���� �� ���������� ���������:
	 *  1.  ��������� ����������� � ������, �������, �������� ��� �����������.
	 *      ���� ����� � iccs.properties ������� �������� ������ ��� ������ � ����������. ������: ��������� ���������� �� ������
	 *      ���� � ������������ ��� ������� � ������/�������/��������, �� ����� ���� ��� �� ������������.
	 *  2.  ���� ����� 1-�� ������ � ������� ���� ������ � ������� ������, �� ���������� ���������� ������ �� 3-� �������
     *      �.� ������������ �� ������ ���� ������� � negativeService.
     *        ������: ���� � ������������ ��� ������� � "��������� �������� ��� ����� � �������", �� ����� ���� ���������� ����������.
	 *      b.��������� ������� ��������� ������� ���� � �������. ���� � ������������ ���� �������� ������� ����, �� ����� ���������� ����������.
	 *      c.��������� ������� ������� � �������������� ��������. ���� ���� ������ ���� �� � ������, �� ����� ����������.
	 */
	private MenuLinkInfo makeMenuLinkInfo(MenuLink link) throws BusinessException
	{
		MenuLinkInfo linkInfo = new MenuLinkInfo(link);
		int linkId = link.getLinkId();
		String[] additionalServices = new String[]{};
		String[] services = new String[]{};
		String[] andOneOf = new String[]{};
		MenuLinkConfig config = ConfigFactory.getConfig(MenuLinkConfig.class);

		String module = config.getProperty(MenuLinkConfig.MAIN_MENU_LINK_MODULE + linkId);
		String serviceStr = config.getProperty(MenuLinkConfig.MAIN_MENU_LINK_SERVICE + linkId);
		if (StringHelper.isNotEmpty(serviceStr))
			services = serviceStr.split(MenuLinkConfig.DELIMITER);
		String negativeService = config.getProperty(MenuLinkConfig.MAIN_MENU_LINK_NEGATIVE_SERVICE + linkId);
		String additionalServiceStr = config.getProperty(MenuLinkConfig.MAIN_MENU_LINK_ADDITIONAL_SERVICE + linkId);
		if (StringHelper.isNotEmpty(additionalServiceStr))
			additionalServices = additionalServiceStr.split(MenuLinkConfig.DELIMITER);
		String operation = config.getProperty(MenuLinkConfig.MAIN_MENU_LINK_OPERATION + linkId);
		String resourceType = config.getProperty(MenuLinkConfig.MAIN_MENU_RESOURCE_TYPE + linkId);
		String isNovelty = config.getProperty(MenuLinkConfig.MAIN_MENU_LINK_NOVELTY + linkId);

        String andOneOfStr = config.getProperty(MenuLinkConfig.MAIN_MENU_AND_ONE_OF + linkId);
        if (StringHelper.isNotEmpty(andOneOfStr))
            andOneOf = andOneOfStr.split(MenuLinkConfig.DELIMITER);

		MenuLinkCondition condition = null;
		String conditionClassName = config.getProperty(MenuLinkConfig.MAIN_MENU_LINK_CONDITION + linkId);
		if (StringHelper.isNotEmpty(conditionClassName))
			condition = ClassHelper.newInstance(conditionClassName);
		if (!implies(module, services, operation))
			return null;

		boolean hasAccess = false;
        hasAccess = hasAccess || !checkNegativeService(negativeService);
        hasAccess = hasAccess || checkProductExist(resourceType);
		hasAccess = hasAccess || checkAdditionalService(additionalServices);
		hasAccess = hasAccess && checkOneOfService(andOneOf);
		hasAccess = hasAccess && ((condition == null) || (condition != null && condition.accept()));

		if (hasAccess)
		{
			linkInfo.setText(config.getProperty(MenuLinkConfig.MAIN_MENU_LINK_TEXT + linkId));
			linkInfo.setAction(config.getProperty(MenuLinkConfig.MAIN_MENU_LINK_ACTION + linkId));
			linkInfo.setActivity(config.getProperty(MenuLinkConfig.MAIN_MENU_LINK_ACTIVITY + linkId));
			linkInfo.setModule(module);
			linkInfo.setService(services.length == 1 ? services[0] : "");
			linkInfo.setOperation(operation);
			linkInfo.setNovelty(BooleanUtils.toBoolean(isNovelty));
			return linkInfo;
		}
		return null;
	}

	private boolean checkNegativeService(String serviceName)
	{
		if(StringHelper.isEmpty(serviceName))
			return true;
		return authModule.implies(new ServicePermission(serviceName));
	}

	private boolean checkProductExist(String resourceType)
	{
		if(StringHelper.isEmpty(resourceType))
			return true;
		try
		{
			Class resourceClazz = ResourceType.valueOf(resourceType).getResourceLinkClass();
			List links = externalResourceService.getInSystemLinks(authModule.getPrincipal().getLogin(),resourceClazz);
			return CollectionUtils.isNotEmpty(links);
		}
		catch (Exception e)
		{
			return false;
		}
	}

	private boolean checkAdditionalService(String[] additionalServices)
	{
		boolean hasServiceAccess = false;
		for(String serviceName : additionalServices)
			hasServiceAccess = hasServiceAccess || authModule.implies(new ServicePermission(serviceName));
		return hasServiceAccess;
	}

	private boolean checkOneOfService(String[] andOneOf)
	{
		if (andOneOf.length < 1)
            return true;
        boolean hasServiceAccess = false;
		for(String serviceName : andOneOf)
			hasServiceAccess = hasServiceAccess || authModule.implies(new ServicePermission(serviceName));
		return hasServiceAccess;
	}

	/** ��������� ������ ���� �� ���������
	 * @return true ���� ������ ��������
	 */
	private boolean implies(String module, String[] services, String operation)
	{
		if (StringHelper.isNotEmpty(module))
			return authModule.implies(new AccessSchemePermission(module));
		boolean hasAcces = false;
		if (services == null || services.length == 0)
			return true;
		if (services.length == 1)
		{
			String service = services[0];
			if (StringHelper.isNotEmpty(service) && StringHelper.isEmpty(operation))
				return authModule.implies(new ServicePermission(service, true));

			if (StringHelper.isNotEmpty(operation))
				return authModule.implies(new OperationPermission(operation, service));
		}
		else
		{
			for (String service : services)
			{
				if (StringHelper.isNotEmpty(service))
					hasAcces = hasAcces || authModule.implies(new ServicePermission(service));
			}
			return hasAcces;
		}
		return true;
	}

	/**
	 * ���������, ������� �� ������������ �������� �������� ����
	 * @param selectedLinksList ������ ��������������� ��������� ������� ����
	 * @param sortMenuLinks ����� ������� ����������� �������
	 * @return true, ���� ���� ���������
	 * @throws BusinessException
	 */
	public boolean checkChanges(List<String> selectedLinksList, int[] sortMenuLinks) throws BusinessException
	{
		List<MenuLinkInfo> savedList = this.getMenuLinksInfo();
		List<String> savedSelectedIds = new ArrayList<String>();
		int[] sortMenuLinksOld = new int[savedList.size()];
		int i = 0;
		for (MenuLinkInfo linkInfo : savedList)
		{
			if (linkInfo.getLink().isUse())
				savedSelectedIds.add(linkInfo.getLink().getId().toString());
			sortMenuLinksOld[i] = linkInfo.getLink().getId().intValue();
			i++;
		}
		return !selectedLinksList.equals(savedSelectedIds) || !Arrays.equals(sortMenuLinks, sortMenuLinksOld);
	}
}
