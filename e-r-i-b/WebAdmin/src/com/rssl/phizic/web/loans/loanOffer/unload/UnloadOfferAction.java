package com.rssl.phizic.web.loans.loanOffer.unload;

import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.operations.loanOffer.*;
import com.rssl.phizic.operations.loanOffer.unloadOfferValue.UnloadOfferClaimOperationBase;
import com.rssl.phizic.operations.tasks.UnloadPereodicalTaskOperationBase;
import com.rssl.phizic.operations.virtualcards.unload.UnloadVirtualCardClaimOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.actions.UnloadOperationalActionBase;
import org.apache.struts.action.*;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: Moshenko
 * Date: 17.06.2011
 * Time: 11:44:02
 * Экшен выгрузки заявок на кредитные продукты и виртуальные карты.
 * Заявки на вирт. карты выгружаются в xml, а все остальные - в csv, поэтому для вирт. карт выделено особое
 * поведение, но все это нужно делать в рамках одного (текущего) Action-а.
 */
public class UnloadOfferAction extends UnloadOperationalActionBase<UnloadClaimsData>
{
	private static final String FORWARD_REPORT = "Report";
	private static final String ZIP = ".zip";

	public Pair<String, UnloadClaimsData> createData(ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{

		UnloadOfferForm frm = (UnloadOfferForm) form;
		MapValuesSource mapValuesSource = new MapValuesSource(frm.getFields());

		FormProcessor<ActionMessages, ?> formProcessor = createFormProcessor(mapValuesSource, UnloadOfferForm.createForm());

		if(!formProcessor.process())
		{
			saveErrors(request, formProcessor.getErrors());
			return null;
		}

		Map<String, Object> result = formProcessor.getResult();
		Calendar fromDate = createCalendar(result.get("fromDate"), result.get("fromTime"));
		if (fromDate != null)
			fromDate.set(Calendar.MILLISECOND, 0);
		Calendar toDate = createCalendar(result.get("toDate"), result.get("toTime"));
		if (toDate != null)
			toDate.set(Calendar.MILLISECOND, 999);

		String type = (String) result.get("type");
		ProductKind kind = ProductKind.valueOf(type);

		switch (kind)
		{
			case VIRTUAL_CARD:
				UnloadVirtualCardClaimOperation operation = createOperation(UnloadVirtualCardClaimOperation.class);
				operation.manualInitialize(fromDate, toDate);
				Pair<String, Set<Long>> document = operation.getFullDocument(operation.getDataPack(null));

				if (isClaimListEmpty(operation, request))
					return null;

				Long successCount = operation.getResult().getSuccesslResultCount();
				frm.setUnloadCount(successCount);

				UnloadClaimsData data = new UnloadClaimsData(kind, document.getSecond(), new ArrayList<String>(Arrays.asList(document.getFirst())));
				return new Pair<String, UnloadClaimsData>(operation.getFileName(), data);
			default:
				return processOfferClaimOperation(kind, fromDate, toDate, request, frm);
		}
	}

	private  Pair<String, UnloadClaimsData> processOfferClaimOperation(ProductKind kind, Calendar fromDate,Calendar toDate,HttpServletRequest request,UnloadOfferForm frm) throws Exception
	{
		Class<? extends UnloadOfferClaimOperationBase> clazz;
		switch (kind)
		{
			case LOAN_CARD_OFFER:
				clazz = UnloadCardOfferOperation.class;
				break;
			case LOAN_CARD_PRODUCT:
				clazz = UnloadCardProductOperation.class;
				break;
			case LOAN_OFFER:
				clazz = UnloadLoanOfferOperation.class;
				break;
			case LOAN_PRODUCT:
				clazz = UnloadLoanProductOperation.class;
				break;
			default:
				return null;
		}
		UnloadOfferClaimOperationBase operation = createOperation(clazz);
		operation.manualInitialize(fromDate, toDate);
		Pair<ArrayList<String>,Set<Long>> documentPair  = operation.getUnloadedDataList(operation.getDataPack(null));
		ArrayList<String>  document =  documentPair.getFirst();
		Long errCount = operation.getResult().getTotalResultCount() - operation.getResult().getSuccesslResultCount();
		boolean	claimEmpty =  isClaimListEmpty(operation,request);
		/*нет выгруженные и ошибок*/
		if (claimEmpty && errCount == 0) return null;
		/*нет выгруженные но есть ошибки*/
		else if (claimEmpty && errCount != 0)
		{
			frm.setUnloadCount(0L);
			frm.setErrorsCount(errCount);
			frm.setErrors(operation.getErrors());
			return null;
		}
		/*есть выгруженные и возможно есть ошибки*/
		else
		{
			frm.setUnloadCount(operation.getResult().getSuccesslResultCount());
			frm.setErrorsCount(errCount);
			frm.setErrors(operation.getErrors());
			String archName = document.get(document.size()-1) + ZIP;
			UnloadClaimsData data = new UnloadClaimsData(kind, documentPair.getSecond(), document);
			return new Pair<String, UnloadClaimsData>(archName, data);
		}
	}

	private boolean isClaimListEmpty(UnloadPereodicalTaskOperationBase operation, HttpServletRequest request)
	{
		if(operation.getResult().getSuccesslResultCount()<1)
		{
			ActionMessages msgs = new ActionMessages();
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("За заданный период нет файлов для выгрузки.", false));
			saveErrors(request, msgs);
			return true;
		}
		return false;
	}

	public ActionForward actionAfterUnload(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		UnloadOfferForm frm = (UnloadOfferForm) form;
		if (frm.getUnloadCount() == 0 && frm.getErrorsCount() == 0)
			return mapping.findForward(FORWARD_START);
		return mapping.findForward(FORWARD_REPORT);
	}

	/**
	 объедениям время и дату 
	 * @param date
	 * @param time
	 * @return
	 * @throws BusinessException
	 */
	private Calendar createCalendar(Object date, Object time) throws BusinessException
	{
		Calendar dateCalendar;
		Calendar timeCalendar;

		dateCalendar = DateHelper.toCalendar((Date) (date));
		timeCalendar = DateHelper.toCalendar((Date) (time));

		if (time != null)
		{
			dateCalendar.set(Calendar.HOUR_OF_DAY, timeCalendar.get(Calendar.HOUR_OF_DAY));
			dateCalendar.set(Calendar.MINUTE, timeCalendar.get(Calendar.MINUTE));
			dateCalendar.set(Calendar.SECOND, timeCalendar.get(Calendar.SECOND));
		}
		return dateCalendar;
	}

}
