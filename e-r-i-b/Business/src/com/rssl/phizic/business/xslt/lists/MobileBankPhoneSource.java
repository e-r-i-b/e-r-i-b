package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ermb.MobileBankManager;
import com.rssl.phizic.utils.PhoneNumberFormat;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.Collection;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

/**
 * список телефонов из мобильного банка по информационным картам
 * @author lukina
 * @ created 02.10.2012
 * @ $Author$
 * @ $Revision$
 */

public class MobileBankPhoneSource    implements EntityListSource
{
	private static final String FORMAT_PHONE_PARAM = "formatPhone";
	private boolean formatPhone = false;

	public MobileBankPhoneSource()
	{
	}

	public MobileBankPhoneSource(Map parameters)
	{
		formatPhone = Boolean.parseBoolean((String) parameters.get(FORMAT_PHONE_PARAM));
	}

	public Source getSource(Map<String, String> params) throws BusinessException, BusinessLogicException
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
	private EntityListBuilder getEntityListBuilder(Map<String, String> params) throws BusinessException
	{
		Collection<String> phones = MobileBankManager.getInfoPhones();
		EntityListBuilder builder = new EntityListBuilder();
		builder.openEntityListTag();
		for (String phone: phones)
		{
			builder.openEntityTag(Integer.toString(phone.hashCode()));
			builder.appentField("value", formatPhone ? PhoneNumberFormat.SIMPLE_NUMBER.translateAsHidden(phone) : phone);
			builder.closeEntityTag();
		}
		builder.closeEntityListTag();
		return builder;
	}
}
