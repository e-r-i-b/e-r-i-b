<?xml version='1.0' encoding='windows-1251'?>
<!DOCTYPE xsl:stylesheet [
	<!ENTITY nbsp "&#160;">
]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" version="1.0" indent="yes" encoding="windows-1251"/>

	<xsl:param name="mode" select="'edit'"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>
	<xsl:param name="personAvailable" select="true()"/>

	<xsl:variable name="tvProvider" select="document('providers')/entity-list/entity"/>

	<xsl:template match="/">
      <script type="text/javascript" language="javascript">
      function Provider(_name)
      { this.name = _name; }
      var providers = new Array();
      <xsl:for-each select="$tvProvider">
           <xsl:variable name="tvProvider" select="@key"/>
           providers["<xsl:value-of select="$tvProvider"/>"]=
				new Provider('<xsl:value-of select="."/>');
       </xsl:for-each>
     </script>
    <xsl:choose>
		<xsl:when test="$mode = 'edit'">
			<xsl:apply-templates mode="edit"/>
		</xsl:when>
		<xsl:when test="$mode = 'view'">
				<xsl:apply-templates mode="view"/>
			</xsl:when>
	</xsl:choose>
</xsl:template>

	<xsl:template match="/form-data" mode="edit"><xsl:variable name="account" select="clientAccount"/>
    <script type="text/javascript" language="javascript">
       function setProvider(provider)
       {
           var elems = document.getElementsByName("tvProvider");
           if ( provider == "" ) provider = elems[0].value;
           for(var i=0; i &lt; elems.length; i++)
              elems[i].checked = ( elems[i].value==provider );
       }

       </script>
    <table class="paymentFon">
        <tr>
           <td>
              <table class="MaxSize">
              <tr>
                 <td width="60px" align="right" valign="middle"><img src="{$resourceRoot}/images/TVPayment.gif" border="0"/>&nbsp;</td>
                 <td align="center" class="silverBott paperTitle">
                    <table height="100%" width="320px" cellspacing="0" cellpadding="0">
                    <tr>
                       <td class="titleHelp">
                          <span class="formTitle">Оплата услуг телевидения</span>
                          <br/>Используйте данную форму для оплаты услуг телевидения.
                       </td>
                    </tr>
                    </table>
                 </td>
                 <td width="20px">&nbsp;</td>
               </tr>
               </table>
            </td>
        </tr>
        <tr>
            <td>
    <table cellSpacing="2" cellPadding="0" border="0">
		<tbody>
			<xsl:for-each select="$tvProvider">
				<tr>
					<td class="Width120" align="right">
						<img src="{$resourceRoot}/images/{@key}.gif" border="0"/>
					</td>
					<td>
						<input type="radio" value="{@key}" name="tvProvider" style="border-right: medium none; border-top: medium none; border-left: medium none; border-bottom: medium none"/>
						<xsl:value-of select="."/>
					</td>
				</tr>
			</xsl:for-each>
		</tbody>
	</table>
        </td>
        </tr>
        <tr>
        <td>
    <table cellSpacing="2" cellPadding="0" border="0">
		<tbody>
			<tr>
				<td class="Width120 LabelAll">Списать со счета</td>
				<td><xsl:variable name="payerAccount" select="clientAccount"/>
					<xsl:if test="$personAvailable">
						<select name="clientAccount">
							<xsl:for-each select="document('accounts.xml')/entity-list/*">
								<option>
									<xsl:attribute name="value"><xsl:value-of select="./@key"/></xsl:attribute><xsl:if test="$payerAccount = ./@key"><xsl:attribute name="selected">true</xsl:attribute></xsl:if><xsl:value-of select="./@key"/> 
									[<xsl:value-of select="./field[@name='type']"/>]
									<xsl:value-of select="format-number(./field[@name='amountDecimal'], '0.00')"/><xsl:value-of select="./field[@name='currencyCode']"/>
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
				<td class="Width120 LabelAll" id="contractNumber">Номер договора</td>
				<td>
					<input name="contractNumber" type="text" value="{contractNumber}" maxlength="20" size="23"/></td>
			</tr>
            <tr>
				<td class="Width120 LabelAll">Сумма
					<span class="asterisk">*</span>
				</td>
				<td>
					<input id="amount" maxLength="10" size="23" value="{amount}" name="amount"/>&nbsp;RUB
				</td>
			</tr>
		</tbody>
	</table>
        </td>
        </tr>
        <tr><td colspan="2">&nbsp;</td></tr>
        </table>
    <script type="text/javascript">
	    setProvider('<xsl:value-of select="tvProvider"/>');
    </script>
</xsl:template>

	<xsl:template match="/form-data" mode="view">
		<table cellspacing="2" cellpadding="0" border="0" class="paymentFon">
            <tr>
                <td align="right" valign="middle"><img src="{$resourceRoot}/images/TVPayment.gif" border="0"/>&nbsp;</td>
                <td>
                    <table class="MaxSize">

                    <tr>
                        <td align="center"class="silverBott paperTitle">
                            <table height="100%" width="260px" cellspacing="0" cellpadding="0">
                            <tr>
                                <td class="titleHelp">
                                    <span class="formTitle">Оплата услуг телевидения</span>
                                    <br/>
                                    <span id="titleHelp"></span>
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
				<td class="Width120 LabelAll"><b>Оператор</b></td>
				<td>
                    <script type="text/javascript">
                        var providerName = providers['<xsl:value-of select="tvProvider"/>'].name;
						document.write(providerName);
					</script>
				</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll"><b>Списать со счета</b></td>
				<td>
					<xsl:variable name="acc" select="clientAccount"/>
					<xsl:variable name="account" select="document('accounts.xml')/entity-list/entity[@key=$acc]"/>
					<xsl:value-of select="clientAccount"/>&nbsp;
					<xsl:value-of select="$account/field[@name='currencyCode']"/>
				</td>
			</tr>
            <tr>
				<td class="Width120 LabelAll" name="contractNumber"><b>Номер договора</b></td>
				<td>
					<xsl:value-of select="contractNumber"/>
				</td>
			</tr>
            <tr>
				<td class="Width120 LabelAll"><b>Сумма</b></td>
				<td>
					<xsl:value-of select="amount"/>&nbsp;RUB</td>
			</tr>
            <tr><td colspan="2">&nbsp;</td></tr>
        </table>
	</xsl:template>
</xsl:stylesheet><!-- Stylus Studio meta-information - (c) 2004-2005. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="form&#x2D;data" userelativepaths="yes" externalpreview="no" url="form&#x2D;data.xml" htmlbaseurl="" outputurl="" processortype="internal" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator=""/></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/><MapperBlockPosition></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->