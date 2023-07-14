package com.rssl.phizic.business.persons.clients;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.processing.StringErrorCollector;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.common.forms.validators.strategy.DefaultValidationStrategy;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.gate.clients.Address;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.utils.BeanHelper;

import java.util.List;
import java.util.Map;

/**
 * @author Omeliyanchuk
 * @ created 07.11.2007
 * @ $Author$
 * @ $Revision$
 */

public class ClientDataValidator
{
	Client client;
	List<String> errors;

	public ClientDataValidator(Client client)
	{
		this.client = client;
	}

	public List<String> getErrors()
	{
		return errors;
	}

	public boolean validate() throws BusinessException
	{
		if(client==null)
			throw new BusinessException("�� ���������� ������");

		Map map = BeanHelper.createMapFromProperties(client);

		Address residenceAddress = client.getRealAddress();
		if (residenceAddress != null)
		{
			map.put("residenceAddress.postalCode", residenceAddress.getPostalCode());
			map.put("residenceAddress.province", residenceAddress.getProvince());
			map.put("residenceAddress.district", residenceAddress.getDistrict());
			map.put("residenceAddress.city", residenceAddress.getCity());
			map.put("residenceAddress.street", residenceAddress.getStreet());
			map.put("residenceAddress.house", residenceAddress.getHouse());
			map.put("residenceAddress.building", residenceAddress.getBuilding());
			map.put("residenceAddress.flat", residenceAddress.getFlat());
		}
		Address registrationAddress = client.getLegalAddress();
		if (registrationAddress != null)
		{
			map.put("registrationAddress.postalCode", registrationAddress.getPostalCode());
			map.put("registrationAddress.province", registrationAddress.getProvince());
			map.put("registrationAddress.district", registrationAddress.getDistrict());
			map.put("registrationAddress.city", registrationAddress.getCity());
			map.put("registrationAddress.street", registrationAddress.getStreet());
			map.put("registrationAddress.house", registrationAddress.getHouse());
			map.put("registrationAddress.building", registrationAddress.getBuilding());
			map.put("registrationAddress.flat", registrationAddress.getFlat());
		}

		MapValuesSource fieldsSource = new MapValuesSource(map);

		FormProcessor processor= new FormProcessor<List<String>, StringErrorCollector>(fieldsSource,createForm(), new StringErrorCollector(), DefaultValidationStrategy.getInstance());
		if(processor.process())
		{
			return true;
		}
		else
		{
			errors = (List<String>)processor.getErrors();
			return false;
		}
	}

