<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<html:form action="/editPortfolioList" onsubmit="return setEmptyAction(event)">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="webModulePagePfp">
        <tiles:put name="title">
            <bean:message bundle="pfpBundle" key="page.title"/>
        </tiles:put>
        <tiles:put name="description">
            <c:choose>
                <c:when test="${form.portfoliosState == 'EMPTY'}">
                    <bean:message bundle="pfpBundle" key="newPortfolioList.description"/>
                </c:when>
                <c:otherwise>
                    <bean:message bundle="pfpBundle" key="editPortfolioList.description"/>
                </c:otherwise>
            </c:choose>
        </tiles:put>

        <tiles:put name="breadcrumbs">
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="main" value="true"/>
                <tiles:put name="action" value="/private/accounts.do"/>
            </tiles:insert>
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="name"><bean:message bundle="pfpBundle" key="index.breadcrumbsLink"/></tiles:put>
                <c:choose>
                    <c:when test="${phiz:impliesService('UseWebAPIService')}">
                        <tiles:put name="url" value="${phiz:getWebAPIUrl('graphics.finance')}"/>
                    </c:when>
                    <c:otherwise>
                        <tiles:put name="action" value="/private/graphics/finance"/>
                    </c:otherwise>
                </c:choose>
            </tiles:insert>
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="name">Финансовое планирование</tiles:put>
                <tiles:put name="last" value="true"/>
            </tiles:insert>
        </tiles:put>
        
        <tiles:put name="data">
            <div class="pfpBlocks portfolioList">
                <div id="paymentStripe">
                    <tiles:insert definition="stripe" flush="false">
                        <tiles:put name="name">
                            <bean:message key="label.line.targets" bundle="pfpBundle"/>
                        </tiles:put>
                        <tiles:put name="future" value="false"/>
                    </tiles:insert>
                    <tiles:insert definition="stripe" flush="false">
                        <tiles:put name="name">
                            <bean:message key="label.line.riskProfile" bundle="pfpBundle"/>
                        </tiles:put>
                        <tiles:put name="future" value="false"/>
                    </tiles:insert>
                    <tiles:insert definition="stripe" flush="false">
                        <tiles:put name="name">
                            <bean:message key="label.line.portfolio" bundle="pfpBundle"/>
                        </tiles:put>
                        <tiles:put name="current" value="true"/>
                    </tiles:insert>
                    <tiles:insert definition="stripe" flush="false">
                        <tiles:put name="name">
                            <bean:message key="label.line.financePlan" bundle="pfpBundle"/>
                        </tiles:put>
                    </tiles:insert>
                    <tiles:insert definition="stripe" flush="false">
                        <tiles:put name="name">
                            <bean:message key="label.line.plan" bundle="pfpBundle"/>
                        </tiles:put>
                    </tiles:insert>
                    <div class="clear"></div>
                </div>


                <c:forEach items="${form.portfolioList}" var="portfolioItem">
                    <div class="portfolio">
                        <c:set var="portfolio" value="${portfolioItem}" scope="request"/>
                        <tiles:insert page="/WEB-INF/jsp/common/pfp/portfolioTemplate.jsp" flush="false"/>
                    </div>
                </c:forEach>

                <script type="text/javascript">
                    function showHideAbstract(obj, className)
                    {
                        var elem = findChildByClassName(obj.parentNode, className);
                        $(elem).toggle();

                        var altText = $(obj).attr('altText');
                        $(obj).attr('altText', $(obj).text());
                        $(obj).text(altText);
                    }

                    function goToEdit(portfolioId)
                    {
                        loadNewAction('', '');
                        window.location = "${editPortfolioUrl}" + "&portfolioId=" + portfolioId;
                    }
                </script>

                <div class="pfpButtonsBlock">
                    <c:choose>
                        <c:when test="${form.portfoliosState == 'FULL'}">
                            <tiles:insert definition="commandButton" flush="false">
                                <tiles:put name="commandKey" value="button.next"/>
                                <tiles:put name="commandHelpKey" value="button.next.help"/>
                                <tiles:put name="bundle" value="pfpBundle"/>
                            </tiles:insert>
                            <div class="clear"></div>
                        </c:when>
                        <c:when test="${form.portfoliosState == 'NOT_EMPTY'}">
                            <tiles:insert definition="confirmationButton" flush="false">
                                <tiles:put name="winId">confirmNextState</tiles:put>
                                <tiles:put name="title"><bean:message bundle="pfpBundle" key="edit.portfolioList.confirmWindow.label"/></tiles:put>
                                <tiles:put name="message"><bean:message bundle="pfpBundle" key="edit.portfolioList.confirmWindow.message"/></tiles:put>
                                <tiles:put name="buttonViewType">buttonGreen</tiles:put>
                                <tiles:put name="confirmCommandKey">button.next</tiles:put>
                                <tiles:put name="currentBundle">pfpBundle</tiles:put>
                            </tiles:insert>
                        </c:when>
                    </c:choose>
                </div>
                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="commandKey" value="button.back2"/>
                    <tiles:put name="commandHelpKey" value="button.back2.help"/>
                    <tiles:put name="bundle" value="pfpBundle"/>
                    <tiles:put name="viewType" value="blueGrayLink"/>
                </tiles:insert>
            </div>
        </tiles:put>
    </tiles:insert>

</html:form>
