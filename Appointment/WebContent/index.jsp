<%@page import="com.Appointment"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Appointment Management</title>

<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/Appo.js"></script>


<!-- Bootstrap Date-Picker Plugin -->
<script  src="Components/bootstrap-datepicker.min.js"></script>
<link rel="stylesheet" href="Views/bootstrap-datepicker3.css"/>




</head>
<body>

<body>
<div class="container">
	<div class="row" >
		<div class="col-6">
			<h1>Appointments Management</h1>
			
			<form id="formAppointment" name="formAppointment" method="post" action="index.jsp">

				Doctor_ID:
				<input id="Doctor_ID" name="Doctor_ID" type="text" class="form-control form-control-sm">
				<br>
				 
				Hospital_ID:
				<input id="Hospital_ID" name="Hospital_ID" type="text" class="form-control form-control-sm">
				<br>
				
				Chargers:
				<input id="Chargers" name="Chargers" type="text" class="form-control form-control-sm">
				<br>
				 
				Date:
        		<input  id="Date" name="Date" placeholder="YYYY-MM-DD" type="text" class="form-control form-control-sm "/>
      			<br>
      			
      			Start Time:
        		<input class="form-control form-control-sm " id="Start_Time" name="Start_Time" placeholder="00.00AM" type="text"/>
      			<br>
      			
      			End Time:
        		<input class="form-control form-control-sm " id="End_Time" name="End_Time" placeholder="00.00AM" type="text"/>
      			<br>
      			
				
				<input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary">
				<input type="hidden" id="hidAppointmentIDSave" name="hidAppointmentIDSave" value="">
			</form>
			
			<div id="alertSuccess" class="alert alert-success"></div>
			<div id="alertError" class="alert alert-danger"></div>
			<br>

				<div id="divAppointmentGrid">
					<%
						Appointment AppointmentObj = new Appointment();
						out.print(AppointmentObj.readAppointment());
					%>
				</div>
			
		</div>
	</div>
</div>
</body>
</html>