package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.auth.CheckLoginTest;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.ermb.products.ErmbProductSettings;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.DateSpan;
import com.rssl.phizic.common.types.MockObject;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.bankroll.*;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.test.BusinessTestCaseBase;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * @author Krenev
 * @ created 02.08.2007
 * @ $Author$
 * @ $Revision$
 */
@SuppressWarnings({"JavaDoc"})
public class ExternalResourceServiceTest extends BusinessTestCaseBase
{
	private Login testLogin;
	private ExternalResourceService externalResourceService;

	public void testAccounts()  throws Exception
    {

	    Account account = mockAccount();
	    AccountLink al = externalResourceService.addAccountLink(testLogin, account, null, ErmbProductSettings.OFF, null);

	    assertEquals(testLogin.getId(), al.getLoginId());
	    assertEquals(account.getNumber(), al.getNumber());
	    assertFalse(al.getPaymentAbility());
	    assertEquals(account.getId(), al.getExternalId());
	    assertNotNull(al.getId());

	    AccountLink accountLink = (AccountLink) externalResourceService.findLinkById(AccountLink.class, al.getId());
	    assertNotNull(accountLink);

	    assertEquals(accountLink.getLoginId(), al.getLoginId());
	    assertEquals(accountLink.getNumber(), al.getNumber());
	    assertEquals(accountLink.getPaymentAbility(), al.getPaymentAbility());
	    assertEquals(accountLink.getExternalId(), al.getExternalId());
	    assertEquals(accountLink.getId(), al.getId());

	    al.setPaymentAbility(true);
	    // TODO externalResourceService.update(al);

	    externalResourceService.removeLink(al);
    }

	public void testCards()  throws Exception
	 {
		 CardLink cl = externalResourceService.addCardLink(testLogin, mockCard(), null, ErmbProductSettings.OFF, null, null);

		 CardLink cardLink = externalResourceService.findLinkById(CardLink.class, cl.getId());
		 assertNotNull(cardLink);

		 assertEquals(cardLink.getLoginId(), cl.getLoginId());
		 assertEquals(cardLink.getNumber(), cl.getNumber());
		 assertEquals(cardLink.getExternalId(), cl.getExternalId());
		 assertEquals(cardLink.getId(), cl.getId());

		 externalResourceService.removeLink(cl);
	 }

	private Account mockAccount()
	{
		return new MockAccount();
	}

	private Card mockCard()
	{
		return new MockCard();
	}

	protected void setUp() throws Exception
	{
		super.setUp();
		testLogin = CheckLoginTest.getTestLogin();
		externalResourceService = new ExternalResourceService();
	}

	@Override protected void tearDown() throws Exception
	{
		testLogin = null;
		externalResourceService = null;
		super.tearDown();
	}

	private static class MockAccount implements Account, MockObject
	{

		public String getId()
		{
			return "123";
		}

		public String getDescription()
		{
			return null;
		}

		public String getFullDescription()
		{
			return null;
		}

		public String getType()
		{
			return null;
		}

		public String getNumber()
		{
			return "12345678900987654321";
		}

		public Currency getCurrency()
		{
			return null;
		}

		public Calendar getOpenDate()
		{
			return null;
		}

		public String getFullname()
		{
			return null;
		}

		public Boolean getCreditAllowed()
		{
			return null;
		}

		public Boolean getDebitAllowed()
		{
			return null;
		}
			public BigDecimal getInterestRate()
		{
			return null;
		}

		public Money getBalance()
		{
			return null;
		}

		public Money getMaxSumWrite()
		{
			return null;
		}

		public AccountState getAccountState()
		{
			return null;
		}
		public Office getOffice()
		{
			return null;
		}

		public Long getKind()
		{
			return null;
		}

		public Long getSubKind()
		{
			return null;
		}

		public Boolean getDemand()
		{
			return false;
		}

		public Boolean getPassbook()
		{
			return true;
		}

