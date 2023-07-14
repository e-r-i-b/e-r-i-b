package com.rssl.phizic.operations.person;

import com.rssl.phizic.gate.csa.UserInfo;
import com.rssl.phizic.auth.pin.PINEnvelope;
import com.rssl.phizic.auth.pin.PINService;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.clients.list.ClientInformationService;
import com.rssl.phizic.business.clients.list.ClientNodeState;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.dictionaries.MultiInstancePaymentReceiverService;
import com.rssl.phizic.business.dictionaries.PaymentReceiverBase;
import com.rssl.phizic.business.dictionaries.PaymentReceiverJur;
import com.rssl.phizic.business.ext.sbrf.receivers.PaymentReceiverPhizSBRF;
import com.rssl.phizic.business.csa.IncognitoService;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.MultiInstanceExternalResourceService;
import com.rssl.phizic.business.resources.external.PaymentSystemIdLink;
import com.rssl.phizic.business.resources.own.MultiInstanceSchemeOwnService;
import com.rssl.phizic.business.services.Service;
import com.rssl.phizic.business.services.ServiceService;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.context.EmployeeData;
import com.rssl.phizic.gate.clients.IncognitoState;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import org.apache.commons.lang.BooleanUtils;

import java.util.List;

/**
 * @author Roshka
 * @ created 08.09.2006
 * @ $Author$
 * @ $Revision$
 */

public abstract class PersonExtendedInfoOperationBase extends PersonOperationBase
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final MultiInstanceExternalResourceService externalResourceService = new MultiInstanceExternalResourceService();
    private final static ServiceService serviceService = new ServiceService();
	private static final ClientInformationService clientInformationService = new ClientInformationService();
	private List<AccountLink>  accounts;
	private List<CardLink>     cards;
	protected Department         department;
    protected final static MultiInstanceSchemeOwnService schemeOwnService = new MultiInstanceSchemeOwnService();
    private final static MultiInstancePaymentReceiverService paymentReceiverService = new MultiInstancePaymentReceiverService();
    protected static final PINService pinService = new PINService();

    public List<AccountLink> getAccountLinks () throws BusinessException, BusinessLogicException
    {
        if (accounts==null)
        {
            accounts = loadAccountLinks(getPerson());
        }
        //noinspection ReturnOfCollectionOrArrayField
        return accounts;
    }

	protected List<AccountLink> loadAccountLinks(ActivePerson person) throws BusinessException, BusinessLogicException
	{
		return externalResourceService.getLinks(person.getLogin(), AccountLink.class,getInstanceName());
	}

	public List<CardLink> getCardLinks() throws BusinessException, BusinessLogicException
	{
		if (cards == null)
		{
			cards = loadCardLinks(getPerson());
		}
		//noinspection ReturnOfCollectionOrArrayField
		return cards;
	}

	protected List<CardLink> loadCardLinks(ActivePerson person)
			throws BusinessException, BusinessLogicException
	{
		return externalResourceService.getLinks(person.getLogin(), CardLink.class,getInstanceName());
	}

	public Department getDepartment() throws BusinessException
	{
		if(department == null)
		{
			Long departmentId;
			if (getPerson() == null)
			{
				departmentId = EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee().getDepartmentId();
			}
			else
			{
				departmentId = getPerson().getDepartmentId();
			}
			department = departmentService.findById(departmentId, getInstanceName());
		}
		return department;
	}

	/**
	 * Получить доверителя
	 * @return
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public ActivePerson getPrincipalPerson() throws BusinessException
	{
		return (ActivePerson) personService.findById(getPerson().getTrustingPersonId(), getInstanceName());
	}

    /**
     * @return Список услуг клиента
     * @throws com.rssl.phizic.business.BusinessException
     */
    public List<Service> getServices () throws BusinessException
    {
        return serviceService.getPersonServices(getPerson());
    }

	public List<PaymentReceiverBase> getPaymentReceivers() throws BusinessException
	{
		return paymentReceiverService.findBaseListReceiver(getPerson().getLogin().getId(),getInstanceName());
	}

    public List<PaymentReceiverJur> getPaymentReceiversJur () throws BusinessException
    {
        return paymentReceiverService.findListReceiver(PaymentReceiverJur.class, getPerson().getLogin().getId(),getInstanceName());
    }

    public List<PaymentReceiverPhizSBRF> getPaymentReceiversPhizSBRF () throws BusinessException
    {
        return paymentReceiverService.findListReceiver(PaymentReceiverPhizSBRF.class, getPerson().getLogin().getId(),getInstanceName());
    }

	public String getGorodCardNumber() throws BusinessException, BusinessLogicException
	{
		List<PaymentSystemIdLink> list = externalResourceService.getLinks(getPerson().getLogin(), PaymentSystemIdLink.class, getInstanceName());
		if(list != null && list.size() > 0)
			return list.get(0).getValue();
		return null;
	}
    /**
     *  Получить текущий pin конверт.
     */
    @Transactional
    public PINEnvelope getCurrentLinkedPINEnvelope()
    {
        return PrintPersonOperation.pinService.findEnvelope(getPerson().getLogin().getUserId());
    }

	/**
	 * Получение настройки приватности клиента
	 * @return
	 * @throws BusinessException
	 */
	public IncognitoState getClientIncognito()
	{
		try
		{
			return BooleanUtils.isNotFalse(IncognitoService.getIncognitoSetting(getPerson()))? IncognitoState.incognito : IncognitoState.publicly;
		}
		catch (Exception e)
		{
			log.error("Ошибка получения признака инкогнито для пользователя " + getPerson().getId(), e);
			return null;
		}
	}
	/**
	 * @return состояние клиента
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public ClientNodeState getClientNodeState() throws BusinessException, BusinessLogicException
	{
		ActivePerson person = getPerson();
		EmployeeData employeeData = EmployeeContext.getEmployeeDataProvider().getEmployeeData();
		Long clientId = person.getId();
		employeeData.setCurrentClientId(clientId);
		ClientNodeState nodeState = employeeData.getCurrentClientNodeState();
		if (nodeState != null)
			return nodeState;

		UserInfo userInfo = PersonHelper.buildUserInfo(person);
		return employeeData.setCurrentClientNodeState( clientInformationService.getClientNodeState(userInfo));
	}
}