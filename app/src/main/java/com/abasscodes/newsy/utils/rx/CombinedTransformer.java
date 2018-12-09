package com.abasscodes.newsy.utils.rx;

import io.reactivex.CompletableTransformer;
import io.reactivex.FlowableTransformer;
import io.reactivex.MaybeTransformer;
import io.reactivex.ObservableTransformer;
import io.reactivex.SingleTransformer;

public interface CombinedTransformer<R> extends
        ObservableTransformer<R, R>,
        FlowableTransformer<R, R>,
        SingleTransformer<R, R>,
        CompletableTransformer,
        MaybeTransformer<R, R> {
}
