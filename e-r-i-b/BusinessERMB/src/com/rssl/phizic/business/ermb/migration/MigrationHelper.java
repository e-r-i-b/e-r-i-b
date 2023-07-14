package com.rssl.phizic.business.ermb.migration;

import com.rssl.phizgate.mobilebank.MobileBankConfig;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.PrincipalImpl;
import com.rssl.phizic.auth.modes.AccessPolicy;
import com.rssl.phizic.auth.modes.AccessPolicyService;
import com.rssl.phizic.auth.modes.AccessType;
import com.rssl.phizic.auth.modes.AuthenticationConfig;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.service.TemplateDocumentService;
import com.rssl.phizic.business.ermb.ErmbHelper;
import com.rssl.phizic.business.ermb.ErmbProfileImpl;
import com.rssl.phizic.business.ermb.migration.mbk.MBKTemplateConvertor;
import com.rssl.phizic.business.ext.sbrf.mobilebank.UncompatibleServiceProviderException;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.CardLinkHelper;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.business.resources.external.MultiInstanceExternalResourceService;
import com.rssl.phizic.common.type.TimeZone;
import com.rssl.phizic.common.types.DaysOfWeek;
import com.rssl.phizic.common.types.client.ClientDocumentType;
import com.rssl.phizic.common.types.exceptions.IKFLException;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ermb.ErmbConfig;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.AdditionalCardType;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.mbv.ClentPhone;
import com.rssl.phizic.gate.mbv.ClientAccPh;
import com.rssl.phizic.gate.mbv.MbvClientIdentity;
import com.rssl.phizic.gate.mbv.MbvRegistrationInfo;
import com.rssl.phizic.gate.mobilebank.*;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.person.PersonDocumentType;
import com.rssl.phizic.security.ConfirmBean;
import com.rssl.phizic.security.ConfirmBeanService;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.security.SecurityUtil;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.utils.MaskUtil;
import com.rssl.phizic.utils.PhoneNumberFormat;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.store.SimpleStore;
import com.rssl.phizic.utils.store.StoreManager;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import java.sql.Time;
import java.util.*;

/**
 * @author Puzikov
 * @ created 07.02.14
 * @ $Author$
 * @ $Revision$
 */

public class MigrationHelper
{
	private static final ServiceProviderService providerService = new ServiceProviderService();
	private static final ExternalResourceService externalResourceService = new ExternalResourceService();
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	/**
	 * @param client1 ������ 1
	 * @param client2 ������ 1
	 * @return true ���� ���������� �������
	 */
	public static boolean isSameClient(MbvClientIdentity client1, Client client2)
	{
		if (!ErmbHelper.isSameFIOAndBirthday(client1.getFirstName(), client1.getSurName(), client1.getPatrName(), client1.getBirthDay(),
				client2.getFirstName(), client2.getSurName(), client2.getPatrName(), client2.getBirthDay()))
			return false;

		List<? extends ClientDocument>  clientDocuments2 = client2.getDocuments();

		for(ClientDocument doc1:clientDocuments2)
		{
			String seriesAndNumber1 = (StringHelper.getEmptyIfNull(doc1.getDocSeries()) + StringHelper.getEmptyIfNull(doc1.getDocNumber())).replaceAll(" ", "");
			String seriesAndNumber2 = (StringHelper.getEmptyIfNull(client1.getDocSeries()) + StringHelper.getEmptyIfNull(client1.getDocNumber())).replaceAll(" ", "");

			if (seriesAndNumber1.equalsIgnoreCase(seriesAndNumber2))
				return true;
		}
		return false;
	}

	/**
	 * ���������� ���������� � ������� ����������� �� ���������
	 * @param ermbProfile ������� ����
	 */
	public static void updateDefaultNotificationTime(ErmbProfileImpl ermbProfile)
	{
		//�������� �� ���������
		ermbProfile.setDaysOfWeek(new DaysOfWeek(true));
		ermbProfile.setTimeZone(TimeZone.MOSCOW.getCode());
		ermbProfile.setNotificationStartTime(Time.valueOf("09:00:00"));
		ermbProfile.setNotificationEndTime(Time.valueOf("22:00:00"));
	}

