<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%--
  Created by IntelliJ IDEA.
  User: FRAMGIA\duong.van.tien
  Date: 10/04/2017
  Time: 13:59
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<link rel="stylesheet" href="https://formden.com/static/cdn/bootstrap-iso.css"/>
<link rel="stylesheet" href="https://formden.com/static/cdn/font-awesome/4.4.0/css/font-awesome.min.css"/>
<style>
	.bootstrap-iso .formden_header h2, .bootstrap-iso .formden_header p,
	.bootstrap-iso form {
		font-family: Arial, Helvetica, sans-serif;
		color: black
	}

	.bootstrap-iso form button,
	.bootstrap-iso form button:hover {
		color: white !important;
	}

	.asteriskField {
		color: red;
	}
</style>

<script type="text/javascript"
		src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/js/bootstrap-datepicker.min.js">
</script>
<link rel="stylesheet"
	  href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/css/bootstrap-datepicker3.css"/>

<div class="col-lg-9 ">
	<div class="col-md-12">
		<div class="col-md-3 col-sm-6 col-xs-12"></div>
		<div class="col-md-6 col-sm-6 col-xs-12">
			<p id="err_startDate" style="color: red"></p>
			<p id="err_endDate" style="color: red"></p>
			<p style="color: red"><h5>${err_empty}</h5></p>
		</div>
		<div class="col-md-3 col-sm-6 col-xs-12"></div>
	</div>

	<div class="col-md-12">
		<div class="bootstrap-iso">
			<div class="container-fluid">
				<div class="row">
					<form:form class="form-horizontal" id="formSearch"
							   action="${pageContext.request.contextPath}/" method="POST">
						<div class="col-md-6 col-sm-6 col-xs-12">
							<br/>

							<div class="form-group ">
								<label class="control-label col-sm-4 requiredField">
									City
									<span class="asteriskField">*</span>
								</label>
								<div class="col-sm-8">
									<select class="form-control" path="cityName" name="cityName" id="cityName">
										<c:forEach items="${listCities}" var="city">
											<option value="${city.nameCity}">${city.nameCity}</option>
										</c:forEach>
									</select>
								</div>
								<div class="col-sm-4"></div>
							</div>
						</div>

						<div class="col-md-6 col-sm-6 col-xs-12">
							<br/>
							<div class="form-group">
								<label class="control-label col-sm-4 requiredField">
								</label>
								<div class="col-sm-8">
									<div class="col-sm-6 col-xs-6 ">
										<a class="btn btn-success " style="color: #efeaee" id="btnCloneData"> Clone data </a>
									</div>
								</div>
							</div>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		$("a[id='btnCloneData']").on('click', function(e){
//			e.preventDefault();
			var cityName = $("#cityName").val();
			console.log("------x---"+cityName);
			$.ajax({url: "/clone-data/"+cityName,type: "GET",dataType: "json",
				data: {cityName: cityName.toString()},
				success: function(data){
					console.log(data);
					var html = '';
				}
			});
		})
	});

</script>

