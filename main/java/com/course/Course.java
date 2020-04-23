package com.course;

public class Course {
    public String name="null";
    public String code="null";
    public boolean isLecture=true;
    public int weekDay=-1;
    public int start=-1;
    public int end=-1;
    public String location="null";
    public String comments="null";
    public int notification=0;

    @Override
    public String toString(){
        return name+";"+code+";"+(isLecture?"lecture":"practice")+";"+weekDay+";"+start+";"+end+";"+location+";"+comments+";"+notification+";";
    }

    public String toDetailString(){
        return "name:"+name+" code:"+code+" "+(isLecture?"lecture":"practice")+" weekday:"+weekDay+" start:"+start+" end:"+end+" location:"+location+" comments:"+comments+" notificate "+notification+" before";
    }

    public String toShortString(){
        return name+"\n"+location;
    }

    public void setName(String name){
        if(name!=null)
            if(name.length()>0)
                this.name=name;
    }

    public void setCode(String code){
        if(code!=null)
            if (code.length()>0)
                this.code=code;
    }

    public void setLecture(String lecture){
        if(lecture==null)
            return;
        if(lecture.equals("lecture"))
            this.isLecture=true;
        else
            this.isLecture=false;
    }

    public void setWeekDay(String weekDay){
        int result=-1;
        try{
            result=Integer.parseInt(weekDay);
        }catch (Exception e){

        }
        this.weekDay=result;
    }

    public void setNotification(String start){
        int result=0;
        try{
            result=Integer.parseInt(start);
        }catch (Exception e){

        }
        this.notification=result;

    }

    public void setStart(String start){
        int result=-1;
        try{
            result=Integer.parseInt(start);
        }catch (Exception e){

        }
        this.start=result;

    }

    public void setEnd(String end){
        int result=-1;
        try{
            result=Integer.parseInt(end);
        }catch (Exception e){

        }
        this.end=result;
    }

    public void setLocation(String location){
        if(location!=null)
            if(location.length()>0)
                this.location=location;
    }
    public void setComments(String comments){
        if(comments!=null)
            if(comments.length()>0)
                this.comments=comments;
    }

}
