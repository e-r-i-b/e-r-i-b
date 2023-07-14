package com.rssl.phizic.business.ermb.migration.mbk.registrator;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.phizic.business.ermb.profile.events.ErmbResourseParamsEvent;
import com.rssl.phizic.gate.csa.UserInfo;
import com.rssl.phizgate.common.services.types.OfficeImpl;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.cards.CardsUtil;
import com.rssl.phizic.business.clients.ClientDocumentImpl;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.ermb.*;
import com.rssl.phizic.business.ermb.migration.MigrationHelper;
import com.rssl.phizic.business.ermb.migration.list.entity.migrator.MigrationClient;
import com.rssl.phizic.business.ermb.migration.mbk.ERMBPhoneService;
import com.rssl.phizic.gate.ermb.MBKRegistration;
import com.rssl.phizic.business.ermb.migration.mbk.registrator.sender.ProcessResult;
import com.rssl.phizic.business.ermb.profile.ErmbUpdateListener;
import com.rssl.phizic.business.ermb.profile.events.ErmbProfileEvent;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.persons.clients.ClientImpl;
import com.rssl.phizic.business.persons.clients.FakeClient;
import com.rssl.phizic.common.types.client.ClientDocumentType;
import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.AdditionalProductData;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.AdditionalCardType;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.gate.clients.ClientProductsService;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.ermb.MbkTariff;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.mbv.MbvRegistrationInfo;
import com.rssl.phizic.gate.mobilebank.DepoMobileBankConfig;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.utils.PhoneNumberFormat;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateUtils;

import java.util.*;

/**
 * ���������� ��������� �� ����������� ������ �� � ����
 * @author Puzikov
 * @ created 09.07.14
 * @ $Author$
 * @ $Revision$
 */

public class NewRegistrationMbkMessageProcessor extends MbkMessageProcessorBase
{
	private static final ErmbTariffService tariffService = new ErmbTariffService();
	private static final ErmbClientSearchHelper clientSearchHelper = new ErmbClientSearchHelper();
	private static final ErmbProfileBusinessService profileService = new ErmbProfileBusinessService();
	private final ERMBPhoneService ermbPhoneService = new ERMBPhoneService();

	public ProcessResult process(MBKRegistration mbkMessage) throws SystemException, LogicException
	{
		if (CardsUtil.isCorporate(mbkMessage.getPaymentCardNumber()))
		{
			return createErrorResult(mbkMessage, false, "� ������ ������� ������������� ����� � �������� ���������");
		}

		excludeCorporateFromInfoCards(mbkMessage.getActiveCards());
		Client cardOwner = getClientByCard(mbkMessage.getPaymentCardNumber(), mbkMessage.getOfficeCode());
		if (cardOwner == null)
			return createErrorResult(mbkMessage, false, "�� ������ ������ �� ��������� ����� �����������");
		//6.	���� ������ ��������� �������� �� �� ��� ��������� ��. ������ � ��������� �� = �������� �� .
		mbkMessage.getOfficeCode().getFields().put("region", getPilotTb());
		List<Card> cards = getCardsViaGFL(mbkMessage);
		if (!containsAllCards(cards, mbkMessage.getPaymentCardNumber(), mbkMessage.getActiveCards()))
		{
			return createErrorResult(mbkMessage, false, "� ������ ������������ ����� �� ������� ��������� � �������");
		}

		switch (mbkMessage.getFiltrationReason())
		{
			case ERMB_PHONE:
				return processPhoneConnection(mbkMessage, cards, cardOwner);

			case ERMB_CARD:
				return processCardConnection(mbkMessage, cards);

			case PILOT_ZONE:
				return processPilotConnection(mbkMessage, cardOwner);

			default:
				throw new IllegalArgumentException("����������� FiltrationReason");
		}
	}

