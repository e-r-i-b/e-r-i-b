package com.rssl.phizic.rsa.senders.types;

/**
 * Определяет направление, в котором средства передаются
 *
 * @author khudyakov
 * @ created 04.02.15
 * @ $Author$
 * @ $Revision$
 */
public enum  DirectionTransferFundsType
{
	ME_TO_YOU,                                  //счет, на который осуществляется перевод, принадлежит конрагенту
	ME_TO_ME,                                   //счет, на который осуществляется перевод, принадлежит тому же клиенту
}
