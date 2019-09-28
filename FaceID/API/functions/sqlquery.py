import os
import sqlite3
import pandas as pd
import matplotlib.pyplot as plt




conn = None
# Clear example.db if it exists
if os.path.exists('C:/Users/Dell/Desktop/IDGate/IDGate/FaceID/API/FaceID.db') == False:
    # Create a database
    conn = sqlite3.connect('C:/Users/Dell/Desktop/IDGate/IDGate/FaceID/API/FaceID.db',check_same_thread=False)
    # Add the data to our database
    data_table.to_sql('Employee', conn, dtype={
		'ID_Emp':'VARCHAR(60)',
		'FullName':'VARCHAR(200)',
		'address':'VARCHAR(256)',
		'email':'VARCHAR(100)',
		'phone':'VARCHAR(10)',
		'position':'VARCHAR(100)',
    })
    # Add the data to our database
    data_table.to_sql('Employee_History', conn, dtype={
        'ID_Emp_His':'INTEGER',
		'ID_Emp':'VARCHAR(30)',
		'time':'VARCHAR(100)',
		'email':'VARCHAR(100)',
		'confident':'VARCHAR(30)',		
		'note':'VARCHAR(200)',
    })
else:

    conn = sqlite3.connect('C:/Users/Dell/Desktop/IDGate/IDGate/FaceID/API/FaceID.db',check_same_thread=False)    

# Create a database
#conn = sqlite3.connect('example.db',check_same_thread=False)
conn.row_factory = sqlite3.Row

# Make a convenience function for running SQL queries
def sql_query(query):
    cur = conn.cursor()
    cur.execute(query)
    rows = cur.fetchall()
    return rows

def sql_query_process(query):
    cur = conn.cursor()
    cur.execute(query)
    conn.commit();

def sql_edit_insert(query,var):
    cur = conn.cursor()
    cur.execute(query,var)
    conn.commit()
    

def sql_delete(query,var):
    cur = conn.cursor()
    cur.execute(query,var)
    conn.commit()
    

def sql_query2(query,var):
    cur = conn.cursor()
    cur.execute(query,var)
    rows = cur.fetchall()
    return rows

# Make a convenience function for running SQL queries
def sql_query_export(query):    
	df = pd.read_sql_query(query, conn)
	df.to_csv(data_url_files);    

def build_graph(x_coordinates, y_coordinates):    
    plt.plot(x_coordinates, y_coordinates)    
    plt.savefig("anh.jpg")
