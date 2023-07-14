<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<html:form action="/pfp/configure"  onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="propertiesForm">
    <tiles:put name="tilesDefinition" type="string" value="pfpConfigure"/>
        <tiles:put name="submenu" type="string" value="pfpConfigure"/>
        <tiles:put name="pageName" type="string">
           <bean:message bundle="pfpConfigureBundle" key="title"/>
        </tiles:put>
        <tiles:put name="pageDescription"><bean:message bundle="pfpConfigureBundle" key="description"/></tiles:put>
        <tiles:put name="replicateAccessOperation" value="EditDisplayingRecommendationsOperation"/>
        <tiles:put name="data" type="string">
            <fieldset>
                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="title">
                        <bean:message bundle="pfpConfigureBundle" key="label.recommendation"/>
                    </tiles:put>
                    <tiles:put name="needMargin" value="true"/>
                    <tiles:put name="data">
                        <b><bean:message bundle="pfpConfigureBundle" key="label.condition.displaying.recommendations.START_CAPITAL"/></b>
                    </tiles:put>
                </tiles:insert>
                <table class="propertyFieldStyle">
                    <tiles:insert definition="propertyField" flush="false">
                        <tiles:put name="fieldName" value="condition.displaying.recommendations.START_CAPITAL"/>
                        <tiles:put name="fieldDescription"><bean:message bundle="pfpConfigureBundle" key="label.condition.display"/></tiles:put>
                        <tiles:put name="showHint" value="none"/>
                        <tiles:put name="textSize" value="6"/>
                        <tiles:put name="textMaxLength" value="6"/>
                        <tiles:put name="inputDesc" ><b><bean:message bundle="pfpConfigureBundle" key="label.percent"/></b></tiles:put>
                        <tiles:put name="inputDescLeft" ><b><bean:message bundle="pfpConfigureBundle" key="label.not.show.percent"/></b></tiles:put>
                        <tiles:put name="imagePath" value="${imagePath}"/>
                    </tiles:insert>
                </table>
            </fieldset>

            <fieldset>
                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="title">
                        <bean:message bundle="pfpConfigureBundle" key="label.recommendation"/>
                    </tiles:put>
                    <tiles:put name="needMargin" value="true"/>
                    <tiles:put name="data">
                        <b><bean:message bundle="pfpConfigureBundle" key="label.condition.displaying.recommendations.QUARTERLY_INVEST"/></b>
                    </tiles:put>
                </tiles:insert>
                <table class="propertyFieldStyle">
                    <tiles:insert definition="propertyField" flush="false">
                        <tiles:put name="fieldName" value="condition.displaying.recommendations.QUARTERLY_INVEST"/>
                        <tiles:put name="fieldDescription"><bean:message bundle="pfpConfigureBundle" key="label.condition.display"/></tiles:put>
                        <tiles:put name="showHint" value="none"/>
                        <tiles:put name="textSize" value="6"/>
                        <tiles:put name="textMaxLength" value="6"/>
                        <tiles:put name="inputDesc" ><b><bean:message bundle="pfpConfigureBundle" key="label.percent"/></b></tiles:put>
                        <tiles:put name="inputDescLeft" ><b><bean:message bundle="pfpConfigureBundle" key="label.not.show.percent"/></b></tiles:put>
                        <tiles:put name="imagePath" value="${imagePath}"/>
                    </tiles:insert>
                </table>
            </fieldset>
        </tiles:put>
        <tiles:put name="formButtons">
            <tiles:insert definition="commandButton" flush="false" operation="EditDisplayingRecommendationsOperation">
                <tiles:put name="commandKey"     value="button.save"/>
                <tiles:put name="commandHelpKey" value="button.save.help"/>
                <tiles:put name="bundle"  value="pfpConfigureBundle"/>
                <tiles:put name="isDefault" value="true"/>
                <tiles:put name="postbackNavigation" value="true"/>
            </tiles:insert>
            <tiles:insert definition="clientButton" flush="false" operation="EditDisplayingRecommendationsOperation">
                <tiles:put name="commandTextKey"     value="button.cancel"/>
                <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                <tiles:put name="bundle"  value="pfpConfigureBundle"/>
                <tiles:put name="onclick" value="javascript:resetForm(event)"/>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>