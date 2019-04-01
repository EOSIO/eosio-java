package one.block.eosiojava.error.session;

public class TransactionProcessorConstructorInputError extends TransactionProcessorError {

    public TransactionProcessorConstructorInputError() {
    }

    public TransactionProcessorConstructorInputError(String s) {
        super(s);
    }

    public TransactionProcessorConstructorInputError(String s, Throwable throwable) {
        super(s, throwable);
    }

    public TransactionProcessorConstructorInputError(Throwable throwable) {
        super(throwable);
    }

    public TransactionProcessorConstructorInputError(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
