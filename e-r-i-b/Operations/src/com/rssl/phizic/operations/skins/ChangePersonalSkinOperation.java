package com.rssl.phizic.operations.skins;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.business.profile.Profile;
import com.rssl.phizic.business.profile.ProfileService;
import com.rssl.phizic.business.skins.Skin;
import com.rssl.phizic.business.skins.SkinsService;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.EntityUtils;

import java.util.Collections;
import java.util.List;

/**
 * @author Erkin
 * @ created 01.06.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * �������� ��������� ��������������� ����� ������� � ���������� �������
 */
public class ChangePersonalSkinOperation extends OperationBase
{
	private static final SkinsService skinService = new SkinsService();
	private static final ProfileService profileService = new ProfileService();

	private Person person;

	/**
	 * ��������� ������������ �����
	 */
	private List<Skin> skins;

	/**
	 * ������� ����� ������������
	 * �� �� ����� ��� �������������
	 */
	private Skin currentSkin;

	/**
	 * ������� ������������ 
	 */
	private Profile profile;//������� ������������
	///////////////////////////////////////////////////////////////////////////

	/**
	 * ������������� ��������
	 * @param currentSkinId - ID �������� ����� ��� null
	 */
	public void initialize(Long currentSkinId) throws BusinessException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		person = personData.getPerson();
		Login login = person.getLogin();
		//������� ������������
		profile = personData.getProfile();
		
		skins = skinService.getPersonAvailableSkins(login.getId());

		// 3. ���������� ������� �����
		// 3.A ������� ����� ������ ��� ������������� �� ������ ������
		if (currentSkinId != null)
		{
			Skin skin = EntityUtils.findById(skins, currentSkinId);
			if (skin == null)
				throw new BusinessException("����� �� �������� �������. " +
						"SKIN_ID=" + currentSkinId + ", LOGIN_ID=" + login.getId());
			currentSkin = skin;
		}

		// 3.B ������� ����� ������������ ���������� ������������
		else
		{
			if (profile.getSkin() != null)
				currentSkin = profile.getSkin();
			else currentSkin = skinService.getPersonActiveSkin(login.getId());
		}
	}

	/**
	 * @return ��������� ������������ �����
	 */
	public List<Skin> getSkins()
	{
		return Collections.unmodifiableList(skins);
	}

	/**
	 * @return ������� ����� ������������
	 * �� �� ����� ��� �������������
	 * (never null)
	 */
	public Skin getCurrentSkin()
	{
		return currentSkin;
	}

	public Profile getProfile()
	{
		return profile;
	}

	public void setProfile(Profile profile)
	{
		this.profile = profile;
	}

	/**
	 * ��������� ��������� ����� � �������� ��������������� ����� ������������
	 */
	public void save() throws BusinessException
	{

		if (EntityUtils.equalsById(currentSkin, profile.getSkin()))
			return;
		profile.setSkin(currentSkin);
		profileService.update(profile);

		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		String currentSkinUrl = currentSkin != null ? currentSkin.getUrl() : null;
		personData.setSkinUrl(currentSkinUrl);
	}
}
