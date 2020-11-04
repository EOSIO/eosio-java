# EOSIO SDK for Java Examples

EOSIO SDK for Java contains an extensive set of functionality beyond the basics required for transactions.  The code snippets below show how to use some of this extended functionality.  It is important to note that these are simply example snippets and may not work the way you expect if you just copy and paste them into a method.  

Note: For clarity, these examples use the soft key signature provider which is NOT recommended for production use!

## Basic Transaction Examples

### Submitting a Transaction

Basic submission of a transaction is shown in the main [README.md](README.md) file at the top level of the repository.  Please see the [Basic Usage](README.md/#basic-usage) example for details.

### How to Transfer an EOSIO Token

The [Basic Usage](README.md/#basic-usage) example in the top level [README.md](README.md) file is an example of transferring an EOSIO token.  Please see that example for details.

## Extended Transaction Examples

### Retrieving Action Return Values From Transactions

This snippet calls a transaction that returns a 32-bit numeric value.  The user is required to know the correct type that the action returns in order to cast successfully.  Each action will contain its return value, if the server provided one.

```java
// Creating serialization provider
ISerializationProvider serializationProvider;
try {
    serializationProvider = new AbiEosSerializationProviderImpl();
} catch (SerializationProviderError serializationProviderError) {
    serializationProviderError.printStackTrace();
    return null;
}

// Creating RPC Provider
IRPCProvider rpcProvider;
try {
    rpcProvider = new EosioJavaRpcProviderImpl("https://my.test.blockchain", ENABLE_NETWORK_LOG);
} catch (EosioJavaRpcProviderInitializerError eosioJavaRpcProviderInitializerError) {
    eosioJavaRpcProviderInitializerError.printStackTrace();
    println(Boolean.toString(false), eosioJavaRpcProviderInitializerError.getMessage());
    return null;
}

// Creating ABI provider
IABIProvider abiProvider = new ABIProviderImpl(rpcProvider, serializationProvider);

// Creating Signature provider
ISignatureProvider signatureProvider = new SoftKeySignatureProviderImpl();

try {
    ((SoftKeySignatureProviderImpl) signatureProvider).importKey("MyPrivateKey");
} catch (ImportKeyError importKeyError) {
    importKeyError.printStackTrace();
    println(Boolean.toString(false), importKeyError.getMessage());
    return null;
}

// Creating TransactionProcess
TransactionSession session = new TransactionSession(serializationProvider, rpcProvider, abiProvider, signatureProvider);
TransactionProcessor processor = session.getTransactionProcessor();

String jsonData = "{}";

// Creating action for return value call.

Action action = new Action("returnvalue", "actionresret", Collections.singletonList(new Authorization(fromAccount, "active")), jsonData);
int index = 0;
try {

    // Prepare transaction with above action. A transaction can be executed with multiple action.
    println("Preparing Transaction...");
    processor.prepare(Collections.singletonList(action));

    // Sign and broadcast the transaction.
    println("Signing and Broadcasting Transaction...");
    SendTransactionResponse response = processor.signAndBroadcast();

    Double actionReturnValue = response.getActionValueAtIndex(index, Double.class);

    println("Your transaction id is:  " + response.getTransactionId());
    println(Boolean.toString(true), "Finished!  Your action return value is:  " + actionReturnValue.toString());
} catch (TransactionPrepareError transactionPrepareError) {
    // Happens if preparing transaction unsuccessful
    transactionPrepareError.printStackTrace();
    println(Boolean.toString(false), transactionPrepareError.getLocalizedMessage());
} catch (TransactionSignAndBroadCastError transactionSignAndBroadCastError) {
    // Happens if Sign transaction or broadcast transaction unsuccessful.
    transactionSignAndBroadCastError.printStackTrace();

    // try to get backend error if the error come from backend
    RPCResponseError rpcResponseError = ErrorUtils.getBackendError(transactionSignAndBroadCastError);
    if (rpcResponseError != null) {
        String backendErrorMessage = ErrorUtils.getBackendErrorMessageFromResponse(rpcResponseError);
        println(Boolean.toString(false), backendErrorMessage);
        return null;
    }

    println(Boolean.toString(false), transactionSignAndBroadCastError.getMessage());
} catch (IndexOutOfBoundsException outOfBoundsError) {
    println(Boolean.toString(false), "No action value at index: " + index);
} catch (ClassCastException castError) {
    println(Boolean.toString(false), "Cannot cast action value to requested class");
}
```

### Accessing Extended Fields in Transaction Responses

Using `signAndBroadcast()` provides an easy way to sign and submit transactions but the `SendTransactionResponse` defined in the `IRPCProvider` interface only specifically unmarshalls the transaction ID.  The remainder of the response decodes into a `Map` and requires additional processing to decode values from it.  In the example below some extra response properties are decoded. The [NODEOS Reference](https://developers.eos.io/eosio-nodeos/reference) is helpful for decoding other parts of responses that are not fully unmarshalled.  

```java
// Creating serialization provider
ISerializationProvider serializationProvider;
try {
    serializationProvider = new AbiEosSerializationProviderImpl();
} catch (SerializationProviderError serializationProviderError) {
    serializationProviderError.printStackTrace();
    return null;
}

// Creating RPC Provider
IRPCProvider rpcProvider;
try {
    rpcProvider = new EosioJavaRpcProviderImpl("https://my.test.blockchain", ENABLE_NETWORK_LOG);
} catch (EosioJavaRpcProviderInitializerError eosioJavaRpcProviderInitializerError) {
    eosioJavaRpcProviderInitializerError.printStackTrace();
    println(Boolean.toString(false), eosioJavaRpcProviderInitializerError.getMessage());
    return null;
}

// Creating ABI provider
IABIProvider abiProvider = new ABIProviderImpl(rpcProvider, serializationProvider);

// Creating Signature provider
ISignatureProvider signatureProvider = new SoftKeySignatureProviderImpl();

try {
    ((SoftKeySignatureProviderImpl) signatureProvider).importKey("MyPrivateKey");
} catch (ImportKeyError importKeyError) {
    importKeyError.printStackTrace();
    println(Boolean.toString(false), importKeyError.getMessage());
    return null;
}

// Creating TransactionProcess
TransactionSession session = new TransactionSession(serializationProvider, rpcProvider, abiProvider, signatureProvider);
TransactionProcessor processor = session.getTransactionProcessor();

// Apply transaction data to Action's data
String jsonData = "{\n" +
        "\"from\": \"" + fromAccount + "\",\n" +
        "\"to\": \"" + toAccount + "\",\n" +
        "\"quantity\": \"" + amount + "\",\n" +
        "\"memo\" : \"" + memo + "\"\n" +
        "}";

// Creating action with action's data, eosio.token contract and transfer action.
Action action = new Action("eosio.token", "transfer", Collections.singletonList(new Authorization(fromAccount, "active")), jsonData);
int index = 0;
try {

    // Prepare transaction with above action. A transaction can be executed with multiple action.
    println("Preparing Transaction...");
    processor.prepare(Collections.singletonList(action));

    // Sign and broadcast the transaction.
    println("Signing and Broadcasting Transaction...");
    SendTransactionResponse response = processor.signAndBroadcast();

    // Start parsing additional values from response.
    JSONObject processed = new JSONObject(response.getProcessed());
    JSONObject receipt = (JSONObject) processed.get("receipt");
    String status = (String) receipt.get("status");
    BigInteger cpuUsage = (BigInteger) receipt.get("cpu_usage_us");
    // Extract other values as desired

    println(Boolean.toString(true), "Finished!  Your transaction id is:  " + response.getTransactionId());
} catch (TransactionPrepareError transactionPrepareError) {
    // Happens if preparing transaction unsuccessful
    transactionPrepareError.printStackTrace();
    println(Boolean.toString(false), transactionPrepareError.getLocalizedMessage());
} catch (TransactionSignAndBroadCastError transactionSignAndBroadCastError) {
    // Happens if Sign transaction or broadcast transaction unsuccessful.
    transactionSignAndBroadCastError.printStackTrace();

    // try to get backend error if the error come from backend
    RPCResponseError rpcResponseError = ErrorUtils.getBackendError(transactionSignAndBroadCastError);
    if (rpcResponseError != null) {
        String backendErrorMessage = ErrorUtils.getBackendErrorMessageFromResponse(rpcResponseError);
        println(Boolean.toString(false), backendErrorMessage);
        return null;
    }

    println(Boolean.toString(false), transactionSignAndBroadCastError.getMessage());
}
```