	private ProcessResult processPhoneConnection(MBKRegistration mbkMessage, List<Card> cards, Client cardOwner) throws SystemException, LogicException
	{
		ErmbProfileImpl profile = findByPhone(mbkMessage.getPhoneNumber());
		if (profile == null)
			return createErrorResult(mbkMessage, false, "��� ������ ������� � ���� �� ������ ������� ���� �� ��������");

		//� ���������,  ��� �������� ����� �� ������ ������� �� ���, ���, �� ������� �� ���������� �������, ��������� ��� ����� ��,
		// � � �������� �������������� ����� � ������ ������� ���� �� ���� �������� ����� �������� �� ��� ����� �������
		boolean canUpdate = isSamePerson(cardOwner, profile.getPerson());
		canUpdate = canUpdate && containsMainCard(cards, mbkMessage.getActiveCards());
		if (!canUpdate)
		{
			return createErrorResult(mbkMessage, false, "����� ������� ��� ��������������� � ������� � ����������� ������� �������");
		}
		updateErmbProfile(mbkMessage, profile);
		return createSuccessResult(mbkMessage);
	}

	private ProcessResult processCardConnection(MBKRegistration mbkMessage, List<Card> cards) throws SystemException, LogicException
	{
		ErmbProfileImpl profile = findByFioDulDrPilotTb(new MigrationClient(mbkMessage));
		//a.	���� ������� ������, ���� ������ ���������, ��� � �������� �������������� �����, � ������,
		// ������� ���� �� ���� �������� �����, �������� �� ��� ��������� �������� �����.
		if (profile != null)
		{
			//i.	���� �������� ������ �������, ���� ������ �������� ������� ���� �������
			// �� ������. � ���������� ������ ���������� ��������� ������: ������� ������� ���������� � �����
			if (containsMainCard(cards, mbkMessage.getActiveCards()))
			{
				updateErmbProfile(mbkMessage, profile);
			}
			//ii.	����� ���� �������� ������ �� �������, ���� ������ ��� ������ �������������� �����, �������� �� �� ��� ��������� ��������� �����,
			// �������� ���, ���, �� ������� �� ��� �������� ��� ����� �������, ����� ��������� CRDWI. ���
			else
			{
				List<Card> additionalInfoCards = getAdditionalInfoCards(mbkMessage.getActiveCards(), cards);
				Set<MigrationClient> additionalOwners = getOwnersViaCRDWI(additionalInfoCards, mbkMessage.getOfficeCode());
				if (CollectionUtils.isEmpty(additionalOwners))
					log.info("�� ������� ���������� �������� �� CRDWI");

				//iii.	����� ��� ������� ��������� �������������� ����� �������� �� �� ��� ��������� ��������� �����,
				// ���� ������ ��������� ������� ������� ���� �� ���, ���,�� ���������� �� ���������� ���� + �������� ��
				for (MigrationClient additionalOwner : additionalOwners)
				{
					if (isErmbConnected(additionalOwner))
					{
						//v.	����� ����, ������ ���� ��� ������ �������� ����, ���� ������ ���������� ������ ���������� ��������� ������: ������� �� ���������� � ����,
						// � ��� ���������� �������. ����� ������: ����������� ���������� ��������� �������� ����� �������� ������������ � �����. ��������� ��������� ������.
						return createErrorResult(mbkMessage, false, "���������� ���������� ��������� �������� ����� �������� ������������ � ����");
					}
				}
				//iv.	���� �� ������ �� ���� ������� ����, �� ���� ������ ���������� ������ ���������� ��������� ������:
				// ������� �� ���������� � ����, ���������� � ��ʻ. ��������� ��������� ������.
				return createErrorResult(mbkMessage, true, "� �������� ��������� � ������, �� ������� �� ���� �������� �����, �������� �� ��� ��������� �������� �����. " +
						"����� ���������� �������� ��� ��������, ������������ � ����");
			}
		}
		//b.	�����, ���� ������� �� ������, ���� ������ ���������, ��� � �������� �������������� �����,
		// � ������, ������� ���� �� ���� �������� �����, �������� �� ��� ��������� �������� �����.
		else
		{
			//i.	���� �������� ������ �������, ���� ������ ���������� ������ ���������� ��������� ������:
			// ������� �� ���������� � ����, ���������� � ��ʻ. ��������� ��������� ������.
			if (containsMainCard(cards, mbkMessage.getActiveCards()))
			{
				return createErrorResult(mbkMessage, true, "� �������� �������������� ����� � ������ ������� �������� �����, �������� �� ��� ��������� �������� �����");
			}
			//ii.	�����, ���� �������� ������ �� �������, ���� ������ ��� ������ �������������� �����, �������� �� �� ��� ��������� ��������� �����,
			// �������� ���, ���, �� ������� �� ��� �������� ��� ����� �������, ����� ��������� CRDWI. ���.
			else
			{
				List<Card> additionalInfoCards = getAdditionalInfoCards(mbkMessage.getActiveCards(), cards);
				Set<MigrationClient> persons = getOwnersViaCRDWI(additionalInfoCards, mbkMessage.getOfficeCode());
				if (CollectionUtils.isEmpty(persons))
					log.info("�� ������� ���������� �������� �� CRDWI");

				//iii.	����� ��� ������� ��������� �������������� ����� �������� �� �� ��� ��������� ��������� �����,
				// ���� ������ ��������� ������� ������� ���� �� ���, ���, �� ���������� �� ���������� ���� + �������� ��.
				for (MigrationClient person : persons)
				{
					if (isErmbConnected(person))
					{
						//v.	����� ����, ������ ���� ��� ������ �������� ����, ���� ������ ���������� ������ ���������� ��������� ������:
						// ������� �� ���������� � ����, ���������� � ��ʻ. ��������� ��������� ������.
						return createErrorResult(mbkMessage, true, "������� ��������� ��������, ������������ � ����, �� ������� ���� ����� �� �������");
					}
				}
				//iv.	���� �� ������ �� ���� ������� ����, �� ���� ������ ���������� ������ ���������� ��������� ������: ������� �� ���������� � ����, � ��� ���������� �������.
				// ����� ������: ����� ��� ����� ���� � ������  ����� ������� ����, �� ������� ���� ����� �� ��������. ��������� ��������� ������.
				return createErrorResult(mbkMessage, false, "���� ��� ����� ���� � ������ ����� ������� ����, �� ������� ���� ����� �� �������");
			}
		}
		return createSuccessResult(mbkMessage);
	}

