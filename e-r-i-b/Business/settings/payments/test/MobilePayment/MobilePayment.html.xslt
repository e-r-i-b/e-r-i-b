<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [
	<!ENTITY nbsp "&#160;">
]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" version="1.0" indent="yes" encoding="windows-1251"/>
	<xsl:param name="mode" select="'view'"/>
	<xsl:param name="personAvailable" select="true()"/>
	<xsl:param name="webRoot" select="'webRoot'"/>
	<xsl:param name="resourceRoot" select="'resourceRoot'"/>
	<xsl:variable name="providers" select="document('providers')/entity-list/entity"/>
	<xsl:template match="/">
		<script type="text/javascript" language="javascript">
         function Provider(_name, _codes)
         {
            this.name = _name;
            this.codes= _codes;
         }
		<!-- fill structure -->
			var providers = new Array();
			<xsl:for-each select="$providers">
				<xsl:variable name="provider" select="@key"/>

			providers["<xsl:value-of select="$provider"/>"]=
				new Provider('<xsl:value-of select="."/>',
								new Array(<xsl:for-each select="document(concat('AC','-', $provider))/entity-list/entity">
									"<xsl:value-of select="@key"/>"
									<xsl:if test="position()!=last()">,</xsl:if>
				</xsl:for-each>)
				);
			var defaultProvider = '<xsl:value-of select="$providers[position()=1]/@key"/>';
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
         function setProvider(provider,areaCode)
         {
           var elems = document.getElementsByName("providersSelect");
           if ( provider == "" ) provider=defaultProvider;
           for(var i=0; i &lt; elems.length; i++) {
              elems[i].checked =( elems[i].value==provider);}
              updateCodes(areaCode);
         }

         function getProvider()
         {
           var elems = document.getElementsByName("providersSelect");
           for(var i=0; i &lt; elems.length; i++)
               if ( elems[i].checked) return elems[i].value;
         }

        function updateCodes(areaCode)
		{
          var areaCodesSelect = getElement('areaCodesSelect');
          var codes = providers[getProvider()].codes;
          clearOptions(areaCodesSelect);
		  for(var i=0; i &lt; codes.length; i++)
            addOption (areaCodesSelect,codes[i],codes[i],codes[i]==areaCode);
	  }
      </script>
		<xsl:variable name="account" select="accountsSelect"/>
        <table class="paymentFon">
        <tr>
           <td>
              <table class="MaxSize">
              <tr>
                 <td width="60px" align="right" valign="middle"><img src="{$resourceRoot}/images/MobilePayment.gif" border="0"/>&nbsp;</td>
                 <td align="center" class="silverBott paperTitle">
                    <table height="100%" width="320px" cellspacing="0" cellpadding="0">
                    <tr>
                       <td class="titleHelp">
                          <span class="formTitle">Оплата сотовой связи</span>
                          <br/>
                          Используйте данную форму для оплаты услуг мобильной связи.
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
			<xsl:for-each select="$providers">
				<tr>
					<td class="Width120" align="right">
						<img src="{$resourceRoot}/images/{@key}.gif" border="0"/>
					</td>
					<td>
						<input type="radio" name="providersSelect" value="{@key}" onclick="javascript:updateCodes()" style="border:none">
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
                    <table class="MaxSize" cellspacing="0" cellspadding="0">
                    <tr>
                        <td>
                    <xsl:variable name="payerAccount" select="accountsSelect"/>
					<xsl:if test="$personAvailable">
						<select id="accountsSelect" name="accountsSelect">
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
                        <td valign="bottom" class="helpPayment" src="Строчка для подсказки что это такое и с чем его едят. Разминка для фантазии ;)"><img src="{$resourceRoot}/images/help1.gif" height="20px" width="20px"/></td>
                    </tr>
                    </table>
                </td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Номер телефона <span class="asterisk">*</span>
				</td>
				<td>
					<select id="areaCodesSelect" name="areaCodesSelect"/>
					<input id="phoneNumber" name="phoneNumber" type="text" value="{phoneNumber}" maxlength="7" size="10"/>
				</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Сумма <span class="asterisk">*</span>
				</td>
				<td>
					<input id="amount" name="amount" type="text" value="{amount}" maxlength="10" size="19"/>&nbsp;RUB</td>
			</tr>
		</table>
        </td>
    </tr>
    <tr><td colspan="2">&nbsp;</td></tr>
    </table>
        <script type="text/javascript">
				setProvider('<xsl:value-of select="providersSelect"/>','<xsl:value-of select="areaCodesSelect"/>');
			</script>
	</xsl:template>
	<xsl:template match="/form-data" mode="view">
		<table cellspacing="2" cellpadding="0" border="0" class="paymentFon">
             <tr>
                <td align="right" valign="middle"><img src="{$resourceRoot}/images/MobilePayment.gif" border="0"/>&nbsp;&nbsp;</td>
                <td>
                    <table class="MaxSize">

                    <tr>
                        <td align="center"  class="silverBott paperTitle">
                            <table height="100%" width="260px" cellspacing="0" cellpadding="0">
                            <tr>
                                <td class="titleHelp">
                                    <span class="formTitle">Оплата сотовой связи</span>
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
						var providerName = providers['<xsl:value-of select="providersSelect"/>'].name;
						document.write(providerName);
					</script>
				</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll"><b>Списать со счета</b></td>
				<td>
					<xsl:variable name="acc" select="accountsSelect"/>
					<xsl:variable name="account" select="document('accounts.xml')/entity-list/entity[@key=$acc]"/>
					<xsl:value-of select="accountsSelect"/>&nbsp;
		  <xsl:value-of select="$account/field[@name='currencyCode']"/>
				</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll"><b>Телефон</b></td>
				<td> +7(<xsl:value-of select="areaCodesSelect"/>)&nbsp;<xsl:value-of select="phoneNumber"/>
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
</xsl:stylesheet>
<!-- Stylus Studio meta-information - (c) 2004-2005. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="Scenario1" userelativepaths="yes" externalpreview="no" url="form&#x2D;data.xml" htmlbaseurl="" outputurl="" processortype="internal" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator=""/></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/><MapperBlockPosition></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->
