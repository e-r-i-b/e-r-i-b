<%--
  Created by IntelliJ IDEA.
  User: lepihina
  Date: 19.03.2013
  Time: 11:28:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<tiles:importAttribute/>

<c:set var="commonImagePath" value="${globalUrl}/commonSkin/images"/>

<html:form action="/private/finances/targets/selectTarget">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="targets" value="${form.targets}"/>
    <c:set var="url" value="${phiz:calculateActionURL(pageContext,'/private/finances/targets/editTarget')}"/>

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
                        <tiles:put name="infoText"><bean:message key="selectTarget.infoText" bundle="financesBundle"/></tiles:put>
                        <tiles:put name="data">
                            <div class="selectAccTargetBlock">
                                <c:forEach items="${targets}" var="target">
                                    <div class="accTarget firstTarget">
                                        <div class="accTargetImg">
                                            <a href="${url}?targetType=${target}"><img src="${commonImagePath}/account_targets/${target}.png" alt="${target.description}"></a>
                                        </div>
                                        <div class="accTargetName">
                                            <a href="${url}?targetType=${target}">${target.description}</a>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>

                            <c:if test="${form.hasTargets}">
                                <tiles:insert definition="clientButton" flush="false">
                                    <tiles:put name="commandTextKey" value="button.back.to.targetList"/>
                                    <tiles:put name="commandHelpKey" value="button.back.to.targetList.help"/>
                                    <tiles:put name="bundle" value="financesBundle"/>
                                    <tiles:put name="viewType" value="blueGrayLink"/>
                                    <tiles:put name="action" value="/private/finances/targets/targetsList"/>
                                </tiles:insert>
                            </c:if>
                        </tiles:put>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>