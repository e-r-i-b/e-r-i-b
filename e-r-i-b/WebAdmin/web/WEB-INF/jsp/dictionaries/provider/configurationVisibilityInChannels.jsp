<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>

<script type="text/javascript">

    function toogleAvaiblePayments()
    {
        if (!ensureElementByName('field(availablePaymentsForInternetBank)').checked || ${isFederalDisabled})
        {
            $('[name = "field(visiblePaymentsForInternetBank)"]:not([type=hidden])').attr("disabled", true);
            $('[name = "field(visiblePaymentsForInternetBank)"][type=hidden]').attr("value", $('[name = "field(visiblePaymentsForInternetBank)"]:checked').val());
        }
        else
        {
            $('[name = "field(visiblePaymentsForInternetBank)"]').attr("disabled", false);
        }

        if (!ensureElementByName('field(availablePaymentsForMApi)').checked || ${isFederalDisabled})
        {
            $('[name = "field(visiblePaymentsForMApi)"]:not([type=hidden])').attr("disabled", true);
            $('[name = "field(visiblePaymentsForMApi)"][type=hidden]').attr("value", $('[name = "field(visiblePaymentsForMApi)"]:checked').val());
        }
        else
        {
            $('[name = "field(visiblePaymentsForMApi)"]').attr("disabled", false);
        }

        if (!ensureElementByName('field(availablePaymentsForSocialApi)').checked || ${isFederalDisabled})
        {
            $('[name = "field(visiblePaymentsForSocialApi)"]:not([type=hidden])').attr("disabled", true);
            $('[name = "field(visiblePaymentsForSocialApi)"][type=hidden]').attr("value", $('[name = "field(visiblePaymentsForSocialApi)"]:checked').val());
        }
        else
        {
            $('[name = "field(visiblePaymentsForSocialApi)"]').attr("disabled", false);
        }

        if (!ensureElementByName('field(availablePaymentsForAtmApi)').checked || ${isFederalDisabled})
        {
            $('[name = "field(visiblePaymentsForAtmApi)"]:not([type=hidden])').attr("disabled", true);
            $('[name = "field(visiblePaymentsForAtmApi)"][type=hidden]').attr("value", $('[name = "field(visiblePaymentsForAtmApi)"]:checked').val());
        }
        else
        {
            $('[name = "field(visiblePaymentsForAtmApi)"]').attr("disabled", false);
        }

        $('[name = "field(versionAPI)"]:not([type=hidden])').attr("disabled", !ensureElementByName('field(availablePaymentsForMApi)').checked || ${isFederalDisabled});
        $('[name = "field(versionAPI)"][type=hidden]').attr("value", $('[name = "field(versionAPI)"] option:selected').val());
    }
</script>

