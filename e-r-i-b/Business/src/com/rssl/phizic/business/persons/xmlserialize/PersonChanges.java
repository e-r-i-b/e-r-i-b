package com.rssl.phizic.business.persons.xmlserialize;

import com.rssl.phizic.business.dictionaries.PaymentReceiverBase;
import com.rssl.phizic.business.dictionaries.PaymentReceiverJur;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.ext.sbrf.receivers.PaymentReceiverPhizSBRF;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Omeliyanchuk
 * Date: 27.09.2006
 * Time: 17:22:56
 * To change this template use File | Settings | File Templates.
 */
public class PersonChanges
{
	List<String> accountAdded =new ArrayList<String>();
	List<String> accountDeleted =new ArrayList<String>();
	List<String> feeAccountAdded =new ArrayList<String>();
	List<String> feeAccountDeleted =new ArrayList<String>();
	List<String> empoweredPersonAdded =new ArrayList<String>();
	List<String> empoweredPersonModified =new ArrayList<String>();
	//ActivePerson заполняется не полностью, гарантировано те данные которые нужны для отключения.
	List<ActivePerson> empoweredPersonDeleted =new ArrayList<ActivePerson>();
	List<PaymentReceiverBase> receiverAdded = new ArrayList<PaymentReceiverBase>();
	List<PaymentReceiverBase> receiverDeleted = new ArrayList<PaymentReceiverBase>();
	//оставляем для CODRequestHelper
	List<PaymentReceiverJur> jurReceiverAdded =new ArrayList<PaymentReceiverJur>();
	List<PaymentReceiverJur> jurReceiverDeleted =new ArrayList<PaymentReceiverJur>();
	List<PaymentReceiverPhizSBRF> phizReceiverAdded =new ArrayList<PaymentReceiverPhizSBRF>();
	List<PaymentReceiverPhizSBRF> phizReceiverDeleted =new ArrayList<PaymentReceiverPhizSBRF>();

	String oldMobilePhone;
	String newMobilePhone;
	Boolean isChanged = false;

	public Boolean getIsChanged()
	{
		return isChanged;
	}

	public void setIsChanged()
	{
		this.isChanged = true;
	}

	public List<String> getEmpoweredPersonModified()
	{
		return empoweredPersonModified;
	}

	public void addEmpoweredPersonModified(String clientId)
	{
		setIsChanged();
		 empoweredPersonModified.add(clientId);
	}

	public List<ActivePerson> getEmpoweredPersonDeleted()
	{
		return empoweredPersonDeleted;
	}

	public void addEmpoweredPersonDeleted(ActivePerson person)
	{
		setIsChanged();
		empoweredPersonDeleted.add(person);
	}

	public List<String> getEmpoweredPersonAdded()
	{
		return empoweredPersonAdded;
	}

	public void addEmpoweredPersonAdded(String clientId)
	{
		setIsChanged();
		 empoweredPersonAdded.add(clientId);
	}

	public List<String> getAccountAdded()
	{
		return accountAdded;
	}

	public void addAccountToAdd(String accountNum)
	{
		setIsChanged();
		 accountAdded.add(accountNum);
	}

	public List<String> getAccountDeleted()
	{
		return accountDeleted;
	}

	public void addAccountToDelete(String accountNum)
	{
		setIsChanged();
		 accountDeleted.add(accountNum);
	}

	public List<String> getFeeAccountAdded()
	{
		return feeAccountAdded;
	}

	public void addFeeAccountToAdd(String accountNum)
	{
		setIsChanged();
		 feeAccountAdded.add(accountNum);
	}

	public List<String> getFeeAccountDeleted()
	{
		return feeAccountDeleted;
	}

	public void addFeeAccountToDelete(String accountNum)
	{
		setIsChanged();
		 feeAccountDeleted.add(accountNum);
	}

		public List<PaymentReceiverBase> getReceiverAdded()
	{
		return receiverAdded;
	}

	public void addReceiverToAdd(PaymentReceiverBase receiver)
	{
		setIsChanged();
		receiverAdded.add(receiver);
	}

	public List<PaymentReceiverBase> getReceiverDeleted()
	{
		return receiverDeleted;
	}

	public void addReceiverToDelete(PaymentReceiverBase receiver)
	{
		setIsChanged();
		receiverDeleted.add(receiver);
	}

	public String getOldMobilePhone()
	{
		return oldMobilePhone;
	}

	public void setOldMobilePhone(String oldMobilePhone)
	{
		this.oldMobilePhone = oldMobilePhone;
		setIsChanged();
	}

	public String getNewMobilePhone()
	{
		return newMobilePhone;
	}

	public void setNewMobilePhone(String newMobilePhone)
	{
		this.newMobilePhone = newMobilePhone;
		setIsChanged();
	}
}
