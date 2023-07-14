package com.rssl.phizic.operations.ext.sbrf.payment;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.ermb.MobileBankManager;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.AutoSubscriptionLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.autopayments.ScheduleItemByDateComparator;
import com.rssl.phizic.gate.longoffer.autosubscription.AutoSubscription;
import com.rssl.phizic.gate.payments.longoffer.AutoSubscriptionDetailInfo;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.gate.autopayments.ScheduleItem;
import com.rssl.phizic.operations.autosubscription.AutoSubscriptionOperationBase;
import com.rssl.phizic.security.PersonConfirmManager;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * @author saharnova
 * @ created 01.10.14
 * @ $Author$
 * @ $Revision$
 * �������� ��������� �������
 */

public class ViewMoneyBoxOperation extends AutoSubscriptionOperationBase implements ViewEntityOperation
{

	private AutoSubscriptionLink link;                  //�������� ��� ������
	private CardLink cardLink;                          //����� ��������
	private AccountLink accountLink;                    //���� ����������
	private String textUpdateSheduleItemsError;         //����� ������
	private boolean updateSheduleItemsError;            //������� ������
	private List<ScheduleItem> scheduleItems;           //������ �������� �� �������
	private boolean hasConnectionToMB;                //������� ����, ��� ��������� ����� ���������� � ���������� �����

	/**
	 *
	 * @param id ������������� ��������-������� ��� ������ �� ���
	 * @param isLink - ������� ��������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void initialize(Long id, Boolean isLink) throws BusinessException, BusinessLogicException
	{
		super.initialize();
		updateSheduleItemsError = false;
		if (isLink)
		{
			link = getPersonData().getAutoSubscriptionLink(id);
		}
		else
		{
			BusinessDocumentService documentService = new BusinessDocumentService();
			link = new AutoSubscriptionLink(getPersonData().getLogin().getId(), (AutoSubscription) documentService.findById(id));
		}
		cardLink = link.getCardLink();
		if (cardLink == null)
			throw new BusinessException("������ ��� ��������� ��������� ���������� �� ����� �������� �������");

		this.hasConnectionToMB = MobileBankManager.hasAnyMB(cardLink.getNumber());

		accountLink = link.getAccountLink();
		if (accountLink == null)
			throw new ResourceNotFoundBusinessException("������ ��� ��������� ��������� ���������� �� ����� ���������� �������", AccountLink.class, "_For_MoneyBox");
		if (isLink)
		{
			try
			{
				List<ScheduleItem> scheduleReports = link.getSheduleReport(null, null);
				Collections.sort(scheduleReports, new ScheduleItemByDateComparator());
				//�������� �� ������ ���������� ��������� 10 �������� ��� ����� ��������� � �������� �����
				if (scheduleReports.size() > 10)
					scheduleItems = scheduleReports.subList(0, 10);
				else
					scheduleItems = scheduleReports;
			}
			catch (BusinessLogicException ex)
			{
				updateSheduleItemsError = true;
				textUpdateSheduleItemsError = ex.getMessage();
			}
			catch (BusinessException ex)
			{
				updateSheduleItemsError = true;
				textUpdateSheduleItemsError = "������ �������� ������� �������� ����������. ��������� ������� �����.";
			}
		}
		else
		{
			scheduleItems = Collections.EMPTY_LIST;
		}
	}

	public AutoSubscriptionLink getEntity() throws BusinessException, BusinessLogicException
	{
		return link;
	}

	public CardLink getCardLink()
	{
		return cardLink;
	}

	public AccountLink getAccountLink()
	{
		return accountLink;
	}

	/**
	 * ���������� 10 ��������� ���������� ������� �������
	 *
	 * @return ������ ����������.
	 */
	public List<ScheduleItem> getScheduleItems() throws BusinessException, BusinessLogicException
	{
		return Collections.unmodifiableList(scheduleItems);
	}

	public String getTextUpdateSheduleItemsError()
	{
		return textUpdateSheduleItemsError;
	}

	public boolean isUpdateSheduleItemsError()
	{
		return updateSheduleItemsError;
	}

	public boolean hasConnectionToMB()
	{
		return hasConnectionToMB;
	}
}
