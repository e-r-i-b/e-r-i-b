package com.rssl.phizic.operations.security;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.exceptions.BackException;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.auth.csa.wsclient.exceptions.MobileBankRegistrationNotFoundException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.cards.CardsUtil;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;

import java.util.List;

/**
 * Операция получения временного логина и пароля
 * @author Jatsky
 * @ created 16.12.13
 * @ $Author$
 * @ $Revision$
 */

public class GetRegistrationOperation extends OperationBase
{
	private String login;
	private String password;
	private boolean sended;
	private static final ExternalResourceService externalService = new ExternalResourceService();
	private static final PersonService personService = new PersonService();

	/**
	 * пустой конструктор
	 */
	public GetRegistrationOperation()
	{}

	public GetRegistrationOperation(String cardNum) throws BusinessLogicException, BusinessException
	{
		sendRequest(cardNum, false);
	}

	/**
	 * Инициализация операции
	 * @param personId идентификатор клиента
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void initialization(Long personId) throws BusinessException, BusinessLogicException
	{
		Person person = personService.findById(personId);
		List<CardLink> cardLinks = externalService.getLinks(person.getLogin(), CardLink.class);
		String cardNumber = cardLinks.isEmpty() ? "" : CardsUtil.getLongTermCard(cardLinks).getNumber();
		sendRequest(cardNumber, true);
	}

	private void sendRequest(String cardNum, boolean sendSMS) throws BusinessLogicException, BusinessException
	{
		try
		{
			Document responce = CSABackRequestHelper.sendUserRegistrationDisposableRq(cardNum, sendSMS ? "true" : "");

			this.login = XmlHelper.getSimpleElementValue(responce.getDocumentElement(), "login");
			this.password = XmlHelper.getSimpleElementValue(responce.getDocumentElement(), "password");
			this.sended = true;
		}
		catch (MobileBankRegistrationNotFoundException e)
		{
			this.sended = false;
		}
		catch (BackLogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (BackException e)
		{
			throw new BusinessException(e);
		}
	}

	public String getLogin()
	{
		return login;
	}

	public String getPassword()
	{
		return password;
	}

	public boolean isSended()
	{
		return sended;
	}
}
