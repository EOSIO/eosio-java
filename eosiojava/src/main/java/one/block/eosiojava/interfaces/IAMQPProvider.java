package one.block.eosiojava.interfaces;

import io.reactivex.Completable;
import org.jetbrains.annotations.NotNull;

/**
 * The interface of AMQP provider.
 */
public interface IAMQPProvider {

    /**
     * Sends an AMQP message to configured queue
     *
     * @param message the message to send to AMQP
     * @return the response from the queue that message was received
     */
    @NotNull
    Completable send(String message);
}
