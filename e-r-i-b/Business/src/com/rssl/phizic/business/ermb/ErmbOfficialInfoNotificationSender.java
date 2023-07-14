package com.rssl.phizic.business.ermb;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.persons.oldIdentity.PersonIdentityService;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.LoanLink;
import com.rssl.phizic.common.type.ErmbProfileIdentity;
import com.rssl.phizic.common.type.ErmbProfileIdentityCard;
import com.rssl.phizic.common.type.ErmbProfileInfo;
import com.rssl.phizic.common.type.PersonOldIdentity;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.TextMessage;
import com.rssl.phizic.common.types.client.ClientDocumentType;
import com.rssl.phizic.common.types.client.CreationType;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.AdditionalCardType;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.loans.Loan;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.messaging.MessageLogger;
import com.rssl.phizic.messaging.TranslitMode;
import com.rssl.phizic.messaging.ermb.ErmbJndiConstants;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.person.PersonDocumentType;
import com.rssl.phizic.synchronization.SOSMessageHelper;
import com.rssl.phizic.utils.MockHelper;
import com.rssl.phizic.utils.PassportTypeWrapper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.jms.JmsService;
import org.xml.sax.SAXException;

import java.util.*;
import javax.jms.JMSException;
import javax.xml.bind.JAXBException;

/**
 @author: EgorovaA
 @ created: 24.12.2012
 @ $Author$
 @ $Revision$
 */
public class ErmbOfficialInfoNotificationSender
{
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final JmsService jmsService = new JmsService();
	private static final PersonIdentityService personIdentityService = new PersonIdentityService();

	/**
	 * Преобразовать профиль клиента
	 * @param profile
	 * @return
	 */
	private ErmbProfileInfo prepareProfile(ErmbProfileImpl profile) throws BusinessException
	{
		ErmbProfileInfo newProfile = new ErmbProfileInfo();
		Long profileId = profile.getId();
		Person clientPerson = profile.getPerson();
		newProfile.setId(profileId);
		newProfile.setMainPhoneNumber(profile.getMainPhoneNumber());
		if (profile.isServiceStatus())
		{
			if (profile.isClientBlocked())
			{
				newProfile.setServiceStatus("blocked");
			}
			else if (profile.isPaymentBlocked())
			{
				newProfile.setServiceStatus("nonpayed");
			}
			else
			{
				newProfile.setServiceStatus("active");
			}
		}
		//0 – услуга не подключена
		else
		{
			newProfile.setServiceStatus(null);
		}
		newProfile.setPhoneNumbers(profile.getPhoneNumbers());
		newProfile.setProfileVersion(profile.getProfileVersion());
		newProfile.setNewProductNotification(profile.getNewProductNotification());
		newProfile.setSuppressAdv(profile.isSuppressAdv());
		newProfile.setDepositsTransfer(profile.getDepositsTransfer());
		newProfile.setTransliterateSms(clientPerson.getSMSFormat() != TranslitMode.DEFAULT);
		//временной интервал для отправки сообщений
		newProfile.setNotificationStartTime(profile.getNotificationStartTime());
		newProfile.setNotificationEndTime(profile.getNotificationEndTime());
		newProfile.setTimeZone(profile.getTimeZone());
		newProfile.setDaysOfWeek(profile.getDaysOfWeek());
		newProfile.setClientCategory(ErmbHelper.getMssClientCategoryCode(profile.getPerson()));

		List<Pair<String, String>> informProducts = new ArrayList<Pair<String, String>>();
		Map<String, Boolean> additionalCadrs = new HashMap<String, Boolean>();

		//Получаем карты клиента
		List<CardLink> cardLinks = profile.getCardLinks();
		Set<Card> cards = new HashSet<Card>();
		for (CardLink link : cardLinks)
		{
			Card card = link.getCard();
			if (MockHelper.isMockObject(card))
			{
				throw new BusinessException("[ЕРМБ]: невозможно отправить сообщение об обновлении профиля клиента с id = "
					+ profileId + "Не получена информация по карте № " + card.getNumber());
			}
			else
			{
				// если дополнительная карта находится в профиле доп. держателя
				if (!card.isMain())
				{
					boolean isOwnerProfile = card.getAdditionalCardType() != AdditionalCardType.OTHERTOCLIENT;
					additionalCadrs.put(link.getNumber(), isOwnerProfile);
				}


				cards.add(card);
				if (link.getErmbNotification())
					informProducts.add(new Pair<String, String>("card", link.getNumber()));
			}
		}
		newProfile.setCards(cards);
		newProfile.setAdditionalCadrs(additionalCadrs);

		//Получаем вклады клиента
		List<AccountLink> accountLinks = profile.getAccountLinks();
		Set<Account> accounts = new HashSet<Account>();
		for (AccountLink link : accountLinks)
		{
			Account account = link.getAccount();
			if (MockHelper.isMockObject(account))
			{
				throw new BusinessException("[ЕРМБ]: невозможно отправить сообщение об обновлении профиля клиента с id = "
					+ profileId + "Не получена информация по вкладу № " + account.getNumber());
			}
			else
			{
				accounts.add(account);
				if (link.getErmbNotification())
					informProducts.add(new Pair<String, String>("account", link.getNumber()));
			}
		}
		newProfile.setAccounts(accounts);
		
		//Получаем кредиты клиента
		List<LoanLink> loanLinks = profile.getLoanLinks();
		Set<Loan> loans = new HashSet<Loan>();
		for (LoanLink link : loanLinks)
		{
			Loan loan = link.getLoanShortCut();
			if (MockHelper.isMockObject(loan))
			{
				throw new BusinessException("[ЕРМБ]: невозможно отправить сообщение об обновлении профиля клиента с id = "
					+ profileId + "Не получена информация по кредиту № договора" + loan.getAgreementNumber());
			}
			else
			{
				loans.add(loan);
				if (link.getErmbNotification())
					informProducts.add(new Pair<String, String>("loan", link.getLoanShortCut().getAccountNumber()));
			}
		}
		newProfile.setLoans(loans);

		// Список продуктов, по которым надо отправлять уведомления
		newProfile.setInformProducts(informProducts);

		// Устаревшие идентификационные данные клиента
		List<ErmbProfileIdentity> oldIdentity = getOldIdentity(profile);
		newProfile.setOldIdentityList(oldIdentity);

		// Идентификационные данные клиента
		newProfile.setIdentity(getActualIdentity(clientPerson));
		newProfile.setUDBO(clientPerson.getCreationType() == CreationType.UDBO);

		return newProfile;
	}

