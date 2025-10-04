## 1. System Context

- The Core Banking System is responsible for managing:
- Users (Customers) — who own one or more accounts.
- Accounts — that hold balances and are associated with transactions.
- Transactions — that record all monetary movements (deposit, withdrawal, transfer, etc.).
- The system must ensure accuracy, consistency, and auditability of all financial data.

## 2. User Requirements

**Functional Requirements**
- The system shall allow a new user to be registered with basic KYC details:
  - Full name
  - Email / phone
  - Address
  - National ID or Passport number
- Each user shall have a unique identifier (e.g., customer number).
- A user may own one or more accounts.
- A user may view all their accounts and transaction history.
- A user may be marked as active, suspended, or closed.

**Business Rules**
- Each email or phone number must be unique across all users.
- Only active users can create or manage accounts.
- KYC verification is mandatory before activating a user.

## 3. Account Requirements

**Functional Requirements**
- The system shall allow the creation of new accounts for registered users.
- Each account shall have:
  - A unique account number
  - Account type (e.g., SAVINGS, CURRENT, FIXED)
  - Currency (e.g., USD, VND, EUR)
  - Initial balance (default 0 unless specified)
  - Status (ACTIVE, FROZEN, CLOSED)
- The system shall allow:
  - Deposit of funds
  - Withdrawal of funds
  - Transfer between accounts (same or different users)
- The system shall track account creation date, last updated date, and version for concurrency control.

**Business Rules**
- A user can own multiple accounts, but each account belongs to exactly one user.
- An account must be ACTIVE to allow deposit, withdrawal, or transfer.
- No transaction is allowed if the account is CLOSED or FROZEN.
- Balance must never go negative unless account type supports overdraft.
- Each account must maintain accurate balance based on all completed transactions.
- Account number must be globally unique.
- Account closure requires zero balance.

## 4. Transaction Requirements
**Functional Requirements**
- Every financial operation (deposit, withdrawal, transfer) must generate a Transaction record.
- A transaction record includes:
  - Unique transaction ID
  - Account reference
  - Transaction type: DEPOSIT, WITHDRAWAL, TRANSFER
  - Transaction amount
  - Currency
  - Reference or description
  - Timestamp
  - Status: PENDING, SUCCESS, FAILED
- Transactions should be immutable once created (cannot be modified, only reversed).
- The system shall support:
  - Viewing all transactions for an account
  - Filtering by date, type, or status
- Transfers between two accounts should be atomic:
  - Debit source account
  - Credit destination account
  - If either step fails, the transaction must roll back.

**Business Rules**
- Transaction amounts must be positive.
- A withdrawal or transfer should fail if balance is insufficient.
- Every transaction must have a unique reference ID (idempotency).
- Audit trails must exist for all transactions.
- Currency mismatches (e.g., USD → EUR) require explicit FX handling rules.
- Daily transaction limits may apply based on account type.

## 5. Security and Compliance Requirements
- All API requests must be authenticated and authorized.
- Sensitive data (e.g., account number, user info) must be encrypted in transit (HTTPS/TLS).
- All monetary operations must be logged for auditing and regulatory compliance.
- The system must comply with AML (Anti-Money Laundering) and KYC (Know Your Customer) rules.
- Reversal of a transaction must create a new compensating transaction — no direct modification.

## 6. Operational & Technical Requirements

| Category        | Requirement                                                              |
| --------------- | ------------------------------------------------------------------------ |
| **Concurrency** | Prevent race conditions on balance updates using optimistic locking      |
| **Atomicity**   | All money operations must be transactional (commit or rollback entirely) |
| **Idempotency** | Duplicate transaction requests must not cause double processing          |
| **Audit**       | Every transaction, account change, and user action must be recorded      |
| **Performance** | Must handle at least 1000 transactions/sec in production                 |
| **Scalability** | Must support horizontal scaling with stateless APIs                      |
| **Resilience**  | Must recover cleanly from network or DB failures                         |

## 7. Example Business Scenarios

| Scenario       | Description                            | Expected Behavior                                    |
| -------------- | -------------------------------------- | ---------------------------------------------------- |
| Deposit        | User deposits 500 USD                  | Account balance increases by 500; transaction logged |
| Withdraw       | User withdraws 200 USD                 | Account balance decreases by 200 if sufficient funds |
| Transfer       | User transfers 100 USD to another user | Debit sender, credit receiver, single transaction ID |
| Close Account  | User closes account                    | Allowed only if balance = 0                          |
| Frozen Account | Attempt withdrawal from frozen account | Operation denied, logged as failed                   |

## 8. Core Business Goals

- Ensure financial accuracy — every cent must be tracked.
- Provide reliability and transparency for users and regulators.
- Enable extensibility — easily support new account types or transaction features.
- Ensure security and compliance across all operations.