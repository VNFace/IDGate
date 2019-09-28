from flask import Flask, request, redirect, render_template,send_from_directory,Response,jsonify,url_for
import threading
import sys
import os
import datetime
import time
from datetime import datetime,timedelta, date
import numpy as np
import jsonpickle
import cv2
import matplotlib.pyplot as plt
from shutil import rmtree,copyfile
import base64
import json
import tablib
import csv
import pandas as pd


APP_ROOT = os.path.dirname(os.path.abspath(__file__))
# initialize the output frame and a lock used to ensure thread-safe
lock = threading.Lock()

app = Flask(__name__)


# get employee decteced
@app.route("/people", methods=["GET"])
def getPeopl():
    from functions.sqlquery import sql_query,sql_query_export    
    results = sql_query(''' SELECT * FROM Employee_History ORDER BY time DESC LIMIT 1''')
    data = []
    idx = 0;
    image = {}
    for row in results :
        # correct
        data.append({'FullName' : row["FullName"], 
            'etime ' : datetime.strptime(row["time"],"%Y-%m-%d %H:%M:%S"),
            'ID_Emp_His' : row["ID_Emp_His"],
            'id_emp' : row["id_emp"],
            'confident' : row["confident"]})
    return jsonify({'data' : data, 'message' : 'sucess'})



# get all employee
@app.route("/employee", methods=["GET"])
def getAllEmployee():
    from functions.sqlquery import sql_query, sql_query_export
    from pandas.io.json import json_normalize

    results = sql_query(''' SELECT * FROM Employee ''')
    employee = []
    dataXlSX = []
    idx = 0;
    image = {}

    dataTest = [];
    hearders = {('ID_Emp', 'FullName', 'Address', 'email', 'phone', 'position',
        'Finger', 'trainedCount')}
    for row in results :
        employee.append({'ID_Emp' : row["ID_Emp"],
            'FullName' : row["FullName"],
            'Address' : row["address"],
            'email' : row["email"],
            'phone' : row["phone"],
            'position' : row["position"],
            'Finger' : row["Finger"],
            'trainedCount' : row["trainedCount"]})
        #maybe correct
    # write json to csv , in this time , good
    with open('C:/Users/Dell/Desktop/IDGate/IDGate/FaceID/API/fileJson.json', 'w') as f:  # open the xlsx file
        (json.dump(employee, f))
        f.close()
    json_data = pd.read_json("C:/Users/Dell/Desktop/IDGate/IDGate/FaceID/API/fileJson.json")

    # write to file excel
    json_data.to_excel("C:/Users/Dell/Desktop/IDGate/IDGate/FaceID/API/output.xls", index = False)

    # write to file csv
    json_data.to_csv('C:/Users/Dell/Desktop/IDGate/IDGate/FaceID/API/fileJson.csv')
    return jsonify({'message' : "success", 'employee' : employee})
    
    


# get all employee in date
@app.route("/history", methods=["GET"])
def getEmployeeHistoryByDate():
    from functions.sqlquery import sql_query, sql_query_export
    arg1 = request.args['arg1']
    history = sql_query("SELECT * FROM Employee_History WHERE date(time) = " + '"' + arg1 + '"')
    employee_history = []
    idx = 0
    for row in history :
        employee_history.append({'ID_Emp_His' : row["ID_Emp_His"],
            'ID_Emp' : row["ID_Emp"],
            'FullName' : row["FullName"],
            'time' : datetime.strptime(row["time"],"%Y-%m-%d %H:%M:%S"),
            'confident' : row["confident"],
            'url_history' : row["url_history"]})
    return jsonify({'message' : "success", 'employee' : employee_history})


# cach 1
# get all account 
@app.route("/login", methods=["GET"])
def login():
    from functions.sqlquery import sql_query, sql_query_export
    results = sql_query("SELECT * FROM Account")
    account = []
    for row in results :
        account.append({'ID' : row["ID"],
            'ID_Account' : row['ID_Account'],
            'user_name' : row["user_name"],
            'password' : row["password"]})
    return jsonify({'message' : "success", 'account' : account})


# get information employee by id
@app.route("/information", methods=["GET"])
def information():
    from functions.sqlquery import sql_query, sql_query_export
    arg1 = request.args['arg1']
    employee = sql_query("SELECT * FROM Employee WHERE ID_Emp = " + '"' + arg1 + '"')
    # employee = sql_query("SELECT * FROM Employee WHERE ID_Emp = " + arg1)
    information = []
    idx = 0
    for row in employee :
        information.append({'ID_Emp' : row["ID_Emp"],
            'FullName' : row["FullName"],
            'address' : row["address"],
            'email' : row["email"],
            'phone' : row["phone"],
            'position' : row["position"]})
    return jsonify({'message' : "success", 'employee' : information})

@app.route("/delete", methods=["DELETE"])
def deleteEmployee():
    from functions.sqlquery import sql_query, sql_query_export
    arg1 = request.args['arg1']
    sql_query("DELETE FROM Employee WHERE ID_Emp = " + '"' + arg1 + '"')

    # delete employee in table employee_history
    # sql_query("DELETE FROM Employee_History WHERE ID_Emp = " + '"' + arg1 + '"')
    return {'message' : "delete success"}


# Update employee
@app.route("/update", methods=["PUT"])
def update_employee():
    from functions.sqlquery import sql_query, sql_query_export
    fullName = request.args['arg1']
    idEmp = request.args['arg2']
        # address = request.args['arg2']
        # email = request.args['arg3']
        # phone = request.args['arg4']
        # position = request.args['arg5']
    sql_query("UPDATE Employee SET FullName = " + '"' + fullName + '"' +
        "WHERE ID_Emp = " + '"' + idEmp + '"')
         # +
         #    "address = " + '"' + address + '"' + 
         #    "email = " + '"' + email + '"' +
         #    "phone = " + '"' + phone + '"' + 
         #    "position = " + '"' + position + '"')
    return {'message' : "update success"}



# Insert employee
@app.route("/insert", methods=["POST"])
def insertEmployee():
    from functions.sqlquery import sql_query, sql_query_export, sql_query_process
    idEmp = request.args["idEmp"]
    fullName = request.args["fullName"]
    address = request.args["address"]
    email = request.args["email"]
    phone = request.args["phone"]
    position = request.args["position"]
    sql_query_process("INSERT INTO Employee (ID_Emp, FullName, address, email, phone, position) VALUES (" 
        + '"' + idEmp 
        + '"'+ "," + '"' + fullName 
        + '"'+ "," + '"' + address
        + '"'+ "," + '"' + email
        + '"'+ "," + '"' + phone
        + '"'+ "," + '"' + position
        + '"' + ")")
    print("INSERT INTO Employee (ID_Emp, FullName) VALUES (" + '"' + "NV005" 
        + '"'
        + "," + '"' +"TEST_3" + '"' + ")")
    return {'message' : "insert success"}


#==========================================END=======================================================================
if __name__ == "__main__":
    app.run(host="192.168.1.185", port="8000",debug=True)

