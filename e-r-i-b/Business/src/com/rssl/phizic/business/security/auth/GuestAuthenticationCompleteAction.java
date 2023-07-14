package com.rssl.phizic.business.security.auth;

import com.rssl.auth.csa.back.servises.GuestClaimType;
import com.rssl.phizic.auth.GuestLogin;
import com.rssl.phizic.auth.modes.AthenticationCompleteAction;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.GuestPersonData;
import com.rssl.phizic.business.clients.ClientDocumentImpl;
import com.rssl.phizic.business.creditcards.CreditCardsUtil;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.loanCardOffer.LoanCardOfferShortCut;
import com.rssl.phizic.business.persons.GuestPerson;
import com.rssl.phizic.business.persons.PersonDocumentImpl;
import com.rssl.phizic.business.resources.external.guest.GuestType;
import com.rssl.phizic.common.types.SegmentCodeType;
import com.rssl.phizic.common.types.client.ClientDocumentType;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.*;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.mobilebank.MobileBankService;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.GroupResultHelper;
import org.apache.commons.collections.CollectionUtils;

import java.text.ParseException;
import java.util.*;

/**
 * @author niculichev
 * @ created 10.12.14
 * @ $Author$
 * @ $Revision$
 */
public class GuestAuthenticationCompleteAction implements AthenticationCompleteAction
{
	private static final String INDEX_GUEST_URL = "/guest/index.do";
	private static final DepartmentService departmentService = new DepartmentService();
	private static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	public void execute(AuthenticationContext context) throws SecurityException, SecurityLogicException
	{
		try
		{
			GuestType       guestType   = GuestType.UNREGISTERED;
			GuestLogin      guestLogin  = (GuestLogin) context.getLogin();
			GuestPerson     guestPerson = (GuestPerson) context.getPerson();

			String documentNumber = null;
			String documentSeries = null;
			boolean  hasPhoneInMB = false;
			boolean  needUpdatePersonData = false;

			if (context.isMB())
			{
				Set<String> cardNumbers = findCards(guestLogin.getAuthPhone());

				if (CollectionUtils.isNotEmpty(cardNumbers))
				{
					List<Card> cards = getCardsByNumbers(cardNumbers);
					if (CollectionUtils.isNotEmpty(cards))
					{
						needUpdatePersonData = true;
						Client client = createClient(context);
						if (equalClients(client, cards))
						{
							Card card = cards.get(0);
							guestLogin.setLastLogonCardNumber(card.getNumber());
							fillPerson(guestPerson, card);
							ClientDocument document = client.getDocuments().get(0);
							hasPhoneInMB = true;
							documentNumber = document.getDocNumber();
							documentSeries = document.getDocSeries();

							guestType = GuestType.REGISTERED;
						}
						else
						{
							guestPerson.setSurName(client.getSurName());
							guestPerson.setFirstName(client.getFirstName());
							guestPerson.setPatrName(client.getPatrName());
							guestPerson.setBirthDay(client.getBirthDay());
							Set<PersonDocument> documents = guestPerson.getPersonDocuments();
							ClientDocument clientDocument = client.getDocuments().get(0);
							if (clientDocument.getDocumentType() == null)
								clientDocument.setDocumentType(ClientDocumentType.REGULAR_PASSPORT_RF);
							PersonDocumentImpl document = new PersonDocumentImpl(clientDocument);
							document.setDocumentMain(true);
							documents.add(document);
							documentNumber = clientDocument.getDocNumber();
							documentSeries = clientDocument.getDocSeries();
						}
					}
				}
			}

			GuestPersonData personData  = new GuestPersonData(guestLogin, guestPerson, false, context.getCsaProfileId(), context.getGuestProfileId(), context.getUserAlias());
			if (needUpdatePersonData)
			{
				personData.setDocumentNumber(documentNumber);
				personData.setDocumentSeries(documentSeries);
				personData.setHasPhoneInMB(hasPhoneInMB);
			}
			log.info("јутентифицировалс€ " + (guestType == GuestType.REGISTERED ? "зарегистрированный " : "незарегистрированный ")
					+ "гость по телефону " + guestLogin.getAuthPhone());

			personData.setGuestType(guestType);
			PersonContext.getPersonDataProvider().setPersonData(personData);

			GuestClaimType claimType = context.getGuestClaimType();
			context.setStartJobPagePath(claimType == null  ? INDEX_GUEST_URL : createJobPagePath(guestType, claimType));
			context.clearAuthenticationParameters();
		}
		catch (Exception e)
		{
			throw new SecurityException(e);
		}
	}

	private String createJobPagePath(GuestType guestType, GuestClaimType claimType)
	{
		String jobPagePath = claimType.getActionPath();

		if (claimType == GuestClaimType.credit_card)
		{
			if (guestType == GuestType.REGISTERED)
			{
				LoanCardOfferShortCut shortCut = CreditCardsUtil.getLoanOfferClaimType();

				// ≈сть предодобренное предложение отправл€ем заполн€ть его. ≈сли нету предложени€, заполнен€ет по обычным услови€м
				if (shortCut != null && shortCut.getId() != null)
				{
					jobPagePath += "&offerId=" + shortCut.getId() + "&offerType=" + shortCut.getType();
				}
			}
		}

		return jobPagePath;
	}

