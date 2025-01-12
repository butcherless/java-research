package dev.cmartin.learn.generics;

import java.util.function.Supplier;

interface ErrorHandler {
    /**
     * Executes a supplier operation with standard error handling.
     *
     * @param supplier The operation to execute
     * @param <T>      The return type (Boolean, Integer, OrderStatusResponse, etc.)
     * @return The result of the operation
     * @throws ServiceUnavailableException if other errors occur
     */
    static <T> T execute(final Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (final Exception e) {
            throw new ServiceUnavailableException(e);
        }
    }
}