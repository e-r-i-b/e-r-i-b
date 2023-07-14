package com.rssl.phizic.business.documents;

import com.rssl.common.forms.doc.DocumentSignature;
import com.rssl.phizic.auth.modes.DocumentSignatureImpl;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.ClientBusinessDocumentOwner;
import com.rssl.phizic.business.documents.payments.RurPayment;
import com.rssl.phizic.business.extendedattributes.ExtendedAttribute;
import com.rssl.phizic.business.payments.forms.PaymentFormService;
import com.rssl.phizic.business.payments.forms.PaymentFormServiceTest;
import com.rssl.phizic.business.payments.forms.meta.MetadataBean;
import com.rssl.phizic.business.payments.forms.meta.MetadataLoaderImpl;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonServiceTest;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.test.BusinessTestCaseBase;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.hibernate.Session;
import org.w3c.dom.Document;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Evgrafov
 * @ created 01.11.2005
 * @ $Author: saharnova $
 * @ $Revision:4639 $
 */
@SuppressWarnings({"JavaDoc", "ReuseOfLocalVariable"})
public class DocumentServiceTest extends BusinessTestCaseBase
{
	private static final BusinessDocumentService businessDocumentService = new BusinessDocumentService();

	private static Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	protected void setUp() throws Exception
	{
		super.setUp();
		initializeRsV51Gate();
	}

	public void testCRD() throws BusinessException
	{
		// создание поиск  удаление платежа без формы и атрибутов

		RurPayment payment1 = createTestPayment();
		businessDocumentService.addOrUpdate(payment1);

		payment1 = (RurPayment) businessDocumentService.findById(payment1.getId());
		assertNotNull(payment1);
		businessDocumentService.remove(payment1);

		// создание поиск удаление платежа без атрибутов
		RurPayment payment2 = createTestPayment();

		String paymentFormName = PaymentFormServiceTest.getSomePaymentFormName();
		MetadataBean metadata = new PaymentFormService().findByName(paymentFormName);

		payment2.setFormName(metadata.getName());

		businessDocumentService.addOrUpdate(payment2);

		payment2 = (RurPayment) businessDocumentService.findById(payment2.getId());

		assertNotNull(payment2);
		businessDocumentService.remove(payment2);

		// создание поиск удаление платежа с формой и атрибутами
		RurPayment payment3 = createTestPayment();

		payment3.setPayerName("01234567890123456789");
		payment3.setChargeOffAccount("01234567890123456789");
		payment3.setFormName(metadata.getName());

		businessDocumentService.addOrUpdate(payment3);

		payment3 = (RurPayment) businessDocumentService.findById(payment3.getId());

		assertNotNull(payment3);

		businessDocumentService.remove(payment3);
	}

