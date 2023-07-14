package com.rssl.phizic.business.ermb.migration.list.operations;

import com.rssl.phizic.utils.StringHelper;

import java.util.*;

/**
 * @author Gulov
 * @ created 04.02.14
 * @ $Author$
 * @ $Revision$
 */

/**
 * Данные для отчета сотрудников call центра
 */
class ClientData
{
	private ClientInfo client;
	private List<ConflictingClientInfo> conflictingClients = new LinkedList<ConflictingClientInfo>();

	static class ClientInfo
	{
		private String lastName;
		private String firstName;
		private String middleName;
		private String document;
		private Calendar birthDate;

		String getLastName()
		{
			return lastName;
		}

		void setLastName(String lastName)
		{
			this.lastName = lastName;
		}

		String getFirstName()
		{
			return firstName;
		}

		void setFirstName(String firstName)
		{
			this.firstName = firstName;
		}

		String getMiddleName()
		{
			return middleName;
		}

		void setMiddleName(String middleName)
		{
			this.middleName = middleName;
		}

		Calendar getBirthDate()
		{
			return birthDate;
		}

		void setBirthDate(Calendar birthDate)
		{
			this.birthDate = birthDate;
		}

		String getDocument()
		{
			return document;
		}

		void setDocument(String document)
		{
			this.document = document;
		}
	}

	static class ConflictingClientInfo extends ClientInfo
	{
		private String phone;

		String getPhone()
		{
			return phone;
		}

		void setPhone(String phone)
		{
			this.phone = phone;
		}
	}

	ClientInfo getClient()
	{
		return client;
	}

	void setClient(ClientInfo client)
	{
		this.client = client;
	}

	List<ConflictingClientInfo> getConflictingClients()
	{
		return Collections.unmodifiableList(conflictingClients);
	}

	void addConflictingClient(ConflictingClientInfo conflictingClient)
	{
		conflictingClients.add(conflictingClient);
	}

	boolean hasConflict()
	{
		for (ConflictingClientInfo conflictingClientInfo : conflictingClients)
			if (StringHelper.isNotEmpty(conflictingClientInfo.getLastName()))
				return true;
		return false;
	}
}
