package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.ExtendedLoanClaim;
import com.rssl.phizic.gate.loanclaim.type.Spouse;

import java.math.BigDecimal;

/**
 * Проверяет, что сумма основного дохода и доп. дохода больше (или не меньше) общего дохода
 *
 * @author EgorovaA
 * @ created 31.01.14
 * @ $Author$
 * @ $Revision$
 */
public class ExtendedLoanClaimTotalIncomeHandler extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof ExtendedLoanClaim))
		{
			throw new DocumentException("Неверный тип заявки id=" + ((BusinessDocument) document).getId() + " (Ожидается ExtendedLoanClaim)");
		}

		ExtendedLoanClaim loanClaim = (ExtendedLoanClaim) document;

		BigDecimal basicIncome = loanClaim.getApplicantBasicIncome();
		BigDecimal additionalIncome = loanClaim.getApplicantAdditionalIncome();
		BigDecimal income = basicIncome.add(additionalIncome);
		BigDecimal familyIncome = loanClaim.getApplicantFamilyIncome();

		// A. CHG070592
		// Если у клиента есть супруг (га) и он (она) находятся не на иждивении"
		// должна выполняться  следующая проверка
		// "Доход семьи" > "доп. доход" + "среднемесячный доход
		Spouse spouse = loanClaim.getApplicantSpouse();
		if (spouse != null && !spouse.isDependant())
		{
			if ( !(familyIncome.compareTo(income) > 0) )
				throw new DocumentLogicException("Среднемесячный доход семьи должен быть больше суммы среднемесячного основного дохода и среднемесячного дополнительного дохода. Пожалуйста, проверьте заполнение полей");
		}

		// B. CHG071765
		// Если заемщик холост\не замужем или семейное положение указано «женат/замужем» и для жены/мужа установлен флаг «на иждивении», то
		// "Доход семьи" ="доп. доход" + "среднемесячный доход
		else
		{
			if ( !(familyIncome.compareTo(income) == 0) )
				throw new DocumentLogicException("Среднемесячный доход семьи должен быть равен сумме среднемесячного основного дохода и среднемесячного дополнительного дохода. Пожалуйста, проверьте заполнение полей");
		}
	}
}
