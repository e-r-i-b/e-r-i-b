package com.rssl.phizic.operations.dictionaries.regions;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.regions.Region;
import com.rssl.phizic.business.dictionaries.regions.RegionDictionaryService;
import com.rssl.phizic.business.profile.Profile;
import com.rssl.phizic.business.profile.ProfileService;
import com.rssl.phizic.business.profileSynchronization.PersonSettingsManager;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.business.regions.RegionHelper;

/**
 * @author Rydvanskiy
 * @ created 26.04.2010
 * @ $Author$
 * @ $Revision$
 */
public class RegionsListOperation extends OperationBase implements ListEntitiesOperation
{
	protected static final RegionDictionaryService regionService = new RegionDictionaryService();
	private Region region = null;
	private static final ProfileService profileService = new ProfileService();

	/**
	 * Инициализация
	 */
	public void initialize()
	{
		region = new Region();
	}

	/**
	 * Инициализация региона
	 * @param id - идентификатор региона
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public void initialize(Long id) throws BusinessException
	{
		if(RegionHelper.isAllRegionsSelected(id))
			region = RegionHelper.createAllRegion();
		else
		{
			region = regionService.findById(id);
			if (region == null)
			{
				region = RegionHelper.createAllRegion();
			}
		}
	}

	/**
	 * @return регион
	 */
	public Region getRegion()
	{
		return this.region;
	}

	/**
	 * Сохранение региона в профиль клиента
	 * @param login - логин
	 * @throws BusinessException
	 */
	public void saveRegion(CommonLogin login) throws BusinessException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		if(personData != null)
		{
			personData.getProfile().selectRegion(region);
			personData.setCurrentRegion(region);
		}

		PersonSettingsManager.savePersonData(PersonSettingsManager.USER_REGION_KEY, region);
		Profile profile = profileService.findByLogin(login);
		Long regionId = region.getId();

		if (RegionHelper.isOneRegionSelected(regionId))
			profile.selectRegion(region);
		else
			profile.selectRegion(null);

		profileService.update(profile);
		Long csaProfileId = AuthenticationContext.getContext().getCsaProfileId();
		if (csaProfileId!=null)
			RegionHelper.updateCsaRegion(csaProfileId, RegionHelper.getParentRegion(profile));
	}
}
