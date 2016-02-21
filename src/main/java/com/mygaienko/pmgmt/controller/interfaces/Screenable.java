package com.mygaienko.pmgmt.controller.interfaces;

import com.mygaienko.pmgmt.screenframework.ScreensMediator;

public interface Screenable {
    
    //This method will allow the injection of the Parent ScreenPane
    public void setScreenParent(ScreensMediator screenPage);
    public void initScreen();
}