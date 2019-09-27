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

APP_ROOT = os.path.dirname(os.path.abspath(__file__))
# initialize the output frame and a lock used to ensure thread-safe
lock = threading.Lock()

app = Flask(__name__)

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


@app.route("/employee", methods=["GET"])
def getAllEmployee():
    from functions.sqlquery import sql_query, sql_query_export
    results = sql_query(''' SELECT * FROM Employee ''')
    employee = []
    idx = 0;
    image = {}
    for row in results :
        employee.append({'ID_Emp' : row["ID_Emp"],
            'FullName' : row["FullName"],
            'Address' : row["address"],
            'email' : row["email"],
            'phone' : row["phone"],
            'position' : row["position"],
            'Finger' : row["Finger"],
            'trainedCount' : row["trainedCount"]})
    return jsonify({'message' : "success", 'employee' : employee})


# test api
@app.route("/history:day", methods=["GET"])
def getEmployeeByDate(day):
    from functions.sqlquery import sql_query, sql_query_export
    history = sql_query('''SELECT * FROM Employee_History WHERE date(time) = day''')
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
#==========================================END=======================================================================
if __name__ == "__main__":
    app.run(host="192.168.1.106", port="8000",debug=True)

