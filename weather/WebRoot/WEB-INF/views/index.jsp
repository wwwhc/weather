<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>天气查询</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<style type="text/css">
#main{
    border:1;
    width:1100px;
    height:650px;
    margin-left: auto;
    margin-right: auto;
    background: #81c0c0;
    }
#head{
    width:1100px;
    height:40px;
}
#input {
	background: pink;
	width:1100px;
    height:60px;
}
#inner{
   background: green;
   position: relative;
   margin-left: 300px;
   margin-right: 300px;
   margin-top: 125px;
   margin-bottom: 125px;

}
</style>
</head>

<body>
	<div id="main">
	    <div id="head"><center><h1>天气查询</h1></center></div>
		<div id="input">
			<center>
				<sf:form method="post" commandName="message">
					<p>
						<label style="text-color:red;">*请以正确的格式填写，如：湖北省荆州市，则输入：湖北，荆州</label>
					</p>
					<sf:label path="province" cssErrorClass="error">省份</sf:label>
					<sf:input path="province" cssErrorClass="error" />
					<sf:label path="city" cssErrorClass="error">城市</sf:label>
					<sf:input path="city" cssErrorClass="error" />
					<input type="submit" value="查询">
					<sf:errors path="*" element="div" cssClass="error"/>
				</sf:form>
			</center>
		</div>
		<div id="body">
		    <div id="inner">
		      <p style="text-align:center;">${error}</p>
		      <p>${result.name }</p>
		      <p>${result.info }</p>
		      <p>${result.t1 }</p>
		      <p>${result.t2 }</p>
		      <p>${result.t3 }</p>
		      <p>${result.t4 }</p>
		      <p>${result.t5 }</p>
		      <p>${result.date }</p>
		    </div>
		</div>


	</div>
</body>
</html>
