package one.block.eosiojava.error.session;

public class TransactionSerializeError extends TransactionProcessorError {

    public TransactionSerializeError() {
    }

    public TransactionSerializeError(String s) {
        super(s);
    }

    public TransactionSerializeError(String s, Throwable throwable) {
        super(s, throwable);
    }

    public TransactionSerializeError(Throwable throwable) {
        super(throwable);
    }

    public TransactionSerializeError(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
