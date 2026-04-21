# Distributed Sample Sort Simulator 🚀

A high-performance distributed sorting system simulation built with **Java** and the **GridSim Toolkit**. This project demonstrates advanced concepts in parallel computing, network simulation, and load balancing.

## 📌 Project Overview
[cite_start]This simulator implements the **Sample Sort** algorithm on a grid computing infrastructure[cite: 31]. [cite_start]The goal is to efficiently sort a large dataset by distributing the workload across multiple worker nodes while minimizing network overhead and ensuring a perfect load balance[cite: 32].

### Key Technical Features:
* [cite_start]**Star Topology**: Entities are connected through a central **RIPRouter**[cite: 43, 44, 49].
* [cite_start]**Network Constraints**: Simulation includes real-world parameters like 10ms latency and 10,000 bits/s bandwidth[cite: 51, 52].
* **Master-Worker Architecture**: 
    * [cite_start]**Master (Orchestrator)**: Handles sampling, pivot calculation, and final result concatenation[cite: 62, 63].
    * [cite_start]**Workers (Compute Nodes)**: Execute independent local sorting using `Collections.sort`[cite: 69, 72].
* [cite_start]**Asynchronous Communication**: Uses `Sim_event` and specific Tags for robust data exchange[cite: 73, 76].

## 💡 How the "Sample Sort" Strategy Works
[cite_start]Unlike a standard Merge Sort, our strategy is content-oriented[cite: 82]:
1. [cite_start]**Sampling**: The Master analyzes a subset of data to understand its distribution[cite: 83].
2. [cite_start]**Pivot Selection**: Values are split into $P$ buckets based on the number of workers[cite: 84].
3. [cite_start]**Zero-Overlap Distribution**: Worker $k$ only receives values smaller than those of Worker $k+1$[cite: 86, 89].
4. [cite_start]**Instant Fusion**: The Master simply concatenates the results without a complex merge phase[cite: 92, 95].

## 🧠 Why "Intelligent" Pivots? (The Core Innovation)
In distributed computing, the biggest bottleneck is **Data Skew**. Our pivot strategy addresses this:
* **Dynamic Load Balancing**: Instead of splitting by size, we split by **value distribution**. [cite_start]This ensures each Worker receives an equal amount of work, even if the input data is poorly distributed[cite: 32, 66].
* [cite_start]**Minimized Communication**: By defining clear boundaries, we eliminate the need for Workers to communicate with each other, reducing network traffic[cite: 33, 89].
* [cite_start]**Zero-Overhead Fusion**: Because pivot $P_i < P_{i+1}$, the Master produces the final list by simple concatenation, drastically accelerating the final phase[cite: 34, 42].



## 📂 Project Structure
* `Main.java`: Network configuration and grid initialization.
* `Master.java`: Orchestration logic and data partitioning.
* `Worker.java`: Parallel compute logic and local sorting.
* [cite_start]`Yagoubi-Sample-Sort Report.pdf`: Detailed technical report[cite: 24].

## 🎓 Academic Context
[cite_start]Developed as part of the **Master 1 in Decision Support Systems (SID)** at **USTO-MB**, Algeria[cite: 26, 27].
[cite_start]**Author**: Zohra Mayar Yagoubi [cite: 28]
[cite_start]**Supervisor**: Mme S. SENHADJI [cite: 30]
