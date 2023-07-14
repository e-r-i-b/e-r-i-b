package com.rssl.phizic.operations.skins;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.profile.Profile;
import com.rssl.phizic.business.profile.ProfileService;
import com.rssl.phizic.business.skins.Skin;
import com.rssl.phizic.business.skins.SkinsService;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.EntityUtils;

import java.util.Collections;
import java.util.List;

/**
 * User: Balovtsev
 * Date: 23.05.2011
 * Time: 14:49:50
 */
public class EditPersonUIOperation extends OperationBase implements EditEntityOperation
{
	private static final SkinsService  skinsService  = new SkinsService();
	private static final PersonService personService = new PersonService();
	private static final ProfileService profileService = new ProfileService();

	private Person person;
	private List<Skin> personSkins;
	private Profile profile;
	private Skin personActiveSkin;

	/**
	 * »нициализаци€ операции
	 * @param id идентификатор пользовател€
	 */
	public void init(Long id) throws BusinessException
	{
		person = personService.findById(id);
		//¬се доступные пользователю стили
		personSkins = skinsService.getPersonAvailableSkins(id);
		//»ндивидуальный стиль пользовател€
		profile = profileService.findByLogin(person.getLogin());
		//получаем текущий скин пользовател€
		personActiveSkin = skinsService.getPersonActiveSkin(person.getLogin().getId());
	}

	/**
	 * ¬озвращает список стилей доступных пользователю
	 * @return List<Skin>
	 */
	public List<Skin> getPersonSkins()
	{
		if(personSkins == null || personSkins.isEmpty())
		{
			return Collections.emptyList();	
		}
		return Collections.unmodifiableList(personSkins);
	}

	/**
	 * ¬озвращает индивидуальный стиль пользовател€
	 * @return Skin
	 */
	public Skin getPersonSkin()
	{
		return profile.getSkin();
	}

	/**
	 * @return текущий скин пользовател€
	 */
	public Skin getPersonActiveSkin()
	{
		return personActiveSkin;
	}

	public Person getEntity()
	{
		return person;
	}

	public void save() throws BusinessException
	{
		personService.update( getEntity() );
		profileService.update(profile);
	}

	/**
	 * ”станавливает пользователю новый скин.
	 * @param skinId идентификатор скина
	 * @throws BusinessException
	 */
	public void updatePersonSkin(Long skinId) throws BusinessException
	{
		if(skinId == null)
		{
			profile.setSkin(null);
			return;
		}

		if (profile.getSkin() != null && skinId.equals(profile.getSkin().getId()))
		{
			return;	
		}

		Skin skin = EntityUtils.findById(personSkins, skinId);
		if ( skin == null )
		{
			throw new BusinessException("—тиль не доступен пользователю. " +
					"LOGIN_ID=" + person.getLogin().getId() + ", " +
					"SKIN_ID=" + skinId);
		}
		profile.setSkin(skin);
	}
}
