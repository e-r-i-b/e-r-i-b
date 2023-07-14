<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://rssl.com/pfptags" prefix="pfptags" %>
<tiles:importAttribute/>

<html:form action="/editPortfolio/chooseProduct" onsubmit="return setEmptyAction(event)">
    <c:set var="globalImagePath" value="${globalUrl}/images"/>
    <c:set var="globalPath" value="${globalUrl}/commonSkin/images"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="personPortfolioType" value="${pfptags:getPersonPortfolioTypeById(form.portfolioId)}"/>

    <tiles:insert definition="webModulePagePfp">
        <tiles:put name="title">
            <bean:message bundle="pfpBundle" key="page.title"/>
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
                <tiles:put name="action" value="/private/pfp/editPortfolioList.do?id=${form.id}"/>
            </tiles:insert>
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="name">«${personPortfolioType}»</tiles:put>
                <tiles:put name="action" value="/private/pfp/editPortfolio.do?id=${form.id}&portfolioId=${form.portfolioId}"/>
            </tiles:insert>
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="name"><bean:message key="label.productTitle.${form.dictionaryProductType}" bundle="pfpBundle"/></tiles:put>
                <tiles:put name="last" value="true"/>
            </tiles:insert>
        </tiles:put>
        <tiles:put name="data">
            <div class="pfpBlocks">
                <tiles:insert definition="formHeader" flush="false">
                    <c:set var="insuranceImageId" value="${null}"/>
                    <tiles:put name="image" value="${globalPath}/pfp/icon_${form.dictionaryProductType}.png"/>
                    <tiles:put name="width" value="64px"/>
                    <tiles:put name="height" value="64px"/>
                    <tiles:put name="description">
                        <bean:message key="label.chooseProduct.${form.dictionaryProductType}" bundle="pfpBundle"/>
                        <br/>
                        <span class="notation">
                            <bean:message key="label.chooseProduct.${form.dictionaryProductType}.notation" bundle="pfpBundle" failIfNone="false"/>
                        </span>
                    </tiles:put>
                </tiles:insert>
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

                <c:url var="editPortfolioUrl" value="/editPortfolio.do">
                    <c:param name="id" value="${form.id}"/>
                    <c:param name="portfolioId" value="${form.portfolioId}"/>
                </c:url>

                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.back.toProfile"/>
                    <tiles:put name="commandHelpKey" value="button.back.toProfile.help"/>
                    <tiles:put name="bundle" value="pfpBundle"/>
                    <tiles:put name="viewType" value="blueGrayLink"/>
                    <tiles:put name="onclick" value="goBackToPortfolio();"/>
                </tiles:insert>
                <div class="clear"></div>
                
                <c:choose>
                    <c:when test="${phiz:size(form.data) > 0}">
                        <tiles:insert page="/WEB-INF/jsp/common/pfp/chooseProduct/displayProducts.jsp" flush="false">
                            <tiles:put name="productType" value="${form.dictionaryProductType}"/>
                            <tiles:put name="dataName" value="data"/>
                        </tiles:insert>
                    </c:when>
                    <c:otherwise><br>
                        <div class="emptyText">
                            <tiles:insert definition="roundBorderLight" flush="false">
                                <tiles:put name="color" value="greenBold"/>
                                <tiles:put name="data">
                                    Не найдено ни одного продукта. Пожалуйста, выберите другой банковский продукт.
                                </tiles:put>
                            </tiles:insert>
                        </div>
                    </c:otherwise>
                </c:choose>

                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.back.toProfile"/>
                    <tiles:put name="commandHelpKey" value="button.back.toProfile.help"/>
                    <tiles:put name="bundle" value="pfpBundle"/>
                    <tiles:put name="viewType" value="blueGrayLink"/>
                    <tiles:put name="onclick" value="goBackToPortfolio();"/>
                </tiles:insert>
            </div>

                <%-- TODO убрать сделать нормальный переход через параметр action в clientButton --%>
                <%-- TODO в текущий момент для модульности такой переход(через экшен) не работает --%>
                <script type="text/javascript">
                    function goBackToPortfolio()
                    {
                        loadNewAction('', '');
                        window.location = "${editPortfolioUrl}";
                    }
                </script>
            
        </tiles:put>
    </tiles:insert>

</html:form>