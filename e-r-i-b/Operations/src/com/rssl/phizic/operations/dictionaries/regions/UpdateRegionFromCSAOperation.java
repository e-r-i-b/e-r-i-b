package com.rssl.phizic.operations.dictionaries.regions;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.regions.CSARegion;
import com.rssl.phizic.business.dictionaries.regions.Region;
import com.rssl.phizic.business.dictionaries.regions.RegionDictionaryService;
import com.rssl.phizic.business.profile.Profile;
import com.rssl.phizic.business.profile.ProfileService;
import com.rssl.phizic.business.profileSynchronization.PersonSettingsManager;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.business.regions.RegionHelper;
import com.rssl.phizic.utils.StringHelper;

/**
 * Операция синхронизации изменения региона в ЦСА и ЕРИБ
 * @author komarov
 * @ created 27.03.2013 
 * @ $Author$
 * @ $Revision$
 */

public class UpdateRegionFromCSAOperation extends OperationBase implements ListEntitiesOperation
{
	private static final String ALL_REGION_CODE = "-1";
	private Region region;
	private static final RegionDictionaryService regionService = new RegionDictionaryService();
	private static final ProfileService profileService = new ProfileService();

	/**
	 * Инициализация регина
	 * @param id идентификатор региона
	 * @throws BusinessException
	 */
	public void initialize(Long id) throws BusinessException
	{
		if(RegionHelper.isAllRegionsSelected(id))
			region = RegionHelper.createAllRegion();
		else
			region = regionService.findById(id);
		if (region == null)
			throw new BusinessException("регион с id = " + id + " не найден");
	}

	/**
	 * Выполняет синхронизацию
	 * @return выбран ли регион (true -- выбран)
	 * @throws BusinessException
	 */
	public boolean process() throws BusinessException
	{
		CommonLogin login = AuthenticationContext.getContext().getLogin();
		Profile profile = profileService.findByLogin(login);
		Long csaProfileId = AuthenticationContext.getContext().getCsaProfileId();
		if(!RegionHelper.isRegionFunctionalityOn() || csaProfileId == null)
			return profile.isRegionSelected();
		CSARegion csaRegion = regionService.findUserRegionByUserId(csaProfileId);
		if (csaRegion == null)
			return profile.isRegionSelected();
		if (StringHelper.isEmpty(csaRegion.getCode()))
		{
			if (profile.isRegionSelected())
			{
				RegionHelper.updateCsaRegion(csaProfileId, RegionHelper.getParentRegion(profile));
				return true;
			}
			return false;
		}

		String csaRegionCode = csaRegion.getCode();
		if (needUpdate(csaRegionCode, profile))
		{
			Region reg = findRegion(csaRegionCode);
			profile.selectRegion(reg);
			if (reg==null || RegionHelper.isAllRegionsSelected(reg.getId()))
				PersonSettingsManager.savePersonData(PersonSettingsManager.USER_REGION_KEY, RegionHelper.createAllRegion());
			else
				PersonSettingsManager.savePersonData(PersonSettingsManager.USER_REGION_KEY, reg);
			profileService.update(profile);
		}
		return true;
	}

	// нужно ли обновлять данные региона в ЕРИБе
	private boolean needUpdate(String csaRegionCode, Profile profile) throws BusinessException
	{
		//нужно: не выбран регион в ЕРИБ  или код выбранного региона в ЕРИБ не соответствует коду выбранного региона в ЦСА  
		return !profile.isRegionSelected() || !csaRegionCode.equals(RegionHelper.getParentRegion(profile).getSynchKey());
	}

	private Region findRegion(String csaRegionCode) throws BusinessException
	{
		if (ALL_REGION_CODE.equals(csaRegionCode))
			return null;
		return regionService.findBySynchKey(csaRegionCode);
	}

	/**
	 * Сохранение региона в профиль клиента
	 * @param login - логин
	 * @throws BusinessException
	 */
	public void saveRegion(CommonLogin login) throws BusinessException
	{
		Profile clientProfile = profileService.findByLogin(login);
		Long regionId = region.getId();

		PersonSettingsManager.savePersonData(PersonSettingsManager.USER_REGION_KEY, region);
		if (RegionHelper.isOneRegionSelected(regionId))
		{
			clientProfile.selectRegion(region);
			PersonSettingsManager.savePersonData(PersonSettingsManager.USER_REGION_KEY, region);
		}
		else
		{
			clientProfile.selectRegion(null);
			PersonSettingsManager.savePersonData(PersonSettingsManager.USER_REGION_KEY, RegionHelper.createAllRegion());
		}
		profileService.update(clientProfile);
		Long csaProfile = AuthenticationContext.getContext().getCsaProfileId();
		if (csaProfile!=null)
			RegionHelper.updateCsaRegion(csaProfile, region);
	}
}
