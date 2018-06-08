package com.android.ming.retrofitmanager.config;

import android.content.Context;

public class ProjectInit {
    public static Configurator init(Context context){
        Configurator.getInstance()
                .getConfigs()
                .put(ConfigKey.APPLICATION_CONTEXT.name(),context.getApplicationContext());
        return Configurator.getInstance();
    }

    public static  Configurator getConfigurator(){
        return Configurator.getInstance();
    }

    public static <T> T getConfiguration(Object key){
        return getConfigurator().getConfiguration(key);
    }

    public static Context getApplication(){
        return getConfiguration(ConfigKey.APPLICATION_CONTEXT.name());
    }
}
