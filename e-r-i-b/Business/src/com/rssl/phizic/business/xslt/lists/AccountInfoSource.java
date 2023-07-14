package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.utils.MockHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

/**
 * @author Omeliyanchuk
 * @ created 21.08.2006
 * @ $Author$
 * @ $Revision$
 */
public class AccountInfoSource implements EntityListSource
{
	public Source getSource( final Map<String,String> params ) throws BusinessException, BusinessLogicException
	{
		EntityListBuilder builder = getEntityListBuilder(params);
		return new StreamSource(new StringReader(builder.toString()));
	}

	public Document getDocument(Map<String, String> params) throws BusinessException, BusinessLogicException
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

	private EntityListBuilder getEntityListBuilder(Map<String, String> params) throws BusinessException, BusinessLogicException
	{
		String accountNumber = params.get("AccountNumber");

		PersonData  personData  = PersonContext.getPersonDataProvider().getPersonData();
		AccountLink accountLink = personData.findAccount(accountNumber);

		Account account = accountLink.getAccount();
		Office  office  = accountLink.getOffice();

		if(!MockHelper.isMockObject(account) && !MockHelper.isMockObject(office))
		{
			Code code = office.getCode();

			EntityListBuilder builder = new EntityListBuilder();

			builder.openEntityListTag();

			builder.openEntityTag(accountNumber);
			builder.appentField("currency", account.getCurrency().getCode());
			builder.appendObject(code,null);
			builder.closeEntityTag();

			builder.closeEntityListTag();

			return builder;
		}
		else
		{
			throw new BusinessException("Не возможно получить информацию о счете: " + accountNumber);
		}
	}
}
