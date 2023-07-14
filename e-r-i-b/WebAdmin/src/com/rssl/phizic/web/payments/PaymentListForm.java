package com.rssl.phizic.web.payments;

import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.web.claims.ClaimListForm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Egorova
 * @ created 11.06.2008
 * @ $Author$
 * @ $Revision$
 */
public class PaymentListForm extends ClaimListForm
{
	private List<BusinessDocument> payments = new ArrayList<BusinessDocument>();
	private List forms;

	/**
	 * @return ������ ��������
	 */
	public List<BusinessDocument> getPayments()
	{
		return Collections.unmodifiableList(payments);
	}

	/**
	 * @param payments ������ ��������
	 */
	public void setPayments(List<BusinessDocument> payments)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.payments = payments;
	}

	/**
	 * @return ������ ���� (�����) ��������
	 */
	public List getForms()
	{
		return forms;
	}

	/**
     * @return ������ ���� ����
     */
	public void setForms(List forms)
	{
		this.forms = forms;
	}
}
