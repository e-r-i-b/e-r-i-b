<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>

<c:set var="commonImagePath" value="${globalUrl}/commonSkin/images"/>

<html:form action="/private/finances/targets/targetsList" onsubmit="return setEmptyAction(event)">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="financePlot">

        <tiles:put name="data" type="string">
            <tiles:insert definition="roundBorderWithoutTop" flush="false">
                <tiles:put name="top">
                    <c:set var="selectedTab" value="myTargets"/>
                    <%@ include file="/WEB-INF/jsp/graphics/showFinanceHeader.jsp" %>
                </tiles:put>
                <tiles:put name="data">
                    <tiles:insert definition="financeContainer" flush="false">
                        <tiles:put name="showFavourite" value="false"/>
                        <tiles:put name="data">
                            <c:set var="targetInfoLink" value="true" scope="request"/>
                            <c:set var="isDetailInfoPage" value="false" scope="request"/>
                            <logic:iterate id="item" name="form" property="targets" indexId="i">
                                <c:set var="target" value="${item}" scope="request"/>
                                <c:set var="accountLink" value="${target.accountLink}" scope="request"/>
                                <c:set var="resourceAbstract" value="${form.accountAbstract[accountLink]}" scope="request"/>
                                <c:set var="abstractError" value="${form.accountAbstractErrors[accountLink]}" scope="request"/>
                                <c:set var="showBottomData" value="true" scope="request"/>
                                <c:if test="${empty accountLink}">
                                    <c:set var="showBottomData" value="false" scope="request"/>
                                </c:if>
                                <%@ include file="/WEB-INF/jsp-sbrf/accounts/targetTemplate.jsp"%>

                                <div class="productDivider"></div>
                            </logic:iterate>

                            <div class="buttonsArea">
                                <tiles:insert definition="commandButton" flush="false">
                                    <tiles:put name="commandTextKey" value="button.addTarget"/>
                                    <tiles:put name="commandHelpKey" value="button.addTarget.help"/>
                                    <tiles:put name="bundle" value="financesBundle"/>
                                    <tiles:put name="validationFunction">checkTargetsCount();</tiles:put>
                                    <tiles:put name="action" value="/private/finances/targets/selectTarget.do"/>
                                </tiles:insert>

                                <script type="text/javascript">
                                    function checkTargetsCount()
                                    {
                                        var targetsCount = ${fn:length(form.targets)};
                                        if (targetsCount >= 20)
                                        {
                                            addError("Вы не можете добавить новую цель, потому что Вы создали максимальное количество целей.");
                                            return false;
                                        }
                                        return true;
                                    }
                                </script>
                            </div>
                        </tiles:put>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>

            <%--<tiles:insert definition="hidableRoundBorder" flush="false">
                <tiles:put name="title" value="Неактивные цели"/>
                <tiles:put name="color" value="whiteTop"/>
                <tiles:put name="data">

                </tiles:put>
            </tiles:insert>--%>

        </tiles:put>
    </tiles:insert>
</html:form>