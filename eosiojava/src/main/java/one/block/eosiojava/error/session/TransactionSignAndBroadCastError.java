package one.block.eosiojava.error.session;

public class TransactionSignAndBroadCastError extends TransactionProcessorError {

    public TransactionSignAndBroadCastError() {
    }

    public TransactionSignAndBroadCastError(String s) {
        super(s);
    }

    public TransactionSignAndBroadCastError(String s, Throwable throwable) {
        super(s, throwable);
    }

    public TransactionSignAndBroadCastError(Throwable throwable) {
        super(throwable);
    }

    public TransactionSignAndBroadCastError(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
