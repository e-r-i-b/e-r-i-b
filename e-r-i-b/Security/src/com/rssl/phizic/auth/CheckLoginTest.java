package com.rssl.phizic.auth;

import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.utils.test.RSSLTestCaseBase;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;

import java.util.GregorianCalendar;
import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * User: Evgrafov
 * Date: 02.09.2005
 * Time: 14:16:19
 */
@SuppressWarnings({"JavaDoc"})
public class CheckLoginTest  extends RSSLTestCaseBase
{
	public static final String TEST_LOGIN_NAME = "T_" + generateUID();
    public static final String PASSWORD_VALUE = "testPassword";

	public static String generateUID()
    {
	    String s = String.valueOf(System.currentTimeMillis());
	    return s.substring(s.length() - 8, s.length());
    }

    public void testAddNewLogin() throws Exception
    {
	    final LoginImpl login = new LoginImpl();
        login.setUserId ( generateUID() );

        HibernateExecutor executor = HibernateExecutor.getInstance();

	    Login selectedLogin = executor.execute(new HibernateAction<Login>()
	    {
		    public Login run(Session session) throws Exception
		    {
			    session.save(login);
			    Criteria criteria = session.createCriteria(LoginImpl.class).add(Expression.eq("id", login.getId()));
			    Login selectedLogin = (Login) criteria.uniqueResult();
			    session.delete(login);
			    return selectedLogin;
		    }
	    }
	    );

	    assertNotNull("Ошибка - полученный логин равен null!", selectedLogin);
        assertEquals("Несоответcтвие логинов!", selectedLogin.getUserId(), login.getUserId());
    }

	/**
	 * Создать тестовый Login
	 *
	 * @return
	 * @throws Exception
	 */
	public static Login getTestLogin() throws Exception
	{
		SecurityService securityService = new SecurityService();
		Login login = securityService.getClientLogin(TEST_LOGIN_NAME);

		if(login != null)
		{
			securityService.markDeleted(login);
		}

		Login clientLogin = new ClientLoginGenerator(TEST_LOGIN_NAME, PASSWORD_VALUE,null).generate();
		final Calendar blockUntil = new GregorianCalendar();

		securityService.unlock(clientLogin, true, null, blockUntil.getTime());
		return clientLogin;
	}
}
