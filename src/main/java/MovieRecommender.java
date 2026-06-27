import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.functions;
import org.apache.spark.ml.recommendation.ALS;
import org.apache.spark.ml.recommendation.ALSModel;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class MovieRecommender {
    public static void main(String[] args) {

        // 1. Spark ke faltu logs band karne ke liye (Sirf Errors dikhenge)
        Logger.getLogger("org").setLevel(Level.ERROR);
        Logger.getLogger("include").setLevel(Level.ERROR);

        // 2. Initialize Spark Session (Local Mode)
        System.out.println(" Starting Local Spark Cluster...");
        SparkSession spark = SparkSession.builder()
                .appName("MovieRecommendationSystem")
                .master("local[*]") // Computer ke saare CPU cores use karega
                .getOrCreate();

        // 3. Dataset Load Karein (Path humare resources folder ka hai)
        System.out.println(" Loading MovieLens Dataset...");
        Dataset<Row> ratingsDF = spark.read()
                .option("header", "true")
                .option("inferSchema", "true") // Automatic data types detect karega
                .csv("src/main/resources/ratings.csv");

        System.out.println(" Total Ratings Loaded: " + ratingsDF.count());
        ratingsDF.show(5); // Pehli 5 lines dekhne ke liye

        // 4. ALS Algorithm Configure Karein (Modern Collaborative Filtering)
        ALS als = new ALS()
                .setMaxIter(10)
                .setRegParam(0.1)
                .setUserCol("userId")
                .setItemCol("movieId")
                .setRatingCol("rating")
                .setColdStartStrategy("drop"); // Naye users ke error handle karne ke liye

        // 5. Model Train Karein
        System.out.println(" Training the Recommendation Model (ALS)...");
        ALSModel model = als.fit(ratingsDF);
        System.out.println(" Model Training Complete!");

        // 6. Har User ke liye Top 5 Movies Recommend Karein
        System.out.println(" Generating Top 5 Movie Recommendations for all users...");
        /*

        Dataset<Row> userRecs = model.recommendForAllUsers(5);

        // Results ko console par dekhna
        userRecs.show(5, false);

        // 7. Spark Session Stop Karein
        spark.stop();

        */


        // 1. Model se Top 5 recommendations generate karein
        Dataset<Row> userRecs = model.recommendForAllUsers(5);

        // 2. Resources folder se 'movies.csv' file ko load karein
        Dataset<Row> moviesDF = spark.read()
                .option("header", "true")
                .option("inferSchema", "true")
                .csv("src/main/resources/movies.csv"); //  Aapka bataya hua sahi path

        // 3. Recommendations ka jo guchha (Array) hai, use tod kar alag alag rows mein convert (explode) karein
        Dataset<Row> explodedRecs = userRecs.withColumn("rec", functions.explode(functions.col("recommendations")))
                .select(
                        functions.col("userId"),
                        functions.col("rec.movieId").as("movieId"),
                        functions.col("rec.rating").as("predicted_rating")
                );

        // 4. Dono DataFrames ko 'movieId' ke zariye JOIN karein taaki Titles aa sakein
        Dataset<Row> recsWithTitles = explodedRecs.join(moviesDF, "movieId")
                .select("userId", "title", "predicted_rating")
               // .orderBy("userId", functions.col("predicted_rating").desc());
                .orderBy(functions.col("userId"), functions.col("predicted_rating").desc());

        // 5. Final output ko saaf suthra titles ke sath screen par dikhaein
        System.out.println(" Top Movie Recommendations with Titles:");
        recsWithTitles.show(20, false);

        // 6. Spark session ko close karein
        spark.stop();
        System.out.println(" Spark Session Stopped.");
    }
}