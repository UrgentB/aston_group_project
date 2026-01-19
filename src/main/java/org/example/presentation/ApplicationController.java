package org.example.presentation;

import org.example.application.InputStrategy;
import org.example.application.Service;

/**
 * Abstract controller class for the application.
 * @param <T> the type of elements being processed
 */

public abstract class ApplicationController<T> {
    protected InputStrategy<T> inputStrategy;
    protected Service<T> service;
    
    public abstract void run();
}
