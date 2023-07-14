package com.rssl.phizic.operations.passwordcards;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.operations.passwordcards.generated.*;
import com.rssl.phizic.security.config.SecurityConfig;
import com.rssl.phizic.security.config.SecurityFactory;
import com.rssl.phizic.security.crypto.CryptoService;
import com.rssl.phizic.security.password.PasswordValueGenerator;
import com.rssl.phizic.security.password.SecurePasswordValueGenerator;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 * @author Evgrafov
 * @ created 05.02.2007
 * @ $Author: bogdanov $
 * @ $Revision: 57189 $
 */

public class MockRequestProcessor
{
	private InputStream is;
	private OutputStream passwordOs;
	private OutputStream hashOs;

	private JAXBContext  context;

	private PasswordValueGenerator passwordValueGenerator;
	private char[]                 allowedChars;
	private int                    passwordLength;
	private CryptoService cryptoService;

	public MockRequestProcessor(InputStream is, OutputStream passwordOs, OutputStream hashOs)
	{
		this.hashOs = hashOs;
		this.is = is;
		this.passwordOs = passwordOs;
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		try
		{
			this.context = JAXBContext.newInstance("com.rssl.phizic.operations.passwordcards.generated", classLoader);
		}
		catch (JAXBException e)
		{
			throw new RuntimeException(e);
		}

		SecurityConfig securityConfig = ConfigFactory.getConfig(SecurityConfig.class);

		this.passwordValueGenerator = new SecurePasswordValueGenerator();
		this.passwordLength = securityConfig.getCardPasswordLength();
		this.allowedChars = securityConfig.getCardPasswordAllowedChars().toCharArray();

		cryptoService = SecurityFactory.cryptoService();
	}

	public void process() throws BusinessException
	{
		try
		{
			Request request = (Request) context.createUnmarshaller().unmarshal(is);
			ObjectFactory of = new ObjectFactory();

			Response passwordResponse = of.createResponse();
			List passwordCards = passwordResponse.getCards();
			Response hashResponse = of.createResponse();
			List hashCards = hashResponse.getCards();

			List<RequestCardDescriptor> list = request.getCards();

			for(int i = 0; i < list.size(); i++)
			{
				RequestCardDescriptor requestCard = list.get(i);

				ResponseCardDescriptor hashCard = createResponseCard(of, requestCard);
				ResponseCardDescriptor passwordCard = createResponseCard(of, requestCard);

				for(int j = 0; j < requestCard.getKeys(); j++)
				{
					String passwordString = String.valueOf(passwordValueGenerator.newPassword(passwordLength, allowedChars));
					String hashString = cryptoService.hash(passwordString);
					int number = j + 1;

					PasswordDescriptor hash = of.createPasswordDescriptor();
					hash.setHash(hashString);
					hash.setNumber(number);
					hashCard.getPasswords().add(hash);

					PasswordDescriptor password = of.createPasswordDescriptor();
					password.setHash(passwordString);
					password.setNumber(number);
					passwordCard.getPasswords().add(password);
				}

				passwordCards.add(passwordCard);
				hashCards.add(hashCard);
			}

			Marshaller marshaller = context.createMarshaller();

			marshaller.marshal(passwordResponse, passwordOs);
			marshaller.marshal(hashResponse, hashOs);
		}
		catch (JAXBException e)
		{
			throw new BusinessException(e);
		}
	}

	private ResponseCardDescriptor createResponseCard(ObjectFactory of, RequestCardDescriptor requestCard)
			throws JAXBException
	{
		ResponseCardDescriptor temp = of.createResponseCardDescriptor();
		temp.setIssueDate(requestCard.getIssueDate());
		temp.setNumber(requestCard.getNumber());
		temp.setKeys(requestCard.getKeys());
		return temp;
	}
}