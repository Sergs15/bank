# Bank API

A small API created to simulate transactions in a Blockchain enviroment.

## Getting Started

A Ehtereum simulator is needed to run the application. We can use Ganache (https://trufflesuite.com/ganache/) for that purpose.

Once we have at least one available ehtereum account, we start our application passing its private key as a property. This account will be used to make deposits in the wallets created inside the application.

```java -jar bank.jar --ganache.account.privatekey=<private_key>```

Login credentials:
    - Username: user1
    - Password: admin

http://localhost:8080 displays the OpenApi interface with all the operations available.
http://localhost:8080/h2-console displays the console for the h2 in memory database used.

## License

Copyright 2023 Sergio Martínez Fernández

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
