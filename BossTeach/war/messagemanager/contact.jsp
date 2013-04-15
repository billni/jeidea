<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
	<title>北京博胜天成管理咨询有限公司</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="keywords" content="北京博胜天成管理咨询有限公司,博胜天成,管理咨询,consultant,boss,teach,service"/>	 
	<meta name="description" content="boss teach and boss teacher"/>
	<meta name="pagename" content="contact"/>
	<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
	<link href="<%=request.getContextPath()%>/css/layout.css" rel="stylesheet" type="text/css" />	
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-easyui-1.3.1/jquery-1.8.2.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.form.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/default.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/messagemanager/message.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/default.js"></script>
</head>


<body id="index_5">
	<div id="header_tall">
		<div id="main">
			<!--header -->
			<div id="header">
				<div id="logo" class="h_logo"></div>
				<div id="menu"></div>
				<div id="companycontent" class="content"></div>		
			</div>
			<!--header end-->
			<div id="middle">
				<div class="indent">
					<div class="columns1">
						<div class="column1">
							<div class="border">
								<div class="btall">
									<div class="ltall">
										<div class="rtall">
											<div class="tleft">
												<div class="tright">
													<div class="bleft">
														<div class="bright">
															<div class="ind">
																<div class="h_text">
																	<img alt="" src="/img/6-t1.jpg" /><br />
																</div>
																<div class="padding">
																	<strong>北京博胜天成管理咨询有限公司</strong><br />
																	<p class="p1">
																		8901 Marmora Road,<br />
																		Glasgow, D04 89GR.<br /><br />
																		
																		Freephone: &nbsp; &nbsp; &nbsp;+010 800 559 6580<br />
																		Telephone: &nbsp; &nbsp; &nbsp; +010 959 603 6035<br />
																		FAX: &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;+1 504 889 9898<br />
																		E-mail: <a href="mailto:example@phplamp.com?subject=请尽快联系我">bossteach2012@gmail.com</a>																	</p>
																</div>
															</div>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="indent_column">&nbsp;</div>
						<div class="column2">
							<div class="border">
								<div class="btall">
									<div class="ltall">
										<div class="rtall">
											<div class="tleft">
												<div class="tright">
													<div class="bleft">
														<div class="bright">
															<div class="ind">
																<div class="h_text">
																	<img alt="" src="/img/6-t2.jpg" /><br />
																</div>
																<div class="padding">
																	<strong>Argentina</strong><br />
																	Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Praesent vestibulum molestie lacus. Aenean nonummy hendrerit mauris. Phasellus porta.<br />
																	<strong>Denmark</strong><br />
																	Morbi nunc odio, gravida at, cursus nec, luctus a, lorem. Maecenas tristique orci ac sem. Duis ultricies pharetra magna. Donec accumsan malesuada orci.<br />
																	<strong>Finland</strong><br />
																	Quisque nulla. Vestibulum libero nisl, porta vel, scelerisque eget, malesuada at, neque. Vivamus eget nibh. Etiam cursus leo vel metus. Nulla facilisi.																</div>
															</div>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="clear"></div>
					</div>
					<div class="columns2">
						<div class="padding">
							<img alt="" src="/img/6-t3.gif" /><br />
							<p class="p1">
								<strong class="b_text">可能你的公司运作的很好了，但是你不希望运作的更好吗？ </strong><br />
								如果你想联系我们，获得专业的服务，请不要犹豫。请填写下面的信息来告知你的需求。等待你的消息!<br class="clear" />
							</p>
							<form id="messageform">
								<div class="column1">
									<div class="row">
										<input name="message.visitor.name" type="text" class="input" value='联系人'/>
									</div>
									<div class="row">
										<input name="message.visitor.mail" type="text" class="input" value='电子邮件'/>
									</div>
									<div class="row">
										<input name="message.visitor.fax" type="text" class="input" value='联系电话'/>
									</div>
								</div>
								<div class="column2">
									<div class="">
										<textarea cols="1" rows="1" name="message.content"></textarea><br/>
										<div class="div">
											<input type="reset" value="重置" class="button"></input>&nbsp;
											<input id="submit" type="button" value="提交" class="button"></input>
										</div>
									</div>
								</div>
								<div class="clear"></div>
							</form>
							<div id="msg" style="font-weight: bold;display:none"></div>
						</div>
					</div>
				</div>
			</div>
			<!--footer -->
			<div id="footer">			
			</div>
			<!--footer end-->
		</div>
	</div>
</body>
</html>