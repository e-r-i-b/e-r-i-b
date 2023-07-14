<%@ page contentType="text/html;charset=windows-1251" language="java"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="faqLink" value="${phiz:calculateActionURLWithAnchor(pageContext, '/faq.do', 'm5')}"/>
<html:form action="/private/userprofile/userSettings">
<c:set var="form" value="${phiz:currentForm(pageContext)}" scope="request"/>
<c:set var="confirmStrategy" value="${form.confirmStrategy}"/>
<c:set var="hasCapButton" value="${phiz:isContainStrategy(confirmStrategy,'cap')}"/>

<c:if test="${not empty form.fields.confirmableObject}">
    <c:set var="confirmRequest" value="${phiz:currentConfirmRequest(form.fields.confirmableObject)}" scope="request"/>
</c:if>

<tiles:insert definition="userProfile">
    <tiles:put name="enabledLink" value="false"/>
    <tiles:put name="breadcrumbs">
        <tiles:insert definition="breadcrumbsLink" flush="false">
            <tiles:put name="main" value="true"/>
            <tiles:put name="action" value="/private/accounts.do"/>
        </tiles:insert>
        <tiles:insert definition="breadcrumbsLink" flush="false">
            <tiles:put name="name" value="Настройки"/>
            <tiles:put name="action" value="/private/userprofile/userSettings.do"/>
        </tiles:insert>
        <tiles:insert definition="breadcrumbsLink" flush="false">
            <tiles:put name="name" value="Персональная информация"/>
            <tiles:put name="last" value="true"/>
        </tiles:insert>
    </tiles:put>
    <tiles:put name="data" type="string">
        <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
        <c:set var="bundle" value="userprofileBundle"/>
        <html:hidden property="field(unsavedData)" name="form"/>
        <script type="text/javascript">
        $(document).ready(function()
        {
            var phonesString = "${form.fields.mobilePhone}";
            if (phonesString != '')
            {
                var values = phonesString.split(', ');
                for (var i = 0; i < values.length; i++)
                    $('#mobilePhone').append('<div class="phoneList">' + values[i] + '</div>');
            }
        });

        <c:if test="${phiz:impliesOperation('EditUserSettingsOperation', 'ClientProfile')}">
            $(document).ready(function()
            {
                var windowId = 'personRegionsDiv';
                var selectRegion = function(myself)
                {
                    ensureElementByName("field(regionId)").value = myself.currentRegionId;
                    ensureElementByName("field(regionName)").value = myself.currentRegionName;
                    ensureElement("region").innerHTML = myself.currentRegionName;
                    win.close(windowId);
                };
                var parameters =
                {
                    windowId: windowId,
                    click:
                    {
                        getParametersCallback: function(id)
                        {
                            return "setCnt=true" + (id > 0? '&id=' + id: '');
                        },
                        afterActionCallback: function (myself, msg)
                        {
                            if (trim(msg) != '')
                                $("#personRegionsDiv").html(msg);
                            else
                                selectRegion(myself);
                        }
                    },
                    choose:
                    {
                        useAjax: false,
                        afterActionCallback: selectRegion
                    }
                };
                initializeRegionSelector(parameters);
                //регистрируем слушателя изменения региона в шапке
                getRegionSelector('regionsDiv').addListener(function(){location.reload(true);});
            });
            </c:if>
            <%-- контактная информация --%>
            function openContacts()
            {
                win.open('userContacts');
                ensureElement("fieldEmail").value = trim(getField("email").value);
                ensureElement("fieldHomePhone").value = trim(getField("homePhone").value);
                ensureElement("fieldJobPhone").value = trim(getField("jobPhone").value);
                ensureElement("fieldMailFormat").value = trim(getField("mailFormat").value);
            }

            <%-- Пенсионный фонд РФ --%>
            function openUserPensionFundRF()
            {
                win.open('userPensionFundRF');
                ensureElement("fieldSNILS").value = trim(ensureElement("SNILS").innerHTML);
            }

            function checkData()
            {
                var unsavedData = ensureElementByName("field(unsavedData)").value;
                if (unsavedData) return true;
                if (!isDataChanged())
                {
                    addMessage("Вы не внесли никаких изменений в персональную информацию.");
                    return false;
                }
                return true;
             }
        </script>
        <div id="profile" onkeypress="onEnterKey(event);">
            <tiles:insert definition="mainWorkspace" flush="false">
                <tiles:put name="title" value="Настройки"/>
                <tiles:put name="data">
                    <tiles:insert definition="userSettings" flush="false">
                        <tiles:put name="data">
                            <c:set var="selectedTab" value="pesonalInfo"/>
                            <%@ include file="/WEB-INF/jsp/private/userprofile/userSettingsHeader.jsp" %>
                            <div class="payments-tabs">
                                <div class="clear"></div>
                                <div class="personal-items">
                                    <c:if test="${phiz:impliesOperation('EditUserSettingsOperation', 'ClientProfile')}">
                                    <tiles:insert definition="window" flush="false">
                                        <tiles:put name="id" value="userContacts"/>
                                        <tiles:put name="data">
                                            <%@include file="userContacts.jsp" %>
                                        </tiles:put>
                                    </tiles:insert>

                                    <tiles:insert definition="window" flush="false">
                                        <tiles:put name="id" value="userPensionFundRF"/>
                                        <tiles:put name="data">
                                            <%@include file="userPensionFundRF.jsp" %>
                                        </tiles:put>
                                    </tiles:insert>
                                    </c:if>
                                    <h2 class="darkTitle"><bean:message bundle="${bundle}" key="title.profile.main"/></h2>

                                    <tiles:insert definition="formRow" flush="false">
                                        <tiles:put name="title"><bean:message key="label.profile.fio"
                                                                              bundle="${bundle}"/></tiles:put>
                                        <tiles:put name="needMark" value="false"/>
                                        <tiles:put name="data">
                                            <span class="bold">
                                                <bean:write name="form" property="field(fio)"/>
                                            </span>
                                        </tiles:put>
                                    </tiles:insert>

                                    <tiles:insert definition="formRow" flush="false">
                                        <tiles:put name="title"><bean:message key="label.profile.phone.mobile"
                                                                              bundle="${bundle}"/></tiles:put>
                                        <tiles:put name="needMark" value="false"/>
                                        <tiles:put name="data">
                                            <span class="bold" id="mobilePhone"></span>
                                        </tiles:put>
                                    </tiles:insert>

                                    <tiles:insert definition="formRow" flush="false">
                                        <tiles:put name="title"><bean:message key="label.profile.region"
                                                                              bundle="${bundle}"/></tiles:put>
                                        <tiles:put name="needMark" value="false"/>
                                        <tiles:put name="data">
                                            <c:choose>
                                                <c:when test="${phiz:impliesOperation('EditUserSettingsOperation', 'ClientProfile')}">
                                                    <html:hidden name="form" property="field(regionId)"/>
                                                    <html:hidden name="form" property="field(regionName)"/>
                                                    <div onclick="win.open('personRegionsDiv'); return false">
                                                        <div>
                                                            <span id="region" class="regionUserSelect blueGrayLinkDotted">
                                                                <bean:write name="form" property="field(regionName)"/>
                                                            </span>
                                                        </div>
                                                        <div class="clear"></div>
                                                    </div>

                                                    <c:set var="regionUrl" value="${phiz:calculateActionURL(pageContext,'/dictionaries/regions/list')}"/>
                                                    <tiles:insert definition="window" flush="false">
                                                        <tiles:put name="id" value="personRegionsDiv"/>
                                                        <tiles:put name="loadAjaxUrl" value="${regionUrl}?isOpening=true"/>
                                                        <tiles:put name="styleClass" value="regionsDiv"/>
                                                    </tiles:insert>
                                                </c:when>
                                                <c:otherwise>
                                                    <div><div><span id="region">
                                                        <bean:write name="form" property="field(regionName)"/>
                                                    </span></div></div>
                                                </c:otherwise>
                                            </c:choose>
                                            <%--
                                            <img class="save-template-hint" src="${globalImagePath}/hint.png" alt=""
                                                 onclick="javascript:openFAQ('${faqLink}')"/>--%>
                                        </tiles:put>
                                    </tiles:insert>
                                    <div class="grayUnderline"></div>

                                    <h2  class="darkTitle"><bean:message bundle="${bundle}" key="title.profile.contacts"/>
                                         <c:set var="personInfo" value="${phiz:getPersonInfo()}"/>
                                        <c:if test="${empty confirmRequest && phiz:impliesOperation('EditUserSettingsOperation', 'ClientProfile')}">
                                                <span class="edit">
                                                    <a class="blueGrayLinkDotted" href="#" onclick="openContacts(); return false;">
                                                        <bean:message bundle="${bundle}" key="link.form.edit"/>
                                                    </a>
                                                </span>
                                        </c:if>
                                    </h2>

                                    <tiles:insert definition="formRow" flush="false">
                                        <tiles:put name="title"><bean:message key="label.profile.email"
                                                                              bundle="${bundle}"/></tiles:put>
                                        <tiles:put name="needMark" value="false"/>
                                        <tiles:put name="data">
                                                <html:hidden name="form" property="field(email)"/>
                                                <html:hidden name="form" property="field(mailFormat)"/>
                                                <c:set var="email">
                                                    <bean:write name="form" property="field(email)"/>
                                                </c:set>
                                                <span id="email" class="bold">${email}</span>
                                                <div id="mailFormat" class="emailMessage">
                                                    <c:choose>
                                                        <c:when test="${email != ''}">
                                                            <bean:message key="mailFormat.value.${form.fields.mailFormat}.description" bundle="${bundle}"/>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <bean:message key="label.profile.email.empty" bundle="${bundle}"/>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                        </tiles:put>
                                    </tiles:insert>

                                    <tiles:insert definition="formRow" flush="false">
                                        <tiles:put name="title"><bean:message key="label.profile.phone.home"
                                                                              bundle="${bundle}"/></tiles:put>
                                        <tiles:put name="needMark" value="false"/>
                                        <tiles:put name="data">
                                                <html:hidden name="form" property="field(homePhone)"/>
                                                <span id="homePhone" class="bold"><bean:write name="form" property="field(homePhone)"/></span>
                                        </tiles:put>
                                    </tiles:insert>

                                    <tiles:insert definition="formRow" flush="false">
                                        <tiles:put name="title"><bean:message key="label.profile.phone.job"
                                                                              bundle="${bundle}"/></tiles:put>
                                        <tiles:put name="needMark" value="false"/>
                                        <tiles:put name="data">
                                                <html:hidden name="form" property="field(jobPhone)"/>
                                                <span id="jobPhone" class="bold"><bean:write name="form" property="field(jobPhone)"/></span>
                                        </tiles:put>
                                    </tiles:insert>
                                    <div class="grayUnderline"></div>

                                    <h2  class="darkTitle">
                                        <bean:message bundle="${bundle}" key="title.profile.pensionFundRF"/>
                                        <c:if test="${empty confirmRequest && phiz:impliesOperation('EditUserSettingsOperation', 'ClientProfile')}">
                                                <span class="edit">
                                                    <a class="blueGrayLinkDotted" href="#" onclick="openUserPensionFundRF(); return false;">
                                                        <bean:message bundle="${bundle}" key="link.form.edit"/>
                                                    </a>
                                                </span>
                                        </c:if>
                                    </h2>

                                    <tiles:insert definition="formRow" flush="false">
                                        <tiles:put name="title"><bean:message key="label.profile.SNILS"
                                                                              bundle="${bundle}"/></tiles:put>
                                        <tiles:put name="needMark" value="false"/>
                                        <tiles:put name="data">
                                            <html:hidden name="form" property="field(SNILS)"/>
                                            <br>
                                            <span id="SNILS" class="bold">
                                                <bean:write name="form" property="field(SNILS)"/>
                                            </span>
                                        </tiles:put>
                                    </tiles:insert>

                                </div>
                            </div>
                        </tiles:put>

                        <div class="clear"></div>
                        <c:set var="message" value=""/>
                        <tiles:put name="buttons">
                            &nbsp;
                            <div class="buttonsArea">
                                <c:choose>
                                    <c:when test="${not empty confirmRequest}">
                                        <tiles:insert definition="commandButton" flush="false">
                                            <tiles:put name="commandKey" value="button.backToEdit"/>
                                            <tiles:put name="commandHelpKey" value="button.backToEdit.help"/>
                                            <tiles:put name="viewType" value="buttonGrey"/>
                                            <tiles:put name="bundle" value="commonBundle"/>
                                        </tiles:insert>
                                        <tiles:insert definition="confirmButtons" flush="false" operation="EditUserSettingsOperation">
                                            <tiles:put name="ajaxUrl" value="/private/async/userprofile/userSettings"/>
                                            <tiles:put name="confirmRequest" beanName="confirmRequest"/>
                                            <tiles:put name="anotherStrategy" value="false"/>
                                            <tiles:put name="hasCapButton" value="${hasCapButton}"/>
                                        </tiles:insert>
                                    </c:when>
                                    <c:otherwise>
                                        <tiles:insert definition="clientButton" flush="false">
                                            <tiles:put name="commandTextKey" value="button.cancel"/>
                                            <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                                            <tiles:put name="bundle" value="commonBundle"/>
                                            <tiles:put name="action" value="/private/accounts.do"/>
                                            <tiles:put name="viewType" value="buttonGrey"/>
                                        </tiles:insert>
                                        <tiles:insert definition="commandButton" flush="false" operation="EditUserSettingsOperation">
                                            <tiles:put name="commandKey" value="button.save"/>
                                            <tiles:put name="commandHelpKey" value="button.save.help"/>
                                            <tiles:put name="validationFunction" value="checkData()"/>
                                            <tiles:put name="isDefault" value="true"/>
                                            <tiles:put name="bundle" value="commonBundle"/>
                                        </tiles:insert>
                                    </c:otherwise>
                                </c:choose>
                                <div class="clear"></div>
                            </div>
                        </tiles:put>
                    </tiles:insert>
                </tiles:put>
        </tiles:insert>
        </div>
    </tiles:put>
</tiles:insert>

</html:form>