	private Form createForm()
	{
		//todo �������� ����� ���� ������������ ���� �� PersonFormBuilder
		FormBuilder fb = new FormBuilder();

		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("���");
		fieldBuilder.setName("firstName");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(new RequiredFieldValidator(),new RegexpFieldValidator(".{0,42}", "���� ��� �� ������ ��������� 42 ��������") );

		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������");
		fieldBuilder.setName("surName");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(new RequiredFieldValidator(), new RegexpFieldValidator(".{0,42}", "���� ������� �� ������ ��������� 42 ��������") );

		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��������");
		fieldBuilder.setName("patrName");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(new RegexpFieldValidator(".{0,42}", "���� �������� �� ������ ��������� 42 ��������") );

		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� ��������");
		fieldBuilder.setName("birthPlace");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators( new RegexpFieldValidator(".{0,128}", "���� ����� �������� �� ������ ��������� 128 ��������"));

		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("id ���������");
		fieldBuilder.setName("documentId");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators( new RegexpFieldValidator(".{0,10}", "���� id ��������� �� ������ ��������� 10 ��������"));

		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������������ ���������");
		fieldBuilder.setName("documentName");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators( new RegexpFieldValidator(".{0,128}", "���� ������������ ��������� �� ������ ��������� 128 ��������"));

		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� ���������");
		fieldBuilder.setName("documentNumber");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(
		new RegexpFieldValidator("\\d*", "���� ����� ��������� ������ ��������� ������ �����"),
        new RegexpFieldValidator(".{0,16}", "���� ����� ��������� �� ������ ��������� 16 ����"));

		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� ���������");
		fieldBuilder.setName("documentSeries");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(new RegexpFieldValidator(".{0,16}", "���� ����� ��������� �� ������ ��������� 16 ��������"));

		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��� ����� ��������");
		fieldBuilder.setName("documentIssueBy");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(new RegexpFieldValidator(".{0,128}", "���� ��� ����� �������� �� ������ ��������� 128 ��������"));

		fb.addField(fieldBuilder.build());

		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� �����������");
		fieldBuilder.setName("registrationAddress");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(new RegexpFieldValidator(".{0,256}", "���� ����� ����������� �� ������ ��������� 256 ��������"));

		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����������� �����");
		fieldBuilder.setName("residenceAddress");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(new RegexpFieldValidator(".{0,256}", "���� ����������� ����� �� ������ ��������� 256 ��������"));

		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� ����������� �����");
		fieldBuilder.setName("email");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(new RegexpFieldValidator(".{0,70}", "���� ����� ����������� ����� �� ������ ��������� 70 ��������"));

		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������� �������");
		fieldBuilder.setName("homePhone");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(new RegexpFieldValidator(".{0,35}", "���� �������� ������� �� ������ ��������� 35 ��������"));

		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������� �������");
		fieldBuilder.setName("jobPhone");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(new RegexpFieldValidator(".{0,35}", "���� ������� ������� �� ������ ��������� 35 ��������"));

		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��������� �������");
		fieldBuilder.setName("mobilePhone");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(new RegexpFieldValidator(".{0,16}", "���� ��������� ������� �� ������ ��������� 16 ��������"));

		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("���");
		fieldBuilder.setName("INN");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(new RegexpFieldValidator("\\d{10}(\\d{2}){0,1}", "���� ��� ������ �������� �� 10 ��� 12 ����"));

		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�����������");
		fieldBuilder.setName("citizenship");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(new RegexpFieldValidator(".{0,70}", "���� ����������� �� ������ ��������� 70 ��������"));

		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� ������������ ����������: ������");
		fieldBuilder.setName("residenceAddress.postalCode");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(new RegexpFieldValidator(".{0,6}", "���� ������ �� ������ ��������� 6 ��������"));

		fb.addField(fieldBuilder.build());
		fieldBuilder = new FieldBuilder();
	    fieldBuilder.setDescription("����� ������������ ����������: �������");
		fieldBuilder.setName("residenceAddress.province");
		fieldBuilder.setType("string");
    	fieldBuilder.setValidators(new RegexpFieldValidator(".{0,100}", "���� ������� �� ������ ��������� 100 ��������"));

		fb.addField(fieldBuilder.build());
		fieldBuilder = new FieldBuilder();
	    fieldBuilder.setDescription("����� ������������ ����������: �����");
		fieldBuilder.setName("residenceAddress.district");
		fieldBuilder.setType("string");
    	fieldBuilder.setValidators(new RegexpFieldValidator(".{0,100}", "���� ����� �� ������ ��������� 100 ��������"));

		fb.addField(fieldBuilder.build());
		fieldBuilder = new FieldBuilder();
	    fieldBuilder.setDescription("����� ������������ ����������: �����");
		fieldBuilder.setName("residenceAddress.sity");
		fieldBuilder.setType("string");
    	fieldBuilder.setValidators(new RegexpFieldValidator(".{0,100}", "���� ����� �� ������ ��������� 100 ��������"));

		fb.addField(fieldBuilder.build());
		fieldBuilder = new FieldBuilder();
	    fieldBuilder.setDescription("����� ������������ ����������: �����");
		fieldBuilder.setName("residenceAddress.street");
		fieldBuilder.setType("string");
    	fieldBuilder.setValidators(new RegexpFieldValidator(".{0,100}", "���� ����� �� ������ ��������� 100 ��������"));

		fb.addField(fieldBuilder.build());
		fieldBuilder = new FieldBuilder();
	    fieldBuilder.setDescription("����� ������������ ����������: ���");
		fieldBuilder.setName("residenceAddress.house");
		fieldBuilder.setType("string");
    	fieldBuilder.setValidators(new RegexpFieldValidator(".{0,10}", "���� ��� �� ������ ��������� 10 ��������"));

		fb.addField(fieldBuilder.build());
		fieldBuilder = new FieldBuilder();
	    fieldBuilder.setDescription("����� ������������ ����������: ������");
		fieldBuilder.setName("residenceAddress.building");
		fieldBuilder.setType("string");
    	fieldBuilder.setValidators(new RegexpFieldValidator(".{0,10}", "���� ������ �� ������ ��������� 10 ��������"));

		fb.addField(fieldBuilder.build());
		fieldBuilder = new FieldBuilder();
	    fieldBuilder.setDescription("����� ������������ ����������: ��������");
		fieldBuilder.setName("residenceAddress.flat");
		fieldBuilder.setType("string");
    	fieldBuilder.setValidators(new RegexpFieldValidator(".{0,10}", "���� �������� �� ������ ��������� 10 ��������"));

		fb.addField(fieldBuilder.build());
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� ����������� : ������");
		fieldBuilder.setName("registrationAddress.postalCode");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(new RegexpFieldValidator(".{0,6}", "���� ������ �� ������ ��������� 6 ��������"));

		fb.addField(fieldBuilder.build());
		fieldBuilder = new FieldBuilder();
	    fieldBuilder.setDescription("����� �����������: �������");
		fieldBuilder.setName("registrationAddress.province");
		fieldBuilder.setType("string");
    	fieldBuilder.setValidators(new RegexpFieldValidator(".{0,100}", "���� ������� �� ������ ��������� 100 ��������"));

		fb.addField(fieldBuilder.build());
		fieldBuilder = new FieldBuilder();
	    fieldBuilder.setDescription("����� �����������: �����");
		fieldBuilder.setName("registrationAddress.district");
		fieldBuilder.setType("string");
    	fieldBuilder.setValidators(new RegexpFieldValidator(".{0,100}", "���� ����� �� ������ ��������� 100 ��������"));

		fb.addField(fieldBuilder.build());
		fieldBuilder = new FieldBuilder();
	    fieldBuilder.setDescription("����� �����������: �����");
		fieldBuilder.setName("registrationAddress.city");
		fieldBuilder.setType("string");
    	fieldBuilder.setValidators(new RegexpFieldValidator(".{0,100}", "���� ����� �� ������ ��������� 100 ��������"));

		fb.addField(fieldBuilder.build());
		fieldBuilder = new FieldBuilder();
	    fieldBuilder.setDescription("����� �����������: �����");
		fieldBuilder.setName("registrationAddress.street");
		fieldBuilder.setType("string");
    	fieldBuilder.setValidators(new RegexpFieldValidator(".{0,100}", "���� ����� �� ������ ��������� 100 ��������"));

		fb.addField(fieldBuilder.build());
		fieldBuilder = new FieldBuilder();
	    fieldBuilder.setDescription("����� �����������: ���");
		fieldBuilder.setName("registrationAddress.house");
		fieldBuilder.setType("string");
    	fieldBuilder.setValidators(new RegexpFieldValidator(".{0,10}", "���� ��� �� ������ ��������� 10 ��������"));

		fb.addField(fieldBuilder.build());
		fieldBuilder = new FieldBuilder();
	    fieldBuilder.setDescription("����� �����������: ������");
		fieldBuilder.setName("registrationAddress.building");
		fieldBuilder.setType("string");
    	fieldBuilder.setValidators(new RegexpFieldValidator(".{0,10}", "���� ������ �� ������ ��������� 10 ��������"));

		fb.addField(fieldBuilder.build());
        fieldBuilder = new FieldBuilder();
	    fieldBuilder.setDescription("����� �����������: ��������");
		fieldBuilder.setName("registrationAddress.flat");
		fieldBuilder.setType("string");
    	fieldBuilder.setValidators(new RegexpFieldValidator(".{0,10}", "���� �������� �� ������ ��������� 10 ��������"));

		fb.addField(fieldBuilder.build());

		return fb.build();
	}
}
