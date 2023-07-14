<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [
<!ENTITY nbsp "&#160;">
]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:phizic="java://com.rssl.phizic.utils.StringUtils"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:au="java://com.rssl.phizic.business.accounts.AccountsUtil"
                xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
                xmlns:bn="java://com.rssl.phizic.business.util.BankInfoUtil"
                xmlns:ph="java://com.rssl.phizic.business.persons.PersonHelper"
                xmlns:pnu="java://com.rssl.phizic.utils.PhoneNumberUtil"
                xmlns:mpnu="java://com.rssl.phizic.business.util.MaskPhoneNumberUtil"
                xmlns:msk="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:dh="java://com.rssl.phizic.utils.DateHelper"
                extension-element-prefixes="phizic">
	<xsl:output method="html" version="1.0" indent="yes"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
    <xsl:param name="mode" select="'view'"/>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>

	 <xsl:template match="/">
        <xsl:apply-templates mode="view"/>
    </xsl:template>

	<xsl:template match="/form-data" mode="view">

        <xsl:variable name="currentPerson" select="document('currentPerson.xml')/entity-list"/>
        <xsl:variable name="personData" select="document('currentPersonData.xml')/entity-list"/>

        <input id="documentNumber" name="documentNumber" type="hidden"/>
        <xsl:variable name="userFullName" select="$currentPerson/entity/field[@name='formatedPersonName']"/>
        <xsl:variable name="gender" select="$personData/entity/field[@name='gender']"/>
        <xsl:variable name="birthDayString" select="$currentPerson/entity/field[@name='birthDayString']"/>
        <xsl:variable name="citizenship" select="$personData/entity/field[@name='citizen']"/>
        <xsl:variable name="birthPlace" select="$personData/entity/field[@name='birthPlace']"/>
        <xsl:variable name="mobilePhone" select="$personData/entity/field[@name='additionalPhone1']"/>
        <xsl:variable name="passportIssueDate" select="dh:formatXsdDateToString($personData/field[@name = 'passportIssueDate'])"/>
        <xsl:variable name="passportSeries" select="$personData/entity/field[@name='passportSeries']"/>
        <xsl:variable name="passportNumber" select="$personData/entity/field[@name='passportNumber']"/>
        <xsl:variable name="identityTypeName" select="$personData/entity/field[@name='identityTypeName']"/>

        <style type="text/css">
            td {line-height: 16pt;}
            table {border-collapse: separate!important;}
            .underlineTableBlock {border-bottom-width: 1px; border-bottom-color: #000000; border-bottom-style: solid;}
            .smallText {font-family: Times; font-size: 9pt; vartical-align: top;}
            .textF{font-family: Times; font-size: 12pt; font-style: italic;}
            .centring {text-align: center;}
            .width2mm {max-width: 2mm; width: 2mm;}
            .width4mm {max-width: 4mm; width: 4mm;}
            .width7mm {max-width: 7mm; width: 7mm;}
            .width9mm {max-width: 9mm; width: 9mm;}
            .width10mm {max-width: 10mm; width: 10mm;}
            .width11mm {max-width: 11mm; width: 11mm;}
            .width13mm {max-width: 13mm; width: 13mm;}
            .width15mm {max-width: 15mm; width: 15mm;}
            .width18mm {max-width: 18mm; width: 18mm;}
            .width3cm {width: 30mm;}
            .bold{font-weight: bold; font-family: Times; font-size: 12pt;}
            .bigText {font-size: big;}
            .uppercase {text-transform: uppercase;}
        </style>

        <table cellpadding="0" cellspacing="0">
            <tr>
                <td colspan="2" class="centring bold bigText">Подключение всех возможностей Сбербанк Онлайн</td>
            </tr>
            <tr>
                <td style="width:40%;">ФИО</td>
                <td class="underlineTableBlock centring"><xsl:value-of select="$userFullName"/></td>
            </tr>
            <tr>
                <td>Ваш пол</td>
                <td class="underlineTableBlock centring"><xsl:value-of select="$gender"/></td>
            </tr>
            <tr>
                <td>Дата рождения</td>
                <td class="underlineTableBlock centring"><xsl:value-of select="$birthDayString"/></td>
            </tr>
            <tr>
                <td>Гражданство</td>
                <td class="underlineTableBlock"><xsl:value-of select="$citizenship"/></td>
            </tr>
            <tr>
                <td>Место рождения</td>
                <td class="underlineTableBlock centring"><xsl:value-of select="$birthPlace"/></td>
            </tr>
            <tr>
                <td>Мобильный телефон</td>
                <td class="underlineTableBlock centring"><xsl:value-of select="mpnu:getCutPhoneForAddressBook($mobilePhone)"/></td>
            </tr>
            <tr>
                <td colspan="2">Документ, удостоверяющий личность:</td>
            </tr>
            <tr>
                <td>Тип документа</td>
                <td class="underlineTableBlock centring"><xsl:value-of select="$identityTypeName"/></td>
            </tr>
            <tr>
                <td>Серия и номер:</td>
                <td class="underlineTableBlock centring"><xsl:value-of select="msk:maskString(concat($passportSeries, $passportNumber), 4, 2, '•')"/></td>
            </tr>
            <tr>
                <td>Дата выдачи</td>
                <td class="underlineTableBlock centring textF"><xsl:value-of select="$passportIssueDate"/></td>
            </tr>
        </table>

        <script type="text/javascript">
			 $("#squareField").text("\u25aa");
        </script>
	</xsl:template>

    <xsl:template name="stamp">
        <xsl:param name="state"/>
        <xsl:if test="$state = 'DISPATCHED'">
            <br/>
            <div id="stamp" class="forBankStamp" style="position: relative; top : 0; right: 0;">
                <xsl:variable name="imagesPath" select="concat($resourceRoot, '/commonSkin/images/transaction_status/')"/>
                <span class="stampTitle"><xsl:value-of select="bn:getBankNameForPrintCheck()"/></span>
                <div style="text-align:center;">
                    <img src="{concat($imagesPath, 'stampExecuted_noBorder.gif')}" alt=""/>
                </div>
            </div>
        </xsl:if>
    </xsl:template>

</xsl:stylesheet>