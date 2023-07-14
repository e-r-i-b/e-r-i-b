function dropdown_menu_hack(el)
{
    if(el.runtimeStyle.behavior.toLowerCase()=="none"){return;}
	el.runtimeStyle.behavior="none";

	el.ondblclick = function(e)
                        {
                            e.returnValue=false;
                            return false;
                        };

	function showMenu(event)
	{
		
		function selectMenu(obj)
			{				
				var o = document.createElement("option");
				o.value =  el.contentOptions[obj.selectedIndex].value;
				o.innerHTML = obj.innerHTML;			
				while(el.options.length>0){el.options[0].removeNode(true);}
				el.appendChild(o);
				el.title =  o.innerHTML; 
				el.contentIndex = obj.selectedIndex  ;
				el.menu.hide();
                if (el.onchange != null)
                        el.onchange();
                
			}

        var menuObj = el.menu.document.obj
        var mb = el.menu.document.body;

		if ( el.opened != undefined && el.opened && menuObj == el)
		 {  
		 //self.focus();
         el.disabled=true;
         el.disabled=false;
         el.blur();
		 el.opened = false;
 
		 return ;
		 }

        el.opened = true;
        el.menu.document.obj = el;
		el.menu.show(0 , el.offsetHeight , 10,  10, el);

		
		mb.innerHTML = el.selectHTML;
        mb.style.cssText = "border:solid 1px black;margin:0;padding:0;overflow-y:auto;overflow-x:auto;background:white;text-aligbn:center;font-family:Verdana;font-size:12px;";
        
		el.select = mb.all.tags("ul")[0];
		el.select.style.cssText="list-style:none;margin:0;padding:0;";
		mb.options = el.select.getElementsByTagName("li");
		
		for(var i=0;i<mb.options.length;i++)
		{
			mb.options[i].selectedIndex = i;
			mb.options[i].style.cssText = "list-style:none;margin:0;padding:1px 2px;cursor:pointer;white-space:nowrap;"

            mb.options[i].title = mb.options[i].innerHTML.replace(/&nbsp;/gi, " ");
			mb.options[i].innerHTML ="<nobr>" + mb.options[i].innerHTML + "</nobr>";
			mb.options[i].onmouseover = function()
				{
					if( mb.options.selected ){mb.options.selected.style.background="white";mb.options.selected.style.color="black";}
					mb.options.selected = this;
					this.style.background="#333366";this.style.color="white";
				}
			
			mb.options[i].onmouseout = function(){this.style.background="white";this.style.color="black";}
			mb.options[i].onmousedown = function(){selectMenu(this);	}
			mb.options[i].onkeydown = function(){selectMenu(this);	}
				

			if(i == el.contentIndex)
			{
				mb.options[i].style.background="#333366";
				mb.options[i].style.color="white";	
				mb.options.selected = mb.options[i];
			}
		}
	
		
		var mw = Math.max(   ( el.select.offsetWidth + 22 ), el.offsetWidth + 22  );
			 mw = Math.max(  mw, ( mb.scrollWidth+22) );
		var mh =  mb.options.length * 15  + 8 ; 
		 
		var mx = 0;
		var my = el.offsetHeight -2;
			
		var docH =   document.documentElement.offsetHeight ;
		var bottomH = docH  - el.getBoundingClientRect().bottom ; 
		mh = Math.min(mh, Math.max(( docH - el.getBoundingClientRect().top - 50),100)		);
		if(( bottomH < mh) )
		{
			
			if( Math.max( (bottomH - 12),10) <100 ) 
			{
				my = -1 * mh ;

			}		
		}

		self.focus(); 
		
		el.menu.show( mx , my ,  mw, mh , el);

		if(mb.options.selected)
		{
			mb.scrollTop = mb.options.selected.offsetTop;
		}
		
		window.onresize = function(){el.menu.hide() };
        if (el.scroll == undefined)
               el.scroll = window.onscroll
        var scroll = el.scroll;
        window.onscroll = function(){ if (scroll!=null) scroll(); el.menu.hide() };
	};

	function switchMenu()
	{
		if(event.keyCode)
		{
			if(event.keyCode==40){ el.contentIndex++ ;}
			else if(event.keyCode==38){ el.contentIndex--; }
		}
		else if(event.wheelDelta )
		{
			if (event.wheelDelta >= 120)
			el.contentIndex++ ;
			else if (event.wheelDelta <= -120)
			el.contentIndex-- ;
		}else{return true;}

		if( el.contentIndex > (el.contentOptions.length-1) ){ el.contentIndex =0;}
		else if (el.contentIndex<0){el.contentIndex = el.contentOptions.length-1 ;}

		var o = document.createElement("option");
			 o.value = el.contentOptions[el.contentIndex].value;
			 o.innerHTML = el.contentOptions[el.contentIndex].text;
			 while(el.options.length>0){el.options[0].removeNode(true);}
			 el.appendChild(o);
			 el.title =  o.innerHTML; 
	}
	
	if(dropdown_menu_hack.menu ==null)
	{
		dropdown_menu_hack.menu =  window.createPopup();
		document.attachEvent("onkeydown",dropdown_menu_hack.menu.hide);

	}
	el.menu = dropdown_menu_hack.menu ;
	el.contentOptions = new Array();
	el.contentIndex = el.selectedIndex;
	el.contentHTML = el.outerHTML;

    var t = el.contentHTML;
		t = t.replace(/<select/gi,'<ul');
		t = t.replace(/<option/gi,'<li');
		t = t.replace(/<\/option/gi,'</li');
		t = t.replace(/<\/select/gi,'</ul');
		t = t.replace(/value=/gi,'optionValue=');
    el.selectHTML = t;

	for(var i=0;i<el.options.length;i++)
	{	
		el.contentOptions [el.contentOptions.length] = 
		{
			"value": el.options[i].value,
			"text": el.options[i].innerHTML
		}

		if(!el.options[i].selected){el.options[i].removeNode(true);i--;};
	}

    el.style.width = el.clientWidth+"px";
	el.menu.document.obj = null;
	el.onkeydown = switchMenu;
	el.onclick = function () { showMenu(this); return false; }
	el.ondblclick = showMenu;
	el.onmousewheel= switchMenu;
    el.opened = false;
    el.onmouseover = function () { el.opened = el.menu.isOpen; }
}

// –еализаци€ инициализации хака, только после полной загрузки DOM, дл€ предотвращени€ падени€ IE
function initDropdownMenuHack(el)
{
    doOnLoad(
            function (obj) {
              if (window.dropdown_menu_hack!=null) window.dropdown_menu_hack(obj)
            }, el
            );
}