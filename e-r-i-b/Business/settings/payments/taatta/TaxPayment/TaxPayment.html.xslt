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

		<!--TODO ����� ��� ������� ����?!! ���� ���� ���� � ����� �� ������ � ID(!!!!!) ���������?!!!-->
		<input id="documentNumber" name="documentNumber" type="hidden">
				<xsl:attribute name="value">
					<xsl:value-of select="documentNumber"/>
	            </xsl:attribute>
		</input>
		<table cellspacing="2" cellpadding="0" border="0"  class="paymentFon">
		     <tr>
		        <td align="right" valign="middle"><img src="{$resourceRoot}/images/TaxPayment.gif" border="0"/></td>
		        <td colspan="2" >
		            <table class="MaxSize paymentTitleFon">
		            <tr><td colspan="2">&nbsp;</td></tr>
		            <tr>
		                <td align="center" class="paperTitle silverBott">
		                    <table height="100%" width="420" cellspacing="0" cellpadding="0" class="paymentTitleFon">
		                    <tr>
		                        <td class="titleHelp">
		                             <span class="formTitle">������ �������</span>
		                             <br/>
			                        ������������ �������� ������� � ������ ����� � ���� ������ ������� � ������.		                             
		                        </td>
		                    </tr>
		                    </table>
		                </td>
		                <td width="100%">&nbsp;</td>
		            </tr>
		            </table>
		        </td>
		    </tr>
			<xsl:if test="$personAvailable">
				<tr>
					<td class="Width120 LabelAll">����� ���������<span class="asterisk">*</span></td>
					<td>
						<input type="text" id="documentNumber" name="documentNumber" size="10" value="{documentNumber}" disabled="true"/>
					</td>
				</tr>
				<tr>
					<td class="Width120 LabelAll">���� ���������<span class="asterisk">*</span></td>
					<td>
						<input type="text" id="documentDate" name="documentDate" size="10">
							<xsl:attribute name="value">
								<xsl:value-of select="documentDate"/>
							</xsl:attribute>
						</input>
					</td>
				</tr>
			</xsl:if>
			<tr>
				<td class="Width120 LabelAll">�����<span class="asterisk">*</span></td>
				<td><input id="amount" name="amount" type="text" value="{amount}" size="10"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">���� ��������<span class="asterisk">*</span></td>
				<td>
					<xsl:variable name="payerAccount" select="payerAccountSelect"/>
					<xsl:if test="$personAvailable">
						<select id="payerAccountSelect" name="payerAccountSelect">
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
						<select disabled="disabled"><option selected="selected">���� �������&nbsp;</option></select>
					</xsl:if>
				</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll" colspan="2" style="text-align:left;">&nbsp;<b>��������� ���������� �������</b></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">������������<span class="asterisk">*</span></td>
				<td>
					<input id="receiverName" name="receiverName" type="text" value="{receiverName}" size="60"/>
					<xsl:if test="$personAvailable">
						<input type="button" class="buttWhite smButt"  onclick="javascript:openSelectReceiver();" value="..."/>
					</xsl:if>
				</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">���<span class="asterisk">*</span></td>
				<td><input id="receiverINN" name="receiverINN" type="text" value="{receiverINN}" size="20" maxlength="10"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">���<span class="asterisk">*</span></td>
				<td><input id="receiverKPP" name="receiverKPP" type="text" value="{receiverKPP}" size="20" maxlength="9"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">����<span class="asterisk">*</span></td>
				<td><input id="receiverAccount" name="receiverAccount" type="text" value="{receiverAccount}" maxlength="20" size="24"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll" colspan="2" style="text-align:left;">&nbsp;<b>��������� ����� ����������</b></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">������������<span class="asterisk">*</span></td>
				<td><input id="receiverBank" name="receiverBank" type="text" value="{receiverBank}" size="60"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">���<span class="asterisk">*</span></td>
				<td><input id="receiverBIC" name="receiverBIC" type="text" value="{receiverBIC}" size="14" maxlength="9"/>
					<input type="button" class="buttWhite smButt" onclick="javascript:openNationalBanksDictionary(setBankInfo,getFieldValue('receiverBank'),getFieldValue('receiverBIC'));" value="..."/>
				</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">���. ����&nbsp;</td>
				<td><input id="receiverCorAccount" name="receiverCorAccount" type="text" value="{receiverCorAccount}" size="24" maxlength="20"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">���������� �������<span class="asterisk">*</span></td>
				<td><input id="ground" name="ground" type="text" value="{ground}" size="60"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll" colspan="2" style="text-align:left;">&nbsp;<b>��������� ����</b></td>
			</tr>
		    <tr>
			    <td colspan="2">
				    <table cellpadding="0" cellspacing="0" width="100%">
					<tr>
						<td class="Width120 LabelAll">������<span class="asterisk">*</span></td>
						<td width="80px"><input id="taxStatus" name="taxStatus" type="text" value="{taxStatus}" size="5"  onkeydown="enterNumericFld(event,this);"/><input type="button" class="buttWhite smButt"
										onclick="javascipt:displayPeriodList(event,'taxstatus')" value="..."/></td>
						<td class="Width120 LabelAll">���<span class="asterisk">*</span></td>
						<td><input id="taxKBK" name="taxKBK" type="text" value="{taxKBK}" size="25"/>
							<input type="button" class="buttWhite smButt" onclick="javascript:openKBKDictionary(setKBKInfo,getFieldValue('taxKBK'));" value="..."/>
						</td>
					</tr>
					<tr>
						<td class="Width120 LabelAll">���������<span class="asterisk">*</span></td>
						<td><input id="taxGround" name="taxGround" type="text" value="{taxGround}" size="5"  onkeydown="enterNumericFld(event,this);"/><input type="button" class="buttWhite smButt"
										onclick="javascipt:displayPeriodList(event,'taxfund')" value="..."/></td>
						<td class="Width120 LabelAll">�����<span class="asterisk">*</span></td>
						<td><input id="taxOKATO" name="taxOKATO" type="text" value="{taxOKATO}" size="25"/></td>
					</tr>
					 <tr>
						<td class="Width120 LabelAll">���<span class="asterisk">*</span></td>
						<td><input id="taxType" name="taxType" type="text" value="{taxType}" size="5"  onkeydown="enterNumericFld(event,this);"/><input type="button" class="buttWhite smButt"
										onclick="javascipt:displayPeriodList(event,'taxtype')" value="..."/></td>
						<td class="Width120 LabelAll">����<span class="asterisk">*</span></td>
						<td><input id="taxDocumentDate" name="taxDocumentDate" type="text" value="{taxDocumentDate}" size="10" onkeydown="enterNumericTemplateFld(event,this,DATE_TEMPLATE);"/></td>
					</tr>
					<tr>
						<td class="Width120 LabelAll">������<span class="asterisk">*</span></td>
						<td>
							<input id="taxPeriod1" name="taxPeriod1" type="text" maxLength="2" size="2" value="{taxPeriod1}"/>.
							<input id="taxPeriod2" name="taxPeriod2" type="text" onkeydown="enterNumericTemplateFld(event,this,'__');"
                                   maxLength="2" size="2" value="{taxPeriod2}"/>.
							<input id="taxPeriod3" name="taxPeriod3" type="text" onkeydown="enterNumericTemplateFld(event,this,'____');"
                                   maxLength="4" size="4" value="{taxPeriod3}"/>
                            
						    <input type="button" class="buttWhite smButt"
										onclick="javascipt:displayPeriodList(event,'periodfill')" value="..."/></td>
						<td class="Width120 LabelAll">�����<span class="asterisk">*</span></td>
						<td><input id="taxDocumentNumber" name="taxDocumentNumber" type="text" value="{taxDocumentNumber}" size="10"/></td>
					</tr>
					</table>
				</td>
			</tr>
			<tr><td colspan="2">&nbsp;</td></tr>
		</table>
		<script type="text/javascript">
			function openSelectReceiver()
			{
			   window.open( document.webRootPrivate + '/receivers/list.do?action=getReceiverInfo',
				'Receivers', "resizable=1,menubar=0,toolbar=0,scrollbars=1");
			}

			function setReceiverInfo(Info)
			{
			 setElement("receiverName",Info["receiverName"]);
 			 setElement("receiverINN",Info["receiverINN"]);
			 setElement("receiverKPP",Info["receiverKPP"]);
			 setElement("receiverAccount",Info["receiverAccount"]);
			 setElement("receiverBIC",Info["BIC"]);
			 setElement("receiverBank",Info["name"]);
			 setElement("receiverCorAccount",Info["account"]);
			}

			function setKBKInfo(Info)
			{
			 setElement("taxKBK",Info["code"]);
			}

			function setBankInfo(Info)
			{
			 setElement("receiverBIC",Info["BIC"]);
			 setElement("receiverBank",Info["name"]);
	 		 setElement("receiverCorAccount",Info["account"]);
			}

			function displayPeriodList(event,action)
			{
				win=window.open(document.webRootPrivate + "/PD4.do?action="+action,"","resizable=0,menubar=0,toolbar=0,scrollbars=0,status=0");
				win.resizeTo(500,250);
				win.moveTo(300,300);
			}
			function setTaxStatus(taxInfo)
			{
			  setElement('taxStatus',taxInfo["taxStatus"]);
			}
			function setTaxType(taxInfo)
			{
			  setElement('taxType',taxInfo["taxType"]);
			}
			function setTaxFund(taxInfo)
			{
			  setElement('taxGround',taxInfo["taxFund"]);
			}
			function setTaxPeriod(taxInfo)
			{
			  setElement('taxPeriod1',taxInfo["taxPeriod"]);
			  setElement('taxPeriod2',taxInfo["month"]);
			}

			function setTaxPeriodNull()
			{
			   setElement('taxPeriod1',"00");
			   setElement('taxPeriod2',"00");
			   setElement('taxPeriod3',"0000");
			}
		</script>

	</xsl:template>
    <xsl:template match="/form-data" mode="view">
		<table cellspacing="2" cellpadding="0" class="PaymentFon" style="width:540px;">

            <tr>
                <td align="right" valign="middle"><img src="{$resourceRoot}/images/TaxPayment.gif" border="0"/></td>
                <td>
                    <table>
                     <tr>
                        <td align="center" style="border-bottom:1 solid silver"  class="paperTitle">
                            <table width="420" cellspacing="0" cellpadding="0">
                            <tr>
                                <span class="formTitle">������ �������</span>
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
				<td class="Width120 LabelAll">����� ���������</td>
				<td>
					<xsl:value-of select="documentNumber"/>
				</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">���� ���������</td>
				<td>
					<xsl:value-of select="documentDate"/>
				</td>
			</tr>

			<xsl:variable name="adDate" select="admissionDate"/>
			<xsl:if test="not($adDate = '')">
				<tr>
					<td class="Width120 LabelAll">���� ������ �������</td>
					<td>
						<xsl:value-of select="admissionDate"/>
					</td>
				</tr>
			</xsl:if>

			<tr>
				<td class="Width120 LabelAll">�����</td>
				<td><xsl:value-of select="amount"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">���� ��������</td>
				<td>
					<xsl:variable name="payerAcc" select="payerAccountSelect"/>
					<!-- TODO �� ����� ��������� ������ ������ ������� �� ���� -->
					<xsl:variable name="payerAccount" select="document('all-accounts.xml')/entity-list/entity[@key=$payerAcc]"/>
					<xsl:value-of select="payerAccountSelect"/>[<xsl:value-of select="$payerAccount/field[@name='type']"/>]
					<xsl:value-of select="$payerAccount/field[@name='currencyCode']"/>
				</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll" colspan="2" style="text-align:left;">&nbsp;<b>��������� ���������� �������</b></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">������������</td>
				<td><xsl:value-of select="receiverName"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">���</td>
				<td><xsl:value-of select="receiverINN"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">���</td>
				<td><xsl:value-of select="receiverKPP"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">����</td>
				<td><xsl:value-of select="receiverAccount"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll" colspan="2" style="text-align:left;">&nbsp;<b>��������� ����� ����������</b></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">������������</td>
				<td><xsl:value-of select="receiverBank"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">���</td>
				<td><xsl:value-of select="receiverBIC"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">���. ����</td>
				<td><xsl:value-of select="receiverCorAccount"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">���������� �������</td>
				<td><xsl:value-of select="ground"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll" colspan="2" style="text-align:left;">&nbsp;<b>��������� ����</b></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">������</td>
				<td><xsl:value-of select="taxStatus"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">���</td>
				<td><xsl:value-of select="taxKBK"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">�����</td>
				<td><xsl:value-of select="taxOKATO"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">���������</td>
				<td><xsl:value-of select="taxGround"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">���</td>
				<td><xsl:value-of select="taxType"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">����</td>
				<td><xsl:value-of select="taxDocumentDate"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">�����</td>
				<td><xsl:value-of select="taxDocumentNumber"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">������</td>
				<td><xsl:value-of select="taxPeriod1"/>.<xsl:value-of select="taxPeriod2"/>.<xsl:value-of select="taxPeriod3"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">����� ��������</td>
				<td><xsl:value-of select="commissionAmount"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">������ �������</td>
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
			<xsl:when test="$code='I'">������</xsl:when>
			<xsl:when test="$code='S'">��������� � ���� ����������</xsl:when>
			<xsl:when test="$code='A'">������� ������ �����������</xsl:when>
			<xsl:when test="$code='W'">������</xsl:when>
			<xsl:when test="$code='S'">��������</xsl:when>
			<xsl:when test="$code='E'">�������</xsl:when>
			<xsl:otherwise>Fix me</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet><!-- Stylus Studio meta-information - (c) 2004-2005. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="Scenario1" userelativepaths="yes" externalpreview="no" url="form&#x2D;data.xml" htmlbaseurl="" outputurl="" processortype="xalan" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator=""/></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/><MapperBlockPosition></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->