<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<html:form action="/guest/sberbankForEveryDay" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="hasAccount" value="${phiz:hasGuestAnyAccount()}"/>
    <c:set var="definition" value="${hasAccount ? 'guestPage' : 'guestAfterClaim'}" />
    <c:choose>
        <c:when test="${!phiz:isLogOnGuestProfile()}">
            <%--если клиент не зарегистрирован, показываем форму регистрации--%>
            <tiles:insert definition="guestAfterClaim">
                <tiles:put name="contentHeader">
                    <bean:message key="label.title" bundle="sbnkdBundle"/>
                </tiles:put>
                <tiles:put name="topMessage">
                    <c:if test="${form.successConfirmMessage != null}">
                        <tiles:insert definition="roundBorderLight" flush="false">
                            <tiles:put name="color" value="orangeLight"/>
                            <tiles:put name="data">
                                <div class="notice ">
                                    <div class="noticeTitle">Заявка успешно отправлена в Банк</div>
                                        ${form.successConfirmMessage}
                                </div>
                            </tiles:put>
                        </tiles:insert>
                    </c:if>
                </tiles:put>
                <tiles:put name="documentID" value="${form.id}"/>
                <tiles:put name="documentType" value="IssueCard"/>
                <tiles:put name="content">
                    <div class="page_content">
                        <%@ include file="viewClaim.jsp" %>
                    </div>
                </tiles:put>
            </tiles:insert>
        </c:when>
        <c:otherwise>
            <tiles:insert definition="guestPage">
                <tiles:put name="mainMenuType" value="guestMainMenu"/>
                <tiles:put name="mainmenu" value="Index"/>

                <tiles:put name="data" type="string">
                    <div class="page_content">
                        <c:if test="${form.successConfirmMessage != null}">
                            <tiles:insert definition="roundBorderLight" flush="false">
                                <tiles:put name="color" value="orangeLight"/>
                                <tiles:put name="data">
                                    <div class="notice ">
                                        <div class="noticeTitle">Заявка успешно отправлена в Банк</div>
                                            ${form.successConfirmMessage}
                                    </div>
                                </tiles:put>
                            </tiles:insert>
                        </c:if>
                        <div class="title_common head_title margin_top_40 margin_bottom_40"><bean:message key="label.title" bundle="sbnkdBundle"/></div>
                        <div class="clear"></div>
                        <%@ include file="viewClaim.jsp" %>

                        <div class="float">
                            <span id="backToServicesButton">
                                <tiles:insert definition="clientButton" flush="false">
                                    <tiles:put name="commandTextKey" value="button.backToMain"/>
                                    <tiles:put name="commandHelpKey" value="button.backToMain"/>
                                    <tiles:put name="bundle"         value="sbnkdBundle"/>
                                    <tiles:put name="viewType"       value="darkGrayButton"/>
                                    <tiles:put name="action"         value="/guest/index.do"/>
                                    <tiles:put name="image"          value="back-to-catalog.png"/>
                                    <tiles:put name="imageHover"     value="back-to-catalog-hover.png"/>
                                    <tiles:put name="imagePosition"  value="left"/>
                                </tiles:insert>
                            </span>
                        </div>
                    </div>
                </tiles:put>
            </tiles:insert>
        </c:otherwise>
    </c:choose>


</html:form>
