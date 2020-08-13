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
     * @param data the request
     * @return the response from the queue that message was received
     */
    @NotNull
    Completable send(byte[] data);
}
