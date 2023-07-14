<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="windows-1251" indent="yes"/>
	<xsl:template match="/form-data">
		<payment>
			<payer-account>
				<xsl:value-of select="payerAccountSelect"/>
			</payer-account>

			<receiver-name>
				<xsl:value-of select="receiverName"/>
			</receiver-name>

			<amount>
				<xsl:value-of select="amount"/>
			</amount>
			<xsl:variable name="addressFull" select="document('currentPerson.xml')/entity-list/entity[@key='person']/field[@name='residenceAddress']"/>
			<xsl:variable name="address">
				<xsl:choose>
					<xsl:when test="contains($addressFull, 'Российская Федерация,')">
						<xsl:value-of select="concat(substring-before($addressFull,'Российская Федерация'),'РФ',substring-after($addressFull,'Российская Федерация'))"/>
					</xsl:when>
					<xsl:otherwise>
						<xsl:value-of select="$addressFull"/>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:variable>
			<xsl:variable name="appointment" select="appointment"/>
			<xsl:variable name="receiverKey" select="receiverKey"/>
			<xsl:variable name="destination">
				<xsl:choose>
					<xsl:when test="/form-data/appointment='cellular-communication'">п/п по заяв. кл-та от !@date@!, адрес: <xsl:value-of select="$address"/>, оплата услуг сотовой связи (<xsl:value-of select="receiverName"/>) тел. <xsl:value-of select="phoneNumber"/> без налога НДС</xsl:when>
					<xsl:when test="/form-data/appointment='telephony'">п/п по заяв. кл-та от !@date@!, адрес: <xsl:value-of select="$address"/>, оплата услуг телефонии (<xsl:value-of select="receiverName"/>) тел. <xsl:value-of select="phoneNumber"/> без налога НДС.</xsl:when>
					<xsl:when test="/form-data/appointment='ip-telephony'">п/п по заяв. кл-та от !@date@!, адрес: <xsl:value-of select="$address"/>, оплата за услуги интернет (<xsl:value-of select="receiverName"/>) <xsl:for-each select="document(concat(concat(concat('additionalFields.xml?appointment=',appointment),'&amp;receiverKey='),receiverKey))/entity-list/*">, &#160;<xsl:value-of select="./field[@name='description']"/> &#160;<xsl:variable name="fieldName" select="./field[@name='name']"/>&#160;<xsl:value-of select="/form-data/*[name()=$fieldName]"/></xsl:for-each>, без налога НДС.</xsl:when>
					<xsl:when test="/form-data/appointment='satellite-connection'">п/п по заяв. кл-та от !@date@!, адрес: <xsl:value-of select="$address"/>, оплата за услуги коммерч. телев. (<xsl:value-of select="receiverName"/>) <xsl:for-each select="document(concat(concat(concat('additionalFields.xml?appointment=',appointment),'&amp;receiverKey='),receiverKey))/entity-list/*"><xsl:value-of select="./field[@name='description']"/>&#160;<xsl:variable name="fieldName" select="./field[@name='name']"/>&#160;<xsl:value-of select="/form-data/*[name()=$fieldName]"/>&#160;</xsl:for-each>, без налога НДС.</xsl:when>
					<xsl:when test="/form-data/appointment='credit-repayment'">п/п по заяв. кл-та от !@date@!,адрес: <xsl:value-of select="$address"/>, оплата за погашение кредита. Получатель <xsl:value-of select="receiverName"/> <xsl:for-each select="document(concat(concat(concat('additionalFields.xml?appointment=',appointment),'&amp;receiverKey='),receiverKey))/entity-list/*"><xsl:value-of select="./field[@name='description']"/>&#160;<xsl:variable name="fieldName" select="./field[@name='name']"/>&#160;<xsl:value-of select="/form-data/*[name()=$fieldName]"/>&#160;</xsl:for-each>, без налога НДС.</xsl:when>
					<xsl:when test="/form-data/appointment='payment-system'">п/п по заяв. кл-та от !@date@!, адрес: <xsl:value-of select="$address"/>, пополнение счета (<xsl:value-of select="receiverName"/>) <xsl:for-each select="document(concat(concat(concat('additionalFields.xml?appointment=',appointment),'&amp;receiverKey='),receiverKey))/entity-list/*">,&#160;<xsl:value-of select="./field[@name='description']"/> <xsl:variable name="fieldName" select="./field[@name='name']"/>&#160;<xsl:value-of select="/form-data/*[name()=$fieldName]"/>&#160;</xsl:for-each>, без налога НДС.</xsl:when>
					<xsl:when test="/form-data/appointment='gkh-payment'">п/п по заяв. кл-та от !@date@!, адрес: <xsl:value-of select="$address"/>, оплата услуг ЖКХ <xsl:value-of select="month"/>.<xsl:value-of select="year"/>, без налога НДС.</xsl:when>
					<xsl:when test="/form-data/appointment='electric-payment'">п/п по заяв. кл-та от !@date@!, адрес: <xsl:value-of select="$address"/>,оплата за электроэнергию <xsl:value-of select="receiverName"/> период оплаты <xsl:value-of select="month"/>.<xsl:value-of select="year"/>, без налога НДС.</xsl:when>
				</xsl:choose>
			</xsl:variable>
			<extra-parameters>
                <parameter name="amount-currency"
                           value="{amountCurrency}"
                           type="string"
                           />
				 <parameter name="receiver-key"
                           value="{receiverKey}"
                           type="string"
                           />
				<parameter name="from-account-type"
                           value="{fromAccountType}"
                           type="string"
                           />
				<parameter name="from-account-amount"
                           value="{fromAccountAmount}"
                           type="string"
                           />
				<xsl:apply-templates select="/form-data/*[name() != '']"/>
				<parameter name="destination"
						   value="{$destination}"
					 	   type="string"/>
			</extra-parameters>
			<ground>
				<xsl:choose>
					<xsl:when test="/form-data/appointment='cellular-communication'"><xsl:value-of select="document('currentPerson.xml')/entity-list/entity[@key='person']/field[@name='surName']"/>&#160;<xsl:value-of select="document('currentPerson.xml')/entity-list/entity[@key='person']/field[@name='firstName']"/>&#160;<xsl:value-of select="document('currentPerson.xml')/entity-list/entity[@key='person']/field[@name='patrName']"/></xsl:when>
					<xsl:when test="/form-data/appointment='credit-repayment'"><xsl:value-of select="document('currentPerson.xml')/entity-list/entity[@key='person']/field[@name='surName']"/>&#160;<xsl:value-of select="document('currentPerson.xml')/entity-list/entity[@key='person']/field[@name='firstName']"/>&#160;<xsl:value-of select="document('currentPerson.xml')/entity-list/entity[@key='person']/field[@name='patrName']"/></xsl:when>
					<xsl:when test="/form-data/appointment='satellite-connection'"><xsl:value-of select="document('currentPerson.xml')/entity-list/entity[@key='person']/field[@name='surName']"/>&#160;<xsl:value-of select="document('currentPerson.xml')/entity-list/entity[@key='person']/field[@name='firstName']"/>&#160;<xsl:value-of select="document('currentPerson.xml')/entity-list/entity[@key='person']/field[@name='patrName']"/></xsl:when>
					<xsl:when test="/form-data/appointment='ip-telephony'"><xsl:value-of select="document('currentPerson.xml')/entity-list/entity[@key='person']/field[@name='surName']"/>&#160;<xsl:value-of select="document('currentPerson.xml')/entity-list/entity[@key='person']/field[@name='firstName']"/>&#160;<xsl:value-of select="document('currentPerson.xml')/entity-list/entity[@key='person']/field[@name='patrName']"/></xsl:when>
					<xsl:when test="/form-data/appointment='telephony'"><xsl:value-of select="document('currentPerson.xml')/entity-list/entity[@key='person']/field[@name='surName']"/>&#160;<xsl:value-of select="document('currentPerson.xml')/entity-list/entity[@key='person']/field[@name='firstName']"/>&#160;<xsl:value-of select="document('currentPerson.xml')/entity-list/entity[@key='person']/field[@name='patrName']"/></xsl:when>
					<xsl:when test="/form-data/appointment='payment-system'"><xsl:value-of select="document('currentPerson.xml')/entity-list/entity[@key='person']/field[@name='surName']"/>&#160;<xsl:value-of select="document('currentPerson.xml')/entity-list/entity[@key='person']/field[@name='firstName']"/>&#160;<xsl:value-of select="document('currentPerson.xml')/entity-list/entity[@key='person']/field[@name='patrName']"/></xsl:when>
					<xsl:when test="/form-data/appointment='electric-payment'"><xsl:value-of select="document('currentPerson.xml')/entity-list/entity[@key='person']/field[@name='surName']"/>&#160;<xsl:value-of select="document('currentPerson.xml')/entity-list/entity[@key='person']/field[@name='firstName']"/>&#160;<xsl:value-of select="document('currentPerson.xml')/entity-list/entity[@key='person']/field[@name='patrName']"/></xsl:when>
					<xsl:when test="/form-data/appointment='gkh-payment'"><xsl:value-of select="document('currentPerson.xml')/entity-list/entity[@key='person']/field[@name='surName']"/>&#160;<xsl:value-of select="document('currentPerson.xml')/entity-list/entity[@key='person']/field[@name='firstName']"/>&#160;<xsl:value-of select="document('currentPerson.xml')/entity-list/entity[@key='person']/field[@name='patrName']"/></xsl:when>
				</xsl:choose>
			</ground>
		</payment>
	</xsl:template>

	<!-- ��� ���� ��� �������� -->
	<xsl:template match="/form-data/payerAccountSelect">
	</xsl:template>
	<xsl:template match="/form-data/receiverName"/>
	<xsl:template match="/form-data/amount"/>

	<!-- ��� ���� -->
	<xsl:template match="/form-data/*" priority="-99">
		<parameter name="{name()}" value="{text()}" type="string"/>
	</xsl:template>

</xsl:stylesheet><!-- Stylus Studio meta-information - (c) 2004-2005. Progress Software Corporation. All rights reserved.
<metaInformation>
<scenarios ><scenario default="yes" name="Scenario1" userelativepaths="yes" externalpreview="no" url="form&#x2D;data.xml" htmlbaseurl="" outputurl="" processortype="internal" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline="" additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator=""/></scenarios><MapperMetaTag><MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/><MapperBlockPosition></MapperBlockPosition><TemplateContext></TemplateContext><MapperFilter side="source"></MapperFilter></MapperMetaTag>
</metaInformation>
-->