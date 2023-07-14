package com.rssl.phizic.auth;

import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.utils.test.RSSLTestCaseBase;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;

/**
 * Created by IntelliJ IDEA.
 * User: Evgrafov
 * Date: 02.09.2005
 * Time: 14:16:19
 */
public class CheckBankLoginTest  extends RSSLTestCaseBase
{
	public static final String TEST_LOGIN_NAME = "BL_" + CheckLoginTest.generateUID();

    public void testAddRemoveLogin() throws Exception
    {
        BankLogin selectedLogin;
        final BankLoginImpl login = new BankLoginImpl();
        login.setUserId( CheckLoginTest.generateUID() );
        
        HibernateExecutor executor = HibernateExecutor.getInstance();

        selectedLogin = (BankLogin)executor.execute(new HibernateAction()
        {                                                                   
           public Object run(Session session) throws Exception
            {
                session.save(login);
                Criteria criteria = session.createCriteria(BankLogin.class).add(Expression.eq("id", login.getId()));
                BankLogin selectedLogin = (BankLogin) criteria.uniqueResult();
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
	public static BankLogin getTestLogin() throws Exception
	{
		SecurityService securityService = new SecurityService();
		BankLogin login = securityService.getBankLogin(TEST_LOGIN_NAME);

		if (login != null)
		{
			securityService.markDeleted(login);
		}

		return new BankLoginGenerator(TEST_LOGIN_NAME, CheckLoginTest.PASSWORD_VALUE).generate();
	}

}
