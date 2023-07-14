package com.rssl.phizic.business.regions;

import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.authgate.csa.CSARefreshConfig;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderShort;
import com.rssl.phizic.business.dictionaries.regions.Region;
import com.rssl.phizic.business.dictionaries.regions.RegionDictionaryService;
import com.rssl.phizic.business.documents.BusinessDocumentBase;
import com.rssl.phizic.business.documents.payments.ExtendedLoanClaim;
import com.rssl.phizic.business.payments.PaymentsConfig;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.profile.Profile;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.atm.AtmApiConfig;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.util.ApplicationUtil;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Хелпер для работы с регионами и всем, что с ними связанно
 * @author Rydvanskiy
 * @ created 04.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class RegionHelper
{
	private static final RegionDictionaryService regionService = new RegionDictionaryService();
	private static final ServiceProviderService providerService = new ServiceProviderService();
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final String PARAMETER_REGION_ATM_CODE = "atmRegionCode";

	/**
	 * Ищет регион по идентификатору
	 * @param id - идентификатор
	 * @return
	 */
	public static Region findById(Long id)
	{
		try
		{
			return regionService.findById(id);
		}
		catch (Exception e)
		{
			log.error("Ошибка определения региона по id", e);
			return null;
		}
	}

	/**
	 * Возвращает список наследников
	 * @param region - регион, наследников которого нужно вернуть
	 * @return список
	 */
	public static List getChildren(Region region)
	{
		try
		{
			return regionService.getChildren(region);
		}
		catch (Exception e)
		{
			log.error("Ошибка определения списка подчиненных регионов", e);
			return new ArrayList();
		}
	}

	/**
	 * Возвращает список родительских регионов
	 * @param region - регион, родителей которого нужно вернуть
	 * @return список
	 */
	public static List<Region> getParents(Region region)
	{
		return arrayToList(region);
	}

	/**
	 * Рекурсивная функция для формирования навигационного списка
	 * @param parrent - текущий регион
	 * @return Список регионов в обратном порядке (путь от корня к текущему элементу)
	 */
	public static List<Region> arrayToList(Region parrent)
	{
		List<Region> regions = new ArrayList<Region>();
		if ( parrent != null )
		{
			if (parrent.getParent() != null)
				regions.addAll(arrayToList(parrent.getParent()));
			regions.add(parrent);
		}
		return regions;
	}

	/**
	 * Получить текущий регион клиента. Регион берется из профиля клиента.
	 * @return Регион
	 */
	public static Region getCurrentRegion() throws BusinessException
	{
		if (!PersonContext.isAvailable())
			return null;
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		return personData.getCurrentRegion();
	}

	/**
	 * @param regionId id выбранного региона
	 * @return выбраны ли все регионы
	 */
	public static boolean isAllRegionsSelected(Long regionId)
	{
		return regionId == null || regionId == 0 || regionId == -1;
	}

	/**
	 * @param regionId идентификатор региона ID или UUID
	 * @return выбраны ли все регионы
	 */
	public static boolean isAllRegionsSelected(String regionId)
	{
		return StringHelper.isEmpty(regionId) || regionId.equals("0") || regionId.equals("-1");
	}

	/**
	 * @param regionId id выбранного региона
	 * @return выбран ли один регион
	 */
	public static boolean isOneRegionSelected(Long regionId)
	{
		return !isAllRegionsSelected(regionId);
	}

	/**
	 * @return все регионы
	 */
	public static Region createAllRegion()
	{
		Region region = new Region();
		region.setId(-1L);
		region.setSynchKey("-1");
		region.setName("Все регионы");
		return region;
	}

	/**
	 * возвращаем корневой регион, ВЫБРАННЫЙ в ЕРИБ
	 * @param profile профиль клиента
	 * @return регион, выбранный в ЕРИБ
	 */
	public static Region getParentRegion(Profile profile) throws BusinessException
	{
		Region parent = RegionHelper.getRegionByCode(profile.getRegionCode());
		if (parent == null)
			return createAllRegion();

		while(parent.getParent() != null)
			parent = parent.getParent();

		return parent;
	}

	/**
	 * возвращаем корневой регион, ВЫБРАННЫЙ в ЕРИБ
	 * @param region регион клиента
	 * @return корневой регион, выбранный в ЕРИБ
	 */
	public static Region getParentRegion(Region region)
	{
		if (region == null)
			return createAllRegion();

		Region parent = region;

		while(parent.getParent() != null)
			parent = parent.getParent();

		return parent;
	}

	/**
	 * Обновляет данные о регионе
	 * @param profileId идентификатор профиля
	 * @param region регион
	 * @throws BusinessException
	 */
	public static void updateCsaRegion(final Long profileId, final Region region) throws BusinessException
	{
		if(isRegionFunctionalityOn())
			regionService.updateCsaRegion(profileId, region);
	}

	/**
	 * @return Включён ли функционал выбора региона
	 */
	public static boolean isRegionFunctionalityOn()
	{
		return ConfigFactory.getConfig(CSARefreshConfig.class).getRegionSelectionFunctionalityMode();
	}

	/**
	 * Поиск региона по кросблочному (глобальному) идентификатору "uuid"
	 * @param regionGuid
	 * @return
	 * @throws BusinessException
	 */
	public static Region findRegionByGuid(String regionGuid) throws BusinessException
	{
		if (StringHelper.isEmpty(regionGuid))
			return null;
		Region region = regionService.findByGuid(regionGuid);
		//TODO (CHG082279: [ATMAPI] Вернуть ОКАТО при входе клиента) убрать дополнительный поиск по коду ОКАТО и текущему региону клиента после доработок СИРИУСа
		if (region == null && ApplicationUtil.isATMApi())
		{
			region = regionService.findBySynchKey(regionGuid);
			//возвращает текущий регион клиента если был поиск по GUID и по коду ОКАТО
			if (region == null)
				region = getCurrentRegion();
		}
		return region;
	}

	/**
	 * Возвращает регион в зависимости от канала (mAPI или atmAPI)
	 * @return
	 * @throws BusinessException
	 */
	public static Region getRegionApi() throws BusinessException
	{
		//для mAPI - регион клиента
		if (ApplicationUtil.isMobileApi() && PersonContext.isAvailable())
		{
			return PersonContext.getPersonDataProvider().getPersonData().getCurrentRegion();
		}
		//для atmAPI - регион УС
		else if (ApplicationUtil.isATMApi())
		{
			return findRegionByAtmRegionCode();
		}

		return null;
	}

	/**
	 * Метод ищет регион следующим образом:
	 *
	 * <br/>- Если в справочнике найден регион с CODE равным atmRegionCode - то возвращается он;
	 * <br/>- Если регион с указанным atmRegionCode не найден - берутся первые 2 цифры atmRegionCode и дополняются 3мя
	 * нулями, после чего, по полученному значению производится, новый поиск;
	 * <br/>- Ежели и в этот раз невод ничего не поймал, возвращаем регион с полем name "Все регионы" и id равным 0.
	 *
	 * @return Region
	 */
	public static Region findRegionByAtmRegionCode()
	{
		//В качестве кода региона могут передать как 5 цифр кода ОКАТО, так и идентификатор региона в АС ЕРИБ
		String regionCode = AuthenticationContext.getContext().getAuthenticationParameter(PARAMETER_REGION_ATM_CODE);
		Region region = null;
		try
		{
			if (!StringHelper.isEmpty(regionCode))
			{
				AtmApiConfig atmApiConfig = ConfigFactory.getConfig(AtmApiConfig.class);
				if (atmApiConfig.isRegionCodeIsOkato()) //передали 5 цифр кода ОКАТО
				{
					region = regionService.findBySynchKey(regionCode);
					if (region == null)
					{
						//Для случая, когда в справочнике регион не найден, ищем по первым двум цифрам дополненным 3мя нулями
						regionCode = StringHelper.appendEndingZeros(regionCode.substring(0, 2), 5);
						region = regionService.findBySynchKey(regionCode);
					}
				}
				else //передали идентификатор региона в АС ЕРИБ
					region = regionService.findById(Long.valueOf(regionCode));
			}
		}
		catch (Exception e)
		{
			log.error("Ошибка определения региона по \"atmRegionCode\"", e);
		}
		finally
		{
			if (region == null)
			{
				region = new Region();
				region.setId(0L);
				region.setName("Все регионы");
			}
		}

		return region;
	}

	/**
	 * Возвращает поставщика услуг по региону. Для платежей по штрих-коду. Только для mAPI и atmAPI.
	 * @return
	 * @throws BusinessException
	 */
	public static ServiceProviderShort getBarCodeProvider() throws BusinessException
	{
		if (ApplicationUtil.isNotApi())
			return null;

		//Поиск внешнего ID поставщика услуг (ПУ) по региону с учетом вложенности регионов
		return getProviderByRegion(getRegionApi());
	}

	/**
	 * Возвращает поставщика услуг по региону. Только для mAPI и atmAPI.
	 * @return
	 * @throws BusinessException
	 */
	private static ServiceProviderShort getProviderByRegion(Region region) throws BusinessException
	{
		if (ApplicationUtil.isNotApi())
			return null;

		//Поиск внешнего ID поставщика услуг (ПУ) по региону с учетом вложенности регионов
		String providerExternalID = getProviderExternalID(region);

		ServiceProviderShort provider = StringHelper.isNotEmpty(providerExternalID) ?
				 providerService.findShortProviderBySynchKey(providerExternalID) : null;

		if (provider != null)
			return provider;
		//Если для региона поставщик не задан, то берём дефолтного поставщика из конфига
		providerExternalID = getDefaultProviderExternalID();
		provider = StringHelper.isNotEmpty(providerExternalID) ?
				 providerService.findShortProviderBySynchKey(providerExternalID) : null;
		if (provider == null)
			log.error(String.format("Не задан поставщик услуг для оплаты по штрих-кодам для региона \"%s\".",
					region != null ? region.getName() : "Все регионы"));
		return provider;
	}

	/**
	 * Идентификатор ПУ (EXTERNAL_ID) во внешней системе для данного региона
	 * из АРМ (Поставщики услуг/Регионы/Код ПУ для оплаты по штрих-кодам) с учетом родительских регионов
	 * @param region
	 * @return
	 */
	private static String getProviderExternalID(Region region)
	{
		if (region == null)
			return null;

		String providerExternalID = null;
		if (ApplicationUtil.isMobileApi())
			providerExternalID = region.getProviderCodeMAPI();
		else if (ApplicationUtil.isATMApi())
			providerExternalID = region.getProviderCodeATM();
		else
			return null;
		//Рекурсия. Если не задан поставщик для региона, то пробуем его найти у родительского региона
		return StringHelper.isEmpty(providerExternalID) ? getProviderExternalID(region.getParent()) : providerExternalID;
	}

	/**
	 * Дефолтный идентификатор ПУ (EXTERNAL_ID) во внешней системе для "Всех регионов" с учетом канала
	 * @return
	 */
	private static String getDefaultProviderExternalID()
	{
		String providerExternalID = null;
		if (ApplicationUtil.isMobileApi())
			providerExternalID = ConfigFactory.getConfig(PaymentsConfig.class).getAllRegionsProviderMobile();
		else if (ApplicationUtil.isATMApi())
			providerExternalID = ConfigFactory.getConfig(PaymentsConfig.class).getAllRegionsProviderAtm();
		return providerExternalID;
	}

	/**
	 * Определение региона клиента в заявке на кредит
	 * @param document заявка на кредит
	 *  @return регион
	 */
	public static String getClientRegionName(BusinessDocumentBase document)
	{
		if (PersonHelper.isGuest())
		{
			if (!(document instanceof ExtendedLoanClaim))
				return "Россия";
			return ((ExtendedLoanClaim) document).getRegion(1).getName();
		}
		try
		{
			Region region = getCurrentRegion();
			return region == null ? "Россия" : region.getName();
		}
		catch (BusinessException e)
		{
			log.error("Невозможно определить регион клиента из его профиля");
			return "Россия";
		}
	}

	/**
	 * Определение тербанка клиента в заявке на кредит
	 * @param document заявка на кредит
	 * @return тербанк
	 */
	public static String getClientTerBank(BusinessDocumentBase document)
	{
		if (!(document instanceof ExtendedLoanClaim))
			return "";
		ExtendedLoanClaim loanClaim = (ExtendedLoanClaim) document;
		boolean isOffer = loanClaim.getLoanOfferId() != null;
		if (!PersonHelper.isGuest())
		{
			if (isOffer)
			{
				String tbCode = PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin().getLastLogonCardTB();
				return StringHelper.addLeadingZeros(tbCode, 3);
			}
			else
			{
				Collection<String> salaryCards = loanClaim.getApplicantSalaryCards();
				Collection<String> pensionCards = loanClaim.getApplicantPensionCards();
				if (CollectionUtils.isNotEmpty(salaryCards)
					|| CollectionUtils.isNotEmpty(pensionCards)
					|| loanClaim.getApplicantAsSBEmployee() != null)
					return "";
				else
					try
					{
						return StringHelper.addLeadingZeros(getCurrentRegion().getCodeTB(), 3);
					}
					catch (BusinessException e)
					{
						log.error("Невозможно определить регион клиента из профиля");
						return "";
					}
			}
		}
		else
		{
			if (!isOffer)
			{
				Collection<String> salaryCards = loanClaim.getApplicantSalaryCards();
				Collection<String> pensionCards = loanClaim.getApplicantPensionCards();
				if (CollectionUtils.isNotEmpty(salaryCards)
						|| CollectionUtils.isNotEmpty(pensionCards)
						|| loanClaim.getApplicantAsSBEmployee() != null)
					return "";
			}
			return StringHelper.addLeadingZeros(loanClaim.getTb(), 3);
		}
	}

	/**
	 * Получение региона по коду.
	 * @param code Код региона
	 * @return Регион, с указанным кодом.
	 * @throws BusinessException
	 */
	public static Region getRegionByCode(String code) throws BusinessException
	{
		return RegionHelper.isAllRegionsSelected(code) ? null :regionService.findBySynchKey(code);
	}
}