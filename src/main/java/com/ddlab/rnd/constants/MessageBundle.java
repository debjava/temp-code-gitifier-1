package com.ddlab.rnd.constants;

import com.intellij.DynamicBundle;
import org.jetbrains.annotations.NotNull;

public class MessageBundle extends DynamicBundle  {

    private static final MessageBundle INSTANCE = new MessageBundle();

    protected MessageBundle() {
        super("messages.MyMessageBundle");
    }

    public static String message(String key, Object... params) {
        return INSTANCE.getMessage(key, params);
    }
}
