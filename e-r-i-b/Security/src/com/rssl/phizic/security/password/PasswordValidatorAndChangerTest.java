package com.rssl.phizic.security.password;

import com.rssl.phizic.auth.*;
import com.rssl.phizic.security.test.SecurityTestBase;

/**
 * Created by IntelliJ IDEA.
 * User: Evgrafov
 * Date: 02.09.2005
 * Time: 12:45:03
 */
public class PasswordValidatorAndChangerTest extends SecurityTestBase
{
    private static final String TEST_PASSWORD_NAME = "testPassword";
	private Login     testClientLogin;
	private BankLogin testBankLogin;

	String generatePassword()
	{
	    return "";
	}

    public void testCheckPassword() throws Exception
    {
        prepareClientTestData();

        NamePasswordValidator userValidator = new UserPasswordValidator();
	    Login clientLogin = (Login) userValidator.validateLoginInfo(CheckLoginTest.TEST_LOGIN_NAME, TEST_PASSWORD_NAME.toCharArray());
	    assertNotNull(clientLogin);

	    NamePasswordValidator emplValidator = new UserPasswordValidator(SecurityService.SCOPE_EMPLOYEE);
	    BankLogin bankLogin = (BankLogin) emplValidator.validateLoginInfo(CheckBankLoginTest.TEST_LOGIN_NAME, TEST_PASSWORD_NAME.toCharArray());
	    assertNotNull(bankLogin);
    }

	public void testChangePassword() throws Exception
	{
        prepareClientTestData();

        PasswordChanger changer     = new UserPasswordChanger();
		char[]          newPassword = "NewPassword".toCharArray();
		changer.changePassword(testClientLogin, newPassword);

		UserPasswordValidator userValidator = new UserPasswordValidator(SecurityService.SCOPE_CLIENT);
		userValidator.validateLoginInfo(CheckLoginTest.TEST_LOGIN_NAME, newPassword);
		changer.changePassword(testClientLogin, TEST_PASSWORD_NAME.toCharArray());

        changer.changePassword(testBankLogin, newPassword);

		UserPasswordValidator emplValidator = new UserPasswordValidator(SecurityService.SCOPE_EMPLOYEE);
		emplValidator.validateLoginInfo(CheckBankLoginTest.TEST_LOGIN_NAME, newPassword);
		changer.changePassword(testBankLogin, TEST_PASSWORD_NAME.toCharArray());
    }

	public void testNeedChangePassword() throws Exception
	{
		prepareClientTestData();
		LoginInfoProvider loginInfoProvider=new LoginInfoProviderImpl();
		assertFalse(loginInfoProvider.needChangePassword(CheckLoginTest.getTestLogin()));
	}
    /**
     * Создается Логин и Одноразовый пароль для Клиента
     * @throws Exception
     */
    private void prepareClientTestData()
            throws Exception
    {
        testClientLogin = CheckLoginTest.getTestLogin();
        testBankLogin = CheckBankLoginTest.getTestLogin();
    }

}
