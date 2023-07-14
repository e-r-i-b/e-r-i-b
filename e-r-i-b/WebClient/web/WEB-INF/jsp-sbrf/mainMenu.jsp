<%--
  Created by IntelliJ IDEA.
  User: Zhuravleva
  Date: 20.01.2009
  Time: 15:47:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>

<tiles:importAttribute name="mainmenu" ignore="true"/>
<c:set var="mode" scope="request" value="${mainmenu}"/>
<tiles:importAttribute name="enabledLink" ignore="true"/>
<c:set var="enabledLink" scope="request" value="${enabledLink}"/>
<c:set var="showMoreSbol" value="${phiz:isShowMoreSbolMenuItem()}"/>
<c:set var="showMoreSbolNovelty" value="${phiz:isShowMoreSbolNovelty()}"/>
<div id="menu">
    <script type="text/javascript">
        function activeParentItem()
        {
            $('#other').addClass('active activeOther');
        }
    </script>
    <c:set var="links" value="${phiz:getMenuLink()}" scope="request"/>
    <%--Количество ненастриваемых пользователем отображаемых пунктов меню (Главная, Переводы и платежи, Больше Сбербанк Онлайн)--%>
    <c:set var="staticLinksSize">
        <c:choose>
            <c:when test="${phiz:impliesAccessScheme('Info')}">3</c:when>
            <c:otherwise>0</c:otherwise>
        </c:choose>
    </c:set>
    <%--Общее количество кнопок меню--%>
    <c:set var="linksSumSize" value="${staticLinksSize}"/>
    <c:if test="${not empty links}">
        <%--Количество настраиваемых отображаемых пунктов меню--%>
        <c:set var="linksSize" value="${phiz:size(links)}"/>
        <c:set var="linksSumSize" value="${linksSize + staticLinksSize}"/>
    </c:if>

    <table border="0" cellspacing="0" cellpadding="0" class="mainMenu">
        <tr>
            <tiles:insert definition="mainMenuInset" module="Info">
                <tiles:put name="activity" value="${mode == 'Info'}"/>
                <tiles:put name="action" value="/private/accounts"/>
                <tiles:put name="text" value="Главная"/>
                <tiles:put name="insetWidth" value="${width}"/>
                <tiles:put name="positionItem" value="firstPosition"/>
                <tiles:put name="enabledLink" value="${enabledLink}"/>
            </tiles:insert>
            <tiles:insert definition="mainMenuInset" module="Info">
                <tiles:put name="activity" value="${mode == 'Payments'}"/>
                <tiles:put name="action" value="/private/payments"/>
                <tiles:put name="isImplement" value="false"/>
                <tiles:put name="text"><bean:message key="label.mainMenu.payments" bundle="commonBundle"/></tiles:put>
                <tiles:put name="insetWidth" value="${width}"/>
                <tiles:put name="enabledLink" value="${enabledLink}"/>
                <tiles:put name="mode" value="Payments"/>
            </tiles:insert>

            <c:if test="${not empty links}">
                <c:set var="maxLettersCount" value="26" />
                <c:if test="${showMoreSbol}">
                    <c:set var="maxLettersCount" value="14" />
                </c:if>
                <c:set var="maxLettersCountWithoutOther" value="50" />
                <c:if test="${showMoreSbol}">
                    <c:set var="maxLettersCountWithoutOther" value="38" />
                </c:if>
                <c:set var="lettersCount" value="0" />
                <c:set var="linksCounter" value="0"/>
                <%--сколько линков влезет без пункта "прочее"--%>
                <c:set var="linksCountWithoutOther" value="0"/>
                <c:forEach var="link" items="${links}" end="${linksSize}">
                    <c:set var="linkLength" value="${fn:length(link.text)}"/>
                    <c:set var="lettersCount" value="${lettersCount+linkLength}"/>
                    <c:if test="${lettersCount <= maxLettersCount}">
                        <c:set var="linksCounter" value="${linksCounter+1}"/>
                    </c:if>
                    <c:if test="${lettersCount <= maxLettersCountWithoutOther - 5*linksCountWithoutOther}">
                        <c:set var="linksCountWithoutOther" value="${linksCountWithoutOther+1}"/>
                    </c:if>
                </c:forEach>
               <c:if test="${linksCountWithoutOther == linksSize}" >
                   <c:set var="linksCounter" value="${linksCountWithoutOther}" />
               </c:if>
                <c:forEach var="link" items="${links}" end="${linksCounter-1}" varStatus="linksIndex">
                    <c:set var="activity" value="${link.activity}"/>
                    <c:if test="${showMoreSbol and activity != 'Cards' and linksIndex.count == 1}">
                        <tiles:insert definition="mainMenuInset">
                            <tiles:put name="activity" value="${mode == 'moreSbol'}"/>
                            <tiles:put name="action" value="/private/connectUdbo/connectUdbo"/>
                            <tiles:put name="text"><bean:message key="label.mainMenu.moreSberbank" bundle="commonBundle"/></tiles:put>
                            <tiles:put name="insetWidth" value="250"/>
                            <tiles:put name="enabledLink" value="${enabledLink}"/>
                            <c:if test="${showMoreSbolNovelty}">
                                <tiles:put name="novelty" value="true"/>
                            </c:if>
                            <tiles:put name="mode" value="moreSbol"/>
                        </tiles:insert>
                    </c:if>
                    <tiles:insert definition="mainMenuInset">
                        <tiles:put name="activity" value="${mode == activity}"/>
                        <tiles:put name="action" value="${link.action}"/>
                        <tiles:put name="text" value="${link.text}"/>
                        <tiles:put name="insetWidth" value="${width}"/>
                        <tiles:put name="enabledLink" value="${enabledLink}"/>
                    </tiles:insert>
                    <c:if test="${showMoreSbol and activity == 'Cards' and linksIndex.count == 1}">
                        <tiles:insert definition="mainMenuInset">
                            <tiles:put name="activity" value="${mode == 'moreSbol'}"/>
                            <tiles:put name="action" value="/private/connectUdbo/connectUdbo"/>
                            <tiles:put name="text"><bean:message key="label.mainMenu.moreSberbank" bundle="commonBundle"/></tiles:put>
                            <tiles:put name="insetWidth" value="250"/>
                            <tiles:put name="enabledLink" value="${enabledLink}"/>
                            <c:if test="${showMoreSbolNovelty}">
                                <tiles:put name="novelty" value="true"/>
                            </c:if>
                            <tiles:put name="mode" value="moreSbol"/>
                        </tiles:insert>
                    </c:if>
                </c:forEach>
                <c:if test="${linksSize > linksCounter}">
                    <%--Кнопка "Другие активы"--%>
                    <td id="other">
                        <span class="menuItems">
                            <a> <span>Прочее</span></a>
                        </span>
                        <%--Выпадающее меню. Вставляем в самом начале из-за неадекватного поведения IE--%>
						<div class="relative dropMenu">
							<div id="dropDownMenu" style="display: none;" class="css3">
								<table>
									<c:forEach var="link" items="${links}" begin="${linksCounter}">
										<tr>
											<c:set var="activity" value="${mode == link.activity}"/>
											<tiles:insert definition="mainMenuInset">
												<tiles:put name="activity" value="${activity}"/>
												<tiles:put name="action" value="${link.action}"/>
												<tiles:put name="text" value="${link.text}"/>
												<tiles:put name="enabledLink" value="${enabledLink}"/>
												<tiles:put name="novelty" value="${link.novelty}"/>
												<%--Если одна из кнопок выпадающего меню выбрана, то делаем активным и Прочее.--%>
												<c:if test="${activity}">
													<script type="text/javascript">
														activeParentItem();
													</script>
												</c:if>
											</tiles:insert>
										</tr>
									</c:forEach>
								</table>
							</div>
						</div>
                    </td>
                </c:if>
            </c:if>

            <tiles:insert definition="mainMenuInset" module="Info">
                <tiles:put name="activity" value="${mode == 'Settings'}"/>
                <tiles:put name="action" value="${phiz:impliesServiceRigid('NewClientProfile') ? '/private/userprofile/accountSecurity' : '/private/userprofile/userSettings'}"/>
                <tiles:put name="insetWidth" value="55"/>
                <tiles:put name="image" value="options.gif"/>
                <tiles:put name="title" value="Настройки"/>
                <tiles:put name="positionItem" value="lastPosition"/>
                <tiles:put name="enabledLink" value="${enabledLink}"/>
            </tiles:insert>
        </tr>
    </table>
</div>

<script type="text/javascript">


    $(document).ready(function(){

        $('#other').live("mouseenter",
            function(){
                $('#dropDownMenu').css('display','block');
        });

        $('#other').live("mouseleave",
            function(){
                $('#dropDownMenu').css('display','none');
        });

    });

</script>