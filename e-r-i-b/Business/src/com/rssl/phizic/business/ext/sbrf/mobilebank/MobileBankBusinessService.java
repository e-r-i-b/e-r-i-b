package com.rssl.phizic.business.ext.sbrf.mobilebank;

import com.rssl.phizgate.mobilebank.MobileBankConfig;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.common.types.exceptions.IKFLException;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.mobilebank.*;
import com.rssl.phizic.utils.MaskUtil;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.*;

import static com.rssl.phizic.business.ext.sbrf.mobilebank.MobileBankUtils.buildTemplatesList;

/**
 * @author Erkin
 * @ created 10.03.2011
 * @ $Author$
 * @ $Revision$
 * @deprecated ���������� �� ���
 */

/**
 * ������ ��� ������ � ��������� ������ � �������
 */
@Deprecated
//todo CHG059738 �������
public class MobileBankBusinessService extends MobileBankBusinessServiceBase
{
	private static final String QUERY_PREFIX = "com.rssl.phizic.business.ext.sbrf.mobilebank.MobileBankBusinessService";

	private static final SimpleService simpleService = new SimpleService();

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ���������� ������� ����������� � ��������� �����
	 * @param login - ����� �������
	 * @return ��������� ����������� (never null)
	 */
	public List<RegistrationProfile> getRegistrationProfiles(Login login) throws BusinessException, BusinessLogicException
	{
		if (login == null)
			throw new NullPointerException("�������� 'login' �� ����� ���� null");

		// 1. �������� ����
		List<CardLink> cardlinks = loadPersonCardLinks(login);
		String[] cards = collectCardNumbers(cardlinks);

		// 2. �������� �����������
		List<MobileBankRegistration> registrations = loadRegistrations(cards);

		// 3. �������� �������� SMS-��������
		List<MobileBankTemplate> smsTemplates = loadSmsTemplates(registrations);

		// 4. ������������ �������� ����������� (�� �������� ����-����� � SMS-��������)
		List<RegistrationProfile> profiles = buildProfiles(registrations);

		// 5. ���������� SMS-�������� � ����-������ � �������
		bindSmsCommands(profiles, smsTemplates);
		bindCardLinks(profiles, cardlinks);

		return profiles;
	}

	/**
	 * ��������� ������� ����������� �� ���� ������ �������� � �����
	 * @param login - ����� �������
	 * @param phoneCode - �������������� ����� �������� (���-��� ������ � ���� ������)
	 * @param cardCode - �������������� ����� ����� (��������� N ���� ������)
	 * @return ������� ����������� ���� null, ���� �� ������
	 */
	public RegistrationProfile getRegistrationProfile(Login login, String phoneCode, String cardCode)
			throws BusinessException, BusinessLogicException
	{
		if (login == null)
			throw new NullPointerException("�������� 'login' �� ����� ���� null");
		if (StringHelper.isEmpty(phoneCode))
			throw new IllegalArgumentException("Argument 'phoneCode' cannot be null nor empty");
		if (StringHelper.isEmpty(cardCode))
			throw new IllegalArgumentException("Argument 'cardCode' cannot be null nor empty");

		// 1. ��������� ����
		Collection<CardLink> cardlinks = loadPersonCardLinks(login);
		String[] cards = collectCardNumbers(cardlinks);

		// 2. ����� ������ �����
		CardLink cardlink = selectCardLinkByCardCode(cardlinks, cardCode);
		if (cardlink == null)
			return null;
		String cardNumber = cardlink.getNumber();

		// 3. �������� ���� ��-����������� �������
		List<MobileBankRegistration> allRegistrations = loadRegistrations(cards);

		// 4. ����� ������ ��-����������� �� ���� ������ �������� � ������ �����
		MobileBankRegistration registration = selectRegistration(allRegistrations, phoneCode, cardlink.getNumber());
		if (registration == null)
			return null;
		String phoneNumber = registration.getMainCardInfo().getMobilePhoneNumber();

		// 5. �������� �������� SMS-�������� ��� ��������� ��-�����������
		List<MobileBankTemplate> smsTemplates = loadSmsTemplates(Collections.singletonList(registration));

		// 6. ������������ �������� ����������� (��� ����� ����-������ � SMS-��������)
		// ��� ����������� ������������ ������� ����� ��� ��-����������� (��. ������ RegistrationProfile.showCardNumberInSms)
		List<RegistrationProfile> allProfiles = buildProfiles(allRegistrations);

		// 7. ����� ������� ������� ����������� �� ������ �������� � ������ �����
		RegistrationProfile profile = selectProfile(allProfiles, phoneNumber, cardNumber);

		// 8. ���������� SMS-�������� � ����-������ � ���������� ������� �����������
		List<RegistrationProfile> profileAsList = Collections.singletonList(profile);
		bindSmsCommands(profileAsList, smsTemplates);
		bindCardLinks(profileAsList, cardlinks);

		return profile;
	}

