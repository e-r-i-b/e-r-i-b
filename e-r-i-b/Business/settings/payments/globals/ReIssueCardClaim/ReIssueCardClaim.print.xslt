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
                extension-element-prefixes="phizic">
	<xsl:output method="html" version="1.0" indent="yes"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
    <xsl:param name="mode" select="'view'"/>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>

	 <xsl:template match="/">
		<xsl:choose>
			<xsl:when test="$mode = 'view'">
				<xsl:apply-templates mode="view"/>
			</xsl:when>
		</xsl:choose>
    </xsl:template>

	<xsl:template match="/form-data" mode="view">
        <xsl:variable name="currentPersonTemp" select="document('currentPerson.xml')/entity-list/entity"/>

        <xsl:variable name="cardNumber" select="mask:getCutCardNumber(cardNumber)"/>
        <xsl:variable name="cardType" select="cardType"/>
        <xsl:variable name="cardName" select="cardName"/>
        <xsl:variable name="currency" select="cardCurrency"/>
        <xsl:variable name="expireDate" select="expireDate"/>
        <xsl:variable name="displayedExpireDate" select="displayedExpireDate"/>
        <xsl:variable name="fio" select="ph:getFormattedPersonName(userFirstName, userSurname, userPatrName)"/>
        <xsl:variable name="reissueReason" select="reissueReason"/>
        <xsl:variable name="currentPerson" select="$currentPersonTemp/field[@name='formatedPersonName']/text()"/>
        <xsl:variable name="homePhone" select="$currentPersonTemp/field[@name='homePhone']/text()"/>
        <xsl:variable name="jobPhone" select="$currentPersonTemp/field[@name='jobPhone']/text()"/>
        <xsl:variable name="birthDay" select="$currentPersonTemp/field[@name='birthDay']/text()"/>
        <xsl:variable name="mobilePhone" select="$currentPersonTemp/field[@name='mobilePhone']/text()"/>
        <xsl:variable name="address" select="$currentPersonTemp/field[@name='address']/text()"/>
        <xsl:variable name="documentSeries" select="$currentPersonTemp/field[@name='documentSeries']/text()"/>
        <xsl:variable name="documentNumber" select="$currentPersonTemp/field[@name='documentNumber']/text()"/>
        <xsl:variable name="documentGivePlace" select="$currentPersonTemp/field[@name='documentGivePlace']/text()"/>
        <xsl:variable name="documentGiveDate" select="$currentPersonTemp/field[@name='documentGiveDate']/text()"/>
        <xsl:variable name="operationDate" select="operationDate"/>
        <xsl:variable name="officeName" select="officeName"/>
        <xsl:variable name="officeAddress" select="officeAddress"/>
        <xsl:variable name="state" select="state"/>

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

        <table cellpadding="0" cellspacing="0" style="width: 200mm; margin-left: 5mm;">
            <tr style="height: 10mm;">
                <td class="width7mm"> </td>
                <td class="width11mm"> </td>
                <td class="width11mm"> </td>
                <td class="width15mm"> </td>
                <td class="width7mm"> </td>
                <td class="width13mm"> </td>
                <td class="width7mm"> </td>
                <td class="width3cm"> </td>
                <td class="width10mm"> </td>
                <td class="width13mm"> </td>
                <td class="width2mm"> </td>
                <td class="width10mm"> </td>
                <td class="width2mm"> </td>
                <td class="width7mm"> </td>
                <td class="width2mm"> </td>
                <td class="width4mm"> </td>
                <td class="width15mm"> </td>
                <td class="width2mm"> </td>
                <td> </td>
                <td class="width10mm"> </td>
                <td class="width10mm"> </td>
            </tr>
            <tr>
                <td colspan="21" class="centring bold bigText uppercase">Заявление на перевыпуск карты</td>
            </tr>
            <tr>
                <td colspan="21">&nbsp;</td>
            </tr>
            <tr>
                <td colspan="6" class="bold">Прошу перевыпустить карту</td>
                <td colspan="8" class="underlineTableBlock centring textF"><xsl:value-of select="$cardName"/></td>
                <td colspan="7"> </td>
            </tr>
            <tr>
                <td colspan="6"> </td>
                <td colspan="8" class="smalltext centring">(Тип карты)</td>
                <td colspan="7"> </td>
            </tr>
            <tr>
                <td colspan="2" class="bold">№ карты</td>
                <td colspan="6" class="underlineTableBlock centring textF"><xsl:value-of select="$cardNumber"/></td>
                <td colspan="7" class="bold">Срок действия карты</td>
                <td colspan="6" class="underlineTableBlock centring textF">
                    <xsl:if test="string-length($displayedExpireDate) != 0">
                        <xsl:value-of select="concat(substring($displayedExpireDate, 3, 2),'/20',substring($displayedExpireDate, 1, 2))"/> (включительно)
                    </xsl:if>
                </td>
            </tr>
            <tr>
                <td colspan="3" class="bold"><span id="squareField" name="squareField"></span>Основная</td>
                <td colspan="18"> </td>
            </tr>
            <tr>
                <td colspan="5" class="bold">Карта выпущена на имя</td>
                <td colspan="16" class="underlineTableBlock centring textF"><xsl:value-of select="$fio"/></td>
            </tr>
            <tr>
                <td colspan="7" class="bold">Дата рождения держателя карты</td>
                <td colspan="3" class="underlineTableBlock centring textF"><xsl:value-of select="$birthDay"/></td>
                <td colspan="10"> </td>
            </tr>
            <tr>
                <td colspan="4" class="bold">Валюта счета карты</td>
                <td colspan="4" class="underlineTableBlock centring textF">
                    <xsl:choose>
                        <xsl:when test="$currency = 'RUB'">
                            Рубли РФ
                        </xsl:when>
                        <xsl:when test="$currency = 'RUR'">
                            Рубли РФ
                        </xsl:when>
                        <xsl:when test="$currency = 'USD'">
                            Доллары США
                        </xsl:when>
                        <xsl:when test="$currency = 'EUR'">
                            Евро
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="$currency"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </td>
                <td colspan="13"> </td>
            </tr>
            <tr>
                <td colspan="21">&nbsp;</td>
            </tr>
            <tr>
                <td colspan="21" class="bold">Причина перевыпуска карты: </td>
            </tr>
            <tr>
                <td colspan="21" class="underlineTableBlock centring textF">
                    <xsl:choose>
                        <xsl:when test="$reissueReason = 'S'">
                            Карта украдена
                        </xsl:when>
                        <xsl:when test="$reissueReason = 'L'">
                            Карта утеряна
                        </xsl:when>
                        <xsl:when test="$reissueReason = 'F'">
                            Компрометация
                        </xsl:when>
                        <xsl:when test="$reissueReason = 'P'">
                            Утрата ПИН-кода
                        </xsl:when>
                        <xsl:when test="$reissueReason = 'Y'">
                            Изъята банком\Порча или техническая неисправность
                        </xsl:when>
                    </xsl:choose>
                </td>
            </tr>
            <tr>
                <td colspan="21">&nbsp;</td>
            </tr>
            <tr>
                <td colspan="8" class="bold">С условиями переоформления ознакомлен(а)</td>
                <td> </td>
                <td colspan="5" class="bold">Контактный тел</td>
                <td colspan="7" class="underlineTableBlock centring textF"><xsl:value-of select="pnu:getCutPhoneNumber($mobilePhone)"/></td>
            </tr>
            <tr>
                <td colspan="21">&nbsp;</td>
            </tr>
            <tr>
                <td colspan="4" class="bold">Место получения</td>
                <td colspan="17"> </td>
            </tr>
            <tr>
                <td colspan="5" class="bold">перевыпущенной карты</td>
                <xsl:choose>
                    <xsl:when test="$officeName = ''">
                        <td colspan="16" class="underlineTableBlock centring textF"><xsl:value-of select="placeForGetCard"/></td>
                    </xsl:when>
                    <xsl:otherwise>
                        <td colspan="3" class="underlineTableBlock centring textF"><xsl:value-of select="$officeName"/></td>
                        <td> </td>
                        <xsl:if test="$officeAddress != ''">
                            <td class="bold">Адрес</td>
                            <td colspan="11" class="underlineTableBlock centring textF"><xsl:value-of select="$officeAddress"/></td>
                        </xsl:if>
                    </xsl:otherwise>
                </xsl:choose>
            </tr>
            <tr style="height: 15mm;">
                <td> </td>
                <td colspan="6" rowspan="3">
                    <xsl:call-template name="stamp">
                        <xsl:with-param name="state" select="$state"/>
                     </xsl:call-template>
                </td>
                <td colspan="14"> </td>
            </tr>
            <tr>
                <td> </td>
                <td colspan="4"> </td>
                <td class="bold">Дата</td>
                <td colspan="8" class="underlineTableBlock centring textF"><xsl:value-of select="$operationDate"/></td>
                <td>г.</td>
            </tr>
            <tr>
                <td> </td>
                <td colspan="14">&nbsp;</td>
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