	/**
	 * Получить идентификационые данные клиента
	 * @param clientPerson - клиент
	 * @return идентификационые данные клиента
	 */
	private ErmbProfileIdentity getActualIdentity(Person clientPerson) throws BusinessException
	{
		ErmbProfileIdentity identity = new ErmbProfileIdentity();
		identity.setFirstName(clientPerson.getFirstName());
		identity.setSurName(clientPerson.getSurName());
		identity.setPatrName(clientPerson.getPatrName());
		identity.setBirthDay(clientPerson.getBirthDay());
		ErmbProfileIdentityCard identityCard = new ErmbProfileIdentityCard();
		for(PersonDocument personDocument : clientPerson.getPersonDocuments())
		{
			if (personDocument.getDocumentMain())
			{
				PersonDocumentType docType = personDocument.getDocumentType();
				String passportType = null;
				if (docType != null)
					passportType = PassportTypeWrapper.getPassportType(PersonDocumentType.valueFrom(docType));
				else
				{
					Map<ClientDocumentType, String> types = PassportTypeWrapper.getAllTypes();
					passportType = types.get(ClientDocumentType.OTHER);
				}
				identityCard.setIdType(passportType);

				String docNum = personDocument.getDocumentNumber();
				String docSeries = personDocument.getDocumentSeries();

				if (docType == PersonDocumentType.PASSPORT_WAY)
				{
					if (StringHelper.isEmpty(docNum))
						identityCard.setIdNum(StringHelper.getEmptyIfNull(docSeries));
					else identityCard.setIdNum(docNum + StringHelper.getEmptyIfNull(docSeries));
				}
				else
				{
					identityCard.setIdNum(docNum);
					identityCard.setIdSeries(docSeries);
				}
				identityCard.setIssuedBy(personDocument.getDocumentIssueBy());
				identityCard.setIssueDt(personDocument.getDocumentIssueDate());
			}
		}
		identity.setIdentityCard(identityCard);
		identity.setTb(PersonHelper.getPersonTb(clientPerson));
		return identity;
	}

