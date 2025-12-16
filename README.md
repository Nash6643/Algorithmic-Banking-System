## GitHub Repository Description

An algorithm-focused banking system simulation demonstrating how efficient data structures and sorting algorithms dramatically improve performance at scale. The project replaces slow linear operations with Binary Search Trees, Hash Tables, and advanced sorting algorithms to achieve logarithmic and constant-time efficiency for real-world banking operations.

---

# Algorithmic Analysis of Banking Data

## 📌 Overview

The **Algorithmic Banking System** is an academic project developed for an **Algorithms** course. It simulates a banking environment while showcasing how algorithmic design choices impact performance when handling large datasets such as customer accounts and transaction histories.

The system focuses on replacing inefficient **O(N)** linear operations with scalable **O(log N)** and **O(1)** solutions using classic data structures and algorithms.

**Presented by:**

* Ahmed Osama
* Utku Mert Şeren
* Omar Nashiru-Deen

**Date:** December 12, 2025

---

## ❗ Problem Statement

Traditional banking simulations often rely on linear search and basic sorting algorithms. As the number of users and transactions grows, these approaches cause:

* Slow lookup times
* Increased system lag
* Poor scalability

**Linear Search:** O(N) → becomes impractical at scale.

---

## ✅ Solution

We implemented efficient algorithms and data structures to drastically improve performance:

* **Binary Search Trees (BSTs)** for ordered account storage and fast lookup
* **Hash Tables** for instant customer profile access
* **Advanced Sorting Algorithms** to efficiently organize transaction data

This reduces lookup and processing time to **O(log N)** or **O(1)** in most cases.

---

## 🧠 Core Algorithms & Data Structures

### 1️⃣ Binary Search

**Technique:** Divide & Conquer
**Usage:** Searching sorted transaction histories
**Time Complexity:** O(log N)
**Constraint:** Requires pre-sorted data

Used to instantly locate specific transactions (e.g., "$67.67") in an account log.

---

### 2️⃣ Binary Search Tree (BST)

**Logic:**

* Left Child < Parent < Right Child

**Performance:**

* Search: O(log N) (average)
* Insert: O(log N)

**Why BST?**
Maintains a continuously sorted set of accounts, enabling fast searches without repeated sorting.

---

### 3️⃣ Merge Sort

**Technique:** Divide & Conquer
**Time Complexity:** O(N log N) (Best, Average, Worst)
**Space Complexity:** O(N)

**Why Merge Sort?**

* Stable sorting
* Preserves the order of equal elements
* Ideal for timestamped transactions

---

### 4️⃣ Quick Sort

**Technique:** Partitioning using a pivot
**Time Complexity:**

* Average: O(N log N)
* Worst: O(N²) (rare with good pivot selection)

**Why Quick Sort?**

* In-place (low memory usage)
* Extremely fast in practice due to cache efficiency

---

### 5️⃣ Heap Sort

**Technique:** Binary Max-Heap
**Time Complexity:** O(N log N)
**Space Complexity:** O(1)

**Why Heap Sort?**
Guarantees O(N log N) performance without extra memory overhead.

---

### 6️⃣ Hash Table

**Technique:** Direct key-to-index mapping
**Collision Handling:** Separate Chaining
**Time Complexity:**

* Average Retrieval: O(1)

**Why Hash Tables?**
Banking systems require instant customer access. Hashing removes the need for searching entirely.

---

## 💰 Financial Logic Implemented

### 🏦 Loan System

* Immediate loan approval
* Fixed **5% interest rate**

**Formula:**
`Total Debt = Principal × 1.05`

Example:

* Loan: $1000
* Debt: $1050

---

### 🔁 Fund Transfers

* **Atomic operations** (sender debit + receiver credit occur together)
* **Validation using BST search** to ensure both accounts exist before transfer

---

## 🧪 Simulation & Analysis Features

* Generate **1000 fake accounts** (IDs from 1000–2000)
* Sort deposits and withdrawals using different algorithms
* Performance benchmarking
* Graph-based visualization of algorithm efficiency

---

## 📊 Performance Results

**Sorting Performance Hierarchy:**

1. Quick Sort – fastest (best cache locality)
2. Heap Sort – consistent, in-place
3. Merge Sort – slowest due to memory overhead

**Search Efficiency:**

* BST search is effectively instantaneous (< 0.1 ms)
* Clearly demonstrates the advantage of O(log N) over O(N log N)

---

## 🏁 Conclusion

* Successfully replaced inefficient linear operations with scalable algorithms
* Demonstrated real-world performance benefits of algorithmic optimization
* Identified **Quick Sort** as the most practical choice for transaction logs

This project highlights how theoretical algorithm analysis translates into real system performance improvements.

---

## 🎥 Demo

A demo video is included showcasing:

* Account creation
* Loan & transfer operations
* Sorting benchmarks
* Performance graphs

---

## 📚 Academic Disclaimer

This project was developed for educational purposes as part of an Algorithms course and does not represent a production-ready banking system.

---

**Thank you for reading!**
