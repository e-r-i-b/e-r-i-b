package com.rssl.phizic.business.ermb.sms.config;

import com.rssl.phizic.utils.xml.jaxb.JAXBUtils;
import junit.framework.TestCase;

import java.io.File;
import javax.xml.bind.JAXBException;

/**
 * @author Erkin
 * @ created 27.12.2012
 * @ $Author$
 * @ $Revision$
 */
public class ConfigTest extends TestCase
{
	private static final String CONFIG_XML = "Settings/configs/sbrf/PhizMB/sms-config.xml";

	public void testDeserialize() throws JAXBException
	{
		File xml = new File(CONFIG_XML);
		SmsConfigBean configBean = JAXBUtils.unmarshalBean(SmsConfigBean.class, xml);
		System.out.println(configBean);
	}
}
