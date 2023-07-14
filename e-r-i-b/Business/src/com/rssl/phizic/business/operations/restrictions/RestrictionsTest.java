package com.rssl.phizic.business.operations.restrictions;

import com.rssl.phizic.business.deposits.DepositProduct;
import com.rssl.phizic.business.deposits.DepositProductService;
import com.rssl.phizic.business.operations.OperationDescriptor;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.services.Service;
import com.rssl.phizic.business.services.ServiceOperationDescriptor;
import com.rssl.phizic.business.services.ServiceService;
import com.rssl.phizic.business.services.ServiceServiceTest;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.test.BusinessTestCaseBase;

import java.util.List;

/**
 * @author Roshka
 * @ created 17.04.2006
 * @ $Author$
 * @ $Revision$
 */

public class RestrictionsTest extends BusinessTestCaseBase
{
	private PersonData personData;
	private OperationDescriptor operationDescriptor;
	private Service service;

	public void testAccountRestrictions() throws Exception
	{
		RestrictionService restrictionService = new RestrictionService();
		PersonData testClientContext = createTestClientContext();
		List<AccountLink> accountLinks = testClientContext.getAccounts();

		assertTrue("Нет счетов у тестового пользователя!", accountLinks.size() > 0);

		ListAccountRestrictionData restrictionData = new ListAccountRestrictionData();
		setDataToRestriction(restrictionData);
		restrictionData.setAccountLinks(accountLinks);

		try
		{
			restrictionService.add(restrictionData);

			RestrictionData foundRestrictionData = restrictionService.find(personData.getPerson().getLogin(), service, operationDescriptor);

			assertNotNull(foundRestrictionData);

			ListAccountRestriction listAccountRestriction = (ListAccountRestriction) foundRestrictionData.buildRestriction();
			assertNotNull(listAccountRestriction);
		}
		finally
		{
			restrictionService.remove(restrictionData);
		}
	}

	public void testCardRestrictions() throws Exception
	{
		RestrictionService restrictionService = new RestrictionService();
		List<CardLink> cardLinks = personData.getCards();

		assertTrue("Нет карт у тестового пользователя!", cardLinks.size() > 0);

		ListCardRestrictionData restrictionData = new ListCardRestrictionData();
		setDataToRestriction(restrictionData);
		restrictionData.setCardLinks(cardLinks);

		try
		{
			restrictionService.add(restrictionData);

			ListCardRestrictionData foundRestrictionData =
					(ListCardRestrictionData) restrictionService.find(personData.getPerson().getLogin(), service, operationDescriptor);

			assertNotNull(foundRestrictionData);

			CardRestriction restriction = foundRestrictionData.buildRestriction();
			assertNotNull(restriction);
		}
		finally
		{
			restrictionService.remove(restrictionData);
		}
	}

	public void testDPRestrictions() throws Exception
	{
		RestrictionService restrictionService = new RestrictionService();
		List<DepositProduct> products = new DepositProductService().getAllProducts();

		assertTrue("Нет продуктов!!!", products.size() > 0);

		ListDepositPoductRestrictionData restrictionData = new ListDepositPoductRestrictionData();
		setDataToRestriction(restrictionData);
		restrictionData.setProducts(products);

		try
		{
			restrictionService.add(restrictionData);

			ListDepositPoductRestrictionData foundRestrictionData =
					(ListDepositPoductRestrictionData) restrictionService.find(personData.getPerson().getLogin(), service, operationDescriptor);

			assertNotNull(foundRestrictionData);

			DepositProductRestriction restriction = foundRestrictionData.buildRestriction();
			assertNotNull(restriction);
		}
		finally
		{
			restrictionService.remove(restrictionData);
		}
	}

	private void setDataToRestriction(RestrictionData restrictionData) throws Exception
	{

		restrictionData.setLoginId(personData.getPerson().getLogin().getId());
		restrictionData.setServiceId(service.getId());

		restrictionData.setOperationId(operationDescriptor.getId());
	}

	protected void setUp() throws Exception
	{
		super.setUp();
		personData = createTestClientContext();

		ServiceService serviceService = new ServiceService();
		service = ServiceServiceTest.getTestService();
		ServiceOperationDescriptor sod = serviceService.getServiceOperations(service).get(0);
		operationDescriptor = sod.getOperationDescriptor();
	}

	protected void tearDown() throws Exception
	{
		personData = null;
		service = null;
		operationDescriptor = null;

		super.tearDown();
	}
}
