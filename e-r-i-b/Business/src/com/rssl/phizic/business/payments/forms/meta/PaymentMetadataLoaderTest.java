package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.payments.forms.PaymentFormService;
import com.rssl.phizic.business.payments.forms.PaymentFormServiceTest;
import com.rssl.phizic.test.BusinessTestCaseBase;
import com.rssl.phizic.utils.test.annotation.IncludeTest;

import java.util.List;

/**
 * @author Evgrafov
 * @ created 10.11.2006
 * @ $Author: khudyakov $
 * @ $Revision: 49377 $
 */
@SuppressWarnings({"JavaDoc"})
public class PaymentMetadataLoaderTest extends BusinessTestCaseBase
{
	private Metadata metadata;

	protected void setUp() throws Exception
	{
		super.setUp();
		MetadataLoaderImpl loader = new MetadataLoaderImpl();
		metadata = loader.load(PaymentFormServiceTest.getSomePaymentFormName());
		assertNotNull(metadata);
	}

	public void testBeforeOpenActions() throws BusinessException, DocumentException, DocumentLogicException
	{
		MetadataLoaderImpl metadataLoader = new MetadataLoaderImpl();
		PaymentFormService service = new PaymentFormService();

		List<MetadataBean> forms = service.getAllForms();
		for (MetadataBean metadataBean : forms)
		{
			String name = metadataBean.getName();
			Metadata metaData = metadataLoader.load(name);
			assertNotNull("Форма " + name + " не найдена", metaData);

			BusinessDocument businessDocument = metaData.createDocument();
		}
	}

	@IncludeTest(configurations = "russlav")
	public void testOpenActions() throws BusinessException, DocumentException, DocumentLogicException
	{
		MetadataLoaderImpl metadataLoader = new MetadataLoaderImpl();
		Metadata metaData = metadataLoader.load("GoodsAndServicesPayment");
		assertNotNull("Форма GoodsAndServicesPayment не найдена", metaData);

		BusinessDocument businessDocument = metaData.createDocument();

		assertNotNull(businessDocument);
	}

	public void testCreateDocument()
	{
		assertNotNull(metadata.createDocument());
	}

	public void testConverters()
	{
		assertNotNull(metadata.createConverter("html"));
		assertNotNull(metadata.createConverter("list-html"));
		assertNotNull(metadata.createConverter("list-filter-html"));
	}

	public void testDictionaries()
	{
		assertNotNull(metadata.getDictionaries());
	}

	protected void tearDown() throws Exception
	{
		metadata = null;
		super.tearDown();
	}
}