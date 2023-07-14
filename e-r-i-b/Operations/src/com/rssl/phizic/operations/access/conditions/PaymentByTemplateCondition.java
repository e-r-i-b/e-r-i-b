package com.rssl.phizic.operations.access.conditions;

import com.rssl.phizic.auth.modes.StrategyCondition;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.documents.AbstractPaymentDocument;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.payments.BillingPaymentHelper;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.service.TemplateDocumentService;
import com.rssl.phizic.business.limits.LimitHelper;
import com.rssl.phizic.gate.payments.AccountToCardTransfer;
import com.rssl.phizic.gate.payments.ClientAccountsTransfer;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.security.util.MobileApiUtil;
import com.rssl.phizic.util.ApplicationUtil;
import com.rssl.phizic.utils.mobile.MobileAPIVersions;

import java.util.HashSet;
import java.util.Set;

/**
 * ������� �� ��, ��� ������ ��������� �� ������ �������������� �������
 * @author egorova
 * @ created 05.07.2010
 * @ $Author$
 * @ $Revision$
 *  ��������:
 *  1.	��������� �������� �� ����������� ���������� ������� �� ������ �����, ������� ���� (�������/�� �������� �����)
	2.	���������, ���� �������� ������ �� �������
		���, ���� �� ��� 3
		��:
		1. ��������� �����-�����, �����-�����?
			��
                1. ��������� ��������� � �������, ����� �� ������������ ����� ��������. ��������� �������� - - ����� ������������
				2. ��������� ���������� �������� �����. ���������� - ����� ������������
				3. ��������� ��������� ����� ������ � ������ �������. ��������� � n ��� - ����� ������������
				4. ����� ������������
		2.	��������� ����� ������: mAPI >= 5.00
			1.	��, ���� �� ��� 2.6.
		3.	��������� ������ �������, �� �������� ����������� ��������:
			1.	������ ������� ����� ��������
				1.	��, ���� �� ��� 2.7.
		4.	��������� ���������� ������ �� ������� ������ �����
			1.	��,  ����������� ����� ���������, ������ ������������� �� ����/���/.. ������������ (�����)
		5.	��������� ���� �������������� �������� �� �������� ���-�������
			1.	��, ������ ������������� �� ����/���/� ������������ (�����)
		6.	��������� ���������� �������� ����� �������� �� ������� �� ��������� ����� �������
			1.	��, ���������. ��������� ����� ������: mAPI?
				1.  ��, ������������� �� otp �� �����, ��������� � ���������� ����
				2.  ���, ������ ������������� �� ����/���/� ������������ (�����)
		7.	��������� ��������� �������� �����
			1.	��, �������� ��������, ������ ������������� �� ����/���/� ������������ (�����)
	3.	��������� ������ � �������� ��� ������ �� ��� ����� ��������:
		1.	��, ��������� ���� ���������� ������� �� ������ �����.
			1.	��, ������ ������������� �� ����/���/� ������������ (�����)
			2.	���, ��� ������������� �� ����/���/� (�����)
		2.	���, ��������� ������.
	4.	��������� ������ �� ������� ���������� �����
		1.	���, ���� �� ��� 5.
		2.	��, ������ ��. ��������� ����� ������: mAPI ����������������� ����?
			1.  ��, ���� �� ��� 5.
			2.  ���
				1.	��������� ���������� ����� �� ������
					1.	��, ����� ���������, ������ ������������� �� ����/���/.. ������������ (�����)
					2.	���, �� ���������, ������ ������������� �� ����/���/.. �� ������������ (�����)
	5.	���������� ������ ������������� �� ����/���/� (�����)

 */

public class PaymentByTemplateCondition implements StrategyCondition
{
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private static final Set<String> NOT_COMFIRMABLE_STATES = new HashSet<String>(); //������ ��������, ��� ������� �������� ������������� ��� ��� � ��..
	//������ ��� �������� ����� �� ���������� � ������ ��������� ����� ����������� �������� ������������ ��� ������������. ��� ��������� �������� - ������������� �����������.
	static
	{
		NOT_COMFIRMABLE_STATES.add("TEMPLATE");
		NOT_COMFIRMABLE_STATES.add("WAIT_CONFIRM_TEMPLATE");
	}

