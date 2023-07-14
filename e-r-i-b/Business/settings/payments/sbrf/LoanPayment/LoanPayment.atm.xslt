<?xml version="1.0" encoding="windows-1251"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xalan="http://xml.apache.org/xalan"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:ph="java://com.rssl.phizic.business.persons.PersonHelper"
                exclude-result-prefixes="xalan mask ph">
	<xsl:import href="commonFieldTypes.atm.template.xslt"/>
	<xsl:output method="xml" version="1.0" indent="yes" omit-xml-declaration="yes"/>
	<xsl:param name="mode" select="'edit'"/>
	<xsl:param name="longOffer" select="false()"/>
	<xsl:param name="documentId"/>
	<xsl:param name="documentStatus"/>
	<xsl:param name="mobileApiVersion"/>
	<xsl:param name="changedFields"/>

	<xsl:template match="/">
		<xsl:choose>
			<xsl:when test="$mode = 'edit' and not($longOffer)">
				<xsl:apply-templates mode="edit"/>
			</xsl:when>
			<xsl:when test="$mode = 'edit' and $longOffer">
				<xsl:apply-templates mode="edit-long-offer"/>
			</xsl:when>
			<xsl:when test="$mode = 'view' and not($longOffer)">
				<xsl:apply-templates mode="view"/>
			</xsl:when>
			<xsl:when test="$mode = 'view' and $longOffer">
				<xsl:apply-templates mode="view-long-offer"/>
			</xsl:when>
		</xsl:choose>
	</xsl:template>

	<xsl:template match="/form-data" mode="edit">
		<xsl:variable name="activeCards" select="document('stored-active-not-virtual-not-credit-cards.xml')/entity-list/entity"/>
		<xsl:variable name="loans"       select="document('different-loans.xml')/entity-list/entity"/>

		<initialData>
			<form>LoanPayment</form>
			<LoanPayment>
				<availableFromResources>
					<xsl:call-template name="resourceField">
						<xsl:with-param name="name">fromResource</xsl:with-param>
						<xsl:with-param name="title">Карта списания</xsl:with-param>
						<xsl:with-param name="required" select="true()"/>
						<xsl:with-param name="editable" select="true()"/>
						<xsl:with-param name="visible" select="true()"/>
						<xsl:with-param name="listValues" select="$activeCards"/>
						<xsl:with-param name="link" select="fromResource"/>
						<xsl:with-param name="linkNumber" select="fromAccountSelect"/>
					</xsl:call-template>
				</availableFromResources>
				<availableLoanResources>
					<xsl:call-template name="loanField">
						<xsl:with-param name="required" select="true()"/>
						<xsl:with-param name="editable" select="true()"/>
						<xsl:with-param name="loans" select="$loans"/>
						<xsl:with-param name="loanLink" select="loan"/>
						<xsl:with-param name="loanLinkId" select="loanLinkId"/>
					</xsl:call-template>
				</availableLoanResources>
				<amount>
					<xsl:call-template name="moneyField">
						<xsl:with-param name="name">amount</xsl:with-param>
						<xsl:with-param name="title">Сумма</xsl:with-param>
						<xsl:with-param name="required" select="true()"/>
						<xsl:with-param name="editable" select="true()"/>
						<xsl:with-param name="visible" select="true()"/>
						<xsl:with-param name="value" select="amount"/>
					</xsl:call-template>
				</amount>
				<longOfferAllowed><xsl:value-of select="ph:isUdboSupported()"/></longOfferAllowed>
			</LoanPayment>
		</initialData>
	</xsl:template>

	<xsl:template match="/form-data" mode="view">
		<document>
			<form>LoanPayment</form>
			<status><xsl:value-of select="$documentStatus"/></status>
			<id><xsl:value-of select="$documentId"/></id>
            <documentNumber>
                <xsl:call-template name="integerField">
                    <xsl:with-param name="name">documentNumber</xsl:with-param>
                    <xsl:with-param name="title">Номер документа</xsl:with-param>
                    <xsl:with-param name="required" select="true()"/>
                    <xsl:with-param name="editable" select="false()"/>
                    <xsl:with-param name="visible" select="true()"/>
                    <xsl:with-param name="value" select="documentNumber"/>
                </xsl:call-template>
            </documentNumber>
			<LoanPaymentDocument>
				<xsl:call-template name="viewPayment"/>
			</LoanPaymentDocument>
		</document>
	</xsl:template>

	<xsl:template name="viewPayment">
		<xsl:variable name="cards" select="document('stored-active-not-virtual-not-credit-cards.xml')/entity-list/entity"/>
		<xsl:variable name="loans" select="document('different-loans.xml')/entity-list/entity"/>

		<loan>
			<xsl:call-template name="loanField">
				<xsl:with-param name="required" select="false()"/>
				<xsl:with-param name="editable" select="false()"/>
				<xsl:with-param name="loans" select="$loans"/>
				<xsl:with-param name="loanLinkId" select="loanLinkId"/>
				<xsl:with-param name="isView" select="true()"/>
			</xsl:call-template>
		</loan>
		<fromResource>
			<xsl:call-template name="resourceField">
				<xsl:with-param name="name">fromResource</xsl:with-param>
				<xsl:with-param name="title">Карта списания</xsl:with-param>
				<xsl:with-param name="required" select="false()"/>
				<xsl:with-param name="editable" select="false()"/>
				<xsl:with-param name="visible" select="true()"/>
				<xsl:with-param name="listValues" select="$cards"/>
				<xsl:with-param name="linkNumber" select="fromAccountSelect"/>
				<xsl:with-param name="isView" select="true()"/>
			</xsl:call-template>
		</fromResource>
		<amount>
			<xsl:call-template name="moneyField">
				<xsl:with-param name="name">amount</xsl:with-param>
				<xsl:with-param name="title">Сумма</xsl:with-param>
				<xsl:with-param name="required" select="false()"/>
				<xsl:with-param name="editable" select="false()"/>
				<xsl:with-param name="visible" select="true()"/>
				<xsl:with-param name="value" select="amount"/>
			</xsl:call-template>
		</amount>
	</xsl:template>

	<xsl:template match="/form-data" mode="edit-long-offer">
		<xsl:variable name="cards"              select="document('stored-active-not-virtual-not-credit-cards.xml')/entity-list/entity"/>
		<xsl:variable name="loans"              select="document('different-loans.xml')/entity-list/entity"/>
		<xsl:variable name="priorities"         select="document('priority.xml')/entity-list/entity"/>
		<xsl:variable name="sumTypesDictionary" select="document('sumTypesDictionary')/entity-list/entity"/>

		<initialData>
			<form>LoanPaymentLongOffer</form>
			<LoanPaymentLongOffer>
				<loan>
					<xsl:call-template name="loanField">
						<xsl:with-param name="required" select="true()"/>
						<xsl:with-param name="editable" select="true()"/>
						<xsl:with-param name="loans" select="$loans"/>
						<xsl:with-param name="loanLinkId" select="loanLinkId"/>
						<xsl:with-param name="isView" select="true()"/>
					</xsl:call-template>
				</loan>

				<fromResource>
					<xsl:call-template name="resourceField">
						<xsl:with-param name="name">fromResource</xsl:with-param>
						<xsl:with-param name="title">Карта списания</xsl:with-param>
						<xsl:with-param name="required" select="true()"/>
						<xsl:with-param name="editable" select="true()"/>
						<xsl:with-param name="visible" select="true()"/>
						<xsl:with-param name="listValues" select="$cards"/>
						<xsl:with-param name="linkNumber" select="fromAccountSelect"/>
						<xsl:with-param name="isView" select="true()"/>
					</xsl:call-template>
				</fromResource>

				<longOfferDetails>
					<startDate>
						<xsl:call-template name="dateField">
							<xsl:with-param name="name">longOfferStartDate</xsl:with-param>
							<xsl:with-param name="title">Дата начала действия</xsl:with-param>
							<xsl:with-param name="required" select="true()"/>
							<xsl:with-param name="editable" select="true()"/>
							<xsl:with-param name="visible" select="true()"/>
							<xsl:with-param name="value" select="longOfferStartDate"/>
						</xsl:call-template>
					</startDate>

					<endDate>
						<xsl:call-template name="dateField">
							<xsl:with-param name="name">longOfferEndDate</xsl:with-param>
							<xsl:with-param name="title">Дата окончания</xsl:with-param>
							<xsl:with-param name="required" select="true()"/>
							<xsl:with-param name="editable" select="true()"/>
							<xsl:with-param name="visible" select="true()"/>
							<xsl:with-param name="value" select="longOfferEndDate"/>
						</xsl:call-template>
					</endDate>

					<eventType>
						<xsl:call-template name="simpleField">
							<xsl:with-param name="name">eventType</xsl:with-param>
							<xsl:with-param name="title">Повторяется</xsl:with-param>
							<xsl:with-param name="type">list</xsl:with-param>
							<xsl:with-param name="required" select="true()"/>
							<xsl:with-param name="editable" select="false()"/>
							<xsl:with-param name="visible" select="true()"/>
							<xsl:with-param name="valueTag">
								<listType>
									<availableValues>
										<valueItem>
											<value>ONCE_IN_MONTH</value>
											<title>Каждый месяц</title>
											<selected>true</selected>
										</valueItem>
									</availableValues>
								</listType>
							</xsl:with-param>
						</xsl:call-template>
					</eventType>

					<payDay>
						<xsl:call-template name="integerField">
							<xsl:with-param name="name">longOfferPayDay</xsl:with-param>
							<xsl:with-param name="title">Число месяца</xsl:with-param>
							<xsl:with-param name="required" select="true()"/>
							<xsl:with-param name="editable" select="true()"/>
							<xsl:with-param name="visible" select="true()"/>
							<xsl:with-param name="value" select="longOfferPayDay"/>
						</xsl:call-template>
					</payDay>

					<firstPaymentDate>
						<xsl:call-template name="dateField">
							<xsl:with-param name="name">firstPaymentDate</xsl:with-param>
							<xsl:with-param name="title">Дата ближайшего платежа</xsl:with-param>
							<xsl:with-param name="required" select="true()"/>
							<xsl:with-param name="editable" select="true()"/>
							<xsl:with-param name="visible" select="false()"/>
							<xsl:with-param name="value" select="firstPaymentDate"/>
						</xsl:call-template>
					</firstPaymentDate>

					<priority>
						<xsl:call-template name="simpleField">
							<xsl:with-param name="name">longOfferPrioritySelect</xsl:with-param>
							<xsl:with-param name="title">Выполняется</xsl:with-param>
							<xsl:with-param name="type">list</xsl:with-param>
							<xsl:with-param name="required" select="true()"/>
							<xsl:with-param name="editable" select="true()"/>
							<xsl:with-param name="visible" select="true()"/>
							<xsl:with-param name="valueTag">
								<listType>
									<xsl:if test="count($priorities)>0">
										<availableValues>
											<xsl:variable name="priority" select="longOfferPrioritySelect"/>
											<xsl:for-each select="$priorities">
												<valueItem>
													<value><xsl:value-of select="./@key"/></value>
													<title><xsl:value-of select="./text()"/></title>
													<selected><xsl:value-of select="string(./@key = $priority)"/></selected>
												</valueItem>
											</xsl:for-each>
										</availableValues>
									</xsl:if>
								</listType>
							</xsl:with-param>
						</xsl:call-template>
					</priority>

					<isSumModify>
						<xsl:call-template name="booleanField">
							<xsl:with-param name="name">isSumModify</xsl:with-param>
							<xsl:with-param name="required" select="true()"/>
							<xsl:with-param name="editable" select="true()"/>
							<xsl:with-param name="visible" select="false()"/>
							<xsl:with-param name="value" select="isSumModify"/>
						</xsl:call-template>
					</isSumModify>

					<sumType>
						<xsl:call-template name="simpleField">
							<xsl:with-param name="name">longOfferSumType</xsl:with-param>
							<xsl:with-param name="title">Тип суммы</xsl:with-param>
							<xsl:with-param name="type">list</xsl:with-param>
							<xsl:with-param name="required" select="true()"/>
							<xsl:with-param name="editable" select="true()"/>
							<xsl:with-param name="visible" select="true()"/>
							<xsl:with-param name="valueTag">
								<listType>
									<availableValues>
										<xsl:variable name="sumType" select="longOfferSumType"/>
										<xsl:for-each select="$sumTypesDictionary">
											<valueItem>
												<value><xsl:value-of select="./@key"/></value>
												<title><xsl:value-of select="./text()"/></title>
												<selected><xsl:value-of select="string(./@key = $sumType)"/></selected>
											</valueItem>
										</xsl:for-each>
									</availableValues>
								</listType>
							</xsl:with-param>
						</xsl:call-template>
					</sumType>

					<fixedSumma>
						<amount>
							<xsl:call-template name="moneyField">
								<xsl:with-param name="name">amount</xsl:with-param>
								<xsl:with-param name="title">Сумма</xsl:with-param>
								<xsl:with-param name="required" select="true()"/>
								<xsl:with-param name="editable" select="true()"/>
								<xsl:with-param name="visible" select="true()"/>
								<xsl:with-param name="value" select="amount"/>
							</xsl:call-template>
						</amount>
					</fixedSumma>
				</longOfferDetails>
			</LoanPaymentLongOffer>
		</initialData>
	</xsl:template>

	<xsl:template match="/form-data" mode="view-long-offer">
		<document>
			<form>LoanPaymentLongOffer</form>
			<status><xsl:value-of select="$documentStatus"/></status>
			<id><xsl:value-of select="$documentId"/></id>

			<documentNumber>
            	<xsl:call-template name="integerField">
                    <xsl:with-param name="name">documentNumber</xsl:with-param>
                    <xsl:with-param name="title">Номер документа</xsl:with-param>
                    <xsl:with-param name="required" select="true()"/>
                    <xsl:with-param name="editable" select="false()"/>
                    <xsl:with-param name="visible" select="true()"/>
                    <xsl:with-param name="value" select="documentNumber"/>
				</xsl:call-template>
			</documentNumber>

			<LoanPaymentLongOfferDocument>
				<xsl:call-template name="viewPayment"/>

				<longOfferDetails>
					<startDate>
						<xsl:call-template name="dateField">
							<xsl:with-param name="name">longOfferStartDate</xsl:with-param>
							<xsl:with-param name="title">Дата начала действия</xsl:with-param>
							<xsl:with-param name="required" select="false()"/>
							<xsl:with-param name="editable" select="false()"/>
							<xsl:with-param name="visible" select="true()"/>
							<xsl:with-param name="value" select="longOfferStartDate"/>
						</xsl:call-template>
					</startDate>

					<endDate>
						<xsl:call-template name="dateField">
							<xsl:with-param name="name">longOfferEndDate</xsl:with-param>
							<xsl:with-param name="title">Дата окончания</xsl:with-param>
							<xsl:with-param name="required" select="false()"/>
							<xsl:with-param name="editable" select="false()"/>
							<xsl:with-param name="visible" select="true()"/>
							<xsl:with-param name="value" select="longOfferEndDate"/>
						</xsl:call-template>
					</endDate>

					<eventType>
						<xsl:call-template name="simpleField">
							<xsl:with-param name="name">eventType</xsl:with-param>
							<xsl:with-param name="title">Повторяется</xsl:with-param>
							<xsl:with-param name="type">list</xsl:with-param>
							<xsl:with-param name="required" select="false()"/>
							<xsl:with-param name="editable" select="false()"/>
							<xsl:with-param name="visible" select="true()"/>
							<xsl:with-param name="valueTag">
								<listType>
									<availableValues>
										<valueItem>
											<value>ONCE_IN_MONTH</value>
											<title>Каждый месяц</title>
											<selected>true</selected>
										</valueItem>
									</availableValues>
								</listType>
							</xsl:with-param>
						</xsl:call-template>
					</eventType>

					<firstPaymentDate>
						<xsl:call-template name="dateField">
							<xsl:with-param name="name">firstPaymentDate</xsl:with-param>
							<xsl:with-param name="title">Дата ближайшего платежа</xsl:with-param>
							<xsl:with-param name="required" select="false()"/>
							<xsl:with-param name="editable" select="false()"/>
							<xsl:with-param name="visible" select="false()"/>
							<xsl:with-param name="value" select="firstPaymentDate"/>
						</xsl:call-template>
					</firstPaymentDate>

					<priority>
						<xsl:call-template name="simpleField">
							<xsl:with-param name="name">longOfferPrioritySelect</xsl:with-param>
							<xsl:with-param name="title">Выполняется</xsl:with-param>
							<xsl:with-param name="type">list</xsl:with-param>
							<xsl:with-param name="required" select="false()"/>
							<xsl:with-param name="editable" select="false()"/>
							<xsl:with-param name="visible" select="true()"/>
							<xsl:with-param name="valueTag">
								<listType>
									<availableValues>
										<xsl:variable name="priority" select="longOfferPrioritySelect"/>
										<valueItem>
											<value><xsl:value-of select="$priority"/></value>
											<title><xsl:value-of select="document('priority.xml')/entity-list/entity[@key=$priority]/text()"/></title>
											<selected>true</selected>
										</valueItem>
									</availableValues>
								</listType>
							</xsl:with-param>
						</xsl:call-template>
					</priority>

					<sumType>
						<xsl:call-template name="simpleField">
							<xsl:with-param name="name">longOfferSumType</xsl:with-param>
							<xsl:with-param name="title">Тип суммы</xsl:with-param>
							<xsl:with-param name="type">list</xsl:with-param>
							<xsl:with-param name="required" select="false()"/>
							<xsl:with-param name="editable" select="false()"/>
							<xsl:with-param name="visible" select="true()"/>
							<xsl:with-param name="valueTag">
								<listType>
									<availableValues>
										<xsl:variable name="sumType" select="longOfferSumType"/>
										<valueItem>
											<value><xsl:value-of select="$sumType"/></value>
											<title><xsl:value-of select="document('sumTypesDictionary')/entity-list/entity[@key=$sumType]"/></title>
											<selected>true</selected>
										</valueItem>
									</availableValues>
								</listType>
							</xsl:with-param>
						</xsl:call-template>
					</sumType>

					<isSumModify>
						<xsl:call-template name="booleanField">
							<xsl:with-param name="name">isSumModify</xsl:with-param>
							<xsl:with-param name="required" select="false()"/>
							<xsl:with-param name="editable" select="false()"/>
							<xsl:with-param name="visible" select="false()"/>
							<xsl:with-param name="value" select="isSumModify"/>
						</xsl:call-template>
					</isSumModify>

					<payDayDescription>
						<xsl:call-template name="stringField">
							<xsl:with-param name="name">payDayDescription</xsl:with-param>
							<xsl:with-param name="title">Дата оплаты</xsl:with-param>
							<xsl:with-param name="required" select="false()"/>
							<xsl:with-param name="editable" select="false()"/>
							<xsl:with-param name="visible" select="true()"/>
							<xsl:with-param name="value">
								<xsl:variable name="firstDate"  select="firstPaymentDate"/>
								<xsl:variable name="day"    select="substring($firstDate, 9, 2)"/>
								<xsl:variable name="month"  select="substring($firstDate, 6, 2)"/>
								<xsl:variable name="monthDescription" select="document('monthsDictionary')/entity-list/entity[@key=$month]/text()"/>
								<xsl:value-of select="concat($monthDescription, '. ', $day, ' число')"/>
							</xsl:with-param>
						</xsl:call-template>
					</payDayDescription>
				</longOfferDetails>
			</LoanPaymentLongOfferDocument>
		</document>
	</xsl:template>

	<xsl:template name="resourceField">
		<xsl:param name="name"/>
		<xsl:param name="title"/>
		<xsl:param name="description" select="''"/>
		<xsl:param name="hint" select="''"/>
		<xsl:param name="required"/>
		<xsl:param name="editable"/>
		<xsl:param name="visible"/>
		<xsl:param name="listValues"/>
		<xsl:param name="isView" select="false()"/> <!--false=редактирование; true=просмотр-->
		<xsl:param name="link" select="''"/> <!--Ресурс. Ожидается link.getCode() (строка вида "card:1234"), но здесь может оказаться link.toString() (строка вида "Карта № ..."). при редактировании-->
		<xsl:param name="linkNumber"/> <!--Номер карты или счета. при просмотре и редактировании-->

		<xsl:variable name="isValidLinkCode" select="string-length($link)>0 and (contains($link, 'card:') or contains($link, 'account:'))"/>

		<name><xsl:value-of select="$name"/></name>
		<title><xsl:value-of select="$title"/></title>
		<xsl:if test="string-length($description)>0">
			<description><xsl:value-of select="$description"/></description>
		</xsl:if>
		<xsl:if test="string-length($hint)>0">
			<hint><xsl:value-of select="$hint"/></hint>
		</xsl:if>
		<type>resource</type>
		<required><xsl:value-of select="$required"/></required>
		<editable><xsl:value-of select="$editable"/></editable>
		<visible><xsl:value-of select="$visible"/></visible>
		<isSum><xsl:value-of select="'false'"/></isSum>
		<resourceType>
			<xsl:if test="count($listValues)>0">
				<availableValues>
					<xsl:for-each select="$listValues">
						<xsl:variable name="code" select="field[@name='code']/text()"/>
						<xsl:variable name="selected" select="($isValidLinkCode and $code=$link) or (not($isValidLinkCode) and @key=$linkNumber)"/>
						<xsl:if test="not($isView) or $selected">
							<valueItem>
								<value><xsl:value-of select="$code"/></value>
								<selected><xsl:value-of select="string($selected)"/></selected>
								<displayedValue>
									<xsl:value-of select="mask:getCutCardNumber(./@key)"/> [<xsl:value-of select="./field[@name='name']"/>]
								</displayedValue>
								<xsl:if test="$name = 'fromResource' and string-length(fromResourceCurrency)>0">
									<currency><xsl:value-of select="fromResourceCurrency"/></currency>
								</xsl:if>
								<xsl:if test="$name = 'toResource' and string-length(toResourceCurrency)>0">
									<currency><xsl:value-of select="toResourceCurrency"/></currency>
								</xsl:if>
							</valueItem>
						</xsl:if>
					</xsl:for-each>
				</availableValues>
			</xsl:if>
		</resourceType>
		<changed><xsl:value-of select="string(contains($changedFields, $name))"/></changed>
	</xsl:template>

	<xsl:template name="loanField">
		<xsl:param name="required"/>
		<xsl:param name="editable"/>
		<xsl:param name="loans"/>
		<xsl:param name="isView" select="false()"/> <!--false=редактирование; true=просмотр-->
		<xsl:param name="loanLink" select="''"/> <!--Ресурс. Ожидается link.getCode() (строка вида "loan:1234"), но здесь может оказаться link.toString() (строка вида "Кредит № ..."). при редактировании-->
		<xsl:param name="loanLinkId"/> <!--id линка на кредит. при просмотре и редактировании-->

		<xsl:variable name="isValidLinkCode" select="string-length($loanLink)>0 and starts-with($loanLink, 'loan:')"/>

		<name>loan</name>
		<title>Кредит</title>
		<type>resource</type>
		<required><xsl:value-of select="$required"/></required>
		<editable><xsl:value-of select="$editable"/></editable>
		<visible>true</visible>
		<isSum><xsl:value-of select="'false'"/></isSum>
		<resourceType>
			<xsl:if test="count($loans)>0">
				<availableValues>
					<xsl:for-each select="$loans">
						<xsl:variable name="currentLoanLinkId" select="field[@name='loanLinkId']/text()"/>
						<xsl:variable name="currentLoanLinkCode" select="concat('loan:', $currentLoanLinkId)"/>
						<xsl:variable name="selected" select="($isValidLinkCode and $currentLoanLinkCode=$loanLink) or (not($isValidLinkCode) and $currentLoanLinkId=$loanLinkId)"/>
						<xsl:if test="not($isView) or $selected">
							<valueItem>
								<value><xsl:value-of select="concat('loan:', $currentLoanLinkId)"/></value>
								<selected><xsl:value-of select="string($selected)"/></selected>
								<displayedValue>
									<xsl:value-of select="./field[@name='type']"/> (<xsl:value-of select="./field[@name='name']"/>)
								</displayedValue>
								<xsl:if test="string-length(loanCurrency)>0">
									<currency><xsl:value-of select="loanCurrency"/></currency>
								</xsl:if>
							</valueItem>
						</xsl:if>
					</xsl:for-each>
				</availableValues>
			</xsl:if>
		</resourceType>
		<changed><xsl:value-of select="string(contains($changedFields, 'loan'))"/></changed>
	</xsl:template>

</xsl:stylesheet>