	/**
	 * ��������������� � �������� ������� �� ��� � ����
	 * @param profile ���� �������
	 * @param mbkTemplates ������ ��������
	 * @throws BusinessException
	 */
	public static void saveMbkTemplates(ErmbProfileImpl profile, List<MobileBankTemplate> mbkTemplates) throws BusinessException, BusinessLogicException
	{
		if (CollectionUtils.isEmpty(mbkTemplates))
			return;

		MBKTemplateConvertor mbkTemplateConvertor = new MBKTemplateConvertor();
		for (MobileBankTemplate mbkTemplate : mbkTemplates)
		{
			Person person = profile.getPerson();
			String recipient = mbkTemplate.getRecipient();
			String cardNumber = mbkTemplate.getCardInfo().getCardNumber();
			List<String> payerCodes = Arrays.asList(mbkTemplate.getPayerCodes());

			BillingServiceProvider serviceProvider = providerService.findByMobileBankCode(recipient);
			//���������� �� ��������� �����������
			if (serviceProvider == null)
			{
				log.error("���������� � ����� " + recipient + " �� ������� ����� � ����, ������ ��������.");
				continue;
			}
			CardLink cardLink = externalResourceService.findLinkByNumber(person.getLogin(), ResourceType.CARD, cardNumber);
			//��� ����� ������ ���� ������������ �� �������� �������
			if (cardLink == null)
			{
				log.error("�� ������� ����� ���� �����, ����������� � �������");
				continue;
			}

			try
			{
				TemplateDocument template = mbkTemplateConvertor.getTemplate(person, serviceProvider, cardLink, payerCodes);
				TemplateDocumentService.getInstance().addOrUpdate(template);
			}
			catch (UncompatibleServiceProviderException e)
			{
				log.error(e);
			}
		}
	}

	/**
	 * ��������� ������ �� ���
	 * @param profile ���� �������
	 * @param mbkPhoneOffers ������
	 * @param log ���, null - �� ����������
	 */
	public static void savePhoneOffers(ErmbProfileImpl profile, List<String> mbkPhoneOffers, Log log)
	{
		ConfirmBeanService confirmBeanService = new ConfirmBeanService();

		Long loginId = profile.getPerson().getLogin().getId();
		for(String confirmCode:mbkPhoneOffers)
		{
			ConfirmBean confirmBean = new ConfirmBean();
			confirmBean.setLoginId(loginId);
			confirmBean.setPrimaryConfirmCode(confirmCode);
			confirmBean.setSecondaryConfirmCode(confirmCode);
			Date now = new Date();
			ErmbConfig config = ConfigFactory.getConfig(ErmbConfig.class);
			Date expireTime = DateUtils.addSeconds(now, config.getConfirmBeanSecondsActive());
			Date overdueTime = DateUtils.addSeconds(now, config.getConfirmBeanTimeToLife());
			confirmBean.setExpireTime(expireTime);
			confirmBean.setOverdueTime(overdueTime);
			String mainPhoneNumber = profile.getMainPhoneNumber();
			if (StringUtils.isNotEmpty(mainPhoneNumber))
			{
				confirmBean.setPhone(mainPhoneNumber);
			}
			else
			{
				//������� ������� ���������� - ��������� � ���� ����������� ��� �������������
				if (log != null)
					log.error("� �������� �������� ��� �� ������� �������� � ���� confirm-���, c ����� " + confirmBean.getPrimaryConfirmCode() + ". ���������� �������.");
				continue;
			}
			if (!confirmBeanService.addConfirmBean(confirmBean) && log != null)
				log.error("� �������� �������� ��� �� ������� �������� � ���� confirm-���, c ����� " + confirmBean.getPrimaryConfirmCode() + ". ��� �� ��������.");
		}
	}

