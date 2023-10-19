package com.main.x_base.message;

import com.main.x_base.message.callback.ProtectedUnPeekLiveData;

public class Result<T> extends ProtectedUnPeekLiveData<T> {

    public Result(T value) {
        super(value);
    }

    public Result() {
        super();
    }

}
