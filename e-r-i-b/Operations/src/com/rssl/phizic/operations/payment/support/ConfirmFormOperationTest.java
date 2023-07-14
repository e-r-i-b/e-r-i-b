package com.rssl.phizic.operations.payment.support;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.InternalTransfer;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.metadata.MetadataCache;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.RurPayment;
import com.rssl.phizic.business.documents.payments.source.ExistingSource;
import com.rssl.phizic.business.documents.payments.validators.IsOwnDocumentValidator;
import com.rssl.phizic.business.extendedattributes.ExtendedAttribute;
import com.rssl.phizic.business.payments.forms.meta.MetadataLoaderImpl;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.operations.payment.ConfirmFormOperation;
import com.rssl.phizic.operations.payment.ConfirmFormPaymentOperation;
import com.rssl.phizic.test.BusinessTestCaseBase;

import java.math.BigDecimal;

/**
 * @author Kosyakova
 * @ created 12.10.2006
 * @ $Author$
 * @ $Revision$
 */
@SuppressWarnings({"JavaDoc", "MagicNumber"})
public class ConfirmFormOperationTest extends BusinessTestCaseBase
{
	private static final BusinessDocumentService businessDocumentService = new BusinessDocumentService();
	private InternalTransfer internalPayment;
	private BusinessDocument personalPayment;
	private PersonData testClientContext;

	protected void setUp() throws Exception
	{
		super.setUp();
		testClientContext = createTestClientContext();
	}

	protected void tearDown() throws Exception
	{
		super.tearDown();

		if (internalPayment != null)
		{
			internalPayment = (InternalTransfer) businessDocumentService.findById(internalPayment.getId());
			businessDocumentService.remove(internalPayment);
		}
		if (personalPayment != null)
		{
			personalPayment = businessDocumentService.findById(personalPayment.getId());
			businessDocumentService.remove(personalPayment);
		}
	}

	public void testInternalPayment() throws Exception
	{
		ConfirmFormOperation operation = new ConfirmFormPaymentOperation();

		Metadata metadata = MetadataCache.getBasicMetadata("InternalPayment");
		assertNotNull("Форма InternalPayment не найдена", metadata);
		internalPayment = (InternalTransfer) metadata.createDocument();
		internalPayment.setOwner(testClientContext.createDocumentOwner());
		internalPayment.setFormName(metadata.getName());
		internalPayment.setChargeOffAmount(new Money(12, 22, getDefaultCurrency()));
		internalPayment.setChargeOffAccount("12345678901234567890");
		internalPayment.setReceiverAccount("22345678901234567890");
		businessDocumentService.addOrUpdate(internalPayment);

		operation.initialize(new ExistingSource(internalPayment.getId(), new IsOwnDocumentValidator()));
		operation.confirm();
	}

	public void testPersonPayment() throws Exception
	{
		ConfirmFormOperation operation = new ConfirmFormPaymentOperation();

		personalPayment = createPersonalPayment();

		operation.initialize(new ExistingSource(personalPayment.getId(), new IsOwnDocumentValidator()));
		operation.confirm();
	}

	private RurPayment createPersonalPayment() throws Exception
	{
		PersonData data = createTestClientContext();

		Metadata metadata = new MetadataLoaderImpl().load("RurPayment");
		RurPayment payment = (RurPayment) metadata.createDocument();

		payment.setOwner(data.createDocumentOwner());

		assertNotNull("Форма RurPayment не найдена", metadata);
		payment.setFormName(metadata.getName());

		payment.setPayerName("test PN");
		payment.setChargeOffAccount("01234567890123456789");

		payment.setAttributeValue(ExtendedAttribute.STRING_TYPE,"receiverAlias", "TestCreditAlias");

		//noinspection MagicNumber
		Money amount = new Money(new BigDecimal("9129.9"), getDefaultCurrency());
		payment.setChargeOffAmount(amount);

		businessDocumentService.addOrUpdate(payment);

		return payment;
	}

	private Currency getDefaultCurrency() throws BusinessException
	{
		try
		{
			CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
			return currencyService.findByAlphabeticCode("RUB");
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
	}
}
