package com.rssl.phizic.operations.loanOffer;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.AbstractPaymentDocument;
import com.rssl.phizic.business.documents.GateExecutableDocument;
import com.rssl.phizic.common.types.Encodings;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.loan.UnloadConfig;
import com.rssl.phizic.operations.loanOffer.unloadOfferValue.UnloadOfferClaimOperationBase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * User: Moshenko
 * Date: 08.11.2011
 * Time: 12:26:48
 */
public class UnloadCardProductOperation extends UnloadOfferClaimOperationBase
{
	public UnloadCardProductOperation() throws BusinessException
	{
		super();
	}

	public List<? extends GateExecutableDocument> getDataPack(Integer maxResults)
	{
		List<? extends GateExecutableDocument> partList = new ArrayList<AbstractPaymentDocument>();
		try
		{
			partList =  getLoan(this.startDate,this.endDate,ProductKind.LOAN_CARD_PRODUCT,maxResults);
			List<? extends GateExecutableDocument> loanOfferClaimList = getLoan(this.startDate, this.endDate, ProductKind.LOAN_CARD_CLAIM, maxResults);
			partList.addAll(new ArrayList(loanOfferClaimList));
			Collections.sort(partList, new BusinessDocumentExecutionDateComporator());
		}
		catch (BusinessException e)
		{
			addReportError(GET_PART_ERROR,e);
		}
		return partList;
	}

	public String getFileName() throws BusinessException
	{
		return  getFileName(ProductKind.LOAN_CARD_PRODUCT.toValue());
	}

	public int getRepeatInterval()
	{
		return ConfigFactory.getConfig(UnloadConfig.class).getCardProductUnloadRepeatInterval();
	}

	public String getEncoding()
	{
		return Encodings.CP_1251;
	}
}
