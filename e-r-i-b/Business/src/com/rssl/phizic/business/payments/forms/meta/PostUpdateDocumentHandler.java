package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.ObjectEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.documents.*;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.EarlyLoanRepaymentClaimImpl;
import com.rssl.phizic.business.documents.payments.LoanCardClaim;
import com.rssl.phizic.business.documents.payments.ShortLoanClaim;
import com.rssl.phizic.business.promocodes.PromoCodeService;
import com.rssl.phizic.business.promocodes.PromoCodesHelper;
import com.rssl.phizic.business.statemachine.PaymentStateMachineService;
import com.rssl.phizic.business.statemachine.StateMachineExecutor;
import com.rssl.phizic.gate.cms.claims.CardBlockingClaim;
import com.rssl.phizic.gate.payments.*;
import com.rssl.phizic.gate.payments.autopayment.RefuseAutoPayment;
import com.rssl.phizic.gate.payments.systems.AbstractPaymentSystemPayment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Хендлер, выполняющий каскад действий над документом
 * при смене статуса на EXECUTED
 *
 * @author hudyakov
 * @ created 01.07.2010
 * @ $Author$
 * @ $Revision$
 */
public class PostUpdateDocumentHandler extends BusinessDocumentHandlerBase
{
	private static final BusinessDocumentService businessDocumentService = new BusinessDocumentService();
	private static final PromoCodeService promoCodeService = new PromoCodeService();
	private static final DepartmentService departmentService = new DepartmentService();
	private final PaymentStateMachineService stateMachineService = new PaymentStateMachineService();
	private static final List<Class> jurPaymentClasses = new ArrayList<Class>();

	static
	{
		jurPaymentClasses.add(CardRUSTaxPayment.class);
		jurPaymentClasses.add(CardJurIntraBankTransfer.class);
		jurPaymentClasses.add(CardJurTransfer.class);
		jurPaymentClasses.add(AccountRUSTaxPayment.class);
		jurPaymentClasses.add(AccountJurIntraBankTransfer.class);
		jurPaymentClasses.add(AccountJurTransfer.class);
	}

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		GateExecutableDocument gateDocument = (GateExecutableDocument) document;

		if (!(document instanceof AbstractPaymentDocument)
			&& (!gateDocument.getFormName().equals("LossPassbookApplication"))
			&& !(gateDocument instanceof LoanCardClaim)
			&& !(gateDocument instanceof RefuseLongOffer)
			&& !(gateDocument instanceof AbstractDepoAccountClaim)
			&& !(gateDocument instanceof RecallDepositaryClaim)
			&& !(gateDocument instanceof LoyaltyProgramRegistrationClaim)
			&& !(gateDocument instanceof ShortLoanClaim)
			&& !(gateDocument instanceof CardBlockingClaim)
			&& !(gateDocument instanceof AccountChangeInterestDestinationClaim)
			&& !(gateDocument instanceof ChangeDepositMinimumBalanceClaim)
			&& !(gateDocument instanceof EarlyLoanRepaymentClaimImpl)
			&& !(gateDocument instanceof CancelEarlyLoanRepaymentClaimImpl))
		{
			throw new DocumentException("Ожидается наследник AbstractPaymentDocument, RefuseLongOffer, RecallDepositaryClaim или LossPassbookApplication");
		}

		try
		{
			if (gateDocument.getFormName().equals("LossPassbookApplication")
				|| document instanceof RefuseLongOffer
				|| document instanceof RefuseAutoPayment
				|| document instanceof LoanCardClaim
				|| document instanceof CardBlockingClaim
				|| document instanceof ShortLoanClaim
				|| document instanceof AccountChangeInterestDestinationClaim
				|| document instanceof ChangeDepositMinimumBalanceClaim
				|| document instanceof EarlyLoanRepaymentClaimImpl
				|| document instanceof CancelEarlyLoanRepaymentClaimImpl
				|| (document instanceof AbstractLongOfferDocument && ((AbstractLongOfferDocument) document).isLongOffer()))
			{
				gateDocument.setExecutionDate(Calendar.getInstance());
			}
			else if(document instanceof AbstractDepoAccountClaim)
			{
				AbstractDepoAccountClaim abstr = (AbstractDepoAccountClaim) document;
				abstr.setExecutionDate(Calendar.getInstance());
			}
			else if(document instanceof RecallDepositaryClaim)
			{
				updateRecallDocument(document);
			}
			else
			{
				AbstractPaymentDocument abstr = (AbstractPaymentDocument) document;
				abstr.setExecutionDate(Calendar.getInstance());
			}

			//добавляем в документ промо-код
			if ((document instanceof AbstractPaymentSystemPayment || jurPaymentClasses.contains(gateDocument.getType()))
				 && !((document instanceof AbstractLongOfferDocument) && ((AbstractLongOfferDocument)document).isLongOffer()))
			{
				Department tb = departmentService.getTB(gateDocument.getDepartment());
				if (promoCodeService.existPromoAction(tb.getId(), gateDocument.getExecutionDate()))
				{
					gateDocument.setPromoCode(PromoCodesHelper.generatePromoCode(gateDocument.getId()));
				}
			}
		}
		catch (Exception e)
		{
			// При любых ислючениях пишем ошибку в лог
			// т.к. платеж успешно исполнился. и его надо отобразить клиенту.
			log.error("Ошибка при переводе платежа с идентификатором "+ gateDocument.getId() + "в состояние исполнен", e);
		}
	}

	private void updateRecallDocument(StateObject document) throws DocumentException, DocumentLogicException
	{
		try
		{
			RecallDepositaryClaim depo = (RecallDepositaryClaim) document;
			depo.setExecutionDate(Calendar.getInstance());
			businessDocumentService.addOrUpdate(depo);
			//находим исходный платеж
			BusinessDocument parent = businessDocumentService.findById( depo.getRecallDocumentID() );

			//обновляем отзываемый документ

			StateMachineExecutor executor = new StateMachineExecutor(stateMachineService.getStateMachineByFormName(parent.getFormName()));
			executor.initialize(parent);
			executor.fireEvent(new ObjectEvent(DocumentEvent.RECALL, "client", depo.getRevokePurpose()));

			businessDocumentService.addOrUpdate(parent);
		}
		catch (BusinessException e)
		{
			throw new DocumentException("при отправке отзыва не найден исходный платеж id=" + ((RecallDepositaryClaim)document).getRecallDocumentID(), e);
		}
		catch (BusinessLogicException e)
		{
			throw new DocumentLogicException(e);
		}
	}
}
