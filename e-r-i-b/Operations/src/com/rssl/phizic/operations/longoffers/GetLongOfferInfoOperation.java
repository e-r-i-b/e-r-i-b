package com.rssl.phizic.operations.longoffers;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.business.access.AccessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.resources.external.*;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.longoffer.LongOffer;
import com.rssl.phizic.gate.longoffer.LongOfferService;
import com.rssl.phizic.gate.longoffer.ScheduleItem;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author osminin
 * @ created 30.09.2010
 * @ $Author$
 * @ $Revision$
 */
public class GetLongOfferInfoOperation extends OperationBase
{
	private static LongOfferService longOfferService = GateSingleton.getFactory().service(LongOfferService.class);
	private static ExternalResourceService externalResourceService = new ExternalResourceService();
	private static final DepartmentService departmentService = new DepartmentService();

	private Calendar toDate;
	private Calendar fromDate;
	private LongOfferLink longOfferLink;

	private boolean isUseStoredResource;
	private Department currentDepartment;

	public void initialize(Long id) throws BusinessLogicException, BusinessException
	{
		toDate =  Calendar.getInstance();
		fromDate = DateHelper.toCalendar(DateHelper.add(toDate.getTime(),0,-6,0));
		longOfferLink = externalResourceService.findLinkById(LongOfferLink.class, id);
		if (longOfferLink == null)
		{
			throw new ResourceNotFoundBusinessException("ƒлительное поручение c id=" + id + " не найдено.", LongOfferLink.class);
		}
		Long loginId = longOfferLink.getLoginId();
		ActivePerson person = PersonContext.getPersonDataProvider().getPersonData().getPerson();
		if( !loginId.equals(person.getLogin().getId()) )
		{
			throw new AccessException("ƒлительное поручение c id=" + id + " не принадлежит пользователю");
		}
		Long departmentId = person.getDepartmentId();
		currentDepartment = departmentService.findById(departmentId);
	}

	//ќтображение записей за указанный период
	public List<ScheduleItem> getScheduleReport(Calendar fromDate, Calendar toDate) throws BusinessLogicException, BusinessException
	{
		try
		{
			LongOffer longOffer = longOfferLink.getValue();
			if (longOffer instanceof StoredLongOffer)
			{
				isUseStoredResource = true;
				return new ArrayList<ScheduleItem>();
			}

			return longOfferService.getSheduleReport(longOffer, fromDate, toDate);
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
		catch (GateLogicException e)
		{
			throw new BusinessLogicException(e);
		}
	}

	public LongOfferLink getLongOfferLink()
	{
		longOfferLink.updateLinkParameters(longOfferLink.getValue());
		return longOfferLink;
	}

	private CardLink getCardLink(String number, CommonLogin login) throws BusinessException
	{
		return externalResourceService.findLinkByNumber(login, ResourceType.CARD, number);
	}

	public String getCardName (String number, CommonLogin login) throws BusinessException
	{
		CardLink link = getCardLink(number, login);
		if (link == null)
			return "";
		return StringHelper.getEmptyIfNull(link.getName());
	}

	private AccountLink getAccountLink(String number, CommonLogin login) throws BusinessException
	{
		return externalResourceService.findLinkByNumber(login, ResourceType.ACCOUNT, number);
	}

	public String getAccountName (String number, CommonLogin login) throws BusinessException
	{
		AccountLink link = getAccountLink(number, login);
		if (link == null)
			return "";
		return StringHelper.getEmptyIfNull(link.getName());
	}

	/**
	 * ѕолучение линка на ресурс оплаты
	 * @return линк на ресурс оплаты (CardLink или AccountLink)
	 * @throws BusinessException
	 */
	public ExternalResourceLink getPayerResource() throws BusinessException
	{
		CommonLogin login = PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin();
		if (StringHelper.isNotEmpty(longOfferLink.getChargeOffAccount()))
			return getAccountLink(longOfferLink.getChargeOffAccount(), login);
		else if (StringHelper.isNotEmpty(longOfferLink.getChargeOffCard()))
			return getCardLink(longOfferLink.getChargeOffCard(), login);
		return null;
	}

	/**
	 * ¬озвращает текущую дату
	 * @return
	 */
	public Calendar getToDate()
	{
		return toDate;
	}

	/**
	 * ¬озвращает минус 6 мес€цев от текущей даты
	 * @return
	 */
	public Calendar getFromDate()
	{
		return fromDate;
	}

	public boolean isUseStoredResource()
	{
		return isUseStoredResource;
	}

	public Department getCurrentDepartment()
	{
		return currentDepartment;
	}

	public Department getTopLevelDepartment() throws BusinessException
	{
		return departmentService.getTB(currentDepartment);
	}
}
