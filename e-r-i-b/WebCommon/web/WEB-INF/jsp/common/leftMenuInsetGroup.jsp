<%--
  Created by IntelliJ IDEA.
  User: Egorova
  Date: 15.09.2008
  Time: 13:10:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>
<%--

	ѕараметры
	enabled     - ссылка активна если true  (Ќе используетс€ сейчас)
	action      - ссылка по которой осуществл€етс€ переход

	name        - id группы
    data        - перечисление подменю

	text      - текст отображаемый в ссылке
	title     - всплывающа€ подсказка
--%>
<div class="subMInactiveInset subMInsetGroup">

	<div class="haveSubMenuLevel2">
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
	<script type="text/javascript">initLeftMenu(${name});</script>
</div>