package com.rssl.phizic.operations.passwordcards;

import com.rssl.phizic.auth.passwordcards.PasswordCard;
import com.rssl.phizic.auth.passwordcards.PasswordCardService;
import com.rssl.phizic.test.BusinessTestCaseBase;
import com.rssl.phizic.utils.test.annotation.ExcludeTest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * @author Evgrafov
 * @ created 01.02.2007
 * @ $Author: Roshka $
 * @ $Revision: 3569 $
 */

@SuppressWarnings({"JavaDoc"})
@ExcludeTest(configurations = "sbrf")
public class RequestPasswordCardsOperationTest extends BusinessTestCaseBase
{
	public void test() throws Exception
	{
		PasswordCardService passwordCardService = new PasswordCardService();

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		RequestBuilder rb = new RequestBuilder(stream);
		PasswordCard[] cards = passwordCardService.create(1, 10);
		rb.addCards(cards);
		rb.build();
		System.out.println("Запрос");
		System.out.println(stream.toString());

		byte[] request = stream.toByteArray();
		ByteArrayOutputStream pass = new ByteArrayOutputStream();
		ByteArrayOutputStream hash = new ByteArrayOutputStream();

		MockRequestProcessor processor = new MockRequestProcessor(new ByteArrayInputStream(request), pass, hash);

		processor.process();

		System.out.println("Ответ с паролями");
		System.out.println(pass.toString());

		System.out.println("Ответ с хешами");
		System.out.println(hash.toString());

		ByteArrayInputStream is = new ByteArrayInputStream(hash.toByteArray());

		AddPasswordCardHashesOperation addHashesOperation = new AddPasswordCardHashesOperation();
		addHashesOperation.initialize(is);
		addHashesOperation.add();
	}
}