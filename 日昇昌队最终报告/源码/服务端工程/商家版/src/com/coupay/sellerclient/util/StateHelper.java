package com.coupay.sellerclient.util;

import javax.media.ConfigureCompleteEvent;  
import javax.media.ControllerEvent;  

import javax.media.Player;  
import javax.media.Processor;  
  
import javax.media.RealizeCompleteEvent;  

  

public class StateHelper implements javax.media.ControllerListener   
{   
Player xplayer = null;  
boolean configured = false;  
boolean realized = false;  
boolean failed = false;  
boolean closed = false;   
  
public StateHelper(Player p)   
{   
    xplayer = p;   
    p.addControllerListener(this);   
}   

public boolean configure(int timeOutMillis)   
{   
//RealizeCompleteEvent�����˵Ļ�ʹce�¼���֮�Ƚϣ�����ȣ���ôrealizedΪtrue��   
/*����ConfigureCompleteEvent��ConfigureCompleteEvent�¼��ķ�����  
* ��ConfigureCompleteEvent�¼���������ô�ͻḳ��configuredΪture��  
* ʹ��public boolean configure�����е�  
* while (!configured && !failed){}���ѭ���˳���*/   
long startTime = System.currentTimeMillis();   
synchronized (this)   
{   
    if (xplayer instanceof Processor)   
        ((Processor) xplayer).configure();   
    else   
        return false;   
    while (!configured && !failed)   
    {   
        try   
        {   
            wait(timeOutMillis);   
        } catch (InterruptedException ie)   
        {   
    }   
        if (System.currentTimeMillis() - startTime > timeOutMillis)   
            break;   
    }   
}   
    return configured;   
}   

public boolean realize(int timeOutMillis)   
{   
    long startTime = System.currentTimeMillis();   
    synchronized (this)   
    {   
    xplayer.realize();   
    while (!realized && !failed)   
    {   
    try   
    {   
    wait(timeOutMillis);   
    } catch (InterruptedException ie)   
    {   
    }   
    if (System.currentTimeMillis() - startTime > timeOutMillis)   
    break;   
    }   
    }   
    return realized;   
    }   
  
    public synchronized void controllerUpdate(ControllerEvent ce)   
    {   
    if (ce instanceof RealizeCompleteEvent)   
    {   
    realized = true;   
    } else if (ce instanceof ConfigureCompleteEvent)   
    {   
    configured = true;   
    } else   
    {   
    return;   
    }   
    notifyAll();   
    }   
}//