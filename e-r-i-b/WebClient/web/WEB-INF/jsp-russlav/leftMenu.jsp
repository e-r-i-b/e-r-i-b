<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>

<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<script type="text/javascript">

function setCookie(elem, state)
{
  var expiry = new Date();
	   expiry.setTime(expiry.getTime() + 24*60*60*1000);
  document.cookie = elem + "=" + state + "; path=/; expires=" + expiry.toGMTString();
}

function blockNoneSubMenu(subMenu)
{
	if (document.getElementById(subMenu).style.display == "block"){
		document.getElementById(subMenu).style.display = "none";
		setCookie(subMenu, "none");
	}
	else {
		document.getElementById(subMenu).style.display = "block";
		setCookie(subMenu, "block");
	}
}

function GetCookie (name) {
     var arg = name + "=";
     var alen = arg.length;
     var clen = document.cookie.length;
     var endstr = 0;
     var i = 0;
     while (i < clen) {
        var j = i + alen;
        if (document.cookie.substring(i, j) == arg){
             endstr = document.cookie.indexOf (";", j);
             if (endstr == -1){
                  endstr = document.cookie.length;
             }
             return unescape(document.cookie.substring(j, endstr));
        }
        i = document.cookie.indexOf(" ", i) + 1;
        if (i == 0) { break; }
      }
      return null;
}

