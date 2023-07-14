package com.rssl.phizic.operations.loanOffer;

import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;

/**
 * User: Moshenko
 * Date: 16.06.2011
 * Time: 9:40:32
 * ����������� ����� �������� ��������������  ����������� �� ������
 */
public class LoanCardOfferForm
{
    public static Form getForm()
    {
        FormBuilder fb = new FormBuilder();
        
        FieldBuilder fieldBuilder = new FieldBuilder();
        fieldBuilder.setDescription("�������");
        fieldBuilder.setName("surname");
        fieldBuilder.setType("string");
        fieldBuilder.addValidators
                (
                        new RequiredFieldValidator(),
                        new RegexpFieldValidator(".{0,20}", "����� ���� '�������' �� ������ ��������� 20 ��������")
                );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
        fieldBuilder.setDescription("���");
        fieldBuilder.setName("name");
        fieldBuilder.setType("string");
        fieldBuilder.addValidators
                (
                        new RequiredFieldValidator(),
                        new RegexpFieldValidator(".{0,20}", "����� ���� '���' �� ������ ��������� 20 ��������")
                );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
        fieldBuilder.setDescription("��������");
        fieldBuilder.setName("patrName");
        fieldBuilder.setType("string");
        fieldBuilder.addValidators
                (
                        new RequiredFieldValidator(),
                        new RegexpFieldValidator(".{0,15}", "����� ���� '��������' �� ������ ��������� 15 ��������")
                );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
        fieldBuilder.setDescription("����� � ����� �������� ��");
        fieldBuilder.setName("seriesAndNumber");
        fieldBuilder.setType("string");
        fieldBuilder.addValidators
                (
                        new RequiredFieldValidator(),
                        new RegexpFieldValidator(".{0,19}", "����� ���� '����� � ����� �������� ��' �� ������ ��������� 19 ��������")
                );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
        fieldBuilder.setDescription("���� ��������");
        fieldBuilder.setName("birthDate");
        fieldBuilder.setType("date");
        fieldBuilder.addValidators
                (
                        new RequiredFieldValidator(),
                        new RegexpFieldValidator(".{0,10}", "����� ���� '���� ��������' �� ������ ��������� 10 ��������")
                );
        fb.addField(fieldBuilder.build());

	    fieldBuilder = new FieldBuilder();
        fieldBuilder.setDescription("��� �����������");
        fieldBuilder.setName("offerType");
        fieldBuilder.setType("integer");
        fieldBuilder.addValidators
                (
                        new RequiredFieldValidator(),
                        new RegexpFieldValidator(".{0,1}", "����� ���� '��� �����������' �� ������ ��������� 1 ��������")
                );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
        fieldBuilder.setDescription("����� �������");
        fieldBuilder.setName("creditLimit");
        fieldBuilder.setType("money");
        fieldBuilder.addValidators
                (
                        new RequiredFieldValidator(),
                        new RegexpFieldValidator(".{0,14}", "����� ���� '����� �������' �� ������ ��������� 14 ��������")
                );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
        fieldBuilder.setDescription("������������ ��������");
        fieldBuilder.setName("companyName");
        fieldBuilder.setType("string");
        fieldBuilder.addValidators
                (
                        new RegexpFieldValidator(".{0,30}", "����� ���� '������������ ��������' �� ������ ��������� 30 ��������")
                );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
        fieldBuilder.setDescription("������� ������");
        fieldBuilder.setName("index");
        fieldBuilder.setType("integer");
        fieldBuilder.addValidators
                (
                        new RegexpFieldValidator(".{0,6}", "����� ���� '������� ������' �� ������ ��������� 6 ��������")
                );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
        fieldBuilder.setDescription("������� �����1");
        fieldBuilder.setName("adres1");
        fieldBuilder.setType("string");
        fieldBuilder.addValidators
                (
                        new RegexpFieldValidator(".{0,30}", "����� ���� '������� �����1' �� ������ ��������� 30 ��������")
                );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
        fieldBuilder.setDescription("������� �����2");
        fieldBuilder.setName("adres2");
        fieldBuilder.setType("string");
        fieldBuilder.addValidators
                (
                        new RegexpFieldValidator(".{0,30}", "����� ���� '������� �����2' �� ������ ��������� 30 ��������")
                );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
        fieldBuilder.setDescription("������� �����3");
        fieldBuilder.setName("adres3");
        fieldBuilder.setType("string");
        fieldBuilder.addValidators
                (
                        new RegexpFieldValidator(".{0,30}", "����� ���� '������� �����3' �� ������ ��������� 30 ��������")
                );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
        fieldBuilder.setDescription("������� �����4");
        fieldBuilder.setName("adres4");
        fieldBuilder.setType("string");
        fieldBuilder.addValidators
                (
                        new RegexpFieldValidator(".{0,30}", "����� ���� '������� �����4' �� ������ ��������� 30 ��������")
                );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
        fieldBuilder.setDescription("ID �������");
        fieldBuilder.setName("idWay");
        fieldBuilder.setType("integer");
        fb.addField(fieldBuilder.build());
        fieldBuilder.addValidators
                (
                        new RegexpFieldValidator(".{0,18}", "����� ���� 'ID �������' �� ������ ��������� 18 ��������")
                );

        fieldBuilder = new FieldBuilder();
        fieldBuilder.setDescription("����� �����");
        fieldBuilder.setName("cardNumber");
        fieldBuilder.setType("integer");
        fieldBuilder.addValidators
                (
                        new RegexpFieldValidator(".{0,19}", "����� ���� '����� �����' �� ������ ��������� 19 ��������")
                );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
        fieldBuilder.setDescription("����");
        fieldBuilder.setName("terbank");
        fieldBuilder.setType("integer");
        fieldBuilder.addValidators
                (
                        new RegexpFieldValidator(".{0,2}", "����� ���� '����' �� ������ ��������� 2 ��������")
                );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
        fieldBuilder.setDescription("���������");
        fieldBuilder.setName("osb");
        fieldBuilder.setType("integer");
        fieldBuilder.addValidators
                (
                        new RequiredFieldValidator(),
                        new RegexpFieldValidator(".{0,4}", "����� ���� '���������' �� ������ ��������� 4 ��������")
                );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
        fieldBuilder.setDescription("���");
        fieldBuilder.setName("vsp");
        fieldBuilder.setType("integer");
        fieldBuilder.addValidators
                (
                        new RegexpFieldValidator(".{0,10}", "����� ���� '���' �� ������ ��������� 10 ��������")
                );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
        fieldBuilder.setDescription("��� ���� �������/ ������ �������");
        fieldBuilder.setName("clientCodeType");
        fieldBuilder.setType("string");
        fieldBuilder.addValidators
                (
                        new RequiredFieldValidator(),
                        new RegexpFieldValidator(".{0,10}", "����� ���� '��� ���� �������/ ������ �������' �� ������ ��������� 10 ��������")
                );
        fb.addField(fieldBuilder.build());

        return fb.build();
    }
}
