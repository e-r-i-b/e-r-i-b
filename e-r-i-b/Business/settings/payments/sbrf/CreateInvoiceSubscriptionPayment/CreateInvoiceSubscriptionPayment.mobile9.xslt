<?xml version="1.0" encoding="windows-1251"?>
<!DOCTYPE xsl:stylesheet [
        <!ENTITY nbsp "&#160;">
        ]>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:mask="java://com.rssl.phizic.utils.MaskUtil"
                xmlns:au="java://com.rssl.phizic.business.accounts.AccountsUtil"
                xmlns:mu="java://com.rssl.phizic.web.util.MoneyFunctions"
                xmlns:spu="java://com.rssl.phizic.web.util.ServiceProviderUtil"
                xmlns:pu="java://com.rssl.phizic.web.util.PersonInfoUtil"
                xmlns:su="java://com.rssl.phizic.utils.StringUtils"
                xmlns:bh="java://com.rssl.phizic.business.basket.BasketHelper"
                xmlns:dh="java://com.rssl.phizic.utils.DateHelper"
                xmlns:xalan = "http://xml.apache.org/xalan"
                exclude-result-prefixes="mask au mu spu pu su bh dh xalan">
    <!--������� ������� �����-->
    <xsl:import href="billingFieldTypes.mobile.template.xslt"/>
    <xsl:import href="commonFieldTypes.mobile.template.xslt"/>
    <xsl:output method="xml" version="1.0" indent="yes" omit-xml-declaration="yes"/>
	<xsl:param name="mode" select="'edit'"/>
    <xsl:param name="longOffer" select="true()"/>
    <xsl:param name="documentStatus" select="''"/>
    <xsl:param name="documentId" select="''"/>
    <xsl:param name="checkAvailable" select="''"/>
    <xsl:param name="isMobileLightScheme" select="false()"/> <!--Light-����� ����������?-->
    <xsl:param name="isMobileLimitedScheme" select="true()"/> <!--����������������� ����?-->
    <xsl:param name="changedFields"/>
    <xsl:variable name="formData" select="/form-data"/>
    <xsl:variable name="isInitialLongOfferState" select="$documentStatus = 'INITIAL_LONG_OFFER'"/>
    <xsl:variable name="extendedFields"     select="document('extendedFields.xml')/Attributes/*"/>
    <xsl:variable name="accessAutoSubTypes" select="document('supportedAutoPays.xml')/entity-list"/>

	<xsl:template match="/">
        <xsl:choose>
			<xsl:when test="$mode = 'edit' and not($isInitialLongOfferState)">
            	<xsl:apply-templates mode="edit"/>
			</xsl:when>
            <xsl:when test="$mode = 'edit' and $isInitialLongOfferState">
                <xsl:apply-templates mode="view"/>
            </xsl:when>
			<xsl:when test="$mode = 'view'">
				<xsl:apply-templates mode="view"/>
			</xsl:when>
		</xsl:choose>
    </xsl:template>

    <xsl:template match="/form-data" mode="edit">
        <xsl:variable name="bankDetails" select="bankDetails"/>
        <xsl:variable name="activeCards" select="document('stored-active-not-virtual-cards-all-visible.xml')/entity-list/*"/>

        <document>
            <!-- ��������� ��������� -->
            <form>CreateInvoiceSubscriptionPayment</form>
            <status><xsl:value-of select="$documentStatus"/></status>
            <xsl:if test="string-length($documentId)>0">
                <id><xsl:value-of select="$documentId"/></id>
            </xsl:if>
            <xsl:if test="string-length($checkAvailable)>0">
                <checkAvailable><xsl:value-of select="$checkAvailable"/></checkAvailable>
            </xsl:if>

            <CreateInvoiceSubscriptionPaymentDocument>
                <!--����������-->
                <receiver>
                    <!--������������ ����������-->
                    <name>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">receiverName</xsl:with-param>
                            <xsl:with-param name="title">������������</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="receiverName"/>
                        </xsl:call-template>
                    </name>
                    <!--�������� ����������-->
                    <xsl:if test="string-length(receiverDescription)>0">
                       <description>
                           <xsl:variable name="receiverDescr">
                               <xsl:call-template name="tokenizeString">
                                  <xsl:with-param name="string" select="receiverDescription"/>
                               </xsl:call-template>
                           </xsl:variable>

                           <xsl:call-template name="stringField">
                               <xsl:with-param name="name">receiverDescription</xsl:with-param>
                               <xsl:with-param name="title">���������� �� ���������� �������</xsl:with-param>
                               <xsl:with-param name="required" select="false()"/>
                               <xsl:with-param name="editable" select="false()"/>
                               <xsl:with-param name="visible" select="true()"/>
                               <xsl:with-param name="value" select="$receiverDescr"/>
                           </xsl:call-template>
                       </description>
                    </xsl:if>
                    <!--������ ������-->
                    <region>
                        <xsl:variable name="payRegion">
                            <xsl:call-template name="regionTemplate">
                                <xsl:with-param name="providerId" select="recipient"/>
                                <xsl:with-param name="personRegionName" select="pu:getPersonRegionName()"/>
                            </xsl:call-template>
                        </xsl:variable>
                        <xsl:call-template name="stringField">
                           <xsl:with-param name="name">region</xsl:with-param>
                           <xsl:with-param name="title">������ ������</xsl:with-param>
                           <xsl:with-param name="required" select="false()"/>
                           <xsl:with-param name="editable" select="false()"/>
                           <xsl:with-param name="visible" select="true()"/>
                           <xsl:with-param name="value" select="$payRegion"/>
                        </xsl:call-template>
                    </region>

                    <xsl:if test="$bankDetails = 'true'">
                        <!--��� ����������-->
                        <inn>
                            <xsl:call-template name="integerField">
                                <xsl:with-param name="name">receiverINN</xsl:with-param>
                                <xsl:with-param name="title">���</xsl:with-param>
                                <xsl:with-param name="required" select="false()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value" select="receiverINN"/>
                            </xsl:call-template>
                        </inn>
                        <!--���� ����������-->
                        <account>
                           <xsl:call-template name="integerField">
                               <xsl:with-param name="name">receiverAccount</xsl:with-param>
                               <xsl:with-param name="title">����</xsl:with-param>
                               <xsl:with-param name="required" select="false()"/>
                               <xsl:with-param name="editable" select="false()"/>
                               <xsl:with-param name="visible" select="true()"/>
                               <xsl:with-param name="value" select="receiverAccount"/>
                           </xsl:call-template>
                        </account>
                        <!--���� ����������-->
                        <bank>
                            <!--������������-->
                            <name>
                                <xsl:call-template name="stringField">
                                    <xsl:with-param name="name">receiverBankName</xsl:with-param>
                                    <xsl:with-param name="title">������������</xsl:with-param>
                                    <xsl:with-param name="required" select="false()"/>
                                    <xsl:with-param name="editable" select="false()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="value" select="receiverBankName"/>
                                </xsl:call-template>
                            </name>
                            <!--��� �����-->
                            <bic>
                                <xsl:call-template name="stringField">
                                    <xsl:with-param name="name">receiverBIC</xsl:with-param>
                                    <xsl:with-param name="title">���</xsl:with-param>
                                    <xsl:with-param name="required" select="false()"/>
                                    <xsl:with-param name="editable" select="false()"/>
                                    <xsl:with-param name="visible" select="true()"/>
                                    <xsl:with-param name="value" select="receiverBIC"/>
                                </xsl:call-template>
                            </bic>

                            <!--������� �����-->
                            <xsl:if test="string-length(receiverCorAccount)>0">
                                <corAccount>
                                    <xsl:call-template name="integerField">
                                        <xsl:with-param name="name">receiverCorAccount</xsl:with-param>
                                        <xsl:with-param name="title">�������</xsl:with-param>
                                        <xsl:with-param name="required" select="false()"/>
                                        <xsl:with-param name="editable" select="false()"/>
                                        <xsl:with-param name="visible" select="true()"/>
                                        <xsl:with-param name="value" select="receiverCorAccount"/>
                                    </xsl:call-template>
                                </corAccount>
                            </xsl:if>
                        </bank>
                    </xsl:if>
                </receiver>
                <!--������-->
                <xsl:if test="string-length(nameService)>0">
                    <serviceName>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">nameService</xsl:with-param>
                            <xsl:with-param name="title">������</xsl:with-param>
                            <xsl:with-param name="required" select="true()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="nameService"/>
                        </xsl:call-template>
                    </serviceName>
                </xsl:if>
                <!-- ������ ��������� -->
                <subscriptionDetails>
                    <extendedFields>
                        <xsl:for-each select="$extendedFields">
                            <xsl:variable name="id" select="./NameBS"/>
                            <xsl:variable name="val">
                                <xsl:variable name="currentValue" select="$formData/*[name()=$id]"/>
                                <xsl:choose>
                                    <!--���� ������ �������� ����, ����� ���-->
                                    <xsl:when test="string-length($currentValue) > 0">
                                        <xsl:value-of select="$currentValue"/>
                                    </xsl:when>
                                    <!--����� - ���������������� ��������-->
                                    <xsl:otherwise>
                                        <xsl:value-of select="./DefaultValue"/>
                                    </xsl:otherwise>
                                </xsl:choose>
                            </xsl:variable>
                            <!--��� ����������� ����� �������� ������� "IsRiskRequisite"-->
                            <xsl:variable name="requisiteTypes" select="./RequisiteTypes"/>
                            <xsl:variable name="isRiskField" select="string-length($requisiteTypes) > 0 and contains($requisiteTypes, 'IsRiskRequisite')"/>
                            <xsl:variable name="editable" select="./IsEditable='true' and (not($isMobileLightScheme) or (not($isMobileLimitedScheme) and (./IsKey='false')))"/>

                            <xsl:choose>
                                <xsl:when test="./Type = 'date'">
                                    <field>
                                        <xsl:call-template name="dateBillingField">
                                            <xsl:with-param name="name" select="./NameBS"/>
                                            <xsl:with-param name="title" select="./NameVisible"/>
                                            <xsl:with-param name="description" select="./Description"/>
                                            <xsl:with-param name="hint" select="./Comment"/>
                                            <xsl:with-param name="required" select="./IsRequired"/>
                                            <xsl:with-param name="editable" select="$editable"/>
                                            <xsl:with-param name="visible" select="./IsVisible"/>
                                            <xsl:with-param name="value" select="$val"/>
                                            <xsl:with-param name="isRiskField" select="$isRiskField"/>
                                        </xsl:call-template>
                                    </field>
                                </xsl:when>
                                <xsl:when test="./Type = 'calendar'">
                                    <field>
                                        <xsl:call-template name="calendarBillingField">
                                            <xsl:with-param name="name" select="./NameBS"/>
                                            <xsl:with-param name="title" select="./NameVisible"/>
                                            <xsl:with-param name="description" select="./Description"/>
                                            <xsl:with-param name="hint" select="./Comment"/>
                                            <xsl:with-param name="required" select="./IsRequired"/>
                                            <xsl:with-param name="editable" select="$editable"/>
                                            <xsl:with-param name="visible" select="./IsVisible"/>
                                            <xsl:with-param name="value" select="$val"/>
                                            <xsl:with-param name="isRiskField" select="$isRiskField"/>
                                        </xsl:call-template>
                                    </field>
                                </xsl:when>
                                <xsl:when test="./Type = 'string'">
                                    <field>
                                        <xsl:call-template name="stringBillingField">
                                            <xsl:with-param name="name" select="./NameBS"/>
                                            <xsl:with-param name="title" select="./NameVisible"/>
                                            <xsl:with-param name="description" select="./Description"/>
                                            <xsl:with-param name="hint" select="./Comment"/>
                                            <xsl:with-param name="minLength" select="./MinLength"/>
                                            <xsl:with-param name="maxLength" select="./MaxLength"/>
                                            <xsl:with-param name="required" select="./IsRequired"/>
                                            <xsl:with-param name="editable" select="$editable"/>
                                            <xsl:with-param name="visible" select="./IsVisible"/>
                                            <xsl:with-param name="isSum" select="./IsSum"/>
                                            <xsl:with-param name="value" select="$val"/>
                                            <xsl:with-param name="dictTypeFieldName" select="'dictFieldId'"/>
                                            <xsl:with-param name="isRiskField" select="$isRiskField"/>
                                        </xsl:call-template>
                                    </field>
                                </xsl:when>
                                <xsl:when test="./Type = 'integer'">
                                    <field>
                                        <xsl:call-template name="integerBillingField">
                                            <xsl:with-param name="name" select="./NameBS"/>
                                            <xsl:with-param name="title" select="./NameVisible"/>
                                            <xsl:with-param name="description" select="./Description"/>
                                            <xsl:with-param name="hint" select="./Comment"/>
                                            <xsl:with-param name="required" select="./IsRequired"/>
                                            <xsl:with-param name="editable" select="$editable"/>
                                            <xsl:with-param name="visible" select="./IsVisible"/>
                                            <xsl:with-param name="isSum" select="./IsSum"/>
                                            <xsl:with-param name="value" select="$val"/>
                                            <xsl:with-param name="dictTypeFieldName" select="'dictFieldId'"/>
                                            <xsl:with-param name="isRiskField" select="$isRiskField"/>
                                        </xsl:call-template>
                                    </field>
                                </xsl:when>
                                <xsl:when test="./Type = 'number'">
                                    <field>
                                        <xsl:call-template name="numberBillingField">
                                            <xsl:with-param name="name" select="./NameBS"/>
                                            <xsl:with-param name="title" select="./NameVisible"/>
                                            <xsl:with-param name="description" select="./Description"/>
                                            <xsl:with-param name="hint" select="./Comment"/>
                                            <xsl:with-param name="required" select="./IsRequired"/>
                                            <xsl:with-param name="editable" select="$editable"/>
                                            <xsl:with-param name="visible" select="./IsVisible"/>
                                            <xsl:with-param name="isSum" select="./IsSum"/>
                                            <xsl:with-param name="value" select="$val"/>
                                            <xsl:with-param name="dictTypeFieldName" select="'dictFieldId'"/>
                                            <xsl:with-param name="isRiskField" select="$isRiskField"/>
                                        </xsl:call-template>
                                    </field>
                                </xsl:when>
                                <xsl:when test="./Type = 'money'">
                                    <field>
                                        <xsl:call-template name="moneyBillingField">
                                            <xsl:with-param name="name" select="./NameBS"/>
                                            <xsl:with-param name="title" select="./NameVisible"/>
                                            <xsl:with-param name="description" select="./Description"/>
                                            <xsl:with-param name="hint" select="./Comment"/>
                                            <xsl:with-param name="required" select="./IsRequired"/>
                                            <xsl:with-param name="editable" select="$editable"/>
                                            <xsl:with-param name="visible" select="./IsVisible"/>
                                            <xsl:with-param name="isSum" select="./IsSum"/>
                                            <xsl:with-param name="value" select="$val"/>
                                            <xsl:with-param name="isRiskField" select="$isRiskField"/>
                                        </xsl:call-template>
                                    </field>
                                </xsl:when>
                                <xsl:when test="./Type = 'list'">
                                    <field>
                                        <xsl:call-template name="listBillingField">
                                            <xsl:with-param name="name" select="./NameBS"/>
                                            <xsl:with-param name="title" select="./NameVisible"/>
                                            <xsl:with-param name="description" select="./Description"/>
                                            <xsl:with-param name="hint" select="./Comment"/>
                                            <xsl:with-param name="required" select="./IsRequired"/>
                                            <xsl:with-param name="editable" select="$editable"/>
                                            <xsl:with-param name="visible" select="./IsVisible"/>
                                            <xsl:with-param name="isSum" select="./IsSum"/>
                                            <xsl:with-param name="listValues" select="./Menu/MenuItem"/>
                                            <xsl:with-param name="value" select="$val"/>
                                            <xsl:with-param name="isRiskField" select="$isRiskField"/>
                                        </xsl:call-template>
                                    </field>
                                </xsl:when>
                                <xsl:when test="./Type = 'set'">
                                    <field>
                                        <xsl:call-template name="setBillingField">
                                            <xsl:with-param name="name" select="./NameBS"/>
                                            <xsl:with-param name="title" select="./NameVisible"/>
                                            <xsl:with-param name="description" select="./Description"/>
                                            <xsl:with-param name="hint" select="./Comment"/>
                                            <xsl:with-param name="required" select="./IsRequired"/>
                                            <xsl:with-param name="editable" select="$editable"/>
                                            <xsl:with-param name="visible" select="./IsVisible"/>
                                            <xsl:with-param name="isSum" select="./IsSum"/>
                                            <xsl:with-param name="availableValues" select="./Menu/MenuItem"/>
                                            <xsl:with-param name="checkedValues" select="$formData/*[name()=$id]/text()"/>
                                            <xsl:with-param name="isRiskField" select="$isRiskField"/>
                                        </xsl:call-template>
                                    </field>
                                </xsl:when>
                                <xsl:when test="./Type = 'choice'">
                                    <field>
                                        <xsl:call-template name="agreementField">
                                            <xsl:with-param name="name" select="./NameBS"/>
                                            <xsl:with-param name="title" select="'� �������� � ��������� ������� �������'"/>
                                            <xsl:with-param name="required" select="./IsRequired"/>
                                            <xsl:with-param name="editable" select="$editable"/>
                                            <xsl:with-param name="visible" select="./IsVisible"/>
                                            <xsl:with-param name="extendedDescId" select="./ExtendedDescriptionId"/>
                                            <xsl:with-param name="value" select="$val"/>
                                            <xsl:with-param name="isRiskField" select="$isRiskField"/>
                                        </xsl:call-template>
                                    </field>
                                </xsl:when>
                                <xsl:when test="./Type = 'graphicset'">
                                    <xsl:variable name="parameters">
                                        <params>
                                            <xsl:for-each select="./Menu/MenuItem">
                                                <param key="{./Id}"><xsl:value-of select="./Value"/></param>
                                            </xsl:for-each>
                                        </params>
                                    </xsl:variable>
                                    <field>
                                        <xsl:call-template name="placesField">
                                            <xsl:with-param name="name" select="./NameBS"/>
                                            <xsl:with-param name="title" select="./NameVisible"/>
                                            <xsl:with-param name="required" select="false()"/>
                                            <xsl:with-param name="editable" select="$editable"/>
                                            <xsl:with-param name="visible" select="./IsVisible"/>
                                            <xsl:with-param name="params" select="xalan:nodeset($parameters)"/>
                                            <xsl:with-param name="value" select="$val"/>
                                            <xsl:with-param name="isRiskField" select="$isRiskField"/>
                                        </xsl:call-template>
                                    </field>
                                </xsl:when>
                                <xsl:when test="./Type = 'email'">
                                    <field>
                                        <xsl:call-template name="stringBillingField">
                                            <xsl:with-param name="name" select="./NameBS"/>
                                            <xsl:with-param name="title" select="./NameVisible"/>
                                            <xsl:with-param name="description" select="./Description"/>
                                            <xsl:with-param name="hint" select="./Comment"/>
                                            <xsl:with-param name="minLength" select="./MinLength"/>
                                            <xsl:with-param name="maxLength" select="./MaxLength"/>
                                            <xsl:with-param name="required" select="./IsRequired"/>
                                            <xsl:with-param name="editable" select="$editable"/>
                                            <xsl:with-param name="visible" select="./IsVisible"/>
                                            <xsl:with-param name="isSum" select="./IsSum"/>
                                            <xsl:with-param name="value" select="$val"/>
                                            <xsl:with-param name="isRiskField" select="$isRiskField"/>
                                        </xsl:call-template>
                                    </field>
                                </xsl:when>
                            </xsl:choose>
                        </xsl:for-each>
                    </extendedFields>
                </subscriptionDetails>

                <!--������ �������-->
                <!--���� ��������-->
                <fromResource>
                    <xsl:call-template name="resourceField">
                        <xsl:with-param name="name">fromResource</xsl:with-param>
                        <xsl:with-param name="title">������� �� �����</xsl:with-param>
                        <xsl:with-param name="required" select="true()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible"  select="true()"/>
                        <xsl:with-param name="link"     select="fromResourceLink"/>
                        <xsl:with-param name="cards"    select="$activeCards"/>
                    </xsl:call-template>
                </fromResource>

                <xsl:if test="not($isInitialLongOfferState)">
                    <xsl:call-template name="editLongOffer"/>
                </xsl:if>

                <accountingEntityId>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">accountingEntityId</xsl:with-param>
                        <xsl:with-param name="title">������������� ������� �����</xsl:with-param>
                        <xsl:with-param name="required" select="false()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible"  select="false()"/>
                        <xsl:with-param name="value"    select="accountingEntityId"/>
                    </xsl:call-template>
                </accountingEntityId>

                <recipient>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">recipient</xsl:with-param>
                        <xsl:with-param name="title">������������ ���������� �����</xsl:with-param>
                        <xsl:with-param name="required" select="false()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible"  select="false()"/>
                        <xsl:with-param name="value"    select="recipient"/>
                    </xsl:call-template>
                </recipient>
            </CreateInvoiceSubscriptionPaymentDocument>
        </document>
    </xsl:template>

    <xsl:template match="/form-data" mode="view">
        <document>
            <form>CreateInvoiceSubscriptionPayment</form>
            <status><xsl:value-of select="$documentStatus"/></status>
            <xsl:if test="string-length($documentId)>0">
                <id><xsl:value-of select="$documentId"/></id>
            </xsl:if>
            <xsl:if test="string-length($checkAvailable)>0">
                <checkAvailable><xsl:value-of select="$checkAvailable"/></checkAvailable>
            </xsl:if>

            <CreateInvoiceSubscriptionPaymentDocument>
                <xsl:call-template name="viewPayment"/>
                <xsl:choose>
                    <xsl:when test="$mode = 'edit' and $isInitialLongOfferState">
                        <xsl:call-template name="editLongOffer"/>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:call-template name="viewLongOfferPayment"/>
                    </xsl:otherwise>
                </xsl:choose>

                <accountingEntityId>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">accountingEntityId</xsl:with-param>
                        <xsl:with-param name="title">������������� ������� �����</xsl:with-param>
                        <xsl:with-param name="required" select="false()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible"  select="false()"/>
                        <xsl:with-param name="value"    select="accountingEntityId"/>
                    </xsl:call-template>
                </accountingEntityId>

                <recipient>
                    <xsl:call-template name="stringField">
                        <xsl:with-param name="name">recipient</xsl:with-param>
                        <xsl:with-param name="title">������������ ���������� �����</xsl:with-param>
                        <xsl:with-param name="required" select="false()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible"  select="false()"/>
                        <xsl:with-param name="value"    select="recipient"/>
                    </xsl:call-template>
                </recipient>
            </CreateInvoiceSubscriptionPaymentDocument>
        </document>
    </xsl:template>

    <!--�������-->
    <xsl:template name="editLongOffer">
       <xsl:variable name="autoSubName">
           <xsl:choose>
               <xsl:when test="string-length(autoSubName)>0">
                   <xsl:value-of select="autoSubName"/>
               </xsl:when>
               <xsl:otherwise>
                   <xsl:choose>
                       <xsl:when test="20>=string-length(receiverName)">
                           <xsl:value-of select="su:removeNotLetter(nameService)"/>
                       </xsl:when>
                   </xsl:choose>
               </xsl:otherwise>
           </xsl:choose>
       </xsl:variable>

       <autoSubName>
           <xsl:call-template name="stringField">
               <xsl:with-param name="name">autoSubName</xsl:with-param>
               <xsl:with-param name="title">�������� ����������� �������</xsl:with-param>
               <xsl:with-param name="required" select="true()"/>
               <xsl:with-param name="editable" select="true()"/>
               <xsl:with-param name="visible" select="true()"/>
               <xsl:with-param name="value" select="$autoSubName"/>
           </xsl:call-template>
       </autoSubName>

       <xsl:variable name="autoSubEventType">
            <xsl:choose>
               <xsl:when test="string-length(autoSubEventType) > 0">
                   <xsl:value-of select="autoSubEventType"/>
               </xsl:when>
               <xsl:otherwise>
                   <xsl:value-of select="'ONCE_IN_MONTH'"/>
               </xsl:otherwise>
           </xsl:choose>
       </xsl:variable>
       <xsl:variable name="chooseDateInvoice">
           <xsl:choose>
               <xsl:when test="string-length(chooseDateInvoice) > 0">
                   <xsl:value-of select="dh:convertIfXmlDateFormat(chooseDateInvoice)"/>
               </xsl:when>
               <xsl:otherwise>
                   <xsl:value-of select="dh:formatDateToStringWithPoint(dh:getNearDateByMonthWithoutCurrent(10))"/>
               </xsl:otherwise>
           </xsl:choose>
       </xsl:variable>
       <!--������ ������ (ONCE_IN_WEEK (��� � ������), ONCE_IN_MONTH (��� � �����), ONCE_IN_QUARTER (��� � �������))-->
       <autoSubEvent>
           <payType>
               <xsl:call-template name="stringField">
                   <xsl:with-param name="name">autoSubEventType</xsl:with-param>
                   <xsl:with-param name="title">������ ������</xsl:with-param>
                   <xsl:with-param name="required" select="true()"/>
                   <xsl:with-param name="editable" select="true()"/>
                   <xsl:with-param name="visible" select="true()"/>
                   <xsl:with-param name="value" select="$autoSubEventType"/>
               </xsl:call-template>
           </payType>
           <!--������ ������: ��� ONCE_IN_WEEK - ���� ������, ��� ONCE_IN_MONTH ����� ������, ��� ONCE_IN_QUARTER ����� � ����� ��������-->
           <payDate>
               <xsl:call-template name="stringField">
                   <xsl:with-param name="name">chooseDateInvoice</xsl:with-param>
                   <xsl:with-param name="title">���� ������</xsl:with-param>
                   <xsl:with-param name="required" select="true()"/>
                   <xsl:with-param name="editable" select="true()"/>
                   <xsl:with-param name="visible" select="true()"/>
                   <xsl:with-param name="value" select="$chooseDateInvoice"/>
               </xsl:call-template>
           </payDate>
       </autoSubEvent>
    </xsl:template>

    <xsl:template name="viewPayment">
        <xsl:variable name="activeCards" select="document('stored-active-not-virtual-cards-all-visible.xml')/entity-list/*"/>
        <xsl:variable name="bankDetails" select="bankDetails"/>
        <!--����������-->
        <receiver>
            <!--������������ ����������-->
            <name>
                <xsl:call-template name="stringField">
                    <xsl:with-param name="name">receiverName</xsl:with-param>
                    <xsl:with-param name="title">������������</xsl:with-param>
                    <xsl:with-param name="required" select="false()"/>
                    <xsl:with-param name="editable" select="false()"/>
                    <xsl:with-param name="visible" select="true()"/>
                    <xsl:with-param name="value" select="receiverName"/>
                </xsl:call-template>
            </name>
            <!--�������� ����������-->
            <xsl:if test="string-length(receiverDescription)>0">
               <description>
                   <xsl:call-template name="stringField">
                       <xsl:with-param name="name">receiverDescription</xsl:with-param>
                       <xsl:with-param name="title">���������� �� ���������� �������</xsl:with-param>
                       <xsl:with-param name="required" select="false()"/>
                       <xsl:with-param name="editable" select="false()"/>
                       <xsl:with-param name="visible" select="true()"/>
                       <xsl:with-param name="value" select="receiverDescription"/>
                   </xsl:call-template>
               </description>
            </xsl:if>
            <!--������ ������-->
            <region>
                <xsl:variable name="payRegion">
                    <xsl:call-template name="regionTemplate">
                        <xsl:with-param name="providerId" select="recipient"/>
                        <xsl:with-param name="personRegionName" select="pu:getPersonRegionName()"/>
                    </xsl:call-template>
                </xsl:variable>
                <xsl:call-template name="stringField">
                   <xsl:with-param name="name">region</xsl:with-param>
                   <xsl:with-param name="title">������ ������</xsl:with-param>
                   <xsl:with-param name="required" select="false()"/>
                   <xsl:with-param name="editable" select="false()"/>
                   <xsl:with-param name="visible" select="true()"/>
                   <xsl:with-param name="value" select="$payRegion"/>
                </xsl:call-template>
            </region>

            <xsl:if test="$bankDetails = 'true'">
                <!--��� ����������-->
                <inn>
                    <xsl:call-template name="integerField">
                        <xsl:with-param name="name">receiverINN</xsl:with-param>
                        <xsl:with-param name="title">���</xsl:with-param>
                        <xsl:with-param name="required" select="false()"/>
                        <xsl:with-param name="editable" select="false()"/>
                        <xsl:with-param name="visible" select="true()"/>
                        <xsl:with-param name="value" select="receiverINN"/>
                    </xsl:call-template>
                </inn>
                <!--���� ����������-->
                <account>
                   <xsl:call-template name="integerField">
                       <xsl:with-param name="name">receiverAccount</xsl:with-param>
                       <xsl:with-param name="title">����</xsl:with-param>
                       <xsl:with-param name="required" select="false()"/>
                       <xsl:with-param name="editable" select="false()"/>
                       <xsl:with-param name="visible" select="true()"/>
                       <xsl:with-param name="value" select="receiverAccount"/>
                   </xsl:call-template>
                </account>
                <!--���� ����������-->
                <bank>
                    <!--������������-->
                    <name>
                        <xsl:call-template name="stringField">
                            <xsl:with-param name="name">receiverBankName</xsl:with-param>
                            <xsl:with-param name="title">������������</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="receiverBankName"/>
                        </xsl:call-template>
                    </name>
                    <!--��� �����-->
                    <bic>
                        <xsl:call-template name="integerField">
                            <xsl:with-param name="name">receiverBIC</xsl:with-param>
                            <xsl:with-param name="title">���</xsl:with-param>
                            <xsl:with-param name="required" select="false()"/>
                            <xsl:with-param name="editable" select="false()"/>
                            <xsl:with-param name="visible" select="true()"/>
                            <xsl:with-param name="value" select="receiverBIC"/>
                        </xsl:call-template>
                    </bic>

                    <!--������� �����-->
                    <xsl:if test="string-length(receiverCorAccount)>0">
                        <corAccount>
                            <xsl:call-template name="integerField">
                                <xsl:with-param name="name">receiverCorAccount</xsl:with-param>
                                <xsl:with-param name="title">�������</xsl:with-param>
                                <xsl:with-param name="required" select="false()"/>
                                <xsl:with-param name="editable" select="false()"/>
                                <xsl:with-param name="visible" select="true()"/>
                                <xsl:with-param name="value" select="receiverCorAccount"/>
                            </xsl:call-template>
                        </corAccount>
                    </xsl:if>
                </bank>
            </xsl:if>
        </receiver>
        <!--������-->
        <xsl:if test="string-length(nameService)>0">
            <serviceName>
                <xsl:call-template name="stringField">
                    <xsl:with-param name="name">nameService</xsl:with-param>
                    <xsl:with-param name="title">������</xsl:with-param>
                    <xsl:with-param name="required" select="false()"/>
                    <xsl:with-param name="editable" select="false()"/>
                    <xsl:with-param name="visible" select="true()"/>
                    <xsl:with-param name="value" select="nameService"/>
                </xsl:call-template>
            </serviceName>
        </xsl:if>
        <!-- ������ ��������� -->
        <subscriptionDetails>
            <extendedFields>
                <xsl:for-each select="$extendedFields">
                    <xsl:variable name="id" select="./NameBS"/>
                    <xsl:variable name="val">
                        <xsl:variable name="currentValue" select="$formData/*[name()=$id]"/>
                        <xsl:choose>
                            <!--���� ������ �������� ����, ����� ���-->
                            <xsl:when test="string-length($currentValue) > 0">
                                <xsl:value-of select="$currentValue"/>
                            </xsl:when>
                            <!--����� - ���������������� ��������-->
                            <xsl:otherwise>
                                <xsl:value-of select="./DefaultValue"/>
                            </xsl:otherwise>
                        </xsl:choose>
                    </xsl:variable>

                    <!--��� ����������� ����� �������� ������� "IsRiskRequisite"-->
                    <xsl:variable name="requisiteTypes" select="./RequisiteTypes"/>
                    <xsl:variable name="isRiskField" select="string-length($requisiteTypes) > 0 and contains($requisiteTypes, 'IsRiskRequisite')"/>
                    <xsl:variable name="editable" select="./IsEditable='true' and (not($isMobileLightScheme) or (not($isMobileLimitedScheme) and (./IsKey='false')))"/>

                    <xsl:choose>
                        <xsl:when test="./Type = 'date'">
                            <field>
                                <xsl:call-template name="dateBillingField">
                                    <xsl:with-param name="name" select="./NameBS"/>
                                    <xsl:with-param name="title" select="./NameVisible"/>
                                    <xsl:with-param name="description" select="./Description"/>
                                    <xsl:with-param name="hint" select="./Comment"/>
                                    <xsl:with-param name="required" select="./IsRequired"/>
                                    <xsl:with-param name="editable" select="$editable"/>
                                    <xsl:with-param name="visible" select="./IsVisible"/>
                                    <xsl:with-param name="value" select="$val"/>
                                    <xsl:with-param name="isRiskField" select="$isRiskField"/>
                                </xsl:call-template>
                            </field>
                        </xsl:when>
                        <xsl:when test="./Type = 'calendar'">
                            <field>
                                <xsl:call-template name="calendarBillingField">
                                    <xsl:with-param name="name" select="./NameBS"/>
                                    <xsl:with-param name="title" select="./NameVisible"/>
                                    <xsl:with-param name="description" select="./Description"/>
                                    <xsl:with-param name="hint" select="./Comment"/>
                                    <xsl:with-param name="required" select="./IsRequired"/>
                                    <xsl:with-param name="editable" select="$editable"/>
                                    <xsl:with-param name="visible" select="./IsVisible"/>
                                    <xsl:with-param name="value" select="$val"/>
                                    <xsl:with-param name="isRiskField" select="$isRiskField"/>
                                </xsl:call-template>
                            </field>
                        </xsl:when>
                        <xsl:when test="./Type = 'string'">
                            <field>
                                <xsl:call-template name="stringBillingField">
                                    <xsl:with-param name="name" select="./NameBS"/>
                                    <xsl:with-param name="title" select="./NameVisible"/>
                                    <xsl:with-param name="description" select="./Description"/>
                                    <xsl:with-param name="hint" select="./Comment"/>
                                    <xsl:with-param name="minLength" select="./MinLength"/>
                                    <xsl:with-param name="maxLength" select="./MaxLength"/>
                                    <xsl:with-param name="required" select="./IsRequired"/>
                                    <xsl:with-param name="editable" select="$editable"/>
                                    <xsl:with-param name="visible" select="./IsVisible"/>
                                    <xsl:with-param name="isSum" select="./IsSum"/>
                                    <xsl:with-param name="value" select="$val"/>
                                    <xsl:with-param name="dictTypeFieldName" select="'dictFieldId'"/>
                                    <xsl:with-param name="isRiskField" select="$isRiskField"/>
                                </xsl:call-template>
                            </field>
                        </xsl:when>
                        <xsl:when test="./Type = 'integer'">
                            <field>
                                <xsl:call-template name="integerBillingField">
                                    <xsl:with-param name="name" select="./NameBS"/>
                                    <xsl:with-param name="title" select="./NameVisible"/>
                                    <xsl:with-param name="description" select="./Description"/>
                                    <xsl:with-param name="hint" select="./Comment"/>
                                    <xsl:with-param name="required" select="./IsRequired"/>
                                    <xsl:with-param name="editable" select="$editable"/>
                                    <xsl:with-param name="visible" select="./IsVisible"/>
                                    <xsl:with-param name="isSum" select="./IsSum"/>
                                    <xsl:with-param name="value" select="$val"/>
                                    <xsl:with-param name="dictTypeFieldName" select="'dictFieldId'"/>
                                    <xsl:with-param name="isRiskField" select="$isRiskField"/>
                                </xsl:call-template>
                            </field>
                        </xsl:when>
                        <xsl:when test="./Type = 'number'">
                            <field>
                                <xsl:call-template name="numberBillingField">
                                    <xsl:with-param name="name" select="./NameBS"/>
                                    <xsl:with-param name="title" select="./NameVisible"/>
                                    <xsl:with-param name="description" select="./Description"/>
                                    <xsl:with-param name="hint" select="./Comment"/>
                                    <xsl:with-param name="required" select="./IsRequired"/>
                                    <xsl:with-param name="editable" select="$editable"/>
                                    <xsl:with-param name="visible" select="./IsVisible"/>
                                    <xsl:with-param name="isSum" select="./IsSum"/>
                                    <xsl:with-param name="value" select="$val"/>
                                    <xsl:with-param name="dictTypeFieldName" select="'dictFieldId'"/>
                                    <xsl:with-param name="isRiskField" select="$isRiskField"/>
                                </xsl:call-template>
                            </field>
                        </xsl:when>
                        <xsl:when test="./Type = 'money'">
                            <field>
                                <xsl:call-template name="moneyBillingField">
                                    <xsl:with-param name="name" select="./NameBS"/>
                                    <xsl:with-param name="title" select="./NameVisible"/>
                                    <xsl:with-param name="description" select="./Description"/>
                                    <xsl:with-param name="hint" select="./Comment"/>
                                    <xsl:with-param name="required" select="./IsRequired"/>
                                    <xsl:with-param name="editable" select="$editable"/>
                                    <xsl:with-param name="visible" select="./IsVisible"/>
                                    <xsl:with-param name="isSum" select="./IsSum"/>
                                    <xsl:with-param name="value" select="$val"/>
                                    <xsl:with-param name="isRiskField" select="$isRiskField"/>
                                </xsl:call-template>
                            </field>
                        </xsl:when>
                        <xsl:when test="./Type = 'list'">
                            <field>
                                <xsl:call-template name="listBillingField">
                                    <xsl:with-param name="name" select="./NameBS"/>
                                    <xsl:with-param name="title" select="./NameVisible"/>
                                    <xsl:with-param name="description" select="./Description"/>
                                    <xsl:with-param name="hint" select="./Comment"/>
                                    <xsl:with-param name="required" select="./IsRequired"/>
                                    <xsl:with-param name="editable" select="$editable"/>
                                    <xsl:with-param name="visible" select="./IsVisible"/>
                                    <xsl:with-param name="isSum" select="./IsSum"/>
                                    <xsl:with-param name="listValues" select="./Menu/MenuItem"/>
                                    <xsl:with-param name="value" select="$val"/>
                                    <xsl:with-param name="isRiskField" select="$isRiskField"/>
                                </xsl:call-template>
                            </field>
                        </xsl:when>
                        <xsl:when test="./Type = 'set'">
                            <field>
                                <xsl:call-template name="setBillingField">
                                    <xsl:with-param name="name" select="./NameBS"/>
                                    <xsl:with-param name="title" select="./NameVisible"/>
                                    <xsl:with-param name="description" select="./Description"/>
                                    <xsl:with-param name="hint" select="./Comment"/>
                                    <xsl:with-param name="required" select="./IsRequired"/>
                                    <xsl:with-param name="editable" select="$editable"/>
                                    <xsl:with-param name="visible" select="./IsVisible"/>
                                    <xsl:with-param name="isSum" select="./IsSum"/>
                                    <xsl:with-param name="availableValues" select="./Menu/MenuItem"/>
                                    <xsl:with-param name="checkedValues" select="$formData/*[name()=$id]/text()"/>
                                    <xsl:with-param name="isRiskField" select="$isRiskField"/>
                                </xsl:call-template>
                            </field>
                        </xsl:when>
                        <xsl:when test="./Type = 'choice'">
                            <field>
                                <xsl:call-template name="agreementField">
                                    <xsl:with-param name="name" select="./NameBS"/>
                                    <xsl:with-param name="title" select="'� �������� � ��������� ������� �������'"/>
                                    <xsl:with-param name="required" select="./IsRequired"/>
                                    <xsl:with-param name="editable" select="$editable"/>
                                    <xsl:with-param name="visible" select="./IsVisible"/>
                                    <xsl:with-param name="extendedDescId" select="./ExtendedDescriptionId"/>
                                    <xsl:with-param name="value" select="$val"/>
                                    <xsl:with-param name="isRiskField" select="$isRiskField"/>
                                </xsl:call-template>
                            </field>
                        </xsl:when>
                        <xsl:when test="./Type = 'graphicset'">
                            <xsl:variable name="parameters">
                                <params>
                                    <xsl:for-each select="./Menu/MenuItem">
                                        <param key="{./Id}"><xsl:value-of select="./Value"/></param>
                                    </xsl:for-each>
                                </params>
                            </xsl:variable>
                            <field>
                                <xsl:call-template name="placesField">
                                    <xsl:with-param name="name" select="./NameBS"/>
                                    <xsl:with-param name="title" select="./NameVisible"/>
                                    <xsl:with-param name="required" select="false()"/>
                                    <xsl:with-param name="editable" select="$editable"/>
                                    <xsl:with-param name="visible" select="./IsVisible"/>
                                    <xsl:with-param name="params" select="xalan:nodeset($parameters)"/>
                                    <xsl:with-param name="value" select="$val"/>
                                    <xsl:with-param name="isRiskField" select="$isRiskField"/>
                                </xsl:call-template>
                            </field>
                        </xsl:when>
                        <xsl:when test="./Type = 'email'">
                            <field>
                                <xsl:call-template name="stringBillingField">
                                    <xsl:with-param name="name" select="./NameBS"/>
                                    <xsl:with-param name="title" select="./NameVisible"/>
                                    <xsl:with-param name="description" select="./Description"/>
                                    <xsl:with-param name="hint" select="./Comment"/>
                                    <xsl:with-param name="minLength" select="./MinLength"/>
                                    <xsl:with-param name="maxLength" select="./MaxLength"/>
                                    <xsl:with-param name="required" select="./IsRequired"/>
                                    <xsl:with-param name="editable" select="$editable"/>
                                    <xsl:with-param name="visible" select="./IsVisible"/>
                                    <xsl:with-param name="isSum" select="./IsSum"/>
                                    <xsl:with-param name="value" select="$val"/>
                                    <xsl:with-param name="isRiskField" select="$isRiskField"/>
                                </xsl:call-template>
                            </field>
                        </xsl:when>
                    </xsl:choose>
                </xsl:for-each>
            </extendedFields>
        </subscriptionDetails>

        <!--���� ��������-->
        <fromResource>
            <xsl:call-template name="resourceField">
                <xsl:with-param name="name">fromResource</xsl:with-param>
                <xsl:with-param name="title">���� ��������</xsl:with-param>
                <xsl:with-param name="required" select="true()"/>
                <xsl:with-param name="editable" select="false()"/>
                <xsl:with-param name="visible"  select="true()"/>
                <xsl:with-param name="link"     select="fromResourceLink"/>
                <xsl:with-param name="cards"    select="$activeCards"/>
                <xsl:with-param name="isView"   select="true()"/>
            </xsl:call-template>
        </fromResource>
    </xsl:template>

    <xsl:template name="viewLongOfferPayment">
        <xsl:variable name="autoSubName">
            <xsl:choose>
                <xsl:when test="string-length(autoSubName)>0">
                    <xsl:value-of select="autoSubName"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:choose>
                        <xsl:when test="20>=string-length(receiverName)">
                            <xsl:value-of select="su:removeNotLetter(nameService)"/>
                        </xsl:when>
                    </xsl:choose>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>
        <xsl:if test="string-length($autoSubName)>0">
            <autoSubName>
                <xsl:call-template name="stringField">
                    <xsl:with-param name="name">nameService</xsl:with-param>
                    <xsl:with-param name="title">�������� ������</xsl:with-param>
                    <xsl:with-param name="required" select="false()"/>
                    <xsl:with-param name="editable" select="false()"/>
                    <xsl:with-param name="visible" select="true()"/>
                    <xsl:with-param name="value" select="$autoSubName"/>
                </xsl:call-template>
            </autoSubName>
        </xsl:if>

        <xsl:variable name="autoSubEventType">
             <xsl:choose>
                <xsl:when test="string-length(autoSubEventType) > 0">
                    <xsl:value-of select="autoSubEventType"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="'ONCE_IN_MONTH'"/>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>
        <xsl:variable name="chooseDateInvoice">
            <xsl:choose>
                <xsl:when test="string-length(chooseDateInvoice) > 0">
                    <xsl:value-of select="dh:convertIfXmlDateFormat(chooseDateInvoice)"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="dh:formatDateToStringWithPoint(dh:getNearDateByMonthWithoutCurrent(10))"/>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>
        <!--������ ������ (ONCE_IN_WEEK (��� � ������), ONCE_IN_MONTH (��� � �����), ONCE_IN_QUARTER (��� � �������))-->
        <autoSubEvent>
            <payType>
                <xsl:call-template name="stringField">
                    <xsl:with-param name="name">autoSubEventType</xsl:with-param>
                    <xsl:with-param name="title">������ ������</xsl:with-param>
                    <xsl:with-param name="required" select="false()"/>
                    <xsl:with-param name="editable" select="false()"/>
                    <xsl:with-param name="visible" select="true()"/>
                    <xsl:with-param name="value" select="$autoSubEventType"/>
                </xsl:call-template>
            </payType>
            <!--������ ������: ��� ONCE_IN_WEEK - ���� ������, ��� ONCE_IN_MONTH ����� ������, ��� ONCE_IN_QUARTER ����� � ����� ��������-->
            <payDate>
                <xsl:call-template name="stringField">
                    <xsl:with-param name="name">chooseDateInvoice</xsl:with-param>
                    <xsl:with-param name="title">���� ������</xsl:with-param>
                    <xsl:with-param name="required" select="false()"/>
                    <xsl:with-param name="editable" select="false()"/>
                    <xsl:with-param name="visible" select="true()"/>
                    <xsl:with-param name="value" select="$chooseDateInvoice"/>
                </xsl:call-template>
            </payDate>
        </autoSubEvent>
    </xsl:template>

    <xsl:template name="tokenizeString">
        <xsl:param name="string"/>

        <xsl:if test="string-length($string) > 0">
            <xsl:variable name="before-separator" select="substring-before($string, '&#10;')"/>
            <xsl:variable name="after-separator" select="substring-after($string, '&#10;')"/>

            <xsl:choose>
                <xsl:when test="string-length($before-separator)=0 and string-length($after-separator)=0">
                    <xsl:value-of select="$string"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:if test="string-length($before-separator)!=0">
                        <xsl:value-of select="$before-separator"/><br/>
                    </xsl:if>
                    <xsl:call-template name="tokenizeString">
                        <xsl:with-param name="string" select="$after-separator"/>
                    </xsl:call-template>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:if>
    </xsl:template>

    <xsl:template name="regionTemplate">
        <xsl:param name="providerId"/>
        <xsl:param name="personRegionName"/>

        <xsl:variable name="regionsNode" select="spu:getProviderRegions($providerId)"/>
        <xsl:variable name="notError" select="spu:allowedAnyRegions($providerId)"/>
        <xsl:variable name="isAllRegions" select="not($regionsNode/entity-list/*)"/>
        <xsl:variable name="regions">
            <xsl:for-each select="$regionsNode/entity-list/*"><xsl:value-of select="./field[@name = 'name']/text()"/>|</xsl:for-each>
        </xsl:variable>

        <xsl:choose>
            <xsl:when test="$isAllRegions">
                ��������� �������� �� ���� ��������.
            </xsl:when>
            <xsl:otherwise>
                <xsl:choose>
                    <xsl:when test="$notError and $personRegionName!=''">
                        <xsl:choose>
                        <xsl:when test="string-length($regions) > 0 and substring-before($regions, '|')!='' and substring-after($regions, '|')!=''">
                            <xsl:value-of select="$personRegionName"/>,..
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="$personRegionName"/>
                        </xsl:otherwise>
                        </xsl:choose>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:choose>
                        <xsl:when test="string-length($regions) > 0 and substring-before($regions, '|')!='' and substring-after($regions, '|')!=''">
                            <xsl:value-of select="substring-before($regions, '|')"/>,...
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="substring-before($regions, '|')"/>
                        </xsl:otherwise>
                        </xsl:choose>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="resourceField">
        <xsl:param name="name"/>
        <xsl:param name="title"/>
        <xsl:param name="description" select="''"/>
        <xsl:param name="hint" select="''"/>
        <xsl:param name="required"/>
        <xsl:param name="editable"/>
        <xsl:param name="visible"/>
        <xsl:param name="cards"/>
        <xsl:param name="isView" select="false()"/> <!--false=��������������; true=��������-->
        <xsl:param name="link"   select="''"/>      <!--������. ��������� link.getCode() (������ ���� "card:1234"), �� ����� ����� ��������� link.toString() (������ ���� "����� � ..."). ��� ��������������-->
        <xsl:param name="linkCode"/>                <!--��� ����� ��� ����� (card:1234). ��� ��������� � ��������������-->

        <xsl:variable name="isValidLinkCode" select="string-length($link)>0 and starts-with($link, 'card:')"/>

        <xsl:call-template name="simpleField">
            <xsl:with-param name="name"        select="$name"/>
            <xsl:with-param name="title"       select="$title"/>
            <xsl:with-param name="description" select="$description"/>
            <xsl:with-param name="hint"        select="$hint"/>
            <xsl:with-param name="type"        select="'resource'"/>
            <xsl:with-param name="required"    select="$required"/>
            <xsl:with-param name="editable"    select="$editable"/>
            <xsl:with-param name="visible"     select="$visible"/>
            <xsl:with-param name="valueTag">
                <resourceType>
                    <xsl:if test="string-length($cards)>0">
                    <availableValues>
                        <xsl:for-each select="$cards">
                            <xsl:variable name="code" select="field[@name='code']/text()"/>
                            <xsl:variable name="selected" select="($isValidLinkCode and $code=$link) or (not($isValidLinkCode) and $code=$linkCode)"/>
                            <xsl:if test="not($isView) or $selected">
                                <valueItem>
                                    <value><xsl:value-of select="$code"/></value>
                                    <selected><xsl:value-of select="string($selected)"/></selected>
                                    <displayedValue>
                                        <xsl:value-of select="mask:getCutCardNumber(./@key)"/> [<xsl:value-of select="./field[@name='name']"/>]
                                    </displayedValue>
                                </valueItem>
                            </xsl:if>
                        </xsl:for-each>
                    </availableValues>
                    </xsl:if>
                </resourceType>
            </xsl:with-param>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="agreementField">
        <xsl:param name="name"/>
        <xsl:param name="title" select="''"/>
        <xsl:param name="description" select="''"/>
        <xsl:param name="hint" select="''"/>
        <xsl:param name="required" select="false()"/>
        <xsl:param name="editable" select="false()"/>
        <xsl:param name="visible" select="true()"/>
        <xsl:param name="extendedDescId"/>
        <xsl:param name="value"/>
        <xsl:param name="isRiskField" select="false()"/>

        <xsl:call-template name="simpleField">
            <xsl:with-param name="name" select="$name"/>
            <xsl:with-param name="title" select="$title"/>
            <xsl:with-param name="description" select="$description"/>
            <xsl:with-param name="hint" select="$hint"/>
            <xsl:with-param name="type" select="'agreement'"/>
            <xsl:with-param name="required" select="$required"/>
            <xsl:with-param name="editable" select="$editable"/>
            <xsl:with-param name="visible" select="$visible"/>
            <xsl:with-param name="valueTag">
                <agreementType>
                    <xsl:if test="string-length($value)>0">
                        <value><xsl:value-of select="$value"/></value>
                    </xsl:if>
                    <xsl:if test="string-length($extendedDescId)>0">
                        <agreementId><xsl:value-of select="$extendedDescId"/></agreementId>
                    </xsl:if>
                </agreementType>
            </xsl:with-param>
            <xsl:with-param name="isRiskField" select="$isRiskField"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="placesField">
        <xsl:param name="name"/>
        <xsl:param name="title" select="''"/>
        <xsl:param name="description" select="''"/>
        <xsl:param name="hint" select="''"/>
        <xsl:param name="required" select="false()"/>
        <xsl:param name="editable" select="false()"/>
        <xsl:param name="visible" select="true()"/>
        <xsl:param name="params"/>
        <xsl:param name="value"/>
        <xsl:param name="isRiskField" select="false()"/>

        <xsl:call-template name="simpleField">
            <xsl:with-param name="name" select="$name"/>
            <xsl:with-param name="title" select="$title"/>
            <xsl:with-param name="description" select="$description"/>
            <xsl:with-param name="hint" select="$hint"/>
            <xsl:with-param name="type" select="'places'"/>
            <xsl:with-param name="required" select="$required"/>
            <xsl:with-param name="editable" select="$editable"/>
            <xsl:with-param name="visible" select="$visible"/>
            <xsl:with-param name="valueTag">
                <placesType>
                    <maxCount><xsl:value-of select="$params/params/param[@key = 'maxCount']/text()"/></maxCount>
                    <places>
                        <xsl:for-each select="$params/params/param[@key = 'place']">
                            <xsl:sort select="substring-before(./text(), ';')" data-type="number"/>
                            <place>
                                <number><xsl:value-of select="substring-before(./text(), ';')"/></number>
                                <state><xsl:value-of select="substring-after(./text(), ';')"/></state>
                            </place>
                        </xsl:for-each>
                    </places>
                    <xsl:if test="string-length($value)>0">
                        <value><xsl:value-of select="$value"/></value>
                    </xsl:if>
                </placesType>
            </xsl:with-param>
            <xsl:with-param name="isRiskField" select="$isRiskField"/>
        </xsl:call-template>
    </xsl:template>
</xsl:stylesheet>