package one.block.eosiojava.error.session;

public class TransactionProcessorError extends Exception {

    public TransactionProcessorError() {
    }

    public TransactionProcessorError(String s) {
        super(s);
    }

    public TransactionProcessorError(String s, Throwable throwable) {
        super(s, throwable);
    }

    public TransactionProcessorError(Throwable throwable) {
        super(throwable);
    }

    public TransactionProcessorError(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