	private void fillPerson(Person guestPerson, Card card) throws BusinessException
	{
		Client client = card.getCardClient();
		guestPerson.setSurName(client.getSurName());
		guestPerson.setFirstName(client.getFirstName());
		guestPerson.setPatrName(client.getPatrName());
		guestPerson.setBirthDay(client.getBirthDay());
		Set<PersonDocument> documents = guestPerson.getPersonDocuments();
		Office clientOffice = card.getOffice();
		if (clientOffice != null && clientOffice.getCode() != null)
		{
			Map<String, String> fields = clientOffice.getCode().getFields();
			Department department = departmentService.getDepartment(fields.get("region"), fields.get("branch"), fields.get("office"));

			if (department != null)
			{
				guestPerson.setDepartmentId(department.getId());
			}
		}
		documents.clear();
		// после запроса в crdwi получеам только один документ
		ClientDocument clientDocument = client.getDocuments().get(0);
		if (clientDocument.getDocumentType() == null)
			clientDocument.setDocumentType(ClientDocumentType.PASSPORT_WAY);
		documents.add(new PersonDocumentImpl(clientDocument));
	}

	private List<Card> getCardsByNumbers(Set<String> cardNumbers)
	{
		String[] cardIds = new String[cardNumbers.size()];
		int i = 0;
		for (String number : cardNumbers)
			cardIds[i++] = number + "^null^" + "40400001" + "^null";
		BankrollService bankrollService = GateSingleton.getFactory().service(BankrollService.class);
		GroupResult<String, Card> cardGroupResult = bankrollService.getCard(cardIds);
		return GroupResultHelper.getResults(cardGroupResult);
	}

	private Set<String> findCards(String phoneNumber) throws GateException, GateLogicException
	{
		MobileBankService mobileBankService = GateSingleton.getFactory().service(MobileBankService.class);
		return mobileBankService.getCardsByPhone(phoneNumber);
	}

	private boolean equalClients(Client client, List<Card> cards)
	{
		for (Card card : cards)
			if (!ClientHelper.isSameClient(client, card.getCardClient()))
				return false;
		return true;
	}

	private Client createClient(final AuthenticationContext context)
	{
		final Map<String, String> map = context.getAuthenticationParameters();

		return new Client()
		{
			public String getId()
			{
				return null;
			}

			public Long getInternalId()
			{
				return null;
			}

			public Long getInternalOwnerId()
			{
				return null;
			}

			public String getDisplayId()
			{
				return null;
			}

			public String getShortName()
			{
				return null;
			}

			public String getFullName()
			{
				return null;
			}

			public String getFirstName()
			{
				return map.get("firstName");
			}

			public String getSurName()
			{
				return map.get("surName");
			}

			public String getPatrName()
			{
				return map.get("patrName");
			}

			public Calendar getBirthDay()
			{
				try
				{
					return map.get("birthday") == null ? null : DateHelper.parseCalendar(map.get("birthday"));
				}
				catch (ParseException e)
				{
					return null;
				}
			}

			public String getBirthPlace()
			{
				return null;
			}

			public String getEmail()
			{
				return null;
			}

			public String getHomePhone()
			{
				return null;
			}

			public String getJobPhone()
			{
				return null;
			}

			public String getMobilePhone()
			{
				return null;
			}

			public String getMobileOperator()
			{
				return null;
			}

			public String getSex()
			{
				return null;
			}

			public String getINN()
			{
				return null;
			}

			public boolean isResident()
			{
				return false;
			}

			public Address getLegalAddress()
			{
				return null;
			}

			public Address getRealAddress()
			{
				return null;
			}

			public List<? extends ClientDocument> getDocuments()
			{
				String document = map.get("document");
				return Collections.singletonList(new ClientDocumentImpl(document.substring(0, 4), document.substring(4), null));
			}

			public String getCitizenship()
			{
				return null;
			}

			public Office getOffice()
			{
				return null;
			}

			public String getAgreementNumber()
			{
				return null;
			}

			public Calendar getInsertionDate()
			{
				return null;
			}

			public Calendar getCancellationDate()
			{
				return null;
			}

			public ClientState getState()
			{
				return null;
			}

			public boolean isUDBO()
			{
				return false;
			}

			public SegmentCodeType getSegmentCodeType()
			{
				return null;
			}

			public String getTarifPlanCodeType()
			{
				return null;
			}

			public Calendar getTarifPlanConnectionDate()
			{
				return null;
			}

			public String getManagerId()
			{
				return null;
			}

			public String getManagerTB()
			{
				return null;
			}

			public String getManagerOSB()
			{
				return null;
			}

			public String getManagerVSP()
			{
				return null;
			}

			public String getGender()
			{
				return null;
			}
		};
	}
}