	private ProcessResult processPilotConnection(MBKRegistration mbkMessage, Client cardClient) throws BusinessLogicException, BusinessException, GateException, GateLogicException
	{
		ErmbProfileImpl byPhone = profileService.findByPhone(mbkMessage.getPhoneNumber());
		if (byPhone != null)
			return createErrorResult(mbkMessage, false, "��� ������ ����� � �� ������ ������� ���� �� ��������");

		Client cedboClient = clientSearchHelper.findClient(
				mbkMessage.getOwner().getFirstname(),
				mbkMessage.getOwner().getSurname(),
				mbkMessage.getOwner().getPatrname(),
				mbkMessage.getOwner().getBirthdate(),
				null,
				mbkMessage.getOwner().getPassport(),
				ClientDocumentType.PASSPORT_WAY,
				getPilotTb()
		);
		Client client = mergeClientInfo(cardClient, cedboClient);

		boolean mbvAvailability = ConfigFactory.getConfig(DepoMobileBankConfig.class).isMbvAvaliability();
		if (!mbvAvailability)
			log.error("��������� ����������� ��� ���������. ��������/�������� ������� � ��� ��������� �� �����.");

		//todo BUG085485: ������ ��� ����� ��������� �� ������������, �.�. ���� ������ ������ �� crdwi, �� �� cedbo, ������ � ��� ������� ����������
		if (!client.isUDBO() && mbvAvailability)
		{
			MbvRegistrationInfo mbvInfo = MigrationHelper.getMbvInfo(client);
			if (CollectionUtils.isNotEmpty(mbvInfo.getIdentities()))
				return createErrorResult(mbkMessage, true, "�� ����-������ ��������������� � ���");
		}

		MigratorOnTheFly migrator = new MigratorOnTheFly();
		if (migrator.migrate(mbkMessage, client, mbkMessage.getOfficeCode()))
		{
			return createSuccessResult(mbkMessage);
		}
		else
		{
            return createErrorResult(mbkMessage, true, "���� ��������");
		}
	}

