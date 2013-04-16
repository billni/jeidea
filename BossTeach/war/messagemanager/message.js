$(function(){
    var success = "Thanks! We will contact you as quickly as possible!";
    var error= "Name , Email , Fax , Message may be empty, please check.";
    
	$("#submit").click(function(){
		$("#msg").text("");
		if ($("input[name='message.visitor.name']").val() == "联系人" 
		 || $("input[name='message.visitor.mail']").val() == "电子邮件"
	     || $("input[name='message.visitor.fax']").val() == "联系电话"
	     || $("textarea[name='message.content']").text() == "需求"){
			$("#msg").show(function(){	
				$("#msg").text(error);
				$("#msg").fadeOut(4000);				
			});
			resetform();			
			return false;		
		}
		
		$.ajax({ 
			type: "post",
			data: $("#messageform").formSerialize(),
			url: "/messagemanager/addMessage.action",
			success:function(t){
				$("#msg").show(function(){					
					$("#msg").text(success);
					$("#msg").fadeOut(2000);					
				});
				$("#messageform").clearForm();
				resetform();
			}
		}); 				   
	});
	  
	$("input[name*='message.']").focus(function(){				   
	   $(this).clearFields();	   
	}); 
	
	function resetform(){
		$("input[name='message.visitor.name']").val("联系人");
		$("input[name='message.visitor.mail']").val("电子邮件");
		$("input[name='message.visitor.fax']").val("联系电话");
		$("textarea[name='message.content']").text("需求");
	}
})