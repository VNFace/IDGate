#!/usr/bin/env python
# -*- coding: utf-8 -*-
#
#  video_query.py
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

@app.route("/video_day", methods=["POST"])
def search_video_playback():    
    from functions.sqlquery import sql_query,sql_query2
    current_date = datetime.now().strftime('%Y-%m-%d')
    image_name={}    
    image_path={}    
    url_name =None    
    idx =0
    result_rcs =None
    if request.method == 'POST':        
        start = datetime.strptime(request.form['start'],'%Y-%m-%d').replace(hour=0, minute=0).strftime("%Y-%m-%d %H:%M:%S")        
        end = datetime.strptime(request.form['end'],'%Y-%m-%d').replace(hour=23, minute=59).strftime("%Y-%m-%d %H:%M:%S")                    
        
        sql ="SELECT * FROM Video where start>='"+start+"' and end <='"+end+"'"
        results = sql_query(sql)            
        for row in results:
            image_name[idx]=os.path.basename(row["url_image"])
            image_path[idx]=os.path.dirname(row["url_image"])
            if idx == 0:
                #remove all old file
                inf = os.walk(VIDEO_DIRECTORY)            
                for (r,d,f) in os.walk(VIDEO_DIRECTORY):                
                    for file in f:
                        file=os.path.join(r,file)
                        os.remove(file)                                        
                url_name = os.path.basename(row["url_video"])
                url_name = "static/video/"+url_name        
                copyfile(row["url_video"],os.path.join(APP_ROOT,url_name))
                print("url_name = ",url_name)
                url_name = os.path.basename(row["url_video"])    
                url_name = "video/"+url_name        
                #tim kiem du lieu lich su
                d_start = start
                d_end = end
                result_rcs = sql_query2(''' SELECT * FROM Employee_History where time>=? and time <=? ORDER  BY time DESC''',(d_start,d_end))                
            idx = idx+1
            
    return render_template('video_report.html',current_date=current_date,start = start,end=end,len = len(results),video_path=url_name,results = results, image_name = image_name, image_path = image_path,result_rcs = result_rcs)
    
@app.route('/query_video_play',methods = ['POST', 'GET']) #this is when user clicks edit link 
def sql_videolink():    
    from functions.sqlquery import sql_query,sql_query2
    current_date = datetime.now().strftime('%Y-%m-%d')
    image_name={}    
    image_path={}    
    url_name =None
    result_rcs =None
    idx =0
    
    if request.method == 'GET':        
        
        start = datetime.strptime(request.args.get('start'),'%Y-%m-%d %H:%M:%S').replace(hour=0, minute=0).strftime("%Y-%m-%d %H:%M:%S")        
        end = datetime.strptime(request.args.get('end'),'%Y-%m-%d %H:%M:%S').replace(hour=23, minute=59).strftime("%Y-%m-%d %H:%M:%S")                    
        start_rc=datetime.strptime(request.args.get('start_rc'),'%Y-%m-%d %H:%M:%S').replace(hour=0, minute=0).strftime("%Y-%m-%d %H:%M:%S")        
        end_rc = datetime.strptime(request.args.get('end_rc'),'%Y-%m-%d %H:%M:%S').replace(hour=23, minute=59).strftime("%Y-%m-%d %H:%M:%S")                    
        video_path = request.args.get('video_path')
        sql ="SELECT * FROM Video where start>='"+start+"' and end <='"+end+"'"
        results = sql_query(sql)            
        for row in results:
            image_name[idx]=os.path.basename(row["url_image"])
            image_path[idx]=os.path.dirname(row["url_image"])            
            idx = idx+1
        
        #remove all old file
        inf = os.walk(VIDEO_DIRECTORY)            
        for (r,d,f) in os.walk(VIDEO_DIRECTORY):                
            for file in f:
                file=os.path.join(r,file)
                os.remove(file)                                        
        url_name = os.path.basename(video_path)
        url_name = "static/video/"+url_name        
        copyfile(video_path,os.path.join(APP_ROOT,url_name))
        print("url_name = ",url_name)
        url_name = os.path.basename(video_path)    
        url_name = "video/"+url_name        
        #tim kiem du lieu lich su
        d_start = start_rc
        d_end = end_rc
        result_rcs = sql_query2(''' SELECT * FROM Employee_History where time>=? and time <=? ORDER  BY time DESC ''',(d_start,d_end))        
            
    return render_template('video_report.html',current_date=current_date,start = start,end=end,len = len(results),video_path=url_name,results = results, image_name = image_name, image_path = image_path,result_rcs=result_rcs)
def main(args):
    return 0

if __name__ == '__main__':
    import sys
    sys.exit(main(sys.argv))