	public void testSignature() throws Exception
	{
		HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
		{
			public Void run(Session session) throws Exception
			{
				RurPayment payment = createTestPayment();
				businessDocumentService.addOrUpdate(payment);

				DocumentSignature signature = new DocumentSignatureImpl();
				signature.setText("123");
				payment.setSignature(signature);
				businessDocumentService.addOrUpdate(payment);

				payment = (RurPayment) businessDocumentService.findById(payment.getId());

				assertNotNull(payment);
				DocumentSignature signature1 = payment.getSignature();
				assertNotNull(signature1);
				assertEquals("123", signature1.getText());

				businessDocumentService.remove(payment);

				return null;
			}
		});
	}

	public void testDomConversion() throws Exception
	{
		RurPayment payment = createTestPayment();

		payment.setAttributeValue(ExtendedAttribute.STRING_TYPE, "testAttrName2", "testAttr2");

		Document document = payment.convertToDom();

		assertNotNull(document);
		String str1 = XmlHelper.convertDomToText(document);
		log.info(str1);

		RurPayment payment2 = new RurPayment();
		payment2.readFromDom(document, null);
		Document document2 = payment2.convertToDom();

		String str2 = XmlHelper.convertDomToText(document2);
		log.info(str2);

		assertEquals(str1, str2);

		assertNotNull(payment2);
	}

	public static RurPayment createTestPayment() throws BusinessException
	{
		RurPayment payment = new RurPayment();
		payment.setFormName("InternalPayment");
		payment.setPayerName("test PN");
		payment.setChargeOffAccount("01234567890123456789");

		payment.setReceiverName("test RN");
		payment.setReceiverAccount("01234567890123456789");

		//noinspection MagicNumber
		Money amount = new Money(new BigDecimal("99.9"), getDefaultCurrency());
		payment.setChargeOffAmount(amount);

		return payment;
	}

	public void testReadPaymentClass() throws BusinessException
	{
		MetadataLoaderImpl metadataLoader = new MetadataLoaderImpl();
		String paymentFormName = PaymentFormServiceTest.getSomePaymentFormName();
		Metadata metadata = metadataLoader.load(paymentFormName);
		assertNotNull("Форма " + paymentFormName + " не найдена", metadata);

		BusinessDocument businessDocument = metadata.createDocument();

		assertNotNull(businessDocument);
	}

	public void testExtendedAttribute() throws Exception
	{
		Metadata metadata = createMetadata();
		List<BusinessDocumentBase> list = new ArrayList<BusinessDocumentBase>();

		for (int i = 0; i <= 1; i++)
		{
			BusinessDocumentBase payment = createPayment(metadata);
			businessDocumentService.addOrUpdate(payment);

			payment = (BusinessDocumentBase) businessDocumentService.findById(payment.getId());

			ExtendedAttribute attribute = payment.getAttribute("testAttrName2");

			assertNotNull(attribute);

			list.add(payment);
		}

		List<BusinessDocumentBase> all = testGetAllPayments();
		assertTrue(all.size() > 0);

		for (BusinessDocumentBase payment1 : list)
		{
			businessDocumentService.remove(payment1);
		}
	}

	private static Metadata createMetadata() throws BusinessException
	{
		MetadataLoaderImpl metadataLoader = new MetadataLoaderImpl();
		String paymentFormName = PaymentFormServiceTest.getSomePaymentFormName();
		Metadata metadata = metadataLoader.load(paymentFormName);
		assertNotNull("Форма " + paymentFormName + " не найдена", metadata);

		return metadata;
	}

	private static BusinessDocumentBase createPayment(Metadata metadata)
	{
		RurPayment payment = (RurPayment) metadata.createDocument();

		//noinspection MagicNumber
		Money amount = new Money(new BigDecimal("99.9"), getDefaultCurrency());
		payment.setChargeOffAmount(amount);

		payment.setAttributeValue(ExtendedAttribute.STRING_TYPE, "testAttrName1", "testAttr1");

		payment.setAttributeValue(ExtendedAttribute.STRING_TYPE, "testAttrName2", "testAttr2");

		return payment;
	}

	public List<BusinessDocumentBase> testGetAllPayments() throws Exception
	{
		return HibernateExecutor.getInstance().execute(new HibernateAction<List<BusinessDocumentBase>>()
		{
			public List<BusinessDocumentBase> run(Session session) throws Exception
			{
				String hql = "select payment from com.rssl.phizic.business.documents.BusinessDocumentBase payment";
				//noinspection unchecked
				return session.createQuery(hql).list();
			}
		});
	}

	public static BusinessDocumentBase createTestPaymentWithAttributes(ActivePerson person) throws BusinessException
	{
		if (person == null)
			throw new IllegalArgumentException("Error: person = null");

		Metadata metadata = createMetadata();
		BusinessDocumentBase payment = createPayment(metadata);
		payment.setOwner(new ClientBusinessDocumentOwner(person));
		businessDocumentService.addOrUpdate(payment);

		return payment;
	}

	public void testCreateTestPaymentWithAttributes() throws Exception
	{
		ActivePerson testPerson = PersonServiceTest.getTestPerson();
		BusinessDocumentBase payment = createTestPaymentWithAttributes(testPerson);
		businessDocumentService.remove(payment);
	}

	public void testcommission() throws Exception
	{
		RurPayment payment = createTestPayment();
		Money amount = new Money(new BigDecimal("99.9"), getDefaultCurrency());
		payment.setChargeOffAmount(amount);

		Money commission = new Money(new BigDecimal("23.9"), getDefaultCurrency());
		//	payment.setCommission(commission);
		businessDocumentService.addOrUpdate(payment);

		BusinessDocument payment1 = businessDocumentService.findById(payment.getId());
		assertNotNull(payment1);
//		assertEquals(commission, payment1.getCommission());
//		assertEquals(amount, payment1.getChargeOffAmount());

		businessDocumentService.remove(payment);
	}

	protected static Currency getDefaultCurrency()
	{
		try
		{
			CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
			return currencyService.findByAlphabeticCode("RUB");
		}
		catch (GateException e)
		{
			throw new RuntimeException(e);
		}
	}
}
