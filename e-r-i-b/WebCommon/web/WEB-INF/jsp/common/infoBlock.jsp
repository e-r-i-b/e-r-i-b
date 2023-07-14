<%--
  Created by IntelliJ IDEA.
  User: Egorova
  Date: 11.09.2008
  Time: 18:09:23
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tiles:importAttribute/>

<%--
Информационный блок, который отображается в левом меню

	image   - картинка блока
	data    - содержание блока
--%>

<div class="clientCardInfo">
    <div class="clientPhoto float">
        <img src="${skinUrl}/images/${image}" alt="" border="0"/>
    </div>
    <div class="clientCardData">
        ${data}
    </div>
    <div class="clear"></div>
</div>
<div class="clear"></div>