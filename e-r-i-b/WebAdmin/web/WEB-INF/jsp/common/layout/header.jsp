<%--
  User: Zhuravleva
  Date: 15.11.2006
  Time: 11:55:51
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--<tiles:importAttribute name="headerGroup" ignore="true"/>--%>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="helpLink" value="${phiz:calculateActionURL(pageContext,'/help.do?id=')}${$$helpId}"/>

<div class="NewHeader">
    <div class="Logo">
        <a class="logoImage logoImageText" onclick="return redirectResolved();" href="${phiz:calculateActionURL(pageContext, "/index.do")}">
            <img alt="" src="${skinUrl}/images/logoHeader.gif"/>
        </a>
    </div>
    <div class="timeBlock">
        <div class="hourBlock" id="hours">
            <script type="text/javascript">
                obj_hours=document.getElementById("hours");
                function wr_hours()
                {
                    time=new Date();

                    time_min=time.getMinutes();
                    time_hours=time.getHours();
                    time_wr=((time_hours<10)?"0":"")+time_hours;
                    time_wr+=":";
                    time_wr+=((time_min<10)?"0":"")+time_min;
                    obj_hours.innerHTML=time_wr;
                }
                wr_hours();
                setInterval("wr_hours();",1000);
            </script>

        </div>
        <script type="text/javascript">
            var dateStr = time.getDate() + ' ' + monthToStringByNumber(time.getMonth()) + ' ' + (time.getFullYear());
            document.write(dateStr);
        </script>
    </div>
</div>
<div class="topLineContainer">
    <div class="UserInfo">
        <div class="table-row">
            <div class="feedbackItems">
                <a id="helpLink" class="onlineHelp" href="javascript:openHelp('${helpLink}');">
                    <span>Помощь</span>
                </a>
            </div>
            <div class="employeeInfo">
                <c:set var="employeeInfo" value="${phiz:getEmployeeInfo()}"/>
                <c:if test="${not empty employeeInfo}">
                    <c:choose>
                        <c:when test="${employeeInfo.id==null}">
                            <a><span>admin</span></a>
                        </c:when>
                        <c:otherwise>
                            <phiz:link action="/profile/managerInfo" operationClass="EditPersonalManagerInformationOperation" serviceId="SelfPersonalManagerInformationManagement">
                                <span class="word-wrap">${employeeInfo.surName} ${employeeInfo.firstName} ${employeeInfo.patrName} </span>
                                <c:if test="${phiz:needUpdateManagerInfo()}">
                                    <img width="16" height="14" src="${imagePath}/importantIco.gif" title="Пожалуйста, заполните Ваши персональные данные."/>&nbsp;
                                </c:if>
                            </phiz:link>

                        </c:otherwise>
                    </c:choose>
                </c:if>
                <c:if test="${phiz:isMultiblockMode()}">
                    <div class="clear"></div>
                    <div class="multiblock">
                        <span class="float">${phiz:getCurrentNode().name} блок</span>
                        <span class="float">
                            <phiz:link action="javascript:win.open('chooseNode');" operationClass="SelfChangeNodeOperation" loadTime="false" styleClass="changeMultiblock">
                                (<em>изменить</em>)
                            </phiz:link>
                        </span>
                        <%@ include file="/WEB-INF/jsp/node/chooseNodeWindow.jsp"%>
                    </div>
                 </c:if>
            </div>
            <html:link action="/logoff" styleClass="saveExit" title="Выход" >
                <span>Выход&nbsp;</span>
                <div id="exit"></div>
            </html:link>
        </div>
    </div>
</div>


