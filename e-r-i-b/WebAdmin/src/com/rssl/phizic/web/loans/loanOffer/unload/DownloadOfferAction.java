package com.rssl.phizic.web.loans.loanOffer.unload;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.source.ExistingSource;
import com.rssl.phizic.business.documents.payments.validators.NullDocumentValidator;
import com.rssl.phizic.business.operations.restrictions.defaults.NullDocumentRestriction;
import com.rssl.phizic.common.types.Encodings;
import com.rssl.phizic.operations.loanOffer.ProductKind;
import com.rssl.phizic.operations.payment.ProcessDocumentOperation;
import com.rssl.phizic.web.actions.DownloadOperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: Gorshkov
 * Date: 16.07.2013
 * Time: 13:24:02
 * Экшен выгрузки заявок на кредитные продукты и виртуальные карты.
 * Заявки на вирт. карты выгружаются в xml, а все остальные - в csv, поэтому для вирт. карт выделено особое
 * поведение, но все это нужно делать в рамках одного (текущего) Action-а.
 */
public class DownloadOfferAction extends DownloadOperationalActionBase<UnloadClaimsData>
{
	private static final String CSV = ".csv";

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return download(mapping, form, request, response);
    }

	protected void outData(UnloadClaimsData data, ServletOutputStream outputStream) throws Exception
	{

		String engoding = data.getKind() == ProductKind.LOAN_CARD_OFFER ?  Encodings.UTF_8 : Encodings.CP_1251;

		if(data.getKind() == ProductKind.VIRTUAL_CARD) //заявки на виртуальные карты
		{
			try
			{
				if(data.getData().size() > 0)
					outputStream.write(data.getData().get(0).getBytes(engoding));
			}
			finally
			{
				if(outputStream != null)
					outputStream.close();

				executeClaims(data.getUnloadedIds());
			}
		}
		else //все остальные типы заявок
		{
			ZipOutputStream zipOutputStream = null;
			try
			{
				zipOutputStream = new ZipOutputStream(outputStream);
				zipOutputStream.putNextEntry(new ZipEntry(data.getData().get(data.getData().size() - 1) + CSV));
				for (int i = 0; i < data.getData().size() - 1; i++)
				{
					zipOutputStream.write(data.getData().get(i).getBytes(engoding));
					zipOutputStream.write("\n".getBytes(engoding));
				}
				zipOutputStream.flush();
			}
			finally
			{
				if (zipOutputStream != null)
					zipOutputStream.close();

				executeClaims(data.getUnloadedIds());
			}
		}
	}

	/**
	 * Переводит заявки в состояние EXECUTED
	 * @param ids ID-шники заявок
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	private void executeClaims(Set<Long> ids) throws BusinessException, BusinessLogicException
	{
		ProcessDocumentOperation operation = createOperation(ProcessDocumentOperation.class);
		for (Long id : ids)
		{
			ExistingSource source = new ExistingSource(id, new NullDocumentValidator());
			operation.setRestriction(NullDocumentRestriction.INSTANCE);
			operation.initialize(source);
			operation.adoptSystem();
		}
	}

}
