package com.rssl.phizic.business.ermb.disconnector;

import com.rssl.phizic.business.ermb.migration.onthefly.fpp.DepartmentIdentity;
import com.rssl.phizic.common.types.annotation.PlainOldJavaObject;

import java.util.List;
import javax.xml.bind.annotation.*;

/**
 * @author Gulov
 * @ created 10.09.13
 * @ $Author$
 * @ $Revision$
 */
@XmlRootElement(name="config")
@XmlType(name = "Config")
@XmlAccessorType(XmlAccessType.NONE)
@PlainOldJavaObject
class OSSConfigBean
{
	@XmlElement(name = "use-integration")
	private boolean useIntegration;

	@XmlElement(name = "new-client-registration", required = true)
	private NewClientRegistration newClientRegistration;

	@XmlElement(name = "disconnector-phone", required = true)
	private DisconnectorPhone disconnectorPhone;

	@XmlType(name = "NewClientRegistrationType")
	@XmlAccessorType(XmlAccessType.NONE)
	static class NewClientRegistration
	{
		@XmlElement(name = "max-results")
		private int maxResults;

		int getMaxResults()
		{
			return maxResults;
		}

		void setMaxResults(int maxResults)
		{
			this.maxResults = maxResults;
		}
	}

	@XmlType(name = "DisconnectorPhoneType")
	@XmlAccessorType(XmlAccessType.NONE)
	static class DisconnectorPhone
	{
		@XmlElement(name = "max-results")
		private int maxResults;

		@XmlElement(name = "max-results-for-update")
		private int maxResultsForUpdate;

		@XmlElementWrapper(name = "notifying-reason-codes", required = true)
		@XmlElement(name = "code")
		private List<Integer> codes;

		int getMaxResults()
		{
			return maxResults;
		}

		void setMaxResults(int maxResults)
		{
			this.maxResults = maxResults;
		}

		int getMaxResultsForUpdate()
		{
			return maxResultsForUpdate;
		}

		void setMaxResultsForUpdate(int maxResultsForUpdate)
		{
			this.maxResultsForUpdate = maxResultsForUpdate;
		}

		List<Integer> getCodes()
		{
			//noinspection ReturnOfCollectionOrArrayField
			return codes;
		}

		void setCodes(List<Integer> codes)
		{
			//noinspection AssignmentToCollectionOrArrayFieldFromParameter
			this.codes = codes;
		}
	}

	boolean getUseIntegration()
	{
		return useIntegration;
	}

	void setUseIntegration(boolean useIntegration)
	{
		this.useIntegration = useIntegration;
	}

	NewClientRegistration getNewClientRegistration()
	{
		return newClientRegistration;
	}

	void setNewClientRegistration(NewClientRegistration newClientRegistration)
	{
		this.newClientRegistration = newClientRegistration;
	}

	DisconnectorPhone getDisconnectorPhone()
	{
		return disconnectorPhone;
	}

	void setDisconnectorPhone(DisconnectorPhone disconnectorPhone)
	{
		this.disconnectorPhone = disconnectorPhone;
	}
}