	/**
	 * ���������� �������� ����������� ��� ���������� �������
	 * @param login - ����� �������
	 * @return ������ ��������� ����������� (never null)
	 */
	public List<RegistrationShortcut> getRegistrationShortcuts(Login login) throws BusinessException, BusinessLogicException
	{
		if (login == null)
			throw new NullPointerException("�������� 'login' �� ����� ���� null");

		// 1. ��������� ����
		Collection<CardLink> cardlinks = loadPersonCardLinks(login);
		String[] cards = collectCardNumbers(cardlinks);

		// 2. ��������� �����������
		List<MobileBankRegistration> registrations = loadRegistrations(cards);

		// 3. ����������� ���������
		List<RegistrationShortcut> shortcuts = new ArrayList<RegistrationShortcut>(registrations.size());
		for (MobileBankRegistration registration : registrations)
			shortcuts.add(createRegistrationShortcut(registration));

		return shortcuts;
	}

	/**
	 * ���������� ������� ����������� �� ���� ������ �������� � �����
	 * @param login - ����� �������
	 * @param phoneCode - �������������� ����� �������� (���-��� ������ � ���� ������)
	 * @param cardCode - �������������� ����� ����� (��������� N ���� ������)
	 * @return ������� �����������
	 */
	public RegistrationShortcut getRegistrationShortcut(Login login, String phoneCode, String cardCode)
			throws BusinessException, BusinessLogicException
	{
		if (login == null)
			throw new NullPointerException("�������� 'login' �� ����� ���� null");
		if (StringHelper.isEmpty(phoneCode))
			throw new IllegalArgumentException("Argument 'phoneCode' cannot be null nor empty");
		if (StringHelper.isEmpty(cardCode))
			throw new IllegalArgumentException("Argument 'cardCode' cannot be null nor empty");

		// 1. ��������� �����
		Card card = getPersonCardByNumberCode(login, cardCode);
		if (card == null)
			return null;

		// 2. ��������� ����������� �� �����
		List<MobileBankRegistration> registrations = loadRegistrations(new String[]{card.getNumber()});

		// 3. ����� ������ ����������� �� ������ ��������
		for (MobileBankRegistration registration : registrations) {
			String phoneNumber = registration.getMainCardInfo().getMobilePhoneNumber();
			if (MobileBankUtils.getPhoneCode(phoneNumber).equals(phoneCode))
				return createRegistrationShortcut(registration);
		}

		return null;
	}

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ���������� ��-������ �� ID
	 * @param login - ����� �������
	 * @param updateId - ������������� ��-�������
	 * @return ��-������ ��� null, ���� �� ������
	 */
	public PaymentTemplateUpdate getUpdate(Login login, long updateId) throws BusinessException
	{
		PaymentTemplateUpdate update = simpleService.findById(PaymentTemplateUpdate.class, updateId);
		if (update == null)
			return null;

		if (!login.getId().equals(update.getLogin().getId()))
			throw new BusinessException("������ �������� SMS-�������� " +
					"�� �������� �������� ������������. " +
					"PaymentTemplateUpdate ID = " + updateId + ", " +
					"LoginID = " + login.getId());

		return update;
	}

	/**
	 * ��������� ��-������ � ���� ����
	 * @param update - ��-������
	 */
	public void saveUpdate(PaymentTemplateUpdate update) throws BusinessException
	{
		if (update == null)
			throw new NullPointerException("�������� 'update' �� ����� ���� null");

		simpleService.addOrUpdate(update);
	}

