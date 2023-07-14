package com.rssl.phizic.operations.passwordcards;

import com.rssl.phizic.auth.passwordcards.PasswordCard;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.passwordcards.generated.ObjectFactory;
import com.rssl.phizic.operations.passwordcards.generated.Request;
import com.rssl.phizic.operations.passwordcards.generated.RequestCardDescriptor;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 * @author Evgrafov
 * @ created 05.02.2007
 * @ $Author: mescheryakova $
 * @ $Revision: 13667 $
 */

public class RequestBuilder
{
	private List<PasswordCard> cards = new ArrayList<PasswordCard>();
	private OutputStream os;

	public RequestBuilder(OutputStream os)
	{
		this.os = os;
	}

	/**
	 * @param cards  арты дл€ которых надо построить запрос
	 */
	public void addCards(PasswordCard... cards)
	{
		for (PasswordCard card : cards)
		{
			this.cards.add(card);
		}
	}

	public void build() throws BusinessException
	{
		try
		{
			ObjectFactory of = new ObjectFactory();
			Request request = of.createRequest();
			List list = request.getCards();

			for (PasswordCard card : cards)
			{
				RequestCardDescriptor descriptor = of.createRequestCardDescriptor();
				GregorianCalendar calendar = new GregorianCalendar();
				calendar.setTime(card.getIssueDate().getTime());
				descriptor.setIssueDate(calendar);
				descriptor.setKeys(card.getPasswordsCount());
				descriptor.setNumber(card.getNumber());
				list.add(descriptor);
			}

			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			JAXBContext context = JAXBContext.newInstance("com.rssl.phizic.operations.passwordcards.generated", classLoader);
			Marshaller marshaller = context.createMarshaller();

			marshaller.marshal(request, os);
		}
		catch (JAXBException e)
		{
			throw new BusinessException(e);
		}
	}
}