<?xml version="1.0" encoding="windows-1251"?>
<payment-form name="EarlyLoanRepaymentClaim"
              description="������ �� ��������� ���������"
              detailedDescription="������ �� ��������� ���������"
              xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
              xsi:noNamespaceSchemaLocation="../../PaymentForm.xsd">
	<implementation class="com.rssl.phizic.business.documents.payments.EarlyLoanRepaymentClaimImpl"/>
    <statemachine name="EarlyLoanRepaymentClaim"/>
    <extended-metadata-loader class="com.rssl.phizic.business.payments.forms.meta.EarlyLoanRepaymentMetadataLoader"/>

    <fields>
        <field name="documentNumber"
               description="����� ���������"
               source="document-number"
               type="string"/>

        <field name="partial"
               description="������� ���������� �������"
               source="extra-parameters/parameter[@name='partial']/@value"
               type="string"
               inital="false"/>

        <field name="loanLinkId"
               description="������������� ���������� ��������"
               source="extra-parameters/parameter[@name='loanLinkId']/@value"
               type="long"/>

        <field  name="state"
                description="������ �������"
                type="string"
                source="state">
        </field>

        <field name="amount"
               description="�����"
               type="money"
               source="amount">
            <validators>
                <validator class="com.rssl.common.forms.validators.RequiredFieldValidator" enabled="form.partial == 'true'">
                    <message text="������� ����� �������."/>
                </validator>
            </validators>
        </field>

        <field name="departmentId"
               source="departmentId"
               description="���� �����"
               type="string"/>

        <field name="exactAmount"
               description="����� �������� (����������)"
               type="string"
               source="exact-amount"
               value="'charge-off-field-exact'"/>

        <field  name="documentDate"
                description="���� ���������"
                type="date"
                source="document-date"
                signable="true">
            <validators>
                <validator class="com.rssl.common.forms.validators.RequiredFieldValidator"/>
                <validator class="com.rssl.phizic.business.payments.forms.validators.EarlyLoanRepaymentDateValidator">
                    <message text="��� ������ ���� ���������� ��������� �������������� ����������."/>
                </validator>
            </validators>
        </field>

        <field  name="fromResource"
                description="�������� �������� �������: ����, �����"
                type="resource"
                source="extra-parameters/parameter[@name='from-resource']/@value">

            <validators>
                <validator class="com.rssl.common.forms.validators.RequiredFieldValidator"/>
            </validators>
        </field>

        <field  name="fromResourceType"
                description="��� ��������� �������� �������: ����, �����"
                type="string"
                source="extra-parameters/parameter[@name='from-resource-type']/@value"
                value="form.fromResource==null?null:form.fromResource.getClass().getName()">
        </field>

        <field  name="fromAccountSelect"
                description="����/����� ��������"
                type="string"
                source="payer-account"
                value="form.fromResource==null?null:form.fromResource.getNumber()"
                signable="true">
		</field>

        <field  name="fromAccountType"
                description="��� (��������) ��������� ��������"
                type="string"
                source="extra-parameters/parameter[@name='from-account-type']/@value"
                value="form.fromResource==null?null:form.fromResource.getValue().getDescription()"
                signable="true">
        </field>

        <field  name="fromAccountName"
                description="������������ ��������� ��������"
                type="string"
                source="extra-parameters/parameter[@name='from-account-name']/@value"
                value="form.fromResource==null?null:form.fromResource.getName()"
                signable="true">
        </field>

        <field name="fromResourceCurrency"
               description="������"
               type="string"
               source="extra-parameters/parameter[@name='from-resource-currency']/@value"
               value="form.fromAccountSelect==null?null:form.fromResource.getCurrency().getCode()"
               signable="true">
        </field>

        <field  name="operationDate"
                description="���� �������� �������"
                source="operation-date-short-year"
                type="string">
        </field>

        <field  name="operationTime"
                description="����� ���������� �������"
                source="operation-time"
                type="string">
        </field>

        <field  name="selectedResourceRest"
                description="������� �� ��������� �����"
                source="selected-resource-rest"
                type="string">
        </field>

    </fields>

    <form-validators>
        <form-validator class="com.rssl.phizic.business.payments.forms.validators.EarlyLoanRepaymentClosureDateValidator">
            <message text="���� ���������� ��������� �� ������ ���� ������ ���� ��������� �������."/>
        </form-validator>
        <form-validator class="com.rssl.phizic.business.payments.forms.validators.EarlyLoanRepaymentAmountValidator">
            <message text="����� ��� ���������� ��������� ������� �� ������ ���� ������ ����������� ��� ��������� ������������."/>
        </form-validator>
        <form-validator class="com.rssl.phizic.business.payments.forms.validators.EarlyLoanRepWorkDayValidator">
            <message text="���� ���������� ��������� �� ������ �������� �� �������� � ����������� ���. ����������, �������� ������ ����."/>
        </form-validator>
    </form-validators>

</payment-form>