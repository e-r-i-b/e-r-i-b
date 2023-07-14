<?xml version="1.0" encoding="windows-1251"?>
<payment-form name="AccountChangeInterestDestinationClaim" description="�������������� ���������� � �������� � ������"
              detailedDescription="��������� ����� � ������� �� ������ ������������."
              confirmDescription="�� ������� ����� � ��������� ������! ��� ���� ����� �������� ��������� �������� �� ���������� �������� �������, ���������� � ��������� ��������� �����."
              xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
              xsi:noNamespaceSchemaLocation="../../PaymentForm.xsd">

	<implementation class="com.rssl.phizic.business.documents.AccountChangeInterestDestinationClaim"/>
	<statemachine name="PaymentStateMachine"/>

	<fields>
        <field name="accountCode"
               type="string"
               source="extra-parameters/parameter[@name='account-code']/@value"
               description="������������� �����, ��� �������� ���������� ������� ������ ���������"/>

        <field name="documentNumber"
               description="����� ���������"
               type="string"
               source="document-number"
               signable="true"/>

        <field name="documentDate"
               description="���� ���������"
               type="date"
               source="document-date"
               signable="true"/>

        <field name="interestDestinationSource"
               description="������� ������ ���������"
               source="extra-parameters/parameter[@name='interest-destination-source']/@value"
               type="string"
               signable="true"/>

        <field name="cardLink"
               description="�����"
               type="resource"
               source="extra-parameters/parameter[@name='card']/@value">
               <validators>
                    <validator class="com.rssl.common.forms.validators.RequiredFieldValidator"
                               enabled="form.interestDestinationSource == 'card'">
                        <message text="�������, ����������, ����� ��� ������������ ���������."/>
                    </validator>
               </validators>
        </field>

        <field name="interestCardNumber"
               description="����� ����� ��� ���������� ���������"
               type="string"
               source="extra-parameters/parameter[@name='interest-card-number']/@value"
               value="form.cardLink==null?null:form.cardLink.getNumber()"
               signable="true">
        </field>

        <field name="state"
               source="state"
               description="������ �������"
               type="string"/>
    </fields>

</payment-form>
