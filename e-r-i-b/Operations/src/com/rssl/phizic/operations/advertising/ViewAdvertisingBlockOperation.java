package com.rssl.phizic.operations.advertising;

import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.advertising.AdvertisingAvailability;
import com.rssl.phizic.business.advertising.AdvertisingBlock;
import com.rssl.phizic.business.advertising.AdvertisingOrderedFieldComparator;
import com.rssl.phizic.business.advertising.AdvertisingService;
import com.rssl.phizic.business.bki.CreditProfileService;
import com.rssl.phizic.business.bki.PersonCreditProfile;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.productRequirements.AccTypesRequirement;
import com.rssl.phizic.business.dictionaries.productRequirements.ProductRequirement;
import com.rssl.phizic.business.dictionaries.productRequirements.ProductRequirementType;
import com.rssl.phizic.business.dictionaries.productRequirements.RequirementState;
import com.rssl.phizic.business.resources.external.*;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.security.permissions.OperationPermission;
import com.rssl.phizic.utils.ClientConfig;
import com.rssl.phizic.utils.DateHelper;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;

/**
 * @author lukina
 * @ created 23.12.2011
 * @ $Author$
 * @ $Revision$
 */

public class ViewAdvertisingBlockOperation extends OperationBase
{
	private static final AdvertisingService advertisingService = new  AdvertisingService();
	private static final DepartmentService departmentService = new DepartmentService();
	protected static final ExternalResourceService externalResourceService = new ExternalResourceService();
	private final CreditProfileService creditProfileService = new CreditProfileService();

	private AdvertisingBlock advertisingBlock;
	private List<Long> bannersList;
	private Person person;
	private Login login;

    /**
     * Инициализация операции для основного приложения
     * @param currentId
     * @throws Exception
     */
    public void initialize(Long currentId) throws Exception
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		person = personData.getPerson();
		login = person.getLogin();
		String tb = departmentService.getNumberTB(  person.getDepartmentId() );
		Calendar date = DateHelper.getCurrentDate();

		bannersList = personData.getBannersList();
		if (CollectionUtils.isEmpty(bannersList))
		{
			bannersList = advertisingService.getBunnersList(date, tb);

			if (CollectionUtils.isNotEmpty(bannersList))
				applyRequirements(bannersList);

			int maxBannerCount = ConfigFactory.getConfig(ClientConfig.class).getMaxBannerCount();
			if (bannersList.size() > maxBannerCount)
				bannersList = bannersList.subList(0, maxBannerCount);

			personData.setBannersList(bannersList);
		}

		AdvertisingBlock tmpAdvBlock = null;
        if (currentId != 0)
			tmpAdvBlock = advertisingService.findById(currentId);
		else if (!CollectionUtils.isEmpty(bannersList))
			tmpAdvBlock = advertisingService.findById(bannersList.get(0));

        //баннеры для API не отображаем в основном приложении
        if(tmpAdvBlock != null && (tmpAdvBlock.getAvailability() == AdvertisingAvailability.FULL || tmpAdvBlock.getAvailability() == AdvertisingAvailability.MAIN))
            advertisingBlock = tmpAdvBlock;
        else
            advertisingBlock = null;

