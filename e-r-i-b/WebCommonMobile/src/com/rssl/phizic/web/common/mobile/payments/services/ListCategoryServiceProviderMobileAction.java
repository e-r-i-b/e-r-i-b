package com.rssl.phizic.web.common.mobile.payments.services;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.payment.services.CategoryServiceType;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.security.util.MobileApiUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.mobile.MobileAPIVersions;
import com.rssl.phizic.web.actions.payments.IndexForm;
import com.rssl.phizic.web.actions.payments.catalog.ApiCatalogActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;

import java.util.LinkedHashSet;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 1. ��� mAPI >= 5.20
 * ��������� ������ ��������� �����
 * ��� forward �� ������ ����������� ��������� �����
 * 2. ��� mAPI < 5.20
 * ��������� ������ ��������� �����
 * ��� �������� �� ������ ����� (����� �����) ���������
 * ��� �������� �� ������ �������� ������ (������ �����) ���� ������ ����������� ������.
 * @author Dorzhinov
 * @ created 12.01.2012
 * @ $Author$
 * @ $Revision$
 * /private/dictionary/servicesPayments
 */
public class ListCategoryServiceProviderMobileAction extends ApiCatalogActionBase
{
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListCategoryMobileForm frm = (ListCategoryMobileForm) form;
		if (MobileApiUtil.isMobileApiLT(MobileAPIVersions.V5_20))
		{
			return startLT52(mapping, frm);
		}
		return super.start(mapping, form, request, response);
	}

	protected boolean getFinalDescendants(IndexForm form)
	{
		ListCategoryMobileForm frm = (ListCategoryMobileForm) form;
		return !(MobileApiUtil.isMobileApiGE(MobileAPIVersions.V6_00) && frm.isIncludeServices());
	}

	protected String getChanel()
	{
		return "MAPI";
	}

//����� ������ ������
	private static final String CATEGORY_PAYMENTS_URL = "/private/payments/category.do";
	private static final String SERVICES_PAYMENTS_URL = "/private/payments/servicesPayments.do";

	private static final String SUPPORTED_PAYMENT_CATEGORIES = "supportedPaymentCategories";

	private static final String FORWARD_START_LT_52 = "StartLT52";

	private ActionForward startLT52(ActionMapping mapping, ListCategoryMobileForm frm) throws BusinessException
	{
		String idS = frm.getId();
		//��������� ��������� � mobileApi: paginationSize � paginationOffset, � � �������� ������: currentPage � itemsPerPage,
		//������� ��������� ��������������, ��� ���� ���������������, ��� offset ������ ������ size.
		int paginationSize = frm.getPaginationSize();
		int paginationOffset = frm.getPaginationOffset();
		int itemsPerPage = paginationSize != 0 ? paginationSize : (Integer.MAX_VALUE - 1);
		int currentPage = paginationSize != 0 ? paginationOffset / paginationSize : 0;

		String paymentCategoiesString = currentServletContext().getInitParameter(SUPPORTED_PAYMENT_CATEGORIES);
		if (StringHelper.isEmpty(paymentCategoiesString))
			throw new BusinessException("�� ����� �������� " + SUPPORTED_PAYMENT_CATEGORIES);
		Set<CategoryServiceType> paymentCategories = new LinkedHashSet<CategoryServiceType>();
		for (String categoryName : paymentCategoiesString.split(","))
			paymentCategories.add(CategoryServiceType.fromValue(categoryName));

		// ���� ����� => ���������� ������ ���������
		if (StringHelper.isEmpty(idS) || idS.trim().length() == 0)
		{
			frm.setPaymentCategories(paymentCategories);
			return mapping.findForward(FORWARD_START_LT_52);
		}

		// ���� ����� ���� ���� ���-�� ���, �� �������, ��� ��� ��������� ��� ���������
		// => �������� �� ���� � �������� ������ ���������
		if (!DIGIT_PATTERN.matcher(idS.trim()).matches())
		{
			//��������� ������������ �� ������� ������ API ������ ��������� ��������
			if (!paymentCategories.contains(CategoryServiceType.fromValue(idS)))
				throw new BusinessException("�������� ������������ ���������");

			ActionRedirect redirect = new ActionRedirect(CATEGORY_PAYMENTS_URL);
			redirect.addParameter("categoryId", idS);
			redirect.addParameter("itemsPerPage", itemsPerPage);
			redirect.addParameter("currentPage", currentPage);
			return redirect;
		}
		// ���� ������ �����, �� �������, ��� ��� id ������
		// => �������� �� ���� � ������������ ������ ������
		else
		{
			ActionRedirect redirect = new ActionRedirect(SERVICES_PAYMENTS_URL);
			redirect.addParameter("serviceId", idS);
			redirect.addParameter("itemsPerPage", itemsPerPage);
			redirect.addParameter("currentPage", currentPage);
			return redirect;
		}
	}
}