<div id="visibilityChannelsRow" class="showBilling showTax">
    <fieldset>
        <legend>
            <bean:message key="label.visibility.channels" bundle="providerBundle"/>
        </legend>
        <div class="providerContent">
            <bean:message key="label.visibility.explanation.message" bundle="providerBundle"/>
        </div>
        <tiles:insert definition="simpleFormRow" flush="false">
            <tiles:put name="title">
                <bean:message key="label.internet.bank" bundle="providerBundle"/>
            </tiles:put>
            <tiles:put name="isNecessary" value="true"/>
            <tiles:put name="data">
                <html:checkbox property="field(availablePaymentsForInternetBank)" name="frm" value="true"
                               onclick="toogleAvaiblePayments();"
                               disabled="${isFederalDisabled}"/>
                <bean:message key="label.available" bundle="providerBundle"/>
            </tiles:put>
        </tiles:insert>
        <c:if test="${isFederalDisabled}">
            <html:hidden property="field(availablePaymentsForInternetBank)" name="frm"/>
        </c:if>
        <tiles:insert definition="simpleFormRow" flush="false">
            <tiles:put name="title">
                <bean:message key="label.visible" bundle="providerBundle"/>
            </tiles:put>
            <tiles:put name="isNecessary" value="true"/>
            <tiles:put name="data">
                <html:radio property="field(visiblePaymentsForInternetBank)" name="frm" value="true" disabled="${isFederalDisabled}"/>
                <bean:message key="label.catalog" bundle="providerBundle"/>
                <br>
                <html:radio property="field(visiblePaymentsForInternetBank)" name="frm" value="false" disabled="${isFederalDisabled}" styleId="hideInSystem"/>
                <bean:message key="label.search.only"  bundle="providerBundle"/>
                <html:hidden property="field(visiblePaymentsForInternetBank)" name="frm"/>
            </tiles:put>
        </tiles:insert>
        <tiles:insert definition="simpleFormRow" flush="false">
            <tiles:put name="title">
                <bean:message key="label.mApi" bundle="providerBundle"/>
            </tiles:put>
            <tiles:put name="isNecessary" value="true"/>
            <tiles:put name="data">
                <html:checkbox property="field(availablePaymentsForMApi)" name="frm" value="true"
                               onclick="toogleAvaiblePayments();"
                               disabled="${isFederalDisabled}"/>
                <bean:message key="label.available" bundle="providerBundle"/>
            </tiles:put>
        </tiles:insert>
        <c:if test="${isFederalDisabled}">
            <html:hidden property="field(availablePaymentsForMApi)" name="frm"/>
        </c:if>
        <tiles:insert definition="simpleFormRow" flush="false">
            <tiles:put name="title">
                <bean:message key="label.mobile.versionAPI" bundle="providerBundle"/>
            </tiles:put>
            <tiles:put name="data">
                <html:select property="field(versionAPI)" styleId="versionAPI" name="frm" disabled="${isFederalDisabled}">
                    <c:forEach items="${frm.mapiVersions}" var="versionApi">
                        <html:option value="${versionApi.solid}">${versionApi}</html:option>
                    </c:forEach>
                </html:select>
                <html:hidden property="field(versionAPI)" name="frm"/>
            </tiles:put>
        </tiles:insert>
        <tiles:insert definition="simpleFormRow" flush="false">
            <tiles:put name="title">
                <bean:message key="label.visible" bundle="providerBundle"/>
            </tiles:put>
            <tiles:put name="isNecessary" value="true"/>
            <tiles:put name="data">
                <html:radio property="field(visiblePaymentsForMApi)" name="frm" value="true" disabled="${isFederalDisabled}"/>
                <bean:message key="label.catalog" bundle="providerBundle"/>
                <br>
                <html:radio property="field(visiblePaymentsForMApi)" name="frm" value="false" disabled="${isFederalDisabled}" styleId="hideInMApi"/>
                <bean:message key="label.search.only" bundle="providerBundle"/>
                <html:hidden property="field(visiblePaymentsForMApi)" name="frm"/>
            </tiles:put>
        </tiles:insert>
        <c:if test="${isFederalDisabled}">
            <html:hidden property="field(visiblePaymentsForMApi)" name="frm"/>
        </c:if>
        <tiles:insert definition="simpleFormRow" flush="false">
            <tiles:put name="title">
                <bean:message key="label.atmApi" bundle="providerBundle"/>
            </tiles:put>
            <tiles:put name="isNecessary" value="true"/>
            <tiles:put name="data">
                <html:checkbox property="field(availablePaymentsForAtmApi)" name="frm" value="true"
                               onclick="toogleAvaiblePayments();"
                               disabled="${isFederalDisabled}"/>
                <bean:message key="label.available" bundle="providerBundle"/>
            </tiles:put>
        </tiles:insert>
        <c:if test="${isFederalDisabled}">
            <html:hidden property="field(availablePaymentsForAtmApi)" name="frm"/>
        </c:if>
        <tiles:insert definition="simpleFormRow" flush="false">
            <tiles:put name="title">
                <bean:message key="label.visible" bundle="providerBundle"/>
            </tiles:put>
            <tiles:put name="isNecessary" value="true"/>
            <tiles:put name="data">
                <html:radio property="field(visiblePaymentsForAtmApi)" name="frm" value="true" disabled="${isFederalDisabled}"/>
                <bean:message key="label.catalog" bundle="providerBundle"/>
                <br>
                <html:radio property="field(visiblePaymentsForAtmApi)" name="frm" value="false" disabled="${isFederalDisabled}" styleId="hideInAtm"/>
                <bean:message key="label.search.only" bundle="providerBundle"/>
                <html:hidden property="field(visiblePaymentsForAtmApi)" name="frm"/>
            </tiles:put>
        </tiles:insert>
        <c:if test="${isFederalDisabled}">
            <html:hidden property="field(visiblePaymentsForAtmApi)" name="frm"/>
        </c:if>

        <tiles:insert definition="simpleFormRow" flush="false">
            <tiles:put name="title">
                <bean:message key="label.socialApi" bundle="providerBundle"/>
            </tiles:put>
            <tiles:put name="isNecessary" value="true"/>
            <tiles:put name="data">
                <html:checkbox property="field(availablePaymentsForSocialApi)" name="frm" value="true"
                               onclick="toogleAvaiblePayments();"
                               disabled="${isFederalDisabled}"/>
                <bean:message key="label.available" bundle="providerBundle"/>
            </tiles:put>
        </tiles:insert>
        <c:if test="${isFederalDisabled}">
            <html:hidden property="field(availablePaymentsForSocialApi)" name="frm"/>
        </c:if>
        <tiles:insert definition="simpleFormRow" flush="false">
            <tiles:put name="title">
                <bean:message key="label.visible" bundle="providerBundle"/>
            </tiles:put>
            <tiles:put name="isNecessary" value="true"/>
            <tiles:put name="data">
                <html:radio property="field(visiblePaymentsForSocialApi)" name="frm" value="true" disabled="${isFederalDisabled}"/>
                <bean:message key="label.catalog" bundle="providerBundle"/>
                <br>
                <html:radio property="field(visiblePaymentsForSocialApi)" name="frm" value="false" disabled="${isFederalDisabled}" styleId="hideInSocial"/>
                <bean:message key="label.search.only" bundle="providerBundle"/>
                <html:hidden property="field(visiblePaymentsForSocialApi)" name="frm"/>
            </tiles:put>
        </tiles:insert>
        <c:if test="${isFederalDisabled}">
            <html:hidden property="field(visiblePaymentsForSocialApi)" name="frm"/>
        </c:if>
        <tiles:insert definition="simpleFormRow" flush="false">
            <tiles:put name="title">
                <bean:message key="label.ERMB" bundle="providerBundle"/>
            </tiles:put>
            <tiles:put name="isNecessary" value="true"/>
            <tiles:put name="data">
                <html:checkbox property="field(availablePaymentsForErmb)" name="frm" value="true" disabled="${isFederalDisabled}"/>
                <bean:message key="label.available" bundle="providerBundle"/>
            </tiles:put>
        </tiles:insert>
        <c:if test="${isFederalDisabled}">
            <html:hidden property="field(availablePaymentsForErmb)" name="frm"/>
        </c:if>
        <tiles:insert definition="simpleFormRow" flush="false">
            <tiles:put name="title">
                <bean:message key="label.sms.template" bundle="providerBundle"/>
            </tiles:put>
            <tiles:put name="isNecessary" value="true"/>
            <tiles:put name="data">
                <html:checkbox property="field(mobilebank)" name="frm" value="true" disabled="${isFederalDisabled}"/>
            </tiles:put>
        </tiles:insert>
        <c:if test="${isFederalDisabled}">
            <html:hidden property="field(mobilebank)" name="frm"/>
        </c:if>
    </fieldset>
</div>