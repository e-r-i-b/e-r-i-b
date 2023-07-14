 // поле подлежащее проверке
function ChkFld(_form,_id,_name,_type,_isReq,_minLen,_maxLen,_minVal,_maxVal)
{
  this.frm =_form;             //форма
  this.id=_id;                 //идентификатор пол€
  this.userName=_name;         //название пол€ дл€ пользовател€
  this.type=_type;             //тип пол€
  this.minLen=_minLen;         //минимальна€ длина
  this.maxLen=_maxLen;         //максимальна€ длина
  this.minVal=_minVal;         //минимальое значение
  this.maxVal=_maxVal;         //максимальное значение
  this.isReq =_isReq;          //признак об€зательности
  this.SetErr= fnSetErr;       //сообщение об ошибке заполнени€ пол€
  this.Check = fnCheckField;   //функци€ проверки правильности задани€ пол€
  this.isEmpty=fnFieldEmpty;   // проверка заполнено ли поле
}
function fnSetErr(msg) //сообщение об ошибке заполнени€ пол€
{
  alert ("ѕоле \"" + this.userName + "\":\n" + msg);
  try
  {document.getElementById(this.id).focus();}
  catch (e){}
  return false;
}

// проверка заполнено ли поле
function fnFieldEmpty()
{
  var f= document.getElementById(this.id).value;
  switch ( this.type)
  {
   case 'money':
         return ((f=="") || (parseFloat(f)==0));
   default:
         return (f == "");
  }
}
function fnCheckField ()  //функци€ проверки правильности задани€ пол€
{
                  //длина и тип значени€
  var f, d, s;
  f = document.getElementById (this.id);
  if ( this.isEmpty()) return true;
  if ( this.minLen != null ) if ( f.value.length < this.minLen) return this.SetErr("длина пол€ не должна быть меньше " + this.minLen + " симв.");
  if ( this.maxLen != null ) if ( f.value.length > this.maxLen) return this.SetErr("длина пол€ не должна превышать " + this.maxLen + " симв.");
  switch ( this.type)
  {
     case 'money': // в поле только цифры и точка
          if ( f.value==".") { f.value=""; break; }
          if ( !StrCheck("0123456789.", f.value)) return ( this.SetErr("может содержать только цифры и точку."));
          f.value = formatFloat(f.value,2);
          break;
     case 'date': //задана корректна€ дата
          d =new CDate(f.value);
          if ( d.rc != 0) return this.SetErr("указана неверна€ дата");
          if ( f.value.length < 10 ) f.value = formatDate(d.dt);
          break;
     case 'number':  // в поле только цифры
          if ( !StrCheck("0123456789", f.value)) return ( this.SetErr("может содержать только цифры."));
          break;
     case 'string':  // произвольна€ строка
    	  s = f.value.toUpperCase();
          if ( s.indexOf( '</SCRIPT' ) >= 0 ) return (this.SetErr("ѕоле содержит недопустимый текст."));
      default:
  }
  if ( this.minVal != null ) if ( f.value < this.minVal) return this.SetErr("значение пол€ не должно быть меньше " + this.minVal);
  if ( this.maxVal != null ) if ( f.value > this.maxVal) return this.SetErr("значение пол€ не должно превышать " + this.maxVal);
  return true;
}

// форма пол€ которой надо провер€ть
function ChkForm( _oForm, _isCheckReq, _isSaveIncomplete)
{
  this.obj = _oForm;    // объект документа - форма
  this.flds=new Array();// пол€ формы
  this.isCheckReq = _isCheckReq;// признак - провер€ть на наличие об€зат. полей или нет
  this.Add = fnAddField;// добавить поле в форму
  this.Check = fnCheckForm; //проверить все пол€ формы
  this.CheckFld= fnCheckFormField;//проверить поле формы
  this.isSaveIncomplete = _isSaveIncomplete; //сохран€ть при наличии незаполненных об€зат. полей или нет
}
// добавить поле в форму
function fnAddField(_id,_name,_type,_isReq,_minLen,_maxLen,_minVal,_maxVal)
{
  var i;
  var of= new  ChkFld(this.obj,_id,_name,_type,_isReq,_minLen,_maxLen,_minVal,_maxVal);
  for (i=0;i<this.flds.length;i++)
     if ( this.flds[i].id==_id )
     {
       this.flds[i]=of;
       return;
     }
     this.flds=this.flds.concat(of);
}

// проверка полей формы (заполнены правильно и заданы об€зательные пол€ )
function fnCheckForm()
{
  var i, rc=true, requiedFlds=new Array(), requiedFldsNames;
  var state =  document.getElementById('checkState');
  var firstIdUnderflow=null;
  for ( i=0;i<this.flds.length;i++)
  {
    if (!this.flds[i].Check()) return false; // ошибка заполнени€ пол€
    if (this.flds[i].isEmpty() && this.flds[i].isReq )
    {
        if (firstIdUnderflow==null) firstIdUnderflow=i;
        requiedFlds = requiedFlds.concat(this.flds[i].userName);
    }
  }
  if ( state != null) state.value = "¬ведена";
  if ( this.isCheckReq && (requiedFlds.length > 0 )) // есть незаполненные об€зательные пол€
  {
    if ( state != null) state.value = "ѕодготовка";
    requiedFldsNames = "Ќе заполнены об€зательные пол€:\n\n" + requiedFlds.join( "\n");
    if ( this.isSaveIncomplete) {
      document.getElementById(this.flds[firstIdUnderflow].id).scrollIntoView();
      document.getElementById(this.flds[firstIdUnderflow].id).focus();
      rc =  window.confirm(requiedFldsNames + "\n\n—охранить документ как частично заполненный?");
     }
    else {
      alert (requiedFldsNames );
      rc=false;
    }
  }
  return rc;
}
// проверить поле формы
function fnCheckFormField(_id)
{
 var i;
 for (i=0;i<this.flds.length;i++)
   {
     if ( this.flds[i].id==_id ) return this.flds[i].Check();
   }
 return true; // поле не найдено
}

 var oForm;