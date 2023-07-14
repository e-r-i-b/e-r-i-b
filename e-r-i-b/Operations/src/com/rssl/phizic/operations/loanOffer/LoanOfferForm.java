package com.rssl.phizic.operations.loanOffer;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;

/**
 * User: Moshenko
 * Date: 10.06.2011
 * Time: 11:26:09
 * ����������� ����� �������� �������������� ��������� �����������
 */
public class LoanOfferForm {

	public static final Form FORM = createForm();

	private static Form createForm()
	{
        FormBuilder fb = new FormBuilder();

    	FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������");
		fieldBuilder.setName(Constants.TERBANK);
		fieldBuilder.setType("string");
        fieldBuilder.addValidators
            (
                new RegexpFieldValidator(".{0,2}", "����� ���� '�������' �� ������ ��������� 2 ��������")
             );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("���");
		fieldBuilder.setName(Constants.FIO);
		fieldBuilder.setType("string");
        fieldBuilder.addValidators
	        (
                new RequiredFieldValidator(),
                new RegexpFieldValidator(".{0,250}", "����� ���� '���' �� ������ ��������� 250 ��������")
	        );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("���");
		fieldBuilder.setName(Constants.SEX);
		fieldBuilder.setType("string");
        fieldBuilder.addValidators
	        (
                new RegexpFieldValidator(".{0,1}", "����� ���� '���' �� ������ ��������� 1 ��������")
	        );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("���� ��������");
		fieldBuilder.setName(Constants.BIRTH_DATE);
		fieldBuilder.setType("date");
        fieldBuilder.addValidators
	        (
                new RequiredFieldValidator(),
                new RegexpFieldValidator(".{0,10}", "����� ���� '���� ��������' �� ������ ��������� 10 ��������")
	        );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� ��������");
		fieldBuilder.setName(Constants.PASPORT_NUMBER);
		fieldBuilder.setType("string");
        fieldBuilder.addValidators
	        (
                new RequiredFieldValidator(),
                new RegexpFieldValidator(".{0,6}", "����� ���� '����� ��������' �� ������ ��������� 6 ��������")
    	    );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� ��������");
		fieldBuilder.setName(Constants.SERIES);
		fieldBuilder.setType("string");
        fieldBuilder.addValidators
	        (
                new RequiredFieldValidator(),
                new RegexpFieldValidator(".{0,5}", "����� ���� '����� ��������' �� ������ ��������� 5 ��������")

	        );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("���� ������ ��������");
		fieldBuilder.setName(Constants.PASPORT_DATE);
		fieldBuilder.setType("date");
        fieldBuilder.addValidators
	        (
                new RegexpFieldValidator(".{0,10}", "����� ���� '���� ������ ��������' �� ������ ��������� 10 ��������")

	        );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� ������ ��������");
		fieldBuilder.setName(Constants.ISSUE_PLACE);
		fieldBuilder.setType("string");
        fieldBuilder.addValidators
	        (
                new RegexpFieldValidator(".{0,250}", "����� ���� '����� ������ ��������' �� ������ ��������� 250 ��������")

	        );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� �����������: ������");
		fieldBuilder.setName(Constants.REGION);
		fieldBuilder.setType("string");
        fieldBuilder.addValidators
	        (
                new RegexpFieldValidator(".{0,250}", "����� ���� '����� �����������: ������' �� ������ ��������� 205 ��������")

	        );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� �����������: �����");
		fieldBuilder.setName(Constants.DISTRICT);
		fieldBuilder.setType("string");
        fieldBuilder.addValidators
	        (
                new RegexpFieldValidator(".{0,250}", "����� ���� '���� �����������: �����' �� ������ ��������� 250 ��������")

	        );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� �����������: ���������� �����");
		fieldBuilder.setName(Constants.LOCATION);
		fieldBuilder.setType("string");
        fieldBuilder.addValidators
	        (
                new RegexpFieldValidator(".{0,250}", "����� ���� '����� �����������: ���������� �����' �� ������ ��������� 250 ��������")

	        );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� �����������: ������");
		fieldBuilder.setName(Constants.INDEX);
		fieldBuilder.setType("string");
        fieldBuilder.addValidators
	        (
                new RegexpFieldValidator(".{0,6}", "����� ���� ''����� �����������: ������' �� ������ ��������� 6 ��������")

	        );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� �����������: �����");
		fieldBuilder.setName(Constants.STREET);
		fieldBuilder.setType("string");
        fieldBuilder.addValidators
	        (
                new RegexpFieldValidator(".{0,250}", "����� ���� '����� �����������: �����' �� ������ ��������� 250 ��������")

	        );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� �����������: ���");
		fieldBuilder.setName(Constants.HOME);
		fieldBuilder.setType("string");
        fieldBuilder.addValidators
	        (
                new RegexpFieldValidator(".{0,4}", "����� ���� '����� �����������: ���' �� ������ ��������� 4 ��������")

	        );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� �����������: ������");
		fieldBuilder.setName(Constants.HOUSING);
		fieldBuilder.setType("string");
        fieldBuilder.addValidators
	        (
                new RegexpFieldValidator(".{0,4}", "����� ���� '����� �����������: ������' �� ������ ��������� 4 ��������")

	        );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� �����������: ��������");
		fieldBuilder.setName(Constants.APARTMENT);
		fieldBuilder.setType("string");
        fieldBuilder.addValidators
	        (
                new RegexpFieldValidator(".{0,4}", "����� ���� '����� �����������: ��������' �� ������ ��������� 4 ��������")

	        );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� ����������: ������");
		fieldBuilder.setName(Constants.REGION2);
		fieldBuilder.setType("string");
        fieldBuilder.addValidators
	        (
                new RegexpFieldValidator(".{0,250}", "����� ���� '����� ����������: ������' �� ������ ��������� 250 ��������")

	        );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� ����������: �����");
		fieldBuilder.setName(Constants.DISTRICT2);
		fieldBuilder.setType("string");
        fieldBuilder.addValidators
	        (
                new RegexpFieldValidator(".{0,250}", "����� ���� '����� ����������: �����' �� ������ ��������� 250 ��������")

	        );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� ����������: ���������� �����");
		fieldBuilder.setName(Constants.LOCATION2);
		fieldBuilder.setType("string");
        fieldBuilder.addValidators
	        (
                new RegexpFieldValidator(".{0,250}", "����� ���� '����� ����������: ���������� �����' �� ������ ��������� 250 ��������")

	        );
        fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� ����������: ������");
		fieldBuilder.setName(Constants.INDEX2);
		fieldBuilder.setType("string");
        fieldBuilder.addValidators
	        (
                new RegexpFieldValidator(".{0,6}", "����� ���� '����� ����������: ������' �� ������ ��������� 6 ��������")

	        );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� ����������: �����");
		fieldBuilder.setName(Constants.STREET2);
		fieldBuilder.setType("string");
        fieldBuilder.addValidators
	        (
                new RegexpFieldValidator(".{0,250}", "����� ���� '����� ����������: �����' �� ������ ��������� 250 ��������")

	        );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� ����������: ���");
		fieldBuilder.setName(Constants.HOME2);
		fieldBuilder.setType("string");
        fieldBuilder.addValidators
	        (
                new RegexpFieldValidator(".{0,4}", "����� ���� '����� ����������: ���' �� ������ ��������� 4 ��������")

	        );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� ����������: ������");
		fieldBuilder.setName(Constants.HOUSING2);
		fieldBuilder.setType("string");
        fieldBuilder.addValidators
	        (
                new RegexpFieldValidator(".{0,4}", "����� ���� '����� ����������: ������' �� ������ ��������� 4 ��������")

	        );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� ����������: ��������");
		fieldBuilder.setName(Constants.APARTENT2);
		fieldBuilder.setType("string");
        fieldBuilder.addValidators
	        (
                new RegexpFieldValidator(".{0,4}", "����� ���� '����� ����������: ��������' �� ������ ��������� 4 ��������")

	        );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������� 1");
		fieldBuilder.setName(Constants.PHONE1);
		fieldBuilder.setType("string");
        fieldBuilder.addValidators
	        (
                new RegexpFieldValidator(".{0,35}", "����� ���� '������� 1' �� ������ ��������� 35 ��������")

	        );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������� 2");
		fieldBuilder.setName(Constants.PHONE2);
		fieldBuilder.setType("string");
        fieldBuilder.addValidators
	        (
                new RegexpFieldValidator(".{0,35}", "����� ���� '������� 2' �� ������ ��������� 35 ��������")

	        );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������� 3");
		fieldBuilder.setName(Constants.PHONE3);
		fieldBuilder.setType("string");
        fieldBuilder.addValidators
	        (
                new RegexpFieldValidator(".{0,35}", "����� ���� '������� 3' �� ������ ��������� 35 ��������")

	        );
        fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� �� ������ �������: 6 ���.");
		fieldBuilder.setName(Constants.MONTH6);
		fieldBuilder.setType("money");
        fieldBuilder.addValidators
	        (
                new RegexpFieldValidator(".{0,9}", "����� ���� '����� �� ������ �������: 6 ���' �� ������ ��������� 9 ��������")

	        );
        fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� �� ������ �������: 12 ���.");
		fieldBuilder.setName(Constants.MONTH12);
		fieldBuilder.setType("money");
        fieldBuilder.addValidators
	        (
                new RegexpFieldValidator(".{0,9}", "����� ���� '����� �� ������ �������: 12 ���.' �� ������ ��������� 9 ��������")

	        );
        fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� �� ������ �������: 18 ���.");
		fieldBuilder.setName(Constants.MONTH18);
		fieldBuilder.setType("money");
        fieldBuilder.addValidators
	        (
                new RegexpFieldValidator(".{0,9}", "����� ���� '����� �� ������ �������: 18 ���.' �� ������ ��������� 9 ��������")

	        );
        fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� �� ������ �������: 24 ���.");
		fieldBuilder.setName(Constants.MONTH24);
		fieldBuilder.setType("money");
        fieldBuilder.addValidators
	        (
                new RegexpFieldValidator(".{0,9}", "����� ���� '����� �� ������ �������: 24 ���.' �� ������ ��������� 9 ��������")

	        );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� �� ������ �������: 36 ���.");
		fieldBuilder.setName(Constants.MONTH36);
		fieldBuilder.setType("money");
        fieldBuilder.addValidators
	        (
                new RegexpFieldValidator(".{0,9}", "����� ���� '����� �� ������ �������: 36 ���.' �� ������ ��������� 9 ��������")

	        );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� �� ������ �������: 48 ���.");
		fieldBuilder.setName(Constants.MONTH48);
		fieldBuilder.setType("money");
        fieldBuilder.addValidators
	        (
                new RegexpFieldValidator(".{0,9}", "����� ���� '����� �� ������ �������: 48 ���.' �� ������ ��������� 9 ��������")

	        );
        fb.addField(fieldBuilder.build());

        fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� �� ������ �������: 60 ���.");
		fieldBuilder.setName(Constants.MONTH60);
		fieldBuilder.setType("money");
        fieldBuilder.addValidators
	        (
                new RegexpFieldValidator(".{0,9}", "����� ���� '����� �� ������ �������: 60 ���.' �� ������ ��������� 9 ��������")

	        );
        fb.addField(fieldBuilder.build());



		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������������ ���������� ��������");
		fieldBuilder.setName(Constants.PRODUCT_NAME);
		fieldBuilder.setType("string");
        fieldBuilder.addValidators
	        (
			    new RequiredFieldValidator(),
                new RegexpFieldValidator(".{0,48}", "����� ���� '������������ ���������� ��������' �� ������ ��������� 48 ��������")

	        );
        fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��� ��������");
		fieldBuilder.setName(Constants.PRODUCT_CODE);
		fieldBuilder.setType("string");
		fieldBuilder.addValidators
				(
						new RegexpFieldValidator(".{0,2}", "����� ���� '��� ��������' �� ������ ��������� 2 ��������")

				);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��� �����������");
		fieldBuilder.setName(Constants.SUB_PRODUCT_CODE);
		fieldBuilder.setType("string");
		fieldBuilder.addValidators
				(
						new RegexpFieldValidator(".{0,5}", "����� ���� '��� �����������' �� ������ ��������� 5 ��������")

				);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("���������� ������");
		fieldBuilder.setName(Constants.PRACENT_RATE);
		fieldBuilder.setType("string");
        fieldBuilder.addValidators
	        (
			    new RequiredFieldValidator(),
                new RegexpFieldValidator(".{0,5}", "����� ���� '���������� ������' �� ������ ��������� 5 ��������")

	        );
        fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("���� ��������� �������� �����������");
		fieldBuilder.setName(Constants.END_DATE);
		fieldBuilder.setType("date");
		fieldBuilder.addValidators
				(
						new RequiredFieldValidator(),
						new RegexpFieldValidator(".{0,10}", "����� ���� '���� ��������� �������� �����������' �� ������ ��������� 10 ��������")
				);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������");
		fieldBuilder.setName(Constants.CURRENCY);
		fieldBuilder.setType("string");
        fieldBuilder.addValidators
	        (
			    new RequiredFieldValidator(),
                new RegexpFieldValidator(".{0,3}", "����� ���� '������' �� ������ ��������� 3 ��������")

	        );

        fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������������� ��������� ��������");
		fieldBuilder.setName(Constants.CAMPAIGN_MEMBER_ID);
		fieldBuilder.setType("string");
		fieldBuilder.addValidators
				(
						new RequiredFieldValidator(),
						new RegexpFieldValidator(".{0,50}", "����� ���� '������������� ��������� ��������' �� ������ ��������� 50 ��������")

				);

		fb.addField(fieldBuilder.build());

		return fb.build();
	}
}
