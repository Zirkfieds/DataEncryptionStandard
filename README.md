# Data Encryption Standard Algorithm
An implementation of the famous DES algorithm in Java with GUI

### Project Description ###

`DES`, or `Data Encryption Standard` is an encryption algorithm developed in early 1970s. It is mainly used for protecting sensitive electronic data. DES ciphers a given `Plain Text` into a `Cipher Text` by using a given `Key`, and is capable of doing the reverse process of it. In this project, I used Java and its GUI components to create a simple implementation of the DES Algorithm.

### Brief Introduction of My Implementation ###

DES Algorithm is a symmetric encryption algorithm that the encrypted data can be recovered from cipher only by using exactly the same key used to encipher it. Unauthorized recipients of the cipher who know the algorithm but do not have the correct key cannot derive the original data algorithmically. A DES key consists of 64 binary digits ("0"s or "1"s) of which 56 bits are randomly generated and used directly by the algorithm. In order to address the fixed-length trait of DES, all of the inputs that this demo accept have strict restrictions (“0x” followed by 16 hex digits, two consecutive digits will be unpacked into a character).

The implementation mainly focused on displaying the `Key Sequence`, which is generated and participated in each iteration of the encryption/decryption process, and is aimed to show how these keys are being used. In DES, the key sequence are generated and being used in different order to achieve the goal of encryption/decryption, which is crucial to full understandings of this algorithm.

A more detailed explanation of how the algorithm works can be found on the Internet, either in flowchart or in visual demonstration, as I will not expand on it in details in this document since it has been transcribed and translated into the form of source code that is also included in this repository.

### How to use it ###

1. Run the DES_Demo.jar
2. Input a hex that has 16 digits sharp and starts with "0x", which can be either the input or the output.
3. Input a hex that has 16 digits sharp and starts with "0x", which will be used as the key.
4. Click `Encrypt` or `Decrypt` Button to execute the respective operation on the given texts and the key.
5. Click `Logs` to browse the full key sequence used in the encryption/decryption operation.
6. Click `Clear All` to reset all input areas to empty, allowing a faster reset for all inputs.