	/**
	 * ���������� ���������� ���������� �� ���� ��� ��������
	 * �� CRDWI �� �������� ���� ���������, � CEDBO ��� ���������� �������
	 * �������
	 *  ��� udbo - ������������ �� cedbo
	 *  ��� - ���������������� ��� ������� way
	 * @param crdwiClient ������, ���������� �� ��������� �����
	 * @param cedboClient ������ �� CEDBO (����� ���� ���� ��� ���������� �������)
	 * @return ������
	 */
	private Client mergeClientInfo(Client crdwiClient, Client cedboClient)
	{
		if (cedboClient != null)
			return cedboClient;

		ClientImpl client = new ClientImpl(crdwiClient.getFirstName(), crdwiClient.getPatrName(), crdwiClient.getSurName());
		client.setBirthDay(crdwiClient.getBirthDay());
		client.setId(crdwiClient.getId());
		client.setOffice(crdwiClient.getOffice());

		List<ClientDocument> documents = new ArrayList<ClientDocument>(crdwiClient.getDocuments().size());
		//�� CRDWI �������� ������ �����-�����, ���������������� ��� ������� WAY
		for (ClientDocument clientDocument : crdwiClient.getDocuments())
		{
			ClientDocumentImpl document = new ClientDocumentImpl();
			document.setDocumentType(ClientDocumentType.PASSPORT_WAY);
			document.setDocSeries(DocumentHelper.getPassportWayNumber(clientDocument.getDocSeries(), clientDocument.getDocNumber()));
			document.setDocIdentify(true);
			documents.add(document);
		}
		client.setDocuments(documents);

		return client;
	}

	/**
	 * �������� ����� �� ���, ��� (��� Way), �� �� ��� � ��������� ��
	 */
	private List<Card> getCardsViaGFL(MBKRegistration message) throws LogicException, SystemException
	{
		List<Card> cards = new ArrayList<Card>();

		Client fakeClient = new FakeClient(message.getOwner());

		ClientProductsService productsService = GateSingleton.getFactory().service(ClientProductsService.class);
		GroupResult<Class, List<Pair<Object, AdditionalProductData>>> gflResult = productsService.getClientProducts(fakeClient, Card.class);
		for (Pair<Object, AdditionalProductData> card : GroupResultHelper.getResult(gflResult, Card.class))
		{
			cards.add((Card) card.getFirst());
		}
		return cards;
	}

