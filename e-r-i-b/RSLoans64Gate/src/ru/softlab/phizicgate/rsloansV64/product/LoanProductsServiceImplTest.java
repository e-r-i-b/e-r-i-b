package ru.softlab.phizicgate.rsloansV64.product;

import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import ru.softlab.phizicgate.rsloansV64.junit.RSLoans64GateTestCaseBase;

import java.util.List;
import java.util.ArrayList;

/**
 * @author Omeliyanchuk
 * @ created 06.12.2007
 * @ $Author$
 * @ $Revision$
 */

public class LoanProductsServiceImplTest extends RSLoans64GateTestCaseBase
{
	public void testgetLoansInfo() throws Exception
	{
		LoanProductsServiceImpl productService = new LoanProductsServiceImpl(gateFactory);
		Document loandInfoDocument = productService.getLoansInfo();
		assertNotNull(loandInfoDocument);
		String result = XmlHelper.convertDomToText(loandInfoDocument);
		System.out.println(result);
		assertTrue(result.length()!=0);
	}

	public void testgetLoanProduct() throws Exception
	{
		LoanProductsServiceImpl productService = new LoanProductsServiceImpl(gateFactory);
		List<String> ids = new ArrayList<String>();
		ids.add("1027");
		ids.add("1218");
		Document loandInfoDocument = productService.getLoanProduct(ids);
		assertNotNull(loandInfoDocument);
		String result = XmlHelper.convertDomToText(loandInfoDocument);
		System.out.println(result);
		assertTrue(result.length()!=0);
	}
}

