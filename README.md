 Tech Stack & Prerequisites
Language: Java 17 (OpenJDK)

Big Data Engine: Apache Spark 3.x (SQL & MLlib modules)

Build Automation: Apache Maven

Containerization: Docker Desktop

Dataset Source: MovieLens Latest Small Dataset

 Local & Dockerized Deployment Guide
Running via Local IDE (IntelliJ IDEA)
Clone or download the source code structure into your project directory.

Verify that your dataset arrays are correctly located under src/main/resources/.

Open the project inside IntelliJ IDEA and let Maven resolve all required artifacts defined in pom.xml.

Right-click inside MovieRecommender.java and execute the application.

Building and Running via Docker
Docker eliminates any requirement of having Java, Maven, or Apache Spark installed on the host operating system.

Open a terminal at the root folder of the project (MovieRecommenderSpark/).

Build the isolated Docker Image by running the following command:

Bash
docker build -t movie-recommender-spark .
Execute the Spark Engine inside the container container:

Bash
docker run --rm movie-recommender-spark
(Note: The --rm argument tells Docker to automatically prune and wipe the container memory/lifecycle once execution safely completes, saving host RAM).

 Verification and Analytical Output Output
Upon execution, Apache Spark initializes an in-memory cluster across all local micro-processing cores, executes the ALS model optimization, links the catalog data, and streams clean rows onto the stdout console interface:

Plaintext
 Top Movie Recommendations with Titles:
+------+-------------------------------------+------------------+
|userId|title                                |predicted_rating  |
+------+-------------------------------------+------------------+
|1     |Toy Story (1995)                     |5.797703          |
|1     |Matrix, The (1999)                   |5.6390605         |
|1     |Forrest Gump (1994)                  |5.5880027         |
|2     |Inception (2010)                     |4.859169          |
|2     |Interstellar (2014)                  |4.8248844         |
+------+-------------------------------------+------------------+
 Educational Context & Core Principles Demonstrated
Microservice and Cloud Readiness: Demonstrates cloud orchestration design patterns where backend computational workflows are compartmentalized.

Advanced Mathematical Optimization: Utilizes high-performance matrix-factorization algorithms to map multi-dimensional consumer affinity matrices.

Resource Resiliency: Features precise cluster lifecycle teardown handling through the active implementation of spark.stop() to guarantee local host computing stability.
"""

with open("README.md", "w", encoding="utf-8") as f:
f.write(readme_content)

print("README.md generated successfully.")
