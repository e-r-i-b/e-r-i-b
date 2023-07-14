package com.rssl.phizic.web.common.dictionaries;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.business.dictionaries.PaymentReceiverBase;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.web.common.ListLimitActionForm;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kidyaev
 * @ created 29.11.2005
 * @ $Author$
 * @ $Revision$
 */
public class ShowPaymentReceiverListForm extends ListLimitActionForm
{
	private String   kind = "";
	private String listKind = "";
	private Long     person = Long.valueOf(0);
	private ActivePerson activePerson;
	private List<PaymentReceiverBase> receivers;
	private String externalPaymentSystemId = "";
	private Boolean    modified=false;

	public Boolean getModified()
	{
		return modified;
	}

	public void setModified(Boolean modified)
	{
		this.modified = modified;
	}

	public String getExternalPaymentSystemId()
	{
		return externalPaymentSystemId;
	}

	public void setExternalPaymentSystemId(String externalPaymentSystemId)
	{
		this.externalPaymentSystemId = externalPaymentSystemId;
	}

	public String getKind ()
	{
		return kind;
	}

	public void setKind ( String kind )
	{
		this.kind = kind;
	}

	public String getListKind()
	{
		return listKind;
	}

	public void setListKind(String listKind)
	{
		this.listKind = listKind;
	}

	public Long getPerson ()
	{
		return person;
	}

	public void setPerson ( Long person )
	{
		this.person = person;
	}

	public ActivePerson getActivePerson ()
	{
		return activePerson;
	}

	public void setActivePerson ( ActivePerson activePerson )
	{
		this.activePerson = activePerson;
	}

	public List getReceivers()
	{
		return receivers;
	}

	public void setReceivers(List receivers)
	{
		this.receivers = receivers;
	}

	public static Form FILTER_FORM = createForm();

	private static Form createForm()
	{
		ArrayList<Field> fields = new ArrayList<Field>();
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("name");
		fieldBuilder.setDescription("Наименование получателя");
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("INN");
		fieldBuilder.setDescription("ИНН");
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("paymentService");
		fieldBuilder.setDescription("Услуга");
		fields.add(fieldBuilder.build());

		FormBuilder formBuilder = new FormBuilder();
		formBuilder.addFields(fields);

		return formBuilder.build();
	}
}