	/**
	 * Получить старые идентификационые данные клиента
	 * @param ermbProfile - ЕРМБ-профиль
	 * @return null, если у клиента нет старых(измененных) идентификационных данных
	 */
	private List<ErmbProfileIdentity> getOldIdentity(ErmbProfileImpl ermbProfile) throws BusinessException
	{
		List<PersonOldIdentity> pesonOldIdentities = null;
		try
		{
			pesonOldIdentities = personIdentityService.getListByProfile(ermbProfile.getPerson());
		}
		catch (BusinessException e)
		{
			log.error("Ошибка получения устаревших идентификационных данных клиента", e);
		}

		if (pesonOldIdentities == null)
			return null;
		List<ErmbProfileIdentity> oldIdentities = new ArrayList<ErmbProfileIdentity>(pesonOldIdentities.size());
		for (PersonOldIdentity personOldIdentity: pesonOldIdentities)
		{
			ErmbProfileIdentity oldIdentity = new ErmbProfileIdentity();
			oldIdentity.setFirstName(personOldIdentity.getFirstName());
			oldIdentity.setSurName(personOldIdentity.getSurName());
			oldIdentity.setPatrName(personOldIdentity.getPatrName());
			oldIdentity.setBirthDay(personOldIdentity.getBirthDay());
			oldIdentity.setDocTimeUpDate(personOldIdentity.getDateChange());

			ErmbProfileIdentityCard identityCardType = new ErmbProfileIdentityCard();

			PersonDocumentType oldType = personOldIdentity.getDocType();

			String docNum = personOldIdentity.getDocNumber();
			String docSeries = personOldIdentity.getDocSeries();

			if (oldType == PersonDocumentType.PASSPORT_WAY)
			{
				if (StringHelper.isEmpty(docNum))
					identityCardType.setIdNum(StringHelper.getEmptyIfNull(docSeries));
				else identityCardType.setIdNum(docNum + StringHelper.getEmptyIfNull(docSeries));
			}
			else
			{
				identityCardType.setIdNum(docNum);
				identityCardType.setIdSeries(docSeries);
			}

			String passportType = null;
			if (oldType != null)
				passportType = PassportTypeWrapper.getPassportType(PersonDocumentType.valueFrom(oldType));
			identityCardType.setIdType(passportType);
			identityCardType.setIssuedBy(personOldIdentity.getDocIssueBy());
			Calendar issueDate = personOldIdentity.getDocIssueDate();
			if (issueDate != null)
				identityCardType.setIssueDt(issueDate);
			oldIdentity.setIdentityCard(identityCardType);
			oldIdentity.setTb(PersonHelper.getPersonTb(personOldIdentity.getPerson()));
			oldIdentities.add(oldIdentity);
		}
		return oldIdentities;
	}

	/**
	 * Сформировать и отправить сообщение с информацией об измененных профилях клиента
	 * @param profileList - список измененных профилей
	 */
	public void sendNotification(List<ErmbProfileImpl> profileList)
	{
		List<ErmbProfileInfo> profileInfoList = new ArrayList<ErmbProfileInfo>();
		for (ErmbProfileImpl profile : profileList)
		{
			if (!profile.getProfileVersion().equals(profile.getConfirmProfileVersion()))
			{
				try
				{
					ErmbProfileInfo profileInfo = prepareProfile(profile);
					profileInfoList.add(profileInfo);
				}
				catch (BusinessException e)
				{
					log.error(e.getMessage(), e);
				}
			}
		}
		if (!profileInfoList.isEmpty())
		{
	        SOSMessageHelper sosMessageHelper = new SOSMessageHelper();
	        String text = null;
	        try
	        {
	            text = sosMessageHelper.getUpdateProfilesRqStr(profileInfoList);
	        }
	        catch (JAXBException e)
	        {
	            log.error(e.getMessage(), e);
	            return;
	        }
	        catch (SAXException e)
	        {
		        log.error(e.getMessage(), e);
		        return;
	        }
	        try
			{
				jmsService.sendMessageToQueue(text, ErmbJndiConstants.AUX_UPDATE_PROFILE_QUEUE, ErmbJndiConstants.AUX_UPDATE_PROFILE_CQF, null, null);
				new MessageLogger(new TextMessage(text,text), Application.ErmbAuxChannel,"ermb-UpdateProfile").makeAndWrite();
			}
			catch (JMSException e)
			{
				log.error(e.getMessage(), e);
			}
		}
	}
}