function loadFunc()
{
	var itemMenu = "${mainmenu}${submenu}";
    <%--alert("${mainmenu}${submenu}");--%> //alert, � ������� �������� ����� ������ id ������ ����, ��� ������������ ��������

	<%--alert('${param["appointment"]}');--%>  //alert, � ������� �������� ����� ������ id ������ "������ ������� � �����"

	//��������� ������� ���������
//	if (itemMenu == "" && document.getElementById("itemMenu"))
//		itemMenu = document.getElementById("itemMenu").value;

	if (itemMenu == "ClaimsDepositOpeningClaim" || itemMenu == "ClaimsDepositClosingClaim" || itemMenu == "ClaimsBankcellLeasingClaim")
		itemMenu = "Claims";

	//��������� ���� �� ������ �� cookie
	for (var i=1; i<8; i++){
		var cookieValue = GetCookie("subMenu"+i);
		var submenu = document.getElementById('subMenu'+i);
		if (submenu){
			if (cookieValue != "" && cookieValue != null){
				submenu.style.display = cookieValue;
			}
			if (submenu.rows.length > 0){
				for (j=1; j<=submenu.rows.length; j++){
					cookieValue = GetCookie("subMenu"+i+"_"+j);
					var submenu2 = document.getElementById('subMenu'+i+"_"+j);
					if (submenu2){
						if (cookieValue != "" && cookieValue != null){
							submenu2.style.display = cookieValue;
						}
					}
				}
			}
		}
    }

	if (itemMenu != "")
	{
		var node = document.getElementById(itemMenu);
		if (node){
			if (node.parentNode.parent){
				document.getElementById(node.parentNode.parent).style.display = "block";
				var str = new String(node.parentNode.parent);
				if ((str.indexOf('_')) > 0)
					document.getElementById(str.substr(0,str.indexOf('_'))).style.display = "block";
				var nodeNavigator = navigator.appName == 'Netscape' ? node.parentNode.parentNode.parentNode.parentNode.childNodes[1] : node.parentNode.parentNode.parentNode.childNodes[0];
				nodeNavigator.innerHTML = "<img src='${imagePath}/tickSm.gif' alt='' width='8' height='12' border='0'>&nbsp;";
			}
			else{
				var nodeNavigator = navigator.appName == 'Netscape' ? node.parentNode.parentNode.parentNode.childNodes[1] : node.parentNode.parentNode.childNodes[0];
				nodeNavigator.innerHTML = "<img src='${imagePath}/tickSm.gif' alt='' width='8' height='12' border='0'>&nbsp;";
			}
		}
	}
}
</script>
		<table width="100%" height="100%" cellspacing="0" cellpadding="2">
		<tr>
			<td valign="top">
				<table  height="100%" cellspacing="0" cellpadding="2" class="RusSlavMenu" border="0">
				<tr>
					<td colspan="2" align="right" style="padding:8;"><a href="/PhizIC/private/accounts.do"><img src="${imagePath}/home.gif" alt="" width="7" height="10" border="0"></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="mailto:welcome@russlavbank.com"><img src="${imagePath}/mailLink.gif" alt="" width="10" height="6" border="0"></a></td>
				</tr>
				<tr>
					<td id="tick1" valign="top"><img src="${globalImagePath}/1x1.gif" width="10px"/></td>
					<td width="100%">
						<a id="Mails" href="/PhizIC/private/mail/list.do"><b>�����</b></a><br>
					</td>                             
				</tr>
				<tr><td id="tick2" valign="top">&nbsp;</td><td><a href="/PhizIC/private/payments/common.do" id="Payments"><b>������ ��������</b></a></td></tr>
				<tr>
					<td id="tick3" valign="top">&nbsp;</td>
					<td><a href="/PhizIC/private/claims.do" id="Claims" id="Deposits"><b>������</b></a></td>
				</tr>
				<tr>
					<td id="tick4" valign="top">&nbsp;</td>
					<td><a href="#" id="menu4" onclick="blockNoneSubMenu('subMenu4');"><b>������� �������</b></a>
				</tr>
				<tr>
					<td colspan="2">
						<table cellspacing="0" cellpadding="0" id="subMenu4" style="display:block;">
						<tr>
							<td><img src="${globalImagePath}/1x1.gif" width="10px"/></td>
							<td><li type="disc" parent="subMenu4"><a href='/PhizIC/private/payments/payment.do?form=InternalPayment' id="InternalPayment">��&nbsp;������&nbsp;�&nbsp;�������</a></li></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td><li type="disc" parent="subMenu4"><a href='/PhizIC/private/payments/payment.do?form=PurchaseSaleCurrencyPayment' id="PurchaseSaleCurrencyPayment">������/�������&nbsp;������</a></li></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td><li type="disc" parent="subMenu4"><a href='/PhizIC/private/payments/payment.do?form=RurPayment' id="RurPayment">����� ��</a></li></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td><li type="disc" parent="subMenu4"><a href='/PhizIC/private/payments/payment.do?form=CurrencyPayment' id="CurrencyPayment">����������� ������</a></li></td>
						</tr>
					    <tr>
							<td>&nbsp;</td>
							<td><li type="disc" parent="subMenu4"><a href='/PhizIC/private/payments/payment.do?form=ContactPayment' id="ContactPayment"><nobr>������ ��� ��������</nobr><br>����� ����������</a></li></td>
						</tr>
						</table>
					</td>
				</tr>

				<tr>
					<td id="tick5" valign="top">&nbsp;</td>
					<td>
						<a id="PaymentsGoodsAndServicesPayment" href="#" onclick="blockNoneSubMenu('subMenu5');"><b><nobr>������ �����/�������</nobr></b></a>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<table cellspacing="0" cellpadding="0" id="subMenu5" style="display:block;">
						<tr>
							<td><img src="${globalImagePath}/1x1.gif" width="10px"/></td>
							<td><li type="disc" parent="subMenu5"><a href='/PhizIC/private/payments/payment.do?form=GKHPayment' id='GKHPayment'>������ ����� ���</a></li></td>
						</tr>
						<tr>
							<td><img src="${globalImagePath}/1x1.gif" width="10px"/></td>
							<td><li type="disc" parent="subMenu5"><a href='/PhizIC/private/payments/forms/GoodsAndServicesPayment.do?form=GoodsAndServicesPayment&appointment=cellular-communication' id='GoodsAndServicesPayment/cellular-communication'>������� �����</a></li></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td><li type="disc" parent="subMenu5"><a href='/PhizIC/private/payments/forms/GoodsAndServicesPayment.do?form=GoodsAndServicesPayment&appointment=credit-repayment' id='GoodsAndServicesPayment/credit-repayment'>��������� �������</a></li></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td><li type="disc" parent="subMenu5"><a href='/PhizIC/private/payments/forms/GoodsAndServicesPayment.do?form=GoodsAndServicesPayment&appointment=inet-connection' id='GoodsAndServicesPayment/inet-connection'>��������</a></li></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td><li type="disc" parent="subMenu5"><a href='/PhizIC/private/payments/forms/GoodsAndServicesPayment.do?form=GoodsAndServicesPayment&appointment=satellite-connection' id='GoodsAndServicesPayment/satellite-connection'>����������� �����/TV</a></li></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td onclick="blockNoneSubMenu('subMenu5_1')">
								<a href="#"><li type="disc"><b>���������</b></li></a>
								<table cellpadding="0" cellspacing="0" style="display:none;" id="subMenu5_1">
								<tr>
									<td><img src="${globalImagePath}/1x1.gif" width="10px"/></td>
									<td><li type="square" parent="subMenu5_1"><a href='/PhizIC/private/payments/forms/GoodsAndServicesPayment.do?form=GoodsAndServicesPayment&appointment=ip-telephony' id='GoodsAndServicesPayment/ip-telephony'>IP ���������</a></li></td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td><li type="square" parent="subMenu5_1"><a href='/PhizIC/private/payments/forms/GoodsAndServicesPayment.do?form=GoodsAndServicesPayment&appointment=telephony' id='GoodsAndServicesPayment/telephony'>������ ���������</a></li></td>
								</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td onclick="blockNoneSubMenu('subMenu5_2')">
								<a href="#"><li type="disc"><b>��������(������)</b></li></a>
								<table cellpadding="0" cellspacing="0" style="display:none;" id="subMenu5_2">
								<tr>
									<td><img src="${globalImagePath}/1x1.gif" width="10px"/></td>
									<td><li type="square" parent="subMenu5_2"><a href='/PhizIC/private/payments/forms/GoodsAndServicesPayment.do?form=GoodsAndServicesPayment&appointment=game-portals' id='GoodsAndServicesPayment/game-portals'>������� �������</a></li></td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td><li type="square" parent="subMenu5_2"><a href='/PhizIC/private/payments/forms/GoodsAndServicesPayment.do?form=GoodsAndServicesPayment&appointment=inet-shops' id='GoodsAndServicesPayment/inet-shops'>�������� ��������</a></li></td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td><li type="square" parent="subMenu5_2"><a href='/PhizIC/private/payments/forms/GoodsAndServicesPayment.do?form=GoodsAndServicesPayment&appointment=payment-system' id='GoodsAndServicesPayment/payment-system'>��������� �������</a></li></td>
								</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td onclick="blockNoneSubMenu('subMenu5_3')">
								<a href="#"><li type="disc"><b>������</b></li></a>
								<table cellpadding="0" cellspacing="0" id="subMenu5_3" style="display:none;">
								<tr>
									<td><img src="${globalImagePath}/1x1.gif" width="10px"/></td>
									<td><li type="square" parent="subMenu5_3"><a href='/PhizIC/private/payments/forms/GoodsAndServicesPayment.do?form=GoodsAndServicesPayment&appointment=air-ticket' id='GoodsAndServicesPayment/air-ticket'>����������</a></li></td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td><li type="square" parent="subMenu5_3"><a href='/PhizIC/private/payments/forms/GoodsAndServicesPayment.do?form=GoodsAndServicesPayment&appointment=travel-agency' id='GoodsAndServicesPayment/travel-agency'>����������</a></li></td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td><li type="square" parent="subMenu5_3"><a href='/PhizIC/private/payments/forms/GoodsAndServicesPayment.do?form=GoodsAndServicesPayment&appointment=hotel-payment' id='GoodsAndServicesPayment/hotel-payment'>���������</a></li></td>
								</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td><li type="disc" parent="subMenu5"><a href='/PhizIC/private/payments/forms/GoodsAndServicesPayment.do?form=GoodsAndServicesPayment&appointment=policy-payment' id='GoodsAndServicesPayment/policy-payment'>��������� �����</a></li></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td><li type="disc" parent="subMenu5"><a href='/PhizIC/private/payments/forms/GoodsAndServicesPayment.do?form=GoodsAndServicesPayment&appointment=realty-operation' id='GoodsAndServicesPayment/realty-operation'>������������</a></li></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td><li type="disc" parent="subMenu5"><a href='/PhizIC/private/payments/forms/GoodsAndServicesPayment.do?form=GoodsAndServicesPayment&appointment=other-payment' id='GoodsAndServicesPayment/other-payment'>������</a></li></td>
						</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td style="padding-left:4;width:10;" id="tick6" valign="top">&nbsp;</td>
					<td><a id="ClaimsSecuritiesOperationsClaim" href="/PhizIC/private/claims/claim.do?form=SecuritiesOperationsClaim"><b>��������<br><nobr>� ������� ��������</nobr></b></a></td>
				</tr>
				<tr>
					<td style="padding-left:4;width:10;" id="tick7" valign="top">&nbsp;</td>
					<td><a href="#" id="menu7" onclick="blockNoneSubMenu('subMenu7');" href="/PhizIC/private/services.do"><b>������</b></a>
				</tr>
				<tr> 					
					<td colspan="2">
						<table cellspacing="0" cellpadding="0" id="subMenu7" style="display:block;">
						<tr>
							<td><img src="${globalImagePath}/1x1.gif" width="10px"/></td>
							<td onclick="blockNoneSubMenu('subMenu7_1')">
								<a href="#"><li type="disc"><b>��������� ��������</b></li></a>
								<table cellpadding="0" cellspacing="0" style="display:none;" id="subMenu7_1">
								<tr>
									<td><img src="${globalImagePath}/1x1.gif" width="10px"/></td>
									<td><li type="square" parent="subMenu7_1"><a id="ChangePassword" href='/PhizIC/private/service.do'>������� ������</a></li></td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td><li type="square" parent="subMenu7_1"><a id="ServicesClient" href='/PhizIC/private/notification/events.do'>��������� ����������</a></li></td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td><li type="square" parent="subMenu7_1"><a id="Activecard" href='/PhizIC/private/activatecard.do'>������������ �����</a></li></td>
								</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td onclick="blockNoneSubMenu('subMenu7_2')">
								<a href="#"><li type="disc"><b>�����������</b></li></a>
								<table cellpadding="0" cellspacing="0" id="subMenu7_2" style="display:none;">
								<tr>
									<td><img src="${globalImagePath}/1x1.gif" width="10px"/></td>
									<td><li type="square" parent="subMenu7_2"><a id="certificationList" href='/PhizIC/private/certification/certificate/list.do'>�����������</a></li></td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td><li type="square" parent="subMenu7_2"><a id="DemandList" href='/PhizIC/private/certification/list.do'>������ �� ����������</a></li></td>
								</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td onclick="blockNoneSubMenu('subMenu7_3')">
								<a href="#"><li type="disc"><b>�����������</b></li></a>
								<table cellpadding="0" cellspacing="0" id="subMenu7_3" style="display:none;">
								<tr>
									<td><img src="${globalImagePath}/1x1.gif" width="10px"/></td>
									<td><li type="square" parent="subMenu7_3"><a id="ReceiversDictionary" href='/PhizIC/private/receivers/paymentReceivers.do'>���������� �����������</a></li></td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td><li type="square" parent="subMenu7_3"><a id="Users" href='/PhizIC/private/receivers.do'>���������� Contact</a></li></td>
								</tr>
								</table>
							</td>
						</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td style="height:100%;" colspan="2">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="2"><img src="${imagePath}/underLine.gif" alt="" width="100%" height="3" border="0"></td>
				</tr>
				<tr>
					<td class="infoBank" colspan="2">
						������, 119049, �.������,<br>
						��.�������, �.14, ���.2<br>
												<br>
						���.: (495) 237-1919<br>
						����: (495) 232-0295<br>
						<a href="#" style="font-size:8pt;">welcome@russlavbank.com</a>
					</td>
				</tr>
				</table>
			</td>
		</tr>
		</table>
<script type="text/javascript">
	loadFunc();
</script>