<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="windows-1251" indent="yes"/>

    <xsl:template match="/form-data">
        <document>

            <initial-2-fields>
                <ownerLastName>
                    <xsl:value-of select="surName"/>
                </ownerLastName>

                <ownerFirstName>
                    <xsl:value-of select="firstName"/>
                </ownerFirstName>

                <ownerMiddleName>
                    <xsl:value-of select="patrName"/>
                </ownerMiddleName>

                <ownerBirthday>
                    <xsl:value-of select="birthDay"/>
                </ownerBirthday>

                <ownerIdCardNumber>
                    <xsl:value-of select="passportNumber"/>
                </ownerIdCardNumber>

                <ownerIdCardSeries>
                    <xsl:value-of select="passportSeries"/>
                </ownerIdCardSeries>

            </initial-2-fields>


            <!--
            ******************************************************************
                    Поля разделены по шагам для того, чтобы при сохранении очередного шага поля предыдущих шагов не перезаписывались и не затирались.
            ******************************************************************
            -->

            <request-parameters>
                <!--поля, которые приходят из реквеста-->
                <parameter name="birthPlace"                value="{birthPlace}"            type="string"/>
                <parameter name="email"                     value="{email}"                 type="string"/>
                <parameter name="previos-firstname"         value="{previosFirstname}"      type="string"/>
                <parameter name="previos-surname"           value="{previosSurname}"        type="string"/>
                <parameter name="previos-patr"              value="{previosPatr}"           type="string"/>
                <parameter name="passport-series"           value="{passportSeries}"        type="string"/>
                <parameter name="passport-number"           value="{passportNumber}"        type="string"/>
                <parameter name="passport-issue-by"         value="{passportIssueBy}"       type="string"/>
                <parameter name="passport-issue-by-code"    value="{passportIssueByCode}"   type="string"/>
                <parameter name="passport-issue-date"       value="{passportIssueDate}"     type="date"/>
                <parameter name="mobileTelecom"             value="{mobileTelecom}"         type="string"/>
                <parameter name="mobileNumber"              value="{mobileNumber}"          type="string"/>
                <parameter name="shortLoanId"               value="{shortLoanId}"           type="long"/>
                <parameter name="inWaitTM"                  value="{inWaitTM}"              type="boolean"/>
            </request-parameters>

            <initial-state-parameters>
                <parameter name="loanOfferId"               value="{loanOfferId}"           type="string"/>
                <parameter name="campaignMemberId"          value="{campaignMemberId}"      type="string"/>
                <parameter name="condId"                    value="{condId}"                type="long"/>
                <parameter name="condCurrId"                value="{condCurrId}"            type="long"/>
                <parameter name="loanAmount"                value="{loanAmount}"            type="decimal"/>
                <parameter name="loanPeriod"                value="{loanPeriod}"            type="long"/>
                <parameter name="useLoanOffer"              value="{useLoanOffer}"          type="boolean"/>
                <parameter name="usePercent"                value="{usePercent}"            type="boolean"/>
                <parameter name="fromLoanAmount"            value="{fromLoanAmount}"        type="decimal"/>
                <parameter name="loanOfferAmount"           value="{loanOfferAmount}"       type="decimal"/>
                <parameter name="jsonCondition"             value="{jsonCondition}"         type="string"/>
                <parameter name="jsonCurrency"              value="{jsonCurrency}"          type="string"/>
            </initial-state-parameters>

            <initial2-state-parameters>

                <!--информация о клиенте, первый шаг-->
                <parameter name="sbrf-relation-type" value="{sbrfRelationType}" type="string"/>
                <parameter name="sbrf-account-select" value="{sbrfAccountSelect}" type="string"/>
                <parameter name="sbrf-account" value="{sbrfAccount}" type="string"/>
                <parameter name="sbrf-resource" value="{sbrfResource}" type="string"/>
                <parameter name="sbrf-resource-id" value="{sbrfResourceId}" type="string"/>
                <parameter name="gender"   value="{gender}"   type="string"/>
                <parameter name="birthPlace" value="{birthPlace}" type="string"/>
                <parameter name="citizenShip" value="{citizenShip}" type="string"/>
                <parameter name="email" value="{email}" type="string"/>
                <parameter name="fio-changed" value="{fioChanged}" type="boolean"/>
                <parameter name="fio-changed-date" value="{fioChangedDate}" type="date"/>
                <parameter name="previos-firstname" value="{previosFirstname}" type="string"/>
                <parameter name="previos-surname" value="{previosSurname}" type="string"/>
                <parameter name="previos-patr" value="{previosPatr}" type="string"/>
                <parameter name="fio-changed-reason-type" value="{fioChangedReasonType}" type="string"/>
                <parameter name="fio-changed-other-reason" value="{fioChangedOtherReason}" type="string"/>
                <parameter name="passport-issue-by" value="{passportIssueBy}" type="string"/>
                <parameter name="passport-issue-by-code" value="{passportIssueByCode}" type="string"/>
                <parameter name="passport-issue-date" value="{passportIssueDate}" type="date"/>
                <parameter name="has-old-passport" value="{hasOldPassport}" type="boolean"/>
                <parameter name="old-passport-issue-date" value="{oldPassportIssueDate}" type="date"/>
                <parameter name="old-passport-series-and-number" value="{oldPassportSeriesAndNumber}" type="string"/>
                <parameter name="old-passport-issue-by" value="{oldPassportIssueBy}" type="string"/>
                <parameter name="has-foreign-passport" value="{hasForeignPassport}" type="boolean"/>
                <parameter name="education-type" value="{educationTypeSelect}" type="string"/>
                <parameter name="higher-education-course" value="{higherEducationCourse}" type="string"/>
                <parameter name="inn" value="{inn}" type="string"/>

                <!-- Телефоны -->
                <parameter name="mobileCountry"         value="{mobileCountry}"        type="string"/>
                <parameter name="mobileTelecom"         value="{mobileTelecom}"        type="string"/>
                <parameter name="mobileNumber"          value="{mobileNumber}"         type="string"/>
                <parameter name="jobphoneCountry"       value="{jobphoneCountry}"      type="string"/>
                <parameter name="jobphoneTelecom"       value="{jobphoneTelecom}"      type="string"/>
                <parameter name="jobphoneNumber"        value="{jobphoneNumber}"       type="string"/>
                <parameter name="residencePhoneCountry" value="{residencePhoneCountry}" type="string"/>
                <parameter name="residencePhoneTelecom" value="{residencePhoneTelecom}" type="string"/>
                <parameter name="residencePhoneNumber"  value="{residencePhoneNumber}"  type="string"/>

                <parameter name="phoneBlank_1_type"      value="{phoneBlank_1_type}"    type="string"/>
                <parameter name="phoneBlank_1_country"   value="{phoneBlank_1_country}" type="string"/>
                <parameter name="phoneBlank_1_telecom"   value="{phoneBlank_1_telecom}" type="string"/>
                <parameter name="phoneBlank_1_number"    value="{phoneBlank_1_number}"  type="string"/>
                <parameter name="stepFilled-INITIAL2"    value="{stepFilled-INITIAL2}"  type="boolean"/>
            </initial2-state-parameters>

            <initial3-state-parameters>

                <!--
                  ******************************************************************
                     Поля шага 3 «Семья и родственники»
                  ******************************************************************
                 -->
                <parameter name="family-status" value="{familyStatusSelect}" type="string"/>
                <parameter name="partnerSurname" value="{partnerSurname}" type="string"/>
                <parameter name="partnerFirstname" value="{partnerFirstname}" type="string"/>
                <parameter name="partnerPatr" value="{partnerPatr}" type="string"/>
                <parameter name="partnerBirthday" value="{partnerBirthday}" type="date"/>
                <parameter name="partnerOnDependent" value="{partnerOnDependent}" type="string"/>
                <parameter name="partnerHasLoansInSber" value="{partnerHasLoansInSber}" type="string"/>
                <parameter name="hasPrenup" value="{hasPrenup}" type="string"/>

                 <!--Информация о детях-->

                <parameter name="child_1_relativeType" value="{child_1_relativeType}" type="string"/>
                <parameter name="child_1_surname" value="{child_1_surname}" type="string"/>
                <parameter name="child_1_name" value="{child_1_name}" type="string"/>
                <parameter name="child_1_patrName" value="{child_1_patrName}" type="string"/>
                <parameter name="child_1_birthday" value="{child_1_birthday}" type="date"/>
                <parameter name="child_1_dependent" value="{child_1_dependent}" type="string"/>
                <parameter name="child_1_credit" value="{child_1_credit}" type="string"/>
                <parameter name="child_1_employee" value="{child_1_employee}" type="boolean"/>
                <parameter name="child_1_employeePlace" value="{child_1_employeePlace}" type="string"/>

                <parameter name="child_2_relativeType" value="{child_2_relativeType}" type="string"/>
                <parameter name="child_2_surname" value="{child_2_surname}" type="string"/>
                <parameter name="child_2_name" value="{child_2_name}" type="string"/>
                <parameter name="child_2_patrName" value="{child_2_patrName}" type="string"/>
                <parameter name="child_2_birthday" value="{child_2_birthday}" type="date"/>
                <parameter name="child_2_dependent" value="{child_2_dependent}" type="string"/>
                <parameter name="child_2_credit" value="{child_2_credit}" type="string"/>
                <parameter name="child_2_employee" value="{child_2_employee}" type="boolean"/>
                <parameter name="child_2_employeePlace" value="{child_2_employeePlace}" type="string"/>

                <parameter name="child_3_relativeType" value="{child_3_relativeType}" type="string"/>
                <parameter name="child_3_surname" value="{child_3_surname}" type="string"/>
                <parameter name="child_3_name" value="{child_3_name}" type="string"/>
                <parameter name="child_3_patrName" value="{child_3_patrName}" type="string"/>
                <parameter name="child_3_birthday" value="{child_3_birthday}" type="date"/>
                <parameter name="child_3_dependent" value="{child_3_dependent}" type="string"/>
                <parameter name="child_3_credit" value="{child_3_credit}" type="string"/>
                <parameter name="child_3_employee" value="{child_3_employee}" type="boolean"/>
                <parameter name="child_3_employeePlace" value="{child_3_employeePlace}" type="string"/>

                <parameter name="child_4_relativeType" value="{child_4_relativeType}" type="string"/>
                <parameter name="child_4_surname" value="{child_4_surname}" type="string"/>
                <parameter name="child_4_name" value="{child_4_name}" type="string"/>
                <parameter name="child_4_patrName" value="{child_4_patrName}" type="string"/>
                <parameter name="child_4_birthday" value="{child_4_birthday}" type="date"/>
                <parameter name="child_4_dependent" value="{child_4_dependent}" type="string"/>
                <parameter name="child_4_credit" value="{child_4_credit}" type="string"/>
                <parameter name="child_4_employee" value="{child_4_employee}" type="boolean"/>
                <parameter name="child_4_employeePlace" value="{child_4_employeePlace}" type="string"/>

                <parameter name="child_5_relativeType" value="{child_5_relativeType}" type="string"/>
                <parameter name="child_5_surname" value="{child_5_surname}" type="string"/>
                <parameter name="child_5_name" value="{child_5_name}" type="string"/>
                <parameter name="child_5_patrName" value="{child_5_patrName}" type="string"/>
                <parameter name="child_5_birthday" value="{child_5_birthday}" type="date"/>
                <parameter name="child_5_dependent" value="{child_5_dependent}" type="string"/>
                <parameter name="child_5_credit" value="{child_5_credit}" type="string"/>
                <parameter name="child_5_employee" value="{child_5_employee}" type="boolean"/>
                <parameter name="child_5_employeePlace" value="{child_5_employeePlace}" type="string"/>

                <parameter name="child_6_relativeType" value="{child_6_relativeType}" type="string"/>
                <parameter name="child_6_surname" value="{child_6_surname}" type="string"/>
                <parameter name="child_6_name" value="{child_6_name}" type="string"/>
                <parameter name="child_6_patrName" value="{child_6_patrName}" type="string"/>
                <parameter name="child_6_birthday" value="{child_6_birthday}" type="date"/>
                <parameter name="child_6_dependent" value="{child_6_dependent}" type="string"/>
                <parameter name="child_6_credit" value="{child_6_credit}" type="string"/>
                <parameter name="child_6_employee" value="{child_6_employee}" type="boolean"/>
                <parameter name="child_6_employeePlace" value="{child_6_employeePlace}" type="string"/>

                <parameter name="child_7_relativeType" value="{child_7_relativeType}" type="string"/>
                <parameter name="child_7_surname" value="{child_7_surname}" type="string"/>
                <parameter name="child_7_name" value="{child_7_name}" type="string"/>
                <parameter name="child_7_patrName" value="{child_7_patrName}" type="string"/>
                <parameter name="child_7_birthday" value="{child_7_birthday}" type="date"/>
                <parameter name="child_7_dependent" value="{child_7_dependent}" type="string"/>
                <parameter name="child_7_credit" value="{child_7_credit}" type="string"/>
                <parameter name="child_7_employee" value="{child_7_employee}" type="boolean"/>
                <parameter name="child_7_employeePlace" value="{child_7_employeePlace}" type="string"/>

                <parameter name="child_8_relativeType" value="{child_8_relativeType}" type="string"/>
                <parameter name="child_8_surname" value="{child_8_surname}" type="string"/>
                <parameter name="child_8_name" value="{child_8_name}" type="string"/>
                <parameter name="child_8_patrName" value="{child_8_patrName}" type="string"/>
                <parameter name="child_8_birthday" value="{child_8_birthday}" type="date"/>
                <parameter name="child_8_dependent" value="{child_8_dependent}" type="string"/>
                <parameter name="child_8_credit" value="{child_8_credit}" type="string"/>
                <parameter name="child_8_employee" value="{child_8_employee}" type="boolean"/>
                <parameter name="child_8_employeePlace" value="{child_8_employeePlace}" type="string"/>

                <parameter name="child_9_relativeType" value="{child_9_relativeType}" type="string"/>
                <parameter name="child_9_surname" value="{child_9_surname}" type="string"/>
                <parameter name="child_9_name" value="{child_9_name}" type="string"/>
                <parameter name="child_9_patrName" value="{child_9_patrName}" type="string"/>
                <parameter name="child_9_birthday" value="{child_9_birthday}" type="date"/>
                <parameter name="child_9_dependent" value="{child_9_dependent}" type="string"/>
                <parameter name="child_9_credit" value="{child_9_credit}" type="string"/>
                <parameter name="child_9_employee" value="{child_9_employee}" type="boolean"/>
                <parameter name="child_9_employeePlace" value="{child_9_employeePlace}" type="string"/>

                <parameter name="child_10_relativeType" value="{child_10_relativeType}" type="string"/>
                <parameter name="child_10_surname" value="{child_10_surname}" type="string"/>
                <parameter name="child_10_name" value="{child_10_name}" type="string"/>
                <parameter name="child_10_patrName" value="{child_10_patrName}" type="string"/>
                <parameter name="child_10_birthday" value="{child_10_birthday}" type="date"/>
                <parameter name="child_10_dependent" value="{child_10_dependent}" type="string"/>
                <parameter name="child_10_credit" value="{child_10_credit}" type="string"/>
                <parameter name="child_10_employee" value="{child_10_employee}" type="boolean"/>
                <parameter name="child_10_employeePlace" value="{child_10_employeePlace}" type="string"/>

                <parameter name="child_11_relativeType" value="{child_11_relativeType}" type="string"/>
                <parameter name="child_11_surname" value="{child_11_surname}" type="string"/>
                <parameter name="child_11_name" value="{child_11_name}" type="string"/>
                <parameter name="child_11_patrName" value="{child_11_patrName}" type="string"/>
                <parameter name="child_11_birthday" value="{child_11_birthday}" type="date"/>
                <parameter name="child_11_dependent" value="{child_11_dependent}" type="string"/>
                <parameter name="child_11_credit" value="{child_11_credit}" type="string"/>
                <parameter name="child_11_employee" value="{child_11_employee}" type="boolean"/>
                <parameter name="child_11_employeePlace" value="{child_11_employeePlace}" type="string"/>

                <parameter name="child_12_relativeType" value="{child_12_relativeType}" type="string"/>
                <parameter name="child_12_surname" value="{child_12_surname}" type="string"/>
                <parameter name="child_12_name" value="{child_12_name}" type="string"/>
                <parameter name="child_12_patrName" value="{child_12_patrName}" type="string"/>
                <parameter name="child_12_birthday" value="{child_12_birthday}" type="date"/>
                <parameter name="child_12_dependent" value="{child_12_dependent}" type="string"/>
                <parameter name="child_12_credit" value="{child_12_credit}" type="string"/>
                <parameter name="child_12_employee" value="{child_12_employee}" type="boolean"/>
                <parameter name="child_12_employeePlace" value="{child_12_employeePlace}" type="string"/>

                <!--Информация о родственниках-->

                <parameter name="relative_1_relativeType" value="{relative_1_relativeType}" type="string"/>
                <parameter name="relative_1_surname" value="{relative_1_surname}" type="string"/>
                <parameter name="relative_1_name" value="{relative_1_name}" type="string"/>
                <parameter name="relative_1_patrName" value="{relative_1_patrName}" type="string"/>
                <parameter name="relative_1_birthday" value="{relative_1_birthday}" type="date"/>
                <parameter name="relative_1_dependent" value="{relative_1_dependent}" type="string"/>
                <parameter name="relative_1_credit" value="{relative_1_credit}" type="string"/>
                <parameter name="relative_1_employee" value="{relative_1_employee}" type="boolean"/>
                <parameter name="relative_1_employeePlace" value="{relative_1_employeePlace}" type="string"/>

                <parameter name="relative_2_relativeType" value="{relative_2_relativeType}" type="string"/>
                <parameter name="relative_2_surname" value="{relative_2_surname}" type="string"/>
                <parameter name="relative_2_name" value="{relative_2_name}" type="string"/>
                <parameter name="relative_2_patrName" value="{relative_2_patrName}" type="string"/>
                <parameter name="relative_2_birthday" value="{relative_2_birthday}" type="date"/>
                <parameter name="relative_2_dependent" value="{relative_2_dependent}" type="string"/>
                <parameter name="relative_2_credit" value="{relative_2_credit}" type="string"/>
                <parameter name="relative_2_employee" value="{relative_2_employee}" type="boolean"/>
                <parameter name="relative_2_employeePlace" value="{relative_2_employeePlace}" type="string"/>

                <parameter name="relative_3_relativeType" value="{relative_3_relativeType}" type="string"/>
                <parameter name="relative_3_surname" value="{relative_3_surname}" type="string"/>
                <parameter name="relative_3_name" value="{relative_3_name}" type="string"/>
                <parameter name="relative_3_patrName" value="{relative_3_patrName}" type="string"/>
                <parameter name="relative_3_birthday" value="{relative_3_birthday}" type="date"/>
                <parameter name="relative_3_dependent" value="{relative_3_dependent}" type="string"/>
                <parameter name="relative_3_credit" value="{relative_3_credit}" type="string"/>
                <parameter name="relative_3_employee" value="{relative_3_employee}" type="boolean"/>
                <parameter name="relative_3_employeePlace" value="{relative_3_employeePlace}" type="string"/>

                <parameter name="relative_4_relativeType" value="{relative_4_relativeType}" type="string"/>
                <parameter name="relative_4_surname" value="{relative_4_surname}" type="string"/>
                <parameter name="relative_4_name" value="{relative_4_name}" type="string"/>
                <parameter name="relative_4_patrName" value="{relative_4_patrName}" type="string"/>
                <parameter name="relative_4_birthday" value="{relative_4_birthday}" type="date"/>
                <parameter name="relative_4_dependent" value="{relative_4_dependent}" type="string"/>
                <parameter name="relative_4_credit" value="{relative_4_credit}" type="string"/>
                <parameter name="relative_4_employee" value="{relative_4_employee}" type="boolean"/>
                <parameter name="relative_4_employeePlace" value="{relative_4_employeePlace}" type="string"/>

                <parameter name="relative_5_relativeType" value="{relative_5_relativeType}" type="string"/>
                <parameter name="relative_5_surname" value="{relative_5_surname}" type="string"/>
                <parameter name="relative_5_name" value="{relative_5_name}" type="string"/>
                <parameter name="relative_5_patrName" value="{relative_5_patrName}" type="string"/>
                <parameter name="relative_5_birthday" value="{relative_5_birthday}" type="date"/>
                <parameter name="relative_5_dependent" value="{relative_5_dependent}" type="string"/>
                <parameter name="relative_5_credit" value="{relative_5_credit}" type="string"/>
                <parameter name="relative_5_employee" value="{relative_5_employee}" type="boolean"/>
                <parameter name="relative_5_employeePlace" value="{relative_5_employeePlace}" type="string"/>

                <parameter name="relative_6_relativeType" value="{relative_6_relativeType}" type="string"/>
                <parameter name="relative_6_surname" value="{relative_6_surname}" type="string"/>
                <parameter name="relative_6_name" value="{relative_6_name}" type="string"/>
                <parameter name="relative_6_patrName" value="{relative_6_patrName}" type="string"/>
                <parameter name="relative_6_birthday" value="{relative_6_birthday}" type="date"/>
                <parameter name="relative_6_dependent" value="{relative_6_dependent}" type="string"/>
                <parameter name="relative_6_credit" value="{relative_6_credit}" type="string"/>
                <parameter name="relative_6_employee" value="{relative_6_employee}" type="boolean"/>
                <parameter name="relative_6_employeePlace" value="{relative_6_employeePlace}" type="string"/>

                <parameter name="relative_7_relativeType" value="{relative_7_relativeType}" type="string"/>
                <parameter name="relative_7_surname" value="{relative_7_surname}" type="string"/>
                <parameter name="relative_7_name" value="{relative_7_name}" type="string"/>
                <parameter name="relative_7_patrName" value="{relative_7_patrName}" type="string"/>
                <parameter name="relative_7_birthday" value="{relative_7_birthday}" type="date"/>
                <parameter name="relative_7_dependent" value="{relative_7_dependent}" type="string"/>
                <parameter name="relative_7_credit" value="{relative_7_credit}" type="string"/>
                <parameter name="relative_7_employee" value="{relative_7_employee}" type="boolean"/>
                <parameter name="relative_7_employeePlace" value="{relative_7_employeePlace}" type="string"/>

                <parameter name="relative_8_relativeType" value="{relative_8_relativeType}" type="string"/>
                <parameter name="relative_8_surname" value="{relative_8_surname}" type="string"/>
                <parameter name="relative_8_name" value="{relative_8_name}" type="string"/>
                <parameter name="relative_8_patrName" value="{relative_8_patrName}" type="string"/>
                <parameter name="relative_8_birthday" value="{relative_8_birthday}" type="date"/>
                <parameter name="relative_8_dependent" value="{relative_8_dependent}" type="string"/>
                <parameter name="relative_8_credit" value="{relative_8_credit}" type="string"/>
                <parameter name="relative_8_employee" value="{relative_8_employee}" type="boolean"/>
                <parameter name="relative_8_employeePlace" value="{relative_8_employeePlace}" type="string"/>

                <parameter name="relative_9_relativeType" value="{relative_9_relativeType}" type="string"/>
                <parameter name="relative_9_surname" value="{relative_9_surname}" type="string"/>
                <parameter name="relative_9_name" value="{relative_9_name}" type="string"/>
                <parameter name="relative_9_patrName" value="{relative_9_patrName}" type="string"/>
                <parameter name="relative_9_birthday" value="{relative_9_birthday}" type="date"/>
                <parameter name="relative_9_dependent" value="{relative_9_dependent}" type="string"/>
                <parameter name="relative_9_credit" value="{relative_9_credit}" type="string"/>
                <parameter name="relative_9_employee" value="{relative_9_employee}" type="boolean"/>
                <parameter name="relative_9_employeePlace" value="{relative_9_employeePlace}" type="string"/>

                <parameter name="relative_10_relativeType" value="{relative_10_relativeType}" type="string"/>
                <parameter name="relative_10_surname" value="{relative_10_surname}" type="string"/>
                <parameter name="relative_10_name" value="{relative_10_name}" type="string"/>
                <parameter name="relative_10_patrName" value="{relative_10_patrName}" type="string"/>
                <parameter name="relative_10_birthday" value="{relative_10_birthday}" type="date"/>
                <parameter name="relative_10_dependent" value="{relative_10_dependent}" type="string"/>
                <parameter name="relative_10_credit" value="{relative_10_credit}" type="string"/>
                <parameter name="relative_10_employee" value="{relative_10_employee}" type="boolean"/>
                <parameter name="relative_10_employeePlace" value="{relative_10_employeePlace}" type="string"/>

                <parameter name="relative_11_relativeType" value="{relative_11_relativeType}" type="string"/>
                <parameter name="relative_11_surname" value="{relative_11_surname}" type="string"/>
                <parameter name="relative_11_name" value="{relative_11_name}" type="string"/>
                <parameter name="relative_11_patrName" value="{relative_11_patrName}" type="string"/>
                <parameter name="relative_11_birthday" value="{relative_11_birthday}" type="date"/>
                <parameter name="relative_11_dependent" value="{relative_11_dependent}" type="string"/>
                <parameter name="relative_11_credit" value="{relative_11_credit}" type="string"/>
                <parameter name="relative_11_employee" value="{relative_11_employee}" type="boolean"/>
                <parameter name="relative_11_employeePlace" value="{relative_11_employeePlace}" type="string"/>

                <parameter name="relative_12_relativeType" value="{relative_12_relativeType}" type="string"/>
                <parameter name="relative_12_surname" value="{relative_12_surname}" type="string"/>
                <parameter name="relative_12_name" value="{relative_12_name}" type="string"/>
                <parameter name="relative_12_patrName" value="{relative_12_patrName}" type="string"/>
                <parameter name="relative_12_birthday" value="{relative_12_birthday}" type="date"/>
                <parameter name="relative_12_dependent" value="{relative_12_dependent}" type="string"/>
                <parameter name="relative_12_credit" value="{relative_12_credit}" type="string"/>
                <parameter name="relative_12_employee" value="{relative_12_employee}" type="boolean"/>
                <parameter name="relative_12_employeePlace" value="{relative_12_employeePlace}" type="string"/>

                <parameter name="stepFilled-INITIAL3"    value="{stepFilled-INITIAL3}"  type="boolean"/>
            </initial3-state-parameters>

            <initial4-state-parameters>
                <!--
                  ******************************************************************
                     Поля шага 4 «Прописка»
                  ******************************************************************
                 -->

                <parameter name="registration-type-1"   value="{registrationType1}"   type="string"/>
                <parameter name="region-1" value="{regionSelect1}" type="string"/>
                <parameter name="district-type-1" value="{districtTypeSelect1}" type="string"/>
                <parameter name="district-code-1" value="{districtCode1}" type="string"/>
                <parameter name="district-1" value="{district1}" type="string"/>
                <parameter name="city-type-1" value="{cityTypeSelect1}" type="string"/>
                <parameter name="city-1" value="{city1}" type="string"/>
                <parameter name="city-code-1" value="{cityCode1}" type="string"/>
                <parameter name="locality-type-1" value="{localityTypeSelect1}" type="string"/>
                <parameter name="locality-1" value="{locality1}" type="string"/>
                <parameter name="locality-code-1" value="{localityCode1}" type="string"/>
                <parameter name="street-type-1" value="{streetTypeSelect1}" type="string"/>
                <parameter name="street-1" value="{street1}" type="string"/>
                <parameter name="house-1" value="{house1}" type="string"/>
                <parameter name="building-1" value="{building1}" type="string"/>
                <parameter name="construction-1" value="{construction1}" type="string"/>
                <parameter name="flat-1" value="{flat1}" type="string"/>
                <parameter name="flat-with-num-1" value="{flatWithNum1}" type="boolean"/>
                <parameter name="index-1" value="{index1}" type="string"/>

                <parameter name="registration-type-2"   value="{registrationType2}"   type="string"/>
                <parameter name="region-2" value="{regionSelect2}" type="string"/>
                <parameter name="district-type-2" value="{districtTypeSelect2}" type="string"/>
                <parameter name="district-code-2" value="{districtCode2}" type="string"/>
                <parameter name="district-2" value="{district2}" type="string"/>
                <parameter name="city-type-2" value="{cityTypeSelect2}" type="string"/>
                <parameter name="city-2" value="{city2}" type="string"/>
                <parameter name="city-code-2" value="{cityCode2}" type="string"/>
                <parameter name="locality-type-2" value="{localityTypeSelect2}" type="string"/>
                <parameter name="locality-2" value="{locality2}" type="string"/>
                <parameter name="locality-code-2" value="{localityCode2}" type="string"/>
                <parameter name="street-type-2" value="{streetTypeSelect2}" type="string"/>
                <parameter name="street-2" value="{street2}" type="string"/>
                <parameter name="house-2" value="{house2}" type="string"/>
                <parameter name="building-2" value="{building2}" type="string"/>
                <parameter name="construction-2" value="{construction2}" type="string"/>
                <parameter name="flat-2" value="{flat2}" type="string"/>
                <parameter name="flat-with-num-2" value="{flatWithNum2}" type="boolean"/>
                <parameter name="index-2" value="{index2}" type="string"/>

                <parameter name="registration-type-3"   value="{registrationType3}"   type="string"/>
                <parameter name="region-3" value="{regionSelect3}" type="string"/>
                <parameter name="district-type-3" value="{districtTypeSelect3}" type="string"/>
                <parameter name="district-code-3" value="{districtCode3}" type="string"/>
                <parameter name="district-3" value="{district3}" type="string"/>
                <parameter name="city-type-3" value="{cityTypeSelect3}" type="string"/>
                <parameter name="city-3" value="{city3}" type="string"/>
                <parameter name="city-code-3" value="{cityCode3}" type="string"/>
                <parameter name="locality-type-3" value="{localityTypeSelect3}" type="string"/>
                <parameter name="locality-3" value="{locality3}" type="string"/>
                <parameter name="locality-code-3" value="{localityCode3}" type="string"/>
                <parameter name="street-type-3" value="{streetTypeSelect3}" type="string"/>
                <parameter name="street-3" value="{street3}" type="string"/>
                <parameter name="house-3" value="{house3}" type="string"/>
                <parameter name="building-3" value="{building3}" type="string"/>
                <parameter name="construction-3" value="{construction3}" type="string"/>
                <parameter name="flat-3" value="{flat3}" type="string"/>
                <parameter name="flat-with-num-3" value="{flatWithNum3}" type="boolean"/>
                <parameter name="index-3" value="{index3}" type="string"/>

                <parameter name="registration-expiry-date" value="{registrationExpiryDate}" type="date"/>
                <parameter name="residence-right" value="{residenceRightSelect}" type="string"/>
                <parameter name="residence-duration" value="{residenceDuration}" type="long"/>
                <parameter name="fact-address-duration" value="{factAddressDuration}" type="long"/>

                <parameter name="stepFilled-INITIAL4"    value="{stepFilled-INITIAL4}"  type="boolean"/>

            </initial4-state-parameters>
            <initial5-state-parameters>

                <!--
                 ******************************************************************
                    Поля шага 5 «Работа и доход»
                 ******************************************************************
                -->
                <parameter name="work-places-count"        value="{workPlacesCount}"        type="string"/>
                <parameter name="basic-income"             value="{basicIncome}"             type="decimal"/>
                <parameter name="pensioner-basic-income"   value="{pensionerBasicIncome}"    type="decimal"/>
                <parameter name="additional-income"        value="{additionalIncome}"        type="decimal"/>
                <parameter name="monthly-income"           value="{monthlyIncome}"           type="decimal"/>
                <parameter name="monthly-expense"          value="{monthlyExpense}"          type="decimal"/>

                <parameter name="is-sber-employee"           value="{isSberEmployee}"         type="boolean"/>
                <parameter name="company-activity-scope"     value="{companyActivityScope}"   type="string"/>
                <parameter name="company-activity-comment"   value="{companyActivityComment}" type="string"/>
                <parameter name="contract-type"              value="{contractType}"           type="string"/>
                <parameter name="incorporation-type"         value="{incorporationType}"      type="string"/>
                <parameter name="company-full-name"          value="{companyFullName}"        type="string"/>
                <parameter name="company-inn"                value="{companyInn}"             type="string"/>
                <parameter name="position"                   value="{employeePosition}"       type="string"/>
                <parameter name="position-type"              value="{employeePositionType}"   type="string"/>
                <parameter name="private-practice"           value="{privatePractice}"        type="string"/>
                <parameter name="number-of-employees"        value="{numberOfEmployees}"      type="string"/>
                <parameter name="seniority"                  value="{seniority}"              type="string"/>

                <parameter name="department"                 value="{department}"             type="string"/>
                <parameter name="department-full-name"       value="{departmentFullName}"     type="string"/>
                <parameter name="is-tb-chairman"             value="{isTBChairman}"           type="boolean"/>

                <parameter name="stepFilled-INITIAL5"    value="{stepFilled-INITIAL5}"  type="boolean"/>

            </initial5-state-parameters>
            <initial6-state-parameters>
                <!--
                 ******************************************************************
                    Поля шага 6 «Собственность
                 ******************************************************************
                -->
                <parameter name="realty_1_realtyType" value="{realty_1_realtyType}" type="string"/>
                <parameter name="realty_1_address" value="{realty_1_address}" type="string"/>
                <parameter name="realty_1_yearOfPurchase" value="{realty_1_yearOfPurchase}" type="string"/>
                <parameter name="realty_1_square" value="{realty_1_square}" type="string"/>
                <parameter name="realty_1_typeOfSquareUnit" value="{realty_1_typeOfSquareUnit}" type="string"/>
                <parameter name="realty_1_approxMarketValue" value="{realty_1_approxMarketValue}" type="string"/>

                <parameter name="realty_2_realtyType" value="{realty_2_realtyType}" type="string"/>
                <parameter name="realty_2_address" value="{realty_2_address}" type="string"/>
                <parameter name="realty_2_yearOfPurchase" value="{realty_2_yearOfPurchase}" type="string"/>
                <parameter name="realty_2_square" value="{realty_2_square}" type="string"/>
                <parameter name="realty_2_typeOfSquareUnit" value="{realty_2_typeOfSquareUnit}" type="string"/>
                <parameter name="realty_2_approxMarketValue" value="{realty_2_approxMarketValue}" type="string"/>

                <parameter name="realty_3_realtyType" value="{realty_3_realtyType}" type="string"/>
                <parameter name="realty_3_address" value="{realty_3_address}" type="string"/>
                <parameter name="realty_3_yearOfPurchase" value="{realty_3_yearOfPurchase}" type="string"/>
                <parameter name="realty_3_square" value="{realty_3_square}" type="string"/>
                <parameter name="realty_3_typeOfSquareUnit" value="{realty_3_typeOfSquareUnit}" type="string"/>
                <parameter name="realty_3_approxMarketValue" value="{realty_3_approxMarketValue}" type="string"/>

                <parameter name="realty_4_realtyType" value="{realty_4_realtyType}" type="string"/>
                <parameter name="realty_4_address" value="{realty_4_address}" type="string"/>
                <parameter name="realty_4_yearOfPurchase" value="{realty_4_yearOfPurchase}" type="string"/>
                <parameter name="realty_4_square" value="{realty_4_square}" type="string"/>
                <parameter name="realty_4_typeOfSquareUnit" value="{realty_4_typeOfSquareUnit}" type="string"/>
                <parameter name="realty_4_approxMarketValue" value="{realty_4_approxMarketValue}" type="string"/>

                <parameter name="realty_5_realtyType" value="{realty_5_realtyType}" type="string"/>
                <parameter name="realty_5_address" value="{realty_5_address}" type="string"/>
                <parameter name="realty_5_yearOfPurchase" value="{realty_5_yearOfPurchase}" type="string"/>
                <parameter name="realty_5_square" value="{realty_5_square}" type="string"/>
                <parameter name="realty_5_typeOfSquareUnit" value="{realty_5_typeOfSquareUnit}" type="string"/>
                <parameter name="realty_5_approxMarketValue" value="{realty_5_approxMarketValue}" type="string"/>

                <parameter name="realty_6_realtyType" value="{realty_6_realtyType}" type="string"/>
                <parameter name="realty_6_address" value="{realty_6_address}" type="string"/>
                <parameter name="realty_6_yearOfPurchase" value="{realty_6_yearOfPurchase}" type="string"/>
                <parameter name="realty_6_square" value="{realty_6_square}" type="string"/>
                <parameter name="realty_6_typeOfSquareUnit" value="{realty_6_typeOfSquareUnit}" type="string"/>
                <parameter name="realty_6_approxMarketValue" value="{realty_6_approxMarketValue}" type="string"/>

                <parameter name="realty_7_realtyType" value="{realty_7_realtyType}" type="string"/>
                <parameter name="realty_7_address" value="{realty_7_address}" type="string"/>
                <parameter name="realty_7_yearOfPurchase" value="{realty_7_yearOfPurchase}" type="string"/>
                <parameter name="realty_7_square" value="{realty_7_square}" type="string"/>
                <parameter name="realty_7_typeOfSquareUnit" value="{realty_7_typeOfSquareUnit}" type="string"/>
                <parameter name="realty_7_approxMarketValue" value="{realty_7_approxMarketValue}" type="string"/>

                <parameter name="transport_1_transportType" value="{transport_1_transportType}" type="string"/>
                <parameter name="transport_1_registrationNumber" value="{transport_1_registrationNumber}" type="string"/>
                <parameter name="transport_1_brand" value="{transport_1_brand}" type="string"/>
                <parameter name="transport_1_approxMarketValue" value="{transport_1_approxMarketValue}" type="string"/>
                <parameter name="transport_1_ageOfTransport" value="{transport_1_ageOfTransport}" type="string"/>
                <parameter name="transport_1_yearOfPurchase" value="{transport_1_yearOfPurchase}" type="string"/>

                <parameter name="transport_2_transportType" value="{transport_2_transportType}" type="string"/>
                <parameter name="transport_2_registrationNumber" value="{transport_2_registrationNumber}" type="string"/>
                <parameter name="transport_2_brand" value="{transport_2_brand}" type="string"/>
                <parameter name="transport_2_approxMarketValue" value="{transport_2_approxMarketValue}" type="string"/>
                <parameter name="transport_2_ageOfTransport" value="{transport_2_ageOfTransport}" type="string"/>
                <parameter name="transport_2_yearOfPurchase" value="{transport_2_yearOfPurchase}" type="string"/>

                <parameter name="transport_3_transportType" value="{transport_3_transportType}" type="string"/>
                <parameter name="transport_3_registrationNumber" value="{transport_3_registrationNumber}" type="string"/>
                <parameter name="transport_3_brand" value="{transport_3_brand}" type="string"/>
                <parameter name="transport_3_approxMarketValue" value="{transport_3_approxMarketValue}" type="string"/>
                <parameter name="transport_3_ageOfTransport" value="{transport_3_ageOfTransport}" type="string"/>
                <parameter name="transport_3_yearOfPurchase" value="{transport_3_yearOfPurchase}" type="string"/>

                <parameter name="transport_4_transportType" value="{transport_4_transportType}" type="string"/>
                <parameter name="transport_4_registrationNumber" value="{transport_4_registrationNumber}" type="string"/>
                <parameter name="transport_4_brand" value="{transport_4_brand}" type="string"/>
                <parameter name="transport_4_approxMarketValue" value="{transport_4_approxMarketValue}" type="string"/>
                <parameter name="transport_4_ageOfTransport" value="{transport_4_ageOfTransport}" type="string"/>
                <parameter name="transport_4_yearOfPurchase" value="{transport_4_yearOfPurchase}" type="string"/>

                <parameter name="transport_5_transportType" value="{transport_5_transportType}" type="string"/>
                <parameter name="transport_5_registrationNumber" value="{transport_5_registrationNumber}" type="string"/>
                <parameter name="transport_5_brand" value="{transport_5_brand}" type="string"/>
                <parameter name="transport_5_approxMarketValue" value="{transport_5_approxMarketValue}" type="string"/>
                <parameter name="transport_5_ageOfTransport" value="{transport_5_ageOfTransport}" type="string"/>
                <parameter name="transport_5_yearOfPurchase" value="{transport_5_yearOfPurchase}" type="string"/>

                <parameter name="transport_6_transportType" value="{transport_6_transportType}" type="string"/>
                <parameter name="transport_6_registrationNumber" value="{transport_6_registrationNumber}" type="string"/>
                <parameter name="transport_6_brand" value="{transport_6_brand}" type="string"/>
                <parameter name="transport_6_approxMarketValue" value="{transport_6_approxMarketValue}" type="string"/>
                <parameter name="transport_6_ageOfTransport" value="{transport_6_ageOfTransport}" type="string"/>
                <parameter name="transport_6_yearOfPurchase" value="{transport_6_yearOfPurchase}" type="string"/>

                <parameter name="transport_7_transportType" value="{transport_7_transportType}" type="string"/>
                <parameter name="transport_7_registrationNumber" value="{transport_7_registrationNumber}" type="string"/>
                <parameter name="transport_7_brand" value="{transport_7_brand}" type="string"/>
                <parameter name="transport_7_approxMarketValue" value="{transport_7_approxMarketValue}" type="string"/>
                <parameter name="transport_7_ageOfTransport" value="{transport_7_ageOfTransport}" type="string"/>
                <parameter name="transport_7_yearOfPurchase" value="{transport_7_yearOfPurchase}" type="string"/>

                <parameter name="stepFilled-INITIAL6"       value="{stepFilled-INITIAL6}"  type="boolean"/>

            </initial6-state-parameters>

            <initial7-state-parameters>
                <parameter name="creditMethodObtaining"         value="{creditMethodObtaining}"         type="string"/>
                <parameter name="accountNumber"                 value="{accountNumber}"                 type="string"/>
                <parameter name="receiving-resource"            value="{receivingResource}"             type="string"/>
                <parameter name="receiving-resource-id"         value="{receivingResourceId}"           type="string"/>
                <parameter name="receivingDepartmentName"       value="{receivingDepartmentName}"       type="string"/>
                <parameter name="receivingRegion"               value="{receivingRegion}"               type="string"/>
                <parameter name="receivingOffice"               value="{receivingOffice}"               type="string"/>
                <parameter name="receivingBranch"               value="{receivingBranch}"               type="string"/>
                <parameter name="stockholder"                   value="{stockholder}"                   type="boolean"/>
                <parameter name="ordinaryStockCount"            value="{ordinaryStockCount}"            type="long"/>
                <parameter name="preferredStockCount"           value="{preferredStockCount}"           type="long"/>
                <parameter name="agreeRequestBKI"               value="{agreeRequestBKI}"               type="boolean"/>
                <parameter name="agreeRequestPFP"               value="{agreeRequestPFP}"               type="boolean"/>
                <parameter name="subjectCreditHistoryCode"      value="{subjectCreditHistoryCode}"      type="string"/>
                <parameter name="agreeReceiveCard"              value="{agreeReceiveCard}"              type="boolean"/>
                <parameter name="snils"                         value="{snils}"                         type="string"/>
                <parameter name="openedAccountsCount"           value="{openedAccountsCount}"           type="long"/>

                <parameter name="cardBlank_1_type"              value="{cardBlank_1_type}"              type="string"/>
                <parameter name="cardBlank_1_resource"          value="{cardBlank_1_resource}"          type="string"/>
                <parameter name="cardBlank_1_id"                value="{cardBlank_1_id}"                type="string"/>
                <parameter name="cardBlank_1_customNumber"      value="{cardBlank_1_customNumber}"      type="string"/>

                <parameter name="cardBlank_2_type"              value="{cardBlank_2_type}"              type="string"/>
                <parameter name="cardBlank_2_resource"          value="{cardBlank_2_resource}"          type="string"/>
                <parameter name="cardBlank_2_id"                value="{cardBlank_2_id}"                type="string"/>
                <parameter name="cardBlank_2_customNumber"      value="{cardBlank_2_customNumber}"      type="string"/>

                <parameter name="cardBlank_3_type"              value="{cardBlank_3_type}"              type="string"/>
                <parameter name="cardBlank_3_resource"          value="{cardBlank_3_resource}"          type="string"/>
                <parameter name="cardBlank_3_id"                value="{cardBlank_3_id}"                type="string"/>
                <parameter name="cardBlank_3_customNumber"      value="{cardBlank_3_customNumber}"      type="string"/>

                <parameter name="accountBlank_1_type"           value="{accountBlank_1_type}"           type="string"/>
                <parameter name="accountBlank_1_resource"       value="{accountBlank_1_resource}"       type="string"/>
                <parameter name="accountBlank_1_id"             value="{accountBlank_1_id}"             type="string"/>
                <parameter name="accountBlank_1_customNumber"   value="{accountBlank_1_customNumber}"   type="string"/>

                <parameter name="accountBlank_2_type"           value="{accountBlank_2_type}"           type="string"/>
                <parameter name="accountBlank_2_resource"       value="{accountBlank_2_resource}"       type="string"/>
                <parameter name="accountBlank_2_id"             value="{accountBlank_2_id}"             type="string"/>
                <parameter name="accountBlank_2_customNumber"   value="{accountBlank_2_customNumber}"   type="string"/>

                <parameter name="accountBlank_3_type"           value="{accountBlank_3_type}"           type="string"/>
                <parameter name="accountBlank_3_resource"       value="{accountBlank_3_resource}"       type="string"/>
                <parameter name="accountBlank_3_id"             value="{accountBlank_3_id}"             type="string"/>
                <parameter name="accountBlank_3_customNumber"   value="{accountBlank_3_customNumber}"   type="string"/>

                <parameter name="stepFilled-INITIAL7"           value="{stepFilled-INITIAL7}"           type="boolean"/>
            </initial7-state-parameters>

            <initial8-state-parameters>
                <parameter name="question_count"              value="{question_count}"              type="string"/>

                <parameter name="question_1_id"               value="{question_1_id}"               type="long"/>
                <parameter name="question_1_text"             value="{question_1_text}"             type="string"/>
                <parameter name="question_1_answer_type"      value="{question_1_answer_type}"      type="string"/>
                <parameter name="question_1_answer"           value="{question_1_answer}"           type="string"/>

                <parameter name="question_2_id"               value="{question_2_id}"               type="long"/>
                <parameter name="question_2_text"             value="{question_2_text}"             type="string"/>
                <parameter name="question_2_answer_type"      value="{question_2_answer_type}"      type="string"/>
                <parameter name="question_2_answer"           value="{question_2_answer}"           type="string"/>

                <parameter name="question_3_id"               value="{question_3_id}"               type="long"/>
                <parameter name="question_3_text"             value="{question_3_text}"             type="string"/>
                <parameter name="question_3_answer_type"      value="{question_3_answer_type}"      type="string"/>
                <parameter name="question_3_answer"           value="{question_3_answer}"           type="string"/>

                <parameter name="question_4_id"               value="{question_4_id}"               type="long"/>
                <parameter name="question_4_text"             value="{question_4_text}"             type="string"/>
                <parameter name="question_4_answer_type"      value="{question_4_answer_type}"      type="string"/>
                <parameter name="question_4_answer"           value="{question_4_answer}"           type="string"/>

                <parameter name="question_5_id"               value="{question_5_id}"               type="long"/>
                <parameter name="question_5_text"             value="{question_5_text}"             type="string"/>
                <parameter name="question_5_answer_type"      value="{question_5_answer_type}"      type="string"/>
                <parameter name="question_5_answer"           value="{question_5_answer}"           type="string"/>

                <parameter name="question_6_id"               value="{question_6_id}"               type="long"/>
                <parameter name="question_6_text"             value="{question_6_text}"             type="string"/>
                <parameter name="question_6_answer_type"      value="{question_6_answer_type}"      type="string"/>
                <parameter name="question_6_answer"           value="{question_6_answer}"           type="string"/>

                <parameter name="question_7_id"               value="{question_7_id}"               type="long"/>
                <parameter name="question_7_text"             value="{question_7_text}"             type="string"/>
                <parameter name="question_7_answer_type"      value="{question_7_answer_type}"      type="string"/>
                <parameter name="question_7_answer"           value="{question_7_answer}"           type="string"/>

                <parameter name="question_8_id"               value="{question_8_id}"               type="long"/>
                <parameter name="question_8_text"             value="{question_8_text}"             type="string"/>
                <parameter name="question_8_answer_type"      value="{question_8_answer_type}"      type="string"/>
                <parameter name="question_8_answer"           value="{question_8_answer}"           type="string"/>

                <parameter name="question_9_id"               value="{question_9_id}"               type="long"/>
                <parameter name="question_9_text"             value="{question_9_text}"             type="string"/>
                <parameter name="question_9_answer_type"      value="{question_9_answer_type}"      type="string"/>
                <parameter name="question_9_answer"           value="{question_9_answer}"           type="string"/>

                <parameter name="question_10_id"               value="{question_10_id}"               type="long"/>
                <parameter name="question_10_text"             value="{question_10_text}"             type="string"/>
                <parameter name="question_10_answer_type"      value="{question_10_answer_type}"      type="string"/>
                <parameter name="question_10_answer"           value="{question_10_answer}"           type="string"/>

                <parameter name="question_11_id"               value="{question_11_id}"               type="long"/>
                <parameter name="question_11_text"             value="{question_11_text}"             type="string"/>
                <parameter name="question_11_answer_type"      value="{question_11_answer_type}"      type="string"/>
                <parameter name="question_11_answer"           value="{question_11_answer}"           type="string"/>

                <parameter name="question_12_id"               value="{question_12_id}"               type="long"/>
                <parameter name="question_12_text"             value="{question_12_text}"             type="string"/>
                <parameter name="question_12_answer_type"      value="{question_12_answer_type}"      type="string"/>
                <parameter name="question_12_answer"           value="{question_12_answer}"           type="string"/>

                <parameter name="question_13_id"               value="{question_13_id}"               type="long"/>
                <parameter name="question_13_text"             value="{question_13_text}"             type="string"/>
                <parameter name="question_13_answer_type"      value="{question_13_answer_type}"      type="string"/>
                <parameter name="question_13_answer"           value="{question_13_answer}"           type="string"/>

                <parameter name="question_14_id"               value="{question_14_id}"               type="long"/>
                <parameter name="question_14_text"             value="{question_14_text}"             type="string"/>
                <parameter name="question_14_answer_type"      value="{question_14_answer_type}"      type="string"/>
                <parameter name="question_14_answer"           value="{question_14_answer}"           type="string"/>

                <parameter name="question_15_id"               value="{question_15_id}"               type="long"/>
                <parameter name="question_15_text"             value="{question_15_text}"             type="string"/>
                <parameter name="question_15_answer_type"      value="{question_15_answer_type}"      type="string"/>
                <parameter name="question_15_answer"           value="{question_15_answer}"           type="string"/>

                <parameter name="question_16_id"               value="{question_16_id}"               type="long"/>
                <parameter name="question_16_text"             value="{question_16_text}"             type="string"/>
                <parameter name="question_16_answer_type"      value="{question_16_answer_type}"      type="string"/>
                <parameter name="question_16_answer"           value="{question_16_answer}"           type="string"/>

                <parameter name="question_17_id"               value="{question_17_id}"               type="long"/>
                <parameter name="question_17_text"             value="{question_17_text}"             type="string"/>
                <parameter name="question_17_answer_type"      value="{question_17_answer_type}"      type="string"/>
                <parameter name="question_17_answer"           value="{question_17_answer}"           type="string"/>

                <parameter name="question_18_id"               value="{question_18_id}"               type="long"/>
                <parameter name="question_18_text"             value="{question_18_text}"             type="string"/>
                <parameter name="question_18_answer_type"      value="{question_18_answer_type}"      type="string"/>
                <parameter name="question_18_answer"           value="{question_18_answer}"           type="string"/>

                <parameter name="question_19_id"               value="{question_19_id}"               type="long"/>
                <parameter name="question_19_text"             value="{question_19_text}"             type="string"/>
                <parameter name="question_19_answer_type"      value="{question_19_answer_type}"      type="string"/>
                <parameter name="question_19_answer"           value="{question_19_answer}"           type="string"/>

                <parameter name="question_20_id"               value="{question_20_id}"               type="long"/>
                <parameter name="question_20_text"             value="{question_20_text}"             type="string"/>
                <parameter name="question_20_answer_type"      value="{question_20_answer_type}"      type="string"/>
                <parameter name="question_20_answer"           value="{question_20_answer}"           type="string"/>

                <parameter name="stepFilled-INITIAL8"          value="{stepFilled-INITIAL8}"          type="boolean"/>
            </initial8-state-parameters>
        </document>
    </xsl:template>
</xsl:stylesheet>