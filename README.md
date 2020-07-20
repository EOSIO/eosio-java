![Java Logo](img/java-logo.png)
# EOSIO SDK for Java ![EOSIO Alpha](https://img.shields.io/badge/EOSIO-Alpha-blue.svg)

[![Software License](https://img.shields.io/badge/license-MIT-lightgrey.svg)](./LICENSE)
![Language Java](https://img.shields.io/badge/Language-Java-yellow.svg)
![](https://img.shields.io/badge/Deployment%20Target-JVM-blue.svg)

EOSIO SDK for Java is an API for integrating with EOSIO-based blockchains using the [EOSIO RPC API](https://developers.eos.io/eosio-nodeos/reference).  For a high-level introduction to our Swift and Java SDKs, check out our announcement on Medium: [EOSIO™ Software Release: Native SDKs for Swift and Java](https://medium.com/eosio/eosio-software-release-native-sdks-for-swift-and-java-e6086ddd37b8).

To date, EOSIO SDK for Java has only been tested on Android. The goal, however, is for the core library to run anywhere Java runs, adding other targets (server, desktop, embedded) as the library matures.

*All product and company names are trademarks™ or registered® trademarks of their respective holders. Use of them does not imply any affiliation with or endorsement by them.*

## Contents

- [Updates](#updates)
- [Installation](#installation)
- [Helpful Utilities](#helpful-utilities)
- [Basic Usage](#basic-usage)
- [Android Example App](#android-example-app)
- [Provider Interface Architecture](#provider-interface-architecture)
- [Want to Help?](#want-to-help)
- [License & Legal](#license)

## Updates

7/7/20

Version 0.1.3 Allows for deserialization of action return value structs.

2/25/20

Version 0.1.2 Uses JDK11 to build and targets 1.8 for source and target compatibility.

2/21/20

Version 0.1.1 Fixes a transaction expiration error.

## Installation

### Prerequisites

* Java JDK 1.8+ (1.8 source compatibility is targeted)
* Gradle 4.10.1+
* For Android, Android 6 (Marshmallow)+

***Note:** Android 6 (Marshmallow) was selected as the minimum target level due to Keystore security concerns in older versions of Android.

Since EOSIO SDK for Java is not an Android specific project, we recommend using IntelliJ if you are going to work on it.  You can use Android Studio but be aware that some of the menu options under Build like `Rebuild Project` and `Clean Project` will not work correctly.  You may still compile within Android Studio using `Make Project` under the Build menu, or by using Gradle from the command line.

### Instructions

To use EOSIO SDK for Java in your app, add the following modules to your build.gradle:

```groovy
implementation 'one.block:eosiojava:0.1.2'
implementation 'one.block:eosiojavasoftkeysignatureprovider:0.1.3'
implementation 'one.block:eosiojavaandroidabieosserializationprovider:0.1.1'
implementation 'one.block:eosiojavarpcprovider:0.1.1'
```

If you are using EOSIO SDK for Java, or any library that depends on it, in an Android application, you must also add the following to your application's `build.gradle` file in the `android` section:

```groovy
// Needed to get bitcoin-j to produce a valid apk for android.
packagingOptions {
    exclude 'lib/x86_64/darwin/libscrypt.dylib'
    exclude 'lib/x86_64/freebsd/libscrypt.so'
    exclude 'lib/x86_64/linux/libscrypt.so'
}
```
The `build.gradle` files for the project currently include configurations for publishing the project to Artifactory and Bintray.  These should be removed if you are not planning to use Artifactory and Bintray or you will encounter build errors.  To do so, make the changes marked by comments throughout the files.

Then refresh your gradle project. Then you're all set for the [Basic Usage](#basic-usage) example!

## Helpful Utilities

One of the most complicated and time consuming tasks about encryption can be figuring out how to transform keys into a format that works on the target blockchain.  This library includes two utilities that make that process painless.  The `EOSFormatter` and `PEMProcessor` classes include methods that allow you to convert a PEM or DER encoded public or private key fo and from the standard EOS formats.  The `PEMProcessor` wraps a key and gives you the ability to extract the type, the DER format, the algorithm used to generate the key, and to perform a checksum validation.  The `EOSFormatter` utility converts keys between DER or PEM and the EOS format and formats signatures and transactions into an EOS compliant format as well.  There is an abundance of documentation on the Internet about converting keys and signatures to a DER encoded or PEM (Privacy Enhanced Mail) format (See [PEM](https://tools.ietf.org/html/rfc1421) and [DER](https://www.itu.int/ITU-T/studygroups/com17/languages/X.690-0207.pdf)).  If you can get your key into one of these formats we provide a simple transition to the EOS format.

## Basic Usage

Transactions are instantiated via a `TransactionSession()` which must be configured with a number of providers and a `TransactionProcessor()`, which manipulates and performs actions on a Transaction, prior to use. The code below shows a very barebones flow. Error handling has been omitted for clarity but should be handled in normal usage. (See [Provider Interface Architecture](#provider-interface-architecture) below for more information about providers.)

```java
IRPCProvider rpcProvider = new EosioJavaRpcProviderImpl("https://baseurl.com/v1/");
ISerializationProvider serializationProvider = new AbiEosSerializationProviderImpl();
IABIProvider abiProvider = new ABIProviderImpl(rpcProvider, serializationProvider);
ISignatureProvider signatureProvider = new SoftKeySignatureProviderImpl();

signatureProvider.importKey(privateKeyK1EOS);
// or...
signatureProvider.importKey(privateKeyR1EOS);

TransactionSession session = new TransactionSession(
        serializationProvider,
        rpcProvider,
        abiProvider,
        signatureProvider
);

TransactionProcessor processor = session.getTransactionProcessor();

String jsonData = "{\n" +
        "\"from\": \"person1\",\n" +
        "\"to\": \"person2\",\n" +
        "\"quantity\": \"10.0000 EOS\",\n" +
        "\"memo\" : \"Something\"\n" +
        "}";

List<Authorization> authorizations = new ArrayList<>();
authorizations.add(new Authorization("myaccount", "active"));
List<Action> actions = new ArrayList<>();
actions.add(new Action("eosio.token", "transfer", authorizations, jsonData));

processor.prepare(actions);

PushTransactionResponse pushTransactionResponse = processor.signAndBroadcast();
```

## Android Example App

If you'd like to see EOSIO SDK for Java in action, check out our open source [Android Example App](https://github.com/EOSIO/eosio-java-android-example-app)--a working application that fetches an account's token balance and pushes a transfer action.

## Provider Interface Architecture

The core EOSIO SDK for Java library uses a provider-interface-driven architecture to provide maximum flexibility in a variety of environments and use cases. `TransactionSession` and `TransactionProcessor` leverages those providers to prepare and process transactions. EOSIO SDK for Java exposes four interfaces. You, the developer, get to choose which conforming implementations to use.

### Signature Provider Protocol

The Signature Provider abstraction is arguably the most useful of all of the providers. It is responsible for _a)_ finding out what keys are available for signing and _b)_ requesting and obtaining transaction signatures with a subset of the available keys.

By simply switching out the signature provider on a transaction, signature requests can be routed any number of ways. Need a signature from keys in the platform's Keystore or hardware backed security module such as Titan M? Configure the `TransactionSession` with a conforming signature provider that exposes that functionality. Need signatures from a wallet on the user's device? A signature provider can do that too!

EOSIO SDK for Java _does not include_ a signature provider implementation; one must be installed separately.

* [ISignatureProvider](eosiojava/src/main/java/one/block/eosiojava/interfaces/ISignatureProvider.java)
* [Softkey Signature Provider](https://github.com/EOSIO/eosio-java-softkey-signature-provider) - Example signature provider for signing transactions using SECP256R1 and SECP256R1 keys in memory.*

*_Softkey Signature Provider stores keys in memory and is therefore not secure. It should only be used for development purposes. In production, we strongly recommend using a signature provider that interfaces with a secure vault, authenticator or wallet._

### RPC Provider Protocol

The RPC Provider is responsible for all [RPC calls to nodeos](https://developers.eos.io/eosio-nodeos/reference), as well as general network handling (offline detection, retry logic, etc.)

EOSIO SDK for Java _does not include_ an RPC provider implementation; one must be installed separately.

* [IRPCProvider](eosiojava/src/main/java/one/block/eosiojava/interfaces/IRPCProvider.java)
* [Default RPC Provder](https://github.com/EOSIO/eosio-java-android-rpc-provider) - Currently supports Android 6 (Marshmallow)+
* [Nodeos RPC Reference Documentation](https://developers.eos.io/eosio-nodeos/reference)

*_Alternate RPC providers can be used assuming they conform to the minimal [RPC Provider Interface](eosiojava/src/main/java/one/block/eosiojava/interfaces/IRPCProvider.java). The core EOSIO SDK for Java library depends only on the five RPC endpoints set forth in that Interface. Other endpoints, however, are planned to be exposed in the [default RPC provider](https://github.com/EOSIO/eosio-java-android-rpc-provider)._

### Serialization Provider Protocol

The Serialization Provider is responsible for ABI-driven transaction, action, and struct serialization and deserialization between JSON and binary data representations. These implementations often contain platform-sensitive C++ code and larger dependencies. For those reasons, EOSIO SDK for Java _does not include_ a serialization provider implementation; one must be installed separately.

* [ISerializationProvider](eosiojava/src/main/java/one/block/eosiojava/interfaces/ISerializationProvider.java)
* [ABIEOS Serialization Provider Implementation](https://github.com/EOSIO/eosio-java-android-abieos-serialization-provider) - Currently supports Android 6 (Marshmallow)+

### ABI Provider Protocol

The ABI Provider is responsible for fetching and caching ABIs for use during serialization and deserialization. One must be explicitly set on the `TransactionSession` with the other providers. EOSIO SDK for Java provides a default [ABIProviderImpl](eosiojava/src/main/java/one/block/eosiojava/implementations/ABIProviderImpl.java) that can be used. (The default implementation suffices for most use cases.)

* [IABIProvider](eosiojava/src/main/java/one/block/eosiojava/interfaces/IABIProvider.java)
* [Default ABIProviderImpl Implementation](eosiojava/src/main/java/one/block/eosiojava/implementations/ABIProviderImpl.java)

### Design document

For more details about the complete workflow of EOSIO SDK for Java, see [`EOSIO SDK for Java - Complete workflow`](https://github.com/EOSIO/eosio-java/tree/master/documents/complete_workflow.pdf).

An overview of the error model used in this library can be found in the [`EOSIO SDK for Java - Error Model`](https://github.com/EOSIO/eosio-java/tree/master/documents/error_model.pdf)

## Want to help?

Interested in contributing? That's awesome! Here are some [Contribution Guidelines](./CONTRIBUTING.md) and the [Code of Conduct](./CONTRIBUTING.md#conduct).

We're always looking for ways to improve EOSIO SDK for Java. Check out our [#enhancement Issues](/../../issues?q=is%3Aissue+is%3Aopen+label%3Aenhancement) for ways you can pitch in.

## License

[MIT](./LICENSE)

## Important

See LICENSE for copyright and license terms.  Block.one makes its contribution on a voluntary basis as a member of the EOSIO community and is not responsible for ensuring the overall performance of the software or any related applications.  We make no representation, warranty, guarantee or undertaking in respect of the software or any related documentation, whether expressed or implied, including but not limited to the warranties of merchantability, fitness for a particular purpose and noninfringement. In no event shall we be liable for any claim, damages or other liability, whether in an action of contract, tort or otherwise, arising from, out of or in connection with the software or documentation or the use or other dealings in the software or documentation. Any test results or performance figures are indicative and will not reflect performance under all conditions.  Any reference to any third party or third-party product, service or other resource is not an endorsement or recommendation by Block.one.  We are not responsible, and disclaim any and all responsibility and liability, for your use of or reliance on any of these resources. Third-party resources may be updated, changed or terminated at any time, so the information here may be out of date or inaccurate.  Any person using or offering this software in connection with providing software, goods or services to third parties shall advise such third parties of these license terms, disclaimers and exclusions of liability.  Block.one, EOSIO, EOSIO Labs, EOS, the heptahedron and associated logos are trademarks of Block.one.

Wallets and related components are complex software that require the highest levels of security.  If incorrectly built or used, they may compromise users’ private keys and digital assets. Wallet applications and related components should undergo thorough security evaluations before being used.  Only experienced developers should work with this software.
