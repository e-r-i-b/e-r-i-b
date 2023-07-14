package com.rssl.phizic.operations.person.list;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ermb.ErmbProfileBusinessService;
import com.rssl.phizic.business.ermb.ErmbProfileImpl;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.persons.csa.ProfileService;
import com.rssl.phizic.gate.csa.Profile;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.PhoneNumber;
import com.rssl.phizic.utils.PhoneNumberFormat;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author akrenev
 * @ created 01.11.13
 * @ $Author$
 * @ $Revision$
 *
 * Операция выбора клиента для редактирования
 */

public class ChooseClientOperation extends OperationBase implements ListEntitiesOperation
{
	private static final ProfileService profileService = new ProfileService();
	private static final PersonService personService = new PersonService();
	private final static ErmbProfileBusinessService ermbProfileService = new ErmbProfileBusinessService();
	private List<ActivePerson> clients = new ArrayList<ActivePerson>();

	/**
	 * инициализация операции
	 * @param profile - информация о клиенте
	 * @throws BusinessException
	 */
	public void initialize(Profile profile) throws BusinessException, BusinessLogicException
	{
		clients = personService.getByFIOAndDoc(profile.getSurName(), profile.getFirstName(), profile.getPatrName(), null, profile.getPassport(), profile.getBirthDay(), profile.getTb());
		if(CollectionUtils.isEmpty(clients))
		{
			Profile profileFromCSA = profileService.findProfileWithHistory(profile);
			if(profileFromCSA != null)
			{
				for(Profile history : profileFromCSA.getHistory())
				{
					clients = personService.getByFIOAndDoc(history.getSurName(), history.getFirstName(), history.getPatrName(), null, history.getPassport(), history.getBirthDay(), history.getTb());
					if(CollectionUtils.isNotEmpty(clients))
						return;
				}
			}
		}
		if(CollectionUtils.isEmpty(clients))
		{
			List<String> cardNumberList = profileService.findProfileCardNumberList(profile);
			for(String cardNumber: cardNumberList)
			{
				clients.addAll(personService.findByLasLogonCardNumber(cardNumber));
			}
		}
	}

	/**
	 * инициализация операции
	 * @param ermbActivePhone телефон ЕРМБ
	 */
	public void initialize(String ermbActivePhone) throws BusinessException
	{
		PhoneNumber phone = PhoneNumberFormat.MOBILE_INTERANTIONAL.parse(ermbActivePhone);
		ErmbProfileImpl profile = ermbProfileService.findByPhone(phone);
		if (profile != null)
		{
			clients.add((ActivePerson) profile.getPerson());
		}
	}

	/**
	 * @return список клиентов
	 */
	public List<ActivePerson> getClients()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return clients;
	}
}
