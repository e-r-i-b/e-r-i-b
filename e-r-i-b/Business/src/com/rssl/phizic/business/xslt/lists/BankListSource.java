package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.gate.dictionaries.Bank;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.hibernate.Query;
import org.hibernate.Session;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.w3c.dom.Document;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.parsers.ParserConfigurationException;
import java.io.StringReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.Map;
import java.util.List;

/**
 * @author Kosyakov
 * @ created 03.11.2005
 * @ $Author: miklyaev $
 * @ $Revision: 67903 $
 */

public class BankListSource implements EntityListSource
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
	    List<Bank> banks;

	    try
	    {
	        banks = HibernateExecutor.getInstance().execute(new HibernateAction<List<Bank>>()
	        {
	            public List<Bank> run(Session session) throws Exception
	            {
		            Query query = session.getNamedQuery("com.rssl.phizic.business.getBankByBIC");
		            query.setParameter("BIC",params.get("BIC"));
		            query.setParameter("CUR_DATE", Calendar.getInstance());
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

	    for (Bank bank : banks)
	    {
		    builder.openEntityTag(params.get("BIC"));
		    builder.appentField("name", bank.getName());
		    builder.appentField("place", bank.getPlace());
		    builder.appentField("account", bank.getAccount());
		    builder.closeEntityTag();

	    }
	    builder.closeEntityListTag();

        return builder;
    }

}
