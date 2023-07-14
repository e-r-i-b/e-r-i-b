<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [
<!ENTITY nbsp "&#160;">
]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" version="1.0" indent="yes"/>
	<xsl:param name="mode" select="'edit'"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>
	<xsl:param name="personAvailable" select="true()"/>
	
	<xsl:variable name="extendedFields" select="document('extendedFields.xml')/fields/field"/>
	<xsl:variable name="formData" select="/form-data"/>
    <xsl:variable name="title">
		<xsl:choose>
			<xsl:when test="/form-data/appointment='credit-repayment'">
		        Погашение кредита			    
			</xsl:when>
			<xsl:when test="/form-data/appointment='cellular-communication'">
		        Оплата услуг сотовой связи
			</xsl:when>
			<xsl:when test="/form-data/appointment='inet-connection'">
			    Интернет			    
			</xsl:when>
			<xsl:when test="/form-data/appointment='satellite-connection'">
			    Спутниковая связь/ТВ
			</xsl:when>
			<xsl:when test="/form-data/appointment='ip-telephony'">
			    IP-телефония
			</xsl:when>
			<xsl:when test="/form-data/appointment='telephony'">
			    Услуги телефонии
			</xsl:when>
			<xsl:when test="/form-data/appointment='game-portals'">
			    Игровые порталы
			</xsl:when>
			<xsl:when test="/form-data/appointment='inet-shops'">
			    Интернет-магазины
			</xsl:when>
			<xsl:when test="/form-data/appointment='payment-system'">
			    Платежные системы
			</xsl:when>
			<xsl:when test="/form-data/appointment='air-ticket'">
			    Авиабилеты
			</xsl:when>
			<xsl:when test="/form-data/appointment='travel-agency'">
			    Турпоездки
			</xsl:when>
			<xsl:when test="/form-data/appointment='hotel-payment'">
			    Гостиницы
			</xsl:when>
			<xsl:when test="/form-data/appointment='policy-payment'">
			    Страховой полис
			</xsl:when>
			<xsl:when test="/form-data/appointment='realty-operation'">
			    Недвижимость
			</xsl:when>
            <xsl:when test="/form-data/appointment='other-payment'"> 
			    Прочее
			</xsl:when>
		</xsl:choose>
	</xsl:variable>
    <xsl:variable name="description">
		<xsl:choose>
			<xsl:when test="/form-data/appointment='credit-repayment'">
		        Перечисление денежных средств с вашего счета для погашения кредита и процентов по нему.			    
			</xsl:when>
			<xsl:when test="/form-data/appointment='cellular-communication'">
		        Перечисление денежных средств с вашего счета для оплаты услуг сотовой связи.
			</xsl:when>
			<xsl:when test="/form-data/appointment='inet-connection'">
			    Перечисление денежных средств с вашего счета для оплаты услуг Интернет-провайдеров.			    
			</xsl:when>
			<xsl:when test="/form-data/appointment='satellite-connection'">
			    Перечисление денежных средств для пополнения счета операторов спутниковой связи и ТВ.
			</xsl:when>
			<xsl:when test="/form-data/appointment='ip-telephony'">
			    Перечисление денежных средств с вашего счета для оплаты услуг операторов IP-телефонии.
			</xsl:when>
			<xsl:when test="/form-data/appointment='telephony'">
			    Перечисление денежных средств с вашего счета для оплаты услуг операторов телефонной связи.
			</xsl:when>
			<xsl:when test="/form-data/appointment='game-portals'">
			    Перечисление денежных средств с вашего счета для пополнения счетов игровых порталов.
			</xsl:when>
			<xsl:when test="/form-data/appointment='inet-shops'">
			    Перечисление денежных средств с вашего счета для оплаты товаров и услуг, заказанных в Интернет-магазинах.
			</xsl:when>
			<xsl:when test="/form-data/appointment='payment-system'">
			    Перечисление денежных средств с вашего счета для пополнения счета (электронного кошелька) платежных систем.
			</xsl:when>
			<xsl:when test="/form-data/appointment='air-ticket'">
			    Перечисление денежных средств с вашего счета для оплаты заказанных авиабилетов.
			</xsl:when>
			<xsl:when test="/form-data/appointment='travel-agency'">
			    Перечисление денежных средств с вашего счета для оплаты туристического пакета.
			</xsl:when>
			<xsl:when test="/form-data/appointment='hotel-payment'">
			    Перечисление денежных средств с вашего счета для оплаты забронированного номера в гостинице.
			</xsl:when>
			<xsl:when test="/form-data/appointment='policy-payment'">
			    Перечисление денежных средств с вашего счета для оплаты страхового полиса.
			</xsl:when>
			<xsl:when test="/form-data/appointment='realty-operation'">
			    Перечисление денежных средств с вашего счета для оплаты операций с недвижимостью.
			</xsl:when>
            <xsl:when test="/form-data/appointment='other-payment'"> 
			    Перечисление денежных средств с вашего счета для оплаты прочих товаров и услуг.
			</xsl:when>
		</xsl:choose>
	</xsl:variable>
    <xsl:variable name="imageName">
		<xsl:choose>
			<xsl:when test="/form-data/appointment='credit-repayment'">CreditRepaymentaymanet.gif</xsl:when>
			<xsl:when test="/form-data/appointment='cellular-communication'">MobilePayment.gif</xsl:when>
			<xsl:when test="/form-data/appointment='inet-connection'">InternetPayment.gif</xsl:when>
			<xsl:when test="/form-data/appointment='satellite-connection'">SatelliteConnection.gif</xsl:when>
			<xsl:when test="/form-data/appointment='ip-telephony'">IPTelephony.gif</xsl:when>
			<xsl:when test="/form-data/appointment='telephony'">Telephony.gif</xsl:when>
			<xsl:when test="/form-data/appointment='game-portals'">GamePortals.gif</xsl:when>
			<xsl:when test="/form-data/appointment='inet-shops'">InetShops.gif</xsl:when>
			<xsl:when test="/form-data/appointment='payment-system'">PaymentSystem.gif</xsl:when>
			<xsl:when test="/form-data/appointment='air-ticket'">AirTicket.gif</xsl:when>
			<xsl:when test="/form-data/appointment='travel-agency'">TravelAgency.gif</xsl:when>
			<xsl:when test="/form-data/appointment='hotel-payment'">HotelPayment.gif</xsl:when>
			<xsl:when test="/form-data/appointment='policy-payment'">PolicyPayment.gif</xsl:when>
			<xsl:when test="/form-data/appointment='realty-operation'">RealtyOperation.gif</xsl:when>
            <xsl:when test="/form-data/appointment='other-payment'">OtherPayment.gif</xsl:when>
		</xsl:choose>
	</xsl:variable>

    <xsl:template match="/">             
		<xsl:choose>
			<xsl:when test="$mode = 'edit'">
				<xsl:apply-templates mode="edit"/>
			</xsl:when>
			<xsl:when test="$mode = 'view'">
				<xsl:apply-templates mode="view"/>
			</xsl:when>
		</xsl:choose>
	</xsl:template>

	<xsl:template match="/form-data" mode="edit">
		<script type="text/javascript">document.webRootPrivate = '<xsl:value-of select="$webRoot"/>/private';</script>

		<input id="appointment" name="appointment" type="hidden">
			<xsl:attribute name="value">
					<xsl:value-of select="appointment"/>
	            </xsl:attribute>
		</input>

		<input id="receiverKey" name="receiverKey" type="hidden">
			<xsl:attribute name="value">
					<xsl:value-of select="receiverKey"/>
	            </xsl:attribute>
		</input>

		<table cellspacing="2" cellpadding="0" border="0" class="paymentFon">
			<tr>
				<td align="right" valign="middle">
					<img src="{$resourceRoot}/images/{$imageName}" border="0"/>
				</td>
				<td>
					<table class="MaxSize">
						<tr>
							<td align="center" class="silverBott paperTitle">
								<table height="100%" width="420px" cellspacing="0" cellpadding="0">
									<tr>
										<td class="titleHelp">
											<span class="formTitle"><xsl:value-of select="$title"/></span>
											<br/><xsl:value-of select="$description"/>
										</td>
									</tr>
								</table>
							</td>
							<td width="100%">&nbsp;</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td style="text-align:left;" class="Width120 LabelAll">&nbsp;<b>Номер перевода</b>&nbsp;</td>
				<td>
					<xsl:value-of select="documentNumber"/>
				</td>
			</tr>
			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Списать со счета</td>
				<td>
					<xsl:variable name="payerAccount" select="payerAccountSelect"/>
					<xsl:if test="$personAvailable">
						<select id="payerAccountSelect" name="payerAccountSelect">
							<xsl:for-each select="document('accounts.xml')/entity-list/*">
								<option>
									<xsl:attribute name="value">
										<xsl:value-of select="./@key"/>
									</xsl:attribute>
									<xsl:if test="$payerAccount = ./@key">
										<xsl:attribute name="selected">true</xsl:attribute>
									</xsl:if>
									<xsl:value-of select="./@key"/>&nbsp;
									[<xsl:value-of select="./field[@name='type']"/>]
									<xsl:value-of select="format-number(./field[@name='amountDecimal'], '0.00')"/>&nbsp;
									<xsl:value-of select="./field[@name='currencyCode']"/>
								</option>
							</xsl:for-each>
						</select>
					</xsl:if>
					<xsl:if test="not($personAvailable)">
						<select disabled="disabled">
							<option selected="selected">Счет клиента</option>
						</select>
					</xsl:if>
				</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Сумма <span class="asterisk">*</span></td>
				<td>
					<input id="amount" name="amount" type="text" value="{amount}" size="24"/>&nbsp;RUB</td>
			</tr>
			<tr>
				<td colspan="2" style="text-align:left;" class="Width120 LabelAll">&nbsp;<b>Получатель платежа</b></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Наименование</td>
				<td>
					<table class="MaxSize" cellspacing="0" cellspadding="0">
						<tr>
							<td>
					<xsl:variable name="receiverName" select="receiverName"/>
					<input type="hidden" name="receiverName" value="{$receiverName}"/>
					<xsl:value-of select="receiverName"/>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>

			<xsl:if test="count($extendedFields) != 0">
				<tr>
					<td colspan="2" style="text-align:left;" class="Width120 LabelAll">&nbsp;<b>Дополнительные поля платежа</b></td>
				</tr>
			</xsl:if>

			<xsl:for-each select="$extendedFields">
				<tr>
					<td class="Width120 LabelAll" valign="top">
						<xsl:value-of select="@description"/>
					</td>
					<td>
						<xsl:variable name="fieldName" select="@name"/>
						<xsl:variable name="fieldValue" select="$formData/*[name()=$fieldName]"/>
						<xsl:variable name="len" select="@length"/>
				
						<xsl:if test="number($len)&gt;0">
							<input id="{$fieldName}" name="{$fieldName}" type="text" size="{number($len)+1}" maxlength="{$len}" value="{$fieldValue}"/>
						</xsl:if>
						<xsl:if test="not(number($len)&gt;0)">
							<input id="{$fieldName}" name="{$fieldName}" type="text" size="25" maxlength="128" value="{$fieldValue}"/>
						</xsl:if>
						&nbsp;
					</td>
				</tr>
			</xsl:for-each>
		</table>
	</xsl:template>

	<xsl:template match="/form-data" mode="view">
		<table cellspacing="2" cellpadding="0" border="0" class="paymentFon">
			<tr>
				<td align="right" valign="middle">
                    <img src="{$resourceRoot}/images/{$imageName}" border="0"/>
				</td>
				<td>
					<table class="MaxSize">
						<tr>
							<td align="center" class="silverBott paperTitle">
								<table height="100%" width="260px" cellspacing="0" cellpadding="0">
									<tr>
										<td class="titleHelp">
											<span class="formTitle"><xsl:value-of select="$title"/></span>
											<br/>
											<span id="titleHelp"><xsl:value-of select="$description"/></span>
										</td>
									</tr>
								</table>
							</td>
							<td width="15px">&nbsp;</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td style="text-align:left;" class="Width120 LabelAll">&nbsp;<b>Номер перевода</b>&nbsp;</td>
				<td>
					<xsl:value-of select="documentNumber"/>
				</td>
			</tr>
			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Списать со счета</td>
				<td>
					<xsl:variable name="payerAcc" select="payerAccountSelect"/>
					<xsl:variable name="payerAccount" select="document('accounts.xml')/entity-list/entity[@key=$payerAcc]"/>
					<xsl:value-of select="payerAccountSelect"/>[<xsl:value-of select="$payerAccount/field[@name='type']"/>]</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Сумма</td>
				<td>
					<xsl:value-of select="amount"/>&nbsp;RUB &nbsp;&nbsp;Комиссия:&nbsp; <xsl:value-of select="commissionAmount"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll" style="text-align:left;">&nbsp;<b>Получатель платежа</b></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Наименование</td>
				<td>
					<xsl:value-of select="receiverName"/>
				</td>
			</tr>

			<xsl:if test="count($extendedFields) != 0">
				<tr>
					<td colspan="2" style="text-align:left;" class="Width120 LabelAll">
						<nobr>&nbsp;<b>Дополнительные поля</b></nobr>
					</td>
				</tr>
			</xsl:if>

			<!-- Выберем дополнительные поля -->
			<xsl:for-each select="$extendedFields">
				<tr>
					<td class="Width120 LabelAll" valign="top">
						<xsl:value-of select="@description"/>
					</td>
					<td>
						<xsl:variable name="fieldName" select="@name"/>
						<xsl:value-of select="$formData/*[name()=$fieldName]"/>
						&nbsp;
					</td>
				</tr>
			</xsl:for-each>

			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>
		</table>
	</xsl:template>