	/**
	 * ������������� ����� ��� ������� � ���
	 * ��������� �������� ������� ������� ���� ��� ��������� �����, � �.�. ��� ��������� ���. ����� �������
	 * @param cardLinks ����� �������
	 * @return ����� ����
	 */
	public static Set<MbkCard> getCardsToMigrate(Collection<CardLink> cardLinks) throws BusinessException, BusinessLogicException
	{
		Set<MbkCard> cards = new HashSet<MbkCard>();
		if (cardLinks == null)
			return cards;

		for (CardLink cardLink : cardLinks)
		{
			MbkCard card = new MbkCard();
			card.setNumber(cardLink.getNumber());

			boolean main = cardLink.isMain();
			AdditionalCardType additionalCardType = cardLink.getAdditionalCardType();
			if (main)
			{
				card.setCardType(MigrationCardType.MAIN);
				card.setErmbConnected(false);
			}
			else if (additionalCardType == null)
			{
				log.warn("����� �� ��������, �� ��� ���. ����� �� ���������. �������� � ������� � ��� ��� ��������. linkId=" + cardLink.getId());
				card.setCardType(MigrationCardType.MAIN);
				card.setErmbConnected(false);
			}
			else
			{
				switch (additionalCardType)
				{
					case CLIENTTOCLIENT:
						card.setCardType(MigrationCardType.ADDITIONAL);
						card.setErmbConnected(false);
						break;
					case CLIENTTOOTHER:
						card.setCardType(MigrationCardType.ADDITIONAL_TO_OTHER);
						//� ���� ������ ��������� ����� - ������ ������, ����� �������� ��������� ��� �� ������� ������� ����
						Client cardClient = cardLink.getCardClient();
						if (cardClient == null)
							throw new BusinessLogicException("�� ������ ��������� ���. �����. cardNumber = " + MaskUtil.getCutCardNumberForLog(cardLink.getNumber()));

						card.setErmbConnected(ErmbHelper.hasErmbProfileByClient(cardClient));
						break;
					case OTHERTOCLIENT:
						card.setCardType(MigrationCardType.OTHERS_ADDITIONAL);
						card.setErmbConnected(false);
						break;
					default:
						throw new IllegalArgumentException("����������� ��� ���. �����");
				}
			}
			cards.add(card);
		}

		return cards;
	}

	/**
	 * �� ����� ��������� �������� ������ ��� ������� �������: ������������ ����, ���� ���� �� ������ �����,
	 * ���� ���-�� ���� �� ������� �� ����� �������.
	 * @param all ��� ������
	 * @return ������ �������
	 */
	public static CommitMigrationResult findBestCondition(Collection<CommitMigrationResult> all)
	{
		if (CollectionUtils.isEmpty(all))
			throw new IllegalArgumentException("�� ������� �������� ������ ������ ������ ���");

		return Collections.max(all, new Comparator<CommitMigrationResult>()
		{
			public int compare(CommitMigrationResult firstResult, CommitMigrationResult secondResult)
			{
				ClientTariffInfo first = firstResult.getTariffInfo();
				ClientTariffInfo second = secondResult.getTariffInfo();

				//������ ����� ������������ ����������
				boolean firstFull = first.getLinkTariff() == 1;
				boolean secondFull = second.getLinkTariff() == 1;
				if (firstFull && !secondFull)
					return 1;
				else if (!firstFull && secondFull)
					return -1;

				//����������������� �� �������� ������������
				boolean firstBlocked = first.getLinkPaymentBlockID() == 1;
				boolean secondBlocked = second.getLinkPaymentBlockID() == 1;
				if (!firstBlocked && secondBlocked)
					return 1;
				else if (firstBlocked && !secondBlocked)
					return -1;

				//������� ���������� ������
				if (second.getNextPaidPeriod() == null)
					return 1;
				if (first.getNextPaidPeriod() == null)
					return -1;
				Calendar firstNextPaidPeriod = first.getNextPaidPeriod();
				Calendar secondNextPaidPeriod = second.getNextPaidPeriod();
				if (!firstNextPaidPeriod.equals(secondNextPaidPeriod))
					return firstNextPaidPeriod.compareTo(secondNextPaidPeriod);

				//����� ������� ����� �������
				return (first.getPayDate() > second.getPayDate()) ? 1 : -1;
			}
		});
	}

