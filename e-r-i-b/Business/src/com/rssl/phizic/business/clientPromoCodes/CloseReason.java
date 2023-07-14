package com.rssl.phizic.business.clientPromoCodes;

/**
 * Причина перехода промо – кода в статус «недействующий»
 * @author sergunin
 * @ created 16.12.14
 * @ $Author$
 * @ $Revision$
 */

public enum CloseReason
{
     DELETED_BY_CLIENT,
     ACTION_PROMO_CODE_EXPIRED,
     REACH_USE_LIMIT;
}
