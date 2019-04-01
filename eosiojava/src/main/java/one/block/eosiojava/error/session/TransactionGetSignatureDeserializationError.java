package one.block.eosiojava.error.session;

public class TransactionGetSignatureDeserializationError extends TransactionGetSignatureError {

    public TransactionGetSignatureDeserializationError() {
    }

    public TransactionGetSignatureDeserializationError(String s) {
        super(s);
    }

    public TransactionGetSignatureDeserializationError(String s, Throwable throwable) {
        super(s, throwable);
    }

    public TransactionGetSignatureDeserializationError(Throwable throwable) {
        super(throwable);
    }

    public TransactionGetSignatureDeserializationError(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