	/**
	 * �������� ���������� ���������������� ������ �� ���
	 * ���� ������� �� �� ��������� � ���, ���������� ������������� ����������
	 * @param client ������
	 * @return ���+���+��+�������� �� ���
	 */
	public static MbvRegistrationInfo getMbvInfo(Client client) throws BusinessException
	{
		MbvRegistrationInfo result = new MbvRegistrationInfo();
		List<MbvClientIdentity> identities = new ArrayList<MbvClientIdentity>();
		Set<String> phones = new HashSet<String>();
		Set<String> accounts = new HashSet<String>();
		result.setIdentities(identities);
		result.setPhones(phones);
		result.setAccounts(accounts);
		if (!ConfigFactory.getConfig(DepoMobileBankConfig.class).isMbvAvaliability())
		{
			return result;
		}

		DepoMobileBankService mbvService = GateSingleton.getFactory().service(DepoMobileBankService.class);

		//���������� ����� �� ��� ���
		for (ClientDocument doc : client.getDocuments())
		{

			MbvClientIdentity identity = new MbvClientIdentity();
			identity.setFirstName(client.getFirstName());
			identity.setSurName(client.getSurName());
			identity.setPatrName(client.getPatrName());
			identity.setBirthDay(client.getBirthDay());

			ClientDocumentType docType = doc.getDocumentType();
			//�� ���������� ����� ��� ��������  WAY, ��� � MBV  ��� (BUG063066)
			if (docType == ClientDocumentType.PASSPORT_WAY)
				continue;
			identity.setDocType(PersonDocumentType.valueOf(docType));
			identity.setDocNumber(doc.getDocNumber());
			identity.setDocSeries(doc.getDocSeries());
			try
			{
				ClientAccPh clientAccPh = mbvService.getClientAccPh(identity);
				if (CollectionUtils.isNotEmpty(clientAccPh.getPhoneList()) && CollectionUtils.isNotEmpty(clientAccPh.getAccList()))
				{
					identities.add(identity);
					for (ClentPhone resPhon : clientAccPh.getPhoneList())
					{
						String validPhone = ErmbHelper.getValidPhoneNumber(resPhon.getPhoneNumber());
						if (!StringHelper.isEmpty(validPhone))
							phones.add(validPhone);
					}
					accounts.addAll(clientAccPh.getAccList());
				}
			}
			catch (GateException e)
			{
				throw new BusinessException(e);
			}
		}

		return result;
	}

