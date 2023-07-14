<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/settings/cards/delivery/asvypiska" onsubmit="return setEmptyAction(event);">
    <tiles:importAttribute/>
    <c:set var="globalImagePath" value="${globalUrl}/commonSkin/images/"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="propertiesForm">
        <tiles:put name="tilesDefinition" type="string" value="configEdit"/>
        <tiles:put name="submenu" type="string" value="ASVypiska"/>
        <tiles:put name="pageTitle"><bean:message bundle="configureBundle" key="settings.cards.delivery.asvypiska.title"/></tiles:put>
        <tiles:put name="pageDescription"><bean:message bundle="configureBundle" key="settings.cards.delivery.asvypiska.description.title"/></tiles:put>

        <tiles:put name="data" type="string">
            <table>
                <tiles:insert definition="propertyField" flush="false">
                    <tiles:put name="fieldName" value="com.rssl.iccs.cardreports.asvypiska.active"/>
                    <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="settings.cards.delivery.asvypiska"/></tiles:put>
                    <tiles:put name="fieldHint"><bean:message bundle="configureBundle" key="settings.cards.delivery.asvypiska.switcher"/></tiles:put>
                    <tiles:put name="imagePath" value="${imagePath}"/>
                    <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
                    <tiles:put name="fieldType" value="switcher"/>
                    <tiles:put name="onclick">function(show){showAll(show);}</tiles:put>
                </tiles:insert>

                <tiles:insert definition="propertyField" flush="false">
                    <tiles:put name="fieldName" value="com.rssl.iccs.cardreports.asvypiska.VISA"/>
                    <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="settings.cards.delivery.asvypiska.VISA"/></tiles:put>
                    <tiles:put name="imagePath" value="${imagePath}"/>
                    <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
                    <tiles:put name="fieldType" value="switcher"/>
                    <tiles:put name="showHint" value="none"/>
                    <tiles:put name="trStyle" value="asvypiskacard"/>
                    <tiles:put name="onclick">function(showit){show(showit, "visasettings");}</tiles:put>
                </tiles:insert>

                <tr id="visasettings">
                    <td class="vertical-align rightAlign paymentLabelTable">
                    </td>
                    <td>
                        <table class="tableForSettings">
                            <tr>
                                <td><img src="${globalImagePath}p2p_visaExternalCard.png"/></td>
                                <td>RUS</td>
                                <td>ENG</td>
                            </tr>
                            <tr>
                                <td>PDF</td>
                                <td>
                                    <tiles:insert definition="propertyInput" flush="false">
                                        <tiles:put name="fieldName" value="com.rssl.iccs.cardreports.asvypiska.VISA.PDF_RUS"/>
                                        <tiles:put name="fieldType" value="switcher"/>
                                    </tiles:insert>
                                </td>
                                <td>
                                    <tiles:insert definition="propertyInput" flush="false">
                                        <tiles:put name="fieldName" value="com.rssl.iccs.cardreports.asvypiska.VISA.PDF_ENG"/>
                                        <tiles:put name="fieldType" value="switcher"/>
                                    </tiles:insert>
                                </td>
                            </tr>
                            <tr>
                                <td>HTML</td>
                                <td>
                                    <tiles:insert definition="propertyInput" flush="false">
                                        <tiles:put name="fieldName" value="com.rssl.iccs.cardreports.asvypiska.VISA.HTML_RUS"/>
                                        <tiles:put name="fieldType" value="switcher"/>
                                    </tiles:insert>
                                </td>
                                <td>
                                    <tiles:insert definition="propertyInput" flush="false">
                                        <tiles:put name="fieldName" value="com.rssl.iccs.cardreports.asvypiska.VISA.HTML_ENG"/>
                                        <tiles:put name="fieldType" value="switcher"/>
                                    </tiles:insert>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>

                <tiles:insert definition="propertyField" flush="false">
                    <tiles:put name="fieldName" value="com.rssl.iccs.cardreports.asvypiska.MasterCard"/>
                    <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="settings.cards.delivery.asvypiska.MasterCard"/></tiles:put>
                    <tiles:put name="imagePath" value="${imagePath}"/>
                    <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
                    <tiles:put name="fieldType" value="switcher"/>
                    <tiles:put name="showHint" value="none"/>
                    <tiles:put name="trStyle" value="asvypiskacard"/>
                    <tiles:put name="onclick">function(showit){show(showit, "mastercardsettings");}</tiles:put>
                </tiles:insert>

                <tr id="mastercardsettings">
                    <td class="vertical-align rightAlign paymentLabelTable">
                    </td>
                    <td>
                        <table class="tableForSettings">
                            <tr>
                                <td><img src="${globalImagePath}p2p_masterCardExternalCard.png"/></td>
                                <td>RUS</td>
                                <td>ENG</td>
                            </tr>
                            <tr>
                                <td>PDF</td>
                                <td>
                                    <tiles:insert definition="propertyInput" flush="false">
                                        <tiles:put name="fieldName" value="com.rssl.iccs.cardreports.asvypiska.MasterCard.PDF_RUS"/>
                                        <tiles:put name="fieldType" value="switcher"/>
                                    </tiles:insert>
                                </td>
                                <td>
                                    <tiles:insert definition="propertyInput" flush="false">
                                        <tiles:put name="fieldName" value="com.rssl.iccs.cardreports.asvypiska.MasterCard.PDF_ENG"/>
                                        <tiles:put name="fieldType" value="switcher"/>
                                    </tiles:insert>
                                </td>
                            </tr>
                            <tr>
                                <td>HTML</td>
                                <td>
                                    <tiles:insert definition="propertyInput" flush="false">
                                        <tiles:put name="fieldName" value="com.rssl.iccs.cardreports.asvypiska.MasterCard.HTML_RUS"/>
                                        <tiles:put name="fieldType" value="switcher"/>
                                    </tiles:insert>
                                </td>
                                <td>
                                    <tiles:insert definition="propertyInput" flush="false">
                                        <tiles:put name="fieldName" value="com.rssl.iccs.cardreports.asvypiska.MasterCard.HTML_ENG"/>
                                        <tiles:put name="fieldType" value="switcher"/>
                                    </tiles:insert>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>

                <tiles:insert definition="propertyField" flush="false">
                    <tiles:put name="fieldName" value="com.rssl.iccs.cardreports.asvypiska.AMEX"/>
                    <tiles:put name="fieldDescription"><bean:message bundle="configureBundle" key="settings.cards.delivery.asvypiska.AMEX"/></tiles:put>
                    <tiles:put name="imagePath" value="${imagePath}"/>
                    <tiles:put name="tdStyle" value="vertical-align rightAlign paymentLabelTable"/>
                    <tiles:put name="fieldType" value="switcher"/>
                    <tiles:put name="showHint" value="none"/>
                    <tiles:put name="trStyle" value="asvypiskacard"/>
                    <tiles:put name="onclick">function(showit){show(showit, "amexsettings");}</tiles:put>
                </tiles:insert>

                <tr id="amexsettings">
                    <td class="vertical-align rightAlign paymentLabelTable">
                    </td>
                    <td>
                        <table class="tableForSettings">
                            <tr>
                                <td></td>
                                <td>RUS</td>
                                <td>ENG</td>
                            </tr>
                            <tr>
                                <td>PDF</td>
                                <td>
                                    <tiles:insert definition="propertyInput" flush="false">
                                        <tiles:put name="fieldName" value="com.rssl.iccs.cardreports.asvypiska.AMEX.PDF_RUS"/>
                                        <tiles:put name="fieldType" value="switcher"/>
                                    </tiles:insert>
                                </td>
                                <td>
                                    <tiles:insert definition="propertyInput" flush="false">
                                        <tiles:put name="fieldName" value="com.rssl.iccs.cardreports.asvypiska.AMEX.PDF_ENG"/>
                                        <tiles:put name="fieldType" value="switcher"/>
                                    </tiles:insert>
                                </td>
                            </tr>
                            <tr>
                                <td>HTML</td>
                                <td>
                                    <tiles:insert definition="propertyInput" flush="false">
                                        <tiles:put name="fieldName" value="com.rssl.iccs.cardreports.asvypiska.AMEX.HTML_RUS"/>
                                        <tiles:put name="fieldType" value="switcher"/>
                                    </tiles:insert>
                                </td>
                                <td>
                                    <tiles:insert definition="propertyInput" flush="false">
                                        <tiles:put name="fieldName" value="com.rssl.iccs.cardreports.asvypiska.AMEX.HTML_ENG"/>
                                        <tiles:put name="fieldType" value="switcher"/>
                                    </tiles:insert>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>

            <script language="JavaScript">
                function showAll(showit)
                {
                    if (showit)
                        $("[class=asvypiskacard]").show();
                    else
                        $("[class=asvypiskacard]").hide();
                    show(showit && $("[name=field(com.rssl.iccs.cardreports.asvypiska.VISA)]")[0].checked, "visasettings");
                    show(showit && $("[name=field(com.rssl.iccs.cardreports.asvypiska.MasterCard)]")[0].checked, "mastercardsettings");
                    show(showit && $("[name=field(com.rssl.iccs.cardreports.asvypiska.AMEX)]")[0].checked, "amexsettings");
                }

                function show(showit, id)
                {
                    if (showit)
                        $("#" + id).show();
                    else
                        $("#" + id).hide();
                }

                doOnLoad(function(){
                    showAll($("[name=field(com.rssl.iccs.cardreports.asvypiska.active)]")[0].checked);
                });
            </script>
            <tiles:put name="formButtons">
                <tiles:insert definition="commandButton" flush="false"
                              operation="EditASVypiskaActivityOperation">
                    <tiles:put name="commandKey" value="button.save"/>
                    <tiles:put name="commandHelpKey" value="button.save.help"/>
                    <tiles:put name="bundle" value="commonBundle"/>
                    <tiles:put name="isDefault" value="true"/>
                    <tiles:put name="postbackNavigation" value="true"/>
                </tiles:insert>
                <tiles:insert definition="clientButton" flush="false"
                              operation="EditASVypiskaActivityOperation">
                    <tiles:put name="commandTextKey" value="button.cancel"/>
                    <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                    <tiles:put name="bundle" value="commonBundle"/>
                    <tiles:put name="onclick" value="window.location.reload();"/>
                </tiles:insert>
            </tiles:put>
        </tiles:put>
    </tiles:insert>
</html:form>
