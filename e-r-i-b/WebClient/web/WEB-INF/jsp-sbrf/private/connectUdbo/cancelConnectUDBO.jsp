<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/udbo/cancel" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="globalImagePath" value="${globalUrl}/commonSkin/images"/>

    <tiles:insert definition="main">
        <tiles:put name="pageTitle" type="string" value="Сбербанк Онлайн"/>
        <tiles:put name="headerGroup" value="true"/>
        <tiles:put name="needHelp" value="true"/>
        <tiles:put name="mainmenu" value="moreSbol"/>
        <tiles:put name="data" type="string">
            <tiles:insert definition="roundBorderLight" flush="false">
                <tiles:put name="color" value="orangeLight"/>
                <tiles:put name="data">
                    <div class="errorMessage">
                        <h1>${form.messageTitle}</h1>
                        ${form.messageText}
                    </div>
                </tiles:put>
            </tiles:insert>

            <div class="dividerBtn"></div>

            <div class="float">
                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.back.to.main"/>
                    <tiles:put name="commandHelpKey" value="button.back.help"/>
                    <tiles:put name="bundle"         value="commonBundle"/>
                    <tiles:put name="viewType"       value="darkGrayButton"/>
                    <tiles:put name="imageUrl"       value="${globalImagePath}/back-to-catalog.png"/>
                    <tiles:put name="imageHover"     value="${globalImagePath}/back-to-catalog-hover.png"/>
                    <tiles:put name="imagePosition"  value="left"/>
                    <tiles:put name="action"         value="/private/accounts.do"/>
                </tiles:insert>
            </div>
        </tiles:put>
    </tiles:insert>
</html:form>
