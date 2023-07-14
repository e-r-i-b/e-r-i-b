package com.rssl.phizic.operations.basket;

import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.phizic.business.documents.AbstractAccountsTransfer;
import com.rssl.phizic.business.util.MaskingInfo;
import com.rssl.phizic.operations.forms.ViewDocumentOperation;

import java.util.Collections;

/**
 * Операция просмотра интернет заказа В СОТРУДНИКЕ
 * @author niculichev
 * @ created 21.08.15
 * @ $Author$
 * @ $Revision$
 */
public class ViewShopOrderOperation extends ViewDocumentOperation
{
	protected MaskingInfo getMaskingInfo(FieldValuesSource fieldValuesSource)
	{
		return new MaskingInfo(metadata, fieldValuesSource, Collections.<String>emptyList());
	}

	/**
	 * @return имя получателя
	 */
	public String getReceiverName()
	{
		return ((AbstractAccountsTransfer) document).getReceiverName();
	}
}
