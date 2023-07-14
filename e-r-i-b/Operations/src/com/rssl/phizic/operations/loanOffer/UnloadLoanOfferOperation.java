package com.rssl.phizic.operations.loanOffer;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.AbstractPaymentDocument;
import com.rssl.phizic.business.documents.GateExecutableDocument;
import com.rssl.phizic.common.types.Encodings;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.loan.UnloadConfig;
import com.rssl.phizic.operations.loanOffer.unloadOfferValue.UnloadOfferClaimOperationBase;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Moshenko
 * Date: 08.11.2011
 * Time: 11:58:00
 */
public class UnloadLoanOfferOperation extends UnloadOfferClaimOperationBase
{
	public UnloadLoanOfferOperation() throws BusinessException
	{
		super();
	}

	public List<? extends GateExecutableDocument>  getDataPack(Integer maxResults)
	{
		List<? extends GateExecutableDocument> partList = new ArrayList<AbstractPaymentDocument>();
		try
		{
			partList =  getLoan(this.startDate,this.endDate,ProductKind.LOAN_OFFER,maxResults);
		}
		catch (BusinessException e)
		{
			addReportError(GET_PART_ERROR,e);
		}
		return partList;
	}

	public String getFileName() throws BusinessException
	{
		return  getFileName(ProductKind.LOAN_OFFER.toValue());
	}

	public int getRepeatInterval()
	{
		return ConfigFactory.getConfig(UnloadConfig.class).getLoanOfferUnloadRepeatInterval();
	}

	public String getEncoding()
	{
		return Encodings.CP_1251;
	}
}
