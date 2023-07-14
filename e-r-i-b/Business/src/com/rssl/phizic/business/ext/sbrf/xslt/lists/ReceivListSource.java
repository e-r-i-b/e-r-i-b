package com.rssl.phizic.business.ext.sbrf.xslt.lists;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.xslt.lists.EntityListSource;
import com.rssl.phizic.business.xslt.lists.EntityListBuilder;
import com.rssl.phizic.business.ext.sbrf.receivers.PaymentReceiverPhizSBRF;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.dictionaries.PaymentReceiverService;
import com.rssl.phizic.context.PersonDataProvider;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.parsers.ParserConfigurationException;
import java.io.StringReader;
import java.io.IOException;
import java.util.*;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.hibernate.Session;
import org.hibernate.Query;

/**
 * @author Evgrafov
 * @ created 08.08.2006
 * @ $Author: Novikov $
 * @ $Revision$
 */

public class ReceivListSource implements EntityListSource
{

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

        List<PaymentReceiverPhizSBRF> list = paymentReceiverService.findListReceiver("S", loginId);

	    EntityListBuilder builder = new EntityListBuilder();
        builder.openEntityListTag();

	    for (PaymentReceiverPhizSBRF paymentReceiver : list)
	    {
	        builder.openEntityTag(paymentReceiver.getId().toString());
            builder.appentField("name", paymentReceiver.getName());
            builder.appentField("alias", paymentReceiver.getAlias());
            builder.appentField("account", paymentReceiver.getAccount());
            builder.appentField("info", paymentReceiver.getDescription());
		    //данные банка
		    builder.appentField("bic", paymentReceiver.getBankCode());
		    builder.appentField("bankName", paymentReceiver.getBankName());
		    builder.appentField("corAccount", paymentReceiver.getCorrespondentAccount());
		    builder.appendObject(paymentReceiver.getOfficeKey().getCode(),null);
            builder.closeEntityTag();
        }

        builder.closeEntityListTag();

		return builder;
	}

}
