package com.rssl.phizic.test;

import com.rssl.phizic.business.StaticPersonData;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonServiceTest;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;

/**
 * @author Omeliyanchuk
 * @ created 08.12.2006
 * @ $Author$
 * @ $Revision$
 */

public class SBRFTestClientCreator
{
	private static final ExternalResourceService externalResourceService = new ExternalResourceService();

    public static ActivePerson getTestPerson() throws Exception
    {
        ActivePerson person = PersonServiceTest.getTestPerson();

        PersonData data = new StaticPersonData(person);

        // ƒобавить счет дл€ тестового клиента
        if ( data.getAccounts().size() == 0 )
        {
            createAccountForTestClient(person);
        }

        // ƒобавить карту дл€ тестового клиента
        if ( data.getCards().size() == 0 )
        {
            createCardForTestPerson(person);
        }

        data = new StaticPersonData(person);

        PersonContext.setPersonDataProvider(new MockPersonDataProvider(data));

        return person;
    }
    private static void createAccountForTestClient(final ActivePerson person) throws Exception
    {
/*
        TODO переделать(изменилс€ интерфейс)
        HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
        {
            public Void run(Session session) throws Exception
            {
                AccountLink accountLink = new AccountLink();
                accountLink.setExternalId("40820840500000000000");
                accountLink.setPaymentAbility(true);
	            accountLink.setLogin(person.getLogin());
                externalResourceService.addLink(accountLink);
	            return null;
            }
        });
*/
    }

    private static void createCardForTestPerson(final ActivePerson person) throws Exception
    {
/*
        TODO переделать(изменилс€ интерфейс)
        HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
        {
            public Void run(Session session) throws Exception
            {
                CardLink cardLink = new CardLink();
                cardLink.setExternalId("8757557576567553");
//                cardLink.setPaymentAbility(true);
	            cardLink.setLogin(person.getLogin());
                externalResourceService.addLink( cardLink);
	            return null;
            }
        });
*/
    }
}
