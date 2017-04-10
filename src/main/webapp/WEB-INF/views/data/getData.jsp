<%--
  Created by IntelliJ IDEA.
  User: FRAMGIA\duong.van.tien
  Date: 10/04/2017
  Time: 13:59
  To change this template use File | Settings | File Templates.
--%>
<%--
  Created by IntelliJ IDEA.
  User: FRAMGIA\duong.van.tien
  Date: 07/03/2017
  Time: 13:53
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
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
                    <form:form class="form-horizontal" modelAttribute="conditionBookingBeanForm" id="formSearch"
                               action="${pageContext.request.contextPath}/searchRoomCondition" method="POST">
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <br/>
                            <div class="form-group ">
                                <label class="control-label col-sm-4 requiredField" for="startDate">
                                    Start Date
                                    <span class="asteriskField">*</span>
                                </label>
                                <div class="col-sm-8">
                                    <div class="input-group">
                                        <div class="input-group-addon">
                                            <i class="fa fa-calendar"></i>
                                        </div>
                                        <form:input class="form-control" path="" id="startDate" name="startDate"
                                                    placeholder="yyyy/mm/dd" type="text"
                                                    value=""/>
                                    </div>
                                </div>
                                <label class="control-label col-sm-12 requiredField">
                                    <h5>${err_empty}</h5>
                                </label>
                            </div>

                            <div class="form-group ">
                                <label class="control-label col-sm-4 requiredField">
                                    Room size
                                    <span class="asteriskField">*</span>
                                </label>
                                <div class="col-sm-8">
                                    <select class="form-control" path="size" name="size">
                                        <c:forEach begin="1" end="5" var="i">
                                            <option value="${i}" <c:if test="${i eq conditionBookingBeanForm.size}">
                                                selected="selected"</c:if>>${i}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-sm-4"></div>
                            </div>
                        </div>

                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <br/>
                            <div class="form-group ">
                                <label class="control-label col-sm-4 requiredField" for="endDate">
                                    End Date
                                    <span class="asteriskField">*</span>
                                </label>
                                <div class="col-sm-8">
                                    <div class="input-group">
                                        <div class="input-group-addon">
                                            <i class="fa fa-calendar"></i>
                                        </div>
                                        <form:input class="form-control" path="" id="endDate" name="endDate"
                                                    placeholder="yyyy/mm/dd" type="text"
                                                    value=""/>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-sm-4 requiredField">
                                </label>
                                <div class="col-sm-8">
                                    <div class="col-sm-6 col-xs-6 ">
                                        <a class="btn btn-success " style="color: #efeaee" id="btnSubmit"> Search </a>
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

<!-- RIGHT SIDEBAR CONTENT -->

<script type="text/javascript">
    $(document).ready(function() {
        $("a[id='btnSearch']").on('click', function(e){
            e.preventDefault();
            var endDateVal = $("#endDate").val();
            $.ajax({url: "/searchRoomCondition",type: "POST",dataType: "json",
                data: {startDateVal: startDateVal.toString(), endDateVal: endDateVal.toString()},
                success: function(data){
                    console.log(data);
                    var html = '';
                }
            });
        })
    });

    function logout() {
        $('#logoutForm').submit();
    };
</script>