	private List<Card> getAdditionalInfoCards(List<String> infoCards, List<Card> cards)
	{
		List<Card> result = new ArrayList<Card>(cards.size());
		for (Card card : cards)
		{
			if (card.getAdditionalCardType() == AdditionalCardType.CLIENTTOOTHER && infoCards.contains(card.getNumber()))
			{
				result.add(card);
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	private Set<MigrationClient> getOwnersViaCRDWI(List<Card> additionalInfoCards, Code officeCode) throws LogicException, SystemException
	{
		Set<MigrationClient> result = new HashSet<MigrationClient>();
		for (Card card : additionalInfoCards)
		{
			Client client = getClientByCard(card.getNumber(), officeCode);
			result.add(new MigrationClient(client));
		}
		return result;
	}

	private Client getClientByCard(String cardNumber, Code officeCode) throws LogicException, SystemException
	{
		BankrollService bankrollService = GateSingleton.getFactory().service(BankrollService.class);
		//noinspection unchecked
		return GroupResultHelper.getOneResult(bankrollService.getOwnerInfoByCardNumber(new Pair<String, Office>(cardNumber, new OfficeImpl(officeCode))));
	}

	private void updateErmbProfile(MBKRegistration mbkMessage, ErmbProfileImpl profile) throws LogicException, SystemException
	{
		String mbkPhoneNumber = PhoneNumberFormat.MOBILE_INTERANTIONAL.format(mbkMessage.getPhoneNumber());
		ErmbProfileEvent profileEvent = new ErmbProfileEvent(ErmbHelper.copyProfile(profile));
		ErmbResourseParamsEvent resourceEvent = new ErmbResourseParamsEvent(profile);
		resourceEvent.setOldData(profile.getCardLinks(), profile.getAccountLinks(), profile.getLoanLinks());

		//�������� ���������� �� ������
		//� ������ ������� ������ "����� 3" - �� ������
		MbkTariff mbkTariff = mbkMessage.getTariff();
		if (mbkTariff != MbkTariff.PACKAGE3)
		{
			ErmbTariff tariff = tariffService.getTariffByCode(mbkTariff.getStringCode());
			ErmbHelper.updateErmbTariff(profile, tariff);
		}

		//�������� ������� � �������� ��������� (���� �� ����)
		boolean contains = profile.getPhoneNumbers().contains(mbkPhoneNumber);
		if (!contains)
		{
			for (ErmbClientPhone phone : profile.getPhones())
				phone.setMain(false);

			ErmbClientPhone phone = new ErmbClientPhone(mbkPhoneNumber, profile);
			phone.setMain(true);
			profile.getPhones().add(phone);
		}

		//���������� mss + sms
		profileEvent.setNewProfile(profile);
		resourceEvent.setNewData(profile.getCardLinks(), profile.getAccountLinks(), profile.getLoanLinks());
		ErmbUpdateListener updateListener = ErmbUpdateListener.getListener();
		updateListener.beforeProfileUpdate(profileEvent);
		profileService.addOrUpdate(profile);
		updateListener.afterProfileUpdate(profileEvent);
		updateListener.onResoursesUpdate(resourceEvent);

		//���������� csa
		if (!contains)
		{
			UserInfo userInfo = PersonHelper.buildUserInfo(profile.getPerson());
			CSABackRequestHelper.sendUpdatePhoneRegistrationsRq(mbkPhoneNumber, userInfo, Collections.<String>singletonList(mbkPhoneNumber), Collections.<String>emptyList());
		}

		ermbPhoneService.saveOrUpdateERMBPhones(Collections.singletonList(mbkPhoneNumber), Collections.<String>emptyList());
	}

	private void excludeCorporateFromInfoCards(List<String> infoCards)
	{
		for (Iterator<String> it = infoCards.iterator(); it.hasNext(); )
		{
			String cardNumber = it.next();
			if (CardsUtil.isCorporate(cardNumber))
				it.remove();
		}
	}

	private boolean containsAllCards(List<Card> cards, String paymentCardNumber, List<String> infoCardNumbers)
	{
		List<String> actualCardNumbers = new ArrayList<String>(cards.size());
		for (Card card : cards)
		{
			actualCardNumbers.add(card.getNumber());
		}

		return actualCardNumbers.contains(paymentCardNumber) && actualCardNumbers.containsAll(infoCardNumbers);
	}

	private boolean isSamePerson(Client gateClient, Person person)
	{
		String gateName = gateClient.getFirstName() + StringHelper.getEmptyIfNull(gateClient.getPatrName()) + gateClient.getSurName();
		String eribName = person.getFirstName() + StringHelper.getEmptyIfNull(person.getPatrName()) + person.getSurName();
		if (!StringHelper.equalsAsPersonName(gateName, eribName))
			return false;
		if (!DateUtils.isSameDay(gateClient.getBirthDay(), person.getBirthDay()))
			return false;

		return true;
	}

	/**
	 * @param clientCards - ����� ������� (�� GFL)
	 * @param mbkMessageInfoCards - �������������� ����� � ������
	 * @return true, ���� � �������� �������������� ����� � ������
	 * ������� ���� �� ���� �������� �����, �������� �� ��� ��������� �������� �����.
	 */
	private boolean containsMainCard(List<Card> clientCards, List<String> mbkMessageInfoCards)
	{
		if (CollectionUtils.isEmpty(clientCards))
			return false;

		if (CollectionUtils.isEmpty(mbkMessageInfoCards))
			return false;

		List<String> mainCardNumbers = new ArrayList<String>(clientCards.size());
		for (Card card : clientCards)
		{
			if (card.isMain() && card.getAdditionalCardType() != AdditionalCardType.CLIENTTOOTHER)
				mainCardNumbers.add(card.getNumber());
		}
		return CollectionUtils.containsAny(mbkMessageInfoCards, mainCardNumbers);
	}
}
