package dev.cmartin.learn.generics;

import dev.cmartin.learn.generics.DomainModel.SendMailRequest;
import dev.cmartin.learn.generics.DomainModel.SendMailResponse;

import java.util.UUID;

/**
 * Interface for business service operations.
 */
public interface BusinessService {
    /**
     * Checks if there are pending items for the given process ID.
     *
     * @param processId the UUID of the process
     * @return true if there are pending items, false otherwise
     */
    Boolean hasPendingItems(UUID processId);

    /**
     * Gets the index for the given process ID.
     *
     * @param processId the UUID of the process
     * @return the index as an Integer
     */
    Integer getIndex(UUID processId);

    /**
     * Creates a reference alias for the given process ID.
     *
     * @param processId the UUID of the process
     * @return the created reference UUID
     */
    UUID createReference(UUID processId);

    /**
     * Sends an email based on the provided request.
     *
     * @param request the {@link SendMailRequest} object containing email details
     * @return the {@link SendMailResponse} object containing the response details
     */
    SendMailResponse sendMail(SendMailRequest request);
}
