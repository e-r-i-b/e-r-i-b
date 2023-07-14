package com.rssl.phizic.gate;

/**
 * Группа ограничений для одноразовых паролей (OTP)
 * @author lukina
 * @ created 11.12.2011
 * @ $Author$
 * @ $Revision$
 */

public interface OTPRestriction extends AdditionalProductData
{
	/** Признак возможности печати одноразовых паролей по карте
	 * @return true - установить возможность, false - запретить возможность
	 */
	Boolean isOTPGet();

	/** Признак возможности использования ранее выданных одноразовых паролей.
     *  Обязателен при OPTGet=false
	 * @return true – выданные ранее одноразовые пароли не блокируются,
	 * false - - выданные ранее одноразовые парлиблокируются
	 */
	Boolean isOTPUse();
}
