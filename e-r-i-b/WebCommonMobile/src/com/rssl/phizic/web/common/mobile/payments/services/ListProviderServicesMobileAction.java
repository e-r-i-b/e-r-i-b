package com.rssl.phizic.web.common.mobile.payments.services;

import com.rssl.phizic.operations.ext.sbrf.payment.ListProviderServicesMobileOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ��������� ������ ����� (�����������) ����������.
 * @author Dorzhinov
 * @ created 20.01.2012
 * @ $Author$
 * @ $Revision$
 */
public class ListProviderServicesMobileAction extends OperationalActionBase
{
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		//TODO ��� ����������� BUG078963 � ��� mAPI, ������������� �� ListProviderServicesAPIActionBase
		ListProviderServicesMobileOperation operation = createOperation("ListProviderServicesMobileOperation", "RurPayJurSB");
		ListProviderServicesMobileForm frm = (ListProviderServicesMobileForm) form;

		frm.setServiders(operation.getList(frm.getId()));
		
		return mapping.findForward(FORWARD_START);
	}
}