	/**
	 * ���������� ��-������ � ��������� ����
	 * @param update - ��-������
	 */
	public void applyUpdate(PaymentTemplateUpdate update) throws BusinessException, BusinessLogicException
	{
		Login login = (Login) update.getLogin();

		CardLink cardLink = getPersonCardByNumber(login, update.getCardNumber());
		if (cardLink == null)
			throw new BusinessException("�� ������� ����� �����������");

		MobileBankCardInfo cardInfo = new BusinessMobileBankCardInfo(
				cardLink.getNumber(), MobileBankCardStatus.ACTIVE, update.getPhoneNumber());

		List<MobileBankTemplate> templates = buildTemplatesList(cardInfo, update.getDestlist());
		switch (update.getType()) {
			case ADD:
				addTemplates(cardInfo, templates);
				break;
			case REMOVE:
				removeTemplates(cardInfo, templates);
				break;
			default:
				log.warn("����������� ��� ���������� �������� ��������: " + update.getType());
				return;
		}
	}

	/**
	 * ������� ��-������ �� ���� ����
	 * @param update - ��-������
	 */
	public void deleteUpdate(PaymentTemplateUpdate update) throws BusinessException
	{
		if (update == null)
			throw new NullPointerException("�������� 'update' �� ����� ���� null");
		simpleService.remove(update);
	}

	/**
	 * ���������� SMS-�������� ��-�������
	 * @param update - update ��� ������ ��������
	 * @return ������ SMS-��������
	 */
	public List<SmsCommand> getUpdateSmsCommands(PaymentTemplateUpdate update) throws BusinessException, BusinessLogicException
	{
		Login login = (Login) update.getLogin();
		String phoneNumber = update.getPhoneNumber();
		String cardNumber = update.getCardNumber();
		RegistrationProfile profile = getRegistrationProfile(login, MobileBankUtils.getPhoneCode(phoneNumber), MobileBankUtils.getCardCode(cardNumber));
		if (profile == null)
			return Collections.emptyList();

		// ��������� ��� �������� ������ ��������
		// ��� ���� ����� ������, ��� ����� ��������� ����� ������� ��� ������������,
		// �� ����� "�������" �� ������� (������ ������ ������� ������� �� ���������������),
		// � ����� � ����� ������ ����� ������� �� �������
		CardShortcut mainCardShortcut = profile.getMainCard();
		MobileBankCardInfo mainCardInfo = getProfileMainCardInfo(profile);

		List<SmsCommand> oldCommands = mainCardShortcut.getPaymentSmsTemplates();

		List<MobileBankTemplate> oldTemplates = MobileBankUtils.buildTemplatesList(mainCardInfo, oldCommands);
		List<MobileBankTemplate> newTemplates = MobileBankUtils.buildTemplatesList(mainCardInfo, update.getDestlist());

		// ��������� ������ ������ ��������
		List<MobileBankTemplate> allTemplates = new LinkedList<MobileBankTemplate>();
		allTemplates.addAll(oldTemplates);
		allTemplates.addAll(newTemplates);

		Map<String, String> subServiceCodes = getSubServiceCodes(allTemplates);
		List<SmsCommand> allCommands;
		if (profile.isShowCardNumberInSms())
			allCommands = smsComposer.composePaymentSmsTemplates(allTemplates, subServiceCodes, cardNumber);
		else allCommands = smsComposer.composePaymentSmsTemplates(allTemplates, subServiceCodes, null);

		// ������� ����� ��������
		List<SmsCommand> newCommands = new LinkedList<SmsCommand>();
		for (SmsCommand command : allCommands)
		{
			if (StringHelper.isEmpty(command.getRecipientCode()))
				continue;
			if (StringHelper.isEmpty(command.getPayerCode()))
				continue;
			for (MobileBankTemplate template : newTemplates)
			{
				boolean eq = StringUtils.equals(template.getRecipient(), command.getRecipientCode());
				eq=eq && ArrayUtils.contains(template.getPayerCodes(), command.getPayerCode());
				if (eq) {
					newCommands.add(command);
					break;
				}
			}
		}
		return newCommands;
	}

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ��������� ��-����������� �� ��������� ������ ���������� ������������
	 * @param cardNumbers - ������ ������� ����
	 * @return ������ ��-����������� (never null)
	 */
	public List<MobileBankRegistration> loadRegistrations(String[] cardNumbers) throws BusinessException, BusinessLogicException
	{
		return loadRegistrations(cardNumbers, false);
	}

