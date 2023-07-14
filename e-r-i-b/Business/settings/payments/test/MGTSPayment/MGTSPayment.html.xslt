<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [
	<!ENTITY nbsp "&#160;">
]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" version="1.0" indent="yes" encoding="windows-1251"/>
	<xsl:param name="mode" select="'view'"/>
	<xsl:param name="personAvailable" select="true()"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
	<xsl:param name="resourceRoot" select="'webRoot'"/>
	<xsl:template match="/">
		<xsl:choose>
			<xsl:when test="$mode = 'edit'">
				<xsl:apply-templates mode="edit"/>
                  <script language="javascript">
          function initTemplates() {
             setInputTemplate("phoneNumber",PHONE_NUMBER_TEMPLATE);
             setInputTemplate("areaCode",AREA_CODE_TEMPLATE);

           }
          function clearMasks(event){
             clearInputTemplate("phoneNumber",PHONE_NUMBER_TEMPLATE);
             clearInputTemplate("areaCode",AREA_CODE_TEMPLATE);
           }
           function checkData() {
             return true;
           }
           initTemplates();

           </script>
            </xsl:when>
			<xsl:when test="$mode = 'view'">
				<xsl:apply-templates mode="view"/>
			</xsl:when>
		</xsl:choose>
	</xsl:template>
	<xsl:template match="/form-data" mode="edit">
		<xsl:variable name="account" select="payer-account"/>
		<table cellspacing="2" cellpadding="0" border="0"  class="paymentFon">
             <tr>
                <td align="right" valign="middle"><img src="{$resourceRoot}/images/MgtsPayment.gif" border="0"/>&nbsp;</td>
                <td colspan="2">
                    <table class="MaxSize">
                    <tr>
                        <td align="center" class="silverBott paperTitle">
                            <table height="100%" width="280px" cellspacing="0" cellpadding="0">
                            <tr>
                                <td class="titleHelp">
                                    <span class="formTitle">МГТС. Абонентская плата</span>
                                    <br/>
                                    Используйте данную форму для оплаты ежемесячной абонентской платы за домашний телефон МГТС.
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
				<td class="Width120 LabelAll">Списать со счета</td>
				<td>
					<xsl:if test="$personAvailable">
						<select name="payer-account" id="payer-account" >
                            <xsl:variable name="payerAccount" select="payer-account"/>
                             <xsl:for-each select="document('accounts.xml')/entity-list/*">
								<option>
									<xsl:attribute name="value"><xsl:value-of select="./@key"/></xsl:attribute>
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
				<td class="Width120 LabelAll">Номер телефона <span class="asterisk">*</span>
				</td>
				<td>
					<input id="areaCode" name="areaCode" type="text" value="{areaCode}" maxlength="3" size="4" onkeydown="enterNumericTemplateFld(event,this,AREA_CODE_TEMPLATE);onTabClick(event,'phoneNumber')"/>&nbsp;
					<input id="phoneNumber" name="phoneNumber" type="text" value="{phoneNumber}" maxlength="7" size="10" onkeydown="enterNumericTemplateFld(event,this,PHONE_NUMBER_TEMPLATE)"/>
				</td>
			</tr>
            <tr>
				<td class="Width120 LabelAll">Номер квартиры <span class="asterisk">*</span>
				</td>
				<td>
					<input id="flatNumber" name="flatNumber" type="text" value="{flatNumber}" maxlength="4" size="5"/>
				</td>
			</tr>
            <tr>
				<td class="Width120 LabelAll">Сумма <span class="asterisk">*</span>
				</td>
				<td>
					<input id="amount" name="amount" type="text" value="{amount}" maxlength="10" size="19"/>&nbsp;RUB</td>
			</tr>
            <tr><td colspan="2">&nbsp;</td></tr>
        </table>
        <script>
            var defaultCode = (('<xsl:value-of select="areaCode"/>' == '') ? '495' : '<xsl:value-of select="areaCode"/>');
			setElement('areaCode', defaultCode);
        </script>
	</xsl:template>
	<xsl:template match="/form-data" mode="view">
		<table cellspacing="2" cellpadding="0" border="0" class="PaymentFon">
            <tr>
                <td align="right" valign="middle"><img src="{$resourceRoot}/images/MgtsPayment.gif" border="0"/>&nbsp;</td>
                <td>
                    <table class="MaxSize">
                     <tr>
                        <td align="center" class="silverBott paperTitle">
                            <table height="100%" width="300px" cellspacing="0" cellpadding="0">
                            <tr>
                                <td class="titleHelp">
                                  <span class="formTitle">МГТС. Абонентская плата</span>
                                  <br/>
                                  <span id="titleHelp"></span>
                             </td>
                            </tr>
                            </table>
                        </td>

                    </tr>
                    </table>
                </td>
            </tr>
			<tr>
				<td class="Width120 LabelAll"><b>Списать со счета</b></td>
				<td>
					<xsl:variable name="acc" select="accountsSelect"/>
					<xsl:variable name="account" select="document('accounts.xml')/entity-list/entity[@key=$acc]"/>
					<xsl:value-of select="payer-account"/>&nbsp;
		  <xsl:value-of select="$account/field[@name='currencyCode']"/>
				</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll"><b>Телефон</b></td>
				<td> +7(<xsl:value-of select="areaCode"/>)&nbsp;<xsl:value-of select="phoneNumber"/>
				</td>
			</tr>
            <tr>
				<td class="Width120 LabelAll"><b>Номер квартиры</b></td>
				<td><xsl:value-of select="flatNumber" size="5" maxlength="4" />
				</td>
			</tr>
            <tr>
				<td class="Width120 LabelAll"><b>Сумма</b></td>
				<td>
					<xsl:value-of select="amount"/>&nbsp;RUB
                </td>
			</tr>
            <tr><td colspan="2">&nbsp;</td></tr>
        </table>
	</xsl:template>
</xsl:stylesheet>

