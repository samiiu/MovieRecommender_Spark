 #  MovieRecommenderSpark: Big Data-Driven Machine Learning Movie Recommendation System

An advanced Big Data and Machine Learning pipeline designed to generate fine-grained **Top-N Movie Recommendations** from large-scale user interaction data using Apache Spark's distributed computing architecture and Matrix Factorization.

---

##  Project Overview
Traditional recommendation architectures struggle with massive datasets and the "sparsity problem." This project implements a high-performance **Collaborative Filtering** engine using the **Alternating Least Squares (ALS)** algorithm to predict user preferences and maps abstract IDs to human-readable titles via optimized Spark SQL DataFrame joins.

\text{Matrix Factorization Matrix: } R \approx U \times V^T

- **R (Rating Matrix):** Sparse $m \times n$ matrix containing user interactions.
- **U (User Matrix):** Latent features capturing unique user behaviors and tastes.
- **V (Item Matrix):** Latent features capturing intrinsic movie attributes.
- **Predicted Score:** Highest values represent the optimal movies recommended to the target user.

---

##  Tech Stack & Frameworks
- **Language:** Java 17 (JDK)
- **Big Data Architecture:** Apache Spark 3.x (Spark SQL & Spark MLlib)
- **Build & Dependency Automation:** Apache Maven
- **DevOps Containerization:** Docker / Docker Desktop
- **Version Control System:** Git & GitHub

---

##  How It Works (Pipeline Architecture)



1. **Distributed Data Ingestion:** Parallel loads standard MovieLens datasets (`ratings.csv` and `movies.csv`) from the local resource bundle using automatic schema inference.
2. **In-Memory ML Training:** Feeds interaction matrices into the ALS algorithm, running distributed iterative updates (`MaxIter=10`, `RegParam=0.1`) to establish the baseline model.
3. **Data Relational Unpacking:** Uses Spark SQL `functions.explode` to flat-map nested recommended arrays into distinct, linear data rows.
4. **Contextual Title Joining:** Executes an inner relational join across dataframes using `movieId` as the primary key to swap raw integers with human-readable film titles.
5. **Portability Layer:** Packages the entire runtime cluster state (Java environment, Spark libraries, and data paths) inside an isolated Docker image.

---

##  Sample Analytics Output

| User ID | Title | Predicted Rating |
| :--- | :--- | :--- |
| 1 | Toy Story (1995) | 5.797703 |
| 1 | Matrix, The (1999) | 5.639060 |
| 1 | Forrest Gump (1994) | 5.588002 |
| 2 | Inception (2010) | 4.859169 |
| 2 | Interstellar (2014) | 4.824884 |

---

## 🚀 Getting Started

### 1. Clone the Repository
```bash
git clone [https://github.com/YOUR_USERNAME/MovieRecommenderSpark.git](https://github.com/YOUR_USERNAME/MovieRecommenderSpark.git)
cd MovieRecommenderSpark
