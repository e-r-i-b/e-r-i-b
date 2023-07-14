package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.BusinessException;
import junit.framework.TestCase;

/**
 * @author Erkin
 * @ created 24.02.2011
 * @ $Author$
 * @ $Revision$
 */
public class XSLTServiceTest extends TestCase
{
	private static final String TEST_XSLT_TEMPLATE = "<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">\n" +
			"\t<xsl:output method=\"xml\" version=\"1.0\" encoding=\"windows-1251\" indent=\"yes\"/>\n" +
			"\t<xsl:template match=\"/\">\n" +
			"\t</xsl:template>\n" +
			"</xsl:stylesheet>";

	private static final String TEST_XSLT_NAME = "Test";

	private static final String TEST_XSD = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
			"<xs:schema xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" elementFormDefault=\"qualified\" attributeFormDefault=\"unqualified\">\n" +
			"</xs:schema>";

	private static final XSLTService xsltService = new XSLTService();

	private static final SimpleService simpleService = new SimpleService();

	private XSLTBean xsltBean = null;

	///////////////////////////////////////////////////////////////////////////

	protected void setUp() throws Exception
	{
		super.setUp();
	}

	public void testAdd() throws BusinessException
	{
		xsltBean = new XSLTBean();
		xsltBean.setXsltTemplate(TEST_XSLT_TEMPLATE);
		xsltBean.setXsltTemplate(TEST_XSLT_NAME);
		xsltBean.setXsd(TEST_XSD);
		xsltService.addOrUpdate(xsltBean);
	}

	public void testGetXSLTByName() throws BusinessException
	{
		assertNotNull(xsltService.getXSLTByName(TEST_XSLT_NAME));
	}

	public void testGetXSDByName() throws BusinessException
	{
		assertNotNull(xsltService.getXSDByName(TEST_XSLT_NAME));
	}

	protected void tearDown() throws Exception
	{
		super.tearDown();

		if (xsltBean != null)
			simpleService.remove(xsltBean);
	}
}
