<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tiles:useAttribute name="additionClass"/>
<div class="b-btn next_btn<c:if test='${not empty additionClass}'> ${additionClass}</c:if>" id="Submit">
    <tiles:useAttribute name="text"/>
    <span class="btn_text">${text}</span>
    <i class="btn_crn"></i>
    <tiles:useAttribute name="commandKey"/>
    <tiles:useAttribute name="tabindex"/>
    <input class="btn_hide" name="operation" value="${commandKey}" type="submit"  <c:if test="${not empty tabindex}">tabindex="${tabindex}"</c:if> />
</div><!-- // b-btn -->