package com.rssl.phizic.rsa.senders.types;

/**
 * Отношение счета получателя к "нашему" банку - счет получателя в нашем \ в другом банке
 *
 * @author khudyakov
 * @ created 04.02.15
 * @ $Author$
 * @ $Revision$
 */
public enum BeneficiaryType
{
	OTHER_BANK,                                 //счет, на который осуществляется перевод, в другом банке
	SAME_BANK,                                  //счет, на который осуществляется перевод, в "нашем" банке
}
