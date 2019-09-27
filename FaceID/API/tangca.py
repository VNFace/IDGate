#!/usr/bin/env python
# -*- coding: utf-8 -*-
#
#  tangca.py
#  
#  Copyright 2019  <pi@raspberrypi>
#  
#  This program is free software; you can redistribute it and/or modify
#  it under the terms of the GNU General Public License as published by
#  the Free Software Foundation; either version 2 of the License, or
#  (at your option) any later version.
#  
#  This program is distributed in the hope that it will be useful,
#  but WITHOUT ANY WARRANTY; without even the implied warranty of
#  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#  GNU General Public License for more details.
#  
#  You should have received a copy of the GNU General Public License
#  along with this program; if not, write to the Free Software
#  Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
#  MA 02110-1301, USA.
#  
#  

#thong ke theo thoi gian
@app.route('/report_one_person',methods = ['POST'])  
def sql_report_one_person():
    from functions.sqlquery import sql_query, sql_query2
    result_ = None
    time = {}
    checkin={}
    checkout={}
    checked={}
    Ghichu={}    
    idx =0
    if request.method == 'POST':
        #lay thong tin ID va thoi gian de tim kiem
        start = datetime.strptime(request.form['start'],'%Y-%m-%d')
        end = datetime.strptime(request.form['end'],'%Y-%m-%d')
        ID_Emp = request.form['ID_Emp']
        FullName= request.form['FullName']
        #lay danh sach bo phan co san. moi bo phan co cac ca lam viec
        sql_depart = sql_query(''' SELECT * FROM Position''')
        dates = [start+timedelta(days=i) for i in range((end-start).days+1)]
        #duyet tung ngay
        for single_date in dates:
        
            d_start = single_date.replace(hour=0, minute=0).strftime("%Y-%m-%d %H:%M:%S")
            d_end = single_date.replace(hour=23, minute=59).strftime("%Y-%m-%d %H:%M:%S")
            #lay danh sach lich su theo ID
            sql ="SELECT h.id_emp,e.FullName,e.position,h.time FROM Employee_History as h JOIN Employee as e ON h.ID_Emp=e.ID_Emp where h.ID_Emp ='"+ID_Emp+"'and time>='"+d_start+"' and time <='"+d_end+"'"
            result_all = sql_query(sql)
            #lay danh sach lich su va nhom lai thong tin thoi gian vao va ra
            sql ="SELECT Count(h.id_emp) as Count,MIN(time) as checkin, MAX(time) as checkout,h.id_emp,e.FullName,h.time FROM Employee_History as h JOIN Employee as e ON h.ID_Emp=e.ID_Emp where h.ID_Emp ='"+ID_Emp+"' and time>='"+d_start+"' and time <='"+d_end+"' group by h.id_emp"
            result_ = sql_query(sql)            
            nv_dimuon =0
            nv_dunggio=0
            nv_ngoaigio=0
            min = 10000
            min_val=1000;
            min_e = 1000
            min_val_e = 0
            
            if len(result_)>0:
                #lay thong tin checkin out theo tung ngay
                for row in result_:                    
                    time[idx] = single_date.strftime("%Y-%m-%d")
                    checkin[idx] = row["checkin"]
                    checkout[idx] = row["checkout"]
                    checked[idx] = row["Count"]
                    Ghichu[idx] = None
                    print("time",time[idx],"checkin = ",checkin[idx],"checkout=",checkout[idx],"checked=",checked[idx])
                #thong ke ghi chu: thoi gian di muon|ve som| tang ca    
                for row in result_all:                             
                    for rowd in sql_depart:
                        if row["position"] == rowd["ID_Pos"]:
                            shift = rowd["Shift"].split('|')
                            
                            for sh in shift:
                                sql_timeshift = sql_query2(''' SELECT * FROM TimeShift where ID_Shift=?''',sh)                            
                                for shif_d in sql_timeshift:
                                    time_star = datetime.strptime(shif_d["Start"],'%H:%M').replace(year=single_date.year, month=single_date.month,day=single_date.day)
                                    time_end = datetime.strptime(shif_d["End"],'%H:%M').replace(year=single_date.year, month=single_date.month,day=single_date.day)
                                    time_ = datetime.strptime(row["time"],"%Y-%m-%d %H:%M:%S")                                    
                                    diff =  (time_- time_star).total_seconds()/60
                                    diff_e =  (time_- time_end).total_seconds()/60
                                    if abs(diff) <min:
                                        min = abs(diff)
                                        min_val = diff
                                    if abs(diff_e)<min_e:
                                        min_e = abs(diff_e)
                                        min_val_e = diff_e                                    
                                    #print("diff time = ",diff,time_,time_star)                                                            
                            if min_val > 0:
                                nv_dimuon = nv_dimuon+1
                            else:
                                nv_dunggio= nv_dunggio+1
                            #lam ngoai gio neu qua 20 phut nhan vien chua ra khoi cong ty    
                            if min_val_e> 20:
                                nv_ngoaigio = nv_ngoaigio+1
                #đi muộn
                if min_val>0:
                    Ghichu[idx] = "Đi muộn "+"{:.2f}".format(min_val) +" phút"
                #tang ca                       
                if min_val_e>20:
                    Ghichu[idx] = Ghichu[idx]+" Tăng ca "+"{:.2f}".format(min_val_e)+" phút"
                #ve som    
                if min_val_e<-10:
                    Ghichu[idx] = Ghichu[idx]+" về sớm "+"{:.2f}".format(abs(min_val_e))+" phút"
                    
                idx = idx+1   
    current_date = datetime.now().strftime('%Y-%m-%d')            
    return render_template('sqldatabase_bc.html', len = len(time),times = time,checkin = checkin,checkout = checkout,checked=checked,Ghichu=Ghichu,ID_Emp=ID_Emp,FullName=FullName,current_date=current_date)
def main(args):
    return 0

if __name__ == '__main__':
    import sys
    sys.exit(main(sys.argv))
