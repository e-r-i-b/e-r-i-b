package com.rssl.phizic.web.actions.payments;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.regions.Region;
import com.rssl.phizic.business.persons.LoginHelper;
import com.rssl.phizic.business.regions.RegionHelper;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ext.sbrf.payment.ListServicesPaymentSearchOperation;
import com.rssl.phizic.operations.payment.ListInvoicesOperation;
import com.rssl.phizic.utils.NumericUtil;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.struts.action.*;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Rydvanskiy
 * @ created 30.04.2010
 * @ $Author$
 * @ $Revision$
 * �������� "������� � ��������"
 */
public class IndexAction extends CatalogActionBase
{
	private static final List<String> paymentList = new ArrayList<String>();
	//������ ��������, ������� ����� ������ � ������
	static {
		paymentList.add("AccountClosingPayment");
		paymentList.add("AccountOpeningClaim");
		paymentList.add("AccountOpeningClaimWithClose");
		paymentList.add("BlockingCardClaim");
		paymentList.add("ConvertCurrencyPayment");
		paymentList.add("IMAPayment");
		paymentList.add("InternalPayment");
		paymentList.add("JurPayment");
		paymentList.add("LoanCardProduct");
		paymentList.add("LoanPayment");
		paymentList.add("LoanProduct");
		paymentList.add("LossPassbookApplication");
		paymentList.add("PFRStatementClaim");
		paymentList.add("RurPayment");
		paymentList.add("VirtualCardClaim");
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionForward forward = super.start(mapping, form, request, response);
		if (needFillInvoices())
		{
			fillInvoices((IndexForm) form, request);
		}
		return forward;

	}

	protected ActionForward search(IndexForm frm) throws Exception
	{
		ActionForward forward = super.search(frm);
		if (!isServiceContent(frm))
		{
			findRegions(frm);
		}
		return forward;
	}
	protected String getSearchResultQueryName(IndexForm frm)
	{
		if (isServiceContent(frm))
		{
			return super.getSearchResultQueryName(frm);
		}
		if (StringHelper.isEmpty(frm.getSearchType()))
			frm.setSearchType("byRegion");
		return "searchPaymentsResult";
	}

	private boolean isServiceContent(IndexForm frm)
	{
		return frm.getServiceId() != null && frm.getServiceId() > 0;
	}

	protected void fillSearchQuery(Query query, IndexForm frm) throws BusinessException
	{
		super.fillSearchQuery(query, frm);
		query.setParameter("orderType", frm.getSearchType());
		query.setParameterList("paymentList", paymentList);
		query.setParameter("loginId", LoginHelper.getCurrentUserLoginId().getId());
	}

	protected void fillSearchPagination(Query query, IndexForm frm)
	{
		if (isServiceContent(frm))
		{
			super.fillSearchPagination(query, frm);
			return;
		}
		// Paging
		int itemsPerPage = frm.getResultCount();
		//���������� ����������� ��������� (������/�����)
		if (frm.getPaginationType().equals("last"))
		{
			String[] pageList = getPageList(frm).split("\\|");
			int searchPage = frm.getSearchPage();
			itemsPerPage = NumericUtil.parseInt(pageList[searchPage]);
			frm.setProvCount(frm.getProvCount()- itemsPerPage);
		}
		else
		{
			frm.setProvCount(frm.getProvCount()+ frm.getProvCountInPage());
		}

		int provCount = frm.getProvCount();
		query.setMaxResults(itemsPerPage + 1);
		query.setFirstResult(0);
		if (provCount > 0)
			query.setFirstResult(provCount);
		//paging
	}

	protected String getPageList(IndexForm frm)
	{
		return frm.getPageList();
	}

	protected String getTopLevelServicesQueryName(IndexForm frm)
	{
		return "toplist.payments";
	}

	protected String getChanel()
	{
		return "WEB";
	}

	protected String[] getFunctionality(IndexForm frm) throws BusinessException
	{
		return new String[]{PAYMENTS_FUNCTIONALITY};
	}

