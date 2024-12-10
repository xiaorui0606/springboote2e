package com.nuance.ent.cc.e2e.cloudtest.utility;

import java.time.Duration;
import java.util.function.Predicate;
import net.jodah.failsafe.Failsafe;
import net.jodah.failsafe.RetryPolicy;
import net.jodah.failsafe.function.CheckedSupplier;

public class RetryUtil<T> {

    RetryPolicy<T> retryPolicy=null;
    Duration duration=Duration.ofSeconds(20);
    Integer maxTry=10;
    Duration delayDuration=Duration.ofMillis(3000l);
    private Predicate<T> pollResultPredicate = null;
    private CheckedSupplier<T> pollMethod = null;


    public RetryUtil<T> pollDuration(Integer maxDuration,Long delayInMilli,Integer maxTry)
    {
        if(maxDuration!=null&&maxDuration!=0)
            this.duration=Duration.ofSeconds(maxDuration);
        if(delayInMilli!=null&&maxDuration!=0)
            delayDuration=Duration.ofMillis(delayInMilli);
        if(maxTry!=null&&maxDuration!=0)
            this.maxTry=maxTry;
        return this;
    }

    public RetryUtil<T> pollDuration(Integer maxDuration,Long delayInMilli)
    {
        if(maxDuration!=null&&maxDuration!=0)
            this.duration=Duration.ofSeconds(maxDuration);
        if(delayInMilli!=null&&maxDuration!=0)
            delayDuration=Duration.ofMillis(delayInMilli);
        return this;
    }


    public RetryUtil<T> setRetryPolicy(Predicate<T> predicate)
    {
        retryPolicy = new RetryPolicy<T>()
                .handleResultIf(predicate.negate())
                .withDelay(delayDuration)
                .withMaxDuration(duration)
                .withMaxRetries(maxTry);
        pollResultPredicate = predicate;
        return this;
    }

    public RetryUtil<T> method(CheckedSupplier<T> supplier)
    {
        try {
            pollMethod = supplier;
        }catch(Exception e) {
            e.printStackTrace();
        }
        return this;
        //return Failsafe.with(retryPolicy).get(supplier);
    }

    public T execute()
    {
        T result=null;
        result=Failsafe.with(retryPolicy).get(pollMethod);
        resetDuration();
        return result;
    }

    public void resetDuration()
    {
        duration=Duration.ofSeconds(20);
        maxTry=10;
        delayDuration=Duration.ofMillis(3000l);
    }
}
