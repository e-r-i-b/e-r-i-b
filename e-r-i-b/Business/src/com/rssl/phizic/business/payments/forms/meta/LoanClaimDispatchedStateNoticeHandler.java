package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.payments.DocumentNotice;
import com.rssl.phizic.business.documents.payments.ExtendedLoanClaim;
import com.rssl.phizic.business.loanclaim.LoanClaimHelper;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.loanOffer.LoanOfferConfig;
import com.rssl.phizic.gate.loanclaim.dictionary.LoanIssueMethod;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author Rtischeva
 * @ created 23.07.15
 * @ $Author$
 * @ $Revision$
 */
public class LoanClaimDispatchedStateNoticeHandler extends NoticeHandler
{
	private static final String NO_VISIT_BANK_TEXT = "Вам придет SMS-уведомление о решении банка. В случае одобрения заявки, Вы сможете получить кредит на указанную карту/счёт без визита в Банк, согласившись с индивидуальными условиями кредитования в Сбербанк Онлайн. " +
			"Следить за статусом заказа вы можете " + "<a href = \"/PhizIC/private/loans/list.do\">" + "на странице списка кредитов." + "</a>";
	private static final String MUST_VISIT_BANK_TEXT = "Вам придет SMS-уведомление о решении банка. Следить за статусом заявки вы можете " + "<a href = \"/PhizIC/private/loans/list.do\">" + "на странице списка кредитов." + "</a>";

	@Override
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentLogicException, DocumentException
	{
		if (!(document instanceof ExtendedLoanClaim))
		{
			throw new DocumentException("Неверный тип документа. Ожидается ExtendedLoanClaim");
		}

		ExtendedLoanClaim claim = (ExtendedLoanClaim) document;

		String text;

		if (claim.getLoanIssueMethod().isNewProductForLoan() && claim.getLoanIssueMethod().getProductForLoan().equals(LoanIssueMethod.LoanProductType.CARD))
		{
			text = MUST_VISIT_BANK_TEXT;
		}
		else
		{
			Person person;
			try
			{
				person = claim.getOwner().getPerson();
			}
			catch (BusinessException e)
			{
				throw new DocumentException(e);
			}

			Long claimPeriod = claim.getLoanPeriod();
			boolean isNearPensioner = PersonHelper.isNearPensioner(person, claimPeriod.intValue());
			if (isNearPensioner)
			{
				text = NO_VISIT_BANK_TEXT;
			}
			else
			{
				boolean byLoanOffer = StringHelper.isNotEmpty(claim.getLoanOfferId());
				LoanOfferConfig config = ConfigFactory.getConfig(LoanOfferConfig.class);
				if (LoanClaimHelper.isSalary(claim))
				{
					if (byLoanOffer)
					{
						if (config.isUkoPreapprovedSalaries())
						    text = NO_VISIT_BANK_TEXT;
						else
							text = MUST_VISIT_BANK_TEXT;
					}
					else
					{
						if (config.isUkoCommonConditionsSalaries())
						    text = NO_VISIT_BANK_TEXT;
						else
							text = MUST_VISIT_BANK_TEXT;
					}
				}
				else
				{
					if (LoanClaimHelper.isPensioner(claim))
					{
						if (byLoanOffer)
						{
							if (config.isUkoPreapprovedPensioners())
						        text = NO_VISIT_BANK_TEXT;
							else
						        text = MUST_VISIT_BANK_TEXT;
						}
						else
						{
							if (config.isUkoCommonConditionsPensioners())
						        text = NO_VISIT_BANK_TEXT;
							else
						        text = MUST_VISIT_BANK_TEXT;
						}
					}
					else
					{
						if (LoanClaimHelper.isSberEmployee(claim))
						{
							if (byLoanOffer)
							{
								if (config.isUkoPreapprovedEmployees())
							        text = NO_VISIT_BANK_TEXT;
								else
							        text = MUST_VISIT_BANK_TEXT;
							}
							else
							{
								if (config.isUkoCommonConditionsEmployees())
							        text = NO_VISIT_BANK_TEXT;
								else
							        text = MUST_VISIT_BANK_TEXT;
							}
						}
						else
							text = MUST_VISIT_BANK_TEXT;
					}

				}
			}
		}

		String clazz = getParameter(TYPE);
		String title = getParameter(TITLE);

		claim.setNotice(new DocumentNotice(clazz, title, text));
		save(claim);
	}

}
