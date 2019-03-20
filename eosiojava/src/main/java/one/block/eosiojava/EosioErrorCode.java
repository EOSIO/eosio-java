package one.block.eosiojava;

//
//  EosioErrorCode
//  eosio-java
//
//  Created by Steve McCoole on 1/30/19.
//  Copyright © 2018-2019 block.one.
//

public enum EosioErrorCode {
    biometricsDisabled("biometricsDisabled"),
    keychainError("keychainError"),
    manifestError("manifestError"),
    metadataError("metadataError"),
    networkError("networkError"),
    parsingError("parsingError"),
    resourceIntegrityError("resourceIntegrityError"),
    resourceRetrievalError("resourceRetrievalError"),
    signingError("signingError"),
    transactionError("transactionError"),
    vaultError("vaultError"),
    whitelistingError("whitelistingError"),
    malformedRequestError("malformedRequestError"),
    domainError("domainError"),
    eosioNameError("eosioNameError"),
    signatureProviderError("signatureProviderError"),
    serializationError("serializationError"),
    deserializationError("deserializationError"),
    dataCodingError("dataCodingError"),
    missingDataError("missingDataError"),
    eosioKeyError("eosioKeyError"),
    eosioSignatureError("eosioSignatureError"),

    // General catch all
    unexpectedError("unexpectedError");

    private final String text;

    EosioErrorCode(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