        if(advertisingBlock != null)
            prepareAdvertisingBlock(advertisingBlock);
	}

	/**
	 * Получить баннер для отображения на странице
	 * @return баннер
	 */
	public AdvertisingBlock getAdvertisingBlock()
	{
		return advertisingBlock;
	}

	/**
	 * Получить список id баннеров в порядке отображения
	 * @return список id баннеров
	 */
	public List<Long> getBannersList()
	{
		return bannersList;
	}

    /**
     * Возвращает список баннеров в виде сущностей AdvertisingBlock в порядке их отображения
     * @return Список баннеров
     * @throws BusinessException
     */
    public List<AdvertisingBlock> getApiBannersList() throws BusinessException
    {
        Long departmentId = PersonContext.getPersonDataProvider().getPersonData().getPerson().getDepartmentId();
        Calendar date = DateHelper.getCurrentDate();
        return advertisingService.getApiBunnersList(date, departmentService.getNumberTB(departmentId));
    }

	/**
	 * Сортирует области баннера и кнопки согласно их порядку отображения
	 * @param advertisingBlock баннер
	 */
	private void prepareAdvertisingBlock(AdvertisingBlock advertisingBlock)
	{
		AdvertisingOrderedFieldComparator comparator = new AdvertisingOrderedFieldComparator();
		Collections.sort(advertisingBlock.getButtons(), comparator);
		Collections.sort(advertisingBlock.getAreas(), comparator);
	}

	/**
	 * Удаляет из списка баннеры, несоответствующие требованиям к продуктам клиента
	 * @param bannersList список id баннеров
	 * @throws BusinessException
	 */
	private void applyRequirements(List<Long> bannersList) throws BusinessException, BusinessLogicException
	{
		List<Long> deleteBanners = new ArrayList<Long>();
		
		List<CardLink> cards = externalResourceService.getLinks(login, CardLink.class);
		List<CardLink> creditCards = getFilterCardLinks(new CreditCardFilter(), cards);
		List<CardLink> debitCards = getFilterCardLinks(new DebitCardFilter(), cards);
		List<AutoPaymentLink> autopayments = externalResourceService.getLinks(login, AutoPaymentLink.class);
		List<AccountLink> accounts = externalResourceService.getLinks(login, AccountLink.class);
		List<LoanLink> loans = externalResourceService.getLinks(login, LoanLink.class);
		List<DepoAccountLink> depoAccounts = externalResourceService.getLinks(login, DepoAccountLink.class);
		List<IMAccountLink> ima = externalResourceService.getLinks(login, IMAccountLink.class);

		for(Long id : bannersList)
		{
			// проверяем подходит ли баннер по требованиям к продуктам клиента
			List<ProductRequirement> requirements = advertisingService.getRequirementsList(id);
			boolean delete = false;
			for(ProductRequirement requirement : requirements)
			{
				RequirementState state = requirement.getRequirementState();
				if (requirement.getRequirementType() == ProductRequirementType.PFR)
				{
					AuthModule authModule = AuthModule.getAuthModule();
					boolean access = authModule.implies(new OperationPermission("CreateFormPaymentOperation", "PFRStatementClaim"));
					if (state == RequirementState.CONNECTED ^ access)
					{
						delete = true;
					}
				}
				else if (requirement.getRequirementType() == ProductRequirementType.CREDIT_REPORT)
				{
					boolean access = checkCreditReportAccess();
					if (state == RequirementState.CONNECTED ^ access)
						delete = true;
				}
				else
				{
					int size = -1;
					if (requirement.getRequirementType() == ProductRequirementType.CREDIT_CARD)
					{
						size = creditCards == null ? -1 : creditCards.size();
					}
					else if (requirement.getRequirementType() == ProductRequirementType.DEBIT_CARD)
					{
						size = debitCards == null ? -1 : debitCards.size();
					}
					else if (requirement.getRequirementType() == ProductRequirementType.AUTOPAYMENT)
					{
						size = autopayments.size();
					}
					else if (requirement.getRequirementType() == ProductRequirementType.ACCOUNT)
					{
						size = accounts.size();
					}
					else if (requirement.getRequirementType() == ProductRequirementType.LOAN)
					{
						size = loans.size();
					}
					else if (requirement.getRequirementType() == ProductRequirementType.DEPO_ACCOUNT)
					{
						size = depoAccounts.size();
					}
					else if (requirement.getRequirementType() == ProductRequirementType.IMA)
					{
						size = ima.size();
					}
					if (size < 0 || state == RequirementState.CONNECTED && size == 0 || state == RequirementState.NOTCONNECTED && size > 0)
						delete = true;
				}
			}

			// проверяем подходит ли баннер по требованиям к видам вклада
			List<AccTypesRequirement> accTypesReq = advertisingService.getAccTypesRequirementList(id);
			Set<Long> accKinds = getUsedAccountKinds(accounts);
			for(AccTypesRequirement accTypeReq : accTypesReq)
			{
				Long productId = accTypeReq.getDepositProduct().getProductId();
				RequirementState accTypeState = accTypeReq.getRequirementState();
				if (accKinds == null || !accKinds.contains(productId) && accTypeState == RequirementState.CONNECTED ||
						accKinds.contains(productId) && accTypeState == RequirementState.NOTCONNECTED)
					delete = true;
			}

			if (delete)
				deleteBanners.add(id);
		}

		for(Long id : deleteBanners)
			bannersList.remove(id);
	}

	private boolean checkCreditReportAccess() throws BusinessException
	{
		AuthModule authModule = AuthModule.getAuthModule();
		if (authModule.implies(new OperationPermission("CreditReportOperation", "CreditReportService")))
		{
			PersonCreditProfile profile = creditProfileService.findByPerson(person);
			return profile != null && profile.isConnected();
		}
		return false;
	}

	/**
	 * Возвращает множество типов вклада, которые есть у клиента
	 * @param accounts список вкладов клиента
	 * @return множество типов вклада, которые есть у клиента
	 */
	private Set<Long> getUsedAccountKinds(List<AccountLink> accounts)
	{
		Set<Long> result = new HashSet<Long>();

		try
		{
			for(AccountLink link : accounts)
			{
				result.add(link.getAccount().getKind());
			}
			return result;
		}
		catch (Exception e)
		{
			return null;
		}
	}

	/**
	 * Получить список карт пользователя по фильтру.
	 * @param cardFilter фильтр
	 * @param cards список карт клиента 
	 * @return список карт пользователя.
	 * @throws BusinessException
	 */
	private List<CardLink> getFilterCardLinks(CardFilter cardFilter, List<CardLink> cards) throws BusinessException
	{
		try
		{
			List<CardLink> result = new ArrayList<CardLink>(cards);
			CollectionUtils.filter(result, cardFilter);
			return result;
		}
		catch (Exception e)
		{
			return null;
		}
	}
}