</xsl:stylesheet><!-- Stylus Studio meta-information - (c) 2004-2006. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="Edit" userelativepaths="yes" externalpreview="no" url="form&#x2D;data.xml" htmlbaseurl="" outputurl="" processortype="internal" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator="" ><parameterValue name="mode" value="'edit'"/></scenario><scenario default="no" name="View" userelativepaths="yes" externalpreview="no" url="form&#x2D;data.xml" htmlbaseurl="" outputurl="" processortype="internal" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator="" ><parameterValue name="mode" value="'view'"/></scenario></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no" ><SourceSchema srcSchemaPath="form&#x2D;data.xml" srcSchemaRoot="form&#x2D;data" AssociatedInstance="" loaderFunction="document" loaderFunctionUsesURI="no"/><SourceSchema srcSchemaPath="preparedPayment_a.xml" srcSchemaRoot="preparedPayment_a" AssociatedInstance="file:///c:/Projects/RS/PhizIC&#x2D;SBRF/Business/settings/forms/RurPayJurSB/preparedPayment_a.xml" loaderFunction="document" loaderFunctionUsesURI="no"/></MapperInfo><MapperBlockPosition><template match="/"></template><template match="/form&#x2D;data"><block path="script/xsl:value&#x2D;of" x="167" y="18"/><block path="table/tr[1]/td[1]/xsl:if" x="127" y="137"/><block path="table/tr[1]/td[1]/xsl:if/select/xsl:for&#x2D;each" x="167" y="177"/><block path="table/tr[1]/td[1]/xsl:if/select/xsl:for&#x2D;each/option/xsl:attribute/xsl:value&#x2D;of" x="87" y="177"/><block path="table/tr[1]/td[1]/xsl:if/select/xsl:for&#x2D;each/option/xsl:if/=[0]" x="1" y="175"/><block path="table/tr[1]/td[1]/xsl:if/select/xsl:for&#x2D;each/option/xsl:if" x="47" y="177"/><block path="table/tr[1]/td[1]/xsl:if/select/xsl:for&#x2D;each/option/xsl:value&#x2D;of" x="47" y="57"/><block path="table/tr[1]/td[1]/xsl:if/select/xsl:for&#x2D;each/option/xsl:value&#x2D;of[1]" x="127" y="17"/><block path="table/tr[1]/td[1]/xsl:if/select/xsl:for&#x2D;each/option/xsl:value&#x2D;of[2]" x="87" y="17"/><block path="table/tr[1]/td[1]/xsl:if/select/xsl:for&#x2D;each/option/xsl:value&#x2D;of[3]" x="47" y="17"/><block path="table/tr[1]/td[1]/xsl:if[1]" x="87" y="137"/><block path="table/tr[5]/td[1]/textarea/xsl:value&#x2D;of" x="47" y="137"/><block path="table/tr[5]/td[1]/textarea/xsl:value&#x2D;of[1]" x="167" y="97"/><block path="table/xsl:for&#x2D;each" x="167" y="137"/><block path="table/xsl:for&#x2D;each/tr/td/xsl:value&#x2D;of" x="207" y="137"/><block path="table/xsl:for&#x2D;each/tr/td[1]/nobr/xsl:for&#x2D;each" x="97" y="67"/><block path="table/xsl:for&#x2D;each/tr/td[1]/nobr/xsl:for&#x2D;each/xsl:value&#x2D;of" x="207" y="97"/><block path="table/xsl:for&#x2D;each/tr/td[1]/nobr/xsl:for&#x2D;each/xsl:choose" x="217" y="27"/><block path="table/xsl:for&#x2D;each/tr/td[1]/nobr/xsl:for&#x2D;each/xsl:choose/=[0]" x="171" y="21"/><block path="table/xsl:for&#x2D;each/tr/td[1]/nobr/xsl:for&#x2D;each/xsl:choose/xsl:when/select/xsl:for&#x2D;each" x="207" y="217"/><block path="table/xsl:for&#x2D;each/tr/td[1]/nobr/xsl:for&#x2D;each/xsl:choose/xsl:when/select/xsl:for&#x2D;each/xsl:element/xsl:if/=[0]" x="81" y="215"/><block path="table/xsl:for&#x2D;each/tr/td[1]/nobr/xsl:for&#x2D;each/xsl:choose/xsl:when/select/xsl:for&#x2D;each/xsl:element/xsl:if" x="127" y="217"/><block path="table/xsl:for&#x2D;each/tr/td[1]/nobr/xsl:for&#x2D;each/xsl:choose/xsl:when/select/xsl:for&#x2D;each/xsl:element/xsl:value&#x2D;of" x="167" y="217"/><block path="table/xsl:for&#x2D;each/tr/td[1]/nobr/xsl:for&#x2D;each/xsl:choose/=[1]" x="171" y="49"/><block path="" x="47" y="97"/><block path="table/xsl:for&#x2D;each/tr/td[1]/nobr/xsl:for&#x2D;each/xsl:choose/xsl:otherwise/xsl:if/>[0]" x="161" y="175"/><block path="table/xsl:for&#x2D;each/tr/td[1]/nobr/xsl:for&#x2D;each/xsl:choose/xsl:otherwise/xsl:if/>[0]/number[0]" x="115" y="169"/><block path="table/xsl:for&#x2D;each/tr/td[1]/nobr/xsl:for&#x2D;each/xsl:choose/xsl:otherwise/xsl:if" x="207" y="177"/><block path="table/xsl:for&#x2D;each/tr/td[1]/nobr/xsl:for&#x2D;each/xsl:choose/xsl:otherwise/xsl:if[1]" x="127" y="177"/><block path="table/xsl:for&#x2D;each/tr/td[1]/nobr/xsl:for&#x2D;each/xsl:value&#x2D;of[1]" x="167" y="57"/></template></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->