 // ���� ���������� ��������
function ChkFld(_form,_id,_name,_type,_isReq,_minLen,_maxLen,_minVal,_maxVal)
{
  this.frm =_form;             //�����
  this.id=_id;                 //������������� ����
  this.userName=_name;         //�������� ���� ��� ������������
  this.type=_type;             //��� ����
  this.minLen=_minLen;         //����������� �����
  this.maxLen=_maxLen;         //������������ �����
  this.minVal=_minVal;         //���������� ��������
  this.maxVal=_maxVal;         //������������ ��������
  this.isReq =_isReq;          //������� ��������������
  this.SetErr= fnSetErr;       //��������� �� ������ ���������� ����
  this.Check = fnCheckField;   //������� �������� ������������ ������� ����
  this.isEmpty=fnFieldEmpty;   // �������� ��������� �� ����
}
function fnSetErr(msg) //��������� �� ������ ���������� ����
{
  alert ("���� \"" + this.userName + "\":\n" + msg);
  try
  {document.getElementById(this.id).focus();}
  catch (e){}
  return false;
}

// �������� ��������� �� ����
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
function fnCheckField ()  //������� �������� ������������ ������� ����
{
                  //����� � ��� ��������
  var f, d, s;
  f = document.getElementById (this.id);
  if ( this.isEmpty()) return true;
  if ( this.minLen != null ) if ( f.value.length < this.minLen) return this.SetErr("����� ���� �� ������ ���� ������ " + this.minLen + " ����.");
  if ( this.maxLen != null ) if ( f.value.length > this.maxLen) return this.SetErr("����� ���� �� ������ ��������� " + this.maxLen + " ����.");
  switch ( this.type)
  {
     case 'money': // � ���� ������ ����� � �����
          if ( f.value==".") { f.value=""; break; }
          if ( !StrCheck("0123456789.", f.value)) return ( this.SetErr("����� ��������� ������ ����� � �����."));
          f.value = formatFloat(f.value,2);
          break;
     case 'date': //������ ���������� ����
          d =new CDate(f.value);
          if ( d.rc != 0) return this.SetErr("������� �������� ����");
          if ( f.value.length < 10 ) f.value = formatDate(d.dt);
          break;
     case 'number':  // � ���� ������ �����
          if ( !StrCheck("0123456789", f.value)) return ( this.SetErr("����� ��������� ������ �����."));
          break;
     case 'string':  // ������������ ������
    	  s = f.value.toUpperCase();
          if ( s.indexOf( '</SCRIPT' ) >= 0 ) return (this.SetErr("���� �������� ������������ �����."));
      default:
  }
  if ( this.minVal != null ) if ( f.value < this.minVal) return this.SetErr("�������� ���� �� ������ ���� ������ " + this.minVal);
  if ( this.maxVal != null ) if ( f.value > this.maxVal) return this.SetErr("�������� ���� �� ������ ��������� " + this.maxVal);
  return true;
}

// ����� ���� ������� ���� ���������
function ChkForm( _oForm, _isCheckReq, _isSaveIncomplete)
{
  this.obj = _oForm;    // ������ ��������� - �����
  this.flds=new Array();// ���� �����
  this.isCheckReq = _isCheckReq;// ������� - ��������� �� ������� ������. ����� ��� ���
  this.Add = fnAddField;// �������� ���� � �����
  this.Check = fnCheckForm; //��������� ��� ���� �����
  this.CheckFld= fnCheckFormField;//��������� ���� �����
  this.isSaveIncomplete = _isSaveIncomplete; //��������� ��� ������� ������������� ������. ����� ��� ���
}
// �������� ���� � �����
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

// �������� ����� ����� (��������� ��������� � ������ ������������ ���� )
function fnCheckForm()
{
  var i, rc=true, requiedFlds=new Array(), requiedFldsNames;
  var state =  document.getElementById('checkState');
  var firstIdUnderflow=null;
  for ( i=0;i<this.flds.length;i++)
  {
    if (!this.flds[i].Check()) return false; // ������ ���������� ����
    if (this.flds[i].isEmpty() && this.flds[i].isReq )
    {
        if (firstIdUnderflow==null) firstIdUnderflow=i;
        requiedFlds = requiedFlds.concat(this.flds[i].userName);
    }
  }
  if ( state != null) state.value = "�������";
  if ( this.isCheckReq && (requiedFlds.length > 0 )) // ���� ������������� ������������ ����
  {
    if ( state != null) state.value = "����������";
    requiedFldsNames = "�� ��������� ������������ ����:\n\n" + requiedFlds.join( "\n");
    if ( this.isSaveIncomplete) {
      document.getElementById(this.flds[firstIdUnderflow].id).scrollIntoView();
      document.getElementById(this.flds[firstIdUnderflow].id).focus();
      rc =  window.confirm(requiedFldsNames + "\n\n��������� �������� ��� �������� �����������?");
     }
    else {
      alert (requiedFldsNames );
      rc=false;
    }
  }
  return rc;
}
// ��������� ���� �����
function fnCheckFormField(_id)
{
 var i;
 for (i=0;i<this.flds.length;i++)
   {
     if ( this.flds[i].id==_id ) return this.flds[i].Check();
   }
 return true; // ���� �� �������
}

 var oForm;