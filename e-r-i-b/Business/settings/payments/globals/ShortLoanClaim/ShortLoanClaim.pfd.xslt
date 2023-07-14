<?xml version="1.0" encoding="windows-1251"?>

<payment-form name="ShortLoanClaim"
              description="Оформление кредита"
              xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
              xsi:noNamespaceSchemaLocation="../../PaymentForm.xsd">

    <implementation class="com.rssl.phizic.business.documents.payments.ShortLoanClaim"/>
    <statemachine   name="ShortLoanClaimMachine"/>

    <fields>
        <field name="documentNumber"
               description="Номер документа"
               type="string"
               source="document-number"
               signable="true">
        </field>

        <field name="documentDate"
               description="Дата документа"
               type="date"
               source="document-date"
               signable="true"/>

        <field name="state"
               source="state"
               signable="true"
               description="Статус заявки"
               type="string"/>

        <field name="loanOfferId"
               type="string"
               signable="true"
               source="extra-parameters/parameter[@name='loanOfferId']/@value"
               description="Идентификатор предодобренного кредитного предложения">
        </field>

        <field name="condId"
               type="long"
               signable="true"
               source="extra-parameters/parameter[@name='condId']/@value"
               description="Идентификатор условия кредитного предложения">
        </field>

        <field name="condCurrId"
               type="long"
               signable="true"
               source="extra-parameters/parameter[@name='condCurrId']/@value"
               description="Идентификатор повалютного условия кредитного предложения">
        </field>

        <field name="currMinPercentRate"
               signable="true"
               source="extra-parameters/parameter[@name='currMinPercentRate']/@value"
               description="Минимальная процентная ставка повалютного условия">
        </field>

        <field name="currMaxPercentRate"
               signable="true"
               source="extra-parameters/parameter[@name='currMaxPercentRate']/@value"
               description="Максимальная процентная ставка повалютного условия">
        </field>

        <field name="onlyShortClaim"
               type="boolean"
               signable="true"
               source="extra-parameters/parameter[@name='onlyShortClaim']/@value"
               description="Признак показывающий возможность создания расширенной кредитной заявки">
        </field>

        <field name="productName"
               signable="true"
               source="extra-parameters/parameter[@name='productName']/@value"
               description="Наименование кредита">
        </field>

        <field name="ensuring"
               signable="true"
               source="extra-parameters/parameter[@name='ensuring']/@value"
               description="Обеспечение">

        </field>

        <field name="productPeriod"
               signable="true"
               source="extra-parameters/parameter[@name='productPeriod']/@value"
               description="Срок продукта">
        </field>

        <field name="loanPeriod"
               type="long"
               signable="true"
               source="extra-parameters/parameter[@name='loanPeriod']/@value"
               description="Срок">
        </field>

        <field name="additionalInfo"
               signable="true"
               source="extra-parameters/parameter[@name='additionalInfo']/@value"
               description="Дополнительная информация по кредиту">
        </field>

        <field name="loanAmount"
               type="money"
               source="extra-parameters/parameter[@name='loanAmount']/@value"
               signable="true"
               description="Сумма кредита">
        </field>

        <field name="loanCurrency"
               signable="true"
               source="extra-parameters/parameter[@name='loanCurrency']/@value"
               description="Валюта кредита">
        </field>

        <field name="loanRate"
               signable="true"
               source="extra-parameters/parameter[@name='loanRate']/@value"
               description="Процентная ставка">
        </field>

        <field name="fio"
               signable="true"
               source="extra-parameters/parameter[@name='fio']/@value"
               description="Фамилия, имя, отчество">

			<validators>
				<validator mode="document" class="com.rssl.common.forms.validators.RequiredFieldValidator"/>
				<validator class="com.rssl.common.forms.validators.RegexpFieldValidator">
					<message text="Количество символов в поле не должно превышать 100"/>
					<parameter name="regexp">.{1,100}</parameter>
				</validator>
			</validators>
        </field>

        <field name="jobLocation"
               signable="true"
               source="extra-parameters/parameter[@name='jobLocation']/@value"
               description="Место работы">

               <validators>
                    <validator class="com.rssl.common.forms.validators.RequiredFieldValidator"/>
                    <validator class="com.rssl.common.forms.validators.RegexpFieldValidator">
                        <message text="Количество символов в поле не должно превышать 100"/>
                        <parameter name="regexp">.{1,100}</parameter>
                    </validator>
               </validators>
        </field>

        <field name="phoneNumber"
               signable="true"
               source="extra-parameters/parameter[@name='phoneNumber']/@value"
               inital="xpath:phiz:document('currentPerson.xml')//field[@name='mobilePhone']"
               description="Контактный номер телефона"
               mask="###***####">

               <validators>
                   <validator class="com.rssl.common.forms.validators.RequiredFieldValidator"/>
                    <validator class="com.rssl.common.forms.validators.RegexpFieldValidator">
                        <message text="Номер телефона указывается без 8, содержит 10 цифр."/>
                        <parameter name="regexp">\d{10}</parameter>
                    </validator>
               </validators>
        </field>

        <field name="incomePerMonth"
               type="money"
               signable="true"
               source="extra-parameters/parameter[@name='incomePerMonth']/@value"
               description="Средний доход в месяц">

               <validators>
                   <validator class="com.rssl.common.forms.validators.RequiredFieldValidator"/>
                   <validator class="com.rssl.common.forms.validators.MoneyFieldValidator">
                        <message text="Значение поля должно быть в диапазоне 0,01 - 999999999999999.99"/>
                        <parameter name="minValue">0.01</parameter>
                        <parameter name="maxValue">999999999999999.99</parameter>
                   </validator>
               </validators>
        </field>

        <field name="processingEnabled"
               type="boolean"
               signable="true"
               source="extra-parameters/parameter[@name='processingEnabled']/@value"
               description="Согласие на обработку персональных данных">
        </field>
    </fields>
</payment-form>