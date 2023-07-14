package com.rssl.phizic.business.payments.forms.meta.externalPayments;

import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.forms.ExtendedMetadata;
import com.rssl.phizic.business.shop.ExternalPaymentDataServiceSources;
import com.rssl.phizic.business.shop.FnsDataServiceSources;

/**
 * Загрузка данных для ФНС.
 *
 * @author Mescheryakova
 * @ created 02.03.2011
 * @ $Author$
 * @ $Revision$
 */

public class FnsMetadataLoader extends ExternalPaymentMetadataLoader
{
	@Override 
	protected void addDictionaries(FieldValuesSource source, BusinessDocument document, ExtendedMetadata newMetadata) throws BusinessException
	{
	}

	@Override 
	protected void addDictionariesNew(ExternalPaymentDataServiceSources source, FieldValuesSource fieldSource, ExtendedMetadata newMetadata) throws BusinessException
	{
	}

	@Override
	protected ExternalPaymentDataServiceSources getDataSource(String orderId) throws BusinessException
	{
		return new FnsDataServiceSources(orderId);
	}
}
