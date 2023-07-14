<%--
  Created by IntelliJ IDEA.
  User: lepihina
  Date: 13.12.2011
  Time: 13:52:54
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tiles:importAttribute/>

<c:set var="imagePath" value="${skinUrl}/images"/>
<html:form action="/private/userprofile/otpRestriction">
    <c:set var="form" value="${phiz:currentForm(pageContext)}" scope="request"/>
    <c:if test="${not empty form.confirmableObject}">
        <c:set var="confirmRequest" value="${phiz:currentConfirmRequest(form.confirmableObject)}"
               scope="request"/>
    </c:if>

    <tiles:insert definition="userProfile">
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
                <tiles:put name="name" value="Настройка безопасности"/>
                <tiles:put name="action" value="/private/userprofile/accountSecurity.do"/>
            </tiles:insert>
            <tiles:insert definition="breadcrumbsLink" flush="false">
                <tiles:put name="name" value="Установка ограничений на получение одноразовых паролей"/>
                <tiles:put name="last" value="true"/>
            </tiles:insert>
        </tiles:put>
        <tiles:put name="data" type="string">
            <div id="profile">
                <tiles:insert definition="mainWorkspace" flush="false">
                    <tiles:put name="title" value="Настройки"/>
                    <tiles:put name="data">
                        <tiles:insert definition="userSettings" flush="false">
                            <tiles:put name="data">
                                <c:set var="selectedTab" value="securetySettings"/>
                                <%@ include file="/WEB-INF/jsp/private/userprofile/userSettingsHeader.jsp" %>
                                <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
                                <div class="payments-tabs">
                                    <c:set var="security" value="false"/>
                                    <%@ include file="securityHeader.jsp" %>
                                </div>

                                <div class="clear"></div>
                                <div><h2>Установка ограничений на получение одноразовых паролей</h2></div>
                                <div class="greenContainer picInfoBlockSysView">
                                    <div>
                                        На этой странице Вы можете ограничить печать одноразовых паролей по каждой Вашей карте, а также заблокировать уже распечатанные пароли.
                                    </div>
                                </div>
                                <div class="clear"></div>

                                <div class="securityOptions">
                                    <html:hidden property="field(unsavedData)" name="form"/>
                                    <%@ include file="cardsOTPRestriction.jsp" %>
                                </div>
                            </tiles:put>
                        </tiles:insert>
                    </tiles:put>
                </tiles:insert>
            </div>
        </tiles:put>
    </tiles:insert>

    <script type="text/javascript">
        function checkData()
        {
            var unsavedData = ensureElementByName("field(unsavedData)").value;
            if (unsavedData) return true;
            if (!isDataChanged())
            {
                addMessage("Вы не внесли никаких изменений в настройки ограничений на получение одноразовых паролей.");
                return false;
            }
            return true;
        }
    </script>

</html:form>