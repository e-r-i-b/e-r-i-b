<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<html:form action="/edit" onsubmit="return setEmptyAction(event)">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="viewMode" value="false"/>
    <c:if test="${form.fields['isViewMode']}">
        <c:set var="viewMode" value="true"/>
    </c:if>

    <tiles:insert definition="webModulePagePfp">
        <tiles:put name="description">
            <c:choose>
                <c:when test="${viewMode}">
                    <bean:message bundle="pfpBundle" key="index.notContinue.description"/>
                </c:when>
                <c:otherwise>
                    <bean:message bundle="pfpBundle" key="index.continue.description"/>    
                </c:otherwise>
            </c:choose>
        </tiles:put>

        <tiles:put name="data">
            <div class="pfpBlocks">

                <div class="pfpButtonsBlock">
                    <c:if test="${form.fields.isAvailableStartPFP == 'true'}">
                        <tiles:insert definition="commandButton" flush="false">
                            <tiles:put name="commandKey"     value="button.startReplanning"/>
                            <tiles:put name="commandTextKey" value="index.button.startNewPlanning.text"/>
                            <tiles:put name="commandHelpKey" value="index.button.startNewPlanning.help"/>
                            <tiles:put name="isDefault"        value="true"/>
                            <tiles:put name="bundle"         value="pfpBundle"/>
                        </tiles:insert>
                    </c:if>
                    <c:if test="${!viewMode}">
                        <tiles:insert definition="commandButton" flush="false">
                            <tiles:put name="commandKey"     value="button.continuePlanning"/>
                            <tiles:put name="commandTextKey" value="index.button.continuePlanning.text"/>
                            <tiles:put name="commandHelpKey" value="index.button.continuePlanning.help"/>
                            <tiles:put name="isDefault"        value="true"/>
                            <tiles:put name="bundle"         value="pfpBundle"/>
                        </tiles:insert>
                    </c:if>

                </div>
            </div>
        </tiles:put>
    </tiles:insert>

</html:form>
