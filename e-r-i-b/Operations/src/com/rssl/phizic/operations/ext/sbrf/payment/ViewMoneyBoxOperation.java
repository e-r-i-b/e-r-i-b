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
 * Операция просмотра копилки
 */

public class ViewMoneyBoxOperation extends AutoSubscriptionOperationBase implements ViewEntityOperation
{

	private AutoSubscriptionLink link;                  //подписка или заявка
	private CardLink cardLink;                          //карта списания
	private AccountLink accountLink;                    //счет зачисления
	private String textUpdateSheduleItemsError;         //текст ошибки
	private boolean updateSheduleItemsError;            //признак ошибки
	private List<ScheduleItem> scheduleItems;           //список платежей по копилке
	private boolean hasConnectionToMB;                //Признак того, что указанная карта подключена к мобильному банку

	/**
	 *
	 * @param id идентификатор подписки-копилки или заявки на нее
	 * @param isLink - признак подписки
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
			throw new BusinessException("Ошибка при получении детальной информации по карте списания копилки");

		this.hasConnectionToMB = MobileBankManager.hasAnyMB(cardLink.getNumber());

		accountLink = link.getAccountLink();
		if (accountLink == null)
			throw new ResourceNotFoundBusinessException("Ошибка при получении детальной информации по счету зачисления копилки", AccountLink.class, "_For_MoneyBox");
		if (isLink)
		{
			try
			{
				List<ScheduleItem> scheduleReports = link.getSheduleReport(null, null);
				Collections.sort(scheduleReports, new ScheduleItemByDateComparator());
				//согласно РО должны возвращать последние 10 платежей для формы просмотра и печатной формы
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
				textUpdateSheduleItemsError = "Список платежей копилки временно недоступен. Повторите попытку позже.";
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
	 * возвращает 10 последних исполнений платежа копилки
	 *
	 * @return график исполнения.
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