		public String getAgreementNumber()
		{
			return null;
		}

		public Calendar getCloseDate()
		{
			return null;
		}

		public DateSpan getPeriod()
		{
			return null;
		}

		public Boolean getCreditCrossAgencyAllowed()
		{
			return null;
		}

		public Boolean getDebitCrossAgencyAllowed()
		{
			return null;
		}

		public Boolean getProlongationAllowed()
		{
			return null;
		}

		public String getInterestTransferAccount()
		{
			return null;
		}

		public String getInterestTransferCard()
		{
			return null;
		}

		public BigDecimal getClearBalance()
		{
			return null;
		}

		public BigDecimal getMaxBalance()
		{
			return null;
		}

		public Calendar getLastTransactionDate()
		{
			return null;
		}

		public Money getMinimumBalance()
		{
			return null;
		}

		public Client getAccountClient()
		{
			return null;
		}

		public Calendar getProlongationDate()
		{
			return null;
		}

        public Long getClientKind()
        {
            return null;
        }
	}

	private static class MockCard implements Card
	{

		public String getId()
		{
			return "123";
		}

		public String getType()
		{
			return "VISA";
		}

		public String getDescription()
		{
			return null;
		}

		public StatusDescExternalCode getStatusDescExternalCode()
		{
			return null;
		}

		public String getNumber()
		{
			return "123321";
		}

		public Calendar getIssueDate()
		{
			return null;
		}

		public Calendar getExpireDate()
		{
			return null;
		}

		public String getDisplayedExpireDate()
		{
			return null;
		}

		public boolean isMain()
		{
			return false;
		}

		public CardType getCardType()
		{
			return null;
		}

		public Money getAvailableLimit()
		{
			return null;
		}

        public Office getOffice()
        {
	        return null;
        }

		public Code getOriginalTbCode()
        {
	        return null;
        }

        public AdditionalCardType getAdditionalCardType()
        {
	        return null;
        }

	    public String getStatusDescription()
	    {
			 return null;
	    }
        public CardState getCardState()
        {
	        return null;
        }

		public AccountState getCardAccountState()
		{
			return null;
		}

		public Currency getCurrency()
        {
	        return null;
        }

		public String getMainCardNumber()
		{
			return null;
		}

		public boolean isVirtual()
		{
			return false;
		}

		public String getPrimaryAccountNumber()
		{
			return null;
		}

		public String getPrimaryAccountExternalId()
		{
			return null;
		}

		public Long getKind()
		{
			return null;
		}

		public Long getSubkind()
		{
			return null;
		}

		public String getUNICardType()
		{
			return null;
		}

		public String getUNIAccountType()
		{
			return null;  
		}

		public CardLevel getCardLevel()
		{
			return null;
		}

		public CardBonusSign getCardBonusSign()
		{
			return null;
		}

		public String getClientId()
		{
			return null;
		}

		public boolean isUseReportDelivery()
		{
			return false;
		}

		public String getEmailAddress()
		{
			return null;
		}

		public ReportDeliveryType getReportDeliveryType()
		{
			return null;
		}

		public ReportDeliveryLanguage getReportDeliveryLanguage()
		{
			return null;
		}

		public Money getPurchaseLimit()
		{
			return null;
		}

		public Money getAvailableCashLimit()
		{
			return null;
		}

		public String getHolderName()
		{
			return null;
		}

		public String getContractNumber()
		{
			return null;
		}

        public Calendar getNextReportDate()
        {
            return null;
        }

		public Money getOverdraftLimit()
		{
			return null;
		}

		public Money getOverdraftTotalDebtSum()
		{
			return null;
		}

		public Money getOverdraftMinimalPayment()
		{
			return null;
		}

		public Calendar getOverdraftMinimalPaymentDate()
		{
			return null;
		}

		public Money getOverdraftOwnSum()
		{
			return null;
		}

		public Client getCardClient()
		{
			return null;
		}
	}
}
