# DWP Cinema Tickets - Coding Challenge

## Overview
This is my solution to the DWP Cinema Tickets coding challenge. The original challenge can be found here: [DWP Cinema Tickets](https://github.com/dwp/cinema-tickets).

## Running the Tests
There is no main function, as the logic is tested using unit tests. To execute the test suite, run the following command:

```sh
mvn clean test
```

If all tests pass, the implementation is correct. If a test fails, Maven will highlight the test case that failed and the reason for the failure.

---

## Implemented Features
### **TicketService Implementation**
The `TicketServiceImpl` class provides the functionality to purchase tickets. Key features include:

- **Maximum Ticket Limit**: A single purchase cannot exceed **25 tickets**.
- **Adult Ticket Requirement**: There must be at least **one adult ticket** in any purchase request.
- **Invalid Account ID Handling**: The account ID must be greater than `0`.
- **Correct Pricing Calculation**:
  - **Adults:** £25 per ticket
  - **Children:** £15 per ticket
  - **Infants:** £0 (Infants do not occupy a seat)

### **Unit Tests in `TicketServiceTest`**
The test suite covers the following scenarios:

#### **Validation Tests**
Throws an exception when the **account ID is invalid** (`<= 0`).  
Throws an exception when **no adult ticket is present** in the request.  
Throws an exception when **more than 25 tickets** are requested.  

#### **Valid Purchase Scenarios**
Successfully reserves tickets when **exactly 25 tickets** are purchased.  
Allows purchases with **at least one adult and one infant**.  
Allows purchases with **at least one adult and one child**.  
