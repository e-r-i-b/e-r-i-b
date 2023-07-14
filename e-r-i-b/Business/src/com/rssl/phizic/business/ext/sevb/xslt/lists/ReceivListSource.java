package com.rssl.phizic.business.ext.sevb.xslt.lists;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.resources.external.ReceiverFilter;
import com.rssl.phizic.business.resources.external.NullReceiverFilter;
import com.rssl.phizic.business.dictionaries.PaymentReceiverService;
import com.rssl.phizic.business.dictionaries.PaymentReceiverPhiz;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.xslt.lists.EntityListSource;
import com.rssl.phizic.business.xslt.lists.EntityListBuilder;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.context.PersonDataProvider;
import com.rssl.phizic.context.PersonContext;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.parsers.ParserConfigurationException;
import java.util.*;
import java.io.StringReader;
import java.io.IOException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * @author Evgrafov
 * @ created 08.08.2006
 * @ $Author: Novikov $
 * @ $Revision$
 */

public class ReceivListSource implements EntityListSource
{
	public static final String FILTER_CLASS_NAME_PARAMETR = "filter";
	private ReceiverFilter receiverFilter;

	public ReceivListSource()
	{
		this (new NullReceiverFilter());
	}

	public ReceivListSource(ReceiverFilter receiverFilter)
	{
		this.receiverFilter = receiverFilter;
	}

	public ReceivListSource(Map parametrs) throws BusinessException
	{
		try
		{
			String filterClassName = (String) parametrs.get(FILTER_CLASS_NAME_PARAMETR);
			Class filterClass = Thread.currentThread().getContextClassLoader().loadClass(filterClassName);

			receiverFilter = (ReceiverFilter) filterClass.newInstance();
		}
		catch(ClassNotFoundException e)
		{
			throw new BusinessException(e);
		}
		catch(IllegalAccessException e)
		{
			throw new BusinessException(e);
		}
		catch(InstantiationException e)
		{
			throw new BusinessException(e);
		}
	}

	public Source getSource( final Map<String,String> params ) throws BusinessException
	{

		EntityListBuilder builder = getEntityListBuilder();
		return new StreamSource(new StringReader(builder.toString()));
	}

	public Document getDocument(Map<String, String> params) throws BusinessException
	{

		EntityListBuilder builder = getEntityListBuilder();
		try
		{
			return XmlHelper.parse(builder.toString());
		}
		catch (ParserConfigurationException e)
		{
			throw new BusinessException(e);
		}
		catch (SAXException e)
		{
			throw new BusinessException(e);
		}
		catch (IOException e)
		{
			throw new BusinessException(e);
		}
	}


	private EntityListBuilder getEntityListBuilder() throws BusinessException
	{
		PaymentReceiverService paymentReceiverService = new PaymentReceiverService();

	    PersonDataProvider provider = PersonContext.getPersonDataProvider();
        ActivePerson person = provider.getPersonData().getPerson();
        Long loginId;

        //для представителя получатели того пользователя, которому он пренадлежит.
        if (person.getTrustingPersonId() != null)
        {
            PersonService personService = new PersonService();
            ActivePerson realPerson = (ActivePerson) personService.findById(person.getTrustingPersonId());
            if (realPerson == null)
            {
                throw new BusinessException("Не найден доверитель для представителя с id:" + person.getId());
            }
            loginId = realPerson.getLogin().getId();
        }
        else
        {
            loginId = person.getLogin().getId();
        }
		
        List<PaymentReceiverPhiz> list = paymentReceiverService.findListReceiver("P", loginId);

	    EntityListBuilder builder = new EntityListBuilder();
        builder.openEntityListTag();

	    for (PaymentReceiverPhiz paymentReceiver : list)
	    {
		    if (receiverFilter.accept(paymentReceiver)){
				builder.openEntityTag(paymentReceiver.getId().toString());
				builder.appentField("name", paymentReceiver.getName());
				builder.appentField("alias", paymentReceiver.getAlias());
				builder.appentField("account", paymentReceiver.getAccount());
				builder.appentField("info", paymentReceiver.getDescription());
				//данные банка
				builder.appentField("bic", paymentReceiver.getBankCode());
				builder.appentField("bankName", paymentReceiver.getBankName());
				builder.appentField("corAccount", paymentReceiver.getCorrespondentAccount());
				/*builder.appendObject(paymentReceiver.getOfficeKey().getCode(),null);*/
				builder.closeEntityTag();
		    }
        }

        builder.closeEntityListTag();

		return builder;
	}
}