	/**
	 * �������� ������ ��������� ��� ��������� ������ � QUERY_PROFILE
	 * ������ ������������ ��� �������� � ����, �.�. ��� ��� �� �������������
	 * @param person person
	 * @return ����� ������ ��������� �������. �� ���, ��� � ������.
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public static Set<String> getAllPhoneNumbersForResponse(ActivePerson person) throws BusinessException, BusinessLogicException
	{
		Set<String> clientPhones = new HashSet<String>();
		//��������� ��������� �� ���
		clientPhones.addAll(getMbkPhones(person));
		//��������� ��������� �� ���
		DepoMobileBankConfig mbvConfig = ConfigFactory.getConfig(DepoMobileBankConfig.class);
		if (mbvConfig.isMbvAvaliability())
			clientPhones.addAll(getMbvPhones(person));
		//���������� ��������� �� ������ �������.
		String homePhone = person.getHomePhone();
		if (!StringHelper.isEmpty(homePhone))
		{
			String validHomePhone = ErmbHelper.getValidPhoneNumber(homePhone);
			if (!StringHelper.isEmpty(validHomePhone))
				clientPhones.add(validHomePhone);
		}

		String jobPhone = person.getJobPhone();
		if (!StringHelper.isEmpty(jobPhone))
		{
			String validJobPhone = ErmbHelper.getValidPhoneNumber(jobPhone);
			if (!StringHelper.isEmpty(validJobPhone))
				clientPhones.add(validJobPhone);
		}

		String mobilePhone = person.getMobilePhone();
		if (!StringHelper.isEmpty(mobilePhone))
		{
			String validMobilePhone = ErmbHelper.getValidPhoneNumber(mobilePhone);
			if (!StringHelper.isEmpty(validMobilePhone))
				clientPhones.add(validMobilePhone);
		}

		return clientPhones;
	}

	private static Set<String> getMbkPhones(ActivePerson person) throws BusinessLogicException, BusinessException
	{
		MultiInstanceExternalResourceService externalResourceService = new MultiInstanceExternalResourceService();
		List<CardLink> links = externalResourceService.getLinks(person.getLogin(), CardLink.class, null);
		List<String> cardNumbers = CardLinkHelper.listCardNumbers(links);
		try
		{
			Set<String> mbkPhones = new HashSet<String>();
			MobileBankService mbkService = GateSingleton.getFactory().service(MobileBankService.class);
			if (ConfigFactory.getConfig(MobileBankConfig.class).isUsePackRegistrations())
			{
				List<MobileBankRegistration> registrations = mbkService.getRegistrationsPack(false, cardNumbers.toArray(new String[cardNumbers.size()]));
				mbkPhones.addAll(ErmbHelper.getPhonesFromMbkRegistrations(registrations));
			}
			else
			{
				GroupResult<String, List<MobileBankRegistration>> registrations = mbkService.getRegistrations(false, cardNumbers.toArray(new String[cardNumbers.size()]));
				GroupResultHelper.checkAndThrowAnyException(registrations);

				for (String cardNumber : registrations.getKeys())
				{
					List<MobileBankRegistration> registrationByNumber = registrations.getResult(cardNumber);
					mbkPhones.addAll(ErmbHelper.getPhonesFromMbkRegistrations(registrationByNumber));
				}
			}
			return mbkPhones;
		}
		catch (IKFLException e)
		{
			throw new BusinessException("������ ��������� ���������� �� ���", e);
		}
	}

	private static Set<String> getMbvPhones(ActivePerson person) throws BusinessException
	{
		return getMbvInfo(person.asClient()).getPhones();
	}

	/**
	 * �������� ����������� � ���� �������� �� ���
	 * � ������� ���������� ������������� �������� ��������� ������ ���� ��������
	 * �������� ���������� � ������� �������� � ������ ������� �����, ����� ��������� (���������� ������������ ��������)
	 * ��� �������� ��� ���������� �� ����������� ������������
	 * @param phones ������ ��������� �� �������� (������ ���� � ���)
	 */
	public static void removePhoneFromMbk(Set<String> phones) throws BusinessException
	{
		MobileBankService mobileBankService = GateSingleton.getFactory().service(MobileBankService.class);
		Long migrationId;
		try
		{
			//---------------
			//BEGIN MIGRATION
			//---------------
			BeginMigrationResult migrationResult = mobileBankService.beginMigrationErmb(
					Collections.<MbkCard>emptySet(),
					phones,
					Collections.<String>emptySet(),
					MigrationType.PILOT
			);
			migrationId = migrationResult.getMigrationId();
		}
		catch (GateException e)
		{
			//ROLLBACK �� �����, �.�. ������� ����� - ������� ������
			throw new BusinessException("�� ������ ����� ��������", e);
		}
		catch (GateLogicException e)
		{
			throw new BusinessException("�� ������ ����� ��������", e);
		}

		//----------------
		//COMMIT MIGRATION
		//----------------
		try
		{
			mobileBankService.commitMigrationErmb(migrationId);
		}
		catch (GateException e)
		{
			throw new BusinessException("�� ������ ������ ��������", e);
		}
		catch (GateLogicException e)
		{
			throw new BusinessException("�� ������ ������ ��������", e);
		}
	}

	/**
	 * ������������� ��������� ��� ��������
	 * �������, ��������� ��� ���������� ��������� ������� ��� ������
	 * @param person ������
	 */
	public static void initContext(Person person) throws BusinessException
	{
		StoreManager.setCurrentStore(new SimpleStore());
		Login login = person.getLogin();

		AuthenticationConfig authConfig = ConfigFactory.getConfig(AuthenticationConfig.class);
		AccessPolicy policy = authConfig.getPolicy(AccessType.simple);

		try
		{
			AccessPolicyService accessPolicyService = new AccessPolicyService();
			Properties properties = accessPolicyService.getProperties(login, policy.getAccessType());
			SecurityUtil.createAuthModule(new PrincipalImpl(login, policy, properties));
		}
		catch (SecurityDbException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ������� ������ ��������� �� ������ ���
	 * @param beginMigrationResult ������ �� �����
	 * @return ����� ������� ���������
	 */
	public static Set<String> extractMbkPhoneNumbers(BeginMigrationResult beginMigrationResult)
	{
		Set<String> result = new HashSet<String>();
		for (MbkConnectionInfo info : beginMigrationResult.getMbkConnectionInfo())
		{
			result.add(PhoneNumberFormat.MOBILE_INTERANTIONAL.format(info.getPhoneNumber()));
		}
		return result;
	}
}
