<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [
<!ENTITY nbsp "&#160;">
]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" version="1.0" indent="yes" encoding="windows-1251"/>
	<xsl:param name="mode" select="'edit'"/>
	<xsl:param name="personAvailable" select="true()"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>
	<xsl:variable name="internetProviders" select="document('internetProviders')/entity-list/entity"/>

    <xsl:template match="/">
     <script type="text/javascript" language="javascript">
      function Provider(_name)
      { this.name = _name; }
      var providers = new Array();
         <xsl:for-each select="$internetProviders">
           <xsl:variable name="internetProviders" select="@key"/>
             providers["<xsl:value-of select="$internetProviders"/>"]=
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

    <xsl:template match="/form-data" mode="edit">
       <script type="text/javascript" language="javascript">
       function updateForm(provider)
       {   fieldName = document.getElementById("numClPayment");
           if (provider == "Zebra")
            {fieldName.innerText = "Номер карты " }
           else if (provider == "ROL")
            {fieldName.innerText = "Логин " }
           else
            {fieldName.innerText = "Номер&nbsp;лицевого&nbsp;счета&nbsp;" }
       }
       function setProvider(provider)
       {
           var elems = document.getElementsByName("internetProvider");
           if ( provider == "" ) provider = elems[0].value;
           for(var i=0; i &lt; elems.length; i++)
              elems[i].checked = ( elems[i].value==provider );
       }
       </script>
        <xsl:variable name="account" select="clientAccount"/>
        <table class="paymentFon">
        <tr>
           <td>
              <table class="MaxSize">
              <tr>
                 <td width="60px" align="right" valign="middle"><img src="{$resourceRoot}/images/InternetPayment.gif" border="0"/>&nbsp;</td>
                 <td align="center" style="border-bottom:1 solid silver"  class="paperTitle">
                    <table height="100%" width="320px" cellspacing="0" cellpadding="0">
                    <tr>
                       <td class="titleHelp">
                          <span class="formTitle">Оплата Интернет</span>
                          <br/>
                          Используйте данную форму для оплаты услуг доступа в Интернет.
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
        <table cellspacing="2" cellpadding="0" border="0">
			<xsl:for-each select="$internetProviders">
				<tr>
					<td class="Width120" align="right">
						<img src="{$resourceRoot}/images/{@key}.gif" border="0"/>
					</td>
					<td>
						<input type="radio" name="internetProvider" value="{@key}" onclick="javascript:updateForm('{@key}')" style="border:none">
							<xsl:value-of select="."/>
						</input>
					</td>
				</tr>
			</xsl:for-each>
		</table>
       </td>
       </tr>
       <tr>
       <td>
        <table cellspacing="2" cellpadding="0" border="0">
			<tr>
				<td class="Width120 LabelAll">Списать со счета</td>
				<td>
					<xsl:variable name="payerAccount" select="clientAccount"/>
					<xsl:if test="$personAvailable">
						<select id="clientAccount" name="clientAccount">
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
				<td class="Width120 LabelAll" id="numClPayment">Номер&nbsp;лицевого&nbsp;счета</td>
				<td>
					<input id="numberClPaymen" name="numberClPayment" type="text" value="{numberClPayment}" maxlength="20" size="23"/></td>
			</tr>
            <tr>
				<td class="Width120 LabelAll">Сумма </td>
				<td>
					<input id="amount" name="amount" type="text" value="{amount}" maxlength="9" size="23"/>&nbsp;RUB</td>
			</tr>
        </table>
       </td>
       </tr>
       <tr><td colspan="2">&nbsp;</td></tr>
       </table>
        <script type="text/javascript">
				setProvider('<xsl:value-of select="internetProvider"/>');
		</script>
    </xsl:template>

	<xsl:template match="/form-data" mode="view">
        <table cellspacing="2" cellpadding="0" border="0" class="paymentFon">
             <tr>
                <td align="right" valign="middle"><img src="{$resourceRoot}/images/InternetPayment.gif" border="0"/>&nbsp;</td>
                <td>
                    <table class="MaxSize">

                    <tr>
                        <td align="center" style="border-bottom:1 solid silver"  class="paperTitle">
                            <table height="100%" width="260px" cellspacing="0" cellpadding="0">
                            <tr>
                                <td class="titleHelp">
                                    <span class="formTitle">Оплата Интернет</span>
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
                        var providerName = providers['<xsl:value-of select="internetProvider"/>'].name;
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
				<td class="Width120 LabelAll" id="numClPayment" name="numberClPayment" style="font-weight:bold;">Номер&nbsp;лицевого&nbsp;счета</td>
				<td>
					<xsl:value-of select="numberClPayment"/>
				</td>
			</tr>
            <tr>
				<td class="Width120 LabelAll"><b>Сумма</b></td>
				<td>
					<xsl:value-of select="amount"/>&nbsp;RUB</td>
			</tr>
            <tr><td colspan="2">&nbsp;</td></tr>
        </table>
        <script type="text/javascript">
           var provider = '<xsl:value-of select="internetProvider"/>';
           fieldName = document.getElementById("numClPayment");
           if (provider == "Zebra")
            {fieldName.innerText = "Номер карты " }
           else if (provider == "ROL")
            {fieldName.innerText = "Логин " }
           else
            {fieldName.innerText = "Номер&nbsp;лицевого&nbsp;счета&nbsp;" }
        </script>
    </xsl:template>
</xsl:stylesheet><!-- Stylus Studio meta-information - (c) 2004-2005. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="Scenario1" userelativepaths="yes" externalpreview="no" url="InternetPayment.pfd.xml" htmlbaseurl="" outputurl="" processortype="internal" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator=""/></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/><MapperBlockPosition></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->