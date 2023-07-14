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

		<input id="amount" name="amount" type="hidden">
				<xsl:attribute name="value">
					<xsl:value-of select="amount"/>
			    </xsl:attribute>
		</input>

		<input id="currency" name="currency" type="hidden">
				<xsl:attribute name="value">
					<xsl:value-of select="currency"/>
			    </xsl:attribute>
		</input>

		<input id="payerAccountSelect" name="payerAccountSelect" type="hidden">
				<xsl:attribute name="value">
					<xsl:value-of select="payerAccountSelect"/>
			    </xsl:attribute>
		</input>

		<input id="recalledDocumentNumber" name="recalledDocumentNumber" type="hidden">
				<xsl:attribute name="value">
					<xsl:value-of select="recalledDocumentNumber"/>
			    </xsl:attribute>
		</input>

		<input id="recalledDocumentDate" name="recalledDocumentDate" type="hidden">
				<xsl:attribute name="value">
					<xsl:value-of select="recalledDocumentDate"/>
			    </xsl:attribute>
		</input>

		<!--TODO зачем это скрытое поле?!! если ниже есть с таким же именем и ID(!!!!!) нескрытое?!!!-->
		<input id="documentNumber" name="documentNumber" type="hidden">
				<xsl:attribute name="value">
					<xsl:value-of select="documentNumber"/>
	            </xsl:attribute>
		</input>

		<input id="receiverBankCode" name="receiverBankCode" type="hidden">
				<xsl:attribute name="value">
					<xsl:value-of select="receiverBankCode"/>
	            </xsl:attribute>
		</input>

		<input id="receiverBankName" name="receiverBankName" type="hidden">
				<xsl:attribute name="value">
					<xsl:value-of select="receiverBankName"/>
	            </xsl:attribute>
		</input>

		<input id="receiverBankPhone" name="receiverBankPhone" type="hidden">
				<xsl:attribute name="value">
					<xsl:value-of select="receiverBankPhone"/>
	            </xsl:attribute>
		</input>

		<!--<input id="addInformation" name="addInformation" type="hidden">-->
				<!--<xsl:attribute name="value">-->
					<!--<xsl:value-of select="addInformation"/>-->
	            <!--</xsl:attribute>-->
		<!--</input>-->

		<input id="parentId" name="parentId" type="hidden">
				<xsl:attribute name="value">
					<xsl:value-of select="parentId"/>
	            </xsl:attribute>
		</input>

		<table cellspacing="2" cellpadding="0" border="0"  class="paymentFon">
		     <tr>
		        <td align="right" valign="middle"><img src="{$resourceRoot}/images/RurPaymentPh.gif" border="0"/></td>
		        <td colspan="2" >
		            <table class="MaxSize paymentTitleFon">
		            <tr><td colspan="2">&nbsp;</td></tr>
		            <tr>
		                <td align="center" class="silverBott paperTitle">
		                    <table height="100%" width="420" cellspacing="0" cellpadding="0" class="paymentTitleFon">
		                    <tr>
		                        <td class="titleHelp">
		                             <span class="formTitle">Редактирование перевода по сети Contact</span>
		                             <br/>
		                             Используйте данную форму для редактирования перевода по сети CONTACT
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
				<td class="Width120 LabelAll">Номер документа</td>
				<td>
					<input type="text" id="documentNumber" name="documentNumber" size="10" value="{documentNumber}" disabled="true"/>
				</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Дата документа</td>
				<td>
					<input type="text" id="documentDate" name="documentDate" size="10">
						<xsl:attribute name="value">
							<xsl:value-of select="documentDate"/>
						</xsl:attribute>
					</input>
				</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Номер изменяемого перевода</td>
				<td>
					<input type="text" id="recalledDocumentNumber" name="recalledDocumentNumber" size="10" disabled="true">
						<xsl:attribute name="value">
							<xsl:value-of select="recalledDocumentNumber"/>
						</xsl:attribute>
					</input>
				</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Дата изменяемого перевода</td>
				<td>
					<input type="text" id="recalledDocumentDate" name="recalledDocumentDate" size="10" disabled="true">
						<xsl:attribute name="value">
							<xsl:value-of select="recalledDocumentDate"/>
						</xsl:attribute>
					</input>
				</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Сумма перевода</td>
				<td><input id="amount" name="amount" type="text" value="{amount}" size="10"  disabled="true"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll" colspan="2" style="text-align:left;">&nbsp;<b>Получатель платежа</b></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Фамилия</td>
				<td><input id="receiverSurName" name="receiverSurName" type="text" value="{receiverSurName}" size="20"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Имя</td>
				<td><input id="receiverFirstName" name="receiverFirstName" type="text" value="{receiverFirstName}" size="20"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Отчество</td>
				<td><input id="receiverPatrName" name="receiverPatrName" type="text" value="{receiverPatrName}" size="20"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll" colspan="2" style="text-align:left;">&nbsp;<b>Реквизиты банка получателя</b></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Код</td>
				<td><input id="receiverBankCodeV" name="receiverBankCodeV" type="text" value="{receiverBankCode}" size="6" disabled="true"/>
					<input type="button" class="buttWhite smButt" onclick="javascript:openContactMembersDictionary(setBankInfo,getFieldValue('receiverBankCodeV'));" value="..."/>
				</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Банк</td>
				<td><input id="receiverBankNameV" name="receiverBankNameV" type="text" value="{receiverBankName}" size="60" disabled="true"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Телефон</td>
				<td><input id="receiverBankPhoneV" name="receiverBankPhoneV" type="text" value="{receiverBankPhone}" size="20" disabled="true"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Дополнительная информация</td>
				<td><input id="addInformation" name="addInformation" type="text" value="{addInformation}" size="60"/></td>
			</tr>
			<tr><td colspan="2">&nbsp;</td></tr>
		</table>
		<script type="text/javascript">
			function openSelectReceiver()
			{
			   window.open( document.webRootPrivate + '/receivers.do?action=getReceiverInfo',
				'Receivers', "resizable=1,menubar=0,toolbar=0,scrollbars=1");
			}

			function setReceiverInfo(Info)
			{
			 setElement("receiverSurName",Info["surName"]);
			 setElement("receiverFirstName",Info["firstName"]);
		     setElement("receiverPatrName",Info["patrName"]);
 			 setElement("receiverBankCodeV",Info["contactBankCode"]);
			 setElement("receiverBankCode",Info["contactBankCode"]);
			 setElement("addInformation",Info["addInfo"]);
 			 setElement("receiverBankName",Info["contactBankName"]);
		     setElement("receiverBankPhone",Info["contactBankPhone"]);
			 setElement("receiverBankNameV",Info["contactBankName"]);
		     setElement("receiverBankPhoneV",Info["contactBankPhone"]);
			}

			function setBankInfo(Info)
			{
			 setElement("receiverBankCode",Info["code"]);
			 setElement("receiverBankName",Info["name"]);
		     setElement("receiverBankPhone",Info["phone"]);
	 		 setElement("receiverBankCodeV",Info["code"]);
			 setElement("receiverBankNameV",Info["name"]);
		     setElement("receiverBankPhoneV",Info["phone"]);
			}

			function setCurrencyCode()
				{
					var currency = document.getElementById("сurrencyCodeSelect");
					var currencyCodeElem       = document.getElementById("currency");
					currencyCodeElem.value  = currency.value;
				}
			function init()
			 {
				 var currencyElem    = document.getElementById("currency");
				 if (currencyElem.value=="")
					 currencyElem.value = "RUB"
			 }

			 init();
		</script>

	</xsl:template>
    <xsl:template match="/form-data" mode="view">
		<table cellspacing="2" cellpadding="0" class="PaymentFon" style="width:540px;">

            <tr>
                <td align="right" valign="middle"><img src="{$resourceRoot}/images/RurPaymentPh.gif" border="0"/></td>
                <td>
                    <table>
                     <tr>
                        <td align="center" class="silverBott paperTitle">
                            <table width="420" cellspacing="0" cellpadding="0">
                            <tr>
                                <span class="formTitle">Редактирование перевода по сети Contact</span>
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
				<td class="Width120 LabelAll">Номер документа</td>
				<td>
					<xsl:value-of select="documentNumber"/>
				</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Дата документа</td>
				<td>
					<xsl:value-of select="documentDate"/>
				</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Номер изменяемого перевода</td>
				<td>
					<xsl:value-of select="recalledDocumentNumber"/>
				</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Дата изменяемого перевода</td>
				<td>
					<xsl:value-of select="recalledDocumentDate"/>
				</td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Сумма перевода</td>
				<td><xsl:value-of select="amount"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll" colspan="2" style="text-align:left;">&nbsp;<b>Получатель платежа</b></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Фамилия</td>
				<td><xsl:value-of select="receiverSurName"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Имя</td>
				<td><xsl:value-of select="receiverFirstName"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Отчество</td>
				<td><xsl:value-of select="receiverPatrName"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll" colspan="2" style="text-align:left;">&nbsp;<b>Реквизиты банка получателя</b></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Код</td>
				<td><xsl:value-of select="receiverBankCode"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Банк</td>
				<td><xsl:value-of select="receiverBankName"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Телефон</td>
				<td><xsl:value-of select="receiverBankPhone"/></td>
			</tr>
			<tr>
				<td class="Width120 LabelAll">Дополнительная информация</td>
				<td><xsl:value-of select="addInformation"/></td>
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
			<xsl:when test="$code='S'">Отправлен в банк получателя</xsl:when>
			<xsl:when test="$code='A'">Получен банком получателем</xsl:when>
			<xsl:when test="$code='X'">Выдан</xsl:when>
			<xsl:when test="$code='C'">Аннулирование</xsl:when>
			<xsl:when test="$code='M'">Изменение</xsl:when>
			<xsl:when test="$code='L'">Отозван</xsl:when>
			<xsl:when test="$code='N'">Возвращен</xsl:when>
			<xsl:when test="$code='W'">Принят</xsl:when>
			<xsl:when test="$code='S'">Исполнен</xsl:when>
			<xsl:when test="$code='E'">Отказан</xsl:when>
			<xsl:otherwise>Fix me</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet><!-- Stylus Studio meta-information - (c) 2004-2005. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="Scenario1" userelativepaths="yes" externalpreview="no" url="form&#x2D;data.xml" htmlbaseurl="" outputurl="" processortype="xalan" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator=""/></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/><MapperBlockPosition></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->