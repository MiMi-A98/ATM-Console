# Automated Teller Machine (ATM) Project

The Automated Teller Machine (ATM) project is a Java-based simulation of an ATM system. It provides functionalities such
as account balance inquiries, withdrawals, deposits, money transfers, and PIN change. This project demonstrates the use
of object-oriented programming principles in a financial application context.

## Features

- **Login:** Imitates the real-life authentication at an ATM using a card and PIN.
- **View accounts info:** Lets you see the basic info of the accounts you own. (Account type, Balance, etc.)
- **View Balance:** Check the balance of the checking account associated to the current card.
- **Withdraw Funds:** Withdraw money from the checking account associated to the current card.
- **Deposit Funds:** Deposit money into the checking account associated to the current card.
- **Transfer Money:** Transfer money between different owned bank accounts.
- **Change PIN:** Change the card PIN.
- **Logout:** Securely logout from the ATM system.

## Tech Stack

- **Java:** Main platform and language used to create and run the application.
    - **JUnit:** Framework for writing and running tests to ensure code quality and correctness.
    - **Mockito:** Framework for creating mock objects in tests to isolate and test individual components.
    - **Hibernate:** An ORM (Object-Relational Mapping) framework used to map Java objects to database tables, providing
      a high-level abstraction for database interactions.
- **Maven:** Dependency management and build automation tool that ensures all required libraries are included and
  up-to-date.
- **MySQL:** Relational database management system used for persisting ATM data.
- **H2:** Default relational database management system used for persisting ATM data.

## Class diagram

This project uses a layered architecture, a common approach to separate concerns, promote reusability, and facilitate
maintenance.

- **Main app and utility classes**
  ![ATM](projectDiagrams/ATM.jpg)

- **Service classes**
  ![service](projectDiagrams/service.jpg)

- **DAO classes**
  ![dao](projectDiagrams/dao.jpg)

- **Entity classes**
  ![entity](projectDiagrams/entity.jpg)

## Run Locally

### Prerequisites

- JDK 21
- a DBMS (H2 is configured by default)

### Steps

Clone the project

```bash
git clone https://github.com/MiMi-A98/ATM-Console.git
```

Go to the project directory

```bash
cd my-project
```

Configure database connection and dependencies:

- In the `PERSISTENCE.XML` file found in `src\main\resources\META-INF` folder to match your database connection info

```xml

<persistence>
    <persistence-unit name="<YOUR DB NAME>" transaction-type="RESOURCE_LOCAL">
        ...
        <properties>
            <property name="jakarta.persistence.jdbc.driver" value="<YOUR DB DRIVER>"/>
            <property name="jakarta.persistence.jdbc.url" value="<YOUR DB URL>"/>
            <property name="jakarta.persistence.jdbc.user" value="<YOUR DB USER>"/>
            <property name="jakarta.persistence.jdbc.password" value="<YOUR DB PASSWORD>"/>

            <property name="hibernate.dialect" value="org.hibernate.dialect.<YOUR DB DIALECT>"/>
        </properties>
    </persistence-unit>
</persistence>
```

- In the `pom.xml` file found in the root folder, add the database connector dependency (default is MySQL)

```xml

<project>
    <dependencies>
        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
            <version>8.2.0</version>
        </dependency>
    </dependencies>
</project>
```

Compile the source code

```bash
./mvnw clean compile
```

Run the app

```bash
./mvnw exec:java
```

## Running Tests

To run tests, run the following command from the root folder of the project:

```bash
./mvnw clean test
```

