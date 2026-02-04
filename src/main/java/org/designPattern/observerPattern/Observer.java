package org.designPattern.observerPattern;

import DistribuedJobScheduler.Job.Job;

public interface Observer {
    void update(String message);
}
