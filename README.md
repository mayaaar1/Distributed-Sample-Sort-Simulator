# Distributed Sample Sort Simulator 🚀

A high-performance distributed sorting system simulation built with **Java** and the **GridSim Toolkit**. This project demonstrates advanced concepts in parallel computing, network simulation, and load balancing.

## 📌 Project Overview
    This simulator implements the  Sample Sort  algorithm on a grid computing infrastructure. The goal is to efficiently sort a large dataset by distributing the workload across multiple worker nodes while minimizing network overhead and ensuring a perfect load balance.

### Key Technical Features:
   **Star Topology**: Entities are connected through a central **RIPRouter** .
   **Network Constraints**: Simulation includes real-world parameters like 10ms latency and 10,000 bits/s bandwidth .
   **Master-Worker Architecture**: 
           **Master (Orchestrator)**: Handles sampling, pivot calculation, and final result concatenation.
           **Workers (Compute Nodes)**: Execute independent local sorting using `Collections.sort.
          **Asynchronous Communication**: Uses `Sim_event` and specific Tags for robust data exchange.

## 💡 How the "Sample Sort" Strategy Works
Unlike a standard Merge Sort, our strategy is content-oriented:
1.    **Sampling**: The Master analyzes a subset of data to understand its distribution.
2.    **Pivot Selection**: Values are split into $P$ buckets based on the number of workers.
3.    **Zero-Overlap Distribution**: Worker k only receives values smaller than those of Worker k+1.
4.    **Instant Fusion**: The Master simply concatenates the results without a complex merge phase.

## 🧠 Why "Intelligent" Pivots? 
      In distributed computing, the biggest bottleneck is **Data Skew**. Our pivot strategy addresses this:
  **Dynamic Load Balancing**: Instead of splitting by size, we split by **value distribution**. This ensures each Worker receives an equal amount of work, even if the input data is poorly distributed.
  **Minimized Communication**: By defining clear boundaries, we eliminate the need for Workers to communicate with each other, reducing network traffic.
 **Zero-Overhead Fusion**: Because pivot Pi < P{i+1}, the Master produces the final list by simple concatenation, drastically accelerating the final phase.



## 📂 Project Structure
 `Main.java`: Network configuration and grid initialization.
 `Master.java`: Orchestration logic and data partitioning.
 `Worker.java`: Parallel compute logic and local sorting.
 [cite_start]`Yagoubi-Sample-Sort Report.pdf`: Detailed technical report[cite: 24].

## 🎓 Academic Context
Developed as part of the **Master 1 in Decision Support Systems (SID)** at **USTO-MB Univ**, Algeria.
**Author**: Zohra Mayar Yagoubi 
**Supervisor**: Mme S. SENHADJI 
