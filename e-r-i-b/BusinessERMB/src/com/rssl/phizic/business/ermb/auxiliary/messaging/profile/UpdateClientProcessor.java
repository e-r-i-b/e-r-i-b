package com.rssl.phizic.business.ermb.auxiliary.messaging.profile;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ermb.ErmbProfileBusinessService;
import com.rssl.phizic.business.ermb.ErmbProfileImpl;
import com.rssl.phizic.business.ermb.profile.ErmbPersonListener;
import com.rssl.phizic.business.ermb.profile.ErmbUpdateListener;
import com.rssl.phizic.business.ermb.profile.events.ErmbPersonEvent;
import com.rssl.phizic.business.persons.oldIdentity.DuplicationIdentityException;
import com.rssl.phizic.business.persons.oldIdentity.PersonIdentityService;
import com.rssl.phizic.common.type.PersonIdentity;
import com.rssl.phizic.messaging.ErmbXmlMessage;
import com.rssl.phizic.messaging.MessageProcessorBase;
import com.rssl.phizic.module.Module;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.synchronization.SOSMessageHelper;
import com.rssl.phizic.synchronization.types.IdentityCardType;
import com.rssl.phizic.synchronization.types.IdentityType;
import com.rssl.phizic.synchronization.types.UpdateClientProfilesType;
import com.rssl.phizic.synchronization.types.UpdateClientRq;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;

import java.text.ParseException;
import java.util.Calendar;

/**
 * Процессор служебного канала, обрабатывающий оповещения об изменении данных клиента в ЕРМБ
 * @author Rtischeva
 * @ created 12.03.2013
 * @ $Author$
 * @ $Revision$
 */
public class UpdateClientProcessor extends MessageProcessorBase<ErmbXmlMessage>
{
	private static final ErmbProfileBusinessService PROFILE_SERVICE = new ErmbProfileBusinessService();
	private static final PersonIdentityService PERSON_IDENTITY_SERVICE = new PersonIdentityService();

	public UpdateClientProcessor(Module module)
	{
		super(module);
	}

	@Override
	@SuppressWarnings({"ThrowCaughtLocally"})
	protected void doProcessMessage(ErmbXmlMessage xmlRequest)
	{
		UpdateClientRq clientInfoMsg = xmlRequest.getUpdateClientRq();

		try
		{
			// Обновляем данные клиента в БД

			UpdateClientProfilesType profileType = clientInfoMsg.getProfile();
			IdentityType oldIdentity = profileType.getClientOldIdentity();
            String surName = oldIdentity.getLastname();
            String name = oldIdentity.getFirstname();
            String patrName = oldIdentity.getMiddlename();
			IdentityCardType identityCard = oldIdentity.getIdentityCard();
            String docSeries = identityCard.getIdSeries();
            String docNumber = identityCard.getIdNum();
            Calendar birthDate = oldIdentity.getBirthday();
			String tb = StringHelper.removeLeadingZeros(oldIdentity.getTb());
            ErmbProfileImpl profile = PROFILE_SERVICE.findByFIOAndDocInTB(surName, name, patrName, docSeries, docNumber, birthDate,tb);
			if (profile == null)
				throw new BusinessException("Не найден профиль ЕРМБ с ФИО: " + surName + " "  + name + " " + patrName + " ДУЛ: " + docNumber + " " + docSeries + " ДР: " + DateHelper.formatDateToString(birthDate) + " ТБ: " + tb);

			ErmbPersonEvent personEvent = new ErmbPersonEvent(profile);
			IdentityType newIdentity = profileType.getClientIdentity();
			PersonIdentity personIdentity =  SOSMessageHelper.getClientIdentity(newIdentity).getPersonIdentity();
			PersonIdentity oldPersonIdentity = SOSMessageHelper.getClientIdentity(oldIdentity).getPersonIdentity();
			Person oldPerson = profile.getPerson();
			personEvent.setOldPerson(oldPerson);
			Person updatedPerson = PERSON_IDENTITY_SERVICE.updateIdentity(oldPerson, oldPersonIdentity, personIdentity, null);
			personEvent.setNewPerson(updatedPerson);
			ErmbPersonListener listener = ErmbUpdateListener.getListener();
			listener.onPersonUpdate(personEvent);
		}
		catch (BusinessException e)
		{
			log.error("Ошибка обновления данных клиента", e);
		}
		catch (ParseException e)
        {
            log.error("Ошибка разбора xml-сообщения с оповещением ЕРИБ об изменении данных клиента", e);
        }
		catch (DuplicationIdentityException e)
		{
			log.error("Клиент с такими идентификационными данными уже существует в БД ЕРИБ.", e);
		}
	}
}
