# Distributed-Sample-Sort-Simulator

A high-performance distributed sorting system simulation built with **Java** and the **GridSim Toolkit**. This project demonstrates concepts in parallel computing, network simulation, and load balancing.

## 📌 Project Overview
This simulator implements the **Sample Sort** algorithm on a grid computing infrastructure. The goal is to efficiently sort a large dataset by distributing the workload across multiple worker nodes while minimizing network overhead and ensuring a perfect load balance.

### Key Technical Features:
* **Star Topology**: Entities are connected through a central **RIPRouter** simulating real-world network constraints (latency, bandwidth, MTU).
* **Intelligent Partitioning**: Uses sampling to define data "pivots," ensuring that each worker receives a non-overlapping range of values.
* **Master-Worker Architecture**: 
    * **Master (Orchestrator)**: Handles sampling, pivot calculation, and final result concatenation.
    * **Workers (Compute Nodes)**: Execute independent local sorting (CPU-intensive task).
* **Asynchronous Communication**: Uses `Sim_event` and specific Tags for robust data exchange between entities.

## 🛠️ Technology Stack
* **Language**: Java
* **Simulation**: GridSim Toolkit & SimJava
* **Protocol**: RIP (Routing Information Protocol)

## 💡 How the "Sample Sort" Strategy Works
Unlike a standard Merge Sort, our Sample Sort strategy is content-oriented:
1.  **Sampling**: The Master analyzes a subset of data to understand its distribution.
2.  **Pivot Selection**: Values are split into $P$ buckets (where $P$ = number of workers).
3.  **Zero-Overlap Distribution**: Worker $k$ only receives values smaller than those of Worker $k+1$.
4.  **Instant Fusion**: Since buckets are already ordered relative to each other, the Master simply concatenates the results without needing a complex merge phase.

## 📂 Project Structure
* `Main.java`: Network configuration and grid initialization.
* `Master.java`: Orchestration logic and data partitioning.
* `Worker.java`: Parallel compute logic and local sorting.
* `/docs`: Technical report (PDF) detailing the methodology.

## 🎓 Academic Context
Developed as part of the **Master 1 in Decision Information Systems (SID)** at **USTO-MB Univ
**, Algeria.
**Author**: Zohra Mayar Yagoubi
**Supervisor**: Mme S. SENHADJI
