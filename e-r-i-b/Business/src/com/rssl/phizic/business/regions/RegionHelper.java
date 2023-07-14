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
 * ������ ��� ������ � ��������� � ����, ��� � ���� ��������
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
	 * ���� ������ �� ��������������
	 * @param id - �������������
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
			log.error("������ ����������� ������� �� id", e);
			return null;
		}
	}

	/**
	 * ���������� ������ �����������
	 * @param region - ������, ����������� �������� ����� �������
	 * @return ������
	 */
	public static List getChildren(Region region)
	{
		try
		{
			return regionService.getChildren(region);
		}
		catch (Exception e)
		{
			log.error("������ ����������� ������ ����������� ��������", e);
			return new ArrayList();
		}
	}

	/**
	 * ���������� ������ ������������ ��������
	 * @param region - ������, ��������� �������� ����� �������
	 * @return ������
	 */
	public static List<Region> getParents(Region region)
	{
		return arrayToList(region);
	}

	/**
	 * ����������� ������� ��� ������������ �������������� ������
	 * @param parrent - ������� ������
	 * @return ������ �������� � �������� ������� (���� �� ����� � �������� ��������)
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
	 * �������� ������� ������ �������. ������ ������� �� ������� �������.
	 * @return ������
	 */
	public static Region getCurrentRegion() throws BusinessException
	{
		if (!PersonContext.isAvailable())
			return null;
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		return personData.getCurrentRegion();
	}

	/**
	 * @param regionId id ���������� �������
	 * @return ������� �� ��� �������
	 */
	public static boolean isAllRegionsSelected(Long regionId)
	{
		return regionId == null || regionId == 0 || regionId == -1;
	}

	/**
	 * @param regionId ������������� ������� ID ��� UUID
	 * @return ������� �� ��� �������
	 */
	public static boolean isAllRegionsSelected(String regionId)
	{
		return StringHelper.isEmpty(regionId) || regionId.equals("0") || regionId.equals("-1");
	}

	/**
	 * @param regionId id ���������� �������
	 * @return ������ �� ���� ������
	 */
	public static boolean isOneRegionSelected(Long regionId)
	{
		return !isAllRegionsSelected(regionId);
	}

	/**
	 * @return ��� �������
	 */
	public static Region createAllRegion()
	{
		Region region = new Region();
		region.setId(-1L);
		region.setSynchKey("-1");
		region.setName("��� �������");
		return region;
	}

	/**
	 * ���������� �������� ������, ��������� � ����
	 * @param profile ������� �������
	 * @return ������, ��������� � ����
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
	 * ���������� �������� ������, ��������� � ����
	 * @param region ������ �������
	 * @return �������� ������, ��������� � ����
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
	 * ��������� ������ � �������
	 * @param profileId ������������� �������
	 * @param region ������
	 * @throws BusinessException
	 */
	public static void updateCsaRegion(final Long profileId, final Region region) throws BusinessException
	{
		if(isRegionFunctionalityOn())
			regionService.updateCsaRegion(profileId, region);
	}

	/**
	 * @return ������� �� ���������� ������ �������
	 */
	public static boolean isRegionFunctionalityOn()
	{
		return ConfigFactory.getConfig(CSARefreshConfig.class).getRegionSelectionFunctionalityMode();
	}

	/**
	 * ����� ������� �� ������������ (�����������) �������������� "uuid"
	 * @param regionGuid
	 * @return
	 * @throws BusinessException
	 */
	public static Region findRegionByGuid(String regionGuid) throws BusinessException
	{
		if (StringHelper.isEmpty(regionGuid))
			return null;
		Region region = regionService.findByGuid(regionGuid);
		//TODO (CHG082279: [ATMAPI] ������� ����� ��� ����� �������) ������ �������������� ����� �� ���� ����� � �������� ������� ������� ����� ��������� �������
		if (region == null && ApplicationUtil.isATMApi())
		{
			region = regionService.findBySynchKey(regionGuid);
			//���������� ������� ������ ������� ���� ��� ����� �� GUID � �� ���� �����
			if (region == null)
				region = getCurrentRegion();
		}
		return region;
	}

	/**
	 * ���������� ������ � ����������� �� ������ (mAPI ��� atmAPI)
	 * @return
	 * @throws BusinessException
	 */
	public static Region getRegionApi() throws BusinessException
	{
		//��� mAPI - ������ �������
		if (ApplicationUtil.isMobileApi() && PersonContext.isAvailable())
		{
			return PersonContext.getPersonDataProvider().getPersonData().getCurrentRegion();
		}
		//��� atmAPI - ������ ��
		else if (ApplicationUtil.isATMApi())
		{
			return findRegionByAtmRegionCode();
		}

		return null;
	}

	/**
	 * ����� ���� ������ ��������� �������:
	 *
	 * <br/>- ���� � ����������� ������ ������ � CODE ������ atmRegionCode - �� ������������ ��;
	 * <br/>- ���� ������ � ��������� atmRegionCode �� ������ - ������� ������ 2 ����� atmRegionCode � ����������� 3��
	 * ������, ����� ����, �� ����������� �������� ������������, ����� �����;
	 * <br/>- ����� � � ���� ��� ����� ������ �� ������, ���������� ������ � ����� name "��� �������" � id ������ 0.
	 *
	 * @return Region
	 */
	public static Region findRegionByAtmRegionCode()
	{
		//� �������� ���� ������� ����� �������� ��� 5 ���� ���� �����, ��� � ������������� ������� � �� ����
		String regionCode = AuthenticationContext.getContext().getAuthenticationParameter(PARAMETER_REGION_ATM_CODE);
		Region region = null;
		try
		{
			if (!StringHelper.isEmpty(regionCode))
			{
				AtmApiConfig atmApiConfig = ConfigFactory.getConfig(AtmApiConfig.class);
				if (atmApiConfig.isRegionCodeIsOkato()) //�������� 5 ���� ���� �����
				{
					region = regionService.findBySynchKey(regionCode);
					if (region == null)
					{
						//��� ������, ����� � ����������� ������ �� ������, ���� �� ������ ���� ������ ����������� 3�� ������
						regionCode = StringHelper.appendEndingZeros(regionCode.substring(0, 2), 5);
						region = regionService.findBySynchKey(regionCode);
					}
				}
				else //�������� ������������� ������� � �� ����
					region = regionService.findById(Long.valueOf(regionCode));
			}
		}
		catch (Exception e)
		{
			log.error("������ ����������� ������� �� \"atmRegionCode\"", e);
		}
		finally
		{
			if (region == null)
			{
				region = new Region();
				region.setId(0L);
				region.setName("��� �������");
			}
		}

		return region;
	}

	/**
	 * ���������� ���������� ����� �� �������. ��� �������� �� �����-����. ������ ��� mAPI � atmAPI.
	 * @return
	 * @throws BusinessException
	 */
	public static ServiceProviderShort getBarCodeProvider() throws BusinessException
	{
		if (ApplicationUtil.isNotApi())
			return null;

		//����� �������� ID ���������� ����� (��) �� ������� � ������ ����������� ��������
		return getProviderByRegion(getRegionApi());
	}

	/**
	 * ���������� ���������� ����� �� �������. ������ ��� mAPI � atmAPI.
	 * @return
	 * @throws BusinessException
	 */
	private static ServiceProviderShort getProviderByRegion(Region region) throws BusinessException
	{
		if (ApplicationUtil.isNotApi())
			return null;

		//����� �������� ID ���������� ����� (��) �� ������� � ������ ����������� ��������
		String providerExternalID = getProviderExternalID(region);

		ServiceProviderShort provider = StringHelper.isNotEmpty(providerExternalID) ?
				 providerService.findShortProviderBySynchKey(providerExternalID) : null;

		if (provider != null)
			return provider;
		//���� ��� ������� ��������� �� �����, �� ���� ���������� ���������� �� �������
		providerExternalID = getDefaultProviderExternalID();
		provider = StringHelper.isNotEmpty(providerExternalID) ?
				 providerService.findShortProviderBySynchKey(providerExternalID) : null;
		if (provider == null)
			log.error(String.format("�� ����� ��������� ����� ��� ������ �� �����-����� ��� ������� \"%s\".",
					region != null ? region.getName() : "��� �������"));
		return provider;
	}

	/**
	 * ������������� �� (EXTERNAL_ID) �� ������� ������� ��� ������� �������
	 * �� ��� (���������� �����/�������/��� �� ��� ������ �� �����-�����) � ������ ������������ ��������
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
		//��������. ���� �� ����� ��������� ��� �������, �� ������� ��� ����� � ������������� �������
		return StringHelper.isEmpty(providerExternalID) ? getProviderExternalID(region.getParent()) : providerExternalID;
	}

	/**
	 * ��������� ������������� �� (EXTERNAL_ID) �� ������� ������� ��� "���� ��������" � ������ ������
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
	 * ����������� ������� ������� � ������ �� ������
	 * @param document ������ �� ������
	 *  @return ������
	 */
	public static String getClientRegionName(BusinessDocumentBase document)
	{
		if (PersonHelper.isGuest())
		{
			if (!(document instanceof ExtendedLoanClaim))
				return "������";
			return ((ExtendedLoanClaim) document).getRegion(1).getName();
		}
		try
		{
			Region region = getCurrentRegion();
			return region == null ? "������" : region.getName();
		}
		catch (BusinessException e)
		{
			log.error("���������� ���������� ������ ������� �� ��� �������");
			return "������";
		}
	}

	/**
	 * ����������� �������� ������� � ������ �� ������
	 * @param document ������ �� ������
	 * @return �������
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
						log.error("���������� ���������� ������ ������� �� �������");
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
	 * ��������� ������� �� ����.
	 * @param code ��� �������
	 * @return ������, � ��������� �����.
	 * @throws BusinessException
	 */
	public static Region getRegionByCode(String code) throws BusinessException
	{
		return RegionHelper.isAllRegionsSelected(code) ? null :regionService.findBySynchKey(code);
	}
}