	/**
	 * ��������� ������������� �������� �������. �������� ������ ������������ �������� ��� ���.
	 * @param object - ������ ��� ��������
	 * @return true, ���� ������ ������ �������� � ��� ������������ �� ����, false - ������ ���������� ������������.
	 */
	public boolean checkCondition(ConfirmableObject object)
	{
		if (!(object instanceof BusinessDocument))
			return false;

		BusinessDocument document = (BusinessDocument) object;

		try
		{
			//1. ���� ��� ���������� ������ �� ������ �����, �� ����� ������ �� ������� ��� �������������.
			boolean within = !LimitHelper.needAdditionalConfirm(document);

			//2. ��������, ���� ������ ������ �� �������
			if (document.isByTemplate())
			{
				TemplateDocument template = TemplateDocumentService.getInstance().findById(document.getTemplateId());
				//���� ������ ��� ������ �� ������ �������, ������� ��� �����. ���� ���, �� ������ � ������� ������ ���� �����, ����� �� �������������� �������
				// (�.�. �������������� ������ ����� ��������� ��������.
				// (������ "��������� ������"): ���� ��������� �� ��������, �� ����� ������ ������������ �������� �� ���������.) , �� ������������.
				if (template == null || template.getState() == null || !NOT_COMFIRMABLE_STATES.contains(template.getState().getCode()))
					return preReturnAction(document, false);

				//2.1 ��� �������� ����� ������ ������� �����-�����, �����-�����
				if (template.getType() == ClientAccountsTransfer.class || template.getType() == AccountToCardTransfer.class)
				{
					 //���������� ��������� ������������� ���� "������������� �������� �� �������� ���-�������"
					 //2.1.1 ���� ���� ����������, �� ������ ������������ �� ���.
					if (checkConfirmSMSSetting(document, template))
						return preReturnAction(document, false);

					//2.1.2 ���� ���������� �������� ���������, �� ���������� ������������.
					if (!document.equalsKeyEssentials(template))
						return preReturnAction(document, false);

					//2.1.3 ���� ����� ������� ������������� �����, ��� � N(���������) ���, �� ���������� ������������ ������ �� ��� ��� �����
					if (!checkSumm(document, template))
						return preReturnAction(document, false);
					//2.1.4
					return preReturnAction(document, within);
				}

				// ���� ������ ����������� � ��(�������������). ��������� �� ���������� �������� ����������.
				if (template.getState().getCode().equals("TEMPLATE"))
				{
					return preReturnAction(document, document.equalsKeyEssentials(template));
				}


				//2.2 ��������� ����� ������
				if (!MobileApiUtil.isMobileApiGE(MobileAPIVersions.V5_00))
				{
					//2.4 ��������� ���������� ������ �� ������� ������ �����
					if (!within)
						return preReturnAction(document, false);

					//2.5 ��������� ���� �������������� �������� �� �������� ���-������� - ��������� � ��� ���������� ������������ ��� �������� �� ������� ���-�������
					if (checkConfirmSMSSetting(document, template))
						return preReturnAction(document, false);
				}

				//2.6 ���� ����� ������� ������������� �����, ��� � N(���������) ���, �� ���������� ������������ ������ �� ��� ��� �����
				if (!checkSumm(document, template))
					return preReturnAction(document, false);

				//2.7 ��������� ���������� �������� �����.
				return preReturnAction(document, document.equalsKeyEssentials(template));
			}
			//3. ��������� �������� �� ������ �������� � ����� ����������� ��������� ����� �� ������ ������ ��������.
			if (BillingPaymentHelper.isSelfMobileNumberPayment(document))
				return within;
			//4. ��������, ���� ������ ������ �� ������� ���������� �����
			if (!(new ServicePaymentLikeMobileTemplateCondition().checkCondition(document)) && !MobileApiUtil.isLimitedScheme())
				return within;
			//5. ���������� ������ �������������
			return preReturnAction(document, false);
		}
		catch (BusinessException e)
		{
			log.error(e.getMessage(), e);
			return preReturnAction(document, false);
		}
		catch(BusinessLogicException e)
		{
			log.error(e.getMessage(), e);
			return preReturnAction(document, false);
		}
	}