	/**
	 * ��������� ��-����������� �� ��������� ������ ���������� ������������
	 * @param cardNumbers - ������ ������� ����
	 * @param alternative - ������������ �������������� ������ ��������� ����������� (����������� � GetRegistrationsJDBCAction)
	 * @return ������ ��-����������� (never null)
	 */
	public List<MobileBankRegistration> loadRegistrations(String[] cardNumbers, boolean alternative) throws BusinessException, BusinessLogicException
	{
		if (ArrayUtils.isEmpty(cardNumbers))
			return Collections.emptyList();

		// 1. ������ � �����
		MobileBankService mobileBankService = GateSingleton.getFactory().service(MobileBankService.class);
		if (ConfigFactory.getConfig(MobileBankConfig.class).isUsePackRegistrations())
		{
			try
			{
				return mobileBankService.getRegistrationsPack(alternative, cardNumbers);
			}
			catch (GateException e)
			{
				throw new BusinessException(e);
			}
			catch (GateLogicException e)
			{
				throw new BusinessLogicException(e);
			}
		}
		else
		{
			GroupResult<String, List<MobileBankRegistration>> result = mobileBankService.getRegistrations(alternative, cardNumbers);
			List<MobileBankRegistration> registrations = new ArrayList<MobileBankRegistration>();
			// 2. ��������� ����������
			for (String cardNumber : cardNumbers)
			{
				IKFLException ex = result.getException(cardNumber);
				if (ex != null)
					log.error("���� ��� ��������� ������ ����������� �� ����� " + MaskUtil.getCutCardNumberForLog(cardNumber), ex);

				List<MobileBankRegistration> cardRegistrations = result.getResult(cardNumber);
				if (CollectionUtils.isEmpty(cardRegistrations))
					continue;

				registrations.addAll(cardRegistrations);
			}
			return registrations;
		}
	}

	/**
	 *
	 * ���������� ������ ������� �������� ��� ���������� ��������
	 *
	 * @param phoneNumber ����� ��������
	 * @param status ������
	 * @throws BusinessException
	 */
	public QuickServiceStatusCode setQuickServiceStatus(String phoneNumber, QuickServiceStatusCode status) throws BusinessException
	{
		try
		{
			MobileBankService mobileBankService = GateSingleton.getFactory().service(MobileBankService.class);
			return mobileBankService.setQuickServiceStatus(phoneNumber, status);
		}
		catch (GateException e)
		{
			throw new BusinessException(e.getMessage(), e);
		}
		catch (GateLogicException e)
		{
			throw new BusinessException(e.getMessage(), e);
		}
	}

	/**
	 * ��������� ��-������� SMS-�������� ��� ��������� ��-�����������
	 * @param registrations - ��������� ��-�����������
	 * @return ������ �������� SMS-�������� (never null)
	 */
	private List<MobileBankTemplate> loadSmsTemplates(Collection<MobileBankRegistration> registrations) throws BusinessException
	{
		if (registrations.isEmpty())
			return Collections.emptyList();

		MobileBankCardInfo[] mainCardInfos = collectMainCardInfos(registrations);

		// 1. ������ � �����
		MobileBankService mobileBankService = GateSingleton.getFactory().service(MobileBankService.class);
		GroupResult<MobileBankCardInfo, List<MobileBankTemplate>> result =
				mobileBankService.getTemplates(null, mainCardInfos);

		// 2. ��������� ����������
		List<MobileBankTemplate> templates = new LinkedList<MobileBankTemplate>();
		for (MobileBankCardInfo cardInfo : mainCardInfos)
		{
			Exception ex = result.getException(cardInfo);
			if (ex != null) {
				log.error("���� ��� ��������� ������ �������� ��������. " +
						"�������: " + cardInfo.getMobilePhoneNumber() + ". " +
						"�����: " + MaskUtil.getCutCardNumberForLog(cardInfo.getCardNumber()), ex);
			}

			List<MobileBankTemplate> cardTemplates = result.getResult(cardInfo);
			if (CollectionUtils.isEmpty(cardTemplates))
				continue;

			templates.addAll(cardTemplates);
		}

		// 3. �������� ��������� � ����� ������������
		return templates;
	}

