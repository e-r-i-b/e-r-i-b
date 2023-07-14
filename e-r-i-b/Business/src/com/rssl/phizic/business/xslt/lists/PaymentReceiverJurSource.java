package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.dictionaries.PaymentReceiverJur;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.context.PersonDataProvider;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.utils.xml.XmlHelper;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.parsers.ParserConfigurationException;
import java.util.Map;
import java.util.List;
import java.io.StringReader;
import java.io.IOException;

import org.hibernate.Session;
import org.hibernate.Query;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.w3c.dom.Document;

/**
 * Created by IntelliJ IDEA.
 * User: Omeliyanchuk
 * Date: 31.08.2006
 * Time: 9:41:37
 * To change this template use File | Settings | File Templates.
 */
public class PaymentReceiverJurSource implements EntityListSource
{
	public Source getSource( final Map<String,String> params ) throws BusinessException
	{
		EntityListBuilder builder = getEntityListBuilder(params);
		return new StreamSource(new StringReader(builder.toString()));
	}

	public Document getDocument(Map<String, String> params) throws BusinessException
	{
		EntityListBuilder builder = getEntityListBuilder(params);
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

	private EntityListBuilder getEntityListBuilder( final Map<String,String> params ) throws BusinessException
	{
		List<PaymentReceiverJur> receivers;

	    try
	    {
	        receivers = HibernateExecutor.getInstance().execute(new HibernateAction<List<PaymentReceiverJur>>()
	        {
	            public List<PaymentReceiverJur> run(Session session) throws Exception
	            {
					PersonDataProvider provider = PersonContext.getPersonDataProvider();
        			ActivePerson person = provider.getPersonData().getPerson();
        			Long loginId = person.getLogin().getId();
					Query query = session.getNamedQuery("com.rssl.phizic.business.dictionaries.getPaymentReceiverJurByBankInfoAndAccount");
		            query.setParameter("BIC",params.get("BIC"));
					query.setParameter("correspondentAccount",params.get("correspondentAccount"));
					query.setParameter("account",params.get("account"));
					query.setParameter("loginId",loginId);
					//noinspection unchecked
		            return query.list();
	            }
	        });
	    }
	    catch (Exception e)
	    {
	       throw new BusinessException(e);
	    }

		EntityListBuilder builder = new EntityListBuilder();

		builder.openEntityListTag();

		for (PaymentReceiverJur receiver : receivers)
		{
			builder.openEntityTag(receiver.getAccount());
			builder.appentField("account", receiver.getAccount());
			builder.appentField("BIC", receiver.getBankCode());
			builder.appentField("correspondentAccount", receiver.getCorrespondentAccount());
			builder.appentField("INN", receiver.getINN());
			builder.closeEntityTag();
		}
		builder.closeEntityListTag();

		return builder;
	}
}
