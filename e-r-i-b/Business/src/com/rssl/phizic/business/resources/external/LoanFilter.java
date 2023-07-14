package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.business.operations.restrictions.Restriction;
import com.rssl.phizic.gate.loans.Loan;
import org.apache.commons.collections.Predicate;

/**
 * @author gladishev
 * @ created 06.08.2010
 * @ $Author$
 * @ $Revision$
 */
public interface LoanFilter extends Restriction, Predicate
{
    /**
     * Подходит ли кредит под критерии
     * @param loan- кредит
     * @return true - подходит, false - не подходит
     */
    boolean accept(Loan loan);
}