	private void addTemplates(MobileBankCardInfo cardInfo, List<MobileBankTemplate> templates) throws BusinessException, BusinessLogicException
	{
		try
		{
			MobileBankService mobileBankService = GateSingleton.getFactory().service(MobileBankService.class);
			mobileBankService.addTemplates(cardInfo, templates);
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
		catch (GateLogicException e)
		{
			throw new BusinessLogicException(e);
		}
	}

	private void removeTemplates(MobileBankCardInfo cardInfo, List<MobileBankTemplate> templates) throws BusinessException
	{
		try
		{
			MobileBankService mobileBankService = GateSingleton.getFactory().service(MobileBankService.class);
			mobileBankService.removeTemplates(cardInfo, templates);
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
		catch (GateLogicException e)
		{
			throw new BusinessException(e);
		}
	}

	private List<RegistrationProfile> buildProfiles(Collection<MobileBankRegistration> registrations) throws BusinessException
	{
		if (registrations.isEmpty())
			return Collections.emptyList();

		List<String> multiregPhones = collectMultiRegPhones(registrations);

		List<RegistrationProfile> profiles = new ArrayList<RegistrationProfile>(registrations.size());
		for (MobileBankRegistration registration : registrations)
		{
			RegistrationProfile profile = createProfile(registration);
			String mainPhoneNumber = profile.getMainPhoneNumber();

			// ���� �������� ��������� ����������� �� ���� ����� ��������,
			// �� � ����� �������� �� ������ ����� �������� ����� �����
			profile.setShowCardNumberInSms(multiregPhones.contains(mainPhoneNumber));

			/*
			 * �������� ������ "������� ��������" ��� ������ � mainPhoneNumber
			 */
			try
			{
				MobileBankService mobileBankService = GateSingleton.getFactory().service(MobileBankService.class);
				QuickServiceStatusCode statusCode = mobileBankService.getQuickServiceStatus(mainPhoneNumber);
				profile.setQuickServiceStatusCode(statusCode);
			}
			catch (GateException e)
			{
				throw new BusinessException(e);
			}
			catch (GateLogicException e)
			{
				throw new BusinessException(e);
			}

			profiles.add(profile);
		}

		return profiles;
	}

	/**
	 * ���������� ������ �� ����������� � ��
	 * @param claimId - ID ������
	 * @return ������ ��� null, ���� �� �������
	 */
	public MobileBankRegistrationClaim getRegistrationClaim(Long claimId) throws BusinessException
	{
		if (claimId == null)
			throw new NullPointerException("�������� 'claimId' �� ����� ���� null");
		return simpleService.findById(MobileBankRegistrationClaim.class, claimId);
	}

	/**
	 * ���������� ��������� ����������� ������ 
	 * @param loginId - LoginID ������������, ������ �������� ����� ���������
	 * @return ������ ��� null, ���� �� �������
	 */
	public MobileBankRegistrationClaim getLastCompletedRegistrationClaim(final long loginId) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<MobileBankRegistrationClaim>()
			{
				public MobileBankRegistrationClaim run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(QUERY_PREFIX + ".getLastCompletedRegistrationClaim");
					query.setLong("loginId", loginId);
					query.setMaxResults(1);
					return (MobileBankRegistrationClaim)query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ��������� ����� ��� ��������� � �� ��������� ������ �� ����������� � ��
	 * @param claim - ������
	 */
	public void addOrUpdateRegistrationClaim(MobileBankRegistrationClaim claim) throws BusinessException
	{
		simpleService.addOrUpdate(claim);
	}

	/**
	 * ������� ��� ������������� (���������������) ������ �� ����������� ��� ��������� ������������
	 * @param loginId - LoginID ������������
	 */
	public void removeUncompletedRegistrationClaims(final long loginId) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(QUERY_PREFIX + ".removeUncompletedRegistrationClaims");
					query.setLong("loginId", loginId);
					query.executeUpdate();
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * �������� ������ �� ����������� � ��
	 * @param person - ������, ���������� ������
	 * @param claim - ������
	 */
	public void sendRegistrationClaim(ActivePerson person, MobileBankRegistrationClaim claim) throws BusinessException
	{
		try
		{
			Client client = person.asClient();
			MobileBankService mobileBankService = GateSingleton.getFactory().service(MobileBankService.class);
			mobileBankService.addRegistration(client, claim.getCardNumber(), claim.getPhoneNumber(), "001", claim.getTariff());
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
		catch (GateLogicException e)
		{
			throw new BusinessException(e);
		}
	}
}