	protected boolean needFillInvoices()
	{
		return true;
	}

	/**
	 * ��������� � ��������� ������ ��� ����� "����� � ������"
	 * @param form - form
	 * @param request - request
	 */
	public void fillInvoices(IndexForm form, HttpServletRequest request) throws BusinessException
	{
		//���� �������� ����������-������ �� ���������.
		if(!checkAccess(ListInvoicesOperation.class))
			return;

		ListInvoicesOperation operation = createOperation(ListInvoicesOperation.class);
		try
		{
			Set<String> errorCollector = new HashSet<String>();
			operation.initialize(errorCollector, BooleanUtils.isTrue(form.isShowInvoices()));
			if(!errorCollector.isEmpty())
			{
				ActionMessages msgs = new ActionMessages();
				for(String errorText: errorCollector)
					msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(errorText, false));
				saveMessages(request, msgs);
			}
		}
		catch (Exception ignored)
		{
			saveMessage(request, ListInvoicesOperation.DEFAULT_UNAVAILABLE_INVOICE_TEXT);
		}
		String successMessage =  form.getSuccessMessage();
		//� ������ ������/������������ ������� - ��� ������� �������� ���������� ��������� �� ������.
		if(StringHelper.isNotEmpty(successMessage))
			saveMessage(request, successMessage);
		form.setData(operation.getData());
		form.setSumValue(operation.getSumValue());
		form.setBannerText(operation.getBannerText());
		form.setAmountOfHiddenInvoices(operation.getHiddenInvoicesNumber());
	}

//-----������ �����
	//����� �������� ��� ��������� �����������
	protected void findRegions(IndexForm frm)  throws Exception
	{
		List<Object> searchRes = frm.getSearchResults();
		List<String> providers = new ArrayList<String>();
		//�������� ������ ��������� �����������(��� ����� ��������)
		for (Object res : searchRes)
		{
			Object[] res1 = (Object[]) res;
			if (res1[10].toString().equals("provider"))
				providers.add((String) res1[6]);
		}
		//���� ����� ������ ������� �������
		if (CollectionUtils.isEmpty(providers))
			return;

		frm.setRegions(getRegionList(providers));
	}

	protected Map<String, String[]> getRegionList(List<String> providers) throws Exception
	{
		ListServicesPaymentSearchOperation operation = createOperation("ListServicesPaymentSearchOperation", "RurPayJurSB");
		Query query = operation.createQuery("findRegions");
		query.setParameterList("providers", providers );
		List<Object[]> resultList = query.executeList();
		Map<String, String[]> regions = new HashMap<String, String[]>();
		//���� �������� �� ������� �������
		if (CollectionUtils.isEmpty(resultList))
			return regions;
		Region region = RegionHelper.getCurrentRegion();
		String personRegionName = (region == null || region.getName() == null) ? "" : region.getName();
		String  nameRegions = "";
		String providerId = "";
		String firstRegion = ""; //������, ������� ������������ � ����� �������
		boolean needComma = false; //����� �� ����������� ����� ���������
		for (Object[] result : resultList)
		{
			String currProviderId = (String) result[0];
			if (providerId.equals(""))
				providerId = currProviderId;
			if (!providerId.equals(currProviderId))
			{
				regions.put(providerId, new String[]{firstRegion, nameRegions});
				providerId = currProviderId;
				nameRegions = "";
				needComma = false;
				firstRegion = "";
			}
			if (needComma)
				nameRegions = nameRegions + ", ";
			if (firstRegion.equals(""))
			{
				firstRegion = (String) result[1];
			}
			else if (personRegionName.equals(result[1]))
			{
				nameRegions = "<a>"+firstRegion+"</a>, " + nameRegions;
				firstRegion = (String) result[1];
				needComma = false;
			}
			else
			{
				nameRegions = nameRegions + "<a>"+result[1]+"</a>";
				needComma = true;
			}
		}
		regions.put(providerId, new String[]{firstRegion, nameRegions.toString()});
		return regions;
	}

}
