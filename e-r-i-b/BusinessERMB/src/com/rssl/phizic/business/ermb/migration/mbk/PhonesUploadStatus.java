package com.rssl.phizic.business.ermb.migration.mbk;

/**
 * Статус аплоудера, выгружающего ЕРМБ-телефоны в МБК
 * @author Rtischeva
 * @ created 11.07.14
 * @ $Author$
 * @ $Revision$
 */
public enum PhonesUploadStatus
{
	MORE,   //продолжать выгружать телефоны

	ENOUGH  //не выгружать телефоны
}
