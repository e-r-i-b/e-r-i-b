package com.rssl.phizic.operations.person;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.operations.restrictions.UserRestriction;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.profile.Profile;
import com.rssl.phizic.business.profile.ProfileService;
import com.rssl.phizic.business.util.MoneyUtil;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.operations.OperationBase;

import java.math.BigDecimal;

/**
 * @author osminin
 * @ created 08.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class ResetMobileWalletOperation extends OperationBase<UserRestriction>
{
	private static final ProfileService profileService = new ProfileService();
	private static final PersonService personService = new PersonService();

	private ActivePerson person;
	private Profile profile;

	public void initialize(Long personId) throws BusinessException, BusinessLogicException
	{
		Person temp = personService.findById(personId);
		if (temp == null)
			throw new BusinessException("Клиент с заданным id не найден");

		UserRestriction restriction = getRestriction();
		if (!restriction.accept(temp))
			throw new BusinessLogicException("Вы не можете работать с данным клиентом");

		this.person = (ActivePerson) temp;

		profile = profileService.findByLogin(person.getLogin());
		if (profile == null)
			throw new BusinessException("Не обнаружено записей для данного клиента");
	}
	/**
	 * @return профиль
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public Profile getProfile() throws BusinessException, BusinessLogicException
	{
		return profile;
	}

	/**
	 * @return персона
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public ActivePerson getPerson() throws BusinessException, BusinessLogicException
	{
		return person;
	}

	/**
	 * Обнуление мобильного кошелька клиента
	 * @throws BusinessException
	 */
	public void reset() throws BusinessException
	{
		profileService.updateMobileWallet(profile, new Money(BigDecimal.ZERO, MoneyUtil.getNationalCurrency()));
	}
}
