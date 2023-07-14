package com.rssl.phizic.auth;

import com.rssl.phizic.auth.pin.PINEnvelope;
import com.rssl.phizic.auth.pin.PINEnvelopeCreatorTest;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.security.password.UserPassword;
import com.rssl.phizic.security.test.SecurityTestBase;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.LockMode;

/**
 * Created by IntelliJ IDEA.
 * User: Evgrafov
 * Date: 14.09.2005
 * Time: 18:10:16
 */
public class SecurityServiceTest extends SecurityTestBase
{
    private CommonLogin login;
	private SecurityService securityService = new SecurityService();

	public void testCreateLogin() throws Exception
    {
	    String userId = new UserIdValueGeneratorByTime().newUserId(8);
	    login = new ClientLoginGenerator(userId, "aaaa", null).generate();
    }

    protected void tearDown() throws Exception
    {
        HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
        {
            public Void run(Session session) throws Exception
            {
                if(login == null )
                    return null;

	            session.lock(login, LockMode.NONE);
	            Query query = session.getNamedQuery("com.rssl.phizic.security.password.getPasswordByLogin");
				                        query.setParameter( "loginId", login.getId() );

				UserPassword userPassword = (UserPassword) query.uniqueResult();
				session.delete(userPassword);
                securityService.markDeleted(login);
                return null;
            }
        });
        super.tearDown();
    }

	public void testLinkEnvelope() throws Exception
	{
		Login testLogin = CheckLoginTest.getTestLogin();
		PINEnvelope testEnvelope = PINEnvelopeCreatorTest.createUploadedPin();
		
		securityService.linkPinEvenlope(testLogin, testEnvelope);

		assertEquals(testLogin.getUserId(), testEnvelope.getUserId());
		assertEquals(testLogin.getPinEnvelopeId(), testEnvelope.getId());
		assertTrue(testEnvelope.getState().equals(PINEnvelope.STATE_PROCESSED));
	}
}
