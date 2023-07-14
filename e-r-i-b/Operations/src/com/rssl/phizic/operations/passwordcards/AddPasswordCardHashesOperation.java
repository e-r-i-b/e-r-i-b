package com.rssl.phizic.operations.passwordcards;

import com.rssl.phizic.auth.passwordcards.CardPassword;
import com.rssl.phizic.auth.passwordcards.PasswordCard;
import com.rssl.phizic.auth.passwordcards.PasswordCardService;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.passwordcards.generated.PasswordDescriptor;
import com.rssl.phizic.operations.passwordcards.generated.Response;
import com.rssl.phizic.operations.passwordcards.generated.ResponseCardDescriptor;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.security.SecurityLogicException;
import org.hibernate.Session;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

/**
 * @author Evgrafov
 * @ created 05.02.2007
 * @ $Author: krenev $
 * @ $Revision: 12319 $
 */
public class AddPasswordCardHashesOperation extends OperationBase
{
    private static final PasswordCardService service = new PasswordCardService();
	private List<ResponseCardDescriptor> cards;

	/**
	 * инициализация
	 */
    public void initialize(InputStream is) throws BusinessException
	{
		try
		{
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			JAXBContext context = JAXBContext.newInstance("com.rssl.phizic.operations.passwordcards.generated", classLoader);
			Response response = (Response) context.createUnmarshaller().unmarshal(is);

			cards = response.getCards();
		}
		catch (JAXBException e)
		{
			throw new BusinessException(e);
		}
	}

    /**
     * Добавить в карту ключей пароли
     * @throws BusinessLogicException неверный статус
     */
    @Transactional
    public void add() throws BusinessLogicException, BusinessException
    {
	    try
	    {
		    for (ResponseCardDescriptor cardDescriptor : cards)
		    {
			    PasswordCard card = service.findByNumber(cardDescriptor.getNumber());

			    if (card == null)
				    throw new BusinessException("Карта ключей не найдена №" + cardDescriptor.getNumber());

			    List<CardPassword> passwords = createPasswords(cardDescriptor);
			    service.addPasswordsAndMarkAsNew(card, passwords);
		    }
	    }
	    catch(SecurityLogicException e)
	    {
		    throw new BusinessLogicException(e);
	    }
	    catch (Exception e)
	    {
	       throw new BusinessException(e);
	    }
    }

	private List<CardPassword> createPasswords(ResponseCardDescriptor cardDescriptor) throws SecurityDbException, BusinessLogicException, BusinessException
	{

		// JAXP
		//noinspection unchecked
		List<PasswordDescriptor> list = cardDescriptor.getPasswords();
		List<CardPassword> result = new ArrayList<CardPassword>();

		for (PasswordDescriptor passwordDescriptor : list)
		{
			CardPassword cardPassword = new CardPassword();

			cardPassword.setNumber(passwordDescriptor.getNumber());
			cardPassword.setValue(passwordDescriptor.getHash().toCharArray());

			result.add(cardPassword);
		}

		return result;
	}
}