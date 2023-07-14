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

		<input id="currency" name="currency" type="hidden">
				<xsl:attribute name="value">
					<xsl:value-of select="currency"/>
			    </xsl:attribute>
		</input>
		<!--TODO зачем это скрытое поле?!! если ниже есть с таким же именем и ID(!!!!!) нескрытое?!!!-->
		<input id="documentNumber" name="documentNumber" type="hidden">
				<xsl:attribute name="value">
					<xsl:value-of select="documentNumber"/>
	            </xsl:attribute>
		</input>
		
		<table cellspacing="2" cellpadding="0" border="0"  class="paymentFon">
		     <tr>
		        <td align="right" valign="middle"><img src="{$resourceRoot}/images/GKHPayment.gif" border="0"/></td>
		        <td colspan="2" >
		            <table class="MaxSize paymentTitleFon">
		            <tr><td colspan="2">&nbsp;</td></tr>
		            <tr>
		                <td align="center" class="silverBott paperTitle">
		                    <table height="100%" width="420" cellspacing="0" cellpadding="0" class="paymentTitleFon">
		                    <tr>
		                        <td class="titleHelp">
		                             <span class="formTitle">Оплата услуг ЖКХ</span>
		                             <br/>
		                             Перечисление денежных средств с вашего счета для оплаты жилищно-коммунальных услуг по форме единого платежного документа. 
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
				<td class="Width120 LabelAll">Номер платежа</td>
				<td>
					<input type="text" id="documentNumber" name="documentNumber" size="10" value="{documentNumber}" disabled="true"/>
				</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Дата платежа</td>
				<td>
					<input type="text" id="documentDate" name="documentDate" size="10">
						<xsl:attribute name="value">
							<xsl:value-of select="documentDate"/>
						</xsl:attribute>
					</input>
				</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Фамилия, имя, отчество</td>
				<td>
					<input type="text" id="payerName" name="payerName" size="40">
						<xsl:attribute name="value">
							<xsl:value-of select="payerName"/>
						</xsl:attribute>
					</input>
				</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Код плательщика</td>
				<td>
					<input type="text" id="payerCode" name="payerCode" size="12">
						<xsl:attribute name="value">
							<xsl:value-of select="payerCode"/>
						</xsl:attribute>
					</input>
				</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Адрес</td>
				<td>
					<xsl:variable name="payerAddress" select="payerAddress"/>
					<input type="text" id="payerAddress" name="payerAddress"  size="40">
						<xsl:if test="$payerAddress = ''">
							<xsl:attribute name="value">г. Москва</xsl:attribute>
						</xsl:if>
						<xsl:if test="$payerAddress != ''">
							<xsl:attribute name="value"><xsl:value-of select="payerAddress"/></xsl:attribute>
						</xsl:if>
					</input>
				</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Период оплаты&nbsp;</td>
				<td>
					<xsl:variable name="monthSelect" select="monthSelect"/>
					<select id="monthSelect" name="monthSelect">
						<option value="1">
							<xsl:if test="$monthSelect = '1'">
								<xsl:attribute name="selected">true</xsl:attribute>
							</xsl:if>
							Январь
						</option>
						<option value="2">
							<xsl:if test="$monthSelect = '2'">
								<xsl:attribute name="selected">true</xsl:attribute>
							</xsl:if>
							Февраль
						</option>
						<option value="3">
							<xsl:if test="$monthSelect = '3'">
								<xsl:attribute name="selected">true</xsl:attribute>
							</xsl:if>
							Март
						</option>
						<option value="4">
							<xsl:if test="$monthSelect = '4'">
								<xsl:attribute name="selected">true</xsl:attribute>
							</xsl:if>
							Апрель
						</option>
						<option value="5">
							<xsl:if test="$monthSelect = '5'">
								<xsl:attribute name="selected">true</xsl:attribute>
							</xsl:if>
							Май
						</option>
						<option value="6">
							<xsl:if test="$monthSelect = '6'">
								<xsl:attribute name="selected">true</xsl:attribute>
							</xsl:if>
							Июнь
						</option>
						<option value="7">
							<xsl:if test="$monthSelect = '7'">
								<xsl:attribute name="selected">true</xsl:attribute>
							</xsl:if>
							Июль
						</option>
						<option value="8">
							<xsl:if test="$monthSelect = '8'">
								<xsl:attribute name="selected">true</xsl:attribute>
							</xsl:if>
							Август
						</option>
						<option value="9">
							<xsl:if test="$monthSelect = '9'">
								<xsl:attribute name="selected">true</xsl:attribute>
							</xsl:if>
							Сентябрь
						</option>
						<option value="10">
							<xsl:if test="$monthSelect = '10'">
								<xsl:attribute name="selected">true</xsl:attribute>
							</xsl:if>
							Октябрь
						</option>
						<option value="11">
							<xsl:if test="$monthSelect = '11'">
								<xsl:attribute name="selected">true</xsl:attribute>
							</xsl:if>
							Ноябрь
						</option>
						<option value="12">
							<xsl:if test="$monthSelect = '12'">
								<xsl:attribute name="selected">true</xsl:attribute>
							</xsl:if>
							Декабрь
						</option>
					</select>
					<select id="yearSelect" name="yearSelect">
						<xsl:variable name="yearSelect" select="yearSelect"/>
						<option value="2006">
							<xsl:if test="$yearSelect = '2006'">
								<xsl:attribute name="selected">true</xsl:attribute>
							</xsl:if>
							2006
						</option>
						<option value="2007">
							<xsl:if test="$yearSelect = '2007'">
								<xsl:attribute name="selected">true</xsl:attribute>
							</xsl:if>
							2007
						</option>
						<option value="2008">
							<xsl:if test="$yearSelect = '2008'">
								<xsl:attribute name="selected">true</xsl:attribute>
							</xsl:if>
							2008
						</option>
						<option value="2009">
							<xsl:if test="$yearSelect = '2009'">
								<xsl:attribute name="selected">true</xsl:attribute>
							</xsl:if>
							2009
						</option>
						<option value="2010">
							<xsl:if test="$yearSelect = '2010'">
								<xsl:attribute name="selected">true</xsl:attribute>
							</xsl:if>
							2010
						</option>
						<option value="2011">
							<xsl:if test="$yearSelect = '2011'">
								<xsl:attribute name="selected">true</xsl:attribute>
							</xsl:if>
							2011
						</option>
						<option value="2012">
							<xsl:if test="$yearSelect = '2012'">
								<xsl:attribute name="selected">true</xsl:attribute>
							</xsl:if>
							2012
						</option>
					</select>
				</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Сумма к оплате с учетом страхования</td>
				<td><input id="amount" name="amount" type="text" value="{amount}" size="10"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Сумма добровольного страхования</td>
				<td><input id="insuranceAmount" name="insuranceAmount" type="text" value="{insuranceAmount}" size="10"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Счет списания</td>
				<td>
					<xsl:variable name="payerAccount" select="payerAccountSelect"/>
					<xsl:if test="$personAvailable">
						<select id="payerAccountSelect" name="payerAccountSelect" onchange="javascript:updateCurrencyCodes()">
							<xsl:for-each select="document('active-rur-accounts.xml')/entity-list/*">
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
						<select disabled="disabled"><option selected="selected">Счет клиента</option></select>
					</xsl:if>
				</td>
			</tr>
			<tr><td colspan="2">&nbsp;</td></tr>
		</table>
		<script type="text/javascript">
		var currencyCodes = new Array();
	    function updateCurrencyCodes()
	    {
	        var payerAccountSelect         = document.getElementById("payerAccountSelect");
	        var code                     = currencyCodes[payerAccountSelect.value];
	        var currency   = document.getElementById("currency");
	        currency.value = code;
		}


	    function init()
	    {
	    <xsl:variable name="allAccounts" select="document('rur-accounts.xml')/entity-list/*"/>
	    <xsl:for-each select="$allAccounts">
	        currencyCodes['<xsl:value-of select="./@key"/>'] = '<xsl:value-of select="./field[@name='currencyCode']"/>';
	    </xsl:for-each>

	        updateCurrencyCodes();
	    }

	    init();
	</script>

	</xsl:template>
	<xsl:template match="/form-data" mode="view">
		<table cellspacing="2" cellpadding="0" class="PaymentFon" style="width:540px;">

            <tr>
                <td align="right" valign="middle"><img src="{$resourceRoot}/images/GKHPayment.gif" border="0"/></td>
                <td>
                    <table>
                     <tr>
                        <td align="center" class="silverBott paperTitle">
                            <table width="420" cellspacing="0" cellpadding="0">
                            <tr>
                                <span class="formTitle">Оплата услуг ЖКХ</span>
                                <br/>
                                <td class="titleHelp"><span id="titleHelp"></span></td>
                            </tr>
                            </table>
                        </td>
                    </tr>
                    </table>
                </td>
            </tr>

			<tr>
				<td class="Width120 LabelAll">Номер платежа</td>
				<td>
					<xsl:value-of select="documentNumber"/>
				</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Дата платежа</td>
				<td>
					<xsl:value-of select="documentDate"/>
				</td>
			</tr>
			<xsl:variable name="adDate" select="admissionDate"/>
			<xsl:if test="not($adDate = '')">
				<tr>
					<td class="Width120 LabelAll">Дата приема платежа</td>
					<td>
						<xsl:value-of select="admissionDate"/>
					</td>
				</tr>
			</xsl:if>
			<tr>
				<td class="Width120 LabelAll">Фамилия, имя, отчество</td>
				<td>
					<xsl:value-of select="payerName"/>
				</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Код плательщика</td>
				<td>
					<xsl:value-of select="payerCode"/>
				</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Адрес</td>
				<td>
					<xsl:value-of select="payerAddress"/>
				</td>
			</tr>
			<xsl:variable name="monthV" select="monthSelect"/>
			<tr>
				<td class="Width120 LabelAll">Период оплаты</td>
				<td>
					<xsl:if test="$monthV = '1'">январь</xsl:if>
					<xsl:if test="$monthV = '2'">февраль</xsl:if>
					<xsl:if test="$monthV = '3'">март</xsl:if>
					<xsl:if test="$monthV = '4'">апрель</xsl:if>
					<xsl:if test="$monthV = '5'">май</xsl:if>
					<xsl:if test="$monthV = '6'">июнь</xsl:if>
					<xsl:if test="$monthV = '7'">июль</xsl:if>
					<xsl:if test="$monthV = '8'">август</xsl:if>
					<xsl:if test="$monthV = '9'">сентябрь</xsl:if>
					<xsl:if test="$monthV = '10'">октябрь</xsl:if>
					<xsl:if test="$monthV = '11'">ноябрь</xsl:if>
					<xsl:if test="$monthV = '12'">декабрь</xsl:if>
					&nbsp;<xsl:value-of select="yearSelect"/>
				</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Сумма к оплате с учетом страхования</td>
				<td><xsl:value-of select="amount"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Сумма добровольного страхования</td>
				<td><xsl:value-of select="insuranceAmount"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Счет списания</td>
				<td>
					<xsl:variable name="payerAcc" select="payerAccountSelect"/>
					<!-- TODO на форме просмотра данные должны браться из базы -->
					<xsl:variable name="payerAccount" select="document('rur-accounts.xml')/entity-list/entity[@key=$payerAcc]"/>
					<xsl:value-of select="payerAccountSelect"/>[<xsl:value-of select="$payerAccount/field[@name='type']"/>]
					<xsl:value-of select="$payerAccount/field[@name='currencyCode']"/>
				</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Сумма комиссии</td>
				<td><xsl:value-of select="commissionAmount"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Статус платежа</td>
				<td>
					<xsl:call-template name="state2text">
					<xsl:with-param name="code">
						<xsl:value-of select="state"/>
					</xsl:with-param>
				</xsl:call-template>
				</td>
			</tr>
			<tr><td colspan="2">&nbsp;<br/>&nbsp;</td></tr>

		</table>
	</xsl:template>
	<xsl:template name="state2text">
		<xsl:param name="code"/>
		<xsl:choose>
			<xsl:when test="$code='I'">Введен</xsl:when>
			<xsl:when test="$code='W'">Принят</xsl:when>
			<xsl:when test="$code='S'">Исполнен</xsl:when>
			<xsl:when test="$code='R'">Отказан</xsl:when>
			<xsl:otherwise>Fix me</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet><!-- Stylus Studio meta-information - (c) 2004-2005. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="Scenario1" userelativepaths="yes" externalpreview="no" url="form&#x2D;data.xml" htmlbaseurl="" outputurl="" processortype="xalan" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator=""/></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/><MapperBlockPosition></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->