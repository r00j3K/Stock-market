# Stock-market
This service implements a simplified stock market simulation.

# 🚀 Quick Start
The application is **fully containerized**. There is no need to have Java installed locally — **only Docker and Docker Compose are required**.

#### **1. Running the application** 
The startup scripts accept a port number as the first argument. If the provided port is invalid (not a number or outside the 1-65535 range), the system defaults to 8080. If `1` is passed as second argument, the service starts in an interactive mode, displaying all application logs. Below are some examples of script invocation.

**Windows**
```
:: Basic run (defaults to 8080, detached mode)
./run.bat
```

```
:: Run on specific port 
./run.bat 9090
```

```
:: Run in interactive mode 
./run.bat 8080 1
```
<br>

**Linux / macOS**
First, grant execute permission to the scripts

```
chmod +x *.sh
```

Then invoke one of the commands displayed below: 

```
# Basic run (defaults to 8080, detached mode)
./run.sh
```

```
# Run on specific port (e.g., 9090)
./run.sh 9090
```

```
# Run in interactive mode (foreground)
./run.sh 8080 1
```
<br>

#### **2. Stopping and cleanup**

To stop the application and remove containers, use the provided stop scripts.

**Windows**
```
./stop.bat
```
<br>

**Linux / macOS**
```
./stop.sh
```

<br>

> **⚠️ NOTE**: By default, the database volume is preserved. To perform a full reset (delete all wallets, bank state, and logs), use the `-v` flag:
>
 >**Windows**
> ```
> ./stop.bat -v
>```
><br>
>
>**Linux / macOS**
>```
>./stop.sh -v
>```
><br/>
# 🛠️ Tech Stack
### Core:

* **Java 21** (LTS): Selected as the project’s foundation due to its Long-Term Support status and proven stability in production environments. While newer versions are available, Java 21 remains the current industry standard, offering a perfect balance between modern features and a mature ecosystem of libraries and tools.

* **Spring Boot 3.5.14**: Using the latest patched version of the 3.5 branch for native Java 21 support, enhanced performance, and up-to-date security defaults. Optimized with Spring Boot Actuator for enhanced health monitoring and Docker-native lifecycle management.

### Data & Persistence:

* **PostgreSQL 16**: Selected as the centralized data store to maintain shared state across multiple service instances. Utilizing the official Docker image ensures consistent database behavior across all operating systems.

* **Spring Data JPA**: Configured with Pessimistic Locking (FOR UPDATE) to ensure transactional integrity and prevent race conditions during concurrent trades.

### Infrastructure & DevOps:

* **Docker & Docker Compose**: For full environment containerization and multi-arch support (x64/arm64).

* **Nginx Load Balancer**: Orchestrated via Docker Compose to satisfy the High Availability requirement, ensuring the system survives instance failure (POST /chaos endpoint).

### Productivity:

* **Lombok** : To maintain a clean, boilerplate-free codebase focused on business logic.

# 📝 Key Principles & Assumptions
To align with the task specification, the following simplifications were implemented:

* **Fixed Pricing**: All stock prices are fixed at 1.

* **Liquidity**: The Bank acts as the sole liquidity provider.

* **Wallet Management**: Wallet balance is not tracked; any trade operation on a non-existent wallet_id triggers its immediate creation.

* **Audit Log**: Only successful wallet operations are logged (bank-only operations are excluded).

# 🧠 Design Decisions
During development, several key architectural choices were made to satisfy the requirements of high availability and data consistency:

* **Pessimistic Locking Over Optimistic Locking**: In a stock market scenario with high contention (many users buying the same stock), optimistic locking would result in frequent ObjectOptimisticLockingFailureException and retries. Pessimistic Locking (FOR UPDATE) was chosen to ensure that stock quantity updates are serialized at the database level, guaranteeing 100% accuracy in balances.

* **Stateless Application Tier**: The service itself holds no in-memory state regarding wallets or stock levels. All state is offloaded to PostgreSQL. This enables seamless scaling—instances can be created or destroyed (e.g., via the /chaos endpoint) without any data loss or session interruption.

* **Self-healing Infrastructure**: The application utilizes Docker's restart_policy (on-failure). Combined with Spring Boot Actuator health checks, this ensures that any instance killed during a "chaos event" is automatically detected and restarted by the container runtime, maintaining the desired replica count.

* **Nginx as a Transparent Proxy**: r Nginx is used to decouple the client from the backend and provide High Availability. It monitors upstream availability in real-time (passive health checks); if an instance becomes unreachable (e.g., after the /chaos call), Nginx automatically reroutes traffic to the healthy instance, ensuring zero downtime for the user.

* **Audit Log capacity**: The audit trail is optimized for high-performance, ordered retrieval of up to 10,000 successful operations, strictly following the project's scale requirements.

# 📑 API reference

| Method | Endpoint | Description | Request Body example |
| :---: | :---: | :---: | :---: |
| POST | /stocks | Sets/Initializes the Bank's stock inventory. | 	{"stocks": [{"name": "BTC", "quantity": 100}]} |
| GET| /stocks | Returns the current state of the Bank. | None |
| POST | /wallets/{id}/stocks/{name} | Performs a single trade (buy/sell) | {"type": "buy"} or {"type": "sell"} |
| GET | /wallets/{id} | Returns the full state of a specific wallet. | None |
| GET | /wallets/{id}/stocks/{name} | Returns the quantity of a specific stock in a wallet.	| None |
| GET | /log | Returns the audit log of successful wallet operations. | None |
| POST | /chaos	| HA Test: Kills the instance serving the request. | None |

> **⚠️ Note on POST /wallets/{id}/stocks/{name} (SELL):**
The specification requires creating a wallet if it does not exist. However, for SELL operations, a newly created wallet is always empty. Since you cannot sell a stock you do not own, the system throws a StockNotAvailableException (400) immediately.
Due to Database Atomicity (@Transactional), even if a wallet.save() were called, the subsequent exception would trigger a rollback. This prevents the database from being cluttered with "ghost" empty wallets created by failed sale attempts.
