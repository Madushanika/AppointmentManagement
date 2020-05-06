// Datepicker

$(document).ready(function(){
    var date_input=$('input[name="Date"]'); //our date input has the name "date"
    var container=$('.bootstrap-iso form').length>0 ? $('.bootstrap-iso form').parent() : "body";
    var options={
      format: 'yyyy-mm-dd',
      container: container,
      todayHighlight: true,
      autoclose: true,
    };
    date_input.datepicker(options);
  })




$(document).ready(function()
{
	$("#alertSuccess").hide();
	$("#alertError").hide(); 
});

//SAVE ============================================
$(document).on("click", "#btnSave", function(event)
{
	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();
	
	// Form validation-------------------
	var status = validateAppointmentForm();
	
	if (status != true)
	{
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}
	
	// If valid------------------------
	var type = ($("#hidAppointmentIDSave").val() == "") ? "POST" : "PUT";
	
	$.ajax(
	{
		url : "AppointmentAPI",
		type : type,
		data : $("#formAppointment").serialize(),
		dataType : "text",
		complete : function(response, status)
		{
			onAppointmentSaveComplete(response.responseText, status);
		}
	});
});

function onAppointmentSaveComplete(response, status)
{
	if (status == "success")
	{
		var resultSet = JSON.parse(response);
		
		if (resultSet.status.trim() == "success")
		{
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			
			$("#divAppointmentGrid").html(resultSet.data);
		} 
		else if (resultSet.status.trim() == "error")
		{
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} 
	else if (status == "error")
	{
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} 
	else
	{
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}
	
	$("#hidAppointmentIDSave").val("");
	$("#formAppointment")[0].reset();
}

//UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event)
{
	$("#hidAppointmentIDSave").val($(this).closest("tr").find('#hidAppointmentIDUpdate').val());
	$("#Doctor_Name").val($(this).closest("tr").find('td:eq(0)').text());
	$("#Hospital_Name").val($(this).closest("tr").find('td:eq(1)').text());
	$("#Chargers").val($(this).closest("tr").find('td:eq(2)').text());
	$("#Date").val($(this).closest("tr").find('td:eq(3)').text());
	$("#Start_Time").val($(this).closest("tr").find('td:eq(4)').text());
	$("#End_Time").val($(this).closest("tr").find('td:eq(5)').text());
	});


//REMOVE==========================================
$(document).on("click", ".btnRemove", function(event)
{
	$.ajax(
	{
		url : "AppointmentAPI",
		type : "DELETE",
		data : "Shedule_ID=" + $(this).data("scheduleid"),
		dataType : "text",
		complete : function(response, status)
		{
			onAppointmentDeleteComplete(response.responseText, status);
		}
	});
});

function onAppointmentDeleteComplete(response, status)
{
	if (status == "success")
	{
		var resultSet = JSON.parse(response);
		
		if (resultSet.status.trim() == "success")
		{
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			$("#divAppointmentGrid").html(resultSet.data);
		} 
		else if (resultSet.status.trim() == "error")
		{
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} 
	else if (status == "error")
	{
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} 
	else
	{
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}

//CLIENT-MODEL=================================================================
function validateAppointmentForm()
{
	
	// Doctor_Name
	if ($("#Doctor_Name").val().trim() == "")
	{
		return "Insert Doctor Name.";
	}
	

	//Hospital_Name
	if ($("#Hospital_Name").val().trim() == "")
	{
		return "Insert Hospital Name.";
	}
	
	
	//Chargers-------------------------------
	if ($("#Chargers").val().trim() == "")
	{
		return "Insert Chargers.";
	}
	// is numerical value
	var tmpChargers = $("#Chargers").val().trim();
	
	if (!$.isNumeric(tmpChargers))
	{
		return "Insert numerical value for Chargers.";
	}
	
	//Date
	if ($("#Date").val().trim() == "")
	{
		return "Insert E-mail Date.";
	}
	
	//Start_Time
	if ($("#Start_Time").val().trim() == "")
	{
		return "Insert Valid StartTime.";
	}
	
	//End_Time
	if ($("#End_Time").val().trim() == "")
	{
		return "Insert Valid EndTime.";
	}
	
	
	
	return true;
}



