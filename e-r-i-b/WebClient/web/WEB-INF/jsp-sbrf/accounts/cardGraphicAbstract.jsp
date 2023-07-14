<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html:form action="/private/cards/graphic-abstract"  onsubmit="return setEmptyAction(event)">
<tiles:importAttribute/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<tiles:insert definition="cardPlot">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="cardLink" value="${form.cardLink}" scope="request"/>
    <c:set var="cardAccountAbstract" value="${form.cardAccountAbstract}" scope="request"/>
    <c:set var="page" value="cardsDetail" scope="request" />
    <c:set var="card"    value="${cardLink.card}"/>
    <c:set var="isLock" value="${card.cardState!=null && card.cardState!='active'}"/>
    <c:set var="cardInfoUrl" value="${phiz:calculateActionURL(pageContext,'/private/cards/info.do?id=')}"/>
    <tiles:put name="mainmenu" value="Cards"/>
    <tiles:put name="menu" type="string"/>
    <tiles:put name="breadcrumbs">
        <tiles:insert definition="breadcrumbsLink" flush="false">
            <tiles:put name="main" value="true"/>
            <tiles:put name="action" value="/private/accounts.do"/>
        </tiles:insert>
        <tiles:insert definition="breadcrumbsLink" flush="false">
            <tiles:put name="name" value="�����"/>
            <tiles:put name="action" value="/private/cards/list.do"/>
        </tiles:insert>
        <tiles:insert definition="breadcrumbsLink" flush="false">
            <tiles:put name="name"><bean:write name="cardLink" property="name"/> ${phiz:getCutCardNumber(cardLink.number)}</tiles:put>
            <tiles:put name="last" value="true"/>
        </tiles:insert>
     </tiles:put>
    <tiles:put name="data" type="string">
        <script type="text/javascript">
            addMessage("�� ���������, ����������� � ����������, ���������� ��� ���������� ������� �����, �������� ������� � "+
                       "����������� ������� ����� ������������ � ��������� �� 3-� ������� ���� � ������� ���������� ��������.");
        </script>
        <html:hidden property="id"/>
        <tiles:insert definition="mainWorkspace" flush="false">
            <tiles:put name="data">
                <c:set var="showInMainCheckBox" value="true" scope="request"/>

                <c:set var="nameOrNumber">
                    <c:choose>
                        <c:when test="${not empty cardLink.name}">
                            �${cardLink.name}�
                        </c:when>
                        <c:otherwise>
                            ${phiz:getFormattedAccountNumber(cardNumber)}
                        </c:otherwise>
                    </c:choose>
                </c:set>
                <c:set var="pattern">
                    <c:choose>
                        <c:when test="${not empty cardLink.name}">
                            �${cardLink.patternForFavouriteLink}�
                        </c:when>
                        <c:otherwise>
                            ${cardLink.patternForFavouriteLink}
                        </c:otherwise>
                    </c:choose>
                </c:set>

                <div class="abstractContainer3">
                    <c:set var="baseInfo">
                        <bean:message key="favourite.link.card" bundle="paymentsBundle"/>
                    </c:set>

                    <div class="favouriteLinksButton marginFavouriteLinksButton">
                        <tiles:insert definition="addToFavouriteButton" flush="false">
                            <tiles:put name="formName"><c:out value='${baseInfo} ${nameOrNumber}'/></tiles:put>
                            <tiles:put name="patternName"><c:out value='${baseInfo} ${pattern}'/></tiles:put>
                            <tiles:put name="typeFormat">CARD_LINK</tiles:put>
                            <tiles:put name="productId">${form.id}</tiles:put>
                        </tiles:insert>
                    </div>
                </div>

                <div class="productdetailInfo">
                    <%@ include file="cardTemplate.jsp"%>
                </div>
                <div class="tabContainer">
                    <tiles:insert definition="paymentTabs" flush="false">
                        <c:set var="showTemplates" value="${phiz:showTemplatesForProduct('card')}"/>
                        <tiles:put name="count" value="4"/>
                        <c:if test="${!showTemplates}">
                            <tiles:put name="count" value="3"/>
                        </c:if>
                        <tiles:put name="tabItems">
                            <tiles:insert definition="paymentTabItem" flush="false">
                                <tiles:put name="position" value="first"/>
                                <tiles:put name="active" value="false"/>
                                <tiles:put name="title" value="��������� ��������"/>
                                <tiles:put name="action" value="/private/cards/info?id=${cardLink.id}"/>
                            </tiles:insert>
                            <tiles:insert definition="paymentTabItem" flush="false">
                                <tiles:put name="active" value="false"/>
                                <tiles:put name="title" value="���������� �� �����"/>
                                <tiles:put name="action" value="/private/cards/detail.do?id=${cardLink.id}"/>
                            </tiles:insert>
                            <c:if test="${showTemplates}">
                                <tiles:insert definition="paymentTabItem" flush="false">
                                    <tiles:put name="active" value="false"/>
                                    <tiles:put name="title" value="������� � �������"/>
                                    <tiles:put name="action" value="/private/cards/payments.do?id=${cardLink.id}"/>
                                </tiles:insert>
                            </c:if>
                            <tiles:insert definition="paymentTabItem" flush="false">
                                <tiles:put name="position" value="last"/>
                                <tiles:put name="active" value="true"/>
                                <tiles:put name="title" value="����������� �������"/>
                                <tiles:put name="action" value="/private/cards/graphic-abstract.do?id=${cardLink.id}"/>
                            </tiles:insert>
                        </tiles:put>
                    </tiles:insert>

                    <div class="clear">&nbsp;</div>
                    <div class="abstractContainer2">
                        <%-- ������ --%>
                        <c:set var="periodName" value="Date"/>
                        <c:set var="curType"><bean:write name="org.apache.struts.taglib.html.BEAN"
                                                         property="filter(type${periodName})"/></c:set>
                        <tiles:insert definition="filterDataPeriod" flush="false">
                            <tiles:put name="week" value="false"/>
                            <tiles:put name="month" value="false"/>
                            <tiles:put name="name" value="${periodName}"/>
                            <tiles:put name="buttonKey" value="button.filter"/>
                            <tiles:put name="buttonBundle" value="accountInfoBundle"/>
                            <tiles:put name="needErrorValidate" value="false"/>
                            <tiles:put name="isNeedTitle" value="false"/>
                        </tiles:insert>
                        <%-- /������ --%>
                        <c:choose>
                            <c:when test="${not empty cardAccountAbstract}">
                                 <c:set var="infoText">
                                     <br/>����������: ���� �� ������������ ��� ����������� �������� �� �����, �� �����, �� ������� �� ����� �� ���������, ����� �������� ��� ����� ��������� � �������.
                                 </c:set>
                                 <tiles:insert page="/WEB-INF/jsp/graphics/balanceGraphic.jsp" flush="false">
                                    <tiles:put name="title" value="�� �����" />
                                    <tiles:put name="dateFrom"><bean:write name="org.apache.struts.taglib.html.BEAN" property="filter(from${periodName})" format="MM/dd/yyyy"/></tiles:put>
                                    <tiles:put name="dateTo"><bean:write name="org.apache.struts.taglib.html.BEAN" property="filter(to${periodName})" format="MM/dd/yyyy"/></tiles:put>
                                    <tiles:put name="resourceAbstract" beanName="cardAccountAbstract"/>
                                    <tiles:put name="descriptionBoth">�� ���� �������� �� ������ ���������� �������� ������� �� ����� �����: ������� ���������� ����� ����������� ��� ��������� �������, ����� - ��������� ������� �����. �� ������ ����������� ������� ��������, � ����� ������ ������ ������ ����������� ����������� �������.
                                        <c:if test="${card.cardType =='overdraft'}">
                                           ${infoText}
                                        </c:if>
                                    </tiles:put>
                                    <tiles:put name="descriptionLine">�� ���� �������� �� ������ ���������� ��������� ������� ����� �����. ��� ���� ����� �������� ��������� ����������, �������� ������ �� ������������ ��� ����� �������.
                                        <c:if test="${card.cardType =='overdraft'}">
                                            ${infoText}
                                        </c:if>
                                    </tiles:put>
                                    <tiles:put name="descriptionBar">�� ���� �������� �� ������ ���������� ����������� � �������� ������� � �����. ��� ���� ����� �������� ��������� ���������� � �������� �������, �������� ������ �� ������������ ��� �������.</tiles:put>
                                 </tiles:insert>
                            </c:when>
                            <c:otherwise>
                                <%--���� ���� ����, �� ��� ��������--%>
                                <c:set var="emptyMsg" value="�� ������ ����� �� �� ��������� �� ����� ��������."/>
                                <%--���� ����� ������ ��� (�������� ��� � ��������� ����)--%>
                                <c:choose>
                                    <c:when test="${empty cardAccountAbstract and not empty card and card.cardType=='credit'}">
                                        <c:set var="emptyMsg" value="�� ������ ����� ���������� �������� ����������� �������."/>
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="emptyMsg" value="����������� ������� �������� ����������. ����������, ��������� ������� �����."/>
                                    </c:otherwise>
                                </c:choose>
                                <div class="emptyText greenBlock">
                                    <tiles:insert definition="roundBorderLight" flush="false">
                                        <tiles:put name="color" value="greenBold"/>
                                        <tiles:put name="data" value="${emptyMsg}"/>
                                    </tiles:insert>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </tiles:put>
        </tiles:insert>
        <c:if test="${not empty form.otherCards}">
            <div id="another-cards">
                <tiles:insert definition="mainWorkspace" flush="false">
                    <c:set var="cardCount" value="${phiz:getClientProductLinksCount('CARD')}"/>
                    <tiles:put name="title">
                        ��������� �����
                        (<a href="${phiz:calculateActionURL(pageContext, "/private/cards/list.do")}" class="blueGrayLink">�������� ��� ${cardCount}</a>)
                    </tiles:put>
                    <tiles:put name="data">
                        <div class="another-items">
                            &nbsp;
                            <c:forEach items="${form.otherCards}" var="others">
                                <div class="another-container">
                                    <c:choose>
                                        <c:when test="${not empty others.card.cardLevel and others.card.cardLevel == 'MQ'}">
                                            <c:set var="imgSrc" value="mq" />
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="imgSrc" value="${phiz:getCardImageCode(others.description)}" />
                                        </c:otherwise>
                                    </c:choose>

                                    <a href="${cardInfoUrl}${others.id}">
                                        <img src="${skinUrl}/images/cards_type/icon_cards_${imgSrc}32.gif" alt="${others.description}"/>
                                    </a>
                                    <a href="${cardInfoUrl}${others.id}" class="another-name decoration-none">
                                        <bean:write name="others" property="name"/>
                                    </a>
                                    <span class="another-type uppercase">
                                        <c:set var="virtual" value="${others.card.virtual}"/>
                                        <c:choose>
                                            <c:when test="${others.main  && !virtual}">��������</c:when>
                                            <c:when test="${!others.main && !virtual}">��������������</c:when>
                                            <c:otherwise>�����������</c:otherwise>
                                        </c:choose>
                                    </span>

                                    <div class="another-number">
                                        <a href="${cardInfoUrl}${others.id}" class="another-number">${phiz:getCutCardNumber(others.number)}</a>
                                        <c:set var="state" value="${others.card.cardState}"/>
                                        <c:set var="className">
                                            <c:if test="${state eq 'blocked' or state eq 'delivery'}">
                                                red
                                            </c:if>
                                        </c:set>

                                        <span class="${className}">
                                            <span class="prodStatus status" style="font-weight:normal;">
                                                <c:if test="${not empty className}">
                                                    <nobr>(${state.description})</nobr>
                                                </c:if>
                                            </span>
                                        </span>
                                    </div>
                                </div>
                            </c:forEach>
                            &nbsp;
                            <div class="clear"></div>
                        </div>
                    </tiles:put>
                </tiles:insert>
            </div>
        </c:if>
    </tiles:put>
</tiles:insert>
</html:form>
