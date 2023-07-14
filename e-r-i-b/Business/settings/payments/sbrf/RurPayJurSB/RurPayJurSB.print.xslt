<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [
<!ENTITY nbsp "&#160;">
]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                              xmlns:phizic="java://com.rssl.phizic.utils.StringUtils"
                              xmlns:bn="java://com.rssl.phizic.business.util.BankInfoUtil"
                              xmlns:pu="java://com.rssl.phizic.business.persons.PersonHelper"
                              xmlns:xalan = "http://xml.apache.org/xalan"
                              xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                              xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
                              xmlns:mpnu="java://com.rssl.phizic.business.util.MaskPhoneNumberUtil"
                              extension-element-prefixes="phizic">
	<xsl:output method="html" version="1.0" indent="yes"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>
    <xsl:variable name="extendedFields" select="document('extendedFields.xml')/Attributes/*"/>
    <xsl:key name="subService" match="Attribute[./Type != 'calendar']" use="GroupName"/>
    <xsl:variable name="formData" select="/form-data"/>
    <xsl:param name="mode" select="'edit'"/>

    <xsl:template match="/">
        <xsl:choose>
           	<xsl:when test="$mode = 'view'">
				<xsl:apply-templates mode="view"/>
			</xsl:when>
            <xsl:when test="$mode = 'printCheck'">
				<xsl:apply-templates mode="printCheck"/>
			</xsl:when>
		</xsl:choose>
    </xsl:template>

	<xsl:template match="/form-data" mode="view">
		<style>
			.insertInput{border:0 solid transparent;border-bottom:1 solid black;margin:0pt;font-family:Times New Roman;font-size:10pt;}
			.insertInputnoBorder{border:0 solid transparent;border-bottom:1 solid white;margin:0pt;font-family:Times New Roman;font-size:10pt;}
			.docTableBorder{border-top:1px solid #000000;border-left:1px solid #000000;}
			.docTdBorder{border-bottom:1px solid #000000;border-right:1px solid #000000;}
			.docBorderFirst{border-bottom:1px solid #000000;border-right:1px solid #000000;border-top:1px solid #000000;border-left:1px solid #000000;}
			.docBorder{border-bottom:1px solid #000000;border-right:1px solid #000000;border-top:1px solid #000000;}
			.textPadding{padding-left:4;padding-right:4;}
			.textPaddingTop{padding-left:4;padding-right:4;padding-top:4;}
			.font10{font-family:Times New Roman;font-size:10pt;}
			.ul {border-bottom: 1px solid black;}
			.bd {border: 1px solid black;}
		</style>
		<input id="purchase" name="purchase" type="hidden">
				<xsl:attribute name="value">
					<xsl:value-of select="purchase"/>
	            </xsl:attribute>
		</input>
			   <table cellspacing="0" cellpadding="0" border="0" style="margin-left:5mm;margin-top:5mm;table-layout:fixed;width:180mm;border-collapse:collapse;">
				<col style="width:180mm"/>
				<tr>
					<td>
						<table style="width:172mm;height:100%;" cellpadding="0" cellspacing="0" class="textDoc">
						<tr style="height:5mm">
							  <td colspan="2" align="center" class="ul" valign="bottom">
								 &nbsp;
							  </td>
							  <td colspan="2"></td>
							  <td colspan="3" align="center" class="ul" valign="bottom">
								 &nbsp;
							  </td>
							  <td colspan="11"></td>
							   <td colspan="3" align="center"  valign="middle" class="bd" style="border-top:1 solid black;border-bottom:1 solid black;border-left:1 solid black;border-right:1 solid black;">
								0401060
							  </td>
						</tr>
						<tr style="height:6mm">
						  <td colspan="2"  align="center" valign="top" style="font-size:8pt;">Поступ. в банк плат.</td>
						  <td colspan="2"></td>
						  <td colspan="3" align="center" valign="top" style="font-size:8pt;">Списано со сч. плат.</td>
						  <td colspan="14"></td>
						</tr>
						<tr style="7mm">
						  <td colspan="5" class="Title">ПЛАТЕЖНОЕ ПОРУЧЕНИЕ</td>
						  <td colspan="2" class="Title"><span UNSELECTABLE="on">N </span>
							<xsl:value-of select="documentNumber"/>
						  </td>
						  <td colspan="5" align="center" class="ul" valign="bottom">
							&nbsp;<xsl:value-of select="documentDate"/>
						  </td>
						  <td></td>
						  <td colspan="4" align="center" class="ul" valign="bottom" style="font-size:9pt;">
							  электронно
						  </td>
						  <td colspan="3"></td>
						  <td colspan="1" align="center"  valign="middle" class="bd">&nbsp;</td>
						</tr>
						<tr style="height:7mm">
						  <td colspan="7"></td>
						  <td colspan="5" align="center" valign="top" style="font-size:8pt;">Дата</td>
						  <td>&nbsp;</td>
						  <td colspan="4" align="center" valign="top" style="font-size:8pt;">Вид платежа</td>
						  <td colspan="4">&nbsp;</td>
						</tr>
						<tr style="height:15mm">
						   <td valign="top" class="ul" style="border-right:1px solid black;border-bottom:1px solid black;">Сумма прописью</td>
						   <td colspan="20" class="ul" valign="top">&nbsp;
							  <xsl:variable name="rurAccounts" select="document('rur-accounts.xml')/entity-list"/>
							  <xsl:variable name="amountV" select="amount"/>
							  <xsl:variable name="currencyV" select="$rurAccounts/entity/field[@name='currencyCode']"/>
							  <xsl:value-of select="phizic:sumInWords($amountV, $currencyV)"/>
						   </td>
						</tr>
						<tr style="height:5mm">
						   <td colspan="4" style="border-bottom:1px solid black;border-right:1px solid black">ИНН&nbsp;
                             <xsl:variable name="currentPerson" select="document('currentPerson.xml')/entity-list"/>
				             <xsl:value-of select="$currentPerson/entity/field[@name='inn']"/>
                           </td>
                           <td colspan="5" style="border-bottom:1px solid black;border-right:1px solid black">&nbsp;КПП&nbsp;
                            &nbsp;
                           </td>
                           <td colspan="2" style="border-right:1px solid black">&nbsp;Сумма</td>
                           <td colspan="10">&nbsp;
                             <xsl:value-of select="amount"/>
						   </td>
						</tr>
						<tr style="height:10mm">
						   <td colspan="9" rowspan="2" style="border-right:1px solid black" valign="top">
							   <xsl:variable name="currentPerson" select="document('currentPerson.xml')/entity-list"/>
							   &nbsp;<xsl:value-of select="$currentPerson/entity/field[@name='fullName']"/>
						   </td>
						   <td colspan="2" class="ul" style="border-right:1px solid black">&nbsp;</td>
						   <td colspan="10" class="ul">&nbsp;</td>
						</tr>
						<tr style="height:10mm">
						   <td colspan="2" style="border-right:1px solid black" valign="top">&nbsp;Сч. N</td>
						   <td colspan="10" valign="top">&nbsp;
							  <xsl:value-of select="payerAccountSelect"/>
						   </td>
						</tr>
						<tr style="height:5mm">
						   <td colspan="9" class="ul" style="border-right:1px solid black">Плательщик</td>
						   <td colspan="2" class="ul" style="border-right:1px solid black">&nbsp;</td>
						   <td colspan="10"></td>
						</tr>
						<xsl:variable name="currentBankConfig" select="document('currenntBankConfig.xml')"/>
						<tr style="height:5mm">
						   <td colspan="9" rowspan="2" style="border-right:1px solid black" valign="top">
							  <xsl:value-of select="$currentBankConfig//entity[@key='bankConfig']/field[@name='name']"/>
						   </td>
						   <td colspan="2" style="border-right:1px solid black">БИК</td>
						   <td colspan="10" ><xsl:value-of select="$currentBankConfig//entity[@key='bankConfig']/field[@name='bic']"/></td>
						</tr>
						<tr style="height:5mm">
						   <td colspan="2" style="border-right:1px solid black">Сч. N</td>
						   <td colspan="10" ><xsl:value-of select="$currentBankConfig//entity[@key='bankConfig']/field[@name='corrAcc']"/></td>
						</tr>
						<tr style="height:5mm">
						   <td colspan="9" class="ul" style="border-right:1px solid black">Банк плательщика</td>
						   <td colspan="2" class="ul" style="border-right:1px solid black">&nbsp;</td>
						   <td colspan="10" class="ul"></td>
						</tr>
						<tr style="height:5mm">
						   <td colspan="9" rowspan="2" style="border-right:1px solid black" valign="top">
							  &nbsp;<xsl:value-of select="receiverBankName"/>
						   </td>
						   <td colspan="2" style="border-right:1px solid black">&nbsp;БИК</td>
						   <td colspan="10" ><xsl:value-of select="receiverBIC"/></td>
						</tr>
						<tr style="height:5mm">
						   <td colspan="2" style="border-right:1px solid black">&nbsp;Сч. N</td>
						   <td colspan="10" ><xsl:value-of select="receiverCorAccount"/></td>
						</tr>
						<tr style="height:5mm">
						   <td colspan="9" class="ul" style="border-right:1px solid black">Банк получателя</td>
						   <td colspan="2" class="ul" style="border-right:1px solid black">&nbsp;</td>
						   <td colspan="10">&nbsp;</td>
						</tr>
						<tr style="height:5mm">
						   <td colspan="4" style="border-bottom:1px solid black;border-right:1px solid black">ИНН&nbsp;
                            &nbsp;<xsl:value-of select="receiverINN"/>
                           </td>
                           <td colspan="5" style="border-bottom:1px solid black;border-right:1px solid black">&nbsp;КПП&nbsp;
                            &nbsp;<xsl:value-of select="receiverKPP"/>
                           </td>
						   <td colspan="2" style="border-right:1px solid black">&nbsp;Сч. N</td>
						   <td colspan="10">&nbsp;<xsl:value-of select="receiverAccount"/></td>
						</tr>
						<tr style="height:10mm">
						  <td colspan="9" rowspan="3" style="border-right:1px solid black" valign="top">
							&nbsp;<xsl:value-of select="receiverName"/>
						  </td>
						  <td colspan="2" class="ul" style="border-right:1px solid black">&nbsp;</td>
						  <td colspan="10" class="ul">&nbsp;</td>
						</tr>
						<tr style="height:5mm">
							<td colspan="2" class="ul" style="border-right:1px solid black">&nbsp;Вид оп.</td>
							<td colspan="3"  style="border-right:1px solid black">&nbsp;01</td>
							<td colspan="2" class="ul" style="border-right:1px solid black">&nbsp;Срок плат.</td>
							<td colspan="5">&nbsp;</td>
						</tr>
						<tr style="height:5mm">
							<td colspan="2" class="ul" style="border-right:1px solid black">&nbsp;Наз. пл.</td>
							<td colspan="3" style="border-right:1px solid black">&nbsp;

							</td>
							<td colspan="2" class="ul" style="border-right:1px solid black">&nbsp;Очер. плат.</td>
							<td colspan="5">&nbsp;

							</td>
						</tr>
						<tr style="height:5mm">
							<td colspan="9" style="border-bottom:1px solid black;border-right:1px solid black">Получатель</td>
							<td colspan="2" style="border-bottom:1px solid black;border-right:1px solid black">&nbsp;Код</td>
							<td colspan="3" style="border-bottom:1px solid black;border-right:1px solid black">&nbsp;

							</td>
							<td colspan="2" style="border-bottom:1px solid black;border-right:1px solid black">&nbsp;Рез. поле.</td>
							<td colspan="5" class="ul">&nbsp;

							</td>
						</tr>
						<tr style="height:25mm">
							<td colspan="21" valign="top">
			                    <xsl:value-of select="ground"/>
								<br/>
								<br/>
								<br/>

							</td>
					   </tr>
					   <tr style="height:5mm">
							 <td colspan="21" style="border-bottom:1px solid black;">Назначение платежа</td>
					   </tr>
						<tr style="height:15mm;">
							<td colspan="5" style="border-top:1px solid black">&nbsp;</td>
							<td colspan="7" align="center" valign="top" style="border-bottom:1px solid black;border-top:1px solid black">Подписи</td>
							<td colspan="9" align="center" valign="top" class="ul">Отметки банка</td>
						   </tr>
						   <tr style="height:15mm">
							<td colspan="2">&nbsp;</td>
							<td align="center" valign="top">М.П.</td>
							<td colspan="2">&nbsp;</td>
							<td colspan="7" class="ul">&nbsp;</td>
							<td colspan="9" align="center" ><xsl:value-of select="documentDate"/>&nbsp;</td>
						</tr>
					    </table>
					</td>
				</tr>
			</table>

	</xsl:template>

    <xsl:template match="/form-data" mode="printCheck">
        <xsl:call-template name="printTitle">
            <xsl:with-param name="value">безналичная  оплата услуг</xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">ДАТА ОПЕРАЦИИ</xsl:with-param>
            <xsl:with-param name="value"><xsl:value-of  select="operationDate"/></xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">время операции (МСК)</xsl:with-param>
            <xsl:with-param name="value"><xsl:value-of  select="operationTime"/></xsl:with-param>
        </xsl:call-template>

        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">идентификатор операции</xsl:with-param>
            <xsl:with-param name="value"><xsl:value-of  select="documentNumber"/></xsl:with-param>
        </xsl:call-template>

        <xsl:if test="string-length(billingDocumentNumber)>0 and state = 'EXECUTED'">
            <xsl:call-template name="paramsCheck">
                <xsl:with-param name="title">номер операции</xsl:with-param>
                <xsl:with-param name="value"><xsl:value-of  select="billingDocumentNumber"/></xsl:with-param>
            </xsl:call-template>
        </xsl:if>
        <br/>

        <xsl:variable name="fromResourceType" select="fromResourceType"/>
        <xsl:choose>
            <xsl:when test="contains($fromResourceType, 'Account')">
                <xsl:call-template name="printText">
                    <xsl:with-param name="value"><xsl:value-of  select="fromAccountType"/>,</xsl:with-param>
                </xsl:call-template>
                <xsl:call-template name="printText">
                    <xsl:with-param name="value"><xsl:value-of  select="fromAccountSelect"/></xsl:with-param>
                </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
                <xsl:variable name="fromAccountSelect"><xsl:value-of  select="fromAccountSelect"/></xsl:variable>
                   <xsl:call-template name="paramsCheck">
                        <xsl:with-param name="title">карта</xsl:with-param>
                       <xsl:with-param name="value"><xsl:value-of select="mask:getCutCardNumberPrint($fromAccountSelect)"/></xsl:with-param>
                    </xsl:call-template>
            </xsl:otherwise>
        </xsl:choose>

        <br/>
        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">Сумма операции</xsl:with-param>
            <xsl:with-param name="value">
                <xsl:variable name="id" select="$extendedFields[./IsMainSum = 'true']/NameBS"/>
                <xsl:value-of select="$formData/*[name()=$id]"/>
                &nbsp;<xsl:value-of select="destinationCurrency"/>
            </xsl:with-param>
        </xsl:call-template>
        <xsl:if test="commission!=''">
            <xsl:call-template name="paramsCheck">
                <xsl:with-param name="title">Комиссия</xsl:with-param>
                <xsl:with-param name="value"><xsl:value-of  select="commission"/>&nbsp;<xsl:value-of select="commissionCurrency"/></xsl:with-param>
            </xsl:call-template>
        </xsl:if>
        <xsl:if test="authorizeCode!=''">
            <xsl:call-template name="paramsCheck">
                <xsl:with-param name="title">Код авторизации</xsl:with-param>
                <xsl:with-param name="value"><xsl:value-of  select="authorizeCode"/></xsl:with-param>
            </xsl:call-template>
        </xsl:if>
        <br/>
        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">Реквизиты плательщика</xsl:with-param>
            <xsl:with-param name="value">
                <br/><xsl:value-of select="pu:getFormattedPersonName()"/>
                <xsl:call-template name="showExtendedFieldByKeyProperty">
                    <xsl:with-param name="fields" select="$extendedFields"/>
                    <xsl:with-param name="onlyKey">true</xsl:with-param>
                    <xsl:with-param name="currency" select="destinationCurrency"/>
                </xsl:call-template>
            </xsl:with-param>
        </xsl:call-template>
        <br/>
        <xsl:call-template name="paramsCheck">
            <xsl:with-param name="title">Реквизиты платежа</xsl:with-param>
            <xsl:with-param name="value">
                <br/>
                <xsl:value-of  select="nameService"/>
                <xsl:call-template name="showExtendedFieldByKeyProperty">
                    <xsl:with-param name="fields" select="$extendedFields"/>
                    <xsl:with-param name="onlyKey">false</xsl:with-param>
                    <xsl:with-param name="currency" select="destinationCurrency"/>
                </xsl:call-template>
            </xsl:with-param>
        </xsl:call-template>

        <xsl:if test="$extendedFields/Type = 'calendar'">
            <br/>
            <xsl:call-template name="paramsCheck">
                <xsl:with-param name="title">Период оплаты</xsl:with-param>
                <xsl:with-param name="value"><xsl:value-of select="payPeriod"/></xsl:with-param>
            </xsl:call-template>
        </xsl:if>
        <br/>

        <xsl:if test="string-length(receiverName)>0">
            <xsl:call-template name="paramsCheck">
                <xsl:with-param name="title">получатель платежа</xsl:with-param>
                <xsl:with-param name="value"><br/>
                    <xsl:choose>
                        <xsl:when test="string-length(nameOnBill) > 0">
                            <xsl:value-of  select="nameOnBill"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of  select="receiverName"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:with-param>
            </xsl:call-template>
        </xsl:if>
        <br/>

        <xsl:variable name="bankDetails" select="bankDetails"/>
        <xsl:if test="$bankDetails = 'true'">
            <!-- реквизиты получателя-->
            <xsl:call-template name="printText">
                <xsl:with-param name="value">реквизиты получателя:</xsl:with-param>
            </xsl:call-template>
            <xsl:call-template name="paramsCheck">
                    <xsl:with-param name="title">Наименование банка получателя</xsl:with-param>
                    <xsl:with-param name="value"><xsl:value-of select="receiverBankName"/></xsl:with-param>
            </xsl:call-template>
            <xsl:call-template name="paramsCheck">
                    <xsl:with-param name="title">БИК</xsl:with-param>
                    <xsl:with-param name="value"><xsl:value-of select="receiverBIC"/></xsl:with-param>
            </xsl:call-template>
            <xsl:call-template name="paramsCheck">
                    <xsl:with-param name="title">ИНН</xsl:with-param>
                    <xsl:with-param name="value"><xsl:value-of select="receiverINN"/></xsl:with-param>
            </xsl:call-template>
            <xsl:call-template name="paramsCheck">
                    <xsl:with-param name="title">СЧЕТ</xsl:with-param>
                    <xsl:with-param name="value"><xsl:value-of select="receiverAccount"/></xsl:with-param>
            </xsl:call-template>
            <xsl:call-template name="paramsCheck">
                    <xsl:with-param name="title">КОРР.СЧЕТ</xsl:with-param>
                    <xsl:with-param name="value"><xsl:value-of select="receiverCorAccount"/></xsl:with-param>
            </xsl:call-template>
        </xsl:if>
        <!-- печать -->
        <xsl:call-template name="stamp"/>

        <!-- дополнительная информация -->
        <xsl:if test="state='DISPATCHED' or state='ERROR' or state='UNKNOW' ">
            <xsl:call-template name="printTextForDispatched">
                <xsl:with-param name="value">
                    платеж может быть не исполнен банком в случае указания неверных
                    реквизитов получателя, либо отзыва операции плательщиком.
                </xsl:with-param>
            </xsl:call-template>
            <br/>
        </xsl:if>


        <div  id="additionalInfo" class="checkSize titleAdditional">
            <br/>ПО ВОПРОСУ ПРЕДОСТАВЛЕНИЯ УСЛУГИ ОБРАЩАЙТЕСЬ К ПОЛУЧАТЕЛЮ ПЛАТЕЖА
            <!-- Вывод телефонного номера -->
            <xsl:if test="phoneNumber!=''">
                ПО ТЕЛЕФОНУ: <xsl:value-of select="phoneNumber"/>
            </xsl:if>
        </div>

    </xsl:template>

    <xsl:template name="printTitle">
          <xsl:param name="value"/>
          <div class="checkSize title"><xsl:copy-of select="$value"/></div>
          <br/>
      </xsl:template>

      <xsl:template name="printTitleAdditional">
          <xsl:param name="value"/>
          <div class="checkSize titleAdditional"><xsl:copy-of select="$value"/></div>
          <br/>
      </xsl:template>

      <xsl:template name="printText">
          <xsl:param name="value"/>
          <div class="checkSize"><xsl:copy-of select="$value"/></div>
      </xsl:template>

      <xsl:template name="printTextForDispatched">
          <xsl:param name="value"/>
          <div class="checkSize titleAdditional"><xsl:copy-of select="$value"/></div>
      </xsl:template>

      <xsl:template name="paramsCheck">
          <xsl:param name="title"/>
          <xsl:param name="value"/>
          <div class="checkSize"><xsl:copy-of select="$title"/>: <xsl:copy-of select="$value"/></div>
      </xsl:template>

    <xsl:template name="showExtendedFieldByKeyProperty">
        <xsl:param name="fields"/>
        <xsl:param name="onlyKey"/>
        <xsl:param name="currency"/>
        <xsl:variable name="showOnlyKey" select="($onlyKey ='true')"/>
        <xsl:for-each select="$fields[./Type != 'calendar']">
            <xsl:choose>
                <xsl:when test="preceding-sibling::Attribute[./GroupName = current()/GroupName]"/>
                <xsl:otherwise>
                    <xsl:for-each select="self::node()|key('subService',current()/GroupName)">
                        <xsl:variable name="name" select="./NameVisible"/>
                        <xsl:variable name="isForBill" select="./IsForBill='true'"/>
                        <xsl:variable name="type" select="./Type"/>
                        <xsl:variable name="id" select="./NameBS"/>
                        <xsl:variable name="currentValue" select="$formData/*[name()=$id]"/>
                        <xsl:variable name="isKey" select="./IsKey = 'true'"/>
                        <xsl:if test="(($isForBill) or ($isKey)) and ($isKey=$showOnlyKey)">
                            <br/><xsl:value-of select="$name"/>:&nbsp;
                            <xsl:choose>
                                <xsl:when test="$type='list'">
                                    <xsl:value-of select="./Menu/MenuItem[$currentValue = ./Id]/Value"/>
                                </xsl:when>
                                <xsl:when test="$type='set'">
                                    <xsl:choose>
                                        <xsl:when test="contains($currentValue, '@')">
                                            <xsl:for-each select="xalan:tokenize($currentValue, '@')">
                                                <xsl:value-of select="current()"/>&nbsp;
                                            </xsl:for-each>
                                        </xsl:when>
                                        <xsl:otherwise>
                                            <xsl:value-of select="$currentValue"/>
                                        </xsl:otherwise>
                                    </xsl:choose>
                                </xsl:when>
                                <xsl:when test="$type = 'money'">
                                    <xsl:value-of select="$currentValue"/>&nbsp;<xsl:value-of select="$currency"/>
                                </xsl:when>
                                <xsl:otherwise>
                                    <xsl:value-of select="$currentValue"/>
                                </xsl:otherwise>
                            </xsl:choose>
                        </xsl:if>
                    </xsl:for-each>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:for-each>
    </xsl:template>

      <xsl:template name="stamp">
          <br/>
          <div id="stamp" class="stamp">
             <xsl:value-of select="bn:getBankNameForPrintCheck()"/>
             <xsl:variable name="imagesPath" select="concat($resourceRoot, '/images/')"/>
             <xsl:choose>
                 <xsl:when test="state='DELAYED_DISPATCH'">
                    <img src="{concat($imagesPath, 'stampDelayed_blue.gif')}" alt=""/>
                 </xsl:when>
                 <xsl:when test="state='DISPATCHED' or state='ERROR' or state ='UNKNOW'">
                     <img src="{concat($imagesPath, 'stampDispatched_blue.gif')}" alt=""/>
                 </xsl:when>
                 <xsl:when test="state='EXECUTED'">
                     <img src="{concat($imagesPath, 'stampExecuted_blue.gif')}" alt=""/>
                 </xsl:when>
                 <xsl:when test="state='REFUSED'">
                     <img src="{concat($imagesPath, 'stampRefused_blue.gif')}" alt=""/>
                 </xsl:when>
             </xsl:choose>
         </div>
         <br/>
      </xsl:template>

</xsl:stylesheet>