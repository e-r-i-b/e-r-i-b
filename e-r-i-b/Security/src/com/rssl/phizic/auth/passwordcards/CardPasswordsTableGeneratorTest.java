package com.rssl.phizic.auth.passwordcards;

import com.rssl.phizic.security.test.SecurityTestBase;
import com.rssl.phizic.utils.test.annotation.ExcludeTest;

/**
 * Created by IntelliJ IDEA.
 * User: Kidyaev
 * Date: 12.09.2005
 * Time: 20:07:42
 * Класс для проверки создания таблицы паролей.
 * Тест реализаций интерфейса PasswordsTableGenerator
 */
@ExcludeTest(configurations = "sbrf")
public class CardPasswordsTableGeneratorTest extends SecurityTestBase
{
	public void testGenerateCardPasswordsTable() throws Exception
	{
	    PasswordCardGenerator passwordGenerator = new PasswordCardGeneratorImpl();
	    PasswordCard          card = passwordGenerator.generate();

		PasswordCardService passwordCardService = new PasswordCardService();
		passwordCardService.remove(card);
	}

}