	/**
	 * ��������� ����� � �������.
	 * ���� ����� ������� ������������ ����� � ������� ����������� ������ ��� � factor (�������� � iccs.properties),
	 * �� ��������� ����� ������:
	 *      mAPI, ������������� �� otp �� �����, �� ���� ���������� ������ � ������� ����� ���������
	 *      �� mAPI, ����� �������� ���������� ������������ �� ���.
	 *
	 * @param document ��������.
	 * @param template ������.
	 * @return true ���� ������������ �� ����.
	 */
	private boolean checkSumm(BusinessDocument document, TemplateDocument template) throws BusinessException, BusinessLogicException
	{
		if (!(document instanceof AbstractPaymentDocument))
			return true;

		AbstractPaymentDocument payment = (AbstractPaymentDocument) document;
		boolean sumIncreasedOverLimit = DocumentHelper.checkPaymentByTemplateSum(document, template);
		payment.setSumIncreasedOverLimit(sumIncreasedOverLimit);
		//� mAPI ������������� �� otp �� �����
		return ApplicationUtil.isMobileApi() || !sumIncreasedOverLimit;
	}

	public String getWarning()
	{
		return null;
	}

    /**
     * ���� �������� �� ������ �������� � ��� ���� ������������ (checkCondition ������ false), �� � ������� ��������� ��������������� ����
     * @param document �������������� ��������
     * @param result ���������, ������������ ������� checkCondition
     * @return result
     */
    private boolean preReturnAction(BusinessDocument document, boolean result)
    {
        if (!result && document instanceof AbstractPaymentDocument)
            ((AbstractPaymentDocument)document).setPaymentFromTemplateNeedConfirm(true);
        return result;
    }

	/**
	 * �������� ��������� ������������� �������� �� ������� ���-�������
	 * @param document - ����������� ��������
	 * @return true - ������ �� ������� �� ��������������� � �� � ���������� ���������� ������������� �������� ���-�������.
	 * @throws BusinessException
	 */
	protected boolean checkConfirmSMSSetting(BusinessDocument document) throws BusinessException
	{
		if (document.isByTemplate())
		{
			TemplateDocument template = TemplateDocumentService.getInstance().findById(document.getTemplateId());
			if (template == null)
			{
				throw new BusinessException("�� ������ ������ id = " + document.getTemplateId());
			}

			return checkConfirmSMSSetting(document, template);
		}
		return false;
	}

	/**
	 * �������� ��������� ������������� �������� �� ������� ���-�������
	 * @param document - ����������� ��������
	 * @param template - ������
	 * @return true - ������ �� ������� �� ��������������� � �� � ���������� ���������� ������������� �������� ���-�������.
	 * @throws BusinessException
	 */
	protected boolean checkConfirmSMSSetting(BusinessDocument document, TemplateDocument template) throws BusinessException
	{
		//������������ ��� �������� �� ������� � ����� ���������� ����������, �����-�����, �����-����� ���-�������:
		if(template.getType() == ClientAccountsTransfer.class || template.getType() == AccountToCardTransfer.class)
		{
			//���� ��������� ���� ������������� ��� �������� �� ������� � ����� ���������� ����������, ����� � �����, ����� � ����� ��� � �������
			//�� ����� ���������� ������ ������������� �������� �� ������� ��� � ������컻, �� �������� ����� � �����, ����� � �����
			//������ �������������� ��� � �������.
			return DocumentHelper.getTemplateConfirmSetting((Department) document.getDepartment());
		}

		String templateState = template.getState().getCode();
		return DocumentHelper.getTemplateConfirmSetting((Department) document.getDepartment())
					&& ((templateState.equals("WAIT_CONFIRM_TEMPLATE") && (!MobileApiUtil.isMobileApiGE(MobileAPIVersions.V5_00))));
	}
}
