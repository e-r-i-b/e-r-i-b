package com.rssl.phizic.sms.banking.test;

import com.rssl.common.forms.doc.CreationSourceType;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.auth.PrincipalImpl;
import com.rssl.phizic.auth.SecurityService;
import com.rssl.phizic.auth.modes.AccessPolicy;
import com.rssl.phizic.auth.modes.AccessPolicyService;
import com.rssl.phizic.auth.modes.AccessType;
import com.rssl.phizic.auth.modes.AuthenticationConfig;
import com.rssl.phizic.business.StaticPersonData;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.source.DocumentSource;
import com.rssl.phizic.business.documents.payments.source.NewDocumentSource;
import com.rssl.phizic.business.log.ContextFillHelper;
import com.rssl.phizic.business.operations.OperationFactory;
import com.rssl.phizic.business.operations.OperationFactoryImpl;
import com.rssl.phizic.business.operations.restrictions.RestrictionProviderImpl;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.DepositLink;
import com.rssl.phizic.business.resources.external.LoanLink;
import com.rssl.phizic.business.web.SimpleSessionStore;
import com.rssl.phizic.business.web.session.SimpleSession;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.operations.payment.CreateFormPaymentOperation;
import com.rssl.phizic.operations.payment.EditDocumentOperation;
import com.rssl.phizic.sms.banking.persons.SmsPersonDataProvider;
import com.rssl.phizic.sms.banking.security.SessionManager;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.store.StoreManager;
import com.rssl.phizic.utils.test.JNDIUnitTestHelper;
import com.rssl.phizic.utils.test.RSSLTestCaseBase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import javax.naming.NamingException;

/**
 * @author eMakarov
 * @ created 02.10.2008
 * @ $Author$
 * @ $Revision$
 */
public class SmsBankingLoginTest extends RSSLTestCaseBase
{
	private static final BusinessDocumentService businessDocumentService = new BusinessDocumentService();
	private static final PersonService personService = new PersonService();
	private static final String MOBILE_PHONE_NUMBER = "+7(666)123-45-67";
	private static final boolean useRetailV51 = true; // если false, то используется V6

	private OperationFactory operationFactory = new OperationFactoryImpl(new RestrictionProviderImpl());

	/**
	 * конструктор, в котором устанавливается ритейл, в котором живут счета/карточки/вклады и куда записываются платежи
	 * @throws Exception
	 */
	public SmsBankingLoginTest() throws Exception
	{
		if (JNDIUnitTestHelper.notInitialized())
		{
			try
			{
				JNDIUnitTestHelper.init();
			}
			catch (NamingException ne)
			{
				ne.printStackTrace();
				fail("NamingException thrown on Init : " + ne.getMessage());
			}
			catch (Exception e)
			{
				e.printStackTrace();
				fail("Exception thrown on Init : " + e.getMessage());
			}
		}
/* todo BUG013795: Убрать лишние зависимости модуля SmsBanking.
		if (useRetailV51)
		{
			new RsV51GateInitializer().configure();
			try
			{
				new com.rssl.phizicgate.rsV51.jni.RetailJNIService().start();
			}
			catch(MBeanException ex)
			{
				throw new GateException(ex);
			}
		}
		else
		{
			new RSRetailV6r4GateInitializer().configure();
			try
			{
				new RetailJNIService().start();
			}
			catch(MBeanException ex)
			{
				throw new GateException(ex);
			}
		}
*/
	}

	public void testFindPersonByPhone() throws Exception
    {
	/*	ActivePerson ap = smsPersonService.findByPhone(MOBILE_PHONE_NUMBER);
	    ap.setMobilePhone("0326159487");
	    personService.update(ap);
        ap = smsPersonService.findByPhone("0326159487");
	    ap.setMobilePhone(MOBILE_PHONE_NUMBER);
	    personService.update(ap);           */
    }

	public void testLoginByPhone() throws Exception
    {
	  /*  ActivePerson person = PhoneNumberLoginAction.loginByPhone(MOBILE_PHONE_NUMBER);
	    initializeContexts(person);
	    initializeConfigs(person);

	    PersonData personData = PersonContext.getPersonDataProvider().getPersonData();

	    List<AccountLink> accounts = personData.getAccounts();
	    List<CardLink> cards = personData.getCards();
	    List<DepositLink> deposits = personData.getDeposits();
	    List<LoanLink> loans = personData.getLoans();

	    System.out.println("acconts ++++++++");
	    for (AccountLink link : accounts)
	    {
		    System.out.println(link.getAccount());
	    }

	    System.out.println("cards ++++++++");
	    for (CardLink link : cards)
	    {
		    System.out.println(link.getCard().getNumber());
	    }

	    System.out.println("deposits ++++++++");
	    for (DepositLink link : deposits)
	    {
		    System.out.println(link.getDepositInfo().getAccount().getNumber());
	    }

	    System.out.println("loans ++++++++");
	    for (LoanLink link : loans)
	    {
		    System.out.println(link.getNumber());
	    }                         */
    }

	public void testCreatePayment() throws Exception
	{
		/*ActivePerson person = PhoneNumberLoginAction.loginByPhone(MOBILE_PHONE_NUMBER);
		initializeContexts(person);
		initializeConfigs(person);

		HashMap sourceMap = new HashMap();
		sourceMap.put("toAccountSelect", "A00018");
		sourceMap.put("amount", "80.80");
		sourceMap.put("fromAccountSelect", "A00015");
		sourceMap.put("documentDate", DateHelper.toXMLDateFormat(Calendar.getInstance().getTime()));
		sourceMap.put("documentNumber", null);
		sourceMap.put("amountCurrency", "RUB");
		sourceMap.put("comissionAmount", null);
		sourceMap.put("state", null);
		sourceMap.put("admissionDate", null);
		DocumentSource source = new NewDocumentSource("InternalPayment", new MapValuesSource(sourceMap), CreationType.sms, CreationSourceType.ordinary);

		EditDocumentOperation operation = operationFactory.create(CreateFormPaymentOperation.class, source.getMetadata().getName());
		operation.initialize(source);

		Long newPaymentId = operation.save();

		BusinessDocument document = businessDocumentService.findById(newPaymentId);
		if (document == null)
		{
			throw new Exception("не удалось сохранить платеж");
		}

		businessDocumentService.remove(document);  */
	}

	public static void initializeContexts(ActivePerson person)
	{
		LogThreadContext.setIPAddress("RS-Alarm");
		LogThreadContext.setSessionId(person.getMobilePhone());
		ContextFillHelper.fillContextByLogin(person.getLogin());

		PersonData data = new StaticPersonData(person);

		PersonContext.setPersonDataProvider(new SmsPersonDataProvider());
		PersonContext.getPersonDataProvider().setPersonData(data);

		SimpleSession session = SessionManager.getSession(person.getMobilePhone());
		StoreManager.setCurrentStore(new SimpleSessionStore(session));
	}

	public static void initializeConfigs(ActivePerson person) throws Exception
	{
		AuthenticationConfig authenticationConfig = ConfigFactory.getConfig(AuthenticationConfig.class, Application.PhizIC);
		AccessPolicy policy = authenticationConfig.getPolicy(AccessType.smsBanking);

		PrincipalImpl principal = new PrincipalImpl(person.getLogin(), policy, new AccessPolicyService().getProperties(person.getLogin(), policy.getAccessType()));

		AuthModule.setAuthModule(new AuthModule(principal));
	}
}
