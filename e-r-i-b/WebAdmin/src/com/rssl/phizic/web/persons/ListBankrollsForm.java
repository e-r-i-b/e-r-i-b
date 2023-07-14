package com.rssl.phizic.web.persons;

import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.web.common.FilterActionForm;
import com.rssl.phizic.web.common.ListLimitActionForm;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FieldBuilder;
import org.apache.struts.action.ActionForm;

import java.util.ArrayList;
import java.util.List;

/** Created by IntelliJ IDEA. User: Evgrafov Date: 06.10.2005 Time: 14:44:08 */
public class ListBankrollsForm extends ListLimitActionForm
{
    private Long   person = new Long(0);
    private String type   = "cards";
	private String clientId = "0";
	private Client client;

    public Long getPerson()
    {
        return person;
    }

    public void setPerson(Long person)
    {
        this.person = person;
    }

    public void setType(String type)
    {
        this.type = type;
    }

	public String getType()
	{
		return type;
	}

	public String getClientId()
	{
		return clientId;
	}

	public void setClientId(String clientId)
	{
		this.clientId = clientId;
	}

	public Client getClient()
	{
		return client;
	}

	public void setClient(Client client)
	{
		this.client = client;
	}

	public static final Form FILTER_FORM = createForm();

	private static Form createForm(){
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("ID");
		fieldBuilder.setName("id");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
