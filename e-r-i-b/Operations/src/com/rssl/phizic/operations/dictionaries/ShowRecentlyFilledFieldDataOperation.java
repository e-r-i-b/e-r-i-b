package com.rssl.phizic.operations.dictionaries;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.StaticPersonData;
import com.rssl.phizic.business.profile.addressbook.AddressBookService;
import com.rssl.phizic.business.profile.addressbook.Contact;
import com.rssl.phizic.business.profile.addressbook.ContactStatus;
import com.rssl.phizic.common.types.BusinessFieldSubType;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @ author: Vagin
 * @ created: 23.07.2013
 * @ $Author
 * @ $Revision
 * ќпераци€ получени€ справочника доверенных получателей
 */
public class ShowRecentlyFilledFieldDataOperation extends OperationBase implements ListEntitiesOperation
{
	private static final AddressBookService service = new AddressBookService();
	private List<Contact> contacts;
	private List<String> selfPhones;

	/**
	 * »нициализаци€ операции
	 * @param subType - тип пол€ по которому будет получен справочник
	 * @param login - идентификатор клиента в рамках котолрого будет осуществлен поиск
	 * @throws BusinessException
	 */
	public void initialize(Login login, BusinessFieldSubType subType) throws BusinessException, BusinessLogicException
	{
		if (subType == null)
			throw new BusinessException("Ќеверно указан тип запрашиваемых данных из справочника доверенных получателей.");

		if (subType != BusinessFieldSubType.phone)
		{
			return;
		}
		contacts = new LinkedList<Contact>(service.getAllClientContacts(login.getId()));
		Iterator<Contact> iter = contacts.iterator();
		while (iter.hasNext()) {
			Contact c = iter.next();
			if (
					(c.getFrequencyPay() > 0 || c.getFrequencypP2P() > 0)
					&& c.isTrusted()
					&& c.getStatus() != ContactStatus.DELETE
				)
			{
				continue;
			}

			iter.remove();
		}
	}

    /**
     * »нициализаци€ операции
     * @param subType - тип пол€ по которому будет получен справочник
     * @param login - идентификатор клиента в рамках котолрого будет осуществлен поиск
     * @throws BusinessException
     */
    public void initializeSelf(Login login, BusinessFieldSubType subType) throws BusinessException, BusinessLogicException
    {
	    if (subType == null)
            throw new BusinessException("Ќеверно указан тип запрашиваемых данных из справочника доверенных получателей.");

	    if (subType != BusinessFieldSubType.phone)
        {
            return;
        }

	    selfPhones = new LinkedList<String>(StaticPersonData.getPhones(login, false));
    }

	public List<Contact> getContacts()
	{
		return contacts;
	}

	public List<String> getSelfPhones()
	{
		return selfPhones;
	}
}
