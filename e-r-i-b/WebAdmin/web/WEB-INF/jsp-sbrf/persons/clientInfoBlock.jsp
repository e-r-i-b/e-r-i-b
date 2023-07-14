<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<tiles:importAttribute/>

<script type="text/javascript">
function deleteClientAvatar()
{
    var params = "operation=button.deletePersonAvatar&loginId=" + ${form.fields.loginId};
    ajaxQuery(params, "${phiz:calculateActionURL(pageContext, "/private/async/persons/editPersonAvatar")}", function(data){location.reload();}, null, false);
    $('.avatarContainer:hover .grayProfileButton').css('display', 'none');
}
</script>

<div class="float avatarContainer">
    <tiles:insert definition="userImageTemplate" flush="false">
        <tiles:put name="selector" value="AVATAR"/>
        <c:if test="${phiz:impliesOperation('ViewPersonAvatarOperation', 'ViewPersonAvatar') || phiz:impliesOperation('EditPersonAvatarOperation', 'EditPersonAvatar')}">
            <tiles:put name="imagePath" value="${phiz:avatarPath('AVATAR', form.avatarInfo)}"/>
        </c:if>
        <tiles:put name="imgStyle" value="avatarImage"/>
    </tiles:insert>
    <c:if test="${phiz:impliesOperation('EditPersonAvatarOperation', 'EditPersonAvatar')}">
        <c:if test="${form.hasAvatar}">
            <tiles:insert definition="profileButton" flush="false">
                <tiles:put name="commandText" value="Удалить фото"/>
                <tiles:put name="viewType" value="grayProfileButton"/>
                <tiles:put name="onclick" value="deleteClientAvatar();"/>
            </tiles:insert>
        </c:if>
    </c:if>
</div>

<div class="personInfo float">
    <span class="clientInfoFIO">
        ${person.fullName}<br/>
    </span>
    <c:set var="login"  value="${person.login}"/>
    <c:set var="isNewOrTemplate" value="${person.status == 'T'}"/>
    <c:set var="isCancelation" value="${person.status == 'W'}"/>
    <c:set var="isErrorCancelation" value="${person.status == 'E'}"/>
    <c:set var="isSignAgreement" value="${empty person or person.status == 'S'}"/>
    <span class="clientActivityState">
        <c:choose>
            <c:when test="${isNewOrTemplate}">
                <img src="${imagePath}/iconSm_activate.gif" width="12px" height="12px" alt="" border="0"/>&nbsp;Подключение
            </c:when>
            <c:when test="${not empty login.blocks and not isCancelation and not isErrorCancelation and not isSignAgreement}">
                <img src="${imagePath}/iconSm_lock.gif" width="12px" height="12px" alt="" border="0"/>&nbsp;Заблокирован
            </c:when>
            <c:when test="${isCancelation}">
                Прекращение обслуживания
            </c:when>
            <c:when test="${isErrorCancelation}">
                Ошибка расторжения
            </c:when>
            <c:when test="${isSignAgreement}">
                Подписание заявления
            </c:when>
            <c:otherwise>
                Aктивный
            </c:otherwise>
        </c:choose>
    </span>
    <div class="clientInfoRow">
        <span class="clientInfoLabel"><bean:message key="label.mobilePhone" bundle="personsBundle"/>:</span><br/>
        <span class="clientInfoText"><bean:write name="form" property="field(mobilePhone)"/></span>
    </div>
    <div class="clientInfoRow">
        <span class="clientInfoLabel"><bean:message key="label.birthDay" bundle="personsBundle"/>:</span><br/>
        <span class="clientInfoText"><bean:write name="form" property="fields.birthDay" format="dd.MM.yyyy"/></span>
    </div>
    <div class="clientInfoCaption"><bean:message key="label.last.logon.information" bundle="personsBundle"/>:</div>
    <div id="additionalMigrationState"><bean:message key="label.state.migration.${additionalMigrationState}" bundle="personsBundle"/></div>
    <table>
        <tr>
            <td><span class="clientInfoLabel"><bean:message key="label.entered.date" bundle="personsBundle"/></span></td>
            <td><span class="clientInfoText"><fmt:formatDate value="${person.login.logonDate.time}" pattern="dd.MM.yyyy HH:mm:ss"/></span></td>
        </tr>
        <tr>
            <td><span class="clientInfoLabel"><bean:message key="label.entered.ip" bundle="personsBundle"/></span></td>
            <td><span class="clientInfoText"><c:out value="${person.login.ipAddress}"/></span></td>
        </tr>
        <tr>
            <c:choose>
                <c:when test="${not empty person.login.lastLogonType && not empty person.login.lastLogonParameter}">

                    <c:set var="autentificationType">
                        <c:choose>
                            <c:when test="${person.login.lastLogonType == 'ATM'}">cardNumber</c:when>
                            <c:when test="${person.login.lastLogonType == 'CSA'}">login</c:when>
                            <c:when test="${person.login.lastLogonType == 'TERMINAL' or person.login.lastLogonType == 'MAPI'}">connector</c:when>
                        </c:choose>
                    </c:set>

                    <td><span class="clientInfoLabel"><bean:message key="label.entered.autentification.type" bundle="personsBundle"/></span></td>
                    <td>
                        <span class="clientInfoText">
                            <c:out value="${autentificationType}"/>&nbsp;:&nbsp;
                            <c:choose>
                                <c:when test="${autentificationType == 'cardNumber'}">
                                    <c:out value="${phiz:getCutCardNumber(person.login.lastLogonParameter)}"/>
                                </c:when>
                                <c:otherwise>
                                    <c:out value="${person.login.lastLogonParameter}"/>
                                </c:otherwise>
                            </c:choose>
                        </span>
                    </td>
                </c:when>
                <c:otherwise>
                    <td><span class="clientInfoLabel"><bean:message key="label.entered.autentification.type" bundle="personsBundle"/></span></td>
                    <td>
                        <span class="clientInfoText">
                            <c:if test="${not empty person.login.csaUserAlias}">
                                <bean:message key="label.login" bundle="personsBundle"/>&nbsp;<c:out value="${person.login.csaUserAlias}"/>
                            </c:if>
                        </span>
                    </td>
                </c:otherwise>
            </c:choose>
        </tr>
    </table>

    <div>
        <tiles:insert definition="clientFormAdditionalButton" flush="false">
            <tiles:put name="commandTextKey" value="button.new.search"/>
            <tiles:put name="commandHelpKey" value="button.new.search"/>
            <tiles:put name="bundle" value="personsBundle"/>
            <tiles:put name="availableOperation" value="GetPersonsListOperation"/>
            <tiles:put name="availableService" value="PersonsViewing"/>
            <tiles:put name="action" value="/persons/list.do"/>
        </tiles:insert>
    </div>
</div>
<div class="clear"></div>