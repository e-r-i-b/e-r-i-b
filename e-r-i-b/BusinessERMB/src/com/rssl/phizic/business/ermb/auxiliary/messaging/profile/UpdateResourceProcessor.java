package com.rssl.phizic.business.ermb.auxiliary.messaging.profile;

import com.rssl.phizic.bankroll.BankrollEngine;
import com.rssl.phizic.bankroll.PersonBankrollManager;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ermb.ErmbProfileBusinessService;
import com.rssl.phizic.business.ermb.ErmbProfileImpl;
import com.rssl.phizic.business.ermb.migration.MigrationHelper;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.common.type.ErmbProfileIdentity;
import com.rssl.phizic.common.type.ErmbProfileIdentityCard;
import com.rssl.phizic.common.types.client.ClientDocumentType;
import com.rssl.phizic.messaging.MessageProcessorBase;
import com.rssl.phizic.messaging.ErmbXmlMessage;
import com.rssl.phizic.module.Module;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.synchronization.SOSMessageHelper;
import com.rssl.phizic.synchronization.types.UpdateResourceRq;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.PassportTypeWrapper;
import com.rssl.phizic.utils.StringHelper;

import java.text.ParseException;
import java.util.Calendar;

/**
 * Процессор служебного канала, обрабатывающий оповещения об изменении продукта клиента в ЕРМБ
 * @author Rtischeva
 * @ created 01.04.2013
 * @ $Author$
 * @ $Revision$
 */
public class UpdateResourceProcessor extends MessageProcessorBase<ErmbXmlMessage>
{
	private static final ErmbProfileBusinessService profileService = new ErmbProfileBusinessService();
	private static final PersonService personService = new PersonService();

	public UpdateResourceProcessor(Module module)
	{
		super(module);
	}

	protected void doProcessMessage(ErmbXmlMessage xmlRequest)
	{
		try
		{
			UpdateResourceRq clientInfoMsg = xmlRequest.getUpdateResourceRq();

			ErmbProfileIdentity ermbProfileIdentity = SOSMessageHelper.getClientIdentity(clientInfoMsg.getProfile().getClientIdentity());

			//Обновляем список продуктов по ЕРМБ-профилю
			reloadProducts(ermbProfileIdentity);
		}
		catch (ParseException e)
		{
		  log.error("Ошибка разбора xml-сообщения с оповещение ЕРИБ об изменении продукта клиента", e);
		}
		catch(BusinessException e)
		{
			log.error("Ошибка обновления данных клиента", e);
		}
	}

	protected void reloadProducts(ErmbProfileIdentity personIdentity) throws BusinessException
	{
		ErmbProfileImpl profile = loadProfile(personIdentity);
		Person person = profile.getPerson();
		MigrationHelper.initContext(person);

		BankrollEngine bankrollEngine = getModule().getBankrollEngine();
		PersonBankrollManager bankrollManager = bankrollEngine.createPersonBankrollManager(person);
		bankrollManager.reloadProducts();

		//Если текущий клиент не имеет паспорта Way и он пришел в сообщении - сохранить
		boolean hasNoWayPassport = PersonHelper.getPersonPassportWay(profile.getPerson()) == null;
		ErmbProfileIdentityCard identityCard = personIdentity.getIdentityCard();
		boolean isWayPassport = PassportTypeWrapper.getClientDocumentType(identityCard.getIdType()) == ClientDocumentType.PASSPORT_WAY;
		if (hasNoWayPassport && isWayPassport)
		{
			String passport = StringHelper.getEmptyIfNull(identityCard.getIdSeries()) + StringHelper.getEmptyIfNull(identityCard.getIdNum());
			PersonHelper.updatePersonPassportWay(profile.getPerson(), passport.trim());
			personService.update(person);
		}
	}

	private ErmbProfileImpl loadProfile(ErmbProfileIdentity personIdentity) throws BusinessException
	{
        String surName = personIdentity.getSurName();
        String name = personIdentity.getFirstName();
        String patrName = personIdentity.getPatrName();
		ErmbProfileIdentityCard identityCard = personIdentity.getIdentityCard();
        String docSeries = identityCard.getIdSeries();
        String docNumber = identityCard.getIdNum();
        Calendar birthDate = personIdentity.getBirthDay();
        ErmbProfileImpl profile = profileService.findByFIOAndDocInTB(surName, name, patrName, docSeries, docNumber, birthDate,personIdentity.getTb());
        if (profile == null)
            throw new BusinessException("Не найден профиль ЕРМБ с ФИО: " + surName + " "  + name + " " + patrName + " ДУЛ: " + docNumber + " " + docSeries + " ДР: " + DateHelper.formatDateToString(birthDate));
		return profile;
	}
}
