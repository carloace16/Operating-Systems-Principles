public class Main {
    public static void main(String[] args) {
        // Create Green team athletes
        for (int i = 1; i <= 8; i++) {
            AthleteThread greenAthlete = new AthleteThread("Green", i);
            greenAthlete.start();
        }

        // Create Red team athletes
        for (int i = 1; i <= 8; i++) {
            AthleteThread redAthlete = new AthleteThread("Red", i);
            redAthlete.start();
        }

        // Create Judge thread
        JudgeThread judge = new JudgeThread();
        judge.start();

        try {
            // Wait for the judge to finish before terminating the program
            judge.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}