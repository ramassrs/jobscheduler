package com.srs.jms.jobscheduler;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.srs.jms.jobscheduler.MyJMSBean;

public class CronScheduledJob extends QuartzJobBean {
	
	private MyJMSBean myJMSBean;
	
	@Override
    protected void executeInternal(JobExecutionContext arg0)
            throws JobExecutionException {
        try {
        	myJMSBean.sendMessage();
        } catch (Exception e)
        {
        	e.printStackTrace();
        }
    }
 
    public void setMyJMSBean(MyJMSBean myJMSBean) {
        this.myJMSBean = myJMSBean;
    } 

}
