package com.linkedin.android.testbutler;

import java.util.Locale;

class SystemLocaleChanger {
    boolean setSystemLocale(String language, String country) {
        return setSystemLocale(new Locale(language, country));
    }

    boolean setSystemLocale(Locale locale) {
        try {
            Class<?> activityManagerNative = Class.forName("android.app.ActivityManagerNative");
            Object am = activityManagerNative.getMethod("getDefault").invoke(activityManagerNative);
            Object config = am.getClass().getMethod("getConfiguration").invoke(am);
            config.getClass().getDeclaredField("locale").set(config, locale);
            config.getClass().getDeclaredField("userSetLocale").setBoolean(config, true);
            am.getClass().getMethod("updateConfiguration", android.content.res.Configuration.class).invoke(am, config);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
