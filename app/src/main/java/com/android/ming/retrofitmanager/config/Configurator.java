package com.android.ming.retrofitmanager.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Interceptor;

public class Configurator {
    private static final Map<Object, Object> CONFIGS = new HashMap<>();
    private static final List<Interceptor> INTERCEPTORS = new ArrayList<>();

    //静态内部类实现单例
    private Configurator() {
        //默认false
        CONFIGS.put(ConfigKey.CONFIG_READY.name(), false);
    }

    private static class Holder {
        private static final Configurator INSTANCE = new Configurator();
    }

    public static Configurator getInstance() {
        return Holder.INSTANCE;
    }


    //获取配置信息
    public final Map<Object, Object> getConfigs() {
        return CONFIGS;
    }

    //配置APIHOST(返回this,方便链式调用)
    public final Configurator withApiHost(String host){
        CONFIGS.put(ConfigKey.API_HOST.name(),host);
        return this;
    }

    /**
     * 根据key获取配置信息
     * @param key
     * @param <T>
     * @return
     */
    public <T> T getConfiguration(Object key) {
        checkConfiguration();
        final Object value = CONFIGS.get(key);
        if (value == null) {
            throw new NullPointerException(key.toString() + "IS NULL");
        }
        return (T) CONFIGS.get(key);
    }

    /**
     * 检查配置是否完成
     */
    private void checkConfiguration(){
        final  boolean isReady = (boolean) CONFIGS.get(ConfigKey.CONFIG_READY.name());
        if (!isReady) {
            throw new RuntimeException("Configuration is not ready,call configure()");
        }
    }

    /**
     * 配置完成
     */
    public final void configure(){
        CONFIGS.put(ConfigKey.CONFIG_READY,true);
    }
}
