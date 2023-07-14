<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>
<%--
    Группа меню второго уровня, для трехуровневой иерархии.
	Параметры
	enabled     - ссылка активна если true  (Не используется сейчас)
	action      - ссылка по которой осуществляется переход
	name        - id подгруппы
	parentName  - id родителя подгруппы
    data        - перечисление подменю
	text      - текст отображаемый в ссылке
	title     - всплывающая подсказка
--%>

	<div class="haveSubMenuLevel3">
		<c:set var="path" value="${action}"/>
        <c:set var="url" value="${phiz:calculateActionURL(pageContext,path)}"/>
		<input type="hidden" id="locationSM" value="${url}">
        <div class="subMInaciveTitleBg" id="subMTitleL2_${name}">
            <div class="shadowInsetLeft">
                <span id="${name}" class="subMInsetGroupTitle"
                <c:choose>
                    <c:when test="${enabled}">
                          onclick="showHide(this.id,'${action}');"
                    </c:when>
                    <c:otherwise>
                          onclick="showHide(this.id,'');"
                    </c:otherwise>
                </c:choose>
                >${text}</span>

                <div id="subML2_${name}"
                    <c:choose>
                        <c:when test="${enabled}">
                              class="displayNone"
                        </c:when>
                        <c:otherwise>
                              class="displayBlock"
                        </c:otherwise>
                    </c:choose>
                    >
                    ${data}
                </div>
            </div>
        </div>
	</div>
	<script type="text/javascript">
        initLeftMenu(${parentName});

        var subGroup = document.getElementById("subML2_${parentName}");
        if(subGroup!=null && subGroup.className == 'displayBlock')
        {
            var title = document.getElementById('subMTitleL2_${parentName}');
            title.className = "subMAciveTitleBg";
        }
    </script>