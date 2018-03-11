<%@ page language="java" contentType="text/html; charset=UTF-8"  
    pageEncoding="UTF-8"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">  
<html>  
<head>  
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">  
<title>DaiGo</title>  
</head>  
<body>  
<form id="from" action="GetInstantOrderState" method="post">  
<table>  
<tr><td>用户ID</td><td><input type="text" name="userid"></td></tr>  
<tr><td>状态</td><td><input type="text" name="type"></td></tr> 
<tr><td>index</td><td><input type="text" name="index"></td></tr>
<tr><td>订单号</td><td><input type="text" name="orderid"></td></tr>  
<tr><td>发送用户</td><td><input type="text" name="senderid"></td></tr>  
<tr><td>校区码</td><td><input type="text" name="campusid"></td></tr>  
<tr><td>标题</td><td><input type="text" name="title"></td></tr>  
<tr><td>要求送达时间</td><td><input type="text" name="requesttime"></td></tr>  
<tr><td>公开内容</td><td><input type="text" name="publiccontent"></td></tr> 
<tr><td>私密内容</td><td><input type="text" name="privatecontent"></td></tr>  
<tr><td>联系方式</td><td><input type="text" name="contact"></td></tr>  
<tr><td>价格</td><td><input type="text" name="price"></td></tr> 
<tr><td>手机号</td><td><input type="text" name="phonenum"></td></tr> 
<tr><td>密码</td><td><input type="text" name="password"></td></tr> 
<tr><td>身份证号</td><td><input type="text" name="idcode"></td></tr> 
<tr><td>真实姓名</td><td><input type="text" name="realname"></td></tr> 
<tr><td>学号</td><td><input type="text" name="campusidcode"></td></tr> 

<tr><td colspan="2" align="center"><input type="submit"  value="登陆"></td></tr>  
</table>  
</form>  
</body>  
</html>