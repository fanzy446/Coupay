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
//RealizeCompleteEvent发生了的话使ce事件与之比较，若相等，那么realized为true。   
/*监听ConfigureCompleteEvent和ConfigureCompleteEvent事件的发生。  
* 如ConfigureCompleteEvent事件发生，那么就会赋给configured为ture，  
* 使得public boolean configure方法中的  
* while (!configured && !failed){}这个循环退出。*/   
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