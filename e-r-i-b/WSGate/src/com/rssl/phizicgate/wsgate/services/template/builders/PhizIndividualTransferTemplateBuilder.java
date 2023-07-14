package com.rssl.phizicgate.wsgate.services.template.builders;

import com.rssl.common.forms.FormConstants;
import com.rssl.phizic.business.documents.payments.RurPayment;
import com.rssl.phizic.business.documents.templates.impl.TransferTemplateBase;
import com.rssl.phizic.business.ext.sbrf.payments.PaymentsFormatHelper;
import com.rssl.phizic.common.types.documents.FormType;

/**
 * Билдер шаблонов документов частному лицу
 *
 * @author gladishev
 * @ created 04.12.2014
 * @ $Author$
 * @ $Revision$
 */

public class PhizIndividualTransferTemplateBuilder extends IndividualTransferTemplateBuilder
{
	@Override
	protected TransferTemplateBase newInstance(String formName)
	{
		return super.newInstance(PaymentsFormatHelper.getActualRurPaymentForm(formName));
	}
}
